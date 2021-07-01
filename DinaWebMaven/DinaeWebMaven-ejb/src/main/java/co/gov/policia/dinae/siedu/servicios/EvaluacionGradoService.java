package co.gov.policia.dinae.siedu.servicios;

import java.util.List;
import java.util.Map;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EvaluacionFiltro;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;

/**
 * @author Juan José Buzón
 *
 */
public interface EvaluacionGradoService {

    /**
     *
     * @return @throws ServiceException
     */
    List<SieduEvalGrado> findAll() throws ServiceException;

    /**
     *
     * @param params
     * @return
     * @throws ServiceException
     */
    List<SieduEvalGrado> findByEvaluation(Long idEvaluation) throws ServiceException;

    /**
     *
     * @param params
     * @return
     * @throws ServiceException
     */
    List<SieduEvalGrado> findByFilter(EvaluacionFiltro filtro) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    SieduEvalGrado findById(Long id) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    SieduEvalGrado create(SieduEvalGrado record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void update(SieduEvalGrado record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(SieduEvalGrado record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    int deleteByEvaluation(Long idEvaluation) throws ServiceException;

}
