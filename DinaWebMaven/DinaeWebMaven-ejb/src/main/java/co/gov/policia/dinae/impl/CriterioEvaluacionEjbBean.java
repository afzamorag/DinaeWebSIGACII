package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.CriterioEvaluacionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICriterioEvaluacionLocal;
import co.gov.policia.dinae.modelo.CriterioEvaluacion;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class CriterioEvaluacionEjbBean implements ICriterioEvaluacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idCriterioEvaluacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CriterioEvaluacion obtenerCriterioEvaluacionPorId(Long idCriterioEvaluacion) throws JpaDinaeException {
    try {

      return entityManager.find(CriterioEvaluacion.class, idCriterioEvaluacion);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param tema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CriterioEvaluacion guardarCriterioEvaluacion(CriterioEvaluacion tema) throws JpaDinaeException {
    try {

      if (tema.getIdCriterioEvaluacion() == null) {

        entityManager.persist(tema);

      } else {

        entityManager.merge(tema);

      }

      return tema;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<CriterioEvaluacion> getListaCriterioEvaluacionTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("CriterioEvaluacion.findAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param estado
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CriterioEvaluacion> getListaCriterioEvaluacionPorEstado(String estado, String tipo) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("CriterioEvaluacion.findAllPorEstado")
              .setParameter("estado", estado)
              .setParameter("tipo", tipo)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param estado
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CriterioEvaluacionDTO> getListaCriterioEvaluacionPorEstadoDTO(String estado, String tipo) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("CriterioEvaluacionDTO.findAllPorEstadoDTO")
              .setParameter("estado", estado)
              .setParameter("tipo", tipo)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  @Override
  public List<CriterioEvaluacion> getListaCriterioEvaluacionPorTipo(String tipo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("CriterioEvaluacion.findAllPorTipo").setParameter("tipo", tipo).
              getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

}
