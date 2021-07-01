package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IOtrosEstimulosSemilleroLocal;
import co.gov.policia.dinae.modelo.OtrosEstimulosSemillero;
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
public class OtrosEstimulosSemilleroEjbBean implements IOtrosEstimulosSemilleroLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<OtrosEstimulosSemillero> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosEstimulosSemillero.findAll", OtrosEstimulosSemillero.class).getResultList();
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
  public OtrosEstimulosSemillero findById(Long idOtrosEstimulosSemillero) throws JpaDinaeException {
    try {
      return entityManager.find(OtrosEstimulosSemillero.class, idOtrosEstimulosSemillero);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param estimulosSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public OtrosEstimulosSemillero saveOrUpdate(OtrosEstimulosSemillero estimulosSemillero) throws JpaDinaeException {
    try {

      if (estimulosSemillero.getIdOtrosEstimulosSemillero() == null) {
        estimulosSemillero.setFechaCreacion(new Date());
        entityManager.persist(estimulosSemillero);
      } else {
        estimulosSemillero.setFechaActualizacion(new Date());
        entityManager.merge(estimulosSemillero);
      }

      return estimulosSemillero;
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
  public List<OtrosEstimulosSemillero> findBySemilleroInvestigacion(Long idSemillero) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosEstimulosSemillero.findBySemilleroInvestigacion", OtrosEstimulosSemillero.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
