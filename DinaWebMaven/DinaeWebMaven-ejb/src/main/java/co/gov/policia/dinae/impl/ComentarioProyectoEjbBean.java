package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.ComentarioProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.modelo.ComentarioCompromisoProyecto;
import co.gov.policia.dinae.modelo.ComentarioProyecto;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ComentarioProyectoEjbBean implements IComentarioProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idComentarioProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ComentarioProyecto obtenerComentarioProyectoPorId(Long idComentarioProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(ComentarioProyecto.class, idComentarioProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param comentarioProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ComentarioProyecto guardarComentarioProyecto(ComentarioProyecto comentarioProyecto) throws JpaDinaeException {
    try {

      if (comentarioProyecto.getIdComentarioProyecto() == null) {

        comentarioProyecto.setFechaRegistro(new Date());
        comentarioProyecto.setFechaModificacion(new Date());
        entityManager.persist(comentarioProyecto);

      } else {

        comentarioProyecto.setFechaModificacion(new Date());
        entityManager.merge(comentarioProyecto);

      }

      return comentarioProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyecto> getListaComentarioProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ComentarioProyecto.findAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idTitem
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyecto> getComentarioProyectoPorItemYproyecto(Long idTitem, Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ComentarioProyecto.findAllPorItemYproyecto")
              .setParameter("idTitem", idTitem)
              .setParameter("idProyecto", idProyecto)
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
  public List<ComentarioProyecto> getComentarioProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ComentarioProyecto.findAllPorIdProyecto")
              .setParameter("idProyecto", idProyecto)
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
  public List<ComentarioProyectoDTO> getComentarioProyectoDTOPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ComentarioProyectoDTO.findAllPorIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @param idDocumProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyectoDTO> getListaComentarioProyectoDTOPorIdProyectoAndIdDocumProyecto(Long idProyecto, Long idDocumProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioProyectoDTO.findAllPorIdProyectoAndIdDocumProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idDocumProyecto", idDocumProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idProyecto
   * @param idDocumProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyectoDTO> getListaComentarioProyectoDTOPorIdProyectoAndIdDocumProyectoTrabajoGrado(Long idProyecto, Long idDocumProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioProyectoDTO.findAllPorIdProyectoAndIdDocumProyectoTrabajoGrado")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idDocumProyecto", idDocumProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyecto> findComentarioByEnsayo(Long idEnsayoCritico) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("ComentarioProyecto.findComentarioByEnsayo", ComentarioProyecto.class)
              .setParameter("idEnsayoCritico", idEnsayoCritico)
              .getResultList();

      return results;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idEnsayoCritico
   * @param autorComentario
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ComentarioProyecto findComentarioByEnsayoAutor(Long idEnsayoCritico, Long autorComentario) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("ComentarioProyecto.findComentarioByEnsayoAutor", ComentarioProyecto.class)
              .setParameter("idEnsayoCritico", idEnsayoCritico)
              .setParameter("autorComentario", autorComentario)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (ComentarioProyecto) results.get(0);
      }

      return null;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idDocumProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyecto> getListaComentarioProyectoPorIdDocumProyecto(Long idDocumProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioProyecto.findAllPorIdDocumProyecto")
              .setParameter("idDocumProyecto", idDocumProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public void eliminarComentarioProyectoPorIdComentarioProyecto(Long idComentarioProyecto) throws JpaDinaeException {

    try {
      entityManager.createNamedQuery("ComentarioProyecto.eliminarComentarioProyectoPorIdComentarioProyecto")
              .setParameter("idComentarioProyecto", idComentarioProyecto)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @param origenComentario
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoProyecto(Long idCompromisoProyecto, String origenComentario) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioCompromisoProyecto.findAllPorIdCompromisoProyectoYorigenCU")
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .setParameter("autorCasoUso", origenComentario)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorCompromisoProyecto(Long idCompromisoProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioCompromisoProyecto.findAllPorCompromisoProyecto")
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @param idCompromisoImplementacion
   * @param origenComentario
   * @param idItemSeleccionado
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoProyectoItemSeleccionado(Long idCompromisoProyecto, Long idCompromisoImplementacion, String origenComentario, Long idItemSeleccionado) throws JpaDinaeException {
    try {

      List<ComentarioCompromisoProyecto> listaComentarioCompromisoProyecto = new ArrayList<ComentarioCompromisoProyecto>();

      if (idCompromisoProyecto != null) {

        listaComentarioCompromisoProyecto.addAll(
                entityManager.createNamedQuery("ComentarioCompromisoProyecto.findAllPorIdCompromisoProyectoYorigenCUeItem")
                .setParameter("idCompromisoProyecto", idCompromisoProyecto)
                .setParameter("autorCasoUso", origenComentario)
                .setParameter("idItem", idItemSeleccionado)
                .getResultList());
      }

      if (idCompromisoImplementacion != null) {

        listaComentarioCompromisoProyecto.addAll(
                entityManager.createNamedQuery("ComentarioCompromisoProyecto.findAllPorIdCompromisoImplementacionYorigenCUeItem")
                .setParameter("idCompromisoImplementacion", idCompromisoProyecto)
                .setParameter("autorCasoUso", origenComentario)
                .setParameter("idItem", idItemSeleccionado)
                .getResultList());

      }

      return listaComentarioCompromisoProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param comentarioCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ComentarioCompromisoProyecto guardarCompromisoProyecto(ComentarioCompromisoProyecto comentarioCompromisoProyecto) throws JpaDinaeException {
    try {

      if (comentarioCompromisoProyecto.getIdComentarioCompromisoProyecto() == null) {

        comentarioCompromisoProyecto.setFechaRegistro(new Date());

        entityManager.persist(comentarioCompromisoProyecto);

      } else {

        entityManager.merge(comentarioCompromisoProyecto);

      }

      return comentarioCompromisoProyecto;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idComentarioCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ComentarioCompromisoProyecto obtenerComentariCompromisoProyecto(Long idComentarioCompromisoProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(ComentarioCompromisoProyecto.class, idComentarioCompromisoProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @param idCompromisoImplementacion
   * @param origenComentario
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioProyectoDTO> getComentarioCompromisoProyectoPorCompromiso(Long idCompromisoProyecto, Long idCompromisoImplementacion, String origenComentario) throws JpaDinaeException {

    try {

      List<ComentarioProyectoDTO> listaComentarioProyectoDTO = new ArrayList<ComentarioProyectoDTO>();

      if (idCompromisoProyecto != null) {

        listaComentarioProyectoDTO.addAll(
                entityManager.createNamedQuery("ComentarioCompromisoProyecto.ComentarioProyectoDTOfindAllPorIdCompromisoProyectoYorigenCU")
                .setParameter("idCompromisoProyecto", idCompromisoProyecto)
                .setParameter("autorCasoUso", origenComentario)
                .getResultList());
      }

      if (idCompromisoImplementacion != null) {

        listaComentarioProyectoDTO.addAll(
                entityManager.createNamedQuery("ComentarioCompromisoProyecto.ComentarioProyectoDTOfindAllPorIdCompromisoImplementacionYorigenCU")
                .setParameter("idCompromisoImplementacion", idCompromisoImplementacion)
                .setParameter("autorCasoUso", origenComentario)
                .getResultList());

      }

      return listaComentarioProyectoDTO;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idCompromisoImplementacion
   * @param origenComentario
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(Long idCompromisoImplementacion, String origenComentario) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioCompromisoProyecto.findAllPorIdCompromisoImplementacionYorigenCU")
              .setParameter("idCompromisoImplementacion", idCompromisoImplementacion)
              .setParameter("autorCasoUso", origenComentario)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(Long idCompromisoImplementacion) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioCompromisoProyecto.findAllPorIdCompromisoImplementacion")
              .setParameter("idCompromisoImplementacion", idCompromisoImplementacion)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

}
