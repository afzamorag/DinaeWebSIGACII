/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.OtrosEstimulosSemillero;
import co.gov.policia.dinae.modelo.TalentoEstimuloSemillero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface ITalentoEstimuloSemilleroLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<TalentoEstimuloSemillero> findAll() throws JpaDinaeException;

  /**
   *
   * @param idTalentoEstimuloSemillero
   * @return
   * @throws JpaDinaeException
   */
  TalentoEstimuloSemillero findById(Long idTalentoEstimuloSemillero) throws JpaDinaeException;

  /**
   *
   * @param talentoEstimuloSemillero
   * @return
   * @throws JpaDinaeException
   */
  TalentoEstimuloSemillero saveOrUpdate(TalentoEstimuloSemillero talentoEstimuloSemillero) throws JpaDinaeException;

  /**
   *
   * @param idOtrosEstimulosSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<TalentoEstimuloSemillero> findByOtrosEstimulosSemillero(Long idOtrosEstimulosSemillero) throws JpaDinaeException;

  /**
   *
   * @param talentoEstimuloSemillero
   * @throws JpaDinaeException
   */
  void deleteEntity(TalentoEstimuloSemillero talentoEstimuloSemillero) throws JpaDinaeException;
}
