package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ComentarioProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ComentarioCompromisoProyecto;
import co.gov.policia.dinae.modelo.ComentarioProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IComentarioProyectoLocal {

  /**
   *
   * @param idComentarioProyecto
   * @return
   * @throws JpaDinaeException
   */
  ComentarioProyecto obtenerComentarioProyectoPorId(Long idComentarioProyecto) throws JpaDinaeException;

  /**
   *
   * @param comentarioProyecto
   * @return
   * @throws JpaDinaeException
   */
  ComentarioProyecto guardarComentarioProyecto(ComentarioProyecto comentarioProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<ComentarioProyecto> getListaComentarioProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idTitem
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyecto> getComentarioProyectoPorItemYproyecto(Long idTitem, Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyecto> getComentarioProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyectoDTO> getComentarioProyectoDTOPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idDocumProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyectoDTO> getListaComentarioProyectoDTOPorIdProyectoAndIdDocumProyecto(Long idProyecto, Long idDocumProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idDocumProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyectoDTO> getListaComentarioProyectoDTOPorIdProyectoAndIdDocumProyectoTrabajoGrado(Long idProyecto, Long idDocumProyecto) throws JpaDinaeException;

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyecto> findComentarioByEnsayo(Long idEnsayoCritico) throws JpaDinaeException;

  /**
   *
   * @param idEnsayoCritico
   * @param autorComentario
   * @return
   * @throws JpaDinaeException
   */
  ComentarioProyecto findComentarioByEnsayoAutor(Long idEnsayoCritico, Long autorComentario) throws JpaDinaeException;

  /**
   *
   * @param idDocumProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyecto> getListaComentarioProyectoPorIdDocumProyecto(Long idDocumProyecto) throws JpaDinaeException;

  /**
   *
   * @param idComentarioProyecto
   * @throws JpaDinaeException
   */
  void eliminarComentarioProyectoPorIdComentarioProyecto(Long idComentarioProyecto) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyecto
   * @param origenComentario
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoProyecto(Long idCompromisoProyecto, String origenComentario) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoImplementacion
   * @param origenComentario
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(Long idCompromisoImplementacion, String origenComentario) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorCompromisoProyecto(Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(Long idCompromisoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyecto
   * @param idCompromisoImplementacion
   * @param origenComentario
   * @param idItemSeleccionado
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioCompromisoProyecto> getListaComentarioCompromisoProyectoPorIdCompromisoProyectoItemSeleccionado(Long idCompromisoProyecto, Long idCompromisoImplementacion, String origenComentario, Long idItemSeleccionado) throws JpaDinaeException;

  /**
   *
   * @param comentarioCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  ComentarioCompromisoProyecto guardarCompromisoProyecto(ComentarioCompromisoProyecto comentarioCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idComentarioCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  ComentarioCompromisoProyecto obtenerComentariCompromisoProyecto(Long idComentarioCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyecto
   * @param idCompromisoImplementacion
   * @param origenComentario
   * @return
   * @throws JpaDinaeException
   */
  List<ComentarioProyectoDTO> getComentarioCompromisoProyectoPorCompromiso(Long idCompromisoProyecto, Long idCompromisoImplementacion, String origenComentario) throws JpaDinaeException;

}
