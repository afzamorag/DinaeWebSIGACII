/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.excepciones.ValidationsException;
import co.gov.policia.dinae.siedu.filtros.FormacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Formacion;
import co.gov.policia.dinae.siedu.modelo.FormacionEscuela;
import co.gov.policia.dinae.siedu.modelo.FormacionEscuelaCohorte;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.servicios.APPService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.FormacionService;
import co.gov.policia.dinae.siedu.servicios.NecesidadService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class FormacionController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(FormacionController.class);

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
  @EJB
  private PAEService servicePAE;
  @EJB
  private APPService serviceAPP;
  @EJB
  private FormacionService service;
  @EJB
  private NecesidadService serviceNecesidad;
  private FormacionFiltro filtro;
  private List<Formacion> list;
  private Formacion selected;
  private boolean editable;
  private List<PAE> vigencias;
  private DualListModel<UnidadDependencia> escuelas;
  private FormacionEscuelaCohorte cohorteSelected;
  private boolean showFormAssociateEscuelas;
  private boolean showFormCohorte;

  public FormacionController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    this.filtro = new FormacionFiltro();
    this.loadVigencias();
    this.initializeList();
  }

  public String initReturnCU() {
    LOG.trace("metodo: initReturnCU()");
    releaseControllers();
    return navigationFaces.redirectToList_CU05_CAP_FORMACION_PAE();
  }

  private void initializeList() {
    LOG.trace("metodo: initializeList()");
  }

  public void onLoadList() {
    LOG.trace("metodo: onLoadList()");
    try {
      this.list = this.service.findByFilter(getFiltro());
    } catch (Exception ex) {
      LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public boolean enableConsolidateTrainingNeeds() {
    return this.filtro != null
            && this.filtro.getPae() != null
            && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void consolidateTrainingNeeds() {
    LOG.trace("metodo: consolidateTrainingNeeds()");
    try {
      this.validarProcesoNecesidades();
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

  private void validarProcesoNecesidades() throws ValidationsException {
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

  public String onRowSelect() {
    LOG.trace("metodo: onRowSelect()");
    try {
      selected = service.findFullById(selected.getId());
      initializeDetail();
      return navigationFaces.redirectToDetail_CU05_CAP_FORMACION_PAE();
    } catch (ServiceException ex) {
      LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.single.summary"), getPropertyFromBundle("commons.msg.error.read.single.detail"));
      return null;
    }
  }

  private void initializeDetail() {
    LOG.trace("metodo: initializeList()");
    cohorteSelected = null;
    escuelas = new DualListModel<>();
    editable = false;
  }

  public void buildDualListEscuelas() {
    try {
      List<UnidadDependencia> escuelasSource = serviceAPP.consultarEscuelasVigentes();
      List<UnidadDependencia> escuelasTarget = new ArrayList<>();
      if (selected.getEscuelas() != null) {
        for (FormacionEscuela record : selected.getEscuelas()) {
          escuelasSource.remove(record.getEscuela());
          escuelasTarget.add(record.getEscuela());
        }
      }
      escuelas = new DualListModel<>(escuelasSource, escuelasTarget);
    } catch (Exception ex) {
      LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public boolean enableAdminEscuelas() {
    LOG.trace("metodo: enableAdminEscuelas()");
    return editable
            && selected != null
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && (selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado()))
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onAdminEscuelas() {
    LOG.trace("metodo: onAdminEscuelas()");
    showFormAssociateEscuelas = true;
    buildDualListEscuelas();
  }

  public void onSaveAdminEscuelas() {
    LOG.trace("metodo: onSaveAdminEscuelas()");
    try {
      selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      service.administrarEscuelas(selected, escuelas.getTarget());
      selected = service.findFullById(selected.getId());
      showFormAssociateEscuelas = false;
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (ServiceException ex) {
      LOG.error("metodo: onSaveAdminEscuelas() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
    }
  }

  public void onCancelSaveAdminEscuelas() {
    LOG.trace("metodo: onCancelSaveAdminEscuelas()");
    showFormAssociateEscuelas = false;
  }

  private void initializeEdit() {
    LOG.trace("metodo: initializeEdit()");
    editable = true;
    if ((selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())) || (selected.getEstado().equals(NecesidadEstadoEnum.NO_APROBADO.getEstado()))) {
      try {
        selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
        service.pendiente(getSelected());
        selected = service.findFullById(selected.getId());
        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
      } catch (Exception ex) {
        LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    }
  }

  public boolean enableEdit() {
    return !this.editable
            && this.selected != null
            && this.selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && this.selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onEdit() {
    LOG.trace("metodo: onEdit()");
    initializeEdit();
  }

  public boolean enableApprove() {
    return selected != null
            && !editable
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && (selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || selected.getEstado().equals(NecesidadEstadoEnum.NO_APROBADO.getEstado()))
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onApprove() {
    LOG.trace("metodo: onEdit()");
    initializeEdit();
  }

  public boolean enableSaveApprove() {
    return selected != null
            && editable
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado());
  }

  public void onSaveApprove() {
    LOG.trace("metodo: onSaveApprove()");
    try {
      // validaciones
      this.validateApprove();
      try {
        selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
        service.aprobar(getSelected());
        editable = false;
        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
      } catch (Exception ex) {
        LOG.error("metodo: onApprove() ->> mensaje: {}", ex.getMessage());
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    } catch (ValidationsException ex) {
      LOG.error("metodo: onApprove() ->> mensaje ->> {}", ex.getMessage());
    }
  }

  private void validateApprove() throws ValidationsException {
    LOG.trace("metodo: validate()");
    {
      // minimo una escuela asociada
      if (selected.getEscuelas() == null || selected.getEscuelas().isEmpty()) {
        addErrorMessage(getPropertyFromBundle("formacion.msg.validate.escuelas"));
        throw new ValidationsException();
      }
    }
    {
      // minimo un cohorte por escuela
      if (selected.getEscuelas() != null) {
        for (FormacionEscuela escuela : selected.getEscuelas()) {
          if (escuela.getCohortes() == null || escuela.getCohortes().isEmpty()) {
            addErrorMessage(getPropertyFromBundle("formacion.msg.validate.escuela.cohorte"));
            throw new ValidationsException();
          }
        }
      }
    }
    {
      // la sumatoria de numero de estudiantes por corte, no puede ser mayor al numero de la necesidad de la formacion
      int cantidadEstudiantesAcomulados = 0;
      if (selected.getEscuelas() != null) {
        for (FormacionEscuela escuela : selected.getEscuelas()) {
          if (escuela.getCohortes() != null) {
            for (FormacionEscuelaCohorte cohorte : escuela.getCohortes()) {
              cantidadEstudiantesAcomulados += cohorte.getNumeroEstudiantes();
            }
          }
        }
      }
      if (cantidadEstudiantesAcomulados > selected.getNecesidad()) {
        addErrorMessage(getPropertyFromBundle("formacion.msg.validate.necesidad"));
        throw new ValidationsException();
      }
    }
  }

  public boolean enableReprobate() {
    return selected != null
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && (selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || selected.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado()))
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onReprobate() {
    LOG.trace("metodo: onReprobate()");
    try {
      selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      service.reprobar(getSelected());
      selected = service.findFullById(selected.getId());
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onReprobate() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
    }
  }

  public String onBack() {
    LOG.trace("metodo: onBack()");
    initializeList();
    onLoadList();
    return navigationFaces.redirectToList_CU05_CAP_FORMACION_PAE();
  }

  public boolean enableCreateCohorte() {
    return editable
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onCreateCohorte(FormacionEscuela escuela) {
    LOG.trace("metodo: onCreateCohorte()");
    cohorteSelected = new FormacionEscuelaCohorte(null, selected.getId(), escuela.getEscuela().getPk().getFuerza(), escuela.getEscuela().getPk().getConsecutivo());
    initializeEditCohorte();
    showFormCohorte = true;
  }

  private void initializeEditCohorte() {
    LOG.trace("metodo: initializeEditCohorte()");
    this.showFormCohorte = true;
  }

  public boolean enableEditCohorte() {
    return editable
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onEditCohorte(FormacionEscuelaCohorte cohorte) {
    LOG.trace("metodo: onEditCohorte()");
    cohorteSelected = cohorte;
    this.initializeEditCohorte();
  }

  public void onSaveCohorte() {
    LOG.trace("metodo: onSavePresupuesto()");
    try {
      // validaciones
      this.validateSaveCohorte();
      try {
        if (cohorteSelected.getPk().getCohorte() == null) {
          cohorteSelected.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
          service.createCohorte(cohorteSelected);
        } else {
          cohorteSelected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
          service.updateCohorte(cohorteSelected);
        }
        showFormCohorte = false;
        cohorteSelected = null;
        selected = this.service.findFullById(this.selected.getId());
        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
      } catch (Exception ex) {
        LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    } catch (ValidationsException ex) {
      LOG.error("metodo: onApprove() ->> mensaje ->> {}", ex.getMessage());
    }
  }

  private void validateSaveCohorte() throws ValidationsException {
    LOG.trace("metodo: validate()");
    {
      // la sumatoria de numero de estudiantes por corte, no puede ser mayor al numero de la necesidad de la formacion
      int cantidadEstudiantesAcomulados = 0;
      if (selected.getEscuelas() != null) {
        for (FormacionEscuela escuela : selected.getEscuelas()) {
          if (escuela.getCohortes() != null) {
            for (FormacionEscuelaCohorte cohorte : escuela.getCohortes()) {
              cantidadEstudiantesAcomulados += cohorte.getNumeroEstudiantes();
            }
          }
        }
      }
      int sumatoriaTotal;
      if (cohorteSelected.getPk().getCohorte() == null) {
        sumatoriaTotal = cantidadEstudiantesAcomulados + cohorteSelected.getNumeroEstudiantes();
      } else {
        sumatoriaTotal = cantidadEstudiantesAcomulados;
      }
      if (sumatoriaTotal > selected.getNecesidad()) {
        addErrorMessage(getPropertyFromBundle("formacion.msg.validate.necesidad"));
        throw new ValidationsException();
      }
    }
  }

  public void onCancelSaveCohorte() {
    LOG.trace("metodo: onCancelSavePresupuesto()");
    this.showFormCohorte = false;
    if (this.cohorteSelected.getPk().getCohorte() == null) {
      this.cohorteSelected = null;
    }
  }

  public boolean enableDeleteCohorte() {
    return editable
            && selected.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && selected.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIACA.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onDeleteCohorte(FormacionEscuelaCohorte cohorte) {
    LOG.trace("metodo: onDeleteCohorte()");
    try {
      cohorteSelected = cohorte;
      cohorteSelected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      service.deleteCohorte(cohorteSelected);
      selected = this.service.findFullById(this.selected.getId());
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
    }
  }

  private void loadVigencias() {
    LOG.trace("metodo: loadVigencias()");
    try {
      this.vigencias = servicePAE.consultarVigencias();
    } catch (Exception ex) {
      LOG.error("metodo: loadVigencias() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public FormacionFiltro getFiltro() {
    return filtro;
  }

  public void setFiltro(FormacionFiltro filtro) {
    this.filtro = filtro;
  }

  public List<Formacion> getList() {
    return list;
  }

  public void setList(List<Formacion> list) {
    this.list = list;
  }

  public Formacion getSelected() {
    return selected;
  }

  public void setSelected(Formacion selected) {
    this.selected = selected;
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

  public DualListModel<UnidadDependencia> getEscuelas() {
    return escuelas;
  }

  public void setEscuelas(DualListModel<UnidadDependencia> escuelas) {
    this.escuelas = escuelas;
  }

  public FormacionEscuelaCohorte getCohorteSelected() {
    return cohorteSelected;
  }

  public void setCohorteSelected(FormacionEscuelaCohorte cohorteSelected) {
    this.cohorteSelected = cohorteSelected;
  }

  public boolean isShowFormAssociateEscuelas() {
    return showFormAssociateEscuelas;
  }

  public void setShowFormAssociateEscuelas(boolean showFormAssociateEscuelas) {
    this.showFormAssociateEscuelas = showFormAssociateEscuelas;
  }

  public boolean isShowFormCohorte() {
    return showFormCohorte;
  }

  public void setShowFormCohorte(boolean showFormCohorte) {
    this.showFormCohorte = showFormCohorte;
  }
}
