/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramasEscuelas;
import java.util.List;

/**
 *
 * @author andres.zamorag
 */
public interface SieduProgramasEscuelasService {
    
    /**
     * 
     * @return
     * @throws ServiceException 
     */
List<SieduProgramasEscuelas> findAll() throws ServiceException;

/**
 * 
 * @param codEscuelas
 * @return
 * @throws ServiceException 
 */
List<SieduProgramasEscuelas> findByEscuela(Long codEscuela) throws ServiceException;
}
