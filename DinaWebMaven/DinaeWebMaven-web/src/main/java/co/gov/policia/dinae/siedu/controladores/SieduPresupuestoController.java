/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.Presupuesto;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import co.gov.policia.dinae.siedu.servicios.PresupuestoService;


/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class SieduPresupuestoController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(SieduPresupuestoController.class);

  @EJB
  private PresupuestoService service;
  private Presupuesto selected;
  private List<Presupuesto> list;
  private boolean editable;
  private NavEnum optionNavEnum;

  public SieduPresupuestoController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    optionNavEnum = NavEnum.LIST;
    setSelected(null);
    setEditable(false);
    loadList();
    
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
   */
  public void onCreate() {
    LOG.trace("metodo: onCreate()");
    optionNavEnum = NavEnum.DETAILS;
    setSelected(new Presupuesto());
    initializeEdit();
  }

  /**
   *
   * @param event
   */
  public void onRowSelect(SelectEvent event) {
    LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
    setSelected((Presupuesto) event.getObject());
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
  public void onSave() {
    LOG.trace("metodo: onSave()");
    try {
      if (getSelected().getId() == null) {
        service.create(getSelected());
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
   */
  public void onCancel() {
    LOG.trace("metodo: onCancel()");
    setEditable(false);
    if (getSelected().getId() == null) {
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
    boolean newRecord = (getSelected() != null && getSelected().getId() == null);
    return newRecord;
  }

  /**
   * @return the selected
   */
  public Presupuesto getSelected() {
    return selected;
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(Presupuesto selected) {
    this.selected = selected;
  }

  /**
   * @return the list
   */
  public List<Presupuesto> getList() {
    return list;
  }

  /**
   * @param list the list to set
   */
  public void setList(List<Presupuesto> list) {
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
