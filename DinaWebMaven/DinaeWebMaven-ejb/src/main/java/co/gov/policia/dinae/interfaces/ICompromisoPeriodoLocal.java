package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.CompromisoDTO;
import co.gov.policia.dinae.dto.CompromisoPeriodoDTO;
import co.gov.policia.dinae.dto.ValidacionCompromisoPeriodoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ICompromisoPeriodoLocal {

  /**
   *
   * @param listaHitoPeriodos
   * @param identificacion
   * @throws JpaDinaeException
   */
  void agregarCompromisoPeriodo(List<CompromisoPeriodo> listaHitoPeriodos, String identificacion) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoPeriodo> buscarCompromisoPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idTipoCompromiso
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoPeriodo> buscarCompromisoPeriodoPorPeriodoYTipoCompromiso(Long idPeriodo, Long idTipoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idTipoCompromiso
   * @return
   * @throws JpaDinaeException
   */
  int contarCompromisoPeriodoPorPeriodoYTipoCompromiso(Long idPeriodo, Long idTipoCompromiso) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoDTO> getListaCompromisosProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoPeriodoDTO> getListaCompromisoPeriodoDTOporIdPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoPeriodoDTO> getListaCompromisoPeriodoDTOporConvocatoriaPeriodo(Long idConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoPeriodo> getListaCompromisoPeriodoPorConvocatoriaConvocatoria(Long idConvocatoria) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<CompromisoPeriodo> getListaCompromisoPeriodoPorIdPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param compromisoPeriodo
   * @return
   * @throws JpaDinaeException
   */
  CompromisoPeriodo guardarCompromisoPeriodo(CompromisoPeriodo compromisoPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoPeriodo
   * @return
   * @throws JpaDinaeException
   */
  CompromisoPeriodo obtenerCompromisoPeriodoPorId(Long idCompromisoPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param fechaActualCompromisoPeriiodo
   * @return
   * @throws JpaDinaeException
   */
  CompromisoPeriodo getFechaConcecuenteCompromisoPeriodo(Long idPeriodo, Date fechaActualCompromisoPeriiodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<ValidacionCompromisoPeriodoDTO> conteoValidacionComprimisosPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param compromisoPeriodo
   * @throws JpaDinaeException
   */
  void eliminarCompromisoPeriodo(CompromisoPeriodo compromisoPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  int contarCompromisoPeriodoPorPeriodo(Long idPeriodo) throws JpaDinaeException;
  
  /**
   * 
   * @param propuestaNecesidad
   * @return
   * @throws JpaDinaeException 
   */
  List<CompromisoPeriodo> buscarCompromisoPeriodoByIdPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) throws JpaDinaeException;
  
  List<CompromisoPeriodo> buscarCompromisoPeriodoByIdPropuestaNecesidadAndTipoCompromiso(PropuestaNecesidad propuestaNecesidad, Constantes tipoCompromiso) throws JpaDinaeException;
}
