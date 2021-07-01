package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEjecucionViajesProyectoLocal;
import co.gov.policia.dinae.modelo.EjecucionViajesProyecto;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cguzman
 */
@Stateless
public class EjecucionViajesProyectoEjbBean implements IEjecucionViajesProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecucionViajesProyecto> findViajesRelacionadoProyecto(Long idProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionViajesProyecto.findViajesRelacionadoProyecto", EjecucionViajesProyecto.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvance
   * @param idViajeProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionViajesProyecto findViajesRelacionadoInformeProyecto(Long idInformeAvance, Long idViajeProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionViajesProyecto.findViajesRelacionadoInformeProyecto", EjecucionViajesProyecto.class)
              .setParameter("idInformeAvance", idInformeAvance)
              .setParameter("idViajeProyecto", idViajeProyecto)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionViajesProyecto) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param ejecucionViajesProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionViajesProyecto saveOrUpdate(EjecucionViajesProyecto ejecucionViajesProyecto) throws JpaDinaeException {

    try {

      if (ejecucionViajesProyecto.getIdEjecucionViajesProyecto() == null) {
        entityManager.persist(ejecucionViajesProyecto);
      } else {
        entityManager.merge(ejecucionViajesProyecto);
      }

      return ejecucionViajesProyecto;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  @Override
  public void deleteViajesRelacionadoInformeProyecto(Long idInformeAvance) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionViajesProyecto.deleteViajesRelacionadoInformeProyecto")
              .setParameter("idInformeAvance", idInformeAvance)
              .executeUpdate();

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
  public List<EjecucionViajesProyecto> findViajesRelacionadoImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionViajesProyecto.findViajesRelacionadoImplementacionProyecto", EjecucionViajesProyecto.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idViajeProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionViajesProyecto findViajesRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idViajeProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionViajesProyecto.findViajesRelacionadoInformeImplementacionProyecto", EjecucionViajesProyecto.class)
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .setParameter("idViajeProyecto", idViajeProyecto)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionViajesProyecto) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @throws JpaDinaeException
   */
  @Override
  public void deleteViajesRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionViajesProyecto.deleteViajesRelacionadoInformeImplementacionProyecto")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
