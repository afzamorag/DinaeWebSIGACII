/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersona;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersonaPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduBancoNecesidadPersonaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduBancoNecesidadPersona> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduBancoNecesidadPersona> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduBancoNecesidadPersona findById(SieduBancoNecesidadPersonaPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduBancoNecesidadPersona create(SieduBancoNecesidadPersona record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduBancoNecesidadPersona record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduBancoNecesidadPersona record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(SieduBancoNecesidadPersonaPK id) throws ServiceException;
}
