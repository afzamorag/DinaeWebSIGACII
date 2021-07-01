package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEjecucionEquiposProyectoLocal;
import co.gov.policia.dinae.modelo.EjecucionEquiposProyecto;
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
public class EjecucionEquiposProyectoEjbBean implements IEjecucionEquiposProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecucionEquiposProyecto> findEquipoRelacionadoProyecto(Long idProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEquiposProyecto.findEquipoRelacionadoProyecto", EjecucionEquiposProyecto.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<EjecucionEquiposProyecto> findEquipoRelacionadoImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEquiposProyecto.findEquipoRelacionadoImplementacionProyecto", EjecucionEquiposProyecto.class)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvance
   * @param idEquipoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionEquiposProyecto findEquipoRelacionadoInformeProyecto(Long idInformeAvance, Long idEquipoInvestigacion) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEquiposProyecto.findEquipoRelacionadoInformeProyecto", EjecucionEquiposProyecto.class)
              .setParameter("idInformeAvance", idInformeAvance)
              .setParameter("idEquipoInvestigacion", idEquipoInvestigacion)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionEquiposProyecto) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param ejecucionEquiposProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionEquiposProyecto saveOrUpdate(EjecucionEquiposProyecto ejecucionEquiposProyecto) throws JpaDinaeException {

    try {

      if (ejecucionEquiposProyecto.getIdEjecucionEquipProy() == null) {
        entityManager.persist(ejecucionEquiposProyecto);
      } else {
        entityManager.merge(ejecucionEquiposProyecto);
      }

      return ejecucionEquiposProyecto;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  @Override
  public void deleteEquipoRelacionadoInformeProyecto(Long idInformeAvance) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionEquiposProyecto.deleteEquipoRelacionadoInformeProyecto")
              .setParameter("idInformeAvance", idInformeAvance)
              .executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idEquipoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecucionEquiposProyecto findEquipoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idEquipoInvestigacion) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EjecucionEquiposProyecto.findEquipoRelacionadoInformeImplementacionProyecto", EjecucionEquiposProyecto.class)
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .setParameter("idEquipoInvestigacion", idEquipoInvestigacion)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EjecucionEquiposProyecto) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @throws JpaDinaeException
   */
  @Override
  public void deleteEquipoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("EjecucionEquiposProyecto.deleteEquipoRelacionadoInformeImplementacionProyecto")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
