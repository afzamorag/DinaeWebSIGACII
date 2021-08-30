/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.Carreras1;
import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EventoEscuelaFiltro;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipante;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.SieduConvenio;
import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.Presupuesto;
import co.gov.policia.dinae.siedu.modelo.SieduConvocatoriaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduHorasDocenteEventoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.DominioService;
import co.gov.policia.dinae.siedu.servicios.EvaluacionParticipanteService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.servicios.SieduConvocatoriaEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduDocenteEventoService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduEventoEscuelaService;
import co.gov.policia.dinae.siedu.servicios.SieduEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import co.gov.policia.dinae.siedu.util.date.DateUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import co.gov.policia.dinae.moodle.MoodleCapacitacion;
import co.gov.policia.dinae.siedu.constantes.ModalidadEnum;

/**
 * description
 *
 * @author: ANDRES.ZAMORAG <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEvaluacionEvento;
import co.gov.policia.dinae.siedu.servicios.EvaluacionService;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendDataService;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendHttpService;
import co.gov.policia.dinae.siedu.servicios.SieduAlumnosService;
import co.gov.policia.dinae.siedu.servicios.SieduEvaluacionEventoService;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@SessionScoped
public class SieduEventoEscuelaController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SieduEventoEscuelaController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private SieduEventoEscuelaService service;
    @EJB
    private PAEService servicePAE;
    @EJB
    APPService serviceAPP;
    @EJB
    private ILugarGeograficoLocal lugarGeografico;
    @EJB
    private IUnidadDependenciaLocal unidadDependencia;
    @EJB
    private SieduEventoService serviceEvento;
    @EJB
    private DominioService serviceDominio;
    @EJB
    private SieduConvocatoriaEventoService serviceConvocatoria;
    @EJB
    private SieduParticipanteEventoService serviceParticipante;
    @EJB
    private SieduDocenteEventoService serviceDocente;
    @EJB
    private EvaluacionParticipanteService serviceEvaluacionParticipante;
    @EJB
    private SieduAlumnosService serviceAlumno;
    @EJB
    private LogMoodleSendDataService serviceLog;
    @EJB
    private LogMoodleSendHttpService serviceUri;
    @EJB
    private EvaluacionService serviceEvaluacion;
    @EJB
    private SieduEvaluacionEventoService serviceEvaluacionEvento;

    private List<SieduEvaluacionEvento> listEvaluacionEvento;
    private List<NivelesAcademicos> listNivelAcademico;
    private List<SieduEventoEscuela> list;
    private List<PAE> vigencias;
    private List<Dominio> modalidad;
    private List<LugarGeografico> ciudad;
    private List<Dominio> recursoFinancia;
    private List<UnidadDependencia> unidades;
    private boolean editable;
    private NavEnum optionNavEnum;
    private UnidadDependencia escuela;
    private List<UnidadDependencia> escuelas;
    private Long nivelAcademico = -1L;
    private Carreras1 carreras;
    private EventoEscuelaFiltro eventoEscuelaFiltro;
    private SieduEntidad entidad;
    private SieduConvenio convenio;
    private Presupuesto presupuesto;
    private SieduEvento evento;
    private List<SieduDocenteEvento> docente;
    private List<Integer> days;
    private String finicioRestrict = "";
    private String ffinRestrict = "";
    private String finiMin = "";
    private boolean externo = false;
    private boolean contrato = false;
    private boolean disabledFechaFinal = true;
    private boolean validaRol = false;
    private boolean validaRolTelem = false;
    private boolean renderTabView = false;
    private boolean renderButtom = false;
    private SieduEventoEscuela selected;
    private Integer totalDiasMax;
    private int totalDiasMin;
    private String calificacionGeneralEvento;
    private static final List<String> CONTROLLERS = Arrays.asList("sieduDocenteEventoController", "sieduInasistenteEventoController");
    private MoodleCapacitacion moodleMigrate;
    private SieduDocenteEventoController docenteEventoController;
    private SieduParticipanteEventoController participanteEventoController = new SieduParticipanteEventoController();
    private SieduConvocatoriaEventoController convocatoriaEventoController = new SieduConvocatoriaEventoController();
    private SieduInasistenteEventoController inasistenteEventoController = new SieduInasistenteEventoController();
    private static DecimalFormat df2;
    private Calendar fechaMaxCal;
    private Calendar fechaMinCal;
    private Date fechaMax;
    private Date fechaMin;
    private boolean renderCalendar;

    public SieduEventoEscuelaController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        optionNavEnum = NavEnum.LIST;
        setSelected(null);
        setEditable(false);
        eventoEscuelaFiltro = new EventoEscuelaFiltro();
        eventoEscuelaFiltro.setCapaUdeEscu(-1L);
        eventoEscuelaFiltro.setIdModalidad(-1L);
        eventoEscuelaFiltro.setIdCarrera(-1L);
        entidad = new SieduEntidad();
        convenio = new SieduConvenio();
        presupuesto = new Presupuesto();
        evento = new SieduEvento();
        loadVigencias();
        loadModalidad();
        loadEscuelas();
        loadNivelesAcademicos();
        municipios();
        loadRecursos();
        loadUnidades();
        totalDiasMax = 0;
        totalDiasMin = 0;
        this.calificacionGeneralEvento = "";
        validaRol();
        validaRolTelem();
        this.moodleMigrate = new MoodleCapacitacion(this);
        df2 = new DecimalFormat(".##");
        this.docenteEventoController = this.findManagedBean("sieduDocenteEventoController");
        this.participanteEventoController = this.findManagedBean("sieduParticipanteEventoController");
        this.convocatoriaEventoController = this.findManagedBean("sieduConvocatoriaEventoController");
        this.inasistenteEventoController = this.findManagedBean("sieduInasistenteEventoController");
        this.fechaMaxCal = Calendar.getInstance();
        this.fechaMinCal = Calendar.getInstance();
        this.fechaMinCal.add(Calendar.DATE, -3);
        this.fechaMax = new Date();
        this.fechaMin = new Date();
        this.fechaMax = this.fechaMaxCal.getTime();
        this.fechaMin = this.fechaMinCal.getTime();
        this.renderCalendar = false;

    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        this.initialize();
        releaseControllers();
        return navigationFaces.redirect_CU09_CAP_BUSCAR_EVENTO_ESCUELA();
    }

    public boolean validaRol() {
        validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        return validaRol;
    }

    public boolean validaRolTelem() {
        validaRolTelem = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        return validaRolTelem;
    }

    private void loadNivelesAcademicos() {
        LOG.trace("metodo: loadNivelesAcademicos()");
        try {
            setListNivelAcademico(serviceAPP.consultarNivelesAcademicos());
        } catch (Exception ex) {
            LOG.error("metodo: loadCarreras() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    /**
     *
     */
    public void loadList() {
        LOG.trace("metodo: loadList()");
        try {
            setList(service.findAll());
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadEscuelas() {
        LOG.trace("metodo: loadEscuelas()");
        try {
            setEscuelas(this.serviceAPP.consultarEscuelasVigentes());
            this.escuelas.add(this.unidadDependencia.getUnidadDependenciaById(23536L));
        } catch (Exception ex) {
            LOG.error("metodo: loadEscuelas() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    /**
     *
     */
    public void findByFilter() {
        LOG.trace("metodo: findByFilter()");
        try {
            if (validaRol == false) {
                eventoEscuelaFiltro.setCapaUdeEscu(loginFaces.getPerfilUsuarioDTO().getUnidadDependencia());
            }
            setList(service.findByFilter(eventoEscuelaFiltro));
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    /**
     *
     */
    private void initializeEdit() {
        LOG.trace("metodo: initializeEdit()");
        setEditable(true);
    }

    /**
     *
     */
    public void onCreate() {
        LOG.trace("metodo: onCreate()");
        optionNavEnum = NavEnum.DETAILS;
        setSelected(new SieduEventoEscuela());
        initializeEdit();
    }

    public void minEndDateSelectEvent() throws ParseException {
        Calendar c = Calendar.getInstance();
        this.minDiasEvento();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.setFiniMin(df.format(this.selected.getEveeFechaini()));
        c.setTime(df.parse(finiMin));
        c.add(Calendar.DATE, (this.totalDiasMin));
        this.setFiniMin(df.format(c.getTime()));
    }

    public void totalDiasEvento() throws ParseException {
        this.days = new ArrayList<>();
        this.minDiasEvento();
        Calendar cinicio = Calendar.getInstance();
        Calendar cfinal = Calendar.getInstance();
        cinicio.setTime(selected.getEveeFechaini());
        cfinal.setTime(selected.getEveeFechafin());
        totalDiasMax = (DateUtil.daysBetween(cinicio, cfinal));
        totalDiasMax = totalDiasMax + 1;
        totalDiasMin = totalDiasMin + 1;
        int x = totalDiasMax - totalDiasMin;
        for (int i = 0; i <= x; i++) {
            days.add(this.totalDiasMin);
            totalDiasMin++;
        }
    }

    public void onCloseEvento() {
        LOG.debug("metodo: onCerrarEvento()");
        try {
            List<SieduConvocatoriaEvento> listConvocatoria = serviceConvocatoria.findListById(selected);
            if (listConvocatoria.isEmpty()) {
                addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.close.nodatafound.fk.convocatoria.sumary"), getPropertyFromBundle("siedueventoescuela.msg.error.close.nodatafound.fk.convocatoria.detail"));
            } else {
                List<SieduParticipanteEvento> listParticipantes = serviceParticipante.findListById(selected);
                if (listParticipantes.isEmpty()) {
                    addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.close.nodatafound.fk.participante.sumary"), getPropertyFromBundle("siedueventoescuela.msg.error.close.nodatafound.fk.participante.detail"));
                } else {
                    List<SieduHorasDocenteEventoDTO> listDocente = serviceDocente.findListByIdDistinct(selected);
                    if (listDocente.isEmpty()) {
                        addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.close.nodatafound.fk.docente.sumary"), getPropertyFromBundle("siedueventoescuela.msg.error.close.nodatafound.fk.docente.detail"));
                    } else {
                        int horasDictadas = 0;
                        Short horasEvento = 0;
                        horasEvento = serviceEvento.findById(selected.getEveeEven().getId()).getEvenIntenHoras();
                        for (SieduHorasDocenteEventoDTO docentes : listDocente) {
                            horasDictadas += docentes.getHorasTema();
                        }
                        if (horasDictadas < horasEvento) {
                            addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.close.fk.docente.tema.sumary"), getPropertyFromBundle("siedueventoescuela.msg.error.close.fk.docente.tema.detail"));
                        } else {
                            List<EvaluacionParticipante> listEvalParti = serviceEvaluacionParticipante.findByPareEvee(selected);
                            String usuarioLogueado = (loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                            if (selected.getEvaluacion() == null) {
                                Evaluacion evaluacion = service.consultarEvaluacionEvento(selected);
                                if (evaluacion == null) {
                                    addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.close.fk.evaluacion.sumary"), getPropertyFromBundle("siedueventoescuela.msg.error.close.fk.evaluacion.detail"));
                                } else {
                                    selected.setEvaluacion(evaluacion);
                                    service.cerrarEvento(usuarioLogueado, selected);
                                    findByFilter();
                                    selected = service.findById(selected.getEveeEvee());
                                    renderButtom = selected.getEveeCerrado().equals(DecisionEnum.NO.toString());
                                }
                            } else {
                                service.cerrarEvento(usuarioLogueado, selected);
                                findByFilter();
                                selected = service.findById(selected.getEveeEvee());
                                renderButtom = selected.getEveeCerrado().equals(DecisionEnum.NO.toString());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public void onOpenEvento() throws ServiceException {
        LOG.trace("metodo: onOpenEvento() ->> parametros:{}");
        try {
            selected.setEveeCerrado(DecisionEnum.NO.toString());
            selected.setEveeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            service.update(getSelected());
            findByFilter();
            selected = service.findById(selected.getEveeEvee());
            renderButtom = selected.getEveeCerrado().equals(DecisionEnum.NO.toString());
        } catch (Exception ex) {
        }
    }

    public void initCUEvento() {
        this.docenteEventoController.initialize();
        this.participanteEventoController.initialize();
        this.convocatoriaEventoController.initialize();
        this.inasistenteEventoController.initialize();
    }

    /**
     *
     * @param evenEscuela
     * @return
     * @throws ServiceException
     */
    public Evaluacion colsultEvaluation(SieduEventoEscuela evenEscuela) throws ServiceException {
        try {
            Evaluacion evaluacion = new Evaluacion();
            SieduEvento evenEval = serviceEvento.findByIdCarreraVigente(evenEscuela);
            evaluacion = serviceEvaluacion.findForDevelopByEvent(evenEval.getId(), evenEscuela.getEveeCapa().getPae());
            return evaluacion;
        } catch (Exception ex) {
            LOG.error("metodo: colsultEvaluation() ->> mensaje: {}", ex.getMessage());
            return null;
        }
    }

    public void restrictDateEvent() {
        LOG.trace("metodo: restrictDateEvent() ->> parametros: event {}");
        switch (selected.getEveeTrimes()) {
            case 1:
                finicioRestrict = "01-01-" + selected.getEveeCapa().getPae().getVigencia();
                ffinRestrict = "31-03-" + selected.getEveeCapa().getPae().getVigencia();
                break;
            case 2:
                finicioRestrict = "01-04-" + selected.getEveeCapa().getPae().getVigencia();
                ffinRestrict = "30-06-" + selected.getEveeCapa().getPae().getVigencia();
                break;
            case 3:
                finicioRestrict = "01-07-" + selected.getEveeCapa().getPae().getVigencia();
                ffinRestrict = "30-09-" + selected.getEveeCapa().getPae().getVigencia();
                break;
            case 4:
                finicioRestrict = "01-10-" + selected.getEveeCapa().getPae().getVigencia();
                ffinRestrict = "31-12-" + selected.getEveeCapa().getPae().getVigencia();
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param event
     * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
     */
    public void onRowSelect(SelectEvent event) throws ServiceException {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        setSelected((SieduEventoEscuela) event.getObject());
        try {
            //if (this.colsultEvaluation(selected) != null) {
            if (selected.getEveeFechaini() == null) {
                evento = serviceEvento.findByIdCarreraVigente(selected);
                if (evento != null) {
                    this.removeViewScopedBeans(CONTROLLERS);
                    editable = (selected.getEveeFechaini() == null);
                    renderTabView = (selected.getEveeFechaini() == null);
                    renderButtom = selected.getEveeCerrado().equals(DecisionEnum.NO.toString());
                    optionNavEnum = NavEnum.DETAILS;
                    externo = selected.getEveeCapa().getExterno().equals(DecisionEnum.NO.toString());
                    contrato = selected.getEveeCapa().getManejaPresupuesto().equals(DecisionEnum.NO.toString());
                    this.restrictDateEvent();

                } else {
                    addErrorMessage(getPropertyFromBundle("siedueventoesuela.msg.error.nodatafound.fk.evento.sumary"), getPropertyFromBundle("siedueventoesuela.msg.error.nodatafound.fk.evento.detail"));
                }
            } else {
                this.initCUEvento();
                this.removeViewScopedBeans(CONTROLLERS);
                editable = (selected.getEveeFechaini() == null);
                renderTabView = (selected.getEveeFechaini() == null);
                renderButtom = selected.getEveeCerrado().equals(DecisionEnum.NO.toString());
                optionNavEnum = NavEnum.DETAILS;
                externo = selected.getEveeCapa().getExterno().equals(DecisionEnum.NO.toString());
                contrato = selected.getEveeCapa().getManejaPresupuesto().equals(DecisionEnum.NO.toString());
                this.restrictDateEvent();
                if (this.fechaMinCal.getTime().before(this.selected.getEveeFechaini())) {
                    this.fechaMin = this.selected.getEveeFechaini();
                }
                if (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString())) {
                    this.fechaMax = selected.getEveeFechafin();
                    this.fechaMin = selected.getEveeFechaini();
                    this.renderCalendar = true;
                } else if ((selected.getEveeFechaini().before(this.fechaMaxCal.getTime()) || selected.getEveeFechaini().equals(this.fechaMaxCal.getTime())) && (selected.getEveeFechafin().after(this.fechaMinCal.getTime()) || selected.getEveeFechafin().equals(this.fechaMinCal.getTime()))) {
                    renderCalendar = true;
                } else {
                    renderCalendar = false;
                }
                this.listEvaluacionEvento = this.serviceEvaluacionEvento.findById(this.selected.getEveeEvee());
                //this.calificacionGeneralEvento = this.calcularEvaluacionGeneral(listEvaluacionEvento);

            }
            //} else {
            //  addErrorMessage(getPropertyFromBundle("siedueventoesuela.msg.error.nodatafound.fk.evaluacion.sumary"), getPropertyFromBundle("siedueventoesuela.msg.error.nodatafound.fk.evaluacion.detail"));
            //}
        } catch (Exception ex) {
            LOG.error("metodo: onRowSelect() ->> mensaje: {}", ex.getMessage());
        }
    }

    /*public String calcularEvaluacionGeneral(List<SieduEvaluacionEvento> lst) {
        double d = 0;
        double scale = Math.pow(10, 2);
        String dd;
        for (SieduEvaluacionEvento s : lst) {
            d = d + ((s.getCalificacion() * s.getPorcentaje()) / 100);
        }
        return dd = df2.format(d);
    }*/
    public void municipios() {
        LOG.trace("method: municipios()");
        try {
            List<String> tiposMun = new ArrayList<>();
            tiposMun.add("CM");
            setCiudad(lugarGeografico.findLugarGeoggraficoByTipo(tiposMun));
        } catch (Exception ex) {
            LOG.error("Error en <<escuelas>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    private void loadModalidad() {
        LOG.trace("metodo: loadEstrategias()");
        try {
            setModalidad(this.serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD.getId()));
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public String getRecurso() {
        LOG.trace("metodo: getRecurso()");
        try {
            String recurso = serviceDominio.findById(selected.getEveeDomFinancia()).getNombre();
            return recurso;
        } catch (Exception ex) {
            LOG.error("metodo: getRecurso() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            return null;
        }
    }

    private void loadRecursos() {
        LOG.trace("metodo: loadEstrategias()");
        try {
            setRecursoFinancia(this.serviceAPP.consultarDominios(TipoDominioEnum.TIPO_FINANCIACION.getId()));
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadVigencias() {
        LOG.trace("metodo: loadVigencias()");
        try {
            setVigencias(servicePAE.consultarVigencias());
        } catch (Exception ex) {
            LOG.error("metodo: loadVigencias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadUnidades() {
        LOG.trace("metodo: loadUnidades()");
        try {
            setUnidades(this.serviceAPP.consultarUnidadesVigentes());
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    /**
     *
     */
    public void onEdit() {
        LOG.trace("metodo: onEdit()");
        initializeEdit();
    }

    /**
     *
     */
    public void onDelete() {
        LOG.trace("metodo: onDelete()");
        try {
            service.delete(getSelected());
            initialize();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    /**
     *
     */
    public void minDiasEvento() {
        LOG.debug("metodo: minDiasEvento()");
        try {
            SieduEvento evento = serviceEvento.findByIdCarreraVigente(selected);
            int totalHorasEvento = evento.getEvenIntenHoras();
            totalDiasMin = (int) Math.round(totalHorasEvento / 10);
            if ((totalHorasEvento % 10 == 0) && (totalDiasMin > 0)) {
                totalDiasMin = totalDiasMin - 1;
            }
        } catch (Exception ex) {
            LOG.error("metodo: minDiasEvento() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    public void rollbackEvent() {
        LOG.trace("método: rollbackEvent()");
        try {
            this.selected.setEveeFechaini(null);
            this.selected.setEveeFechafin(null);
            this.selected.setEveeUdeUdepe(null);
            this.selected.setEveeDomFinancia(null);
            this.selected.setEveeCostoCapa(null);
            this.selected.setEveeNroDias(null);
            this.selected.setLugarGeografico(null);
            this.selected.setEveeEven(null);
            this.selected.setEveeUsuMod(this.loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            this.service.update(this.selected);
        } catch (Exception ex) {
            LOG.error("metodo: rollbackEvent() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
        }
    }

    /**
     *
     */
    public void onSave() {
        //Método valida la modalidad del evento a fin de determinar si se debe migrar a Moodle
        LOG.trace("metodo: onSave()");
        try {
            if (migrateToMoodle()) {
                if (this.selected.getEveeUsuMod() == null) {
                    if (this.saveEvent()) {
                        if (!this.moodleMigrate.onSave()) {
                            this.rollbackEvent();
                            this.addErrorMessage(getPropertyFromBundle("siedueventoescuela.moodle.msg.error.save.createevent.summary"), getPropertyFromBundle("siedueventoescuela.moodle.msg.error.save.createevent.detail"));
                        }
                    } else {
                        //this.moodleMigrate.cursoUpdateOnly();
                        this.addErrorMessage(getPropertyFromBundle("siedueventoescuela.moodle.msg.error.save.createevent.summary"), getPropertyFromBundle("siedueventoescuela.moodle.msg.error.save.createevent.detail"));
                    }
                } else {
                    this.saveEvent();
                    this.moodleMigrate.cursoUpdateOnly();
                }
            } else {
                this.saveEvent();
            }
            if (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString())) {
                this.fechaMax = selected.getEveeFechafin();
                this.fechaMin = selected.getEveeFechaini();
                this.renderCalendar = true;
            } else if ((selected.getEveeFechaini().before(this.fechaMaxCal.getTime()) || selected.getEveeFechaini().equals(this.fechaMaxCal.getTime())) && selected.getEveeFechafin().after(this.fechaMinCal.getTime())) {
                renderCalendar = true;
            } else {
                renderCalendar = false;
            }
        } catch (Exception ex) {
            LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
        }
    }

    public boolean migrateToMoodle() {
        if (((selected.getEveeCapa().getModalidad().getId().compareTo(ModalidadEnum.VIRTUAL.getId()) == 0)
                || (selected.getEveeCapa().getModalidad().getId().compareTo(ModalidadEnum.SEMIPRESENCIAL.getId()) == 0))
                && (selected.getEveeCapa().getExterno().equals(DecisionEnum.NO.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveEvent() {
        //Método persiste el evento en SIGAC, diseñado previo a la integración con Moodle
        LOG.trace("metodo: saveEvent()");
        try {
            if (getSelected().getEveeEvee() == null) {
                selected.setEveeEven(serviceEvento.findByIdCarreraVigente(selected));
                selected.setEveeUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.create(getSelected());
                renderTabView = false;
                this.initCUEvento();
                return true;
            } else if (selected.getEveeEven() == null) {
                selected.setEveeEven(serviceEvento.findByIdCarreraVigente(selected));
                selected.setEveeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.update(getSelected());
                renderTabView = false;
                setEditable(false);
                this.initCUEvento();
                return true;
            } else {
                SieduFechasMaxMinEventoDTO fechasDTO = new SieduFechasMaxMinEventoDTO();
                fechasDTO = service.consultasFechasMaxMinInasistencia(selected);
                if (fechasDTO.getMinFecha() == null && fechasDTO.getMaxFecha() == null) {
                    selected.setEveeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    service.update(getSelected());
                    setEditable(false);
                    addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                    this.initCUEvento();
                    return true;
                } else if (selected.getEveeFechaini().after(fechasDTO.getMinFecha())) {
                    addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.fechaInicioMax.summary"), getPropertyFromBundle("siedueventoescuela.msg.error.fechaInicioMax.detail"));
                    findByFilter();
                    return false;
                } else if (selected.getEveeFechafin().before(fechasDTO.getMaxFecha())) {
                    addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.fechaFinMax.summary"), getPropertyFromBundle("siedueventoescuela.msg.error.fechaFinMax.detail"));
                    findByFilter();
                    return false;
                } else {
                    selected.setEveeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    service.update(getSelected());
                    setEditable(false);
                    addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                    this.initCUEvento();
                    return true;
                }
            }

        } catch (Exception ex) {
            LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            return false;
        }
    }

    /**
     *
     */
    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        setEditable(false);
        if (getSelected().getEveeEvee() == null) {
            optionNavEnum = NavEnum.LIST;
            setSelected(null);
        }
    }

    /**
     *
     */
    public void onBack() {
        LOG.trace("metodo: onBack()");
        try {
            optionNavEnum = NavEnum.LIST;
            setSelected(new SieduEventoEscuela());
            setList(service.findByFilter(eventoEscuelaFiltro));
        } catch (Exception ex) {
            LOG.error("metodo: onBack() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }

    }

    public void moodleMasivo() {
        this.moodleMigrate.moodleMigrate();
    }

    /**
     *
     * @return
     */
    public boolean isShowList() {
        boolean showList = (optionNavEnum == NavEnum.LIST);
        return showList;
    }

    /**
     *
     * @return
     */
    public boolean isShowDetails() {
        boolean showDetails = (optionNavEnum == NavEnum.DETAILS);
        return showDetails;
    }

    /**
     *
     * @return
     */
    public boolean isNewRecord() {
        boolean newRecord = (getSelected() != null && getSelected().getEveeEvee() == null);
        return newRecord;
    }

    public void enableFechaFinal(SelectEvent event) {
        this.disabledFechaFinal = false;
    }

    public void limpiarFechas() {
        try {
            List<SieduConvocatoriaEvento> listConv = serviceConvocatoria.findListById(selected);
            if (listConv.isEmpty()) {
                List<SieduHorasDocenteEventoDTO> listDocent = serviceDocente.findListByIdDistinct(selected);
                if (listDocent.isEmpty()) {
                    List<SieduParticipanteEvento> listPart = serviceParticipante.findListById(selected);
                    if (listPart.isEmpty()) {
                        List<EvaluacionParticipante> listEvalPart = serviceEvaluacionParticipante.findByPareEvee(selected);
                        if (listEvalPart.isEmpty()) {
                            this.selected.setEveNroAprobados(null);
                            this.selected.setEveNroNoaprobados(null);
                            this.selected.setEveeCostoCapa(null);
                            this.selected.setEveeEnti(null);
                            this.selected.setEveeDomFinancia(null);
                            this.selected.setEveeEnti(null);
                            this.selected.setEveeFechafin(null);
                            this.selected.setEveeFechaini(null);
                            this.selected.setEveeNroConvocados(null);
                            this.selected.setEveeNroDesertados(null);
                            this.selected.setEveeNroDias(null);
                            this.selected.setEveePpto(null);
                            this.selected.setLugarGeografico(null);
                            this.selected.setEveeUdeUdepe(null);
                            this.service.update(selected);
                        } else {
                            addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.clear.evaluacionesNotEmpty"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
                        }
                    } else {
                        addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.clear.participanteNotEmpty"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
                    }
                } else {
                    addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.clear.docentesNotEmpty"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
                }
            } else {
                addErrorMessage(getPropertyFromBundle("siedueventoescuela.msg.error.clear.convocatoriaNotEmpty"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            }
        } catch (Exception ex) {
            LOG.error("metodo: onBack() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    /**
     * @return the selected
     */
    public SieduEventoEscuela getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(SieduEventoEscuela selected) {
        this.selected = selected;
    }

    /**
     * @return the list
     */
    public List<SieduEventoEscuela> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<SieduEventoEscuela> list) {
        this.list = list;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public UnidadDependencia getEscuela() {
        return escuela;
    }

    public void setEscuela(UnidadDependencia escuela) {
        this.escuela = escuela;
    }

    public List<NivelesAcademicos> getListNivelAcademico() {
        return listNivelAcademico;
    }

    public void setListNivelAcademico(List<NivelesAcademicos> listNivelAcademico) {
        this.listNivelAcademico = listNivelAcademico;
    }

    public List<LugarGeografico> getCiudad() {
        return ciudad;
    }

    public void setCiudad(List<LugarGeografico> ciudad) {
        this.ciudad = ciudad;
    }

    public List<Dominio> getRecursoFinancia() {
        return recursoFinancia;
    }

    public void setRecursoFinancia(List<Dominio> recursoFinancia) {
        this.recursoFinancia = recursoFinancia;
    }

    public List<UnidadDependencia> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadDependencia> unidades) {
        this.unidades = unidades;
    }

    public SieduEvento getEvento() {
        return evento;
    }

    public void setEvento(SieduEvento evento) {
        this.evento = evento;
    }

    public Long getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(Long nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public Carreras1 getCarreras() {
        return carreras;
    }

    public void setCarreras(Carreras1 carreras) {
        this.carreras = carreras;
    }

    public EventoEscuelaFiltro getEventoEscuelaFiltro() {
        return eventoEscuelaFiltro;
    }

    public void setEventoEscuelaFiltro(EventoEscuelaFiltro eventoEscuelaFiltro) {
        this.eventoEscuelaFiltro = eventoEscuelaFiltro;
    }

    public SieduEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(SieduEntidad entidad) {
        this.entidad = entidad;
    }

    public SieduConvenio getConvenio() {
        return convenio;
    }

    public void setConvenio(SieduConvenio convenio) {
        this.convenio = convenio;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getFinicioRestrict() {
        return finicioRestrict;
    }

    public void setFinicioRestrict(String finicioRestrict) {
        this.finicioRestrict = finicioRestrict;
    }

    public String getFfinRestrict() {
        return ffinRestrict;
    }

    public void setFfinRestrict(String ffinRestrict) {
        this.ffinRestrict = ffinRestrict;
    }

    public boolean getExterno() {
        return externo;
    }

    public void setExterno(boolean externo) {
        this.externo = externo;
    }

    public boolean isContrato() {
        return contrato;
    }

    public void setContrato(boolean contrato) {
        this.contrato = contrato;
    }

    public boolean isDisabledFechaFinal() {
        return disabledFechaFinal;
    }

    public void setDisabledFechaFinal(boolean disabledFechaFinal) {
        this.disabledFechaFinal = disabledFechaFinal;
    }

    public List<PAE> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<PAE> vigencias) {
        this.vigencias = vigencias;
    }

    public List<Dominio> getModalidad() {
        return modalidad;
    }

    public void setModalidad(List<Dominio> modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getTotalDiasMax() {
        return totalDiasMax;
    }

    public void setTotalDiasMax(Integer totalDias) {
        this.totalDiasMax = totalDias;
    }

    public int getTotalDiasMin() {
        return totalDiasMin;
    }

    public void setTotalDiasMin(int totalDiasMin) {
        this.totalDiasMin = totalDiasMin;
    }

    public boolean isRenderTabView() {
        return renderTabView;
    }

    public void setRenderTabView(boolean renderTabView) {
        this.renderTabView = renderTabView;
    }

    public boolean isRenderButtom() {
        return renderButtom;
    }

    public void setRenderButtom(boolean renderButtom) {
        this.renderButtom = renderButtom;
    }

    public boolean isValidaRol() {
        return validaRol;
    }

    public void setValidaRol(boolean validaRol) {
        this.validaRol = validaRol;
    }

    public List<SieduDocenteEvento> getDocente() {
        return docente;
    }

    public void setDocente(List<SieduDocenteEvento> docente) {
        this.docente = docente;
    }

    public String getFiniMin() {
        return finiMin;
    }

    public void setFiniMin(String finiMin) {
        this.finiMin = finiMin;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public boolean isValidaRolTelem() {
        return validaRolTelem;
    }

    public void setValidaRolTelem(boolean validaRolTelem) {
        this.validaRolTelem = validaRolTelem;
    }

    public List<SieduEvaluacionEvento> getListEvaluacionEvento() {
        return listEvaluacionEvento;
    }

    public void setListEvaluacionEvento(List<SieduEvaluacionEvento> listEvaluacionEvento) {
        this.listEvaluacionEvento = listEvaluacionEvento;
    }

    public String getCalificacionGeneralEvento() {
        return calificacionGeneralEvento;
    }

    public void setCalificacionGeneralEvento(String calificacionGeneralEvento) {
        this.calificacionGeneralEvento = calificacionGeneralEvento;
    }

    public Date getFechaMax() {
        return fechaMax;
    }

    public void setFechaMax(Date fechaMax) {
        this.fechaMax = fechaMax;
    }

    public Date getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(Date fechaMin) {
        this.fechaMin = fechaMin;
    }

    public boolean isRenderCalendar() {
        return renderCalendar;
    }

    public void setRenderCalendar(boolean renderCalendar) {
        this.renderCalendar = renderCalendar;
    }

    public SieduParticipanteEventoService getServiceParticipante() {
        return serviceParticipante;
    }

    public LogMoodleSendDataService getServiceLog() {
        return serviceLog;
    }

    public LoginFaces getLoginFaces() {
        return loginFaces;
    }

    public LogMoodleSendHttpService getServiceUri() {
        return serviceUri;
    }

    public SieduDocenteEventoService getServiceDocente() {
        return serviceDocente;
    }

    public MoodleCapacitacion getMoodleMigrate() {
        return moodleMigrate;
    }

    public void setMoodleMigrate(MoodleCapacitacion moodleMigrate) {
        this.moodleMigrate = moodleMigrate;
    }

    public List<UnidadDependencia> getEscuelas() {
        return escuelas;
    }

    public void setEscuelas(List<UnidadDependencia> escuelas) {
        this.escuelas = escuelas;
    }

}
