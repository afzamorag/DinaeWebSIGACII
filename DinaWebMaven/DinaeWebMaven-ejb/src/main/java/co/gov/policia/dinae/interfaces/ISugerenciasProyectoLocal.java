package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.SugerenciasProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.SugerenciasProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ISugerenciasProyectoLocal {

  /**
   *
   * @param idSugerenciasProyecto
   * @return
   * @throws JpaDinaeException
   */
  SugerenciasProyecto obtenerSugerenciasProyectoPorId(Long idSugerenciasProyecto) throws JpaDinaeException;

  /**
   *
   * @param idSugerenciasProyecto
   * @return
   * @throws JpaDinaeException
   */
  SugerenciasProyecto guardarSugerenciasProyecto(SugerenciasProyecto idSugerenciasProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<SugerenciasProyecto> getListaSugerenciasProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<SugerenciasProyecto> getListaSugerenciasProyectoPorProyectoYCompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<SugerenciasProyectoDTO> getListaSugerenciasProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<SugerenciasProyectoDTO> getListaSugerenciasProyectoDTOporProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idSugerenciasProyecto
   * @throws JpaDinaeException
   */
  void eliminarSugerenciasProyecto(Long idSugerenciasProyecto) throws JpaDinaeException;

}
