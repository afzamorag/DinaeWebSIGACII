package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.KeyProperties;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IkeyPropertiesLocal {

  /**
   *
   * @param clave
   * @return una KeyProperties, si no existe retorna NULL
   * @throws JpaDinaeException
   */
  KeyProperties getKeyPropertiePorClave(String clave) throws JpaDinaeException;

  /**
   *
   * @param clave
   * @return
   * @throws JpaDinaeException
   */
  Long getConcecutivoConvocatoriaPropertiePorClave(String clave) throws JpaDinaeException;

}
