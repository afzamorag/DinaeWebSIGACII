/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CronogramaSemillero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface ICronogramaSemilleroLocal {

  /**
   *
   * @param idCronogramaSemillero
   * @return
   * @throws JpaDinaeException
   */
  CronogramaSemillero findById(Long idCronogramaSemillero) throws JpaDinaeException;

  /**
   *
   * @param cronogramaSemillero
   * @return
   * @throws JpaDinaeException
   */
  CronogramaSemillero saveOrUpdate(CronogramaSemillero cronogramaSemillero) throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<CronogramaSemillero> findAllActivitiesBySemillero(Long idSemillero) throws JpaDinaeException;

  /**
   *
   * @param idSemilleroProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<CronogramaSemillero> findAllActivitiesBySemilleroProyecto(Long idSemilleroProyecto) throws JpaDinaeException;

  /**
   *
   * @param idSemillerosImplementacion
   * @return
   * @throws JpaDinaeException
   */
  List<CronogramaSemillero> findAllActivitiesBySemillerosImplementacion(Long idSemillerosImplementacion) throws JpaDinaeException;
}
