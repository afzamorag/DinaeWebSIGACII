/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.RecursoHumanoSemillero;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IRecursoHumanoSemilleroLocal {

  /**
   *
   * @param recursoHumanoSemillero
   * @return
   * @throws JpaDinaeException
   */
  RecursoHumanoSemillero saveOrUpdate(RecursoHumanoSemillero recursoHumanoSemillero) throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<RecursoHumanoSemillero> findAllBySemillero(Long idSemillero) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  public RecursoHumanoSemillero findByIdAndSemilleroId(String identificacion, Long idSemillero) throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @param fechaOtorgamiento
   * @return
   * @throws JpaDinaeException
   */
  List<RecursoHumanoSemillero> findBySemilleroAndEstadoActivoFecha(Long idSemillero, Date fechaOtorgamiento) throws JpaDinaeException;
}
