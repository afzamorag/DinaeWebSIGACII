/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.HorasDocenteCapacitacion;
import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduHorasDocenteEventoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduDocenteEventoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduDocenteEvento> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduDocenteEvento> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduDocenteEvento findById(Long id) throws ServiceException;
  
      /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  List<SieduDocenteEvento> findListById(SieduEventoEscuela id) throws ServiceException;
  /**
   * 
   * @param id
   * @return
   * @throws ServiceException 
   */
  List<SieduHorasDocenteEventoDTO> findListByIdDistinct(SieduEventoEscuela id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduDocenteEvento create(SieduDocenteEvento record) throws ServiceException;
  
  /**
   * 
   * @param persona
   * @param eventoEscuela
   * @return
   * @throws ServiceException 
   */
  
  List <SieduDocenteEvento> findByDoceeveeDocepers(SieduPersona persona, SieduEventoEscuela eventoEscuela) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduDocenteEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduDocenteEvento record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
  
  List<HorasDocenteCapacitacion> findByPeriod(Map<String, Object> params) throws ServiceException;
}
