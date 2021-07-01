/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduPropuestaAsignadaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduPropuestaAsignada> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduPropuestaAsignada> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduPropuestaAsignada findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduPropuestaAsignada create(SieduPropuestaAsignada record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduPropuestaAsignada record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduPropuestaAsignada record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
  
  /**
   * 
   * @param propuestaNecesidad
   * @return
   * @throws ServiceException 
   */
  List<SieduPropuestaAsignada> findByVigencia(PropuestaNecesidad propuestaNecesidad) throws ServiceException;
}
