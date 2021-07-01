package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import static co.gov.policia.dinae.constantes.IConstantes.CODIGO_LUGAR_GEOGRAFICO_COLOMBIA;
import static co.gov.policia.dinae.constantes.IConstantes.LUGAR_GEOGRAFICO_COLOMBIA;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.CronogramaSemilleroDTO;
import co.gov.policia.dinae.dto.EstimulosSemilleroDTO;
import co.gov.policia.dinae.dto.EventoSemilleroProyectoDTO;
import co.gov.policia.dinae.dto.EventosCapacitacionSemilleroDTO;
import co.gov.policia.dinae.dto.HorarioReunionesDTO;
import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.dto.LugarGeograficoDTO;
import co.gov.policia.dinae.dto.NivelFormacionFuncionarioDTO;
import co.gov.policia.dinae.dto.RecursoHumanoSemilleroDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.SemilleroInvestigacionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICronogramaSemilleroLocal;
import co.gov.policia.dinae.interfaces.IEventosCapacitacionSemilleroLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.interfaces.IOtrosEstimulosSemilleroLocal;
import co.gov.policia.dinae.interfaces.IRecursoHumanoSemilleroLocal;
import co.gov.policia.dinae.interfaces.IReunionesSemilleroLocal;
import co.gov.policia.dinae.interfaces.ISemilleroInvestigacionLocal;
import co.gov.policia.dinae.interfaces.ISemilleroProyectoLocal;
import co.gov.policia.dinae.interfaces.ISemillerosImplementacionLocal;
import co.gov.policia.dinae.interfaces.ITalentoEstimuloSemilleroLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.CronogramaSemillero;
import co.gov.policia.dinae.modelo.EventosCapacitacionSemillero;
import co.gov.policia.dinae.modelo.FormacionInvestigador;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.OtrosEstimulosSemillero;
import co.gov.policia.dinae.modelo.RecursoHumanoSemillero;
import co.gov.policia.dinae.modelo.ReunionesSemillero;
import co.gov.policia.dinae.modelo.SemilleroInvestigacion;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
import co.gov.policia.dinae.modelo.TalentoEstimuloSemillero;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFormacionFuncionario;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlSelectOneMenu;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
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
@javax.inject.Named(value = "cuIv3GestionarSemillerosInvestigacionFaces")
@javax.enterprise.context.SessionScoped
public class CuIv3GestionarSemillerosInvestigacionFaces extends JSFUtils implements Serializable {

  private List<SemilleroInvestigacionDTO> listaSemilleroInvestigacionDTO;
  private SemilleroInvestigacion semilleroInvestigacionSeleccionado;
  private int idTabSeleccionado;
  private List<SemilleroInvestigacionDTO> listaSemilleroInvestigacionDTOJefeUnidadAprueba;
  private List<EventoSemilleroProyectoDTO> listaEventoSemilleroProyectoDTO;

  @EJB
  private ISemilleroInvestigacionLocal iSemilleroInvestigacionLocal;
  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;
  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

  @EJB
  private IVistaFuncionarioLocal _iVistaFuncionario;

  @EJB
  private IInvestigadorLocal _iInvestigador;

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IRecursoHumanoSemilleroLocal _iRecursoHumanoSemillero;

  @EJB
  private ISemilleroProyectoLocal _iSemilleroProyecto;

  @EJB
  private ISemillerosImplementacionLocal _iSemilleroImpl;

  @EJB
  private ICronogramaSemilleroLocal _iCronogramaSemillero;

  @EJB
  private IReunionesSemilleroLocal _iReunionesSemillero;

  @EJB
  private ILugarGeograficoLocal iCiudad;

  @EJB
  private IEventosCapacitacionSemilleroLocal _iEventosSemilleros;

  @EJB
  private IOtrosEstimulosSemilleroLocal _iEstimulosSemillero;

  @EJB
  private ITalentoEstimuloSemilleroLocal _iTalentoEstimuloSemillero;

  private List<InvestigadorProyectoDTO> _investigadorSeleccionado;

  private List<NivelFormacionFuncionarioDTO> _nivelFormacionFuncionaio;

  private List<Constantes> _listaRelacionSemillero;

  private List<Constantes> _listaTipoVinculacion;

  private List<RecursoHumanoSemillero> _listaRecursoHumanoSemillero;

  private ListGenericDataModel<RecursoHumanoSemillero> _listaRecursoHumanoSemilleroModel;

  private String _identificacion;

  private Long _relacionSemilleroSeleccion;

  private String _compania;

  private String _cursoPromocion;

  private Date _fechaInicioPrograma;

  private Date _fechaFinPrograma;

  private Long _tipoVinculacion;

  private InputText _inputIdentificacion;

  private HtmlSelectOneMenu _selectRelacionSemillero;

  private InputText _inputCompania;

  private InputText _inputCursoPromocion;

  private RecursoHumanoSemillero[] _recursoHumanoSeleccionado;

  private UsuarioRol _usuarioRol;

  private List<SemilleroProyecto> _listaSemillerosProyecto;

  private List<SemillerosImplementacion> _listaSemillerosImplementacion;

  private SemilleroProyecto _semilleroProyectoSeleccionado;

  private SemillerosImplementacion _semilleroImplementacionSeleccionado;

  private InputText _inputActividad;

  private InputTextarea _inputObjetivo;

  private InputText _inputResponsable;

  private InputTextarea _textAreaProductoResultado;

  private Calendar _calendarFechaCumplimiento;

  private FileUploadEvent _uploadEvidenciaFoto;

  private FileUploadEvent _uploadEvidencia;

  private InputTextarea _temaTituloTrabajoTextArea;

  private InputText _inputNombreEvento;

  private InputText _inputLugarEvento;

  private Calendar _calendarFechaInicialEvento;

  private HtmlSelectOneMenu _selectDepartamentoEvento;

  private HtmlSelectOneMenu _selectCiudadEvento;

  private InputText _inputClaseTemaPonencia;

  private InputText _inputNumeroOficiales;

  private InputText _inputMotivoEstimulo;

  private InputTextarea _textAreaDescripcionMotivoEstimulo;

  private Calendar _calendarFechaOtorgamiento;

  private boolean mostrarEnlaceDescargaFoto;

  private boolean mostrarEnlaceDescarga;

  private String _nombreArchivoEvidenciaFoto;

  private String _nombreArchivoEvidencia;

  private List<CronogramaSemillero> _listaCronogramaSemillero;

  private List<CronogramaSemilleroDTO> _listaCronogramaSemilleroDTO;

  private ListGenericDataModel _listaCronogramaSemilleroModel;

  private CronogramaSemillero _cronogramaSemillero;

  private Object _entidadOrigen;

  private String _tituloProyecto;

  private String _codigoProyecto;

  private CronogramaSemilleroDTO seleccionCronograma;

  private String _trabajoIndependiente;

  private String _temaTituloTrabajo;

  private String _actividadCronograma;

  private String _objetivoCronograma;

  private String _responsableCronograma;

  private String _productoResultado;

  private Date _fechaCumplimiento;

  private List<HorarioReunionesDTO> _listaHorarioReuniones;

  private List<LugarGeograficoDTO> _listaDepartamentos;

  private List<LugarGeografico> _listaCiudadesDepartamento;

  private Long _departamentoEvento;

  private Long _ciudadEvento;

  private String _nombreEventoSemillero;

  private String _lugarEventoSemillero;

  private Date _fechaInicialEventoSemillero;

  private Date _fechaFinalEventoSemillero;

  private String _claseTemaPonencia;

  private Integer _numeroOficiales;

  private Integer _numeroSubOficiales;

  private Integer _numeroEstudiantes;

  private Integer _numeroNoUniformados;

  private List<EventosCapacitacionSemilleroDTO> _listaEventosSemillero;

  private EventosCapacitacionSemilleroDTO _eventoSemillero;

  private List<Constantes> _listaModalidadParticipacion;

  private Long _modalidadParticipacion;

  private ListGenericDataModel<EventosCapacitacionSemilleroDTO> _listaEventosSemilleroModel;

  private String _motivoEstimulo;

  private String _descMotivoEstimulo;

  private Date _fechaOtrogamiento;

  private List<RecursoHumanoSemilleroDTO> _listaRecursosSemilleroEstimuloDTO;

  private List<EstimulosSemilleroDTO> _listaEstimulosSemillero;

  private ListGenericDataModel<RecursoHumanoSemilleroDTO> _listaRecursosSemilleroEstimuloDTOModel;

  private ListGenericDataModel<EstimulosSemilleroDTO> _listaEstimulosSemilleroModel;

  private RecursoHumanoSemilleroDTO[] _participantesOtorgados;

  private EstimulosSemilleroDTO _estimuloSemilleroSeleccionado;

  @javax.annotation.PostConstruct
  public void init() {

    try {

      listaEventoSemilleroProyectoDTO = null;
      listaSemilleroInvestigacionDTOJefeUnidadAprueba = null;
      idTabSeleccionado = 0;
      semilleroInvestigacionSeleccionado = null;
      listaSemilleroInvestigacionDTO = new ArrayList<SemilleroInvestigacionDTO>();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar semilleros de investigación. (init)", e);
    }

  }

