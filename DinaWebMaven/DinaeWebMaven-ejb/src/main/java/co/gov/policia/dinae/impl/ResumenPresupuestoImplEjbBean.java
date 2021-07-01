package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoImplLocal;
import co.gov.policia.dinae.modelo.ResumenPresupuestoImpl;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cguzman
 */
@Stateless
public class ResumenPresupuestoImplEjbBean implements IResumenPresupuestoImplLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<ResumenPresupuestoImpl> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResumenPresupuestoImpl.findAll", ResumenPresupuestoImpl.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ResumenPresupuestoImpl> findByProyectoImpl(Long idImplementacionProy, Long idPlanTrabajo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResumenPresupuestoImpl.findByProyectoImpl", ResumenPresupuestoImpl.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @param idPlanTrabajo
   * @param estadoPresupuesto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ResumenPresupuestoImpl> findByProyectoImplEstadoPresepuesto(Long idImplementacionProy, Long idPlanTrabajo, String estadoPresupuesto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResumenPresupuestoImpl.findByProyectoImplEstadoPresepuesto", ResumenPresupuestoImpl.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .setParameter("estadoPresupuesto", estadoPresupuesto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @param idPlanTrabajo
   * @throws JpaDinaeException
   */
  @Override
  public void removeAllByProyectoImpl(Long idImplementacionProy, Long idPlanTrabajo) throws JpaDinaeException {
    try {
      entityManager.createNamedQuery("ResumenPresupuestoImpl.removeAllByProyectoImpl", ResumenPresupuestoImpl.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .executeUpdate();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idPlanTrabajo
   * @param idImplementacionProy
   * @throws JpaDinaeException
   */
  @Override
  public void calcularPresupuestoImpl(Long idPlanTrabajo, Long idImplementacionProy) throws JpaDinaeException {
    try {
      Query query = entityManager.createNamedQuery("ResumenPresupuestoImpl.calcularPresupuestoImpl");
      query.setParameter("idPlanTrabajo", idPlanTrabajo);
      query.setParameter("idImplementacionProy", idImplementacionProy);
      query.executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
