package co.gov.policia.dinae.exceptions;

import java.io.Serializable;
import java.util.Set;
import javax.ejb.ApplicationException;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@ApplicationException(rollback = true)
public class JpaDinaeException extends Exception implements Serializable {

  public JpaDinaeException(String message) {
    super(message);
  }

  public JpaDinaeException(Throwable cause) {
    this(obtieneMensajeSegunException(cause), cause);
  }

  public JpaDinaeException(String message, Throwable cause) {
    super(message, cause);
  }

  private static String obtieneMensajeSegunException(Throwable cause) {
    if (cause == null) {
      return null;
    }
    if (cause instanceof javax.validation.ConstraintViolationException) {
      Set set = ((javax.validation.ConstraintViolationException) cause).getConstraintViolations();
      if (set != null && !set.isEmpty()) {
        return set.toString();
      }
    }
    return null;
  }
}
