/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EjecucionEquiposProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IEjecucionEquiposProyectoLocal {

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionEquiposProyecto> findEquipoRelacionadoProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @param idEquipoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  EjecucionEquiposProyecto findEquipoRelacionadoInformeProyecto(Long idInformeAvance, Long idEquipoInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param ejecucionEquiposProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionEquiposProyecto saveOrUpdate(EjecucionEquiposProyecto ejecucionEquiposProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  void deleteEquipoRelacionadoInformeProyecto(Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionEquiposProyecto> findEquipoRelacionadoImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idEquipoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  EjecucionEquiposProyecto findEquipoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idEquipoInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @throws JpaDinaeException
   */
  void deleteEquipoRelacionadoInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException;
}
