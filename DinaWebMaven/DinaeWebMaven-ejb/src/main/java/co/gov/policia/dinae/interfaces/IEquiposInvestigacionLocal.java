/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EquiposInvestigacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/11/28
 */
@Local
public interface IEquiposInvestigacionLocal {

  /**
   * Consulta la lista de equipos de investigación existentes
   *
   * @return La lista de equipos de investigación existentes
   * @throws JpaDinaeException Si ocurre un error en base de datos
   */
  public List<EquiposInvestigacion> getListaEquiposInvestigacion() throws JpaDinaeException;

  /**
   * Permite añadir un nuevo equipo para la investigación a la tabla correspondiente.
   *
   * @param equipo
   * @throws JpaDinaeException
   */
  public void addEquipoInvestigacion(EquiposInvestigacion equipo) throws JpaDinaeException;

  /**
   *
   * @param equipo
   * @throws JpaDinaeException
   */
  public void updateEquipoInvestigacion(EquiposInvestigacion equipo) throws JpaDinaeException;

  /**
   * saveOrUpdate
   *
   * @param equipo
   * @return
   * @throws JpaDinaeException
   */
  public EquiposInvestigacion saveOrUpdate(EquiposInvestigacion equipo) throws JpaDinaeException;

  /**
   * findEquiposByProyecto
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List findEquiposByProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   * Delete
   *
   * @param equipo
   * @return
   * @throws JpaDinaeException
   */
  void delete(EquiposInvestigacion equipo) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<EquiposInvestigacion> findByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException;
}
