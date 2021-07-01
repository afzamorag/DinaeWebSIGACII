package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.CompromisoProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public interface ICompromisoProyectoLocal {

  /**
   *
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  CompromisoProyecto obtenerCompromisoProyecto(Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyectoActual
   * @param idTipoCompromiso
   * @return
   * @throws JpaDinaeException
   */
  CompromisoProyecto obtenerCompromisoProyectoAnteriorTipoAvance(Long idCompromisoProyectoActual, Long idTipoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param compromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  CompromisoProyecto agregarCompromisoProyecto(CompromisoProyecto compromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param listaIdEstadoCompromiso
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyectoDTO> getCompromisoProyectoDTOPorUnidadPolicial(Long idUnidadPolicial, List<Long> listaIdEstadoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param listaIdEstadoCompromiso
   * @param codigoProyectoInstitucional
   * @param codigoProyectoConvocatoria
   * @param idUnidadPolicialSeleccionado
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyectoDTO> getCompromisoProyectoDTOEnvidosAvicinporListaEstado(List<Long> listaIdEstadoCompromiso, String codigoProyectoInstitucional, String codigoProyectoConvocatoria, Long idUnidadPolicialSeleccionado) throws JpaDinaeException;

  /**
   *
   * @param listaCompromisoProyectoDTO
   * @throws JpaDinaeException
   */
  void enviarVicinListCompromisoProyectoDTO(List<CompromisoProyectoDTO> listaCompromisoProyectoDTO) throws JpaDinaeException;

  /**
   *
   * @param listaCompromisoProyectoDTO
   * @param identificacion
   * @param usuario
   * @param nombreCompleto
   * @param idUsuarioRol
   * @throws JpaDinaeException
   */
  void enviarGrupoInvestigacionListCompromisoProyectoDTO(List<CompromisoProyectoDTO> listaCompromisoProyectoDTO, String identificacion, String usuario, String nombreCompleto, Long idUsuarioRol) throws JpaDinaeException;

  /**
   *
   * @param listaCompromisoProyectoDTO
   * @throws JpaDinaeException
   */
  void guardarListCompromisoProyectoDTO(List<CompromisoProyectoDTO> listaCompromisoProyectoDTO) throws JpaDinaeException;

  /**
   *
   * @param fechaRetroalimentacion
   * @param idResultadoRetroalimenta
   * @param idComprimiso
   * @param origenCompromiso
   * @throws JpaDinaeException
   */
  void actualizarResultadoRetroalimentacionCompromisoProyectoEimplementacion(Date fechaRetroalimentacion, Long idResultadoRetroalimenta, Long idComprimiso, String origenCompromiso) throws JpaDinaeException;

  /**
   *
   * @param fechaRetroalimentacion
   * @param idResultadoRetroalimenta
   * @param idComprimiso
   * @param origenCompromiso
   * @param idEstadoCompromiso
   * @throws JpaDinaeException
   */
  void actualizarCompromisoProyectoEimplementacionResultadoRetroalimentacionEnviarCompromisoUnidadPolicial(Date fechaRetroalimentacion, Long idResultadoRetroalimenta, Long idComprimiso, String origenCompromiso, Long idEstadoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param fechaRetroalimentacion
   * @param idResultadoRetroalimenta
   * @param idComprimiso
   * @param origenCompromiso
   * @param idEstadoCompromiso
   * @throws JpaDinaeException
   */
  void actualizarCompromisoProyectoEimplementacionResultadoRetroalimentacionAceptarCumplimiento(Date fechaRetroalimentacion, Long idResultadoRetroalimenta, Long idComprimiso, String origenCompromiso, Long idEstadoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param listaIdEstadoCompromiso
   * @param idListaItemTiposProyectosSeleccionado
   * @param idListaItemConvocatoriasSeleccionado
   * @param idListaItemAnioSeleccionadoIdPeriodo
   * @param idListaItemTipoCompromisoSeleccionado
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyectoDTO> getListaUnidadPolicialNohanPresentadoPropuestaCompromisoEimplementacion(
          List<Long> listaIdEstadoCompromiso,
          Long idListaItemTiposProyectosSeleccionado,
          Long idListaItemConvocatoriasSeleccionado,
          Long idListaItemAnioSeleccionadoIdPeriodo,
          Long idListaItemTipoCompromisoSeleccionado) throws JpaDinaeException;

  /**
   *
   * @param compromisoProyecto
   */
  void actualizarFechaUltimaProyectoListener(CompromisoProyecto compromisoProyecto);

  /**
   *
   * @param idProyecto
   * @param idListaEstadoCompromiso
   * @return
   * @throws JpaDinaeException
   */
  int contarCompromisoroyectoPorProyectoYlistaEstado(Long idProyecto, List<Long> idListaEstadoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyectoActual
   * @return
   * @throws JpaDinaeException
   */
  List<Long> obtenerListaIdCompromisoProyectoAnterioresSoloInformeAvance(Long idCompromisoProyectoActual) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param fechaActual
   * @param estados
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyecto> findAllCompromisosVigentes(Long idUnidadPolicial, Date fechaActual, Long[] estados) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param idProyecto
   * @param fechaActual
   * @param fechaMes
   * @param estados
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyecto> findAllCompromisosVigentesMesProyecto(Long idUnidadPolicial, Long idProyecto, Date fechaActual, Date fechaMes, Long[] estados) throws JpaDinaeException;

  /**
   *
   * @param prefijoCodigoProyecto
   * @param estados
   * @return
   * @throws JpaDinaeException
   */
  int findAllCompromisosEnviadosVicin(String prefijoCodigoProyecto, Long[] estados) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoPRoyecto
   * @return
   * @throws JpaDinaeException
   */
  Long getIdTipoCompromisoProyectoPorCompromisoProyecto(Long idCompromisoPRoyecto) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param estados
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyecto> findAllCompromisosVigentesJefeUnidad(Long idUnidadPolicial, Long[] estados) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idEstadoCompromiso
   * @param idTipoCompromiso
   * @return
   * @throws JpaDinaeException
   */
  Long getIdCompromisoProyectoFinalPorProyectoYtipoCompromisoYEstadoCompromiso(Long idProyecto, Long idEstadoCompromiso, Long idTipoCompromiso) throws JpaDinaeException;

  /**
   * @param origenCompromiso
   * @param idCompromiso
   */
  void ejecutarProcedimientoCompromisoCumplido(String origenCompromiso, Long idCompromiso) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  Long getEstadoCompromisoProyectoPorIdInformeAvance(Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idTipoProyecto
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoProyectoDTO> getCompromisoProyectosCorreccionPorTipoProyectoYUnidad(Long idTipoProyecto, Long idUnidadPolicial) throws JpaDinaeException;

}
