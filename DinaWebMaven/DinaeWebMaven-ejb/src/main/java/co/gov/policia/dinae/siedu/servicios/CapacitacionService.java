/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.CapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Capacitacion;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface CapacitacionService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Capacitacion> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<Capacitacion> findByFilter(CapacitacionFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Capacitacion findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void aprobar(Capacitacion record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void reprobar(Capacitacion record) throws ServiceException;

  /**
   *
   * @param paramsSP
   * @return
   * @throws ServiceException
   */
  String consolidateTrainingNeeds(SPFiltro paramsSP) throws ServiceException;
  
  void delete(Long id) throws ServiceException;
}
