package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IPersonalImplementacionLocal;
import co.gov.policia.dinae.modelo.PersonalImplementacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class PersonalImplementacionEjbBean implements IPersonalImplementacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public PersonalImplementacion getPersonalImplementacionByIdentificacion(String numIdentificacion) throws JpaDinaeException {
    try {
      return (PersonalImplementacion) entityManager.createNamedQuery("PersonalImplementacion.findByIdentificacion").
              setParameter("identificacion", numIdentificacion).getSingleResult();
    } catch (NoResultException nre) {
      Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, nre.getLocalizedMessage());
      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param personalImplementacion
   * @throws JpaDinaeException
   */
  @Override
  public PersonalImplementacion savePersonalImplementacion(PersonalImplementacion personalImplementacion) throws JpaDinaeException {

    try {

      if (personalImplementacion.getIdPersonalImplementacion() == null) {

        personalImplementacion.setFechaRegistro(new Date());
        entityManager.persist(personalImplementacion);

      } else {

        entityManager.merge(personalImplementacion);

      }

      return personalImplementacion;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<PersonalImplementacion> getPersonalImplementacionListPorIdInformeImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("PersonalImplementacion.findAllByIdidInformeImplementacion").
              setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

}
