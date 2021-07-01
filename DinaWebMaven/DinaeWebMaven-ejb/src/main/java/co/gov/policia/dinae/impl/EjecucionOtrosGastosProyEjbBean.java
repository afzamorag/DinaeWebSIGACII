package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEjecucionOtrosGastosProyLocal;
import co.gov.policia.dinae.modelo.EjecucionOtrosGastosProy;
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
public class EjecucionOtrosGastosProyEjbBean implements IEjecucionOtrosGastosProyLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecucionOtrosGastosProy> findOtrosGastosInforme(Long idInformeAvance) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionOtrosGastosProy.findOtrosGastosInforme", EjecucionOtrosGastosProy.class)
              .setParameter("idInformeAvance", idInformeAvance)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvance
   * @param idOtrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionOtrosGastosProy findOtrosGastosInformeProyecto(Long idInformeAvance, Long idOtrosGastosProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionOtrosGastosProy.findOtrosGastosInformeProyecto", EjecucionOtrosGastosProy.class)
              .setParameter("idInformeAvance", idInformeAvance)
              .setParameter("idOtrosGastosProyecto", idOtrosGastosProyecto)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionOtrosGastosProy) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param ejecucionOtrosGastosProy
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionOtrosGastosProy saveOrUpdate(EjecucionOtrosGastosProy ejecucionOtrosGastosProy) throws JpaDinaeException {

    try {

      if (ejecucionOtrosGastosProy.getIdEjecucionGastosProy() == null) {
        entityManager.persist(ejecucionOtrosGastosProy);
      } else {
        entityManager.merge(ejecucionOtrosGastosProy);
      }

      return ejecucionOtrosGastosProy;

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
  public void deleteOtrosGastosInformeProyecto(Long idInformeAvance) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionOtrosGastosProy.deleteOtrosGastosInformeProyecto")
              .setParameter("idInformeAvance", idInformeAvance)
              .executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecucionOtrosGastosProy> findOtrosGastosInformeImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionOtrosGastosProy.findOtrosGastosInformeImplementacion", EjecucionOtrosGastosProy.class)
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idOtrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionOtrosGastosProy findOtrosGastosInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idOtrosGastosProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionOtrosGastosProy.findOtrosGastosInformeImplementacionProyecto", EjecucionOtrosGastosProy.class)
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .setParameter("idOtrosGastosProyecto", idOtrosGastosProyecto)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionOtrosGastosProy) results.get(0);
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
  public void deleteOtrosGastosInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionOtrosGastosProy.deleteOtrosGastosInformeImplementacionProyecto")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
