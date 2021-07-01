/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface ISemillerosImplementacionLocal {

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<SemillerosImplementacion> findAllBySemilleroInvestigacion(Long idSemillero) throws JpaDinaeException;
}
