/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExternaParticipante;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExternaParticipantePK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduInvestigacionExternaParticipanteService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduInvestigacionExternaParticipante> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduInvestigacionExternaParticipante> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduInvestigacionExternaParticipante findById(SieduInvestigacionExternaParticipantePK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduInvestigacionExternaParticipante create(SieduInvestigacionExternaParticipante record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduInvestigacionExternaParticipante record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduInvestigacionExternaParticipante record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(SieduInvestigacionExternaParticipantePK id) throws ServiceException;
}
