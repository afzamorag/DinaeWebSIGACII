/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.HorasDocenteSigac;
import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduHorasDocenteService {

  /**
   *
   * @return @throws ServiceException
   */
  List<HorasDocenteSigac> findAll() throws ServiceException;
  /**
   * 
   * @param fecha_ini
   * @param fecha_fin
   * @param fecha_crea
   * @return
   * @throws ServiceException 
   */
  List<HorasDocenteSigac> findByFechas(Date fecha_ini, Date fecha_fin, Date fecha_crea) throws ServiceException;
  
  /**
   * 
   * 
     * @param fecha_ini
     * @param fecha_fin
     * @param fecha_crea
     * @param identificacion
     * @param codEscuela
     * @return 
     * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  List<HorasDocenteSigac> findByFechasIdentificacion(Date fecha_ini, Date fecha_fin, Date fecha_crea, Long identificacion, Long codEscuela) throws ServiceException;
  
  /**
   * 
   * @param fecha_ini
   * @param fecha_fin
   * @param fecha_crea
   * @return
   * @throws ServiceException 
   */
  List<HorasDocenteSigac> findByPeriodo(Date fecha_ini, Date fecha_fin, Date fecha_crea) throws ServiceException;
  
  /**
   * 
   * @param fecha_ini
   * @param fecha_fin
   * @param fecha_crea
   * @return
   * @throws ServiceException 
   */
  List<HorasDocenteSigac> findAllByFechas(Date fecha_ini, Date fecha_fin, Date fecha_crea) throws ServiceException;
  
}
