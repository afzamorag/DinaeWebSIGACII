package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoGradoLocal;
import co.gov.policia.dinae.modelo.EvaluacionProyectoGrado;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author RafaelGomez
 */
@Stateless
public class EvaluacionProyectoGradoEjbBean implements IEvaluacionProyectoGradoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public EvaluacionProyectoGrado guardarEvaluacionProyectoGrado(EvaluacionProyectoGrado evaluacionProyectoGrado) throws JpaDinaeException {
    try {

      if (evaluacionProyectoGrado.getIdEvaluacionProyGrado() == null) {
        evaluacionProyectoGrado.setFechaRegistro(new Date());
        entityManager.persist(evaluacionProyectoGrado);

      } else {
        entityManager.merge(evaluacionProyectoGrado);
      }

      return evaluacionProyectoGrado;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public EvaluacionProyectoGrado getEvaluacionProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return (EvaluacionProyectoGrado) entityManager.createNamedQuery("EvaluacionProyectoGrado.findEvaluacionProyectoByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

    } catch (NoResultException nre) {
      return null;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

}
