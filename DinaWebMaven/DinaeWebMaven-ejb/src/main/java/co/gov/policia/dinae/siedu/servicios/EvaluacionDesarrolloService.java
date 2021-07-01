package co.gov.policia.dinae.siedu.servicios;

import java.util.List;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrollo;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrolloPK;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipante;

public interface EvaluacionDesarrolloService {

    /**
     *
     * @return @throws ServiceException
     */
    List<EvaluacionDesarrollo> findAll() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    EvaluacionDesarrollo findById(EvaluacionDesarrolloPK id) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    EvaluacionDesarrollo create(EvaluacionDesarrollo record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void update(EvaluacionDesarrollo record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(EvaluacionDesarrollo record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     *
     * @param records
     * @param observation
     * @return
     * @throws ServiceException
     */
    List<EvaluacionDesarrollo> create(List<EvaluacionDesarrollo> records, String observation) throws ServiceException;

    /**
     *	
     * @param idEvaluation
     * @return
     * @throws ServiceException
     */
    List<EvaluacionDesarrollo> findByEvaluation(Long idEvaluation) throws ServiceException;

}
