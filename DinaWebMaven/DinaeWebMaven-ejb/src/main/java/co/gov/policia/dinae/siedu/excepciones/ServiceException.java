/*
 * SoftStudio
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.excepciones;

import javax.ejb.ApplicationException;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@ApplicationException(rollback = true)
public class ServiceException extends Exception {

  public ServiceException() {
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
