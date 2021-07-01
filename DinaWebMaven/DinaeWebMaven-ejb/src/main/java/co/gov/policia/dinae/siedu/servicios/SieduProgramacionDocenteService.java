/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramacionDocente;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andres.zamorag
 */
public interface SieduProgramacionDocenteService {

    /**
     *
     * @return @throws ServiceException
     */
    List<SieduProgramacionDocente> findAll() throws ServiceException;

    /**
     *
     * @param identificacion
     * @return
     * @throws ServiceException
     */
    List<SieduProgramacionDocente> findByIdentificacion(String identificacion) throws ServiceException;
    
    /**
     *  
     * @param identificacion
     * @param unidad
     * @return
     * @throws ServiceException 
     */

    List<SieduProgramacionDocente> findByIdentificacionUnidad(String identificacion, Long unidad) throws ServiceException;
    /**
     * 
     * @param identificacion
     * @param vigente
     * @return
     * @throws ServiceException 
     */
    List<SieduProgramacionDocente> findByIdentificacionVigente(String identificacion, String vigente) throws ServiceException;
    
    /**
     * 
     * @param params
     * @return
     * @throws ServiceException 
     */
    List<SieduProgramacionDocente> findByFilter(Map<String, Object> params) throws ServiceException;
    
    /**
     * 
     * @param idProgDocente
     * @return
     * @throws ServiceException 
     */    
    SieduFechasMaxMinEventoDTO findByidProgDocente(BigDecimal idProgDocente) throws ServiceException;
}
