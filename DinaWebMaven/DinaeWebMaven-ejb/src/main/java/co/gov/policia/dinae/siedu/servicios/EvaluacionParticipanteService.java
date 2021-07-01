/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipante;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipantePK;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.util.List;

/**
 *
 * @author Juan Jose Buzon
 */
public interface EvaluacionParticipanteService {

    /**
     *
     * @return @throws ServiceException
     */
    List<EvaluacionParticipante> findAll() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    EvaluacionParticipante findById(EvaluacionParticipantePK id) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    EvaluacionParticipante create(EvaluacionParticipante record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void update(EvaluacionParticipante record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(EvaluacionParticipante record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(EvaluacionParticipantePK id) throws ServiceException;

    /**
     *	
     * @param idEvaluation
     * @return
     * @throws ServiceException
     */
    List<EvaluacionParticipante> findByEvaluation(Long idEvaluation) throws ServiceException;
    
    List<EvaluacionParticipante> findByPareEvee(SieduEventoEscuela evento) throws ServiceException;
}
