/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.DocumentacionProyecto;
import co.gov.policia.dinae.dto.DocumentacionProyectoDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author RafaelGomez
 */
@Local
public interface IDocumentacionProyectoLocal {

  /**
   *
   * @param idDocumentacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  DocumentacionProyecto obtenerDocumentacionProyectoByIdDocumProyecto(Long idDocumentacionProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idUsuarioRol
   * @return
   * @throws JpaDinaeException
   */
  List<DocumentacionProyecto> obtenerListaDocumentacionProyectoByIdProyectoAndIdUsuarioRol(Long idProyecto, Long idUsuarioRol) throws JpaDinaeException;

  /**
   *
   * @param documentacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  DocumentacionProyecto guardarDocumentacionProyecto(DocumentacionProyecto documentacionProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<DocumentacionProyecto> getListaDocumentacionProyectoAll() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<DocumentacionProyectoDTO> getListaDocumentacionProyectoDTOByIdProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idDocumentacionProyecto
   * @throws JpaDinaeException
   */
  void eliminarDocumentacionProyecto(Long idDocumentacionProyecto) throws JpaDinaeException;

}
