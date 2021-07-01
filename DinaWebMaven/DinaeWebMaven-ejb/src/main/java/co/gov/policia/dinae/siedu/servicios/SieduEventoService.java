/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.EventoDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EventoFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduEventoService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduEvento> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<SieduEvento> findByFilter(EventoFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  SieduEvento findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @param competencias
   * @param categorias
   * @return
   * @throws ServiceException
   */
  SieduEvento create(SieduEvento record, List<SieduCompetencia> competencias, List<Categoria> categorias) throws ServiceException;

  /**
   *
   * @param record
   * @param competencias
   * @param categorias
   * @return
   * @throws ServiceException
   */
  SieduEvento update(SieduEvento record, List<SieduCompetencia> competencias, List<Categoria> categorias) throws ServiceException;

  /**
   *
   * @param evento
   * @return
   */
  boolean hasAssociatedRecords(Long evento);

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(SieduEvento record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void activate(SieduEvento record) throws ServiceException;

  /**
   *
   * @param idNivelAcademico
   * @return
   * @throws ServiceException
   */
  List<SieduEvento> findByNivelAcademico(Long idNivelAcademico) throws ServiceException;

  /**
   *
   * @param eventoEscuela
   * @return
   * @throws ServiceException
   */
  SieduEvento findByIdCarreraVigente(SieduEventoEscuela eventoEscuela) throws ServiceException;

  /**
   *
   * @param identificacion
   * @return
   * @throws ServiceException
   */
  List<EventoDTO> findForEvaluationByParticipant(String identificacion) throws ServiceException;
}
