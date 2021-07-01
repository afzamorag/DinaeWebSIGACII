/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.excepciones.ValidationsException;
import co.gov.policia.dinae.siedu.filtros.CapacitacionFiltro;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.Capacitacion;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.servicios.APPService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.CapacitacionService;
import co.gov.policia.dinae.siedu.servicios.NecesidadService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.servicios.SieduEventoEscuelaService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.ArrayList;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
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
public class CapacitacionController extends AbstractController {
    
    private static final Logger LOG = LoggerFactory.getLogger(CapacitacionController.class);
    
    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private PAEService servicePAE;
    @EJB
    private APPService serviceAPP;
    @EJB
    private CapacitacionService service;
    @EJB
    private NecesidadService serviceNecesidad;
    @EJB
    private IUnidadDependenciaLocal unidadDependencia;
    @EJB
    private SieduEventoEscuelaService serviceEventoEscuela;
    private NavEnum optionNavEnum;
    private Capacitacion selected;
    private CapacitacionFiltro filtro;
    private List<Capacitacion> list;
    private boolean editable;
    private List<PAE> vigencias;
    private List<UnidadDependencia> escuelas;
    private List<Dominio> modalidades;
    private Integer totalNroParticipantes;
    
    public CapacitacionController() {
        LOG.trace("metodo: constructor()");
    }
    
    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.filtro = new CapacitacionFiltro();
        this.loadVigencias();
        this.loadEscuelas();
        this.initializeList();
    }
    
    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirect_CU04_CAP_CAPACITACION_PAE();
    }
    
    private void initializeList() {
        LOG.trace("metodo: initializeList()");
        this.optionNavEnum = NavEnum.LIST;
        this.selected = null;
        this.editable = false;
    }
    
    public void onLoadList() {
        LOG.trace("metodo: onLoadList()");
        try {
            setList(this.service.findByFilter(getFiltro()));
        } catch (Exception ex) {
            LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }
    
    public boolean enableConsolidateTrainingNeeds() {
        return this.filtro != null
                && this.filtro.getPae() != null
                && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString())
                || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }
    
    public void consolidateTrainingNeeds() {
        LOG.trace("metodo: consolidateTrainingNeeds()");
        try {
            this.validateConsolidation();
            try {
                SPFiltro paramsSP = new SPFiltro();
                paramsSP.setPae(this.filtro.getPae().getId());
                paramsSP.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                addInfoMessage("Exitoso", this.service.consolidateTrainingNeeds(paramsSP));
                this.onLoadList();
            } catch (ServiceException ex) {
                LOG.error("metodo: consolidateTrainingNeeds() ->> mensaje: {}", ex.getMessage());
                addErrorMessage("Error", ex.getMessage());
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: consolidateTrainingNeeds() ->> mensaje ->> {}", ex.getMessage());
        }
    }
    
    private void validateConsolidation() throws ValidationsException {
        LOG.trace("metodo: validations()");
        try {
            {
                List<String> msgs = this.serviceNecesidad.validarProcesoNecesidades(this.filtro.getPae().getId());
                if (msgs != null && !msgs.isEmpty()) {
                    addErrorMessage(getPropertyFromBundle("necesidad.msg.error.validation.complete"));
                    for (String msg : msgs) {
                        addErrorMessage(msg);
                    }
                    throw new ValidationsException();
                }
            }
        } catch (ServiceException ex) {
            LOG.error("metodo: onFindPAE() ->> mensaje ->> {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        selected = (Capacitacion) event.getObject();
        editable = false;
        optionNavEnum = NavEnum.DETAILS;
        calcularTotalEventos();
        calcularTotalParticipantes();
        if (selected.getPae().getEstado().equals(PAEEstadoEnum.CERRADO.toString())) {
            totalNroParticipantes = selected.getTotalParticipantes();
        }
    }
    
    public boolean editableField() {
        return editable
                && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString());
    }
    
    public boolean enableEdit() {
        return !this.editable
                && this.selected != null
                && this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString())
                || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }
    
    public void onEdit() {
        LOG.trace("metodo: onEdit()");
        initializeEdit();
    }
    
    private void initializeEdit() {
        LOG.trace("metodo: initializeEdit()");
        setEditable(true);
        this.loadModalidades();
    }
    
    public boolean enableApprove() {
        return !this.editable
                && this.selected != null
                && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
                && (this.selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || this.selected.getEstado().equals(NecesidadEstadoEnum.NO_APROBADO.getEstado()))
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString())
                || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }
    
    public void onApprove() {
        LOG.trace("metodo: onApprove()");
        try {
            // validaciones
            this.validateApprove();
            try {
                selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.aprobar(getSelected());
                setEditable(false);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            } catch (Exception ex) {
                if (ExceptionUtil.isException(ex, "ERRORT1")) {
                    LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
                    String msg = ex.getMessage();
                    msg = msg.substring((msg.lastIndexOf(":") + 2));
                    addErrorMessage(msg, null, "nroEventosT1");
                } else if (ExceptionUtil.isException(ex, "ERRORT2")) {
                    LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
                    String msg = ex.getMessage();
                    msg = msg.substring((msg.lastIndexOf(":") + 2));
                    addErrorMessage(msg, null, "nroEventosT2");
                } else if (ExceptionUtil.isException(ex, "ERRORT3")) {
                    LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
                    String msg = ex.getMessage();
                    msg = msg.substring((msg.lastIndexOf(":") + 2));
                    addErrorMessage(msg, null, "nroEventosT3");
                } else if (ExceptionUtil.isException(ex, "ERRORT4")) {
                    LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
                    String msg = ex.getMessage();
                    msg = msg.substring((msg.lastIndexOf(":") + 2));
                    addErrorMessage(msg, null, "nroEventosT4");
                } else {
                    LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                }
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: onApprove() ->> mensaje ->> {}", ex.getMessage());
        }
    }
    
    private void validateApprove() throws ValidationsException {
        LOG.trace("metodo: validate()");
        {
            if (selected.getNroEventosT1() == 0 && selected.getNroEventosT2() == 0 && selected.getNroEventosT3() == 0 && selected.getNroEventosT4() == 0) {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.trimestres"));
                throw new ValidationsException();
            }
        }
        {
            if (selected.getNroEventosT1() == 0 && selected.getNroParticipantesT1() > 0) {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.trimestre.zero"), null, "nroEventosT1");
                throw new ValidationsException();
            }
            if (selected.getNroEventosT2() == 0 && selected.getNroParticipantesT2() > 0) {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.trimestre.zero"), null, "nroEventosT2");
                throw new ValidationsException();
            }
            if (selected.getNroEventosT3() == 0 && selected.getNroParticipantesT3() > 0) {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.trimestre.zero"), null, "nroEventosT3");
                throw new ValidationsException();
            }
            if (selected.getNroEventosT4() == 0 && selected.getNroParticipantesT4() > 0) {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.trimestre.zero"), null, "nroEventosT4");
                throw new ValidationsException();
            }
        }
        {
            if (selected.getTotalParticipantes() > selected.getNecesidad()) {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.necesidad"), null, "totalParticipantes");
                throw new ValidationsException();
            }
        }
        {
            if (selected.getPae().getEstado().equals(PAEEstadoEnum.CERRADO.toString())) {
                if (!Objects.equals(totalNroParticipantes, selected.getTotalParticipantes())) {
                    addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.necesidad.estado.cerrado"), null, "totalParticipantes");
                    throw new ValidationsException();
                }
            }
        }
    }
    
    public boolean enableReprobate() {
        return !this.editable
                && this.selected != null
                && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
                && (this.selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado()))
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString())
                || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }
    
    public void onReprobate() {
        LOG.trace("metodo: onReprobate()");
        try {
            selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            service.reprobar(getSelected());
            setEditable(false);
            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onReprobate() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
        }
    }
    
    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        setEditable(false);
        if (getSelected().getId() == null) {
            optionNavEnum = NavEnum.LIST;
            setSelected(null);
        }
    }
    
    public void onBack() {
        LOG.trace("metodo: onBack()");
        initializeList();
    }
    
    public void calcularTotalEventos() {
        LOG.trace("metodo: calcularTotalEventos()");
        selected.setTotalEventos(selected.getNroEventosT1() + selected.getNroEventosT2() + selected.getNroEventosT3() + selected.getNroEventosT4());
    }
    
    public void calcularTotalParticipantes() {
        LOG.trace("metodo: calcularTotalParticipantes()");
        selected.setTotalParticipantes(selected.getNroParticipantesT1() + selected.getNroParticipantesT2() + selected.getNroParticipantesT3() + selected.getNroParticipantesT4());
        if (selected.getTotalParticipantes() > selected.getNecesidad()) {
            addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.necesidad"), null, "totalParticipantes");
        }
    }
    
    public boolean isShowList() {
        boolean showList = (optionNavEnum == NavEnum.LIST);
        return showList;
    }
    
    public boolean isShowDetails() {
        boolean showDetails = (optionNavEnum == NavEnum.DETAILS);
        return showDetails;
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
    
    private void loadEscuelas() {
        LOG.trace("metodo: loadEscuelas()");
        try {
            setEscuelas(serviceAPP.consultarEscuelasVigentes());
            this.escuelas.add(this.unidadDependencia.getUnidadDependenciaById(23536L));
        } catch (Exception ex) {
            LOG.error("metodo: loadEscuelas() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }
    
    private void loadModalidades() {
        LOG.trace("metodo: loadModalidades()");
        try {
            setModalidades(serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD.getId()));
        } catch (Exception ex) {
            LOG.error("metodo: loadProcesos() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }
    
    public void onDelete() {
        LOG.trace("metodo: onDelete()");
        try {
            List<SieduEventoEscuela> lst = new ArrayList<>();
            lst = this.serviceEventoEscuela.findByPaeCapacitacion(selected.getId());
            if (lst.isEmpty()) {
                this.service.delete(this.selected.getId());
                addInfoMessage(getPropertyFromBundle("capacitacion.msg.validate.necesidad.eliminar.sucessfull"));
                this.onLoadList();
                initializeList();
            } else {
                addErrorMessage(getPropertyFromBundle("capacitacion.msg.validate.necesidad.eventoescuela.desarrollo"));
            }            
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }
    
    public Capacitacion getSelected() {
        return selected;
    }
    
    public void setSelected(Capacitacion selected) {
        this.selected = selected;
    }
    
    public CapacitacionFiltro getFiltro() {
        return filtro;
    }
    
    public void setFiltro(CapacitacionFiltro filtro) {
        this.filtro = filtro;
    }
    
    public List<Capacitacion> getList() {
        return list;
    }
    
    public void setList(List<Capacitacion> list) {
        this.list = list;
    }
    
    public boolean isEditable() {
        return editable;
    }
    
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public List<PAE> getVigencias() {
        return vigencias;
    }
    
    public void setVigencias(List<PAE> vigencias) {
        this.vigencias = vigencias;
    }
    
    public List<UnidadDependencia> getEscuelas() {
        return escuelas;
    }
    
    public void setEscuelas(List<UnidadDependencia> escuelas) {
        this.escuelas = escuelas;
    }
    
    public List<Dominio> getModalidades() {
        return modalidades;
    }
    
    public void setModalidades(List<Dominio> modalidades) {
        this.modalidades = modalidades;
    }
}
