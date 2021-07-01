package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.OtrosGastosProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IOtrosGastosProyectoLocal;
import co.gov.policia.dinae.modelo.OtrosGastosProyecto;
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
public class OtrosGastosProyectoEjbBean implements IOtrosGastosProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<OtrosGastosProyecto> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosGastosProyecto.findAll", OtrosGastosProyecto.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<OtrosGastosProyecto> findOtrosGastosByProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosGastosProyecto.findOtrosGastosByProyecto", OtrosGastosProyecto.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<OtrosGastosProyectoDTO> findOtrosGastosByProyectoDTO(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosGastosProyectoDTO.findOtrosGastosByProyectoDTO", OtrosGastosProyectoDTO.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idOtrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public OtrosGastosProyecto findById(Long idOtrosGastosProyecto) throws JpaDinaeException {
    try {
      List results = entityManager.createNamedQuery("OtrosGastosProyecto.findById", OtrosGastosProyecto.class)
              .setParameter("idOtrosGastosProyecto", idOtrosGastosProyecto)
              .getResultList();
      if (results == null || results.isEmpty()) {
        return null;
      }

      return (OtrosGastosProyecto) results.get(0);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param otrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public OtrosGastosProyecto saveOrUpdate(OtrosGastosProyecto otrosGastosProyecto) throws JpaDinaeException {
    try {
      if (otrosGastosProyecto.getIdOtrosGastosProyecto() == null) {
        otrosGastosProyecto.setFechaRegistro(new Date());
        entityManager.persist(otrosGastosProyecto);
      } else {
        entityManager.merge(otrosGastosProyecto);
      }

      return otrosGastosProyecto;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")
  /**
   *
   * @param _otrosGastosProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void delete(OtrosGastosProyecto _otrosGastosProyecto) throws JpaDinaeException {
    try {
      OtrosGastosProyecto gasto = entityManager.find(OtrosGastosProyecto.class, _otrosGastosProyecto.getIdOtrosGastosProyecto());
      entityManager.remove(gasto);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @param idTipoRubro
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public OtrosGastosProyectoDTO contarOtrosGastosRubro(Long idProyecto, Long idTipoRubro) throws JpaDinaeException {
    try {
      List results = entityManager.createNamedQuery("OtrosGastosProyecto.contarOtrosGastosRubro", OtrosGastosProyectoDTO.class)
              .setParameter("idProyecto", idProyecto)
              .setParameter("idTipoRubro", idTipoRubro)
              .getResultList();
      if (results != null && !results.isEmpty()) {
        return (OtrosGastosProyectoDTO) results.get(0);
      }

      return null;
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
  public List<OtrosGastosProyectoDTO> findOtrosGastosByPlanTrabajoDTO(Long idPlanTrabajo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosGastosProyectoDTO.findOtrosGastosByPlanTrabajoDTO", OtrosGastosProyectoDTO.class)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idPlanTrabajo
   * @param idTipoRubro
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public OtrosGastosProyectoDTO contarOtrosGastosRubroPlanTrabajo(Long idPlanTrabajo, Long idTipoRubro) throws JpaDinaeException {
    try {
      List results = entityManager.createNamedQuery("OtrosGastosProyecto.contarOtrosGastosRubroPlanTrabajo", OtrosGastosProyectoDTO.class)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .setParameter("idTipoRubro", idTipoRubro)
              .getResultList();
      if (results != null && !results.isEmpty()) {
        return (OtrosGastosProyectoDTO) results.get(0);
      }

      return null;
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
  public List<OtrosGastosProyecto> findOtrosGastosByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("OtrosGastosProyecto.findOtrosGastosByPlanTrabajo", OtrosGastosProyecto.class)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
