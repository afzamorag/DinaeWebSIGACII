package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.FuenteProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Édder Peña Barranco
 */
@Stateless
public class FuenteProyectoEjbBean implements IFuenteProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idFuenteProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public FuenteProyecto getFuenteFinancieraById(Long idFuenteProyecto) throws JpaDinaeException {
    try {
      return entityManager.find(FuenteProyecto.class, idFuenteProyecto);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param fuenteProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void addFuenteFinanciera(FuenteProyecto fuenteProyecto) throws JpaDinaeException {
    try {
      /* Remover este código cuando se implemente el caso de uso pr_01 */
      entityManager.persist(fuenteProyecto);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param fuenteProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void updateFuenteFinanciera(FuenteProyecto fuenteProyecto) throws JpaDinaeException {
    try {
      entityManager.merge(fuenteProyecto);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<FuenteProyecto> getListaFuentesPorProyecto(Long idProyecto) throws JpaDinaeException {
    List<FuenteProyecto> lista;
    try {
      lista = entityManager.createNamedQuery("FuenteProyecto.findFuentesByProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
    return lista;
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<FuenteProyectoDTO> getListaFuentesPorProyectoDTO(Long idProyecto) throws JpaDinaeException {
    List<FuenteProyectoDTO> lista;
    try {
      lista = entityManager.createNamedQuery("FuenteProyecto.findFuentesByProyectoDTO")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
    return lista;
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int contarFuentesBaseProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      Object contar = entityManager.createNamedQuery("FuenteProyecto.countFuentesBaseByProyecto")
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

      if (contar == null) {
        return 0;
      }
      return ((Number) contar).intValue();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<String> getListaFuentesNoBase(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("FuenteProyecto.findNombresFuentesNOBaseByProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int contarFuentesNOBaseProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      Object contar = entityManager.createNamedQuery("FuenteProyecto.countFuentesNOBaseByProyecto")
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

      if (contar == null) {
        return 0;
      }
      return ((Number) contar).intValue();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idProyecto
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int contarFuentesBaseProyectoByIdProyectoYUnidad(Long idProyecto, Long idUnidadPolicial) throws JpaDinaeException {

    try {

      Object contar = entityManager.createNamedQuery("FuenteProyecto.countFuentesBaseByProyectoAndUnidad")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getSingleResult();

      if (contar == null) {
        return 0;
      }
      return ((Number) contar).intValue();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   * saveOrUpdate
   *
   * @param fuenteProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public FuenteProyecto saveOrUpdate(FuenteProyecto fuenteProyecto) throws JpaDinaeException {
    try {
      if (fuenteProyecto.getIdFuenteProyecto() == null) {
        fuenteProyecto.setFechaRegistro(new Date());
        entityManager.persist(fuenteProyecto);
      } else {

        entityManager.merge(fuenteProyecto);
      }

      return fuenteProyecto;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param fuenteProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void delete(FuenteProyecto fuenteProyecto) throws JpaDinaeException {
    try {
      FuenteProyecto fuente = entityManager.find(FuenteProyecto.class, fuenteProyecto.getIdFuenteProyecto());
      entityManager.remove(fuente);
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
  public List<FuenteProyectoDTO> findFuentesByPlanTrabajoImpl(Long idPlanTrabajo) throws JpaDinaeException {
    try {

      List<FuenteProyectoDTO> resList = entityManager.createNamedQuery("FuenteProyectoDTO.findFuentesByPlanTrabajoImpl")
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();

      return resList;

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<FuenteProyecto> findFuentesByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException {
    try {

      List<FuenteProyecto> resList = entityManager.createNamedQuery("FuenteProyecto.findFuentesByPlanTrabajo")
              .setParameter("idPlanTrabajo", idPlanTrabajo)
              .getResultList();

      return resList;

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

}
