/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ResumenPresupuestoImpl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IResumenPresupuestoImplLocal {

  /**
   * Find All
   *
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoImpl> findAll() throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoImpl> findByProyectoImpl(Long idImplementacionProy, Long idPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @param idPlanTrabajo
   * @param estadoPresupuesto
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoImpl> findByProyectoImplEstadoPresepuesto(Long idImplementacionProy, Long idPlanTrabajo, String estadoPresupuesto) throws JpaDinaeException;

  /**
   *
   * @param idImplementacionProy
   * @param idPlanTrabajo
   * @throws JpaDinaeException
   */
  void removeAllByProyectoImpl(Long idImplementacionProy, Long idPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @param idImplementacionProy
   * @throws JpaDinaeException
   */
  void calcularPresupuestoImpl(Long idPlanTrabajo, Long idImplementacionProy) throws JpaDinaeException;

}
