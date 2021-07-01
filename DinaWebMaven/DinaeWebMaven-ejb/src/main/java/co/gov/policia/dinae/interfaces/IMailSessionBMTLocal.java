package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CorreoEnvio;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IMailSessionBMTLocal {

  /**
   *
   * @param correoEnvio
   * @return
   * @throws JpaDinaeException
   */
  CorreoEnvio actualizarCorreoEnvio(CorreoEnvio correoEnvio) throws JpaDinaeException;

}
