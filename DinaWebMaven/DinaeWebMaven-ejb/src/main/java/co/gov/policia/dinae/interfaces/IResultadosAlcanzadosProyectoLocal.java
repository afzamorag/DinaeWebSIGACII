package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ResultadosAlcanzadosProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ResultadosAlcanzadosProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IResultadosAlcanzadosProyectoLocal {

  /**
   *
   * @param idResultadosAlcanzadosProyecto
   * @return
   * @throws JpaDinaeException
   */
  ResultadosAlcanzadosProyecto obtenerResultadosAlcanzadosProyectoPorId(Long idResultadosAlcanzadosProyecto) throws JpaDinaeException;

  /**
   *
   * @param idResultadosAlcanzadosProyecto
   * @return
   * @throws JpaDinaeException
   */
  ResultadosAlcanzadosProyecto guardarResultadosAlcanzadosProyecto(ResultadosAlcanzadosProyecto idResultadosAlcanzadosProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<ResultadosAlcanzadosProyecto> getListaResultadosAlcanzadosProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ResultadosAlcanzadosProyectoDTO> getListaResultadosAlcanzadosProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ResultadosAlcanzadosProyectoDTO> getListaResultadosAlcanzadosProyectoDTOporProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ResultadosAlcanzadosProyecto> getListaResultadosAlcanzadosProyectoPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idActividadRealizada
   * @throws JpaDinaeException
   */
  void eliminarActividadRealizada(Long idActividadRealizada) throws JpaDinaeException;

}
