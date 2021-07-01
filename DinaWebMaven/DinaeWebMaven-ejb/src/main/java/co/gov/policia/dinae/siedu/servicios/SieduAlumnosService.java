/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Alumnos;
import java.util.List;

/**
 *
 * @author andres.zamorag
 */
public interface SieduAlumnosService {
    
/**
   *
   * @return @throws ServiceException
   */
  List<Alumnos> findAll() throws ServiceException;

  /**
   * 
   * @param identificacion
   * @return
   * @throws ServiceException 
   */
  Alumnos findByIdentificacion(String identificacion) throws ServiceException;


}
