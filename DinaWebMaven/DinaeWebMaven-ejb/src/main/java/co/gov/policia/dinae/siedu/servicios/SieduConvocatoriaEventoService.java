/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduConvocatoriaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduConvocatoriaEventoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduConvocatoriaEvento> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduConvocatoriaEvento> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduConvocatoriaEvento findById(Long id) throws ServiceException;
  
    /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  List<SieduConvocatoriaEvento> findListById(SieduEventoEscuela id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduConvocatoriaEvento create(SieduConvocatoriaEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduConvocatoriaEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduConvocatoriaEvento record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
