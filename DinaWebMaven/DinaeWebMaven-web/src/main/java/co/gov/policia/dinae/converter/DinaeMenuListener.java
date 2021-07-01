package co.gov.policia.dinae.converter;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class DinaeMenuListener implements ServletContextListener, Serializable {

  /**
   *
   * @param sce
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "ENTRO AL LISTENER: ");
    FacesContext.getCurrentInstance();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }

}
