/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.ModalidadEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.modelo.SieduTema;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduDocenteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduTemaService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class SieduDocenteEventoController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SieduDocenteEventoController.class);

    @Inject
    protected LoginFaces loginFaces;
    @EJB
    private SieduDocenteEventoService service;
    @EJB
    private SieduTemaService serviceTema;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private SieduParticipanteEventoService serviceParticipante;
    private SieduDocenteEvento selected;
    private List<SieduTema> temas;
    private List<SieduTema> temasList;
    private List<SieduTema> subtemas;
    private List<SieduDocenteEvento> list;
    private boolean editable;
    private NavEnum optionNavEnum;
    private SieduEventoEscuela eventoEscuela;
    private Long temaId;
    private Long subtemaId;
    private String identificacion;
    private SieduTema tema;
    private SieduTema subTemaDoc;
    private SieduEventoEscuelaController eventoEscuelaController;

    public SieduDocenteEventoController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        optionNavEnum = NavEnum.LIST;
        setSelected(new SieduDocenteEvento());
        setEditable(false);
        eventoEscuelaController = this.findManagedBean("sieduEventoEscuelaController");
        eventoEscuela = eventoEscuelaController.getSelected();
        identificacion = "";
        this.temaId = 0L;
        this.subtemaId = 0L;
        this.temas = new ArrayList<>();
        this.temasList = new ArrayList<>();
        if (this.eventoEscuela.getEveeEven() != null) {
            findTemas();
            buscarTemas();
        }
        subTemaDoc = new SieduTema();
        loadList();
        //releaseControllers();
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
        setSelected(new SieduDocenteEvento());
        initializeEdit();
    }

    /**
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        setSelected((SieduDocenteEvento) event.getObject());
        setEditable(false);
        optionNavEnum = NavEnum.DETAILS;
    }

    public void onRowEdit(RowEditEvent event) {
        LOG.trace("metodo: onRowEdit()");
        try {
            setSelected((SieduDocenteEvento) event.getObject());
            selected.setDoceUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            this.subTemaDoc.setId(this.subtemaId);
            selected.setDoceTema(this.subTemaDoc);
            service.update(selected);
            this.list = new ArrayList<>();
            //this.loadList(eventoEscuela);
            //this.subTemaDoc = new SieduTema();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
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
            
            List<SieduDocenteEvento> lst = service.findByDoceeveeDocepers(getSelected().getDocePers(), getSelected().getDoceEvee());
            if(lst.size() < 2 ) {
                if (eventoEscuelaController.getMoodleMigrate().moodleMigrateDeasignarDocente(getSelected().getDocePers())) {
                    selected.setDoceUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    service.delete(getSelected());
                    initialize();
                    addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
                } else {
                    addErrorMessage("No se pudo desasignar el docente en Moodle");
                }
            } else {
                selected.setDoceUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.delete(getSelected());
                initialize();
            }
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    public void onSave() throws ServiceException {
        //Método que guarda al docente, modificado para integración con Moodle.
        LOG.trace("método: onSave()");
        if (migrateToMoodle()) {
            if (this.onEvent()) {
                if (!eventoEscuelaController.getMoodleMigrate().moodleMigrateAsignarDocente(servicePersona.findByIdentificacion(identificacion))) {
                    this.selected.setDoceUsuMod(this.loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    this.service.delete(selected);
                    this.subtemas.add(subTemaDoc);
                    addErrorMessage("No se pudo asignar el docente a Moodle");
                } else {
                    addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                }
            }
        } else if (this.onEvent()) {
            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
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
    public boolean onEvent() {
        //Método existenta antes de la integración con Moodle
        LOG.trace("metodo: onEvent()");
        SieduPersona persona;
        try {
            persona = servicePersona.findByIdentificacion(identificacion);
            if (persona.getPersPers() == null) {
                addInfoMessage(getPropertyFromBundle("eventoesuela.msg.error.nodatafound.sumary"), getPropertyFromBundle("eventoesuela.msg.error.nodatafound.details"));
                return false;
            } else {
                SieduParticipanteEvento participante = serviceParticipante.findByPareeveeParepers(eventoEscuela, persona);
                if (participante == null) {
                    if (getSelected().getDoceDoce() == null) {
                        subTemaDoc.setId(subtemaId);
                        selected.setDoceTema(subTemaDoc);
                        selected.setDoceEvee(eventoEscuela);
                        selected.setDocePers(persona);
                        selected.setDoceUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria                        
                        this.service.create(selected);
                        this.selected.setDoceTema(this.serviceTema.findTemasByTemaPapa(selected.getDoceTema().getId()));
                        this.list.add(selected);
                        this.subtemas.remove(subTemaDoc);
                        setEditable(false);
                        this.setSelected(new SieduDocenteEvento());
                        return true;
                    } else {
                        service.update(getSelected());
                        setEditable(false);
                        return true;
                    }
                } else {
                    addErrorMessage(getPropertyFromBundle("eventoescuela.docente.msg.error.existParticipante.sumary"), getPropertyFromBundle("eventoescuela.docente.msg.error.existParticipante.details"));
                    return false;
                }
            }
        } catch (Exception ex) {
            LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "USR_DISIGAC2.SIEDU_DOCENTE_EVENTO_UK")) {
                addErrorMessage(getPropertyFromBundle("eventoescuela.docente.msg.error.uk.sumary"), getPropertyFromBundle("eventoescuela.docente.msg.error.uk.detail"));
                return false;
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                return false;
            }
        }
    }

    public void findTemas() {
        LOG.trace("metodo: findTemas()");
        try {
            if (this.eventoEscuela.getEveeEven().getId() != null) {
                temas = serviceTema.buscarTemasByEvento(eventoEscuela.getEveeEven());
            }
        } catch (ServiceException ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
//    return temas;
    }

    public void buscarTemas() {
        LOG.trace("metodo: buscarTemas()");
        try {
            if (this.eventoEscuela.getEveeEven() != null) {
                temasList = serviceTema.buscarTemasByEvento(eventoEscuela.getEveeEven());
            }
        } catch (ServiceException ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
//    return temas;
    }

    public String findTema(Long temaPadre) {
        LOG.trace("metodo: findTema() ->> parametros: temaPadre {}", temaPadre);
        try {
            tema = (serviceTema.findTemasByTemaPapa(temaPadre));
        } catch (ServiceException ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
        return tema.getTemaDescri();
    }

    public SieduTema findSubtema(Long idTema) {
        LOG.trace("metodo: findSubtema() ->> parametros: idTema{}", idTema);
        SieduTema subtema = new SieduTema();
        try {
            subtema = serviceTema.findSubtema(idTema);
        } catch (ServiceException ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
        return subtema;
    }

    public List<SieduTema> findTemasByEventoTemaPapa(Long temaId) throws Exception {
        LOG.trace("metodo: findTemasByEventoTemaPapa() -->> parametros: temaPadre{}", temaId);
        try {
            setSubtemas(serviceTema.findTemasByTemaTemaPadre(temaId, eventoEscuela));
        } catch (ServiceException ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
        return subtemas;
    }

    /**
     *
     */
    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        setEditable(false);
        if (getSelected().getDoceDoce() == null) {
            optionNavEnum = NavEnum.LIST;
            setSelected(null);
        }
    }

    /*public void onRemove(SieduDocenteEvento docente) {
        LOG.trace("metodo: onRemove");
        try {
            docente.setDoceUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            service.delete(docente);
            loadList();
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }*/
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
        boolean newRecord = (getSelected() != null && getSelected().getDoceDoce() == null);
        return newRecord;
    }

    /**
     * @return the selected
     */
    public SieduDocenteEvento getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(SieduDocenteEvento selected) {
        this.selected = selected;
    }

    public List<SieduTema> getTemas() {
        return temas;
    }

    public void setTemas(List<SieduTema> temas) {
        this.temas = temas;
    }

    public List<SieduTema> getSubtemas() {
        return subtemas;
    }

    public void setSubtemas(List<SieduTema> subtemas) {
        this.subtemas = subtemas;
    }

    /**
     * @return the list
     */
    public List<SieduDocenteEvento> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<SieduDocenteEvento> list) {
        this.list = list;
    }

    public Long getTemaId() {
        return temaId;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public SieduTema getTema() {
        return tema;
    }

    public void setTema(SieduTema tema) {
        this.tema = tema;
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

    public Long getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(Long subtemaId) {
        this.subtemaId = subtemaId;
    }

    public SieduTema getsubTemaDoc() {
        return subTemaDoc;
    }

    public void setsubTemaDoc(SieduTema subTemaDoc) {
        this.subTemaDoc = subTemaDoc;
    }

    public List<SieduTema> getTemasList() {
        return temasList;
    }

    public void setTemasList(List<SieduTema> temasList) {
        this.temasList = temasList;
    }

}
