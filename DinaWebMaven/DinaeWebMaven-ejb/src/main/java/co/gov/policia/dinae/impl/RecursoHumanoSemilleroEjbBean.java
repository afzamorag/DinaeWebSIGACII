package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IRecursoHumanoSemilleroLocal;
import co.gov.policia.dinae.modelo.RecursoHumanoSemillero;
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
public class RecursoHumanoSemilleroEjbBean implements IRecursoHumanoSemilleroLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param recursoHumanoSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public RecursoHumanoSemillero saveOrUpdate(RecursoHumanoSemillero recursoHumanoSemillero) throws JpaDinaeException {
    try {

      if (recursoHumanoSemillero.getIdRecursoHumanoSemi() == null) {
        entityManager.persist(recursoHumanoSemillero);
      } else {
        entityManager.merge(recursoHumanoSemillero);
      }

      return recursoHumanoSemillero;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<RecursoHumanoSemillero> findAllBySemillero(Long idSemillero) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("RecursoHumanoSemillero.findAllBySemillero", RecursoHumanoSemillero.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param identificacion
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public RecursoHumanoSemillero findByIdAndSemilleroId(String identificacion, Long idSemillero) throws JpaDinaeException {
    try {

      List result = entityManager.createNamedQuery("RecursoHumanoSemillero.findByIdAndSemilleroId", RecursoHumanoSemillero.class)
              .setParameter("identificacion", identificacion)
              .setParameter("idSemillero", idSemillero)
              .getResultList();

      if (result != null && !result.isEmpty()) {
        return (RecursoHumanoSemillero) result.get(0);
      }

      return null;
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idSemillero
   * @param fechaOtorgamiento
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<RecursoHumanoSemillero> findBySemilleroAndEstadoActivoFecha(Long idSemillero, Date fechaOtorgamiento) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("RecursoHumanoSemillero.findBySemilleroAndEstadoActivoFecha", RecursoHumanoSemillero.class)
              .setParameter("idSemillero", idSemillero)
              .setParameter("fechaOtorgamiento", fechaOtorgamiento)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }
}
