/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.OtrosGastosProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.OtrosGastosProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IOtrosGastosProyectoLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  public List<OtrosGastosProyecto> findAll() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<OtrosGastosProyecto> findOtrosGastosByProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idOtrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  public OtrosGastosProyecto findById(Long idOtrosGastosProyecto) throws JpaDinaeException;

  /**
   *
   * @param otrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  public OtrosGastosProyecto saveOrUpdate(OtrosGastosProyecto otrosGastosProyecto) throws JpaDinaeException;

  /**
   *
   * @param _otrosGastosProyecto
   * @throws JpaDinaeException
   */
  public void delete(OtrosGastosProyecto _otrosGastosProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idTipoRubro
   * @return
   * @throws JpaDinaeException
   */
  public OtrosGastosProyectoDTO contarOtrosGastosRubro(Long idProyecto, Long idTipoRubro) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<OtrosGastosProyectoDTO> findOtrosGastosByProyectoDTO(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<OtrosGastosProyectoDTO> findOtrosGastosByPlanTrabajoDTO(Long idPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @param idTipoRubro
   * @return
   * @throws JpaDinaeException
   */
  OtrosGastosProyectoDTO contarOtrosGastosRubroPlanTrabajo(Long idPlanTrabajo, Long idTipoRubro) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<OtrosGastosProyecto> findOtrosGastosByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException;

}
