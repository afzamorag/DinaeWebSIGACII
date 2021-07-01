/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ReseniaInvestigacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IReseniaInvestigacionLocal {

  /**
   * findAll
   *
   * @return
   * @throws JpaDinaeException
   */
  List findAll() throws JpaDinaeException;

  /**
   * findByIdReseniaInvestigacion
   *
   * @param idReseniaInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  ReseniaInvestigacion findByIdReseniaInvestigacion(Long idReseniaInvestigacion) throws JpaDinaeException;

  /**
   * saveOrUpdate
   *
   * @param reseniaInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  ReseniaInvestigacion saveOrUpdate(ReseniaInvestigacion reseniaInvestigacion) throws JpaDinaeException;

  /**
   * findByInformeFinalProyecto
   *
   * @param idInformeAvance
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  ReseniaInvestigacion findByInformeFinalProyecto(Long idInformeAvance, Long idProyecto) throws JpaDinaeException;

}
