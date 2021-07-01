package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.HistorialEstadoProyectosMigradosDTO;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EvaluadoresProyectoMigrados;
import co.gov.policia.dinae.modelo.Proyecto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IProyectoLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  int contarTodosLosProyecto() throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  int contarTodosLosProyectoPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  int contarTodosLosProyectoPorConvocatoria(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  Proyecto obtenerProyectoPorId(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param proyecto
   * @return
   * @throws JpaDinaeException
   */
  Proyecto guardarProyecto(Proyecto proyecto) throws JpaDinaeException;

  /**
   *
   * @param listaIdsConvocatoria
   * @param idEstadoActalizar
   * @throws JpaDinaeException
   */
  void actualizarEstadoListaIdProyecto(List<Long> listaIdsConvocatoria, Long idEstadoActalizar) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<Proyecto> getListaProyectoPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIdEstadoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<Proyecto> getListaProyectoPorPeriodoYestadoYestado(Long idPeriodo, List<Long> listaIdEstadoConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoDTOPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIdEstadoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoDTOPorPeriodoYestado(Long idPeriodo, List<Long> listaIdEstadoConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIdEstadoConvocatoria
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoDTOPorPeriodoYestadoYunidadPolicial(Long idPeriodo, List<Long> listaIdEstadoConvocatoria, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @param idUnidadPolicial
   * @param listaIdEstadoPropuestaConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  int cuentaNumeroPropuestasConvocatoriaPorConvocatoriaYunidadPolicialYListaIdEstado(Long idConvocatoria, Long idUnidadPolicial, List<Long> listaIdEstadoPropuestaConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  int cuentaNumeroPropuestasConvocatoriaPorConvocatoriaYunidadPolicial(Long idConvocatoria, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @param idUnidadPolicial
   * @param listaIdEstadoPropuestaConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<Proyecto> getPropuestasConvocatoriaPorConvocatoriaYunidadPolicialYListaIdEstado(Long idConvocatoria, Long idUnidadPolicial, List<Long> listaIdEstadoPropuestaConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param propuestaConvocaoriaProyecto
   * @throws JpaDinaeException
   */
  void enviarPropuestaConvocatoriaVicin(List<Proyecto> propuestaConvocaoriaProyecto) throws JpaDinaeException;

  /**
   *
   * @param propuestaConvocaoriaProyecto
   * @param identificacion
   * @param nombreCompleto
   * @throws JpaDinaeException
   */
  void enviarPropuestaConvocatoriaAgrupoInvestigacion(List<Proyecto> propuestaConvocaoriaProyecto, String identificacion, String nombreCompleto) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicialDTO> getListaUnidadesPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIsEstadoFiltro
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicialDTO> getListaUnidadesPorPeriodoYlistaIdestadoFiltro(Long idPeriodo, List<Long> listaIsEstadoFiltro) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYestadoFinanciarYnoAprobada(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIdEstado
   * @return
   * @throws JpaDinaeException
   */
  int contarProyectoPropuestaConvocatoriaDTOPorPeriodoYlistaEstado(Long idPeriodo, List<Long> listaIdEstado) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOaFinanciarPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIsEstadoFiltro
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOaFinanciarPorPeriodoYlistaIdEstado(Long idPeriodo, List<Long> listaIsEstadoFiltro) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYunidadPolicial(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param listaIsEstadoFiltro
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodo(Long idPeriodo, List<Long> listaIsEstadoFiltro) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @param listaIsEstadoFiltro
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYunidadPolicial(Long idPeriodo, Long idUnidadPolicial, List<Long> listaIsEstadoFiltro) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idEstado
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYestado(Long idPeriodo, Long idEstado) throws JpaDinaeException;

  /**
   *
   * @param listaPropuestasActualizar
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  void actualizarEstadoYpresupuestoPropuestasConvocatoriasFinancia(List<ProyectoDTO> listaPropuestasActualizar) throws JpaDinaeException;

  /**
   *
   * @param listaPropuestasActualizar
   * @param perfilUsuarioDTO
   * @return La lista de IDs de Unidad Policial involucradas en los proyectos a publicar
   * @throws JpaDinaeException
   */
  Set<Long> actualizarEstadoPropuestasConvocatoriasPublicar(List<ProyectoDTO> listaPropuestasActualizar, PerfilUsuarioDTO perfilUsuarioDTO) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param codigoProyecto
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<ProyectoDTO> getProyectosVigentesByCodigoProyecto(Long idUnidadPolicial, String codigoProyecto) throws JpaDinaeException;
  
   /**
   *
   * @param idUnidadPolicial
   * @param modalidad
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<ProyectoDTO> getProyectosVigentesByModalidad(Long idUnidadPolicial, Long modalidad) throws JpaDinaeException;

  /**
   *
   *
   * @return @throws JpaDinaeException
   */
  List<Proyecto> getProyectosByIdProgramaNotNull() throws JpaDinaeException;

  /**
   *
   * @param idPrograma
   * @return
   * @throws JpaDinaeException
   */
  List<Proyecto> getProyectosByIdPrograma(Long idPrograma) throws JpaDinaeException;

  /**
   *
   * @param estadosFiltro
   * @param palabraClave Palabra clave que hace parte del título del proyecto
   * @param unidadPolicial Código de la unidad policial
   * @param codigoProyecto Código del proyecto
   * @param fechaPresentacionEntre Fecha inicial de búsqueda del proyecto
   * @param fechaPresentacionY Fecha final de búsqueda del proyecto
   * @return Un List con los objetos Proyecto que cumplen con las condiciones especificadas
   * @throws JpaDinaeException Si ocurre un error al realizar la consulta
   */
  public List<ProyectoDTO> getProyectosEjecutadosByFiltros(Long[] estadosFiltro, String palabraClave,
          Long unidadPolicial, String codigoProyecto, Date fechaPresentacionEntre, Date fechaPresentacionY)
          throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public Proyecto getProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException;

  public List<Proyecto> getProyectosAsignadosInvestigadorPrincipalByIdentificacion(String identificacion) throws JpaDinaeException;

  public List<Proyecto> getAllProyectosTipoTrabajoDeGrado() throws JpaDinaeException;

  /**
   *
   * @param estadoEvaluado
   * @param codigoProyecto
   * @return
   * @throws JpaDinaeException
   */
  public Long validateProyectosByCodigoProyecto(Long estadoEvaluado, String codigoProyecto)
          throws JpaDinaeException;

  /**
   *
   * @param idPrograma
   * @param idEstado
   * @return
   * @throws JpaDinaeException
   */
  public List<Proyecto> getProyectosByIdProgramaAndIdEstado(Long idPrograma, Long idEstado) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @param idEstado
   * @return
   * @throws JpaDinaeException
   */
  public List<Proyecto> getProyectosAsignadosInvestigadorPrincipalByIdentificacionAndIdEstado(String identificacion, Long idEstado) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  public List<Proyecto> getProyectosTipoTrabajoDeGradoByIdUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param idEstado
   * @return
   * @throws JpaDinaeException
   */
  public List<Proyecto> getProyectosTipoTrabajoDeGradoByIdUnidadPolicialAndIdEstado(Long idUnidadPolicial, Long idEstado) throws JpaDinaeException;

  /**
   *
   * @param palabraClave
   * @param tiposProyectoSeleccionados
   * @param idUnidadPolicial
   * @param idTiposUnidadesSeleccionadas
   * @param areaCienciaTecnologiaSeleccionada
   * @param lineaSeleccionada
   * @param valorProyectoRangoInicial
   * @param valorProyectoRangoFinal
   * @param codigoProyecto
   * @param idEstadoProyecto
   * @param origenProyecto
   * @param idEstadoImplementacion
   * @param idNombreProgramaSeleccionado
   * @param fechaPresentacionInicial
   * @param fechaPresentacionFinal
   * @param notaFinalRangoInicial
   * @param notaFinalRangoFinal
   * @param idGrupoInvestigacion
   * @param idSemillero
   * @param nombresYApellidos
   * @param idConvocatoria
   * @param idFormasDeVer
   * @return
   * @throws JpaDinaeException
   */
  public List<Proyecto> getProyectosOTrabajosBusquedaPorFiltros(
          String palabraClave,
          List<Long> tiposProyectoSeleccionados,
          Long idUnidadPolicial,
          List<Long> idTiposUnidadesSeleccionadas,
          Long areaCienciaTecnologiaSeleccionada,
          Long lineaSeleccionada,
          BigDecimal valorProyectoRangoInicial,
          BigDecimal valorProyectoRangoFinal,
          String codigoProyecto,
          Long idEstadoProyecto,
          Long origenProyecto,
          Long idEstadoImplementacion,
          Long idNombreProgramaSeleccionado,
          Date fechaPresentacionInicial,
          Date fechaPresentacionFinal,
          BigDecimal notaFinalRangoInicial,
          BigDecimal notaFinalRangoFinal,
          Long idGrupoInvestigacion,
          Long idSemillero,
          String nombresYApellidos,
          Long idConvocatoria,
          Long idFormasDeVer) throws JpaDinaeException;

  public List<Proyecto> getProyectosOTrabajosBusquedaPorFiltros(
          String palabraClave,
          List<Long> tiposProyectoSeleccionados,
          Long idUnidadPolicial,
          List<Long> idTiposUnidadesSeleccionadas,
          Long areaCienciaTecnologiaSeleccionada,
          Long lineaSeleccionada,
          BigDecimal valorProyectoRangoInicial,
          BigDecimal valorProyectoRangoFinal,
          String codigoProyecto,
          List<Long> idEstadoProyecto,
          Long origenProyecto,
          List<Long> idEstadoImplementacion,
          Long idNombreProgramaSeleccionado,
          Date fechaPresentacionInicial,
          Date fechaPresentacionFinal,
          BigDecimal notaFinalRangoInicial,
          BigDecimal notaFinalRangoFinal,
          Long idGrupoInvestigacion,
          Long idSemillero,
          String nombresYApellidos,
          Long idConvocatoria,
          Long idFormasDeVer) throws JpaDinaeException;

  /**
   *
   * @param proyecto
   * @param idCompromisoProyecto
   * @param idEstado
   * @return
   * @throws JpaDinaeException
   */
  Proyecto guardarProyectoYCompromiso(Proyecto proyecto, Long idCompromisoProyecto, Long idEstado) throws JpaDinaeException;

  /**
   *
   * @param idEstado
   * @param idTipoProyecto
   * @param idUnidad
   * @param codigo
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> findAllProyectosInvestigacionFiltros(Long idEstado, Long idTipoProyecto, Long idUnidad, String codigo) throws JpaDinaeException;

  /**
   *
   * @param idEstado
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  int contarAllProyectosInvestigacionNoEvaluadosByPeriodo(Long idEstado, Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param listaIdEstadoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<Proyecto> getProyectoPorUnidadPolicialYListaIdEstado(Long idUnidadPolicial, List<Long> listaIdEstadoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  Proyecto getProyectoPorImplementacionProyecto(Long idImplementacionesProyecto) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  ProyectoDTO getProyectoDTOporImplementacionProyecto(Long idImplementacionProyecto) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param codigoProyecto
   * @param tipoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaProyectoDTOfiltroBusquedaPorUnidaPoliCodigoProTipoProyecto(Long idUnidadPolicial, String codigoProyecto, String tipoProyecto) throws JpaDinaeException;

  /**
   *
   * @param criterios
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getListaPropuestasProyectosInvestigacionFiltros(Map<String, Object> criterios) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idVersion
   * @return
   * @throws JpaDinaeException
   */
  ProyectoDTO getProyectoVersionPorIdProyecto(Long idProyecto, Long idVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @throws JpaDinaeException
   */
  void executeStoredProcedureCrearVersionProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> findAllPropuestaConvocatoriaPorConvocatoria(Long idConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param palabraClave
   * @param tiposProyectoSeleccionados
   * @param idUnidadPolicial
   * @param idTiposUnidadesSeleccionadas
   * @param idAreaCienciaTecnologiaSeleccionada
   * @param idLineaSeleccionada
   * @param valorProyectoRangoInicial
   * @param valorProyectoRangoFinal
   * @param codigoProyecto
   * @param idEstadoProyecto
   * @param idOrigenProyecto
   * @param idEstadoImplementacion
   * @param idNombreProgramaSeleccionado
   * @param fechaPresentacionInicial
   * @param fechaPresentacionFinal
   * @param notaFinalRangoInicial
   * @param notaFinalRangoFinal
   * @param idGrupoInvestigacion
   * @param idSemillero
   * @param nombresYApellidos
   * @param idConvocatoria
   * @param idFormasDeVer
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> getProyectosDTOOTrabajosBusquedaPorFiltros(String palabraClave,
          List<Long> tiposProyectoSeleccionados,
          Long idUnidadPolicial,
          List<Long> idTiposUnidadesSeleccionadas,
          Long idAreaCienciaTecnologiaSeleccionada,
          Long idLineaSeleccionada,
          BigDecimal valorProyectoRangoInicial,
          BigDecimal valorProyectoRangoFinal,
          String codigoProyecto,
          List<Long> idEstadoProyecto,
          Long idOrigenProyecto,
          List<Long> idEstadoImplementacion,
          Long idNombreProgramaSeleccionado,
          Date fechaPresentacionInicial,
          Date fechaPresentacionFinal,
          BigDecimal notaFinalRangoInicial,
          BigDecimal notaFinalRangoFinal,
          Long idGrupoInvestigacion,
          Long idSemillero,
          String nombresYApellidos,
          Long idConvocatoria,
          Long idFormasDeVer) throws JpaDinaeException;

  /**
   * @param tipoProyectoOtgrabajoGrado
   * @param palabraClave
   * @param tiposProyectoSeleccionados
   * @param idUnidadPolicial
   * @param idTiposUnidadesSeleccionadas
   * @param areaCienciaTecnologiaSeleccionada
   * @param lineaSeleccionada
   * @param valorProyectoRangoInicial
   * @param valorProyectoRangoFinal
   * @param codigoProyecto
   * @param idEstadoProyecto
   * @param origenProyecto
   * @param idEstadoImplementacion
   * @param idNombreProgramaSeleccionado
   * @param fechaPresentacionInicial
   * @param fechaPresentacionFinal
   * @param notaFinalRangoInicial
   * @param notaFinalRangoFinal
   * @param idGrupoInvestigacion
   * @param idSemillero
   * @param nombresYApellidos
   * @param idConvocatoria
   * @param idFormasDeVer
   * @return
   * @throws JpaDinaeException
   */
  public List<ProyectoDTO> getProyectosDTOOTrabajosBusquedaPorFiltros(
          String tipoProyectoOtgrabajoGrado,
          String palabraClave,
          List<Long> tiposProyectoSeleccionados,
          Long idUnidadPolicial,
          List<Long> idTiposUnidadesSeleccionadas,
          Long areaCienciaTecnologiaSeleccionada,
          Long lineaSeleccionada,
          BigDecimal valorProyectoRangoInicial,
          BigDecimal valorProyectoRangoFinal,
          String codigoProyecto,
          Long idEstadoProyecto,
          Long origenProyecto,
          Long idEstadoImplementacion,
          Long idNombreProgramaSeleccionado,
          Date fechaPresentacionInicial,
          Date fechaPresentacionFinal,
          BigDecimal notaFinalRangoInicial,
          BigDecimal notaFinalRangoFinal,
          Long idGrupoInvestigacion,
          Long idSemillero,
          String nombresYApellidos,
          Long idConvocatoria,
          Long idFormasDeVer) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @param estados
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> findAllPropuestaNecesidadConvocatoriaByPeriodoEstados(Long idPeriodo, Long idUnidadPolicial, Long[] estados) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoDTO> findAllProyectosInvestigacionVigentesUnidad(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  int obtenerUltimoConcecutivoTrabajoGrado() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<HistorialEstadoProyectosMigradosDTO> listaHistorialEstadoProyectosMigradosDTO(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EvaluadoresProyectoMigrados> listaEvaluadoresProyectoMigrados(Long idProyecto) throws JpaDinaeException;
  
  int contarProyectoByVigencia(List<Long> listaPropuestaNecesidad) throws JpaDinaeException;
}
