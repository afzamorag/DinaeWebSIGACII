package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.FuenteProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Javier Peña Barranco
 * @since 2013/11/19
 */
@Local
public interface IFuenteProyectoLocal {

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  int contarFuentesBaseProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  int contarFuentesNOBaseProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<String> getListaFuentesNoBase(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  int contarFuentesBaseProyectoByIdProyectoYUnidad(Long idProyecto, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idFuenteProyecto
   * @return
   * @throws JpaDinaeException
   */
  public FuenteProyecto getFuenteFinancieraById(Long idFuenteProyecto) throws JpaDinaeException;

  /**
   *
   * @param fuenteProyecto
   * @throws JpaDinaeException
   */
  public void addFuenteFinanciera(FuenteProyecto fuenteProyecto) throws JpaDinaeException;

  /**
   *
   * @param fuenteProyecto
   * @throws JpaDinaeException
   */
  public void updateFuenteFinanciera(FuenteProyecto fuenteProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<FuenteProyecto> getListaFuentesPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   * saveOrUpdate
   *
   * @param fuenteProyecto
   * @return
   * @throws JpaDinaeException
   */
  FuenteProyecto saveOrUpdate(FuenteProyecto fuenteProyecto) throws JpaDinaeException;

  /**
   * saveOrUpdate
   *
   * @param fuenteProyecto
   * @return
   * @throws JpaDinaeException
   */
  void delete(FuenteProyecto fuenteProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<FuenteProyectoDTO> getListaFuentesPorProyectoDTO(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  public List<FuenteProyectoDTO> findFuentesByPlanTrabajoImpl(Long idPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<FuenteProyecto> findFuentesByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException;
}
