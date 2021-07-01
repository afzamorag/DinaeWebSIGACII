/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersona;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersonaPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduPropIntelectualPersonaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduPropIntelectualPersona> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduPropIntelectualPersona> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduPropIntelectualPersona findById(SieduPropIntelectualPersonaPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduPropIntelectualPersona create(SieduPropIntelectualPersona record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduPropIntelectualPersona record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduPropIntelectualPersona record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(SieduPropIntelectualPersonaPK id) throws ServiceException;
  
  /**
   * 
   * @param propiedadId
   * @throws ServiceException 
   */
  void deleteByPropiedadId(Long propiedadId) throws ServiceException;
}
