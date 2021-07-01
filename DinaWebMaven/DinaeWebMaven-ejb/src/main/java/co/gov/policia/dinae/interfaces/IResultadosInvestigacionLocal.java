/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ResultadosInvestigacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IResultadosInvestigacionLocal {

  /**
   * findAll
   *
   * @return
   * @throws JpaDinaeException
   */
  List findAll() throws JpaDinaeException;

  /**
   * findByIdResultadoInvestigacion
   *
   * @param idResultadosInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  ResultadosInvestigacion findByIdResultadoInvestigacion(Long idResultadosInvestigacion) throws JpaDinaeException;

  /**
   * saveOrUpdate
   *
   * @param resultadosInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  ResultadosInvestigacion saveOrUpdate(ResultadosInvestigacion resultadosInvestigacion) throws JpaDinaeException;

  /**
   * findByInformeAvanceProyecto
   *
   * @param idInformeAvace
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List findByInformeAvanceProyecto(Long idInformeAvace, Long idProyecto) throws JpaDinaeException;

  /**
   * delete
   *
   * @param resultadosInvestigacion
   * @throws JpaDinaeException
   */
  void delete(ResultadosInvestigacion resultadosInvestigacion) throws JpaDinaeException;

}
