package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL;
import static co.gov.policia.dinae.constantes.IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE;
import static co.gov.policia.dinae.constantes.IConstantesRole.ENCARGADO_DE_HABILITAR_IMPLEMENTACION_DE_PROYECTOS_EN_VICIN;
import static co.gov.policia.dinae.constantes.IConstantes.RESULTADO_VALIDACION_PROYECTOS_CODIGO_INEXISTENTE;
import static co.gov.policia.dinae.constantes.IConstantes.RESULTADO_VALIDACION_PROYECTOS_NO_EVALUADOS;
import co.gov.policia.dinae.dto.ImplementacionesProyectoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Édder Peña Barranco
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 * @since 2013/12/21
 */
@javax.inject.Named(value = "cuPr13HabilitarImplementacion")
@javax.enterprise.context.SessionScoped
public class CuPr13HabilitarImplementacionFaces extends JSFUtils implements Serializable {

  @EJB
  private IProyectoLocal iProyectoLocal;
  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;
  @EJB
  private IImplementacionProyectoLocal iImplementacionProyectoLocal;

  private String palabraClave;
  private Long unidadPolicial;
  private List<SelectItem> listaItemsUnidadesPoliciales;
  private String codigoProyecto;
  private Date fechaPresentacionEntre;
  private Date fechaPresentacionY;
  private List<ProyectoDTO> listaProyectosEncontrados;
  private ProyectoDTO proyectoSeleccionado;
  private String nombreUnidadPolicial;
  private Date fechaProyecto;
  private String areaProyecto;
  private String lineaProyecto;
  private String tituloProyecto;
  private Long unidadResponsable;
  private ImplementacionesProyecto implementacionProy;
  private List<ImplementacionesProyectoDTO> listaImplementacionesProyectoActuales;
  private static final Long[] listaEstadosProyectos = new Long[]{TIPO_ESTADO_PROYECTO_EVALUADO, TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION, TIPO_ESTADO_PROYECTO_IMPLEMENTADO};

  public String initReturnCU() {

    cargarListaUnidadesPoliciales();
    listaProyectosEncontrados = new ArrayList<ProyectoDTO>();

    return navigationFaces.redirectCuPr13BuscarProyectosParaImplementarRedirect();
  }

