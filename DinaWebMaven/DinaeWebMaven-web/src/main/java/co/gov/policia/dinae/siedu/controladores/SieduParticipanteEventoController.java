/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.ModalidadEnum;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduInasisteEvento;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.CargosService;
import co.gov.policia.dinae.siedu.servicios.GradosService;
import co.gov.policia.dinae.siedu.servicios.SieduDocenteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import javax.inject.Inject;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class SieduParticipanteEventoController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SieduParticipanteEventoController.class);

    @Inject
    protected LoginFaces loginFaces;
    @EJB
    private SieduParticipanteEventoService service;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private CargosService serviceCargos;
    @EJB
    private APPService serviceAPP;
    @EJB
    private GradosService serviceGrados;
    @EJB
    private SieduDocenteEventoService serviceDocente;
    @EJB
    private SieduParticipanteEventoService serviceParticipante;
    private SieduParticipanteEvento selected;
    private List<SieduParticipanteEvento> list;
    private boolean editable;
    private NavEnum optionNavEnum;
    private String identificacion;
    private SieduEventoEscuela eventoEscuela;
    private SieduInasistenteEventoController inasisteEventoController;
    private SieduInasisteEvento inasistenteEvento;
    private SieduEventoEscuelaController eventoEscuelaController;

    public SieduParticipanteEventoController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        optionNavEnum = NavEnum.LIST;
        selected = new SieduParticipanteEvento();
        setEditable(false);
        eventoEscuelaController = this.findManagedBean("sieduEventoEscuelaController");
        eventoEscuela = eventoEscuelaController.getSelected();
        inasisteEventoController = this.findManagedBean("sieduInasistenteEventoController");
        inasistenteEvento = inasisteEventoController.getSelected();
        loadList();
    }

    /**
     *
     */
    public void loadList() {
        LOG.trace("metodo: loadList()");
        try {
            setList(service.findListById(eventoEscuela));
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void loadList(SieduEventoEscuela evento) {
        LOG.trace("metodo: loadList()");
        try {
            setList(service.findListById(evento));
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
        setSelected(new SieduParticipanteEvento());
        initializeEdit();
    }

    /**
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        setSelected((SieduParticipanteEvento) event.getObject());
        setEditable(false);
        optionNavEnum = NavEnum.DETAILS;
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
     * @param record
     */
    public void onDelete(SieduParticipanteEvento record) {
        LOG.trace("metodo: onDelete()");
        try {
            if (inasisteEventoController == null) {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
            } else {
                record.setPareUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                if (eventoEscuelaController.getMoodleMigrate().moodleMigrateDeasignarAlumno(record.getParePers())) {
                    service.delete(record);
                    loadList();
                    inasisteEventoController.loadList();
                    addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
                } else {
                    addErrorMessage("No se pudo eliminar el participante en Moodle");
                }
            }
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    public boolean validInsert(String msgs) {
        LOG.trace("metodo: validInsert()");
        switch (msgs) {
            case "sucessfull":
                try {
                    this.list.add(this.serviceParticipante.findByPareeveeParenroiden(this.eventoEscuela.getEveeEvee(), this.identificacion));
                    return true;
                } catch (Exception ex) {
                    break;
                }
            case "exist_alum":
                addErrorMessage(getPropertyFromBundle("siedueventoescuela.participante.msg.error.save.exist.vigencia.summary"), getPropertyFromBundle("siedueventoescuela.participante.msg.error.save.exist.vigencia.details"));
                return false;
            case "exist_doce":
                addErrorMessage(getPropertyFromBundle("siedueventoescuela.participante.msg.error.save.exist.doc.summary"), getPropertyFromBundle("siedueventoescuela.participante.msg.error.save.exist.doc.details"));
                return false;
            case "max":
                addErrorMessage(getPropertyFromBundle("siedueventoescuela.participante.msg.error.save.max.alu.summary"), getPropertyFromBundle("siedueventoescuela.participante.msg.error.save.max.alu.details"));
                return false;
            case "not exist":
                addErrorMessage(getPropertyFromBundle("eventoesuela.msg.error.nodatafound.sumary"), getPropertyFromBundle("eventoesuela.msg.error.nodatafound.details"));
                return false;
            default:
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                return false;
        }
        return false;
    }

    public boolean createAlum() {
        try {
            if (this.validInsert(this.service.create(this.identificacion, this.eventoEscuela.getEveeCapa().getPae().getVigencia(), this.eventoEscuela.getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera(), this.eventoEscuela.getEveeEvee(), this.loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado(), this.eventoEscuela.getEveeTrimes(), this.eventoEscuela.getEveeCapa().getEscuela().getPk().getConsecutivo()))) {
                identificacion = "";
                return true;
            } else {
                return false;
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
    public void onSave() {
        LOG.trace("metodo: onSave()");
        try {
            SieduPersona persona;
            persona = servicePersona.findByIdentificacion(identificacion);
            if (persona != null) {
                if (migrateToMoodle()) {
                    if (this.createAlum()) {
                        if (!eventoEscuelaController.getMoodleMigrate().moodleMigrateAsignarAlumno(persona)) {
                            this.onDelete(this.service.findByPareeveeParepers(eventoEscuela, persona));
                            addErrorMessage("No se pudo asignar el participante en Moodle");
                        } else {
                            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                        }
                    }
                } else if (this.createAlum()) {
                    addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                } else {
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                }
            } else {
                addErrorMessage(getPropertyFromBundle("eventoesuela.msg.error.nodatafound.sumary"), getPropertyFromBundle("eventoesuela.msg.error.nodatafound.details"));
            }
        } catch (Exception ex) {
            LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
        }
    }

    public boolean migrateToMoodle() {
        if (((this.eventoEscuela.getEveeCapa().getModalidad().getId().compareTo(ModalidadEnum.VIRTUAL.getId()) == 0)
                || (this.eventoEscuela.getEveeCapa().getModalidad().getId().compareTo(ModalidadEnum.SEMIPRESENCIAL.getId()) == 0))
                && (this.eventoEscuela.getEveeCapa().getExterno().equals(DecisionEnum.NO.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        setEditable(false);
        if (getSelected().getParePare() == null) {
            optionNavEnum = NavEnum.LIST;
            setSelected(null);
        }
    }

    /**
     *
     */
    public void onBack() {
        LOG.trace("metodo: onBack()");
        initialize();
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
        boolean newRecord = (getSelected() != null && getSelected().getParePare() == null);
        return newRecord;
    }

    /**
     * @return the selected
     */
    public SieduParticipanteEvento getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(SieduParticipanteEvento selected) {
        this.selected = selected;
    }

    /**
     * @return the list
     */
    public List<SieduParticipanteEvento> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<SieduParticipanteEvento> list) {
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
