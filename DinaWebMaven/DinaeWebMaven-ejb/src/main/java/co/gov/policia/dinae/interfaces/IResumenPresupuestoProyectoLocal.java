/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ResumenPresupuestoProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IResumenPresupuestoProyectoLocal {

  /**
   * Find All
   *
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoProyecto> findAll() throws JpaDinaeException;

  /**
   * Remove all records.
   *
   * @param idProyecto
   * @throws JpaDinaeException
   */
  void removeAllByProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   * Method that allows to execute the stored procedure PKG_CALCULO_PRESUPUESTO.PRC_CREAR_ACTUALIZA_PRES to calculate </br>
   * the project budget
   *
   * @param idProyecto
   * @throws JpaDinaeException
   */
  void executeStoredProcedure(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param estadoDescripcion
   * @return
   * @throws JpaDinaeException
   */
  List<ResumenPresupuestoProyecto> findByProyecto(Long idProyecto, String estadoDescripcion) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @throws JpaDinaeException
   */
  void executeStoredProcedureJDBC(Long idProyecto) throws JpaDinaeException;
}
