package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IReunionesSemilleroLocal;
import co.gov.policia.dinae.modelo.ReunionesSemillero;
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
public class ReunionesSemilleroEjbBean implements IReunionesSemilleroLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<ReunionesSemillero> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ReunionesSemillero.findAll", ReunionesSemillero.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idReunionesSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ReunionesSemillero findById(Long idReunionesSemillero) throws JpaDinaeException {
    try {
      return entityManager.find(ReunionesSemillero.class, idReunionesSemillero);
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
  public List<ReunionesSemillero> findBySemillero(Long idSemillero) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ReunionesSemillero.findBySemillero", ReunionesSemillero.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param reunionesSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ReunionesSemillero saveOrUpdate(ReunionesSemillero reunionesSemillero) throws JpaDinaeException {
    try {

      if (reunionesSemillero.getIdReunionSemillero() == null) {
        reunionesSemillero.setFechaRegistro(new Date());
        entityManager.persist(reunionesSemillero);
      } else {
        reunionesSemillero.setFechaActualizacion(new Date());
        entityManager.merge(reunionesSemillero);
      }

      return reunionesSemillero;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
