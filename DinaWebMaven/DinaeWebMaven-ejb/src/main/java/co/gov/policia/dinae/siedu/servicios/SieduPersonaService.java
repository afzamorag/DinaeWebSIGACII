/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduPersonaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduPersona> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduPersona> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduPersona findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduPersona create(SieduPersona record) throws ServiceException;
  
  /**
   *
   * @param identificacion
   * @return
   * @throws ServiceException
   */
  SieduPersona findByIdentificacion(String identificacion) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduPersona record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduPersona record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
