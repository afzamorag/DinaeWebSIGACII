/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.PresupuestoFiltro;
import co.gov.policia.dinae.siedu.modelo.Presupuesto;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface PresupuestoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Presupuesto> findAll() throws ServiceException;

  /**
   *
   * @param filter
   * @return
   * @throws ServiceException
   */
  List<Presupuesto> findByFilter(PresupuestoFiltro filter) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Presupuesto findById(Long id) throws ServiceException;

  /**
   *
   * @param id identificador de la capacitacion
   * @return
   * @throws ServiceException
   */
  List<Presupuesto> findByCapacitacion(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  Presupuesto create(Presupuesto record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Presupuesto record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Presupuesto record) throws ServiceException;
}
