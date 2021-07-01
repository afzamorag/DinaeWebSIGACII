package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoEjecutadoImplLocal;
import co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutadoImpl;
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
public class ResumenPresupuestoEjecutadoImplEjbBean implements IResumenPresupuestoEjecutadoImplLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idImplementacionProy
   * @param idInformeAvanceImpl
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ResumenPresupuestoEjecutadoImpl> findByProyectoInformeAvance(Long idImplementacionProy, Long idInformeAvanceImpl) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResumenPresupuestoEjecutadoImpl.findByProyectoInformeAvance", ResumenPresupuestoEjecutadoImpl.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .setParameter("idInformeAvanceImpl", idInformeAvanceImpl)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ResumenPresupuestoEjecutadoImpl> findByProyectoInformeAvanceAcum(Long idImplementacionProy) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResumenPresupuestoEjecutadoImpl.findByProyectoInformeAvanceAcum", ResumenPresupuestoEjecutadoImpl.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @param idInformeAvanceImpl
   * @throws JpaDinaeException
   */
  @Override
  public void calcularPresupuestoEjecutadoImpl(Long idImplementacionProy, Long idInformeAvanceImpl) throws JpaDinaeException {
    try {
      Query query = entityManager.createNamedQuery("ResumenPresupuestoEjecutadoImpl.calcularPresupuestoEjecutadoImpl");
      query.setParameter("idImplementacionProy", idImplementacionProy);
      query.setParameter("idInformeAvanceImpl", idInformeAvanceImpl);
      query.executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idImplementacionProy
   * @throws JpaDinaeException
   */
  @Override
  public void calcularPresupuestoEjecutadoAcumImpl(Long idImplementacionProy) throws JpaDinaeException {
    try {
      Query query = entityManager.createNamedQuery("ResumenPresupuestoEjecutadoImpl.calcularPresupuestoEjecutadoAcumImpl");
      query.setParameter("idImplementacionProy", idImplementacionProy);
      query.executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")
}
