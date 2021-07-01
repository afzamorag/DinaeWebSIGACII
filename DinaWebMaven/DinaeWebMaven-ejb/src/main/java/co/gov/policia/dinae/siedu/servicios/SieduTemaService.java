/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduTema;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduTemaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduTema> findAll() throws ServiceException;

  /**
   *
   * @param idEvento
   * @return
   * @throws ServiceException
   */
  List<SieduTema> findTemasByEvento(Long idEvento) throws ServiceException;
  
  /**
   *
   * @param evento
   * @return
   * @throws ServiceException
   */
  List<SieduTema> buscarTemasByEvento(SieduEvento evento) throws ServiceException;

  /**
   *
   * @param idTemaPapa
   * @return
   * @throws ServiceException
   */
  List<SieduTema> findTemasByEventoTemaPapa(Long idTemaPapa) throws ServiceException;

  /**
   *
   * @param temaTemaPadre
     * @param evento
   * @return
   * @throws Exception
   */
  List<SieduTema> findTemasByTemaTemaPadre(Long temaTemaPadre, SieduEventoEscuela evento) throws Exception;

  SieduTema findTemasByTemaPapa(Long idTema) throws ServiceException;

  SieduTema findSubtema(Long idTema) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  SieduTema create(SieduTema record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(SieduTema record) throws ServiceException;

  /**
   *
   * @param eventoEscuela
   * @return
   * @throws ServiceException
   */
  List<SieduTema> findTemasByCarrera(SieduEventoEscuela eventoEscuela) throws ServiceException;

  /**
   *
   * @param record
   * @param evento
   * @throws ServiceException
   */
  void delete(SieduTema record, SieduEvento evento) throws ServiceException;
}
