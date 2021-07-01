/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramasEscuelas;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author andres.zamorag
 */
@Stateless
public class SieduProgramasEscuelasJPAService implements SieduProgramasEscuelasService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduProgramacionDocenteJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
    }

    @Override
    public List<SieduProgramasEscuelas> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduProgramasEscuelas> list;
        try {
            list = sdo.getResultList(em, SieduProgramasEscuelas.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduProgramasEscuelas> findByEscuela(Long codEscuela) throws ServiceException {
        LOG.trace("método: findByEscuela()");
        List<SieduProgramasEscuelas> list;
        try {
            list = em.createNamedQuery(SieduProgramasEscuelas.FIND_BY_ESCUELA, SieduProgramasEscuelas.class)
                    .setParameter("codEscuela", codEscuela)
                    .getResultList();
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findByEscuela() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
