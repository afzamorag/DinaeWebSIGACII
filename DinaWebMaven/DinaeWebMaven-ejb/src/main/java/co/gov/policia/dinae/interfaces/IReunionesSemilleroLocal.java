/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ReunionesSemillero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IReunionesSemilleroLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<ReunionesSemillero> findAll() throws JpaDinaeException;

  /**
   *
   * @param idReunionesSemillero
   * @return
   * @throws JpaDinaeException
   */
  ReunionesSemillero findById(Long idReunionesSemillero) throws JpaDinaeException;

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  List<ReunionesSemillero> findBySemillero(Long idSemillero) throws JpaDinaeException;

  /**
   *
   * @param reunionesSemillero
   * @return
   * @throws JpaDinaeException
   */
  ReunionesSemillero saveOrUpdate(ReunionesSemillero reunionesSemillero) throws JpaDinaeException;
}
