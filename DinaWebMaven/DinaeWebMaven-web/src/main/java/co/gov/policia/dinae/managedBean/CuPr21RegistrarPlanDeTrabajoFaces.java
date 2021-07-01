package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IGradosValoresLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresPlanTrabajoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceImplementacionLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ISemilleroInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajo;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.ResponsableActividaImplementacion;
import co.gov.policia.dinae.modelo.SemilleroInvestigacion;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr21RegistrarPlanDeTrabajo")
@javax.enterprise.context.SessionScoped
public class CuPr21RegistrarPlanDeTrabajoFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr20GestionImplementacionesVigentesAsignadas cuPr20GestionImplementacionesVigentesAsignadas;

  @javax.inject.Inject
  private CuPr05RegistrarPresupuestoFaces cuPr05RegistrarPresupuestoFaces;

  @EJB
  private IPlanTrabajoImplementacionLocal iPlanTrabajoImplementacionLocal;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionario;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private ISemilleroInvestigacionLocal iSemilleroInvestigacionLocal;

  @EJB
  private IInformeAvanceImplementacionLocal iInformeAvanceImplementacionLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IIndicadoresPlanTrabajoLocal iIndicadoresPlanTrabajoLocal;

  @EJB
  private ICompromisoImplementacionLocal iCompromisoImplementacionLocal;

  @EJB
  private IGradosValoresLocal iGradosValoresLocal;

  private Proyecto proyectoSeleccionado;
  private PlanTrabajoImplementacion planTrabajoImplementacion;
  private int idTabSeleccionado;
  private Long idImplementacionProyecto;
  private Long idCompromisoImplementacion;

  // PARA LA PESTAÑA PERSONAL RESPONSABLE DE LA IMPLEMENTACIÓN 
  private List<InvestigadorProyecto> listaInvestigadorProyecto;
  private Long semilleroInvestigacion;
  private List<SelectItem> listaItemsSemilleros;
  private List<SemillerosImplementacion> listaSemillerosVinculados;
  private boolean mostrarDatosPersonalImpl;
  private InvestigadorProyecto investigadorProyectoSeleccionado;
  private SemillerosImplementacion semilleroImplementacion;
  private InvestigadorProyecto investigadorProyectoEliminar;
  private SemillerosImplementacion semillerosImplementacionEliminar;

  // PARA LA PESTAÑA ACTIVIDADES A REALIZAR 
  private List<SelectItem> listaItemsResponsables;
  private List<ActividadesPlanImplementacion> listaActividadesPlanImplementacion;
  private ActividadesPlanImplementacion actividadesPlanImplementacion;
  private InformeAvanceImplementacion informeAvanceImplementacion;
  private RolUsuarioDTO rolUsuarioDTO;
  private ActividadesPlanImplementacion actividadesPlanImplementacionEliminar;

  //INDICADORES
  private List<IndicadoresPlanTrabajo> listaIndicadoresPlanTrabajoOtros;
  private IndicadoresPlanTrabajo indicadoresPlanTrabajoSeleccionado;
  private IndicadoresPlanTrabajo indicadoresPlanTrabajoEliminar;

  private Long responsableActividad;

  private String anioInvestigacion;

  private final SimpleDateFormat sdfSoloAnio = new SimpleDateFormat("yyyy");

  private enum ORIGEN_LLAMADO_CU {

    CU_PR_25, CU_PR_20, CU_PR_24, CU_PR_06
  };

  private CuPr21RegistrarPlanDeTrabajoFaces.ORIGEN_LLAMADO_CU registraLlamdoCU;

  private UnidadPolicial unidadPolicialPersonaBusqueda;
  private Character origenSiafOinvestigadorPersonaBusqueda;
  private BigDecimal valorHoraPersonaImpl;
  private String gradoPersonaBusqueda;
  private String nombresYApellidosPersonaBusqueda;
  private String correoElectronicoPersonaBusqueda;
  private String numeroIdentificacionPersonaBusqueda;
  private boolean accesoSoloConsulta = false;
  private BigDecimal horasTotalesImplementacionPersonaBusqueda;

  @PostConstruct
  public void init() {

    try {

      indicadoresPlanTrabajoEliminar = null;
      listaIndicadoresPlanTrabajoOtros = null;

      unidadPolicialPersonaBusqueda = null;
      origenSiafOinvestigadorPersonaBusqueda = null;
      gradoPersonaBusqueda = null;
      valorHoraPersonaImpl = null;
      nombresYApellidosPersonaBusqueda = null;
      correoElectronicoPersonaBusqueda = null;
      numeroIdentificacionPersonaBusqueda = null;
      horasTotalesImplementacionPersonaBusqueda = null;

      rolUsuarioDTO = null;
      informeAvanceImplementacion = null;
      responsableActividad = null;
      proyectoSeleccionado = null;
      planTrabajoImplementacion = null;
      idTabSeleccionado = 0;
      idImplementacionProyecto = null;
      idCompromisoImplementacion = null;

      listaInvestigadorProyecto = null;
      semilleroInvestigacion = null;
      listaItemsSemilleros = null;
      listaSemillerosVinculados = null;
      mostrarDatosPersonalImpl = false;

      investigadorProyectoSeleccionado = new InvestigadorProyecto();
      indicadoresPlanTrabajoSeleccionado = new IndicadoresPlanTrabajo();

      listaItemsResponsables = null;
      listaActividadesPlanImplementacion = null;

      actividadesPlanImplementacion = new ActividadesPlanImplementacion();
      actividadesPlanImplementacion.setResponsableActividaImplementacionList(new ArrayList<ResponsableActividaImplementacion>());

      semilleroImplementacion = new SemillerosImplementacion();

      anioInvestigacion = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);

    }
  }

  String retorno = null;

  /**
   *
   * @param idImplementacionProyecto
   * @param idCompromisoImplementacion
   * @param accesoSoloConsulta
   * @return
   * @throws Exception
   */
  public String initReturnCU_DESDE_PR_20(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    init();

    retorno = initReturnCU_DESDE_CU_PR_25(idImplementacionProyecto, idCompromisoImplementacion, accesoSoloConsulta);

    this.accesoSoloConsulta = accesoSoloConsulta;
    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_20;

    return retorno;

  }

  /**
   *
   * @param idImplementacionProyecto
   * @param idCompromisoImplementacion
   * @param accesoSoloConsulta
   * @return
   * @throws Exception
   */
  public String initReturnCU_DESDE_CU_PR_24(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    String retorno = initReturnCU_DESDE_CU_PR_25(idImplementacionProyecto, idCompromisoImplementacion, accesoSoloConsulta);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_24;

    return retorno;

  }

  /**
   *
   * @param idImplementacionProyecto
   * @param idCompromisoImplementacion
   * @param accesoSoloConsulta
   * @return
   * @throws Exception
   */
  public String initReturnCU_DESDE_CU_PR_06(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    String retorno = initReturnCU_DESDE_CU_PR_25(idImplementacionProyecto, idCompromisoImplementacion, accesoSoloConsulta);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_06;

    return retorno;

  }

  /**
   *
   * @param idImplementacionProyecto
   * @param idCompromisoImplementacion
   * @param accesoSoloConsulta
   * @return
   * @throws java.lang.Exception
   */
  public String initReturnCU_DESDE_CU_PR_25(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    init();

    idTabSeleccionado = 0;

    this.accesoSoloConsulta = accesoSoloConsulta;

    this.idImplementacionProyecto = idImplementacionProyecto;

    this.idCompromisoImplementacion = idCompromisoImplementacion;

    proyectoSeleccionado = iProyectoLocal.getProyectoPorImplementacionProyecto(idImplementacionProyecto);

    if (proyectoSeleccionado != null && proyectoSeleccionado.getFechaEstimadaInicio() != null) {

      anioInvestigacion = sdfSoloAnio.format(proyectoSeleccionado.getFechaEstimadaInicio());

    }

    rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_LA_IMPLEMENTACION_EN_LA_UNIDAD_POLICIAL);

    informeAvanceImplementacion = iInformeAvanceImplementacionLocal.findInformeAvanceImplementacionFinaByIdImplemenatcionProYIdCompromisoProImpl(
            idImplementacionProyecto, idCompromisoImplementacion);

    if (informeAvanceImplementacion == null) {
      //LA PRIMERA VEZ NO EXISTE EL COMPROMISO PRO IMPL, ESTE DEBE SER CREADO
      informeAvanceImplementacion = new InformeAvanceImplementacion();
      informeAvanceImplementacion.setFechaInicio(new Date());
      informeAvanceImplementacion.setCompromisoImplementacion(new CompromisoImplementacion(idCompromisoImplementacion));
      informeAvanceImplementacion.setImplementacionesProyecto(new ImplementacionesProyecto(idImplementacionProyecto));
      informeAvanceImplementacion.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      informeAvanceImplementacion.setUsuarioCreacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      informeAvanceImplementacion.setUsuarioRol(new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol()));

      //GUARDAMOS LOS DATOS BASICOS
      informeAvanceImplementacion = iInformeAvanceImplementacionLocal.saveOrUpdate(informeAvanceImplementacion);

    }

    cargarPlanDeTrabajoDeLaImplementacion();

    cargarListaInvestigadorProyecto();

    cargarListaItemsSemilleros();

    cargarListaActividadesPlanImplementacion();

    //CARGA LISTA DE INDICADORES
    cargarListaIndicadoresProyectoOtros();

    listaSemillerosVinculados = cargarListaSemillerosImplementacion();

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_25;

    return navigationFaces.redirectCuPr21RegistrarPlanDeTrabajoRedirect();

  }

  /**
   *
   * @param idEstadoCompromiso
   * @throws Exception
   */
  private void actualizarEstadoCompromisoImplementacion(Long idEstadoCompromiso) throws Exception {

    CompromisoImplementacion compromisoImplementacion = iCompromisoImplementacionLocal.obtenerCompromisoImplementacionPorId(idCompromisoImplementacion);
    compromisoImplementacion.setIdEstadoCompromisoImpl(new Constantes(idEstadoCompromiso));
    compromisoImplementacion.setResultadoRetroalimentacion(null);
    compromisoImplementacion.setResultadoRevisionVicin(null);
    compromisoImplementacion.setComentarioTemporal(null);

    iCompromisoImplementacionLocal.saveCompromisoImplementacion(compromisoImplementacion);

  }

  /**
   *
   * @return
   */
  public String guardarIndicadoresPlanTrabajo() {

    if (indicadoresPlanTrabajoSeleccionado == null) {
      return null;
    }

    try {

      indicadoresPlanTrabajoSeleccionado.setPlanTrabajoImplementacion(planTrabajoImplementacion);
      indicadoresPlanTrabajoSeleccionado.setFechaRegistro(new Date());

      iIndicadoresPlanTrabajoLocal.guardarIndicadoresPlanTrabajo(indicadoresPlanTrabajoSeleccionado);

      cargarListaIndicadoresProyectoOtros();

      indicadoresPlanTrabajoSeleccionado = new IndicadoresPlanTrabajo();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_add_nuevo_indicador_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (guardarIndicadoresProyecto) ", e);

    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoOtros() throws Exception {

    if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

      listaIndicadoresPlanTrabajoOtros = new ArrayList<IndicadoresPlanTrabajo>();
      return;
    }

    listaIndicadoresPlanTrabajoOtros = iIndicadoresPlanTrabajoLocal.getListaIndicadoresPlanTrabajoPorPlanTrabajoImplementacion(
            planTrabajoImplementacion.getIdPlanTrabajo()
    );

  }

  /**
   *
   */
  public void eliminarIndicadorOtro() {

    try {

      if (indicadoresPlanTrabajoEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iIndicadoresPlanTrabajoLocal.eliminarIndicadoresPlanTrabajo(indicadoresPlanTrabajoEliminar.getIdIndicadorPlanTrabajo());

      //ACTUALIZAMOS LA LISTA
      cargarListaIndicadoresProyectoOtros();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_eliminar_indicador_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-21 ", e);

    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaActividadesPlanImplementacion() throws Exception {

    if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

      listaActividadesPlanImplementacion = new ArrayList<ActividadesPlanImplementacion>();
      return;

    }

    listaActividadesPlanImplementacion = iPlanTrabajoImplementacionLocal.getListaActividadesPlanImplementacionPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());

  }

  /**
   *
   */
  private void cargarListaInvestigadorProyecto() {
    try {

      if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

        listaInvestigadorProyecto = new ArrayList<InvestigadorProyecto>();
        return;

      }
      listaInvestigadorProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());

      listaItemsResponsables = UtilidadesItem.getListaSel(
              listaInvestigadorProyecto,
              "idInvestigadorProyecto",
              "nombreCompleto");

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaItemsSemilleros() {

    try {

      listaItemsSemilleros = UtilidadesItem.getListaSel(
              iSemilleroInvestigacionLocal.getListaSemilleroInvestigacionTodos(),
              "idSemillero",
              "nombre");

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return @throws Exception
   */
  private List<SemillerosImplementacion> cargarListaSemillerosImplementacion() throws Exception {

    return iPlanTrabajoImplementacionLocal.getSemillerosImplementacionByIdImplementacion(idImplementacionProyecto);

  }

  /**
   *
   */
  private void cargarPlanDeTrabajoDeLaImplementacion() throws Exception {

    planTrabajoImplementacion = iPlanTrabajoImplementacionLocal.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
            idImplementacionProyecto,
            idCompromisoImplementacion);

    //LA PRIMERA VEZ QUE INGRESA EL PLAN DE TRABAJO NO EXISTE
    if (planTrabajoImplementacion == null) {

      planTrabajoImplementacion = new PlanTrabajoImplementacion();
      planTrabajoImplementacion.setFechaRegistro(new Date());
      planTrabajoImplementacion.setImplementacionesProyecto(new ImplementacionesProyecto(idImplementacionProyecto));
      planTrabajoImplementacion.setFechaRegistro(new Date());
      planTrabajoImplementacion.setUsuarioRol(new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol()));
      planTrabajoImplementacion.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      planTrabajoImplementacion.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      planTrabajoImplementacion.setCompromisoImplementacion(new CompromisoImplementacion(idCompromisoImplementacion));

      planTrabajoImplementacion = iPlanTrabajoImplementacionLocal.registrarPlanDeTrabajo(planTrabajoImplementacion);
    }

  }

  /**
   *
   * @return
   */
  public String redirectRegistrarPresupuesto() {
    try {
      cuPr05RegistrarPresupuestoFaces.initProyectoCU21(this.idImplementacionProyecto, this.idCompromisoImplementacion, !ORIGEN_LLAMADO_CU.CU_PR_20.equals(registraLlamdoCU));
      return "/pages/secured/cu_pr_05/registrarPresupuestoImpl.xhtml?faces-redirect=true";
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String registrarPlanDeTrabajo() {

    try {

      planTrabajoImplementacion = iPlanTrabajoImplementacionLocal.registrarPlanDeTrabajo(planTrabajoImplementacion);

      actualizarEstadoCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_21_info_agregar_plan_trabajo_exitoso"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, e);

    }

    return null;
  }

 
  public void enviarPlanDeTrabajo() {

    try {

      //CONSULTAMOS EL PLAN TRABAJO IMPL ALMACENADO
      PlanTrabajoImplementacion planTrabajoImplementacionActual = iPlanTrabajoImplementacionLocal.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
              idImplementacionProyecto,
              idCompromisoImplementacion);
      //REALIZMOS LAS VALIDACIONES DE LAS PESTAÑAS
      if (planTrabajoImplementacionActual.getFechaAprobacionComite() == null) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_fecha_aprobacion"));
        return;

      }

      if (planTrabajoImplementacionActual.getNroActaAprobacion() == null) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_numero_acta"));
        return;

      }

      if (planTrabajoImplementacionActual.getObjetivoGralImplementacion() == null) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_objetivo_general_implementacion"));
        return;

      }

      if (planTrabajoImplementacionActual.getObjetivosEspecificos() == null) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_objetivo_especifico_implementacion"));
        return;

      }

      if (listaInvestigadorProyecto == null || listaInvestigadorProyecto.isEmpty()) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_titulo_tab_personal_implementacion"));
        return;
      }

      if (listaSemillerosVinculados == null || listaSemillerosVinculados.isEmpty()) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_semilleros_investiigacion"));
        return;
      }

      if (listaActividadesPlanImplementacion == null || listaActividadesPlanImplementacion.isEmpty()) {

        adicionaMensajeError("Información incompleta en: " + keyPropertiesFactory.value("cu_pr_21_lbl_titulo_tab_actividades_realizar"));
        return;
      }

      actualizarEstadoCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);

      navigationFaces.redirectFacesCuGenerico(cuPr20GestionImplementacionesVigentesAsignadas.initReturnCU());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, e);

    }

  }

  /**
   *
   * @param event
   */
  public void agregarPersonalDeImplementacion(ActionEvent event) {

    try {

      investigadorProyectoSeleccionado.setFechaRegistro(new Date());
      investigadorProyectoSeleccionado.setPlanTrabajoImplementacion(planTrabajoImplementacion);
      investigadorProyectoSeleccionado.setUnidadPolicial(unidadPolicialPersonaBusqueda);
      investigadorProyectoSeleccionado.setCorreo(correoElectronicoPersonaBusqueda);
      investigadorProyectoSeleccionado.setNombreCompleto(nombresYApellidosPersonaBusqueda);
      investigadorProyectoSeleccionado.setGrado(gradoPersonaBusqueda);
      investigadorProyectoSeleccionado.setIdentificacion(numeroIdentificacionPersonaBusqueda);
      investigadorProyectoSeleccionado.setOrigenSiafOinvestigador(origenSiafOinvestigadorPersonaBusqueda);
      investigadorProyectoSeleccionado.setHorasTotalesImplementacion(horasTotalesImplementacionPersonaBusqueda);
      investigadorProyectoSeleccionado.setValorHoraInvestigadorImplementacion(valorHoraPersonaImpl);

      //ACTUALIZAMOS EL VALOR EN ESPECIA O EN EFECTIVO
      investigadorProyectoSeleccionado.setValorEspecie(BigDecimal.ZERO);
      investigadorProyectoSeleccionado.setValorEfectivo(BigDecimal.ZERO);

      BigDecimal total = investigadorProyectoSeleccionado.getHorasTotalesImplementacion().multiply(investigadorProyectoSeleccionado.getValorHoraInvestigadorImplementacion());

      if (investigadorProyectoSeleccionado.getOrigenSiafOinvestigador().equals(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_SIAFT)) {

        investigadorProyectoSeleccionado.setValorEspecie(total);

      } else if (investigadorProyectoSeleccionado.getOrigenSiafOinvestigador().equals(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_INVESTIGADOR)) {

        investigadorProyectoSeleccionado.setValorEfectivo(total);

      }

      iInvestigadorProyectoLocal.guardarInvestigadorProyecto(investigadorProyectoSeleccionado);

      actualizarEstadoCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      cargarListaInvestigadorProyecto();

      investigadorProyectoSeleccionado = new InvestigadorProyecto();

      unidadPolicialPersonaBusqueda = null;
      origenSiafOinvestigadorPersonaBusqueda = null;
      gradoPersonaBusqueda = null;
      valorHoraPersonaImpl = null;
      nombresYApellidosPersonaBusqueda = null;
      correoElectronicoPersonaBusqueda = null;
      numeroIdentificacionPersonaBusqueda = null;
      horasTotalesImplementacionPersonaBusqueda = null;
      mostrarDatosPersonalImpl = false;

    } catch (Exception ex) {

      adicionaMensajeError("Ocurrió un error al guardar la persona de la implementación");
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, ex);

    }

  }

  /**
   *
   * @param event
   */
  public void vincularSemillero(ActionEvent event) {

    try {

      if (listaSemillerosVinculados != null) {

        //VERIFICAMOS QUE EL SEMILLENO NO SE ENCUENTRE REGISTRADO
        for (SemillerosImplementacion unSemillerosImplementacion : listaSemillerosVinculados) {

          if (unSemillerosImplementacion.getSemilleroInvestigacion().getIdSemillero().equals(semilleroInvestigacion)) {

            adicionaMensajeError("Este semillero ya se encuentra registrado");
            return;
          }

        }

      }

      semilleroImplementacion.setFechaRegistro(new Date());
      semilleroImplementacion.setImplementacionesProyecto(new ImplementacionesProyecto(idImplementacionProyecto));
      semilleroImplementacion.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      semilleroImplementacion.setSemilleroInvestigacion(new SemilleroInvestigacion(semilleroInvestigacion));
      semilleroImplementacion.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      semilleroImplementacion.setUsuarioRol(new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol()));

      iPlanTrabajoImplementacionLocal.vincularSemilleroInvestigacion(semilleroImplementacion);

      actualizarEstadoCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      semilleroImplementacion = new SemillerosImplementacion();

      listaSemillerosVinculados = cargarListaSemillerosImplementacion();

      semilleroInvestigacion = null;

      adicionaMensajeInformativo("Semillero vinculado correctamente.");

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public String asignarResponsableAActividad() {

    if (responsableActividad == null) {
      return null;
    }

    try {

      //VERIFICAMOS QUE RESPONSABLE NO EXISTA
      for (ResponsableActividaImplementacion unResponsableActividaImplementacion : actividadesPlanImplementacion.getResponsableActividaImplementacionList()) {

        if (unResponsableActividaImplementacion.getInvestigadorProyecto().getIdInvestigadorProyecto().equals(responsableActividad)) {

          adicionaMensajeError("Este responsable ya se encuentra registrado");
          return null;

        }
      }

      ResponsableActividaImplementacion responsableActividaImplementacion = new ResponsableActividaImplementacion();

      responsableActividaImplementacion.setInvestigadorProyecto(iInvestigadorProyectoLocal.obtenerInvestigadorProyectoPorId(responsableActividad));

      responsableActividaImplementacion.setActividadesPlanImplementacion(actividadesPlanImplementacion);

      actividadesPlanImplementacion.getResponsableActividaImplementacionList().add(responsableActividaImplementacion);

      responsableActividad = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, e);

    }

    return null;
  }

  /**
   *
   * @return
   */
  public String agregarActividadAPlan() {

    try {

      actividadesPlanImplementacion.setFechaRegistro(new Date());
      actividadesPlanImplementacion.setUsuarioCreacion(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());
      actividadesPlanImplementacion.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      actividadesPlanImplementacion.setPlanTrabajoImplementacion(planTrabajoImplementacion);
      iPlanTrabajoImplementacionLocal.guardarActividadesPlanImplementacion(actividadesPlanImplementacion);

      actualizarEstadoCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      responsableActividad = null;
      actividadesPlanImplementacion = new ActividadesPlanImplementacion();
      actividadesPlanImplementacion.setResponsableActividaImplementacionList(new ArrayList<ResponsableActividaImplementacion>());

      cargarListaActividadesPlanImplementacion();

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   */
  public void buscarPersonalDeImplementacion() {

    mostrarDatosPersonalImpl = false;
    VistaFuncionario funcionario;
    horasTotalesImplementacionPersonaBusqueda = null;

    unidadPolicialPersonaBusqueda = null;
    origenSiafOinvestigadorPersonaBusqueda = null;
    gradoPersonaBusqueda = null;
    valorHoraPersonaImpl = null;
    nombresYApellidosPersonaBusqueda = null;
    correoElectronicoPersonaBusqueda = null;

    try {

      //VERIFICAMOS QUE EL FUNCIONARIO A AGREGAR NO EXISTA EN LA LISTA
      if (listaInvestigadorProyecto != null) {
        for (InvestigadorProyecto unInvestigadorProyecto : listaInvestigadorProyecto) {

          if (unInvestigadorProyecto.getIdentificacion().equals(numeroIdentificacionPersonaBusqueda)) {
            adicionaMensajeError("La identificación ingresada ya se encuentra registrada como responsable.");
            return;
          }
        }

      }

      //VERIFICAMOS SI LA IDENTIFICACION EXISTEN EN FUNCIONARIO - VICIN DINAE - SIAT
      funcionario = iVistaFuncionario.getVistaFuncionarioPorIdentificacion(numeroIdentificacionPersonaBusqueda);

      if (funcionario != null) {

        //CONSULTAMOS EL VALOR HORA DE LA PERSONA
        //SE COMENTA ESTO, DEBIDO A QUE LA POLICIA NO TIENE PARAMETRIZADO LOD GRADDO VALORES TODAVIA
        /*                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime( new Date() );
                Integer anio = calendar.get( Calendar.YEAR );
                    
                
                GradosValores gradosValores = iGradosValoresLocal.getGradosValoresPorAnioYgrado(
                        anio,
                        funcionario.getGradoAlfabetico().trim() );
                    
                if( gradosValores == null ){

                    adicionaMensajeError( 
                            keyPropertiesFactory.value(
                                    "cu_pr_1_lbl_mensaje_error_no_existen_grado_alfabetico_anio", 
                                        new Object[]{ String.valueOf( anio ), funcionario.getGradoAlfabetico() } ) );
                    return;

                }*/
        //VERIFICAMOS SI EL FUNCIONARIO TIIENE UNIDAD POLICIAL
        if (funcionario.getCodigoUnidadLaboral() != null && funcionario.getCodigoUnidadLaboral().trim().length() > 0) {

          Long idUnidadPolicial = Long.valueOf(funcionario.getCodigoUnidadLaboral().trim());
          unidadPolicialPersonaBusqueda = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(idUnidadPolicial);

        }

        //EXISTE
        mostrarDatosPersonalImpl = true;
        gradoPersonaBusqueda = funcionario.getGrado();
        nombresYApellidosPersonaBusqueda = funcionario.getNombreCompleto();
        correoElectronicoPersonaBusqueda = funcionario.getCorreo();
        valorHoraPersonaImpl = null;

        origenSiafOinvestigadorPersonaBusqueda = 'S';

      } /*else {
                //SE DEJA EN COMENTARIO EL PROCESO ELSE
                //YA QUE EL VALOR DE LA HORA NO APLICA PARA LOS INVESTIGADORES
                //ESTO PUEDE SER TEMPORAR, HASTA QUE SE DEFINA COMO SE CALCULA EL VALOR DE LA HORA
                //PARA LOS INVESTIGADORES
                
                
                //NO EXISTEN 
                //VERIFICAMOS SI EXISTE EN LA LISTA DE INVESTIGADORES
                InvestigadorProyecto investigadorProyecto = iInvestigadorProyectoLocal.getInvestigadorProyectoByIdentificacion( numeroIdentificacionPersonaBusqueda );
                
                if (investigadorProyecto != null) {
                    
                    //EXISTE
                    mostrarDatosPersonalImpl = true;
                    
                    gradoPersonaBusqueda = investigadorProyecto.getGrado();
                    nombresYApellidosPersonaBusqueda = investigadorProyecto.getNombreCompleto();
                    correoElectronicoPersonaBusqueda = investigadorProyecto.getCorreo();
                    unidadPolicialPersonaBusqueda = investigadorProyecto.getUnidadPolicial();
                    
                    origenSiafOinvestigadorPersonaBusqueda = 'I';
                    
                } else {
                    
                    //NO EXISTE DIFINITIVAMENTE
                    unidadPolicialPersonaBusqueda = null;
                    origenSiafOinvestigadorPersonaBusqueda = null;
                    gradoPersonaBusqueda = null;
                    nombresYApellidosPersonaBusqueda = null;
                    correoElectronicoPersonaBusqueda = null;
                    numeroIdentificacionPersonaBusqueda = null;
                    
                    adicionaMensajeError( keyPropertiesFactory.value("cu_pr_21_info_persona_no_encontrada"));
                }
            }*/ else {
        //NO EXISTE DIFINITIVAMENTE
        unidadPolicialPersonaBusqueda = null;
        origenSiafOinvestigadorPersonaBusqueda = null;
        gradoPersonaBusqueda = null;
        valorHoraPersonaImpl = null;
        nombresYApellidosPersonaBusqueda = null;
        correoElectronicoPersonaBusqueda = null;
        numeroIdentificacionPersonaBusqueda = null;

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_21_info_persona_no_encontrada"));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void seleccionarResponsable() {

  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;
    if (event == null || event.getTab() == null) {
      return;
    }
    if ("tabDatosGenerales".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("tabPersonalImplementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    } else if ("tabActividadesARealizar".equals(event.getTab().getId())) {
      idTabSeleccionado = 2;
    } else if ("tabIndicadoresDeImpacto".equals(event.getTab().getId())) {
      idTabSeleccionado = 3;
    }
  }

  public Proyecto getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public String getAnioInvestigacion() {
    return anioInvestigacion;
  }

  public void setAnioInvestigacion(String anioInvestigacion) {
    this.anioInvestigacion = anioInvestigacion;
  }

  public List<InvestigadorProyecto> getListaInvestigadorProyecto() {
    return listaInvestigadorProyecto;
  }

  public void setListaInvestigadorProyecto(List<InvestigadorProyecto> listaInvestigadorProyecto) {
    this.listaInvestigadorProyecto = listaInvestigadorProyecto;
  }

  public Long getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(Long semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public List<SelectItem> getListaItemsSemilleros() {
    return listaItemsSemilleros;
  }

  public void setListaItemsSemilleros(List<SelectItem> listaItemsSemilleros) {
    this.listaItemsSemilleros = listaItemsSemilleros;
  }

  public List<SemillerosImplementacion> getListaSemillerosVinculados() {
    return listaSemillerosVinculados;
  }

  public void setListaSemillerosVinculados(List<SemillerosImplementacion> listaSemillerosVinculados) {
    this.listaSemillerosVinculados = listaSemillerosVinculados;
  }

  public Long getResponsableActividad() {
    return responsableActividad;
  }

  public void setResponsableActividad(Long responsableActividad) {
    this.responsableActividad = responsableActividad;
  }

  public List<SelectItem> getListaItemsResponsables() {
    return listaItemsResponsables;
  }

  public void setListaItemsResponsables(List<SelectItem> listaItemsResponsables) {
    this.listaItemsResponsables = listaItemsResponsables;
  }

  public List<ActividadesPlanImplementacion> getListaActividadesPlanImplementacion() {
    return listaActividadesPlanImplementacion;
  }

  public void setListaActividadesPlanImplementacion(List<ActividadesPlanImplementacion> listaActividadesPlanImplementacion) {
    this.listaActividadesPlanImplementacion = listaActividadesPlanImplementacion;
  }

  public boolean isMostrarDatosPersonalImpl() {
    return mostrarDatosPersonalImpl;
  }

  public void setMostrarDatosPersonalImpl(boolean mostrarDatosPersonalImpl) {
    this.mostrarDatosPersonalImpl = mostrarDatosPersonalImpl;
  }

  public boolean isAccesoSoloConsulta() {
    return accesoSoloConsulta;
  }

  public String regresar() {

    if (registraLlamdoCU == null) {
      return null;
    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_25)) {

      return navigationFaces.redirectCuPr25RevisarCompromisoJefeUnidad();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_20)) {

      return cuPr20GestionImplementacionesVigentesAsignadas.initReturnCU();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_24)) {

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_06)) {

      return navigationFaces.redirectCuPr06ConsultarDetalleProyectoInvestigacion();

    }

    return null;
  }

  public PlanTrabajoImplementacion getPlanTrabajoImplementacion() {
    return planTrabajoImplementacion;
  }

  public void setPlanTrabajoImplementacion(PlanTrabajoImplementacion planTrabajoImplementacion) {
    this.planTrabajoImplementacion = planTrabajoImplementacion;
  }

  public ActividadesPlanImplementacion getActividadesPlanImplementacion() {
    return actividadesPlanImplementacion;
  }

  public void setActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) {
    this.actividadesPlanImplementacion = actividadesPlanImplementacion;
  }

  public SemillerosImplementacion getSemilleroImplementacion() {
    return semilleroImplementacion;
  }

  public void setSemilleroImplementacion(SemillerosImplementacion semilleroImplementacion) {
    this.semilleroImplementacion = semilleroImplementacion;
  }

  public UnidadPolicial getUnidadPolicialPersonaBusqueda() {
    return unidadPolicialPersonaBusqueda;
  }

  public void setUnidadPolicialPersonaBusqueda(UnidadPolicial unidadPolicialPersonaBusqueda) {
    this.unidadPolicialPersonaBusqueda = unidadPolicialPersonaBusqueda;
  }

  public String getGradoPersonaBusqueda() {
    return gradoPersonaBusqueda;
  }

  public void setGradoPersonaBusqueda(String gradoPersonaBusqueda) {
    this.gradoPersonaBusqueda = gradoPersonaBusqueda;
  }

  public String getNombresYApellidosPersonaBusqueda() {
    return nombresYApellidosPersonaBusqueda;
  }

  public void setNombresYApellidosPersonaBusqueda(String nombresYApellidosPersonaBusqueda) {
    this.nombresYApellidosPersonaBusqueda = nombresYApellidosPersonaBusqueda;
  }

  public BigDecimal getValorHoraPersonaImpl() {
    return valorHoraPersonaImpl;
  }

  public void setValorHoraPersonaImpl(BigDecimal valorHoraPersonaImpl) {
    this.valorHoraPersonaImpl = valorHoraPersonaImpl;
  }

  public String getCorreoElectronicoPersonaBusqueda() {
    return correoElectronicoPersonaBusqueda;
  }

  public void setCorreoElectronicoPersonaBusqueda(String correoElectronicoPersonaBusqueda) {
    this.correoElectronicoPersonaBusqueda = correoElectronicoPersonaBusqueda;
  }

  public String getNumeroIdentificacionPersonaBusqueda() {
    return numeroIdentificacionPersonaBusqueda;
  }

  public void setNumeroIdentificacionPersonaBusqueda(String numeroIdentificacionPersonaBusqueda) {
    this.numeroIdentificacionPersonaBusqueda = numeroIdentificacionPersonaBusqueda;
  }

  public List<IndicadoresPlanTrabajo> getListaIndicadoresPlanTrabajoOtros() {
    return listaIndicadoresPlanTrabajoOtros;
  }

  public BigDecimal getHorasTotalesImplementacionPersonaBusqueda() {
    return horasTotalesImplementacionPersonaBusqueda;
  }

  public void setHorasTotalesImplementacionPersonaBusqueda(BigDecimal horasTotalesImplementacionPersonaBusqueda) {
    this.horasTotalesImplementacionPersonaBusqueda = horasTotalesImplementacionPersonaBusqueda;
  }

  public void setListaIndicadoresPlanTrabajoOtros(List<IndicadoresPlanTrabajo> listaIndicadoresPlanTrabajoOtros) {
    this.listaIndicadoresPlanTrabajoOtros = listaIndicadoresPlanTrabajoOtros;
  }

  public IndicadoresPlanTrabajo getIndicadoresPlanTrabajoSeleccionado() {
    return indicadoresPlanTrabajoSeleccionado;
  }

  public void setIndicadoresPlanTrabajoSeleccionado(IndicadoresPlanTrabajo indicadoresPlanTrabajoSeleccionado) {
    this.indicadoresPlanTrabajoSeleccionado = indicadoresPlanTrabajoSeleccionado;
  }

  public IndicadoresPlanTrabajo getIndicadoresPlanTrabajoEliminar() {
    return indicadoresPlanTrabajoEliminar;
  }

  public void setIndicadoresPlanTrabajoEliminar(IndicadoresPlanTrabajo indicadoresPlanTrabajoEliminar) {
    this.indicadoresPlanTrabajoEliminar = indicadoresPlanTrabajoEliminar;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarFormato() {

    try {

      HashMap mapa = new HashMap();
      mapa.put("p_id_proyecto", proyectoSeleccionado.getIdProyecto().intValue());
      mapa.put("p_id_plan_trabajo", planTrabajoImplementacion.getIdPlanTrabajo().intValue());

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte10.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "10_FORMATO PLAN DE TRABAJO IMPLEMENTACION.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   */
  public void eliminarActividad() {
    try {

      if (actividadesPlanImplementacionEliminar == null) {
        return;
      }

      iPlanTrabajoImplementacionLocal.desVincularActividadesPlanImplementacion(actividadesPlanImplementacionEliminar);
      cargarListaActividadesPlanImplementacion();
      actividadesPlanImplementacionEliminar = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  /**
   *
   */
  public void eliminarSemillero() {
    try {

      if (semillerosImplementacionEliminar == null) {
        return;
      }

      iPlanTrabajoImplementacionLocal.desVincularSemilleroInvestigacion(semillerosImplementacionEliminar);
      listaSemillerosVinculados = cargarListaSemillerosImplementacion();

      semillerosImplementacionEliminar = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  /**
   *
   */
  public void eliminarInvestigador() {
    try {

      if (investigadorProyectoEliminar == null) {
        return;
      }

      iPlanTrabajoImplementacionLocal.desInvestigadorProyecto(investigadorProyectoEliminar);

      cargarListaInvestigadorProyecto();

      investigadorProyectoEliminar = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr21RegistrarPlanDeTrabajoFaces.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public InvestigadorProyecto getInvestigadorProyectoEliminar() {
    return investigadorProyectoEliminar;
  }

  public void setInvestigadorProyectoEliminar(InvestigadorProyecto investigadorProyectoEliminar) {
    this.investigadorProyectoEliminar = investigadorProyectoEliminar;
  }

  public SemillerosImplementacion getSemillerosImplementacionEliminar() {
    return semillerosImplementacionEliminar;
  }

  public void setSemillerosImplementacionEliminar(SemillerosImplementacion semillerosImplementacionEliminar) {
    this.semillerosImplementacionEliminar = semillerosImplementacionEliminar;
  }

  public ActividadesPlanImplementacion getActividadesPlanImplementacionEliminar() {
    return actividadesPlanImplementacionEliminar;
  }

  public void setActividadesPlanImplementacionEliminar(ActividadesPlanImplementacion actividadesPlanImplementacionEliminar) {
    this.actividadesPlanImplementacionEliminar = actividadesPlanImplementacionEliminar;
  }

  public boolean isEditandoActividadesPlanImplementacion() {
    return actividadesPlanImplementacion != null && actividadesPlanImplementacion.getIdActividadPlanImplementacion() != null;
  }

  public String cancelarEditarActividadImpl() {

    actividadesPlanImplementacion = new ActividadesPlanImplementacion();
    actividadesPlanImplementacion.setResponsableActividaImplementacionList(new ArrayList<ResponsableActividaImplementacion>());
    return retorno;
  }

  public String seleccionActividad() {
    return retorno;
  }
}
