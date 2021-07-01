/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Cargos;
import co.gov.policia.dinae.siedu.modelo.CargosPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface CargosService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Cargos> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<Cargos> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Cargos findById(CargosPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  Cargos create(Cargos record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Cargos record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Cargos record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
