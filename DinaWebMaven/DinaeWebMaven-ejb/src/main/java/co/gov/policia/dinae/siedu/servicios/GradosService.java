/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.GradoPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Andres Zamora <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
public interface GradosService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Grado> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<Grado> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Grado findByAlfabetico(GradoPK id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  Grado create(Grado record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Grado record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Grado record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
