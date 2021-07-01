/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduInasisteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduInasisteEventoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduInasisteEvento> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduInasisteEvento> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduInasisteEvento findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduInasisteEvento create(SieduInasisteEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduInasisteEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduInasisteEvento record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;

  /**
   *
   * @param inasistente
   * @param fechaInasiste
   * @return
   * @throws ServiceException
   */
  SieduInasisteEvento findByInaepareInaefecha(SieduPersona inasistente, Date fechaInasiste) throws ServiceException;

  /**
   *
   * @param evento
   * @return
   * @throws ServiceException
   */
  List<SieduInasisteEvento> findByInaepareEvee(SieduEventoEscuela evento) throws ServiceException;

  /**
   *
   * @param identificacion
   * @return
   * @throws ServiceException
   */
  List<SieduInasisteEvento> findByInaepare(String identificacion, SieduEventoEscuela evento) throws ServiceException;
}
