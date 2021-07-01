/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EventoEscuelaFiltro;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.util.List;


/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduEventoEscuelaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduEventoEscuela> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<SieduEventoEscuela> findByFilter(EventoEscuelaFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduEventoEscuela findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduEventoEscuela create(SieduEventoEscuela record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduEventoEscuela record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduEventoEscuela record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
  /**
   * 
   * @param usuario
   * @param evento
   * @throws ServiceException 
   */  
  void cerrarEvento(String usuario, SieduEventoEscuela evento) throws ServiceException;
  
  /**
   * 
   * @param evento
   * @return
   * @throws ServiceException 
   */
  Evaluacion consultarEvaluacionEvento(SieduEventoEscuela evento) throws ServiceException;
  
  /**
   * 
   * @param evento
   * @return
   * @throws ServiceException 
   */
  SieduFechasMaxMinEventoDTO consultasFechasMaxMinInasistencia(SieduEventoEscuela evento) throws ServiceException;
  
  List<SieduEventoEscuela> findByPaeCapacitacion(Long capaPae) throws ServiceException;
}
