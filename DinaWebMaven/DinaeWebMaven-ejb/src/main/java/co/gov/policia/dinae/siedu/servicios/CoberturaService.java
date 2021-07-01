/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.CoberturaFiltro;
import co.gov.policia.dinae.siedu.modelo.Cobertura;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface CoberturaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<Cobertura> findAll() throws ServiceException;

  /**
   *
   * @param filtro
   * @return
   * @throws ServiceException
   */
  List<Cobertura> findByFilter(CoberturaFiltro filtro) throws ServiceException;
  
    
  /**
   *
   * @param escuela 
   * @param pae 
   * @param estrategia 
   * @return
   * @throws ServiceException
   */
  List<Cobertura> findByEscuela(UnidadDependencia escuela, PAE pae, Dominio estrategia) throws ServiceException;

  /**
   *
   * @param id
   * @return
   * @throws ServiceException
   */
  Cobertura findById(Long id) throws ServiceException;

  /**
   *
   * @param record
   * @param dependencias
   * @throws ServiceException
   */
  void create(Cobertura record, List<UnidadDependencia> dependencias) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void update(Cobertura record) throws ServiceException;

  /**
   *
   * @param record
   * @throws ServiceException
   */
  void delete(Cobertura record) throws ServiceException;
}
