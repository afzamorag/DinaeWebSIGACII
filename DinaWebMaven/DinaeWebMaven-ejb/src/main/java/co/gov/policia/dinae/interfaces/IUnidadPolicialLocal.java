package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface IUnidadPolicialLocal {

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  UnidadPolicial obtenerUnidadPolicialPorId(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param listaTipoUnidad
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicial> getTipoUnidadPolicialByTipoUnidad(List<Long> listaTipoUnidad) throws JpaDinaeException;

  /**
   *
   * @param unidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  UnidadPolicial atualizarUnidadPolicial(UnidadPolicial unidadPolicial) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<UnidadPolicialDTO> getUnidadPolicial() throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicialExcepto
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicial> getUnidadPolicialExcepto(Long idUnidadPolicialExcepto) throws JpaDinaeException;

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicial> getUnidadPolicialDiferenteAEjecutorPropuestaUnidad(Long idPropuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param siglaFisica
   * @return
   * @throws JpaDinaeException
   */
  UnidadPolicialDTO obtenerUnidadPolicialDTOPorCodigoLaboral(String siglaFisica) throws JpaDinaeException;

  /**
   * Permite obtener todas las unidades policiales
   *
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<UnidadPolicial> getAllUnidadesPolicialesActivas() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<UnidadPolicial> getAllUnidadesPolicialesActivasOrdenAlfabetico() throws JpaDinaeException;

  /**
   * Permite obtener todas las unidades policiales
   *
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<UnidadPolicialDTO> getAllUnidadesPolicialesActivasDTO() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<UnidadPolicial> getTodasUnidadesActivasInactivas() throws JpaDinaeException;

  /**
   *
   * @param filtroBusqueda
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicial> getTodasUnidadesActivasInactivas(String filtroBusqueda) throws JpaDinaeException;

  /**
   *
   * @param idTipoUnidad
   * @return
   * @throws JpaDinaeException
   */
  int cuentaUnidadesActivasPorTipo(Long idTipoUnidad) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @param origen
   * @return
   * @throws JpaDinaeException
   */
  UnidadPolicial getUnidadPolicialPorIdentificacionOinvestigador(String identificacion, String origen) throws JpaDinaeException;

}
