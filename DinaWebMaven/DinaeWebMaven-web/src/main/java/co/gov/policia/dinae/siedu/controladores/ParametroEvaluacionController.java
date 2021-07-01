package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.ParametroEvaluacion;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.ParametroEvaluacionService;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Juan Jose Buzon
 */
@Named
@SessionScoped
public class ParametroEvaluacionController extends AbstractController {

  /**
   *
   */
  private static final long serialVersionUID = -1211476164521960978L;

  private static final Logger LOG = LoggerFactory.getLogger(ParametroEvaluacionController.class);

  @EJB
  private APPService serviceAPP;
  @EJB
  private ParametroEvaluacionService parametroEvaluacionService;

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;

  private NavEnum optionNavEnum;
  private List<Dominio> tiposParametros;
  private Dominio tipo;
  private List<ParametroEvaluacion> parametrosEvaluacion;
  private ParametroEvaluacion selected;
  private boolean validaRol = false;

  public ParametroEvaluacionController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    parametrosEvaluacion = new ArrayList<>();
    selected = new ParametroEvaluacion();
    selected.setTipo(new Dominio());
    cargarTiposParametros();
    validaRol();
  }

  public String initReturnCU() throws Exception {
    releaseControllers();
    return navigationFaces.redirectCUAdministrarParametrosEvaluaciones();
  }

  private void cargarTiposParametros() {
    LOG.trace("metodo: cargarTiposParametros()");
    try {
      tiposParametros = serviceAPP
              .consultarDominios(TipoDominioEnum.TIPO_PARAMETRO_EVALUACION
                      .getId());
    } catch (Exception e) {
      LOG.error("metodo: cargarTiposParametros() ->> mensaje: {}",
              e.getMessage());
      addErrorMessage(
              getPropertyFromBundle("commons.msg.error.read.list.summary"),
              getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public boolean validaRol() {
    validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
    return validaRol;
  }

  public void buscarParametrosEvaluacion() {
    LOG.trace("metodo: cargarTiposParametros()");
    try {
      parametrosEvaluacion = parametroEvaluacionService.findByType(tipo.getId());
      if (parametrosEvaluacion.isEmpty()) {
        addInfoMessage(getPropertyFromBundle("commons.msg.success.summary"), getPropertyFromBundle("commons.dt.emptymessage"));
      }
    } catch (Exception e) {
      LOG.error("metodo: buscarParametrosEvaluacion() ->> mensaje: {}", e.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void eliminarParametroEvaluacion() {
    LOG.trace("metodo: eliminarParametroEvaluacion()");
    try {
      parametroEvaluacionService.delete(selected.getId());
      parametrosEvaluacion.remove(selected);
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (Exception e) {
      LOG.error("metodo: eliminarParametroEvaluacion() ->> mensaje: {}", e.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void agregarParametro() {
    LOG.trace("metodo: agregarEvaluacion()");
    try {
      selected = new ParametroEvaluacion();
      selected.setTipo(tipo);

      optionNavEnum = NavEnum.NEW;
    } catch (Exception ex) {
      LOG.error("metodo: agregarEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void guardarParametro() {
    LOG.trace("metodo: agregarEvaluacion()");
    try {
      selected = parametroEvaluacionService.create(selected);
      parametrosEvaluacion.add(selected);
      optionNavEnum = NavEnum.LIST;
      RequestContext.getCurrentInstance().addCallbackParam("saved", true);
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: agregarEvaluacion() ->> mensaje: {}", ex.getMessage());
      if (ExceptionUtil.isException(ex, "SIEDU_PARAMETRO_EVALUACION_UK")) {
        addErrorMessage(getPropertyFromBundle("evaluacion.msg.error.save.param.description.uk.summary"), getPropertyFromBundle("evaluacion.msg.error.save.param.description.uk.detail"), "messagesDialog");
      } else {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"), "messagesDialog");
      }
      RequestContext.getCurrentInstance().addCallbackParam("saved", false);
    }
  }

  public NavEnum getOptionNavEnum() {
    return optionNavEnum;
  }

  public void setOptionNavEnum(NavEnum optionNavEnum) {
    this.optionNavEnum = optionNavEnum;
  }

  public List<Dominio> getTiposParametros() {
    return tiposParametros;
  }

  public void setTiposParametros(List<Dominio> tiposParametros) {
    this.tiposParametros = tiposParametros;
  }

  public Dominio getTipo() {
    return tipo;
  }

  public void setTipo(Dominio dominio) {
    this.tipo = dominio;
  }

  public List<ParametroEvaluacion> getParametrosEvaluacion() {
    return parametrosEvaluacion;
  }

  public void setParametrosEvaluacion(
          List<ParametroEvaluacion> parametrosEvaluacion) {
    this.parametrosEvaluacion = parametrosEvaluacion;
  }

  public ParametroEvaluacion getSelected() {
    return selected;
  }

  public void setSelected(ParametroEvaluacion selected) {
    this.selected = selected;
  }

  public boolean isValidaRol() {
    return validaRol;
  }

  public void setValidaRol(boolean validaRol) {
    this.validaRol = validaRol;
  }

}
