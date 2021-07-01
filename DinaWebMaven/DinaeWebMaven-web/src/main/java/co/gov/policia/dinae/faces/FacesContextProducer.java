package co.gov.policia.dinae.faces;

import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class FacesContextProducer implements Serializable {

  @javax.enterprise.inject.Produces
  @javax.enterprise.context.SessionScoped
  public FacesContext getFacesContext() {

    return FacesContext.getCurrentInstance();

  }

}
