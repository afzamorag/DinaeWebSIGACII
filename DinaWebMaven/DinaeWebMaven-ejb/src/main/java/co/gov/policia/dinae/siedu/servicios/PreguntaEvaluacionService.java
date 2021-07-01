package co.gov.policia.dinae.siedu.servicios;

import java.util.List;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacionPK;

public interface PreguntaEvaluacionService {

    /**
     *	
     * @param idEvaluation
     * @param params
     * @return
     * @throws ServiceException
     */
    List<PreguntaEvaluacion> findByEvaluation(Long idEvaluation) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(PreguntaEvaluacion record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(PreguntaEvaluacionPK id) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    int deleteByEvaluation(Long idEvaluation) throws ServiceException;
    /**
    *
    * @param record
    * @return
    * @throws ServiceException
    */
    PreguntaEvaluacion create(PreguntaEvaluacion record) throws ServiceException;

   /**
    *
    * @param record
    * @throws ServiceException
    */
   void update(PreguntaEvaluacion record) throws ServiceException;

}
