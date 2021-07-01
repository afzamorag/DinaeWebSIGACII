package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEvidenciaProyectoLocal;
import co.gov.policia.dinae.modelo.EvidenciaProyecto;
import co.gov.policia.dinae.modelo.EvidenciaProyectoDTO;
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
public class EvidenciaProyectoEjbBean implements IEvidenciaProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idEvidenciaProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EvidenciaProyecto obtenerEvidenciaProyectoPorId(Long idEvidenciaProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(EvidenciaProyecto.class, idEvidenciaProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param evidenciaProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EvidenciaProyecto guardarEvidenciaProyecto(EvidenciaProyecto evidenciaProyecto) throws JpaDinaeException {
    try {

      if (evidenciaProyecto.getIdEvidenciaProyecto() == null) {

        evidenciaProyecto.setFechaIngresoRegistro(new Date());
        entityManager.persist(evidenciaProyecto);

      } else {

        entityManager.merge(evidenciaProyecto);

      }

      return evidenciaProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<EvidenciaProyecto> getListaEvidenciaProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("EvidenciaProyecto.findAll")
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
  public List<EvidenciaProyectoDTO> getListaEvidenciaProyectoDTOPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("EvidenciaProyectoDTO.findAllPorProyecto")
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
  public List<EvidenciaProyectoDTO> getListaEvidenciaProyectoDTOPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("EvidenciaProyectoDTO.findAllPorProyectoYcompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idIndicadorProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarEvidenciaProyecto(Long idIndicadorProyecto) throws JpaDinaeException {

    try {
      entityManager.createNamedQuery("EvidenciaProyecto.EliminarIndicadorPorId")
              .setParameter("idEvidenciaProyecto", idIndicadorProyecto)
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
  public List<EvidenciaProyecto> getListaEvidenciaProyectoPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("EvidenciaProyecto.findAllPorProyectoYcompromisoProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }
}
