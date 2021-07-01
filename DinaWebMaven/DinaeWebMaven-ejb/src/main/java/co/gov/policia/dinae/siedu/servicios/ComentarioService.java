/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface ComentarioService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Comentario> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<Comentario> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Comentario findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  Comentario create(Comentario record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Comentario record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Comentario record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
