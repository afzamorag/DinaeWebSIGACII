package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.ViajesProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IViajesProyectoLocal;
import co.gov.policia.dinae.modelo.ViajesProyecto;
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
public class ViajesProyectoEjbBean implements IViajesProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ViajesProyecto> findViajesByProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ViajesProyecto.findViajesByProyecto", ViajesProyecto.class)
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
  public List<ViajesProyectoDTO> findViajesByProyectoDTO(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ViajesProyecto.findViajesByProyectoDTO", ViajesProyectoDTO.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param viajesProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ViajesProyecto saveOrUpdate(ViajesProyecto viajesProyecto) throws JpaDinaeException {
    try {
      if (viajesProyecto.getIdViajeProyecto() == null) {
        entityManager.persist(viajesProyecto);
      } else {
        entityManager.merge(viajesProyecto);
      }
      return viajesProyecto;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idViajesProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ViajesProyecto findById(Long idViajesProyecto) throws JpaDinaeException {
    try {
      ViajesProyecto viajesProyecto = null;

      List results = entityManager.createNamedQuery("ViajesProyecto.findById", ViajesProyecto.class)
              .setParameter("idViajeProyecto", idViajesProyecto)
              .getResultList();
      if (results != null && !results.isEmpty()) {
        viajesProyecto = (ViajesProyecto) results.get(0);
      }

      return viajesProyecto;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")
  /**
   *
   * @param _viajesProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void delete(ViajesProyecto _viajesProyecto) throws JpaDinaeException {
    try {
      ViajesProyecto viaje = entityManager.find(ViajesProyecto.class, _viajesProyecto.getIdViajeProyecto());
      entityManager.remove(viaje);
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
  public List<ViajesProyectoDTO> findViajesByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ViajesProyectoDTO.findViajesByPlanTrabajo", ViajesProyectoDTO.class)
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
