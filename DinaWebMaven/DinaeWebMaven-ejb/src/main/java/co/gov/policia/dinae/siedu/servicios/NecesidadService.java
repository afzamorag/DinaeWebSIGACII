/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.NecesidadFiltro;
import co.gov.policia.dinae.siedu.modelo.Necesidad;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface NecesidadService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Necesidad> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<Necesidad> findByFilter(NecesidadFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Necesidad findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  Necesidad create(Necesidad record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Necesidad record) throws ServiceException;

  /**
   *
   * @param records
   * @throws ServiceException
   */
  void update(List<Necesidad> records) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Necesidad record) throws ServiceException;

  /**
   *
   * @param paramsSP
   * @return
   * @throws ServiceException
   */
  String importNeeds(SPFiltro paramsSP) throws ServiceException;

  /**
   * valida que necesidades de un PAE, aun se encuentran en estado PENDIENTE
   *
   * @param pae
   * @return
   * @throws ServiceException
   */
  List<String> validarEstadoNecesidades(Long pae) throws ServiceException;

  /**
   * valida si las necesidades de un PAE, ya tienen asociado el proceso y la estrategia
   *
   * @param pae
   * @return
   * @throws ServiceException
   */
  List<String> validarProcesoNecesidades(Long pae) throws ServiceException;
  
  /**
   * 
   * @param pae
   * @return
   * @throws ServiceException 
   */
  Integer obtenerCantidadNecesidades(Long  pae) throws ServiceException;
}
