/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.TipoCompetenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Juan Jose Buzon
 */
@Stateless
public class CompetenciaJPAService implements CompetenciaService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetenciaJPAService.class);
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
    public List<SieduCompetencia> consultarCompetenciasPorTipo(
            TipoCompetenciaEnum tipo) throws ServiceException {
        LOG.trace(
                "metodo: consultarCompetenciasPorTipo() ->> params: tipo {}",
                tipo);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idTipo", tipo.getCode());

            return sdo.getResultListByNamedQuery(em,
                    SieduCompetencia.FIND_BY_TIPO, params);
        } catch (Exception ex) {
            LOG.error(
                    "metodo: consultarCompetenciasPorTipo() ->> mensaje: {}",
                    ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduCompetencia> consultarCompetencias() throws ServiceException {
        LOG.trace("metodo: consultarCompetencias()");
        List<SieduCompetencia> list;
        try {
            list = sdo.getResultList(em, SieduCompetencia.class);
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: consultarCompetencias() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
