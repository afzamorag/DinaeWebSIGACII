/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Alumnos;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author andres.zamorag
 */
@Stateless
public class SieduAlumnosJPAService implements SieduAlumnosService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduAlumnosJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
    }

    @Override
    public List<Alumnos> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<Alumnos> list;
        try {
            list = sdo.getResultList(em, Alumnos.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public Alumnos findByIdentificacion(String identificacion) throws ServiceException {
        LOG.trace("metodo: findByIdentificacion ->> parametros: identificacion {}", identificacion);
        try {
            Alumnos alumno = (Alumnos) em.createNamedQuery(Alumnos.FIND_BY_IDENTIFICACION, Alumnos.class)
                    .setParameter("identificacion", identificacion.trim())
                    .setHint("eclipselink.refresh", "true")
                    .setMaxResults(1)
                    .getSingleResult();
            return alumno;
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        } catch (Exception ex) {
            LOG.error("metodo: findByIdentificacion() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
