package co.gov.policia.dinae.interfaces;

import java.io.InputStream;
import java.util.HashMap;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IDatosGenericoLocal {

  byte[] generarReporte(HashMap mapa, InputStream inputStream) throws Exception;

}
