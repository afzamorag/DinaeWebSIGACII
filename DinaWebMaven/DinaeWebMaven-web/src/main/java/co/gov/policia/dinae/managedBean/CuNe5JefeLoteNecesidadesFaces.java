package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.IOException;
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
 * Este caso de uso necesita
 *
 * @author juan
 */
@javax.inject.Named(value = "cuNe5JefeLoteNecesidadesFaces")
@javax.enterprise.context.SessionScoped
public class CuNe5JefeLoteNecesidadesFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuNe7PropuestaNecesidadFaces cuNe7PropuestaNecesidadFaces;

  @EJB
  private IConstantesLocal iConstantesLocal;
  @EJB
  private IPropuestaNecesidadLocal propuestaNecesidadLocal;
  @EJB
  private IMailSessionLocal iMailSessionBean;
  @EJB
  private IPropuestaNecesidadLocal iPropuestaNecesidadLocal;
  private List<SelectItem> constantesLineaItem;
  private Periodo periodoSeleccionado;
  private List<PropuestaNecesidad> listaPropuestaNecesidad;
  private ListGenericDataModel<PropuestaNecesidad> listaPropuestaModel;
  private Boolean inactivarBotonVicin = true;
  private PropuestaNecesidad propuestaSeleccionada;

  private static final List<Long> listaIdRolesEnviaCorreo = new ArrayList<Long>(1);

  static {
    listaIdRolesEnviaCorreo.add(IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN);
  }

  /**
   * Método que retorna las propuestas necesidades aceptadas de la unidad para el periodo seleccionado con el fin de cambiarles el estado enviadoAVicin y agr
   *
   * @return
   */
  public List<PropuestaNecesidad> buscarPropuestasAceptadas() {
    try {
      return propuestaNecesidadLocal.getPropuestaNecesidadPorPeriodoPorUnidadPorEstado(this.periodoSeleccionado.getIdPeriodo(), loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(), IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA);
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-05 getContantes", ex);
    }
    return null;

  }

  /**
   * Método que valida que la propuesta ha sido aprobada por el Jefe de Unidad y Se debe enviar a Vicin si esta en estado aprobación
   */
  public void enviarVicin() {

    Boolean estadosValido = aceptadasPropuestas();
    if (listaPropuestaNecesidad.size() > 0 && estadosValido) {
      try {

        List<PropuestaNecesidad> propuestasAceptadas = this.buscarPropuestasAceptadas();
        if (propuestasAceptadas != null) {
          listaPropuestaNecesidad.addAll(propuestasAceptadas);
        }
        //GUARDAMOS LAS PROPUESTAS
        propuestaNecesidadLocal.enviarPropuestaVicin(listaPropuestaNecesidad, loginFaces.getPerfilUsuarioDTO().getIdentificacion());

        //SE ENVÍA CORREO ELECTRÓNICO AL EVALUADOR DE PROPUESTAS DE NECESIDADES EN LA VICIN.
        Map<String, Object> parametrosContenido = new HashMap<String, Object>();
        parametrosContenido.put("{_parametro1_}", loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getNombre());
        parametrosContenido.put("{_parametro2_}", String.valueOf(periodoSeleccionado.getAnio()));

        try {

          iMailSessionBean.enviarMail_ListaRoles(
                  IConstantes.CU_NE_05_ENVIO_PROPUESTA_VICIN,
                  null,
                  parametrosContenido,
                  listaIdRolesEnviaCorreo);

        } catch (Exception e) {

          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-05 mail", e);
        }

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_5_enviado_vicin"));

        navigationFaces.redirectFacesCuNe02();

      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-05", ex);
      } catch (IOException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-05 getContantes", ex);
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
    Boolean aceptadasPropuestas = aceptadasPropuestas();
    Boolean estadoNotNull = estadoNotNull();

    String comentarioPropuesta = validarComentarioNoPropuestas();

    if (listaPropuestaNecesidad.size() > 0 && !aceptadasPropuestas && estadoNotNull) {

      if (comentarioPropuesta.equals("")) {
        try {

          String nombreYgrado = loginFaces.getPerfilUsuarioDTO().getGrado() == null ? "" : loginFaces.getPerfilUsuarioDTO().getGrado().trim().concat(" - ");
          nombreYgrado = nombreYgrado.concat(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());
          propuestaNecesidadLocal.enviarPropuestaGrupoInvestigacion(listaPropuestaNecesidad,
                  loginFaces.getPerfilUsuarioDTO().getIdentificacion(),
                  nombreYgrado);

          adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_5_enviado_ginvestigacion"));

          enviandoCorreoGrupoInvestigacion();

          navigationFaces.redirectFacesCuNe02();

        } catch (JpaDinaeException ex) {

          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-05 getContantes", ex);

        } catch (IOException ex) {

          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-05 getContantes", ex);

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
   * Método que retorna una lista de propuetas aceptadas para un periodo una unidad policial
   *
   * @param periodoSeleccionado
   */
  private List<PropuestaNecesidad> cargarListaPropuestasAceptadas(Periodo periodoSeleccionado) {

    try {
      List<Long> listaIdEstadosJefeUnidadRegistroNecesidad = new ArrayList<Long>(1);
      listaIdEstadosJefeUnidadRegistroNecesidad.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA);

      List<PropuestaNecesidad> propuestasAceptadas = iPropuestaNecesidadLocal.getPropuestaNecesidadPorPeriodoYunidadPolicialyEstados(
              periodoSeleccionado.getIdPeriodo(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              listaIdEstadosJefeUnidadRegistroNecesidad);

      for (PropuestaNecesidad pNecesidad : propuestasAceptadas) {

        pNecesidad.setIdContantes(pNecesidad.getConstantes().getIdConstantes());
      }
      return propuestasAceptadas;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "\"CU-NE-4 Jefe Lote Necesidad -(enviarMailCreacionPeriodo) - (cargarListaPropuestas)", e);
    }
    return null;
  }

  /**
   * Método que fija un periodo para poder mostrar y modificar las propuestas de necesidades asociadaas a e
   *
   * @param periodo
   * @param listaPropuestaNecesidad
   */
  public void fijarPeriodo(Periodo periodo, List<PropuestaNecesidad> listaPropuestaNecesidad) {

    this.periodoSeleccionado = periodo;
    this.listaPropuestaNecesidad = listaPropuestaNecesidad;

    List<PropuestaNecesidad> propuestasAcPropuestaNecesidads = this.cargarListaPropuestasAceptadas(periodo);

    if (propuestasAcPropuestaNecesidads != null) {
      this.listaPropuestaNecesidad.addAll(propuestasAcPropuestaNecesidads);
    }

    listaPropuestaModel = new ListGenericDataModel(listaPropuestaNecesidad);

  }

  /**
   * Método que arma mensaje de mail cuando el Proyecto es publicado
   *
   */
  public void enviandoCorreoGrupoInvestigacion() {
    try {

      iMailSessionBean.enviarMail_RolUnidadPolicial(
              IConstantes.CU_NE_05_PROPUESTAS_NO_ACEPTADAS,
              null,
              null,
              IConstantesRole.AUTORIZADO_PARA_REGISTRO_DE_NECESIDADES_EN_LA_UNIDAD_POLICIAL,
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()
      );

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-05 mail", e);

    }

  }

  public void verDatallePropuestaNecesidad(SelectEvent event) {
    try {
      if (this.propuestaSeleccionada != null) {

        cuNe7PropuestaNecesidadFaces.fijarPropuesta(propuestaSeleccionada, navigationFaces.redirectCuNe05JefeLoteNecesidades(), false, null, null);
        navigationFaces.redirectFacesCuNe07PropuestaNecesidad();

      }

    } catch (IOException ex) {
      adicionaMensajeError("No se ha podido recuperar las propuestas");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-04 Consulta de propuestas de necesidades de investigación (verDatallePropuestaNecesidad) ", ex);
    }
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

  public List<PropuestaNecesidad> getListaPropuestaNecesidad() {
    return listaPropuestaNecesidad;
  }

  public void setListaPropuestaNecesidad(List<PropuestaNecesidad> listaPropuestaNecesidad) {
    this.listaPropuestaNecesidad = listaPropuestaNecesidad;
  }

  public Boolean getInactivarBotonVicin() {
    return inactivarBotonVicin;
  }

  public void handleVerificarActivarBotonVicin() {
    inactivarBotonVicin = !aceptadasPropuestas();
  }

  public ListGenericDataModel<PropuestaNecesidad> getListaPropuestaModel() {
    return listaPropuestaModel;
  }

  public void setListaPropuestaModel(ListGenericDataModel<PropuestaNecesidad> listaPropuestaModel) {
    this.listaPropuestaModel = listaPropuestaModel;
  }

  public List<SelectItem> getConstantesLineaItem() {
    if (constantesLineaItem == null) {
      try {
        String in = IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA + "," + IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA;
        constantesLineaItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipoPorCodigos(IConstantes.TIPO_PROPUESTA_NECESIDAD, in), "idConstantes", "valor");
      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-05 getContantes", ex);
      }

    }
    return constantesLineaItem;
  }

  public void setConstantesLineaItem(List<SelectItem> constantesLineaItem) {

    this.constantesLineaItem = constantesLineaItem;
  }

  public PropuestaNecesidad getPropuestaSeleccionada() {
    return propuestaSeleccionada;
  }

  public void setPropuestaSeleccionada(PropuestaNecesidad propuestaSeleccionada) {
    this.propuestaSeleccionada = propuestaSeleccionada;
  }

  private Boolean aceptadasPropuestas() {
    Boolean estadosValido = true;
    for (PropuestaNecesidad p : listaPropuestaNecesidad) {
      estadosValido &= (p.getIdContantes() != null && p.getIdContantes() == IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA);
    }
    return estadosValido;
  }

  private String validarComentarioNoPropuestas() {

    int fila = 1;
    for (PropuestaNecesidad p : listaPropuestaNecesidad) {
      if (p.getIdContantes() == IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA) {

        if (p.getComentario().trim().length() == 0) {
          return keyPropertiesFactory.value("cu_ne_5_error_comentario_vacio", new String[]{String.valueOf(fila)});
        }
        if (p.getComentario().trim().length() > 512) {
          return keyPropertiesFactory.value("cu_ne_5_error_comentario_mayor", new String[]{String.valueOf(fila)});
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
  public Boolean estadoNotNull() {
    Boolean estadosValido = true;
    for (PropuestaNecesidad p : listaPropuestaNecesidad) {
      estadosValido &= p.getIdContantes() != null;
    }
    return estadosValido;
  }

  public Boolean propuestaNoAceptada(Long idConstante) {
    return idConstante == IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA;
  }

  public void handledFijarComentario(PropuestaNecesidad propuestaNecesidad, String comentario) {
    propuestaNecesidad.setComentario(comentario);
  }

}
