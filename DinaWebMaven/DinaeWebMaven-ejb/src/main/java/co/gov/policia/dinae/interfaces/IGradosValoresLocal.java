package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.GradosValores;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IGradosValoresLocal {

  /**
   *
   * @param anio
   * @param grado
   * @return
   * @throws JpaDinaeException
   */
  GradosValores getGradosValoresPorAnioYgrado(Integer anio, String grado) throws JpaDinaeException;
}
