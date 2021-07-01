package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.ActividadesRealizadasProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IActividadesRealizadasProyectoLocal;
import co.gov.policia.dinae.modelo.ActividadesRealizadasProyecto;
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
public class ActividadesRealizadasProyectoEjbBean implements IActividadesRealizadasProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idActividadesRealizadasProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ActividadesRealizadasProyecto obtenerActividadesRealizadasProyectoPorId(Long idActividadesRealizadasProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(ActividadesRealizadasProyecto.class, idActividadesRealizadasProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param actividadesRealizadasProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ActividadesRealizadasProyecto guardarActividadesRealizadasProyecto(ActividadesRealizadasProyecto actividadesRealizadasProyecto) throws JpaDinaeException {
    try {

      if (actividadesRealizadasProyecto.getIdActividadesRealizadasProyecto() == null) {

        actividadesRealizadasProyecto.setFechaCreacion(new Date());
        entityManager.persist(actividadesRealizadasProyecto);

      } else {

        entityManager.merge(actividadesRealizadasProyecto);

      }

      return actividadesRealizadasProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<ActividadesRealizadasProyecto> getListaActividadesRealizadasProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ActividadesRealizadasProyecto.findAll")
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
  public List<ActividadesRealizadasProyectoDTO> getListaActividadesRealizadasProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ActividadesRealizadasProyectoDTO.findAllPorProyecto")
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
  public List<ActividadesRealizadasProyectoDTO> getListaActividadesRealizadasProyectoDTOporProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ActividadesRealizadasProyectoDTO.findAllPorProyectoYcompromisoProyecto")
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

      entityManager.createNamedQuery("ActividadesRealizadasProyecto.DELETEactividad")
              .setParameter("idActividadesRealizadasProyecto", idActividadRealizada)
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
  public List<ActividadesRealizadasProyecto> getListaActividadesRealizadasProyectoPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ActividadesRealizadasProyecto.findAllPorProyectoYcompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

}
