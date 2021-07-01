/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadDependenciaPK;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.filtros.CoberturaFiltro;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.Cobertura;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.servicios.APPService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.CoberturaService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
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
public class CoberturaController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(CoberturaController.class);

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
  @EJB
  private PAEService servicePAE;
  @EJB
  private APPService serviceAPP;
  @EJB
  private CoberturaService service;
  @EJB
  private IUnidadDependenciaLocal unidadDependencia;
  
  private Cobertura selected;
  private CoberturaFiltro filtro;
  private List<Cobertura> list;
  private boolean editable;
  private NavEnum optionNavEnum;
  private List<PAE> vigencias;
  private List<UnidadDependencia> escuelas;
  private List<Dominio> estrategias;
  private List<UnidadDependencia> unidades;
  private List<UnidadDependencia> unidadesSelected;
 

  public CoberturaController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");    
    this.filtro = new CoberturaFiltro();
    this.initializeList();
    this.loadVigencias();
    this.loadEscuelas();
    this.loadEstrategias();
    this.loadUnidades();    
  }

  public String initReturnCU() {
    LOG.trace("metodo: initReturnCU()");
    releaseControllers();
    return navigationFaces.redirect_CU02_CAP_COBERTURA_PAE();
  }

  private void initializeList() {
    LOG.trace("metodo: initializeList()");
    this.optionNavEnum = NavEnum.LIST;
    this.selected = null;
    this.editable = false;
    this.unidadesSelected = null;
  }

  public void onLoadList() {
    LOG.trace("metodo: onLoadList()");
    try {
      this.selected = null;
      this.list = this.service.findByFilter(getFiltro());
    } catch (Exception ex) {
      LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  private void initializeEdit() {
    LOG.trace("metodo: initializeEdit()");
    editable = true;
  }

  public boolean enableCreate() {
    return !isReadOnly()
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
  }

  public void onCreate() {
    LOG.trace("metodo: onCreate()");
    this.optionNavEnum = NavEnum.DETAILS;
    this.selected = new Cobertura();
    this.selected.setPae(this.filtro.getPae());
    this.selected.setEscuela(this.filtro.getEscuela());
    this.selected.setEstrategia(this.filtro.getEstrategia());
    initializeEdit();
  }

  public boolean enableDelete() {
    return !isReadOnly()
            && this.selected != null
            && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
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
      if (ExceptionUtil.isException(ex, "_FK")) {
        addErrorMessage(getPropertyFromBundle("cobertura.msg.error.delete.fk.summary"), getPropertyFromBundle("cobertura.msg.error.delete.fk.detail"));
      } else {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
      }
    }
  }

  public void onSave() {
    LOG.trace("metodo: onSave()");
    try {
      this.selected.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
      this.service.create(getSelected(), unidadesSelected);
      this.initializeList();
      this.onLoadList();
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
      if (ExceptionUtil.isException(ex, "SIEDU_COBERTURA_UK")) {
        addErrorMessage(getPropertyFromBundle("cobertura.msg.error.save.uk.summary"), getPropertyFromBundle("cobertura.msg.error.save.uk.detail"));
      } else {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    }
  }

  public void onCancel() {
    LOG.trace("metodo: onCancel()");
    this.initializeList();
  }

  public boolean isShowList() {
    boolean showList = (optionNavEnum == NavEnum.LIST);
    return showList;
  }

  public boolean isShowDetails() {
    boolean showDetails = (optionNavEnum == NavEnum.DETAILS);
    return showDetails;
  }

  public boolean isNewRecord() {
    boolean newRecord = (getSelected() != null && getSelected().getId() == null);
    return newRecord;
  }

  private boolean isReadOnly() {
    if (this.filtro == null) {
      return true;
    } else if (this.filtro.getPae() == null) {
      return true;
    } else {
      return this.filtro.getPae().getEstado().equals(PAEEstadoEnum.CERRADO.toString());
    }
  }

  private void loadVigencias() {
    LOG.trace("metodo: loadVigencias()");
    try {
      setVigencias(this.servicePAE.consultarVigencias());
    } catch (Exception ex) {
      LOG.error("metodo: loadVigencias() ->> mensaje: {}", ex.getMessage());
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

  private void loadEstrategias() {
    LOG.trace("metodo: loadEstrategias()");
    try {
      setEstrategias(this.serviceAPP.consultarDominios(TipoDominioEnum.ESTRATEGIA.getId()));
    } catch (Exception ex) {
      LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
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

  public Cobertura getSelected() {
    return selected;
  }

  public void setSelected(Cobertura selected) {
    this.selected = selected;
  }

  public CoberturaFiltro getFiltro() {
    return filtro;
  }

  public void setFiltro(CoberturaFiltro filtro) {
    this.filtro = filtro;
  }

  public List<Cobertura> getList() {
    return list;
  }

  public void setList(List<Cobertura> list) {
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

  public List<Dominio> getEstrategias() {
    return estrategias;
  }

  public void setEstrategias(List<Dominio> estrategias) {
    this.estrategias = estrategias;
  }

  public List<UnidadDependencia> getUnidades() {
    return unidades;
  }

  public void setUnidades(List<UnidadDependencia> unidades) {
    this.unidades = unidades;
  }

  public List<UnidadDependencia> getUnidadesSelected() {
    return unidadesSelected;
  }

  public void setUnidadesSelected(List<UnidadDependencia> unidadesSelected) {
    this.unidadesSelected = unidadesSelected;
  }
  
}
