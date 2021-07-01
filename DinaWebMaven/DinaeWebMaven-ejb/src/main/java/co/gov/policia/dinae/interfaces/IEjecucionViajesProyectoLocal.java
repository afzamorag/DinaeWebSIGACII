/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EjecucionEventosProyecto;
import co.gov.policia.dinae.modelo.EjecucionViajesProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IEjecucionViajesProyectoLocal {

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionViajesProyecto> findViajesRelacionadoProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @param idViajeProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionViajesProyecto findViajesRelacionadoInformeProyecto(Long idInformeAvance, Long idViajeProyecto) throws JpaDinaeException;

  /**
   *
   * @param ejecucionViajesProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionViajesProyecto saveOrUpdate(EjecucionViajesProyecto ejecucionViajesProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  void deleteViajesRelacionadoInformeProyecto(Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionViajesProyecto> findViajesRelacionadoImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idViajeProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionViajesProyecto findViajesRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idViajeProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @throws JpaDinaeException
   */
  void deleteViajesRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException;

}
