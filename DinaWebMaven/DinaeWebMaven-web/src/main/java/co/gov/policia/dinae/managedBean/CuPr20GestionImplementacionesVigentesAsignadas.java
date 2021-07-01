package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CompromisoImplementacionDTO;
import co.gov.policia.dinae.dto.ImplentacionProyectoCompromisosDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.modelo.ComentarioCompromisoProyecto;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuPr20GestionImplementacionesVigentesAsignadasFaces")
@javax.enterprise.context.SessionScoped
public class CuPr20GestionImplementacionesVigentesAsignadas extends JSFUtils implements Serializable {

  @EJB
  private IImplementacionProyectoLocal _iImplProyectos;

  @EJB
  private ICompromisoImplementacionLocal _compromisoImpl;

  @EJB
  private IComentarioProyectoLocal iComentarioProyectoLocal;

  private List<ImplentacionProyectoCompromisosDTO> _implemtacionesProyectos;

  @javax.inject.Inject
  private CuPr21RegistrarPlanDeTrabajoFaces cuPr21RegistrarPlanDeTrabajoFaces;

  @javax.inject.Inject
  private CuPr15_1_2_AvanceImplemenacionFaces cuPr15_1_2_AvanceImplemenacionFaces;

  private List<ComentarioCompromisoProyecto> listaComentarioCompromisoProyecto;

  private CompromisoImplementacionDTO compromisoImplementacionDTOSeleccionadoComentario;

  private static final List<Long> listaIdEstadoProyecto = new ArrayList<Long>(2);

  static {
    listaIdEstadoProyecto.add(IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION);
    listaIdEstadoProyecto.add(IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO);
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {
    try {

      listaComentarioCompromisoProyecto = null;

      _implemtacionesProyectos = new ArrayList<ImplentacionProyectoCompromisosDTO>();
      Long idUnidadPolicial = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial();

      _implemtacionesProyectos = _iImplProyectos.findAllImplementacionesVigentes(listaIdEstadoProyecto, idUnidadPolicial);

      if (_implemtacionesProyectos != null && !_implemtacionesProyectos.isEmpty()) {

        for (ImplentacionProyectoCompromisosDTO implProyecto : _implemtacionesProyectos) {
          List<CompromisoImplementacionDTO> listaCompromisoImplementacionDTO = _compromisoImpl.getListaCompromisoImplementacionDTOPorIdImplementacionProyecto(implProyecto.getIdImplementacionProy());
          implProyecto.setCompromisosImplProyecto(listaCompromisoImplementacionDTO);
        }
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr20GestionImplementacionesVigentesAsignadas.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navigationFaces.redirectCuPr20GestionImplementacionesVigentesAsignadasRedirect();
  }

  /**
   *
   * @param compromisoImplementacionDTOSeleccionado
   * @return
   */
  public String verComentariosCompromisoProyecto(CompromisoImplementacionDTO compromisoImplementacionDTOSeleccionado) {

    try {

      this.compromisoImplementacionDTOSeleccionadoComentario = compromisoImplementacionDTOSeleccionado;

      listaComentarioCompromisoProyecto = iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(
              compromisoImplementacionDTOSeleccionado.getIdCompromisoImplementacion(),
              IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_25);

      if (compromisoImplementacionDTOSeleccionadoComentario.getIdCompromisoPadre() != null) {

        listaComentarioCompromisoProyecto.addAll(
                iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(
                        compromisoImplementacionDTOSeleccionadoComentario.getIdCompromisoPadre(),
                        IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_24)
        );

      }

      if (listaComentarioCompromisoProyecto == null || listaComentarioCompromisoProyecto.isEmpty()) {

        adicionaMensajeError("El compromiso seleccionado no tiene comentarios.");
        return null;
      }

      return navigationFaces.redirectCuPr20GestionImplementacionesVigentesAsignadasVerComentariosRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-19 (verComentariosCompromisoProyecto)", e);

    }
    return null;
  }

  /**
   *
   * @param implentacionProyectoCompromisosDTO
   * @param idCompromisoImplementacion
   * @return
   */
  public String navegarCU(ImplentacionProyectoCompromisosDTO implentacionProyectoCompromisosDTO, Long idCompromisoImplementacion) {
    String navegar = null;
    try {

      Long idTipoCompromiso = _compromisoImpl.obtenerIdTipoCompromisoImplementacionPorId(idCompromisoImplementacion);

      if (IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO.compareTo(idTipoCompromiso) == 0) {

        navegar = cuPr21RegistrarPlanDeTrabajoFaces.initReturnCU_DESDE_PR_20(
                implentacionProyectoCompromisosDTO.getIdImplementacionProy(),
                idCompromisoImplementacion, false);
      }

      if (IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE.equals(idTipoCompromiso)
              || IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL.equals(idTipoCompromiso)) {

        navegar = cuPr15_1_2_AvanceImplemenacionFaces.initReturnCU_DESDE_PR_20(
                implentacionProyectoCompromisosDTO.getIdImplementacionProy(),
                idCompromisoImplementacion, false);
      }
    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr20GestionImplementacionesVigentesAsignadas.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navegar;
  }

  public List<ImplentacionProyectoCompromisosDTO> getImplemtacionesProyectos() {
    return _implemtacionesProyectos;
  }

  public void setImplemtacionesProyectos(List<ImplentacionProyectoCompromisosDTO> _implemtacionesProyectos) {
    this._implemtacionesProyectos = _implemtacionesProyectos;
  }

  public boolean isMostrarLinkCompromiso(CompromisoImplementacionDTO dTO) {

    return dTO.getIdEstadoCompromisoImpl().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)
            || dTO.getIdEstadoCompromisoImpl().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION)
            || dTO.getIdEstadoCompromisoImpl().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO);

  }

  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyecto() {
    return listaComentarioCompromisoProyecto;
  }

  public CompromisoImplementacionDTO getCompromisoImplementacionDTOSeleccionadoComentario() {
    return compromisoImplementacionDTOSeleccionadoComentario;
  }

}
