package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.TemaProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ITemaProyectoLocal {

  /**
   *
   * @param idTemaProyecto
   * @return
   * @throws JpaDinaeException
   */
  TemaProyecto obtenerTemaProyectoPorId(Long idTemaProyecto) throws JpaDinaeException;

  /**
   *
   * @param idTemaProyecto
   * @return
   * @throws JpaDinaeException
   */
  TemaProyecto guardarTemaProyecto(TemaProyecto idTemaProyecto) throws JpaDinaeException;

  /**
   *
   * @param idTemaProyecto
   * @param idEstadoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  TemaProyecto guardarTemaProyectoYactualizarEstadoPropuestaConvocatoria(TemaProyecto idTemaProyecto, Long idEstadoConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idTema
   * @return
   * @throws JpaDinaeException
   */
  List<TemaProyecto> getListaTemaProyectoPorTema(Long idTema) throws JpaDinaeException;

  /**
   *
   * @param idTema
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  TemaProyecto getTemaProyectoPorTemaYproyecto(Long idTema, Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idTema
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  TemaProyecto getTemaProyectoPorTemaEinformeAvanceImplementacion(Long idTema, Long idInformeAvanceImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<Long> getIDTemaProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param tipoInformacion
   * @return
   * @throws JpaDinaeException
   */
  List<Long> getIDTemaProyectoPorInformeAvanceImplementacion(Long idInformeAvanceImplementacion, String tipoInformacion) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<TemaProyecto> getListaTemaProyectoPorInformeAvanceImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  int cuentaTemaProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @param tipoTabInformacion
   * @return
   * @throws JpaDinaeException
   */
  int cuentaTemaProyectoPorInformeAvanceYtipoTemaTabInformacion(Long idInformeAvance, String tipoTabInformacion) throws JpaDinaeException;

}
