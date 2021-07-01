/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.Auditoria;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.AuditoriaService;
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
public class AuditoriaController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(AuditoriaController.class);

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
  @EJB
  private AuditoriaService service;
  private Auditoria selected;
  private List<Auditoria> list;
  private NavEnum optionNavEnum;

  public AuditoriaController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    this.optionNavEnum = NavEnum.LIST;
    this.selected = (null);
    this.loadList();
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {
    LOG.trace("metodo: initReturnCU()");
    releaseControllers();
    return navigationFaces.redirectSIEDUAuditoria();
  }

  /**
   *
   */
  public void loadList() {
    LOG.trace("metodo: loadList()");
    try {
      this.list = service.findAll();
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  /**
   *
   * @param event
   */
  public void onRowSelect(SelectEvent event) {
    LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
    this.selected = ((Auditoria) event.getObject());
    this.optionNavEnum = NavEnum.DETAILS;
  }

  /**
   *
   */
  public void onBack() {
    LOG.trace("metodo: onBack()");
    optionNavEnum = NavEnum.LIST;
    this.selected = null;
  }

  /**
   *
   * @return
   */
  public boolean isShowList() {
    return optionNavEnum == NavEnum.LIST;
  }

  /**
   *
   * @return
   */
  public boolean isShowDetails() {
    return optionNavEnum == NavEnum.DETAILS;
  }

  /**
   * @return the selected
   */
  public Auditoria getSelected() {
    return selected;
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(Auditoria selected) {
    this.selected = selected;
  }

  /**
   * @return the list
   */
  public List<Auditoria> getList() {
    return list;
  }

  /**
   * @param list the list to set
   */
  public void setList(List<Auditoria> list) {
    this.list = list;
  }
}
