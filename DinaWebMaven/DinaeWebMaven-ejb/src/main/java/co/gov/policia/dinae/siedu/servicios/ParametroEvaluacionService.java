package co.gov.policia.dinae.siedu.servicios;

import java.util.List;

import co.gov.policia.dinae.siedu.constantes.TipoParametroEvaluacionEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.ParametroEvaluacion;

public interface ParametroEvaluacionService {

	  /**
	   *
	   * @return @throws ServiceException
	   */
	  List<ParametroEvaluacion> findAll() throws ServiceException;

	  /**
	   *
	   * @param id
	   * @return
	   * @throws ServiceException
	   */
	  ParametroEvaluacion findById(Long id) throws ServiceException;

	  /**
	   *
	   * @param record
	   * @return
	   * @throws ServiceException
	   */
	  ParametroEvaluacion create(ParametroEvaluacion record) throws ServiceException;

	  /**
	   *
	   * @param record
	   * @throws ServiceException
	   */
	  void update(ParametroEvaluacion record) throws ServiceException;

	  /**
	   *
	   * @param record
	   * @throws ServiceException
	   */
	  void delete(ParametroEvaluacion record) throws ServiceException;

	  /**
	   *
	   * @param id
	   * @throws ServiceException
	   */
	  void delete(Long id) throws ServiceException;	

	  /**
	   *
	   * @return @throws ServiceException
	   */
	  List<ParametroEvaluacion> findByType(Long tipo) throws ServiceException;

}
