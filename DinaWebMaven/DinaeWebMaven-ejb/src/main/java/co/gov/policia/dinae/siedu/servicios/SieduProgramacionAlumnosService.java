/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.ProgramacionAlumnos;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: 
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduProgramacionAlumnosService {
  /**
   *
   * @return @throws ServiceException
   */
  List<ProgramacionAlumnos> findAll() throws ServiceException;


  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  ProgramacionAlumnos findById(BigDecimal id) throws ServiceException;


  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  ProgramacionAlumnos create(ProgramacionAlumnos record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(ProgramacionAlumnos record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(ProgramacionAlumnos record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;

  void update(List<ProgramacionAlumnos> lst) throws ServiceException;
  
  /**
   * 
   * @param id
   * @return
   * @throws ServiceException 
   */
  List<ProgramacionAlumnos> findByProgDocente(BigDecimal id) throws ServiceException;
  
}