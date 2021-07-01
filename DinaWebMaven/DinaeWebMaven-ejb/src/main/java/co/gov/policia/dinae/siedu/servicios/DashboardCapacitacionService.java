/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.SeguimientoPAEDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.DashboardCapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.DashboardCapacitacion;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface DashboardCapacitacionService {

    /**
     *
     * @param filtro
     * @return
     * @throws ServiceException
     */
    List<DashboardCapacitacion> findByFilter(DashboardCapacitacionFiltro filtro) throws ServiceException;
    
    /**
     * 
     * @param filtro
     * @return
     * @throws ServiceException 
     */
    List<SeguimientoPAEDTO> findByFilterGraph(DashboardCapacitacionFiltro filtro) throws ServiceException;
    
    /**
     * 
     * @param filtro
     * @return
     * @throws ServiceException 
     */
    List<DashboardCapacitacion> findGeneralTargetByFilter(DashboardCapacitacionFiltro filtro) throws ServiceException;
    
    /**
     * 
     * @param filtro
     * @return
     * @throws ServiceException 
     */
    List<DashboardCapacitacion> findYearGeneralTargetByFilter(DashboardCapacitacionFiltro filtro) throws ServiceException;
    
    /**
     * 
     * @param filtro
     * @return
     * @throws ServiceException 
     */
    List<DashboardCapacitacion> findYearGeneralTarget(DashboardCapacitacionFiltro filtro) throws ServiceException;
}
