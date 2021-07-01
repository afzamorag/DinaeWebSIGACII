package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.SugerenciasProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ISugerenciasProyectoLocal;
import co.gov.policia.dinae.modelo.SugerenciasProyecto;
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
public class SugerenciasProyectoEjbBean implements ISugerenciasProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idSugerenciasProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public SugerenciasProyecto obtenerSugerenciasProyectoPorId(Long idSugerenciasProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(SugerenciasProyecto.class, idSugerenciasProyecto);

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
  public SugerenciasProyecto guardarSugerenciasProyecto(SugerenciasProyecto actividadesRealizadasProyecto) throws JpaDinaeException {
    try {

      if (actividadesRealizadasProyecto.getIdSugerenciasProyecto() == null) {

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
  public List<SugerenciasProyecto> getListaSugerenciasProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SugerenciasProyecto.findAll")
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
  public List<SugerenciasProyectoDTO> getListaSugerenciasProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SugerenciasProyectoDTO.findAllPorProyecto")
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
  public List<SugerenciasProyectoDTO> getListaSugerenciasProyectoDTOporProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SugerenciasProyectoDTO.findAllPorProyectoYcompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idSugerenciasProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarSugerenciasProyecto(Long idSugerenciasProyecto) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("SugerenciasProyecto.DELETEactividad")
              .setParameter("idSugerenciasProyecto", idSugerenciasProyecto)
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
  public List<SugerenciasProyecto> getListaSugerenciasProyectoPorProyectoYCompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SugerenciasProyecto.findAllPorProyectoYCompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

}
