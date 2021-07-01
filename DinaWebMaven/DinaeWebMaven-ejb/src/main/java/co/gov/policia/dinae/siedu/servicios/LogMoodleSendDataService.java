/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendData;
import java.util.List;

/**
 *
 * @author andres.zamorag
 */
public interface LogMoodleSendDataService {
 
  /**
   * 
   * @return
   * @throws ServiceException 
   */  
  List<LogMoodleSendData> findAll() throws ServiceException;  
    
  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  LogMoodleSendData findById(Long id) throws ServiceException;
  
  /**
   *
   * @param record
   * @return
   * @throws ServiceException
   */
  LogMoodleSendData create(LogMoodleSendData record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(LogMoodleSendData record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(LogMoodleSendData record) throws ServiceException;

  /**
   *
   * @param id
   * @throws ServiceException
   */
  void delete(Long id) throws ServiceException;
}
