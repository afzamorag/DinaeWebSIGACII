package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ISemilleroProyectoLocal {

  /**
   *
   * @param idSemilleroProyecto
   * @return
   * @throws JpaDinaeException
   */
  SemilleroProyecto obtenerSemilleroProyectoPorId(Long idSemilleroProyecto) throws JpaDinaeException;

  /**
   *
   * @param idSemilleroProyecto
   * @return
   * @throws JpaDinaeException
   */
  SemilleroProyecto guardarSemilleroProyecto(SemilleroProyecto idSemilleroProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<SemilleroProyecto> getListaSemilleroProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idSemilleroInvestigacion
   * @throws JpaDinaeException
   */
  void eliminarSemilleroProyectoInvestigacion(Long idSemilleroInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroProyecto> findAllBySemilleroInvestigacion(Long idSemillero) throws JpaDinaeException;
}
