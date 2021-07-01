/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduCertificadoEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduCertificadoEventoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduCertificadoEvento> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduCertificadoEvento> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduCertificadoEvento findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduCertificadoEvento create(SieduCertificadoEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduCertificadoEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduCertificadoEvento record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
  
  /**
   * 
   * @param evento
   * @return
   * @throws ServiceException 
   */
  List<SieduParticipanteEvento> findNotCertified(SieduEventoEscuela evento) throws ServiceException;
}
