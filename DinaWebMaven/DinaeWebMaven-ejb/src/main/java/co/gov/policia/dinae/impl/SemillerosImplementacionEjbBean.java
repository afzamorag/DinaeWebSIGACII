package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ISemillerosImplementacionLocal;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
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
public class SemillerosImplementacionEjbBean implements ISemillerosImplementacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<SemillerosImplementacion> findAllBySemilleroInvestigacion(Long idSemillero) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("SemillerosImplementacion.findAllBySemilleroInvestigacion", SemillerosImplementacion.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

}
