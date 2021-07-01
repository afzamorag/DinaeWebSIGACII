/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.filtros.CapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Capacitacion;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.Presupuesto;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.CapacitacionService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.servicios.PresupuestoService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class PresupuestoController extends AbstractController {

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
    private List<Capacitacion> list;
    private CapacitacionFiltro filtro;
    private Capacitacion selected;
    private List<PAE> vigencias;
    private List<UnidadDependencia> escuelas;
    private List<NivelesAcademicos> nivelesAcademicos;
    private List<Carreras> programas;
    private List<Dominio> recursos;
    @EJB
    private PresupuestoService presupuestoService;
    private Presupuesto presupuesto;
    private boolean showFormPresupuesto;
    private boolean validaRol = false;
    private SieduEventoEscuela eventoEscuela;

    public PresupuestoController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.filtro = new CapacitacionFiltro();
        this.loadVigencias();
        this.loadEscuelas();
        this.loadNivelesAcademicos();
        this.loadRecursos();
        validaRol();
        this.initializeList();
        SieduEventoEscuelaController eventoEscuelaController = this.findManagedBean("sieduEventoEscuelaController");
        eventoEscuela = eventoEscuelaController.getSelected();
    }

    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirectToList_CU06_CAP_PRESUPUESTO();
    }

    public boolean validaRol() {
        if (this.loginFaces != null) {
            validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        }
        return validaRol;
    }

    private void initializeList() {
        LOG.trace("metodo: initializeList()");
        this.selected = null;
    }

    public void onLoadList() {
        LOG.trace("metodo: onLoadList()");
        try {
            this.filtro.setPresupuesto(true);

            if (validaRol == false) {
                this.filtro.setEscuela(loginFaces.getPerfilUsuarioDTO().getUnidadDependencia());
            }
            this.list = this.service.findByFilter(this.filtro);
        } catch (Exception ex) {
            LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public String onRowSelect() {
        LOG.trace("metodo: onRowSelect()");
        this.initializePresupuestos();
        this.onLoadPresupuestos();
        return navigationFaces.redirectToDetail_CU06_CAP_PRESUPUESTO();
    }

    public String onBackToList() {
        LOG.trace("metodo: onBackToList()");
        this.initializeList();
        return navigationFaces.redirectToList_CU06_CAP_PRESUPUESTO();
    }

    private void initializePresupuestos() {
        LOG.trace("metodo: initializePresupuestos()");
        this.presupuesto = null;
    }

    public void onLoadPresupuestos() {
        LOG.trace("metodo: onLoadPresupuestos()");
        try {
            this.selected.setPresupuestos(this.presupuestoService.findByCapacitacion(selected.getId()));
        } catch (Exception ex) {
            LOG.error("metodo: onLoadPresupuestos() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public List<Presupuesto> onLoadPresupuestosEvento() {
        LOG.trace("metodo: onLoadPresupuestos()");
        try {
            List<Presupuesto> presupuestoEvento = presupuestoService.findByCapacitacion(eventoEscuela.getEveeCapa().getId());
            return presupuestoEvento;
        } catch (Exception ex) {
            LOG.error("metodo: onLoadPresupuestosEvento() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            return null;
        }
    }

//  public boolean enableCreatePresupuesto() {
//    return this.selected != null
//            && this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())
//            && (UnidadDependenciaEnum.TELEM.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()))
//            && (UnidadDependenciaEnum.VIECO.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()));
//  }
    public void onCreatePresupuesto() {
        LOG.trace("metodo: onCreatePresupuesto()");
        this.presupuesto = new Presupuesto();
        this.presupuesto.setCapacitacion(selected);
        this.initializeEditPresupuesto();
    }

    public boolean enableRowSelectPresupuesto() {
        return this.selected != null
                && this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())
                && (UnidadDependenciaEnum.TELEM.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()))
                && (UnidadDependenciaEnum.VIECO.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()));
    }

    public void onRowSelectPresupuesto(SelectEvent event) {
        LOG.trace("metodo: onRowSelectPresupuesto() ->> parametros: event {}", event);
        this.presupuesto = (Presupuesto) event.getObject();
    }

    public void onRowUnselectPresupuesto(UnselectEvent event) {
        LOG.trace("metodo: onRowUnselectPresupuesto() ->> parametros: event {}", event);
        this.presupuesto = null;
    }

    private void initializeEditPresupuesto() {
        LOG.trace("metodo: initializeEditPresupuesto()");
        this.showFormPresupuesto = true;
    }
//
//  public boolean enableEditPresupuesto() {
//    return this.selected != null
//            && this.presupuesto != null
//            && this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())
//            && (UnidadDependenciaEnum.TELEM.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()))
//            && (UnidadDependenciaEnum.VIECO.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()));
//  }

    public void onEditPresupuesto() {
        LOG.trace("metodo: onEditPresupuesto()");
        this.initializeEditPresupuesto();
    }

//  public boolean enableDeletePresupuesto() {
//    return this.selected != null
//            && this.presupuesto != null
//            && this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())
//            && (UnidadDependenciaEnum.TELEM.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()))
//            && (UnidadDependenciaEnum.VIECO.toString().equals(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia()));
//  }
    public void onDeletePresupuesto() {
        LOG.trace("metodo: onDeletePresupuesto()");
        try {
            this.presupuesto.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            this.presupuestoService.delete(this.presupuesto);
            this.initializePresupuestos();
            this.onLoadPresupuestos();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    public void onSavePresupuesto() {
        LOG.trace("metodo: onSavePresupuesto()");
        try {
            if (this.presupuesto.getId() == null) {
                this.presupuesto.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                this.presupuestoService.create(this.presupuesto);
                this.onLoadPresupuestos();
            } else {
                this.presupuesto.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                this.presupuestoService.update(this.presupuesto);
            }
            this.showFormPresupuesto = false;
            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "SIEDU_PRESUPUESTO_UK")) {
                addErrorMessage(getPropertyFromBundle("presupuesto.msg.error.save.uk.summary"), getPropertyFromBundle("presupuesto.msg.error.save.uk.detail"));
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        }
    }

    public void onCancelSavePresupuesto() {
        LOG.trace("metodo: onCancelSavePresupuesto()");
        this.showFormPresupuesto = false;
        if (this.presupuesto.getId() == null) {
            this.presupuesto = null;
        }
    }

    private void loadVigencias() {
        LOG.trace("metodo: loadVigencias()");
        try {
            setVigencias(servicePAE.consultarVigenciasCerradas());
        } catch (Exception ex) {
            LOG.error("metodo: loadVigencias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadEscuelas() {
        LOG.trace("metodo: loadEscuelas()");
        try {
            this.escuelas = serviceAPP.consultarEscuelasVigentes();
        } catch (Exception ex) {
            LOG.error("metodo: loadEscuelas() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadNivelesAcademicos() {
        LOG.trace("metodo: loadNivelesAcademicos()");
        try {
            setNivelesAcademicos(serviceAPP.consultarNivelesAcademicos());
        } catch (Exception ex) {
            LOG.error("metodo: loadCarreras() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadRecursos() {
        LOG.trace("metodo: loadRecursos()");
        try {
            this.recursos = serviceAPP.consultarDominios(TipoDominioEnum.TIPO_FINANCIACION.getId());
        } catch (Exception ex) {
            LOG.error("metodo: loadRecursos() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public Date getMinDateContrato() {
        if (selected == null) {
            return null;
        } else {
            Integer currentYear = new Integer(selected.getPae().getVigencia());
            Calendar firtsDate = new GregorianCalendar(currentYear-1, 0, 1);
            return firtsDate.getTime();
        }
    }

    public Date getMaxDateContrato() {
        Integer currentYear = new Integer(selected.getPae().getVigencia());
        Calendar firtsDate = new GregorianCalendar(currentYear, 11, 31);
        return firtsDate.getTime();
    }

    public List<Capacitacion> getList() {
        return list;
    }

    public void setList(List<Capacitacion> list) {
        this.list = list;
    }

    public CapacitacionFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(CapacitacionFiltro filtro) {
        this.filtro = filtro;
    }

    public Capacitacion getSelected() {
        return selected;
    }

    public void setSelected(Capacitacion selected) {
        this.selected = selected;
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

    public List<NivelesAcademicos> getNivelesAcademicos() {
        return nivelesAcademicos;
    }

    public void setNivelesAcademicos(List<NivelesAcademicos> nivelesAcademicos) {
        this.nivelesAcademicos = nivelesAcademicos;
    }

    public List<Carreras> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Carreras> programas) {
        this.programas = programas;
    }

    public List<Dominio> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Dominio> recursos) {
        this.recursos = recursos;
    }

    public PresupuestoService getPresupuestoService() {
        return presupuestoService;
    }

    public void setPresupuestoService(PresupuestoService presupuestoService) {
        this.presupuestoService = presupuestoService;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public boolean isShowFormPresupuesto() {
        return showFormPresupuesto;
    }

    public void setShowFormPresupuesto(boolean showFormPresupuesto) {
        this.showFormPresupuesto = showFormPresupuesto;
    }

    public boolean isValidaRol() {
        return validaRol;
    }

    public void setValidaRol(boolean validaRol) {
        this.validaRol = validaRol;
    }

}
