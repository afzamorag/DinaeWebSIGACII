package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CompromisoDTO;
import co.gov.policia.dinae.dto.CompromisoPeriodoDTO;
import co.gov.policia.dinae.dto.ValidacionCompromisoPeriodoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
@Stateless
public class CompromisoPeriodoEjbBean implements ICompromisoPeriodoLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;

    @EJB
    private IConstantesLocal iConstantesLocal;

    /**
     *
     * @param listaHitoPeriodos
     * @param identificacion
     * @throws JpaDinaeException
     */
    @Override
    public void agregarCompromisoPeriodo(List<CompromisoPeriodo> listaHitoPeriodos, String identificacion) throws JpaDinaeException {

        for (CompromisoPeriodo compromisoPeriodo : listaHitoPeriodos) {

            if (compromisoPeriodo.getIdCompromisoPeriodo() == null) {

                compromisoPeriodo.setFechaRegistro(new Date());
                compromisoPeriodo.setIdentificacionRegistro(identificacion);
                entityManager.persist(compromisoPeriodo);

            } else {

                compromisoPeriodo.setIdentificacionActualiza(identificacion);
                compromisoPeriodo.setFechaActualiza(new Date());
                entityManager.merge(compromisoPeriodo);

            }
        }

    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<CompromisoPeriodo> buscarCompromisoPeriodo(Long idPeriodo) throws JpaDinaeException {
        try {

            List<CompromisoPeriodo> periodos = entityManager.createNamedQuery("CompromisoPeriodo.findByPeriodoOrdenarPorFecha")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();
            return periodos;

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idPeriodo
     * @param idTipoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoPeriodo> buscarCompromisoPeriodoPorPeriodoYTipoCompromiso(Long idPeriodo, Long idTipoCompromiso) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("CompromisoPeriodo.findPorPeriodoYtipoCompromiso")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idCompromiso", idTipoCompromiso)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param idPeriodo
     * @param idTipoCompromiso
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarCompromisoPeriodoPorPeriodoYTipoCompromiso(Long idPeriodo, Long idTipoCompromiso) throws JpaDinaeException {

        try {

            Object cuantos = entityManager.createNamedQuery("CompromisoPeriodo.contarPorPeriodoYtiposCompromisoFormulacionEinformeFinal")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idTipoCompromiso", idTipoCompromiso)
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
     * @param idProyecto
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoDTO> getListaCompromisosProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

        try {

            List<Long> idListaEstado = new ArrayList<Long>(1);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ACEPTADO);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN);
            idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO);

            List<CompromisoDTO> listaCompromisoDTO = new ArrayList<CompromisoDTO>();

            List<CompromisoProyecto> listaCompromisoProyecto = entityManager.createNamedQuery("CompromisoProyecto.findByIdProyectoListaEstado")
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idListaEstado", idListaEstado)
                    .getResultList();

            for (CompromisoProyecto unCompromisoProyecto : listaCompromisoProyecto) {

                CompromisoDTO dTO = new CompromisoDTO();
                //SOLO SE MUESTRA EL LINK Ver Informe, SI ESTE SE ENCUENTRA EN ESTADO "ESTADO_COMPROMISO_PROYECTO_CUMPLIDO"
                dTO.setMuestraLink(
                        unCompromisoProyecto.getEstado().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)
                        && !unCompromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO));
                dTO.setIdCompromisoProyecto(unCompromisoProyecto.getIdCompromisoProyecto());
                dTO.setOrigenCompromiso(IConstantes.ORIGEN_COMPROMISO_PROYECTO);
                //TODOS APLICAN EL MISMO ESTADO : FILTRO "idListaEstado"
                dTO.setEstado(unCompromisoProyecto.getEstado().getValor());
                dTO.setIdEstadoCompromiso(unCompromisoProyecto.getEstado().getIdConstantes());
                dTO.setFechaPrentacion(unCompromisoProyecto.getFechaEnvioVicin());

                if (unCompromisoProyecto.getCompromisoProyectoPadre() == null) {

                    dTO.setNombreCompromiso(unCompromisoProyecto.getNombreCompromisoCorreccionNumeroIncrementa());
                    dTO.setFechaLimieGeneral(unCompromisoProyecto.getCompromisoPeriodo().getFecha());

                } else {

                    dTO.setNombreCompromiso(unCompromisoProyecto.getNombreCompromisoCorrecion());
                    dTO.setFechaLimieGeneral(unCompromisoProyecto.getFechaNuevoCompromiso());
                }

                listaCompromisoDTO.add(dTO);
            }

            List<CompromisoImplementacion> listaCompromisoImplementacion = entityManager.createNamedQuery("CompromisoImplementacion.findByIdProyectoListaEstado")
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("idListaEstado", idListaEstado)
                    .getResultList();

            for (CompromisoImplementacion unCompromisoImplementacion : listaCompromisoImplementacion) {

                CompromisoDTO dTO = new CompromisoDTO();
                //SOLO SE MUESTRA EL LINK Ver Informe, SI ESTE SE ENCUENTRA EN ESTADO "ESTADO_COMPROMISO_PROYECTO_CUMPLIDO"
                dTO.setMuestraLink(unCompromisoImplementacion.getIdEstadoCompromisoImpl().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO));
                dTO.setIdCompromisoProyecto(unCompromisoImplementacion.getIdCompromisoImplementacion());
                dTO.setOrigenCompromiso(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION);
                //TODOS APLICAN EL MISMO ESTADO : FILTRO "idListaEstado"
                dTO.setEstado(unCompromisoImplementacion.getIdEstadoCompromisoImpl().getValor());
                dTO.setIdEstadoCompromiso(unCompromisoImplementacion.getIdEstadoCompromisoImpl().getIdConstantes());
                dTO.setFechaPrentacion(unCompromisoImplementacion.getFechaEnvioVicin());

                Constantes constantesTipoCompromiso = iConstantesLocal.getConstantesPorIdConstante(unCompromisoImplementacion.getIdTipoCompromiso().getIdConstantes());

                if (unCompromisoImplementacion.getCompromisoImplementacionPadre() == null) {

                    dTO.setNombreCompromiso(constantesTipoCompromiso.getValor());
                    dTO.setFechaLimieGeneral(unCompromisoImplementacion.getFechaCompromiso());

                } else {

                    dTO.setNombreCompromiso(unCompromisoImplementacion.getNombreCompromisoCorrecion());
                    dTO.setFechaLimieGeneral(unCompromisoImplementacion.getFechaNuevoCompromiso());
                }

                listaCompromisoDTO.add(dTO);
            }

            return listaCompromisoDTO;

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
    public List<CompromisoPeriodoDTO> getListaCompromisoPeriodoDTOporIdPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("CompromisoPeriodoDTO.findAllCompormisoPeriodoProyectosInstitucionalesPorAnioYestadoPublicado")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }

    }

    @Override
    public List<CompromisoPeriodo> buscarCompromisoPeriodoByIdPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("CompromisoPeriodo.finByIdPropuestaNecesidad")
                    .setParameter("propuestaNecesidad", propuestaNecesidad)
                    .getResultList();
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public List<CompromisoPeriodo> buscarCompromisoPeriodoByIdPropuestaNecesidadAndTipoCompromiso(PropuestaNecesidad propuestaNecesidad, Constantes tipoCompromiso) throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("CompromisoPeriodo.finByIdPropuestaNecesidadAndTipoCompromiso")
                    .setParameter("propuestaNecesidad", propuestaNecesidad)
                    .setParameter("tipoCompromiso", tipoCompromiso)
                    .getResultList();
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
    public List<CompromisoPeriodo> getListaCompromisoPeriodoPorIdPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("CompromisoPeriodo.findAllCompormisoPeriodoProyectosInstitucionalesPorAnioYestadoPublicado")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }

    }

    /**
     *
     * @param idConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoPeriodoDTO> getListaCompromisoPeriodoDTOporConvocatoriaPeriodo(Long idConvocatoria) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("CompromisoPeriodoDTO.findAllCompormisoPeriodoProyectosConvocatoriaPorConvocatoria")
                    .setParameter("idPeriodo", idConvocatoria)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }

    }

    /**
     *
     * @param idConvocatoria
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<CompromisoPeriodo> getListaCompromisoPeriodoPorConvocatoriaConvocatoria(Long idConvocatoria) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("CompromisoPeriodo.findAllCompormisoPeriodoProyectosConvocatoriaPorConvocatoria")
                    .setParameter("idPeriodo", idConvocatoria)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }

    /**
     *
     * @param compromisoPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public CompromisoPeriodo guardarCompromisoPeriodo(CompromisoPeriodo compromisoPeriodo) throws JpaDinaeException {

        try {

            if (compromisoPeriodo.getIdCompromisoPeriodo() == null) {

                compromisoPeriodo.setFechaRegistro(new Date());
                entityManager.persist(compromisoPeriodo);

            } else {

                compromisoPeriodo.setFechaActualiza(new Date());
                entityManager.merge(compromisoPeriodo);

            }

            return compromisoPeriodo;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idPeriodo
     * @param fechaActualCompromisoPeriiodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public CompromisoPeriodo getFechaConcecuenteCompromisoPeriodo(Long idPeriodo, Date fechaActualCompromisoPeriiodo) throws JpaDinaeException {

        try {

            return (CompromisoPeriodo) entityManager.createNamedQuery("CompromisoPeriodo.findDatePorPeriodoYFechaActualCompromisoActual")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("fechaCompromisoActual", fechaActualCompromisoPeriiodo)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (NoResultException e) {

            return null;

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idCompromisoPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public CompromisoPeriodo obtenerCompromisoPeriodoPorId(Long idCompromisoPeriodo) throws JpaDinaeException {

        try {

            return entityManager.find(CompromisoPeriodo.class, idCompromisoPeriodo);

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
    public List<ValidacionCompromisoPeriodoDTO> conteoValidacionComprimisosPeriodo(Long idPeriodo) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("CompromisoPeriodo.conteoValidacionComprimisosPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }

    @Override
    public void eliminarCompromisoPeriodo(CompromisoPeriodo compromisoPeriodo) throws JpaDinaeException {
        try {
            compromisoPeriodo = entityManager.find(CompromisoPeriodo.class, compromisoPeriodo.getIdCompromisoPeriodo());
            entityManager.remove(compromisoPeriodo);
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    /**
     *
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarCompromisoPeriodoPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            Object cuantos = entityManager.createNamedQuery("CompromisoPeriodo.contarPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();

            if (cuantos == null) {
                return 0;
            }

            return ((Number) cuantos).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

}
