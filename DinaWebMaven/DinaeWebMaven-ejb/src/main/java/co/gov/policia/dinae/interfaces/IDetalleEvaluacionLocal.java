package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.DetalleEvaluacion;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IDetalleEvaluacionLocal {

  /**
   *
   * @param detalleEvaluacion
   * @return
   * @throws JpaDinaeException
   */
  DetalleEvaluacion saveOrUpdate(DetalleEvaluacion detalleEvaluacion) throws JpaDinaeException;

  /**
   *
   * @param listaEvaluacionProyecto
   * @param detalleEvaluacion
   * @param idProyecto
   * @param idNuevoEstado
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  DetalleEvaluacion saveOrUpdateYActualizarEstadoProyecto(List<EvaluacionProyecto> listaEvaluacionProyecto, DetalleEvaluacion detalleEvaluacion, Long idProyecto, Long idNuevoEstado, Long idPeriodo) throws JpaDinaeException;

}
