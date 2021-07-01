/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualEntidad;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualEntidadPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduPropIntelectualEntidadService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduPropIntelectualEntidad> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduPropIntelectualEntidad> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduPropIntelectualEntidad findById(SieduPropIntelectualEntidadPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduPropIntelectualEntidad create(SieduPropIntelectualEntidad record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduPropIntelectualEntidad record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduPropIntelectualEntidad record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(SieduPropIntelectualEntidadPK id) throws ServiceException;
  
  /**
   * 
   * @param propiedadId
   * @throws ServiceException 
   */
  void deleteByPropiedadId(Long propiedadId) throws ServiceException;
}
