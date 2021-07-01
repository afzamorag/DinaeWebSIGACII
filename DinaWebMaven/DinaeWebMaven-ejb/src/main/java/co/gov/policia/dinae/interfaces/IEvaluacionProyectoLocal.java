package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IEvaluacionProyectoLocal {

  /**
   *
   * @param idEvaluacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  EvaluacionProyecto obtenerEvaluacionProyectoPorId(Long idEvaluacionProyecto) throws JpaDinaeException;

  /**
   *
   * @param evaluacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  EvaluacionProyecto guardarEvaluacionProyecto(EvaluacionProyecto evaluacionProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<EvaluacionProyecto> getListaEvaluacionProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCriterio
   * @return
   * @throws JpaDinaeException
   */
  BigDecimal getValorIngresadoEvaluacionProyectoPorProyectoYCriterio(Long idProyecto, Long idCriterio) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  BigDecimal getValorTotalIngresadoEvaluacionProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCriterio
   * @return
   * @throws JpaDinaeException
   */
  EvaluacionProyecto getEvaluacionProyectoPorProyectoYCriterio(Long idProyecto, Long idCriterio) throws JpaDinaeException;

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  List<EvaluacionProyecto> getCriterioPorEnsayoCritico(Long idEnsayoCritico) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EvaluacionProyecto> getEvaluacionProyecto(Long idProyecto) throws JpaDinaeException;
}
