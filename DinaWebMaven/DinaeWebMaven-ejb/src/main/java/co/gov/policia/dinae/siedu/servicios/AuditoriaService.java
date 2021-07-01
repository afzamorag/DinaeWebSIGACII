/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.AuditoriaFiltro;
import co.gov.policia.dinae.siedu.modelo.Auditoria;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface AuditoriaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Auditoria> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<Auditoria> findByFilter(AuditoriaFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Auditoria findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  Auditoria create(Auditoria record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Auditoria record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Auditoria record) throws ServiceException;
}
