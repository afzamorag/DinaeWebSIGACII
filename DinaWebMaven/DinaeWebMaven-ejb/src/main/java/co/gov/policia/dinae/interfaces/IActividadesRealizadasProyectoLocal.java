package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ActividadesRealizadasProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ActividadesRealizadasProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IActividadesRealizadasProyectoLocal {

  /**
   *
   * @param idActividadesRealizadasProyecto
   * @return
   * @throws JpaDinaeException
   */
  ActividadesRealizadasProyecto obtenerActividadesRealizadasProyectoPorId(Long idActividadesRealizadasProyecto) throws JpaDinaeException;

  /**
   *
   * @param idActividadesRealizadasProyecto
   * @return
   * @throws JpaDinaeException
   */
  ActividadesRealizadasProyecto guardarActividadesRealizadasProyecto(ActividadesRealizadasProyecto idActividadesRealizadasProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<ActividadesRealizadasProyecto> getListaActividadesRealizadasProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesRealizadasProyectoDTO> getListaActividadesRealizadasProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesRealizadasProyectoDTO> getListaActividadesRealizadasProyectoDTOporProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idActividadRealizada
   * @throws JpaDinaeException
   */
  void eliminarActividadRealizada(Long idActividadRealizada) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesRealizadasProyecto> getListaActividadesRealizadasProyectoPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;
}
