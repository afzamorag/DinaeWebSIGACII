/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.EventoDTO;
import java.util.List;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EvaluacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;

/**
 *
 * @author Juan Jose Buzon
 */
public interface EvaluacionService {

    /**
     *
     * @return @throws ServiceException
     */
    List<Evaluacion> findAll() throws ServiceException;

    /**
     *
     * @return @throws ServiceException
     */
    List<Evaluacion> findAllLoadIdAndDescription() throws ServiceException;

    /**
     *
     * @return
     * @throws ServiceException
     */
    List<Evaluacion> findActives() throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    Evaluacion findById(Long id) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void update(Evaluacion record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void updateWithDegrees(Evaluacion record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(Evaluacion record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     *
     * @param filtro
     * @return
     * @throws ServiceException
     */
    List<Evaluacion> findByEvaluationFilter(EvaluacionFiltro filtro)
            throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    Evaluacion newVersion(Evaluacion record) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    Evaluacion create(Evaluacion record) throws ServiceException;

    /**
     * @param idEvent
     * @param pae
     * @return
     * @throws ServiceException
     */
    Evaluacion findForDevelopByEvent(Long idEvent, PAE pae) throws ServiceException;

    /**
     *
     * @param idEvaluation
     * @return
     * @throws ServiceException
     */
    Evaluacion findByIdLoadQuestions(Long idEvaluation) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    Evaluacion create(Evaluacion record, List<SieduEvalGrado> evalGrados) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    Evaluacion create(Evaluacion record, List<SieduEvalGrado> evalGrados, List<PreguntaEvaluacion> preguntasEvaluacion) throws ServiceException;

}
