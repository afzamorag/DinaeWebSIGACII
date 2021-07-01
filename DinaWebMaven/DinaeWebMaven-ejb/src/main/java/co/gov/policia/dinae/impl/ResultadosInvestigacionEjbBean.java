package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IResultadosInvestigacionLocal;
import co.gov.policia.dinae.modelo.ResultadosInvestigacion;
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
public class ResultadosInvestigacionEjbBean implements IResultadosInvestigacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResultadosInvestigacion.findAll", ResultadosInvestigacion.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idResultadosInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ResultadosInvestigacion findByIdResultadoInvestigacion(Long idResultadosInvestigacion) throws JpaDinaeException {

    try {

      ResultadosInvestigacion resultadosInvestigacion = null;

      List results = entityManager.createNamedQuery("ResultadosInvestigacion.findByIdResultadoInvestigacion", ResultadosInvestigacion.class)
              .setParameter("idResultadoInvestigacion", idResultadosInvestigacion)
              .getResultList();

      if (!results.isEmpty()) {
        resultadosInvestigacion = (ResultadosInvestigacion) results.get(0);
      }

      return resultadosInvestigacion;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param resultadosInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ResultadosInvestigacion saveOrUpdate(ResultadosInvestigacion resultadosInvestigacion) throws JpaDinaeException {
    try {
      if (resultadosInvestigacion.getIdResultadoInvestigacion() == null) {
        resultadosInvestigacion.setFechaRegistro(new Date());
        entityManager.persist(resultadosInvestigacion);
      } else {
        entityManager.merge(resultadosInvestigacion);
      }

      return resultadosInvestigacion;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvace
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List findByInformeAvanceProyecto(Long idInformeAvace, Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ResultadosInvestigacion.findByInformeAvanceProyecto", ResultadosInvestigacion.class)
              .setParameter("idInformeAvance", idInformeAvace)
              .setParameter("idProyecto", idProyecto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   * delete
   *
   * @param resultadosInvestigacion
   * @throws JpaDinaeException
   */
  @Override
  public void delete(ResultadosInvestigacion resultadosInvestigacion) throws JpaDinaeException {
    try {
      ResultadosInvestigacion res = entityManager.find(ResultadosInvestigacion.class, resultadosInvestigacion.getIdResultadoInvestigacion());
      entityManager.remove(res);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
