/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IResumenPresupuestoEjecutadoLocal {

  /**
   * Method that allows to execute the stored procedure PKG_CALCULO_PRESUPUESTO.PRC_CALCULAR_PRES_EJECUTA to calculate </br>
   * the project executed budget
   *
   * @param idProyecto
   * @param idInformeAvance
   * @throws JpaDinaeException
   */
  void executeStoredProcedure(Long idProyecto, Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoEjecutado> findByProyectoInformeAvance(Long idProyecto, Long idInformeAvance) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoEjecutado> findByProyectoInformeAvanceAcum(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoEjecutado> findByProyectoInformeAvanceAcumFinal(Long idProyecto, Long idInformeAvance) throws JpaDinaeException;

  /**
   * Method that allows to execute the stored procedure PKG_CALCULO_PRESUPUESTO.PRC_CALCULAR_PRES_ACUM to calculate </br>
   * the project accumulated budget
   *
   * @param idProyecto
   * @throws JpaDinaeException
   */
  void executeStoredProcedureAcum(Long idProyecto) throws JpaDinaeException;

}
