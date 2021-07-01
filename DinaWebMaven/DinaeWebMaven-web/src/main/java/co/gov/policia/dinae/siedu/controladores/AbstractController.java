/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.controladores;

import static co.gov.policia.dinae.util.JSFUtils.copiarArchivoRutaFisica;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public abstract class AbstractController implements Serializable {

  public static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

  private ResourceBundle bundle;

  public Date getSysdate() {
    LOG.debug("method: getSysdate()");
    return new Date();
  }

  public Date getFirtsDateCurrentYear() {
    LOG.debug("method: getFirtsDateCurrentYear()");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    Integer currentYear = calendar.get(Calendar.YEAR);
    Calendar firtsDate = new GregorianCalendar(currentYear, 0, 1);
    return firtsDate.getTime();
  }

  /**
   *
   * @return
   */
  protected FacesContext getFacesContextCurrentInstance() {
    LOG.debug("method: getFacesContextCurrentInstance()");
    return FacesContext.getCurrentInstance();
  }

  /**
   *
   * @param severity
   * @param summary
   * @param detail
   */
  private void addMessage(Severity severity, String summary, String detail) {
    LOG.debug("method: addMensaje()");
    FacesMessage mensaje = new FacesMessage(severity, summary, detail);
    getFacesContextCurrentInstance().addMessage(null, mensaje);
  }

  /**
   *
   * @param severity
   * @param summary
   * @param detail
   * @param clientId
   */
  private void addMessage(Severity severity, String summary, String detail, String clientId) {
    LOG.debug("method: addMensaje()");
    FacesMessage mensaje = new FacesMessage(severity, summary, detail);
    getFacesContextCurrentInstance().addMessage(clientId, mensaje);
  }

  /**
   *
   * @param summary
   */
  public void addInfoMessage(String summary) {
    LOG.debug("method: addInfoMesage()");
    addMessage(FacesMessage.SEVERITY_INFO, summary, null);
  }

  /**
   *
   * @param summary
   * @param detail
   */
  public void addInfoMessage(String summary, String detail) {
    LOG.debug("method: addInfoMesage()");
    addMessage(FacesMessage.SEVERITY_INFO, summary, detail);
  }

  /**
   *
   * @param summary
   * @param detail
   * @param clientId
   */
  public void addInfoMessage(String summary, String detail, String clientId) {
    LOG.debug("method: addInfoMessage()");
    addMessage(FacesMessage.SEVERITY_INFO, summary, detail, clientId);
  }

  /**
   *
   * @param summary
   */
  public void addErrorMessage(String summary) {
    LOG.debug("method: addErrorMessage()");
    addMessage(FacesMessage.SEVERITY_ERROR, summary, null);
  }

  /**
   *
   * @param summary
   * @param detail
   */
  public void addErrorMessage(String summary, String detail) {
    LOG.debug("method: addErrorMessage()");
    addMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
  }

  /**
   *
   * @param summary
   * @param detail
   * @param clientId
   */
  public void addErrorMessage(String summary, String detail, String clientId) {
    LOG.debug("method: addErrorMessage()");
    addMessage(FacesMessage.SEVERITY_ERROR, summary, detail, clientId);
  }

  /**
   *
   * @param summary
   */
  public void addFatalMessage(String summary) {
    LOG.debug("method: addFatalMessage()");
    addMessage(FacesMessage.SEVERITY_FATAL, summary, null);
  }

  /**
   *
   * @param summary
   * @param detail
   */
  public void addFatalMessage(String summary, String detail) {
    LOG.debug("method: addFatalMessage()");
    addMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
  }

  /**
   *
   * @param summary
   * @param detail
   * @param clientId
   */
  public void addFatalMessage(String summary, String detail, String clientId) {
    LOG.debug("method: addFatalMessage()");
    addMessage(FacesMessage.SEVERITY_FATAL, summary, detail, clientId);
  }

  /**
   *
   * @param summary
   */
  public void addWarnMessage(String summary) {
    LOG.debug("method: addWarnMessage()");
    addMessage(FacesMessage.SEVERITY_WARN, summary, null);
  }

  /**
   *
   * @param summary
   * @param detail
   */
  public void addWarnMessage(String summary, String detail) {
    LOG.debug("method: addWarnMessage()");
    addMessage(FacesMessage.SEVERITY_WARN, summary, detail);
  }

  /**
   *
   * @param summary
   * @param detail
   * @param clientId
   */
  public void addWarnMessage(String summary, String detail, String clientId) {
    LOG.debug("method: addWarnMessage()");
    addMessage(FacesMessage.SEVERITY_WARN, summary, detail, clientId);
  }

  /**
   *
   * @param key
   * @return
   */
  public Object getValueFromSessionMap(String key) {
    LOG.debug("method: getValueFromSessionMap()");
    return getFacesContextCurrentInstance().getExternalContext().getSessionMap().get(key);
  }

  /**
   *
   * @param key
   * @param value
   */
  public void setValueIntoSessionMap(String key, Object value) {
    LOG.debug("method: setValueIntoSessionMap()");
    getFacesContextCurrentInstance().getExternalContext().getSessionMap().put(key, value);
  }

  /**
   *
   * @param key
   * @return
   */
  public Object removeValueFromSessionMap(String key) {
    LOG.debug("method: removeValueFromSessionMap()");
    return getFacesContextCurrentInstance().getExternalContext().getSessionMap().remove(key);
  }

  /**
   *
   * @param key
   * @return
   */
  public Object getValueFromRequestMap(String key) {
    LOG.debug("method: getValueFromRequestMap()");
    return getFacesContextCurrentInstance().getExternalContext().getRequestMap().get(key);
  }

  /**
   *
   * @param key
   * @param value
   */
  public void setValueIntoRequestMap(String key, Object value) {
    LOG.debug("method: setValueIntoRequestMap()");
    getFacesContextCurrentInstance().getExternalContext().getRequestMap().put(key, value);
  }

  /**
   *
   * @param key
   * @return
   */
  public Object getValueFromRequestParameterMap(String key) {
    LOG.debug("method: getValueFromRequestParameterMap()");
    return getFacesContextCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
  }

  /**
   *
   * @param <T> Tipo de retorno
   * @param beanName nombre del ManagedBean a buscar
   * @return la instancia del ManagedBean buscado o null si esta no existe
   */
  public <T> T findManagedBean(String beanName) {
    LOG.debug("method: findManagedBean()");
    return (T) getFacesContextCurrentInstance().getApplication().evaluateExpressionGet(getFacesContextCurrentInstance(), "#{" + beanName + "}", Object.class);
  }

  /**
   *
   * @param beanName
   */
  public void removeViewScopedBean(String beanName) {
    LOG.debug("method: removeViewScopedBean()");
    getFacesContextCurrentInstance().getViewRoot().getViewMap().remove(beanName);
  }

  /**
   *
   * @param list
   */
  public void removeViewScopedBeans(List<String> list) {
    LOG.debug("method: removeViewScopedBeans()");
    for (String beanName : list) {
      getFacesContextCurrentInstance().getViewRoot().getViewMap().remove(beanName);
    }
  }

  /**
   * Encuentra un UIComponent por id dentro del ViewRoot
   *
   * @param id
   * @return
   */
  public static UIComponent findComponent(String id) {
    LOG.debug("method: findComponent()");
    return findComponent(FacesContext.getCurrentInstance().getViewRoot(), id);
  }

  /**
   * Encuentra un UIComponent por id dentro del UIComponet pasado por parametro
   *
   * @param component UICOmponet
   * @param id id del UIComponet buscado
   * @return
   */
  public static UIComponent findComponent(UIComponent component, String id) {
    LOG.debug("method: findComponent()");
    if (component == null) {
      return null;
    }
    LOG.debug("Component id: " + component.getId());
    List<UIComponent> items = component.getChildren();
    Iterator<UIComponent> facets = component.getFacetsAndChildren();
    if (items.size() > 0) {
      for (UIComponent item : items) {
        UIComponent found = findComponent(item, id);
        if (found != null) {
          return found;
        }
        if (item.getId().equalsIgnoreCase(id)) {
          return item;
        }
      }
    } else if (facets.hasNext()) {
      while (facets.hasNext()) {
        UIComponent facet = facets.next();
        UIComponent found = findComponent(facet, id);
        if (found != null) {
          return found;
        }
        if (facet.getId().equalsIgnoreCase(id)) {
          return facet;
        }
      }
    }
    return null;
  }

  /**
   *
   * @param valueExpression
   * @param expectedReturnType
   * @param expectedParamTypes
   * @return
   */
  public static MethodExpression createMethodExpression(String valueExpression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes) {
    LOG.debug("method: createMethodExpression()");
    FacesContext fc = FacesContext.getCurrentInstance();
    ExpressionFactory factory = fc.getApplication().getExpressionFactory();
    return factory.createMethodExpression(fc.getELContext(), valueExpression, expectedReturnType, expectedParamTypes);
  }

  /**
   *
   * @param valueExpression
   * @param expectedReturnType
   * @param expectedParamTypes
   * @return
   */
  public static MethodExpressionActionListener createMethodActionListener(String valueExpression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes) {
    LOG.debug("method: createMethodActionListener()");
    return new MethodExpressionActionListener(createMethodExpression(valueExpression, expectedReturnType, expectedParamTypes));
  }

  /**
   *
   * @param expression
   * @return
   */
  public Object getExpression(String expression) {
    LOG.debug("method: findComponent()");
    FacesContext ctx = getFacesContextCurrentInstance();
    ExpressionFactory factory = ctx.getApplication().getExpressionFactory();
    ValueExpression ex = factory.createValueExpression(ctx.getELContext(), "#{" + expression + "}", Object.class);
    return ex.getValue(ctx.getELContext());
  }

  /**
   *
   * @param key
   * @return
   */
  public String getPropertyFromBundle(String key) {
    Locale es = new Locale("ES");
    bundle = ResourceBundle.getBundle("bundle", es);
    return bundle.getString(key);
  }

  /**
   *
   * @param key
   * @param locale
   * @return
   */
  public String getPropertyFromBundle(String key, String locale) {
    Locale es = new Locale(locale);
    bundle = ResourceBundle.getBundle("bundle", es);
    return bundle.getString(key);
  }

  public ResourceBundle getBundle() {
    if (bundle == null) {
      FacesContext context = FacesContext.getCurrentInstance();
      bundle = context.getApplication().getResourceBundle(context, "bundle");
    }
    return bundle;
  }

  protected void releaseControllers() {
    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext ec = fc.getExternalContext();
    HttpServletRequest req = (HttpServletRequest) ec.getRequest();
    HttpSession session = req.getSession(false);
    if (session != null) {
      Enumeration attrNames = session.getAttributeNames();
      while (attrNames.hasMoreElements()) {
        String nombre = (String) attrNames.nextElement();
//                System.out.println(nombre);
        if (isControllerToRelease(nombre)) {
          session.removeAttribute(nombre);
        }
      }
    }
  }

  private boolean isControllerToRelease(String nombre) {
    return nombre.matches(".+class co.gov.policia.dinae.siedu.controladores.*")
            && !nombre.matches(".+class " + this.getClass().getName());
  }
  
  
  protected String getHostName(){
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException ex) {
      java.util.logging.Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
  
  protected String getHostAddress(){
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException ex) {
      java.util.logging.Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /*private String almacenarArchivoFisico(FileUploadEvent evento, String raizNombreArchivo, String ruta) {

    try {
      if (evento != null) {
        String nombreArchivoOriginal = evento.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = raizNombreArchivo.concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(ruta, evento.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return nombreArchivoFisico;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-7 Ingresar Ensayo Critico (almacenarArchivoFisico) ", e);
    }

    return null;

  }*/
  
}
