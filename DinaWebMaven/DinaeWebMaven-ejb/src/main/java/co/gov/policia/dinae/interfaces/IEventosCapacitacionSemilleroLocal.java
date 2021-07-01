/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EventosCapacitacionSemillero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IEventosCapacitacionSemilleroLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<EventosCapacitacionSemillero> findAll() throws JpaDinaeException;

  /**
   *
   * @param idEventCapaSemillero
   * @return
   * @throws JpaDinaeException
   */
  EventosCapacitacionSemillero findById(Long idEventCapaSemillero) throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<EventosCapacitacionSemillero> findByIdSemillero(Long idSemillero) throws JpaDinaeException;

  /**
   *
   * @param capacitacionSemillero
   * @return
   * @throws JpaDinaeException
   */
  EventosCapacitacionSemillero saveOrUpdate(EventosCapacitacionSemillero capacitacionSemillero) throws JpaDinaeException;
}
