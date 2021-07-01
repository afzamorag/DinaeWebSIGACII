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
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.ProcesoEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.NecesidadFiltro;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.Necesidad;
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
import co.gov.policia.dinae.siedu.servicios.NecesidadService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.Regionales;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class NecesidadController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(NecesidadController.class);

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
  @EJB
  private PAEService servicePAE;
  @EJB
  private APPService serviceAPP;
  @EJB
  private NecesidadService service;
  private Necesidad selected;
  private NecesidadFiltro filtro;
  private List<Necesidad> list;
  private boolean editable;
  private NavEnum optionNavEnum;
  private List<PAE> vigencias;
  private List<Regionales> regiones;
  private List<UnidadDependencia> unidadesFisicas;
  private List<UnidadDependencia> unidadesDependencias;
  private List<NivelesAcademicos> nivelesAcademicos;
  private List<Carreras> programas;
  private List<Dominio> procesos;
  private List<Dominio> estrategias;
  private List<NecesidadParcial> necesidadesParciales;

  public NecesidadController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    this.filtro = new NecesidadFiltro();
    this.loadVigencias();
    this.initializeList();
  }

  public String initReturnCU() {
    LOG.trace("metodo: initReturnCU()");
    releaseControllers();
    return navigationFaces.redirect_CU03_CAP_NECESIDADES_PAE();
  }

  public boolean enableImportNeeds() {
    return this.filtro != null
            && this.filtro.getPae() != null
            && this.filtro.getPae().getNecesidadesImportadas().equalsIgnoreCase(DecisionEnum.NO.toString())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onImportNeeds() {
    LOG.trace("metodo: onImportNeeds()");
    try {
      SPFiltro paramsSP = new SPFiltro();
      paramsSP.setPae(this.filtro.getPae().getId());
      paramsSP.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      addInfoMessage("Exitoso", this.service.importNeeds(paramsSP));
      this.filtro.getPae().setNecesidadesImportadas(DecisionEnum.SI.toString());
      this.onLoadList();
    } catch (ServiceException ex) {
      LOG.error("metodo: onImportNeeds() ->> mensaje: {}", ex.getMessage());
      addErrorMessage("Error", ex.getMessage());
    }
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
      this.list = this.service.findByFilter(getFiltro());
    } catch (Exception ex) {
      LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  private void initializeEdit() {
    LOG.trace("metodo: initializeEdit()");
    this.editable = true;
    this.loadRegiones();
    this.loadUnidadesFisicas();
    this.loadUnidadesDependencias();
    this.loadNivelesAcademicos();
    this.loadCarreras();
    this.loadProcesos();
    this.loadEstrategias();
  }

  public boolean enableCreate() {
    return this.filtro != null
            && this.filtro.getPae() != null
            && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onCreate() {
    LOG.trace("metodo: onCreate()");
    this.optionNavEnum = NavEnum.DETAILS;
    this.selected = new Necesidad();
    this.selected.setPae(this.filtro.getPae());
    this.selected.setCarrera(new Carreras());
    this.selected.getCarrera().setNivelAcademico(new NivelesAcademicos());
    this.selected.setOrigen(this.loginFaces.getPerfilUsuarioDTO().getRolDepenencia());
    this.initializeEdit();
  }

  public void onRowSelect(SelectEvent event) {
    LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
    this.selected = (Necesidad) event.getObject();
    this.editable = false;
    this.optionNavEnum = NavEnum.DETAILS;
  }

  public boolean enableEdit() {
    return this.selected != null
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.PLANE.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onEdit() {
    LOG.trace("metodo: onEdit()");
    this.initializeEdit();
  }

  public boolean enableDelete() {
    return this.selected != null
            && this.selected.getId() != null
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.PLANE.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onDelete() {
    LOG.trace("metodo: onDelete()");
    try {
      this.selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      this.service.delete(getSelected());
      this.onLoadList();
      this.initializeList();
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
    }
  }

  public void onSave() {
    LOG.trace("metodo: onSave()");
    try {
      if (this.selected.getId() == null) {
        this.selected.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
        this.service.create(getSelected());
        this.onLoadList();
      } else {
        this.selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
        this.service.update(getSelected());
      }
      this.editable = false;
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
      if (ExceptionUtil.isException(ex, "SIEDU_NECESID_UK")) {
        addErrorMessage(getPropertyFromBundle("necesidad.msg.error.save.uk.summary"), getPropertyFromBundle("necesidad.msg.error.save.uk.detail"));
      } else {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    }
  }

  public void onCancel() {
    LOG.trace("metodo: onCancel()");
    this.editable = false;
    if (this.selected.getId() == null) {
      this.optionNavEnum = NavEnum.LIST;
      this.selected = null;
    }
  }

  public boolean enableAssociateProcesses() {
    return this.list != null
            && !this.list.isEmpty()
            && this.filtro.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) 
            || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onAssociateProcesses() {
    LOG.trace("metodo: onAssociateProcesses()");
    Map<Long, Necesidad> programasmap = new HashMap<>();
    if (this.list != null) {
      this.loadProcesos();
      this.loadEstrategias();
      this.necesidadesParciales = new ArrayList<>();
      for (Necesidad necesidad : this.list) {
        programasmap.put(necesidad.getCarrera().getCarrerasPK().getIdCarrera(), necesidad);
      }
      for (Necesidad necesidad : programasmap.values()) {
        NecesidadParcial necesidadParcial = new NecesidadParcial();
        necesidadParcial.setPrograma(necesidad.getCarrera());
        necesidadParcial.setProceso(necesidad.getProceso());
        necesidadParcial.setEstrategia(necesidad.getEstrategia());
        this.necesidadesParciales.add(necesidadParcial);
      }
    }
    this.optionNavEnum = NavEnum.OTRO;
  }

  public void onSaveAssociateProcesses() {
    LOG.trace("metodo: onAssociateProcesses()");
    Map<Long, NecesidadParcial> programasmap = new HashMap<>();
    if (this.necesidadesParciales != null) {
      for (NecesidadParcial necesidad : this.necesidadesParciales) {
        programasmap.put(necesidad.getPrograma().getCarrerasPK().getIdCarrera(), necesidad);
      }
      for (Necesidad necesidad : this.list) {
        NecesidadParcial necesidadParcial = programasmap.get(necesidad.getCarrera().getCarrerasPK().getIdCarrera());
        necesidad.setProceso(necesidadParcial.getProceso());
        necesidad.setEstrategia(necesidadParcial.getEstrategia());
        necesidad.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      }
      try {
        this.service.update(this.list);
        addInfoMessage(getPropertyFromBundle("necesidad.msg.success.save.associate.processes.summary"), getPropertyFromBundle("necesidad.msg.success.save.associate.processes.detail"));
        this.initializeList();
        this.onLoadList();
      } catch (ServiceException ex) {
        LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
        addErrorMessage(getPropertyFromBundle("necesidad.msg.error.save.associate.processes.summary"), getPropertyFromBundle("necesidad.msg.error.save.associate.processes.detail"));
      }
    }
    this.optionNavEnum = NavEnum.LIST;
  }

  public void onBackToList() {
    LOG.trace("metodo: onBackToList()");
    this.initializeList();
  }

  public boolean isShowList() {
    return (optionNavEnum == NavEnum.LIST);
  }

  public boolean isShowDetails() {
    return (optionNavEnum == NavEnum.DETAILS);
  }

  public boolean isShowAssociateProcesses() {
    return (optionNavEnum == NavEnum.OTRO);
  }

  public boolean isNewRecord() {
    return (this.selected != null && this.selected.getId() == null);
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

  private void loadRegiones() {
    LOG.trace("metodo: loadRegiones()");
    try {
      setRegiones(serviceAPP.consultarRegionales());
    } catch (Exception ex) {
      LOG.error("metodo: loadRegiones() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void loadUnidadesFisicas() {
    LOG.trace("metodo: loadUnidadesFisicas()");
    if (selected.getRegion() == null) {
      unidadesFisicas = null;
    } else {
      try {
        unidadesFisicas = (serviceAPP.consultarUnidadesFisicasVigentes(selected.getRegion().getId()));        
      } catch (Exception ex) {
        LOG.error("metodo: loadUnidadesFisicas() ->> mensaje: {}", ex.getMessage());
        addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
      }
    }
  }

  private void loadUnidadesDependencias() {
    LOG.trace("metodo: loadUnidadesDependencias()");
    try {
      unidadesDependencias = serviceAPP.consultarDireccionesYAsesorasVigentes();
    } catch (Exception ex) {
      LOG.error("metodo: loadUnidadesDependencias() ->> mensaje: {}", ex.getMessage());
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

  public void loadCarreras() {
    LOG.trace("metodo: loadCarreras()");
    if (this.selected.getCarrera().getNivelAcademico() == null || this.selected.getCarrera().getNivelAcademico().getIdNivelAcademico() == null) {
      setProgramas(null);
    } else {
      try {
        this.programas = this.serviceAPP.consultarCarrerasVigentes(this.selected.getCarrera().getNivelAcademico().getIdNivelAcademico());
      } catch (Exception ex) {
        LOG.error("metodo: loadCarreras() ->> mensaje: {}", ex.getMessage());
        addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
      }
    }
  }

  private void loadProcesos() {
    LOG.trace("metodo: loadProcesos()");
    try {
      setProcesos(serviceAPP.consultarDominios(TipoDominioEnum.PROCESO.getId()));
    } catch (Exception ex) {
      LOG.error("metodo: loadProcesos() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  private void loadEstrategias() {
    LOG.trace("metodo: loadEstrategias()");
    try {
      setEstrategias(serviceAPP.consultarDominios(TipoDominioEnum.ESTRATEGIA.getId()));
    } catch (Exception ex) {
      LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public Necesidad getSelected() {
    return selected;
  }

  public void setSelected(Necesidad selected) {
    this.selected = selected;
  }

  public NecesidadFiltro getFiltro() {
    return filtro;
  }

  public void setFiltro(NecesidadFiltro filtro) {
    this.filtro = filtro;
  }

  public List<Necesidad> getList() {
    return list;
  }

  public void setList(List<Necesidad> list) {
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

  public List<Regionales> getRegiones() {
    return regiones;
  }

  public void setRegiones(List<Regionales> regiones) {
    this.regiones = regiones;
  }

  public List<UnidadDependencia> getUnidadesFisicas() {
    return unidadesFisicas;
  }

  public void setUnidadesFisicas(List<UnidadDependencia> unidadesFisicas) {
    this.unidadesFisicas = unidadesFisicas;
  }

  public List<UnidadDependencia> getUnidadesDependencias() {
    return unidadesDependencias;
  }

  public void setUnidadesDependencias(List<UnidadDependencia> unidadesDependencias) {
    this.unidadesDependencias = unidadesDependencias;
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

  public List<Dominio> getProcesos() {
    return procesos;
  }

  public void setProcesos(List<Dominio> procesos) {
    this.procesos = procesos;
  }

  public List<Dominio> getEstrategias() {
    return estrategias;
  }

  public void setEstrategias(List<Dominio> estrategias) {
    this.estrategias = estrategias;
  }

  /**
   * @return the necesidadesParciales
   */
  public List<NecesidadParcial> getNecesidadesParciales() {
    return necesidadesParciales;
  }

  /**
   * @param necesidadesParciales the necesidadesParciales to set
   */
  public void setNecesidadesParciales(List<NecesidadParcial> necesidadesParciales) {
    this.necesidadesParciales = necesidadesParciales;
  }

  public class NecesidadParcial {

    private Carreras programa;
    private Dominio proceso;
    private Dominio estrategia;

    /**
     *
     * @return
     */
    public boolean disableEstrategia() {
      return (proceso != null && proceso.getId().equals(ProcesoEnum.FORMACION.getId()));
    }

    /**
     *
     * @return
     */
    public boolean requiredEstrategia() {
      return (proceso != null && proceso.getId().equals(ProcesoEnum.CAPACITACION.getId()));
    }

    /**
     * @return the programa
     */
    public Carreras getPrograma() {
      return programa;
    }

    /**
     * @param programa the programa to set
     */
    public void setPrograma(Carreras programa) {
      this.programa = programa;
    }

    /**
     * @return the proceso
     */
    public Dominio getProceso() {
      return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(Dominio proceso) {
      if (proceso != null && proceso.getId().equals(ProcesoEnum.FORMACION.getId())) {
        this.estrategia = null;
      }
      this.proceso = proceso;
    }

    /**
     * @return the estrategia
     */
    public Dominio getEstrategia() {
      return estrategia;
    }

    /**
     * @param estrategia the estrategia to set
     */
    public void setEstrategia(Dominio estrategia) {
      this.estrategia = estrategia;
    }
  }
}
