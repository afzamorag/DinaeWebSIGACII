package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EvidenciaProyecto;
import co.gov.policia.dinae.modelo.EvidenciaProyectoDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IEvidenciaProyectoLocal {

  /**
   *
   * @param idEvidenciaProyecto
   * @return
   * @throws JpaDinaeException
   */
  EvidenciaProyecto obtenerEvidenciaProyectoPorId(Long idEvidenciaProyecto) throws JpaDinaeException;

  /**
   *
   * @param idEvidenciaProyecto
   * @return
   * @throws JpaDinaeException
   */
  EvidenciaProyecto guardarEvidenciaProyecto(EvidenciaProyecto idEvidenciaProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<EvidenciaProyecto> getListaEvidenciaProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EvidenciaProyectoDTO> getListaEvidenciaProyectoDTOPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idEvidenciaProyecto
   * @throws JpaDinaeException
   */
  void eliminarEvidenciaProyecto(Long idEvidenciaProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EvidenciaProyectoDTO> getListaEvidenciaProyectoDTOPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EvidenciaProyecto> getListaEvidenciaProyectoPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;
}
