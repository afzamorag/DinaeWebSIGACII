package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IActividadesImplementacionLocal;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresPlanTrabajoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceImplementacionLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IPersonalImplementacionLocal;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ITemaLocal;
import co.gov.policia.dinae.interfaces.ITemaProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.ActividadesImplementacion;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.Tema;
import co.gov.policia.dinae.modelo.TemaProyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr15_1_2_AvanceImplemenacionFaces")
@javax.enterprise.context.SessionScoped
public class CuPr15_1_2_AvanceImplemenacionFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr20GestionImplementacionesVigentesAsignadas cuPr20GestionImplementacionesVigentesAsignadas;

  private enum ORIGEN_LLAMADO_CU {

    CU_PR_25, CU_PR_20, CU_PR_24, CU_PR_06
  };

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IInformeAvanceImplementacionLocal iInformeAvanceImplementacionLocal;

  @EJB
  private IImplementacionProyectoLocal iImplementacionProyectoLocal;

  @EJB
  private ICompromisoImplementacionLocal iCompromisoImplementacionLocal;

  @EJB
  private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;

  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

  @EJB
  private IPersonalImplementacionLocal iPersonalImplementacionLocal;

  @EJB
  private ITemaProyectoLocal iTemaProyectoLocal;

  @EJB
  private ITemaLocal iTemaLocal;

  @EJB
  private IPlanTrabajoImplementacionLocal iPlanTrabajoImplementacionLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IActividadesImplementacionLocal iActividadesImplementacionLocal;

  @EJB
  private IIndicadoresPlanTrabajoLocal iIndicadoresPlanTrabajoLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  @Inject
  private CuPr14AvancePresupuestoProyectoImplementacion cuPr14AvanceProyectoImplementacionFaces;

  @Inject
  private CuPr05RegistrarPresupuestoImplementacionFaces cuPr05RegistrarPresupuestoImplFaces;

  private boolean accesoSoloConsulta;
  private CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU registraLlamdoCU;
  private String lblTituloPagina;
  private ImplementacionesProyecto implementacionProyecto;
  private CompromisoImplementacion compromisoImplementacionInformeAvanceOinformeFinal;
  private ProyectoDTO proyectoSeleccionado;
  private InformeAvanceImplementacion informeAvanceImplementacion;
  private RolUsuarioDTO rolUsuarioDTO;
  private List<EjecutorNecesidad> listaUnidadPolicialEjecutoras;
  private List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba;
  private List<InvestigadorProyecto> listaInvestigadoresProyecto;
  private List<InvestigadorProyecto> listaPersonalImplementacion;
  private List<Tema> listaTemasAvanceProceso;
  private List<Tema> listaTemasInformacionTenerEnCuenta;
  private TemaProyecto temaProyectoSeleccionado;
  private int idTabSeleccionado;
  private String nombreArchivoPlanteamientoProyecto;
  private FileUploadEvent eventArchivoSubido;
  private FileUploadEvent eventArchivoSoporteSubido;
  private FileUploadEvent eventEvidenciaFotograficaSubido;
  private ListGenericDataModel<ActividadesPlanImplementacion> listaActividadesPlanImplementacion;
  private List<ActividadesImplementacion> listaActividadesPlanImplementacionEvidenciaFotografica;
  private ActividadesPlanImplementacion actividadesPlanImplementacionSeleccionado;
  private CompromisoImplementacion compromisoImplementacionPlanTrabajo;
  private PlanTrabajoImplementacion planTrabajoImplementacion;
  private List<SelectItem> listaEstadoActividadItem;
  private Long idEstadoActividadImplSeleccionado;
  private ActividadesImplementacion actividadesImplementacion;
  private ActividadesImplementacion actividadesImplementacionEstadoRealizadaInformeAvance;
  private UsuarioRol usuarioRol;
  private List<InvestigadorProyecto> listaInvestigadorProyecto;
  private ListGenericDataModel<IndicadoresPlanTrabajoImplementacion> listaIndicadoresPlanTrabajoOtros;
  private IndicadoresPlanTrabajoImplementacion indicadoresPlanTrabajoSeleccionado;
  private String importPresupuestoPage;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      indicadoresPlanTrabajoSeleccionado = null;
      usuarioRol = null;
      eventArchivoSoporteSubido = null;
      eventEvidenciaFotograficaSubido = null;
      actividadesImplementacion = null;
      idEstadoActividadImplSeleccionado = null;
      actividadesPlanImplementacionSeleccionado = null;
      planTrabajoImplementacion = null;
      listaActividadesPlanImplementacion = null;
      listaActividadesPlanImplementacionEvidenciaFotografica = null;
      eventArchivoSubido = null;
      nombreArchivoPlanteamientoProyecto = null;
      idTabSeleccionado = 0;
      temaProyectoSeleccionado = null;
      listaTemasAvanceProceso = null;
      listaTemasInformacionTenerEnCuenta = null;
      listaPersonalImplementacion = null;
      listaInvestigadoresProyecto = null;
      listaJefeFuncionariosSeleccionadoAprueba = null;
      implementacionProyecto = null;
      compromisoImplementacionInformeAvanceOinformeFinal = null;
      proyectoSeleccionado = null;
      lblTituloPagina = null;
      accesoSoloConsulta = true;
      listaUnidadPolicialEjecutoras = null;
      listaEstadoActividadItem = null;
      listaInvestigadorProyecto = null;
      listaIndicadoresPlanTrabajoOtros = null;
      actividadesImplementacionEstadoRealizadaInformeAvance = null;

      rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_LA_IMPLEMENTACION_EN_LA_UNIDAD_POLICIAL);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 Avance implementacion (init) ", e);
    }
  }

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

    String retorno = initReturnCU_DESDE_PR_25(idImplementacionProyecto, idCompromisoImplementacion, accesoSoloConsulta);

    this.accesoSoloConsulta = accesoSoloConsulta;

    registraLlamdoCU = CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU.CU_PR_20;

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
  public String initReturnCU_DESDE_PR_06(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    String retorno = initReturnCU_DESDE_PR_25(idImplementacionProyecto, idCompromisoImplementacion, accesoSoloConsulta);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_06;

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
  public String initReturnCU_DESDE_PR_24(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    String retorno = initReturnCU_DESDE_PR_25(idImplementacionProyecto, idCompromisoImplementacion, accesoSoloConsulta);

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
  public String initReturnCU_DESDE_PR_25(Long idImplementacionProyecto, Long idCompromisoImplementacion, boolean accesoSoloConsulta) throws Exception {

    init();

    //CONSULTAMOS EL COMPROMISO TIPO PLAN DE TRABAJO
    //YA QUE LA LISTA DE PERSONAL A MOSTRAR ES LA DE DICHO COMPROMISO
    compromisoImplementacionPlanTrabajo = iCompromisoImplementacionLocal
            .obtenerCompromisoImplementacionPorImplementacionProyectoYtipoCompromiso(
                    idImplementacionProyecto,
                    IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO);

    //EL COMPROMISO IMPLEMENTACION PLAN DE TRABAJO DEBE ESTAR EN ESTADO CUMPLIUDO
    if (compromisoImplementacionPlanTrabajo == null || compromisoImplementacionPlanTrabajo.getIdEstadoCompromisoImpl() == null
            || !compromisoImplementacionPlanTrabajo.getIdEstadoCompromisoImpl().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)) {

      adicionaMensajeError("El compromiso Plan de Trabajo debe estar cumplido para poder continuar");
      return null;

    }

    compromisoImplementacionInformeAvanceOinformeFinal = iCompromisoImplementacionLocal.obtenerCompromisoImplementacionPorId(idCompromisoImplementacion);

    if (isCompromisoInformeFinal()) {

      //SI EL COMPROMISO ES EL INFORM FINAL
      //VERIFICAMOS QUE EL INFORME DE AVANCE SE ENCUENTRE EN ESTADO CUMPLIDO
      CompromisoImplementacion compromisoImplementacionInformeAvance = iCompromisoImplementacionLocal
              .obtenerCompromisoImplementacionPorImplementacionProyectoYtipoCompromiso(
                      idImplementacionProyecto,
                      IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE);
      //EL COMPROMISO IMPLEMENTACION INFORME AVANCE DEBE ESTAR EN ESTADO CUMPLIUDO
      if (compromisoImplementacionInformeAvance == null || compromisoImplementacionInformeAvance.getIdEstadoCompromisoImpl() == null
              || !compromisoImplementacionInformeAvance.getIdEstadoCompromisoImpl().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)) {

        adicionaMensajeError("El compromiso Informe de avance de implementación debe estar cumplido para poder continuar");
        return null;

      }

    }
    implementacionProyecto = iImplementacionProyectoLocal.getImplementacionProyecto(idImplementacionProyecto);

    proyectoSeleccionado = iProyectoLocal.getProyectoDTOporImplementacionProyecto(idImplementacionProyecto);

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

    //CARGAMOS LA LISTA DE UNIDADES EJECUTORAS
    listaUnidadPolicialEjecutoras = iEjecutorNecesidadLocal.getEjecutorNecesidadPorProyecto(proyectoSeleccionado.getId());

    //CARGAMOS LISTA DE JEFES DE UNIDAD QUE APRUEBA
    listaJefeFuncionariosSeleccionadoAprueba = new ArrayList<VistaFuncionario>();

    if (proyectoSeleccionado.getUnidadPolicialDTO() != null && proyectoSeleccionado.getUnidadPolicialDTO().getIdUnidadPolicial() != null) {

      listaJefeFuncionariosSeleccionadoAprueba = iUsuarioUnidadPolicialLocal.getUsuarioByUnidadByRole(
              proyectoSeleccionado.getUnidadPolicialDTO().getIdUnidadPolicial(),
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);

    }

    //TODOS LOS JEFES PERTENECEN A LA MISMA UNIDAD FUNCIONAL(DATOS DEL CU-PR-01)                
    for (VistaFuncionario unaVistaFuncionario : listaJefeFuncionariosSeleccionadoAprueba) {

      //VERIFICAMOS SI EL FUNCIONARIO TIIENE UNIDAD POLICIAL
      if (unaVistaFuncionario.getCodigoUnidadLaboral() != null && unaVistaFuncionario.getCodigoUnidadLaboral().trim().length() > 0) {

        Long idUnidadPolicial = Long.valueOf(unaVistaFuncionario.getCodigoUnidadLaboral().trim());
        UnidadPolicial unidadPolicial = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(idUnidadPolicial);
        unaVistaFuncionario.setUnidadPolicial(unidadPolicial);

      } else {

        unaVistaFuncionario.setUnidadPolicial(new UnidadPolicial());
      }

    }

    listaInvestigadoresProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorProyecto(proyectoSeleccionado.getId());

    planTrabajoImplementacion = iPlanTrabajoImplementacionLocal.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
            idImplementacionProyecto,
            compromisoImplementacionPlanTrabajo.getIdCompromisoImplementacion());

    //CONSULTAMOS EL INFORME AVANCE IMPLEMENTACION
    //InformeAvanceImplementacion informeAvanceImplementacionPlanTrabajo = iInformeAvanceImplementacionLocal.findInformeAvanceImplementacionFinaByIdImplemenatcionProYIdCompromisoProImpl(
    //      idImplementacionProyecto, compromisoImplementacionPlanTrabajo.getIdCompromisoImplementacion());
    listaPersonalImplementacion = new ArrayList<InvestigadorProyecto>();
    if (planTrabajoImplementacion != null) {

      listaPersonalImplementacion = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());
    }

    cargarListaTemasAvanceProceso();

    cargarlistaTemasInformacionTenerEnCuenta();

    cargarListaActividadesPlanImplementacion();

    cargarListaActividadesPlanImplementacionEvidenciaFotografica();

    cargarListaInvestigadorProyecto();

    cargarListaIndicadoresProyectoOtros();

    lblTituloPagina = compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getValor();

    this.accesoSoloConsulta = accesoSoloConsulta;

    listaEstadoActividadItem = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL),
            "idConstantes",
            "valor");

    RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
            IConstantesRole.RESPONSABLE_DE_LA_IMPLEMENTACION_EN_LA_UNIDAD_POLICIAL);
    if (!accesoSoloConsulta && rolUsuarioDTO != null) {
      // ESTAMOS EDITADO 
      usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());
    }

    registraLlamdoCU = CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU.CU_PR_25;

    return navigationFaces.redirectCuPr15_1_2_AvanceImplemenacion();
  }

  /**
   *
   * @return
   */
  public String guardarIndicador() {

    if (indicadoresPlanTrabajoSeleccionado == null) {
      return null;
    }

    try {

      iIndicadoresPlanTrabajoLocal.guardarIndicadoresPlanTrabajoImplementacion(indicadoresPlanTrabajoSeleccionado);

      cargarListaIndicadoresProyectoOtros();

      cancelarIndicador();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", e);

    }

    return null;
  }

  /**
   *
   * @return
   */
  public String cancelarIndicador() {

    indicadoresPlanTrabajoSeleccionado = new IndicadoresPlanTrabajoImplementacion();

    return null;
  }

  /**
   *
   * @param event
   * @throws java.io.IOException
   */
  public void seleccionarIndicadorProyecto(SelectEvent event) throws IOException {

    if (indicadoresPlanTrabajoSeleccionado == null) {
      //FINALIZA EL PROCESO
      //PROCESO FUTURO, SE ADICIONO EL METODO VACIO PARA QUE EL EVENTO onChangeRefres LOS CAMPOS EN XHTML
    }

  }

  /**
   *
   * @param event
   */
  public void noSeleccionarIndicadorProyecto(UnselectEvent event) {

    indicadoresPlanTrabajoSeleccionado = null;

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoOtros() throws Exception {

    if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

      listaIndicadoresPlanTrabajoOtros = new ListGenericDataModel(new ArrayList<IndicadoresPlanTrabajoImplementacion>());
      return;
    }

    listaIndicadoresPlanTrabajoOtros = new ListGenericDataModel(iIndicadoresPlanTrabajoLocal.getListaIndicadoresPlanTrabajoImplementacionPorPlanTrabajoImplYidInformeAvanceImplementacion(
            informeAvanceImplementacion.getIdInformeAvanceImplementacion(),
            planTrabajoImplementacion.getIdPlanTrabajo()));

  }

  /**
   *
   * @return
   */
  public String guardarTiempoDedicado() {

    try {

      int indiceFila = 0;
      //VERIFICAMOS LOS QUE LOS VALORES INGRESADOS EN LAS HORAS DEDICADAS SEAN CORRECTOS
      for (InvestigadorProyecto unInvestigadorProyecto : listaInvestigadorProyecto) {

        indiceFila++;

        if (isCompromisoInformeAvance()) {

          if (unInvestigadorProyecto.getHorasTotalesImplementacion() == null) {

            adicionaMensajeError("Las Horas totales de implementación del registro numero ".concat(String.valueOf(indiceFila)).concat(" es inválido."));
            return null;
          }
          if (unInvestigadorProyecto.getHorasDedicadasImplementacion() == null) {

            adicionaMensajeError("Las Horas dedicadas en el periodo del registro numero ".concat(String.valueOf(indiceFila)).concat(" es inválido."));
            return null;
          }
          //VERIFICAMOS QUE LAS HORAS DE DEDICACION NO SEAM MAYORES A LAS HORAS DE DEDICACION
          //Y TAMPOCO SEA NEGATIVO
          if ((unInvestigadorProyecto.getHorasDedicadasImplementacion().longValue() > unInvestigadorProyecto.getHorasTotalesImplementacion().longValue())
                  || unInvestigadorProyecto.getHorasDedicadasImplementacion().longValue() < 0) {

            adicionaMensajeError("Las Horas dedicadas en el periodo del registro numero ".concat(String.valueOf(indiceFila)).concat(", no deben ser mayores a las Horas totales de implementación "));
            return null;

          }
        } else if (isCompromisoInformeFinal()) {

          if (unInvestigadorProyecto.getHorasTotalesImplementacion() == null) {

            adicionaMensajeError("Las Horas totales de implementación del registro numero ".concat(String.valueOf(indiceFila)).concat(" es inválido."));
            return null;
          }
          if (unInvestigadorProyecto.getHorasDedicadasAvanceFinalImplementacion() == null) {

            adicionaMensajeError("Las Horas dedicadas en el periodo del registro numero ".concat(String.valueOf(indiceFila)).concat(" es inválido."));
            return null;
          }
          //VERIFICAMOS QUE LAS HORAS DE DEDICACION NO SEAM MAYORES A LAS HORAS DE DEDICACION
          //Y TAMPOCO SEA NEGATIVO
          if ((unInvestigadorProyecto.getHorasDedicadasAvanceFinalImplementacion().longValue() > unInvestigadorProyecto.getHorasTotalesImplementacion().longValue())
                  || unInvestigadorProyecto.getHorasDedicadasAvanceFinalImplementacion().longValue() < 0) {

            adicionaMensajeError("Las Horas dedicadas en el periodo del registro numero ".concat(String.valueOf(indiceFila)).concat(", no deben ser mayores a las Horas totales de implementación "));
            return null;

          }

        }

      }
      //ACTUALIZAMOS LOS INVESTIGADORES
      iInvestigadorProyectoLocal.guardarHorasInvestigacionLista(listaInvestigadorProyecto);

      //ACTUALIZAMOS EL COMPROMISO PROYECTO 
      actualizaCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      cargarListaInvestigadorProyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", e);

    }

    return null;

  }

  /**
   *
   * @return
   */
  public String cancelarGuardarResultado() {

    actividadesPlanImplementacionSeleccionado = null;
    eventArchivoSoporteSubido = null;
    eventEvidenciaFotograficaSubido = null;

    return null;

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaInvestigadorProyecto() throws Exception {

    if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

      listaInvestigadorProyecto = new ArrayList<InvestigadorProyecto>();
      return;

    }
    listaInvestigadorProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());

  }

  /**
   *
   * @return
   */
  public String guardarResultado() {

    try {

      //VERIFICAMOS SOBRE QUE PESTAÑA ESTAMOS TRABJANDO
      if (isMostrarTabViewActividad("ACTIVIDAD_NO_CUMPLIDA")) {

        if (DatesUtils.compareDate(actividadesImplementacion.getNuevaFechaInicio(), actividadesImplementacion.getNuevaFechaFin()) == 1) {

          adicionaMensajeError("La Nueva fecha de inicio debe ser menor o igual a la Nueva fecha fin");
          return null;
        }

        actividadesImplementacion.setPorcentajeCumplimiento(BigDecimal.ZERO);

        if (isCompromisoInformeAvance()) {

          actividadesPlanImplementacionSeleccionado.setPorcentaje(BigDecimal.ZERO);

        } else if (isCompromisoInformeFinal()) {

          actividadesPlanImplementacionSeleccionado.setPorcentajeInformeFinal(BigDecimal.ZERO);

        }

      } else if (isMostrarTabViewActividad("ACTIVIDAD_REALIZADA_PARCIALMENTE")) {

        if (co.gov.policia.dinae.util.DatesUtils.compareDate(actividadesImplementacion.getNuevaFechaFin(), new Date()) == -1) {

          //La fecha ingresada es menor a la fecha actual.
          adicionaMensajeError("La Nueva fecha de finalización debe ser mayor a la fecha actual.");
          return null;

        }

        if (isCompromisoInformeAvance()) {

          actividadesPlanImplementacionSeleccionado.setPorcentaje(actividadesImplementacion.getPorcentajeCumplimiento());

        } else if (isCompromisoInformeFinal()) {

          actividadesPlanImplementacionSeleccionado.setPorcentajeInformeFinal(actividadesImplementacion.getPorcentajeCumplimiento());

        }

      } else if (isMostrarTabViewActividad("ACTIVIDAD_REALIZADA")) {

        if (DatesUtils.compareDate(actividadesImplementacion.getFechaInicioReal(), actividadesImplementacion.getFechaFinReal()) == 1) {

          adicionaMensajeError("La Fecha real de inicio debe ser menor o igual a la Fecha real de fin");
          return null;
        }

        //VERIFICAMOS QUE EL ARCHIVO FUE SELECCIONADO MIENTRAS SEA UNA NUEVA ACTIVIDAD
        if (actividadesImplementacion.getIdActividadImplementacion() == null && eventArchivoSoporteSubido == null) {

          adicionaMensajeError("Debe seleccionar el Archivo de soporte");
          return null;

        }

        //VERIFICAMOS QUE EL ARCHIVO FUE SELECCIONADO MIENTRAS SEA UNA NUEVA ACTIVIDAD
        if (actividadesImplementacion.getIdActividadImplementacion() == null && eventEvidenciaFotograficaSubido == null) {

          adicionaMensajeError("Debe seleccionar la Evidencia fotográfica");
          return null;

        }

        String[] archivo;

        //VERIFICAMOS SI LA RUTA FISICA DONDE SE ALMACENAN LOS ARCHIVOS EXISTE
        //Y LOS PERMISOS SON VALIDOS
        File directorioFinal;

        try {

          directorioFinal = new File(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion"));

        } catch (NullPointerException e) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (!directorioFinal.exists()) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (directorioFinal.isFile()) {
          adicionaMensajeError("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (!directorioFinal.canWrite()) {
          adicionaMensajeError("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (eventArchivoSoporteSubido != null) {

          archivo = almacenarArchivoFisico(eventArchivoSoporteSubido);
          actividadesImplementacion.setArchivoSoporte(archivo[0] != null ? archivo[0] : actividadesImplementacion.getArchivoSoporte());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
          actividadesImplementacion.setArchivoSoporteFisico(archivo[1] != null ? archivo[1] : actividadesImplementacion.getArchivoSoporteFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

        }

        if (eventEvidenciaFotograficaSubido != null) {

          archivo = almacenarArchivoFisico(eventEvidenciaFotograficaSubido);
          actividadesImplementacion.setEvidenciaFotografica(archivo[0] != null ? archivo[0] : actividadesImplementacion.getEvidenciaFotografica());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
          actividadesImplementacion.setEvidenciaFotograficaFisico(archivo[1] != null ? archivo[1] : actividadesImplementacion.getEvidenciaFotograficaFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

        }

        actividadesImplementacion.setPorcentajeCumplimiento(BigDecimal.valueOf(100));
        if (isCompromisoInformeAvance()) {

          actividadesPlanImplementacionSeleccionado.setPorcentaje(BigDecimal.valueOf(100));

        } else if (isCompromisoInformeFinal()) {

          actividadesPlanImplementacionSeleccionado.setPorcentajeInformeFinal(BigDecimal.valueOf(100));

        }

      }

      if (actividadesImplementacion.getIdActividadImplementacion() == null) {

        actividadesImplementacion.setActividadesPlanImplementacion(actividadesPlanImplementacionSeleccionado);
        actividadesImplementacion.setInformeAvanceImplementacion(informeAvanceImplementacion);

      }

      actividadesImplementacion.setEstadoActividad(new Constantes(idEstadoActividadImplSeleccionado));
      actividadesImplementacion.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      actividadesImplementacion.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      actividadesImplementacion.setUsuarioRol(usuarioRol);

      if (isCompromisoInformeAvance()) {

        actividadesPlanImplementacionSeleccionado.setEstado(new Constantes(idEstadoActividadImplSeleccionado));

      } else if (isCompromisoInformeFinal()) {

        actividadesPlanImplementacionSeleccionado.setEstadoInformeFinal(new Constantes(idEstadoActividadImplSeleccionado));

      }

      //GUARDAMOS LA ACTIVIDAD
      iActividadesImplementacionLocal.guardarActividadesImplementacionYactualizaEstadoActividadPlanTrabajo(
              actividadesImplementacion,
              actividadesPlanImplementacionSeleccionado);

      //ACTUALIZAMOS EL COMPROMISO PROYECTO 
      actualizaCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      cargarListaActividadesPlanImplementacion();

      cargarListaActividadesPlanImplementacionEvidenciaFotografica();

      actividadesPlanImplementacionSeleccionado = null;
      eventArchivoSoporteSubido = null;
      eventEvidenciaFotograficaSubido = null;

      adicionaMensajeInformativo("Actividad actualiza correctamente.");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", e);

    }

    return null;
  }

  /**
   *
   * @param event
   */
  public void handleFileUploadArchivoSoporte(FileUploadEvent event) {
    try {

      eventArchivoSoporteSubido = event;

      actividadesImplementacion.setArchivoSoporte(event.getFile().getFileName());
      actividadesImplementacion.setArchivoSoporteFisico(event.getFile().getFileName());

      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(event.getFile().getFileName()));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", e);
    }
  }

  /**
   *
   * @param event
   */
  public void handleFileUploadEvidenciaFotografica(FileUploadEvent event) {
    try {

      eventEvidenciaFotograficaSubido = event;

      actividadesImplementacion.setEvidenciaFotografica(event.getFile().getFileName());
      actividadesImplementacion.setEvidenciaFotograficaFisico(event.getFile().getFileName());

      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(event.getFile().getFileName()));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", e);
    }
  }

  public void noSeleccionarActividadImpl(UnselectEvent event) {
    actividadesPlanImplementacionSeleccionado = null;
  }

  public void seleccionarActividadImpl(SelectEvent event) {

    if (actividadesPlanImplementacionSeleccionado == null) {
      //FINALIZA EL PROCESO            
    }

    try {

      actividadesImplementacionEstadoRealizadaInformeAvance = null;
      idEstadoActividadImplSeleccionado = null;

      //BUSCAMOS PARA VER SI EXISTE ALGUNA ACTIVIDAD IMPLEMENTACION GUARDADA
      actividadesImplementacion = iActividadesImplementacionLocal.getActividadesImplementacionPorInformeAvanceImplYActividadPlanTrabajo(
              informeAvanceImplementacion.getIdInformeAvanceImplementacion(),
              actividadesPlanImplementacionSeleccionado.getIdActividadPlanImplementacion()
      );

      if (actividadesImplementacion == null) {

        actividadesImplementacion = new ActividadesImplementacion();
        idEstadoActividadImplSeleccionado = null;

      } else if (isCompromisoInformeAvance()) {

        idEstadoActividadImplSeleccionado = actividadesPlanImplementacionSeleccionado.getEstado().getIdConstantes();

      } else if (isCompromisoInformeFinal()) {

        idEstadoActividadImplSeleccionado = actividadesPlanImplementacionSeleccionado.getEstadoInformeFinal().getIdConstantes();

      }

      if (isSoloLecturaActividadesPlanImplementacion()) {

        adicionaMensajeInformativo("Esta actividad se encuentra REALIZADA, por lo tanto no podra ser modificada.");

        //CONSULTAMOS EL ID DEL INFORME AVANCE DE INFORME (TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)
        InformeAvanceImplementacion informeAvanceTipoCompromisoImplementacionInformeAvance = iInformeAvanceImplementacionLocal.getInformeAvanceImplementacionPorTipoCompromisoEImplementacionProy(
                IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE,
                implementacionProyecto.getIdImplementacionProy());

        actividadesImplementacionEstadoRealizadaInformeAvance = iActividadesImplementacionLocal.getActividadesImplementacionPorInformeAvanceImplYActividadPlanTrabajo(
                informeAvanceTipoCompromisoImplementacionInformeAvance.getIdInformeAvanceImplementacion(),
                actividadesPlanImplementacionSeleccionado.getIdActividadPlanImplementacion()
        );

      }

      eventArchivoSoporteSubido = null;
      eventEvidenciaFotograficaSubido = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);

    }
  }

  /**
   *
   */
  public void handleEstadoActiviadChange() {

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaActividadesPlanImplementacion() throws Exception {

    if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

      listaActividadesPlanImplementacion = new ListGenericDataModel(new ArrayList<ActividadesPlanImplementacion>());
      return;

    }

    listaActividadesPlanImplementacion = new ListGenericDataModel<ActividadesPlanImplementacion>(iPlanTrabajoImplementacionLocal.getListaActividadesPlanImplementacionPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo()));

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaActividadesPlanImplementacionEvidenciaFotografica() throws Exception {

    if (planTrabajoImplementacion == null || planTrabajoImplementacion.getIdPlanTrabajo() == null) {

      listaActividadesPlanImplementacionEvidenciaFotografica = new ArrayList<ActividadesImplementacion>();
      return;

    }

    List<Long> listaEstado = new ArrayList<Long>(1);
    listaEstado.add(IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_REALIZADA);

    listaActividadesPlanImplementacionEvidenciaFotografica = iActividadesImplementacionLocal.getListaActividadesImplementacionInformeAvanceYestadosActividad(
            informeAvanceImplementacion.getIdInformeAvanceImplementacion(), listaEstado
    );

  }

  /**
   *
   * @return
   */
  public String guardarTemaInformacionTenerCuenta() {

    if (temaProyectoSeleccionado == null || temaProyectoSeleccionado.getTema() == null || temaProyectoSeleccionado.getTema().getIdTema() == null) {
      return null;
    }

    if (temaProyectoSeleccionado.getTexto() == null || temaProyectoSeleccionado.getTexto().trim().length() == 0
            || temaProyectoSeleccionado.getTexto().trim().equals("<br>")) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_pr_4_lbl_info_incompleta_detalle_tema"));
      return null;
    }

    try {

      temaProyectoSeleccionado.setInformeAvanceImplementacion(informeAvanceImplementacion);

      //ACTUALIZAMOS
      temaProyectoSeleccionado = iTemaProyectoLocal.guardarTemaProyecto(temaProyectoSeleccionado);

      //ACTUALIZAMOS EL COMPROMISO PROYECTO 
      actualizaCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      eventArchivoSubido = null;

      nombreArchivoPlanteamientoProyecto = null;

      temaProyectoSeleccionado = new TemaProyecto();

      cargarlistaTemasInformacionTenerEnCuenta();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_4_lbl_info_almacenada_correctamente"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);

    }
    return null;

  }

  /**
   *
   * @return
   */
  public String guardarTemaResultadosObtenidos() {

    if (temaProyectoSeleccionado == null || temaProyectoSeleccionado.getTema() == null || temaProyectoSeleccionado.getTema().getIdTema() == null) {
      return null;
    }

    if (temaProyectoSeleccionado.getTexto() == null || temaProyectoSeleccionado.getTexto().trim().length() == 0
            || temaProyectoSeleccionado.getTexto().trim().equals("<br>")) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_pr_4_lbl_info_incompleta_detalle_tema"));
      return null;
    }

    try {

      String[] archivo = new String[]{null, null};
      //VERIFICAMOS SI EL USUARIO INGRESO ALGUN ARCHIVO
      if (eventArchivoSubido != null) {
        //VERIFICAMOS SI LA RUTA FISICA DONDE SE ALMACENAN LOS ARCHIVOS EXISTE
        //Y LOS PERMISOS SON VALIDOS
        File directorioFinal;

        try {

          directorioFinal = new File(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion"));

        } catch (NullPointerException e) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (!directorioFinal.exists()) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (directorioFinal.isFile()) {
          adicionaMensajeError("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        if (!directorioFinal.canWrite()) {
          adicionaMensajeError("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
          return null;
        }

        archivo = almacenarArchivoFisico(eventArchivoSubido);
      }

      temaProyectoSeleccionado.setInformeAvanceImplementacion(informeAvanceImplementacion);
      temaProyectoSeleccionado.setArchivoSoporte(archivo[0] != null ? archivo[0] : temaProyectoSeleccionado.getArchivoSoporte());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      temaProyectoSeleccionado.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : temaProyectoSeleccionado.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

      //ACTUALIZAMOS
      temaProyectoSeleccionado = iTemaProyectoLocal.guardarTemaProyecto(temaProyectoSeleccionado);

      //ACTUALIZAMOS EL COMPROMISO PROYECTO 
      actualizaCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      eventArchivoSubido = null;

      nombreArchivoPlanteamientoProyecto = null;

      cargarListaTemasAvanceProceso();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_4_lbl_info_almacenada_correctamente"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);

    }
    return null;

  }

  /**
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico(FileUploadEvent eventLocal) {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      String nombreArchivoOriginal = eventLocal.getFile().getFileName();
      String extension = "";
      int i = nombreArchivoOriginal.lastIndexOf('.');
      if (i > 0) {
        extension = nombreArchivoOriginal.substring(i);
      }
      String nombreArchivoFisico = "AVANCE_IMPL_PROCESO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

      copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion"), eventLocal.getFile().getInputstream(), nombreArchivoFisico);

      //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
      return new String[]{nombreArchivoOriginal, nombreArchivoFisico};

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 Agregar ", e);
    }

    return null;

  }

  /**
   *
   * @throws Exception
   */
  private void cargarlistaTemasInformacionTenerEnCuenta() throws Exception {

    String tipoTema = null;
    String tipoInformacion = null;

    if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)) {

      tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_INFOR_TENER_CUENTA;
      tipoInformacion = IConstantes.DESTINO_TEMA_CU_PR_15_TAB_6_INFORMACION_TENER_EN_CUENTA_AVANCE;

    } else if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

      tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_FINAL_INFOR_TENER_CUENTA;
      tipoInformacion = IConstantes.DESTINO_TEMA_CU_PR_15_TAB_6_INFORMACION_TENER_EN_CUENTA_AVANCE_FINAL;

    }

    //CONSULTAMOS LOS TEMAS A MOSTRAR
    listaTemasInformacionTenerEnCuenta = iTemaLocal.getListaTemaTodos(tipoTema);

    //CONSULTAMOS LOS TEMAS QUE TIENEN CARGADA INFORMACION
    //CON EL FIN DE MOSTRAR O NO LA IMAGEN DE: icono_chechk.png
    List<Long> listaIdTemasInfoCargada = iTemaProyectoLocal.getIDTemaProyectoPorInformeAvanceImplementacion(
            informeAvanceImplementacion.getIdInformeAvanceImplementacion(),
            tipoInformacion);

    //VERIFICAMOS QUE TEMAS YA CONTIENEN INFORMACION CARGADA
    for (Tema unTema : listaTemasInformacionTenerEnCuenta) {
      for (Long unIDtema : listaIdTemasInfoCargada) {

        if (unIDtema.equals(unTema.getIdTema())) {

          unTema.setMostrarImagenCheckInformacionPlanteamientoProblema(true);
          //CONTINUAMOS CON EL SIGUIENTE TEMA
          break;
        }
      }
    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaTemasAvanceProceso() throws Exception {

    String tipoTema = null;
    String tipoInformacion = null;

    if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)) {

      tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_INFO_AVANCE_PROCESO;
      tipoInformacion = IConstantes.DESTINO_TEMA_PR_15_TAB_2_AVANCE_SOBRE_PROCESO;

    } else if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

      tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_FINAL_PROCESO;
      tipoInformacion = IConstantes.DESTINO_TEMA_PR_15_TAB_2_FINAL_RESULTADO_OBTENIDO;

    }

    //CONSULTAMOS LOS TEMAS A MOSTRAR
    listaTemasAvanceProceso = iTemaLocal.getListaTemaTodos(tipoTema);

    //CONSULTAMOS LOS TEMAS QUE TIENEN CARGADA INFORMACION
    //CON EL FIN DE MOSTRAR O NO LA IMAGEN DE: icono_chechk.png
    List<Long> listaIdTemasInfoCargada = iTemaProyectoLocal.getIDTemaProyectoPorInformeAvanceImplementacion(
            informeAvanceImplementacion.getIdInformeAvanceImplementacion(),
            tipoInformacion);

    //VERIFICAMOS QUE TEMAS YA CONTIENEN INFORMACION CARGADA
    for (Tema unTema : listaTemasAvanceProceso) {
      for (Long unIDtema : listaIdTemasInfoCargada) {

        if (unIDtema.equals(unTema.getIdTema())) {

          unTema.setMostrarImagenCheckInformacionPlanteamientoProblema(true);
          //CONTINUAMOS CON EL SIGUIENTE TEMA
          break;
        }
      }
    }

  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    temaProyectoSeleccionado = null;
    idTabSeleccionado = 0;
    importPresupuestoPage = null;

    if (event == null || event.getTab() == null) {
      return;
    }
    if ("idtabinformacion_general_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idtabavance_proceso_implementacion".equals(event.getTab().getId()) || "idtabresultados_obtenidos_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    } else if ("idtabactividades_realizadas_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 2;
    } else if ("idtiempo_dedicado_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 3;
    } else if ("idtabpresupuesto_implmentacion".equals(event.getTab().getId())) {
      importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";
      ejecutarPresupuesto();
      idTabSeleccionado = 4;
    } else if ("idtabgestion_indicadores_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 5;
    } else if ("idtabinformacion_en_cuenta_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 6;
    } else if ("idtabevidencia_fotografica_implementacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 7;
    }
  }

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      eventArchivoSubido = event;
      nombreArchivoPlanteamientoProyecto = event.getFile().getFileName();
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(nombreArchivoPlanteamientoProyecto));

    } catch (Exception e) {
      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (handleFileUpload) ", e);
    }
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadAvanceProcesoContentProperty() {

    try {

      if (temaProyectoSeleccionado != null
              && temaProyectoSeleccionado.getArchivoSoporte() != null
              && temaProyectoSeleccionado.getNombreArchivoFisico() != null) {

        String name = keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion") + temaProyectoSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, temaProyectoSeleccionado.getArchivoSoporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadAvanceProcesoArchivoProcesoContentProperty() {

    try {

      if (actividadesImplementacion != null
              && actividadesImplementacion.getArchivoSoporte() != null
              && actividadesImplementacion.getArchivoSoporteFisico() != null) {

        String name = keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion") + actividadesImplementacion.getArchivoSoporteFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, actividadesImplementacion.getArchivoSoporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadAvanceProcesoArchivoProcesoContentPropertyActividadRealizada() {

    try {

      if (actividadesImplementacionEstadoRealizadaInformeAvance != null
              && actividadesImplementacionEstadoRealizadaInformeAvance.getArchivoSoporte() != null
              && actividadesImplementacionEstadoRealizadaInformeAvance.getArchivoSoporteFisico() != null) {

        String name = keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion") + actividadesImplementacionEstadoRealizadaInformeAvance.getArchivoSoporteFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, actividadesImplementacionEstadoRealizadaInformeAvance.getArchivoSoporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadAvanceProcesoEvidenciaFotograficaContentProperty() {

    try {

      if (actividadesImplementacion != null
              && actividadesImplementacion.getEvidenciaFotografica() != null
              && actividadesImplementacion.getEvidenciaFotograficaFisico() != null) {

        String name = keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion") + actividadesImplementacion.getEvidenciaFotograficaFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, actividadesImplementacion.getEvidenciaFotografica());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadAvanceProcesoEvidenciaFotograficaContentPropertyActividadRealizada() {

    try {

      if (actividadesImplementacionEstadoRealizadaInformeAvance != null
              && actividadesImplementacionEstadoRealizadaInformeAvance.getEvidenciaFotografica() != null
              && actividadesImplementacionEstadoRealizadaInformeAvance.getEvidenciaFotograficaFisico() != null) {

        String name = keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion") + actividadesImplementacionEstadoRealizadaInformeAvance.getEvidenciaFotograficaFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, actividadesImplementacionEstadoRealizadaInformeAvance.getEvidenciaFotografica());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);
    }
    return null;
  }

  /**
   *
   * @param tema
   * @param tipoInformacion
   * @return
   */
  public String seleccionTemaGeneral(Tema tema, String tipoInformacion) {

    if (tema == null) {

      return null;

    }

    try {
      //BUSCAMOS EL TEMA PROYECTO EN CASO EXISTA
      temaProyectoSeleccionado = iTemaProyectoLocal.getTemaProyectoPorTemaEinformeAvanceImplementacion(tema.getIdTema(), informeAvanceImplementacion.getIdInformeAvanceImplementacion());

      if (temaProyectoSeleccionado == null) {

        temaProyectoSeleccionado = new TemaProyecto();
        temaProyectoSeleccionado.setInformeAvanceImplementacion(informeAvanceImplementacion);
        temaProyectoSeleccionado.setTema(tema);
        temaProyectoSeleccionado.setUsuario(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

        if (tipoInformacion == null || tipoInformacion.trim().length() == 0) {

          if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)) {

            tipoInformacion = IConstantes.DESTINO_TEMA_CU_PR_15_TAB_6_INFORMACION_TENER_EN_CUENTA_AVANCE;

          } else if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

            tipoInformacion = IConstantes.DESTINO_TEMA_CU_PR_15_TAB_6_INFORMACION_TENER_EN_CUENTA_AVANCE_FINAL;

          }
        }

        temaProyectoSeleccionado.setTipoTabInformacionImpl(tipoInformacion);
      }

      return navigationFaces.redirectCuPr15_1_2_AvanceImplemenacion();

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15 ", e);

    }

    return null;
  }

  /**
   *
   * @return
   */
  public String regresar() {

    if (registraLlamdoCU == null) {
      return null;
    }

    if (registraLlamdoCU.equals(CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU.CU_PR_20)) {

      return cuPr20GestionImplementacionesVigentesAsignadas.initReturnCU();

    }

    if (registraLlamdoCU.equals(CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU.CU_PR_25)) {

      return navigationFaces.redirectCuPr25RevisarCompromisoJefeUnidad();

    }

    if (registraLlamdoCU.equals(CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU.CU_PR_24)) {

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad();

    }

    if (registraLlamdoCU.equals(CuPr15_1_2_AvanceImplemenacionFaces.ORIGEN_LLAMADO_CU.CU_PR_06)) {

      return navigationFaces.redirectCuPr06ConsultarDetalleProyectoInvestigacion();

    }

    return null;

  }

  /**
   *
   * @return @throws Exception
   */
  public boolean guardarDetalleInformacionGeneral() throws Exception {

    if (informeAvanceImplementacion.getFechaInicio() == null || informeAvanceImplementacion.getFechaFin() == null) {

      adicionaMensajeError("Las fecha del Periodo del informe se encuentra vacías.");
      return false;
    }

    if (DatesUtils.compareDate(informeAvanceImplementacion.getFechaInicio(), informeAvanceImplementacion.getFechaFin()) == 1) {

      adicionaMensajeError("La fecha fin del Periodo de Informe debe ser mayor a la fecha de inicio");
      return false;

    }

    //GUARDAMOS LOS DATOS BASICOS
    informeAvanceImplementacion = iInformeAvanceImplementacionLocal.saveOrUpdate(informeAvanceImplementacion);

    return true;
  }

  /**
   *
   * @param actionEvent
   */
  public void guardarInformacionGeneral(ActionEvent actionEvent) {

    try {

      if (!guardarDetalleInformacionGeneral()) {
        //ERRORES AL GUARDAR
        return;
      }

      //ACTUALIZAMOS EL COMPROMISO PROYECTO 
      actualizaCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      //GUARDO CORRECTAMENTE
      adicionaMensajeInformativo("Información almacenada correctamente.");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);

    }

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarInformeAvanceImpl(ActionEvent actionEvent) {

    try {

      //HACEMOS LAS VALIDACIONES ANTES DE ENVIAR            
      if (isCompromisoInformeAvance()) {

        //PESTAÑA: 2. Avances sobre el proceso         
        int contadorTemas = iTemaLocal.cuentaTemaTodos(IConstantes.DESTINO_TEMA_CU_PR_15_INFO_AVANCE_PROCESO);
        int contadorTemasImplementados = iTemaProyectoLocal.cuentaTemaProyectoPorInformeAvanceYtipoTemaTabInformacion(
                informeAvanceImplementacion.getIdInformeAvanceImplementacion(),
                IConstantes.DESTINO_TEMA_PR_15_TAB_2_AVANCE_SOBRE_PROCESO);

        //EL NUMEROD DE TEMA POR PROYECTO REGISTRADOS DEBE SER IGUAL AL NUMERO DE TEMAS HAY HABILITADOS
        if (contadorTemas != contadorTemasImplementados) {

          adicionaMensajeError("Debe ingresar información para el informe en \"Avances sobre el proceso\"");
          return;
        }

      } else if (isCompromisoInformeFinal()) {

        //PESTAÑA: 2. Resultados Obtenidos
        int contadorTemas = iTemaLocal.cuentaTemaTodos(IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_FINAL_PROCESO);
        int contadorTemasImplementados = iTemaProyectoLocal.cuentaTemaProyectoPorInformeAvanceYtipoTemaTabInformacion(
                informeAvanceImplementacion.getIdInformeAvanceImplementacion(),
                IConstantes.DESTINO_TEMA_PR_15_TAB_2_FINAL_RESULTADO_OBTENIDO);

        //EL NUMEROD DE TEMA POR PROYECTO REGISTRADOS DEBE SER IGUAL AL NUMERO DE TEMAS HAY HABILITADOS
        if (contadorTemas != contadorTemasImplementados) {

          adicionaMensajeError("Debe ingresar información para el informe en \"Avances sobre el proceso\"");
          return;
        }

      }

      if (listaActividadesPlanImplementacion == null || listaActividadesPlanImplementacion.getNumeroRegistro() == 0) {

        adicionaMensajeError("No existen actividades registradas para el plan de trabajo.");
        return;

      }
      for (ActividadesPlanImplementacion unaActividadesPlanImplementacion : listaActividadesPlanImplementacion) {

        //VERIFICAMOS QUE LAS ACTIVIDADES SE ENCUENTREN EN LOS SIGFUIENTES ESTADOS
        if (isCompromisoInformeAvance()) {

          if (unaActividadesPlanImplementacion.getEstado() != null
                  && (IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_REALIZADA.equals(unaActividadesPlanImplementacion.getEstado().getIdConstantes())
                  || IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_NO_CUMPLIDA.equals(unaActividadesPlanImplementacion.getEstado().getIdConstantes())
                  || IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_PARCIALMENTE_REALIZADA.equals(unaActividadesPlanImplementacion.getEstado().getIdConstantes()))) {

            continue;

          }
        } else if (isCompromisoInformeFinal()) {

          //SI LA ACTIVIDAD FUE REALIZADA EN EL INFORME SE AVANCE, 
          //ENTONCES NO SE EXIGE QUE EL USUARIO LA VUELVA A CALIFICAR LA ACTIVIDAD
          if (unaActividadesPlanImplementacion.getEstado() != null
                  && IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_REALIZADA.equals(unaActividadesPlanImplementacion.getEstado().getIdConstantes())) {

            continue;

          }
          //VERIFICAMOS QUE LA ACTIVIDAD SE ENCUENTRE CALIFICADA
          if (unaActividadesPlanImplementacion.getEstadoInformeFinal() != null
                  && (IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_REALIZADA.equals(unaActividadesPlanImplementacion.getEstadoInformeFinal().getIdConstantes())
                  || IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_NO_CUMPLIDA.equals(unaActividadesPlanImplementacion.getEstadoInformeFinal().getIdConstantes())
                  || IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_PARCIALMENTE_REALIZADA.equals(unaActividadesPlanImplementacion.getEstadoInformeFinal().getIdConstantes()))) {

            continue;

          }

        }
        //SI TIENE UN ESTADO DIFERENTE, SE DETIENE EL PROCESO
        adicionaMensajeError("La actividad: \"".concat(unaActividadesPlanImplementacion.getActividad().concat("\" no ha sido evaluada.")));
        return;
      }

      List<InvestigadorProyecto> listaInvestigadorProyectoActuales = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());
      for (InvestigadorProyecto unInvestigadorProyecto : listaInvestigadorProyectoActuales) {

        if (isCompromisoInformeAvance()) {

          if (unInvestigadorProyecto.getHorasDedicadasImplementacion() == null) {

            adicionaMensajeError("La información de Horas dedicadas en el periodo se encuentra incompleto.");
            return;
          }

        } else if (isCompromisoInformeFinal()) {

          if (unInvestigadorProyecto.getHorasDedicadasAvanceFinalImplementacion() == null) {

            adicionaMensajeError("La información de Horas dedicadas en el periodo se encuentra incompleto.");
            return;
          }

        } else {

          adicionaMensajeError("Compromiso informe invalido.");
          return;
        }

      }

      //INDICADORES
      for (IndicadoresPlanTrabajoImplementacion unIndicadoresPlanTrabajoImplementacion : listaIndicadoresPlanTrabajoOtros) {

        if (unIndicadoresPlanTrabajoImplementacion.getValorNumerador() == null
                || unIndicadoresPlanTrabajoImplementacion.getValorDenominador() == null) {

          adicionaMensajeError("El valor de los indicadores no se han registrado.");
          return;

        }

      }
      if (!guardarDetalleInformacionGeneral()) {
        //ERRORES AL GUARDAR
        return;
      }

      actualizaCompromisoImplementacion(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);

      navigationFaces.redirectFacesCuGenerico(cuPr20GestionImplementacionesVigentesAsignadas.initReturnCU());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);

    }
  }

  /**
   *
   * @throws Exception
   */
  private void actualizaCompromisoImplementacion(Long idEstadoCompromiso) throws Exception {

    compromisoImplementacionInformeAvanceOinformeFinal.setIdEstadoCompromisoImpl(new Constantes(idEstadoCompromiso));
    compromisoImplementacionInformeAvanceOinformeFinal.setResultadoRetroalimentacion(null);
    compromisoImplementacionInformeAvanceOinformeFinal.setResultadoRevisionVicin(null);
    compromisoImplementacionInformeAvanceOinformeFinal.setComentarioTemporal(null);
    iCompromisoImplementacionLocal.saveCompromisoImplementacion(compromisoImplementacionInformeAvanceOinformeFinal);

  }

  public boolean isAccesoSoloConsulta() {
    return accesoSoloConsulta;
  }

  public void setAccesoSoloConsulta(boolean accesoSoloConsulta) {
    this.accesoSoloConsulta = accesoSoloConsulta;
  }

  public ImplementacionesProyecto getImplementacionProyecto() {
    return implementacionProyecto;
  }

  public void setImplementacionProyecto(ImplementacionesProyecto implementacionProyecto) {
    this.implementacionProyecto = implementacionProyecto;
  }

  public ProyectoDTO getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(ProyectoDTO proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public String getLblTituloPagina() {
    return lblTituloPagina;
  }

  public void setLblTituloPagina(String lblTituloPagina) {
    this.lblTituloPagina = lblTituloPagina;
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

  public List<EjecutorNecesidad> getListaUnidadPolicialEjecutoras() {
    return listaUnidadPolicialEjecutoras;
  }

  public void setListaUnidadPolicialEjecutoras(List<EjecutorNecesidad> listaUnidadPolicialEjecutoras) {
    this.listaUnidadPolicialEjecutoras = listaUnidadPolicialEjecutoras;
  }

  public List<VistaFuncionario> getListaJefeFuncionariosSeleccionadoAprueba() {
    return listaJefeFuncionariosSeleccionadoAprueba;
  }

  public void setListaJefeFuncionariosSeleccionadoAprueba(List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba) {
    this.listaJefeFuncionariosSeleccionadoAprueba = listaJefeFuncionariosSeleccionadoAprueba;
  }

  public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
    return listaInvestigadoresProyecto;
  }

  public void setListaInvestigadoresProyecto(List<InvestigadorProyecto> listaInvestigadoresProyecto) {
    this.listaInvestigadoresProyecto = listaInvestigadoresProyecto;
  }

  public List<Object> getListaTemporal() {
    return null;
  }

  public List<InvestigadorProyecto> getListaPersonalImplementacion() {
    return listaPersonalImplementacion;
  }

  public void setListaPersonalImplementacion(List<InvestigadorProyecto> listaPersonalImplementacion) {
    this.listaPersonalImplementacion = listaPersonalImplementacion;
  }

  public boolean isMostrarEditorPlanteamiento() {
    return temaProyectoSeleccionado != null;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return temaProyectoSeleccionado != null && temaProyectoSeleccionado.getArchivoSoporte() != null && temaProyectoSeleccionado.getNombreArchivoFisico() != null;
  }

  public List<Tema> getListaTemasAvanceProceso() {
    return listaTemasAvanceProceso;
  }

  public void setListaTemasAvanceProceso(List<Tema> listaTemasAvanceProceso) {
    this.listaTemasAvanceProceso = listaTemasAvanceProceso;
  }

  public TemaProyecto getTemaProyectoSeleccionado() {
    return temaProyectoSeleccionado;
  }

  public void setTemaProyectoSeleccionado(TemaProyecto temaProyectoSeleccionado) {
    this.temaProyectoSeleccionado = temaProyectoSeleccionado;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public String getNombreArchivoPlanteamientoProyecto() {
    return nombreArchivoPlanteamientoProyecto;
  }

  public void setNombreArchivoPlanteamientoProyecto(String nombreArchivoPlanteamientoProyecto) {
    this.nombreArchivoPlanteamientoProyecto = nombreArchivoPlanteamientoProyecto;
  }

  public boolean isMostrarBtn(String btn) {

    if ("btnGuardarTemaAvanceProceso".equals(btn)) {
      return idTabSeleccionado == 1;
    }
    if ("btnGuardarInformacionGeneral".equals(btn)) {
      return idTabSeleccionado == 0;
    }
    if ("btnGuardarResultado".equals(btn)) {
      return idTabSeleccionado == 2;
    }
    if ("btnGuardarTiempoDedicado".equals(btn)) {
      return idTabSeleccionado == 3 && listaInvestigadorProyecto != null && !listaInvestigadorProyecto.isEmpty();
    }
    if ("btnGuardarTemaInformacionTenerCuenta".equals(btn)) {
      return idTabSeleccionado == 6;
    }

    return false;
  }

  public FileUploadEvent getEventArchivoSubido() {
    return eventArchivoSubido;
  }

  public void setEventArchivoSubido(FileUploadEvent eventArchivoSubido) {
    this.eventArchivoSubido = eventArchivoSubido;
  }

  public ListGenericDataModel<ActividadesPlanImplementacion> getListaActividadesPlanImplementacion() {
    return listaActividadesPlanImplementacion;
  }

  public void setListaActividadesPlanImplementacion(ListGenericDataModel<ActividadesPlanImplementacion> listaActividadesPlanImplementacion) {
    this.listaActividadesPlanImplementacion = listaActividadesPlanImplementacion;
  }

  public ActividadesPlanImplementacion getActividadesPlanImplementacionSeleccionado() {
    return actividadesPlanImplementacionSeleccionado;
  }

  public void setActividadesPlanImplementacionSeleccionado(ActividadesPlanImplementacion actividadesPlanImplementacionSeleccionado) {
    this.actividadesPlanImplementacionSeleccionado = actividadesPlanImplementacionSeleccionado;
  }

  public List<SelectItem> getListaEstadoActividadItem() {
    return listaEstadoActividadItem;
  }

  public void setListaEstadoActividadItem(List<SelectItem> listaEstadoActividadItem) {
    this.listaEstadoActividadItem = listaEstadoActividadItem;
  }

  public Long getIdEstadoActividadImplSeleccionado() {
    return idEstadoActividadImplSeleccionado;
  }

  public void setIdEstadoActividadImplSeleccionado(Long idEstadoActividadImplSeleccionado) {
    this.idEstadoActividadImplSeleccionado = idEstadoActividadImplSeleccionado;
  }

  /**
   *
   * @param tabActividad
   * @return
   */
  public boolean isMostrarTabViewActividad(String tabActividad) {

    if (tabActividad == null || actividadesPlanImplementacionSeleccionado == null) {
      return false;
    }

    if (tabActividad.equals("ACTIVIDAD_REALIZADA")) {
      return IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_REALIZADA.equals(idEstadoActividadImplSeleccionado);
    }

    if (tabActividad.equals("ACTIVIDAD_REALIZADA_PARCIALMENTE")) {
      return IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_PARCIALMENTE_REALIZADA.equals(idEstadoActividadImplSeleccionado);
    }

    if (tabActividad.equals("ACTIVIDAD_NO_CUMPLIDA")) {
      return IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_NO_CUMPLIDA.equals(idEstadoActividadImplSeleccionado);
    }

    return false;
  }

  public ActividadesImplementacion getActividadesImplementacion() {
    return actividadesImplementacion;
  }

  public void setActividadesImplementacion(ActividadesImplementacion actividadesImplementacion) {
    this.actividadesImplementacion = actividadesImplementacion;
  }

  public boolean isMostrarLinkDescargaArchivoSoporte() {
    return actividadesImplementacion != null && actividadesImplementacion.getArchivoSoporte() != null && actividadesImplementacion.getArchivoSoporteFisico() != null;
  }

  public boolean isMostrarLinkDescargaArchivoSoporteActividadRealizada() {
    return actividadesImplementacionEstadoRealizadaInformeAvance != null && actividadesImplementacionEstadoRealizadaInformeAvance.getArchivoSoporte() != null && actividadesImplementacionEstadoRealizadaInformeAvance.getArchivoSoporteFisico() != null;
  }

  public boolean isMostrarLinkDescargaEvidenciaFotografica() {
    return actividadesImplementacion != null && actividadesImplementacion.getEvidenciaFotografica() != null && actividadesImplementacion.getEvidenciaFotograficaFisico() != null;
  }

  public boolean isMostrarLinkDescargaEvidenciaFotograficaActividadRealizada() {
    return actividadesImplementacionEstadoRealizadaInformeAvance != null && actividadesImplementacionEstadoRealizadaInformeAvance.getEvidenciaFotografica() != null && actividadesImplementacionEstadoRealizadaInformeAvance.getEvidenciaFotograficaFisico() != null;
  }

  public List<InvestigadorProyecto> getListaInvestigadorProyecto() {
    return listaInvestigadorProyecto;
  }

  public void setListaInvestigadorProyecto(List<InvestigadorProyecto> listaInvestigadorProyecto) {
    this.listaInvestigadorProyecto = listaInvestigadorProyecto;
  }

  public ListGenericDataModel<IndicadoresPlanTrabajoImplementacion> getListaIndicadoresPlanTrabajoOtros() {
    return listaIndicadoresPlanTrabajoOtros;
  }

  public void setListaIndicadoresPlanTrabajoOtros(ListGenericDataModel<IndicadoresPlanTrabajoImplementacion> listaIndicadoresPlanTrabajoOtros) {
    this.listaIndicadoresPlanTrabajoOtros = listaIndicadoresPlanTrabajoOtros;
  }

  public IndicadoresPlanTrabajoImplementacion getIndicadoresPlanTrabajoSeleccionado() {
    return indicadoresPlanTrabajoSeleccionado;
  }

  public void setIndicadoresPlanTrabajoSeleccionado(IndicadoresPlanTrabajoImplementacion indicadoresPlanTrabajoSeleccionado) {
    this.indicadoresPlanTrabajoSeleccionado = indicadoresPlanTrabajoSeleccionado;
  }

  public boolean isMostrarBtnAgregarIndicadorOtro() {
    return indicadoresPlanTrabajoSeleccionado != null
            && indicadoresPlanTrabajoSeleccionado.getIdIndicadorPlanTrabajoImplementacion() != null;
  }

  public List<Tema> getListaTemasInformacionTenerEnCuenta() {
    return listaTemasInformacionTenerEnCuenta;
  }

  public void setListaTemasInformacionTenerEnCuenta(List<Tema> listaTemasInformacionTenerEnCuenta) {
    this.listaTemasInformacionTenerEnCuenta = listaTemasInformacionTenerEnCuenta;
  }

  public List<ActividadesImplementacion> getListaActividadesPlanImplementacionEvidenciaFotografica() {
    return listaActividadesPlanImplementacionEvidenciaFotografica;
  }

  public void setListaActividadesPlanImplementacionEvidenciaFotografica(List<ActividadesImplementacion> listaActividadesPlanImplementacionEvidenciaFotografica) {
    this.listaActividadesPlanImplementacionEvidenciaFotografica = listaActividadesPlanImplementacionEvidenciaFotografica;
  }

  public StreamedContent getImageEvidencia() {

    ByteArrayInputStream stream = null;
    try {

      if (getFacesContext() == null || getFacesContext().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
        return new DefaultStreamedContent();
      }

      String rutaImg = getFacesContext().getExternalContext().getRequestParameterMap().get("rutaImagenEvidencia");

      if (rutaImg == null) {
        return new DefaultStreamedContent();
      }

      File archivo = new File(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion").concat(rutaImg));
      if (!archivo.exists()) {
        return new DefaultStreamedContent();
      }

      String extension = rutaImg.substring(rutaImg.lastIndexOf('.') + 1);

      stream = new ByteArrayInputStream(FileUtils.readFileToByteArray(archivo));

      StreamedContent imagen = new DefaultStreamedContent(stream, "image/".concat(extension));

      return imagen;

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", e);
      return new DefaultStreamedContent();

    } finally {
      try {

        if (stream != null) {

          stream.close();

        }

      } catch (Exception ex) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-15", ex);
      }
    }
  }

  public boolean isCompromisoInformeFinal() {
    return compromisoImplementacionInformeAvanceOinformeFinal != null && compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL);
  }

  public boolean isCompromisoInformeAvance() {
    return compromisoImplementacionInformeAvanceOinformeFinal != null && compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE);
  }

  /**
   *
   * @return
   */
  public String getTipoTabInformacionImpl() {

    if (compromisoImplementacionInformeAvanceOinformeFinal == null) {
      return null;
    }
    if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)) {

      return IConstantes.DESTINO_TEMA_PR_15_TAB_2_AVANCE_SOBRE_PROCESO;

    } else if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

      return IConstantes.DESTINO_TEMA_PR_15_TAB_2_FINAL_RESULTADO_OBTENIDO;

    }

    return null;

  }

  public String getLabelTab2Informe() {

    if (compromisoImplementacionInformeAvanceOinformeFinal == null) {
      return "-";
    }
    if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)) {

      return "2. Avances sobre el proceso";

    } else if (compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

      return "2. Resultados obtenidos";

    }

    return "-";

  }

  /**
   *
   * @return
   */
  public boolean isSoloLecturaActividadesPlanImplementacion() {

    return compromisoImplementacionInformeAvanceOinformeFinal != null
            && compromisoImplementacionInformeAvanceOinformeFinal.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)
            && actividadesPlanImplementacionSeleccionado != null && actividadesPlanImplementacionSeleccionado.getEstado().getIdConstantes().equals(IConstantes.TIPO_ESTADO_INFORME_ACTIVIDAD_IMPL_REALIZADA);

  }

  public ActividadesImplementacion getActividadesImplementacionEstadoRealizadaInformeAvance() {
    return actividadesImplementacionEstadoRealizadaInformeAvance;
  }

  public void setActividadesImplementacionEstadoRealizadaInformeAvance(ActividadesImplementacion actividadesImplementacionEstadoRealizadaInformeAvance) {
    this.actividadesImplementacionEstadoRealizadaInformeAvance = actividadesImplementacionEstadoRealizadaInformeAvance;
  }

  public String getImportPresupuestoPage() {
    return importPresupuestoPage;
  }

  public void setImportPresupuestoPage(String importPresupuestoPage) {
    this.importPresupuestoPage = importPresupuestoPage;
  }

  /**
   *
   */
  public void ejecutarPresupuesto() {
    presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_EJECUTA_IMPL, implementacionProyecto.getIdImplementacionProy(), informeAvanceImplementacion.getIdInformeAvanceImplementacion());
  }

  public String irAvancePresupuestoImplementacion() {

    try {

      List<InvestigadorProyecto> listaInvestigadorProyectoActuales = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());
      for (InvestigadorProyecto unInvestigadorProyecto : listaInvestigadorProyectoActuales) {

        if (isCompromisoInformeAvance()) {

          if (unInvestigadorProyecto.getHorasDedicadasImplementacion() == null) {

            adicionaMensajeError("La información de Horas dedicadas en el periodo se encuentra incompleto.");
            return null;
          }

        } else if (isCompromisoInformeFinal()) {

          if (unInvestigadorProyecto.getHorasDedicadasAvanceFinalImplementacion() == null) {

            adicionaMensajeError("La información de Horas dedicadas en el periodo se encuentra incompleto.");
            return null;
          }

        } else {

          adicionaMensajeError("Compromiso informe invalido.");
          return null;
        }

      }

      this.importPresupuestoPage = null;

      String cu = CuPr14AvancePresupuestoProyecto.CU_PR_15;

      return cuPr14AvanceProyectoImplementacionFaces.initReturnCU(implementacionProyecto.getIdImplementacionProy(), compromisoImplementacionInformeAvanceOinformeFinal.getIdCompromisoImplementacion(), cu);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr7RegistrarAvanceInvestigacionFaces.class.getName()).log(Level.SEVERE, null, e);

    }

    return null;
  }

  public String irPresupuestoDefinidoImplementacion() {
    try {
      this.importPresupuestoPage = null;
      cuPr05RegistrarPresupuestoImplFaces.initProyectoDesdeCU15(implementacionProyecto.getIdImplementacionProy(), compromisoImplementacionInformeAvanceOinformeFinal.getIdCompromisoImplementacion());
      return "/pages/secured/cu_pr_05/registrarPresupuestoImpl.xhtml?faces-redirect=true";
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr7RegistrarAvanceInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarFormato() {

    try {

      HashMap mapa = new HashMap();

      mapa.put("p_id_proyecto", proyectoSeleccionado.getId().intValue());
      mapa.put("p_id_informe_avance", informeAvanceImplementacion.getIdInformeAvanceImplementacion().intValue());
      mapa.put("p_id_plan_trabajo_impl", planTrabajoImplementacion.getIdPlanTrabajo().intValue());

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, isCompromisoInformeAvance() ? "reporte12.jasper" : "reporte11.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, isCompromisoInformeAvance() ? "12_FORMULARIO-INF AVANCE IMPLEMENTACION.pdf" : "11_FORMULARIO INFORME FINAL IMPLEMENTACION");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }
}