  /**
   *
   * @return @throws Exception
   */
  public String initReturnCU() throws Exception {

    try {

      init();

      idTabSeleccionado = 0;

      semilleroInvestigacionSeleccionado = new SemilleroInvestigacion();
      cargarListaSemilleros();
      cargarListaJefeUnidadAprueba();

      RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_SEMILLEROS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
      _usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

      return navigationFaces.redirectCuIv3GestionarSemillerosInvestigacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar semilleros de investigación. (initReturnCU)", e);

    }

    return null;
  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {
    try {
      idTabSeleccionado = 0;

      if (event == null || event.getTab() == null) {
        return;
      }

      if ((semilleroInvestigacionSeleccionado == null || semilleroInvestigacionSeleccionado.getIdSemillero() == null)
              && !"id_cu_iv_3_lbl_tab_informa_basica_semillero".equals(event.getTab().getId())) {
        adicionaMensajeError("Para continuar el diligenciamiento, se debe diligenciar y registrar un semillero");
        return;
      }

      if ("id_cu_iv_3_lbl_tab_informa_basica_semillero".equals(event.getTab().getId())) {
        idTabSeleccionado = 0;
      } else if ("id_cu_iv_3_lbl_tab_talento_humano".equals(event.getTab().getId())) {

        idTabSeleccionado = 1;
        inicializarCamposTalentoHumano();
        cargarListaRelacionSemillero();
        cargarListaTipoVinculacion();
        cargarListaRecursoHumanoSemillero();

      } else if ("id_cu_iv_3_lbl_investigadores".equals(event.getTab().getId())) {

        idTabSeleccionado = 2;
        cargarListaInvestigacionesSemillero();
        cargarListaImplementacionesSemillero();

      } else if ("id_include_evento_semillero".equals(event.getTab().getId())) {

        idTabSeleccionado = 3;
        cargarListaHorariosReuniones();
        inicializarCamposEventos();
        cargarListaModalidadParticipacion();
        cargarListaDepartamentos();
        cargarListaEventoSemillero();

      } else if ("id_include_estimulos_semillero".equals(event.getTab().getId())) {

        idTabSeleccionado = 4;
        inicializarCamposEstimulosSemillero();
        buscarRecursoHumanoEstimulo();
        cargarListaEstimulosSemillero();
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaJefeUnidadAprueba() throws Exception {

    listaSemilleroInvestigacionDTOJefeUnidadAprueba = new ArrayList<SemilleroInvestigacionDTO>(1);
    SemilleroInvestigacionDTO semilleroInvestigacionDTO = new SemilleroInvestigacionDTO();

    if (semilleroInvestigacionSeleccionado == null || semilleroInvestigacionSeleccionado.getIdSemillero() == null) {

      //CONSULTAMOS EL JEFE DE UNIDAD DEL USUARIO QUE ESTA LOGUEADO
      UnidadPolicial unidadPolicial = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      List<VistaFuncionario> listaFuncionarios = iUsuarioUnidadPolicialLocal.getUsuarioByUnidadByRole(
              unidadPolicial.getIdUnidadPolicial(),
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);

      semilleroInvestigacionDTO.setJefeIdUnidadUnidadPolicial(unidadPolicial.getIdUnidadPolicial());
      semilleroInvestigacionDTO.setJefeNombreUnidadUnidadPolicial(unidadPolicial.getSiglaFisicaYnombreUnidad());
      //SIEMPRE DEBE HABER UN JEFE DE UNIDAD POR UNIDAD POLICIAL
      semilleroInvestigacionDTO.setJefeUnidadApellidos(listaFuncionarios.get(0).getApellidos());
      semilleroInvestigacionDTO.setJefeUnidadCorreo(listaFuncionarios.get(0).getCorreo());
      semilleroInvestigacionDTO.setJefeUnidadGrado(listaFuncionarios.get(0).getGrado());
      semilleroInvestigacionDTO.setJefeUnidadNombres(listaFuncionarios.get(0).getNombres());
      semilleroInvestigacionDTO.setJefeUnidadNombreCompleto(listaFuncionarios.get(0).getNombreCompleto());
      semilleroInvestigacionDTO.setJefeUnidadTelefono(listaFuncionarios.get(0).getTelefono());
      semilleroInvestigacionDTO.setJefeUnidadIdentificacion(listaFuncionarios.get(0).getIdentificacion());
      semilleroInvestigacionDTO.setJefeUnidadCargo(listaFuncionarios.get(0).getCargo());

    } else {

      semilleroInvestigacionDTO.setJefeIdUnidadUnidadPolicial(semilleroInvestigacionSeleccionado.getJefeUnidadUnidadPolicial().getIdUnidadPolicial());
      semilleroInvestigacionDTO.setJefeNombreUnidadUnidadPolicial(semilleroInvestigacionSeleccionado.getJefeUnidadUnidadPolicial().getSiglaFisicaYnombreUnidad());
      //SIEMPRE DEBE HABER UN JEFE DE UNIDAD POR UNIDAD POLICIAL
      semilleroInvestigacionDTO.setJefeUnidadApellidos(semilleroInvestigacionSeleccionado.getJefeUnidadApellidos());
      semilleroInvestigacionDTO.setJefeUnidadCorreo(semilleroInvestigacionSeleccionado.getJefeUnidadCorreo());
      semilleroInvestigacionDTO.setJefeUnidadGrado(semilleroInvestigacionSeleccionado.getJefeUnidadGrado());
      semilleroInvestigacionDTO.setJefeUnidadNombres(semilleroInvestigacionSeleccionado.getJefeUnidadNombres());
      semilleroInvestigacionDTO.setJefeUnidadTelefono(semilleroInvestigacionSeleccionado.getJefeUnidadTelefono());
      semilleroInvestigacionDTO.setJefeUnidadCargo(semilleroInvestigacionSeleccionado.getJefeUnidadCargo());
      semilleroInvestigacionDTO.setJefeUnidadIdentificacion(semilleroInvestigacionSeleccionado.getJefeUnidadIdentificacion());
      semilleroInvestigacionDTO.setJefeUnidadNombreCompleto(semilleroInvestigacionSeleccionado.getJefeUnidadNombreCompleto());
    }

    listaSemilleroInvestigacionDTOJefeUnidadAprueba.add(semilleroInvestigacionDTO);

  }

  /**
   *
   * @return
   */
  public String guardarSemillero() {

    try {

      if (semilleroInvestigacionSeleccionado.getIdSemillero() == null) {

        semilleroInvestigacionSeleccionado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        semilleroInvestigacionSeleccionado.setUsuarioRol(_usuarioRol);
        semilleroInvestigacionSeleccionado.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());

      } else {

        semilleroInvestigacionSeleccionado.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        semilleroInvestigacionSeleccionado.setUsuarioRolActualiza(_usuarioRol);
        semilleroInvestigacionSeleccionado.setUsuarioActualiza(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      }

      semilleroInvestigacionSeleccionado.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));
      semilleroInvestigacionSeleccionado.setJefeUnidadUnidadPolicial(new UnidadPolicial(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeIdUnidadUnidadPolicial()));
      //SIEMPRE DEBE HABER UN JEFE DE UNIDAD POR UNIDAD POLICIAL
      semilleroInvestigacionSeleccionado.setJefeUnidadApellidos(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadApellidos());
      semilleroInvestigacionSeleccionado.setJefeUnidadCorreo(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadCorreo());
      semilleroInvestigacionSeleccionado.setJefeUnidadGrado(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadGrado());
      semilleroInvestigacionSeleccionado.setJefeUnidadNombres(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadNombres());
      semilleroInvestigacionSeleccionado.setJefeUnidadNombreCompleto(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadNombreCompleto());
      semilleroInvestigacionSeleccionado.setJefeUnidadTelefono(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadTelefono());
      semilleroInvestigacionSeleccionado.setJefeUnidadIdentificacion(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadIdentificacion());
      semilleroInvestigacionSeleccionado.setJefeUnidadCargo(listaSemilleroInvestigacionDTOJefeUnidadAprueba.get(0).getJefeUnidadCargo());

      semilleroInvestigacionSeleccionado = iSemilleroInvestigacionLocal.guardarSemilleroInvestigacion(semilleroInvestigacionSeleccionado);

      cargarListaSemilleros();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar semilleros de investigación. (modificarSemillero)", e);

    }
    return null;
  }

  /**
   *
   * @param semilleroInvestigacionDTO
   * @return
   */
  public String modificarSemillero(SemilleroInvestigacionDTO semilleroInvestigacionDTO) {

    try {

      semilleroInvestigacionSeleccionado = iSemilleroInvestigacionLocal.obtenerSemilleroInvestigacionPorId(semilleroInvestigacionDTO.getIdSemillero());
      _temaTituloTrabajo = semilleroInvestigacionSeleccionado.getTemaTituloTrabajo();
      _trabajoIndependiente = semilleroInvestigacionSeleccionado.getTrabajoIndependiente() == null ? "N" : semilleroInvestigacionSeleccionado.getTrabajoIndependiente();
      cargarListaJefeUnidadAprueba();
      idTabSeleccionado = 0;
      return navigationFaces.redirectCuIv3RegistrarSemillerosInvestigacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar semilleros de investigación. (modificarSemillero)", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String nuevoSemillero() {

    try {

      semilleroInvestigacionSeleccionado = new SemilleroInvestigacion();

      return navigationFaces.redirectCuIv3RegistrarSemillerosInvestigacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar semilleros de investigación. (modificarSemillero)", e);

    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaSemilleros() throws Exception {

    listaSemilleroInvestigacionDTO = iSemilleroInvestigacionLocal.getListaSemilleroInvestigacionDTOPorUnidadPolicial(
            loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

  }

  /**
   *
   * @return
   */
  public String buscarInvestigador() {
    try {

      _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
      _nivelFormacionFuncionaio = new ArrayList<NivelFormacionFuncionarioDTO>();

      if (_identificacion != null && !"".equals(_identificacion.trim())) {

        VistaFuncionario vistaFuncionario = _iVistaFuncionario.getVistaFuncionarioPorIdentificacion(_identificacion);
        InvestigadorProyectoDTO investiga;

        if (vistaFuncionario != null) {

          investiga = new InvestigadorProyectoDTO();
          investiga.setGrado(vistaFuncionario.getGrado());
          investiga.setNombreCompleto(vistaFuncionario.getNombreCompleto());
          investiga.setCorreo(vistaFuncionario.getCorreo());
          investiga.setIdentificacion(_identificacion);
          investiga.setCargo(vistaFuncionario.getCargo());
          investiga.setTelefono(vistaFuncionario.getTelefono());

          _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
          _investigadorSeleccionado.add(investiga);

          List<VistaFormacionFuncionario> formacion = _iVistaFuncionario.getListaVistaFormacionFuncionarioPorIdentificacion(_identificacion);

          if (formacion != null && !formacion.isEmpty()) {

            for (VistaFormacionFuncionario forma : formacion) {

              NivelFormacionFuncionarioDTO nivel = new NivelFormacionFuncionarioDTO();
              nivel.setAreaDelSaber(null);
              nivel.setFechaFinalizacion(forma.getFechaTermino());
              nivel.setTituloObtenido(forma.getTitulo());
              nivel.setNivelAcademico(forma.getNivelAcademico());

              _nivelFormacionFuncionaio.add(nivel);
            }
          }

          return null;
        }

        Investigador investigador = _iInvestigador.getInvestigadorPorNumeroIdentificacion(_identificacion);

        if (investigador != null) {

          investiga = new InvestigadorProyectoDTO();
          investiga.setGrado(investigador.getGrado());
          investiga.setNombreCompleto(investigador.getNombres() + " " + investigador.getApellidos());
          investiga.setCorreo(investigador.getCorreoElectronico());
          investiga.setIdentificacion(_identificacion);
          investiga.setCargo(investigador.getFuncionInvestigador());
          investiga.setTelefono(investigador.getTelefono());

          _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
          _investigadorSeleccionado.add(investiga);

          List<FormacionInvestigador> formacion = _iInvestigador.getFormacionInvestigadorByNumIdentificacion(_identificacion);

          if (formacion != null && !formacion.isEmpty()) {

            for (FormacionInvestigador forma : formacion) {

              NivelFormacionFuncionarioDTO nivel = new NivelFormacionFuncionarioDTO();
              nivel.setAreaDelSaber(forma.getAreaSaber().getValor());
              nivel.setFechaFinalizacion(forma.getFechaFinalizacion());
              nivel.setTituloObtenido(forma.getTituloObtenido());
              nivel.setNivelAcademico(forma.getNivelFormacion().getValor());

              _nivelFormacionFuncionaio.add(nivel);
            }
          }
          _inputIdentificacion.setValid(true);
          return null;
        }

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_3_lbl_identificacion_no_encontrada"));
        _inputIdentificacion.setValid(false);
      } else {
        adicionaMensajeError("El N° de identificación esta vacío");
        _inputIdentificacion.setValid(false);
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaRelacionSemillero() throws JpaDinaeException {
    _listaRelacionSemillero = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_RELACION_SEMILLERO);
    if (_listaRelacionSemillero == null || _listaRelacionSemillero.isEmpty()) {
      _listaRelacionSemillero = new ArrayList<Constantes>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaTipoVinculacion() throws JpaDinaeException {
    _listaTipoVinculacion = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_VINCULACION_SEMILLERO);
    if (_listaTipoVinculacion == null || _listaTipoVinculacion.isEmpty()) {
      _listaTipoVinculacion = new ArrayList<Constantes>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaRecursoHumanoSemillero() throws JpaDinaeException {

    if (semilleroInvestigacionSeleccionado == null || semilleroInvestigacionSeleccionado.getIdSemillero() == null) {
      return;
    }

    _listaRecursoHumanoSemillero = _iRecursoHumanoSemillero.findAllBySemillero(semilleroInvestigacionSeleccionado.getIdSemillero());
    if (_listaRecursoHumanoSemillero == null || _listaRecursoHumanoSemillero.isEmpty()) {
      _listaRecursoHumanoSemillero = new ArrayList<RecursoHumanoSemillero>();
    }

    _listaRecursoHumanoSemilleroModel = new ListGenericDataModel<RecursoHumanoSemillero>(_listaRecursoHumanoSemillero);
  }

  /**
   *
   * @return
   */
  public String guardarRecursoHumanoSemillero() {

    try {

      validarCamposTalentoHumano();

      RecursoHumanoSemillero exist = _iRecursoHumanoSemillero.findByIdAndSemilleroId(_identificacion, semilleroInvestigacionSeleccionado.getIdSemillero());

      if (exist == null) {

        RecursoHumanoSemillero recursoHumanoSemillero = new RecursoHumanoSemillero();
        recursoHumanoSemillero.setCompania(_compania);
        recursoHumanoSemillero.setCursoOPromocion(_cursoPromocion);
        recursoHumanoSemillero.setFechaIncio(_fechaInicioPrograma);
        recursoHumanoSemillero.setFechaFin(_fechaFinPrograma);
        recursoHumanoSemillero.setFechaRegistro(new Date());
        recursoHumanoSemillero.setIdentificacion(_identificacion);
        recursoHumanoSemillero.setTipoRelacionSemillero(new Constantes(_relacionSemilleroSeleccion));
        recursoHumanoSemillero.setSemilleroInvestigacion(semilleroInvestigacionSeleccionado);
        recursoHumanoSemillero.setEstado("A");
        recursoHumanoSemillero.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        recursoHumanoSemillero.setCargo(_investigadorSeleccionado.get(0).getCargo());
        recursoHumanoSemillero.setCorreoElectronico(_investigadorSeleccionado.get(0).getCorreo());
        recursoHumanoSemillero.setNombres(_investigadorSeleccionado.get(0).getNombreCompleto());
        recursoHumanoSemillero.setTelefono(_investigadorSeleccionado.get(0).getTelefono());
        recursoHumanoSemillero.setGrado(_investigadorSeleccionado.get(0).getGrado());
        recursoHumanoSemillero.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        recursoHumanoSemillero.setUsuarioRol(_usuarioRol);
        recursoHumanoSemillero.setTipoVinculacion((_tipoVinculacion != null && _tipoVinculacion.compareTo(-1L) != 0) ? new Constantes(_tipoVinculacion) : null);

        _iRecursoHumanoSemillero.saveOrUpdate(recursoHumanoSemillero);

        inicializarCamposTalentoHumano();
        cargarListaRecursoHumanoSemillero();

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

      } else {

        adicionaMensajeError("El investigador que está intentando relacionar al semillero, ya existe.");
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String activarInactivarRecurso() {
    try {
      if (_recursoHumanoSeleccionado != null && _recursoHumanoSeleccionado.length > 0) {

        for (RecursoHumanoSemillero recurso : _recursoHumanoSeleccionado) {

          if ("A".equals(recurso.getEstado())) {
            recurso.setEstado("I");
          } else if ("I".equals(recurso.getEstado())) {
            recurso.setEstado("A");
          }

          recurso.setFechaInactiva(new Date());
          recurso.setFechaActualizacion(new Date());
          recurso.setUsuarioActualiza(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
          recurso.setUsuarioRolActualiza(_usuarioRol);
          recurso.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

          _iRecursoHumanoSemillero.saveOrUpdate(recurso);

        }

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));
        _recursoHumanoSeleccionado = null;
      } else {

        adicionaMensajeError("Para Activar y/0 Inactivar participante debe seleccionarlo.");

      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void validarCamposTalentoHumano() throws JpaDinaeException {

    if (_identificacion == null || "".equals(_identificacion.trim())) {
      _inputIdentificacion.setValid(false);
      throw new JpaDinaeException("El campo 'N° de identificación' es obligatorio");
    }
    _inputIdentificacion.setValid(true);

    if (_relacionSemilleroSeleccion == null || _relacionSemilleroSeleccion.compareTo(-1L) == 0) {
      _selectRelacionSemillero.setValid(false);
      throw new JpaDinaeException("El campo 'Relación con el semillero' es obligatorio");
    }
    _selectRelacionSemillero.setValid(true);

    if (_compania == null || "".equals(_compania.trim())) {
      _inputCompania.setValid(false);
      throw new JpaDinaeException("El campo 'Compañía' es obligatorio");
    }
    _inputCompania.setValid(true);

    if (_cursoPromocion == null || "".equals(_cursoPromocion.trim())) {
      _inputCursoPromocion.setValid(false);
      throw new JpaDinaeException("El campo 'Curso o promoción' es obligatorio");
    }
    _inputCursoPromocion.setValid(true);
  }

  /**
   *
   */
  private void inicializarCamposTalentoHumano() {
    _inputCompania = new InputText();
    _selectRelacionSemillero = new HtmlSelectOneMenu();
    _inputIdentificacion = new InputText();
    _inputCursoPromocion = new InputText();
    _identificacion = null;
    _compania = null;
    _fechaFinPrograma = null;
    _fechaInicioPrograma = null;
    _cursoPromocion = null;
    _listaRecursoHumanoSemillero = new ArrayList<RecursoHumanoSemillero>();
    //_listaRelacionSemillero = new ArrayList<Constantes>();
    //_listaTipoVinculacion = new ArrayList<Constantes>();
    _nivelFormacionFuncionaio = new ArrayList<NivelFormacionFuncionarioDTO>();
    _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
    _relacionSemilleroSeleccion = -1L;
    _tipoVinculacion = -1L;
  }

  private void inicializarCamposCronogramaSemillero() {

    _inputActividad = new InputText();
    _inputObjetivo = new InputTextarea();
    _inputResponsable = new InputText();
    _calendarFechaCumplimiento = new Calendar();
    _textAreaProductoResultado = new InputTextarea();
    _nombreArchivoEvidencia = null;
    _nombreArchivoEvidenciaFoto = null;
    mostrarEnlaceDescarga = false;
    mostrarEnlaceDescargaFoto = false;
    _cronogramaSemillero = null;
    _uploadEvidencia = null;
    _uploadEvidenciaFoto = null;
    seleccionCronograma = null;
    _actividadCronograma = null;
    _objetivoCronograma = null;
    _responsableCronograma = null;
    _productoResultado = null;
    _fechaCumplimiento = null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaInvestigacionesSemillero() throws JpaDinaeException {
    _listaSemillerosProyecto = _iSemilleroProyecto.findAllBySemilleroInvestigacion(semilleroInvestigacionSeleccionado.getIdSemillero());
    if (_listaSemillerosProyecto == null || _listaSemillerosProyecto.isEmpty()) {
      _listaSemillerosProyecto = new ArrayList<SemilleroProyecto>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaImplementacionesSemillero() throws JpaDinaeException {
    _listaSemillerosImplementacion = _iSemilleroImpl.findAllBySemilleroInvestigacion(semilleroInvestigacionSeleccionado.getIdSemillero());
    if (_listaSemillerosImplementacion == null || _listaSemillerosImplementacion.isEmpty()) {
      _listaSemillerosImplementacion = new ArrayList<SemillerosImplementacion>();
    }
  }

  /**
   *
   * @param event
   */
  public void cargarArchivoEvidenciaFoto(FileUploadEvent event) {
    try {

      _uploadEvidenciaFoto = event;
      _nombreArchivoEvidenciaFoto = event.getFile().getFileName();
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(_nombreArchivoEvidenciaFoto));
      mostrarEnlaceDescargaFoto = false;

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Registro Archivo (handleFileUploadFoto) ", e);
    }
  }

  /**
   *
   * @param event
   */
  public void cargarArchivoEvidencia(FileUploadEvent event) {
    try {

      _uploadEvidencia = event;
      _nombreArchivoEvidencia = event.getFile().getFileName();
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(_nombreArchivoEvidencia));
      mostrarEnlaceDescarga = false;

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Registro Archivo (handleFileUploadFoto) ", e);
    }
  }

  /**
   *
   * @return
   */
  public StreamedContent descargarArchivoEvidenciaFoto() {
    try {
      if (_cronogramaSemillero != null && _cronogramaSemillero.getArchivoEvidenciaFoto() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + _cronogramaSemillero.getArchivoEvidenciaFotoOriginal();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _cronogramaSemillero.getArchivoEvidenciaFoto());
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar Semilleros (descargarArchivoEvidenciaFoto)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent descargarArchivoEvidencia() {
    try {
      if (_cronogramaSemillero != null && _cronogramaSemillero.getArchivoEvidencia() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + _cronogramaSemillero.getArchivoEvidenciaOriginal();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _cronogramaSemillero.getArchivoEvidencia());
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar Semilleros (descargarArchivoEvidencia)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String irCronogramaTrabajoSemillero() {
    String navega = null;
    try {

      inicializarCamposCronogramaSemillero();
      cargarListaCronogramaSemillero();
      navega = "/pages/secured/cu_iv_3/registrarCronogramaSemillero.xhtml?faces-redirect=true";

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return navega;
  }

  private void cargarListaCronogramaSemillero() throws JpaDinaeException {

    _listaCronogramaSemillero = new ArrayList<CronogramaSemillero>();

    if (_entidadOrigen instanceof SemilleroProyecto) {
      SemilleroProyecto semilleroProyecto = (SemilleroProyecto) _entidadOrigen;
      _listaCronogramaSemillero = _iCronogramaSemillero.findAllActivitiesBySemilleroProyecto(semilleroProyecto.getIdSemilleroProyecto());
      _tituloProyecto = semilleroProyecto.getProyecto().getTituloPropuesto();
      _codigoProyecto = semilleroProyecto.getProyecto().getCodigoProyecto();
    } else if (_entidadOrigen instanceof SemillerosImplementacion) {
      SemillerosImplementacion semilleroImpl = (SemillerosImplementacion) _entidadOrigen;
      _listaCronogramaSemillero = _iCronogramaSemillero.findAllActivitiesBySemillerosImplementacion(semilleroImpl.getIdSemilleroImplemetacion());
      _tituloProyecto = semilleroImpl.getImplementacionesProyecto().getProyecto().getTituloPropuesto();
      _codigoProyecto = semilleroImpl.getImplementacionesProyecto().getProyecto().getCodigoProyecto();
    } else {

      _listaCronogramaSemillero = _iCronogramaSemillero.findAllActivitiesBySemillero(semilleroInvestigacionSeleccionado.getIdSemillero());
      _tituloProyecto = semilleroInvestigacionSeleccionado.getTemaTituloTrabajo();
      _codigoProyecto = "";

    }

    _listaCronogramaSemilleroDTO = new ArrayList<CronogramaSemilleroDTO>(_listaCronogramaSemillero.size());

    for (CronogramaSemillero crono : _listaCronogramaSemillero) {
      CronogramaSemilleroDTO item = new CronogramaSemilleroDTO(crono);
      _listaCronogramaSemilleroDTO.add(item);
    }

    _listaCronogramaSemilleroModel = new ListGenericDataModel(_listaCronogramaSemilleroDTO);
  }

  /**
   *
   * @param e
   */
  public void cargarDatosCronogramaModificar(SelectEvent e) {
    try {
      if (seleccionCronograma == null) {
        return;
      }

      _cronogramaSemillero = _iCronogramaSemillero.findById(seleccionCronograma.getIdCronogramaSemillero());

      _actividadCronograma = seleccionCronograma.getActividad();
      _objetivoCronograma = seleccionCronograma.getObjetivo();
      _responsableCronograma = seleccionCronograma.getResponsable();
      _productoResultado = seleccionCronograma.getProductoResultadoEsperado();
      _fechaCumplimiento = seleccionCronograma.getFechaCumplimiento();

      mostrarEnlaceDescargaFoto = (seleccionCronograma.getArchivoEvidenciaFoto() != null);
      mostrarEnlaceDescarga = (seleccionCronograma.getArchivoEvidencia() != null);

      validarInputs();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @param event
   */
  public void noSeleccionCronograma(UnselectEvent event) {
    inicializarCamposCronogramaSemillero();
    validarInputs();
  }

  /**
   *
   * @return
   */
  public String cancelarModificarCronograma() {
    inicializarCamposCronogramaSemillero();
    validarInputs();
    return null;
  }

  public String regresarTab3() {

    String navega = null;

    try {
      cargarListaInvestigacionesSemillero();
      cargarListaImplementacionesSemillero();
      idTabSeleccionado = 2;
      navega = "/pages/secured/cu_iv_3/registra_semilleros_investigacion.xhtml?faces-redirect=true";
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void validarCamposCronograma() throws JpaDinaeException {

    if (_actividadCronograma == null || "".equals(_actividadCronograma.trim())) {
      _inputActividad.setValid(false);
      throw new JpaDinaeException("El campo 'Actividad' es obligatorio");
    }
    _inputActividad.setValid(true);

    if (_objetivoCronograma == null || "".equals(_objetivoCronograma.trim())) {
      _inputObjetivo.setValid(false);
      throw new JpaDinaeException("El campo 'Objetivo' es obligatorio");
    }
    _inputObjetivo.setValid(true);

    if (_responsableCronograma == null || "".equals(_responsableCronograma.trim())) {
      _inputResponsable.setValid(false);
      throw new JpaDinaeException("El campo 'Objetivo' es obligatorio");
    }
    _inputResponsable.setValid(true);

    if (_productoResultado == null || "".equals(_productoResultado.trim())) {
      _textAreaProductoResultado.setValid(false);
      throw new JpaDinaeException("El campo 'Objetivo' es obligatorio");
    }
    _textAreaProductoResultado.setValid(true);

    if (_fechaCumplimiento == null) {
      _calendarFechaCumplimiento.setValid(false);
      throw new JpaDinaeException("El campo 'Objetivo' es obligatorio");
    }
    _calendarFechaCumplimiento.setValid(true);

  }

  /**
   *
   * @return
   */
  public String agregarActividadCronograma() {

    try {

      validarCamposCronograma();

      CronogramaSemillero cronograma;

      if (_cronogramaSemillero == null || _cronogramaSemillero.getIdCronogramaSemillero() == null) {
        cronograma = new CronogramaSemillero();
      } else {
        cronograma = _cronogramaSemillero;
      }

      cronograma.setActividad(_actividadCronograma);
      cronograma.setObjetivo(_objetivoCronograma);
      cronograma.setResponsable(_responsableCronograma);
      cronograma.setProductoResultadoEsperado(_productoResultado);
      cronograma.setFechaCumplimiento(_fechaCumplimiento);

      if (_uploadEvidenciaFoto != null) {
        String[] archivo = guardarArchivoEvidencia(_uploadEvidenciaFoto);
        if (archivo != null) {
          cronograma.setArchivoEvidenciaFoto(_nombreArchivoEvidenciaFoto);
          cronograma.setArchivoEvidenciaFotoOriginal(archivo[1]);
        }
      }

      if (_uploadEvidencia != null) {
        String[] archivo = guardarArchivoEvidencia(_uploadEvidencia);
        if (archivo != null) {
          cronograma.setArchivoEvidencia(_nombreArchivoEvidencia);
          cronograma.setArchivoEvidenciaOriginal(archivo[1]);
        }
      }

      if (_cronogramaSemillero == null) {
        cronograma.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        cronograma.setUsuarioRol(_usuarioRol);
      } else {
        cronograma.setUsuarioRolActualiza(_usuarioRol);
        cronograma.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      }

      if (_entidadOrigen instanceof SemilleroProyecto) {
        cronograma.setSemilleroProyecto((SemilleroProyecto) _entidadOrigen);
      } else if (_entidadOrigen instanceof SemillerosImplementacion) {
        cronograma.setSemillerosImplementacion((SemillerosImplementacion) _entidadOrigen);
      } else {
        cronograma.setSemilleroInvestigacion(semilleroInvestigacionSeleccionado);
      }

      _iCronogramaSemillero.saveOrUpdate(cronograma);

      inicializarCamposCronogramaSemillero();
      cargarListaCronogramaSemillero();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
    }

    return null;
  }

  /**
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico(FileUploadEvent archivoGuardar) {

    try {

      if (archivoGuardar != null) {

        String nombreArchivoOriginal = archivoGuardar.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = "ARCHIVO_EVIDENCIA_SEMILLERO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general"), archivoGuardar.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-10 Registro Archivo (almacenarArchivoFisico) ", e);
    }

    return null;

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private String[] guardarArchivoEvidencia(FileUploadEvent archivoGuardar) throws JpaDinaeException {

    String[] archivo = new String[]{null, null};

    if (archivoGuardar != null) {

      File directorioFinal = null;

      try {

        directorioFinal = new File(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general"));

      } catch (NullPointerException e) {
        throw new JpaDinaeException("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general")));
      }

      if (!directorioFinal.exists()) {
        throw new JpaDinaeException("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general")));
      }

      if (directorioFinal.isFile()) {
        throw new JpaDinaeException("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general")));
      }

      if (!directorioFinal.canWrite()) {
        throw new JpaDinaeException("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general")));
      }

      archivo = almacenarArchivoFisico(archivoGuardar);
    }

    return archivo;
  }

  /**
   *
   * @return
   */
  public String habilitarCampoTemaTituloTrabajo() {
    if ("N".equals(_trabajoIndependiente)) {
      _temaTituloTrabajoTextArea.setValid(true);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String guardarInvestigacionesSemillero() {
    try {
      if (semilleroInvestigacionSeleccionado == null || semilleroInvestigacionSeleccionado.getIdSemillero() == null) {
        return null;
      }

      if ("S".equals(_trabajoIndependiente)) {

        if (_temaTituloTrabajo == null || "".equals(_temaTituloTrabajo.trim())) {
          _temaTituloTrabajoTextArea.setValid(false);
          adicionaMensajeError("El campo 'Tema o titulo del trabajo de investigación que se esta desarrollando' es obligatorio");
          return null;
        }
      }

      _temaTituloTrabajoTextArea.setValid(true);

      semilleroInvestigacionSeleccionado.setTrabajoIndependiente(_trabajoIndependiente);
      semilleroInvestigacionSeleccionado.setTemaTituloTrabajo(_temaTituloTrabajo);

      iSemilleroInvestigacionLocal.guardarSemilleroInvestigacion(semilleroInvestigacionSeleccionado);

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaHorariosReuniones() throws JpaDinaeException, ParseException {

    List<Constantes> listaTipoDiasSemana = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_DIAS_SEMANA);
    final SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
    _listaHorarioReuniones = new ArrayList<HorarioReunionesDTO>();

    if (!listaTipoDiasSemana.isEmpty()) {

      for (Constantes dias : listaTipoDiasSemana) {
        HorarioReunionesDTO horario = new HorarioReunionesDTO(dias);
        _listaHorarioReuniones.add(horario);
      }
    }

    List<ReunionesSemillero> reunionesSemilleros = _iReunionesSemillero.findBySemillero(semilleroInvestigacionSeleccionado.getIdSemillero());
    if (!reunionesSemilleros.isEmpty()) {

      for (HorarioReunionesDTO horario : _listaHorarioReuniones) {
        for (ReunionesSemillero reunion : reunionesSemilleros) {
          if (horario.getIdDiaSemana().compareTo(reunion.getDia().getIdConstantes()) == 0) {

            Date fechaInicial = formato.parse(reunion.getHoraInicio());
            Date fechaFinal = formato.parse(reunion.getHoraFin());

            horario.setHoraInicio(fechaInicial);
            horario.setHoraFin(fechaFinal);

            horario.setIdHorarioReunion(reunion.getIdReunionSemillero());
          }
        }
      }
    }
  }

  /**
   *
   * @return
   */
  public String guardarReunionesSemillero() {
    try {
      if (!_listaHorarioReuniones.isEmpty()) {
        for (HorarioReunionesDTO horario : _listaHorarioReuniones) {
          if (horario.getHoraInicio() != null) {
            if (horario.getHoraFin() == null) {
              adicionaMensajeError("La hora final para el dia " + horario.getDiaSemana() + " es obligatoria");
              horario.setEsValidoHoraFin(false);
              return null;
            }
            horario.setEsValidoHoraFin(true);
          }

          if (horario.getHoraFin() != null) {
            if (horario.getHoraInicio() == null) {
              adicionaMensajeError("La hora inicial para el dia " + horario.getDiaSemana() + " es obligatoria");
              horario.setEsValidoHoraInicio(false);
              return null;
            }
            horario.setEsValidoHoraInicio(true);
          }

          if (horario.getHoraInicio() != null && horario.getHoraFin() != null) {

            ReunionesSemillero reunionesSemillero;

            if (horario.getIdHorarioReunion() == null) {
              reunionesSemillero = new ReunionesSemillero();
              reunionesSemillero.setUsuarioRol(_usuarioRol);
              reunionesSemillero.setUsuarioRegistro(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
              reunionesSemillero.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
            } else {
              reunionesSemillero = _iReunionesSemillero.findById(horario.getIdHorarioReunion());
              reunionesSemillero.setUsuarioRolActualiza(_usuarioRol);
              reunionesSemillero.setUsuarioActualiza(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
              reunionesSemillero.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
            }

            reunionesSemillero.setHoraFin(horario.getHoraFinString());
            reunionesSemillero.setHoraInicio(horario.getHoraInicioString());
            reunionesSemillero.setSemilleroInvestigacion(semilleroInvestigacionSeleccionado);
            reunionesSemillero.setDia(new Constantes(horario.getIdDiaSemana()));

            _iReunionesSemillero.saveOrUpdate(reunionesSemillero);
          }
        }
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));
        cargarListaHorariosReuniones();

      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   */
  private void inicializarCamposEventos() {

    _inputNombreEvento = new InputText();
    _inputLugarEvento = new InputText();
    _inputClaseTemaPonencia = new InputText();
    _calendarFechaInicialEvento = new Calendar();
    _selectDepartamentoEvento = new HtmlSelectOneMenu();
    _selectCiudadEvento = new HtmlSelectOneMenu();
    _inputNumeroOficiales = new InputText();

    _nombreEventoSemillero = null;
    _lugarEventoSemillero = null;
    _claseTemaPonencia = null;
    _fechaInicialEventoSemillero = null;
    _fechaFinalEventoSemillero = null;
    _departamentoEvento = -1L;
    _ciudadEvento = -1L;
    _numeroOficiales = null;
    _numeroSubOficiales = null;
    _numeroEstudiantes = null;
    _numeroNoUniformados = null;

    _eventoSemillero = null;

    _nombreArchivoEvidencia = null;
    _nombreArchivoEvidenciaFoto = null;
    _modalidadParticipacion = -1L;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaModalidadParticipacion() throws JpaDinaeException {
    _listaModalidadParticipacion = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_MODALIDAD_PARTICIPACION_SEMILLERO);
    if (_listaModalidadParticipacion == null || _listaModalidadParticipacion.isEmpty()) {
      _listaModalidadParticipacion = new ArrayList<Constantes>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEventoSemillero() throws JpaDinaeException {
    List<EventosCapacitacionSemillero> eventosSemillero = _iEventosSemilleros.findByIdSemillero(semilleroInvestigacionSeleccionado.getIdSemillero());
    _listaEventosSemillero = new ArrayList<EventosCapacitacionSemilleroDTO>();

    if (!eventosSemillero.isEmpty()) {
      for (EventosCapacitacionSemillero evento : eventosSemillero) {
        EventosCapacitacionSemilleroDTO item = new EventosCapacitacionSemilleroDTO(evento);
        _listaEventosSemillero.add(item);
      }
    }

    _listaEventosSemilleroModel = new ListGenericDataModel<EventosCapacitacionSemilleroDTO>(_listaEventosSemillero);
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaDepartamentos() throws JpaDinaeException {
    _listaDepartamentos = iCiudad.getListaDepartamentos(10L);
    if (_listaDepartamentos == null || _listaDepartamentos.isEmpty()) {
      _listaDepartamentos = new ArrayList<LugarGeograficoDTO>();
    }
  }

  /**
   *
   */
  public void cargarCiudadesDepartamento() {
    if (_departamentoEvento != null && !(_departamentoEvento.compareTo(-1L) == 0)) {
      try {
        _listaCiudadesDepartamento = iCiudad.getListaLugaresByCodDepartamento(_departamentoEvento);
        if (_listaCiudadesDepartamento == null || _listaCiudadesDepartamento.isEmpty()) {
          _listaCiudadesDepartamento = new ArrayList<LugarGeografico>();
        }
      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public String guardarEventosSemillero() {

    try {
      validarCamposEventosSemillero();

      EventosCapacitacionSemillero eventosCapacitacionSemillero;

      if (_eventoSemillero != null && _eventoSemillero.getIdEventCapaSemillero() != null) {

        eventosCapacitacionSemillero = _iEventosSemilleros.findById(_eventoSemillero.getIdEventCapaSemillero());
        eventosCapacitacionSemillero.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        eventosCapacitacionSemillero.setUsuarioRolActualiza(_usuarioRol);

      } else {

        eventosCapacitacionSemillero = new EventosCapacitacionSemillero();
        eventosCapacitacionSemillero.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        eventosCapacitacionSemillero.setUsuarioRol(_usuarioRol);
      }

      eventosCapacitacionSemillero.setFechaFinalizacionEvento(_fechaFinalEventoSemillero);
      eventosCapacitacionSemillero.setFechaInicioEvento(_fechaInicialEventoSemillero);
      eventosCapacitacionSemillero.setNombreEvento(_nombreEventoSemillero);
      eventosCapacitacionSemillero.setLugarEvento(_lugarEventoSemillero);
      eventosCapacitacionSemillero.setNumeroEstudiantes(_numeroEstudiantes == null ? null : _numeroEstudiantes.shortValue());
      eventosCapacitacionSemillero.setNumeroNoUniformados(_numeroNoUniformados == null ? null : _numeroNoUniformados.shortValue());
      eventosCapacitacionSemillero.setNumeroOficiales(_numeroOficiales.shortValue());
      eventosCapacitacionSemillero.setNumeroSuboficiales(_numeroSubOficiales == null ? null : _numeroSubOficiales.shortValue());
      eventosCapacitacionSemillero.setSemilleroInvestigacion(semilleroInvestigacionSeleccionado);
      eventosCapacitacionSemillero.setClaseTemaPonencia(_claseTemaPonencia);
      eventosCapacitacionSemillero.setModalidadParticipacion((_modalidadParticipacion == null || _modalidadParticipacion.compareTo(-1L) == 0) ? null : new Constantes(_modalidadParticipacion));
      eventosCapacitacionSemillero.setIdCiudad(_ciudadEvento);

      if (_uploadEvidenciaFoto != null) {
        String[] archivo = guardarArchivoEvidencia(_uploadEvidenciaFoto);
        if (archivo != null) {
          eventosCapacitacionSemillero.setArchivoEvidenciaFotog(_nombreArchivoEvidenciaFoto);
          eventosCapacitacionSemillero.setArchivoEvidenciaFotogOriginal(archivo[1]);
        }
      }

      if (_uploadEvidencia != null) {
        String[] archivo = guardarArchivoEvidencia(_uploadEvidencia);
        if (archivo != null) {
          eventosCapacitacionSemillero.setArchivoEvidenciaDocumental(_nombreArchivoEvidencia);
          eventosCapacitacionSemillero.setArchivoEvidenciaDocumentalOriginal(archivo[1]);
        }
      }

      _iEventosSemilleros.saveOrUpdate(eventosCapacitacionSemillero);

      inicializarCamposEventos();
      cargarListaModalidadParticipacion();
      cargarListaDepartamentos();
      cargarListaEventoSemillero();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
      Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  public void validarCamposEventosSemillero() throws JpaDinaeException {

    if (_nombreEventoSemillero == null || "".equals(_nombreEventoSemillero.trim())) {
      _inputNombreEvento.setValid(false);
      throw new JpaDinaeException("El campo 'Nombre del evento' es obligatorio");
    }
    _inputNombreEvento.setValid(true);

    if (_lugarEventoSemillero == null || "".equals(_lugarEventoSemillero.trim())) {
      _inputLugarEvento.setValid(false);
      throw new JpaDinaeException("El campo 'Lugar del evento' es obligatorio");
    }
    _inputLugarEvento.setValid(true);

    if (_fechaInicialEventoSemillero == null) {
      _calendarFechaInicialEvento.setValid(false);
      throw new JpaDinaeException("El campo 'Fecha de inicio del evento' es obligatorio");
    }
    _calendarFechaInicialEvento.setValid(true);

    if (_departamentoEvento == null || _departamentoEvento.compareTo(-1L) == 0) {
      _selectDepartamentoEvento.setValid(false);
      throw new JpaDinaeException("El campo 'Departamento de realización' es obligatorio");
    }

    _selectDepartamentoEvento.setValid(true);

    if (_ciudadEvento == null || _ciudadEvento.compareTo(-1L) == 0) {
      _selectCiudadEvento.setValid(false);
      throw new JpaDinaeException("El campo 'Ciudad de realización' es obligatorio");
    }

    _selectCiudadEvento.setValid(true);

    if (_claseTemaPonencia == null || "".equals(_claseTemaPonencia.trim())) {
      _inputClaseTemaPonencia.setValid(false);
      throw new JpaDinaeException("El campo 'Clase o tema de la ponencia' es obligatorio");
    }
    _inputClaseTemaPonencia.setValid(true);

    if (_numeroOficiales == null) {
      _inputNumeroOficiales.setValid(false);
      throw new JpaDinaeException("El campo 'Oficiales' es obligatorio");
    }
    _inputNumeroOficiales.setValid(true);

  }

  /**
   *
   * @param e
   */
  public void cargarDatosEventosSemillero(SelectEvent e) {
    try {
      if (_eventoSemillero == null) {
        return;
      }

      _nombreEventoSemillero = _eventoSemillero.getNombreEvento();
      _lugarEventoSemillero = _eventoSemillero.getLugarEvento();
      _fechaInicialEventoSemillero = _eventoSemillero.getFechaInicioEvento();
      _ciudadEvento = _eventoSemillero.getIdCiudad();
      _departamentoEvento = iCiudad.findMunicipioByCodMunicipio(_ciudadEvento).getCodDepartamento();

      cargarListaDepartamentos();
      cargarCiudadesDepartamento();

      _fechaFinalEventoSemillero = _eventoSemillero.getFechaFinalizacionEvento();
      _claseTemaPonencia = _eventoSemillero.getClaseTemaPonencia();
      _numeroOficiales = _eventoSemillero.getNumeroOficiales().intValue();
      _numeroSubOficiales = _eventoSemillero.getNumeroSuboficiales() == null ? null : _eventoSemillero.getNumeroSuboficiales().intValue();
      _numeroEstudiantes = _eventoSemillero.getNumeroEstudiantes() == null ? null : _eventoSemillero.getNumeroEstudiantes().intValue();
      _numeroNoUniformados = _eventoSemillero.getNumeroNoUniformados() == null ? null : _eventoSemillero.getNumeroNoUniformados().intValue();

      _modalidadParticipacion = _eventoSemillero.getModalidadParticipacion() == null ? -1L : _eventoSemillero.getModalidadParticipacion();

      mostrarEnlaceDescargaFoto = (_eventoSemillero.getArchivoEvidenciaFotog() != null);
      mostrarEnlaceDescarga = (_eventoSemillero.getArchivoEvidenciaDocumental() != null);

      validarInputsEventos();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @param event
   */
  public void noSeleccionEventoSemillero(UnselectEvent event) {
    cancelarModificarEventoSemillero();
  }

  /**
   *
   * @return
   */
  public String cancelarModificarEventoSemillero() {
    try {
      inicializarCamposEventos();
      cargarListaModalidadParticipacion();
      cargarListaDepartamentos();
      cargarListaEventoSemillero();
      validarInputsEventos();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent descargarArchivoEvidenciaFotoEvento() {
    try {
      if (_eventoSemillero != null && _eventoSemillero.getArchivoEvidenciaFotog() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + _eventoSemillero.getArchivoEvidenciaFotogOriginal();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _eventoSemillero.getArchivoEvidenciaFotog());
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar Semilleros (descargarArchivoEvidenciaFoto)", e);
    }
    return null;
  }

  /**
   *
   * @param archivoEvidencia
   * @param archivoEvidenciaOrigen
   * @return
   */
  public StreamedContent descargarArchivoEvidenciaFotoEventoTabla(String archivoEvidencia, String archivoEvidenciaOrigen) {
    try {
      if (archivoEvidencia != null && archivoEvidenciaOrigen != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + archivoEvidenciaOrigen;

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, archivoEvidencia);
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar Semilleros (descargarArchivoEvidenciaFoto)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent descargarArchivoEvidenciaEvento() {
    try {
      if (_eventoSemillero != null && _eventoSemillero.getArchivoEvidenciaDocumental() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + _eventoSemillero.getArchivoEvidenciaDocumentalOriginal();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _eventoSemillero.getArchivoEvidenciaDocumental());
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-03 Gestionar Semilleros (descargarArchivoEvidencia)", e);
    }
    return null;
  }

  /**
   *
   * @param evento
   * @return
   */
  public String calcularMostrarAsistentesEventos(EventosCapacitacionSemilleroDTO evento) {

    if (evento != null) {

      Integer numeroOficiales = evento.getNumeroOficiales().intValue();
      Integer numeroSuboficiales = evento.getNumeroSuboficiales() == null ? 0 : evento.getNumeroSuboficiales().intValue();
      Integer numeroEstudiantes = evento.getNumeroEstudiantes() == null ? 0 : evento.getNumeroEstudiantes().intValue();
      Integer numeroNoUniformados = evento.getNumeroNoUniformados() == null ? 0 : evento.getNumeroNoUniformados().intValue();

      return String.valueOf((numeroOficiales + numeroSuboficiales + numeroEstudiantes + numeroNoUniformados));
    }

    return null;
  }

  /**
   *
   * @param idCiudad
   * @return
   */
  public String tomarNombreCiudad(Long idCiudad) {
    if (idCiudad != null) {
      try {
        return iCiudad.findMunicipioByCodMunicipio(idCiudad).getDescMunicipio();
      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return null;
  }

  private void inicializarCamposEstimulosSemillero() {

    _inputMotivoEstimulo = new InputText();
    _textAreaDescripcionMotivoEstimulo = new InputTextarea();
    _calendarFechaOtorgamiento = new Calendar();
    _motivoEstimulo = null;
    _descMotivoEstimulo = null;
    _fechaOtrogamiento = null;

    _listaEstimulosSemillero = new ArrayList<EstimulosSemilleroDTO>();
    _listaRecursosSemilleroEstimuloDTO = new ArrayList<RecursoHumanoSemilleroDTO>();
    _estimuloSemilleroSeleccionado = null;

    validarInputsEstimulos();
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEstimulosSemillero() throws JpaDinaeException {

    List<OtrosEstimulosSemillero> estimulosSemillero = _iEstimulosSemillero.findBySemilleroInvestigacion(semilleroInvestigacionSeleccionado.getIdSemillero());
    _listaEstimulosSemillero = new ArrayList<EstimulosSemilleroDTO>();

    if (!estimulosSemillero.isEmpty()) {
      for (OtrosEstimulosSemillero otros : estimulosSemillero) {
        List<TalentoEstimuloSemillero> talentos = _iTalentoEstimuloSemillero.findByOtrosEstimulosSemillero(otros.getIdOtrosEstimulosSemillero());
        EstimulosSemilleroDTO item = new EstimulosSemilleroDTO(otros, talentos);
        _listaEstimulosSemillero.add(item);
      }
    }

    _listaEstimulosSemilleroModel = new ListGenericDataModel<EstimulosSemilleroDTO>(_listaEstimulosSemillero);
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void buscarRecursoHumanoEstimulo() throws JpaDinaeException {

    Date fechaFiltro = (_fechaOtrogamiento == null) ? new Date() : _fechaOtrogamiento;
    Long idSemillero = semilleroInvestigacionSeleccionado.getIdSemillero();

    List<RecursoHumanoSemillero> recursoHumanoSemilleros = _iRecursoHumanoSemillero.findBySemilleroAndEstadoActivoFecha(idSemillero, fechaFiltro);
    _listaRecursosSemilleroEstimuloDTO = new ArrayList<RecursoHumanoSemilleroDTO>();
    _participantesOtorgados = new RecursoHumanoSemilleroDTO[recursoHumanoSemilleros.size()];

    if (!recursoHumanoSemilleros.isEmpty()) {

      for (RecursoHumanoSemillero recurso : recursoHumanoSemilleros) {
        RecursoHumanoSemilleroDTO item = new RecursoHumanoSemilleroDTO(recurso);
        _listaRecursosSemilleroEstimuloDTO.add(item);
      }
    }

    _listaRecursosSemilleroEstimuloDTOModel = new ListGenericDataModel<RecursoHumanoSemilleroDTO>(_listaRecursosSemilleroEstimuloDTO);
  }

  /**
   *
   * @param event
   */
  public void buscarRecursoHumanoEstimiloFecha(SelectEvent event) {
    try {
      _fechaOtrogamiento = (Date) event.getObject();
      buscarRecursoHumanoEstimulo();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public String guardarOtorgamientoEstimulo() {
    try {

      validarCamposEstimulosSemillero();

      OtrosEstimulosSemillero otrosEstimulosSemillero;
      boolean esActualizacion = false;

      if (_estimuloSemilleroSeleccionado != null && _estimuloSemilleroSeleccionado.getIdOtrosEstimulosSemillero() != null) {

        otrosEstimulosSemillero = _iEstimulosSemillero.findById(_estimuloSemilleroSeleccionado.getIdOtrosEstimulosSemillero());
        otrosEstimulosSemillero.setMaquinaActualiza(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        otrosEstimulosSemillero.setUsuarioRolActualiza(_usuarioRol);
        esActualizacion = true;

      } else {

        otrosEstimulosSemillero = new OtrosEstimulosSemillero();
        otrosEstimulosSemillero.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        otrosEstimulosSemillero.setUsuarioRol(_usuarioRol);
      }

      otrosEstimulosSemillero.setDescripcionTipoEstimulo(_descMotivoEstimulo);
      otrosEstimulosSemillero.setMotivoOtorgamiento(_motivoEstimulo);
      otrosEstimulosSemillero.setFechaOtorgamiento(_fechaOtrogamiento);
      otrosEstimulosSemillero.setSemilleroInvestigacion(semilleroInvestigacionSeleccionado);

      _iEstimulosSemillero.saveOrUpdate(otrosEstimulosSemillero);

      if (esActualizacion) {
        List<TalentoEstimuloSemillero> talentosEstimuloSemilleros = _iTalentoEstimuloSemillero.findByOtrosEstimulosSemillero(otrosEstimulosSemillero.getIdOtrosEstimulosSemillero());
        if (!talentosEstimuloSemilleros.isEmpty()) {
          for (TalentoEstimuloSemillero talento : talentosEstimuloSemilleros) {
            _iTalentoEstimuloSemillero.deleteEntity(talento);
          }
        }
      }

      for (RecursoHumanoSemilleroDTO participante : _participantesOtorgados) {

        TalentoEstimuloSemillero talentoEstimuloSemillero = new TalentoEstimuloSemillero();
        talentoEstimuloSemillero.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        talentoEstimuloSemillero.setOtrosEstimulosSemillero(otrosEstimulosSemillero);
        talentoEstimuloSemillero.setRecursoHumanoSemillero(new RecursoHumanoSemillero(participante.getIdRecursoHumanoSemi()));
        talentoEstimuloSemillero.setUsuarioRol(_usuarioRol);

        _iTalentoEstimuloSemillero.saveOrUpdate(talentoEstimuloSemillero);
      }

      inicializarCamposEstimulosSemillero();
      buscarRecursoHumanoEstimulo();
      cargarListaEstimulosSemillero();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  public void validarCamposEstimulosSemillero() throws JpaDinaeException {

    if (_motivoEstimulo == null || "".equals(_motivoEstimulo.trim())) {
      _inputMotivoEstimulo.setValid(false);
      throw new JpaDinaeException("El campo 'Motivo del otorgamiento' es obligatorio");
    }

    if (_descMotivoEstimulo == null || "".equals(_descMotivoEstimulo.trim())) {
      _textAreaDescripcionMotivoEstimulo.setValid(false);
      throw new JpaDinaeException("El campo 'Describa el tipo de estimulo' es obligatorio");
    }
    _textAreaDescripcionMotivoEstimulo.setValid(true);

    if (_fechaOtrogamiento == null) {
      _calendarFechaOtorgamiento.setValid(false);
      throw new JpaDinaeException("El campo 'Fecha en que otorgó' es obligatorio");
    }
    _calendarFechaOtorgamiento.setValid(true);

    if (_participantesOtorgados == null || _participantesOtorgados.length == 0) {
      throw new JpaDinaeException("Se debe seleccionar al menos un participante al cual otorgar el estimulo");
    }
  }

  /**
   *
   * @param e
   */
  public void cargarDatosEstimulosSemillero(SelectEvent e) {
    try {
      if (_estimuloSemilleroSeleccionado == null) {
        return;
      }

      _motivoEstimulo = _estimuloSemilleroSeleccionado.getMotivoOtorgamiento();
      _descMotivoEstimulo = _estimuloSemilleroSeleccionado.getDescripcionTipoEstimulo();
      _fechaOtrogamiento = _estimuloSemilleroSeleccionado.getFechaOtorgamiento();

      buscarRecursoHumanoEstimulo();
      establecerParticipantesEstimulo(_estimuloSemilleroSeleccionado);

      validarInputsEventos();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   */
  private void establecerParticipantesEstimulo(EstimulosSemilleroDTO estimuloSemillero) throws JpaDinaeException {

    _participantesOtorgados = new RecursoHumanoSemilleroDTO[_listaRecursosSemilleroEstimuloDTO.size()];
    Map<Long, RecursoHumanoSemilleroDTO> mapaParticipantes = new HashMap<Long, RecursoHumanoSemilleroDTO>();

    int i = 0;
    for (RecursoHumanoSemilleroDTO participante : _listaRecursosSemilleroEstimuloDTO) {

      List<TalentoEstimuloSemillero> talentos = _iTalentoEstimuloSemillero.findByOtrosEstimulosSemillero(estimuloSemillero.getIdOtrosEstimulosSemillero());

      if (talentos != null && !talentos.isEmpty()) {

        for (TalentoEstimuloSemillero talento : talentos) {
          if (participante.getIdRecursoHumanoSemi().compareTo(talento.getRecursoHumanoSemillero().getIdRecursoHumanoSemi()) == 0) {

            if (!mapaParticipantes.containsKey(participante.getIdRecursoHumanoSemi())) {
              mapaParticipantes.put(participante.getIdRecursoHumanoSemi(), participante);
            }
          }
        }
      }

    }

    Set<Long> keySet = mapaParticipantes.keySet();
    for (Long key : keySet) {
      _participantesOtorgados[i] = mapaParticipantes.get(key);
      i++;
    }
  }

  /**
   *
   * @param event
   */
  public void noSeleccionEstimuloSemillero(UnselectEvent event) {
    cancelarModificarEventoSemillero();
  }

  /**
   *
   * @return
   */
  public String cancelarModificarEstimuloSemillero() {
    try {
      inicializarCamposEstimulosSemillero();
      buscarRecursoHumanoEstimulo();
      cargarListaEstimulosSemillero();
      validarInputsEventos();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   */
  private void validarInputs() {
    _inputActividad.setValid(true);
    _inputObjetivo.setValid(true);
    _inputResponsable.setValid(true);
    _textAreaProductoResultado.setValid(true);
    _calendarFechaCumplimiento.setValid(true);
  }

  private void validarInputsEventos() {
    _inputNombreEvento.setValid(true);
    _inputLugarEvento.setValid(true);
    _inputClaseTemaPonencia.setValid(true);
    _selectDepartamentoEvento.setValid(true);
    _selectCiudadEvento.setValid(true);
    _inputNumeroOficiales.setValid(true);
  }

  private void validarInputsEstimulos() {
    _inputMotivoEstimulo.setValid(true);
    _textAreaDescripcionMotivoEstimulo.setValid(true);
    _calendarFechaOtorgamiento.setValid(true);
  }

  public List<SemilleroInvestigacionDTO> getListaSemilleroInvestigacionDTO() {
    return listaSemilleroInvestigacionDTO;
  }

  public void setListaSemilleroInvestigacionDTO(List<SemilleroInvestigacionDTO> listaSemilleroInvestigacionDTO) {
    this.listaSemilleroInvestigacionDTO = listaSemilleroInvestigacionDTO;
  }

  public SemilleroInvestigacion getSemilleroInvestigacionSeleccionado() {
    return semilleroInvestigacionSeleccionado;
  }

  public void setSemilleroInvestigacionSeleccionado(SemilleroInvestigacion semilleroInvestigacionSeleccionado) {
    this.semilleroInvestigacionSeleccionado = semilleroInvestigacionSeleccionado;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public List<SemilleroInvestigacionDTO> getListaSemilleroInvestigacionDTOJefeUnidadAprueba() {
    return listaSemilleroInvestigacionDTOJefeUnidadAprueba;
  }

  public void setListaSemilleroInvestigacionDTOJefeUnidadAprueba(List<SemilleroInvestigacionDTO> listaSemilleroInvestigacionDTOJefeUnidadAprueba) {
    this.listaSemilleroInvestigacionDTOJefeUnidadAprueba = listaSemilleroInvestigacionDTOJefeUnidadAprueba;
  }

  public List<EventoSemilleroProyectoDTO> getListaEventoSemilleroProyectoDTO() {
    return listaEventoSemilleroProyectoDTO;
  }

  public void setListaEventoSemilleroProyectoDTO(List<EventoSemilleroProyectoDTO> listaEventoSemilleroProyectoDTO) {
    this.listaEventoSemilleroProyectoDTO = listaEventoSemilleroProyectoDTO;
  }

  public List<InvestigadorProyectoDTO> getInvestigadorSeleccionado() {
    return _investigadorSeleccionado;
  }

  public void setInvestigadorSeleccionado(List<InvestigadorProyectoDTO> _investigadorSeleccionado) {
    this._investigadorSeleccionado = _investigadorSeleccionado;
  }

  public String getIdentificacion() {
    return _identificacion;
  }

  public void setIdentificacion(String _identificacion) {
    this._identificacion = _identificacion;
  }

  public List<Constantes> getListaRelacionSemillero() {
    return _listaRelacionSemillero;
  }

  public void setListaRelacionSemillero(List<Constantes> _listaRelacionSemillero) {
    this._listaRelacionSemillero = _listaRelacionSemillero;
  }

  public Long getRelacionSemilleroSeleccion() {
    return _relacionSemilleroSeleccion;
  }

  public void setRelacionSemilleroSeleccion(Long _relacionSemilleroSeleccion) {
    this._relacionSemilleroSeleccion = _relacionSemilleroSeleccion;
  }

  public String getCompania() {
    return _compania;
  }

  public void setCompania(String _compania) {
    this._compania = _compania;
  }

  public String getCursoPromocion() {
    return _cursoPromocion;
  }

  public void setCursoPromocion(String _cursoPromocion) {
    this._cursoPromocion = _cursoPromocion;
  }

  public Date getFechaInicioPrograma() {
    return _fechaInicioPrograma;
  }

  public void setFechaInicioPrograma(Date _fechaInicioPrograma) {
    this._fechaInicioPrograma = _fechaInicioPrograma;
  }

  public Date getFechaFinPrograma() {
    return _fechaFinPrograma;
  }

  public void setFechaFinPrograma(Date _fechaFinPrograma) {
    this._fechaFinPrograma = _fechaFinPrograma;
  }

  public List<NivelFormacionFuncionarioDTO> getNivelFormacionFuncionaio() {
    return _nivelFormacionFuncionaio;
  }

  public void setNivelFormacionFuncionaio(List<NivelFormacionFuncionarioDTO> _nivelFormacionFuncionaio) {
    this._nivelFormacionFuncionaio = _nivelFormacionFuncionaio;
  }

  public List<Constantes> getListaTipoVinculacion() {
    return _listaTipoVinculacion;
  }

  public void setListaTipoVinculacion(List<Constantes> _listaTipoVinculacion) {
    this._listaTipoVinculacion = _listaTipoVinculacion;
  }

  public List<RecursoHumanoSemillero> getListaRecursoHumanoSemillero() {
    return _listaRecursoHumanoSemillero;
  }

  public void setListaRecursoHumanoSemillero(List<RecursoHumanoSemillero> _listaRecursoHumanoSemillero) {
    this._listaRecursoHumanoSemillero = _listaRecursoHumanoSemillero;
  }

  public Long getTipoVinculacion() {
    return _tipoVinculacion;
  }

  public void setTipoVinculacion(Long _tipoVinculacion) {
    this._tipoVinculacion = _tipoVinculacion;
  }

  public InputText getInputIdentificacion() {
    return _inputIdentificacion;
  }

  public void setInputIdentificacion(InputText _inputIdentificacion) {
    this._inputIdentificacion = _inputIdentificacion;
  }

  public HtmlSelectOneMenu getSelectRelacionSemillero() {
    return _selectRelacionSemillero;
  }

  public void setSelectRelacionSemillero(HtmlSelectOneMenu _selectRelacionSemillero) {
    this._selectRelacionSemillero = _selectRelacionSemillero;
  }

  public InputText getInputCompania() {
    return _inputCompania;
  }

  public void setInputCompania(InputText _inputCompania) {
    this._inputCompania = _inputCompania;
  }

  public InputText getInputCursoPromocion() {
    return _inputCursoPromocion;
  }

  public void setInputCursoPromocion(InputText _inputCursoPromocion) {
    this._inputCursoPromocion = _inputCursoPromocion;
  }

  public RecursoHumanoSemillero[] getRecursoHumanoSeleccionado() {
    return _recursoHumanoSeleccionado;
  }

  public void setRecursoHumanoSeleccionado(RecursoHumanoSemillero[] _recursoHumanoSeleccionado) {
    this._recursoHumanoSeleccionado = _recursoHumanoSeleccionado;
  }

  public ListGenericDataModel<RecursoHumanoSemillero> getListaRecursoHumanoSemilleroModel() {
    return _listaRecursoHumanoSemilleroModel;
  }

  public void setListaRecursoHumanoSemilleroModel(ListGenericDataModel<RecursoHumanoSemillero> _listaRecursoHumanoSemilleroModel) {
    this._listaRecursoHumanoSemilleroModel = _listaRecursoHumanoSemilleroModel;
  }

  public List<SemilleroProyecto> getListaSemillerosProyecto() {
    return _listaSemillerosProyecto;
  }

  public void setListaSemillerosProyecto(List<SemilleroProyecto> _listaSemillerosProyecto) {
    this._listaSemillerosProyecto = _listaSemillerosProyecto;
  }

  public List<SemillerosImplementacion> getListaSemillerosImplementacion() {
    return _listaSemillerosImplementacion;
  }

  public void setListaSemillerosImplementacion(List<SemillerosImplementacion> _listaSemillerosImplementacion) {
    this._listaSemillerosImplementacion = _listaSemillerosImplementacion;
  }

  public SemilleroProyecto getSemilleroProyectoSeleccionado() {
    return _semilleroProyectoSeleccionado;
  }

  public void setSemilleroProyectoSeleccionado(SemilleroProyecto _semilleroProyectoSeleccionado) {
    this._semilleroProyectoSeleccionado = _semilleroProyectoSeleccionado;
  }

  public SemillerosImplementacion getSemilleroImplementacionSeleccionado() {
    return _semilleroImplementacionSeleccionado;
  }

  public void setSemilleroImplementacionSeleccionado(SemillerosImplementacion _semilleroImplementacionSeleccionado) {
    this._semilleroImplementacionSeleccionado = _semilleroImplementacionSeleccionado;
  }

  public InputText getInputActividad() {
    return _inputActividad;
  }

  public void setInputActividad(InputText _inputActividad) {
    this._inputActividad = _inputActividad;
  }

  public InputTextarea getInputObjetivo() {
    return _inputObjetivo;
  }

  public void setInputObjetivo(InputTextarea _inputObjetivo) {
    this._inputObjetivo = _inputObjetivo;
  }

  public InputText getInputResponsable() {
    return _inputResponsable;
  }

  public void setInputResponsable(InputText _inputResponsable) {
    this._inputResponsable = _inputResponsable;
  }

  public InputTextarea getTextAreaProductoResultado() {
    return _textAreaProductoResultado;
  }

  public void setTextAreaProductoResultado(InputTextarea _textAreaProductoResultado) {
    this._textAreaProductoResultado = _textAreaProductoResultado;
  }

  public Calendar getCalendarFechaCumplimiento() {
    return _calendarFechaCumplimiento;
  }

  public void setCalendarFechaCumplimiento(Calendar _calendarFechaCumplimiento) {
    this._calendarFechaCumplimiento = _calendarFechaCumplimiento;
  }

  public boolean isMostrarEnlaceDescargaFoto() {
    return mostrarEnlaceDescargaFoto;
  }

  public void setMostrarEnlaceDescargaFoto(boolean mostrarEnlaceDescargaFoto) {
    this.mostrarEnlaceDescargaFoto = mostrarEnlaceDescargaFoto;
  }

  public boolean isMostrarEnlaceDescarga() {
    return mostrarEnlaceDescarga;
  }

  public void setMostrarEnlaceDescarga(boolean mostrarEnlaceDescarga) {
    this.mostrarEnlaceDescarga = mostrarEnlaceDescarga;
  }

  public String getNombreArchivoEvidenciaFoto() {
    return _nombreArchivoEvidenciaFoto;
  }

  public void setNombreArchivoEvidenciaFoto(String _nombreArchivoEvidenciaFoto) {
    this._nombreArchivoEvidenciaFoto = _nombreArchivoEvidenciaFoto;
  }

  public String getNombreArchivoEvidencia() {
    return _nombreArchivoEvidencia;
  }

  public void setNombreArchivoEvidencia(String _nombreArchivoEvidencia) {
    this._nombreArchivoEvidencia = _nombreArchivoEvidencia;
  }

  public List<CronogramaSemillero> getListaCronogramaSemillero() {
    return _listaCronogramaSemillero;
  }

  public void setListaCronogramaSemillero(List<CronogramaSemillero> _listaCronogramaSemillero) {
    this._listaCronogramaSemillero = _listaCronogramaSemillero;
  }

  public ListGenericDataModel getListaCronogramaSemilleroModel() {
    return _listaCronogramaSemilleroModel;
  }

  public void setListaCronogramaSemilleroModel(ListGenericDataModel _listaCronogramaSemilleroModel) {
    this._listaCronogramaSemilleroModel = _listaCronogramaSemilleroModel;
  }

  public CronogramaSemillero getCronogramaSemillero() {
    return _cronogramaSemillero;
  }

  public void setCronogramaSemillero(CronogramaSemillero _cronogramaSemillero) {
    this._cronogramaSemillero = _cronogramaSemillero;
  }

  public Object getEntidadOrigen() {
    return _entidadOrigen;
  }

  public void setEntidadOrigen(Object _entidadOrigen) {
    this._entidadOrigen = _entidadOrigen;
  }

  public String getTituloProyecto() {
    return _tituloProyecto;
  }

  public void setTituloProyecto(String _tituloProyecto) {
    this._tituloProyecto = _tituloProyecto;
  }

  public String getCodigoProyecto() {
    return _codigoProyecto;
  }

  public void setCodigoProyecto(String _codigoProyecto) {
    this._codigoProyecto = _codigoProyecto;
  }

  public FileUploadEvent getUploadEvidenciaFoto() {
    return _uploadEvidenciaFoto;
  }

  public void setUploadEvidenciaFoto(FileUploadEvent _uploadEvidenciaFoto) {
    this._uploadEvidenciaFoto = _uploadEvidenciaFoto;
  }

  public FileUploadEvent getUploadEvidencia() {
    return _uploadEvidencia;
  }

  public void setUploadEvidencia(FileUploadEvent _uploadEvidencia) {
    this._uploadEvidencia = _uploadEvidencia;
  }

  public CronogramaSemilleroDTO getSeleccionCronograma() {
    return seleccionCronograma;
  }

  public void setSeleccionCronograma(CronogramaSemilleroDTO seleccionCronograma) {
    this.seleccionCronograma = seleccionCronograma;
  }

  public List<CronogramaSemilleroDTO> getListaCronogramaSemilleroDTO() {
    return _listaCronogramaSemilleroDTO;
  }

  public void setListaCronogramaSemilleroDTO(List<CronogramaSemilleroDTO> _listaCronogramaSemilleroDTO) {
    this._listaCronogramaSemilleroDTO = _listaCronogramaSemilleroDTO;
  }

  public String getTrabajoIndependiente() {
    return _trabajoIndependiente;
  }

  public void setTrabajoIndependiente(String _trabajoIndependiente) {
    this._trabajoIndependiente = _trabajoIndependiente;
  }

  public String getTemaTituloTrabajo() {
    return _temaTituloTrabajo;
  }

  public void setTemaTituloTrabajo(String _temaTituloTrabajo) {
    this._temaTituloTrabajo = _temaTituloTrabajo;
  }

  public String getActividadCronograma() {
    return _actividadCronograma;
  }

  public void setActividadCronograma(String _actividadCronograma) {
    this._actividadCronograma = _actividadCronograma;
  }

  public String getObjetivoCronograma() {
    return _objetivoCronograma;
  }

  public void setObjetivoCronograma(String _objetivoCronograma) {
    this._objetivoCronograma = _objetivoCronograma;
  }

  public String getResponsableCronograma() {
    return _responsableCronograma;
  }

  public void setResponsableCronograma(String _responsableCronograma) {
    this._responsableCronograma = _responsableCronograma;
  }

  public String getProductoResultado() {
    return _productoResultado;
  }

  public void setProductoResultado(String _productoResultado) {
    this._productoResultado = _productoResultado;
  }

  public Date getFechaCumplimiento() {
    return _fechaCumplimiento;
  }

  public void setFechaCumplimiento(Date _fechaCumplimiento) {
    this._fechaCumplimiento = _fechaCumplimiento;
  }

  public InputTextarea getTemaTituloTrabajoTextArea() {
    return _temaTituloTrabajoTextArea;
  }

  public void setTemaTituloTrabajoTextArea(InputTextarea _temaTituloTrabajoTextArea) {
    this._temaTituloTrabajoTextArea = _temaTituloTrabajoTextArea;
  }

  public List<HorarioReunionesDTO> getListaHorarioReuniones() {
    return _listaHorarioReuniones;
  }

  public void setListaHorarioReuniones(List<HorarioReunionesDTO> _listaHorarioReuniones) {
    this._listaHorarioReuniones = _listaHorarioReuniones;
  }

  public List<LugarGeograficoDTO> getListaDepartamentos() {
    return _listaDepartamentos;
  }

  public void setListaDepartamentos(List<LugarGeograficoDTO> _listaDepartamentos) {
    this._listaDepartamentos = _listaDepartamentos;
  }

  public List<LugarGeografico> getListaCiudadesDepartamento() {
    return _listaCiudadesDepartamento;
  }

  public void setListaCiudadesDepartamento(List<LugarGeografico> _listaCiudadesDepartamento) {
    this._listaCiudadesDepartamento = _listaCiudadesDepartamento;
  }

  public Long getDepartamentoEvento() {
    return _departamentoEvento;
  }

  public void setDepartamentoEvento(Long _departamentoEvento) {
    this._departamentoEvento = _departamentoEvento;
  }

  public InputText getInputNombreEvento() {
    return _inputNombreEvento;
  }

  public void setInputNombreEvento(InputText _inputNombreEvento) {
    this._inputNombreEvento = _inputNombreEvento;
  }

  public InputText getInputLugarEvento() {
    return _inputLugarEvento;
  }

  public void setInputLugarEvento(InputText _inputLugarEvento) {
    this._inputLugarEvento = _inputLugarEvento;
  }

  public Calendar getCalendarFechaInicialEvento() {
    return _calendarFechaInicialEvento;
  }

  public void setCalendarFechaInicialEvento(Calendar _calendarFechaInicialEvento) {
    this._calendarFechaInicialEvento = _calendarFechaInicialEvento;
  }

  public HtmlSelectOneMenu getSelectDepartamentoEvento() {
    return _selectDepartamentoEvento;
  }

  public void setSelectDepartamentoEvento(HtmlSelectOneMenu _selectDepartamentoEvento) {
    this._selectDepartamentoEvento = _selectDepartamentoEvento;
  }

  public HtmlSelectOneMenu getSelectCiudadEvento() {
    return _selectCiudadEvento;
  }

  public void setSelectCiudadEvento(HtmlSelectOneMenu _selectCiudadEvento) {
    this._selectCiudadEvento = _selectCiudadEvento;
  }

  public InputText getInputClaseTemaPonencia() {
    return _inputClaseTemaPonencia;
  }

  public void setInputClaseTemaPonencia(InputText _inputClaseTemaPonencia) {
    this._inputClaseTemaPonencia = _inputClaseTemaPonencia;
  }

  public InputText getInputNumeroOficiales() {
    return _inputNumeroOficiales;
  }

  public void setInputNumeroOficiales(InputText _inputNumeroOficiales) {
    this._inputNumeroOficiales = _inputNumeroOficiales;
  }

  public Long getCiudadEvento() {
    return _ciudadEvento;
  }

  public void setCiudadEvento(Long _ciudadEvento) {
    this._ciudadEvento = _ciudadEvento;
  }

  public String getNombreEventoSemillero() {
    return _nombreEventoSemillero;
  }

  public void setNombreEventoSemillero(String _nombreEventoSemillero) {
    this._nombreEventoSemillero = _nombreEventoSemillero;
  }

  public String getLugarEventoSemillero() {
    return _lugarEventoSemillero;
  }

  public void setLugarEventoSemillero(String _lugarEventoSemillero) {
    this._lugarEventoSemillero = _lugarEventoSemillero;
  }

  public Date getFechaInicialEventoSemillero() {
    return _fechaInicialEventoSemillero;
  }

  public void setFechaInicialEventoSemillero(Date _fechaInicialEventoSemillero) {
    this._fechaInicialEventoSemillero = _fechaInicialEventoSemillero;
  }

  public Date getFechaFinalEventoSemillero() {
    return _fechaFinalEventoSemillero;
  }

  public void setFechaFinalEventoSemillero(Date _fechaFinalEventoSemillero) {
    this._fechaFinalEventoSemillero = _fechaFinalEventoSemillero;
  }

  public String getClaseTemaPonencia() {
    return _claseTemaPonencia;
  }

  public void setClaseTemaPonencia(String _claseTemaPonencia) {
    this._claseTemaPonencia = _claseTemaPonencia;
  }

  public Integer getNumeroOficiales() {
    return _numeroOficiales;
  }

  public void setNumeroOficiales(Integer _numeroOficiales) {
    this._numeroOficiales = _numeroOficiales;
  }

  public Integer getNumeroSubOficiales() {
    return _numeroSubOficiales;
  }

  public void setNumeroSubOficiales(Integer _numeroSubOficiales) {
    this._numeroSubOficiales = _numeroSubOficiales;
  }

  public Integer getNumeroEstudiantes() {
    return _numeroEstudiantes;
  }

  public void setNumeroEstudiantes(Integer _numeroEstudiantes) {
    this._numeroEstudiantes = _numeroEstudiantes;
  }

  public Integer getNumeroNoUniformados() {
    return _numeroNoUniformados;
  }

  public void setNumeroNoUniformados(Integer _numeroNoUniformados) {
    this._numeroNoUniformados = _numeroNoUniformados;
  }

  public EventosCapacitacionSemilleroDTO getEventoSemillero() {
    return _eventoSemillero;
  }

  public void setEventoSemillero(EventosCapacitacionSemilleroDTO _eventoSemillero) {
    this._eventoSemillero = _eventoSemillero;
  }

  public List<EventosCapacitacionSemilleroDTO> getListaEventosSemillero() {
    return _listaEventosSemillero;
  }

  public void setListaEventosSemillero(List<EventosCapacitacionSemilleroDTO> _listaEventosSemillero) {
    this._listaEventosSemillero = _listaEventosSemillero;
  }

  public List<Constantes> getListaModalidadParticipacion() {
    return _listaModalidadParticipacion;
  }

  public void setListaModalidadParticipacion(List<Constantes> _listaModalidadParticipacion) {
    this._listaModalidadParticipacion = _listaModalidadParticipacion;
  }

  public Long getModalidadParticipacion() {
    return _modalidadParticipacion;
  }

  public void setModalidadParticipacion(Long _modalidadParticipacion) {
    this._modalidadParticipacion = _modalidadParticipacion;
  }

  public ListGenericDataModel<EventosCapacitacionSemilleroDTO> getListaEventosSemilleroModel() {
    return _listaEventosSemilleroModel;
  }

  public void setListaEventosSemilleroModel(ListGenericDataModel<EventosCapacitacionSemilleroDTO> _listaEventosSemilleroModel) {
    this._listaEventosSemilleroModel = _listaEventosSemilleroModel;
  }

  public InputText getInputMotivoEstimulo() {
    return _inputMotivoEstimulo;
  }

  public void setInputMotivoEstimulo(InputText _inputMotivoEstimulo) {
    this._inputMotivoEstimulo = _inputMotivoEstimulo;
  }

  public InputTextarea getTextAreaDescripcionMotivoEstimulo() {
    return _textAreaDescripcionMotivoEstimulo;
  }

  public void setTextAreaDescripcionMotivoEstimulo(InputTextarea _textAreaDescripcionMotivoEstimulo) {
    this._textAreaDescripcionMotivoEstimulo = _textAreaDescripcionMotivoEstimulo;
  }

  public Calendar getCalendarFechaOtorgamiento() {
    return _calendarFechaOtorgamiento;
  }

  public void setCalendarFechaOtorgamiento(Calendar _calendarFechaOtorgamiento) {
    this._calendarFechaOtorgamiento = _calendarFechaOtorgamiento;
  }

  public String getMotivoEstimulo() {
    return _motivoEstimulo;
  }

  public void setMotivoEstimulo(String _motivoEstimulo) {
    this._motivoEstimulo = _motivoEstimulo;
  }

  public String getDescMotivoEstimulo() {
    return _descMotivoEstimulo;
  }

  public void setDescMotivoEstimulo(String _descMotivoEstimulo) {
    this._descMotivoEstimulo = _descMotivoEstimulo;
  }

  public Date getFechaOtrogamiento() {
    return _fechaOtrogamiento;
  }

  public void setFechaOtrogamiento(Date _fechaOtrogamiento) {
    this._fechaOtrogamiento = _fechaOtrogamiento;
  }

  public List<RecursoHumanoSemilleroDTO> getListaRecursosSemilleroEstimuloDTO() {
    return _listaRecursosSemilleroEstimuloDTO;
  }

  public void setListaRecursosSemilleroEstimuloDTO(List<RecursoHumanoSemilleroDTO> _listaRecursosSemilleroEstimuloDTO) {
    this._listaRecursosSemilleroEstimuloDTO = _listaRecursosSemilleroEstimuloDTO;
  }

  public List<EstimulosSemilleroDTO> getListaEstimulosSemillero() {
    return _listaEstimulosSemillero;
  }

  public void setListaEstimulosSemillero(List<EstimulosSemilleroDTO> _listaEstimulosSemillero) {
    this._listaEstimulosSemillero = _listaEstimulosSemillero;
  }

  public ListGenericDataModel<RecursoHumanoSemilleroDTO> getListaRecursosSemilleroEstimuloDTOModel() {
    return _listaRecursosSemilleroEstimuloDTOModel;
  }

  public void setListaRecursosSemilleroEstimuloDTOModel(ListGenericDataModel<RecursoHumanoSemilleroDTO> _listaRecursosSemilleroEstimuloDTOModel) {
    this._listaRecursosSemilleroEstimuloDTOModel = _listaRecursosSemilleroEstimuloDTOModel;
  }

  public ListGenericDataModel<EstimulosSemilleroDTO> getListaEstimulosSemilleroModel() {
    return _listaEstimulosSemilleroModel;
  }

  public void setListaEstimulosSemilleroModel(ListGenericDataModel<EstimulosSemilleroDTO> _listaEstimulosSemilleroModel) {
    this._listaEstimulosSemilleroModel = _listaEstimulosSemilleroModel;
  }

  public RecursoHumanoSemilleroDTO[] getParticipantesOtorgados() {
    return _participantesOtorgados;
  }

  public void setParticipantesOtorgados(RecursoHumanoSemilleroDTO[] _participantesOtorgados) {
    this._participantesOtorgados = _participantesOtorgados;
  }

  public EstimulosSemilleroDTO getEstimuloSemilleroSeleccionado() {
    return _estimuloSemilleroSeleccionado;
  }

  public void setEstimuloSemilleroSeleccionado(EstimulosSemilleroDTO _estimuloSemilleroSeleccionado) {
    this._estimuloSemilleroSeleccionado = _estimuloSemilleroSeleccionado;
  }
}
