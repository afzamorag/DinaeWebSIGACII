package co.gov.policia.dinae.siedu.servicios;

import java.util.List;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEvalCategoria;
import co.gov.policia.dinae.siedu.modelo.SiudeEvalCategoriaPK;

public interface EvaluacionCategoriaService {

    /**
     *
     * @return @throws ServiceException
     */
    List<SieduEvalCategoria> findAll() throws ServiceException;

    /**
     *
     * @param params
     * @return
     * @throws ServiceException
     */
    List<SieduEvalCategoria> findByEvaluation(Long idEvaluation) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    SieduEvalCategoria findById(SiudeEvalCategoriaPK id) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    SieduEvalCategoria create(SieduEvalCategoria record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void update(SieduEvalCategoria record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(SieduEvalCategoria	 record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(SiudeEvalCategoriaPK id) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    int deleteByEvaluation(Long idEvaluation) throws ServiceException;

}
