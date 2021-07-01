package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.PeriodoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.TipoUnidad;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IPeriodoLocal {

  List<Periodo> getPeriodosNecesidades() throws JpaDinaeException;

  /**
   * Se conculta la lista de los periodos en los cuales la fecha actual se encuentra entre la fecha de inicio y fecha fin del periodo (periodo de apertura) y está habilitada para la unidad del usuario
   * para este cronograma.
   *
   * @param fechaActual
   * @param idUnidadPolicial
   * @param estadoPeriodo
   * @return lista de periodos
   * @throws JpaDinaeException
   */
  List<Periodo> getPeriodosFechaActualEntreFechaInicioYfechaFin(Date fechaActual, Long idUnidadPolicial, Character estadoPeriodo) throws JpaDinaeException;

  /**
   *
   * @param fechaActual
   * @param idUnidadPolicial
   * @param idEstadoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<Periodo> getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoria(Date fechaActual, Long idUnidadPolicial, Long idEstadoConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param fechaActual
   * @param idUnidadPolicial
   * @param idEstadoConvocatoria
   * @param idTipoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<Periodo> getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(Date fechaActual, Long idUnidadPolicial, Long idEstadoConvocatoria, Long idTipoConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param idEstadoConvocatoria
   * @param idTipoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<Periodo> getPeriodosUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(Long idUnidadPolicial, Long idEstadoConvocatoria, Long idTipoConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param anio
   * @param tipoUnidad
   * @param descripcion
   * @param fechaInicio
   * @param fechafina
   * @param cantidad
   * @param estado
   * @param listaUnidadPolicialDTO
   * @return
   * @throws JpaDinaeException
   */
  Periodo guardarPeriodo(Integer anio, List<TipoUnidad> tipoUnidad, String descripcion, Date fechaInicio, Date fechafina, Integer cantidad, Character estado, List<UnidadPolicialDTO> listaUnidadPolicialDTO) throws JpaDinaeException;

  /**
   *
   * @param estado
   * @return
   * @throws JpaDinaeException
   */
  List<Periodo> getPeriodosPorEstado(Character estado) throws JpaDinaeException;

  /**
   *
   * @param fechaActual
   * @param estado
   * @return
   * @throws JpaDinaeException
   */
  List<Periodo> getPeriodosPorEstadoYFechaActualEntreFechaInicioYfechaFin(Date fechaActual, Character estado) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<Long> getIdsUnidadesPolicialesPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param periodo
   * @param listaUnidadPolicialDTO
   * @throws JpaDinaeException
   */
  void modificarPeriodio(Periodo periodo, List<UnidadPolicialDTO> listaUnidadPolicialDTO) throws JpaDinaeException;

  /**
   *
   * @param convocatoria
   * @return
   * @throws JpaDinaeException
   */
  Periodo guardarConvocatoria(Periodo convocatoria) throws JpaDinaeException;

  /**
   *
   * @param idTipoConvocatoria
   * @param listaIdEstado Filtro de estados, Si la lista es vacia o nulo no se aplica el filtro de estados
   * @return
   * @throws JpaDinaeException
   */
  List<PeriodoDTO> getListaConvocatoriasPorTipoConvocatoria(Long idTipoConvocatoria, List<Long> listaIdEstado) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  Periodo getPeriodoPorId(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<Periodo> findConvocaEnsayoCriticoAbiertaPublicada() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<Periodo> getListaConvocatoriasParaBusquedaProyectos() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<Periodo> getTodosAniosHabilitadosParaProyectosInstitucionales() throws JpaDinaeException;

  /**
   *
   * @param criterios
   * @return
   * @throws JpaDinaeException
   */
  public List<PeriodoDTO> getListaConvocatoriasPorTipoConvocatoriaFiltros(Map<String, Object> criterios) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param fechaActual
   * @param estadoPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<Periodo> findAllConvocatoriasByFechaActualEntreFechaInicioYfechaFinYestado(Long idUnidadPolicial, Date fechaActual, Character estadoPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  int getNumeroPropuestasNecesidadesDashBoard(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @param nombreFisicoArchivo
   * @param nombreOriginalArchivo
   * @throws JpaDinaeException
   */
  void actualizarConvocatoriaReporteEnsayoCritico(Long idConvocatoria, String nombreFisicoArchivo, String nombreOriginalArchivo) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @param nombreFisicoArchivo
   * @param nombreOriginalArchivo
   * @throws JpaDinaeException
   */
  void actualizarConvocatoriaReportePropuestaConvocatoria(Long idConvocatoria, String nombreFisicoArchivo, String nombreOriginalArchivo) throws JpaDinaeException;
}
