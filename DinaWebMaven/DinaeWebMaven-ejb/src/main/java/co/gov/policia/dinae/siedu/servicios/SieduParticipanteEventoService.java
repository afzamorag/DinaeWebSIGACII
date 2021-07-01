/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduDatosEmpleadoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduParticipanteEventoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduParticipanteEvento> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<SieduParticipanteEvento> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduParticipanteEvento findById(Long id) throws ServiceException;
  
  /**
   * 
   * @param id
   * @return
   * @throws ServiceException 
   */

  List<SieduParticipanteEvento> findListById(SieduEventoEscuela id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduParticipanteEvento create(SieduParticipanteEvento record) throws ServiceException;
  
  String create(String identificacion, String v_vigencia, Long v_id_carrera, Long v_evee, String v_usu_crea, Integer v_trimestre, Long v_escuela) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduParticipanteEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduParticipanteEvento record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;

  /**
   *
   * @param identificacion
   * @return
   * @throws ServiceException
   */
  SieduDatosEmpleadoDTO findDatosEmpleadoPolicial(Long identificacion) throws ServiceException;
  
  /**
   * 
   * @param evento
   * @param participante
   * @return
   * @throws ServiceException 
   */
  
  SieduParticipanteEvento findByPareeveeParepers (SieduEventoEscuela evento, SieduPersona participante) throws ServiceException;
  
  /**
   * 
   * @param evento
   * @param identificacion
   * @return
   * @throws ServiceException 
   */
  List <SieduParticipanteEvento> findParticipanteByVigencia (SieduEventoEscuela evento, String identificacion) throws ServiceException;
  
  /**
   * 
   * @param evento
   * @param identificacion
   * @return
   * @throws ServiceException 
   */
  SieduParticipanteEvento findByPareeveeParenroiden (Long evento, String identificacion) throws ServiceException;
  
  /**
   * 
   * @param identificacion
   * @return
   * @throws ServiceException 
   */
  List<SieduParticipanteEvento> findCapacitacionFuncionario(String identificacion) throws ServiceException;
}
