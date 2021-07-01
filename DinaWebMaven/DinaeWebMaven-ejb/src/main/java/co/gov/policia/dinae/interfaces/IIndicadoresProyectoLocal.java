package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.IndicadoresCompromisoProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IIndicadoresProyectoLocal {

  /**
   *
   * @param idIndicadoresProyecto
   * @return
   * @throws JpaDinaeException
   */
  IndicadoresProyecto obtenerIndicadoresProyectoPorId(Long idIndicadoresProyecto) throws JpaDinaeException;

  /**
   *
   * @param indicadoresProyecto
   * @return
   * @throws JpaDinaeException
   */
  IndicadoresProyecto guardarIndicadoresProyecto(IndicadoresProyecto indicadoresProyecto) throws JpaDinaeException;

  /**
   *
   * @param indicadoresProyectoList
   * @throws JpaDinaeException
   */
  void guardarListaIndicadoresProyecto(List<IndicadoresProyecto> indicadoresProyectoList) throws JpaDinaeException;

  /**
   *
   * @param indicadoresCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  IndicadoresCompromisoProyecto guardarIndicadoresCompromisoProyecto(IndicadoresCompromisoProyecto indicadoresCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<IndicadoresProyecto> getListaIndicadoresProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param indicadorBase
   * @param claveCasoUso
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresProyecto> getListaIndicadoresProyectoPorProyectoEindicadorBase(Long idProyecto, Character indicadorBase, String claveCasoUso) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param indicadorBase
   * @param claveCasoUso
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresCompromisoProyecto> getListaIndicadoresCompromisoProyectoPorProyectoEindicadorBase(Long idProyecto, Character indicadorBase, String claveCasoUso, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idIndicadorProyecto
   * @throws JpaDinaeException
   */
  void eliminarIndicadoresProyecto(Long idIndicadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresCompromisoProyecto> getListaIndicadoresCompromisoProyecto(Long idCompromisoProyecto) throws JpaDinaeException;
}
