package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.PropuestaNecesidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;

import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class PropuestaNecesidadEjbBean implements IPropuestaNecesidadLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;
    @EJB
    private IProyectoLocal iProyectoLocal;
    @EJB
    private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;
    private static final Logger LOG = LoggerFactory.getLogger(PropuestaNecesidadEjbBean.class);

    //@Inject
    //@GenericSDOQualifier
    //private SDO sdo;
    /**
     * Cuantas propuesta necesiades tiene estado 'En elaboración', 'Aceptada´ y
     * 'No aceptada'
     *
     * @param idPeriodo
     * @return (0) Cuando no existen registros que no cumplen con la condicion,
     * (-1) cuando no existen registros que contar, # de registros que cumplen
     * @throws JpaDinaeException
     */
    @Override
    public int contarPropuestaNecesidadPorPeriodoYestados(Long idPeriodo, Long idEstadoElaboracion, Long idEstadoAceptado, Long idEstadoNoAceptado) throws JpaDinaeException {

        try {
            Object cuantos = entityManager.createNamedQuery("PropuestaNecesidad.contarTodasPropuestasPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();
            //SI NO EXISTEN REGISTROS QUE CONTAR
            if (cuantos == null || ((Number) cuantos).intValue() == 0) {
                return -1;
            }

            //SI EXISTEN REGISTRO, AHORA VERIFICAMOS CUANTOS CUMPLEN CON LA CONDICION
            cuantos = entityManager.createNamedQuery("PropuestaNecesidad.contarByPeriodoYEstados")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idEstadoElaboracion", idEstadoElaboracion)
                    .setParameter("idEstadoAceptado", idEstadoAceptado)
                    .setParameter("idEstadoNoAceptado", idEstadoNoAceptado)
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
     * @param idPeriodo
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int contarPropuestaNecesidadPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {
            Object cuantos = entityManager.createNamedQuery("PropuestaNecesidad.contarTodasPropuestasPorPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getSingleResult();
            //SI NO EXISTEN REGISTROS QUE CONTAR
            if (cuantos == null) {
                return 0;
            }

            return ((Number) cuantos).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public int contarPropuestaNecesidadPorPeriodoyUnidadPolicial(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException {

        try {
            Object cuantos = entityManager.createNamedQuery("PropuestaNecesidad.contarTodasPropuestasPorPeriodoYunidad")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getSingleResult();
            //SI NO EXISTEN REGISTROS QUE CONTAR
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
     * @param idPeriodo
     * @param idEstado1
     * @param idEstado2
     * @param idEstado3
     * @param idUnidadPolicialSeleccionada
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoYEstado(Long idPeriodo, Long idEstado1, Long idEstado2, Long idEstado3, Long idUnidadPolicialSeleccionada) throws JpaDinaeException {

        try {

            List<PropuestaNecesidad> listaNecesidad;
            if (idUnidadPolicialSeleccionada == null) {

                listaNecesidad = entityManager.createNamedQuery("PropuestaNecesidad.findAllByPeriodoYListaEstado")
                        .setParameter("idPeriodo", idPeriodo)
                        .setParameter("idEstado1", idEstado1)
                        .setParameter("idEstado2", idEstado2)
                        .setParameter("idEstado3", idEstado3)
                        .getResultList();
            } else {

                listaNecesidad = entityManager.createNamedQuery("PropuestaNecesidad.findAllByPeriodoYListaEstadoYunidadPolicial")
                        .setParameter("idPeriodo", idPeriodo)
                        .setParameter("idEstado1", idEstado1)
                        .setParameter("idEstado2", idEstado2)
                        .setParameter("idEstado3", idEstado3)
                        .setParameter("idUnidadPolicial", idUnidadPolicialSeleccionada)
                        .getResultList();

            }

            //PARA CADA PROPUESTA NECESIDAD CONSULTAMOS LA LA UNIDAD POLICIAL RESPONSABLE
            for (PropuestaNecesidad unaPropuestaNecesidad : listaNecesidad) {

                EjecutorNecesidad ejecutorNecesidad = iEjecutorNecesidadLocal.getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(unaPropuestaNecesidad.getIdPropuestaNecesidad());
                unaPropuestaNecesidad.setUnidadPolicialReponsableEjecutora(ejecutorNecesidad.getUnidadPolicial());
            }

            return listaNecesidad;

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param idPeriodo
     * @param idEstado1
     * @param idEstado2
     * @param idEstado3
     * @param idUnidadPolicialSeleccionada
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<PropuestaNecesidadDTO> getPropuestaNecesidadDTOPorPeriodoYEstado(Long idPeriodo, Long idEstado1, Long idEstado2, Long idEstado3, Long idUnidadPolicialSeleccionada) throws JpaDinaeException {

        try {

            if (idUnidadPolicialSeleccionada == null) {
                return entityManager.createNamedQuery("PropuestaNecesidadDTO.findAllByPeriodoYListaEstado")
                        .setParameter("idPeriodo", idPeriodo)
                        .setParameter("idEstado1", idEstado1)
                        .setParameter("idEstado2", idEstado2)
                        .setParameter("idEstado3", idEstado3)
                        .getResultList();
            } else {
                return entityManager.createNamedQuery("PropuestaNecesidadDTO.findAllByPeriodoYListaEstadoYunidadPolicial")
                        .setParameter("idPeriodo", idPeriodo)
                        .setParameter("idEstado1", idEstado1)
                        .setParameter("idEstado2", idEstado2)
                        .setParameter("idEstado3", idEstado3)
                        .setParameter("idUnidadPolicial", idUnidadPolicialSeleccionada)
                        .getResultList();
            }

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodo(Long idPeriodo) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("PropuestaNecesidad.findAllByPeriodo")
                    .setParameter("idPeriodo", idPeriodo)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
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
    public List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoYunidadPolicial(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException {

        try {

            if (idUnidadPolicial == null) {
                return new ArrayList<PropuestaNecesidad>();
            }
            return entityManager.createNamedQuery("PropuestaNecesidad.findAllByPeriodoYunidadPolicial")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
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
    public int getNumeroPropuestaNecesidadPorPeriodoYunidadYestado(Long idPeriodo, Long idUnidadPolicial, Long idEstado) throws JpaDinaeException {

        try {

            if (idUnidadPolicial == null || idEstado == null) {
                return 0;
            }
            Object numero = entityManager.createNamedQuery("PropuestaNecesidad.ContarByPeriodoYunidadPolicialYEstado")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idEstado", idEstado)
                    .getSingleResult();

            if (numero == null) {
                return 0;
            }
            return ((Number) numero).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idPeriodo
     * @param idUnidadPolicial
     * @param idEstado
     * @param idTipoRol
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public int getNumeroPropuestaNecesidadDesdeEjecutorPorPeriodoYunidadYestado(Long idPeriodo, Long idUnidadPolicial, Long idEstado, Long idTipoRol) throws JpaDinaeException {

        try {

            if (idUnidadPolicial == null || idEstado == null) {
                return 0;
            }
            Object numero = entityManager.createNamedQuery("EjecutorNecesidad.ContarByPeriodoYunidadPolicialYEstado")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .setParameter("idEstado", idEstado)
                    .setParameter("idTipoRol", idTipoRol)
                    .getSingleResult();

            if (numero == null) {
                return 0;
            }
            return ((Number) numero).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
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
    public List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoYunidadPolicialyEstados(Long idPeriodo, Long idUnidadPolicial, List<Long> listaIdEstados) throws JpaDinaeException {

        try {

            if (idUnidadPolicial == null || listaIdEstados == null || listaIdEstados.isEmpty()) {
                return new ArrayList<PropuestaNecesidad>();
            }
            String sql = "SELECT p FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND ";
            StringBuilder selectIn = new StringBuilder();
            for (int index = 0; index < listaIdEstados.size(); index++) {

                selectIn.append(" p.constantes.idConstantes = ".concat(listaIdEstados.get(index).toString()));
                if (!(index == (listaIdEstados.size() - 1))) {
                    selectIn.append(" OR ");
                }
            }

            return entityManager.createQuery(sql.concat(" ( ").concat(selectIn.toString()).concat(" ) ORDER BY p.idPropuestaNecesidad ASC"))
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidadPolicial)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param propuestaNecesidad
     * @param idTipoRolEjecutorNecesidadAdicionar
     * @param idUnidadPolicialEjecutorNecesidad
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public PropuestaNecesidad guardarPropuestaCreaEjecutorNecesidad(PropuestaNecesidad propuestaNecesidad, Long idTipoRolEjecutorNecesidadAdicionar, Long idUnidadPolicialEjecutorNecesidad) throws JpaDinaeException {

        try {

            if (propuestaNecesidad.getIdPropuestaNecesidad() == null) {

                propuestaNecesidad.setFechaEnvio(new Date());
                entityManager.persist(propuestaNecesidad);

            } else {

                propuestaNecesidad.setFechaModificacion(new Date());
                entityManager.merge(propuestaNecesidad);

            }

            //DESPUES DE GUARDAR LA PROPUESTA SE GUARDA EL EJECUTOR DE DICHA NECESIDA
            //SI EL EjecutorNecesidad ES NULL
            //SOLO DEBE HABER UN EjecutorNecesidad CON ROL DE Responsable
            //LOS DEMAS DEBEN SER DE APOYO
            if (idTipoRolEjecutorNecesidadAdicionar == null) {

                //VERIFICAMOS SI ESTA NECESIDAD TIENE ALGUN  EJECUTOR CON ROL RESPONSABLE
                EjecutorNecesidad unEjecutorConsulta = iEjecutorNecesidadLocal
                        .getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(
                                propuestaNecesidad.getIdPropuestaNecesidad());

                //SI NO TIENE EJECUTOR RESPONSABLE, LO CREAMOS
                if (unEjecutorConsulta == null) {

                    unEjecutorConsulta = new EjecutorNecesidad();
                    unEjecutorConsulta.setPropuestaNecesidad(propuestaNecesidad);
                    unEjecutorConsulta.setRol(new Constantes(IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE));
                    unEjecutorConsulta.setUnidadPolicial(propuestaNecesidad.getUnidadPolicial());

                    entityManager.persist(unEjecutorConsulta);
                }
            } else //VERIFICAMOS QUE EL ROL ES : RESPONSABLE
             if (IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE.equals(idTipoRolEjecutorNecesidadAdicionar)) {
                    //BUSCAMOS SI EXISTE UN ROL RESPONSABLE ACTUALMENTE
                    //YA QUE NO DEBE HABER MAS DE UN REPONSABLE
                    EjecutorNecesidad unEjecutorConsulta = iEjecutorNecesidadLocal
                            .getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(propuestaNecesidad.getIdPropuestaNecesidad());

                    //SI NO TIENE EJECUTOR RESPONSABLE, LO CREAMOS
                    if (unEjecutorConsulta == null) {

                        EjecutorNecesidad unEjecutorResponsable = new EjecutorNecesidad();
                        unEjecutorResponsable.setPropuestaNecesidad(propuestaNecesidad);
                        unEjecutorResponsable.setRol(new Constantes(IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE));
                        unEjecutorResponsable.setUnidadPolicial(new UnidadPolicial(idUnidadPolicialEjecutorNecesidad));

                        entityManager.persist(unEjecutorResponsable);

                    } else {
                        //SI YA TIENE ROL DE RESPONSABLE
                        //EL RESPONSABLE ENCONTRADO PASA A SER ROL APOYO
                        unEjecutorConsulta.setRol(new Constantes(IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_DE_APOYO));

                        entityManager.merge(unEjecutorConsulta);

                        //Y SE INSERTA EL NUEVO RESPONSABLE
                        EjecutorNecesidad nuevoRolResponsableEjecutorConsulta = new EjecutorNecesidad();
                        nuevoRolResponsableEjecutorConsulta.setPropuestaNecesidad(propuestaNecesidad);
                        nuevoRolResponsableEjecutorConsulta.setRol(new Constantes(IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE));
                        nuevoRolResponsableEjecutorConsulta.setUnidadPolicial(new UnidadPolicial(idUnidadPolicialEjecutorNecesidad));

                        entityManager.persist(nuevoRolResponsableEjecutorConsulta);

                    }
                } else {
                    //INSERTAMOS EL NUEVO ROL: APOYO
                    EjecutorNecesidad unEjecutorConsulta = new EjecutorNecesidad();
                    unEjecutorConsulta.setPropuestaNecesidad(propuestaNecesidad);
                    unEjecutorConsulta.setRol(new Constantes(idTipoRolEjecutorNecesidadAdicionar));
                    unEjecutorConsulta.setUnidadPolicial(new UnidadPolicial(idUnidadPolicialEjecutorNecesidad));

                    entityManager.persist(unEjecutorConsulta);
                }

            return propuestaNecesidad;

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param propuestaNecesidad
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public PropuestaNecesidad guardar(PropuestaNecesidad propuestaNecesidad) throws JpaDinaeException {
        try {
            if (propuestaNecesidad.getIdPropuestaNecesidad() == null) {
                propuestaNecesidad.setFechaEnvio(new Date());
                entityManager.persist(propuestaNecesidad);
            } else {
                //propuestaNecesidad.setFechaModificacion(new Date());
                entityManager.merge(propuestaNecesidad);
            }
            return propuestaNecesidad;
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param listaPropuestaNecesidad
     * @throws JpaDinaeException
     */
    @Override
    public void guardarListaPropuestaCreaEjecutorNecesidad(List<PropuestaNecesidad> listaPropuestaNecesidad) throws JpaDinaeException {

        try {

            for (PropuestaNecesidad unaPropuestaNecesidad : listaPropuestaNecesidad) {

                guardarPropuestaCreaEjecutorNecesidad(unaPropuestaNecesidad, null, unaPropuestaNecesidad.getUnidadPolicial().getIdUnidadPolicial());

            }

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }

    /**
     *
     * @param listaPropuestaNecesidad
     * @throws JpaDinaeException
     */
    @Override
    public void guardarListaPropuesta(List<PropuestaNecesidad> listaPropuestaNecesidad) throws JpaDinaeException {

        try {

            for (PropuestaNecesidad unaPropuestaNecesidad : listaPropuestaNecesidad) {

                guardar(unaPropuestaNecesidad);

            }

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }

    /**
     *
     * @param listaPropuestaNecesidad
     * @param listaProyectos
     * @throws JpaDinaeException
     */
    @Override
    public void guardarListaPropuestaYgenerarProyecto(List<PropuestaNecesidad> listaPropuestaNecesidad, List<Proyecto> listaProyectos, String nombreReporteUnico, String nombreReporte, Long idPeriodo) throws JpaDinaeException {

        try {

            //ACTUALIZAMOS LAS PROPUESTAS
            for (PropuestaNecesidad unaPropuestaNecesidad : listaPropuestaNecesidad) {

                guardar(unaPropuestaNecesidad);

            }
            //CREAMOS LOS PROYECTO
            for (Proyecto proyecto : listaProyectos) {

                iProyectoLocal.guardarProyecto(proyecto);

            }

            //ACTUALIZAMOS EL PERIODO CON LOS ARCHIVOS GENERADOS
            entityManager.createNamedQuery("Periodo.UpdateArchivosPropuestaNecesidadReporte")
                    .setParameter("nombreArchivoFisicoPropuestaNecesidadReporte", nombreReporte)
                    .setParameter("nombreArchivoOriginalPropuestaNecesidadReporte", nombreReporteUnico)
                    .setParameter("idPeriodo", idPeriodo)
                    .executeUpdate();

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }

    /**
     *
     * @param date
     * @param hour
     * @param minute
     * @param seconds
     * @return
     */
    private Date setTime(Date date, int hour, int minute, int seconds) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     *
     * @param area
     * @param linea
     * @param idUnidadPolicial
     * @param tema
     * @param fechaInicial
     * @param fechaFinal
     * @param listaIdUnidades
     * @return
     * @throws JpaDinaeException
     */
    /*@Override
    public List<PropuestaNecesidad> consultaPropuestaNecesidades1(Long area, Long linea, Long idUnidadPolicial, String tema, Date fechaInicial, Date fechaFinal, List<Long> listaIdUnidades) throws JpaDinaeException {
        try {
            String queryReport = "SELECT p FROM PropuestaNecesidad p ";
            String where = "";
            if (area != null) {
                where += " p.linea.areaCienciaTecnologia.idAreaCienciaTecnologia =  " + area;
            }
            if (idUnidadPolicial != null) {
                if (!where.equals("")) {
                    where += " AND";
                }
                where += " p.unidadPolicial.idUnidadPolicial = " + idUnidadPolicial;
            }
            if (tema != null && !tema.equals("")) {
                if (!where.equals("")) {
                    where += " AND";
                }
                tema = "%" + tema.trim() + "%";
                where += " p.tema LIKE '" + tema + "'";
            }
            if (linea != null) {
                if (!where.equals("")) {
                    where += " AND";
                }
                where += " p.linea.idLinea =  " + linea;
            }
            if (fechaInicial != null && fechaFinal != null) {
                if (!where.equals("")) {
                    where += " AND";
                }
                where += "  P.fechaRegistro BETWEEN :fechaInicial AND :fechaFinal";
            }
            if (listaIdUnidades != null && !listaIdUnidades.isEmpty()) {
                if (!where.equals("")) {
                    where += " AND";
                }
                for (Long idunidades : listaIdUnidades) {
                    where += " P.unidadPolicial.idUnidadPolicial = " + idunidades + " OR";
                }
                where = where.substring(0, where.length() - 2);

            }

            if (where.equals("")) {
                where = " WHERE ";
            } else {
                where = " WHERE " + where + " AND ";
            }
            queryReport += where + " p.constantes.idConstantes =:estado ";
            if (fechaInicial != null && fechaFinal != null) {

                return entityManager.createQuery(queryReport)
                        .setParameter("estado", IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA)
                        .setParameter("fechaInicial", fechaInicial, TemporalType.TIMESTAMP)
                        .setParameter("fechaFinal", fechaFinal, TemporalType.TIMESTAMP)
                        .getResultList();
            } else {
                return entityManager.createQuery(queryReport)
                        .setParameter("estado", IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA)
                        .getResultList();
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }*/
    @Override
    public List<PropuestaNecesidad> consultaPropuestaNecesidades(Long area, Long linea, Long idUnidadPolicial, String tema, Date fechaInicial, Date fechaFinal, List<Long> listaIdUnidades) throws JpaDinaeException {
        try {
            String queryReport = "SELECT p FROM PropuestaNecesidad p ";
            String where = "";
            if (area != null) {
                where += " p.linea.areaCienciaTecnologia.idAreaCienciaTecnologia =  " + area;
            }
            if (idUnidadPolicial != null) {
                if (!where.equals("")) {
                    where += " AND";
                }
                where += " p.unidadPolicial.idUnidadPolicial = " + idUnidadPolicial;
            }
            if (tema != null && !tema.equals("")) {
                if (!where.equals("")) {
                    where += " AND";
                }
                tema = "%" + tema.trim() + "%";
                where += " p.tema LIKE '" + tema + "'";
            }
            if (linea != null) {
                if (!where.equals("")) {
                    where += " AND";
                }
                where += " p.linea.idLinea =  " + linea;
            }
            if (fechaInicial != null && fechaFinal != null) {
                if (!where.equals("")) {
                    where += " AND";
                }
                where += "  P.fechaRegistro BETWEEN :fechaInicial AND :fechaFinal";
            }
            if (listaIdUnidades != null && !listaIdUnidades.isEmpty()) {
                if (!where.equals("")) {
                    where += " AND";
                }
                for (Long idunidades : listaIdUnidades) {
                    where += " P.unidadPolicial.idUnidadPolicial = " + idunidades + " OR";
                }
                where = where.substring(0, where.length() - 2);

            }

            if (where.equals("")) {
                where = " WHERE ";
            } else {
                where = " WHERE " + where + " AND ";
            }
            queryReport += where + " p.constantes.idConstantes =:estado ";
            if (fechaInicial != null && fechaFinal != null) {

                return entityManager.createQuery(queryReport)
                        .setParameter("estado", IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA)
                        .setParameter("fechaInicial", fechaInicial, TemporalType.TIMESTAMP)
                        .setParameter("fechaFinal", fechaFinal, TemporalType.TIMESTAMP)
                        .getResultList();
            } else {
                return entityManager.createQuery(queryReport)
                        .setParameter("estado", IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA)
                        .getResultList();
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param propuestaNecesidades
     * @param identificacion
     * @throws JpaDinaeException
     */
    @Override
    public void enviarPropuestaVicin(List<PropuestaNecesidad> propuestaNecesidades, String identificacion) throws JpaDinaeException {
        try {
            for (PropuestaNecesidad propuestaNecesidad : propuestaNecesidades) {

                propuestaNecesidad.setFechaEnvio(new Date());
                propuestaNecesidad.setIdentificadorUsuarioEnvio(identificacion);
                propuestaNecesidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN));

                //ACTUALIZAMOS EL CAMPO ROL_ACTUAL
                //CON EL OBJETIVO SE SABER EN DONDE SE ENCUENTRA LA PROPUESTA
                //ESTO SE REALIZA PARA CORREGIR 
                //LA INCIDENCIA #0002754: Mientras no se publiquen los resultados de las necesidades, el estado debe ser 'Enviada a VICIN'.
                propuestaNecesidad.setRolActual(IConstantes.PROPUESTA_NECESIDAD_ENVIADA_A_VICIN);
                entityManager.merge(propuestaNecesidad);
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param propuestaNecesidades
     * @param identificacion
     * @param nombreCompleto
     * @throws JpaDinaeException
     */
    @Override
    public void enviarPropuestaGrupoInvestigacion(List<PropuestaNecesidad> propuestaNecesidades, String identificacion, String nombreCompleto) throws JpaDinaeException {
        try {
            for (PropuestaNecesidad propuestaNecesidad : propuestaNecesidades) {

                propuestaNecesidad.setFechaModificacion(new Date());
                propuestaNecesidad.setIdentificadorUsuarioModif(identificacion);
                propuestaNecesidad.setConstantes(new Constantes(propuestaNecesidad.getIdContantes()));

                entityManager.merge(propuestaNecesidad);

                Comentario comentario = new Comentario();
                comentario.setPropuestaNecesidad(propuestaNecesidad);
                comentario.setComentario(propuestaNecesidad.getComentario());
                comentario.setFecha(new Date());
                comentario.setAutor(nombreCompleto);
                comentario.setIdentificacion(identificacion);

                entityManager.persist(comentario);
            }
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoPorUnidadPorEstado(Long idPeriodo, Long idUnidad, Long idEstado) throws JpaDinaeException {
        try {

            return entityManager.createNamedQuery("PropuestaNecesidad.findAllByPeriodoByUnidadByEstado")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idUnidadPolicial", idUnidad)
                    .setParameter("estado", idEstado)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public int getNumeroPropuestaNecesidadPorPeriodoYestado(Long idPeriodo, Long idEstado) throws JpaDinaeException {
        try {

            Object numero = entityManager.createNamedQuery("PropuestaNecesidad.ContarByPeriodoYEstado")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idEstado", idEstado)
                    .getSingleResult();

            if (numero == null) {
                return 0;
            }
            return ((Number) numero).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idPropuestaNecesidad
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public PropuestaNecesidad consultar(Long idPropuestaNecesidad) throws JpaDinaeException {
        try {

            return entityManager.find(PropuestaNecesidad.class, idPropuestaNecesidad);

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public int getNumeroPropuestaNecesidadPorPeriodoYestadoVicinPreaprobadaRevisada(Long idPeriodo, Long idEstadoVicin, Long idEstadoPreaprobada, Long IdEstadoRevisada) throws JpaDinaeException {
        try {

            //:idEstadoVicin,:idEstadoPreaprobada,:idEstadoRevisada
            Object numero = entityManager.createNamedQuery("PropuestaNecesidad.ContarByPeriodoYEstadosVicincAceptadaRevisada")
                    .setParameter("idPeriodo", idPeriodo)
                    .setParameter("idEstadoVicin", idEstadoVicin)
                    .setParameter("idEstadoPreaprobada", idEstadoPreaprobada)
                    .setParameter("idEstadoRevisada", IdEstadoRevisada)
                    .getSingleResult();

            if (numero == null) {
                return 0;
            }
            return ((Number) numero).intValue();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(PropuestaNecesidad pn) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", pn);
        try {
            Query query = entityManager.createNativeQuery("DELETE FROM FUNCIONARIO_NECESIDAD WHERE ID_PROPUESTA_NECESIDAD = " + pn.getIdPropuestaNecesidad());
            query.executeUpdate();
            PropuestaNecesidad propuesta = entityManager.find(PropuestaNecesidad.class, pn.getIdPropuestaNecesidad());
            entityManager.remove(propuesta);
            //entityManager.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(PropuestaNecesidad record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            PropuestaNecesidad propuesta = entityManager.find(PropuestaNecesidad.class, record.getIdPropuestaNecesidad());
            entityManager.merge(propuesta);
            //entityManager.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void guardarPropuestaYgenerarProyecto(PropuestaNecesidad propuestaNecesidad, Proyecto proyecto) throws JpaDinaeException {

        try {
            //ACTUALIZAMOS LA PROPUESTAS

            guardar(propuestaNecesidad);

            //CREAMOS LOS PROYECTO
            iProyectoLocal.guardarProyecto(proyecto);

        } catch (Exception e) {

            throw new JpaDinaeException(e.getMessage(), e);

        }
    }
}
