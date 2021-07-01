package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.CompromisoImplementacionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ICompromisoImplementacionLocal {

  /**
   *
   * @param compromiso
   * @throws JpaDinaeException
   */
  void saveCompromisoImplementacion(CompromisoImplementacion compromiso) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  CompromisoImplementacion obtenerCompromisoImplementacionPorId(Long idCompromisoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  Long obtenerIdTipoCompromisoImplementacionPorId(Long idCompromisoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProyecto
   * @param idTipoCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  CompromisoImplementacion obtenerCompromisoImplementacionPorImplementacionProyectoYtipoCompromiso(Long idImplementacionProyecto, Long idTipoCompromisoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoImplementacion> findByIdImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoImplementacionDTO> getListaCompromisoImplementacionDTOPorIdImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProyecto
   * @param idTipoCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  CompromisoImplementacion findByIdImplementacionProyYtipoCompromisoNoCorregido(Long idImplementacionProyecto, Long idTipoCompromisoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  Long getEstadoCompromisoImplPorIdInformeAvance(Long idInformeAvanceImplementacion) throws JpaDinaeException;
}
