/*
 * SoftStudio
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.excepciones;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class ValidationsException extends Exception {

  public ValidationsException() {
  }

  public ValidationsException(String message) {
    super(message);
  }

  public ValidationsException(Throwable cause) {
    super(cause);
  }

  public ValidationsException(String message, Throwable cause) {
    super(message, cause);
  }
}
