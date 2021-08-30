package co.gov.policia.dinae.manager.managedBean;

import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.MaquinaDTO;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUsuarioLocal;
import co.gov.policia.dinae.siedu.dto.PerfilDependenciaDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "loginFaces")
@javax.enterprise.context.SessionScoped
public class LoginFaces extends JSFUtils implements Serializable {

    //VARIABLES PARA CONTROLAR QUE SE MUESTRA Y QUE NO EN LAS PAGINAS DE 
    //inicio.xhtml, welcome.xhtml y login.xhtml
    private boolean mostrarImagenMenuArriba = true;
    private boolean mostrarMenu = false;
    //VARIABLE PARA LA ADMINISTRACION DE LA SESSION
    private boolean loggedIn = false;
    private String urlPaginaAntesLogin;
    private PerfilUsuarioDTO perfilUsuarioDTO;
    private PerfilDependenciaDTO perfilDependenciaDTO;
    @EJB
    private IUsuarioLocal iUsuarioLocal;
    @EJB
    private APPService appService;
    @javax.inject.Inject
    private MenuFaces menuFaces;

    public LoginFaces() {
        loggedIn = false;
    }

    @javax.annotation.PostConstruct
    public void init() {
    }

    /**
     *
     * @param identificacion
     * @param nombreUsuario
     * @return
     * @throws RuntimeException
     */
    public boolean cargarRoles(String identificacion, String nombreUsuario) throws RuntimeException {
        try {
            loggedIn = false;
            perfilUsuarioDTO = iUsuarioLocal.getPerfilUsuario(identificacion, nombreUsuario);
            adicionaMensajeInformativo("Login OK! WELCOME: ".concat(identificacion));
            //OCULTAMOS LAS IMAGENES DE CEBECERA PRESENTACION
            mostrarImagenMenuArriba = false;
            mostrarMenu = true;
            loggedIn = true;
            //CARGAMOS LAS PAGINAS A LAS QUE TIENE ACCESO EL USUARIO
            List<RolUsuarioDTO> listaRolesUsuarioLogueado = perfilUsuarioDTO.getListaRolUsuarioDTO();
            perfilUsuarioDTO.getListaPaginasAccesos().clear();
            //CUALQUIER USUARIO LOGUEADO
            //BEAN:#{cuPr09ConsultarProyectosDeInvestigacion.initReturnCU}
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_09/consultarProyectosDeInvestigacion.xhtml");
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_09/include_resenia_investigacion.xhtml");
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/desarrollo/filtroDesarrolloEvaluacion.xhtml");
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/desarrollo/desarrolloEvaluacion.xhtml");
            //BEAN:#{cuCo7ConvocatoriasEnsayoCriticoFaces.initReturnCU}
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_7/gestionarConvocatoriasEnsayoCritico.xhtml");
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_7/ingresarEnsayoCritico.xhtml");
            perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_7/listadoConvocatoriasEnsayoCritico.xhtml");
            // METODO QUE CONSULTA LA UNIDAD DE DEPENDENCIA DE UN USUARIO POR EL NUMERO DE CÉDULA.
            // ESTA UNIDAD SE USA PARA VALIDAR LOS PERMISOS SOBRE FUNCIONALIDADES DESARROLLADAS EN LA LA VERSION 2.1
            try {
                perfilDependenciaDTO = appService.getUnidadDependenciaFromUser(identificacion);
                perfilUsuarioDTO.setUnidadDependencia(perfilDependenciaDTO.getUnidadDependencia());
                perfilUsuarioDTO.setRolDepenencia(perfilDependenciaDTO.getRolDepenencia());
                adicionaMensajeInformativo("Unidad Dependencia cargada: ".concat(perfilUsuarioDTO.getRolDepenencia()));
            } catch (ServiceException ex) {
                adicionaMensajeError("Error al cargar la unidad dependencia con la que se validan los permisos: ".concat(ex.getMessage()));
            }
            if (listaRolesUsuarioLogueado != null) {
                for (RolUsuarioDTO unRolUsuarioDTO : listaRolesUsuarioLogueado) {
                    if (IConstantesRole.ADMINISTRADOR_DE_INVESTIGACION_SIGAC_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        continue;
                    }
                    if (IConstantesRole.ADMINISTRADOR_DE_PARAMETROS_DEL_SISTEMA.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuAd03AdminCriteriosEvaluacion.initReturnCU}
                        urlCuAd03AdminCriteriosEvaluacion();
                        //BEAN:#{cuAdCorreoFaces.initReturnCU}
                        urlCuAdCorreoFaces();
                        //BEAN:#{cuAd02AdministrarParametrosSistemaFaces.initReturnCU}
                        urlCuAd02ParametrosSistema();
                        urlCargueArchivos();
                        urlAdministraUnidades();
                        continue;
                    }
                    if (IConstantesRole.ADMINISTRADOR_DE_USUARIOS_DEL_SISTEMA.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuAd01TipoUsuarioAdministrador.initReturnCU}
                        urlCuAd01TipoUsuarioAdministrador();
                        continue;
                    }
                    if (IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuCo2ParticipaConvocatoriasFaces.initReturnCU}
                        urlCuCo2ParticipaConvocatoriasFaces();
                        //BEAN:#{cuIv01GestionarInvestigadoresFaces.initReturnCU}
                        urlCuIv01GestionarInvestigadoresFaces();
                        //
                        urlCuPr1ProyectoFaces();
                        urlCuPr05RegistrarPresupuestoFaces();
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        urlCuCo2ParticipaConvocatoriasFaces();
                        continue;
                    }
                    if (IConstantesRole.AUTORIZADO_PARA_REGISTRO_DE_NECESIDADES_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuNe2GestionaBloquePropuestasNecesidadesFaces.initReturnCU}
                        urlCuNe2GestionaBloquePropuestasNecesidadesFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_INVESTIGADORES_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuIv01GestionarInvestigadoresFaces.initReturnCU}
                        urlCuIv01GestionarInvestigadoresFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_CONVOCATORIAS_EN_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuCo1GestionarConvocatoriasFaces.initReturnCU}
                        urlCuCo1GestionarConvocatoriasFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_ENSAYOS_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuCo8EvaluacionEnsayoCriticoFaces.initReturnCU}
                        urlCuCo8EvaluacionEnsayoCriticoFaces();
                        urlCuCo9EvaluacionEnsayoCriticoFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuCo8EvaluacionEnsayoCriticoFaces.initReturnCU}
                        urlCuCo8EvaluacionEnsayoCriticoFaces();
                        urlCuCo9EvaluacionEnsayoCriticoFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_EVENTOS_EN_LA_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr18IngresaModificarEventoInvestigacionFaces.initReturnCU}
                        urlCuPr18IngresaModificarEventoInvestigacionFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_GESTION_DE_COMPROMISOS_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_HABILITAR_IMPLEMENTACION_DE_PROYECTOS_EN_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr13HabilitarImplementacion.initReturnCU}
                        urlCuPr13HabilitarImplementacion();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_LA_EVALUACION_DE_LOS_PROYECTOS_DE_INVESTIGACION_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr12EvaluacionProyectosInvestigacionFaces.initReturnCU}
                        urlCuPr12EvaluacionProyectosInvestigacionFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_PERIODOS_DE_PROPUESTAS_DE_NECESIDADES_EN_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuNe1PeriodoNecesidadesFaces.initReturnCU}
                        urlCuNe1PeriodoNecesidadesFaces();
                        urlCuNe2GestionaBloquePropuestasNecesidadesFaces();
                        urlCuNe6EvaluarPropuestasNecesidadesInvestigacionFaces();
                        urlCuMe01InvestigacionUnidad();
                        urlCuMe02CrearPropuestaBanco();
                        urlCuMe03AsignarInvestigacionBancoUnidad();
                        urlCuMe04AprobarPropuestaBanco();
                        urlCuMe06AprobarInvestigacionUnidadVicin();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr24RevisarCompromisoEnviadosPorJefeUnidad.initReturnCU}
                        urlCuPr24RevisarCompromisoEnviadosPorJefeUnidad();
                        urlCuPr11ConsultarDetalleCompromisoFaces();
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        urlCuPr7RegistrarAvanceInvestigacionFaces();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_INSTITUCIONALES_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr24RevisarCompromisoEnviadosPorJefeUnidad.initReturnCU}
                        urlCuPr24RevisarCompromisoEnviadosPorJefeUnidad();
                        //BEAN:#{CuPr15_1_2_AvanceImplemenacionFaces}
                        urlCuPr15_1_2_AvanceImplemenacionFaces();
                        //BEAN:#{CuPr21RegistrarPlanDeTrabajoFaces}
                        urlCuPr21RegistrarPlanDeTrabajoFaces();
                        //BEAN:#{CuPr11ConsultarDetalleCompromisoFaces}
                        urlCuPr11ConsultarDetalleCompromisoFaces();
                        //BEAN:#{CuPr6ConsultarDetalleProyectoInvestigacion                        
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        //VERSIONES
                        urlVersiones();
                        //CU-PR-07
                        urlCuPr7RegistrarAvanceInvestigacionFaces();
                        //CU-PR-05
                        urlCuPr05RegistrarPresupuestoFaces();
                        //BEAN: CuPr14AvancePresupuestoProyecto
                        urlCuPr14AvancePresupuestoProyecto();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_DE_SEMILLEROS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuIv3GestionarSemillerosInvestigacionFaces.initReturnCU}
                        urlCuIv3GestionarSemillerosInvestigacionFaces();
                        continue;
                    }
                    if (IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_CONVOCATORIAS_EN_LA_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuCo1GestionarConvocatoriasFaces.initReturnCU}
                        urlCuCo1GestionarConvocatoriasFaces();
                        urlCuCo04EvaluarPropuestaConvocatoria();
                        urlCuPr22Comentarios();
                        urlCuPr23GestionarCompromisoProyectos();
                        continue;
                    }
                    if (IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuNe1PeriodoNecesidadesFaces.initReturnCU}
                        urlCuNe1PeriodoNecesidadesFaces();
                        //BEAN:#{cuPr23GestionarCompromisoProyectos.initReturnCU}
                        urlCuPr23GestionarCompromisoProyectos();
                        //BEAN:#{CuNe6EvaluarPropuestasNecesidadesInvestigacionFaces}
                        urlCuNe6EvaluarPropuestasNecesidadesInvestigacionFaces();
                        continue;
                    }
                    if (IConstantesRole.GENERADOR_DE_CONSULTA_DE_CONVOCATORIAS.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuCo06DetalleConvocatoria();
                        urlCuCo05ConsultaPropuestaConvocatoria();
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        continue;
                    }
                    if (IConstantesRole.GENERADOR_DE_CONSULTA_DE_INVESTIGADORES.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuIv02ConsultarInvestigadores.initReturnCU}
                        urlCuIv02ConsultarInvestigadores();
                        continue;
                    }
                    if (IConstantesRole.GENERADOR_DE_CONSULTA_DE_SEMILLEROS.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuIv4ConsultarDetalleSemilleroFaces.initReturnCU}
                        urlCuIv4ConsultarDetalleSemilleroFaces();
                        continue;
                    }
                    if (IConstantesRole.GENERADOR_DE_CONSULTAS.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuTr01IngresarModificarTrabajoDeGradoFaces.initReturnCU}
                        urlCuTr01IngresarModificarTrabajoDeGradoFaces();
                        //BEAN:#{cuPr08ConsultarTrabajosYProyectos.initReturnCU}
                        urlCuPr08ConsultarTrabajosYProyectos();
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        urlCuCo06DetalleConvocatoria();
                        continue;
                    }
                    if (IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuNe2GestionaBloquePropuestasNecesidadesFaces.initReturnCU}
                        urlCuNe2GestionaBloquePropuestasNecesidadesFaces();
                        //BEAN:#{cuCo2ParticipaConvocatoriasFaces.initReturnCU}
                        urlCuCo2ParticipaConvocatoriasFaces();
                        //BEAN:#{cuPr25RevisarCompromisoJefeUnidad.initReturnCU}
                        urlCuPr25RevisarCompromisoJefeUnidad();
                        //BEAN:cuNe5JefeLoteNecesidadesFaces
                        urlCuNe5JefeLoteNecesidadesFaces();
                        //BEAN: CuNe7PropuestaNecesidadFaces
                        urlCuNe7PropuestaNecesidadFaces();
                        //BEAN: CuPr11ConsultarDetalleCompromisoFaces
                        urlCuPr11ConsultarDetalleCompromisoFaces();
                        //BEAN: CuPr21RegistrarPlanDeTrabajoFaces
                        urlCuPr21RegistrarPlanDeTrabajoFaces();
                        //BEAN: CuPr15_1_2_AvanceImplemenacionFaces
                        urlCuPr15_1_2_AvanceImplemenacionFaces();
                        //CU-PR-06
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        //VERSIONES
                        urlVersiones();
                        //CU-PR-07
                        urlCuPr7RegistrarAvanceInvestigacionFaces();
                        //CU-PR-05
                        urlCuPr05RegistrarPresupuestoFaces();
                        //CU-PR-14
                        urlCuPr14AvancePresupuestoProyecto();
                        //CU-CO-08
                        urlCuCo8EvaluacionEnsayoCriticoFaces();
                        urlCuCo9EvaluacionEnsayoCriticoFaces();
                        urlCuCo06DetalleConvocatoria();
                        //
                        urlCuPr1ProyectoFaces();
                        urlCuCo03RevisarPropuesta();
                        continue;
                    }
                    if (IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD.equals(unRolUsuarioDTO.getIdRol())) {
                        //CU-CO-08
                        urlCuCo8EvaluacionEnsayoCriticoFaces();
                        urlCuCo9EvaluacionEnsayoCriticoFaces();
                        urlCuCo06DetalleConvocatoria();
                        urlCuMe02CrearPropuestaBanco();
                        urlCuMe05InsertarInvestigacionUnidad();
                        continue;
                    }
                    if (IConstantesRole.RESPONSABLE_DE_LA_IMPLEMENTACION_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr20GestionImplementacionesVigentesAsignadasFaces.initReturnCU}
                        urlCuPr20GestionImplementacionesVigentesAsignadasFaces();
                        //CU-PR-21
                        urlCuPr21RegistrarPlanDeTrabajoFaces();
                        //CU-PR-15
                        urlCuPr15_1_2_AvanceImplemenacionFaces();
                        //BEAN: CuPr05RegistrarPresupuestoImplementacion
                        urlCuPr05RegistrarPresupuestoFaces();
                        continue;
                    }
                    if (IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr19ProyectosVigentesAsignadosFaces.initReturnCU}
                        urlCuPr19ProyectosVigentesAsignadosFaces();
                        //BEAN:#{cuIv01GestionarInvestigadoresFaces.initReturnCU}
                        urlCuIv01GestionarInvestigadoresFaces();
                        //CU-PR-06
                        urlCuPr6ConsultarDetalleProyectoInvestigacion();
                        //VERSIONES
                        urlVersiones();
                        //CU-PR-07
                        urlCuPr7RegistrarAvanceInvestigacionFaces();
                        //CU-PR-05
                        urlCuPr05RegistrarPresupuestoFaces();
                        //CU-PR-01
                        urlCuPr1ProyectoFaces();
                        //CU-PR-05                        
                        urlCuPr05RegistrarPresupuestoFaces();
                        //CU-PR-07
                        urlCuPr7RegistrarAvanceInvestigacionFaces();
                        //CU-PR-10
                        urlCuPr10RegistroInformeFinal();
                        //CU-PR-10
                        urlCuPr14AvancePresupuestoProyecto();
                        urlCuPr11ConsultarDetalleCompromisoFaces();
                        urlCuAPruebasFaces();
                        continue;
                    }
                    if (IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuTr01IngresarModificarTrabajoDeGradoFaces.initReturnCU}
                        urlCuTr01IngresarModificarTrabajoDeGradoFaces();
                        continue;
                    }
                    if (IConstantesRole.USUARIO_DEL_SISTEMA.equals(unRolUsuarioDTO.getIdRol())) {
                        continue;
                    }
                    if (IConstantesRole.INVESTIGADOR_PRINCIPAL.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuTr01IngresarModificarTrabajoDeGradoFaces.initReturnCU}
                        urlCuTr01IngresarModificarTrabajoDeGradoFaces();
                        continue;
                    }
                    if (IConstantesRole.AUTORIZADO_PARA_REALIZAR_ASESORIAS_EN_LA_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        //BEAN:#{cuPr17AgregarAsesoriaProyectoInvestigacionFaces.initReturnCU}
                        urlCuPr17AgregarAsesoriaProyectoInvestigacionFaces();
                        continue;
                    }
                    if (IConstantesRole.INVESTIGADOR_PRINCIPAL_TRABAJO_GRADO.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuTr01IngresarModificarTrabajoDeGradoFaces();
                        continue;
                    }
                    if (IConstantesRole.INVESTIGADORES_RESPONSABLES_INVESTIGACION_EXTERNA.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe09InvestigacionesExternasUnidad();
                        continue;
                    }
                    if (IConstantesRole.DIRECTOR_GRUPO_INVESTIGACION.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe09InvestigacionesExternasUnidad();
                        continue;
                    }
                    if (IConstantesRole.VICERRECTORIA_INVESTIGACION_VICIN.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe07PropiedadIntelectual();
                        urlCuMe08InvestigacionesExternas();
                        urlCuMe10EspaciosEquiposElementosInvestigacion();
                        urlCuMe11InsercionNotas();
                        continue;
                    }
                    if (IConstantesRole.USUARIO_UNIDAD.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe02CrearPropuestaBanco();
                        urlCuMe04AprobarPropuestaBanco();
                        urlCuMe05InsertarInvestigacionUnidad();
                        urlCuMe09InvestigacionesExternasUnidad();
                        urlCuMe10EspaciosEquiposElementosInvestigacion();
                        continue;
                    }
                    if (IConstantesRole.ENCARGADO_GENERACION_ARCHIVO_SNIES.equals(unRolUsuarioDTO.getIdRol())) {

                        //BEAN:#{cuPr16EncargadoGeneracionArchivosSniesFaces.initReturnCU}
                        urlCuPr16EncargadoGeneracionArchivosSniesFaces();
                    }
                    //Roles SECAD
                    if (IConstantesRole.SECRETARIA_ACADEMICA.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe11InsercionNotas();
                        continue;
                    }
                    if (IConstantesRole.DOCENTE.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe11InsercionNotas();
                        continue;
                    }
                    if (IConstantesRole.REGISTRO_CONTROL.equals(unRolUsuarioDTO.getIdRol())) {
                        urlCuMe11InsercionNotas();
                        continue;
                    }
                    //Roles VIECO
                    if (IConstantesRole.ADMINISTRADOR_SISTEMA.equals(unRolUsuarioDTO.getIdRol())) {
                        urlAdministracion();
                        continue;
                    }
                    if (IConstantesRole.PARAMETRIZADOR_PAE.equals(unRolUsuarioDTO.getIdRol())) {
                        urlParametrizarPae();
                        continue;
                    }
                    if (IConstantesRole.PARAMETRIZADOR_PROGRAMA_EDUCACION_CONTINUA.equals(unRolUsuarioDTO.getIdRol())) {
                        urlParametrizarProgramasEducacionContinua();
                        continue;
                    }
                    if (IConstantesRole.PARAMETRIZADOR_EVALUACION_EDUCACION_CONTINUA.equals(unRolUsuarioDTO.getIdRol())) {
                        urlParametrizarEvaluacionEducacionContinua();
                        continue;
                    }
                    if (IConstantesRole.PARAMETRIZADOR_DESARROLLO_PAE.equals(unRolUsuarioDTO.getIdRol())) {
                        urlDesarrolloPae();
                        continue;
                    }
                    if (IConstantesRole.SEGUIMIENTO_ESTRATEGIA.equals(unRolUsuarioDTO.getIdRol())) {
                        urlSeguimientoEstrategia();
                        continue;
                    }
                }
            }
            return true;
        } catch (JpaDinaeException ex) {
            loggedIn = false;
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LOGIN", ex.getCause());
            adicionaMensajeError("Login error! ".concat(ex.getMessage() == null ? "" : ex.getMessage()));
            if (ex.getCause() instanceof RuntimeException) {
                throw new RuntimeException(ex.getCause().getMessage());
            }
            return false;
        } catch (Exception e) {
            loggedIn = false;
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LOGIN", e);
            adicionaMensajeError("Login error! ".concat(e.getMessage() == null ? "" : e.getMessage()));
            return false;
        }
    }

    public void cargarUnidadDependencia(String identificacion) {

    }

    /**
     * Logout.
     *
     * @return
     */
    public String logout() {
        try {
            loggedIn = false;
            menuFaces.inicializaMenu(null, false);
            perfilUsuarioDTO = null;
            //SE INVALIDA LA SESSION
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(false);
            session.invalidate();
            //MOSTRAMOS LAS IMAGENES DE CEBECERA PRESENTACION
            mostrarImagenMenuArriba = false;
            mostrarMenu = false;
            //adicionaMensajeInformativo("Logout OK!");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LOGOUT", e);
        }
        return navigationFaces.redirectToWelcome();
    }

    /**
     * Logout.
     *
     * @return
     */
    public String loginSessionTimeout() {
        try {
            logout();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LOGOUT BY SESSION TIMEOUT", e);
        }
        return navigationFaces.redirectToLogin();
    }

    /**
     *
     * @return
     */
    public StreamedContent getDownloadContentPropertyDocumentoAyuda() {
        try {
            String rutaArchivoAyuda = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + "ManualUsuarioProyectoDinae.pdf";
            File archivoAyuda = new File(rutaArchivoAyuda);
            if (!archivoAyuda.exists()) {
                adicionaMensajeError("El archivo de ayuda no existe..");
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ARCHIVO DE AYUDA NO EXISTE: ".concat(rutaArchivoAyuda));
                return null;
            }
            InputStream stream = new FileInputStream(rutaArchivoAyuda);
            String contentType = "application/octet-stream";
            StreamedContent download = new DefaultStreamedContent(stream, contentType, "ManualUsuarioProyectoDinae.pdf");
            return download;
        } catch (Exception e) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LoginFaces (getDownloadContentPropertyDocumentoAyuda)", e);
        }
        return null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUrlPaginaAntesLogin() {
        return urlPaginaAntesLogin;
    }

    public void setUrlPaginaAntesLogin(String urlPaginaAntesLogin) {
        this.urlPaginaAntesLogin = urlPaginaAntesLogin;
    }

    public PerfilUsuarioDTO getPerfilUsuarioDTO() {
        //SI EXISTE UN USUARIO LOGUEADO Y EL PERFIL DE LA MAQUINA NO SE HA CARGADO
        if (isLoggedIn() && perfilUsuarioDTO.getMaquinaDTO() == null) {
            //CARGAMOS LOS DATOS DE LA MAQUINA DESDE DONDE ESTA LOGUEADO EL USUARIO
            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                if (facesContext != null) {
                    HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                    if (httpServletRequest != null) {
                        //MaquinaDTO maquinaDTO = new MaquinaDTO( httpServletRequest.getRemoteAddr() );
                        MaquinaDTO maquinaDTO = new MaquinaDTO(httpServletRequest.getRemoteHost());
                        perfilUsuarioDTO.setMaquinaDTO(maquinaDTO);
                    } else {
                        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "NO HAY CONTEXT PARA CARGAR MAQUINA");
                    }
                } else {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "NO HAY CONTEXT PARA CARGAR MAQUINA");
                }
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROORR USUARIO LOGUEADO - NO HAY CONTEXT ", e);
            }
        }
        return perfilUsuarioDTO;
    }

    public String getInformacionPantallaLogin() {
        if (perfilUsuarioDTO == null) {
            return "";
        }
        try {
            String codigoUnidad = perfilUsuarioDTO.getUnidadPolicial() == null
                    || perfilUsuarioDTO.getUnidadPolicial().getSiglaFisicaUnidad() == null ? ""
                            : perfilUsuarioDTO.getUnidadPolicial().getSiglaFisicaUnidad();
            String nombreUnidad = perfilUsuarioDTO.getUnidadPolicial() == null ? ""
                    : perfilUsuarioDTO.getUnidadPolicial().getNombre();
            return perfilUsuarioDTO.getUsuarioLogueado().concat(" - ")
                    .concat(perfilUsuarioDTO.getNombreCompleto())
                    .concat(" - ")
                    .concat(codigoUnidad)
                    .concat(" - ")
                    .concat(nombreUnidad);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROORR..", e);
            return perfilUsuarioDTO.getUsuarioLogueado();
        }
    }

    public boolean isMostrarImagenMenuArriba() {
        return mostrarImagenMenuArriba;
    }

    public void setMostrarImagenMenuArriba(boolean mostrarImagenMenuArriba) {
        this.mostrarImagenMenuArriba = mostrarImagenMenuArriba;
    }

    public boolean isMostrarMenu() {
        return mostrarMenu;
    }

    public void setMostrarMenu(boolean mostrarMenu) {
        this.mostrarMenu = mostrarMenu;
    }

    private void urlCuNe2GestionaBloquePropuestasNecesidadesFaces() {
        //BEAN:#{cuNe2GestionaBloquePropuestasNecesidadesFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_2/gestiona_bloque_propuestas_necesidades.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_2/include_detalle_propuesta.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_2/include_lista_comentarios.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_3/include_registro_propuesta.xhtml");
    }

    private void urlCuCo2ParticipaConvocatoriasFaces() {
        //BEAN:#{cuCo2ParticipaConvocatoriasFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_2/gestiona_bloque_participa_convocatoria_financia.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_2/include_lista_comentarios.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_2/include_participar_convocatoria.xhtml");
    }

    private void urlCuPr25RevisarCompromisoJefeUnidad() {
        //BEAN:#{cuPr25RevisarCompromisoJefeUnidad.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_25/revisar_compromiso_jefe_unidad.xhtml");
    }

    private void urlCuAd03AdminCriteriosEvaluacion() {
        //BEAN:#{cuAd03AdminCriteriosEvaluacion.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ad_03/adminCriteriosEvaluacion.xhtml");
    }

    private void urlCuAdCorreoFaces() {
        //BEAN:#{cuAdCorreoFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ad_correo/gestiona_plantilla_correo.xhtml");
    }

    private void urlAdministracion() {
        //Administracion
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/auditoria.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/tiposdominios.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/tipodominio.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/dominio.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_07/propiedad_intelectual.xhtml");
        //PAE
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/pae.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/cobertura.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/necesidad.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/capacitacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/formacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/formaciones.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/entidad.xhtml");
        //Desarrollo PAE
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/buscar_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/convocatoria_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/certificacion_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/desarrollo_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/docente_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/inasistencia_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/participante_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/avance_desarrollo_pae.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/capacitacion_funcionario.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/consultas/consulta_horas_docente.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/horas_docente.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/reporte_general_capacitacion.xhtml?faces-redirect=true");
        //Evaluacion
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/buscarEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/detalleEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/evaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/nuevaVersionEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/parametrosEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/preguntaEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/desarrollo/filtroDesarrolloEvaluacion.xhtml");

        //Personal externo
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/personalexterno/personalexterno.xhtml");
        //Presupuesto    
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/presupuesto/presupuesto.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/presupuesto/presupuestos.xhtml");
        //Programa
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/programa/programa.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/programa/programas.xhtml");
    }

    private void urlParametrizarPae() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/pae.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/cobertura.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/necesidad.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/capacitacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/formacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/formaciones.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/personalexterno/personalexterno.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/horas_docente.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/reporte_general_capacitacion.xhtml?faces-redirect=true");
    }

    private void urlParametrizarProgramasEducacionContinua() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/programa/programa.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/programa/programas.xhtml");
    }

    private void urlParametrizarEvaluacionEducacionContinua() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/parametrosEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/buscarEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/desarrollo/filtroDesarrolloEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/evaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/preguntaEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/detalleEvaluacion.xhtml");
    }

    private void urlDesarrolloPae() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/buscar_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/convocatoria_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/certificacion_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/desarrollo_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/docente_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/inasistencia_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/participante_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/presupuesto/presupuesto.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/presupuesto/presupuestos.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/avance_desarrollo_pae.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/capacitacion_funcionario.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/horas_docente.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/reporte_general_capacitacion.xhtml?faces-redirect=true");
    }

    private void urlSeguimientoEstrategia() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/buscar_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/convocatoria_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/desarrollo_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/docente_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/inasistencia_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/participante_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/avance_desarrollo_pae.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/capacitacion_funcionario.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/horas_docente.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/reporte_general_capacitacion.xhtml?faces-redirect=true");
    }

    private void urlCuAPruebasFaces() {
        //BEAN:#{cuAdCorreoFaces.initReturnCU}
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_vieco/cu_consultaEvento.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_vieco/include_consulta_evento.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_vieco/gestionar_evento_escuela.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_vieco/registra_evento_escuela.xhtml");
        //Administracion
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/auditoria.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/tiposdominios.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/tipodominio.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/admin/dominiotipo.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/admin/dominio.xhtml");
        //PAE
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/pae.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/cobertura.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/necesidad.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/capacitacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/formacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/pae/formaciones.xhtml");
        //Desarrollo PAE
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/buscar_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/convocatoria_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/certificacion_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/desarrollo_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/docente_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/inasistencia_evento_escuela.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/desarrolloPae/participante_evento_escuela.xhtml");
        //Evaluacion
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/parametrosEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/buscarEvaluacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/evaluacion/desarrollo/filtroDesarrolloEvaluacion.xhtml");
        //Personal externo
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/personalexterno/personalexterno.xhtml");
        //Presupuesto    
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/presupuesto/presupuesto.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/presupuesto/presupuestos.xhtml");
        //Programa
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/programa/programa.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/programa/programas.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/programa/programaTema.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/evaluacion/buscarEvaluacion.xhtml");
        //perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/evaluacion/parametrosEvaluacion.xhtml");
    }

    private void urlAdministraUnidades() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/unidad/administra_unidad.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/unidad/actualiza_unidad.xhtml");
    }

    private void urlCargueArchivos() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/archivo/cargue.xhtml");
    }

    private void urlCuAd02ParametrosSistema() {
        //BEAN:#{cuAd02AdministrarParametrosSistemaFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ad_02/administrarParametrosSistema.xhtml");
    }

    public void urlCuAd01TipoUsuarioAdministrador() {
        //BEAN:#{cuAd01TipoUsuarioAdministrador.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ad_01/adminUsuariosPerfiles.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ad_01/crearModificarRolesUsuarios.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ad_01/modificarRolUsuarioAdmin.xhtml");
    }

    private void urlCuIv01GestionarInvestigadoresFaces() {
        //BEAN:#{cuIv01GestionarInvestigadoresFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_01/gestionarInvestigador.xhtml");
    }

    private void urlCuCo1GestionarConvocatoriasFaces() {
        //BEAN:#{cuCo1GestionarConvocatoriasFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_1/gestiona_bloque_convocatoria.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_1/include_registra_convocatoria.xhtml");
    }

    private void urlCuCo8EvaluacionEnsayoCriticoFaces() {
        //BEAN:#{cuCo8EvaluacionEnsayoCriticoFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_8/evaluarEnsayoCriticoCriterios.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_8/gestionarConvocatoriasEnsayoCritico.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_8/gestionarEvaluacionEnsayoCritico.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_8/seleccionarGanadorEnsayo.xhtml");
    }

    private void urlCuCo9EvaluacionEnsayoCriticoFaces() {
        //BEAN:#{cuCo8EvaluacionEnsayoCriticoFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_9/detallesEnsayoCritico.xhtml");
    }

    private void urlCuPr18IngresaModificarEventoInvestigacionFaces() {
        //BEAN:#{cuPr18IngresaModificarEventoInvestigacionFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_18/gestiona_evento_investigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_18/include_consulta_evento_investigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_18/include_registra_evento_investigacion.xhtml");
    }

    private void urlCuPr13HabilitarImplementacion() {
        //BEAN:#{cuPr13HabilitarImplementacion.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_13/buscarProyectoAImplementar.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_13/habilitarImplementacion.xhtml");
    }

    private void urlCuPr12EvaluacionProyectosInvestigacionFaces() {
        //BEAN:#{cuPr12EvaluacionProyectosInvestigacionFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_12/evaluacionProyectoInvestigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_12/seleccionProyectoEvaluacion.xhtml");
    }

    private void urlCuNe1PeriodoNecesidadesFaces() {
        //BEAN:#{cuNe1PeriodoNecesidadesFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_1/periodo_necesidades.xhtml");
    }

    private void urlCuPr24RevisarCompromisoEnviadosPorJefeUnidad() {
        //BEAN:#{cuPr24RevisarCompromisoEnviadosPorJefeUnidad.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_24/revisar_compromiso_enviados_por_jefe_unidad.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_24/include_agregar_retroalimentacion_compromisos.xhtml");
    }

    private void urlCuIv3GestionarSemillerosInvestigacionFaces() {
        //BEAN:#{cuIv3GestionarSemillerosInvestigacionFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_3/gestionar_semilleros_investigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_3/registrarCronogramaSemillero.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_3/registra_semilleros_investigacion.xhtml");
    }

    private void urlCuPr23GestionarCompromisoProyectos() {
        //BEAN:#{cuPr23GestionarCompromisoProyectos.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_23/gestiona_compromisos.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_23/gestiona_compromisos_proyecto.xhtml");
    }

    private void urlCuIv02ConsultarInvestigadores() {
        //BEAN:#{cuIv02ConsultarInvestigadores.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_02/consultarInvestigadores.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_02/detallesInvestigador.xhtml");
    }

    private void urlCuIv4ConsultarDetalleSemilleroFaces() {
        //BEAN:#{cuIv4ConsultarDetalleSemilleroFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/seleccionSemilleroCriteriosBusqueda.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesBasicosSemilleroInvestigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesCronogramaSemillero.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesEventosSemilleroInvestigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesInvestigacionesSemillero.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesOtrosEstimulosIncentivosSemillero.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesSemilleroInvestigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/detallesTalentoHumanSemilleroInvestigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_iv_4/seleccionSemilleroCriteriosBusqueda.xhtml");
    }

    private void urlCuTr01IngresarModificarTrabajoDeGradoFaces() {
        //BEAN:#{cuTr01IngresarModificarTrabajoDeGradoFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_tr_01/ingresar_modificar_trabajos_de_grado.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_tr_01/consultar_trabajos_de_grado.xhtml");
    }

    private void urlCuPr08ConsultarTrabajosYProyectos() {
        //BEAN:#{cuPr08ConsultarTrabajosYProyectos.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_08/consultarTrabajosYProyectos.xhtml");
    }

    private void urlCuPr20GestionImplementacionesVigentesAsignadasFaces() {
        //BEAN:#{cuPr20GestionImplementacionesVigentesAsignadasFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_20/consultaImplementacionesVigentes.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_20/include_lista_comentarios_compromisos.xhtml");
    }

    private void urlCuPr19ProyectosVigentesAsignadosFaces() {
        //BEAN:#{cuPr19ProyectosVigentesAsignadosFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_19/administra_consular_proyectos_vigentes_asignados.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_19/include_lista_comentarios_compromisos.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_19/pautas.xhtml");
    }

    private void urlCuPr17AgregarAsesoriaProyectoInvestigacionFaces() {
        //BEAN:#{cuPr17AgregarAsesoriaProyectoInvestigacionFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_17/asesorias_proyectos_investigacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_17/include_registrar_resultado_asesoria.xhtml");
    }

    private void urlCuPr16EncargadoGeneracionArchivosSniesFaces() {
        //BEAN:#{cuPr16EncargadoGeneracionArchivosSniesFaces.initReturnCU}
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_16/encargado_generar_archivos_snies.xhtml");
    }

    private void urlCuPr1ProyectoFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_1/administrar_proyecto.xhtml");
        urlCuIv4ConsultarDetalleSemilleroFaces();
    }

    private void urlCuPr6ConsultarDetalleProyectoInvestigacion() {
        //CU-PR-06
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_6/consultar_detalle_proyecto_investigacion.xhtml");
        urlCuIv4ConsultarDetalleSemilleroFaces();
    }

    private void urlCuNe5JefeLoteNecesidadesFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_5/include_jefe_necesidades.xhtml");
    }

    private void urlCuNe7PropuestaNecesidadFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_7/include_detalle_propuesta_necesidad_datos.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_7/include_detalle_propuesta_necesidad.xhtml");
    }

    private void urlCuNe6EvaluarPropuestasNecesidadesInvestigacionFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_6/gestiona_evaluar_propuestas_necesidades.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_6/include_detalle_propuesta.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_ne_6/include_detalle_propuesta_datos.xhtml");
    }

    private void urlCuPr05RegistrarPresupuestoFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_05/registrarPresupuesto.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_05/registrarPresupuestoImpl.xhtml");
    }

    private void urlCuPr11ConsultarDetalleCompromisoFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_11_1_2/consultar_detalle_compromiso.xhtml");
    }

    private void urlCuPr21RegistrarPlanDeTrabajoFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_21/registrarPlanDeTrabajo.xhtml");
    }

    private void urlCuPr15_1_2_AvanceImplemenacionFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_15_1_2/administrar_avance_implementacion.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_14/gestionarAvancePresupuestoInvestigacionImpl.xhtml");
    }

    private void urlCuPr10RegistroInformeFinal() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_10/registrar_informe_final_trabajo_investigacion.xhtml");
    }

    private void urlCuPr7RegistrarAvanceInvestigacionFaces() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_7/administrar_registro_avance_investigacion_proyecto.xhtml");
    }

    private void urlCuPr14AvancePresupuestoProyecto() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_14/gestionarAvancePresupuestoInvestigacion.xhtml");
    }

    private void urlCuCo03RevisarPropuesta() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_3/administra_revision_propuesta_proyecto.xhtml");
    }

    private void urlVersiones() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/versiones/administra_informacion_proyecto_version.xhtml");
    }

    private void urlCuCo06DetalleConvocatoria() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_6/detallesConvocatoria.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_6/seleccionConvocatoriasCriteriosBusqueda.xhtml");
    }

    private void urlCuCo05ConsultaPropuestaConvocatoria() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_5/propuestasConvocatoriasFinanciacionCriteriosBusqueda.xhtml");
    }

    private void urlCuCo04EvaluarPropuestaConvocatoria() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_4/gestiona_evaluar_propuestas_convocatoria.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_4/include_financiar_propuestas_convocatoria.xhtml");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_co_4/include_revisar_propuesta_convocatoria.xhtml");
    }

    private void urlCuPr22Comentarios() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_pr_22/cu_pr_22_registrar_revision_comentario_proyecto.xhtml");
    }

    private void urlCuMe01InvestigacionUnidad() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_01/parametrizar_investigacion_unidad.xhtml");
    }

    private void urlCuMe02CrearPropuestaBanco() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_02/crear_propuesta_banco.xhtml");
    }

    private void urlCuMe03AsignarInvestigacionBancoUnidad() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_03/asignar_investigacion_banco_unidad.xhtml");
    }

    private void urlCuMe04AprobarPropuestaBanco() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_04/aprobar_propuesta_banco.xhtml");
    }

    private void urlCuMe05InsertarInvestigacionUnidad() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_05/insertar_investigacion_unidad.xhtml");
    }

    private void urlCuMe06AprobarInvestigacionUnidadVicin() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_06/aprobar_investigacion_unidad_vicin.xhtml");
    }

    private void urlCuMe07PropiedadIntelectual() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_07/propiedad_intelectual.xhtml");
    }

    private void urlCuMe08InvestigacionesExternas() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_08/investigaciones_externas.xhtml");
    }

    private void urlCuMe09InvestigacionesExternasUnidad() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_09/investigaciones_externas_unidad.xhtml");
    }

    private void urlCuMe10EspaciosEquiposElementosInvestigacion() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_10/espacios_equipos_elementos_investigacion.xhtml");
    }

    private void urlCuMe11InsercionNotas() {
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/cu_me_11/insercion_notas.xhtml?faces-redirect=true");
        perfilUsuarioDTO.getListaPaginasAccesos().add("/pages/secured/consultas/consulta_horas_docente.xhtml?faces-redirect=true");
    }

}
