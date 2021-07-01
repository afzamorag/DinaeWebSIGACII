package co.gov.policia.dinae.exceptions;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class UsuarioLoginException extends Exception implements Serializable {

  public UsuarioLoginException(String message) {
    super(message);
  }

  public UsuarioLoginException(Throwable cause) {
    super(cause);
  }

  public UsuarioLoginException(String message, Throwable cause) {
    super(message, cause);
  }

}
