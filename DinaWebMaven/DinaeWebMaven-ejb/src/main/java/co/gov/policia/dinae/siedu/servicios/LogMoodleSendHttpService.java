/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendHttp;
import java.util.Date;
import java.util.List; 

/**
 *
 * @author Administrator
 */
public interface LogMoodleSendHttpService {
    
    /**
     * 
     * @return
     * @throws ServiceException 
     */
    public List<LogMoodleSendHttp> findAll() throws ServiceException;  
    
    /**
     * 
     * @param where
     * @return
     * @throws ServiceException 
     */
    public List<LogMoodleSendHttp> findFiltro(String where) throws ServiceException;  
    
    /**
     * 
     * @param estado
     * @throws ServiceException 
     */
    public void deleteEstado(Integer estado) throws ServiceException;   

    /**
     * 
     * @param fecha
     * @throws ServiceException 
     */
    public void deleteHistory(Date fecha) throws ServiceException;   
    
    /**
     * 
     * @param id
     * @return
     * @throws ServiceException 
     */
    public LogMoodleSendHttp findById(Long id) throws ServiceException;
    
  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  LogMoodleSendHttp create(LogMoodleSendHttp record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(LogMoodleSendHttp record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(LogMoodleSendHttp record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
