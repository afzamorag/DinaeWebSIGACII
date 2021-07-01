package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.modelo.Linea;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class LineaEjbBean implements ILineaLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Linea> getLineasPorArea(Long idArea) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Linea.findAllPorArea")
              .setParameter("idAreaCienciaTecnologia", idArea)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public Linea obtenerLineaPorId(Long idLinea) throws JpaDinaeException {
    try {

      return entityManager.find(Linea.class, idLinea);

    } catch (NoResultException e) {
      throw new JpaDinaeException(e.getMessage(), e);
    } catch (NonUniqueResultException e) {
      throw new JpaDinaeException(e.getMessage(), e);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public List<Linea> getAllLineas() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Linea.findAll")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public List<Linea> findAllNoEstado() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("Linea.findAllNoEstado")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public void saveOrUpdate(Linea linea) throws JpaDinaeException {
    try {
      if (linea.getIdLinea() == null) {
        entityManager.persist(linea);
      } else {
        entityManager.merge(linea);
      }

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }
}
