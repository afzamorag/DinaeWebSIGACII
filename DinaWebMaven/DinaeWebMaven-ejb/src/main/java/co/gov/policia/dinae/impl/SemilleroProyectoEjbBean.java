package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ISemilleroProyectoLocal;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
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
public class SemilleroProyectoEjbBean implements ISemilleroProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idSemilleroProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public SemilleroProyecto obtenerSemilleroProyectoPorId(Long idSemilleroProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(SemilleroProyecto.class, idSemilleroProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param semilleroProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public SemilleroProyecto guardarSemilleroProyecto(SemilleroProyecto semilleroProyecto) throws JpaDinaeException {
    try {

      if (semilleroProyecto.getIdSemilleroProyecto() == null) {

        semilleroProyecto.setFechaRegistro(new Date());
        entityManager.persist(semilleroProyecto);

      } else {

        entityManager.merge(semilleroProyecto);

      }

      //ACTUALIZAMOS LA FECHA DEL ACTUALIZACION DEL PROYECTO
      entityManager.createNamedQuery("Proyecto.UpdateFechaActualiacionProyecto")
              .setParameter("fechaActualizacion", new Date())
              .setParameter("idProyecto", semilleroProyecto.getProyecto().getIdProyecto())
              .executeUpdate();

      return semilleroProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<SemilleroProyecto> getListaSemilleroProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroProyecto.findAll")
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
  public List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroProyectoDTO.findPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idSemilleroInvestigacion
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarSemilleroProyectoInvestigacion(Long idSemilleroInvestigacion) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("SemilleroProyecto.EliminarSemilleroInvesPorIdSemillosInvestigacion")
              .setParameter("idSemilleroProyecto", idSemilleroInvestigacion)
              .executeUpdate();

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
  public List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroProyectoDTO.findPorProyectoYuniadadPolicial")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<SemilleroProyecto> findAllBySemilleroInvestigacion(Long idSemillero) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("SemilleroProyecto.findAllBySemilleroInvestigacion", SemilleroProyecto.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

}
