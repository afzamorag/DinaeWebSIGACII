/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.DominioTipo;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface DominioTipoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<DominioTipo> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<DominioTipo> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  DominioTipo findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  DominioTipo create(DominioTipo record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(DominioTipo record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(DominioTipo record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