  /**
   *
   */
  private void cargarListaUnidadesPoliciales() {

    try {
      listaItemsUnidadesPoliciales = UtilidadesItem.getListaSel(
              iUnidadPolicialLocal.getUnidadPolicial(),
              "idUnidadPolicial",
              "siglaFisicaYnombreUnidad");

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_unidades_policiales_no_encontradas"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @return
   */
  public String buscarProyectoAImplementar() {
    String criterioPalabraClave = null;
    Long criterioUnidadPolicial = null;
    String criterioCodigoProyecto = null;
    Date criterioFechaPresentacionEntre = null;
    Date criterioFechaPresentacionY = null;
    Long resultado = 0L;
    listaProyectosEncontrados = new ArrayList<ProyectoDTO>();

    if (palabraClave != null && !palabraClave.equals("")) {
      criterioPalabraClave = palabraClave;
    }
    if (unidadPolicial != null && unidadPolicial != -1L) {
      criterioUnidadPolicial = unidadPolicial;
    }
    if (codigoProyecto != null && !codigoProyecto.equals("")) {
      criterioCodigoProyecto = codigoProyecto;
    }
    if (fechaPresentacionEntre != null
            && (fechaPresentacionEntre.before(new Date()) || fechaPresentacionEntre.equals(new Date()))
            && fechaPresentacionEntre.before(fechaPresentacionY)) {
      criterioFechaPresentacionEntre = fechaPresentacionEntre;
    }
    if (fechaPresentacionY != null
            && (fechaPresentacionY.before(new Date()) || fechaPresentacionY.equals(new Date())
            && fechaPresentacionY.after(fechaPresentacionEntre))) {
      criterioFechaPresentacionY = fechaPresentacionY;
    }
    try {
      /*if (codigoProyecto != null && !codigoProyecto.equals(""))
            {
                resultado = iProyectoLocal.validateProyectosByCodigoProyecto(TIPO_ESTADO_PROYECTO_EVALUADO, criterioCodigoProyecto);
            }
            
            if (resultado.equals(RESULTADO_VALIDACION_PROYECTOS_CODIGO_INEXISTENTE))
            {
                adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_codigo_proyecto_inexistente"));
                return null;
            }
            else if (resultado.equals(RESULTADO_VALIDACION_PROYECTOS_NO_EVALUADOS))
            {
                adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_codigo_proyecto_no_evaluado"));
                return null;
            }*/

      listaProyectosEncontrados = iProyectoLocal.getProyectosEjecutadosByFiltros(
              listaEstadosProyectos, criterioPalabraClave,
              criterioUnidadPolicial, criterioCodigoProyecto, criterioFechaPresentacionEntre,
              criterioFechaPresentacionY);

      if (listaProyectosEncontrados == null || listaProyectosEncontrados.isEmpty()) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_13_error_proyectos_no_encontrados"));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }

  public void seleccionarProyectoParaImplementacion(SelectEvent event) {

    navigationFaces.redirectCuPr13HabilitarImplementacionRedirect();

  }

  public String resetearFormulario() {
    palabraClave = null;
    unidadPolicial = -1L;
    codigoProyecto = null;
    fechaPresentacionEntre = null;
    fechaPresentacionY = null;
    listaProyectosEncontrados = new ArrayList<ProyectoDTO>();
    return "";
  }

  /**
   *
   * @param idProyecto
   * @throws Exception
   */
  private void actualizarListaProyectosImplementados(Long idProyecto) throws Exception {

    listaImplementacionesProyectoActuales = iImplementacionProyectoLocal.getImplementacionProyectoByProyecto(idProyecto);

  }

  /**
   *
   * @param event
   */
  public void irHabilitacionImplementacionProyecto(SelectEvent event) {
    try {
      actualizarListaProyectosImplementados(proyectoSeleccionado.getId());

      navigationFaces.redirectFacesCuPr13HabilitarImplementacion();

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public String habilitarImplementacionProyecto() {
    try {

      if (unidadResponsable == null || unidadResponsable == -1) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_unidad_responsable_obligatoria"));
        return null;

      }

      //VERIFICAMOS SI EL PROYECTO SE ENCUENTRA IMPLEMENTADO EN LA UNIDAD SELECCIONADA
      ImplementacionesProyecto implementacionesProyectoUnidad = iImplementacionProyectoLocal.getImplementacionProyectoByProyectoAndUnidadPolicial(
              proyectoSeleccionado.getId(), unidadResponsable);

      if (implementacionesProyectoUnidad != null && implementacionesProyectoUnidad.getIdImplementacionProy() != null) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_implementacion_existente"));
        return null;

      }

      implementacionProy = new ImplementacionesProyecto();
      implementacionProy.setProyecto(new Proyecto(proyectoSeleccionado.getId()));
      implementacionProy.setUnidadPolicial(new UnidadPolicial(unidadResponsable));

      RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().
              getRolUsuarioPorIdRolDTO(ENCARGADO_DE_HABILITAR_IMPLEMENTACION_DE_PROYECTOS_EN_VICIN);
      UsuarioRol usuarioRol;
      if (rolUsuarioDTO == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_rol_usuario_invalido"));
        return null;
      }
      if (rolUsuarioDTO.getIdUsuarioRol() == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_rol_usuario_invalido"));
        return null;
      }
      usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());
      implementacionProy.setIdUsuarioRol(usuarioRol);
      implementacionProy.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      implementacionProy.setFechaRegistro(new Date());
      implementacionProy.setEstadoImplementacion(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION));
      implementacionProy.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      // CREANDO EL COMPROMISO DEL PLAN DE TRABAJO
      CompromisoImplementacion compromisoPlanTrabajo = new CompromisoImplementacion();
      compromisoPlanTrabajo.setFechaCreacion(new Date());
      compromisoPlanTrabajo.setImplementacionesProyecto(implementacionProy);
      compromisoPlanTrabajo.setIdEstadoCompromisoImpl(new Constantes(ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
      compromisoPlanTrabajo.setIdTipoCompromiso(new Constantes(TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO));
      compromisoPlanTrabajo.setIdUsuarioRol(usuarioRol);
      compromisoPlanTrabajo.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      compromisoPlanTrabajo.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      compromisoPlanTrabajo.setFechaCreacion(new Date());

      // CREANDO EL COMPROMISO DEL INFORME DE AVANCE
      CompromisoImplementacion compromisoInformeAvance = new CompromisoImplementacion();
      compromisoInformeAvance.setFechaCreacion(new Date());
      compromisoInformeAvance.setImplementacionesProyecto(implementacionProy);
      compromisoInformeAvance.setIdEstadoCompromisoImpl(new Constantes(ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
      compromisoInformeAvance.setIdTipoCompromiso(new Constantes(TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE));
      compromisoInformeAvance.setIdUsuarioRol(usuarioRol);
      compromisoInformeAvance.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      compromisoInformeAvance.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      compromisoInformeAvance.setFechaCreacion(new Date());

      // CREANDO EL COMPROMISO DEL INFORME FINAL
      CompromisoImplementacion compromisoInformeFinal = new CompromisoImplementacion();
      compromisoInformeFinal.setFechaCreacion(new Date());
      compromisoInformeFinal.setImplementacionesProyecto(implementacionProy);
      compromisoInformeFinal.setIdEstadoCompromisoImpl(new Constantes(ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
      compromisoInformeFinal.setIdTipoCompromiso(new Constantes(TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL));
      compromisoInformeFinal.setIdUsuarioRol(usuarioRol);
      compromisoInformeFinal.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      compromisoInformeFinal.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      compromisoInformeFinal.setFechaCreacion(new Date());

      iImplementacionProyectoLocal.habilitarImplementacionProyecto(
              implementacionProy,
              compromisoPlanTrabajo,
              compromisoInformeAvance,
              compromisoInformeFinal);

      actualizarListaProyectosImplementados(proyectoSeleccionado.getId());

      unidadResponsable = -1L;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_13_info_implementacion_proyecto_exito"));
    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }

    return "";
  }

  /**
   * ***********************************************************************
   */
  /**
   * *********** SETTERS Y GETTERS
   *
   * @return ***********
   */
  public String getPalabraClave() {
    return palabraClave;
  }

  public void setPalabraClave(String palabraClave) {
    this.palabraClave = palabraClave;
  }

  public Long getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(Long unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public List<SelectItem> getListaItemsUnidadesPoliciales() {
    return listaItemsUnidadesPoliciales;
  }

  public void setListaItemsUnidadesPoliciales(List<SelectItem> listaItemsUnidadesPoliciales) {
    this.listaItemsUnidadesPoliciales = listaItemsUnidadesPoliciales;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public Date getFechaPresentacionEntre() {
    return fechaPresentacionEntre;
  }

  public void setFechaPresentacionEntre(Date fechaPresentacionEntre) {
    this.fechaPresentacionEntre = fechaPresentacionEntre;
  }

  public Date getFechaPresentacionY() {
    return fechaPresentacionY;
  }

  public void setFechaPresentacionY(Date fechaPresentacionY) {
    this.fechaPresentacionY = fechaPresentacionY;
  }

  public List<ProyectoDTO> getListaProyectosEncontrados() {
    return listaProyectosEncontrados;
  }

  public void setListaProyectosEncontrados(List<ProyectoDTO> listaProyectosEncontrados) {
    this.listaProyectosEncontrados = listaProyectosEncontrados;
  }

  public ProyectoDTO getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(ProyectoDTO proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public String getNombreUnidadPolicial() {
    return nombreUnidadPolicial;
  }

  public void setNombreUnidadPolicial(String nombreUnidadPolicial) {
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  public Date getFechaProyecto() {
    return fechaProyecto;
  }

  public void setFechaProyecto(Date fechaProyecto) {
    this.fechaProyecto = fechaProyecto;
  }

  public String getAreaProyecto() {
    return areaProyecto;
  }

  public void setAreaProyecto(String areaProyecto) {
    this.areaProyecto = areaProyecto;
  }

  public String getLineaProyecto() {
    return lineaProyecto;
  }

  public void setLineaProyecto(String lineaProyecto) {
    this.lineaProyecto = lineaProyecto;
  }

  public String getTituloProyecto() {
    return tituloProyecto;
  }

  public void setTituloProyecto(String tituloProyecto) {
    this.tituloProyecto = tituloProyecto;
  }

  public Long getUnidadResponsable() {
    return unidadResponsable;
  }

  public void setUnidadResponsable(Long unidadResponsable) {
    this.unidadResponsable = unidadResponsable;
  }

  public List<ImplementacionesProyectoDTO> getListaImplementacionesProyectoActuales() {
    return listaImplementacionesProyectoActuales;
  }

  public void setListaImplementacionesProyectoActuales(List<ImplementacionesProyectoDTO> listaImplementacionesProyectoActuales) {
    this.listaImplementacionesProyectoActuales = listaImplementacionesProyectoActuales;
  }

}
