package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEjecucionEventosProyectoLocal;
import co.gov.policia.dinae.modelo.EjecucionEventosProyecto;
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
public class EjecucionEventosProyectoEjbBean implements IEjecucionEventosProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecucionEventosProyecto> findEventoRelacionadoProyecto(Long idProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEventosProyecto.findEventoRelacionadoProyecto", EjecucionEventosProyecto.class)
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
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionEventosProyecto findEventoRelacionadoInformeProyecto(Long idInformeAvance, Long idEventoProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEventosProyecto.findEventoRelacionadoInformeProyecto", EjecucionEventosProyecto.class)
              .setParameter("idInformeAvance", idInformeAvance)
              .setParameter("idEventoProyecto", idEventoProyecto)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionEventosProyecto) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param ejecucionEventosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionEventosProyecto saveOrUpdate(EjecucionEventosProyecto ejecucionEventosProyecto) throws JpaDinaeException {

    try {

      if (ejecucionEventosProyecto.getIdEjecucionEventProy() == null) {
        entityManager.persist(ejecucionEventosProyecto);
      } else {
        entityManager.merge(ejecucionEventosProyecto);
      }

      return ejecucionEventosProyecto;

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
  public void deleteEventoRelacionadoInformeProyecto(Long idInformeAvance) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionEventosProyecto.deleteEventoRelacionadoInformeProyecto")
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
  public List<EjecucionEventosProyecto> findEventoRelacionadoImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEventosProyecto.findEventoRelacionadoImplementacionProyecto", EjecucionEventosProyecto.class)
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
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionEventosProyecto findEventoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idEventoProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEventosProyecto.findEventoRelacionadoInformeImplementacionProyecto", EjecucionEventosProyecto.class)
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .setParameter("idEventoProyecto", idEventoProyecto)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionEventosProyecto) results.get(0);
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
  public void deleteEventoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionEventosProyecto.deleteEventoRelacionadoInformeImplementacionProyecto")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
