/*
 * SoftStudio
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.LugarGeografico;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadDependenciaPK;
import co.gov.policia.dinae.siedu.constantes.FunctionEnum;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.dto.PerfilDependenciaDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.DominioTipo;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.Regionales;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.modelo.SieduCompetenciaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoCategoria;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class APPJPAService implements APPService {

    private static final Logger LOG = LoggerFactory.getLogger(APPJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;
    /**
     * lista de los tipos de dominios
     */
    private List<DominioTipo> tiposDominios;
    /**
     * lista de los tipos de modalidades
     */
    private List<Dominio> modalidades;

    /**
     * lista de los tipos de modalidades programación
     */
    private List<Dominio> modalidadesProgramacion;

    /**
     * lista de los tipos de identificaciones
     */
    private List<Dominio> tiposIdentificaciones;
    /**
     * lista de los estados civiles
     */
    private List<Dominio> esatdosCiviles;
    /**
     * lista de los sexos
     */
    private List<Dominio> sexos;
    /**
     * lista de los tipos de documentos
     */
    private List<Dominio> tiposDocumentos;
    /**
     * lista de los procesos
     */
    private List<Dominio> procesos;
    /**
     * lista de las estrategias
     */
    private List<Dominio> estrategias;
    /**
     * lista de las estrategias
     */
    private List<Dominio> tematicas;
    /**
     * lista de los paises
     */
    private List<LugarGeografico> paises;
    /**
     * lista de los paises
     */
    private List<LugarGeografico> municipios;
    /**
     * lista de las unidades de dependencia de la policia nacional
     */
    private List<UnidadDependencia> unidadesDependencias;
    /**
     * listado de los niveles academicos
     */
    private List<NivelesAcademicos> nivelesAcademicos;
    /**
     * listado de categorias
     */
    public List<Categoria> categorias;
    /**
     * listado de comptencias
     */
    private List<SieduCompetencia> competencias;

    @PostConstruct
    public void init() {
        LOG.trace("entró al método: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
        this.loadTiposDominios();
        this.loadModalidades();
        this.loadModalidadesProgramacion();
        this.loadTiposIdentificaciones();
        this.loadEstadosCiviles();
        this.loadProcesos();
        this.loadEstrategias();
        this.loadTematicas();
        this.loadPaises();
        this.loadMunicipios();
        this.loadUnidadesDependencias();
        this.loadNivelesAcademicos();
        this.loadCategorias();
        this.loadCompetencias();
    }

    public void loadTiposDominios() {
        LOG.trace("method: loadTiposDominios()");
        try {
            tiposDominios = sdo.getResultList(em, DominioTipo.class);
        } catch (Exception ex) {
            LOG.error("Error en <<tiposDominios>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    public void loadModalidades() {
        LOG.trace("method: loadModalidades()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.MODALIDAD.getId());
        try {
            modalidades = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("Error en <<loadModalidades>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    public void loadModalidadesProgramacion() {
        LOG.trace("method: loadModalidades()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.MODALIDAD_PROGRAMACION.getId());
        try {
            modalidadesProgramacion = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("Error en <<loadModalidades>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    public void loadTiposIdentificaciones() {
        LOG.trace("method: loadTiposIdentificaciones()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.TIPO_IDENTIFICACION.getId());
        try {
            tiposIdentificaciones = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("Error en <<loadTiposIdentificaciones>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    public void loadEstadosCiviles() {
        LOG.trace("method: loadEstadosCiviles()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.ESTADO_CIVIL.getId());
        try {
            esatdosCiviles = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("Error en <<loadEstadosCiviles>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    public void loadSexos() {
        LOG.trace("method: loadSexos()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.SEXO.getId());
        try {
            sexos = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("Error en <<sexos>> ->> mensaje ->> {}", ex.getMessage());
        }
    }

    @Override
    public List<Dominio> loadTiposDocumentos() throws ServiceException {
        LOG.trace("method: loadTiposDocumentos()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.TIPO_DOCUMENTO.getId());
        try {
            tiposDocumentos = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
            /*tiposDocumentos = em.createNamedQuery(Dominio.FIND_BY_TIPO_AND_VIGENTE, Dominio.class)
                    .setParameter("tipo", params.get("tipo"))
                    .getResultList();*/
        } catch (Exception ex) {
            LOG.error("Error en <<tiposDocumentos>> ->> mensaje ->> {}", ex.getMessage());
        }
        return tiposDocumentos;
    }

    private void loadProcesos() {
        LOG.trace("metodo: loadProcesos()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.PROCESO.getId());
        try {
            procesos = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("metodo: loadProcesos() ->> mensaje: {}", ex.getMessage());
        }
    }

    private void loadEstrategias() {
        LOG.trace("metodo: loadEstrategias()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.ESTRATEGIA.getId());
        try {
            estrategias = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
        }
    }

    private void loadTematicas() {
        LOG.trace("metodo: loadTematicas()");
        Map<String, Object> params = new HashMap<>();
        params.put("tipo", TipoDominioEnum.TEMATICA.getId());
        try {
            tematicas = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
        } catch (Exception ex) {
            LOG.error("metodo: loadTematicas() ->> mensaje: {}", ex.getMessage());
        }
    }

    private void loadPaises() {
        LOG.trace("metodo: loadPaises()");
        try {
            this.paises = new ArrayList<>();
            String nativeQuery = "SELECT L.COD_PAIS, L.DESC_PAIS FROM LUGAR_GEOGRAFICO L GROUP BY L.COD_PAIS, L.DESC_PAIS ORDER BY L.DESC_PAIS";
            List<Object[]> list = sdo.getResultListByNativeQuery(em, nativeQuery);
            if (list != null) {
                for (Object[] object : list) {
                    LugarGeografico pais = new LugarGeografico();
                    pais.setCodPais(((BigDecimal) object[0]).longValue());
                    pais.setDescPais((String) object[1]);
                    paises.add(pais);
                }
            }
            //paises = sdo.getResultListByNamedQuery(em, LugarGeografico.FIND_ALL_PAISES);
        } catch (Exception ex) {
            LOG.error("metodo: loadPaises() ->> mensaje: {}", ex.getMessage());
        }
    }

    private void loadMunicipios() {
        LOG.trace("metodo: loadMunicipios()");
        try {
            municipios = sdo.getResultListByNamedQuery(em, LugarGeografico.FIND_ALL_MUNICIPIOS);
        } catch (Exception ex) {
            LOG.error("metodo: loadMunicipios() ->> mensaje: {}", ex.getMessage());
        }
    }

    @Override
    public List<LugarGeografico> consultarMunicipiosByPais(Long pais) throws ServiceException {
        LOG.trace("metodo: getMunicipiosByPais()");
        if (pais == null) {
            throw new IllegalArgumentException("Error en <<getMunicipiosByPais>> ->> mensaje ->> {El argumento pais es requerido}");
        }
        try {
            String nativeQuery = "SELECT C.COD_DEPTO, C.DESC_DEPTO, C.COD_MUNI, C.DESC_MUNI FROM LUGAR_GEOGRAFICO C WHERE C.COD_PAIS = ? ";
            if (pais == 10) {
                nativeQuery = nativeQuery + "AND C.TIPO IN ('CI', 'CM') ORDER BY C.DESC_DEPTO";
            } else{
                nativeQuery = nativeQuery + "ORDER BY C.DESC_DEPTO";
            }
            List<Object[]> list = sdo.getResultListByNativeQuery(em, nativeQuery, pais);
            List<LugarGeografico> ciudades = null;
            if (list != null) {
                ciudades = new ArrayList<>();
                for (Object[] object : list) {
                    LugarGeografico ciudad = new LugarGeografico();
                    ciudad.setCodDepartamento(((BigDecimal) object[0]).longValue());
                    ciudad.setDescDepartamento((String) object[1]);
                    if (object[2] != null) {
                        ciudad.setCodMunicipio(((BigDecimal) object[2]).longValue());
                        ciudad.setDescMunicipio((String) object[3]);
                    }
                    ciudades.add(ciudad);
                }
            }
            return ciudades;
        } catch (Exception ex) {
            LOG.error("metodo: getMunicipiosByPais() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    /**
     *
     */
    private void loadUnidadesDependencias() {
        LOG.trace("metodo: loadUnidades()");
        try {
            this.unidadesDependencias = sdo.getResultListByNamedQuery(em, UnidadDependencia.FIND_UNIDADES_VIGENTES);
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
        }
    }

    /**
     *
     */
    private void loadNivelesAcademicos() {
        LOG.trace("metodo: loadUnidades()");
        try {
            nivelesAcademicos = sdo.getResultListByNamedQuery(em, NivelesAcademicos.FIND_ALL_ASC);
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
        }
    }

    @Override
    public List<DominioTipo> consultarTiposDominios() throws ServiceException {
        LOG.trace("method: consultarTiposDominios()");
        List<DominioTipo> list;
        try {
            list = sdo.getResultList(em, DominioTipo.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("Error en <<consultarTiposDominios>> ->> mensaje ->> {}",
                    ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<Dominio> consultarDominios(Long tipo) throws ServiceException {
        LOG.trace("method: consultarDominios()");
        if (tipo == null) {
            throw new IllegalArgumentException("Error en <<consultarDominios>> ->> mensaje ->> {El argumento tipo es requerido}");
        }
        List<Dominio> list;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("tipo", tipo);
            list = sdo.getResultListByNamedQuery(em, Dominio.FIND_BY_TIPO_AND_VIGENTE, params);
            em.clear();
        } catch (Exception ex) {
            LOG.error("Error en <<consultarDominios>> ->> mensaje ->> {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<UnidadDependencia> consultarUnidadesVigentes(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: consultarUnidadesDependencia() ->> params: params {}", params);
        List<UnidadDependencia> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT u FROM UnidadDependencia u WHERE 1 = 1 ");
        if (params != null) {
            // recibe com parametro una lista de Long, que representa los tipos por los que se quiere filtrar
            if (params.get("tipos") != null) {
                jpql.append("AND u.tipoUnidadCodigo IN (:tipos) ");
            }
        }
        jpql.append("AND u.vigente = 'SI' ");
        jpql.append("ORDER BY u.descripcionDependencia");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<UnidadDependencia> consultarUnidadesVigentes() throws ServiceException {
        LOG.trace("metodo: consultarUnidadesVigentes()");
        try {
            return sdo.getResultListByNamedQuery(em, UnidadDependencia.FIND_UNIDADES_VIGENTES);
        } catch (Exception ex) {
            LOG.error("metodo: consultarUnidadesVigentes() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<UnidadDependencia> consultarRegionalesVigentes() throws ServiceException {
        LOG.trace("metodo: consultarRegionalesVigentes()");
        try {
            return sdo.getResultListByNamedQuery(em, UnidadDependencia.FIND_REGIONALES_VIGENTES);
        } catch (Exception ex) {
            LOG.error("metodo: consultarRegionalesVigentes() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Regionales> consultarRegionales() throws ServiceException {
        LOG.trace("metodo: consultarEscuelasVigentes()");
        try {
            return sdo.getResultListByNamedQuery(em, Regionales.FIND_ALL);
        } catch (Exception ex) {
            LOG.error("metodo: consultarEscuelasVigentes() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<UnidadDependencia> consultarEscuelasVigentes() throws ServiceException {
        LOG.trace("metodo: consultarEscuelasVigentes()");
        try {
            return sdo.getResultListByNamedQuery(em, UnidadDependencia.FIND_ESCUELAS_VIGENTES);
        } catch (Exception ex) {
            LOG.error("metodo: consultarEscuelasVigentes() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<UnidadDependencia> consultarUnidadesFisicasVigentes(Long regional) throws ServiceException {
        LOG.trace("metodo: consultarUnidadesFisicasVigentes()");
        if (regional == null) {
            throw new IllegalArgumentException("Error en <<consultarUnidadesFisicasVigentes>> ->> mensaje ->> {El argumento regional es requerido}");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("regional", regional);
            return sdo.getResultListByNamedQuery(em, UnidadDependencia.FIND_UNIDADES_FISICAS_VIGENTES_POR_REGIONAL, params);
        } catch (Exception ex) {
            LOG.error("metodo: consultarGradosVigentesPorCategoria() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<UnidadDependencia> consultarDireccionesYAsesorasVigentes() throws ServiceException {
        LOG.trace("metodo: consultarDireccionesYAsesorasVigentes()");
        try {
            return sdo.getResultListByNamedQuery(em, UnidadDependencia.FIND_DIRECCIONES_Y_ASESORAS_VIGENTES);
        } catch (Exception ex) {
            LOG.error("metodo: consultarDireccionesYAsesorasVigentes() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduCompetencia> consultarCompetencias() {
        LOG.trace("metodo: consultarCompetencias()");
        return competencias;
    }

    private void loadCompetencias() {
        LOG.trace("metodo: consultarCompetencias()");
        try {
            competencias = sdo.getResultList(em, SieduCompetencia.class);
        } catch (Exception ex) {
            LOG.error("metodo: consultarCompetencias() ->> mensaje: {}", ex.getMessage());
        }
    }

    @Override
    public List<SieduEventoCategoria> consultarCategoriasByEvento(SieduEvento evento) {
        LOG.trace("method: consultarCategoriasByEvento()");
        List<SieduEventoCategoria> list;
        Map<String, Object> params = new HashMap<>();
        params.put("evento", evento.getId());
        list = sdo.getResultListByNamedQuery(em, SieduEventoCategoria.FIND_BY_EVENTO, params);
        em.clear();
        return list;
    }

    @Override
    public List<SieduCompetenciaEvento> consultarCompetenciasByEvento(SieduEvento evento) {
        LOG.trace("method: consultarCategoriasByEvento()");
        List<SieduCompetenciaEvento> list;
        Map<String, Object> params = new HashMap<>();
        params.put("evento", evento.getId());
        list = sdo.getResultListByNamedQuery(em, SieduCompetenciaEvento.FIND_BY_EVENTO, params);
        em.clear();
        return list;
    }

    @Override
    public List<Grado> consultarGrados() throws ServiceException {
        LOG.trace("metodo: consultarGrados()");
        try {
            return sdo.getResultListByNamedQuery(em, Grado.FIND_GRADOS_VIGENTES);
        } catch (Exception ex) {
            LOG.error("metodo: consultarGrados() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Categoria> consultarCategorias() {
        LOG.trace("metodo: consultarCategorias()");
        return categorias;
    }

    private void loadCategorias() {
        LOG.trace("metodo: loadCategorias()");
        try {
            categorias = sdo.getResultList(em, Categoria.class);
        } catch (Exception ex) {
            LOG.error("metodo: loadCategorias() ->> mensaje: {}", ex.getMessage());
        }
    }

    @Override
    public List<NivelesAcademicos> consultarNivelesAcademicos() throws ServiceException {
        LOG.trace("metodo: consultarNivelesAcademicos() ->> params: params {}");
        try {
            return sdo.getResultListByNamedQuery(em, NivelesAcademicos.FIND_ALL_ASC);
        } catch (Exception ex) {
            LOG.error("metodo: consultarNivelesAcademicos() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Carreras> consultarCarrerasVigentes(Long nivelAcademico) throws ServiceException {
        LOG.trace("metodo: consultarCarrerasVigentes() ->> params: params {}");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("nivelAcademico", nivelAcademico);
            params.put("creadoPor", "USR_EDUC");
            return sdo.getResultListByNamedQuery(em, Carreras.FIND_BY_NIVEL_ACADEMICO_AND_CREADO, params);
        } catch (Exception ex) {
            LOG.error("metodo: consultarCarrerasVigentes() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Grado> consultarGradosVigentesPorCategoria(CategoriaPK pk) throws ServiceException {
        LOG.trace("metodo: consultarGradosVigentesPorCategoria()");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idCategoria", pk);
            return sdo.getResultListByNamedQuery(em, Grado.FIND_VIGENTES_BY_CATEGORY, params);
        } catch (Exception ex) {
            LOG.error("metodo: consultarGradosVigentesPorCategoria() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Grado> consultarGradosVigentesPorCategorias(List<CategoriaPK> pks) throws ServiceException {
        LOG.trace("metodo: consultarGradosVigentesPorCategoria()");
        try {
            Map<Integer, Object> params = new HashMap<>();
            StringBuilder nativeQuery = new StringBuilder("SELECT * FROM USR_REHU.GRADOS WHERE vigente = 'SI' AND (id_categoria, fuerza) in (");
            int i = 0;
            int j = 0;
            int k = 0;
            String prefix = "";
            for (CategoriaPK categoriaPK : pks) {
                nativeQuery.append(prefix);
                prefix = ",";
                j = ++i;
                k = ++i;
                nativeQuery.append("(?").append(j).append(", ?").append(k).append(") ");
                params.put(j, categoriaPK.getIdCategoria());
                params.put(k, categoriaPK.getFuerza());
            }
            nativeQuery.append(") ORDER BY descripcion ASC");
            List<Grado> grados = sdo.getResultListByNativeQuery(em, nativeQuery.toString(), params, Grado.class);
            return grados;
        } catch (Exception ex) {
            LOG.error("metodo: consultarGradosVigentesPorCategoria() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Categoria> consultarCategoriasEvento(Long idEvento) throws ServiceException {
        LOG.trace("method: consultarCategoriasEvento(), params: {}", idEvento);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idEvento", idEvento);
            return sdo.getResultListByNamedQuery(em,
                    SieduEventoCategoria.FIND_CATEGORIES_BY_EVALUATION, params);
        } catch (Exception ex) {
            LOG.error("Error en <<consultarCategoriasEvento>> ->> mensaje ->> {}",
                    ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Carreras> consultarCarrerasVigentesPorCreador(Long nivelAcademico) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Categoria findById(CategoriaPK id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            Categoria i = (Categoria) sdo.find(em, id, Categoria.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public UnidadDependencia findByConsecutivo(UnidadDependenciaPK id) throws ServiceException {
        LOG.debug("metodo: findByConsecutivo() ->> parametros: consecutivo {}", id);
        try {
            UnidadDependencia i = (UnidadDependencia) sdo.find(em, id, UnidadDependencia.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findByConsecutivo() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
    
    @Override
    public Dominio getDescrDominio(Long id) throws ServiceException{
        LOG.debug("método: getDescrDominio() ->> parámetros: id{}", id);
        try{
            Dominio d = (Dominio) sdo.find(em, id, Dominio.class);
            em.clear();
            return d;
        } catch (Exception ex) {
            LOG.error("metodo: getDescrDominio() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }    

    @Override
    public List<DominioTipo> getTiposDominios() {
        return tiposDominios;
    }

    @Override
    public List<Dominio> getModalidades() {
        return modalidades;
    }

    @Override
    public List<Dominio> getModalidadesProgramacion() {
        return modalidadesProgramacion;
    }

    @Override
    public List<Dominio> getTiposIdentificaciones() {
        return tiposIdentificaciones;
    }

    @Override
    public List<Dominio> getEsatdosCiviles() {
        return esatdosCiviles;
    }

    @Override
    public List<Dominio> getSexos() {
        return sexos;
    }

    @Override
    public List<Dominio> getTiposDocumentos() {
        return tiposDocumentos;
    }

    @Override
    public List<Dominio> getProcesos() {
        return procesos;
    }

    @Override
    public List<Dominio> getEstrategias() {
        return estrategias;
    }

    @Override
    public List<Dominio> getTematicas() {
        return tematicas;
    }

    @Override
    public List<LugarGeografico> getPaises() {
        return paises;
    }

    @Override
    public List<LugarGeografico> getMunicipios() {
        return municipios;
    }

    @Override
    public List<UnidadDependencia> getUnidadesDependencias() {
        return unidadesDependencias;
    }

    @Override
    public List<NivelesAcademicos> getNivelesAcademicos() {
        return nivelesAcademicos;
    }

    @Override
    public PerfilDependenciaDTO getUnidadDependenciaFromUser(String identificacion) throws ServiceException {
        LOG.debug("metodo: getUnidadDependenciaFromUser() ->> parametros: identificacion {}", identificacion);
        try {
//      Connection connection = this.datasource.getConnection();
//      StringBuilder sb = new StringBuilder();
//      sb.append("call ");
//      sb.append(FunctionEnum.FUNCTION_VALIDAR_DEPENDENCIA_ROL.getNombreSP());
//      sb.append("(?, ?)");
//      CallableStatement cs = connection.prepareCall(sb.toString());
//      cs.setString(1, identificacion);
//      cs.registerOutParameter(2, Types.VARCHAR);
//      cs.executeQuery();
//      String unidadDependencia = cs.getString(2);
            Long identificacionNew = Long.parseLong(identificacion);
            PerfilDependenciaDTO perfilDependenciaDto = new PerfilDependenciaDTO();
            Object[] perfilUsuario;
            perfilUsuario = (Object[]) em.createNativeQuery("SELECT VALIDAR_DEPENDENCIA_ROL_MAP(?1) FROM DUAL")
                    .setParameter(1, identificacionNew)
                    .getSingleResult();
            perfilDependenciaDto.setRolDepenencia((String) perfilUsuario[0]);
            BigDecimal bg1 = (BigDecimal) perfilUsuario[1];
            Long unidad = bg1.longValue();
            perfilDependenciaDto.setUnidadDependencia(unidad);
            if (perfilDependenciaDto.getRolDepenencia() != null && perfilDependenciaDto.getUnidadDependencia() != null) {
                return perfilDependenciaDto;
            } else {
                throw new ServiceException("Ocurrio un problema al momento de ejecutar la funcion" + FunctionEnum.FUNCTION_VALIDAR_DEPENDENCIA_ROL.getNombreSP());
            }
        } catch (Exception ex) {
            LOG.error("metodo: getUnidadDependenciaFromUser() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
