/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.OtrosEstimulosSemillero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IOtrosEstimulosSemilleroLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<OtrosEstimulosSemillero> findAll() throws JpaDinaeException;

  /**
   *
   * @param idOtrosEstimulosSemillero
   * @return
   * @throws JpaDinaeException
   */
  OtrosEstimulosSemillero findById(Long idOtrosEstimulosSemillero) throws JpaDinaeException;

  /**
   *
   * @param estimulosSemillero
   * @return
   * @throws JpaDinaeException
   */
  OtrosEstimulosSemillero saveOrUpdate(OtrosEstimulosSemillero estimulosSemillero) throws JpaDinaeException;

  List<OtrosEstimulosSemillero> findBySemilleroInvestigacion(Long idSemillero) throws JpaDinaeException;

}
