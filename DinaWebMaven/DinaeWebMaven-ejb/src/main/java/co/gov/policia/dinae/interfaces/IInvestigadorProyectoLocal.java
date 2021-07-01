package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.InvestigacionDesarrollada;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IInvestigadorProyectoLocal {

  /**
   *
   * @param idInvestigadorProyecto
   * @return
   * @throws JpaDinaeException
   */
  InvestigadorProyecto obtenerInvestigadorProyectoPorId(Long idInvestigadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInvestigadorProyecto
   * @return
   * @throws JpaDinaeException
   */
  InvestigadorProyecto guardarInvestigadorProyecto(InvestigadorProyecto idInvestigadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInvestigadorProyecto
   * @throws JpaDinaeException
   */
  void eliminarInvestigadorProyecto(Long idInvestigadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyectoDTO> getListaInvestigadorProyectoDTOPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param listaInvestigadorProyectoDTO
   * @param idCompromisoProyecto
   * @throws JpaDinaeException
   */
  void guardarHorasInvestigacionPeriodoLista(List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTO, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param listaInvestigadorProyecto
   * @throws JpaDinaeException
   */
  void guardarHorasInvestigacionLista(List<InvestigadorProyecto> listaInvestigadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  int cuentaInvestigadorProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  InvestigadorProyecto getInvestigadorProyectoByIdentificacion(String identificacion)
          throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoTipoInvestigadorPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoTipoAsesorPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoByIdentificacion(String numIdentificacion)
          throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idTipoVinculacion
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoPorProyectoYtipoVinculacion(Long idProyecto, Long idTipoVinculacion) throws JpaDinaeException;

  /**
   *
   * @param idPlantaTrabajoImpl
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoPorPlanTrabajoImpl(Long idPlantaTrabajoImpl) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws Exception
   */
  Double getSumaCalculoHorasTotalInvestigadorProyecto(Long idProyecto) throws Exception;

  /**
   *
   * @param idEventoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoPorEventoInvestigacion(Long idEventoInvestigacion) throws JpaDinaeException;

}
