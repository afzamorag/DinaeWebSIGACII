package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEquiposInvestigacionLocal;
import co.gov.policia.dinae.modelo.EquiposInvestigacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/11/28
 */
@Stateless
public class EquipoInvestigacionEjbBean implements IEquiposInvestigacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public List<EquiposInvestigacion> getListaEquiposInvestigacion() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EquiposInvestigacion.findAll").getResultList();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getLocalizedMessage(), e);
    }
  }

  @Override
  public void addEquipoInvestigacion(EquiposInvestigacion equipo) throws JpaDinaeException {
    try {
      /* Remover este código cuando se implemente el caso de uso pr_01 */
      entityManager.persist(equipo);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public void updateEquipoInvestigacion(EquiposInvestigacion equipo) throws JpaDinaeException {
    try {
      /* Remover este código cuando se implemente el caso de uso pr_01 */
      entityManager.merge(equipo);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   * saveOrUpdate
   *
   * @param equipo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EquiposInvestigacion saveOrUpdate(EquiposInvestigacion equipo) throws JpaDinaeException {
    try {
      if (equipo.getIdEquipoInvestigacion() == null) {
        entityManager.persist(equipo);
      } else {
        entityManager.merge(equipo);
      }
      return equipo;
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   * findEquiposByProyecto
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List findEquiposByProyecto(Long idProyecto) throws JpaDinaeException {
    List<EquiposInvestigacion> results = new ArrayList<EquiposInvestigacion>();
    try {
      results = entityManager.createNamedQuery("EquiposInvestigacion.findByProyecto", EquiposInvestigacion.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

    return results;
  }

  /**
   *
   * @param equipo
   * @throws JpaDinaeException
   */
  @Override
  public void delete(EquiposInvestigacion equipo) throws JpaDinaeException {
    try {
      EquiposInvestigacion eq = entityManager.find(EquiposInvestigacion.class, equipo.getIdEquipoInvestigacion());
      entityManager.remove(eq);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EquiposInvestigacion> findByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("EquiposInvestigacion.findByPlanTrabajo", EquiposInvestigacion.class)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

}
