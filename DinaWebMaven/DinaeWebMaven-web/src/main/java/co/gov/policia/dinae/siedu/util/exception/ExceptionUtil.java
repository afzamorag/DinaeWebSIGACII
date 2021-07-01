/*
 * SoftStudio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@logike.co>
 * @version: 1.0
 * @since: 1.0
 */
public class ExceptionUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ExceptionUtil.class);

  /**
   *
   * @param ex
   * @param constraintName
   * @return
   */
  public static boolean isException(Exception ex, String constraintName) {
    LOG.trace("method: isException(({}, {});", ex, constraintName);
    String message;
    if (ex.getCause() != null) {
      if (ex.getCause().getCause() != null) {
        message = ex.getCause().getCause().getMessage();
      } else {
        message = ex.getCause().getMessage();
      }
    } else {
      message = ex.getMessage();
    }
    if (message != null) {
      return message.contains(constraintName);
    }
    return false;
  }
}
