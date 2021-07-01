/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.PAENovedad;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface PAEService {

  /**
   *
   * @return @throws ServiceException
   */
  List<PAE> consultarVigencias() throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<PAE> consultarVigenciasAbiertas() throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<PAE> consultarVigenciasCerradas() throws ServiceException;

  /**
   * Método que consulta el PAE y sus novedades, asociado a la vigencia que entra por parametero
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  PAE consultarPAE(Long id) throws ServiceException;

  /**
   * Método que consulta el PAE y sus novedades, asociado a la vigencia que entra por parametero
   *
   * @param vigencia año de la vigencia
   * @return
   * @throws ServiceException
   */
  PAE consultarPAE(String vigencia) throws ServiceException;

  /**
   * Metodo que se usa para persistir la información de un PAE al momento de iniciar la vigencia.
   *
   * precondición: no existen registros asociados para esa vigencia
   *
   * postcondición: proceso = construcción
   *
   * @param pae
   * @param novedad
   * @return
   * @throws ServiceException
   */
  PAE abrirPAE(PAE pae, PAENovedad novedad) throws ServiceException;

  /**
   * Metodo que se usa para agregar un nuevo registro a la informacion de un PAE
   *
   * precondición: proceso = construcción
   *
   * postcondición: proceso = modificacion
   *
   * @param pae
   * @param novedad
   * @throws ServiceException
   */
  void reabrirPAE(PAE pae, PAENovedad novedad) throws ServiceException;

  /**
   * Metodo que modifica la informacion de un PAE
   *
   * precondición: proceso = construcción/modificacion
   *
   * postcondición: proceso = cerrado
   *
   * @param pae
   * @param novedad
   * @throws ServiceException
   */
  void cerrarPAE(PAE pae, PAENovedad novedad) throws ServiceException;
}
