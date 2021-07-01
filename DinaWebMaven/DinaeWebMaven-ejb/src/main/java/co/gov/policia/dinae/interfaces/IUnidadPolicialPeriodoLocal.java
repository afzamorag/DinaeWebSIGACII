package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public interface IUnidadPolicialPeriodoLocal {

  /**
   *
   * @param idPeriodo
   * @param idEstado1
   * @param idEstado2
   * @param idEstado3
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicial> getUnidadPolicialNoPresentanPropuesta(Long idPeriodo, Long idEstado1, Long idEstado2, Long idEstado3) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<Long> getIdsUnidadPolicialPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<String> getCorreoUnidadPolicialPorPeriodoEisNotNullCorreo(Long idPeriodo) throws JpaDinaeException;

}
