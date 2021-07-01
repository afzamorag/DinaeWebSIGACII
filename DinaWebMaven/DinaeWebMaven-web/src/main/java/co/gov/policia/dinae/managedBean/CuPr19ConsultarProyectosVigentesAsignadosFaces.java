package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CompromisoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.modelo.ComentarioCompromisoProyecto;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuPr19ProyectosVigentesAsignadosFaces")
@javax.enterprise.context.SessionScoped
public class CuPr19ConsultarProyectosVigentesAsignadosFaces extends JSFUtils implements Serializable {

  private List<ProyectoDTO> proyectosInstitucionales;

  private List<ProyectoDTO> proyectosFinanciados;

  private List<ComentarioCompromisoProyecto> listaComentarioCompromisoProyecto;
  private CompromisoDTO compromisoDTOSeleccionado;

  @EJB
  private IComentarioProyectoLocal iComentarioProyectoLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @javax.inject.Inject
  private CuPr10RegistroInformeFinal cuPr10RegistroInformeFinal;

  @javax.inject.Inject
  private CuPr7RegistrarAvanceInvestigacionFaces cuPr7RegistrarAvanceInvestigacionFaces;

  @javax.inject.Inject
  private CuPr1ProyectoFaces cuPr1ProyectoFaces;

  /**
   *
   * @return
   */
  public String initReturnCU() {

    String navega = null;

    try {

      compromisoDTOSeleccionado = null;
      listaComentarioCompromisoProyecto = null;

      Long idUnidadPolicial = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial();
      this.proyectosInstitucionales = iProyectoLocal.getProyectosVigentesByModalidad(idUnidadPolicial, 101L);
      this.proyectosFinanciados = iProyectoLocal.getProyectosVigentesByModalidad(idUnidadPolicial, 100L);

      navega = navigationFaces.redirectCuPr19ConsultarProyectosVigentesAsignadosRedirect();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr19ConsultarProyectosVigentesAsignadosFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      String name = keyPropertiesFactory.value("cu_co_1_dir_file_archivo_soporte") + "pautas.pdf";

      InputStream stream = new FileInputStream(name);
      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "PAUTAS y contenido de apoyo PARA LA INVESTIGACIÓN.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-19 (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    compromisoDTOSeleccionado = null;
    listaComentarioCompromisoProyecto = null;
  }

  /**
   *
   * @param proyectoInstitucionalOconvocatoria
   * @param compromisoDTO
   * @param proyectoDTO
   * @return
   */
  public String administrarCompromiso(String proyectoInstitucionalOconvocatoria, CompromisoDTO compromisoDTO, ProyectoDTO proyectoDTO) {

    try {

      //Formulación del proyecto: (PARA LOS PROYECTOS INSTITUCIONALES ES OBLIGATORIO - SOLO UNO EN CASO EXISTA)
      //Informe de avance 1: (PARA LOS PROYECTOS INSTITUCIONALES Y/O CONVOVATORIA ES OBLIGATORIO, POR LO MENOS UN INFORME)
      //Informe final: (PARA LOS PROYECTOS INSTITUCIONALES Y/O CONVOVATORIA ES OBLIGATORIO, SOLO UN INFORME FINAL)
      CompromisoProyecto compromisoProyectoAnterior = iCompromisoProyectoLocal.obtenerCompromisoProyectoAnteriorTipoAvance(
              compromisoDTO.getIdCompromisoProyecto(),
              compromisoDTO.getTipoCompromiso());

      //EL COMPROMISO ANTERIOR, SI EXISTE, DEBE ESTAR EN ESTADO CUMPLIDO                
      if (compromisoProyectoAnterior != null && !compromisoProyectoAnterior.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)) {

        adicionaMensajeError("El compromiso anterior (".concat(compromisoProyectoAnterior.getNombreCompromisoCorreccionNumeroIncrementa()).concat(") debe estar CUMPLIDO para poder continuar"));
        return null;

      }
      //SI EL TIPO DE COMPROMISO ES "COMPROMISO_PERIODO_INFORME_FINAL" O "COMPROMISO_PERIODO_INFORME_DE_AVANCE"
      if (compromisoDTO.getTipoCompromiso().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE) || compromisoDTO.getTipoCompromiso().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)) {

        if ("P_INSTITUCIONAL".equals(proyectoInstitucionalOconvocatoria)) {

          //VERIFICAMOS EL ESTADO DEL COMPROMISO
          if (compromisoProyectoAnterior.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)
                  || compromisoProyectoAnterior.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO)) {

            CompromisoProyecto compromisoProyectoTMP = iCompromisoProyectoLocal.obtenerCompromisoProyecto(compromisoDTO.getIdCompromisoProyecto());
            adicionaMensajeError("Para actualizar al compromiso \""
                    .concat(compromisoProyectoTMP.getNombreCompromisoCorreccionNumeroIncrementa()
                            .concat("\" primero debe actualizar el compromiso \""
                                    .concat(compromisoProyectoAnterior.getNombreCompromisoCorreccionNumeroIncrementa()
                                            .concat(" \"")))));
            return null;
          }
        } else if ("P_CONVOCATORIA".equals(proyectoInstitucionalOconvocatoria)) {

          //PARA: P_CONVOCATORIA PUEDE HABER O NO COMPROMISO ANTERIOR "FORMULACIÓN DEL PROYECTO" SISMPRE Y CUANDO 
          //SU COMPROMISO CONSECUENTE SE DE TIPO "COMPROMISO_PERIODO_INFORME_DE_AVANCE"
          if ((compromisoDTO.getTipoCompromiso().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                  && compromisoProyectoAnterior != null)
                  || compromisoDTO.getTipoCompromiso().equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {

            CompromisoProyecto compromisoProyectoTMP = iCompromisoProyectoLocal.obtenerCompromisoProyecto(compromisoDTO.getIdCompromisoProyecto());

            //VERIFICAMOS EL ESTADO DEL COMPROMISO
            if (compromisoProyectoAnterior.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)) {

              adicionaMensajeError("Para actualizar al compromiso \""
                      .concat(compromisoProyectoTMP.getNombreCompromisoCorreccionNumeroIncrementa()
                              .concat("\" primero debe actualizar el compromiso \""
                                      .concat(compromisoProyectoAnterior.getNombreCompromisoCorreccionNumeroIncrementa()
                                              .concat(" \"")))));
              return null;
            }

          }

        }

      }
      //VERIFIMOS QUE EL ESTADO COMPROMISO ANTERIOR
      if ("P_INSTITUCIONAL".equals(proyectoInstitucionalOconvocatoria)) {

        if ("F".equals(compromisoDTO.getTipoInforme()) && compromisoDTO.isMuestraLink()) {

          return cuPr10RegistroInformeFinal.initRetrunCUFromCUPR19(proyectoDTO.getId(), compromisoDTO.getIdCompromisoProyecto());

        }

        if ("A".equals(compromisoDTO.getTipoInforme()) && compromisoDTO.isMuestraLink()) {

          return cuPr7RegistrarAvanceInvestigacionFaces.initReturnCU_Desde_CU_PR_19(
                  proyectoDTO.getId(),
                  compromisoDTO.getIdCompromisoProyecto(),
                  compromisoDTO.getNumeroIncrementa());

        }

        if ("N/A".equals(compromisoDTO.getTipoInforme()) && compromisoDTO.isMuestraLink()) {

          return cuPr1ProyectoFaces.initReturnCU_Desde_CU_PR_19_Institucional(
                  proyectoDTO.getId(),
                  compromisoDTO.getIdCompromisoProyecto());

        }

      } else if ("P_CONVOCATORIA".equals(proyectoInstitucionalOconvocatoria)) {

        if ("F".equals(compromisoDTO.getTipoInforme()) && compromisoDTO.isMuestraLink()) {

          return cuPr10RegistroInformeFinal.initRetrunCUFromCUPR19(
                  proyectoDTO.getId(),
                  compromisoDTO.getIdCompromisoProyecto());

        }

        if ("A".equals(compromisoDTO.getTipoInforme()) && compromisoDTO.isMuestraLink()) {

          return cuPr7RegistrarAvanceInvestigacionFaces.initReturnCU_Desde_CU_PR_19(
                  proyectoDTO.getId(),
                  compromisoDTO.getIdCompromisoProyecto(),
                  compromisoDTO.getNumeroIncrementa());

        }

        if ("N/A".equals(compromisoDTO.getTipoInforme()) && compromisoDTO.isMuestraLink()) {

          return cuPr1ProyectoFaces.initReturnCU_Desde_CU_PR_19_Financiacion(
                  proyectoDTO.getId(),
                  compromisoDTO.getIdCompromisoProyecto());

        }

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-19 (administrarCompromiso)", e);

    }

    return null;
  }

  /**
   *
   * @param compromisoDTOSeleccionado
   * @return
   */
  public String verComentariosCompromisoProyecto(CompromisoDTO compromisoDTOSeleccionado) {

    try {

      this.compromisoDTOSeleccionado = compromisoDTOSeleccionado;

      listaComentarioCompromisoProyecto = iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorIdCompromisoProyecto(
              compromisoDTOSeleccionado.getIdCompromisoProyecto(),
              IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_25);

      if (compromisoDTOSeleccionado.getIdCompromisoPadre() != null) {

        listaComentarioCompromisoProyecto.addAll(
                iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorIdCompromisoProyecto(
                        compromisoDTOSeleccionado.getIdCompromisoPadre(),
                        IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_24)
        );

      }

      if (listaComentarioCompromisoProyecto == null || listaComentarioCompromisoProyecto.isEmpty()) {

        adicionaMensajeError("El compromiso seleccionado no tiene comentarios.");
        return null;
      }

      return navigationFaces.redirectCuPr19ConsultarProyectosVigentesAsignadosVerComentariosRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-19 (verComentariosCompromisoProyecto)", e);

    }
    return null;
  }

  public List<ProyectoDTO> getProyectosInstitucionales() {
    return proyectosInstitucionales;
  }

  public void setProyectosInstitucionales(List<ProyectoDTO> proyectosInstitucionales) {
    this.proyectosInstitucionales = proyectosInstitucionales;
  }

  public List<ProyectoDTO> getProyectosFinanciados() {
    return proyectosFinanciados;
  }

  public void setProyectosFinanciados(List<ProyectoDTO> proyectosFinanciados) {
    this.proyectosFinanciados = proyectosFinanciados;
  }

  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyecto() {
    return listaComentarioCompromisoProyecto;
  }

  public String getNombreCompromisoComentario() {

    if (compromisoDTOSeleccionado == null) {
      return null;
    }

    return compromisoDTOSeleccionado.getCompromiso();
  }

  public boolean validarLinkHabilitado(CompromisoDTO compromisoDTO) {

    if (compromisoDTO == null || compromisoDTO.getIdEstadoCompromiso() == null) {
      return false;
    }

    return (compromisoDTO.getIdEstadoCompromiso().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)
            || compromisoDTO.getIdEstadoCompromiso().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION)
            || compromisoDTO.getIdEstadoCompromiso().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO))
            && DatesUtils.compareDate(compromisoDTO.getFecha(), new Date()) == -1;
  }

}
