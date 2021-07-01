package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ITalentoEstimuloSemilleroLocal;
import co.gov.policia.dinae.modelo.TalentoEstimuloSemillero;
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
public class TalentoEstimuloSemilleroEjbBean implements ITalentoEstimuloSemilleroLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<TalentoEstimuloSemillero> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("TalentoEstimuloSemillero.findAll", TalentoEstimuloSemillero.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idTalentoEstimuloSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TalentoEstimuloSemillero findById(Long idTalentoEstimuloSemillero) throws JpaDinaeException {
    try {
      return entityManager.find(TalentoEstimuloSemillero.class, idTalentoEstimuloSemillero);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param talentoEstimuloSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TalentoEstimuloSemillero saveOrUpdate(TalentoEstimuloSemillero talentoEstimuloSemillero) throws JpaDinaeException {
    try {
      if (talentoEstimuloSemillero.getIdTalentoEstimuloSemillero() == null) {
        talentoEstimuloSemillero.setFechaCreacion(new Date());
        entityManager.persist(talentoEstimuloSemillero);
      } else {
        entityManager.merge(talentoEstimuloSemillero);
      }

      return talentoEstimuloSemillero;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idOtrosEstimulosSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<TalentoEstimuloSemillero> findByOtrosEstimulosSemillero(Long idOtrosEstimulosSemillero) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("TalentoEstimuloSemillero.findByOtrosEstimulosSemillero", TalentoEstimuloSemillero.class)
              .setParameter("idOtrosEstimulosSemillero", idOtrosEstimulosSemillero)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param talentoEstimuloSemillero
   * @throws JpaDinaeException
   */
  @Override
  public void deleteEntity(TalentoEstimuloSemillero talentoEstimuloSemillero) throws JpaDinaeException {
    try {

      talentoEstimuloSemillero = findById(talentoEstimuloSemillero.getIdTalentoEstimuloSemillero());
      entityManager.remove(talentoEstimuloSemillero);

    } catch (JpaDinaeException ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
