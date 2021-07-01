/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.PersonaExternaFiltro;
import co.gov.policia.dinae.siedu.modelo.PersonaExterna;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface PersonaExternaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<PersonaExterna> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<PersonaExterna> findByFilter(PersonaExternaFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  PersonaExterna findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  PersonaExterna create(PersonaExterna record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(PersonaExterna record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(PersonaExterna record) throws ServiceException;

  /**
   * Valida si existe un empleado con ese numero de identificacion
   *
   * @param numeroIdentificacion
   * @return @throws ServiceException
   */
  boolean validarNumeroIdentificacion(String numeroIdentificacion);
}
