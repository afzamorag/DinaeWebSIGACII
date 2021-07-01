package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoLocal;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class EvaluacionProyectoEjbBean implements IEvaluacionProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idEvaluacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EvaluacionProyecto obtenerEvaluacionProyectoPorId(Long idEvaluacionProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(EvaluacionProyecto.class, idEvaluacionProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param evaluacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EvaluacionProyecto guardarEvaluacionProyecto(EvaluacionProyecto evaluacionProyecto) throws JpaDinaeException {
    try {

      if (evaluacionProyecto.getIdEvaluacionProyecto() == null) {

        entityManager.persist(evaluacionProyecto);

      } else {

        entityManager.merge(evaluacionProyecto);

      }

      return evaluacionProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<EvaluacionProyecto> getListaEvaluacionProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("EvaluacionProyecto.findAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public BigDecimal getValorIngresadoEvaluacionProyectoPorProyectoYCriterio(Long idProyecto, Long idCriterio) throws JpaDinaeException {

    try {

      return (BigDecimal) entityManager.createNamedQuery("EvaluacionProyecto.findValorCriterioPorProtectoYcriterio")
              .setParameter("idCriterio", idCriterio)
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

    } catch (NoResultException nre) {
      return null;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public BigDecimal getValorTotalIngresadoEvaluacionProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return (BigDecimal) entityManager.createNamedQuery("EvaluacionProyecto.SumaValorCriterioPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

    } catch (NoResultException nre) {
      return BigDecimal.ZERO;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @param idCriterio
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EvaluacionProyecto getEvaluacionProyectoPorProyectoYCriterio(Long idProyecto, Long idCriterio) throws JpaDinaeException {

    try {

      return (EvaluacionProyecto) entityManager.createNamedQuery("EvaluacionProyecto.CriterioPorProtectoYcriterio")
              .setParameter("idCriterio", idCriterio)
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

    } catch (NoResultException nre) {
      return null;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EvaluacionProyecto> getCriterioPorEnsayoCritico(Long idEnsayoCritico) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("EvaluacionProyecto.CriterioPorEnsayoCritico", EvaluacionProyecto.class)
              .setParameter("idEnsayoCritico", idEnsayoCritico)
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
  public List<EvaluacionProyecto> getEvaluacionProyecto(Long idProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("EvaluacionProyecto.getEvaluacionProyecto", EvaluacionProyecto.class)
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
