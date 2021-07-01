/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ViajesProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ViajesProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IViajesProyectoLocal {

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<ViajesProyecto> findViajesByProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param viajesProyecto
   * @return
   * @throws JpaDinaeException
   */
  public ViajesProyecto saveOrUpdate(ViajesProyecto viajesProyecto) throws JpaDinaeException;

  /**
   *
   * @param idViajesProyecto
   * @return
   * @throws JpaDinaeException
   */
  public ViajesProyecto findById(Long idViajesProyecto) throws JpaDinaeException;

  /**
   *
   * @param _viajesProyecto
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  void delete(ViajesProyecto _viajesProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<ViajesProyectoDTO> findViajesByProyectoDTO(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<ViajesProyectoDTO> findViajesByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException;

}
