/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.HorasDocenteSigac;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduHorasDocenteJPAService implements SieduHorasDocenteService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduHorasDocenteJPAService.class);
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
    public List<HorasDocenteSigac> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<HorasDocenteSigac> list;
        try {
            list = sdo.getResultList(em, HorasDocenteSigac.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<HorasDocenteSigac> findByFechasIdentificacion(Date fecha_ini, Date fecha_fin, Date fecha_crea, Long identificacion, Long codEscuela) throws ServiceException {
        LOG.trace("metodo: findByFecha(Date fecha_ini, Date fecha_fin)");
        List<HorasDocenteSigac> list;
        try {
            list = em.createNamedQuery("HorasDocenteSigac.findByFecha")
                    .setParameter("fecha_ini", fecha_ini, TemporalType.DATE)
                    .setParameter("fecha_fin", fecha_fin, TemporalType.DATE)
                    .setParameter("fecha_crea", fecha_crea, TemporalType.DATE)
                    .setParameter("identificacion", identificacion)
                    .setParameter(("codEscuela"), codEscuela)
                    .getResultList();
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findByFecha(Date fecha_ini, Date fecha_fin) ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<HorasDocenteSigac> findAllByFechas(Date fecha_ini, Date fecha_fin, Date fecha_crea) throws ServiceException {
        LOG.trace("metodo: findByFecha(Date fecha_ini, Date fecha_fin)");
        List<HorasDocenteSigac> list;
        try {
            list = em.createNamedQuery(HorasDocenteSigac.FIND_ALL_BY_FECHA)
                    .setParameter("fecha_ini", fecha_ini, TemporalType.DATE)
                    .setParameter("fecha_fin", fecha_fin, TemporalType.DATE)
                    .setParameter("fecha_crea", fecha_crea, TemporalType.DATE)
                    .getResultList();
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findByFecha(Date fecha_ini, Date fecha_fin) ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<HorasDocenteSigac> findByPeriodo(Date fecha_ini, Date fecha_fin, Date fecha_crea) throws ServiceException {
        LOG.trace("metodo: findByPeriodo(Date fecha_ini, Date fecha_fin, Date fecha_crea)");
        Long id = 1L;
        List<Object[]> periodo;
        HorasDocenteSigac horasDocente = new HorasDocenteSigac();
        List<HorasDocenteSigac> list = new ArrayList<>();
        HorasDocenteSigac doc = new HorasDocenteSigac();
        try {
            Query q = em.createNativeQuery("SELECT h.proceso, SUM(h.dictadas), SUM(h.liquidar)\n"
                    + "  FROM (SELECT t.identificacion,\n"
                    + "               t.nombres,\n"
                    + "               t.cod_escuela,\n"
                    + "               t.proceso,\n"
                    + "               SUM(t.horas) dictadas,\n"
                    + "               CASE\n"
                    + "                 WHEN (sum(t.horas)) > 5 THEN\n"
                    + "                  5\n"
                    + "                 ELSE\n"
                    + "                  SUM(t.horas)\n"
                    + "               END liquidar,\n"
                    + "               t.semana\n"
                    + "          FROM horas_docente_sigac t\n"
                    + "         WHERE TO_DATE(TO_CHAR(CAST(t.f_imparte AS DATE), 'DD/MON/YYYY')) BETWEEN ? AND ?\n"
                    + "           AND TO_DATE(TO_CHAR(CAST(t.f_crea AS DATE), 'DD/MON/YYYY')) BETWEEN ? AND ?\n"
                    + "         GROUP BY t.identificacion,\n"
                    + "                  t.nombres,\n"
                    + "                  t.cod_escuela,\n"
                    + "                  t.proceso,\n"
                    + "                  t.horas,\n"
                    + "                  t.semana) h\n"
                    + " GROUP BY h.proceso");
            q.setParameter(1, fecha_ini, TemporalType.DATE);
            q.setParameter(2, fecha_fin, TemporalType.DATE);
            q.setParameter(3, fecha_ini, TemporalType.DATE);
            q.setParameter(4, fecha_crea, TemporalType.DATE);
            periodo = q.getResultList();
            em.clear();
            for (Object[] a : periodo) {
                doc.setProceso(a[0].toString());
                doc.setHorasDictadas(Long.valueOf(a[1].toString()));
                doc.setHorasLiquidadas(Long.valueOf(a[2].toString()));
                doc.setId(String.valueOf(id));
                list.add(doc);
                doc = new HorasDocenteSigac();
                id++;
            }
            id++;
        } catch (Exception ex) {
            LOG.error("metodo: findByFecha(Date fecha_ini, Date fecha_fin) ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<HorasDocenteSigac> findByFechas(Date fecha_ini, Date fecha_fin, Date fecha_crea) throws ServiceException {
        LOG.trace("metodo: findByFecha(Date fecha_ini, Date fecha_fin)");
        List<Object[]> list;
        List<HorasDocenteSigac> listDoc = new ArrayList<>();
        int id = 0;
        HorasDocenteSigac doc = new HorasDocenteSigac();
        try {
            Query q = em.createNativeQuery("SELECT h.identificacion,\n"
                    + "       h.nombres,\n"
                    + "       h.escuela,\n"
                    + "       h.proceso,\n"
                    + "       SUM(h.dictadas),\n"
                    + "       h.cod_escuela,\n"
                    + "       SUM(h.liquidar)\n"
                    + "  FROM (SELECT t.identificacion,\n"
                    + "               t.nombres,\n"
                    + "               t.cod_escuela,\n"
                    + "               t.escuela,\n"
                    + "               t.proceso,\n"
                    + "               SUM(t.horas) dictadas,\n"
                    + "               CASE\n"
                    + "                 WHEN (sum(t.horas)) > 5 THEN\n"
                    + "                  5\n"
                    + "                 ELSE\n"
                    + "                  SUM(t.horas)\n"
                    + "               END liquidar,\n"
                    + "               t.semana\n"
                    + "          FROM horas_docente_sigac t\n"
                    + "         WHERE t.f_imparte BETWEEN ? AND ?\n"
                    + "           AND t.f_crea BETWEEN ? AND ?\n"
                    + "         GROUP BY t.identificacion,\n"
                    + "                  t.nombres,\n"
                    + "                  t.escuela,\n"
                    + "                  t.cod_escuela,\n"
                    + "                  t.proceso,\n"
                    + "                  t.horas,\n"
                    + "                  t.semana) h\n"
                    + " GROUP BY h.identificacion,\n"
                    + "          h.nombres,\n"
                    + "          h.escuela,\n"
                    + "          h.cod_escuela,\n"
                    + "          h.proceso");
            q.setParameter(1, fecha_ini, TemporalType.DATE);
            q.setParameter(2, fecha_fin, TemporalType.DATE);
            q.setParameter(3, fecha_ini, TemporalType.DATE);
            q.setParameter(4, fecha_crea, TemporalType.DATE);
            list = q.getResultList();
            em.clear();
            for (Object[] a : list) {
                doc.setIdentificacion(Long.valueOf(a[0].toString()));
                doc.setNombres(a[1].toString());
                doc.setEscuela(a[2].toString());
                doc.setProceso(a[3].toString());
                doc.setHorasDictadas(Long.valueOf(a[4].toString()));
                doc.setCodEscuela(Long.valueOf(a[5].toString()));
                doc.setId(String.valueOf(id));
                doc.setHorasLiquidadas(Long.valueOf(a[6].toString()));
                listDoc.add(doc);
                doc = new HorasDocenteSigac();
                id = id + 1;
            }
        } catch (Exception ex) {
            LOG.error("metodo: findByFecha(Date fecha_ini, Date fecha_fin) ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return listDoc;
    }

}
