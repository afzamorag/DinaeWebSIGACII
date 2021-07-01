/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduArchivoCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduArchivoCompromisoPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduArchivoCompromisoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduArchivoCompromiso> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduArchivoCompromiso> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduArchivoCompromiso findById(SieduArchivoCompromisoPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduArchivoCompromiso create(SieduArchivoCompromiso record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduArchivoCompromiso record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduArchivoCompromiso record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(SieduArchivoCompromisoPK id) throws ServiceException;
}
