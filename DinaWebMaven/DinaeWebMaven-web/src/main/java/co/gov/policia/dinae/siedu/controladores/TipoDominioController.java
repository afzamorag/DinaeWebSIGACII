/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.modelo.DominioTipo;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.DominioTipoService;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class TipoDominioController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(TipoDominioController.class);

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
  @EJB
  private DominioTipoService service;
  private DominioTipo selected;
  private List<DominioTipo> list;
  private boolean editable;

  public TipoDominioController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    initializeList();
    loadList();
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {
    LOG.trace("metodo: initReturnCU()");
    releaseControllers();
    return navigationFaces.redirectToListTiposDominiosSIEDU();
  }

  /**
   *
   */
  private void initializeList() {
    LOG.trace("metodo: initializeList()");
    this.selected = null;
    this.editable = false;
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

  /**
   *
   */
  private void initializeEdit() {
    LOG.trace("metodo: initializeEdit()");
    setEditable(true);
  }

  /**
   *
   * @return
   */
  public String onCreate() {
    LOG.trace("metodo: onCreate()");
    setSelected(new DominioTipo());
    initializeEdit();
    return navigationFaces.redirectToDetailTipoDominioSIEDU();
  }

  /**
   *
   * @return
   */
  public String onRowSelect() {
    LOG.trace("metodo: onRowSelect()");
    setEditable(false);
    return navigationFaces.redirectToDetailTipoDominioSIEDU();
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
   * @return
   */
  public String onDelete() {
    LOG.trace("metodo: onDelete()");
    try {
      service.delete(getSelected());
      initializeList();
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
      return navigationFaces.redirectToListTiposDominiosSIEDU();
    } catch (Exception ex) {
      LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
      return null;
    }
  }

  /**
   *
   */
  public void onSave() {
    LOG.trace("metodo: onSave()");
    try {
      if (getSelected().getId() == null) {
        service.create(getSelected());
        loadList();
      } else {
        service.update(getSelected());
      }
      setEditable(false);
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
    }
  }

  /**
   *
   * @return
   */
  public String onCancel() {
    LOG.trace("metodo: onCancel()");
    if (getSelected().getId() == null) {
      initializeList();
      return navigationFaces.redirectToListTiposDominiosSIEDU();
    } else {
      setEditable(false);
      return null;
    }
  }

  /**
   *
   * @return
   */
  public String onBack() {
    LOG.trace("metodo: onBack()");
    initializeList();
    return navigationFaces.redirectToListTiposDominiosSIEDU();
  }

  /**
   *
   * @return
   */
  public boolean isNewRecord() {
    boolean newRecord = (getSelected() != null && getSelected().getId() == null);
    return newRecord;
  }

  /**
   * @return the selected
   */
  public DominioTipo getSelected() {
    return selected;
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(DominioTipo selected) {
    this.selected = selected;
  }

  /**
   * @return the list
   */
  public List<DominioTipo> getList() {
    return list;
  }

  /**
   * @param list the list to set
   */
  public void setList(List<DominioTipo> list) {
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
}
