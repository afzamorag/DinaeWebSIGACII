package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.CriterioEvaluacionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CriterioEvaluacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ICriterioEvaluacionLocal {

  /**
   *
   * @param idCriterioEvaluacion
   * @return
   * @throws JpaDinaeException
   */
  CriterioEvaluacion obtenerCriterioEvaluacionPorId(Long idCriterioEvaluacion) throws JpaDinaeException;

  /**
   *
   * @param idCriterioEvaluacion
   * @return
   * @throws JpaDinaeException
   */
  CriterioEvaluacion guardarCriterioEvaluacion(CriterioEvaluacion idCriterioEvaluacion) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<CriterioEvaluacion> getListaCriterioEvaluacionTodos() throws JpaDinaeException;

  /**
   *
   * @param estado
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  List<CriterioEvaluacion> getListaCriterioEvaluacionPorEstado(String estado, String tipo) throws JpaDinaeException;

  /**
   *
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  List<CriterioEvaluacion> getListaCriterioEvaluacionPorTipo(String tipo) throws JpaDinaeException;

  /**
   *
   * @param estado
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  List<CriterioEvaluacionDTO> getListaCriterioEvaluacionPorEstadoDTO(String estado, String tipo) throws JpaDinaeException;

}
