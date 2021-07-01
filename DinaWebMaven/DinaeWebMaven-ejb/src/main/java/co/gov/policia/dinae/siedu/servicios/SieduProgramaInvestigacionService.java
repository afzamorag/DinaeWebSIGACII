/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacion;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacionPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduProgramaInvestigacionService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduProgramaInvestigacion> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduProgramaInvestigacion> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduProgramaInvestigacion findById(SieduProgramaInvestigacionPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduProgramaInvestigacion create(SieduProgramaInvestigacion record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduProgramaInvestigacion record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduProgramaInvestigacion record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(SieduProgramaInvestigacionPK id) throws ServiceException;
}
