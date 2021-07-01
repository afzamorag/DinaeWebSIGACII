package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajo;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajoImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IIndicadoresPlanTrabajoLocal {

  /**
   *
   * @param idIndicadoresPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  IndicadoresPlanTrabajo obtenerIndicadoresPlanTrabajoPorId(Long idIndicadoresPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param indicadoresPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  IndicadoresPlanTrabajo guardarIndicadoresPlanTrabajo(IndicadoresPlanTrabajo indicadoresPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresPlanTrabajo> getListaIndicadoresPlanTrabajoPorPlanTrabajoImplementacion(Long idPlanTrabajoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idIndicadoresPlanTrabajo
   * @throws JpaDinaeException
   */
  void eliminarIndicadoresPlanTrabajo(Long idIndicadoresPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idPlanTrabajoImpl
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresPlanTrabajoImplementacion> getListaIndicadoresPlanTrabajoImplementacionPorPlanTrabajoImplYidInformeAvanceImplementacion(Long idInformeAvanceImplementacion, Long idPlanTrabajoImpl) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresPlanTrabajoImplementacion> getListaIndicadoresPlanTrabajoImplementacionPorInformeAvanceImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException;

  /**
   *
   * @param indicadoresPlanTrabajoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  IndicadoresPlanTrabajoImplementacion guardarIndicadoresPlanTrabajoImplementacion(IndicadoresPlanTrabajoImplementacion indicadoresPlanTrabajoImplementacion) throws JpaDinaeException;
}
