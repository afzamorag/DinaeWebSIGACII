package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.ResultadosAlcanzadosProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IResultadosAlcanzadosProyectoLocal;
import co.gov.policia.dinae.modelo.ResultadosAlcanzadosProyecto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class ResultadosAlcanzadosProyectoEjbBean implements IResultadosAlcanzadosProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idResultadosAlcanzadosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ResultadosAlcanzadosProyecto obtenerResultadosAlcanzadosProyectoPorId(Long idResultadosAlcanzadosProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(ResultadosAlcanzadosProyecto.class, idResultadosAlcanzadosProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param resultadosAlcanzadosProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ResultadosAlcanzadosProyecto guardarResultadosAlcanzadosProyecto(ResultadosAlcanzadosProyecto resultadosAlcanzadosProyecto) throws JpaDinaeException {
    try {

      if (resultadosAlcanzadosProyecto.getIdResultadosAlcanzadosProyecto() == null) {

        resultadosAlcanzadosProyecto.setFechaCreacion(new Date());
        entityManager.persist(resultadosAlcanzadosProyecto);

      } else {

        entityManager.merge(resultadosAlcanzadosProyecto);

      }

      return resultadosAlcanzadosProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<ResultadosAlcanzadosProyecto> getListaResultadosAlcanzadosProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ResultadosAlcanzadosProyecto.findAll")
              .getResultList();

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
  public List<ResultadosAlcanzadosProyectoDTO> getListaResultadosAlcanzadosProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ResultadosAlcanzadosProyectoDTO.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ResultadosAlcanzadosProyectoDTO> getListaResultadosAlcanzadosProyectoDTOporProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ResultadosAlcanzadosProyectoDTO.findAllPorProyectoYcompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idActividadRealizada
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarActividadRealizada(Long idActividadRealizada) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("ResultadosAlcanzadosProyecto.DELETEactividad")
              .setParameter("idResultadosAlcanzadosProyecto", idActividadRealizada)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ResultadosAlcanzadosProyecto> getListaResultadosAlcanzadosProyectoPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ResultadosAlcanzadosProyecto.findAllPorProyectoYcompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }
}
