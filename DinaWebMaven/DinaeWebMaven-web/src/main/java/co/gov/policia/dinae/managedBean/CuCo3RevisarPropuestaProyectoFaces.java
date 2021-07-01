package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@javax.inject.Named(value = "cuCo3RevisarPropuestaProyectoFaces")
@javax.enterprise.context.SessionScoped
public class CuCo3RevisarPropuestaProyectoFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  private List<SelectItem> constantesLineaItem;
  private Periodo periodoSeleccionado;
  private List<Proyecto> listaPropuestaProyecto;
  private ListGenericDataModel<Proyecto> listaPropuestaModel;
  private boolean inactivarBotonVicin = true;
  private Proyecto propuestaSeleccionada;

  private static final List<Long> listaIdRolesEnviaCorreoVicin = new ArrayList<Long>(1);

  static {
    listaIdRolesEnviaCorreoVicin.add(IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN);
  }

  private static final List<Long> listaIdEstadosPropuestaConvocatoriaEstadoAceptado = new ArrayList<Long>(2);

  static {

    listaIdEstadosPropuestaConvocatoriaEstadoAceptado.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ACEPTADO_POR_JEFE_DE_UNIDAD);

  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();
    return navigationFaces.redirectCuCo3RevisarPropuestaProyectoRedirect();

  }

  /**
   *
   * @param periodo
   * @param listaPropuestasProyecto
   */
  public void initReturnCU_Desde_CU_CO_2(Periodo periodo, List<Proyecto> listaPropuestasProyecto) {

    init();

    try {

      this.periodoSeleccionado = periodo;
      this.listaPropuestaProyecto = listaPropuestasProyecto;

      List<Proyecto> propuestasProyectoAceptadas = cargarListaPropuestasAceptadas(periodo);

      if (propuestasProyectoAceptadas != null && !propuestasProyectoAceptadas.isEmpty()) {

        listaPropuestaProyecto.addAll(propuestasProyectoAceptadas);

      }

      //CARGAMOS LA LISTA TIPO          
      constantesLineaItem = new ArrayList<SelectItem>();

      constantesLineaItem.add(new SelectItem(
              IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ACEPTADO_POR_JEFE_DE_UNIDAD,
              keyPropertiesFactory.value("cu_co_3_lbl_item_aceptado_valor")));

      constantesLineaItem.add(new SelectItem(
              IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_ACEPTADO_POR_JEFE_DE_UNIDAD,
              keyPropertiesFactory.value("cu_co_3_lbl_item_no_aceptado_valor")));

      listaPropuestaModel = new ListGenericDataModel(listaPropuestasProyecto);

      navigationFaces.redirectFacesCuCo3RevisarPropuestaProyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-03 Revisar Propuesta Propyecto  (init)", e);
    }

  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      constantesLineaItem = null;
      periodoSeleccionado = null;
      listaPropuestaProyecto = null;
      listaPropuestaModel = null;
      inactivarBotonVicin = true;
      propuestaSeleccionada = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-03 Revisar Propuesta Propyecto    (init)", e);
    }

  }

  /**
   *
   * @return
   */
  private List<Proyecto> buscarPropuestasAceptadas() {

    try {

      return iProyectoLocal.getPropuestasConvocatoriaPorConvocatoriaYunidadPolicialYListaIdEstado(
              periodoSeleccionado.getIdPeriodo(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              listaIdEstadosPropuestaConvocatoriaEstadoAceptado);

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-03 Revisar Propuesta Propyecto   (buscarPropuestasAceptadas)", ex);
    }
    return null;

  }

  /**
   * Método que valida que la propuesta ha sido aprobada por el Jefe de Unidad y Se debe enviar a Vicin si esta en estado aprobación
   */
  public void enviarVicin() {

    Boolean estadosValido = validarEstadoAceptadoPropuestaProyecto();

    if (listaPropuestaProyecto.size() > 0 && estadosValido) {
      try {

        List<Proyecto> propuestasAceptadas = buscarPropuestasAceptadas();

        if (propuestasAceptadas != null) {
          listaPropuestaProyecto.addAll(propuestasAceptadas);
        }
        //GUARDAMOS LAS PROPUESTAS
        iProyectoLocal.enviarPropuestaConvocatoriaVicin(listaPropuestaProyecto);

        //SE ENVÍA CORREO ELECTRÓNICO AL EVALUADOR DE PROPUESTAS DE CONVOCATORIAS EN LA VICIN.
        Map<String, Object> parametrosContenido = new HashMap<String, Object>();
        parametrosContenido.put("{_parametro1_}", loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getNombre());
        parametrosContenido.put("{_parametro2_}", String.valueOf(periodoSeleccionado.getConcecutivo()));

        iMailSessionBean.enviarMail_ListaRoles(
                IConstantes.CU_CO_03_JEFE_UNIDAD_ENVIA_PROP_CONVOCATO_VICIN,
                null,
                parametrosContenido,
                listaIdRolesEnviaCorreoVicin);

        navigationFaces.redirectFacesCuCo2ParticipaConvocatorias();

      } catch (Exception ex) {

        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-03 Revisar Propuesta Propyecto   ", ex);

      }
    } else {
      adicionaMensajeError(keyPropertiesFactory.value("cu_ne_5_error_enviado_vicin"));
    }
  }

  /*
     * Método que cambia los estado de las propuestas
     * Por que almenos una no fue aceptada
   */
  public void enviarGrupoInvestigacion() {

    boolean aceptadasPropuestas = validarEstadoAceptadoPropuestaProyecto();
    boolean estadoNotNull = estadoNotNull();

    String comentarioPropuesta = validarComentarioNoPropuestas();

    if (listaPropuestaProyecto.size() > 0 && !aceptadasPropuestas && estadoNotNull) {

      if (comentarioPropuesta.equals("")) {
        try {

          iProyectoLocal.enviarPropuestaConvocatoriaAgrupoInvestigacion(listaPropuestaProyecto,
                  loginFaces.getPerfilUsuarioDTO().getIdentificacion(),
                  loginFaces.getPerfilUsuarioDTO().getGradoYNombreCompleto());

          enviandoCorreoGrupoInvestigacion();

          navigationFaces.redirectFacesCuCo2ParticipaConvocatorias();

        } catch (Exception ex) {

          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-03 Revisar Propuesta Propyecto   ", ex);

        }
      } else {

        adicionaMensajeError(comentarioPropuesta);
      }
    } else if (!estadoNotNull) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_ne_5_error_enviado_ginvestigacion_estados"));

    } else {

      adicionaMensajeError(keyPropertiesFactory.value("cu_ne_5_error_enviado_ginvestigacion"));
    }
  }

  /**
   * Método que retorna una lista de propuetas aceptadas para un periodo en una unidad policial
   *
   * @param periodoSeleccionado
   */
  private List<Proyecto> cargarListaPropuestasAceptadas(Periodo periodoSeleccionado) {

    return new ArrayList<Proyecto>();
  }

  /**
   * Método que arma mensaje de mail cuando el Proyecto es publicado
   *
   */
  public void enviandoCorreoGrupoInvestigacion() {
    try {

      iMailSessionBean.enviarMail_RolUnidadPolicial(
              IConstantes.CU_CO_03_JEFE_UNIDAD_NOACEPTA_PROPUESTA,
              null,
              null,
              IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL,
              this.loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

    } catch (Exception ex) {

      Logger.getLogger(CuNe1PeriodoNecesidadesFaces.class.getName()).log(Level.SEVERE, null, ex);

    }
  }

  /**
   *
   * @param event
   */
  public void verDatallePropuestaNecesidad(SelectEvent event) {
    try {
      if (propuestaSeleccionada != null) {

        String retorno = cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_CO_03(propuestaSeleccionada.getIdProyecto());
        navigationFaces.redirectFacesCuGenerico(retorno);

      }

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-03 Revisar Propuesta Propyecto  (verDatallePropuestaNecesidad) ", ex);
    }
  }

  /**
   *
   * @return
   */
  private boolean validarEstadoAceptadoPropuestaProyecto() {

    boolean estadosValido = true;
    for (Proyecto unProyeco : listaPropuestaProyecto) {

      estadosValido &= (unProyeco.getIdContantes() != null && unProyeco.getIdContantes().equals(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ACEPTADO_POR_JEFE_DE_UNIDAD));

    }
    return estadosValido;
  }

  /**
   *
   * @return
   */
  private String validarComentarioNoPropuestas() {

    int fila = 1;

    for (Proyecto unProyecto : listaPropuestaProyecto) {

      if (unProyecto.getIdContantes().equals(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_APROBADA)) {

        if (unProyecto.getComentario().trim().length() == 0) {
          return keyPropertiesFactory.value("cu_co_3_error_comentario_vacio", new String[]{String.valueOf(fila)});
        }
        if (unProyecto.getComentario().trim().length() > 512) {
          return keyPropertiesFactory.value("cu_co_3_error_comentario_mayor", new String[]{String.valueOf(fila)});
        }
      }
      fila++;
    }
    return "";
  }

  /**
   * Se valida que las propuesta tenga un estado no null
   *
   * @return
   */
  public boolean estadoNotNull() {

    boolean estadosValido = true;
    for (Proyecto proyecto : listaPropuestaProyecto) {

      estadosValido &= proyecto.getEstado().getIdConstantes() != null;

    }

    return estadosValido;
  }

  public boolean propuestaNoAceptada(Long idConstante) {
    return idConstante.equals(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_ACEPTADO_POR_JEFE_DE_UNIDAD);
  }

  public void handledFijarComentario(Proyecto proyecto, String comentario) {
    proyecto.setComentario(comentario);
  }

  public void setConstantesLineaItem(List<SelectItem> constantesLineaItem) {

    this.constantesLineaItem = constantesLineaItem;
  }

  public Proyecto getPropuestaSeleccionada() {
    return propuestaSeleccionada;
  }

  public void setPropuestaSeleccionada(Proyecto propuestaSeleccionada) {
    this.propuestaSeleccionada = propuestaSeleccionada;
  }

  public void noSeleccionPropuesta(UnselectEvent event) {
    propuestaSeleccionada = null;
  }

  public Periodo getPeriodoSeleccionado() {
    return periodoSeleccionado;
  }

  public void setPeriodoSeleccionado(Periodo periodoSeleccionado) {
    this.periodoSeleccionado = periodoSeleccionado;
  }

  public LoginFaces getLoginFaces() {
    return loginFaces;
  }

  public void setLoginFaces(LoginFaces loginFaces) {
    this.loginFaces = loginFaces;
  }

  public boolean getInactivarBotonVicin() {
    return inactivarBotonVicin;
  }

  public void handleVerificarActivarBotonVicin() {
    inactivarBotonVicin = !validarEstadoAceptadoPropuestaProyecto();
  }

  public ListGenericDataModel<Proyecto> getListaPropuestaModel() {
    return listaPropuestaModel;
  }

  public void setListaPropuestaModel(ListGenericDataModel<Proyecto> listaPropuestaModel) {
    this.listaPropuestaModel = listaPropuestaModel;
  }

  public List<SelectItem> getConstantesLineaItem() {

    return constantesLineaItem;
  }

  public List<Proyecto> getListaPropuestaProyecto() {
    return listaPropuestaProyecto;
  }

  public void setListaPropuestaProyecto(List<Proyecto> listaPropuestaProyecto) {
    this.listaPropuestaProyecto = listaPropuestaProyecto;
  }

}
