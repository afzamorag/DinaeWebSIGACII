/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EjecucionEventosProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IEjecucionEventosProyectoLocal {

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionEventosProyecto> findEventoRelacionadoProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionEventosProyecto findEventoRelacionadoInformeProyecto(Long idInformeAvance, Long idEventoProyecto) throws JpaDinaeException;

  /**
   *
   * @param ejecucionEventosProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionEventosProyecto saveOrUpdate(EjecucionEventosProyecto ejecucionEventosProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  void deleteEventoRelacionadoInformeProyecto(Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionEventosProyecto> findEventoRelacionadoImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionEventosProyecto findEventoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idEventoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @throws JpaDinaeException
   */
  void deleteEventoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException;

}
