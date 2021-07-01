package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoEjecutadoLocal;
import co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutado;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cguzman
 */
@Stateless
public class ResumenPresupuestoEjecutadoEjbBean implements IResumenPresupuestoEjecutadoLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ResumenPresupuestoProyectoEjbBean.class);
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;

    /**
     *
     * @param idProyecto
     * @param idInformeAvance
     * @throws JpaDinaeException
     */
    @Override
    public void executeStoredProcedure(Long idProyecto, Long idInformeAvance) throws JpaDinaeException {
        LOG.debug("metodo: executeStoredProcedureCrearVersionProyecto() ->> parametros: idProyecto {}, idInformeAvance{} ", idProyecto, idInformeAvance);

        try {
            Connection connection = this.datasource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("call ");
            sb.append("PKG_CALCULO_PRESUPUESTO.PRC_CALCULAR_PRES_EJECUTA");
            sb.append("(?,?)");
            CallableStatement cs = connection.prepareCall(sb.toString());
            cs.setLong(1, idProyecto);
            cs.setLong(2, idInformeAvance);
            cs.executeQuery();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }

    }

    /**
     * @Override public void executeStoredProcedure(Long idProyecto, Long
     * idInformeAvance) throws JpaDinaeException { try { Query query =
     * entityManager.createNamedQuery("ResumenPresupuestoEjecutado.calcularPresupuestoEjecutado");
     * query.setParameter("idProyecto", idProyecto);
     * query.setParameter("idInformeAvance", idInformeAvance);
     * query.executeUpdate();
     *
     * } catch (Exception ex) { throw new JpaDinaeException(ex.getMessage()); }
  }
     */
    /**
     *
     * @param idProyecto
     * @param idInformeAvance
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ResumenPresupuestoEjecutado> findByProyectoInformeAvance(Long idProyecto, Long idInformeAvance) throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("ResumenPresupuestoEjecutado.findByProyectoInformeAvance", ResumenPresupuestoEjecutado.class)
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idInformeAvance", idInformeAvance)
                    .getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    /**
     *
     * @param idProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ResumenPresupuestoEjecutado> findByProyectoInformeAvanceAcum(Long idProyecto) throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("ResumenPresupuestoEjecutado.findByProyectoInformeAvanceAcum", ResumenPresupuestoEjecutado.class)
                    .setParameter("idProyecto", idProyecto)
                    .getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    /**
     *
     * @param idProyecto
     * @param idInformeAvance
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ResumenPresupuestoEjecutado> findByProyectoInformeAvanceAcumFinal(Long idProyecto, Long idInformeAvance) throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("ResumenPresupuestoEjecutado.findByProyectoInformeAvanceAcumFinal", ResumenPresupuestoEjecutado.class)
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idInformeAvance", idInformeAvance)
                    .getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    /**
     *
     * @param idProyecto
     * @throws JpaDinaeException
     */
    @Override
    public void executeStoredProcedureAcum(Long idProyecto) throws JpaDinaeException {
        LOG.debug("metodo: executeStoredProcedureCrearVersionProyecto() ->> parametros: idProyecto {}", idProyecto);

        try {
            Connection connection = this.datasource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("call ");
            sb.append("PKG_CALCULO_PRESUPUESTO.PRC_CALCULAR_PRES_ACUM");
            sb.append("(?)");
            CallableStatement cs = connection.prepareCall(sb.toString());
            cs.setLong(1, idProyecto);
            cs.executeQuery();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }

    }

}
