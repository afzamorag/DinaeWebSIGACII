/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutadoImpl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IResumenPresupuestoEjecutadoImplLocal {

  /**
   *
   * @param idImplementacionProy
   * @param idInformeAvanceImpl
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoEjecutadoImpl> findByProyectoInformeAvance(Long idImplementacionProy, Long idInformeAvanceImpl) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoEjecutadoImpl> findByProyectoInformeAvanceAcum(Long idImplementacionProy) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @param idInformeAvanceImpl
   * @throws JpaDinaeException
   */
  void calcularPresupuestoEjecutadoImpl(Long idImplementacionProy, Long idInformeAvanceImpl) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @throws JpaDinaeException
   */
  void calcularPresupuestoEjecutadoAcumImpl(Long idImplementacionProy) throws JpaDinaeException;
}
