/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromisoPK;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface SieduObservacionCompromisoService {

    List<SieduObservacionCompromiso> findByIdCompromiso(Long idCompromiso) throws ServiceException;

    /**
     *
     * @return @throws ServiceException
     */
    List<SieduObservacionCompromiso> findAll() throws ServiceException;

    /**
     *
     * @param params
     * @return
     * @throws ServiceException
     */
    List<SieduObservacionCompromiso> findByFilter(Map<String, Object> params) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    SieduObservacionCompromiso findById(SieduObservacionCompromisoPK id) throws ServiceException;

    /**
     *
     * @param record
     * @return
     * @throws ServiceException
     */
    SieduObservacionCompromiso create(SieduObservacionCompromiso record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void update(SieduObservacionCompromiso record) throws ServiceException;

    /**
     *
     * @param record
     * @throws ServiceException
     */
    void delete(SieduObservacionCompromiso record) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    void delete(SieduObservacionCompromisoPK id) throws ServiceException;
}
