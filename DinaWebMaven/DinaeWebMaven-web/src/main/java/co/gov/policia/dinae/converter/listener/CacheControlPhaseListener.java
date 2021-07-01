package co.gov.policia.dinae.converter.listener;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class CacheControlPhaseListener implements PhaseListener, Serializable {

  @Override
  public PhaseId getPhaseId() {
    return PhaseId.RENDER_RESPONSE;
  }

  @Override
  public void afterPhase(PhaseEvent event) {
  }

  /**
   *
   * @param event
   */
  @Override
  public void beforePhase(PhaseEvent event) {
    FacesContext facesContext = event.getFacesContext();
    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    response.addHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "no-cache");
    // Stronger according to blog comment below that references HTTP spec
    response.addHeader("Cache-Control", "no-store");
    response.addHeader("Cache-Control", "must-revalidate");
    // some date in the past
    response.addHeader("Expires", "0");
  }

}
