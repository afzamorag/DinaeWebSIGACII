package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICronogramaSemilleroLocal;
import co.gov.policia.dinae.modelo.CronogramaSemillero;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cguzman
 */
@Stateless
public class CronogramaSemilleroEjbBean implements ICronogramaSemilleroLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param cronogramaSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CronogramaSemillero saveOrUpdate(CronogramaSemillero cronogramaSemillero) throws JpaDinaeException {
    try {

      if (cronogramaSemillero.getIdCronogramaSemillero() == null) {
        cronogramaSemillero.setFechaCreacion(new Date());
        entityManager.persist(cronogramaSemillero);
      } else {
        cronogramaSemillero.setFechaActualizacion(new Date());
        entityManager.merge(cronogramaSemillero);
      }

      return cronogramaSemillero;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CronogramaSemillero> findAllActivitiesBySemillero(Long idSemillero) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("CronogramaSemillero.findAllActivitiesBySemillero", CronogramaSemillero.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idSemilleroProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CronogramaSemillero> findAllActivitiesBySemilleroProyecto(Long idSemilleroProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("CronogramaSemillero.findAllActivitiesBySemilleroProyecto", CronogramaSemillero.class)
              .setParameter("idSemilleroProyecto", idSemilleroProyecto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idSemillerosImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CronogramaSemillero> findAllActivitiesBySemillerosImplementacion(Long idSemillerosImplementacion) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("CronogramaSemillero.findAllActivitiesBySemillerosImplementacion", CronogramaSemillero.class)
              .setParameter("idSemilleroImplemetacion", idSemillerosImplementacion)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idCronogramaSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CronogramaSemillero findById(Long idCronogramaSemillero) throws JpaDinaeException {
    try {
      return entityManager.find(CronogramaSemillero.class, idCronogramaSemillero);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
