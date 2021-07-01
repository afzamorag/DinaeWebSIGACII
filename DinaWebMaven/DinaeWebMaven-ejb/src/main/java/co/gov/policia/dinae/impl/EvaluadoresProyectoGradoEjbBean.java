package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.EvaluadoresProyectoGradoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEvaluadoresProyectoGradoLocal;
import co.gov.policia.dinae.modelo.EvaluadoresProyectoGrado;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author RafaelGomez
 */
@Stateless
public class EvaluadoresProyectoGradoEjbBean implements IEvaluadoresProyectoGradoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public EvaluadoresProyectoGrado guardarEvaluadoresProyectoGrado(EvaluadoresProyectoGrado evaluadoresProyectoGrado) throws JpaDinaeException {
    try {

      if (evaluadoresProyectoGrado.getIdEvaluadorProy() == null) {
        evaluadoresProyectoGrado.setFechaRegistro(new Date());
        entityManager.persist(evaluadoresProyectoGrado);

      } else {
        entityManager.merge(evaluadoresProyectoGrado);
      }

      return evaluadoresProyectoGrado;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public List<EvaluadoresProyectoGradoDTO> getListaEvaluadoresProyectoDTOByIdProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EvaluadoresProyectoDTO.findAllByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public List<EvaluadoresProyectoGrado> getListaEvaluadoresProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EvaluadoresProyecto.findAllByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

}
