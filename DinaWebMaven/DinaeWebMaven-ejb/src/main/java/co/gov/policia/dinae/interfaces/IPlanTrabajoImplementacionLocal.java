package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ActividadesImplementacion;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/16
 */
@Local
public interface IPlanTrabajoImplementacionLocal {

  /**
   *
   * @param planTrabajoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  PlanTrabajoImplementacion registrarPlanDeTrabajo(PlanTrabajoImplementacion planTrabajoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param semillerosImplementacion
   * @return
   * @throws JpaDinaeException
   */
  SemillerosImplementacion vincularSemilleroInvestigacion(SemillerosImplementacion semillerosImplementacion) throws JpaDinaeException;

  /**
   *
   * @param semillerosImplementacion
   * @throws JpaDinaeException
   */
  void desVincularSemilleroInvestigacion(SemillerosImplementacion semillerosImplementacion) throws JpaDinaeException;

  /**
   *
   * @param investigadorProyecto
   * @throws JpaDinaeException
   */
  void desInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param actividadesPlanImplementacion
   * @throws JpaDinaeException
   */
  void desVincularActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idImplemenacionProyecto
   * @param idCompromisoProyectoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  PlanTrabajoImplementacion findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(Long idImplemenacionProyecto, Long idCompromisoProyectoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<SemillerosImplementacion> getSemillerosImplementacionByIdImplementacion(Long idPlanTrabajoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param actividadesPlanImplementacion
   * @return
   * @throws JpaDinaeException
   */
  ActividadesPlanImplementacion guardarActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajoImpl
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesPlanImplementacion> getListaActividadesPlanImplementacionPorPlanTrabajoImpl(Long idPlanTrabajoImpl) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajoImpl
   * @param listaEstadoActividad
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesPlanImplementacion> getListaActividadesPlanImplementacionPorPlanTrabajoImplYestadosActividad(Long idPlanTrabajoImpl, List<Long> listaEstadoActividad) throws JpaDinaeException;

}
