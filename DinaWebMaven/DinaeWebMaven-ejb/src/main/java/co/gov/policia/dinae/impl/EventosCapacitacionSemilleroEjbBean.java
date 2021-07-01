package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEventosCapacitacionSemilleroLocal;
import co.gov.policia.dinae.modelo.EventosCapacitacionSemillero;
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
public class EventosCapacitacionSemilleroEjbBean implements IEventosCapacitacionSemilleroLocal {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<EventosCapacitacionSemillero> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EventosCapacitacionSemillero.findAll", EventosCapacitacionSemillero.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idEventCapaSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EventosCapacitacionSemillero findById(Long idEventCapaSemillero) throws JpaDinaeException {
    try {
      return entityManager.find(EventosCapacitacionSemillero.class, idEventCapaSemillero);
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
  public List<EventosCapacitacionSemillero> findByIdSemillero(Long idSemillero) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EventosCapacitacionSemillero.findByIdSemillero", EventosCapacitacionSemillero.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param capacitacionSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EventosCapacitacionSemillero saveOrUpdate(EventosCapacitacionSemillero capacitacionSemillero) throws JpaDinaeException {
    try {
      if (capacitacionSemillero.getIdEventCapaSemillero() == null) {
        capacitacionSemillero.setFechaCreacion(new Date());
        entityManager.persist(capacitacionSemillero);
      } else {
        capacitacionSemillero.setFechaActualizacion(new Date());
        entityManager.merge(capacitacionSemillero);
      }

      return capacitacionSemillero;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
