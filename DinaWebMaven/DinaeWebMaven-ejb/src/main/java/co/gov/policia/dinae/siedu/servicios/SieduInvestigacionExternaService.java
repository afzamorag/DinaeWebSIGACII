/*
 * SoftStudio
 * Copyrigth .2017.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExterna;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduInvestigacionExternaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduInvestigacionExterna> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduInvestigacionExterna> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduInvestigacionExterna findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduInvestigacionExterna create(SieduInvestigacionExterna record) throws ServiceException;
  
  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduInvestigacionExterna record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduInvestigacionExterna record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
