package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.SemilleroInvestigacionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.SemilleroInvestigacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ISemilleroInvestigacionLocal {

  /**
   *
   * @param idSemilleroInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  SemilleroInvestigacion obtenerSemilleroInvestigacionPorId(Long idSemilleroInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param idSemilleroInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  SemilleroInvestigacion guardarSemilleroInvestigacion(SemilleroInvestigacion idSemilleroInvestigacion) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<SemilleroInvestigacion> getListaSemilleroInvestigacionTodos() throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroInvestigacion> getListaSemilleroInvestigacionPorUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroInvestigacionDTO> getListaSemilleroInvestigacionDTOPorUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param criterios
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<SemilleroInvestigacionDTO> buscarSemilleroCriterios(Map<String, Object> criterios) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<SemilleroInvestigacionDTO> findAllDTO() throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  SemilleroInvestigacionDTO findByIdSemillero(Long idSemillero) throws JpaDinaeException;
}
