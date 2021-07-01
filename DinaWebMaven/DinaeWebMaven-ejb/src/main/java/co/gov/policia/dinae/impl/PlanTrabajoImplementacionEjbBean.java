package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/15
 */
@Stateless
public class PlanTrabajoImplementacionEjbBean implements IPlanTrabajoImplementacionLocal, Serializable {

  /**
   * EntityManager
   */
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param planTrabajoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public PlanTrabajoImplementacion registrarPlanDeTrabajo(PlanTrabajoImplementacion planTrabajoImplementacion) throws JpaDinaeException {

    try {

      if (planTrabajoImplementacion.getIdPlanTrabajo() == null) {

        planTrabajoImplementacion.setFechaRegistro(new Date());
        entityManager.persist(planTrabajoImplementacion);

      } else {

        entityManager.merge(planTrabajoImplementacion);

      }

      return planTrabajoImplementacion;

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param semillerosImplementacion
   * @throws JpaDinaeException
   */
  @Override
  public SemillerosImplementacion vincularSemilleroInvestigacion(SemillerosImplementacion semillerosImplementacion) throws JpaDinaeException {
    try {

      if (semillerosImplementacion.getIdSemilleroImplemetacion() == null) {

        semillerosImplementacion.setFechaRegistro(new Date());
        entityManager.persist(semillerosImplementacion);

      } else {

        entityManager.merge(semillerosImplementacion);

      }

      return semillerosImplementacion;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param idImplemenacionProyecto
   * @param idCompromisoProyectoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public PlanTrabajoImplementacion findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(Long idImplemenacionProyecto, Long idCompromisoProyectoImplementacion) throws JpaDinaeException {

    try {

      return (PlanTrabajoImplementacion) entityManager.createNamedQuery("PlanTrabajoImplementacion.findPorImplementacionProyectoYcompromisoProyectoImple")
              .setParameter("idImplementacionProy", idImplemenacionProyecto)
              .setParameter("idCompromisoImplementacion", idCompromisoProyectoImplementacion)
              .getSingleResult();

    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<SemillerosImplementacion> getSemillerosImplementacionByIdImplementacion(Long idImplementacionProy) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("SemillerosImplementacion.findAllByImplementacionProyecto").
              setParameter("idImplementacionProy", idImplementacionProy).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param actividadesPlanImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ActividadesPlanImplementacion guardarActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) throws JpaDinaeException {

    try {
      if (actividadesPlanImplementacion.getIdActividadPlanImplementacion() == null) {

        actividadesPlanImplementacion.setFechaRegistro(new Date());
        entityManager.persist(actividadesPlanImplementacion);

      } else {

        entityManager.merge(actividadesPlanImplementacion);

      }

      return actividadesPlanImplementacion;

    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }

  }

  /**
   *
   * @param idPlanTrabajoImpl
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ActividadesPlanImplementacion> getListaActividadesPlanImplementacionPorPlanTrabajoImpl(Long idPlanTrabajoImpl) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("ActividadesPlanImplementacion.findidPlanTrabajoImpl")
              .setParameter("idPlanTrabajoImpl", idPlanTrabajoImpl)
              .getResultList();
    } catch (Exception ex) {

      throw new JpaDinaeException(ex);
    }

  }

  /**
   *
   * @param idPlanTrabajoImpl
   * @param listaEstadoActividad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ActividadesPlanImplementacion> getListaActividadesPlanImplementacionPorPlanTrabajoImplYestadosActividad(Long idPlanTrabajoImpl, List<Long> listaEstadoActividad) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("ActividadesPlanImplementacion.findidPlanTrabajoImplYestados")
              .setParameter("idPlanTrabajoImpl", idPlanTrabajoImpl)
              .setParameter("idListaEstado", listaEstadoActividad)
              .getResultList();
    } catch (Exception ex) {

      throw new JpaDinaeException(ex);
    }

  }

  /**
   *
   * @param semillerosImplementacion
   * @throws JpaDinaeException
   */
  @Override
  public void desVincularSemilleroInvestigacion(SemillerosImplementacion semillerosImplementacion) throws JpaDinaeException {

    try {

      SemillerosImplementacion siDesvincular = entityManager.find(SemillerosImplementacion.class, semillerosImplementacion.getIdSemilleroImplemetacion());

      Long idImplPro = siDesvincular.getImplementacionesProyecto().getIdImplementacionProy();
      siDesvincular.setIdSemilleroImplemetacionEliminado(idImplPro);
      siDesvincular.setImplementacionesProyecto(null);

      entityManager.merge(siDesvincular);

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }

  /**
   *
   * @param investigadorProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void desInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) throws JpaDinaeException {

    try {

      InvestigadorProyecto ipDesvincular = entityManager.find(InvestigadorProyecto.class, investigadorProyecto.getIdInvestigadorProyecto());

      Long idPlanTrabajo = ipDesvincular.getPlanTrabajoImplementacion().getIdPlanTrabajo();
      ipDesvincular.setIdPlanTrabajoEliminado(idPlanTrabajo);
      ipDesvincular.setPlanTrabajoImplementacion(null);

      entityManager.merge(ipDesvincular);

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param actividadesPlanImplementacion
   * @throws JpaDinaeException
   */
  @Override
  public void desVincularActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) throws JpaDinaeException {

    try {

      ActividadesPlanImplementacion apiDesvincular = entityManager.find(ActividadesPlanImplementacion.class, actividadesPlanImplementacion.getIdActividadPlanImplementacion());

      Long idPlanTrabajo = apiDesvincular.getPlanTrabajoImplementacion().getIdPlanTrabajo();
      apiDesvincular.setIdPlanTrabajoEliminado(idPlanTrabajo);
      apiDesvincular.setPlanTrabajoImplementacion(null);

      entityManager.merge(apiDesvincular);

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }
}
