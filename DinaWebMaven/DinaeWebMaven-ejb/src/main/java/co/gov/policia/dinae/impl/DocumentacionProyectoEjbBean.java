package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IDocumentacionProyectoLocal;
import co.gov.policia.dinae.modelo.DocumentacionProyecto;
import co.gov.policia.dinae.dto.DocumentacionProyectoDTO;
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
public class DocumentacionProyectoEjbBean implements IDocumentacionProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public DocumentacionProyecto obtenerDocumentacionProyectoByIdDocumProyecto(Long idDocumentacionProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(DocumentacionProyecto.class, idDocumentacionProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public List<DocumentacionProyecto> obtenerListaDocumentacionProyectoByIdProyectoAndIdUsuarioRol(Long idProyecto, Long idUsuarioRol) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("DocumentacionProyecto.findAllByIdProyectoAndIdUsuarioRol")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idUsuarioRol", idUsuarioRol)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public DocumentacionProyecto guardarDocumentacionProyecto(DocumentacionProyecto documentacionProyecto) throws JpaDinaeException {
    try {

      if (documentacionProyecto.getIdDocumProyecto() == null) {

        documentacionProyecto.setFechaPresentacion(new Date());
        entityManager.persist(documentacionProyecto);

      } else {

        entityManager.merge(documentacionProyecto);

      }

      return documentacionProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public List<DocumentacionProyecto> getListaDocumentacionProyectoAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("DocumentacionProyecto.findAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public List<DocumentacionProyectoDTO> getListaDocumentacionProyectoDTOByIdProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("DocumentacionProyectoDTO.findAllByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public void eliminarDocumentacionProyecto(Long idDocumentacionProyecto) throws JpaDinaeException {

    try {
      entityManager.createNamedQuery("DocumentacionProyecto.EliminarDocumentacionProyectoPorId")
              .setParameter("idDocumProyecto", idDocumentacionProyecto)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

}
