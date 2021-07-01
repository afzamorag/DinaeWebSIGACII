/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramacionAlumno;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author andres.zamorag
 */
public interface SieduProgramacionAlumnoService {
    
    /**
     * 
     * @param progDocente
     * @return
     * @throws ServiceException 
     */
    List<SieduProgramacionAlumno> findByIdProgDocente(BigDecimal progDocente) throws ServiceException;
    
}
