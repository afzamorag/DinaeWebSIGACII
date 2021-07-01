/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEvaluacionEvento;
import java.util.List;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduEvaluacionEventoService {

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    List<SieduEvaluacionEvento> findById(Long id) throws ServiceException;

}
