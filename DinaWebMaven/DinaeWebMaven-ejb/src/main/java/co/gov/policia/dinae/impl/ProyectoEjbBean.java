package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.CompromisoDTO;
import co.gov.policia.dinae.dto.HistorialEstadoProyectosMigradosDTO;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEquiposInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IEventoProyectoLocal;
import co.gov.policia.dinae.interfaces.IExcepcionCompromisoLocal;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IOtrosGastosProyectoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ITipoGastoEventoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IViajesProyectoLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.EquiposInvestigacion;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import co.gov.policia.dinae.modelo.EvaluadoresProyectoMigrados;
import co.gov.policia.dinae.modelo.EventoProyecto;
import co.gov.policia.dinae.modelo.ExcepcionesCompromiso;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyecto;
import co.gov.policia.dinae.modelo.InstitucionesProyecto;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.OtrosGastosProyecto;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
import co.gov.policia.dinae.modelo.TemaProyecto;
import co.gov.policia.dinae.modelo.TipoGastoEvento;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.Versiones;
import co.gov.policia.dinae.modelo.ViajesProyecto;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.io.Serializable;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.CopyGroup;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class ProyectoEjbBean implements IProyectoLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;

    @EJB
    private IUnidadPolicialLocal iUnidadPolicialLocal;

    @EJB
    private ILineaLocal iLineaLocal;

    @EJB
    private IEvaluacionProyectoLocal iEvaluacionProyectoLocal;

    @EJB
    private IConstantesLocal iConstantesLocal;

    @EJB
    private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;

    @EJB
    private ICompromisoProyectoLocal iCompromisoProyectoLocal;

    @EJB
    private IFuenteProyectoLocal iFuente;

    @EJB
    private IEquiposInvestigacionLocal iEquipos;

    @EJB
    private IEventoProyectoLocal iEvento;

    @EJB
    private IViajesProyectoLocal iViajesProyecto;

    @EJB
    private IOtrosGastosProyectoLocal iOtrosGastosProyecto;

    @EJB
    private ITipoGastoEventoLocal iTipoGastoEventoLocal;

    @EJB
    private IExcepcionCompromisoLocal iExcepcionCompromisoLocal;

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ResumenPresupuestoProyectoEjbBean.class);
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Proyecto> getListaProyectoPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("Proyecto.findAllPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Proyecto obtenerProyectoPorId(Long idProyecto) throws JpaDinaeException {
        try {

            //CARGAMOS LOS DATOS BASICOS DEL PROYECTO, QUE SON CONSULTADOS CONSTANTEMENTE
            //Y QUE NO SON TAN PESADOS.
            //ESTOS CON EL FIN DE EVITAR UN LAZIINITIALIZATION EXCEPTION
            Proyecto proyecto = entityManager.find(Proyecto.class, idProyecto);

            if (proyecto.getUnidadPolicial() != null && proyecto.getUnidadPolicial().getIdUnidadPolicial() != null) {

                proyecto.setUnidadPolicial(entityManager.find(UnidadPolicial.class, proyecto.getUnidadPolicial().getIdUnidadPolicial()));

            }

            if (proyecto.getEstado() != null && proyecto.getEstado().getIdConstantes() != null) {

                proyecto.setEstado(entityManager.find(Constantes.class, proyecto.getEstado().getIdConstantes()));

            }

            if (proyecto.getEstadoImplementacion() != null && proyecto.getEstadoImplementacion().getIdConstantes() != null) {

                proyecto.setEstadoImplementacion(entityManager.find(Constantes.class, proyecto.getEstadoImplementacion().getIdConstantes()));

            }

            if (proyecto.getPeriodo() != null && proyecto.getPeriodo().getIdPeriodo() != null) {

                proyecto.setPeriodo(entityManager.find(Periodo.class, proyecto.getPeriodo().getIdPeriodo()));

            }

            return proyecto;

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param proyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Proyecto guardarProyecto(Proyecto proyecto) throws JpaDinaeException {
        try {

            proyecto.setFechaActualizacion(new Date());
            if (proyecto.getIdProyecto() == null) {

                proyecto.setFechaRegistro(new Date());
                entityManager.persist(proyecto);

            } else {

                proyecto.setFechaActualizacion(new Date());
                entityManager.merge(proyecto);

            }

            return proyecto;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param proyecto
     * @param idCompromisoProyecto
     * @param idEstadoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Proyecto guardarProyectoYCompromiso(Proyecto proyecto, Long idCompromisoProyecto, Long idEstadoCompromiso) throws JpaDinaeException {
        try {

            //ACTUALIZAMOS EL PROYECTO
            proyecto = guardarProyecto(proyecto);

            //ACTUALIZAMOS EL COMPROMISO
            if (idCompromisoProyecto != null) {

                CompromisoProyecto compromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);
                compromisoProyecto.setEstado(new Constantes(idEstadoCompromiso));

                iCompromisoProyectoLocal.agregarCompromisoProyecto(compromisoProyecto);
            }

            proyecto = entityManager.find(Proyecto.class, proyecto.getIdProyecto());

            return proyecto;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoDTOPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param listaIdEstadoConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoDTOPorPeriodoYestado(Long idPeriodo, List<Long> listaIdEstadoConvocatoria) throws JpaDinaeException {

        try {

            String sql = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO"
                    + "( p.idProyecto, p.tituloPropuesto, p.linea.nombre, p.linea.areaCienciaTecnologia.nombre, p.estado.idConstantes, p.estado.valor ) "
                    + " FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoConvocatoria, false);

            return entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param listaIdEstadoConvocatoria
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoDTOPorPeriodoYestadoYunidadPolicial(Long idPeriodo, List<Long> listaIdEstadoConvocatoria, Long idUnidadPolicial) throws JpaDinaeException {

        try {

            String sql = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO"
                    + "( p.idProyecto, p.tituloPropuesto, p.linea.nombre, p.linea.areaCienciaTecnologia.nombre, p.estado.idConstantes, p.estado.valor ) "
                    + " FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoConvocatoria, false);

            return entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param listaIdEstadoConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Proyecto> getListaProyectoPorPeriodoYestadoYestado(Long idPeriodo, List<Long> listaIdEstadoConvocatoria) throws JpaDinaeException {

        try {

            String sql = "SELECT p FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoConvocatoria, false);

            return entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param listaIdsConvocatoria
     * @param idEstadoActalizar
     * @throws JpaDinaeException
     */
    @Override
    public void actualizarEstadoListaIdProyecto(List<Long> listaIdsConvocatoria, Long idEstadoActalizar) throws JpaDinaeException {

        try {

            for (Long unIdProyecto : listaIdsConvocatoria) {

                //CONSULTAMOS EL PROYECTO
                Proyecto proyecto = entityManager.find(Proyecto.class, unIdProyecto);
                //CAMBIAMOS EL ESTADO
                proyecto.setEstado(new Constantes(idEstadoActalizar));
                //ACTAULIZAMOS
                entityManager.merge(proyecto);

            }

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idConvocatoria
     * @param listaIdEstadoPropuestaConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int cuentaNumeroPropuestasConvocatoriaPorConvocatoriaYunidadPolicialYListaIdEstado(Long idConvocatoria, Long idUnidadPolicial, List<Long> listaIdEstadoPropuestaConvocatoria) throws JpaDinaeException {

        try {

            String sql = "SELECT COUNT(p) FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoPropuestaConvocatoria, false);

            Object cuenta = entityManager.createQuery(sql)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idPeriodo", idConvocatoria)
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idConvocatoria
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int cuentaNumeroPropuestasConvocatoriaPorConvocatoriaYunidadPolicial(Long idConvocatoria, Long idUnidadPolicial) throws JpaDinaeException {

        try {

            String sql = "SELECT COUNT(p) FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ";

            Object cuenta = entityManager.createQuery(sql)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idPeriodo", idConvocatoria)
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idConvocatoria
     * @param listaIdEstadoPropuestaConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Proyecto> getPropuestasConvocatoriaPorConvocatoriaYunidadPolicialYListaIdEstado(Long idConvocatoria, Long idUnidadPolicial, List<Long> listaIdEstadoPropuestaConvocatoria) throws JpaDinaeException {

        try {

            String sql = "SELECT p FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoPropuestaConvocatoria, false);

            return entityManager.createQuery(sql)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idPeriodo", idConvocatoria)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param propuestaConvocaoriaProyecto
     * @throws JpaDinaeException
     */
    @Override
    public void enviarPropuestaConvocatoriaVicin(List<Proyecto> propuestaConvocaoriaProyecto) throws JpaDinaeException {
        try {

            for (Proyecto unProyecto : propuestaConvocaoriaProyecto) {

                unProyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ENVIADA_A_VICIN));
                entityManager.merge(unProyecto);

            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param propuestaConvocaoriaProyecto
     * @param identificacion
     * @param nombreCompleto
     * @throws JpaDinaeException
     */
    @Override
    public void enviarPropuestaConvocatoriaAgrupoInvestigacion(List<Proyecto> propuestaConvocaoriaProyecto, String identificacion, String nombreCompleto) throws JpaDinaeException {
        try {
            for (Proyecto unProyecto : propuestaConvocaoriaProyecto) {

                if (unProyecto.getIdContantes() == null
                        || unProyecto.getIdContantes() == -1L
                        || unProyecto.getIdContantes().equals(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ACEPTADO_POR_JEFE_DE_UNIDAD)) {
                    //SI EL USUARIO PRESIONA EL BTN ENVIAR AL GRUPO DE INVESTIGACION
                    //NO SE TIENEN EN CUENTA LAS ACEPTADA
                    continue;
                }
                unProyecto.setEstado(new Constantes(unProyecto.getIdContantes()));

                Comentario comentario = new Comentario();
                comentario.setProyecto(unProyecto);
                comentario.setComentario(unProyecto.getComentario());
                comentario.setFecha(new Date());
                comentario.setAutor(nombreCompleto);
                comentario.setIdentificacion(identificacion);

                //ACTUALIZAMOS EL ESTADO DE LA PROPUESTA CONVOCATORIA
                entityManager.merge(unProyecto);
                //GUARDAMOS EL COMENTARIO
                entityManager.persist(comentario);
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<UnidadPolicialDTO> getListaUnidadesPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("ProyectoUnidadPolicialDTO.findAllUnidadaPolicialPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idPeriodo
     * @param listaIsEstadoFiltro
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<UnidadPolicialDTO> getListaUnidadesPorPeriodoYlistaIdestadoFiltro(Long idPeriodo, List<Long> listaIsEstadoFiltro) throws JpaDinaeException {

        try {

            String sql = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO( "
                    + " p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre) "
                    + " FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIsEstadoFiltro, false)
                    + " ORDER BY p.unidadPolicial.nombre ASC";

            return entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYestadoFinanciarYnoAprobada(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodoYestadoFinanciarYNoAprobada")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param listaIdEstado
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarProyectoPropuestaConvocatoriaDTOPorPeriodoYlistaEstado(Long idPeriodo, List<Long> listaIdEstado) throws JpaDinaeException {

        try {

            String sql = "SELECT COUNT(p) FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstado, false);

            Object cuenta = entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    private Comparator comparadorPropuestaFinancia() {

        Comparator<ProyectoDTO> cProyectoDTO = new Comparator<ProyectoDTO>() {

            @Override
            public int compare(ProyectoDTO o1, ProyectoDTO o2) {
                return o1.getSumaValorCriterioIngresado().compareTo(o2.getSumaValorCriterioIngresado());
            }
        };
        return cProyectoDTO;

    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOaFinanciarPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            List<ProyectoDTO> listaPropuestasFinanciar = entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaConvocatoriaAfinanciarPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

            //CONSULTAMOS LOS PORCENTAJES PARA CADA PROPUESTA
            for (ProyectoDTO proyectoDTO : listaPropuestasFinanciar) {

                BigDecimal valorTotalIngresado = iEvaluacionProyectoLocal.getValorTotalIngresadoEvaluacionProyectoPorProyecto(proyectoDTO.getId());
                proyectoDTO.setSumaValorCriterioIngresado(valorTotalIngresado.intValue());
            }

            //ORDENADAS DE MAYOR A MENOR DE ACUERDO CON LA CALIFICACIÓN
            Collections.sort(listaPropuestasFinanciar, comparadorPropuestaFinancia());
            Collections.reverse(listaPropuestasFinanciar);

            return listaPropuestasFinanciar;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param listaIsEstadoFiltro
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOaFinanciarPorPeriodoYlistaIdEstado(Long idPeriodo, List<Long> listaIsEstadoFiltro) throws JpaDinaeException {

        try {

            String sql = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( "
                    + " p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, "
                    + " p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor, "
                    + " p.estadoTemporalFinancia, p.valorFinanciar ) "
                    + " FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIsEstadoFiltro, false)
                    + " AND p.idProyecto IN "
                    + " (SELECT DISTINCT ep.proyecto.idProyecto FROM EvaluacionProyecto ep WHERE ep.proyecto.periodo.idPeriodo = :idPeriodo)";

            List<ProyectoDTO> listaPropuestasFinanciar = entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

            //CONSULTAMOS LOS PORCENTAJES PARA CADA PROPUESTA
            for (ProyectoDTO proyectoDTO : listaPropuestasFinanciar) {

                BigDecimal valorTotalIngresado = iEvaluacionProyectoLocal.getValorTotalIngresadoEvaluacionProyectoPorProyecto(proyectoDTO.getId());
                proyectoDTO.setSumaValorCriterioIngresado(valorTotalIngresado.intValue());
            }

            //ORDENADAS DE MAYOR A MENOR DE ACUERDO CON LA CALIFICACIÓN
            Collections.sort(listaPropuestasFinanciar, comparadorPropuestaFinancia());
            Collections.reverse(listaPropuestasFinanciar);

            return listaPropuestasFinanciar;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYunidadPolicial(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodoYunidadPolicial")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param listaIsEstadoFiltro
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodo(Long idPeriodo, List<Long> listaIsEstadoFiltro) throws JpaDinaeException {

        try {

            String sql = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO("
                    + " p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, "
                    + " p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor ) "
                    + " FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo "
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIsEstadoFiltro, false)
                    + " ORDER BY p.idProyecto ASC ";

            return entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYunidadPolicial(Long idPeriodo, Long idUnidadPolicial, List<Long> listaIsEstadoFiltro) throws JpaDinaeException {

        try {

            String sql = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO"
                    + "( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, "
                    + " p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor ) "
                    + " FROM Proyecto p "
                    + " WHERE p.periodo.idPeriodo = :idPeriodo"
                    + " AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"
                    + " AND p.estado.idConstantes IN " + UtilJPA.generateCollection(listaIsEstadoFiltro, false)
                    + " ORDER BY p.idProyecto ASC ";

            return entityManager.createQuery(sql)
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @param idEstado
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYestado(Long idPeriodo, Long idEstado) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodoYestado")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idEstado", idEstado)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param listaPropuestasActualizar
     * @throws JpaDinaeException
     */
    @Override
    public void actualizarEstadoYpresupuestoPropuestasConvocatoriasFinancia(List<ProyectoDTO> listaPropuestasActualizar) throws JpaDinaeException {

        try {

            BigDecimal valorFinancia;

            for (ProyectoDTO proyectoDTO : listaPropuestasActualizar) {

                valorFinancia = BigDecimal.ZERO;
                if (IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_APROBADA.equals(proyectoDTO.getEstadoTemporalFinancia())) {

                    valorFinancia = proyectoDTO.getValorFinanciar();

                }
                entityManager.createNamedQuery("Proyecto.UpdateEstadoTemporalFinanciYPresupuestoAprobado")
                        .setParameter("estadoTemporalFinancia", proyectoDTO.getEstadoTemporalFinancia())
                        .setParameter("valorFinanciar", valorFinancia)
                        .setParameter("idProyecto", proyectoDTO.getId())
                        .setParameter("presupuestoSolicitado", proyectoDTO.getPresupuestoSolicitado())
                        .executeUpdate();
            }
        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param listaPropuestasActualizar
     * @param perfilUsuarioDTO
     * @throws JpaDinaeException
     */
    @Override
    public Set<Long> actualizarEstadoPropuestasConvocatoriasPublicar(List<ProyectoDTO> listaPropuestasActualizar, PerfilUsuarioDTO perfilUsuarioDTO) throws JpaDinaeException {

        try {

            if (listaPropuestasActualizar == null || listaPropuestasActualizar.isEmpty()) {
                //SI LA LISTA ESTA VACIA, DETENEMOS EL PROCESO
                return null;
            }

            Set<Long> listaUnidadesPoliciales = new HashSet<Long>();

            String iniciaCodigoConv = KeyPropertiesFactory.getInstance().value("cu_co_4_codigo_proyecto_inicia_generacion");
            if (iniciaCodigoConv.contains("-----NOT FOUND-----")) {
                throw new JpaDinaeException(KeyPropertiesFactory.getInstance().value("cu_co_4_error_no_existe_codigo_proyecto_inicia_generacion"));
            }

            //TODAS LAS PROPUESTA DE CONVOCATORIAS PETENECEN A LA MISMA PROPUESTA
            Proyecto proyectoTMP = entityManager.find(Proyecto.class, listaPropuestasActualizar.get(0).getId());
            int contarProyecto = contarTodosLosProyectoPorConvocatoria(proyectoTMP.getPeriodo().getIdPeriodo());

            Constantes constantes = iConstantesLocal.getConstantesPorIdConstante(IConstantes.DURACION_PROYECTOS_DE_CONVOCATORIA);
            int numeroMesesEstimacionProyecto = Integer.parseInt(constantes.getValor());

            for (ProyectoDTO proyectoDTO : listaPropuestasActualizar) {

                Proyecto propuestaConvocatoria = entityManager.find(Proyecto.class, proyectoDTO.getId());

                if (proyectoDTO.getEstadoTemporalFinancia().equals(IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_NO_APROBADA)) {

                    propuestaConvocatoria.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_APROBADA));
                    propuestaConvocatoria.setValorFinanciar(BigDecimal.ZERO);
                    //SE ACTUALIZA LA PROPUESTA DE CONVOCATORIA, ESTA NO TIENE CODIGO
                    entityManager.merge(propuestaConvocatoria);

                    //CONTINUAMOS CON EL SIGUIENTE REGISTRO YA QUE LA PROPUESTA NO FUE APROBADA PARA FINANCIAR
                    continue;

                }

                //PROPUESTA A FINANCIAR
                propuestaConvocatoria.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_PARA_FINALIZAR));
                propuestaConvocatoria.setValorFinanciar(proyectoDTO.getValorFinanciar());
                //SE ACTUALIZA LA PROPUESTA DE CONVOCATORIA, ESTA NO TIENE CODIGO
                entityManager.merge(propuestaConvocatoria);

                //GENERAMOS EL PROYECTO
                contarProyecto += 1;
                Calendar cfechaInicio = Calendar.getInstance();
                cfechaInicio.setTime(propuestaConvocatoria.getPeriodo().getFechaInicio());

                //GENERACION DEL CODIGO DEL PROYECTO
                //CONV - [Consecutivo de proyectos para la convocatoria][Año]- [Código interno de la Unidad Policial o Escuela][Número de la convocatoria]
                StringBuilder codigoProyecto = new StringBuilder(iniciaCodigoConv);
                codigoProyecto.append("-"); //CONV -
                codigoProyecto.append(String.valueOf(contarProyecto));//[Consecutivo de proyectos para la convocatoria]
                codigoProyecto.append(String.valueOf(cfechaInicio.get(Calendar.YEAR)));//[Año]
                codigoProyecto.append("-"); //-
                codigoProyecto.append(propuestaConvocatoria.getUnidadPolicial().getSiglaFisica()); //[Código interno de la Unidad Policial o Escuela]
                codigoProyecto.append(String.valueOf(propuestaConvocatoria.getPeriodo().getConcecutivo()));//[Número de la convocatoria]

                //SE CREA EL PROYECTO // ESTA SI TIENE CODIGO, ESTO ES LO QUE LO DIFERENCIA DE UNA PROPUESTA DE CONVOCATORIA
                //CREANDO UN NUEVO REGISTRO DE PROYECTO
                CopyGroup grupoConfiguracionCopiaObjeto = new CopyGroup();
                grupoConfiguracionCopiaObjeto.setShouldResetPrimaryKey(true);

                Date fechaHoy = new Date();

                Proyecto propuestaConvocatoriaSeConvienteEnProyecto = (Proyecto) entityManager.unwrap(JpaEntityManager.class).copy(propuestaConvocatoria, grupoConfiguracionCopiaObjeto);
                propuestaConvocatoriaSeConvienteEnProyecto.setCodigoProyecto(codigoProyecto.toString());
                propuestaConvocatoriaSeConvienteEnProyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION));
                propuestaConvocatoriaSeConvienteEnProyecto.setPropuestaConvocatoria(propuestaConvocatoria);
                propuestaConvocatoriaSeConvienteEnProyecto.setFechaEstimadaInicio(fechaHoy);
                Calendar fechaFinalEstimadaProyecto = Calendar.getInstance();
                fechaFinalEstimadaProyecto.setTime(fechaHoy);
                fechaFinalEstimadaProyecto.add(Calendar.MONTH, numeroMesesEstimacionProyecto);
                propuestaConvocatoriaSeConvienteEnProyecto.setFechaEstimadaFinalizacion(fechaFinalEstimadaProyecto.getTime());
                propuestaConvocatoriaSeConvienteEnProyecto.setPresupuestoSolicitado(proyectoDTO.getPresupuestoSolicitado());
                propuestaConvocatoriaSeConvienteEnProyecto.setValorFinanciar(proyectoDTO.getValorFinanciar());

                //CREAMOS LOS COMPROMISOS PROYECTOS
                List<CompromisoProyecto> listaCompromisosProyecto = new ArrayList<CompromisoProyecto>();
                //CONSULTAMOS LOS COMPROMISOS DE ESTE PERIODO
                List<CompromisoPeriodo> listaComprimiso = iCompromisoPeriodoLocal.buscarCompromisoPeriodo(
                        propuestaConvocatoria.getPeriodo().getIdPeriodo());

                for (CompromisoPeriodo unCompromisoPeriodo : listaComprimiso) {

                    CompromisoProyecto compromisoProyecto = new CompromisoProyecto();
                    compromisoProyecto.setCompromisoPeriodo(unCompromisoPeriodo);
                    compromisoProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
                    compromisoProyecto.setFechaCreacion(fechaHoy);
                    compromisoProyecto.setMaquina(perfilUsuarioDTO.getMaquinaDTO().getIpLoginRemotoUsuario());
                    compromisoProyecto.setUsuarioRegistro(perfilUsuarioDTO.getIdentificacion());
                    compromisoProyecto.setUsuarioRolRegistra(new UsuarioRol(
                            perfilUsuarioDTO.getRolUsuarioPorIdRolDTO(
                                    IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_CONVOCATORIAS_EN_LA_VICIN).getIdUsuarioRol()));

                    listaCompromisosProyecto.add(compromisoProyecto);

                }

                //DUPLICAMOS LOS DATOS DE LAS PESTAÑAS DEL CU-PR-01
                List<SemilleroProyecto> semilleroProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getSemilleroProyectoList();
                List<SemilleroProyecto> semilleroProyectoCopiaLimpiaList = new ArrayList<SemilleroProyecto>();

                for (SemilleroProyecto semilleroProyecto : semilleroProyectoList) {

                    SemilleroProyecto nuevaSemilleroProyecto = (SemilleroProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(semilleroProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaSemilleroProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    semilleroProyectoCopiaLimpiaList.add(nuevaSemilleroProyecto);
                }

                List<FuenteProyecto> fuenteProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getFuenteProyectoList();
                List<FuenteProyecto> fuenteProyectoCopiaLimpiaList = new ArrayList<FuenteProyecto>();
                for (FuenteProyecto fuenteProyecto : fuenteProyectoList) {

                    FuenteProyecto nuevaFuenteProyecto = (FuenteProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(fuenteProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaFuenteProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    fuenteProyectoCopiaLimpiaList.add(nuevaFuenteProyecto);
                }

                List<GrupoInvestigacionProyecto> grupoInvestProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getGrupoInvestProyectoList();
                List<GrupoInvestigacionProyecto> grupoInvestigacionProyectoCopiaLimpiaList = new ArrayList<GrupoInvestigacionProyecto>();
                for (GrupoInvestigacionProyecto grupoInvestigacionProyecto : grupoInvestProyectoList) {

                    GrupoInvestigacionProyecto nuevaGrupoInvestigacionProyecto = (GrupoInvestigacionProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(grupoInvestigacionProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaGrupoInvestigacionProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    grupoInvestigacionProyectoCopiaLimpiaList.add(nuevaGrupoInvestigacionProyecto);
                }

                List<TemaProyecto> temaProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getTemaProyectoList();
                List<TemaProyecto> temaProyectoCopiaLimpiaList = new ArrayList<TemaProyecto>();
                for (TemaProyecto temaProyecto : temaProyectoList) {

                    TemaProyecto nuevaTemaProyecto = (TemaProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(temaProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaTemaProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    temaProyectoCopiaLimpiaList.add(nuevaTemaProyecto);
                }

                List<InvestigadorProyecto> investigadoresProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getInvestigadoresProyectoList();
                List<InvestigadorProyecto> investigadoresProyectoCopiaLimpiaList = new ArrayList<InvestigadorProyecto>();
                for (InvestigadorProyecto investigadorProyecto : investigadoresProyectoList) {

                    InvestigadorProyecto nuevaInvestigadorProyecto = (InvestigadorProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(investigadorProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaInvestigadorProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    investigadoresProyectoCopiaLimpiaList.add(nuevaInvestigadorProyecto);

                }

                List<EjecutorNecesidad> ejecutorNecesidadCopiaLimpiaList = new ArrayList<EjecutorNecesidad>();
                EjecutorNecesidad ejecutorNecesidadLocal = new EjecutorNecesidad();
                ejecutorNecesidadLocal.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                ejecutorNecesidadLocal.setRol(new Constantes(IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE));
                ejecutorNecesidadLocal.setUnidadPolicial(propuestaConvocatoriaSeConvienteEnProyecto.getUnidadPolicial());
                ejecutorNecesidadCopiaLimpiaList.add(ejecutorNecesidadLocal);

                List<EvaluacionProyecto> evaluacionProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getEvaluacionProyectoList();
                List<EvaluacionProyecto> evaluacionProyectoCopiaLimpiaList = new ArrayList<EvaluacionProyecto>();
                for (EvaluacionProyecto evaluacionProyecto : evaluacionProyectoList) {

                    EvaluacionProyecto nuevaEvaluacionProyecto = (EvaluacionProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(evaluacionProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaEvaluacionProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    evaluacionProyectoCopiaLimpiaList.add(nuevaEvaluacionProyecto);
                }

                List<IndicadoresProyecto> indicadoresProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getIndicadoresProyectoList();
                List<IndicadoresProyecto> indicadoresProyectoCopiaLimpiaList = new ArrayList<IndicadoresProyecto>();
                for (IndicadoresProyecto indicadoresProyecto : indicadoresProyectoList) {

                    IndicadoresProyecto nuevaIndicadoresProyecto = (IndicadoresProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(indicadoresProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaIndicadoresProyecto.setCasoUso(IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);
                    nuevaIndicadoresProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    indicadoresProyectoCopiaLimpiaList.add(nuevaIndicadoresProyecto);

                }

                List<InstitucionesProyecto> institucionesProyectoList = propuestaConvocatoriaSeConvienteEnProyecto.getInstitucionesProyectoList();
                List<InstitucionesProyecto> institucionesProyectoCopiaLimpiaList = new ArrayList<InstitucionesProyecto>();
                for (InstitucionesProyecto institucionesProyecto : institucionesProyectoList) {

                    InstitucionesProyecto nuevaInstitucionesProyecto = (InstitucionesProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(institucionesProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaInstitucionesProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    institucionesProyectoCopiaLimpiaList.add(nuevaInstitucionesProyecto);
                }

                propuestaConvocatoriaSeConvienteEnProyecto.setCompromisoProyectoList(listaCompromisosProyecto);
                propuestaConvocatoriaSeConvienteEnProyecto.setSemilleroProyectoList(semilleroProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setFuenteProyectoList(fuenteProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setGrupoInvestProyectoList(grupoInvestigacionProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setTemaProyectoList(temaProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setInvestigadoresProyectoList(investigadoresProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setEjecutorNecesidadList(ejecutorNecesidadCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setEvaluacionProyectoList(evaluacionProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setIndicadoresProyectoList(indicadoresProyectoCopiaLimpiaList);
                propuestaConvocatoriaSeConvienteEnProyecto.setInstitucionesProyectoList(institucionesProyectoCopiaLimpiaList);

                entityManager.persist(propuestaConvocatoriaSeConvienteEnProyecto);

                //DUPLICAMOS LOS DATOS DE LAS PESTAÑAS DEL CU-PR-05 : PRESUPUESTO
                List<FuenteProyecto> listaFuenteProyecto = iFuente.getListaFuentesPorProyecto(propuestaConvocatoria.getIdProyecto());
                for (FuenteProyecto unaFuenteProyecto : listaFuenteProyecto) {

                    FuenteProyecto nuevaFuenteProyecto = (FuenteProyecto) entityManager.unwrap(JpaEntityManager.class)
                            .copy(unaFuenteProyecto, grupoConfiguracionCopiaObjeto);

                    nuevaFuenteProyecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                    entityManager.persist(nuevaFuenteProyecto);

                    List<EquiposInvestigacion> listaEquiposInvestigacion = iEquipos.findEquiposByProyecto(propuestaConvocatoria.getIdProyecto());
                    for (EquiposInvestigacion unEquiposInvestigacion : listaEquiposInvestigacion) {

                        EquiposInvestigacion nuevoObjecto = (EquiposInvestigacion) entityManager.unwrap(JpaEntityManager.class)
                                .copy(unEquiposInvestigacion, grupoConfiguracionCopiaObjeto);

                        nuevoObjecto.setFuenteProyecto(nuevaFuenteProyecto);
                        entityManager.persist(nuevoObjecto);

                    }

                    List<ViajesProyecto> listaViajesProyecto = iViajesProyecto.findViajesByProyecto(propuestaConvocatoria.getIdProyecto());
                    for (ViajesProyecto unViajesProyecto : listaViajesProyecto) {

                        ViajesProyecto nuevoObjecto = (ViajesProyecto) entityManager.unwrap(JpaEntityManager.class)
                                .copy(unViajesProyecto, grupoConfiguracionCopiaObjeto);

                        nuevoObjecto.setFuenteProyecto(nuevaFuenteProyecto);
                        entityManager.persist(nuevoObjecto);
                    }

                    List<OtrosGastosProyecto> listaOtrosGastosProyecto = iOtrosGastosProyecto.findOtrosGastosByProyecto(propuestaConvocatoria.getIdProyecto());
                    for (OtrosGastosProyecto unOtrosGastosProyecto : listaOtrosGastosProyecto) {

                        OtrosGastosProyecto nuevoObjecto = (OtrosGastosProyecto) entityManager.unwrap(JpaEntityManager.class)
                                .copy(unOtrosGastosProyecto, grupoConfiguracionCopiaObjeto);

                        nuevoObjecto.setFuenteProyecto(nuevaFuenteProyecto);
                        entityManager.persist(nuevoObjecto);
                    }

                    List<EventoProyecto> listaEventoProyecto = iEvento.getListaEventosPorProyecto(propuestaConvocatoria.getIdProyecto());
                    for (EventoProyecto unEventoProyecto : listaEventoProyecto) {

                        EventoProyecto nuevoObjecto = (EventoProyecto) entityManager.unwrap(JpaEntityManager.class)
                                .copy(unEventoProyecto, grupoConfiguracionCopiaObjeto);

                        nuevoObjecto.setProyecto(propuestaConvocatoriaSeConvienteEnProyecto);
                        entityManager.persist(nuevoObjecto);

                        List<TipoGastoEvento> gastoEventoList = iTipoGastoEventoLocal.findTipoGastoEventioByEvento(unEventoProyecto.getIdEventoProyecto());
                        for (TipoGastoEvento unTipoGastoEvento : gastoEventoList) {

                            TipoGastoEvento nuevaTipoGastoEvento = (TipoGastoEvento) entityManager.unwrap(JpaEntityManager.class)
                                    .copy(unTipoGastoEvento, grupoConfiguracionCopiaObjeto);

                            nuevaTipoGastoEvento.setEventoProyecto(nuevoObjecto);
                            entityManager.persist(nuevaTipoGastoEvento);

                        }
                    }
                }

                listaUnidadesPoliciales.add(propuestaConvocatoria.getUnidadPolicial().getIdUnidadPolicial());

            }

            return listaUnidadesPoliciales;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public int contarTodosLosProyecto() throws JpaDinaeException {

        try {

            Object cuenta = entityManager.createNamedQuery("Proyecto.contarTodos")
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarTodosLosProyectoPorConvocatoria(Long idPeriodo) throws JpaDinaeException {

        try {

            Object cuenta = entityManager.createNamedQuery("Proyecto.contarTodosPorConvocatoria")
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarTodosLosProyectoPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            Object cuenta = entityManager.createNamedQuery("Proyecto.contarTodosPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idUnidadPolicial
     * @param codigoProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getProyectosVigentesByCodigoProyecto(Long idUnidadPolicial, String codigoProyecto) throws JpaDinaeException {

        List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>(0);

        try {
            Long codigoEstado = IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION;

            Constantes constantes = iConstantesLocal.getConstantesPorIdConstante(codigoEstado);

            if (constantes != null) {

                List<ProyectoDTO> listaProyectos = entityManager.createNamedQuery("ProyectoDTO.findAllProyectosVigentesByCodigoProyecto")
                        .setParameter("prefijoCodigoProyecto", codigoProyecto + "%")
                        .setParameter("idEstado", codigoEstado)
                        .setParameter("idUnidadPolicial", idUnidadPolicial)
                        .getResultList();

                if (listaProyectos != null && listaProyectos.size() > 0) {

                    proyectos = new ArrayList<ProyectoDTO>(listaProyectos.size());
                    for (ProyectoDTO unProyecto : listaProyectos) {

                        boolean mostrarLinkActualizarProyecto = false;
                        ProyectoDTO proyectoItemDTO = new ProyectoDTO();
                        proyectoItemDTO.setId(unProyecto.getId());

                        proyectoItemDTO.setCodigoProyecto(unProyecto.getCodigoProyecto());
                        proyectoItemDTO.setTituloPropuesto(unProyecto.getTituloPropuesto());
                        proyectoItemDTO.setFechaActualizacion(unProyecto.getFechaActualizacion());

                        String anio;

                        if (codigoProyecto.startsWith(KeyPropertiesFactory.getInstance().value("cu_ne_6_codigo_proyecto_inicia_generacion"))) {
                            short a = unProyecto.getAnioConvocatoria();
                            anio = String.valueOf(a);

                        } else {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(unProyecto.getFechaInicioPeriodo());
                            int a = cal.get(Calendar.YEAR);
                            anio = String.valueOf(a);
                            proyectoItemDTO.setNroActaAprobacionComite(unProyecto.getNroActaAprobacionComite());
                            proyectoItemDTO.setNumeroConvocatoria(unProyecto.getConcecutivoPeriodo());
                        }

                        proyectoItemDTO.setAnio(anio);

                        String sql = "SELECT DISTINCT CPR.ID_COMPROMISO_PERIODO,"
                                + "  CPR.NUMERO_INCREMENTA,"
                                + "  CASE WHEN CP.ID_COMPROMISO_CORRECCION IS NULL THEN CPR.FECHA ELSE FECHA_COMPROMISO END AS FECHA,"
                                + "  CPR.TIPO_COMPROMISO,"
                                + "  CASE WHEN CP.ID_COMPROMISO_CORRECCION IS NULL THEN CNT.VALOR ||' '|| DECODE(CPR.NUMERO_INCREMENTA, 0, '', CPR.NUMERO_INCREMENTA) ELSE CP.NOMBRE_COMPROMISO_CORRECCION END AS COMPROMISO,"
                                + "  CPR.ID_PERIODO,"
                                + "  CN.VALOR AS ESTADO,"
                                + "  CASE WHEN CN.ID_CONSTANTES IN (?,?,?) THEN 1 ELSE 0 END AS MUESTRA_LINK, "
                                + "  CASE WHEN CNT.ID_CONSTANTES = ? THEN 'A' "
                                + "       WHEN CNT.ID_CONSTANTES = ? THEN 'F' "
                                + "       ELSE 'N/A' END AS TIPO_INFORME,"
                                + "  CP.ID_COMPROMISO_PROYECTO,"
                                + "  CASE WHEN CP.ID_COMPROMISO_CORRECCION IS NULL THEN 'CU_PR_25' ELSE 'CU_PR_24' END AS CASO_USO,"
                                + "  CP.ID_COMPROMISO_CORRECCION,"
                                + "  CP.ID_COMPROMISO_PADRE,"
                                + "  CP.ID_COMPROMISO_HIJO,"
                                + "  CP.ESTADO"
                                + " FROM COMPROMISO_PROYECTO CP"
                                + " INNER JOIN COMPROMISO_PERIODO CPR"
                                + " ON (CP.ID_COMPROMISO_PERIODO = CPR.ID_COMPROMISO_PERIODO)"
                                + " INNER JOIN CONSTANTES CN"
                                + " ON (CP.ESTADO = CN.ID_CONSTANTES)"
                                + " INNER JOIN CONSTANTES CNT"
                                + " ON (CPR.TIPO_COMPROMISO = CNT.ID_CONSTANTES)"
                                + " INNER JOIN"
                                + "   (SELECT FECHA,"
                                + "     ID_PERIODO"
                                + "   FROM "
                                + "     (SELECT FECHA,"
                                + "       ID_PERIODO"
                                + "     FROM COMPROMISO_PERIODO"
                                + "     WHERE ID_PERIODO = ?"
                                + "     ORDER BY FECHA ASC"
                                + "     )"
                                + "   ) FIL"
                                + " ON (CPR.FECHA        = FIL.FECHA"
                                + " AND CPR.ID_PERIODO   = FIL.ID_PERIODO)"
                                + " WHERE CP.ID_PROYECTO = ? AND "
                                + " CN.ID_CONSTANTES IN (?,?,?,?,?,?,?) AND"
                                + " CP.ID_COMPROMISO_HIJO IS NULL"
                                + " ORDER BY CPR.TIPO_COMPROMISO, FECHA, CPR.NUMERO_INCREMENTA  ASC ";

                        Query query = entityManager.createNativeQuery(sql)
                                .setParameter(1, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE)
                                .setParameter(2, IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN)
                                .setParameter(3, IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)
                                .setParameter(4, IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                                .setParameter(5, IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)
                                .setParameter(6, unProyecto.getIdPeriodo())
                                .setParameter(7, unProyecto.getId())
                                .setParameter(8, IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)
                                .setParameter(9, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION)
                                .setParameter(10, IConstantes.ESTADO_COMPROMISO_PROYECTO_ACEPTADO)
                                .setParameter(11, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE)
                                .setParameter(12, IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO)
                                .setParameter(13, IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN)
                                .setParameter(14, IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO);

                        List compromisos = query.getResultList();

                        if (compromisos != null && !compromisos.isEmpty()) {

                            List<CompromisoDTO> compromisosDto = new ArrayList<CompromisoDTO>(compromisos.size());

                            for (Object cp : compromisos) {

                                Object[] datos = (Object[]) cp;
                                CompromisoDTO compromisoDTO = new CompromisoDTO();

                                Long idCompromisoPeriodo = ((BigDecimal) datos[0]).longValue();
                                compromisoDTO.setIdCompromisoPeriodo(idCompromisoPeriodo);

                                Integer numeroIncrementa = ((BigDecimal) datos[1]).intValue();
                                compromisoDTO.setNumeroIncrementa(numeroIncrementa);

                                Date fecha = new Date(((Timestamp) datos[2]).getTime());
                                compromisoDTO.setFecha(fecha);

                                Long tipoCompromiso = ((BigDecimal) datos[3]).longValue();
                                compromisoDTO.setTipoCompromiso(tipoCompromiso);

                                compromisoDTO.setCompromiso((String) datos[4]);

                                Long idPeriodo = ((BigDecimal) datos[5]).longValue();
                                compromisoDTO.setIdPeriodo(idPeriodo);

                                compromisoDTO.setEstado((String) datos[6]);

                                Integer muestraLink = ((BigDecimal) datos[7]).intValue();
                                compromisoDTO.setMuestraLink((muestraLink.compareTo(0) == 0));

                                compromisoDTO.setTipoInforme((String) datos[8]);

                                compromisoDTO.setIdCompromisoProyecto(((BigDecimal) datos[9]).longValue());

                                String casoUso = (String) datos[10];
                                compromisoDTO.setCasoUso(casoUso);

                                Long idCompromisoCorreccion = (datos[11] == null) ? null : ((BigDecimal) datos[11]).longValue();
                                compromisoDTO.setIdCompromisoCorreccion(idCompromisoCorreccion);

                                Long idCompromisoPadre = (datos[12] == null) ? null : ((BigDecimal) datos[12]).longValue();
                                compromisoDTO.setIdCompromisoPadre(idCompromisoPadre);

                                Long idCompromisoHijo = (datos[13] == null) ? null : ((BigDecimal) datos[13]).longValue();
                                compromisoDTO.setIdCompromisoHijo(idCompromisoHijo);

                                Long idEstadoCompromisoProyecto = ((BigDecimal) datos[14]).longValue();
                                compromisoDTO.setIdEstadoCompromiso(idEstadoCompromisoProyecto);

                                //VERIFICAMOS SI TIPO DE COMPROMISO ES "FORMULACION PROYECTO"
                                if (IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO.equals(tipoCompromiso)) {

                                    mostrarLinkActualizarProyecto = IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO.equals(idEstadoCompromisoProyecto);

                                }

                                //VERIFICAMOS SI EL COMPROMISO TIENE ALGUNA EXCPETION
                                ExcepcionesCompromiso excepcionesCompromiso = iExcepcionCompromisoLocal.getUltimaExcepcionesCompromisoPorUnidadPolicialYcompromisoPeriodo(
                                        idUnidadPolicial,
                                        idCompromisoPeriodo);
                                if (excepcionesCompromiso != null) {

                                    //ACTUALIZAMOS LA FECHA DEL COMPROMISO SI ESTA ES MAYOR A LA FECHA ACTUAL
                                    if (excepcionesCompromiso.getFechaLimite().compareTo(compromisoDTO.getFecha()) > 0) {

                                        compromisoDTO.setFecha(excepcionesCompromiso.getFechaLimite());
                                        compromisoDTO.setActualizoFechaContenporanea(true);

                                    }
                                }

                                compromisosDto.add(compromisoDTO);
                            }

                            proyectoItemDTO.setCompromisos(compromisosDto);
                        }

                        proyectoItemDTO.setMostrarLinkActualizarProyecto(mostrarLinkActualizarProyecto);

                        proyectos.add(proyectoItemDTO);
                    }
                }
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage());
        }
        return proyectos;
    }

    /**
     *
     * @param idUnidadPolicial
     * @param modalidad
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getProyectosVigentesByModalidad(Long idUnidadPolicial, Long modalidad) throws JpaDinaeException {

        List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>(0);

        try {
            Long codigoEstado = IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION;

            Constantes constantes = iConstantesLocal.getConstantesPorIdConstante(codigoEstado);

            if (constantes != null) {

                List<ProyectoDTO> listaProyectos = entityManager.createNamedQuery("ProyectoDTO.findAllProyectosVigentesByModalidad")
                        .setParameter("modalidad", modalidad)
                        .setParameter("idEstado", codigoEstado)
                        .setParameter("idUnidadPolicial", idUnidadPolicial)
                        .getResultList();

                if (listaProyectos != null && listaProyectos.size() > 0) {

                    proyectos = new ArrayList<ProyectoDTO>(listaProyectos.size());
                    for (ProyectoDTO unProyecto : listaProyectos) {

                        boolean mostrarLinkActualizarProyecto = false;
                        ProyectoDTO proyectoItemDTO = new ProyectoDTO();
                        proyectoItemDTO.setId(unProyecto.getId());

                        proyectoItemDTO.setCodigoProyecto(unProyecto.getCodigoProyecto());
                        proyectoItemDTO.setTituloPropuesto(unProyecto.getTituloPropuesto());
                        proyectoItemDTO.setFechaActualizacion(unProyecto.getFechaActualizacion());

                        String anio;

                        if (modalidad == 100L) {
                            short a = unProyecto.getAnioConvocatoria();
                            anio = String.valueOf(a);

                        } else {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(unProyecto.getFechaInicioPeriodo());
                            int a = cal.get(Calendar.YEAR);
                            anio = String.valueOf(a);
                            proyectoItemDTO.setNroActaAprobacionComite(unProyecto.getNroActaAprobacionComite());
                            proyectoItemDTO.setNumeroConvocatoria(unProyecto.getConcecutivoPeriodo());
                        }

                        proyectoItemDTO.setAnio(anio);

                        String sql = "SELECT DISTINCT CPR.ID_COMPROMISO_PERIODO,"
                                + "  CPR.NUMERO_INCREMENTA,"
                                + "  CASE WHEN CP.ID_COMPROMISO_CORRECCION IS NULL THEN CPR.FECHA ELSE FECHA_COMPROMISO END AS FECHA,"
                                + "  CPR.TIPO_COMPROMISO,"
                                + "  CASE WHEN CP.ID_COMPROMISO_CORRECCION IS NULL THEN CNT.VALOR ||' '|| DECODE(CPR.NUMERO_INCREMENTA, 0, '', CPR.NUMERO_INCREMENTA) ELSE CP.NOMBRE_COMPROMISO_CORRECCION END AS COMPROMISO,"
                                + "  CPR.ID_PERIODO,"
                                + "  CN.VALOR AS ESTADO,"
                                + "  CASE WHEN CN.ID_CONSTANTES IN (?,?,?) THEN 1 ELSE 0 END AS MUESTRA_LINK, "
                                + "  CASE WHEN CNT.ID_CONSTANTES = ? THEN 'A' "
                                + "       WHEN CNT.ID_CONSTANTES = ? THEN 'A' "
                                + "       WHEN CNT.ID_CONSTANTES = ? THEN 'F' "
                                + "       ELSE 'N/A' END AS TIPO_INFORME,"
                                + "  CP.ID_COMPROMISO_PROYECTO,"
                                + "  CASE WHEN CP.ID_COMPROMISO_CORRECCION IS NULL THEN 'CU_PR_25' ELSE 'CU_PR_24' END AS CASO_USO,"
                                + "  CP.ID_COMPROMISO_CORRECCION,"
                                + "  CP.ID_COMPROMISO_PADRE,"
                                + "  CP.ID_COMPROMISO_HIJO,"
                                + "  CP.ESTADO"
                                + " FROM COMPROMISO_PROYECTO CP"
                                + " INNER JOIN COMPROMISO_PERIODO CPR"
                                + " ON (CP.ID_COMPROMISO_PERIODO = CPR.ID_COMPROMISO_PERIODO)"
                                + " INNER JOIN CONSTANTES CN"
                                + " ON (CP.ESTADO = CN.ID_CONSTANTES)"
                                + " INNER JOIN CONSTANTES CNT"
                                + " ON (CPR.TIPO_COMPROMISO = CNT.ID_CONSTANTES)"
                                + " INNER JOIN"
                                + "   (SELECT FECHA,"
                                + "     ID_PERIODO"
                                + "   FROM "
                                + "     (SELECT FECHA,"
                                + "       ID_PERIODO"
                                + "     FROM COMPROMISO_PERIODO"
                                + "     WHERE ID_PERIODO = ?"
                                + "     ORDER BY FECHA ASC"
                                + "     )"
                                + "   ) FIL"
                                + " ON (CPR.FECHA        = FIL.FECHA"
                                + " AND CPR.ID_PERIODO   = FIL.ID_PERIODO)"
                                + " WHERE CP.ID_PROYECTO = ? AND "
                                + " CN.ID_CONSTANTES IN (?,?,?,?,?,?,?) AND"
                                + " CP.ID_COMPROMISO_HIJO IS NULL"
                                + " ORDER BY CPR.TIPO_COMPROMISO, FECHA, CPR.NUMERO_INCREMENTA  ASC ";

                        Query query = entityManager.createNativeQuery(sql)
                                .setParameter(1, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE)
                                .setParameter(2, IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN)
                                .setParameter(3, IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)
                                .setParameter(4, IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                                .setParameter(5, IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)
                                .setParameter(6, IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)
                                .setParameter(7, unProyecto.getIdPeriodo())
                                .setParameter(8, unProyecto.getId())
                                .setParameter(9, IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE)
                                .setParameter(10, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION)
                                .setParameter(11, IConstantes.ESTADO_COMPROMISO_PROYECTO_ACEPTADO)
                                .setParameter(12, IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE)
                                .setParameter(13, IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO)
                                .setParameter(14, IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN)
                                .setParameter(15, IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO);

                        List compromisos = query.getResultList();

                        if (compromisos != null && !compromisos.isEmpty()) {

                            List<CompromisoDTO> compromisosDto = new ArrayList<CompromisoDTO>(compromisos.size());

                            for (Object cp : compromisos) {

                                Object[] datos = (Object[]) cp;
                                CompromisoDTO compromisoDTO = new CompromisoDTO();

                                Long idCompromisoPeriodo = ((BigDecimal) datos[0]).longValue();
                                compromisoDTO.setIdCompromisoPeriodo(idCompromisoPeriodo);

                                Integer numeroIncrementa = ((BigDecimal) datos[1]).intValue();
                                compromisoDTO.setNumeroIncrementa(numeroIncrementa);

                                Date fecha = new Date(((Timestamp) datos[2]).getTime());
                                compromisoDTO.setFecha(fecha);

                                Long tipoCompromiso = ((BigDecimal) datos[3]).longValue();
                                compromisoDTO.setTipoCompromiso(tipoCompromiso);

                                compromisoDTO.setCompromiso((String) datos[4]);

                                Long idPeriodo = ((BigDecimal) datos[5]).longValue();
                                compromisoDTO.setIdPeriodo(idPeriodo);

                                compromisoDTO.setEstado((String) datos[6]);

                                Integer muestraLink = ((BigDecimal) datos[7]).intValue();
                                compromisoDTO.setMuestraLink((muestraLink.compareTo(0) == 0));

                                compromisoDTO.setTipoInforme((String) datos[8]);

                                compromisoDTO.setIdCompromisoProyecto(((BigDecimal) datos[9]).longValue());

                                String casoUso = (String) datos[10];
                                compromisoDTO.setCasoUso(casoUso);

                                Long idCompromisoCorreccion = (datos[11] == null) ? null : ((BigDecimal) datos[11]).longValue();
                                compromisoDTO.setIdCompromisoCorreccion(idCompromisoCorreccion);

                                Long idCompromisoPadre = (datos[12] == null) ? null : ((BigDecimal) datos[12]).longValue();
                                compromisoDTO.setIdCompromisoPadre(idCompromisoPadre);

                                Long idCompromisoHijo = (datos[13] == null) ? null : ((BigDecimal) datos[13]).longValue();
                                compromisoDTO.setIdCompromisoHijo(idCompromisoHijo);

                                Long idEstadoCompromisoProyecto = ((BigDecimal) datos[14]).longValue();
                                compromisoDTO.setIdEstadoCompromiso(idEstadoCompromisoProyecto);

                                //VERIFICAMOS SI TIPO DE COMPROMISO ES "FORMULACION PROYECTO"
                                if (IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO.equals(tipoCompromiso)) {

                                    mostrarLinkActualizarProyecto = IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO.equals(idEstadoCompromisoProyecto);

                                }

                                //VERIFICAMOS SI EL COMPROMISO TIENE ALGUNA EXCPETION
                                ExcepcionesCompromiso excepcionesCompromiso = iExcepcionCompromisoLocal.getUltimaExcepcionesCompromisoPorUnidadPolicialYcompromisoPeriodo(
                                        idUnidadPolicial,
                                        idCompromisoPeriodo);
                                if (excepcionesCompromiso != null) {

                                    //ACTUALIZAMOS LA FECHA DEL COMPROMISO SI ESTA ES MAYOR A LA FECHA ACTUAL
                                    if (excepcionesCompromiso.getFechaLimite().compareTo(compromisoDTO.getFecha()) > 0) {

                                        compromisoDTO.setFecha(excepcionesCompromiso.getFechaLimite());
                                        compromisoDTO.setActualizoFechaContenporanea(true);

                                    }
                                }

                                compromisosDto.add(compromisoDTO);
                            }

                            proyectoItemDTO.setCompromisos(compromisosDto);
                        }

                        proyectoItemDTO.setMostrarLinkActualizarProyecto(mostrarLinkActualizarProyecto);

                        proyectos.add(proyectoItemDTO);
                    }
                }
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage());
        }
        return proyectos;
    }

    @Override
    public List<Proyecto> getProyectosByIdProgramaNotNull() throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("Proyecto.findAllByIdProgramaNotNull")
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public List<Proyecto> getProyectosByIdPrograma(Long idPrograma) throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllByIdPrograma")
                    .setParameter("idPrograma", idPrograma)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public Long validateProyectosByCodigoProyecto(Long estadoEvaluado, String codigoProyecto)
            throws JpaDinaeException {
        Long retorno = 0L;
        StringBuilder query = new StringBuilder();
        query.append("SELECT p FROM Proyecto p WHERE upper(p.codigoProyecto) like upper(:codigoProyecto)");
        Query consulta = entityManager.createQuery(query.toString()).
                setParameter("codigoProyecto", "%" + codigoProyecto + "%");

        List<Proyecto> proyectos = consulta.getResultList();
        if (proyectos == null || proyectos.isEmpty()) {
            return 1L;
        }
        query = new StringBuilder();
        query.append("SELECT p FROM Proyecto p WHERE upper(p.codigoProyecto) like upper(:codigoProyecto)");
        query.append(" AND p.estado.idConstantes <> :estado");

        consulta = entityManager.createQuery(query.toString()).
                setParameter("codigoProyecto", "%" + codigoProyecto + "%").setParameter("estado", estadoEvaluado);
        proyectos = consulta.getResultList();
        if (proyectos != null && !proyectos.isEmpty()) {
            return 2L;
        }
        return retorno;
    }

    /**
     *
     * @param estadosFiltro
     * @param palabraClave
     * @param unidadPolicial
     * @param codigoProyecto
     * @param fechaPresentacionEntre
     * @param fechaPresentacionY
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getProyectosEjecutadosByFiltros(Long[] estadosFiltro, String palabraClave,
            Long unidadPolicial, String codigoProyecto, Date fechaPresentacionEntre, Date fechaPresentacionY) throws JpaDinaeException {
        StringBuilder query = new StringBuilder();

        query.append("SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO(p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.unidadPolicial.siglaFisica , p.codigoProyecto,p.tituloPropuesto, p.fechaEstimadaInicio,p.linea.areaCienciaTecnologia.nombre, p.linea.nombre,p.estado.valor ) FROM Proyecto p WHERE p.estado.idConstantes IN :estados");
        query.append(" AND ( p.codigoProyecto LIKE 'VIC%' OR p.codigoProyecto LIKE 'CONV%' OR p.codigoProyecto LIKE 'MVIC%' )");

        if (palabraClave != null && !palabraClave.equals("")) {
            query.append(" AND upper(p.tituloPropuesto) like upper(:palabraClaveTitulo)");
        }
        if (unidadPolicial != null && unidadPolicial > 0L) {
            query.append(" AND p.unidadPolicial.idUnidadPolicial = :unidadPolicial");
        }
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            query.append(" AND upper(p.codigoProyecto) like upper(:codigoProyecto)");
        }
        if (fechaPresentacionEntre != null && fechaPresentacionY != null) {
            query.append(" AND p.fechaRegistro BETWEEN :fechaRegistroIni AND :fechaRegistroFin");
        }
        try {
            Query queryFinal = entityManager.createQuery(query.toString());
            queryFinal.setParameter("estados", Arrays.asList(estadosFiltro));

            if (palabraClave != null && !palabraClave.equals("")) {
                queryFinal.setParameter("palabraClaveTitulo", "%" + palabraClave + "%");
            }
            if (unidadPolicial != null && unidadPolicial > 0L) {
                queryFinal.setParameter("unidadPolicial", unidadPolicial);
            }
            if (codigoProyecto != null && !codigoProyecto.equals("")) {
                queryFinal.setParameter("codigoProyecto", "%" + codigoProyecto + "%");
            }
            if (fechaPresentacionEntre != null && fechaPresentacionY != null) {
                queryFinal.setParameter("fechaRegistroIni", fechaPresentacionEntre);
                queryFinal.setParameter("fechaRegistroFin", fechaPresentacionY);
            }

            return queryFinal.getResultList();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public Proyecto getProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException {
        try {
            return (Proyecto) entityManager.createNamedQuery("Proyecto.findById").setParameter("idProyecto", idProyecto).getSingleResult();
        } catch (NoResultException nre) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, nre.getLocalizedMessage());
            return null;
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public List<Proyecto> getProyectosAsignadosInvestigadorPrincipalByIdentificacion(String identificacion) throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllAsignadosInvestigadorPrincipalByIdentificacion")
                    .setParameter("identificacion", identificacion)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public List<Proyecto> getAllProyectosTipoTrabajoDeGrado() throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllAllProyectosTipoTrabajoDeGrado")
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public List<Proyecto> getProyectosByIdProgramaAndIdEstado(Long idPrograma, Long idEstado) throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllAllProyectosByIdProgramaAndIdEstado")
                    .setParameter("idPrograma", idPrograma)
                    .setParameter("idEstado", idEstado)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public List<Proyecto> getProyectosAsignadosInvestigadorPrincipalByIdentificacionAndIdEstado(String identificacion, Long idEstado) throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllAsignadosInvestigadorPrincipalByIdentificacionAndIdEstado")
                    .setParameter("identificacion", identificacion)
                    .setParameter("idEstado", idEstado)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public List<Proyecto> getProyectosTipoTrabajoDeGradoByIdUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllAllProyectosTipoTrabajoDeGradoByIdUnidadPolicial")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    @Override
    public List<Proyecto> getProyectosTipoTrabajoDeGradoByIdUnidadPolicialAndIdEstado(Long idUnidadPolicial, Long idEstado) throws JpaDinaeException {

        try {
            return entityManager.createNamedQuery("Proyecto.findAllAllProyectosTipoTrabajoDeGradoByIdUnidadPolicialAndIdEstado")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idEstado", idEstado)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param palabraClave
     * @param tiposProyectoSeleccionados
     * @param idUnidadPolicial
     * @param idTiposUnidadesSeleccionadas
     * @param idAreaCienciaTecnologiaSeleccionada
     * @param idLineaSeleccionada
     * @param valorProyectoRangoInicial
     * @param valorProyectoRangoFinal
     * @param codigoProyecto
     * @param idEstadoProyecto
     * @param idOrigenProyecto
     * @param idEstadoImplementacion
     * @param idNombreProgramaSeleccionado
     * @param fechaPresentacionInicial
     * @param fechaPresentacionFinal
     * @param notaFinalRangoInicial
     * @param notaFinalRangoFinal
     * @param idGrupoInvestigacion
     * @param idSemillero
     * @param nombresYApellidos
     * @param idConvocatoria
     * @param idFormasDeVer
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Proyecto> getProyectosOTrabajosBusquedaPorFiltros(
            String palabraClave,
            List<Long> tiposProyectoSeleccionados,
            Long idUnidadPolicial,
            List<Long> idTiposUnidadesSeleccionadas,
            Long idAreaCienciaTecnologiaSeleccionada,
            Long idLineaSeleccionada,
            BigDecimal valorProyectoRangoInicial,
            BigDecimal valorProyectoRangoFinal,
            String codigoProyecto,
            Long idEstadoProyecto,
            Long idOrigenProyecto,
            Long idEstadoImplementacion,
            Long idNombreProgramaSeleccionado,
            Date fechaPresentacionInicial,
            Date fechaPresentacionFinal,
            BigDecimal notaFinalRangoInicial,
            BigDecimal notaFinalRangoFinal,
            Long idGrupoInvestigacion,
            Long idSemillero,
            String nombresYApellidos,
            Long idConvocatoria,
            Long idFormasDeVer) throws JpaDinaeException {

        //Generacion del String de consulta - Genera el query con las condiciones necesarias
        StringBuilder query = new StringBuilder();

        query.append("SELECT p FROM Proyecto p WHERE p.idProyecto IS NOT NULL");

        if (palabraClave != null && !palabraClave.equals("")) {
            query.append(" AND upper(p.tituloPropuesto) like upper(:palabraClaveTitulo)");
        }

        if (tiposProyectoSeleccionados != null && tiposProyectoSeleccionados.size() > 0) {
            for (Long unIdConstantesTipoProyectoSeleccionado : tiposProyectoSeleccionados) {
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_INVESTIGACIONES_INSTITUCIONALES)) {
                    query.append(" AND p.codigoProyecto like 'VIC%'");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_TRABAJOS_DE_GRADO)) {
                    query.append(" AND p.programas IS NOT NULL");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_PARA_FINANCIACION)) {
                    query.append(" AND p.codigoProyecto like 'CONV%'");
                }
            }
        }

        if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
            query.append(" AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial");
        }

        if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
            for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                query.append(" AND p.unidadPolicial.tipoUnidad.idTipoUnidad = :idTipoUnidad");
                query.append(unIdTipoUnidad);
            }
        }

        if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
            query.append(" AND (p.linea.idLinea IN(SELECT l.idLinea FROM Linea l WHERE l.areaCienciaTecnologia.idAreaCienciaTecnologia = :idAreaCienciaTecnologia))");
        }

        if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
            query.append(" AND p.linea.idLinea = :idLinea");
        }

        if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
            query.append(" AND p.valorTotal BETWEEN :valorProyectoRangoInicial AND :valorProyectoRangoFinal");
        }

        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            query.append(" AND upper(p.codigoProyecto) like upper(:codigoProyecto)");
        }

        //TODO Falta hacer las busquedas por origenes
        if (idOrigenProyecto != null && idOrigenProyecto > 0L) {
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_BANCO_DE_NECESIDADES)) {
                //query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_PROPUESTA_DE_NECESIDADES)) {
                query.append(" AND p.propuestaNecesidad IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_CONVOCATORIA)) {
                query.append(" AND p.propuestaConvocatoria IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_TRABAJO_DE_GRADO)) {
                query.append(" AND p.programas IS NOT NULL");
            }
        }

        if (idEstadoImplementacion != null && idEstadoImplementacion > 0L) {
            query.append(" AND p.estadoImplementacion.idConstantes = :idEstadoImplementacion");
        }

        if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
            query.append(" AND p.programas.idPrograma = :idPrograma");
        }

        if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
            query.append(" AND p.fechaRegistro BETWEEN :fechaPresentacionInicial AND :fechaPresentacionFinal");
        }

        if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
            //Para trabajos de grado
            query.append(" AND (p.idProyecto IN (SELECT eg.proyecto.idProyecto FROM EvaluacionProyectoGrado eg WHERE eg.notaFinal BETWEEN :notaFinalRangoInicial AND :notaFinalRangoFinal))");
            //Para proyectos
            query.append(" UNION SELECT p FROM Proyecto p WHERE p.idProyecto IS NOT NULL AND (p.idProyecto IN (SELECT ep.proyecto.idProyecto FROM EvaluacionProyecto ep GROUP BY ep.proyecto.idProyecto HAVING (AVG(ep.valorCriterio) AS promedioValorCriterio BETWEEN :notaFinalPromedioRangoInicial AND :notaFinalPromedioRangoFinal)))");

        }

        if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT g.proyecto.idProyecto FROM GrupoInvestigacionProyecto g WHERE g.grupoInvestigacion.idGrupoInvestigacion = :idGrupoInvestigacion))");
        }

        if (idSemillero != null && idSemillero > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT s.proyecto.idProyecto FROM SemilleroProyecto s WHERE s.semilleroInvestigacion.idSemillero = :idSemillero))");
        }

        if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
            query.append(" AND (p.idProyecto IN(SELECT i.proyecto.idProyecto FROM InvestigadorProyecto i WHERE upper(i.nombreCompleto) like upper(:nombresYApellidos)))");
        }

        if (idConvocatoria != null && idConvocatoria > 0L) {
            query.append(" AND p.periodo.idPeriodo = :idConvocatoria");
        }

        if (idEstadoProyecto != null && idEstadoProyecto > 0L) {
            query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
        }

        try {

            //Ejecucion de la consulta - Se introduce el valor de los parámetros
            Query queryFinal = entityManager.createQuery(query.toString());

            if (palabraClave != null && !palabraClave.equals("")) {
                queryFinal.setParameter("palabraClaveTitulo", "%" + palabraClave + "%");
            }

            if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
                queryFinal.setParameter("idUnidadPolicial", idUnidadPolicial);
            }

            if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
                for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                    queryFinal.setParameter("idTipoUnidad" + unIdTipoUnidad, unIdTipoUnidad);
                }
            }

            if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
                queryFinal.setParameter("idAreaCienciaTecnologia", idAreaCienciaTecnologiaSeleccionada);
            }

            if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
                queryFinal.setParameter("idLinea", idLineaSeleccionada);
            }

            if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
                queryFinal.setParameter("valorProyectoRangoInicial", valorProyectoRangoInicial);
                queryFinal.setParameter("valorProyectoRangoFinal", valorProyectoRangoFinal);
            }

            if (codigoProyecto != null && !codigoProyecto.equals("")) {
                queryFinal.setParameter("codigoProyecto", "%" + codigoProyecto + "%");
            }

            if (idEstadoProyecto != null && idEstadoProyecto > 0L) {
                queryFinal.setParameter("idEstadoProyecto", idEstadoProyecto);
            }

            if (idEstadoImplementacion != null && idEstadoImplementacion > 0L) {
                queryFinal.setParameter("idEstadoImplementacion", idEstadoImplementacion);
            }

            if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
                queryFinal.setParameter("idPrograma", idNombreProgramaSeleccionado);
            }

            if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
                queryFinal.setParameter("fechaPresentacionInicial", fechaPresentacionInicial);
                queryFinal.setParameter("fechaPresentacionFinal", fechaPresentacionFinal);
            }

            if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
                queryFinal.setParameter("notaFinalRangoInicial", notaFinalRangoInicial);
                queryFinal.setParameter("notaFinalRangoFinal", notaFinalRangoFinal);
            }

            if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
                queryFinal.setParameter("idGrupoInvestigacion", idGrupoInvestigacion);
            }

            if (idSemillero != null && idSemillero > 0L) {
                queryFinal.setParameter("idSemillero", idSemillero);
            }

            if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
                queryFinal.setParameter("nombresYApellidos", "%" + nombresYApellidos + "%");
            }

            if (idConvocatoria != null && idConvocatoria > 0L) {
                queryFinal.setParameter("idConvocatoria", idConvocatoria);
            }

            return queryFinal.getResultList();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @param palabraClave
     * @param tiposProyectoSeleccionados
     * @param idUnidadPolicial
     * @param idTiposUnidadesSeleccionadas
     * @param idAreaCienciaTecnologiaSeleccionada
     * @param idLineaSeleccionada
     * @param valorProyectoRangoInicial
     * @param valorProyectoRangoFinal
     * @param codigoProyecto
     * @param idEstadoProyecto
     * @param idOrigenProyecto
     * @param idEstadoImplementacion
     * @param idNombreProgramaSeleccionado
     * @param fechaPresentacionInicial
     * @param fechaPresentacionFinal
     * @param notaFinalRangoInicial
     * @param notaFinalRangoFinal
     * @param idGrupoInvestigacion
     * @param idSemillero
     * @param nombresYApellidos
     * @param idConvocatoria
     * @param idFormasDeVer
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Proyecto> getProyectosOTrabajosBusquedaPorFiltros(String palabraClave,
            List<Long> tiposProyectoSeleccionados,
            Long idUnidadPolicial,
            List<Long> idTiposUnidadesSeleccionadas,
            Long idAreaCienciaTecnologiaSeleccionada,
            Long idLineaSeleccionada,
            BigDecimal valorProyectoRangoInicial,
            BigDecimal valorProyectoRangoFinal,
            String codigoProyecto,
            List<Long> idEstadoProyecto,
            Long idOrigenProyecto,
            List<Long> idEstadoImplementacion,
            Long idNombreProgramaSeleccionado,
            Date fechaPresentacionInicial,
            Date fechaPresentacionFinal,
            BigDecimal notaFinalRangoInicial,
            BigDecimal notaFinalRangoFinal,
            Long idGrupoInvestigacion,
            Long idSemillero,
            String nombresYApellidos,
            Long idConvocatoria,
            Long idFormasDeVer) throws JpaDinaeException {
        //Generacion del String de consulta - Genera el query con las condiciones necesarias
        StringBuilder query = new StringBuilder();

        query.append("SELECT p FROM Proyecto p WHERE p.idProyecto IS NOT NULL AND p.programas IS NULL ");

        if (palabraClave != null && !palabraClave.equals("")) {
            query.append(" AND upper(p.tituloPropuesto) like upper(:palabraClaveTitulo)");
        }

        if (tiposProyectoSeleccionados != null && tiposProyectoSeleccionados.size() > 0) {
            for (Long unIdConstantesTipoProyectoSeleccionado : tiposProyectoSeleccionados) {
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_INVESTIGACIONES_INSTITUCIONALES)) {
                    query.append(" AND p.codigoProyecto like 'VIC%'");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_TRABAJOS_DE_GRADO)) {
                    query.append(" AND p.programas IS NOT NULL");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_PARA_FINANCIACION)) {
                    query.append(" AND p.codigoProyecto like 'CONV%'");
                }
            }
        }

        if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
            query.append(" AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial");
        }

        if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
            for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                query.append(" AND p.unidadPolicial.tipoUnidad.idTipoUnidad = :idTipoUnidad");
                query.append(unIdTipoUnidad);
            }
        }

        if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
            query.append(" AND (p.linea.idLinea IN(SELECT l.idLinea FROM Linea l WHERE l.areaCienciaTecnologia.idAreaCienciaTecnologia = :idAreaCienciaTecnologia))");
        }

        if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
            query.append(" AND p.linea.idLinea = :idLinea");
        }

        if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
            query.append(" AND p.valorTotal BETWEEN :valorProyectoRangoInicial AND :valorProyectoRangoFinal");
        }

        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            query.append(" AND upper(p.codigoProyecto) like upper(:codigoProyecto)");
        }

        //TODO Falta hacer las busquedas por origenes
        if (idOrigenProyecto != null && idOrigenProyecto > 0L) {
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_BANCO_DE_NECESIDADES)) {
                //query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_PROPUESTA_DE_NECESIDADES)) {
                query.append(" AND p.propuestaNecesidad IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_CONVOCATORIA)) {
                query.append(" AND p.propuestaConvocatoria IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_TRABAJO_DE_GRADO)) {
                query.append(" AND p.programas IS NOT NULL");
            }
        }
        //Consulta por una lista de estado de implementación
        if (idEstadoImplementacion != null && idEstadoImplementacion.size() > 0L) {
            if (idEstadoImplementacion.size() == 1) {
                query.append(" AND p.estadoImplementacion.idConstantes = :idEstadoImplementacion");
            } else {
                int i = 0;
                Iterator<Long> iterador = idEstadoImplementacion.iterator();
                query.append(" AND (");
                while (iterador.hasNext()) {
                    iterador.next();
                    query.append("p.estadoImplementacion.idConstantes = :idEstadoImplementacion");
                    query.append(String.valueOf(i));
                    if (iterador.hasNext()) {
                        query.append(" OR ");
                    } else {
                        query.append(" ) ");
                    }
                    i++;
                }
            }

        }

        if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
            query.append(" AND p.programas.idPrograma = :idPrograma");
        }

        if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
            query.append(" AND p.fechaRegistro BETWEEN :fechaPresentacionInicial AND :fechaPresentacionFinal");
        }

        if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
            //Para trabajos de grado
            query.append(" AND (p.idProyecto IN (SELECT eg.proyecto.idProyecto FROM EvaluacionProyectoGrado eg WHERE eg.notaFinal BETWEEN :notaFinalRangoInicial AND :notaFinalRangoFinal))");
            //Para proyectos
            //query.append(" OR (p.idProyecto = (SELECT ep.proyecto.idProyecto FROM EvaluacionProyecto ep GROUP BY ep.proyecto.idProyecto HAVING (AVG(ep.valorCriterio) AS promedioValorCriterio BETWEEN :notaFinalPromedioRangoInicial AND :notaFinalPromedioRangoFinal)))");
            //query.append(" OR (p.idProyecto IN (SELECT ep.proyecto.idProyecto FROM EvaluacionProyecto ep GROUP BY ep.proyecto.idProyecto HAVING ((AVG(ep.valorCriterio) BETWEEN :notaFinalRangoInicial AND :notaFinalRangoFinal))))");
            query.append(" UNION SELECT p FROM Proyecto p WHERE p.idProyecto IS NOT NULL AND (p.idProyecto IN (SELECT ep.proyecto.idProyecto FROM EvaluacionProyecto ep GROUP BY ep.proyecto.idProyecto HAVING (AVG(ep.valorCriterio) AS promedioValorCriterio BETWEEN :notaFinalPromedioRangoInicial AND :notaFinalPromedioRangoFinal)))");

        }

        if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT g.proyecto.idProyecto FROM GrupoInvestigacionProyecto g WHERE g.grupoInvestigacion.idGrupoInvestigacion = :idGrupoInvestigacion))");
        }

        if (idSemillero != null && idSemillero > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT s.proyecto.idProyecto FROM SemilleroProyecto s WHERE s.semilleroInvestigacion.idSemillero = :idSemillero))");
        }

        if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
            query.append(" AND (p.idProyecto IN(SELECT i.proyecto.idProyecto FROM InvestigadorProyecto i WHERE upper(i.nombreCompleto) like upper(:nombresYApellidos)))");
        }

        if (idConvocatoria != null && idConvocatoria > 0L) {
            query.append(" AND p.periodo.idPeriodo = :idConvocatoria");
        }

        //Consulta por una lista de estado de proyecto
        if (idEstadoProyecto != null && idEstadoProyecto.size() > 0L) {
            if (idEstadoProyecto.size() == 1) {
                query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
            } else {
                int i = 0;
                Iterator<Long> iterador = idEstadoProyecto.iterator();
                query.append(" AND (");
                while (iterador.hasNext()) {
                    iterador.next();
                    query.append(" p.estado.idConstantes = :idEstadoProyecto");
                    query.append(String.valueOf(i));
                    if (iterador.hasNext()) {
                        query.append(" OR ");
                    } else {
                        query.append(" ) ");
                    }
                    i++;
                }
            }

        }

        try {

            //Ejecucion de la consulta - Se introduce el valor de los parámetros
            Query queryFinal = entityManager.createQuery(query.toString());

            if (palabraClave != null && !palabraClave.equals("")) {
                queryFinal.setParameter("palabraClaveTitulo", "%" + palabraClave + "%");
            }

            if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
                queryFinal.setParameter("idUnidadPolicial", idUnidadPolicial);
            }

            if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
                for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                    queryFinal.setParameter("idTipoUnidad" + unIdTipoUnidad, unIdTipoUnidad);
                }
            }

            if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
                queryFinal.setParameter("idAreaCienciaTecnologia", idAreaCienciaTecnologiaSeleccionada);
            }

            if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
                queryFinal.setParameter("idLinea", idLineaSeleccionada);
            }

            if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
                queryFinal.setParameter("valorProyectoRangoInicial", valorProyectoRangoInicial);
                queryFinal.setParameter("valorProyectoRangoFinal", valorProyectoRangoFinal);
            }

            if (codigoProyecto != null && !codigoProyecto.equals("")) {
                queryFinal.setParameter("codigoProyecto", "%" + codigoProyecto + "%");
            }

            if (idEstadoProyecto != null && idEstadoProyecto.size() > 0L) {
                if (idEstadoProyecto.size() == 1) {
                    queryFinal.setParameter("idEstadoProyecto", idEstadoProyecto.get(0));
                } else {
                    int i = 0;
                    Iterator<Long> iterador = idEstadoProyecto.iterator();
                    query.append(" AND (");
                    while (iterador.hasNext()) {
                        Long idEstadoProyectoLong = iterador.next();
                        queryFinal.setParameter("idEstadoProyecto" + i, idEstadoProyectoLong);
                        i++;
                    }
                }
            }

            if (idEstadoImplementacion != null && idEstadoImplementacion.size() > 0L) {
                if (idEstadoImplementacion.size() == 1) {
                    queryFinal.setParameter("idEstadoImplementacion", idEstadoImplementacion.get(0));
                } else {
                    int i = 0;
                    Iterator<Long> iterador = idEstadoImplementacion.iterator();
                    query.append(" AND (");
                    while (iterador.hasNext()) {
                        Long idEstadoImplementacionLong = iterador.next();
                        queryFinal.setParameter("idEstadoImplementacion" + i, idEstadoImplementacionLong);
                        i++;
                    }
                }
            }

            if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
                queryFinal.setParameter("idPrograma", idNombreProgramaSeleccionado);
            }

            if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
                queryFinal.setParameter("fechaPresentacionInicial", fechaPresentacionInicial);
                queryFinal.setParameter("fechaPresentacionFinal", fechaPresentacionFinal);
            }

            if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
                queryFinal.setParameter("notaFinalRangoInicial", notaFinalRangoInicial);
                queryFinal.setParameter("notaFinalRangoFinal", notaFinalRangoFinal);
            }

            if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
                queryFinal.setParameter("idGrupoInvestigacion", idGrupoInvestigacion);
            }

            if (idSemillero != null && idSemillero > 0L) {
                queryFinal.setParameter("idSemillero", idSemillero);
            }

            if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
                queryFinal.setParameter("nombresYApellidos", "%" + nombresYApellidos + "%");
            }

            if (idConvocatoria != null && idConvocatoria > 0L) {
                queryFinal.setParameter("idConvocatoria", idConvocatoria);
            }

            return queryFinal.getResultList();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @param idEstado
     * @param idTipoProyecto
     * @param idUnidad
     * @param codigo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> findAllProyectosInvestigacionFiltros(Long idEstado, Long idTipoProyecto, Long idUnidad, String codigo) throws JpaDinaeException {

        try {
            StringBuilder jpql = new StringBuilder("SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO(p.idProyecto, p.tituloPropuesto, p.codigoProyecto, p.fechaEstimadaInicio, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.periodo.idPeriodo) "
                    + "FROM Proyecto p WHERE p.estado.idConstantes = :idEstado AND p.codigoProyecto IS NOT NULL AND p.programas IS NULL");
            Map<String, Object> params = new HashMap<String, Object>();

            params.put("idEstado", idEstado);

            if (idTipoProyecto != null) {
                String codProyecto = (IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION.compareTo(idTipoProyecto) == 0) ? "CONV%" : "VIC%";
                jpql.append(" AND p.codigoProyecto LIKE :prefijoCodigoProyecto ");
                params.put("prefijoCodigoProyecto", codProyecto.trim());
            }

            if (idUnidad != null) {
                jpql.append(" AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ");
                params.put("idUnidadPolicial", idUnidad);
            }

            if (codigo != null) {
                jpql.append(" AND UPPER(p.codigoProyecto) = UPPER(:codigoProyecto) ");
                params.put("codigoProyecto", codigo.trim());
            }

            jpql.append(" ORDER BY p.codigoProyecto ASC ");

            Query query = entityManager.createQuery(jpql.toString());
            Set<String> keys = params.keySet();

            for (String key : keys) {
                query.setParameter(key, params.get(key));
            }

            return query.getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idEstado
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarAllProyectosInvestigacionNoEvaluadosByPeriodo(Long idEstado, Long idPeriodo) throws JpaDinaeException {
        try {

            Object cuenta = entityManager.createNamedQuery("Proyecto.findAllProyectosInvestigacionNoEvaluadosByPeriodo", Proyecto.class)
                    .setParameter("idEstado", idEstado)
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();

            if (cuenta == null) {
                return 0;
            }

            return ((Number) cuenta).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idUnidadPolicial
     * @param listaIdEstadoProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Proyecto> getProyectoPorUnidadPolicialYListaIdEstado(Long idUnidadPolicial, List<Long> listaIdEstadoProyecto) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("Proyecto.findAllPorUnidadPolicialYlistaEstado")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idListaEstado", listaIdEstadoProyecto)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage());
        }

    }

    /**
     *
     * @param idImplementacionesProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Proyecto getProyectoPorImplementacionProyecto(Long idImplementacionesProyecto) throws JpaDinaeException {

        try {

            return (Proyecto) entityManager.createNamedQuery("ImplementacionesProyecto.findProyectoByIdImplementacionProy")
                    .setParameter("idImplementacionProy", idImplementacionesProyecto)
                    .getSingleResult();

        } catch (Exception e) {
            throw new JpaDinaeException(e);
        }

    }

    /**
     *
     * @param idImplementacionProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public ProyectoDTO getProyectoDTOporImplementacionProyecto(Long idImplementacionProyecto) throws JpaDinaeException {

        try {

            return (ProyectoDTO) entityManager.createNamedQuery("ImplementacionesProyecto.findProyectoPorIdImplementgacionProyecto")
                    .setParameter("idImplementacionProy", idImplementacionProyecto)
                    .getSingleResult();

        } catch (Exception e) {
            throw new JpaDinaeException(e);
        }

    }

    /**
     *
     * @param idUnidadPolicial
     * @param codigoProyecto
     * @param tipoProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaProyectoDTOfiltroBusquedaPorUnidaPoliCodigoProTipoProyecto(Long idUnidadPolicial, String codigoProyecto, String tipoProyecto) throws JpaDinaeException {

        try {

            //PROYECTOS QUE CUMPLAN CON LOS CRITERIOS DE BÚSQUEDA Y QUE SE ENCUENTREN EN ESTADO DIFERENTE A 'CULMINADO' O 'IMPLEMENTADO'
            String sql = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.tituloPropuesto, p.fechaEstimadaInicio, p.unidadPolicial.nombre, p.codigoProyecto )"
                    + " From Proyecto p WHERE p.estado.idConstantes NOT IN (111,114) AND ( p.codigoProyecto LIKE 'VIC%' OR p.codigoProyecto LIKE 'CONV%' OR ( p.codigoProyecto IS NULL AND p.programas IS NOT NULL) ) ";

            if (idUnidadPolicial != null) {

                sql = sql.concat(" AND p.unidadPolicial.idUnidadPolicial = " + idUnidadPolicial);

            }

            //CODIGO PROYECTO DIFERENTE A TRABAJO DE GRADO
            if (tipoProyecto != null && !tipoProyecto.equals("TRABAJO_GRADO")) {

                sql = sql.concat(" AND p.codigoProyecto LIKE '" + tipoProyecto.trim() + "%' AND p.programas IS NULL");

            } //CODIGO PROYECTO IGUAL A TRABAJO DE GRADO
            else if (tipoProyecto != null && tipoProyecto.equals("TRABAJO_GRADO")) {

                sql = sql.concat(" AND p.codigoProyecto IS NULL AND p.programas IS NOT NULL");

            }

            if (codigoProyecto != null && codigoProyecto.trim().length() > 0) {

                sql = sql.concat(" AND p.codigoProyecto = '" + codigoProyecto.trim() + "'");

            }

            return entityManager.createQuery(sql)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e);
        }

    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getListaPropuestasProyectosInvestigacionFiltros(Map<String, Object> criterios) throws JpaDinaeException {

        try {

            StringBuilder jpql = new StringBuilder("SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO"
                    + "( p.idProyecto, p.tema, p.linea.nombre, p.linea.areaCienciaTecnologia.nombre, p.estado.idConstantes, p.estado.valor, p.fechaRegistro, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre) "
                    + " FROM Proyecto p "
                    + " WHERE p.codigoProyecto IS NULL AND p.programas IS NULL");

            Map<String, Object> params = new HashMap<String, Object>();
            boolean aplicaEstado = false;

            if (!criterios.keySet().isEmpty()) {

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_AREA)) {
                    String sqlPart = " AND p.linea.areaCienciaTecnologia.idAreaCienciaTecnologia = :idAreaCienciaTecnologia";
                    jpql.append(sqlPart);
                    params.put("idAreaCienciaTecnologia", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_AREA));
                }

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_LINEA)) {
                    String sqlPart = " AND p.linea.idLinea = :idLinea";
                    jpql.append(sqlPart);
                    params.put("idLinea", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_LINEA));
                }

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_PALABRA_CLAVE)) {
                    String sqlPart = " AND UPPER(p.tema) LIKE UPPER(:p_tema)";
                    jpql.append(sqlPart);
                    params.put("p_tema", "%" + criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_PALABRA_CLAVE) + "%");
                }

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_ESTADO)) {
                    String sqlPart = " AND p.estado.idConstantes = :idEstado";
                    jpql.append(sqlPart);
                    params.put("idEstado", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_ESTADO));
                    aplicaEstado = true;
                }

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_UNIDAD_POLICIAL)) {
                    String sqlPart = " AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial";
                    jpql.append(sqlPart);
                    params.put("idUnidadPolicial", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_UNIDAD_POLICIAL));
                }

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_FECHA_INICIAL)
                        && criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_FECHA_FINAL)) {
                    String sqlPart = " AND p.fechaRegistro BETWEEN :fechaInicioPresentacion AND :fechaFinPresentacion";
                    jpql.append(sqlPart);
                    params.put("fechaInicioPresentacion", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_FECHA_INICIAL));
                    params.put("fechaFinPresentacion", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_FECHA_FINAL));
                }

                if (criterios.containsKey(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_CONVOCATORIA)) {
                    String sqlPart = " AND p.periodo.idPeriodo = :idPeriodo";
                    jpql.append(sqlPart);
                    params.put("idPeriodo", criterios.get(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_CONVOCATORIA));
                }
            }

            if (!aplicaEstado) {

                jpql.append(" AND p.estado.idConstantes IN (86,87)");

            }

            jpql.append(" ORDER BY p.idProyecto ASC ");

            Query query = entityManager.createQuery(jpql.toString());
            Set<String> keys = params.keySet();

            for (String key : keys) {

                query.setParameter(key, params.get(key));

            }

            return query.getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e);
        }
    }

    /**
     *
     * @param objeto
     * @param claseResultante
     * @return
     */
    public Object getValorColumna(Object objeto, Class claseResultante) {

        if (objeto == null) {
            return null;
        }

        if (claseResultante.equals(Long.class)) {

            return ((Number) objeto).longValue();

        }

        if (claseResultante.equals(String.class)) {

            return objeto.toString();

        }

        if (claseResultante.equals(BigDecimal.class)) {

            return BigDecimal.valueOf(((Number) objeto).doubleValue());

        }

        if (claseResultante.equals(Date.class)) {

            return new Date(((Timestamp) objeto).getTime());

        }

        return objeto;
    }

    /**
     *
     * @param idProyecto
     * @param idVersion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public ProyectoDTO getProyectoVersionPorIdProyecto(Long idProyecto, Long idVersion) throws JpaDinaeException {

        try {

            Object objetoProyectoConsulta = entityManager.createNativeQuery(
                    "SELECT ID_PROYECTO_VERSION,ID_VERSION,ID_PROYECTO,CODIGO_PROYECTO,"
                    + "TITULO_PROPUESTO,ID_LINEA,ID_PERIODO,FUNCION_PROYECTO,"
                    + "FECHA_ESTIMADA_INICIO,FECHA_ESTIMADA_FINALIZACION,"
                    + "VALOR_TOTAL,CONVOCATORIA,FECHA_REGISTRO,TEMA,FECHA_APROBACION_COMITE,"
                    + "NRO_ACTA_APROBACION_COMITE,ID_USUARIO_ROL,FECHA_ACTUALIZACION,"
                    + "ID_USUARIO_ROL_ACTUALIZA,ID_ESTADO,INFORME_FINAL,DESCRIPCION_INFORME_FINAL,"
                    + "FECHA_REGISTRO_INFORME_FINAL,ID_PROGRAMA,RESUMEN_PROYECTO,FECHA_APROBACION_COMITE_2,"
                    + "NRO_ACTA_APROB_COMITE,ID_UNIDAD_POLICIAL,ESTADO_TEMPORAL_FINANCIA,VALOR_FINANCIAR,"
                    + "ID_PROPUESTA_NECESIDAD,ID_PROPUESTA_CONVOCATORIA,ID_ESTADO_IMPLEMENTACION,"
                    + "PRESUPUESTO_SOLICITADO FROM PROYECTO_VERSION"
                    + " WHERE ID_PROYECTO = " + idProyecto + " AND ID_VERSION = " + idVersion)
                    .getSingleResult();

            ProyectoDTO proyectoDTO = new ProyectoDTO();

            Object[] objetoProyecto = (Object[]) objetoProyectoConsulta;

            proyectoDTO.setIdProyectoVersion((Long) getValorColumna(objetoProyecto[0], Long.class));//ID_PROYECTO_VERSION

            proyectoDTO.setIdVersion((Long) getValorColumna(objetoProyecto[1], Long.class));//ID_VERSION

            proyectoDTO.setId((Long) getValorColumna(objetoProyecto[2], Long.class));//ID_PROYECTO

            proyectoDTO.setCodigoProyecto((String) getValorColumna(objetoProyecto[3], String.class));//CODIGO_PROYECTO

            proyectoDTO.setTituloPropuesto((String) getValorColumna(objetoProyecto[4], String.class));//TITULO_PROPUESTO

            proyectoDTO.setIdLinea((Long) getValorColumna(objetoProyecto[5], Long.class));//ID_LINEA
            if (proyectoDTO.getIdLinea() != null) {

                Linea linea = iLineaLocal.obtenerLineaPorId(proyectoDTO.getIdLinea());

                proyectoDTO.setNombreLinea(linea.getNombre());
                proyectoDTO.setNombreArea(linea.getAreaCienciaTecnologia().getNombre());

            }

            proyectoDTO.setIdPeriodo((Long) getValorColumna(objetoProyecto[6], Long.class));//ID_PERIODO

            proyectoDTO.setFuncionProyecto((String) getValorColumna(objetoProyecto[7], String.class));//FUNCION_PROYECTO

            proyectoDTO.setFechaEstimadaInicio((Date) getValorColumna(objetoProyecto[8], Date.class));//FECHA_ESTIMADA_INICIO

            proyectoDTO.setFechaEstimadaFinalizacion((Date) getValorColumna(objetoProyecto[9], Date.class));//FECHA_ESTIMADA_FINALIZACION

            proyectoDTO.setValorTotal((BigDecimal) getValorColumna(objetoProyecto[10], BigDecimal.class));//VALOR_TOTAL

            proyectoDTO.setConvocatoria((String) getValorColumna(objetoProyecto[11], String.class));//CONVOCATORIA

            proyectoDTO.setFechaRegistro((Date) getValorColumna(objetoProyecto[12], Date.class));//FECHA_REGISTRO

            proyectoDTO.setTema((String) getValorColumna(objetoProyecto[13], String.class));//TEMA

            proyectoDTO.setFechaAprobacionComite((Date) getValorColumna(objetoProyecto[14], Date.class));//FECHA_APROBACION_COMITE

            proyectoDTO.setNroActaAprobComite((String) getValorColumna(objetoProyecto[15], String.class));//NRO_ACTA_APROBACION_COMITE

            proyectoDTO.setIdUusuarioRol((Long) getValorColumna(objetoProyecto[16], Long.class));//ID_USUARIO_ROL

            proyectoDTO.setFechaActualizacion((Date) getValorColumna(objetoProyecto[17], Date.class));//FECHA_ACTUALIZACION

            //ID_USUARIO_ROL_ACTUALIZA 18
            proyectoDTO.setIdEstado((Long) getValorColumna(objetoProyecto[19], Long.class));//ID_ESTADO

            //INFORME_FINAL 20
            //DESCRIPCION_INFORME_FINAL 21
            //FECHA_REGISTRO_INFORME_FINAL 22
            proyectoDTO.setIdProgramas((Long) getValorColumna(objetoProyecto[23], Long.class)); //ID_PROGRAMA

            proyectoDTO.setResumenProyecto((String) getValorColumna(objetoProyecto[24], String.class));//RESUMEN_PROYECTO

            proyectoDTO.setFechaAprobacionComite2((Date) getValorColumna(objetoProyecto[25], Date.class));//FECHA_APROBACION_COMITE_2

            proyectoDTO.setNroActaAprobacionComite((String) getValorColumna(objetoProyecto[26], String.class));//NRO_ACTA_APROB_COMITE             

            proyectoDTO.setIdUnidadPolicial((Long) getValorColumna(objetoProyecto[27], Long.class));//ID_UNIDAD_POLICIAL
            if (proyectoDTO.getIdUnidadPolicial() != null) {

                UnidadPolicial unidadPolicial = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(proyectoDTO.getIdUnidadPolicial());
                UnidadPolicialDTO unidadPolicialDTO = new UnidadPolicialDTO();
                unidadPolicialDTO.setIdUnidadPolicial(unidadPolicial.getIdUnidadPolicial());
                unidadPolicialDTO.setNombre(unidadPolicial.getNombre());
                unidadPolicialDTO.setSiglaFisicaUnidad(unidadPolicial.getSiglaFisica());
                unidadPolicialDTO.setSiglaPadre(unidadPolicial.getSiglaPapa());

                proyectoDTO.setUnidadPolicialDTO(unidadPolicialDTO);

            }

            //ESTADO_TEMPORAL_FINANCIA 28
            proyectoDTO.setValorFinanciar((BigDecimal) getValorColumna(objetoProyecto[29], BigDecimal.class));//VALOR_FINANCIAR

            proyectoDTO.setIdPropuestaNecesidad((Long) getValorColumna(objetoProyecto[30], Long.class));//ID_PROPUESTA_NECESIDAD

            proyectoDTO.setIdPropuestaConvocatoria((Long) getValorColumna(objetoProyecto[31], Long.class));//ID_PROPUESTA_CONVOCATORIA                                 

            proyectoDTO.setIdEstadoImplementacion((Long) getValorColumna(objetoProyecto[32], Long.class));//ID_ESTADO_IMPLEMENTACION

            proyectoDTO.setPresupuestoSolicitado((BigDecimal) getValorColumna(objetoProyecto[33], BigDecimal.class));//PRESUPUESTO_SOLICITADO

            //CONSULTAMOS EL TIPO DE PERIODO
            Object objetoPeriodo = entityManager.createNativeQuery(
                    "SELECT ID_TIPO_PERIODO FROM PERIODO WHERE ID_PERIODO = " + proyectoDTO.getIdPeriodo())
                    .getSingleResult();

            if (objetoPeriodo != null) {
                proyectoDTO.setIdTipoPeriodo((Long) getValorColumna(objetoPeriodo, Long.class));//ID_TIPO_PERIODO
            }

            return proyectoDTO;

        } catch (Exception e) {

            throw new JpaDinaeException(e);

        }
    }

    /**
     *
     * @param idProyecto
     * @throws JpaDinaeException
     */
    /*
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void executeStoredProcedureCrearVersionProyecto(Long idProyecto) throws JpaDinaeException {
        try {

            //CREAMOS LA VERSION
            Versiones versiones = new Versiones();
            versiones.setFechaVersion(new Date());
            versiones.setProyecto(new Proyecto(idProyecto));

            entityManager.persist(versiones);

            entityManager.createNamedQuery("ProcedimientoGenerarVersionProyecto")
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idVersion", versiones.getIdVersion())
                    .executeUpdate();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }
     */
    @Override
    public void executeStoredProcedureCrearVersionProyecto(Long idProyecto) throws JpaDinaeException {
        LOG.debug("metodo: executeStoredProcedureCrearVersionProyecto() ->> parametros: idProyecto {}", idProyecto);
        try {
            Proyecto proyecto = entityManager.createNamedQuery("Proyecto.findById", Proyecto.class)
                    .setParameter("idProyecto", idProyecto)
                    .getSingleResult();

            Versiones versiones = crearVersion(proyecto);

            Connection connection = this.datasource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("call ");
            sb.append("PRC_VERSION_PROYECTO");
            sb.append("(?,?)");
            CallableStatement cs = connection.prepareCall(sb.toString());
            cs.setLong(1, idProyecto);
            cs.setLong(2, versiones.getIdVersion());
            cs.executeQuery();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }

    }

    /**
     *
     * @param idConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> findAllPropuestaConvocatoriaPorConvocatoria(Long idConvocatoria) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaConvocatoriaPorConvocatoria")
                    .setParameter("idPeriodo", idConvocatoria)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param palabraClave
     * @param tiposProyectoSeleccionados
     * @param idUnidadPolicial
     * @param idTiposUnidadesSeleccionadas
     * @param idAreaCienciaTecnologiaSeleccionada
     * @param idLineaSeleccionada
     * @param valorProyectoRangoInicial
     * @param valorProyectoRangoFinal
     * @param codigoProyecto
     * @param idEstadoProyecto
     * @param idOrigenProyecto
     * @param idEstadoImplementacion
     * @param idNombreProgramaSeleccionado
     * @param fechaPresentacionInicial
     * @param fechaPresentacionFinal
     * @param notaFinalRangoInicial
     * @param notaFinalRangoFinal
     * @param idGrupoInvestigacion
     * @param idSemillero
     * @param nombresYApellidos
     * @param idConvocatoria
     * @param idFormasDeVer
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getProyectosDTOOTrabajosBusquedaPorFiltros(String palabraClave,
            List<Long> tiposProyectoSeleccionados,
            Long idUnidadPolicial,
            List<Long> idTiposUnidadesSeleccionadas,
            Long idAreaCienciaTecnologiaSeleccionada,
            Long idLineaSeleccionada,
            BigDecimal valorProyectoRangoInicial,
            BigDecimal valorProyectoRangoFinal,
            String codigoProyecto,
            List<Long> idEstadoProyecto,
            Long idOrigenProyecto,
            List<Long> idEstadoImplementacion,
            Long idNombreProgramaSeleccionado,
            Date fechaPresentacionInicial,
            Date fechaPresentacionFinal,
            BigDecimal notaFinalRangoInicial,
            BigDecimal notaFinalRangoFinal,
            Long idGrupoInvestigacion,
            Long idSemillero,
            String nombresYApellidos,
            Long idConvocatoria,
            Long idFormasDeVer) throws JpaDinaeException {

        //Generacion del String de consulta - Genera el query con las condiciones necesarias
        StringBuilder query = new StringBuilder();

        query.append("SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.codigoProyecto, p.tituloPropuesto, p.fechaRegistro, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.unidadPolicial.siglaFisica, p.linea.areaCienciaTecnologia.nombre, p.fechaEstimadaInicio, p.fechaEstimadaFinalizacion ) FROM Proyecto p WHERE p.idProyecto IS NOT NULL AND p.programas IS NULL ");

        if (palabraClave != null && !palabraClave.equals("")) {
            query.append(" AND upper(p.tituloPropuesto) like upper(:palabraClaveTitulo)");
        }

        if (tiposProyectoSeleccionados != null && tiposProyectoSeleccionados.size() > 0) {
            for (Long unIdConstantesTipoProyectoSeleccionado : tiposProyectoSeleccionados) {
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_INVESTIGACIONES_INSTITUCIONALES)) {
                    query.append(" AND p.codigoProyecto like 'VIC%'");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_TRABAJOS_DE_GRADO)) {
                    query.append(" AND p.programas IS NOT NULL");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_PARA_FINANCIACION)) {
                    query.append(" AND p.codigoProyecto like 'CONV%'");
                }
            }
        }

        if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
            query.append(" AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial");
        }

        if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
            for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                query.append(" AND p.unidadPolicial.tipoUnidad.idTipoUnidad = :idTipoUnidad");
                query.append(unIdTipoUnidad);
            }
        }

        if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
            query.append(" AND (p.linea.idLinea IN(SELECT l.idLinea FROM Linea l WHERE l.areaCienciaTecnologia.idAreaCienciaTecnologia = :idAreaCienciaTecnologia))");
        }

        if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
            query.append(" AND p.linea.idLinea = :idLinea");
        }

        if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
            query.append(" AND p.valorTotal BETWEEN :valorProyectoRangoInicial AND :valorProyectoRangoFinal");
        }

        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            query.append(" AND upper(p.codigoProyecto) like upper(:codigoProyecto)");
        }

        //TODO Falta hacer las busquedas por origenes
        if (idOrigenProyecto != null && idOrigenProyecto > 0L) {
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_BANCO_DE_NECESIDADES)) {
                //query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_PROPUESTA_DE_NECESIDADES)) {
                query.append(" AND p.propuestaNecesidad IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_CONVOCATORIA)) {
                query.append(" AND p.propuestaConvocatoria IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_TRABAJO_DE_GRADO)) {
                query.append(" AND p.programas IS NOT NULL");
            }
        }
        //Consulta por una lista de estado de implementación
        if (idEstadoImplementacion != null && idEstadoImplementacion.size() > 0L) {
            if (idEstadoImplementacion.size() == 1) {
                query.append(" AND p.estadoImplementacion.idConstantes = :idEstadoImplementacion");
            } else {
                int i = 0;
                Iterator<Long> iterador = idEstadoImplementacion.iterator();
                query.append(" AND (");
                while (iterador.hasNext()) {
                    iterador.next();
                    query.append("p.estadoImplementacion.idConstantes = :idEstadoImplementacion");
                    query.append(String.valueOf(i));
                    if (iterador.hasNext()) {
                        query.append(" OR ");
                    } else {
                        query.append(" ) ");
                    }
                    i++;
                }
            }

        }

        if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
            query.append(" AND p.programas.idPrograma = :idPrograma");
        }

        if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
            query.append(" AND p.fechaRegistro BETWEEN :fechaPresentacionInicial AND :fechaPresentacionFinal");
        }

        if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
            //Para trabajos de grado
            query.append(" AND (p.idProyecto IN (SELECT eg.proyecto.idProyecto FROM EvaluacionProyectoGrado eg WHERE eg.notaFinal BETWEEN :notaFinalRangoInicial AND :notaFinalRangoFinal))");
            //Para proyectos
            query.append(" UNION SELECT p FROM Proyecto p WHERE p.idProyecto IS NOT NULL AND (p.idProyecto IN (SELECT ep.proyecto.idProyecto FROM EvaluacionProyecto ep GROUP BY ep.proyecto.idProyecto HAVING (AVG(ep.valorCriterio) AS promedioValorCriterio BETWEEN :notaFinalPromedioRangoInicial AND :notaFinalPromedioRangoFinal)))");

        }

        if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT g.proyecto.idProyecto FROM GrupoInvestigacionProyecto g WHERE g.grupoInvestigacion.idGrupoInvestigacion = :idGrupoInvestigacion))");
        }

        if (idSemillero != null && idSemillero > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT s.proyecto.idProyecto FROM SemilleroProyecto s WHERE s.semilleroInvestigacion.idSemillero = :idSemillero))");
        }

        if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
            query.append(" AND (p.idProyecto IN(SELECT i.proyecto.idProyecto FROM InvestigadorProyecto i WHERE upper(i.nombreCompleto) like upper(:nombresYApellidos)))");
        }

        if (idConvocatoria != null && idConvocatoria > 0L) {
            query.append(" AND p.periodo.idPeriodo = :idConvocatoria");
        }

        //Consulta por una lista de estado de proyecto
        if (idEstadoProyecto != null && idEstadoProyecto.size() > 0L) {
            if (idEstadoProyecto.size() == 1) {
                query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
            } else {
                int i = 0;
                Iterator<Long> iterador = idEstadoProyecto.iterator();
                query.append(" AND (");
                while (iterador.hasNext()) {
                    iterador.next();
                    query.append(" p.estado.idConstantes = :idEstadoProyecto");
                    query.append(String.valueOf(i));
                    if (iterador.hasNext()) {
                        query.append(" OR ");
                    } else {
                        query.append(" ) ");
                    }
                    i++;
                }
            }

        }

        try {

            //Ejecucion de la consulta - Se introduce el valor de los parámetros
            Query queryFinal = entityManager.createQuery(query.toString());

            if (palabraClave != null && !palabraClave.equals("")) {
                queryFinal.setParameter("palabraClaveTitulo", "%" + palabraClave + "%");
            }

            if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
                queryFinal.setParameter("idUnidadPolicial", idUnidadPolicial);
            }

            if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
                for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                    queryFinal.setParameter("idTipoUnidad" + unIdTipoUnidad, unIdTipoUnidad);
                }
            }

            if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
                queryFinal.setParameter("idAreaCienciaTecnologia", idAreaCienciaTecnologiaSeleccionada);
            }

            if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
                queryFinal.setParameter("idLinea", idLineaSeleccionada);
            }

            if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
                queryFinal.setParameter("valorProyectoRangoInicial", valorProyectoRangoInicial);
                queryFinal.setParameter("valorProyectoRangoFinal", valorProyectoRangoFinal);
            }

            if (codigoProyecto != null && !codigoProyecto.equals("")) {
                queryFinal.setParameter("codigoProyecto", "%" + codigoProyecto + "%");
            }

            if (idEstadoProyecto != null && idEstadoProyecto.size() > 0L) {
                if (idEstadoProyecto.size() == 1) {
                    queryFinal.setParameter("idEstadoProyecto", idEstadoProyecto.get(0));
                } else {
                    int i = 0;
                    Iterator<Long> iterador = idEstadoProyecto.iterator();
                    query.append(" AND (");
                    while (iterador.hasNext()) {
                        Long idEstadoProyectoLong = iterador.next();
                        queryFinal.setParameter("idEstadoProyecto" + i, idEstadoProyectoLong);
                        i++;
                    }
                }
            }

            if (idEstadoImplementacion != null && idEstadoImplementacion.size() > 0L) {
                if (idEstadoImplementacion.size() == 1) {
                    queryFinal.setParameter("idEstadoImplementacion", idEstadoImplementacion.get(0));
                } else {
                    int i = 0;
                    Iterator<Long> iterador = idEstadoImplementacion.iterator();
                    query.append(" AND (");
                    while (iterador.hasNext()) {
                        Long idEstadoImplementacionLong = iterador.next();
                        queryFinal.setParameter("idEstadoImplementacion" + i, idEstadoImplementacionLong);
                        i++;
                    }
                }
            }

            if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
                queryFinal.setParameter("idPrograma", idNombreProgramaSeleccionado);
            }

            if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {

                Calendar cFi = Calendar.getInstance();
                cFi.setTime(fechaPresentacionInicial);
                cFi.set(Calendar.HOUR, 0);
                cFi.set(Calendar.MINUTE, 0);
                cFi.set(Calendar.SECOND, 0);
                cFi.set(Calendar.MILLISECOND, 0);

                Calendar cFf = Calendar.getInstance();
                cFf.setTime(fechaPresentacionFinal);
                cFf.set(Calendar.HOUR, cFf.getMaximum(Calendar.HOUR));
                cFf.set(Calendar.MINUTE, cFf.getMaximum(Calendar.MINUTE));
                cFf.set(Calendar.SECOND, cFf.getMaximum(Calendar.SECOND));
                cFf.set(Calendar.MILLISECOND, cFf.getMaximum(Calendar.MILLISECOND));

                queryFinal.setParameter("fechaPresentacionInicial", cFi.getTime());
                queryFinal.setParameter("fechaPresentacionFinal", cFf.getTime());
            }

            if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
                queryFinal.setParameter("notaFinalRangoInicial", notaFinalRangoInicial);
                queryFinal.setParameter("notaFinalRangoFinal", notaFinalRangoFinal);
            }

            if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
                queryFinal.setParameter("idGrupoInvestigacion", idGrupoInvestigacion);
            }

            if (idSemillero != null && idSemillero > 0L) {
                queryFinal.setParameter("idSemillero", idSemillero);
            }

            if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
                queryFinal.setParameter("nombresYApellidos", "%" + nombresYApellidos + "%");
            }

            if (idConvocatoria != null && idConvocatoria > 0L) {
                queryFinal.setParameter("idConvocatoria", idConvocatoria);
            }

            return queryFinal.getResultList();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @param palabraClave
     * @param tiposProyectoSeleccionados
     * @param idUnidadPolicial
     * @param idTiposUnidadesSeleccionadas
     * @param idAreaCienciaTecnologiaSeleccionada
     * @param idLineaSeleccionada
     * @param valorProyectoRangoInicial
     * @param valorProyectoRangoFinal
     * @param codigoProyecto
     * @param idEstadoProyecto
     * @param idOrigenProyecto
     * @param idEstadoImplementacion
     * @param idNombreProgramaSeleccionado
     * @param fechaPresentacionInicial
     * @param fechaPresentacionFinal
     * @param notaFinalRangoInicial
     * @param notaFinalRangoFinal
     * @param idGrupoInvestigacion
     * @param idSemillero
     * @param nombresYApellidos
     * @param idConvocatoria
     * @param idFormasDeVer
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> getProyectosDTOOTrabajosBusquedaPorFiltros(
            String tipoProyectoOtgrabajoGrado,
            String palabraClave,
            List<Long> tiposProyectoSeleccionados,
            Long idUnidadPolicial,
            List<Long> idTiposUnidadesSeleccionadas,
            Long idAreaCienciaTecnologiaSeleccionada,
            Long idLineaSeleccionada,
            BigDecimal valorProyectoRangoInicial,
            BigDecimal valorProyectoRangoFinal,
            String codigoProyecto,
            Long idEstadoProyecto,
            Long idOrigenProyecto,
            Long idEstadoImplementacion,
            Long idNombreProgramaSeleccionado,
            Date fechaPresentacionInicial,
            Date fechaPresentacionFinal,
            BigDecimal notaFinalRangoInicial,
            BigDecimal notaFinalRangoFinal,
            Long idGrupoInvestigacion,
            Long idSemillero,
            String nombresYApellidos,
            Long idConvocatoria,
            Long idFormasDeVer) throws JpaDinaeException {

        //Generacion del String de consulta - Genera el query con las condiciones necesarias
        StringBuilder query = new StringBuilder("SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.codigoProyecto, p.tituloPropuesto, p.fechaRegistro, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.unidadPolicial.siglaFisica, p.linea.areaCienciaTecnologia.nombre, p.concecutivoProyectoGrado ) FROM Proyecto p WHERE p.idProyecto IS NOT NULL ");

        if ("PROYECTO".equals(tipoProyectoOtgrabajoGrado)) {

            query.append(" AND p.codigoProyecto IS NOT NULL AND p.codigoProyecto NOT LIKE 'MTG%' ");

        } else if ("TRABAJO_GRADO".equals(tipoProyectoOtgrabajoGrado)) {
            //Se comentaria a fin de que el filtro se realice correctamente, se elimina el condicional OR
            //query.append(" AND ( p.codigoProyecto IS NULL AND p.programas IS NOT NULL) OR ( p.codigoProyecto LIKE 'MTG%' ) ");
            query.append(" AND ( p.codigoProyecto IS NULL AND p.programas IS NOT NULL) ");

        } else {

            query.append(" AND p.codigoProyecto IS NOT NULL ");

        }

        if (palabraClave != null && !palabraClave.equals("")) {
            query.append(" AND upper(p.tituloPropuesto) like upper(:palabraClaveTitulo)");
        }

        if (tiposProyectoSeleccionados != null && tiposProyectoSeleccionados.size() > 0) {
            for (Long unIdConstantesTipoProyectoSeleccionado : tiposProyectoSeleccionados) {
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_INVESTIGACIONES_INSTITUCIONALES)) {
                    query.append(" AND ( p.codigoProyecto like 'VIC%' OR p.codigoProyecto like 'MVIC%') ");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_TRABAJOS_DE_GRADO)) {
                    query.append(" AND ( p.programas IS NOT NULL OR p.codigoProyecto like 'MTG%') )");
                }
                if (unIdConstantesTipoProyectoSeleccionado.equals(IConstantes.TIPO_TIPOS_DE_PROYECTO_PARA_FINANCIACION)) {
                    query.append(" AND p.codigoProyecto like 'CONV%'");
                }
            }
        }

        if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
            query.append(" AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial");
        }

        if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
            for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                query.append(" AND p.unidadPolicial.tipoUnidad.idTipoUnidad = :idTipoUnidad");
                query.append(unIdTipoUnidad);
            }
        }

        if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
            query.append(" AND (p.linea.idLinea IN(SELECT l.idLinea FROM Linea l WHERE l.areaCienciaTecnologia.idAreaCienciaTecnologia = :idAreaCienciaTecnologia))");
        }

        if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
            query.append(" AND p.linea.idLinea = :idLinea");
        }

        if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
            query.append(" AND p.valorTotal BETWEEN :valorProyectoRangoInicial AND :valorProyectoRangoFinal");
        }

        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            query.append(" AND upper(p.codigoProyecto) like upper(:codigoProyecto)");
        }

        //TODO Falta hacer las busquedas por origenes
        if (idOrigenProyecto != null && idOrigenProyecto > 0L) {
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_BANCO_DE_NECESIDADES)) {
                //query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_PROPUESTA_DE_NECESIDADES)) {
                query.append(" AND p.propuestaNecesidad IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_CONVOCATORIA)) {
                query.append(" AND p.propuestaConvocatoria IS NOT NULL");
            }
            if (idOrigenProyecto.equals(IConstantes.TIPO_ORIGENES_PROYECTO_TRABAJO_DE_GRADO)) {
                query.append(" AND p.programas IS NOT NULL");
            }
        }

        if (idEstadoImplementacion != null && idEstadoImplementacion > 0L) {
            query.append(" AND p.estadoImplementacion.idConstantes = :idEstadoImplementacion");
        }

        if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
            query.append(" AND p.programas.idPrograma = :idPrograma");
        }

        if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
            query.append(" AND p.fechaRegistro BETWEEN :fechaPresentacionInicial AND :fechaPresentacionFinal");
        }

        if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
            //Para trabajos de grado
            query.append(" AND (p.idProyecto IN (SELECT eg.proyecto.idProyecto FROM EvaluacionProyectoGrado eg WHERE eg.notaFinal BETWEEN :notaFinalRangoInicial AND :notaFinalRangoFinal))");
            //Para proyectos
            query.append(" UNION SELECT p FROM Proyecto p WHERE p.idProyecto IS NOT NULL AND (p.idProyecto IN (SELECT ep.proyecto.idProyecto FROM EvaluacionProyecto ep GROUP BY ep.proyecto.idProyecto HAVING (AVG(ep.valorCriterio) AS promedioValorCriterio BETWEEN :notaFinalPromedioRangoInicial AND :notaFinalPromedioRangoFinal)))");

        }

        if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT g.proyecto.idProyecto FROM GrupoInvestigacionProyecto g WHERE g.grupoInvestigacion.idGrupoInvestigacion = :idGrupoInvestigacion))");
        }

        if (idSemillero != null && idSemillero > 0L) {
            query.append(" AND (p.idProyecto IN(SELECT s.proyecto.idProyecto FROM SemilleroProyecto s WHERE s.semilleroInvestigacion.idSemillero = :idSemillero))");
        }

        if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
            query.append(" AND (p.idProyecto IN(SELECT i.proyecto.idProyecto FROM InvestigadorProyecto i WHERE upper(i.nombreCompleto) like upper(:nombresYApellidos)))");
        }

        if (idConvocatoria != null && idConvocatoria > 0L) {
            query.append(" AND p.periodo.idPeriodo = :idConvocatoria");
        }

        if (idEstadoProyecto != null && idEstadoProyecto > 0L) {
            query.append(" AND p.estado.idConstantes = :idEstadoProyecto");
        }

        query.append(" ORDER BY p.codigoProyecto ASC ");

        try {

            //Ejecucion de la consulta - Se introduce el valor de los parámetros
            Query queryFinal = entityManager.createQuery(query.toString());

            if (palabraClave != null && !palabraClave.equals("")) {
                queryFinal.setParameter("palabraClaveTitulo", "%" + palabraClave + "%");
            }

            if (idUnidadPolicial != null && idUnidadPolicial > 0L) {
                queryFinal.setParameter("idUnidadPolicial", idUnidadPolicial);
            }

            if (idTiposUnidadesSeleccionadas != null && idTiposUnidadesSeleccionadas.size() > 0) {
                for (Long unIdTipoUnidad : idTiposUnidadesSeleccionadas) {
                    queryFinal.setParameter("idTipoUnidad" + unIdTipoUnidad, unIdTipoUnidad);
                }
            }

            if (idAreaCienciaTecnologiaSeleccionada != null && idAreaCienciaTecnologiaSeleccionada > 0L) {
                queryFinal.setParameter("idAreaCienciaTecnologia", idAreaCienciaTecnologiaSeleccionada);
            }

            if (idLineaSeleccionada != null && idLineaSeleccionada > 0L) {
                queryFinal.setParameter("idLinea", idLineaSeleccionada);
            }

            if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {
                queryFinal.setParameter("valorProyectoRangoInicial", valorProyectoRangoInicial);
                queryFinal.setParameter("valorProyectoRangoFinal", valorProyectoRangoFinal);
            }

            if (codigoProyecto != null && !codigoProyecto.equals("")) {
                queryFinal.setParameter("codigoProyecto", "%" + codigoProyecto + "%");
            }

            if (idEstadoProyecto != null && idEstadoProyecto > 0L) {
                queryFinal.setParameter("idEstadoProyecto", idEstadoProyecto);
            }

            if (idEstadoImplementacion != null && idEstadoImplementacion > 0L) {
                queryFinal.setParameter("idEstadoImplementacion", idEstadoImplementacion);
            }

            if (idNombreProgramaSeleccionado != null && idNombreProgramaSeleccionado > 0L) {
                queryFinal.setParameter("idPrograma", idNombreProgramaSeleccionado);
            }

            if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {
                queryFinal.setParameter("fechaPresentacionInicial", fechaPresentacionInicial);
                queryFinal.setParameter("fechaPresentacionFinal", fechaPresentacionFinal);
            }

            if (notaFinalRangoInicial != null && notaFinalRangoFinal != null) {
                queryFinal.setParameter("notaFinalRangoInicial", notaFinalRangoInicial);
                queryFinal.setParameter("notaFinalRangoFinal", notaFinalRangoFinal);
            }

            if (idGrupoInvestigacion != null && idGrupoInvestigacion > 0L) {
                queryFinal.setParameter("idGrupoInvestigacion", idGrupoInvestigacion);
            }

            if (idSemillero != null && idSemillero > 0L) {
                queryFinal.setParameter("idSemillero", idSemillero);
            }

            if (nombresYApellidos != null && !nombresYApellidos.equals("")) {
                queryFinal.setParameter("nombresYApellidos", "%" + nombresYApellidos + "%");
            }

            if (idConvocatoria != null && idConvocatoria > 0L) {
                queryFinal.setParameter("idConvocatoria", idConvocatoria);
            }

            return queryFinal.getResultList();

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @param idPeriodo
     * @param idUnidadPolicial
     * @param estados
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> findAllPropuestaNecesidadConvocatoriaByPeriodoEstados(Long idPeriodo, Long idUnidadPolicial, Long[] estados) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("ProyectoDTO.findAllPropuestaNecesidadConvocatoriaByPeriodoEstados")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("estados", Arrays.asList(estados))
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<ProyectoDTO> findAllProyectosInvestigacionVigentesUnidad(Long idUnidadPolicial) throws JpaDinaeException {
        try {
            Long idEstado = IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION;

            return entityManager.createNamedQuery("ProyectoDTO.findAllProyectosInvestigacionVigentesUnidad")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idEstado", idEstado)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e);
        }
    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public int obtenerUltimoConcecutivoTrabajoGrado() throws JpaDinaeException {

        try {

            String valor = (String) entityManager.createNamedQuery("Proyecto.ultimoRegistroProyectoGrado")
                    .getSingleResult();

            if (valor == null) {
                return 0;
            }

            return Integer.parseInt(valor.trim());

        } catch (NoResultException e) {

            return 0;

        } catch (Exception e) {

            throw new JpaDinaeException(e);

        }
    }

    /**
     *
     * @param idProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<HistorialEstadoProyectosMigradosDTO> listaHistorialEstadoProyectosMigradosDTO(Long idProyecto) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("HistorialEstadoProyecto.consultarPorProyecto")
                    .setParameter("idProyecto", idProyecto)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e);

        }
    }

    /**
     *
     * @param idProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<EvaluadoresProyectoMigrados> listaEvaluadoresProyectoMigrados(Long idProyecto) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("EvaluadoresProyectoMigrados.findAllPorProyecto")
                    .setParameter("idProyecto", idProyecto)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e);

        }
    }

    @Override
    public int contarProyectoByVigencia(List<Long> listaPropuestaNecesidad) throws JpaDinaeException {
        try {
            List<Proyecto> lst = entityManager.createNamedQuery("Proyecto.findByVigencia")
                    .setParameter("idPropuestaNecesidad", listaPropuestaNecesidad)
                    .getResultList();
            if (lst.isEmpty()) {
                return 0;
            }
            return lst.size();
        } catch (Exception e) {
            throw new JpaDinaeException(e);
        }
    }

    public Versiones crearVersion(Proyecto proyecto) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: proyecto {}", proyecto);
        Versiones versiones = new Versiones();
        try {
            versiones.setFechaVersion(new Date());
            versiones.setProyecto(proyecto);
            entityManager.persist(versiones);
            entityManager.flush();
            return versiones;
        } catch (Exception ex) {
            versiones.setIdVersion(null);
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
