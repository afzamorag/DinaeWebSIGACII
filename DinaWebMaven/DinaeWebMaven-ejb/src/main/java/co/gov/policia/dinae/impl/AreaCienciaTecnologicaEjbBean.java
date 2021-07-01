package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class AreaCienciaTecnologicaEjbBean implements IAreaCienciaTecnologiaLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<AreaCienciaTecnologia> getAreaCienciaTecnologias() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("AreaCienciaTecnologia.findAll")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public List<AreaCienciaTecnologia> findAllNoEstado() throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("AreaCienciaTecnologia.findAllNoEstado")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public void saveOrUpdate(AreaCienciaTecnologia areaCienciaTecnologia) throws JpaDinaeException {
    try {

      if (areaCienciaTecnologia.getIdAreaCienciaTecnologia() == null) {
        entityManager.persist(areaCienciaTecnologia);
      } else {
        entityManager.merge(areaCienciaTecnologia);
      }

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

}
