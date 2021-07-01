package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Linea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface ILineaLocal {

  /**
   * Devuelve el listado de lineas activas por área de investigación
   *
   * @param idArea
   * @return
   * @throws JpaDinaeException
   */
  List<Linea> getLineasPorArea(Long idArea) throws JpaDinaeException;

  /**
   * Devuelve una línea activa por id
   *
   * @param idLinea
   * @return
   * @throws JpaDinaeException
   */
  Linea obtenerLineaPorId(Long idLinea) throws JpaDinaeException;

  /**
   * Devuelve el listado de todas la lineas activas
   *
   * @return
   * @throws JpaDinaeException
   */
  List<Linea> getAllLineas() throws JpaDinaeException;

  /**
   * Devuelve el listado de lineas sin importar el estado
   *
   * @return
   * @throws JpaDinaeException
   */
  List<Linea> findAllNoEstado() throws JpaDinaeException;

  /**
   * Método utilizado para crear o actualizar una línea
   *
   * @param linea
   * @throws JpaDinaeException
   */
  void saveOrUpdate(Linea linea) throws JpaDinaeException;
}
