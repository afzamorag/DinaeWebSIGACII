/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EjecucionOtrosGastosProy;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IEjecucionOtrosGastosProyLocal {

  /**
   *
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionOtrosGastosProy> findOtrosGastosInforme(Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @param idOtrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionOtrosGastosProy findOtrosGastosInformeProyecto(Long idInformeAvance, Long idOtrosGastosProyecto) throws JpaDinaeException;

  /**
   *
   * @param ejecucionOtrosGastosProy
   * @return
   * @throws JpaDinaeException
   */
  EjecucionOtrosGastosProy saveOrUpdate(EjecucionOtrosGastosProy ejecucionOtrosGastosProy) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  void deleteOtrosGastosInformeProyecto(Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<EjecucionOtrosGastosProy> findOtrosGastosInformeImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idOtrosGastosProyecto
   * @return
   * @throws JpaDinaeException
   */
  EjecucionOtrosGastosProy findOtrosGastosInformeImplementacionProyecto(Long idInformeAvanceImplementacion, Long idOtrosGastosProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @throws JpaDinaeException
   */
  void deleteOtrosGastosInformeImplementacionProyecto(Long idInformeAvanceImplementacion) throws JpaDinaeException;

}
