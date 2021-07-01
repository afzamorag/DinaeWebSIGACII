/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramacionAlumno;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class SieduProgramacionAlumnoJPAService implements SieduProgramacionAlumnoService {

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
    public List<SieduProgramacionAlumno> findByIdProgDocente(BigDecimal progDocente) throws ServiceException {
        LOG.trace("método: findByIdProgDocente()");
        List<SieduProgramacionAlumno> lst = new ArrayList<>();
        try {
            lst = (List<SieduProgramacionAlumno>) em.createNamedQuery(SieduProgramacionAlumno.FIND_BY_ID_PROG_DOCENTE, SieduProgramacionAlumno.class)
                    .setParameter("idProgDocente", progDocente)
                    .getResultList();
            return lst;
        } catch (Exception ex) {
            LOG.error("metodo: findByIdProgDocente() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
