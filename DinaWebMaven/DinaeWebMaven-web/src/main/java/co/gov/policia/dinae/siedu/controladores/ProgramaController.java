/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.constantes.SieduEventoEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ValidationsException;
import co.gov.policia.dinae.siedu.filtros.EventoFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.modelo.SieduCompetenciaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoCategoria;
import co.gov.policia.dinae.siedu.modelo.SieduTema;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduTemaService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class ProgramaController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ProgramaController.class);

    public enum Option {
        CREATE,
        UPDATE,
        GENERATE_NEW_VERSION;
    }
    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private SieduEventoService service;
    @EJB
    private SieduTemaService serviceTema;
    private EventoFiltro filtro;
    private List<SieduEvento> list;
    private boolean executedSearch;
    private boolean editable;
    private SieduEvento selected;
    private boolean hasAssociatedRecords;
    private List<Categoria> selectedCategorias;
    private List<SieduCompetencia> selectedCompetencias;
    private TreeNode contenido;
    private TreeNode item;
    private SieduTema temaSelected;
    private boolean showFormTema = false;
    private SieduTema subtemaSelected;
    private boolean showFormSubtema = false;
    private Option option;

    public ProgramaController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        filtro = new EventoFiltro();
        this.initializeList();
        this.initializeDetail();
    }

    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirectToList_CU8_CAP_PROGRAMA();
    }

    public void initializeList() {
        LOG.trace("metodo: initializeList()");
        selected = null;
    }

    public void onNivelAcademicoChange() {
        LOG.trace("metodo: initializeList()");
        filtro.getCarrera().setCarrerasPK(null);
        filtro.getCarrera().setTitulo(null);
    }

    public void loadList() {
        LOG.trace("metodo: loadList()");
        executedSearch = false;
        try {
            list = service.findByFilter(filtro);
            executedSearch = true;
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.load.eventos"), getPropertyFromBundle("commons.msg.error.load.detail"));
        }
    }

    public void onClearList() {
        if (list != null) {
            list.clear();
        }
        if (filtro.getCarrera().getCarrerasPK() != null && filtro.getModalidad() != null && filtro.getProceso() != null) {
            loadList();
        }
    }

    public void initializeDetail() {
        LOG.trace("metodo: initializeList()");
        editable = false;
        item = null;
        temaSelected = null;
        subtemaSelected = null;
        showFormTema = false;
        showFormSubtema = false;
        hasAssociatedRecords = false;
    }

    private void initializeEdit() {
        LOG.trace("metodo: initializeEdit()");
        editable = true;
    }

    public boolean isNewRecord() {
        return (selected != null && selected.getId() == null);
    }

    public boolean enabledCreate() {
        if (executedSearch) {
            return (list == null || list.isEmpty()) && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
        } else {
            return false;
        }
    }

    public String onCreate() {
        LOG.trace("metodo: onCreate()");
        option = Option.CREATE;
        initializeDetail();
        initializeEdit();
        selected = (new SieduEvento());
        selected.setCarrera(filtro.getCarrera());
        selected.setEstado(SieduEventoEstadoEnum.PENDIENTE.toString());
        selected.setDominioModalidad(filtro.getModalidad());
        selected.setDominioProceso(filtro.getProceso());
        selected.setEventoCategorias(null);
        selected.setEventoCompetencias(null);
        selected.setEventoTemas(null);
        selectedCategorias = null;
        selectedCompetencias = null;
        contenido = new DefaultTreeNode();
        return navigationFaces.redirectToDetail_CU8_CAP_PROGRAMA();
    }

    public String onRowSelect() {
        LOG.trace("metodo: onRowSelect()");
        initializeDetail();
        loadCategorias();
        loadCompetencias();
        loadTemas();
        hasAssociatedRecords = service.hasAssociatedRecords(selected.getId());
        return navigationFaces.redirectToDetail_CU8_CAP_PROGRAMA();
    }

    private void loadCategorias() {
        LOG.trace("metodo: loadCategorias()");
        selectedCategorias = new ArrayList<>();
        if (selected.getEventoCategorias() != null) {
            for (SieduEventoCategoria catEven : selected.getEventoCategorias()) {
                Categoria catIns;
                catIns = catEven.getEvcaCategoria();
                selectedCategorias.add(catIns);
            }
        }
    }

    private void loadCompetencias() {
        LOG.trace("metodo: loadCompetencias()");
        selectedCompetencias = new ArrayList<>();
        if (selected.getEventoCompetencias() != null) {
            for (SieduCompetenciaEvento comEven : selected.getEventoCompetencias()) {
                SieduCompetencia comIns;
                comIns = comEven.getSieduCompetencia();
                selectedCompetencias.add(comIns);
            }
        }
    }

    private void loadTemas() {
        LOG.trace("metodo: loadTemas()");
        try {
            //selected.setEventoTemas(serviceTema.findTemasByEvento(selected.getId()));
            selected.setEventoTemas(serviceTema.buscarTemasByEvento(selected));
            contenido = new DefaultTreeNode();
            if (selected.getEventoTemas() != null) {
                for (SieduTema tema : selected.getEventoTemas()) {
                    TreeNode temas = new DefaultTreeNode(tema, contenido);
                    if (tema.getSieduTemaList() != null) {
                        for (SieduTema subtema : tema.getSieduTemaList()) {
                            temas.getChildren().add(new DefaultTreeNode(subtema));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("metodo: temaList() ->> mensaje: {}", ex.getMessage());
        }
    }

    public boolean enabledEdit() {
        LOG.trace("metodo: enabledEdit()");
        if (selected.getId() == null) {
            return !editable
                    && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
        } else {
            return !editable && !hasAssociatedRecords;
        }
    }

    public void onEdit() {
        LOG.trace("metodo: onEdit()");
        option = Option.UPDATE;
        initializeEdit();
    }

    public boolean enabledGenerateNewVersion() {
        LOG.trace("metodo: enabledGenerateNewVersion()");
        return !editable
                && hasAssociatedRecords
                && selected.getEstado().equals(SieduEventoEstadoEnum.VIGENTE.toString())
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onGenerateNewVersion() {
        LOG.trace("metodo: onGenerateNewVersion()");
        option = Option.GENERATE_NEW_VERSION;
        initializeDetail();
        initializeEdit();
        selected = new SieduEvento();
        selected.setEstado(SieduEventoEstadoEnum.PENDIENTE.toString());
        selected.setCarrera(filtro.getCarrera());
        selected.setDominioModalidad(filtro.getModalidad());
        selected.setDominioProceso(filtro.getProceso());
        selected.setEventoCategorias(null);
        selected.setEventoCompetencias(null);
        selected.setEventoTemas(null);
        selectedCategorias = null;
        selectedCompetencias = null;
        contenido = new DefaultTreeNode();
    }

    public boolean enabledActivate() {
        LOG.trace("metodo: enabledActivate()");
        return selected != null
                && selected.getId() != null
                && !editable && selected.getEstado().equals(SieduEventoEstadoEnum.PENDIENTE.toString())
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onActivate() {
        LOG.trace("metodo: onActivate()");
        try {
            // validaciones
            this.validateActivate();
            try {
                selected.setEvenUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.activate(selected);
                loadList();
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            } catch (Exception ex) {
                LOG.error("metodo: onActivate() ->> mensaje: {}", ex.getMessage());
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: onActivate() ->> mensaje ->> {}", ex.getMessage());
        }
    }

    private void validateActivate() throws ValidationsException {
        LOG.trace("metodo: validateActivate()");
        {
            StringBuilder msg = new StringBuilder();
            msg.append("Para activar el Evento debe cumplir las siguienets condiciones:");
            msg.append("\n");
            msg.append("\t > La sumatoria de la intensidad horaria de los temas, debe ser igual a la intensidad horaria del evento.");
            msg.append("\n");
            msg.append("\t > La sumatoria de la intensidad horaria de los subtemas, debe ser igual a la intensidad horaria del tema al que pertenecen.");
            int sumatoriaHorasTema = 0;
            if (selected.getEventoTemas() != null) {
                for (SieduTema tema : selected.getEventoTemas()) {
                    sumatoriaHorasTema += tema.getTemaHoras();
                    if (tema.getSieduTemaList() != null) {
                        int sumatoriaHorasSubtema = 0;
                        for (SieduTema subtema : tema.getSieduTemaList()) {
                            sumatoriaHorasSubtema += subtema.getTemaHoras();
                        }
                        if (tema.getTemaHoras() != sumatoriaHorasSubtema) {
                            addErrorMessage(msg.toString(), null);
                            throw new ValidationsException(msg.toString());
                        }
                    }
                }
            }
            if (selected.getEvenIntenHoras() != sumatoriaHorasTema) {
                addErrorMessage(msg.toString(), null);
                throw new ValidationsException(msg.toString());
            }
        }
    }

    public boolean enabledDelete() {
        LOG.trace("metodo: enabledEdit()");
        return !editable
                && !hasAssociatedRecords
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public String onDelete() {
        LOG.trace("metodo: onDelete()");
        try {
            service.delete(selected);
            this.loadList();
            this.initializeList();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            return navigationFaces.redirectToList_CU8_CAP_PROGRAMA();
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "SIEDU_EVALUACION_EVENTO_FK")) {
                addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.delete.uk.evaluacion"), null);
            }
            if (ExceptionUtil.isException(ex, "SIEDU_DOCENTE_EVEN_SUBTEMA_FK")) {
                addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.delete.uk.docente"), null);
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
            return null;
        }
    }

    public void onSave() {
        LOG.trace("metodo: onSave()");
        try {
            // validaciones
            this.validateSave();
            try {
                switch (option) {
                    case CREATE: {
                        selected.setEvenUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                        service.create(selected, getSelectedCompetencias(), getSelectedCategorias());
                        break;
                    }
                    case UPDATE: {
                        selected.setEvenUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                        service.update(selected, getSelectedCompetencias(), getSelectedCategorias());
                        break;
                    }
                    case GENERATE_NEW_VERSION: {
                        selected.setEvenUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                        service.create(selected, getSelectedCompetencias(), getSelectedCategorias());
                        break;
                    }
                }
                loadTemas();
                loadList();
                initializeDetail();
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            } catch (Exception ex) {
                LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
                if (ExceptionUtil.isException(ex, "SIEDU_CAPACITA_PROGRAMA_PK")) {
                    addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.save.pk"), null);
                } else if (ExceptionUtil.isException(ex, "SIEDU_EVENTO_UK")) {
                    addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.save.uk"), null);
                } else {
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                }
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: onSaveTema() ->> mensaje ->> {}", ex.getMessage());
        }
    }

    private void validateSave() throws ValidationsException {
        LOG.trace("metodo: validate()");
        {
            int horasAcomuladas = 0;
            if (selected.getEventoTemas() != null) {
                for (SieduTema tema : selected.getEventoTemas()) {
                    horasAcomuladas += tema.getTemaHoras();
                }
            }
            if (selected.getEvenIntenHoras() < horasAcomuladas) {
                addErrorMessage("La intensidad horaria del evento, no puede ser menor a la sumatoria de la intensidad horaria de los temas.", null, "intesidadHoraria");
                throw new ValidationsException("La intensidad horaria del evento, no puede ser menor a la sumatoria de la intensidad horaria de los temas.");
            }
        }
    }

    public String onCancel() {
        LOG.trace("metodo: onCancel()");
        if (selected.getId() == null) {
            initializeList();
            return navigationFaces.redirectToList_CU8_CAP_PROGRAMA();
        } else {
            editable = false;
            return null;
        }
    }

    public String onBackToList() {
        LOG.trace("metodo: onBack()");
        this.initializeList();
        return navigationFaces.redirectToList_CU8_CAP_PROGRAMA();
    }

    public void onNodeSelect(NodeSelectEvent event) {
        item = event.getTreeNode();
        SieduTema registro = (SieduTema) item.getData();
        if (registro.getTemaTemaPadre() == null) {
            //tema
            temaSelected = registro;
            subtemaSelected = null;
        } else {
            temaSelected = null;
            subtemaSelected = registro;
        }
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        LOG.trace("metodo: onNodeUnselect()");
        item = null;
        temaSelected = null;
        subtemaSelected = null;
    }

    public boolean enabledCreateTema() {
        LOG.trace("metodo: enabledCreateTema()");
        return (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onCreateTema() {
        LOG.trace("metodo: onCreateTema()");
        temaSelected = new SieduTema();
        temaSelected.setTemaEven(selected);
        showFormTema = true;
    }

    public boolean enabledEditTema() {
        LOG.trace("metodo: enabledCreateTema()");
        return temaSelected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onEditTema() {
        LOG.trace("metodo: onEditTema()");
        showFormTema = true;
    }

    public boolean enabledDeleteTema() {
        LOG.trace("metodo: enabledCreateTema()");
        return temaSelected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onDeleteTema() {
        LOG.trace("metodo: onDeleteTema()");
        try {
            serviceTema.delete(temaSelected, selected);
            loadTemas();
            item = null;
            temaSelected = null;
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "SIEDU_DOCENTE_EVEN_SUBTEMA_FK")) {
                addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.tema.delete.uk.docente"), null);
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.delete.save.detail"));
            }
        }
    }

    public void onSaveTema() {
        LOG.trace("metodo: onSaveTema()");
        try {
            // validaciones
            this.validateSaveTema();
            try {
                if (getTemaSelected().getId() == null) {
                    temaSelected.setTemaUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    serviceTema.create(getTemaSelected());
                } else {
                    temaSelected.setTemaUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    serviceTema.update(getTemaSelected());
                }
                loadTemas();
                showFormTema = false;
                temaSelected = null;
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            } catch (Exception ex) {
                if (ExceptionUtil.isException(ex, "SIEDU_TEMA_PK")) {
                    addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.tema.save.pk"), null);
                } else if (ExceptionUtil.isException(ex, "SIEDU_TEMA_UK")) {
                    addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.tema.save.uk"), null);
                } else {
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                }
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: onSaveTema() ->> mensaje ->> {}", ex.getMessage());
        }
    }

    private void validateSaveTema() throws ValidationsException {
        LOG.trace("metodo: validate()");
        {
            int horasAcomuladas = 0;
            if (selected.getEventoTemas() != null) {
                for (SieduTema tema : selected.getEventoTemas()) {
                    horasAcomuladas += tema.getTemaHoras();
                }
            }
            int sumatoriaTotal;
            if (temaSelected.getId() == null) {
                sumatoriaTotal = horasAcomuladas + temaSelected.getTemaHoras();
            } else {
                sumatoriaTotal = horasAcomuladas;
            }
            if (selected.getEvenIntenHoras() < sumatoriaTotal) {
                addErrorMessage("excede numero de horas para este evento", null, "intHoraNewTema");
                throw new ValidationsException("excede numero de horas para este evento");
            }
        }
        {
            int horasAcomuladasSubtemas = 0;
            if (temaSelected.getSieduTemaList() != null) {
                for (SieduTema subtema : temaSelected.getSieduTemaList()) {
                    horasAcomuladasSubtemas += subtema.getTemaHoras();
                }
            }
            if (temaSelected.getTemaHoras() < horasAcomuladasSubtemas) {
                addErrorMessage("La intensidad horaria no puede ser menor a la sumatoria de la intensidad horaria de sus subtemas", null, "intHoraNewTema");
                throw new ValidationsException("La intensidad horaria no puede ser menor a la sumatoria de la intensidad horaria de sus subtemas");
            }
        }
    }

    public void onCancelSaveTema() {
        LOG.trace("metodo: onCancelSaveTema()");
        temaSelected = null;
        showFormTema = false;
    }

    public boolean enabledCreateSubtema() {
        LOG.trace("metodo: enabledCreateSubtema()");
        return temaSelected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onCreateSubtema() {
        LOG.trace("metodo: onCreateSubTema()");
        subtemaSelected = new SieduTema();
        subtemaSelected.setTemaEven(selected);
        subtemaSelected.setTemaTemaPadre(temaSelected);
        showFormSubtema = true;
    }

    public boolean enabledEditSubtema() {
        LOG.trace("metodo: enabledEditSubtema()");
        return subtemaSelected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onEditSubtema() {
        LOG.trace("metodo: onEditSubtema()");
        showFormSubtema = true;
    }

    public boolean enabledDeleteSubtema() {
        LOG.trace("metodo: enabledDeleteSubtema()");
        return subtemaSelected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onDeleteSubTema() {
        LOG.trace("metodo: onDeleteSubTema()");
        try {
            serviceTema.delete(subtemaSelected, selected);
            loadTemas();
            item = null;
            temaSelected = null;
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "SIEDU_DOCENTE_EVEN_SUBTEMA_FK")) {
                addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.subtema.delete.uk.docente"), null);
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.delete.save.detail"));
            }
        }
    }

    public void onSaveSubtema() {
        LOG.trace("metodo: onSaveSubTema()");
        try {
            // validaciones
            this.validateSaveSubtema();
            try {
                if (getSubtemaSelected().getId() == null) {
                    subtemaSelected.setTemaUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    serviceTema.create(subtemaSelected);
                } else {
                    subtemaSelected.setTemaUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    serviceTema.update(subtemaSelected);
                }
                loadTemas();
                showFormSubtema = false;
                subtemaSelected = null;
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            } catch (Exception ex) {
                if (ExceptionUtil.isException(ex, "SIEDU_TEMA_PK")) {
                    addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.subtema.save.pk"), null);
                } else if (ExceptionUtil.isException(ex, "SIEDU_TEMA_UK")) {
                    addErrorMessage(getPropertyFromBundle("sieduEvento.msg.error.subtema.save.uk"), null);
                } else {
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                }
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: onSaveTema() ->> mensaje ->> {}", ex.getMessage());
        }
    }

    private void validateSaveSubtema() throws ValidationsException {
        LOG.trace("metodo: validateSaveSubtema()");
        {
            int horasAcomuladas = 0;
            if (subtemaSelected.getTemaTemaPadre().getSieduTemaList() != null) {
                for (SieduTema tema : subtemaSelected.getTemaTemaPadre().getSieduTemaList()) {
                    horasAcomuladas += tema.getTemaHoras();
                }
            }
            int sumatoriaTotal;
            if (subtemaSelected.getId() == null) {
                sumatoriaTotal = horasAcomuladas + subtemaSelected.getTemaHoras();
            } else {
                sumatoriaTotal = horasAcomuladas;
            }
            if (subtemaSelected.getTemaTemaPadre().getTemaHoras() < sumatoriaTotal) {
                addErrorMessage("excede numero de horas para este Tema", null, "intHoraNewSubTema");
                throw new ValidationsException("excede numero de horas para este Tema");
            }
        }
    }

    public void onCancelSaveSubtema() {
        LOG.trace("metodo: onCancelSaveSubtema()");
        subtemaSelected = null;
        showFormSubtema = false;
    }

    public EventoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(EventoFiltro filtro) {
        this.filtro = filtro;
    }

    public List<SieduEvento> getList() {
        return list;
    }

    public void setList(List<SieduEvento> list) {
        this.list = list;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public SieduEvento getSelected() {
        return selected;
    }

    public void setSelected(SieduEvento selected) {
        this.selected = selected;
    }

    public List<Categoria> getSelectedCategorias() {
        return selectedCategorias;
    }

    public void setSelectedCategorias(List<Categoria> selectedCategorias) {
        this.selectedCategorias = selectedCategorias;
    }

    public List<SieduCompetencia> getSelectedCompetencias() {
        return selectedCompetencias;
    }

    public void setSelectedCompetencias(List<SieduCompetencia> selectedCompetencias) {
        this.selectedCompetencias = selectedCompetencias;
    }

    public TreeNode getContenido() {
        return contenido;
    }

    public void setContenido(TreeNode contenido) {
        this.contenido = contenido;
    }

    public TreeNode getItem() {
        return item;
    }

    public void setItem(TreeNode item) {
        this.item = item;
    }

    public SieduTema getTemaSelected() {
        return temaSelected;
    }

    public void setTemaSelected(SieduTema temaSelected) {
        this.temaSelected = temaSelected;
    }

    public boolean isShowFormTema() {
        return showFormTema;
    }

    public void setShowFormTema(boolean showFormTema) {
        this.showFormTema = showFormTema;
    }

    public SieduTema getSubtemaSelected() {
        return subtemaSelected;
    }

    public void setSubtemaSelected(SieduTema subtemaSelected) {
        this.subtemaSelected = subtemaSelected;
    }

    public boolean isShowFormSubtema() {
        return showFormSubtema;
    }

    public void setShowFormSubtema(boolean showFormSubtema) {
        this.showFormSubtema = showFormSubtema;
    }
}
