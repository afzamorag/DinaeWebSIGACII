package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IAreaCienciaTecnologiaLocal {

  /**
   * Devuelve el listado de áreas activas
   *
   * @return
   * @throws JpaDinaeException
   */
  List<AreaCienciaTecnologia> getAreaCienciaTecnologias() throws JpaDinaeException;

  /**
   * Devuelve el listado de areas sin importar el estado
   *
   * @return
   * @throws JpaDinaeException
   */
  List<AreaCienciaTecnologia> findAllNoEstado() throws JpaDinaeException;

  /**
   * Mátodo utilizado para crear o actualizar un área de investigación
   *
   * @param areaCienciaTecnologia
   * @throws JpaDinaeException
   */
  void saveOrUpdate(AreaCienciaTecnologia areaCienciaTecnologia) throws JpaDinaeException;
}
