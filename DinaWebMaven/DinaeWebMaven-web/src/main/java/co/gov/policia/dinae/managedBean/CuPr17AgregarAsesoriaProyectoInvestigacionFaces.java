package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.interfaces.IAsesoriaProyectoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.AsesoriaProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr17AgregarAsesoriaProyectoInvestigacionFaces")
@javax.enterprise.context.SessionScoped
public class CuPr17AgregarAsesoriaProyectoInvestigacionFaces extends JSFUtils implements Serializable {

  @EJB
  private IAsesoriaProyectoLocal iAsesoriaProyectoLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  private String tipoProyectoSeleccionado;
  private List<SelectItem> listaItemTipoProyectoSeleccionado;
  private List<SelectItem> listaItemUnidadPolicialSeleccionado;
  private String codigoProyecto;
  private Long idUnidadPolicialSeleccionado;
  private List<ProyectoDTO> listaProyectoDTOAsesoriasConsulta;
  private ProyectoDTO proyectoDTOSeleccionado;
  private ListGenericDataModel<AsesoriaProyectoDTO> listaAsesoriaProyectoDTO;
  private AsesoriaProyectoDTO asesoriaProyectoDTO;
  private AsesoriaProyectoDTO asesoriaProyectoDTOSeleccionModifica;
  private UsuarioRol usuarioRol;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      usuarioRol = null;
      listaAsesoriaProyectoDTO = null;
      asesoriaProyectoDTO = null;
      asesoriaProyectoDTOSeleccionModifica = null;
      proyectoDTOSeleccionado = null;
      listaProyectoDTOAsesoriasConsulta = null;
      listaItemTipoProyectoSeleccionado = null;
      listaItemUnidadPolicialSeleccionado = null;
      tipoProyectoSeleccionado = null;
      codigoProyecto = null;
      idUnidadPolicialSeleccionado = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-17", e);

    }
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      usuarioRol = new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.AUTORIZADO_PARA_REALIZAR_ASESORIAS_EN_LA_VICIN)
              .getIdUsuarioRol());

      listaItemTipoProyectoSeleccionado = new ArrayList<SelectItem>();
      listaItemTipoProyectoSeleccionado.add(new SelectItem("VIC", "Proyecto institucional"));
      listaItemTipoProyectoSeleccionado.add(new SelectItem("CONV", "De convocatoria de financiación"));
      listaItemTipoProyectoSeleccionado.add(new SelectItem("TRABAJO_GRADO", "Trabajo de grado"));

      listaItemUnidadPolicialSeleccionado = UtilidadesItem.getListaSel(iUnidadPolicialLocal.getUnidadPolicial(), "idUnidadPolicial", "siglaFisicaYnombreUnidad");

      return navigationFaces.redirectCuPr17AgregarAsesoriaProyectoInvestigacionRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-17", e);

    }

    return null;

  }

  public void noSeleccionAsesoria(UnselectEvent event) {
    asesoriaProyectoDTOSeleccionModifica = null;
  }

  /**
   *
   * @param event
   */
  public void verDatalleAsesoria(SelectEvent event) {
    try {
      if (asesoriaProyectoDTOSeleccionModifica != null) {

        this.asesoriaProyectoDTO = asesoriaProyectoDTOSeleccionModifica;
        navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuPr17AgregarAsesoriaProyectoInvestigacionDetalleRedirect());

      }

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-17", ex);

    }
  }

  /**
   *
   * @return
   */
  public String guardarAsesoria() {

    try {

      AsesoriaProyecto asesoriaProyecto;

      if (asesoriaProyectoDTO.getId() == null) {
        //NUEVA ASESORIA 
        asesoriaProyecto = new AsesoriaProyecto();
        asesoriaProyecto.setProyecto(new Proyecto(proyectoDTOSeleccionado.getId()));
        asesoriaProyecto.setUsuarioRol(usuarioRol);
        asesoriaProyecto.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      } else {

        asesoriaProyecto = iAsesoriaProyectoLocal.getAsesoriaProyecto(asesoriaProyectoDTO.getId());
        asesoriaProyecto.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        asesoriaProyecto.setUsuarioRolActualiza(usuarioRol);

      }

      String grado = loginFaces.getPerfilUsuarioDTO().getGrado();
      if (grado == null) {
        grado = "";
      }
      grado = grado.concat(" - ");
      asesoriaProyecto.setEvaluador(grado.concat(loginFaces.getPerfilUsuarioDTO().getNombreCompleto()));
      asesoriaProyecto.setOtros(asesoriaProyectoDTO.getOpcionesSeleccionadaModalidad().contains("O") ? 'S' : 'N');
      asesoriaProyecto.setPresencial(asesoriaProyectoDTO.getOpcionesSeleccionadaModalidad().contains("P") ? 'S' : 'N');
      asesoriaProyecto.setTelefonica(asesoriaProyectoDTO.getOpcionesSeleccionadaModalidad().contains("T") ? 'S' : 'N');
      asesoriaProyecto.setVirtual(asesoriaProyectoDTO.getOpcionesSeleccionadaModalidad().contains("V") ? 'S' : 'N');
      asesoriaProyecto.setResultadoAsesoria(asesoriaProyectoDTO.getResultadoAsesoria());

      iAsesoriaProyectoLocal.guardarAsesoriaProyecto(asesoriaProyecto);

      cargarListaAsesoresProyecto();

      asesoriaProyectoDTO = new AsesoriaProyectoDTO();

      adicionaMensajeInformativo("La asesoría fue almacenada correctamente");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-17", e);

    }

    return null;

  }

  private void cargarListaAsesoresProyecto() throws Exception {

    listaAsesoriaProyectoDTO = new ListGenericDataModel(iAsesoriaProyectoLocal.getListaAsesoriaProyectoDTOPorIdProyecto(proyectoDTOSeleccionado.getId()));

  }

  /**
   * @param proyectoDTOSeleccionado
   * @return
   */
  public String agregarAsesoria(ProyectoDTO proyectoDTOSeleccionado) {

    try {

      this.proyectoDTOSeleccionado = proyectoDTOSeleccionado;

      asesoriaProyectoDTO = new AsesoriaProyectoDTO();

      cargarListaAsesoresProyecto();

      return navigationFaces.redirectCuPr17AgregarAsesoriaProyectoInvestigacionDetalleRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-17", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String limpiar() {

    proyectoDTOSeleccionado = null;
    tipoProyectoSeleccionado = null;
    codigoProyecto = null;
    idUnidadPolicialSeleccionado = null;
    listaProyectoDTOAsesoriasConsulta = new ArrayList<ProyectoDTO>();

    return navigationFaces.redirectCuPr17AgregarAsesoriaProyectoInvestigacionRedirect();

  }

  /**
   *
   * @return
   */
  public String buscarProyectos() {

    try {

      listaProyectoDTOAsesoriasConsulta = iProyectoLocal.getListaProyectoDTOfiltroBusquedaPorUnidaPoliCodigoProTipoProyecto(
              idUnidadPolicialSeleccionado,
              codigoProyecto,
              tipoProyectoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-17", e);

    }

    return null;

  }

  public String getTipoProyectoSeleccionado() {
    return tipoProyectoSeleccionado;
  }

  public void setTipoProyectoSeleccionado(String tipoProyectoSeleccionado) {
    this.tipoProyectoSeleccionado = tipoProyectoSeleccionado;
  }

  public List<SelectItem> getListaItemTipoProyectoSeleccionado() {
    return listaItemTipoProyectoSeleccionado;
  }

  public void setListaItemTipoProyectoSeleccionado(List<SelectItem> listaItemTipoProyectoSeleccionado) {
    this.listaItemTipoProyectoSeleccionado = listaItemTipoProyectoSeleccionado;
  }

  public Long getIdUnidadPolicialSeleccionado() {
    return idUnidadPolicialSeleccionado;
  }

  public void setIdUnidadPolicialSeleccionado(Long idUnidadPolicialSeleccionado) {
    this.idUnidadPolicialSeleccionado = idUnidadPolicialSeleccionado;
  }

  public List<SelectItem> getListaItemUnidadPolicialSeleccionado() {
    return listaItemUnidadPolicialSeleccionado;
  }

  public void setListaItemUnidadPolicialSeleccionado(List<SelectItem> listaItemUnidadPolicialSeleccionado) {
    this.listaItemUnidadPolicialSeleccionado = listaItemUnidadPolicialSeleccionado;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public List<ProyectoDTO> getListaProyectoDTOAsesoriasConsulta() {
    return listaProyectoDTOAsesoriasConsulta;
  }

  public ProyectoDTO getProyectoDTOSeleccionado() {
    return proyectoDTOSeleccionado;
  }

  public void setProyectoDTOSeleccionado(ProyectoDTO proyectoDTOSeleccionado) {
    this.proyectoDTOSeleccionado = proyectoDTOSeleccionado;
  }

  public ListGenericDataModel<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTO() {
    return listaAsesoriaProyectoDTO;
  }

  public void setListaAsesoriaProyectoDTO(ListGenericDataModel<AsesoriaProyectoDTO> listaAsesoriaProyectoDTO) {
    this.listaAsesoriaProyectoDTO = listaAsesoriaProyectoDTO;
  }

  public AsesoriaProyectoDTO getAsesoriaProyectoDTO() {
    return asesoriaProyectoDTO;
  }

  public void setAsesoriaProyectoDTO(AsesoriaProyectoDTO asesoriaProyectoDTO) {
    this.asesoriaProyectoDTO = asesoriaProyectoDTO;
  }

  public AsesoriaProyectoDTO getAsesoriaProyectoDTOSeleccionModifica() {
    return asesoriaProyectoDTOSeleccionModifica;
  }

  public void setAsesoriaProyectoDTOSeleccionModifica(AsesoriaProyectoDTO asesoriaProyectoDTOSeleccionModifica) {
    this.asesoriaProyectoDTOSeleccionModifica = asesoriaProyectoDTOSeleccionModifica;
  }

}
