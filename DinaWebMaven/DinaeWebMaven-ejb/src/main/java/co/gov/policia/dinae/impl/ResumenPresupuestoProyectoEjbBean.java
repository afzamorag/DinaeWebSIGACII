package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoProyectoLocal;
import co.gov.policia.dinae.modelo.ResumenPresupuestoProyecto;
import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoJPAService;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cguzman
 */
@Stateless
public class ResumenPresupuestoProyectoEjbBean implements IResumenPresupuestoProyectoLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;
    private static final Logger LOG = LoggerFactory.getLogger(ResumenPresupuestoProyectoEjbBean.class);
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<ResumenPresupuestoProyecto> findAll() throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("ResumenPresupuestoProyecto.findAll", ResumenPresupuestoProyecto.class).getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    /**
     *
     * @throws JpaDinaeException
     */
    @Override
    public void removeAllByProyecto(Long idProyecto) throws JpaDinaeException {
        try {
            entityManager.createNamedQuery("ResumenPresupuestoProyecto.removeAllByProyecto")
                    .setParameter("idProyecto", idProyecto)
                    .executeUpdate();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    /**
     *
     * @param idProyecto
     * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
     * @throws JpaDinaeException
     */
    /*@Override
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void executeStoredProcedure(Long idProyecto) throws JpaDinaeException {
    try {
      Query query = entityManager.createNamedQuery("ResumenPresupuestoProyecto.calcularPresupuesto");
      query.setParameter("idProyecto", idProyecto);
      query.executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }*/
    @Override
    public void executeStoredProcedure(Long idProyecto) throws JpaDinaeException {
        LOG.debug("metodo: executeStoredProcedure() ->> parametros: idProyecto {}", idProyecto);
        try {
            Connection connection = this.datasource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("call ");
            sb.append("PKG_CALCULO_PRESUPUESTO.PRC_CREAR_ACTUALIZA_PRES");
            sb.append("(?)");
            CallableStatement cs = connection.prepareCall(sb.toString());
            cs.setLong(1, idProyecto);
            cs.executeQuery();
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
    public void executeStoredProcedureJDBC(Long idProyecto) throws JpaDinaeException {

        Connection connection = null;

        try {
            connection = entityManager.unwrap(Connection.class);

            CallableStatement prcProcedimientoAlmacenado = connection.prepareCall("{ call PKG_CALCULO_PRESUPUESTO.PRC_CREAR_ACTUALIZA_PRES(?) }");
            prcProcedimientoAlmacenado.setLong("p_id_proyecto", idProyecto.longValue());
            prcProcedimientoAlmacenado.execute();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new JpaDinaeException(ex.getMessage());
                }
            }
        }
    }

    /**
     *
     * @param idProyecto
     * @param estadoDescripcion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ResumenPresupuestoProyecto> findByProyecto(Long idProyecto, String estadoDescripcion) throws JpaDinaeException {
        try {

            if (estadoDescripcion == null) {

                return entityManager.createNamedQuery("ResumenPresupuestoProyecto.findByProyecto", ResumenPresupuestoProyecto.class)
                        .setParameter("idProyecto", idProyecto)
                        .getResultList();

            } else {

                return entityManager.createNamedQuery("ResumenPresupuestoProyecto.findByProyectoEstadoPresepuesto", ResumenPresupuestoProyecto.class)
                        .setParameter("idProyecto", idProyecto)
                        .setParameter("estadoPresupuesto", estadoDescripcion)
                        .getResultList();

            }

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }
}
