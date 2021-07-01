/*
 * SoftStudio
 * Copyrigth .2017.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.VDatosPersona;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface VDatosPersonaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<VDatosPersona> findAll() throws ServiceException;

  /**
   *
   * @param params
   * @return
   * @throws ServiceException
   */
  List<VDatosPersona> findByFilter(Map<String, Object> params) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  VDatosPersona findById(String id) throws ServiceException;
  
  /**
   * 
   * @param identificacion
     * @return 
   * @throws ServiceException 
   */
  VDatosPersona findByIdentificacion(String identificacion) throws ServiceException;

}
