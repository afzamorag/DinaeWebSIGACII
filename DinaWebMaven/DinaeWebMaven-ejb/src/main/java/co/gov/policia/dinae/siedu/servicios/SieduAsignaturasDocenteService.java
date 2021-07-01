/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduAsignaturasDocente;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author andres.zamorag
 */
public interface SieduAsignaturasDocenteService {
    
/**
   *
   * @return @throws ServiceException
   */
  List<SieduAsignaturasDocente> findAll() throws ServiceException;

  /**
   * 
   * @param idProgramacionAsignatura
   * @param identificacion
   * @return
   * @throws ServiceException 
   */
  List<SieduAsignaturasDocente> findByProgamaDocente(BigInteger idProgramacionAsignatura, String identificacion) throws ServiceException;

}
