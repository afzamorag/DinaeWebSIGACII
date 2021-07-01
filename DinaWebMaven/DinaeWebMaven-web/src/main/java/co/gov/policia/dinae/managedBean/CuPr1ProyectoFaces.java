package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.AntiguedadDTO;
import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.HistorialEstadoProyectosMigradosDTO;
import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.dto.NivelFormacionFuncionarioDTO;
import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IAsesoriaProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IGradosValoresLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ITemaLocal;
import co.gov.policia.dinae.interfaces.ITemaProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresProyectoLocal;
import co.gov.policia.dinae.interfaces.IInstitucionesProyectoLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.ISemilleroInvestigacionLocal;
import co.gov.policia.dinae.interfaces.ISemilleroProyectoLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.EvaluadoresProyectoMigrados;
import co.gov.policia.dinae.modelo.FormacionInvestigador;
import co.gov.policia.dinae.modelo.GradosValores;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.Tema;
import co.gov.policia.dinae.modelo.TemaProyecto;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyecto;
import co.gov.policia.dinae.modelo.InstitucionesProyecto;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.SemilleroInvestigacion;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFormacionFuncionario;
import static co.gov.policia.dinae.util.JSFUtils.copiarArchivoRutaFisica;
import co.gov.policia.dinae.util.PresupuestoUtil;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
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
@javax.inject.Named(value = "cuPr1ProyectoFaces")
@javax.enterprise.context.SessionScoped
public class CuPr1ProyectoFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr19ConsultarProyectosVigentesAsignadosFaces cuPr19ConsultarProyectosVigentesAsignadosFaces;

  @javax.inject.Inject
  private CuCo2ParticipaConvocatoriasFaces cuCo2ParticipaConvocatoriasFaces;

  @javax.inject.Inject
  private CuValidarInformacionConvocatoriaYProyectoFaces cuValidarInformacionConvocatoriaYProyectoFaces;

  @javax.inject.Inject
  private CuPr05RegistrarPresupuestoFaces cuPr05RegistrarPresupuestoFaces;

  @Inject
  private IncludeInformacionInformeProyectoGenericoFaces includeInformacionInformeProyectoGenericoFaces;

  @EJB
  private IGradosValoresLocal iGradosValoresLocal;

  @EJB
  private ILineaLocal iLineaLocal;

  @EJB
  private IAreaCienciaTecnologiaLocal iAreaCienciaTecnologia;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;

  @EJB
  private ITemaLocal iTemaLocal;

  @EJB
  private ITemaProyectoLocal iTemaProyectoLocal;

  @EJB
  private IGrupoInvestigacionLocal iGrupoInvestigacionLocal;

  @EJB
  private IGrupoInvestigacionProyectoLocal iGrupoInvestigacionProyectoLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  @EJB
  private ISemilleroInvestigacionLocal iSemilleroInvestigacionLocal;

  @EJB
  private ISemilleroProyectoLocal iSemilleroProyectoLocal;

  @EJB
  private IInstitucionesProyectoLocal iInstitucionesProyectoLocal;

  @EJB
  private IIndicadoresProyectoLocal iIndicadoresProyectoLocal;

  @EJB
  private IInvestigadorLocal iInvestigadorLocal;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IAsesoriaProyectoLocal iAsesoriaProyectoLocal;

  private static final List<Long> listaRolesEnviarComprimiso = new ArrayList<Long>(1);

  static {
    listaRolesEnviarComprimiso.add(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);
  }

  private String identificacionFuncionario;
  private List<EjecutorNecesidad> listaUnidadPolicialEjecutoras;
  private Long areaCienciaTecnologiaSeleccionada;
  private Long idLineaSeleccionada;
  private Long idTipoVinculacionSeleccionado;
  private Long idTipoSemilleroSeleccionado;
  private List<SelectItem> listaTipoInvestigador;
  private List<Tema> listaTemas;
  private ListGenericDataModel<InvestigadorProyecto> listaInvestigadoresProyecto;
  private List<NivelFormacionFuncionarioDTO> listaNivelFormacionFuncionario;
  private VistaFuncionario vistaFuncionario;
  private InvestigadorProyecto investigadorProyecto;
  private InvestigadorProyecto investigadorProyectoEliminar;
  private InvestigadorProyecto investigadorProyectoEditar;
  private GrupoInvestigacionProyecto grupoInvestigacionProyectoEliminar;

  //TITULO DE LA PAGINA, YA ESTE PUEDE SER LLAMADO DESDE DISTINTOS LADOS
  //EL TITULO ES DIFERENTE DEL LADO DE ES LLAMADO
  //‘AGREGAR NUEVA PROPUESTA DE PROYECTO’ DESDE EL CASO DE USO CU-CO-02 
  //O LA OPCIÓN ‘ACTUALIZAR PROYECTO’ 
  //O ‘FORMULACIÓN DEL PROYECTO’ DEL CASO DE USO CU-PR-19.
  private String lblTitulo;

  private List<SelectItem> listaLineaItem;
  private List<SelectItem> listaAreaCienciaTecnologiaItem;
  private int idTabSeleccionado;
  private boolean mostrarBtnRegresar;
  private boolean mostrarFechaEstimadaInicioProyecto;
  private boolean mostrarFechaEstimadaFinProyecto;
  private boolean mostrarBtnEnviarProyectoPropuestaConvocatoria;
  private boolean llamadoDesdeCuPr19InstitucionaloConvocatoria;
  private Proyecto proyectoSeleccionado;
  private List<SelectItem> listaTipoVinculacion;
  private TemaProyecto temaProyectoSeleccionado;
  private FileUploadEvent eventArchivoSubido;
  private String nombreArchivoPlanteamientoProyecto;

  //GRUPO INVESTIGACION 
  private List<SelectItem> listaOtrosParticipantesGrupoInvestigacionItem;
  private List<SelectItem> listaOtrosParticipantesSemilleroInvestigaItem;
  private GrupoInvestigacion grupoInvestigacionSeleccionado;
  private Map<Long, GrupoInvestigacion> mapaGruposInvestigacion;

  //SEMILLERO PROYECTO
  private SemilleroProyecto semilleroProyectoSeleccionado;
  private Long idSemilleroProyectoSeleccionado;
  private List<SemilleroProyectoDTO> listaSemilleroProyectoDTO;
  private SemilleroProyectoDTO semilleroProyectoDTOEliminar;

  //UNIDAD POLICIAL
  private List<SelectItem> listaUnidadPolicialOtrosParticipantesItem;
  private Long idUnidadPolicialSeleccionado;
  private String aporteInvestigacionUnidadPolicial;
  private List<SemilleroProyectoDTO> listaUnidadPolicialSemilleroProyectoDTO;
  private SemilleroProyectoDTO unidadPolicialSemilleroProyectoDTOEliminar;

  //OTROS PARTICIPANTES
  private Long idOtraInstitucionParticipante;
  private List<SelectItem> listaOtrasInstitucionesParticipantesItem;
  private String aporteInvestigacionOtrosInstituciones;
  private String otraInstitucionOtrosInstituciones;
  private List<InstitucionesProyectoDTO> listaOtrosInstitucionesInstitucionesProyectoDTO;
  private InstitucionesProyectoDTO institucionesProyectoDTOSeleccionadoEliminar;

  //INDICADORES GENERALES
  private List<IndicadoresProyecto> listaIndicadoresProyectoGeneral;
  private List<IndicadoresProyecto> listaIndicadoresProyectoOtros;
  private IndicadoresProyecto indicadoresProyectoSeleccionado;
  private IndicadoresProyecto indicadoresProyectoEliminar;

  private List<AsesoriaProyectoDTO> listaAsesoriaProyectoDTO;

  private ORIGEN_LLAMADO_CU registraLlamdoCU;

  private Long idCompromisoProyecto;
  private String _importPresupuestoPage;

  private List<HistorialEstadoProyectosMigradosDTO> listaHistorialEstadoProyectosMigradosDTO;
  private List<EvaluadoresProyectoMigrados> listaEvaluadoresProyectoMigrados;

  /**
   *
   */
  private enum ORIGEN_LLAMADO_CU {

    CU_CO_02, CU_PR_19, CU_PR_06
  };

  private List<GrupoInvestigacionProyecto> listaGrupoInvestigacionProyecto;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  /**
   * Cuando es llamado este metodo es por que se deba consultar los datos del proyecto por tal motivo el idUniddadPolicial se toma del proyecto
   *
   * @param idProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_06(Long idProyecto) throws Exception {

    try {

      //SE CARGAN LAS PROPIEDADES PROYECTOS
      init();

      //REGISTRAMOS DESDE DONDE FUE INVOCADO EL CU
      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_CO_02;

      proyectoSeleccionado = iProyectoLocal.obtenerProyectoPorId(idProyecto);

      //PRIMERO VERIFICAMOS SI EL PROYECTO TIENE ASIGNADO CODIGO Y QUE EL CODIGO SEA DIFERENTE DE "MVIC" = MIGRADOS
      //PARA LOS PROYECTOS MIGRADOS, NO SE CALCULA 
      if (proyectoSeleccionado.getCodigoProyecto() != null
              && (!proyectoSeleccionado.getCodigoProyecto().startsWith("MVIC") || !proyectoSeleccionado.getCodigoProyecto().startsWith("MTG"))) {

        includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(proyectoSeleccionado);

      } else {

        includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyectosMigrados(proyectoSeleccionado);

      }

      listaTipoVinculacion = UtilidadesItem.getListaSel(
              iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_VINCULACION_PROYECTO),
              "idConstantes",
              "valor");

      //CARGAMOS LA LISTA DE AREAS
      listaAreaCienciaTecnologiaItem = UtilidadesItem.getListaSel(iAreaCienciaTecnologia.getAreaCienciaTecnologias(),
              "idAreaCienciaTecnologia",
              "nombre");

      //CARGAMOS LA LISTA DE AREAS
      listaTipoInvestigador = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_INVESTIGADOR_PROYECTO),
              "idConstantes",
              "valor");

      //LISTA DE UNIDADES EJECUTORAS
      listaUnidadPolicialEjecutoras = new ArrayList<EjecutorNecesidad>();

      //LISTA DE GRUPOS DE INVESTIGACION
      List<GrupoInvestigacion> listaGrupoInvestiga = iGrupoInvestigacionLocal.getListaGrupoInvestigacionTodos();
      listaOtrosParticipantesGrupoInvestigacionItem = UtilidadesItem.getListaSel(listaGrupoInvestiga,
              "idGrupoInvestigacion",
              "descripcion");

      //LISTA DE SEMILLEROS INVESTIGACION
      listaOtrosParticipantesSemilleroInvestigaItem = UtilidadesItem.getListaSel(
              iSemilleroInvestigacionLocal.getListaSemilleroInvestigacionPorUnidadPolicial(
                      proyectoSeleccionado.getUnidadPolicial().getIdUnidadPolicial()),
              "idSemillero",
              "nombre");

      //LISTA UNIDADES
      listaUnidadPolicialOtrosParticipantesItem = UtilidadesItem.getListaSel(
              iUnidadPolicialLocal.getUnidadPolicialExcepto(
                      proyectoSeleccionado.getUnidadPolicial().getIdUnidadPolicial()),
              "idUnidadPolicial",
              "siglaFisicaYnombreUnidad");

      //LISTA INSTITUCIONES PARTICIPANTES
      listaOtrasInstitucionesParticipantesItem = UtilidadesItem.getListaSel(
              iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO),
              "idConstantes",
              "valor");

      idLineaSeleccionada = proyectoSeleccionado.getLinea().getIdLinea();

      areaCienciaTecnologiaSeleccionada = proyectoSeleccionado.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia();

      listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(
              cargarListaInvestigadorProyecto(proyectoSeleccionado.getIdProyecto())
      );

      //CARGAMOS LA LISTA DE UNIDADES EJECUTORAS
      listaUnidadPolicialEjecutoras = iEjecutorNecesidadLocal.getEjecutorNecesidadPorProyecto(proyectoSeleccionado.getIdProyecto());

      mostrarBtnRegresar = true;

      lblTitulo = keyPropertiesFactory.value("cu_pr_1_lbl_registra_propuesta_convoca");

      //CARGAMOS LA LISTA DE TEMAS
      cargarListaTemas();

      mostrarFechaEstimadaFinProyecto = false;

      mostrarFechaEstimadaInicioProyecto = false;

      mostrarBtnEnviarProyectoPropuestaConvocatoria = false;

      semilleroProyectoSeleccionado = new SemilleroProyecto();

      indicadoresProyectoSeleccionado = new IndicadoresProyecto();

      //LISTA DE GRUPO INVESTIGACION PROYECTO
      cargarListaGrupoInvestigacionProyecto();

      //CARGA LISTA DE SEMILLERO PROYECTO
      cargarListaSemilleroProyecto();

      //CARGA LISTA DE UNIDADES POLICIAL PARTICIPANTES
      cargarListaUnidadPolicialParticipante();

      //CARGA LISTA DE OTRAS INSTITUCIONES
      cargarListaOtrasUnidadesParticipantes();

      //CARGA LISTA DE INDICADORES
      cargarListaIndicadoresProyectoGeneral();
      cargarListaIndicadoresProyectoOtros();

      cargarListaAsesoriaProyecto();

      if (isProyectoMigrado()) {

        listaHistorialEstadoProyectosMigradosDTO = iProyectoLocal.listaHistorialEstadoProyectosMigradosDTO(proyectoSeleccionado.getIdProyecto());
        listaEvaluadoresProyectoMigrados = iProyectoLocal.listaEvaluadoresProyectoMigrados(proyectoSeleccionado.getIdProyecto());

      }

      return navigationFaces.redirectCuPr1ProyectoFacesRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (initReturnCU_Desde_CU_CO_02) ", e);

    }

    return null;

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_19_Institucional(Long idProyecto, Long idCompromisoProyecto) throws Exception {

    String redirect = initReturnCU_Desde_CU_CO_02(iProyectoLocal.obtenerProyectoPorId(idProyecto));

    this.idCompromisoProyecto = idCompromisoProyecto;

    lblTitulo = keyPropertiesFactory.value("cu_pr_1_titulo_llamado_proyecto_institucional");

    //SE HABILITA EL BOTON DE ENVIAR
    mostrarBtnEnviarProyectoPropuestaConvocatoria = true;

    llamadoDesdeCuPr19InstitucionaloConvocatoria = true;

    this.registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_19;

    return redirect;
  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_19_Financiacion(Long idProyecto, Long idCompromisoProyecto) throws Exception {

    String redirect = initReturnCU_Desde_CU_CO_02(iProyectoLocal.obtenerProyectoPorId(idProyecto));

    this.idCompromisoProyecto = idCompromisoProyecto;

    lblTitulo = keyPropertiesFactory.value("cu_pr_1_titulo_llamado_proyecto_institucional");

    //SE HABILITA EL BOTON DE ENVIAR
    mostrarBtnEnviarProyectoPropuestaConvocatoria = true;

    llamadoDesdeCuPr19InstitucionaloConvocatoria = true;

    this.registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_19;

    return redirect;
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_19(Long idProyecto) throws Exception {

    String redirect = initReturnCU_Desde_CU_CO_02(iProyectoLocal.obtenerProyectoPorId(idProyecto));
    this.registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_19;

    return redirect;
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_19_Financiacion(Long idProyecto) throws Exception {

    String redirect = initReturnCU_Desde_CU_CO_02(iProyectoLocal.obtenerProyectoPorId(idProyecto));
    this.registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_19;

    lblTitulo = keyPropertiesFactory.value("cu_pr_1_titulo_llamado_proyecto_institucional");

    return redirect;
  }

  /**
   *
   * @param proyecto
   * @return
   */
  public String initReturnCU_Desde_CU_CO_02(Proyecto proyecto) {

    try {

      //SE CARGAN LAS PROPIEDADES PROYECTOS
      init();

      //REGISTRAMOS DESDE DONDE FUE INVOCADO EL CU
      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_CO_02;

      this.proyectoSeleccionado = proyecto;

      cargarDatoIniciaCUProyecto();

      if (proyecto.getLinea() != null) {

        idLineaSeleccionada = proyecto.getLinea().getIdLinea();
        areaCienciaTecnologiaSeleccionada = proyecto.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia();

        //CARGAMOS LA LISTA DE LINEA CON SUS RESPECTIVA AREA
        handleAreaCienciaTecnologiaSeleccionadaChange();
      }

      if (proyecto.getEstado() == null || proyecto.getEstado().getIdConstantes() == null) {

        //UNA NUEVA CONVOCATORIA
        proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION));

      }

      listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(
              cargarListaInvestigadorProyecto(proyecto.getIdProyecto())
      );

      //CARGAMOS LA LISTA DE UNIDADES EJECUTORAS
      listaUnidadPolicialEjecutoras = iEjecutorNecesidadLocal.getEjecutorNecesidadPorProyecto(proyecto.getIdProyecto());

      //SI NO TIENEN UNIDAD EJECUTORA, LE ASIGNA POR DEFECTOR LA UNIDAD DEL USUARIUO LOGUEADO, CON ROL RESPONSABLE
      if (listaUnidadPolicialEjecutoras.isEmpty()) {

        EjecutorNecesidad ejecutorNecesidad = new EjecutorNecesidad();
        ejecutorNecesidad.setProyecto(proyectoSeleccionado);

        ejecutorNecesidad.setRol(iConstantesLocal.getConstantesPorIdConstante(
                IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE));

        ejecutorNecesidad.setUnidadPolicial(iUnidadPolicialLocal.obtenerUnidadPolicialPorId(
                loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));

        listaUnidadPolicialEjecutoras.add(ejecutorNecesidad);

        proyectoSeleccionado.setEjecutorNecesidadList(listaUnidadPolicialEjecutoras);

      }

      mostrarBtnRegresar = true;

      lblTitulo = keyPropertiesFactory.value("cu_pr_1_lbl_registra_propuesta_convoca");

      //CARGAMOS LA LISTA DE TEMAS
      cargarListaTemas();

      mostrarFechaEstimadaFinProyecto = false;

      mostrarFechaEstimadaInicioProyecto = false;

      mostrarBtnEnviarProyectoPropuestaConvocatoria = false;

      semilleroProyectoSeleccionado = new SemilleroProyecto();

      indicadoresProyectoSeleccionado = new IndicadoresProyecto();

      //LISTA DE GRUPO INVESTIGACION PROYECTO
      cargarListaGrupoInvestigacionProyecto();

      //CARGA LISTA DE SEMILLERO PROYECTO
      cargarListaSemilleroProyecto();

      //CARGA LISTA DE UNIDADES POLICIAL PARTICIPANTES
      cargarListaUnidadPolicialParticipante();

      //CARGA LISTA DE OTRAS INSTITUCIONES
      cargarListaOtrasUnidadesParticipantes();

      //CARGA LISTA DE INDICADORES
      cargarListaIndicadoresProyectoGeneral();
      cargarListaIndicadoresProyectoOtros();

      return navigationFaces.redirectCuPr1ProyectoFacesRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (initReturnCU_Desde_CU_CO_02) ", e);

    }

    return null;

  }

  public String initReturnCU() {

    //SE CARGAN LAS PROPIEDADES PROYECTOS
    init();

    return navigationFaces.redirectCuPr1ProyectoFacesRedirect();

  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      llamadoDesdeCuPr19InstitucionaloConvocatoria = false;
      idCompromisoProyecto = null;
      investigadorProyectoEditar = null;
      indicadoresProyectoEliminar = null;
      indicadoresProyectoSeleccionado = null;
      listaIndicadoresProyectoOtros = null;
      listaIndicadoresProyectoGeneral = null;
      institucionesProyectoDTOSeleccionadoEliminar = null;
      listaOtrosInstitucionesInstitucionesProyectoDTO = null;
      aporteInvestigacionOtrosInstituciones = null;
      listaOtrasInstitucionesParticipantesItem = null;
      idOtraInstitucionParticipante = null;
      listaUnidadPolicialSemilleroProyectoDTO = null;
      aporteInvestigacionUnidadPolicial = null;
      listaUnidadPolicialOtrosParticipantesItem = null;
      idUnidadPolicialSeleccionado = null;
      semilleroProyectoDTOEliminar = null;
      listaSemilleroProyectoDTO = null;
      idSemilleroProyectoSeleccionado = null;
      semilleroProyectoSeleccionado = null;
      grupoInvestigacionProyectoEliminar = null;
      listaOtrosParticipantesSemilleroInvestigaItem = null;
      idTipoSemilleroSeleccionado = null;
      listaGrupoInvestigacionProyecto = null;
      mostrarBtnEnviarProyectoPropuestaConvocatoria = false;
      registraLlamdoCU = null;
      areaCienciaTecnologiaSeleccionada = null;
      idLineaSeleccionada = null;
      mapaGruposInvestigacion = null;
      listaOtrosParticipantesGrupoInvestigacionItem = null;
      grupoInvestigacionSeleccionado = null;
      nombreArchivoPlanteamientoProyecto = null;
      idTabSeleccionado = 0;
      investigadorProyectoEliminar = null;
      mostrarFechaEstimadaFinProyecto = false;
      mostrarFechaEstimadaInicioProyecto = false;
      eventArchivoSubido = null;
      temaProyectoSeleccionado = null;
      mostrarBtnRegresar = false;
      identificacionFuncionario = null;
      listaNivelFormacionFuncionario = new ArrayList<NivelFormacionFuncionarioDTO>();
      listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(new ArrayList<InvestigadorProyecto>());
      investigadorProyecto = new InvestigadorProyecto();
      vistaFuncionario = null;
      listaHistorialEstadoProyectosMigradosDTO = null;
      listaEvaluadoresProyectoMigrados = null;
      proyectoSeleccionado = new Proyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (init) ", e);
    }

  }

  /**
   * Método que realiza el proceso de modificación de proyecto
   */
  public void guardarProyecto() {

    try {

      Date fechaHoy = new Date();
      //VALIDACIONES
      if (mostrarFechaEstimadaInicioProyecto) {

        //FECHA INICIO DEBE SER MAYOR A LA FECHA DEL SISTEMA.
        if (co.gov.policia.dinae.util.DatesUtils.compareDate(proyectoSeleccionado.getFechaEstimadaInicio(), fechaHoy) == -1) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_1_lbl_err_fecha_inicio_mayor"));
          return;
        }

      }

      if (mostrarFechaEstimadaFinProyecto) {

        //FECHA FIN DEBE SER MAYOR A LA FECHA DEL SISTEMA.
        if (co.gov.policia.dinae.util.DatesUtils.compareDate(proyectoSeleccionado.getFechaEstimadaFinalizacion(), fechaHoy) == -1) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_1_lbl_err_fecha_fin_mayor"));
          return;
        }

      }

      if (mostrarFechaEstimadaInicioProyecto && mostrarFechaEstimadaFinProyecto) {

        //VALIDAMOS QUE LA FECHA FINAL NO SEA MENOR A LA FECHA INICIAL
        if (co.gov.policia.dinae.util.DatesUtils.compareDate(proyectoSeleccionado.getFechaEstimadaFinalizacion(), proyectoSeleccionado.getFechaEstimadaInicio()) == -1) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_1_lbl_err_fecha_fin_mayor_a_fecha_inicial"));
          return;
        }

      }

      proyectoSeleccionado.setLinea(new Linea(idLineaSeleccionada));

      //SOLO SE ACTUALIZA EL ESTADO ESTE ES UNA CONVOCATORIA
      //SI LA ENTIDAD PROYECTO TIENE COMO CODIGO_PROYECTO EL VALOR DE NULL 
      //ENTONCS ESTE TODAVIA ES UNA CONVOCATORIA
      if (proyectoSeleccionado.getCodigoProyecto() == null || proyectoSeleccionado.getCodigoProyecto().trim().length() == 0) {

        proyectoSeleccionado.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION));

      }

      UsuarioRol usuarioRol;
      if (proyectoSeleccionado.getCodigoProyecto() == null) {

        usuarioRol = new UsuarioRol(
                loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                        IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol());

      } else {

        usuarioRol = new UsuarioRol(
                loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                        IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol());

      }

      //USUARIO ROL QUE ACTUALIZA EL REGISTRO
      proyectoSeleccionado.setUsuarioRol(usuarioRol);

      boolean debeActualizarDatosCuPr19 = false;
      Long idCompromisoProyectoActualiza = null;
      //VERIFICAMOS, SI SE ESTA EJECUTANDO EL CU-PR-1 DESDE UN COMRPOMISO PROYECTO (CU-PR-19)
      if (ORIGEN_LLAMADO_CU.CU_PR_19.equals(registraLlamdoCU) && idCompromisoProyecto != null && llamadoDesdeCuPr19InstitucionaloConvocatoria) {

        //ACTUALIZAMOS EL COMPROMISO A ESTADO "En Elaboracion"
        CompromisoProyecto unCompromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);

        //REGLA PARA QUE QUE EL COMPROMISO SE ACTUALIZE
        if (unCompromisoProyecto.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)
                || unCompromisoProyecto.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION)
                || unCompromisoProyecto.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO)) {

          idCompromisoProyectoActualiza = idCompromisoProyecto;
          debeActualizarDatosCuPr19 = true;
        }

      }

      //EL COMPROMISO PROYECTO SE ACTUALIZA SOLO SI CUMPLE LA REGLA ANTERIOR
      proyectoSeleccionado = iProyectoLocal.guardarProyectoYCompromiso(proyectoSeleccionado, idCompromisoProyectoActualiza, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);

      //CREAMOS LOS INDICADORES PARA ESTE PROYECTO
      verificarExistenciaIndicadoresBaseYcrear();

      //CARGA LISTA DE INDICADORES
      cargarListaIndicadoresProyectoGeneral();

      if (debeActualizarDatosCuPr19) {

        //ACTUALIZAMOS LOS DATOS DE CU PR 19
        cuPr19ConsultarProyectosVigentesAsignadosFaces.initReturnCU();

      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_info_proyecto_fue_alamacena_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Agregar Nuevo Proyecto (guardarProyecto) ", e);
    }

  }

  /**
   * Metodo que carga los datos necesarios para iniciar un proyecto
   *
   * @throws Exception
   */
  private void cargarDatoIniciaCUProyecto() throws Exception {

    //CARGAMOS LA LISTA TIPO VINCULACION
    listaTipoVinculacion = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_VINCULACION_PROYECTO),
            "idConstantes",
            "valor");

    //CARGAMOS LA LISTA DE AREAS
    listaAreaCienciaTecnologiaItem = UtilidadesItem.getListaSel(iAreaCienciaTecnologia.getAreaCienciaTecnologias(),
            "idAreaCienciaTecnologia",
            "nombre");

    //CARGAMOS LA LISTA DE AREAS
    listaTipoInvestigador = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_INVESTIGADOR_PROYECTO),
            "idConstantes",
            "valor");

    lblTitulo = keyPropertiesFactory.value("cu_pr_1_lbl_registra_propuesta_convoca");

    //LISTA DE UNIDADES EJECUTORAS
    listaUnidadPolicialEjecutoras = new ArrayList<EjecutorNecesidad>();

    //LISTA DE GRUPOS DE INVESTIGACION
    List<GrupoInvestigacion> listaGrupoInvestiga = iGrupoInvestigacionLocal.getListaGrupoInvestigacionTodos();
    listaOtrosParticipantesGrupoInvestigacionItem = UtilidadesItem.getListaSel(listaGrupoInvestiga,
            "idGrupoInvestigacion",
            "descripcion");

    mapaGruposInvestigacion = new HashMap<Long, GrupoInvestigacion>();
    for (GrupoInvestigacion unGrupoInvestiga : listaGrupoInvestiga) {

      mapaGruposInvestigacion.put(unGrupoInvestiga.getIdGrupoInvestigacion(), unGrupoInvestiga);

    }

    //LISTA DE SEMILLEROS INVESTIGACION
    listaOtrosParticipantesSemilleroInvestigaItem = UtilidadesItem.getListaSel(
            iSemilleroInvestigacionLocal.getListaSemilleroInvestigacionPorUnidadPolicial(
                    loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()),
            "idSemillero",
            "nombre");

    //LISTA UNIDADES
    listaUnidadPolicialOtrosParticipantesItem = UtilidadesItem.getListaSel(
            iUnidadPolicialLocal.getUnidadPolicialExcepto(
                    loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()),
            "idUnidadPolicial",
            "siglaFisicaYnombreUnidad");

    //LISTA INSTITUCIONES PARTICIPANTES
    listaOtrasInstitucionesParticipantesItem = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO),
            "idConstantes",
            "valor");

    //VERIFICAMOS SI ESTE PROYECTO YA TIENE ADICIONADO LOS INDICADORES GENERALES
    verificarExistenciaIndicadoresBaseYcrear();

  }

  /**
   *
   */
  public void calcularValorHora() {

    try {

      if (investigadorProyecto == null) {
        return;
      }

      if (investigadorProyecto.getHorasDedicacion() == null) {
        investigadorProyecto.setCalculoTotalHora(BigDecimal.ZERO);
        return;
      }

      List<Constantes> listaDuracionProyecto = new ArrayList<>();

      if (proyectoSeleccionado.getPeriodo().getTipoPeriodo() == null) {

        //SE ESPERA SOLO UN VALOR
        listaDuracionProyecto = iConstantesLocal.getConstantesPorTipoPorCodigo(
                IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
                IConstantes.CODIGO_DURACION_PROYECTOS_INSTITUCIONALES);

      } else if (proyectoSeleccionado.getPeriodo().getTipoPeriodo().getIdConstantes().equals(
              IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION)) {

        //SE ESPERA SOLO UN VALOR
        listaDuracionProyecto = iConstantesLocal.getConstantesPorTipoPorCodigo(
                IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
                IConstantes.CODIGO_DURACION_PROYECTOS_DE_CONVOCATORIA);

      }

      if (listaDuracionProyecto == null || listaDuracionProyecto.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        return;
      }
      List<Constantes> listaDuracionCantidadSemana = new ArrayList<>();
      listaDuracionCantidadSemana = iConstantesLocal.getConstantesPorTipoPorCodigo(
              IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
              IConstantes.CODIGO_CANTIDAD_DE_SEMANAS_POR_MES);

      if (listaDuracionCantidadSemana == null || listaDuracionCantidadSemana.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        return;
      }

      double valor = Double.parseDouble(listaDuracionProyecto.get(0).getValor());
      double cantidadSemanaPorMes = Double.parseDouble(listaDuracionCantidadSemana.get(0).getValor());

      BigDecimal operacion = BigDecimal.valueOf((valor * cantidadSemanaPorMes * investigadorProyecto.getHorasDedicacion()));
      operacion = operacion.setScale(0, BigDecimal.ROUND_HALF_UP);
      investigadorProyecto.setCalculoTotalHora(operacion);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (calcularValorHora) ", e);

    }
  }

  /**
   *
   */
  public void eliminarIndicadorOtro() {

    try {

      if (indicadoresProyectoEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iIndicadoresProyectoLocal.eliminarIndicadoresProyecto(indicadoresProyectoEliminar.getIdIndicadorProyecto());

      //ACTUALIZAMOS LA LISTA
      cargarListaIndicadoresProyectoOtros();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_eliminar_indicador_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (eliminarGrupoInvestigacion) ", e);

    }

  }

  /**
   *
   */
  public void eliminarUnidadPolicialParticipante() {

    try {

      if (unidadPolicialSemilleroProyectoDTOEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iSemilleroProyectoLocal.eliminarSemilleroProyectoInvestigacion(unidadPolicialSemilleroProyectoDTOEliminar.getIdSemilleroProyecto());

      //ACTUALIZAMOS LA LISTA DE GRUPOS 
      cargarListaUnidadPolicialParticipante();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_2_lbl_infor_eliminar_unidad_policial_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (eliminarGrupoInvestigacion) ", e);

    }

  }

  /**
   *
   */
  public void eliminarOtraInstitucionParticipante() {

    try {

      if (institucionesProyectoDTOSeleccionadoEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iInstitucionesProyectoLocal.eliminarInstitucionInvestigacion(institucionesProyectoDTOSeleccionadoEliminar.getIdInstitucionesProyecto());

      //ACTUALIZAMOS LA LISTA DE GRUPOS 
      cargarListaOtrasUnidadesParticipantes();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_2_lbl_infor_eliminar_otra_intitucion_partic_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (eliminarOtraInstitucionParticipante) ", e);

    }

  }

  /**
   *
   */
  public void eliminarSemilleroInvestigacion() {

    try {

      if (semilleroProyectoDTOEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iSemilleroProyectoLocal.eliminarSemilleroProyectoInvestigacion(semilleroProyectoDTOEliminar.getIdSemilleroProyecto());

      //ACTUALIZAMOS LA LISTA DE GRUPOS 
      cargarListaSemilleroProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_2_lbl_infor_eliminar_semillero_investigacion_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (eliminarGrupoInvestigacion) ", e);

    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoGeneral() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaIndicadoresProyectoGeneral = new ArrayList<IndicadoresProyecto>();
      return;
    }

    listaIndicadoresProyectoGeneral = iIndicadoresProyectoLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            proyectoSeleccionado.getIdProyecto(),
            IConstantes.YES_Y,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaAsesoriaProyecto() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null || proyectoSeleccionado.getConcecutivoProyectoGrado() != null) {

      listaAsesoriaProyectoDTO = new ArrayList<AsesoriaProyectoDTO>();
      return;
    }
    listaAsesoriaProyectoDTO = iAsesoriaProyectoLocal.getListaAsesoriaProyectoDTOPorIdProyecto(proyectoSeleccionado.getIdProyecto());

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoOtros() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaIndicadoresProyectoOtros = new ArrayList<IndicadoresProyecto>();
      return;
    }

    listaIndicadoresProyectoOtros = iIndicadoresProyectoLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            proyectoSeleccionado.getIdProyecto(),
            IConstantes.NO_N,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaOtrasUnidadesParticipantes() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaOtrosInstitucionesInstitucionesProyectoDTO = new ArrayList<InstitucionesProyectoDTO>();
      return;
    }

    listaOtrosInstitucionesInstitucionesProyectoDTO = iInstitucionesProyectoLocal.getListaInstitucionesProyectoPorProyecto(
            proyectoSeleccionado.getIdProyecto());

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaUnidadPolicialParticipante() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaUnidadPolicialSemilleroProyectoDTO = new ArrayList<SemilleroProyectoDTO>();
      return;
    }

    listaUnidadPolicialSemilleroProyectoDTO = iSemilleroProyectoLocal.getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(
            proyectoSeleccionado.getIdProyecto());

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaSemilleroProyecto() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaSemilleroProyectoDTO = new ArrayList<SemilleroProyectoDTO>();
      return;
    }

    listaSemilleroProyectoDTO = iSemilleroProyectoLocal.getListaSemilleroProyectoDTOporProyecto(proyectoSeleccionado.getIdProyecto());

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaGrupoInvestigacionProyecto() throws Exception {

    listaGrupoInvestigacionProyecto = new ArrayList<GrupoInvestigacionProyecto>();

    if (proyectoSeleccionado != null && proyectoSeleccionado.getIdProyecto() != null) {

      listaGrupoInvestigacionProyecto = iGrupoInvestigacionProyectoLocal.getListaGrupoInvestigacionProyectoPorProyecto(proyectoSeleccionado.getIdProyecto());

    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws Exception
   */
  private List<InvestigadorProyecto> cargarListaInvestigadorProyecto(Long idProyecto) throws Exception {

    if (idProyecto == null) {

      return new ArrayList<InvestigadorProyecto>();

    }
    return iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorProyecto(idProyecto);

  }

  /**
   * Método que carga las lienas dependiendo del Aera Seleccionada
   */
  public void handleAreaCienciaTecnologiaSeleccionadaChange() {

    if (areaCienciaTecnologiaSeleccionada == null) {

      listaLineaItem = new ArrayList<SelectItem>();
      return;
    }

    try {

      listaLineaItem = UtilidadesItem.getListaSel(iLineaLocal.getLineasPorArea(areaCienciaTecnologiaSeleccionada), "idLinea", "nombre");

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-PR-1 Agregar Nuevo Proyecto (handleAreaCienciaTecnologiaSeleccionadaChange) ", e);
    }

  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;
    _importPresupuestoPage = null;
    if (event == null || event.getTab() == null) {
      return;
    }
    if ("idTabViewInformacionBasicoProyecto".equals(event.getTab().getId())) {

      idTabSeleccionado = 0;

    } else if ("idTabViewTalentoHumano".equals(event.getTab().getId())) {

      idTabSeleccionado = 1;

    } else if ("idTabViewPlanteamientoProyecto".equals(event.getTab().getId())) {

      idTabSeleccionado = 2;

    } else if ("idTabViewOtrosParticipantes".equals(event.getTab().getId())) {

      idTabSeleccionado = 3;

    } else if ("idTabViewIndicadores".equals(event.getTab().getId())) {

      idTabSeleccionado = 4;

    } else if ("idTabViewPresupuestoProyectoDetalleProyecto".equals(event.getTab().getId())) {

      idTabSeleccionado = 4;//DETALLE PREPUESTO PROYECTO            
      _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";
      presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL, proyectoSeleccionado.getIdProyecto(), null);

    } else if ("idTabViewInformeDetalleProyecto".equals(event.getTab().getId())) {

      idTabSeleccionado = 5;//DETALLE PREPUESTO PROYECTO

    } else if ("idTabViewVerIndicadores".equals(event.getTab().getId())) {

      if (proyectoSeleccionado.getCodigoProyecto() == null) {

        idTabSeleccionado = 5;//INDICADORES

      } else {
        idTabSeleccionado = 6;//INDICADORES
      }

    } else if ("idTabViewVerAsesoriasProyecto".equals(event.getTab().getId())) {

      idTabSeleccionado = 7;//ASESORIAS PROYECTO

    } else if ("idTabViewEvaluacionProyecto".equals(event.getTab().getId())) {

      if (isProyectoMigrado()) {
        idTabSeleccionado = 3;//EVALUACION PROYECTO
      } else {
        idTabSeleccionado = 8;//EVALUACION PROYECTO
      }

    } else if ("idTabViewHistorialProyectoMigrado".equals(event.getTab().getId())) {

      idTabSeleccionado = 4;//TAB DETALLE ESTADOS MIGRADOS

    }
  }

  public void handleOtrasInstitucionesOtrosParticipantesChange() {

  }

  /**
   *
   */
  public void handleGrupoInvestigacionOtrosParticipantesChange() {

    grupoInvestigacionSeleccionado = null;

    if (idTipoVinculacionSeleccionado == null) {
      return;
    }
    grupoInvestigacionSeleccionado = mapaGruposInvestigacion.get(idTipoVinculacionSeleccionado);

  }

  public void handleSemilleroInvestigacionOtrosParticipantesChange() {

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarCompromisoProyecto(ActionEvent actionEvent) {

    try {

      List<Long> listaProyectoValidar = new ArrayList<Long>(1);
      listaProyectoValidar.add(proyectoSeleccionado.getIdProyecto());

      //VALIDAMOS LA INFORMACION DE LAS PESTANIAS
      if (!cuValidarInformacionConvocatoriaYProyectoFaces.validarInformacionValidaConvocatoriaYproyecto(listaProyectoValidar)) {
        //LA VALIDACION HA FALLADO
        return;
      }

      //CONSULTAMOS EL COMPROMISO PROYECTO AL QUE VAMOS A PUBLICAR
      CompromisoProyecto unCompromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);
      unCompromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE));
      unCompromisoProyecto.setResultadoRevisionVicin(null);
      unCompromisoProyecto.setComentarioTemporal(null);
      unCompromisoProyecto.setResultadoRetroalimentacion(null);

      iCompromisoProyectoLocal.agregarCompromisoProyecto(unCompromisoProyecto);

      try {

        //proyectoSeleccionado.getUnidadPolicial().getIdUnidadPolicial()
        iMailSessionBean.enviarMail_ListaRoles(
                IConstantes.CU_PR_01_FORMULARIO_JEFE_UNIDAD,
                null,
                null,
                listaRolesEnviarComprimiso);

      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Registrar Avance Investigacion (enviarCompromiso) ", e);
      }

      //RETORNA AL CU DE DONDE FUE LLAMADO
      String retorno = regresar();

      if (retorno != null) {

        navigationFaces.redirectFacesCuGenerico(retorno);

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Registrar Avance Investigacion (enviarCompromiso) ", e);

    }
  }

  /**
   * Método que realiza el proceso de envío de proyecto
   *
   * @param actionEvent
   */
  public void enviarProyecto(ActionEvent actionEvent) {

    try {

      if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_co_3_lbl_msg_error_falta_info_enviar_infobasica"));
        return;
      }

      List<Long> listaProyectoValidar = new ArrayList<Long>(1);
      listaProyectoValidar.add(proyectoSeleccionado.getIdProyecto());

      //VALIDAMOS LA INFORMACION DE LAS PESTANIAS
      if (!cuValidarInformacionConvocatoriaYProyectoFaces.validarInformacionValidaConvocatoriaYproyecto(listaProyectoValidar)) {
        //LA VALIDACION HA FALLADO
        return;
      }

      //CAMBIA EL ESTADO DEL COMPROMISO A ‘EN REVISIÓN DEL JEFE’ 
      proyectoSeleccionado.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_REVISION_DE_JEFE_DE_LA_UNIDAD));

      //EL SISTEMA ALMACENA LOS DATOS INGRESADOS DEL PROYECTO DE INVESTIGACIÓN, 
      proyectoSeleccionado = iProyectoLocal.guardarProyecto(proyectoSeleccionado);

      //ENVÍA UN CORREO ELECTRÓNICO AL JEFE DE UNIDAD.
      try {

        iMailSessionBean.enviarMail_RolUnidadPolicial(
                IConstantes.CU_PR_01_FORMULARIO_JEFE_UNIDAD,
                null,
                null,
                IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
                loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Registrar Avance Investigacion (enviarProyecto) ", e);
      }

      //RECARGAMOS Y ACTUALIZAMOS
      cuCo2ParticipaConvocatoriasFaces.enviarPropuestaSeleccionConvocatoria(null);

      String retorno = regresar();

      if (retorno != null) {

        navigationFaces.redirectFacesCuGenerico(retorno);

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Agregar Nuevo Proyecto (enviarProyecto) ", e);

    }
  }

  /**
   *
   * @return
   */
  public String regresar() {

    if (CuPr1ProyectoFaces.ORIGEN_LLAMADO_CU.CU_PR_19.equals(registraLlamdoCU)) {

      return cuPr19ConsultarProyectosVigentesAsignadosFaces.initReturnCU();

    }

    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaTemas() throws Exception {

    //CONSULTAMOS LOS TEMAS A MOSTRAR
    listaTemas = iTemaLocal.getListaTemaTodos(IConstantes.DESTINO_TEMA_CU_PR_01_PROYECTO);
    if (proyectoSeleccionado.getIdProyecto() != null) {

      //CONSULTAMOS LOS TEMAS QUE TIENEN CARGADA INFORMACION
      //CON EL FIN DE MOSTRAR O NO LA IMAGEN DE: icono_chechk.png
      List<Long> listaIdTemasInfoCargada = iTemaProyectoLocal.getIDTemaProyectoPorProyecto(proyectoSeleccionado.getIdProyecto());

      //VERIFICAMOS QUE TEMAS YA CONTIENEN INFORMACION CARGADA
      for (Tema unTema : listaTemas) {
        for (Long unIDtema : listaIdTemasInfoCargada) {

          if (unIDtema.equals(unTema.getIdTema())) {

            unTema.setMostrarImagenCheckInformacionPlanteamientoProblema(true);
            //CONTINUAMOS CON EL SIGUIENTE TEMA
            break;
          }
        }
      }
    }

  }

  /**
   *
   */
  public void guardarTalentoHumanoInvetigadores() {

    try {

      //VALIDACION TAB "TALENTO HUMANO"
      if (listaInvestigadoresProyecto == null || listaInvestigadoresProyecto.getNumeroRegistro() == 0) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_3_lbl_error_no_han_registrado_investiga_proyecto"));
        return;
      }
      //ADICIONAMOS LA LISTA DE INVESTIGADORES PROYECTO
      Proyecto proyecto = iProyectoLocal.obtenerProyectoPorId(proyectoSeleccionado.getIdProyecto());

      //SOLO SE ACTUALIZA EL ESTADO ESTE ES UNA CONVOCATORIA
      //SI LA ENTIDAD PROYECTO TIENE COMO CODIGO_PROYECTO EL VALOR DE NULL 
      //ENTONCS ESTE TODAVIA ES UNA CONVOCATORIA
      if (proyecto.getCodigoProyecto() == null || proyecto.getCodigoProyecto().trim().length() == 0) {

        proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION));

      }

      List<InvestigadorProyecto> listaInvestPro = new ArrayList<InvestigadorProyecto>();
      if (listaInvestigadoresProyecto != null) {

        Iterator<InvestigadorProyecto> itera = listaInvestigadoresProyecto.iterator();
        while (itera.hasNext()) {

          listaInvestPro.add(itera.next());

        }

      }
      proyecto.setInvestigadoresProyectoList(listaInvestPro);

      proyectoSeleccionado = iProyectoLocal.guardarProyecto(proyecto);

      listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(
              cargarListaInvestigadorProyecto(proyectoSeleccionado.getIdProyecto()));

      investigadorProyecto = new InvestigadorProyecto();

      vistaFuncionario = null;

      identificacionFuncionario = null;

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_info_proyecto_fue_alamacena_investiga_talento_hum_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-2 Agregar Nuevo Proyecto (guardarTalentoHumanoInvetigadores) ", e);

    }
  }

  /**
   *
   * @throws Exception
   */
  private void crearIndicadoresProyecto() throws Exception {

    if (proyectoSeleccionado.getIdProyecto() == null) {
      //ESTE PROCESO ES NECESARIO QUE EL PROYECTO EXISTA COMO ENTIDAD
      return;
    }

    //LO CONSULTAMOS PARA TENER LA ULTIMA VERSION DEL OBJETO 
    List<ConstantesDTO> listaIndicadorConstante = iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_INDICADORES_GENERALES_PROYECTO);

    List<IndicadoresProyecto> indicadoresProyectoList = new ArrayList<IndicadoresProyecto>();

    Long idUsuarioRol;
    if (proyectoSeleccionado.getCodigoProyecto() == null) {

      idUsuarioRol = loginFaces.getPerfilUsuarioDTO()
              .getRolUsuarioPorIdRolDTO(IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL)
              .getIdUsuarioRol();

    } else {

      idUsuarioRol = loginFaces.getPerfilUsuarioDTO()
              .getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL)
              .getIdUsuarioRol();

    }

    for (ConstantesDTO constantesDTO : listaIndicadorConstante) {

      IndicadoresProyecto indicadoresProyecto = new IndicadoresProyecto();
      indicadoresProyecto.setProyecto(new Proyecto(proyectoSeleccionado.getIdProyecto()));
      indicadoresProyecto.setIndicadorBase(IConstantes.YES_Y);
      indicadoresProyecto.setNombreIndicador(constantesDTO.getCodigo().trim());
      indicadoresProyecto.setUsuarioRol(new UsuarioRol(idUsuarioRol));
      indicadoresProyecto.setFechaRegistro(new Date());
      String valores[] = constantesDTO.getValor().split("/");
      indicadoresProyecto.setNombreNumerador(valores[0].trim());
      indicadoresProyecto.setNombreDenominador(valores[1].trim());
      indicadoresProyecto.setCasoUso(IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

      indicadoresProyectoList.add(indicadoresProyecto);

    }

    iIndicadoresProyectoLocal.guardarListaIndicadoresProyecto(indicadoresProyectoList);

  }

  /**
   *
   * @throws Exception
   */
  private void verificarExistenciaIndicadoresBaseYcrear() throws Exception {

    if (proyectoSeleccionado.getIdProyecto() == null) {
      //EL PROYECTO ES NECESARIO PARA ESTE PROCESO
      return;
    }

    List<IndicadoresProyecto> listaIndicadores = iIndicadoresProyectoLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            proyectoSeleccionado.getIdProyecto(),
            IConstantes.YES_Y,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

    if (listaIndicadores.isEmpty()) {
      //NO EXISTEN INDICADORES CREADOS PARA ESTE PROYECTO
      //SE CREAN LOS INDICADORES
      crearIndicadoresProyecto();
    }

  }

  public void noSeleccionarInvestigadorPoyecto(UnselectEvent event) {
    investigadorProyectoEditar = null;
  }

  /**
   *
   * @param event
   */
  public void seleccionarInvestigadorPoyecto(SelectEvent event) {

    if (investigadorProyectoEditar == null) {
      //FINALIZA EL PROCESO
      return;
    }

    try {

      listaNivelFormacionFuncionario = new ArrayList<NivelFormacionFuncionarioDTO>();

      investigadorProyecto = iInvestigadorProyectoLocal.obtenerInvestigadorProyectoPorId(investigadorProyectoEditar.getIdInvestigadorProyecto());

      idTipoVinculacionSeleccionado = investigadorProyecto.getTipoVinculacion().getIdConstantes();

      //VERIFICAMOS DE DONDE FUE ENCONTRADA LA INFORMACION DEL INVESTIGADOR
      if (investigadorProyecto.getOrigenSiafOinvestigador().equals(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_SIAFT)) {

        //BUSCAMOS LA PERSONA EN EL SIAF
        vistaFuncionario = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(investigadorProyectoEditar.getIdentificacion());

        //CARGAMOS LAS FORMACIONES DE LA PERSONA DESDE LA VISTA
        List<VistaFormacionFuncionario> listaVistaFormacion = iVistaFuncionarioLocal.getListaVistaFormacionFuncionarioPorIdentificacion(investigadorProyectoEditar.getIdentificacion());
        cargarFormacionInvestigador(null, listaVistaFormacion);

      } else {

        //BUSCAMOS LA PERSONA EN INVESTIGADOR
        Investigador investigador = iInvestigadorLocal.getInvestigadorPorNumeroIdentificacion(investigadorProyectoEditar.getIdentificacion());

        vistaFuncionario = new VistaFuncionario();
        //SETEAMOS LA INFORMACION DE INVESTIGADOR EN VIST FUNCIONARIO
        setearCamposInvestigadorToVistaFuncionario(investigador);

        //CARGAMOS LAS FORMACIONES DE LA PERSONA DESDE INVESTIGADOR
        cargarFormacionInvestigador(investigador.getFormacionInvestigadorList(), null);

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-2 Agregar Nuevo Proyecto (guardarTalentoHumanoInvetigadores) ", e);

    }

  }

  /**
   *
   * @param investigador
   */
  private void setearCamposInvestigadorToVistaFuncionario(Investigador investigador) {

    vistaFuncionario.setIdentificacion(investigador.getNumeroIdentificacion());
    vistaFuncionario.setNombres(investigador.getNombres());
    vistaFuncionario.setApellidos(investigador.getApellidos());
    vistaFuncionario.setNombreCompleto(investigador.getNombres().concat(" ").concat(investigador.getApellidos()));
    vistaFuncionario.setCorreo(investigador.getCorreoElectronico());
    vistaFuncionario.setTelefono(investigador.getTelefono());
    vistaFuncionario.setDireccionEmpleado(investigador.getDireccion());
    vistaFuncionario.setInformacionSIATH(false);
    vistaFuncionario.setOrigenSIAToInvestigador(investigador.getTipoVinculacion().getValor());
    vistaFuncionario.setDepartamentoReside(investigador.getNombreDeptoResidencia());
    vistaFuncionario.setCiudadReside(investigador.getNombreCiudadResidencia());

  }

  /**
   *
   * @param formacionInvestigadorList
   */
  private void cargarFormacionInvestigador(List<FormacionInvestigador> formacionInvestigadorList, List<VistaFormacionFuncionario> listaVistaFormacionFuncionario) {

    listaNivelFormacionFuncionario = new ArrayList<NivelFormacionFuncionarioDTO>();

    if (formacionInvestigadorList != null) {

      for (FormacionInvestigador formacionInvestigador : formacionInvestigadorList) {

        listaNivelFormacionFuncionario.add(
                new NivelFormacionFuncionarioDTO(
                        formacionInvestigador.getTituloObtenido(),
                        formacionInvestigador.getNivelFormacion().getValor(),
                        formacionInvestigador.getAreaSaber().getValor(),
                        formacionInvestigador.getFechaFinalizacion()));

      }

    }

    if (listaVistaFormacionFuncionario != null) {

      for (VistaFormacionFuncionario vistaFormacionFuncionario : listaVistaFormacionFuncionario) {

        listaNivelFormacionFuncionario.add(
                new NivelFormacionFuncionarioDTO(
                        vistaFormacionFuncionario.getTitulo(),
                        vistaFormacionFuncionario.getNivelAcademico(),
                        null,
                        vistaFormacionFuncionario.getFechaTermino()));

      }

    }

  }

  /**
   * Método para buscar funcionarios en el sistema SIATH.
   *
   * @return
   */
  public String buscarFuncionario() {

    investigadorProyecto = new InvestigadorProyecto();
    vistaFuncionario = null;
    listaNivelFormacionFuncionario = new ArrayList<NivelFormacionFuncionarioDTO>();

    if (identificacionFuncionario == null || identificacionFuncionario.trim().length() == 0) {
      adicionaMensajeError("El número de documento esta vacio.");
      return null;
    }

    try {

      //VERIFICAMOS QUE EL FUNCIONARIO A AGREGAR NO EXISTA EN LA LISTA
      if (listaInvestigadoresProyecto != null) {
        for (InvestigadorProyecto unInvestigadoresProyecto : listaInvestigadoresProyecto) {
          if (unInvestigadoresProyecto.getIdentificacion().equals(identificacionFuncionario)) {
            adicionaMensajeError(keyPropertiesFactory.value("cu_pr_1_lbl_error_add_investigador_ya_existe"));
            return null;
          }
        }

      }

      vistaFuncionario = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacionFuncionario);

      if (vistaFuncionario == null) {

        //BUSCAMOS LA IDENTIFICACION EN LA LISTA DE FUNCIONARIOS
        Investigador investigador = iInvestigadorLocal.getInvestigadorPorNumeroIdentificacion(identificacionFuncionario);

        if (investigador == null) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_3_lbl_identificacion_no_encontrada"));
          vistaFuncionario = null;
          return null;

        } else {

          vistaFuncionario = new VistaFuncionario();
          //SETEAMOS LA INFORMACION DE INVESTIGADOR EN VIST FUNCIONARIO
          setearCamposInvestigadorToVistaFuncionario(investigador);

          //CARGAMOS LAS FORMACIONES DE LA PERSONA DESDE INVESTIGADOR
          cargarFormacionInvestigador(investigador.getFormacionInvestigadorList(), null);

          //EL USUARIO DEBE INGRESAR EL VALOR HORA
          investigadorProyecto.setValorHora(null);
          investigadorProyecto.setOrigenSiafOinvestigador(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_INVESTIGADOR);
        }
      } else {

        try {

          //CARGAMOS LAS FORMACIONES DE LA PERSONA DESDE LA VISTA
          List<VistaFormacionFuncionario> listaVistaFormacion = iVistaFuncionarioLocal.getListaVistaFormacionFuncionarioPorIdentificacion(identificacionFuncionario);
          cargarFormacionInvestigador(null, listaVistaFormacion);

          //VERIFICAMOS SI EL FUNCIONARIO TIIENE UNIDAD POLICIAL
          if (vistaFuncionario.getCodigoUnidadLaboral() != null && vistaFuncionario.getCodigoUnidadLaboral().trim().length() > 0) {

            Long idUnidadPolicial = Long.valueOf(vistaFuncionario.getCodigoUnidadLaboral().trim());
            UnidadPolicial unidadPolicial = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(idUnidadPolicial);
            investigadorProyecto.setUnidadPolicial(unidadPolicial);

          }

          //CALCULAMOS EL VALOR HORA
          if (vistaFuncionario.getGradoAlfabetico() == null || vistaFuncionario.getGradoAlfabetico().trim().length() == 0) {

            adicionaMensajeError(
                    keyPropertiesFactory.value(
                            "cu_pr_1_lbl_mensaje_error_no_existen_grado_alfabetico_anio",
                            new Object[]{vistaFuncionario.getGrado()}));

            vistaFuncionario = null;
            return null;

          }

          //CONSULTAMOS EL VALOR HORA DE LA PERSONA
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          Integer anio = calendar.get(Calendar.YEAR);

          GradosValores gradosValores = iGradosValoresLocal.getGradosValoresPorAnioYgrado(
                  anio,
                  vistaFuncionario.getGradoAlfabetico().trim());

          /*if (gradosValores == null) {

                        adicionaMensajeError(
                                keyPropertiesFactory.value(
                                        "cu_pr_1_lbl_mensaje_error_no_existen_grado_alfabetico_anio",
                                        new Object[]{String.valueOf(anio), vistaFuncionario.getGradoAlfabetico()}));

                        vistaFuncionario = null;
                        return null;
                    }*/
          if (gradosValores != null) {

            investigadorProyecto.setValorHora(gradosValores.getValorHora());

          }
          investigadorProyecto.setOrigenSiafOinvestigador(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_SIAFT);

          //ESTE PROCESO SOLO APLICA PARA LAS PERSONAS DEL SIGAC
          //CALCULAMOS LA ANTIGUEDAD
          AntiguedadDTO antiguedadDTO = new AntiguedadDTO(0L, 0L, 0L);
          if (vistaFuncionario.getIngresoInstitucion() != null) {

            antiguedadDTO = UtilJPA.calcularAntiguedadEntreDosfecha(vistaFuncionario.getIngresoInstitucion(), new Date());
          }
          vistaFuncionario.setAntiguedadDTO(antiguedadDTO);

        } catch (Exception e) {

          vistaFuncionario = null;
          investigadorProyecto.setValorHora(null);
          investigadorProyecto.setOrigenSiafOinvestigador(null);

          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Agregar Nuevo Proyecto (buscarFuncionario) ", e);

        }

      }
    } catch (Exception e) {

      vistaFuncionario = null;
      investigadorProyecto.setValorHora(null);
      investigadorProyecto.setOrigenSiafOinvestigador(null);

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-1 Agregar Nuevo Proyecto (buscarFuncionario) ", e);

    }

    return null;
  }

  /**
   *
   * @return
   */
  public boolean validarAgregarInactivarInvertigador() {

    if (vistaFuncionario == null || vistaFuncionario.getIdentificacion() == null) {

      adicionaMensajeError("Primero ingrese un documento de identidad válido");
      return false;

    }
    return true;
  }

  /**
   *
   * @param activoInactivo
   * @throws Exception
   */
  private void agregarInactivarInvestigador(Character activoInactivo) throws Exception {

    //SI ES NUEVO
    if (investigadorProyecto.getIdInvestigadorProyecto() == null) {

      investigadorProyecto.setActivo(activoInactivo);
      investigadorProyecto.setCargo(vistaFuncionario.getCargo());
      investigadorProyecto.setCorreo(vistaFuncionario.getCorreo());
      investigadorProyecto.setGrado(vistaFuncionario.getGrado());
      investigadorProyecto.setIdentificacion(vistaFuncionario.getIdentificacion());
      investigadorProyecto.setNombreCompleto(vistaFuncionario.getNombreCompleto());
      investigadorProyecto.setProyecto(proyectoSeleccionado);
      investigadorProyecto.setTelefono(vistaFuncionario.getTelefono());
      investigadorProyecto.setFechaRegistro(new Date());

    }

    //ACTUALIZAMOS EL VALOR EN ESPECIA O EN EFECTIVO
    investigadorProyecto.setValorEspecie(BigDecimal.ZERO);
    investigadorProyecto.setValorEfectivo(BigDecimal.ZERO);

    BigDecimal total = investigadorProyecto.getCalculoTotalHora().multiply(investigadorProyecto.getValorHora());

    if (investigadorProyecto.getOrigenSiafOinvestigador().equals(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_SIAFT)) {

      investigadorProyecto.setValorEspecie(total);

    } else if (investigadorProyecto.getOrigenSiafOinvestigador().equals(IConstantes.ORIGEN_INVESTIGADOR_PROYECTO_INVESTIGADOR)) {

      investigadorProyecto.setValorEfectivo(total);

    }

    investigadorProyecto.setTipoVinculacion(new Constantes(idTipoVinculacionSeleccionado));

    //GUARDAMOS LA INVESTIGACION
    iInvestigadorProyectoLocal.guardarInvestigadorProyecto(investigadorProyecto);

    //ACTUALIZAMOS EL ESTADO DEL PROYECTO
    Proyecto proyecto = iProyectoLocal.obtenerProyectoPorId(proyectoSeleccionado.getIdProyecto());

    //SOLO SE ACTUALIZA EL ESTADO ESTE ES UNA CONVOCATORIA
    //SI LA ENTIDAD PROYECTO TIENE COMO CODIGO_PROYECTO EL VALOR DE NULL 
    //ENTONCS ESTE TODAVIA ES UNA CONVOCATORIA
    if (proyectoSeleccionado.getCodigoProyecto() == null || proyectoSeleccionado.getCodigoProyecto().trim().length() == 0) {

      proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION));

    }

    proyectoSeleccionado = iProyectoLocal.guardarProyecto(proyecto);

    //RECARGAMOS LA LISTA DE PROYECTO
    listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(
            cargarListaInvestigadorProyecto(proyectoSeleccionado.getIdProyecto()));

    vistaFuncionario = null;
    investigadorProyecto = new InvestigadorProyecto();
    idTipoVinculacionSeleccionado = null;
    identificacionFuncionario = null;

    adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_info_proyecto_fue_alamacena_investiga_talento_hum_ok"));

  }

  public void cancelarModificacion() {

    vistaFuncionario = null;
    investigadorProyecto = new InvestigadorProyecto();
    listaNivelFormacionFuncionario = new ArrayList<NivelFormacionFuncionarioDTO>();
    identificacionFuncionario = null;

  }

  /**
   *
   */
  public void agregarInvestigador() {

    try {

      if (!validarAgregarInactivarInvertigador()) {
        return;
      }

      //SI ES INVESTIGADOR ES NUEVO
      if (investigadorProyecto.getIdInvestigadorProyecto() == null) {

        agregarInactivarInvestigador(IConstantes.FUNCIONARIO_INVESTIGADOR_ACTIVO);

      } else {

        //DEJAMOS EL ESTADO QUE TIENE ACTUALMENTE
        agregarInactivarInvestigador(investigadorProyecto.getActivo());
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (agregarInvestigador) ", e);

    }

  }

  /**
   *
   */
  public void eliminarRegistroInvestigador() {

    try {

      if (investigadorProyectoEliminar == null) {
        return;
      }

      //ERIFICAMOS SI EL INVESTIGADOR YA EXISTE EN BD
      if (investigadorProyectoEliminar.getIdInvestigadorProyecto() != null) {

        //ELIMINAMOS EL INVESTIGADOR DE LA BD
        iInvestigadorProyectoLocal.eliminarInvestigadorProyecto(investigadorProyectoEliminar.getIdInvestigadorProyecto());
        //ACTUALIZAMOS LA LISTA DE INVESTIGADORES
        listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(
                cargarListaInvestigadorProyecto(proyectoSeleccionado.getIdProyecto()));

      } else {

        //LO ELIMINAMOS DE LA LISTA SOLAMENTE, YA NO TODAVIA NO SE HA GUARDADO EN BD
        Iterator<InvestigadorProyecto> itera = listaInvestigadoresProyecto.iterator();
        while (itera.hasNext()) {

          InvestigadorProyecto investigadorProyectoItera = itera.next();
          if (investigadorProyectoItera.equals(investigadorProyectoEliminar)) {

            itera.remove();

          }
        }
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (eliminarRegistroInvestigador) ", e);

    }

  }

  /**
   *
   * @param investigadorProyecto
   * @param estado
   */
  public void inactivarInvestigador(InvestigadorProyecto investigadorProyecto, Character estado) {

    try {

      investigadorProyecto.setActivo(estado);
      iInvestigadorProyectoLocal.guardarInvestigadorProyecto(investigadorProyecto);

      listaInvestigadoresProyecto = new ListGenericDataModel<InvestigadorProyecto>(
              cargarListaInvestigadorProyecto(proyectoSeleccionado.getIdProyecto()));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (inactivarInvestigador) ", e);

    }

  }

  /**
   *
   * @return
   */
  public String guardarIndicadoresProyecto() {

    if (indicadoresProyectoSeleccionado == null) {
      return null;
    }

    try {

      indicadoresProyectoSeleccionado.setProyecto(proyectoSeleccionado);
      indicadoresProyectoSeleccionado.setFechaRegistro(new Date());
      indicadoresProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol()));

      indicadoresProyectoSeleccionado.setIndicadorBase(IConstantes.NO_N);
      indicadoresProyectoSeleccionado.setCasoUso(IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

      iIndicadoresProyectoLocal.guardarIndicadoresProyecto(indicadoresProyectoSeleccionado);

      cargarListaIndicadoresProyectoOtros();

      indicadoresProyectoSeleccionado = new IndicadoresProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_add_nuevo_indicador_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (guardarIndicadoresProyecto) ", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String guardarTema() {

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

          directorioFinal = new File(keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte"));

        } catch (NullPointerException e) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte")));
          return null;
        }

        if (!directorioFinal.exists()) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte")));
          return null;
        }

        if (directorioFinal.isFile()) {
          adicionaMensajeError("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte")));
          return null;
        }

        if (!directorioFinal.canWrite()) {
          adicionaMensajeError("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte")));
          return null;
        }

        archivo = almacenarArchivoFisico();
      }

      temaProyectoSeleccionado.setArchivoSoporte(archivo[0] != null ? archivo[0] : temaProyectoSeleccionado.getArchivoSoporte());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      temaProyectoSeleccionado.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : temaProyectoSeleccionado.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

      //ACTUALIZAMOS
      temaProyectoSeleccionado = iTemaProyectoLocal.guardarTemaProyectoYactualizarEstadoPropuestaConvocatoria(
              temaProyectoSeleccionado, IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION);

      cargarListaTemas();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_4_lbl_info_almacenada_correctamente"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (guardarTema) ", e);

    }
    return null;

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
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico() {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      if (eventArchivoSubido != null) {

        String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = "PROPUESTA_CONVOCATORIA_PROYECTO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte"), eventArchivoSubido.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (almacenarArchivoFisico) ", e);
    }

    return null;

  }

  /**
   *
   * @param tema
   * @return
   */
  public String seleccionTema(Tema tema) {

    if (tema == null) {

      return null;

    }

    eventArchivoSubido = null;
    idTabSeleccionado = 2;

    try {
      //BUSCAMOS EL TEMA PROYECTO EN CASO EXISTA
      temaProyectoSeleccionado = iTemaProyectoLocal.getTemaProyectoPorTemaYproyecto(tema.getIdTema(), proyectoSeleccionado.getIdProyecto());

      if (temaProyectoSeleccionado == null) {

        temaProyectoSeleccionado = new TemaProyecto();
        temaProyectoSeleccionado.setProyecto(proyectoSeleccionado);
        temaProyectoSeleccionado.setTema(tema);

      }

      nombreArchivoPlanteamientoProyecto = temaProyectoSeleccionado.getArchivoSoporte();
      temaProyectoSeleccionado.setUsuario(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

      return navigationFaces.redirectCuPr1ProyectoFacesRedirect();

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (seleccionTema) ", e);

    }

    return null;
  }

  /**
   *
   */
  public void eliminarGrupoInvestigacion() {

    try {

      if (grupoInvestigacionProyectoEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iGrupoInvestigacionProyectoLocal.eliminarGrupoInvestigacionProyecto(grupoInvestigacionProyectoEliminar.getIdGrupoInvestigacionProyecto());

      //ACTUALIZAMOS LA LISTA DE GRUPOS 
      cargarListaGrupoInvestigacionProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_eliminar_grupo_investiga_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (eliminarGrupoInvestigacion) ", e);

    }

  }

  /**
   *
   * @param actionEvent
   */
  public void vincularSemilleroInvestigacionProyecto(ActionEvent actionEvent) {

    try {

      if (idSemilleroProyectoSeleccionado == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_semillero"));
        return;
      }

      semilleroProyectoSeleccionado.setSemilleroInvestigacion(new SemilleroInvestigacion(idSemilleroProyectoSeleccionado));
      semilleroProyectoSeleccionado.setProyecto(proyectoSeleccionado);

      //GUARDAMOS EL SEMILLERO
      iSemilleroProyectoLocal.guardarSemilleroProyecto(semilleroProyectoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (vincularSemilleroInvestigacionProyecto) ", e);

    }
  }

  /**
   *
   * @param actionEvent
   */
  public void vincularUnidadPolicialParticipante(ActionEvent actionEvent) {

    try {

      if (idUnidadPolicialSeleccionado == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_uniadad_policial_faltante"));
        return;
      }

      //VERIFICAMOS QUE NO EXISTA UN SEMILLERO INVESTIGACION ADICIONADO
      for (SemilleroProyectoDTO unSemilleroProyectoDTO : listaUnidadPolicialSemilleroProyectoDTO) {

        if (unSemilleroProyectoDTO.getIdUnidadPolicial().equals(idUnidadPolicialSeleccionado)) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_unidad_policial_ya_existe"));
          return;
        }
      }

      SemilleroProyecto semilleroProyecto = new SemilleroProyecto();
      semilleroProyecto.setProyecto(proyectoSeleccionado);
      semilleroProyecto.setUnidadPolicial(new UnidadPolicial(idUnidadPolicialSeleccionado));
      semilleroProyecto.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      semilleroProyecto.setAporteInvestigacion(aporteInvestigacionUnidadPolicial);

      iSemilleroProyectoLocal.guardarSemilleroProyecto(semilleroProyecto);

      cargarListaUnidadPolicialParticipante();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_2_lbl_info_adicionar_uniadad_policial_add_ok"));

      idUnidadPolicialSeleccionado = null;
      aporteInvestigacionUnidadPolicial = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (vincularSemilleroInvestigacion) ", e);

    }
  }

  /**
   *
   * @param actionEvent
   */
  public void vincularOtrasInstitucionesParticipantes(ActionEvent actionEvent) {

    try {

      if (idOtraInstitucionParticipante == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_otras_intituciones_err_faltante"));
        return;
      }

      //VERIFICAMOS QUE NO EXISTA UN SEMILLERO INVESTIGACION ADICIONADO
      for (InstitucionesProyectoDTO unaInstitucionesProyectoDTO : listaOtrosInstitucionesInstitucionesProyectoDTO) {

        //SI ES OTRO, CONTINUAMOS CON EL SIGUIENTE REGISTRO
        if (IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO.equals(unaInstitucionesProyectoDTO.getIdInstitucion())) {
          continue;
        }

        if (unaInstitucionesProyectoDTO.getIdInstitucion().equals(idOtraInstitucionParticipante)) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_institu_participa_ya_existe"));
          return;
        }
      }

      InstitucionesProyecto institucionesProyecto = new InstitucionesProyecto();
      institucionesProyecto.setProyecto(proyectoSeleccionado);
      institucionesProyecto.setAporteInvestigicacion(aporteInvestigacionOtrosInstituciones);
      institucionesProyecto.setInstitucion(new Constantes(idOtraInstitucionParticipante));
      //USUARIO ROL QUE ACTUALIZA EL REGISTRO

      if (proyectoSeleccionado.getCodigoProyecto() == null) {

        institucionesProyecto.setUsuarioRol(new UsuarioRol(
                loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                        IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL)
                .getIdUsuarioRol()));

      } else {

        institucionesProyecto.setUsuarioRol(new UsuarioRol(
                loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                        IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL)
                .getIdUsuarioRol()));

      }

      if (IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO.equals(idOtraInstitucionParticipante)) {
        institucionesProyecto.setValorOtroTipo(otraInstitucionOtrosInstituciones);
      }

      iInstitucionesProyectoLocal.guardarInstitucionesProyecto(institucionesProyecto);

      cargarListaOtrasUnidadesParticipantes();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_2_lbl_info_adicionar_otra_institucion_add_ok"));

      idOtraInstitucionParticipante = null;
      aporteInvestigacionOtrosInstituciones = null;
      otraInstitucionOtrosInstituciones = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (vincularOtrasInstitucionesParticipantes) ", e);

    }
  }

  /**
   *
   * @param actionEvent
   */
  public void vincularSemilleroInvestigacion(ActionEvent actionEvent) {

    try {

      if (idTipoSemilleroSeleccionado == null || idTipoSemilleroSeleccionado.compareTo(-1L) == 0) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_semillo_faltante_investiga"));
        return;
      }

      //VERIFICAMOS QUE NO EXISTA UN SEMILLERO INVESTIGACION ADICIONADO
      for (SemilleroProyectoDTO unSemilleroProyectoDTO : listaSemilleroProyectoDTO) {

        if (unSemilleroProyectoDTO.getIdSemillero().equals(idTipoSemilleroSeleccionado)) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_semillero_investiga_ya_existe"));
          return;
        }
      }

      semilleroProyectoSeleccionado.setProyecto(proyectoSeleccionado);
      semilleroProyectoSeleccionado.setSemilleroInvestigacion(new SemilleroInvestigacion(idTipoSemilleroSeleccionado));
      semilleroProyectoSeleccionado.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());

      iSemilleroProyectoLocal.guardarSemilleroProyecto(semilleroProyectoSeleccionado);

      cargarListaSemilleroProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_2_lbl_info_adicionar_semillo_investiga_ok"));

      idTipoSemilleroSeleccionado = null;
      semilleroProyectoSeleccionado = new SemilleroProyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (vincularSemilleroInvestigacion) ", e);

    }
  }

  /**
   *
   * @param actionEvent
   */
  public void vincularGrupoInvestigacion(ActionEvent actionEvent) {

    try {

      if (idTipoVinculacionSeleccionado == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_grupo_investiga"));
        return;
      }

      //VERIFICAMOS QUE NO EXISTA UN GRUPO INVESTIGACION ADICIONADO
      for (GrupoInvestigacionProyecto unGrupoInvestigacionProyecto : listaGrupoInvestigacionProyecto) {

        if (unGrupoInvestigacionProyecto.getGrupoInvestigacion().getIdGrupoInvestigacion().equals(idTipoVinculacionSeleccionado)) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_2_lbl_error_msg_selecc_grupo_investiga_ya_existe"));
          return;
        }
      }

      GrupoInvestigacionProyecto grupoInvestigacionProyecto = new GrupoInvestigacionProyecto();
      grupoInvestigacionProyecto.setFechaVinculacion(new Date());
      grupoInvestigacionProyecto.setGrupoInvestigacion(new GrupoInvestigacion(idTipoVinculacionSeleccionado));
      grupoInvestigacionProyecto.setIdUsuarioRol(IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL);
      grupoInvestigacionProyecto.setIdentificacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      grupoInvestigacionProyecto.setProyecto(proyectoSeleccionado);
      grupoInvestigacionProyecto.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      //GUARDAMOS EL GRUPO
      iGrupoInvestigacionProyectoLocal.guardarGrupoInvestigacionProyecto(grupoInvestigacionProyecto);

      //ACTUALIZAMOS LA LISTA DE GRUPOS 
      cargarListaGrupoInvestigacionProyecto();

      idTipoVinculacionSeleccionado = null;
      grupoInvestigacionSeleccionado = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (vincularGrupoInvestigacion) ", e);

    }

  }

  @javax.inject.Inject
  private CuIv4ConsultarDetalleSemillero cuIv4ConsultarDetalleSemilleroFaces;

  public String irConsultaSemillero() {
    return cuIv4ConsultarDetalleSemilleroFaces.invocacionCuPr02(idTipoSemilleroSeleccionado);
  }

  public ListGenericDataModel<InvestigadorProyecto> getListaInvestigadoresProyecto() {
    return listaInvestigadoresProyecto;
  }

  public void setListaInvestigadoresProyecto(ListGenericDataModel<InvestigadorProyecto> listaInvestigadoresProyecto) {
    this.listaInvestigadoresProyecto = listaInvestigadoresProyecto;
  }

  public Long getAreaCienciaTecnologiaSeleccionada() {
    return areaCienciaTecnologiaSeleccionada;
  }

  public void setAreaCienciaTecnologiaSeleccionada(Long areaCienciaTecnologiaSeleccionada) {
    this.areaCienciaTecnologiaSeleccionada = areaCienciaTecnologiaSeleccionada;
  }

  public Long getIdLineaSeleccionada() {
    return idLineaSeleccionada;
  }

  public void setIdLineaSeleccionada(Long idLineaSeleccionada) {
    this.idLineaSeleccionada = idLineaSeleccionada;
  }

  public List<SelectItem> getListaLineaItem() {
    return listaLineaItem;
  }

  public void setListaLineaItem(List<SelectItem> listaLineaItem) {
    this.listaLineaItem = listaLineaItem;
  }

  public VistaFuncionario getVistaFuncionario() {
    return vistaFuncionario;
  }

  public void setVistaFuncionario(VistaFuncionario vistaFuncionario) {
    this.vistaFuncionario = vistaFuncionario;
  }

  public String getIdentificacionFuncionario() {
    return identificacionFuncionario;
  }

  public void setIdentificacionFuncionario(String identificacionFuncionario) {
    this.identificacionFuncionario = identificacionFuncionario;
  }

  public Long getIdTipoVinculacionSeleccionado() {
    return idTipoVinculacionSeleccionado;
  }

  public void setIdTipoVinculacionSeleccionado(Long idTipoVinculacionSeleccionado) {
    this.idTipoVinculacionSeleccionado = idTipoVinculacionSeleccionado;
  }

  public void setListaTipoVinculacion(List<SelectItem> listaTipoVinculacion) {
    this.listaTipoVinculacion = listaTipoVinculacion;
  }

  public List<SelectItem> getListaTipoVinculacion() {
    return listaTipoVinculacion;
  }

  public List<SelectItem> getListaTipoInvestigador() {
    return listaTipoInvestigador;
  }

  public void setListaTipoInvestigador(List<SelectItem> listaTipoInvestigador) {
    this.listaTipoInvestigador = listaTipoInvestigador;
  }

  public List<SelectItem> getListaAreaCienciaTecnologiaItem() {

    return listaAreaCienciaTecnologiaItem;
  }

  public void setListaAreaCienciaTecnologiaItem(List<SelectItem> listaAreaCienciaTecnologiaItem) {
    this.listaAreaCienciaTecnologiaItem = listaAreaCienciaTecnologiaItem;
  }

  public String getLblTitulo() {
    return lblTitulo;
  }

  public List<EjecutorNecesidad> getListaUnidadPolicialEjecutoras() {
    return listaUnidadPolicialEjecutoras;
  }

  public Proyecto getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public InvestigadorProyecto getInvestigadorProyecto() {
    return investigadorProyecto;
  }

  public void setInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
    this.investigadorProyecto = investigadorProyecto;
  }

  public List<NivelFormacionFuncionarioDTO> getListaNivelFormacionFuncionario() {
    return listaNivelFormacionFuncionario;
  }

  public void setListaNivelFormacionFuncionario(List<NivelFormacionFuncionarioDTO> listaNivelFormacionFuncionario) {
    this.listaNivelFormacionFuncionario = listaNivelFormacionFuncionario;
  }

  public boolean isEsNuevoProyecto() {
    return proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null;
  }

  public void regresarCuCo02() throws IOException {

    if (registraLlamdoCU == null) {
      return;
    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_CO_02)) {

      cuCo2ParticipaConvocatoriasFaces.enviarPropuestaSeleccionConvocatoria(null);

    } else if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_19)) {

      navigationFaces.redirectFacesCuPr19ConsultarProyectosVigentesAsignados();

    }
  }

  public boolean isMostrarBtnRegresar() {
    return mostrarBtnRegresar;
  }

  public List<Tema> getListaTemas() {
    return listaTemas;
  }

  public void setListaTemas(List<Tema> listaTemas) {
    this.listaTemas = listaTemas;
  }

  public TemaProyecto getTemaProyectoSeleccionado() {
    return temaProyectoSeleccionado;
  }

  public void setTemaProyectoSeleccionado(TemaProyecto temaProyectoSeleccionado) {
    this.temaProyectoSeleccionado = temaProyectoSeleccionado;
  }

  public boolean isMostrarEditorPlanteamiento() {
    return temaProyectoSeleccionado != null;
  }

  public boolean isMostrarFechaEstimadaInicioProyecto() {
    return mostrarFechaEstimadaInicioProyecto;
  }

  public boolean isMostrarFechaEstimadaFinProyecto() {
    return mostrarFechaEstimadaFinProyecto;
  }

  public boolean isMostrarActivaInactivarInvestigador(InvestigadorProyecto investigadorProyecto) {
    return investigadorProyecto != null && investigadorProyecto.getIdInvestigadorProyecto() != null;
  }

  public InvestigadorProyecto getInvestigadorProyectoEliminar() {
    return investigadorProyectoEliminar;
  }

  public void setInvestigadorProyectoEliminar(InvestigadorProyecto investigadorProyectoEliminar) {
    this.investigadorProyectoEliminar = investigadorProyectoEliminar;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (temaProyectoSeleccionado != null
              && temaProyectoSeleccionado.getArchivoSoporte() != null
              && temaProyectoSeleccionado.getNombreArchivoFisico() != null) {

        String name = keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte") + temaProyectoSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, temaProyectoSeleccionado.getArchivoSoporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (getDownloadContentProperty) ", e);
    }
    return null;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return temaProyectoSeleccionado != null && temaProyectoSeleccionado.getArchivoSoporte() != null && temaProyectoSeleccionado.getNombreArchivoFisico() != null;
  }

  public String getNombreArchivoPlanteamientoProyecto() {
    return nombreArchivoPlanteamientoProyecto;
  }

  public void setNombreArchivoPlanteamientoProyecto(String nombreArchivoPlanteamientoProyecto) {
    this.nombreArchivoPlanteamientoProyecto = nombreArchivoPlanteamientoProyecto;
  }

  public List<SelectItem> getListaOtrosParticipantesGrupoInvestigacionItem() {
    return listaOtrosParticipantesGrupoInvestigacionItem;
  }

  public void setListaOtrosParticipantesGrupoInvestigacionItem(List<SelectItem> listaOtrosParticipantesGrupoInvestigacionItem) {
    this.listaOtrosParticipantesGrupoInvestigacionItem = listaOtrosParticipantesGrupoInvestigacionItem;
  }

  public GrupoInvestigacion getGrupoInvestigacionSeleccionado() {
    return grupoInvestigacionSeleccionado;
  }

  public void setGrupoInvestigacionSeleccionado(GrupoInvestigacion grupoInvestigacionSeleccionado) {
    this.grupoInvestigacionSeleccionado = grupoInvestigacionSeleccionado;
  }

  public boolean isMostrarDetalleGrupoInvestigacionOtrosParticipantes() {
    return grupoInvestigacionSeleccionado != null;
  }

  public boolean isMostrarSemilleroInvestigacionOtrosParticipantes() {
    return idSemilleroProyectoSeleccionado != null;
  }

  public boolean isMostrarUnidadPolicialParticipanteOtrosParticipantes() {
    return idUnidadPolicialSeleccionado != null;
  }

  public boolean isMostrarBtnEnviar() {

    return mostrarBtnEnviarProyectoPropuestaConvocatoria && proyectoSeleccionado != null && proyectoSeleccionado.getIdProyecto() != null;
  }

  public List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyecto() {
    return listaGrupoInvestigacionProyecto;
  }

  public void setListaGrupoInvestigacionProyecto(List<GrupoInvestigacionProyecto> listaGrupoInvestigacionProyecto) {
    this.listaGrupoInvestigacionProyecto = listaGrupoInvestigacionProyecto;
  }

  public Long getIdTipoSemilleroSeleccionado() {
    return idTipoSemilleroSeleccionado;
  }

  public void setIdTipoSemilleroSeleccionado(Long idTipoSemilleroSeleccionado) {
    this.idTipoSemilleroSeleccionado = idTipoSemilleroSeleccionado;
  }

  public List<SelectItem> getListaOtrosParticipantesSemilleroInvestigaItem() {
    return listaOtrosParticipantesSemilleroInvestigaItem;
  }

  public void setListaOtrosParticipantesSemilleroInvestigaItem(List<SelectItem> listaOtrosParticipantesSemilleroInvestigaItem) {
    this.listaOtrosParticipantesSemilleroInvestigaItem = listaOtrosParticipantesSemilleroInvestigaItem;
  }

  public GrupoInvestigacionProyecto getGrupoInvestigacionProyectoEliminar() {
    return grupoInvestigacionProyectoEliminar;
  }

  public void setGrupoInvestigacionProyectoEliminar(GrupoInvestigacionProyecto grupoInvestigacionProyectoEliminar) {
    this.grupoInvestigacionProyectoEliminar = grupoInvestigacionProyectoEliminar;
  }

  public Long getIdSemilleroProyectoSeleccionado() {
    return idSemilleroProyectoSeleccionado;
  }

  public void setIdSemilleroProyectoSeleccionado(Long idSemilleroProyectoSeleccionado) {
    this.idSemilleroProyectoSeleccionado = idSemilleroProyectoSeleccionado;
  }

  public SemilleroProyecto getSemilleroProyectoSeleccionado() {
    return semilleroProyectoSeleccionado;
  }

  public void setSemilleroProyectoSeleccionado(SemilleroProyecto semilleroProyectoSeleccionado) {
    this.semilleroProyectoSeleccionado = semilleroProyectoSeleccionado;
  }

  public List<SemilleroProyectoDTO> getListaSemilleroProyectoDTO() {
    return listaSemilleroProyectoDTO;
  }

  public void setListaSemilleroProyectoDTO(List<SemilleroProyectoDTO> listaSemilleroProyectoDTO) {
    this.listaSemilleroProyectoDTO = listaSemilleroProyectoDTO;
  }

  public SemilleroProyectoDTO getSemilleroProyectoDTOEliminar() {
    return semilleroProyectoDTOEliminar;
  }

  public void setSemilleroProyectoDTOEliminar(SemilleroProyectoDTO semilleroProyectoDTOEliminar) {
    this.semilleroProyectoDTOEliminar = semilleroProyectoDTOEliminar;
  }

  public boolean isMostrarCommankLinkVincularSemillero() {
    return idTipoSemilleroSeleccionado != null;
  }

  public Long getIdUnidadPolicialSeleccionado() {
    return idUnidadPolicialSeleccionado;
  }

  public void setIdUnidadPolicialSeleccionado(Long idUnidadPolicialSeleccionado) {
    this.idUnidadPolicialSeleccionado = idUnidadPolicialSeleccionado;
  }

  public List<SelectItem> getListaUnidadPolicialOtrosParticipantesItem() {
    return listaUnidadPolicialOtrosParticipantesItem;
  }

  public void setListaUnidadPolicialOtrosParticipantesItem(List<SelectItem> listaUnidadPolicialOtrosParticipantesItem) {
    this.listaUnidadPolicialOtrosParticipantesItem = listaUnidadPolicialOtrosParticipantesItem;
  }

  public String getAporteInvestigacionUnidadPolicial() {
    return aporteInvestigacionUnidadPolicial;
  }

  public void setAporteInvestigacionUnidadPolicial(String aporteInvestigacionUnidadPolicial) {
    this.aporteInvestigacionUnidadPolicial = aporteInvestigacionUnidadPolicial;
  }

  public List<SemilleroProyectoDTO> getListaUnidadPolicialSemilleroProyectoDTO() {
    return listaUnidadPolicialSemilleroProyectoDTO;
  }

  public void setListaUnidadPolicialSemilleroProyectoDTO(List<SemilleroProyectoDTO> listaUnidadPolicialSemilleroProyectoDTO) {
    this.listaUnidadPolicialSemilleroProyectoDTO = listaUnidadPolicialSemilleroProyectoDTO;
  }

  public SemilleroProyectoDTO getUnidadPolicialSemilleroProyectoDTOEliminar() {
    return unidadPolicialSemilleroProyectoDTOEliminar;
  }

  public void setUnidadPolicialSemilleroProyectoDTOEliminar(SemilleroProyectoDTO unidadPolicialSemilleroProyectoDTOEliminar) {
    this.unidadPolicialSemilleroProyectoDTOEliminar = unidadPolicialSemilleroProyectoDTOEliminar;
  }

  public Long getIdOtraInstitucionParticipante() {
    return idOtraInstitucionParticipante;
  }

  public void setIdOtraInstitucionParticipante(Long idOtraInstitucionParticipante) {
    this.idOtraInstitucionParticipante = idOtraInstitucionParticipante;
  }

  public List<SelectItem> getListaOtrasInstitucionesParticipantesItem() {
    return listaOtrasInstitucionesParticipantesItem;
  }

  public void setListaOtrasInstitucionesParticipantesItem(List<SelectItem> listaOtrasInstitucionesParticipantesItem) {
    this.listaOtrasInstitucionesParticipantesItem = listaOtrasInstitucionesParticipantesItem;
  }

  public String getAporteInvestigacionOtrosInstituciones() {
    return aporteInvestigacionOtrosInstituciones;
  }

  public void setAporteInvestigacionOtrosInstituciones(String aporteInvestigacionOtrosInstituciones) {
    this.aporteInvestigacionOtrosInstituciones = aporteInvestigacionOtrosInstituciones;
  }

  public List<InstitucionesProyectoDTO> getListaOtrosInstitucionesInstitucionesProyectoDTO() {
    return listaOtrosInstitucionesInstitucionesProyectoDTO;
  }

  public void setListaOtrosInstitucionesInstitucionesProyectoDTO(List<InstitucionesProyectoDTO> listaOtrosInstitucionesInstitucionesProyectoDTO) {
    this.listaOtrosInstitucionesInstitucionesProyectoDTO = listaOtrosInstitucionesInstitucionesProyectoDTO;
  }

  public String getOtraInstitucionOtrosInstituciones() {
    return otraInstitucionOtrosInstituciones;
  }

  public void setOtraInstitucionOtrosInstituciones(String otraInstitucionOtrosInstituciones) {
    this.otraInstitucionOtrosInstituciones = otraInstitucionOtrosInstituciones;
  }

  public InstitucionesProyectoDTO getInstitucionesProyectoDTOSeleccionadoEliminar() {
    return institucionesProyectoDTOSeleccionadoEliminar;
  }

  public void setInstitucionesProyectoDTOSeleccionadoEliminar(InstitucionesProyectoDTO institucionesProyectoDTOSeleccionadoEliminar) {
    this.institucionesProyectoDTOSeleccionadoEliminar = institucionesProyectoDTOSeleccionadoEliminar;
  }

  public boolean isMostrarAgregarOtroInstitucion() {
    return idOtraInstitucionParticipante != null && idOtraInstitucionParticipante.equals(IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO);
  }

  public boolean isProyectoNoMigrado() {
    //PRIMERO VERIFICAMOS SI EL PROYECTO TIENE ASIGNADO CODIGO Y QUE EL CODIGO SEA DIFERENTE DE "MVIC" = MIGRADOS
    //PARA LOS PROYECTOS MIGRADOS, NO SE CALCULA 
    return proyectoSeleccionado != null
            && proyectoSeleccionado.getCodigoProyecto() != null
            && !(proyectoSeleccionado.getCodigoProyecto().startsWith("MVIC") || proyectoSeleccionado.getCodigoProyecto().startsWith("MTG"));
  }

  public boolean isProyectoMigrado() {
    //PRIMERO VERIFICAMOS SI EL PROYECTO TIENE ASIGNADO CODIGO Y QUE EL CODIGO SEA DIFERENTE DE "MVIC" = MIGRADOS
    //PARA LOS PROYECTOS MIGRADOS, NO SE CALCULA 
    return proyectoSeleccionado != null
            && proyectoSeleccionado.getCodigoProyecto() != null
            && (proyectoSeleccionado.getCodigoProyecto().startsWith("MVIC") || proyectoSeleccionado.getCodigoProyecto().startsWith("MTG"));
  }

  public String consultarPresupuesto() {

    cuPr05RegistrarPresupuestoFaces.initProyecto(proyectoSeleccionado.getIdProyecto(), CuPr05RegistrarPresupuestoFaces.CU_PR_06);
    return "/pages/secured/cu_pr_05/registrarPresupuesto.xhtml?faces-redirect=true";
  }

  public String registrarPresupuesto() {

    cuPr05RegistrarPresupuestoFaces.initProyecto(proyectoSeleccionado.getIdProyecto(), CuPr05RegistrarPresupuestoFaces.CU_PR_1);
    return "/pages/secured/cu_pr_05/registrarPresupuesto.xhtml?faces-redirect=true";
  }

  public List<IndicadoresProyecto> getListaIndicadoresProyectoGeneral() {
    return listaIndicadoresProyectoGeneral;
  }

  public void setListaIndicadoresProyectoGeneral(List<IndicadoresProyecto> listaIndicadoresProyectoGeneral) {
    this.listaIndicadoresProyectoGeneral = listaIndicadoresProyectoGeneral;
  }

  public List<IndicadoresProyecto> getListaIndicadoresProyectoOtros() {
    return listaIndicadoresProyectoOtros;
  }

  public void setListaIndicadoresProyectoOtros(List<IndicadoresProyecto> listaIndicadoresProyectoOtros) {
    this.listaIndicadoresProyectoOtros = listaIndicadoresProyectoOtros;
  }

  public IndicadoresProyecto getIndicadoresProyectoSeleccionado() {
    return indicadoresProyectoSeleccionado;
  }

  public void setIndicadoresProyectoSeleccionado(IndicadoresProyecto indicadoresProyectoSeleccionado) {
    this.indicadoresProyectoSeleccionado = indicadoresProyectoSeleccionado;
  }

  public IndicadoresProyecto getIndicadoresProyectoEliminar() {
    return indicadoresProyectoEliminar;
  }

  public void setIndicadoresProyectoEliminar(IndicadoresProyecto indicadoresProyectoEliminar) {
    this.indicadoresProyectoEliminar = indicadoresProyectoEliminar;
  }

  public boolean isPuedeEditarCampoTextoHora() {

    return true;
    //return vistaFuncionario != null && !vistaFuncionario.isInformacionSIATH();

  }

  public int getNumeroRegistrosInvestigadoresProyecto() {

    if (listaInvestigadoresProyecto == null) {
      return 0;
    }
    return listaInvestigadoresProyecto.getNumeroRegistro();

  }

  public InvestigadorProyecto getInvestigadorProyectoEditar() {
    return investigadorProyectoEditar;
  }

  public void setInvestigadorProyectoEditar(InvestigadorProyecto investigadorProyectoEditar) {
    this.investigadorProyectoEditar = investigadorProyectoEditar;
  }

  public String getImportPresupuestoPage() {
    return _importPresupuestoPage;
  }

  public void setImportPresupuestoPage(String _importPresupuestoPage) {
    this._importPresupuestoPage = _importPresupuestoPage;
  }

  public List<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTO() {
    return listaAsesoriaProyectoDTO;
  }

  public void setListaAsesoriaProyectoDTO(List<AsesoriaProyectoDTO> listaAsesoriaProyectoDTO) {
    this.listaAsesoriaProyectoDTO = listaAsesoriaProyectoDTO;
  }

  /**
   *
   * @return
   */
  public String crearVersionProyecto() {

    try {

      iProyectoLocal.executeStoredProcedureCrearVersionProyecto(proyectoSeleccionado.getIdProyecto());

      adicionaMensajeInformativo("La versión hs sido generada correctamente.");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01", e);

    }
    return null;

  }

  /**
   *
   * @return
   */
  public boolean isEsRegistroProyecto() {

    return proyectoSeleccionado != null && proyectoSeleccionado.getCodigoProyecto() != null;

  }

  public boolean isEsRegistroProyectoTrabajoGrado() {

    return proyectoSeleccionado != null && proyectoSeleccionado.getConcecutivoProyectoGrado() != null;

  }

  public List<HistorialEstadoProyectosMigradosDTO> getListaHistorialEstadoProyectosMigradosDTO() {
    return listaHistorialEstadoProyectosMigradosDTO;
  }

  public void setListaHistorialEstadoProyectosMigradosDTO(List<HistorialEstadoProyectosMigradosDTO> listaHistorialEstadoProyectosMigradosDTO) {
    this.listaHistorialEstadoProyectosMigradosDTO = listaHistorialEstadoProyectosMigradosDTO;
  }

  public List<EvaluadoresProyectoMigrados> getListaEvaluadoresProyectoMigrados() {
    return listaEvaluadoresProyectoMigrados;
  }

  public void setListaEvaluadoresProyectoMigrados(List<EvaluadoresProyectoMigrados> listaEvaluadoresProyectoMigrados) {
    this.listaEvaluadoresProyectoMigrados = listaEvaluadoresProyectoMigrados;
  }

}
