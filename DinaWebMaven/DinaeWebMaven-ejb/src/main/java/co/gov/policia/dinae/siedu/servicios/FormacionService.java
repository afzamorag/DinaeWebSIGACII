/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.FormacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Formacion;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.FormacionEscuelaCohorte;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface FormacionService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Formacion> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<Formacion> findByFilter(FormacionFiltro filtro) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Formacion findById(Long id) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Formacion findFullById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @param escuelas
   * @throws ServiceException
   */
  void administrarEscuelas(Formacion record, List<UnidadDependencia> escuelas) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void aprobar(Formacion record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void reprobar(Formacion record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void pendiente(Formacion record) throws ServiceException;

  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  FormacionEscuelaCohorte createCohorte(FormacionEscuelaCohorte record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void updateCohorte(FormacionEscuelaCohorte record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void deleteCohorte(FormacionEscuelaCohorte record) throws ServiceException;

  /**
   *
   * @param paramsSP
   * @return
   * @throws ServiceException
   */
  String consolidateTrainingNeeds(SPFiltro paramsSP) throws ServiceException;
}
