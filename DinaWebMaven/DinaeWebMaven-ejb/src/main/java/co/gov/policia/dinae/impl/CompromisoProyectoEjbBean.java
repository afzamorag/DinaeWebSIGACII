package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CompromisoProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IActividadesImplementacionLocal;
import co.gov.policia.dinae.interfaces.IActividadesRealizadasProyectoLocal;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionEquiposProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionEventosProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionOtrosGastosProyLocal;
import co.gov.policia.dinae.interfaces.IEjecucionViajesProyectoLocal;
import co.gov.policia.dinae.interfaces.IEvidenciaProyectoLocal;
import co.gov.policia.dinae.interfaces.IExcepcionCompromisoLocal;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.interfaces.IHorasInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresPlanTrabajoLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresProyectoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceImplementacionLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IPersonalImplementacionLocal;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IResultadosAlcanzadosProyectoLocal;
import co.gov.policia.dinae.interfaces.ISugerenciasProyectoLocal;
import co.gov.policia.dinae.interfaces.ITemaProyectoLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.ActividadesImplementacion;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import co.gov.policia.dinae.modelo.ActividadesRealizadasProyecto;
import co.gov.policia.dinae.modelo.ComentarioCompromisoProyecto;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecucionEquiposProyecto;
import co.gov.policia.dinae.modelo.EjecucionEventosProyecto;
import co.gov.policia.dinae.modelo.EjecucionOtrosGastosProy;
import co.gov.policia.dinae.modelo.EjecucionViajesProyecto;
import co.gov.policia.dinae.modelo.EvidenciaProyecto;
import co.gov.policia.dinae.modelo.ExcepcionesCompromiso;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import co.gov.policia.dinae.modelo.HorasInvestigador;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import co.gov.policia.dinae.modelo.IndicadoresCompromisoProyecto;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajo;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.InformeAvance;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PersonalImplementacion;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.ResponsableActividaImplementacion;
import co.gov.policia.dinae.modelo.ResultadosAlcanzadosProyecto;
import co.gov.policia.dinae.modelo.SugerenciasProyecto;
import co.gov.policia.dinae.modelo.TemaProyecto;
import co.gov.policia.dinae.modelo.UsuarioRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.CopyGroup;
import java.sql.CallableStatement;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class CompromisoProyectoEjbBean implements ICompromisoProyectoLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;

    @EJB
    private ICompromisoProyectoLocal iCompromisoProyectoLocal;

    @EJB
    private IActividadesImplementacionLocal iActividadesImplementacionLocal;

    @EJB
    private ISugerenciasProyectoLocal iSugerenciasProyectoLocal;

    @EJB
    private IComentarioProyectoLocal iComentarioProyectoLocal;

    @EJB
    private IIndicadoresProyectoLocal iIndicadoresProyectoLocal;

    @EJB
    private IActividadesRealizadasProyectoLocal iActividadesRealizadasProyectoLocal;

    @EJB
    private IResultadosAlcanzadosProyectoLocal iResultadosAlcanzadosProyectoLocal;

    @EJB
    private IHorasInvestigadorLocal iHorasInvestigadorLocal;

    @EJB
    private IEvidenciaProyectoLocal iEvidenciaProyectoLocal;

    @EJB
    private IExcepcionCompromisoLocal iExcepcionCompromisoLocal;

    @EJB
    private IInformeAvanceLocal iInformeAvanceLocal;

    @EJB
    private IInformeAvanceImplementacionLocal iInformeAvanceImplementacionLocal;

    @EJB
    private IPlanTrabajoImplementacionLocal iPlanTrabajoImplementacionLocal;

    @EJB
    private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

    @EJB
    private IIndicadoresPlanTrabajoLocal iIndicadoresPlanTrabajoLocal;

    @EJB
    private IPersonalImplementacionLocal iPersonalImplementacionLocal;

    @EJB
    private ITemaProyectoLocal iTemaProyectoLocal;

    @EJB
    private IFuenteProyectoLocal iFuenteProyectoLocal;

    @EJB
    private IEjecucionEquiposProyectoLocal iEjecucionEquiposProyectoLocal;

    @EJB
    private IEjecucionEventosProyectoLocal iEjecucionEventosProyectoLocal;

    @EJB
    private IEjecucionOtrosGastosProyLocal iEjecucionOtrosGastosProyLocal;

    @EJB
    private IEjecucionViajesProyectoLocal iEjecucionViajesProyectoLocal;

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ResumenPresupuestoProyectoEjbBean.class);
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;

    /**
     *
     * @param idCompromisoProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public CompromisoProyecto obtenerCompromisoProyecto(Long idCompromisoProyecto) throws JpaDinaeException {

        try {

            return entityManager.find(CompromisoProyecto.class, idCompromisoProyecto);

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }

    }

    /**
     *
     * @param idCompromisoProyectoActual
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<Long> obtenerListaIdCompromisoProyectoAnterioresSoloInformeAvance(Long idCompromisoProyectoActual) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("CompromisoProyecto.finLIstaIDCompromisoProyectoAnterioresPorTipoInforme")
                    .setParameter("idTipoCompromiso", IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                    .setParameter("idCompromisoProyecto", idCompromisoProyectoActual)
                    .setParameter("idEstadoCompromiso", IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }

    /**
     *
     * @param idCompromisoProyectoActual
     * @param idTipoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public CompromisoProyecto obtenerCompromisoProyectoAnteriorTipoAvance(final Long idCompromisoProyectoActual, final Long idTipoCompromiso) throws JpaDinaeException {

        try {

            CompromisoProyecto compromisoProyectoActual = entityManager.find(CompromisoProyecto.class, idCompromisoProyectoActual);

            if (compromisoProyectoActual.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)) {
                //COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO NO TIENE COMPROMISO ANTERIOR
                return null;
            } else if (compromisoProyectoActual.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE) || compromisoProyectoActual.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)  ) {

                short numeroIncrementaAnterior = (short) (compromisoProyectoActual.getCompromisoPeriodo().getNumeroIncrementa()-1);
                Long tipoCompromisoAnterior=0L;
                if (compromisoProyectoActual.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)){
                tipoCompromisoAnterior=IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO;}
                else if(compromisoProyectoActual.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)){
                tipoCompromisoAnterior=IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE;}

                return (CompromisoProyecto) entityManager.createNamedQuery("CompromisoProyecto.findCompromisoProyectoAnteriorPorTipoCompromisoPeriodoYPeriodoYnumeroIncrementa")
                        .setParameter("idConstantes", tipoCompromisoAnterior)// SI numeroIncrementaAnterior == 0, SIGNIFICA QUE DEBE BUSCAR EL COMPROMISO "Formulación del proyecto"
                        .setParameter("idPeriodo", compromisoProyectoActual.getCompromisoPeriodo().getPeriodo().getIdPeriodo())
                        .setParameter("numeroIncrementa", numeroIncrementaAnterior)
                        .setParameter("idProyecto", compromisoProyectoActual.getProyecto().getIdProyecto())
                        .getSingleResult();

            } else if (compromisoProyectoActual.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {
                //DEBEMOS BUSCAR EL ULTIMO COMPROMISO INFORME DE AVANCE                 
                return (CompromisoProyecto) entityManager.createNamedQuery("CompromisoProyecto.findCompromisoProyectoUltimoInformeAvancePorTipoCompromisoPeriodoYPeriodoYnumeroIncrementa")
                        .setParameter("idConstantes", IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)                        
                        .setParameter("idPeriodo", compromisoProyectoActual.getCompromisoPeriodo().getPeriodo().getIdPeriodo())
                        .setParameter("idProyecto", compromisoProyectoActual.getProyecto().getIdProyecto())
                        .setMaxResults(1)
                        .getSingleResult();

            }
            return null;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);

        }

    }

    /**
     *
     * @param compromisoProyecto
     * @throws JpaDinaeException
     */
    @Override
    public CompromisoProyecto agregarCompromisoProyecto(CompromisoProyecto compromisoProyecto) throws JpaDinaeException {

        if (compromisoProyecto.getIdCompromisoProyecto() == null) {

            compromisoProyecto.setFechaCreacion(new Date());
            entityManager.persist(compromisoProyecto);

        } else {

            compromisoProyecto.setFechaActualizacion(new Date());
            entityManager.merge(compromisoProyecto);

        }
        return compromisoProyecto;

    }

    /**
     *
     * @param idUnidadPolicial
     * @param listaIdEstadoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyectoDTO> getCompromisoProyectoDTOPorUnidadPolicial(Long idUnidadPolicial, List<Long> listaIdEstadoCompromiso) throws JpaDinaeException {

        try {

            String consulta = "SELECT c FROM CompromisoProyecto c "
                    + " WHERE c.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false) + " "
                    + " AND c.proyecto.unidadPolicial.idUnidadPolicial = :idUnidadPolicial "
                    + " ORDER BY c.proyecto.codigoProyecto ASC, c.compromisoPeriodo.fecha ASC, c.compromisoPeriodo.numeroIncrementa ASC";

            List<CompromisoProyecto> listaCompromisoProyecto = entityManager.createQuery(consulta)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

            List<CompromisoProyectoDTO> listaCompromisoProyectoDTO = new ArrayList<CompromisoProyectoDTO>();

            for (CompromisoProyecto unCompromisoProyecto : listaCompromisoProyecto) {

                CompromisoProyectoDTO compromisoProyectoDTO = new CompromisoProyectoDTO();

                compromisoProyectoDTO.setCodigoProyecto(unCompromisoProyecto.getProyecto().getCodigoProyecto());
                compromisoProyectoDTO.setComentario(unCompromisoProyecto.getComentarioTemporal());
                String compromiso = unCompromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getValor();

                if (unCompromisoProyecto.getTipoCompromisoCorreccion() == null) {

                    if (unCompromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa() > 0) {

                        compromiso = compromiso.concat(" ").concat(String.valueOf(unCompromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa()));

                    }
                    compromisoProyectoDTO.setCompromiso(compromiso);
                    compromisoProyectoDTO.setFechaLimite(unCompromisoProyecto.getCompromisoPeriodo().getFecha());

                } else {

                    compromisoProyectoDTO.setCompromiso(unCompromisoProyecto.getNombreCompromisoCorrecion());
                    compromisoProyectoDTO.setFechaLimite(unCompromisoProyecto.getFechaCompromiso());
                }

                compromisoProyectoDTO.setIdCompromiso(unCompromisoProyecto.getIdCompromisoProyecto());
                compromisoProyectoDTO.setOrigenCompromiso(IConstantes.ORIGEN_COMPROMISO_PROYECTO);

                if (unCompromisoProyecto.getResultadoRevisionVicin() != null) {
                    compromisoProyectoDTO.setResultadoTemporal(unCompromisoProyecto.getResultadoRevisionVicin().getIdConstantes());
                }
                compromisoProyectoDTO.setTituloProyecto(unCompromisoProyecto.getProyecto().getTituloPropuesto());
                compromisoProyectoDTO.setIdUnidadPolicial(unCompromisoProyecto.getProyecto().getUnidadPolicial().getIdUnidadPolicial());
                compromisoProyectoDTO.setNombreUnidadPolicial(unCompromisoProyecto.getProyecto().getUnidadPolicial().getNombre());

                listaCompromisoProyectoDTO.add(compromisoProyectoDTO);
            }

            consulta = "SELECT c FROM CompromisoImplementacion c "
                    + " WHERE c.idEstadoCompromisoImpl.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false) + " "
                    + " AND c.implementacionesProyecto.unidadPolicial.idUnidadPolicial = :idUnidadPolicial "
                    + " ORDER BY c.implementacionesProyecto.proyecto.codigoProyecto ASC, c.idTipoCompromiso.idConstantes ASC";
            //COSULTAMOS LOS COMPROMISOS IMPLEMENTACION
            List<CompromisoImplementacion> listaCompromisoImplementacion = entityManager.createQuery(consulta)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

            for (CompromisoImplementacion unCompromisoImplementacion : listaCompromisoImplementacion) {

                CompromisoProyectoDTO compromisoProyectoDTO = new CompromisoProyectoDTO();

                compromisoProyectoDTO.setCodigoProyecto(unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getCodigoProyecto());
                compromisoProyectoDTO.setComentario(unCompromisoImplementacion.getComentarioTemporal());

                if (unCompromisoImplementacion.getTipoCompromisoCorreccion() == null) {

                    compromisoProyectoDTO.setCompromiso(unCompromisoImplementacion.getIdTipoCompromiso().getValor());

                } else {

                    compromisoProyectoDTO.setCompromiso(unCompromisoImplementacion.getNombreCompromisoCorrecion());
                    compromisoProyectoDTO.setFechaLimite(unCompromisoImplementacion.getFechaCompromiso());
                }

                compromisoProyectoDTO.setIdCompromiso(unCompromisoImplementacion.getIdCompromisoImplementacion());
                compromisoProyectoDTO.setOrigenCompromiso(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION);

                if (unCompromisoImplementacion.getResultadoRevisionVicin() != null) {
                    compromisoProyectoDTO.setResultadoTemporal(unCompromisoImplementacion.getResultadoRevisionVicin().getIdConstantes());
                }

                Proyecto proyectoLocal = entityManager.find(Proyecto.class, unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getIdProyecto());

                compromisoProyectoDTO.setTituloProyecto(proyectoLocal.getTituloPropuesto());
                compromisoProyectoDTO.setIdUnidadPolicial(proyectoLocal.getUnidadPolicial().getIdUnidadPolicial());
                compromisoProyectoDTO.setNombreUnidadPolicial(proyectoLocal.getUnidadPolicial().getNombre());

                listaCompromisoProyectoDTO.add(compromisoProyectoDTO);
            }

            return listaCompromisoProyectoDTO;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param listaIdEstadoCompromiso
     * @param codigoProyectoInstitucional
     * @param codigoProyectoConvocatoria
     * @param idUnidadPolicialSeleccionado
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyectoDTO> getCompromisoProyectoDTOEnvidosAvicinporListaEstado(List<Long> listaIdEstadoCompromiso, String codigoProyectoInstitucional, String codigoProyectoConvocatoria, Long idUnidadPolicialSeleccionado) throws JpaDinaeException {

        try {

            List<CompromisoProyectoDTO> listaCompromisoProyectoDTO = new ArrayList<CompromisoProyectoDTO>();
            List<CompromisoProyecto> listaCompromisoProyecto = new ArrayList<CompromisoProyecto>();

            //VERIFICAMOS SI DEBEMOS FILTRAR POR CODIGO PROYECTO INSTITUCIONAL
            if (codigoProyectoInstitucional != null) {

                String consulta = "SELECT c FROM CompromisoProyecto c "
                        + " WHERE c.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false) + " "
                        + " AND c.proyecto.codigoProyecto LIKE '" + codigoProyectoInstitucional + "%'"
                        + (idUnidadPolicialSeleccionado == null ? "" : " AND c.proyecto.unidadPolicial.idUnidadPolicial = " + idUnidadPolicialSeleccionado) //SI LLEGA LA UNIDAD POLICIAL, SE INCLUYE EN EL FILTRO
                        + " ORDER BY c.proyecto.unidadPolicial.nombre ASC, c.compromisoPeriodo.fecha ASC, c.compromisoPeriodo.numeroIncrementa ASC";

                listaCompromisoProyecto.addAll(
                        entityManager.createQuery(consulta)
                        .getResultList());

            }

            //VERIFICAMOS SI DEBEMOS FILTRAR POR CODIGO PROYECTO CONVOCATORIA
            if (codigoProyectoConvocatoria != null) {

                String consulta = "SELECT c FROM CompromisoProyecto c "
                        + " WHERE c.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false) + " "
                        + " AND c.proyecto.codigoProyecto LIKE '" + codigoProyectoConvocatoria + "%'"
                        + (idUnidadPolicialSeleccionado == null ? "" : " AND c.proyecto.unidadPolicial.idUnidadPolicial = " + idUnidadPolicialSeleccionado) //SI LLEGA LA UNIDAD POLICIAL, SE INCLUYE EN EL FILTRO
                        + " ORDER BY c.proyecto.unidadPolicial.nombre ASC, c.compromisoPeriodo.fecha ASC, c.compromisoPeriodo.numeroIncrementa ASC";

                listaCompromisoProyecto.addAll(
                        entityManager.createQuery(consulta)
                        .getResultList());

            }

            for (CompromisoProyecto unCompromisoProyecto : listaCompromisoProyecto) {

                CompromisoProyectoDTO compromisoProyectoDTO = new CompromisoProyectoDTO();

                compromisoProyectoDTO.setCodigoProyecto(unCompromisoProyecto.getProyecto().getCodigoProyecto());
                compromisoProyectoDTO.setComentario(unCompromisoProyecto.getComentarioTemporal());
                String compromiso = unCompromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getValor();

                if (unCompromisoProyecto.getTipoCompromisoCorreccion() == null) {

                    if (unCompromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa() > 0) {

                        compromiso = compromiso.concat(" ").concat(String.valueOf(unCompromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa()));

                    }
                    compromisoProyectoDTO.setCompromiso(compromiso);
                    compromisoProyectoDTO.setFechaLimite(unCompromisoProyecto.getCompromisoPeriodo().getFecha());

                } else {

                    compromisoProyectoDTO.setCompromiso(unCompromisoProyecto.getNombreCompromisoCorrecion());
                    compromisoProyectoDTO.setFechaLimite(unCompromisoProyecto.getFechaCompromiso());
                }

                compromisoProyectoDTO.setIdCompromiso(unCompromisoProyecto.getIdCompromisoProyecto());
                compromisoProyectoDTO.setOrigenCompromiso(IConstantes.ORIGEN_COMPROMISO_PROYECTO);

                if (unCompromisoProyecto.getResultadoRevisionVicin() != null) {
                    compromisoProyectoDTO.setResultadoTemporal(unCompromisoProyecto.getResultadoRevisionVicin().getIdConstantes());
                }

                compromisoProyectoDTO.setFechaPresentacion(unCompromisoProyecto.getFechaActualizacion());
                compromisoProyectoDTO.setIdEstado(unCompromisoProyecto.getEstado().getIdConstantes());
                compromisoProyectoDTO.setNombreEstado(unCompromisoProyecto.getEstado().getValor());
                compromisoProyectoDTO.setTituloProyecto(unCompromisoProyecto.getProyecto().getTituloPropuesto());
                compromisoProyectoDTO.setIdUnidadPolicial(unCompromisoProyecto.getProyecto().getUnidadPolicial().getIdUnidadPolicial());
                compromisoProyectoDTO.setNombreUnidadPolicial(unCompromisoProyecto.getProyecto().getUnidadPolicial().getNombre());
                compromisoProyectoDTO.setFechaNuevoCompromiso(unCompromisoProyecto.getFechaNuevoCompromiso());
                if (unCompromisoProyecto.getResultadoRetroalimentacion() != null) {
                    compromisoProyectoDTO.setIdResultadoRetroalimentacion(unCompromisoProyecto.getResultadoRetroalimentacion().getIdConstantes());
                }
                compromisoProyectoDTO.setIdPeriodo(unCompromisoProyecto.getCompromisoPeriodo().getPeriodo().getIdPeriodo());
                compromisoProyectoDTO.setIdTipoCompromiso(unCompromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes());
                compromisoProyectoDTO.setIdProyecto(unCompromisoProyecto.getProyecto().getIdProyecto());

                listaCompromisoProyectoDTO.add(compromisoProyectoDTO);
            }

            //CONSULTAMOS LOS COMPROMISOS IMPLEMENTACION
            List<CompromisoImplementacion> listaCompromisoImplementacion = new ArrayList<CompromisoImplementacion>();
            //VERIFICAMOS SI DEBEMOS FILTRAR POR CODIGO PROYECTO INSTITUCIONAL
            if (codigoProyectoInstitucional != null) {

                String consulta = "SELECT c FROM CompromisoImplementacion c "
                        + " WHERE c.idEstadoCompromisoImpl.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false) + " "
                        + (idUnidadPolicialSeleccionado == null ? "" : " AND c.implementacionesProyecto.proyecto.unidadPolicial.idUnidadPolicial = " + idUnidadPolicialSeleccionado) //SI LLEGA LA UNIDAD POLICIAL, SE INCLUYE EN EL FILTRO
                        + " AND ( c.implementacionesProyecto.proyecto.codigoProyecto LIKE '" + codigoProyectoInstitucional + "%' OR c.implementacionesProyecto.proyecto.codigoProyecto LIKE 'MVIC%' ) ";

                listaCompromisoImplementacion.addAll(
                        entityManager.createQuery(consulta)
                        .getResultList());

            }

            //VERIFICAMOS SI DEBEMOS FILTRAR POR CODIGO PROYECTO CONVOCATORIA
            if (codigoProyectoConvocatoria != null) {

                String consulta = "SELECT c FROM CompromisoImplementacion c "
                        + " WHERE c.idEstadoCompromisoImpl.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false) + " "
                        + (idUnidadPolicialSeleccionado == null ? "" : " AND c.implementacionesProyecto.proyecto.unidadPolicial.idUnidadPolicial = " + idUnidadPolicialSeleccionado) //SI LLEGA LA UNIDAD POLICIAL, SE INCLUYE EN EL FILTRO
                        + " AND c.implementacionesProyecto.proyecto.codigoProyecto LIKE '" + codigoProyectoConvocatoria + "%'";

                listaCompromisoImplementacion.addAll(
                        entityManager.createQuery(consulta)
                        .getResultList());

            }

            for (CompromisoImplementacion unCompromisoImplementacion : listaCompromisoImplementacion) {

                CompromisoProyectoDTO compromisoProyectoDTO = new CompromisoProyectoDTO();

                compromisoProyectoDTO.setCodigoProyecto(unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getCodigoProyecto());
                compromisoProyectoDTO.setComentario(unCompromisoImplementacion.getComentarioTemporal());

                if (unCompromisoImplementacion.getTipoCompromisoCorreccion() == null) {

                    compromisoProyectoDTO.setCompromiso(unCompromisoImplementacion.getIdTipoCompromiso().getValor());

                } else {

                    compromisoProyectoDTO.setCompromiso(unCompromisoImplementacion.getNombreCompromisoCorrecion());
                    compromisoProyectoDTO.setFechaLimite(unCompromisoImplementacion.getFechaCompromiso());
                }

                compromisoProyectoDTO.setIdCompromiso(unCompromisoImplementacion.getIdCompromisoImplementacion());
                compromisoProyectoDTO.setOrigenCompromiso(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION);

                if (unCompromisoImplementacion.getResultadoRevisionVicin() != null) {
                    compromisoProyectoDTO.setResultadoTemporal(unCompromisoImplementacion.getResultadoRevisionVicin().getIdConstantes());
                }

                compromisoProyectoDTO.setIdEstado(unCompromisoImplementacion.getIdEstadoCompromisoImpl().getIdConstantes());
                compromisoProyectoDTO.setNombreEstado(unCompromisoImplementacion.getIdEstadoCompromisoImpl().getValor());
                compromisoProyectoDTO.setFechaPresentacion(unCompromisoImplementacion.getFechaActualizacion());
                compromisoProyectoDTO.setTituloProyecto(unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getTituloPropuesto());
                compromisoProyectoDTO.setIdUnidadPolicial(unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getUnidadPolicial().getIdUnidadPolicial());
                compromisoProyectoDTO.setNombreUnidadPolicial(unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getUnidadPolicial().getNombre());
                compromisoProyectoDTO.setFechaNuevoCompromiso(unCompromisoImplementacion.getFechaNuevoCompromiso());
                if (unCompromisoImplementacion.getResultadoRetroalimentacion() != null) {
                    compromisoProyectoDTO.setIdResultadoRetroalimentacion(unCompromisoImplementacion.getResultadoRetroalimentacion().getIdConstantes());
                }
                //compromisoProyectoDTO.setIdPeriodo( unCompromisoImplementacion.getIdTipoCompromiso().getPeriodo().getIdPeriodo() );
                compromisoProyectoDTO.setIdTipoCompromiso(unCompromisoImplementacion.getIdTipoCompromiso().getIdConstantes());
                compromisoProyectoDTO.setIdProyecto(unCompromisoImplementacion.getImplementacionesProyecto().getProyecto().getIdProyecto());

                listaCompromisoProyectoDTO.add(compromisoProyectoDTO);
            }

            return listaCompromisoProyectoDTO;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param listaCompromisoProyectoDTO
     * @throws JpaDinaeException
     */
    @Override
    public void enviarVicinListCompromisoProyectoDTO(List<CompromisoProyectoDTO> listaCompromisoProyectoDTO) throws JpaDinaeException {

        try {

            for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

                if (unCompromisoProyectoDTO.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                    CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, unCompromisoProyectoDTO.getIdCompromiso());
                    compromisoProyecto.setResultadoRevisionVicin(new Constantes(unCompromisoProyectoDTO.getResultadoTemporal()));
                    compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN));
                    compromisoProyecto.setFechaActualizacion(new Date());
                    compromisoProyecto.setComentarioTemporal(null);
                    compromisoProyecto.setFechaEnvioVicin(new Date());

                    entityManager.merge(compromisoProyecto);

                } else if (unCompromisoProyectoDTO.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                    CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, unCompromisoProyectoDTO.getIdCompromiso());
                    compromisoImplementacion.setResultadoRevisionVicin(new Constantes(unCompromisoProyectoDTO.getResultadoTemporal()));
                    compromisoImplementacion.setIdEstadoCompromisoImpl(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN));
                    compromisoImplementacion.setFechaActualizacion(new Date());
                    compromisoImplementacion.setComentarioTemporal(null);
                    compromisoImplementacion.setFechaEnvioVicin(new Date());

                    entityManager.merge(compromisoImplementacion);
                }

            }
        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());

        }

    }

    /**
     *
     * @param listaCompromisoProyectoDTO
     * @param identificacion
     * @param usuario
     * @param nombreCompleto
     * @param idUsuarioRol
     * @throws JpaDinaeException
     */
    @Override
    public void enviarGrupoInvestigacionListCompromisoProyectoDTO(List<CompromisoProyectoDTO> listaCompromisoProyectoDTO, String identificacion, String usuario, String nombreCompleto, Long idUsuarioRol) throws JpaDinaeException {

        try {

            for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

                ComentarioCompromisoProyecto comentarioCompromisoProyecto = new ComentarioCompromisoProyecto();
                comentarioCompromisoProyecto.setComentario(unCompromisoProyectoDTO.getComentario());
                comentarioCompromisoProyecto.setFechaRegistro(new Date());
                comentarioCompromisoProyecto.setIdentificacion(identificacion);
                comentarioCompromisoProyecto.setNombreCompleto(nombreCompleto);
                comentarioCompromisoProyecto.setUsuario(usuario);
                comentarioCompromisoProyecto.setUsuarioRol(new UsuarioRol(idUsuarioRol));
                comentarioCompromisoProyecto.setAutorCasoUso(IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_25);

                if (unCompromisoProyectoDTO.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                    CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, unCompromisoProyectoDTO.getIdCompromiso());
                    compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO));
                    compromisoProyecto.setFechaActualizacion(new Date());
                    compromisoProyecto.setComentarioTemporal(null);
                    compromisoProyecto.setResultadoRevisionVicin(null);

                    entityManager.merge(compromisoProyecto);

                    comentarioCompromisoProyecto.setCompromisoProyecto(compromisoProyecto);

                } else if (unCompromisoProyectoDTO.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                    CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, unCompromisoProyectoDTO.getIdCompromiso());
                    compromisoImplementacion.setIdEstadoCompromisoImpl(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO));
                    compromisoImplementacion.setFechaActualizacion(new Date());
                    compromisoImplementacion.setComentarioTemporal(null);
                    compromisoImplementacion.setResultadoRevisionVicin(null);

                    entityManager.merge(compromisoImplementacion);

                    comentarioCompromisoProyecto.setCompromisoImplementacion(compromisoImplementacion);
                }

                entityManager.persist(comentarioCompromisoProyecto);

            }
        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());

        }

    }

    /**
     *
     * @param listaCompromisoProyectoDTO
     * @throws JpaDinaeException
     */
    @Override
    public void guardarListCompromisoProyectoDTO(List<CompromisoProyectoDTO> listaCompromisoProyectoDTO) throws JpaDinaeException {

        try {

            for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

                if (unCompromisoProyectoDTO.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                    CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, unCompromisoProyectoDTO.getIdCompromiso());

                    if (unCompromisoProyectoDTO.getResultadoTemporal() != null) {
                        compromisoProyecto.setResultadoRevisionVicin(new Constantes(unCompromisoProyectoDTO.getResultadoTemporal()));
                    }

                    compromisoProyecto.setComentarioTemporal(unCompromisoProyectoDTO.getComentario());
                    compromisoProyecto.setFechaActualizacion(new Date());

                    entityManager.merge(compromisoProyecto);

                } else if (unCompromisoProyectoDTO.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                    CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, unCompromisoProyectoDTO.getIdCompromiso());

                    if (unCompromisoProyectoDTO.getResultadoTemporal() != null) {
                        compromisoImplementacion.setResultadoRevisionVicin(new Constantes(unCompromisoProyectoDTO.getResultadoTemporal()));
                    }

                    compromisoImplementacion.setComentarioTemporal(unCompromisoProyectoDTO.getComentario());
                    compromisoImplementacion.setFechaActualizacion(new Date());

                    entityManager.merge(compromisoImplementacion);
                }

            }
        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());

        }

    }

    /**
     *
     * @param fechaRetroalimentacion
     * @param idResultadoRetroalimenta
     * @param idComprimiso
     * @param origenCompromiso
     * @throws JpaDinaeException
     */
    @Override
    public void actualizarResultadoRetroalimentacionCompromisoProyectoEimplementacion(Date fechaRetroalimentacion, Long idResultadoRetroalimenta, Long idComprimiso, String origenCompromiso) throws JpaDinaeException {

        try {

            if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, idComprimiso);
                compromisoProyecto.setFechaActualizacion(new Date());
                compromisoProyecto.setFechaNuevoCompromiso(fechaRetroalimentacion);
                compromisoProyecto.setResultadoRetroalimentacion(new Constantes(idResultadoRetroalimenta));
                //CAMBIA EL ESTADO DEL COMPROMISO A 'REVISADO'
                compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_REVISADO));

                entityManager.merge(compromisoProyecto);

            } else if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, idComprimiso);
                compromisoImplementacion.setFechaActualizacion(new Date());
                compromisoImplementacion.setFechaNuevoCompromiso(fechaRetroalimentacion);
                compromisoImplementacion.setResultadoRetroalimentacion(new Constantes(idResultadoRetroalimenta));
                //CAMBIA EL ESTADO DEL COMPROMISO A 'REVISADO'
                compromisoImplementacion.setIdEstadoCompromisoImpl(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_REVISADO));

                entityManager.merge(compromisoImplementacion);

            }

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());
        }
    }

    /**
     *
     * @param fechaRetroalimentacion
     * @param idResultadoRetroalimenta
     * @param idComprimiso
     * @param origenCompromiso
     * @param idEstadoCompromiso
     * @throws JpaDinaeException
     */
    @Override
    public void actualizarCompromisoProyectoEimplementacionResultadoRetroalimentacionEnviarCompromisoUnidadPolicial(Date fechaRetroalimentacion, Long idResultadoRetroalimenta, Long idComprimiso, String origenCompromiso, Long idEstadoCompromiso) throws JpaDinaeException {

        try {

            CopyGroup grupoConfiguracionCopiaObjeto = new CopyGroup();
            grupoConfiguracionCopiaObjeto.setShouldResetPrimaryKey(true);

            if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, idComprimiso);
                compromisoProyecto.setFechaActualizacion(new Date());
                compromisoProyecto.setFechaNuevoCompromiso(fechaRetroalimentacion);
                compromisoProyecto.setResultadoRetroalimentacion(new Constantes(idResultadoRetroalimenta));
                //CAMBIA EL ESTADO DEL COMPROMISO A 'CORRECCIÓN COMPROMISO'
                compromisoProyecto.setEstado(new Constantes(idEstadoCompromiso));
                entityManager.merge(compromisoProyecto);

                //CREA UN NUEVO COMPROMISO  'CORREGIR [NOMBRE DEL COMPROMISO]' 
                //PARA LA UNIDAD Y LE ASIGNA COMO FECHA LÍMITE LA FECHA DEL NUEVO COMPROMISO INGRESADA POR EL ACTOR 
                CompromisoProyecto compromisoProyectoCorregir = (CompromisoProyecto) entityManager.unwrap(JpaEntityManager.class).copy(compromisoProyecto, grupoConfiguracionCopiaObjeto);
                compromisoProyectoCorregir.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
                compromisoProyectoCorregir.setFechaCompromiso(fechaRetroalimentacion);
                compromisoProyectoCorregir.setTipoCompromisoCorreccion(new Constantes(IConstantes.TIPO_COMPROMISO_CORRECCION_PENDIENTE_CORRECCION));
                compromisoProyectoCorregir.setFechaActualizacion(new Date());
                compromisoProyectoCorregir.setFechaCreacion(new Date());
                compromisoProyectoCorregir.setNombreCompromisoCorrecion("Corregir ".concat(compromisoProyecto.getCompromisoPeriodo().getNombreCompromisoNumeroIncrementa()));
                compromisoProyectoCorregir.setCompromisoProyectoPadre(compromisoProyecto);
                compromisoProyectoCorregir.setResultadoRevisionVicin(null);
                compromisoProyectoCorregir.setComentarioTemporal(null);
                compromisoProyectoCorregir.setResultadoRetroalimentacion(null);

                entityManager.persist(compromisoProyectoCorregir);

                //LOS COMPROMISOS PROYECTOS - TIPO FORMULACION NO TIENEN INFORMES DE AVANCE
                if (!compromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)) {

                    InformeAvance informeAvanceActual = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(idComprimiso, compromisoProyecto.getProyecto().getIdProyecto());
                    //CREAMOS EL NUEVO COMPROMISO AVANCE (COPIA DEL ANTERIOR) ASOCIADO AL NUEVO COMPROMISO PROYECTO CORREGIR
                    InformeAvance nuevoCopiaInformeAvance = (InformeAvance) entityManager.unwrap(JpaEntityManager.class).copy(informeAvanceActual, grupoConfiguracionCopiaObjeto);
                    nuevoCopiaInformeAvance.setCompromisoProyecto(compromisoProyectoCorregir);
                    nuevoCopiaInformeAvance.setFechaRegistro(new Date());
                    entityManager.persist(nuevoCopiaInformeAvance);

                    //ELIMINAMOS LOS VALORES DEL INFORME ANTERIOR
                    entityManager.createNativeQuery("DELETE FROM RESUMEN_PRESUPUESTO_EJECUTADO WHERE ID_PROYECTO = " + compromisoProyecto.getProyecto().getIdProyecto() + " AND TIPO = 'I' AND ID_INFORME_AVANCE = " + informeAvanceActual.getIdInformeAvance())
                            .executeUpdate();

                    //CREAMOS LOS HIJOS DE InformeAvance
                    //CU-PR-14                    
                    //PESTAÑAS
                    //2. Equipos de la investigación                                        
                    //3. Eventos relacionados
                    //4. Viajes relacionados
                    //5. Otros                    
                    List<EjecucionEquiposProyecto> listaEjecucionEquiposProyecto = entityManager.createNamedQuery("EjecucionEquiposProyecto.SelectEquipoRelacionadoInformeProyecto")
                            .setParameter("idInformeAvance", informeAvanceActual.getIdInformeAvance())
                            .getResultList();
                    for (EjecucionEquiposProyecto unaEjecucionEquiposProyecto : listaEjecucionEquiposProyecto) {

                        EjecucionEquiposProyecto ejecucionEquiposProyectoCorregir = (EjecucionEquiposProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionEquiposProyecto, grupoConfiguracionCopiaObjeto);
                        ejecucionEquiposProyectoCorregir.setIdInformeAvance(nuevoCopiaInformeAvance);

                        //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                        entityManager.createNativeQuery("UPDATE EJECUCION_EQUIPOS_PROYECTO SET CORRECION='Y' WHERE ID_EJECUCION_EQUIP_PROY = " + unaEjecucionEquiposProyecto.getIdEjecucionEquipProy())
                                .executeUpdate();

                        //CREMOS EL NUEVO REGISTRO
                        entityManager.persist(ejecucionEquiposProyectoCorregir);

                    }

                    List<EjecucionEventosProyecto> listaEjecucionEventosProyecto = entityManager.createNamedQuery("EjecucionEventosProyecto.SelectEventoRelacionadoInformeProyecto")
                            .setParameter("idInformeAvance", informeAvanceActual.getIdInformeAvance())
                            .getResultList();
                    for (EjecucionEventosProyecto unaEjecucionEventosProyecto : listaEjecucionEventosProyecto) {

                        EjecucionEventosProyecto ejecucionEventosProyectoCorregir = (EjecucionEventosProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionEventosProyecto, grupoConfiguracionCopiaObjeto);
                        ejecucionEventosProyectoCorregir.setInformeAvance(nuevoCopiaInformeAvance);

                        //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                        entityManager.createNativeQuery("UPDATE EJECUCION_EVENTOS_PROYECTO SET CORRECION='Y' WHERE ID_EJECUCION_EVENT_PROY = " + unaEjecucionEventosProyecto.getIdEjecucionEventProy())
                                .executeUpdate();

                        //CREMOS EL NUEVO REGISTRO
                        entityManager.persist(ejecucionEventosProyectoCorregir);

                    }

                    List<EjecucionViajesProyecto> listaEjecucionViajesProyecto = entityManager.createNamedQuery("EjecucionViajesProyecto.SelectViajesRelacionadoInformeProyecto")
                            .setParameter("idInformeAvance", informeAvanceActual.getIdInformeAvance())
                            .getResultList();
                    for (EjecucionViajesProyecto unaEjecucionViajesProyecto : listaEjecucionViajesProyecto) {

                        EjecucionViajesProyecto ejecucionViajesProyectoCorregir = (EjecucionViajesProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionViajesProyecto, grupoConfiguracionCopiaObjeto);
                        ejecucionViajesProyectoCorregir.setInformeAvance(nuevoCopiaInformeAvance);

                        //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                        entityManager.createNativeQuery("UPDATE EJECUCION_VIAJES_PROYECTO SET CORRECION='Y' WHERE ID_EJECUCION_VIAJES_PROYECTO = " + unaEjecucionViajesProyecto.getIdEjecucionViajesProyecto())
                                .executeUpdate();

                        //CREMOS EL NUEVO REGISTRO
                        entityManager.persist(ejecucionViajesProyectoCorregir);

                    }

                    List<EjecucionOtrosGastosProy> listaEjecucionOtrosGastosProy = entityManager.createNamedQuery("EjecucionOtrosGastosProy.SelectOtrosGastosInformeProyecto")
                            .setParameter("idInformeAvance", informeAvanceActual.getIdInformeAvance())
                            .getResultList();
                    for (EjecucionOtrosGastosProy unaEjecucionOtrosGastosProy : listaEjecucionOtrosGastosProy) {

                        EjecucionOtrosGastosProy ejecucionOtrosGastosProyCorregir = (EjecucionOtrosGastosProy) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionOtrosGastosProy, grupoConfiguracionCopiaObjeto);
                        ejecucionOtrosGastosProyCorregir.setInformeAvance(nuevoCopiaInformeAvance);

                        //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                        entityManager.createNativeQuery("UPDATE EJECUCION_OTROS_GASTOS_PROY SET CORRECION='Y' WHERE ID_EJECUCION_GASTOS_PROY = " + unaEjecucionOtrosGastosProy.getIdEjecucionGastosProy())
                                .executeUpdate();

                        //CREMOS EL NUEVO REGISTRO
                        entityManager.persist(ejecucionOtrosGastosProyCorregir);

                    }

                }

                //CREAMOS LOS HIJOS DE CompromisoProyecto
                List<SugerenciasProyecto> listaSugerenciasProyecto = iSugerenciasProyectoLocal.getListaSugerenciasProyectoPorProyectoYCompromisoProyecto(
                        compromisoProyecto.getProyecto().getIdProyecto(),
                        compromisoProyecto.getIdCompromisoProyecto());

                for (SugerenciasProyecto unaSugerenciasProyecto : listaSugerenciasProyecto) {

                    SugerenciasProyecto sugerenciasProyectoCorregir = (SugerenciasProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaSugerenciasProyecto, grupoConfiguracionCopiaObjeto);
                    sugerenciasProyectoCorregir.setCompromisoProyecto(compromisoProyectoCorregir);
                    sugerenciasProyectoCorregir.setFechaCreacion(new Date());

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE SUGERENCIAS_PROY SET CORRECION='Y' WHERE ID_SUGERENCIAS_PROY = " + unaSugerenciasProyecto.getIdSugerenciasProyecto())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO
                    entityManager.persist(sugerenciasProyectoCorregir);

                }

                //COMENTARIO COMPROMISO PROYECTO
                List<ComentarioCompromisoProyecto> listaComentarioCompromisoProyecto = iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorCompromisoProyecto(
                        compromisoProyecto.getIdCompromisoProyecto());

                for (ComentarioCompromisoProyecto unComentarioCompromisoProyecto : listaComentarioCompromisoProyecto) {

                    ComentarioCompromisoProyecto comentarioCompromisoProyectoCorregir = (ComentarioCompromisoProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unComentarioCompromisoProyecto, grupoConfiguracionCopiaObjeto);
                    comentarioCompromisoProyectoCorregir.setCompromisoProyecto(compromisoProyectoCorregir);
                    comentarioCompromisoProyectoCorregir.setFechaRegistro(new Date());

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE COMENTARIO_COMPROMISO_PROYECTO SET CORRECION='Y' WHERE ID_COMENTARIO_COMPROMISO_PROYE = " + unComentarioCompromisoProyecto.getIdComentarioCompromisoProyecto())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO
                    entityManager.persist(comentarioCompromisoProyectoCorregir);

                }

                //INDICADORES COMPROMISO PROYECTO
                List<IndicadoresCompromisoProyecto> listaIndicadoresCompromisoProyecto = iIndicadoresProyectoLocal.getListaIndicadoresCompromisoProyecto(compromisoProyecto.getIdCompromisoProyecto());
                for (IndicadoresCompromisoProyecto unIndicadoresCompromisoProyecto : listaIndicadoresCompromisoProyecto) {

                    IndicadoresCompromisoProyecto indicadoresCompromisoProyectoCorregir = (IndicadoresCompromisoProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unIndicadoresCompromisoProyecto, grupoConfiguracionCopiaObjeto);
                    indicadoresCompromisoProyectoCorregir.setCompromisoProyecto(compromisoProyectoCorregir);
                    indicadoresCompromisoProyectoCorregir.setFechaRegistro(new Date());
                    indicadoresCompromisoProyectoCorregir.setFechaModifica(null);

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE INDICADOR_COMPROMISO_PROYECTO SET CORRECION='Y' WHERE ID_INDICADOR_COMPROMISO_PROYEC = " + unIndicadoresCompromisoProyecto.getIdIndicadorCompromisoProyecto())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO                    
                    entityManager.persist(indicadoresCompromisoProyectoCorregir);

                }

                //ACTIVIDADES REALIZADAS
                List<ActividadesRealizadasProyecto> listaActividadesRealizadasProyecto = iActividadesRealizadasProyectoLocal.getListaActividadesRealizadasProyectoPorProyectoYcompromisoProyecto(
                        compromisoProyecto.getProyecto().getIdProyecto(),
                        compromisoProyecto.getIdCompromisoProyecto());

                for (ActividadesRealizadasProyecto unaActividadesRealizadasProyecto : listaActividadesRealizadasProyecto) {

                    ActividadesRealizadasProyecto actividadesRealizadasProyectoCorregir = (ActividadesRealizadasProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaActividadesRealizadasProyecto, grupoConfiguracionCopiaObjeto);
                    actividadesRealizadasProyectoCorregir.setCompromisoProyecto(compromisoProyectoCorregir);
                    actividadesRealizadasProyectoCorregir.setFechaCreacion(new Date());

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE ACTIVIDADES_REALIZADAS_PROY SET CORRECION='Y' WHERE ID_ACTIVIDAD_REALIZADA_PROY = " + unaActividadesRealizadasProyecto.getIdActividadesRealizadasProyecto())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO
                    entityManager.persist(actividadesRealizadasProyectoCorregir);

                }

                //RESULTADOS ALCANZADOS
                List<ResultadosAlcanzadosProyecto> listaResultadosAlcanzadosProyecto = iResultadosAlcanzadosProyectoLocal.getListaResultadosAlcanzadosProyectoPorProyectoYcompromisoProyecto(
                        compromisoProyecto.getProyecto().getIdProyecto(),
                        compromisoProyecto.getIdCompromisoProyecto());

                for (ResultadosAlcanzadosProyecto unResultadosAlcanzadosProyecto : listaResultadosAlcanzadosProyecto) {

                    ResultadosAlcanzadosProyecto resultadosAlcanzadosProyectoCorregir = (ResultadosAlcanzadosProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unResultadosAlcanzadosProyecto, grupoConfiguracionCopiaObjeto);
                    resultadosAlcanzadosProyectoCorregir.setCompromisoProyecto(compromisoProyectoCorregir);
                    resultadosAlcanzadosProyectoCorregir.setFechaCreacion(new Date());

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE RESULTADOS_ALCANZADOS_PROY SET CORRECION='Y' WHERE ID_RESULTADOS_ALCANZADOS_PROY = " + unResultadosAlcanzadosProyecto.getIdResultadosAlcanzadosProyecto())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO
                    entityManager.persist(resultadosAlcanzadosProyectoCorregir);

                }

                //HORAS INVESTIGADOR
                List<HorasInvestigador> listaHorasInvestigador = iHorasInvestigadorLocal.getListaHorasInvestigacionPorCompromisoProyectoYproyecto(
                        compromisoProyecto.getProyecto().getIdProyecto(),
                        compromisoProyecto.getIdCompromisoProyecto());

                for (HorasInvestigador unaHorasInvestigador : listaHorasInvestigador) {

                    HorasInvestigador horasInvestigadorCorregir = (HorasInvestigador) entityManager.unwrap(JpaEntityManager.class).copy(unaHorasInvestigador, grupoConfiguracionCopiaObjeto);
                    horasInvestigadorCorregir.setCompromisoProyecto(compromisoProyectoCorregir);

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE HORAS_INVESTIGADOR SET CORRECION='Y' WHERE ID_HORAS_INVESTIGADOR = " + unaHorasInvestigador.getIdHorasInvestigador())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO                    
                    entityManager.persist(horasInvestigadorCorregir);

                }

                //EVIDENCIA PROYECTO
                List<EvidenciaProyecto> listaEvidenciaProyecto = iEvidenciaProyectoLocal.getListaEvidenciaProyectoPorProyectoYcompromisoProyecto(
                        compromisoProyecto.getProyecto().getIdProyecto(),
                        compromisoProyecto.getIdCompromisoProyecto());

                for (EvidenciaProyecto unaEvidenciaProyecto : listaEvidenciaProyecto) {

                    EvidenciaProyecto evidenciaProyectoCorregir = (EvidenciaProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEvidenciaProyecto, grupoConfiguracionCopiaObjeto);
                    evidenciaProyectoCorregir.setCompromisoProyecto(compromisoProyectoCorregir);
                    evidenciaProyectoCorregir.setFechaIngresoRegistro(new Date());
                    evidenciaProyectoCorregir.setFechaModificaRegistro(null);

                    //ACTUALIZAMOS EL REG ACTUAL - INDICANDO QUE ES CORRECCION
                    entityManager.createNativeQuery("UPDATE EVIDENCIA_PROYECTO SET CORRECION='Y' WHERE ID_EVIDENCIA_PROYECTO = " + unaEvidenciaProyecto.getIdEvidenciaProyecto())
                            .executeUpdate();

                    //CREMOS EL NUEVO REGISTRO
                    entityManager.persist(evidenciaProyectoCorregir);

                }

                //ACTUALIZOS EL COMPROMISO PROYECTO CON EL ID DEL COMPROMISO PROYECTO HIJO 
                compromisoProyecto.setCompromisoProyectoHijo(compromisoProyectoCorregir);
                entityManager.merge(compromisoProyecto);

            } else if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, idComprimiso);
                compromisoImplementacion.setFechaActualizacion(new Date());
                compromisoImplementacion.setFechaNuevoCompromiso(fechaRetroalimentacion);
                compromisoImplementacion.setResultadoRetroalimentacion(new Constantes(idResultadoRetroalimenta));
                //CAMBIA EL ESTADO DEL COMPROMISO A 'CORRECCIÓN COMPROMISO'
                compromisoImplementacion.setIdEstadoCompromisoImpl(new Constantes(idEstadoCompromiso));
                entityManager.merge(compromisoImplementacion);

                //CREA UN NUEVO COMPROMISO  'CORREGIR [NOMBRE DEL COMPROMISO]' 
                //PARA LA UNIDAD Y LE ASIGNA COMO FECHA LÍMITE LA FECHA DEL NUEVO COMPROMISO INGRESADA POR EL ACTOR 
                CompromisoImplementacion compromisoImplementacionCorregir = (CompromisoImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(compromisoImplementacion, grupoConfiguracionCopiaObjeto);
                compromisoImplementacionCorregir.setIdEstadoCompromisoImpl(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
                compromisoImplementacionCorregir.setFechaCompromiso(fechaRetroalimentacion);
                compromisoImplementacionCorregir.setTipoCompromisoCorreccion(new Constantes(IConstantes.TIPO_COMPROMISO_CORRECCION_PENDIENTE_CORRECCION));
                compromisoImplementacionCorregir.setFechaActualizacion(new Date());
                compromisoImplementacionCorregir.setFechaCreacion(new Date());
                compromisoImplementacionCorregir.setNombreCompromisoCorrecion("Corregir ".concat(compromisoImplementacion.getIdTipoCompromiso().getValor()));
                compromisoImplementacionCorregir.setCompromisoImplementacionPadre(compromisoImplementacion);

                entityManager.persist(compromisoImplementacionCorregir);

                //COPIA DEL INFORME DE AVANCE
                InformeAvanceImplementacion informeAvanceImplementacion = iInformeAvanceImplementacionLocal.findInformeAvanceImplementacionFinaByIdImplemenatcionProYIdCompromisoProImpl(
                        compromisoImplementacion.getImplementacionesProyecto().getIdImplementacionProy(),
                        compromisoImplementacion.getIdCompromisoImplementacion());

                //ELIMINAMOS LOS VALORES DEL INFORME ANTERIOR
                entityManager.createNativeQuery("DELETE FROM RESUMEN_PRES_EJECUTA_IMPL WHERE ID_IMPLEMENTACION_PROYECTO = " + compromisoImplementacion.getImplementacionesProyecto().getIdImplementacionProy() + " AND TIPO = 'I' AND ID_INFORME_AVANCE_IMPL = " + informeAvanceImplementacion.getIdInformeAvanceImplementacion())
                        .executeUpdate();

                InformeAvanceImplementacion informeAvanceImplementacionCorregir = (InformeAvanceImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(informeAvanceImplementacion, grupoConfiguracionCopiaObjeto);
                informeAvanceImplementacionCorregir.setCompromisoImplementacion(compromisoImplementacionCorregir);
                entityManager.persist(informeAvanceImplementacionCorregir);

                if (compromisoImplementacion.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)
                        || compromisoImplementacion.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

                    List<PersonalImplementacion> listaPersonalImplementacion = iPersonalImplementacionLocal.getPersonalImplementacionListPorIdInformeImplementacion(informeAvanceImplementacion.getIdInformeAvanceImplementacion());
                    for (PersonalImplementacion unPersonalImplementacion : listaPersonalImplementacion) {

                        PersonalImplementacion personalImplementacionCorregir = (PersonalImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(unPersonalImplementacion, grupoConfiguracionCopiaObjeto);
                        personalImplementacionCorregir.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);
                        personalImplementacionCorregir.setFechaRegistro(new Date());

                        entityManager.persist(personalImplementacionCorregir);

                    }

                    List<TemaProyecto> listaTemasTemaProyectos = iTemaProyectoLocal.getListaTemaProyectoPorInformeAvanceImplementacion(informeAvanceImplementacion.getIdInformeAvanceImplementacion());
                    for (TemaProyecto unTemaProyecto : listaTemasTemaProyectos) {

                        TemaProyecto temaProyectoCorregir = (TemaProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unTemaProyecto, grupoConfiguracionCopiaObjeto);
                        temaProyectoCorregir.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);
                        temaProyectoCorregir.setFechaRegistro(new Date());

                        entityManager.persist(temaProyectoCorregir);

                    }

                    List<ActividadesImplementacion> listaActividadesImplementacion = iActividadesImplementacionLocal.getListaActividadesImplementacionPorInformeAvanceImpl(informeAvanceImplementacion.getIdInformeAvanceImplementacion());
                    for (ActividadesImplementacion unaActividadesImplementacion : listaActividadesImplementacion) {

                        ActividadesImplementacion actividadesImplementacionCorregir = (ActividadesImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(unaActividadesImplementacion, grupoConfiguracionCopiaObjeto);
                        actividadesImplementacionCorregir.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);
                        actividadesImplementacionCorregir.setFechaRegistro(new Date());

                        entityManager.persist(actividadesImplementacionCorregir);

                    }

                    List<IndicadoresPlanTrabajoImplementacion> listaIndicadoresPlanTrabajoOtros = iIndicadoresPlanTrabajoLocal.getListaIndicadoresPlanTrabajoImplementacionPorInformeAvanceImplementacion(informeAvanceImplementacion.getIdInformeAvanceImplementacion());
                    for (IndicadoresPlanTrabajoImplementacion unIndicadoresPlanTrabajoImplementacion : listaIndicadoresPlanTrabajoOtros) {

                        IndicadoresPlanTrabajoImplementacion indicadoresPlanTrabajoImplementacionCorregir = (IndicadoresPlanTrabajoImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(unIndicadoresPlanTrabajoImplementacion, grupoConfiguracionCopiaObjeto);
                        indicadoresPlanTrabajoImplementacionCorregir.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);

                        entityManager.persist(indicadoresPlanTrabajoImplementacionCorregir);

                    }

                    //COMENTARIO COMPROMISO PROYECTO 
                    List<ComentarioCompromisoProyecto> listaComentarioCompromisoProyecto = iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(
                            compromisoImplementacion.getIdCompromisoImplementacion());

                    for (ComentarioCompromisoProyecto unComentarioCompromisoProyecto : listaComentarioCompromisoProyecto) {

                        ComentarioCompromisoProyecto comentarioCompromisoProyectoCorregir = (ComentarioCompromisoProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unComentarioCompromisoProyecto, grupoConfiguracionCopiaObjeto);
                        comentarioCompromisoProyectoCorregir.setCompromisoImplementacion(compromisoImplementacionCorregir);
                        comentarioCompromisoProyectoCorregir.setFechaRegistro(new Date());

                        entityManager.persist(comentarioCompromisoProyectoCorregir);

                    }

                    List<EjecucionEquiposProyecto> listaEjecucionEquiposProyecto = entityManager.createNamedQuery("EjecucionEquiposProyecto.SelectEquipoRelacionadoInformeImplementacionProyecto")
                            .setParameter("idInformeAvanceImplementacion", informeAvanceImplementacion.getIdInformeAvanceImplementacion())
                            .getResultList();
                    for (EjecucionEquiposProyecto unaEjecucionEquiposProyecto : listaEjecucionEquiposProyecto) {

                        EjecucionEquiposProyecto nuevaEjecucionEquiposProyecto = (EjecucionEquiposProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionEquiposProyecto, grupoConfiguracionCopiaObjeto);
                        nuevaEjecucionEquiposProyecto.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);

                        entityManager.persist(nuevaEjecucionEquiposProyecto);

                    }

                    List<EjecucionEventosProyecto> listaEjecucionEventosProyecto = entityManager.createNamedQuery("EjecucionEventosProyecto.SelectEventoRelacionadoInformeImplementacionProyecto")
                            .setParameter("idInformeAvanceImplementacion", informeAvanceImplementacion.getIdInformeAvanceImplementacion())
                            .getResultList();
                    for (EjecucionEventosProyecto unaEjecucionEventosProyecto : listaEjecucionEventosProyecto) {

                        EjecucionEventosProyecto nuevaEjecucionEventosProyecto = (EjecucionEventosProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionEventosProyecto, grupoConfiguracionCopiaObjeto);
                        nuevaEjecucionEventosProyecto.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);

                        entityManager.persist(nuevaEjecucionEventosProyecto);

                    }

                    List<EjecucionOtrosGastosProy> listaEjecucionOtrosGastosProy = entityManager.createNamedQuery("EjecucionOtrosGastosProy.SelectOtrosGastosInformeImplementacionProyecto")
                            .setParameter("idInformeAvanceImplementacion", informeAvanceImplementacion.getIdInformeAvanceImplementacion())
                            .getResultList();
                    for (EjecucionOtrosGastosProy unaEjecucionOtrosGastosProy : listaEjecucionOtrosGastosProy) {

                        EjecucionOtrosGastosProy nuevaEjecucionOtrosGastosProy = (EjecucionOtrosGastosProy) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionOtrosGastosProy, grupoConfiguracionCopiaObjeto);
                        nuevaEjecucionOtrosGastosProy.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);

                        entityManager.persist(nuevaEjecucionOtrosGastosProy);

                    }

                    List<EjecucionViajesProyecto> listaEjecucionViajesProyecto = entityManager.createNamedQuery("EjecucionViajesProyecto.SelectViajesRelacionadoInformeImplementacionProyecto")
                            .setParameter("idInformeAvanceImplementacion", informeAvanceImplementacion.getIdInformeAvanceImplementacion())
                            .getResultList();
                    for (EjecucionViajesProyecto unaEjecucionViajesProyecto : listaEjecucionViajesProyecto) {

                        EjecucionViajesProyecto nuevaEjecucionViajesProyecto = (EjecucionViajesProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unaEjecucionViajesProyecto, grupoConfiguracionCopiaObjeto);
                        nuevaEjecucionViajesProyecto.setInformeAvanceImplementacion(informeAvanceImplementacionCorregir);

                        entityManager.persist(nuevaEjecucionViajesProyecto);

                    }

                } else if (compromisoImplementacion.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO)) {

                    //COPIA PLAN TRABAJO IMPLEMENTACION
                    PlanTrabajoImplementacion planTrabajoImplementacion = iPlanTrabajoImplementacionLocal.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
                            compromisoImplementacion.getImplementacionesProyecto().getIdImplementacionProy(),
                            compromisoImplementacion.getIdCompromisoImplementacion());

                    PlanTrabajoImplementacion planTrabajoImplementacionCorregir = (PlanTrabajoImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(planTrabajoImplementacion, grupoConfiguracionCopiaObjeto);
                    planTrabajoImplementacionCorregir.setCompromisoImplementacion(compromisoImplementacionCorregir);
                    entityManager.persist(planTrabajoImplementacionCorregir);

                    //COMENTARIO COMPROMISO PROYECTO 
                    List<ComentarioCompromisoProyecto> listaComentarioCompromisoProyecto = iComentarioProyectoLocal.getListaComentarioCompromisoProyectoPorIdCompromisoImplementacion(
                            compromisoImplementacion.getIdCompromisoImplementacion());

                    for (ComentarioCompromisoProyecto unComentarioCompromisoProyecto : listaComentarioCompromisoProyecto) {

                        ComentarioCompromisoProyecto comentarioCompromisoProyectoCorregir = (ComentarioCompromisoProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unComentarioCompromisoProyecto, grupoConfiguracionCopiaObjeto);
                        comentarioCompromisoProyectoCorregir.setCompromisoImplementacion(compromisoImplementacionCorregir);
                        comentarioCompromisoProyectoCorregir.setFechaRegistro(new Date());

                        entityManager.persist(comentarioCompromisoProyectoCorregir);

                    }

                    //COPIA INVESTIGADORES
                    List<InvestigadorProyecto> listaInvestigadorProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());
                    for (InvestigadorProyecto unInvestigadorProyecto : listaInvestigadorProyecto) {

                        InvestigadorProyecto investigadorProyectoCorregir = (InvestigadorProyecto) entityManager.unwrap(JpaEntityManager.class).copy(unInvestigadorProyecto, grupoConfiguracionCopiaObjeto);
                        investigadorProyectoCorregir.setPlanTrabajoImplementacion(planTrabajoImplementacionCorregir);
                        entityManager.persist(investigadorProyectoCorregir);

                    }

                    //COPIA ACTIVIDADES IMPLEMENTACION
                    List<ActividadesPlanImplementacion> listaActividadesPlanImplementacion = iPlanTrabajoImplementacionLocal.getListaActividadesPlanImplementacionPorPlanTrabajoImpl(planTrabajoImplementacion.getIdPlanTrabajo());
                    for (ActividadesPlanImplementacion unActividadesPlanImplementacion : listaActividadesPlanImplementacion) {

                        ActividadesPlanImplementacion actividadesPlanImplementacionCorregir = (ActividadesPlanImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(unActividadesPlanImplementacion, grupoConfiguracionCopiaObjeto);
                        actividadesPlanImplementacionCorregir.setPlanTrabajoImplementacion(planTrabajoImplementacionCorregir);
                        entityManager.persist(actividadesPlanImplementacionCorregir);

                        //CONSULTAMOS LOS RESPONSABLES DE LA ACTIVIDAD
                        List<ResponsableActividaImplementacion> responsableActividaImplementacionList = unActividadesPlanImplementacion.getResponsableActividaImplementacionList();
                        for (ResponsableActividaImplementacion unResponsableActividaImplementacion : responsableActividaImplementacionList) {

                            ResponsableActividaImplementacion responsableActividaImplementacionCorregir = (ResponsableActividaImplementacion) entityManager.unwrap(JpaEntityManager.class).copy(unResponsableActividaImplementacion, grupoConfiguracionCopiaObjeto);
                            responsableActividaImplementacionCorregir.setActividadesPlanImplementacion(actividadesPlanImplementacionCorregir);
                            entityManager.persist(responsableActividaImplementacionCorregir);

                        }

                    }

                    //COPIA INDICADORES
                    List<IndicadoresPlanTrabajo> listaIndicadoresPlanTrabajoOtros = iIndicadoresPlanTrabajoLocal.getListaIndicadoresPlanTrabajoPorPlanTrabajoImplementacion(
                            planTrabajoImplementacion.getIdPlanTrabajo()
                    );
                    for (IndicadoresPlanTrabajo unIndicadoresPlanTrabajo : listaIndicadoresPlanTrabajoOtros) {

                        IndicadoresPlanTrabajo indicadoresPlanTrabajoCorregir = (IndicadoresPlanTrabajo) entityManager.unwrap(JpaEntityManager.class).copy(unIndicadoresPlanTrabajo, grupoConfiguracionCopiaObjeto);
                        indicadoresPlanTrabajoCorregir.setPlanTrabajoImplementacion(planTrabajoImplementacionCorregir);
                        entityManager.persist(indicadoresPlanTrabajoCorregir);

                    }

                    //ACTUALIZAMOS LAS FUENTES ANTERIORES
                    List<FuenteProyecto> listaFuenteProyectoImpl = iFuenteProyectoLocal.findFuentesByPlanTrabajo(planTrabajoImplementacion.getIdPlanTrabajo());
                    for (FuenteProyecto fuenteProyecto : listaFuenteProyectoImpl) {

                        //ACTUALIZAMOS EL EL ID PLAN TRABAJO
                        fuenteProyecto.setPlanTrabajoImplementacion(planTrabajoImplementacionCorregir);
                        entityManager.merge(fuenteProyecto);

                    }

                }

                compromisoImplementacion.setCompromisoImplementacionHijo(compromisoImplementacionCorregir);
                entityManager.merge(compromisoImplementacion);

            }

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());

        }
    }

    /**
     *
     * @param fechaRetroalimentacion
     * @param idResultadoRetroalimenta
     * @param idComprimiso
     * @param origenCompromiso
     * @param idEstadoCompromiso
     * @throws JpaDinaeException
     */
    @Override
    public void actualizarCompromisoProyectoEimplementacionResultadoRetroalimentacionAceptarCumplimiento(Date fechaRetroalimentacion, Long idResultadoRetroalimenta, Long idComprimiso, String origenCompromiso, Long idEstadoCompromiso) throws JpaDinaeException {

        try {

            if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, idComprimiso);
                compromisoProyecto.setFechaActualizacion(new Date());
                compromisoProyecto.setFechaNuevoCompromiso(fechaRetroalimentacion);
                compromisoProyecto.setResultadoRetroalimentacion(new Constantes(idResultadoRetroalimenta));
                //CAMBIA EL ESTADO DEL COMPROMISO A 'CUMPLIDO'
                compromisoProyecto.setEstado(new Constantes(idEstadoCompromiso));

                entityManager.merge(compromisoProyecto);

                //SI EL COMPROMISO ES 'INFORME FINAL', 
                if (IConstantes.COMPROMISO_PERIODO_INFORME_FINAL.equals(compromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes())) {
                    //EL SISTEMA CAMBIA EL ESTADO DEL PROYECTO A 'CULMINADO'. 
                    Proyecto proyecto = entityManager.find(Proyecto.class, compromisoProyecto.getProyecto().getIdProyecto());
                    proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_CULMINADO));

                    entityManager.merge(proyecto);
                }

            } else if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, idComprimiso);
                compromisoImplementacion.setFechaActualizacion(new Date());
                compromisoImplementacion.setFechaNuevoCompromiso(fechaRetroalimentacion);
                compromisoImplementacion.setResultadoRetroalimentacion(new Constantes(idResultadoRetroalimenta));
                //CAMBIA EL ESTADO DEL COMPROMISO A 'CORRECCIÓN COMPROMISO'
                compromisoImplementacion.setIdEstadoCompromisoImpl(new Constantes(idEstadoCompromiso));

                //SI EL COMPROMISO ES INFORME FINAL DE IMPLEMENTACIÓN, 
                if (IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL.equals(compromisoImplementacion.getIdTipoCompromiso().getIdConstantes())) {
                    //SE CAMBIA EL ESTADO DEL PROYECTO A 'IMPLEMENTADO'.
                    Proyecto proyecto = entityManager.find(Proyecto.class, compromisoImplementacion.getImplementacionesProyecto().getProyecto().getIdProyecto());
                    proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO));

                    entityManager.merge(proyecto);

                    //SE CAMBIA EL ESTADO A LA IMPLEMENTACION PROYECTO
                    ImplementacionesProyecto implementacionProy = compromisoImplementacion.getImplementacionesProyecto();
                    entityManager.refresh(implementacionProy);
                    implementacionProy.setEstadoImplementacion(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO));

                    entityManager.merge(implementacionProy);

                }

                entityManager.merge(compromisoImplementacion);

            }

            ejecutarProcedimientoCompromisoCumplido(origenCompromiso, idComprimiso);

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage());

        }

    }

    /**
     *
     * @param origenCompromiso
     * @param idCompromiso
     */
    @Override
    public void ejecutarProcedimientoCompromisoCumplido(String origenCompromiso, Long idCompromiso) throws JpaDinaeException {
        LOG.debug("metodo: ejecutarProcedimientoCompromisoCumplido() ->> parametros: origenCompromiso {}, idCompromiso {}", origenCompromiso, idCompromiso);

        try {

            if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

                CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, idCompromiso);

                //if( IConstantes.COMPROMISO_PERIODO_INFORME_FINAL.equals( compromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes() ) ){
                Connection connection = this.datasource.getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("call ");
                sb.append("CREA_V_PRESUPUES_PRO_CUMPLIDO");
                sb.append("(?,?)");
                CallableStatement cs = connection.prepareCall(sb.toString());
                cs.setLong(1, compromisoProyecto.getProyecto().getIdProyecto());
                cs.setLong(2, compromisoProyecto.getIdCompromisoProyecto());
                cs.executeQuery();

                //}                
            } else if (origenCompromiso.equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

                CompromisoImplementacion compromisoImplementacion = entityManager.find(CompromisoImplementacion.class, idCompromiso);
                
                Connection connection = this.datasource.getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("call ");
                sb.append("CREA_V_PRESU_PRO_CUMPLIDO_IMPL");
                sb.append("(?,?)");
                CallableStatement cs = connection.prepareCall(sb.toString());
                cs.setLong(1, idCompromiso);
                cs.setLong(2, compromisoImplementacion.getImplementacionesProyecto().getIdImplementacionProy());
                cs.executeQuery();

            }

        } catch (Exception e) {

            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error procedimiento", e);
            throw new JpaDinaeException(e);

        }
    }

    /**
     *
     * @param listaIdEstadoCompromiso
     * @param idListaItemTiposProyectosSeleccionado
     * @param idListaItemConvocatoriasSeleccionado
     * @param idListaItemAnioSeleccionadoIdPeriodo
     * @param idCompromisoSeleccionado
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyectoDTO> getListaUnidadPolicialNohanPresentadoPropuestaCompromisoEimplementacion(
            List<Long> listaIdEstadoCompromiso,
            Long idListaItemTiposProyectosSeleccionado,
            Long idListaItemConvocatoriasSeleccionado,
            Long idListaItemAnioSeleccionadoIdPeriodo,
            Long idCompromisoSeleccionado) throws JpaDinaeException {

        try {

            //COMPROMISOS PROYECTOS
            StringBuilder sqlCompromisoProyecto = new StringBuilder("SELECT NEW co.gov.policia.dinae.dto.CompromisoProyectoDTO( "
                    + " cp.proyecto.unidadPolicial.idUnidadPolicial,"
                    + " cp.proyecto.unidadPolicial.nombre,"
                    + " cp.proyecto.tituloPropuesto,"
                    + " cp.compromisoPeriodo.tipoCompromiso.idConstantes,"
                    + " cp.compromisoPeriodo.tipoCompromiso.valor,"
                    + " cp.compromisoPeriodo.numeroIncrementa,"
                    + " cp.compromisoPeriodo.fecha, "
                    + " cp.compromisoPeriodo.idCompromisoPeriodo ) FROM CompromisoProyecto cp "
                    + " WHERE cp.estado.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false));

            Map<String, Object> mapaParametrosCompromisoProyecto = new HashMap<String, Object>();

            //PRIMERO VERIFICAMOS EL FILTRO DE LOS TIPOS DE PROYECTOS
            if (idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION)) {

                sqlCompromisoProyecto.append(" AND cp.proyecto.codigoProyecto LIKE 'CONV%' ");

                if (idListaItemConvocatoriasSeleccionado != null) {

                    sqlCompromisoProyecto.append(" AND cp.proyecto.periodo.idPeriodo = :idPeriodo ");
                    mapaParametrosCompromisoProyecto.put("idPeriodo", idListaItemConvocatoriasSeleccionado);

                }

            } else if (idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES)) {

                sqlCompromisoProyecto.append(" AND cp.proyecto.codigoProyecto LIKE 'VIC%' ");

                if (idListaItemAnioSeleccionadoIdPeriodo != null) {

                    Periodo periodo = entityManager.find(Periodo.class, idListaItemAnioSeleccionadoIdPeriodo);

                    sqlCompromisoProyecto.append(" AND cp.proyecto.periodo.anio = :anio ");
                    mapaParametrosCompromisoProyecto.put("anio", periodo.getAnio());

                }

            }

            if (idCompromisoSeleccionado != null) {

                CompromisoPeriodo compromisoPeriodo = entityManager.find(CompromisoPeriodo.class, idCompromisoSeleccionado);

                sqlCompromisoProyecto.append(" AND cp.compromisoPeriodo.tipoCompromiso.idConstantes = :idTipoCompromiso ");
                mapaParametrosCompromisoProyecto.put("idTipoCompromiso", compromisoPeriodo.getTipoCompromiso().getIdConstantes());

                //SI EL TIPO DE COMPROMISO DE ESTE COMPROMISO PERIODO ES DE TIPO "INFORME_DE_AVANCE"
                if (compromisoPeriodo.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)) {
                    //ADICIONAMOS EL NUMERO DE CONCECUTIVO, YA QUE LOS INFORMES DE AVANCES PUEDEN SER
                    //Informe de Avance 1
                    //Informe de Avance 2
                    //Informe de Avance ...
                    //Informe de Avance n
                    sqlCompromisoProyecto.append(" AND cp.compromisoPeriodo.numeroIncrementa = :numeroIncrementa ");
                    mapaParametrosCompromisoProyecto.put("numeroIncrementa", compromisoPeriodo.getNumeroIncrementa());
                }
            }

            sqlCompromisoProyecto.append(" ORDER BY cp.proyecto.unidadPolicial.nombre ASC, cp.compromisoPeriodo.fecha ASC ");
            Query query = entityManager.createQuery(sqlCompromisoProyecto.toString());

            Iterator<Map.Entry<String, Object>> itera = mapaParametrosCompromisoProyecto.entrySet().iterator();

            while (itera.hasNext()) {

                Map.Entry<String, Object> kv = itera.next();
                query.setParameter(kv.getKey(), kv.getValue());

            }

            //VERIFICAMOS LAS FECHAS LIMITES OTORGADAS DE CADA PROPUESTA            
            List<CompromisoProyectoDTO> listaCompromisosEncontrados = query.getResultList();
            for (CompromisoProyectoDTO compromisoProyectoDTO : listaCompromisosEncontrados) {

                ExcepcionesCompromiso excepcionesCompromiso = iExcepcionCompromisoLocal.getUltimaExcepcionesCompromisoPorUnidadPolicialYcompromisoPeriodo(
                        compromisoProyectoDTO.getIdUnidadPolicial(),
                        compromisoProyectoDTO.getIdCompromisoPeriodo());

                if (excepcionesCompromiso != null) {
                    compromisoProyectoDTO.setFechaLimieOtorgada(excepcionesCompromiso.getFechaLimite());
                }

            }

            //COMPROMISOS IMPLEMENTACION
            StringBuilder sqlCompromisoImplementacion = new StringBuilder("SELECT NEW co.gov.policia.dinae.dto.CompromisoProyectoDTO( "
                    + " cp.implementacionesProyecto.proyecto.unidadPolicial.idUnidadPolicial,"
                    + " cp.implementacionesProyecto.proyecto.unidadPolicial.nombre,"
                    + " cp.implementacionesProyecto.proyecto.tituloPropuesto,"
                    + " cp.idTipoCompromiso.idConstantes,"
                    + " cp.idTipoCompromiso.valor,"
                    + " cp.fechaNuevoCompromiso ) FROM CompromisoImplementacion cp "
                    + " WHERE cp.idEstadoCompromisoImpl.idConstantes IN " + UtilJPA.generateCollection(listaIdEstadoCompromiso, false));

            Map<String, Object> mapaParametrosCompromisoImplementacion = new HashMap<String, Object>();

            //PRIMERO VERIFICAMOS EL FILTRO DE LOS TIPOS DE PROYECTOS
            if (idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION)) {

                sqlCompromisoImplementacion.append(" AND cp.implementacionesProyecto.proyecto.codigoProyecto LIKE 'CONV%' ");

                if (idListaItemConvocatoriasSeleccionado != null) {

                    sqlCompromisoImplementacion.append(" AND cp.implementacionesProyecto.proyecto.periodo.idPeriodo = :idPeriodo ");
                    mapaParametrosCompromisoImplementacion.put("idPeriodo", idListaItemConvocatoriasSeleccionado);

                }

            } else if (idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES)) {

                sqlCompromisoImplementacion.append(" AND cp.implementacionesProyecto.proyecto.codigoProyecto LIKE 'VIC%' ");

                if (idListaItemAnioSeleccionadoIdPeriodo != null) {

                    sqlCompromisoImplementacion.append(" AND cp.implementacionesProyecto.proyecto.periodo.anio = :anio ");
                    mapaParametrosCompromisoImplementacion.put("anio", idListaItemAnioSeleccionadoIdPeriodo);

                }

            }

            if (idCompromisoSeleccionado != null) {

                sqlCompromisoImplementacion.append(" AND cp.idTipoCompromiso.idConstantes = :idTipoCompromiso ");
                mapaParametrosCompromisoImplementacion.put("idTipoCompromiso", idCompromisoSeleccionado);

            }

            sqlCompromisoImplementacion.append(" ORDER BY cp.implementacionesProyecto.proyecto.unidadPolicial.nombre ASC ");
            query = entityManager.createQuery(sqlCompromisoImplementacion.toString());
            itera = mapaParametrosCompromisoImplementacion.entrySet().iterator();

            while (itera.hasNext()) {

                Map.Entry<String, Object> kv = itera.next();
                query.setParameter(kv.getKey(), kv.getValue());

            }

            listaCompromisosEncontrados.addAll(query.getResultList());

            return listaCompromisosEncontrados;

        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @param compromisoProyecto
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarFechaUltimaProyectoListener(CompromisoProyecto compromisoProyecto) {

        try {

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " ---INICIO PostPersist CompromisoProyectoListenersEjbBean --- ");

            entityManager.createNamedQuery("Proyecto.UPDATEfechaActualizacionPorProyecto")
                    .setParameter("fechaActualizacion", new Date())
                    .setParameter("idProyecto", compromisoProyecto.getProyecto().getIdProyecto())
                    .executeUpdate();

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " ---FIN PostPersist CompromisoProyectoListenersEjbBean --- ");

        } catch (Exception e) {

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " ---FIN ERROR PostPersist CompromisoProyectoListenersEjbBean --- ");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     *
     * @param idProyecto
     * @param idListaEstadoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarCompromisoroyectoPorProyectoYlistaEstado(Long idProyecto, List<Long> idListaEstadoCompromiso) throws JpaDinaeException {

        try {

            Object cuantos = entityManager.createNamedQuery("CompromisoProyecto.contarCompromisoroyectoPorProyectoYlistaEstado")
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idListaEstadoCompromiso", idListaEstadoCompromiso)
                    .getSingleResult();

            if (cuantos == null) {
                return 0;
            }

            return ((Number) cuantos).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param idUnidadPolicial
     * @param fechaActual
     * @param estados
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyecto> findAllCompromisosVigentes(Long idUnidadPolicial, Date fechaActual, Long[] estados) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("CompromisoProyecto.findAllCompromisosVigentes")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("fechaActual", fechaActual)
                    .setParameter("estados", Arrays.asList(estados))
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idUnidadPolicial
     * @param idProyecto
     * @param fechaActual
     * @param fechaMes
     * @param estados
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyecto> findAllCompromisosVigentesMesProyecto(Long idUnidadPolicial, Long idProyecto, Date fechaActual, Date fechaMes, Long[] estados) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("CompromisoProyecto.findAllCompromisosVigentesMesProyecto")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("fechaActual", fechaActual)
                    .setParameter("fechaMes", fechaMes)
                    .setParameter("estados", Arrays.asList(estados))
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param prefijoCodigoProyecto
     * @param estados
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int findAllCompromisosEnviadosVicin(String prefijoCodigoProyecto, Long[] estados) throws JpaDinaeException {
        try {

            String sql = "SELECT count( c.idCompromisoProyecto ) "
                    + " FROM CompromisoProyecto c "
                    + " WHERE c.proyecto.codigoProyecto LIKE '" + prefijoCodigoProyecto + "%' AND c.estado.idConstantes IN :estados";
            Object cuantos = entityManager.createQuery(sql)
                    .setParameter("estados", Arrays.asList(estados))
                    .getSingleResult();

            if (cuantos == null) {
                return 0;
            }

            return ((Number) cuantos).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idCompromisoPRoyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Long getIdTipoCompromisoProyectoPorCompromisoProyecto(Long idCompromisoPRoyecto) throws JpaDinaeException {

        try {

            return (Long) entityManager.createNamedQuery("CompromisoProyecto.findIdTipoCompromiso")
                    .setParameter("idCompromisoProyecto", idCompromisoPRoyecto)
                    .getSingleResult();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idUnidadPolicial
     * @param estados
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyecto> findAllCompromisosVigentesJefeUnidad(Long idUnidadPolicial, Long[] estados) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("CompromisoProyecto.findAllCompromisosVigentesJefeUnidad")
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("estados", Arrays.asList(estados))
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idProyecto
     * @param idEstadoCompromiso
     * @param idTipoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Long getIdCompromisoProyectoFinalPorProyectoYtipoCompromisoYEstadoCompromiso(Long idProyecto, Long idEstadoCompromiso, Long idTipoCompromiso) throws JpaDinaeException {

        try {

            return (Long) entityManager.createNamedQuery("CompromisoProyecto.findIdCompromisosProyectoInformeFinal")
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idTipoCompromiso", idTipoCompromiso)
                    .setParameter("idEstadoCompromiso", idEstadoCompromiso)
                    .getSingleResult();

        } catch (Exception e) {
            throw new JpaDinaeException(e);
        }
    }

    /**
     *
     * @param idInformeAvance
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public Long getEstadoCompromisoProyectoPorIdInformeAvance(Long idInformeAvance) throws JpaDinaeException {

        try {

            return (Long) entityManager.createNamedQuery("InformeAvance.findEstadoCompromisoProyectoPorIdInforme")
                    .setParameter("idInformeAvance", idInformeAvance)
                    .getSingleResult();

        } catch (Exception e) {

            throw new JpaDinaeException(e);

        }
    }

    /**
     *
     * @param idTipoProyecto
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoProyectoDTO> getCompromisoProyectosCorreccionPorTipoProyectoYUnidad(Long idTipoProyecto, Long idUnidadPolicial) throws JpaDinaeException {

        try {

            String consulta = null;
            if (idTipoProyecto.equals(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES)) {

                consulta = "SELECT NEW co.gov.policia.dinae.dto.CompromisoProyectoDTO( c.idCompromisoProyecto, c.nombreCompromisoCorrecion, c.proyecto.codigoProyecto ) FROM CompromisoProyecto c WHERE c.compromisoProyectoHijo IS NULL AND c.nombreCompromisoCorrecion IS NOT NULL AND c.compromisoProyectoPadre.idCompromisoProyecto IS NOT NULL AND c.proyecto.idProyecto IN ( SELECT p.idProyecto FROM Proyecto p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.codigoProyecto LIKE 'VIC%' AND p.estado.idConstantes IN ( 110,111 )  ) ";

            } else if (idTipoProyecto.equals(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION)) {

                consulta = "SELECT NEW co.gov.policia.dinae.dto.CompromisoProyectoDTO( c.idCompromisoProyecto, c.nombreCompromisoCorrecion, c.proyecto.codigoProyecto ) FROM CompromisoProyecto c WHERE c.compromisoProyectoHijo IS NULL AND c.nombreCompromisoCorrecion IS NOT NULL AND c.compromisoProyectoPadre.idCompromisoProyecto IS NOT NULL AND c.proyecto.idProyecto IN ( SELECT p.idProyecto FROM Proyecto p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.codigoProyecto LIKE 'CONV%' AND p.estado.idConstantes IN ( 110,111 )  ) ";

            }

            if (consulta == null) {

                return null;

            }

            return entityManager.createQuery(consulta)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e);

        }
    }
}
