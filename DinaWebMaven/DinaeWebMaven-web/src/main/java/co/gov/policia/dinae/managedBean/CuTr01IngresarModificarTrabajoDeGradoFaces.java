package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ComentarioProyectoDTO;
import co.gov.policia.dinae.dto.EvaluadoresProyectoGradoDTO;
import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IDocumentacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoGradoLocal;
import co.gov.policia.dinae.interfaces.IEvaluadoresProyectoGradoLocal;
import co.gov.policia.dinae.interfaces.IInstitucionesProyectoLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IProgramasLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ISemilleroProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.AsesoriaProyecto;
import co.gov.policia.dinae.modelo.ComentarioProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.DocumentacionProyecto;
import co.gov.policia.dinae.dto.DocumentacionProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.interfaces.IAsesoriaProyectoLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IUsuarioRolLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.modelo.EvaluacionProyectoGrado;
import co.gov.policia.dinae.modelo.EvaluadoresProyectoGrado;
import co.gov.policia.dinae.modelo.InstitucionesProyecto;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.ModalidadAsesoriaProyecto;
import co.gov.policia.dinae.modelo.Programas;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.Rol;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.UsuarioUnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import co.gov.policia.dinae.util.DatesUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author RafaelGomez
 */
@javax.inject.Named(value = "cuTr01IngresarModificarTrabajoDeGradoFaces")
@javax.enterprise.context.SessionScoped
public class CuTr01IngresarModificarTrabajoDeGradoFaces extends JSFUtils implements Serializable {

    //Interfaces y EJB
    @EJB
    private IConstantesLocal iConstantesLocal;
    @EJB
    private IProgramasLocal iProgramasLocal;
    @EJB
    private IAreaCienciaTecnologiaLocal iAreaCienciaTecnologia;
    @EJB
    private ILineaLocal iLineaLocal;
    @EJB
    private IProyectoLocal iProyectoLocal;
    @EJB
    private IVistaFuncionarioLocal iVistaFuncionarioLocal;
    @EJB
    private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;
    @EJB
    private IDocumentacionProyectoLocal iDocumentacionProyectoLocal;
    @EJB
    private IUnidadPolicialLocal iUnidadPolicialLocal;
    @EJB
    private ISemilleroProyectoLocal iSemilleroProyectoLocal;
    @EJB
    private IInstitucionesProyectoLocal iInstitucionesProyectoLocal;
    @EJB
    private IComentarioProyectoLocal iComentarioProyectoLocal;
    @EJB
    private IEvaluacionProyectoGradoLocal iEvaluacionProyectoGradoLocal;
    @EJB
    private IEvaluadoresProyectoGradoLocal iEvaluadoresProyectoGradoLocal;
    @EJB
    private IInvestigadorLocal iInvestigadorLocal;
    @EJB
    private IAsesoriaProyectoLocal iAsesoriaProyectoLocal;
    @EJB
    private IUsuarioRolLocal iUsuarioRolLocal;
    @EJB
    private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

    //Titulo de la pagina
    /*
     Es variable dependiendo de donde se llame
     Registrar Trabajo de Grado
     Actualizar Trabajo de Grado
     */
    private String lblTitulo;

    //Datos y variables comunes
    private String nombreEscuela;
    private Long idUnidadPolicial;
    private Proyecto proyectoSeleccionado;

    //Indicador del tab actual
    private int idTabSeleccionado;

    //Datos y variables de la pantalla de consulta
    private Long idProgramaSeleccionadoConsulta;
    private List<SelectItem> listaNombreProgramaConsultarItem;
    private List<Proyecto> trabajosDeGrado;
    private List<Proyecto> trabajosDeGradoAsigndosInvestigadorPrincipal;
    private List<Proyecto> trabajosDeGradoGeneradorDeConsulta;
    private Long idProgramaSeleccionadoGeneradorConsulta;
    private List<SelectItem> listaNombreProgramaConsultarGeneradorConsultaItem;

    //Datos y varibles de la pestaña 1 - Agregar Trabajo de Grado
    private Long idNombreProgramaSeleccionado;
    private List<SelectItem> listaNombreProgramaItem;
    private Long areaCienciaTecnologiaSeleccionada;
    private List<SelectItem> listaAreaCienciaTecnologiaItem;
    private Long lineaSeleccionada;
    private List<SelectItem> listaLineaCienciaTecnologiaItem;
    private boolean mostrarFechaEstimadaInicioTrabajoDeGrado;
    private boolean mostrarFechaEstimadaFinTrabajoDeGrado;
    private boolean mostrarFechaAprobacionComiteTrabajoDeGrado;
    private boolean mostrarNumeroActaComiteTrabajoDeGrado;
    private int duracionMesesEstimadosTrabajoDeGrado;
    private Date fechaEstimadaInicioTrabajoDeGrado;
    private Date fechaEstimadaFinalizacionTrabajoDeGrado;

    //Datos y variables de la pestaña 2 - Talento Humano
    private String identificacionFuncionario;
    private String ciudadFuncionario;
    private VistaFuncionario vistaFuncionario;
    private List<InvestigadorProyecto> listaInvestigadoresTrabajoDeGrado;
    private List<InvestigadorProyecto> listaAsesoresTrabajoDeGrado;
    private List<SelectItem> listaTipoVinculacionItem;
    private List<SelectItem> listaTipoInvestigadorItem;
    //private String institucionExterna;
    private Long idTipoVinculacionSeleccionado;
    private Long idTipoInvestigadorSeleccionado;
    private InvestigadorProyecto investigadorProyecto;

    //Datos y variables de la pestaña 3 - Documentacion del proyecto
    private DocumentacionProyecto documentacionProyectoSeleccionado;
    private List<SelectItem> listaSelectItemTipoArchivo;
    //private String nombreArchivoSubido;
    private FileUploadEvent eventArchivoSubido;
    private List<DocumentacionProyectoDTO> listaDocumentacionTrabajoDeGradoDTO;
    private DocumentacionProyectoDTO documentacionTrabajoDeGradoDTODescargarSeleccionado;
    private DocumentacionProyectoDTO documentacionTrabajoDeGradoDTOEliminarSeleccionado;
    private ComentarioProyecto comentarioProyectoDocumentoSeleccionado;
    private DocumentacionProyectoDTO documentacionTrabajoDeGradoDTOComentariarSeleccionado;
    private List<ComentarioProyectoDTO> listaComentarioProyectoDTODocumentoSeleccionado;
    private ComentarioProyectoDTO comentarioProyectoDocumentacionProyectoDTOEliminarSeleccionado;
    private boolean agregarComentarioDocumento;
    private boolean verComentarioDocumento;
    private String nombreTipoDocumentoComentariarSeleccionado;

    //Datos y variables de la pestaña 4 - Evaluacion
    private Date fechaSustentacionTrabajoDeGrado;
    private BigDecimal notaTrabajoDeGrado;
    private BigDecimal notaSustentacionTrabajoDeGrado;
    private BigDecimal notaFinalTrabajoDeGrado;
    private List<EvaluadoresProyectoGrado> listaEvaluadoresTrabajoDeGrado;
    //private List <EvaluacionProyectoGrado> listaEvaluacionTrabajoDeGrado;
    private String identificacionFuncionarioEvaluador;
    private String ciudadFuncionarioEvaluador;
    private VistaFuncionario vistaFuncionarioEvaluador;
    private EvaluadoresProyectoGrado evaluadoresProyectoGrado;
    private EvaluacionProyectoGrado evaluacionProyectogrado;
    //private List<EvaluadoresProyectoGrado> listaEvaluadoresProyectoDeGrado;
    private List<EvaluadoresProyectoGradoDTO> listaEvaluadoresTrabajoDeGradoDTO;

    //Datos y variables de la pestaña 5 - Asesorias
    private AsesoriaProyecto asesoriaTrabajoDeGrado;
    private List<ModalidadAsesoriaProyecto> modalidadesAsesoria;
    private List<String> modalidadesAsesoriaSeleccionadas;
    private String resultadoAsesoria;
    private List<AsesoriaProyecto> listaAsesoriasTrabajoDeGrado;
    private List<SelectItem> listaModalidadAsesoriaProyectoItem;

    //Datos y variables de la pestaña 6 - Otras instituciones
    private List<SelectItem> listaOtrasUnidadesPolicialesItem;
    private Long idOtraUnidadPolicialSeleccionada;
    private String aporteInvestigacionUnidadPolicial;
    private List<SemilleroProyectoDTO> listaOtrasUnidadesPolicialesSemilleroProyectoDTO;
    private Long idOtraInstitucionSeleccionada;
    private List<SelectItem> listaOtrasInstitucionesItem;
    private boolean mostrarDetalleOtraNuevaInstitucion;
    private String otraNuevaInstitucion;
    private String aporteInvestigacionOtrasInstituciones;
    private List<InstitucionesProyectoDTO> listaOtrasInstitucionesProyectoDTO;
    private boolean llamadoCuPr08;

    //Datos y variables para modo solo lectura
    private boolean modoSoloLectura;

    //Datos y variables para investigador principal
    private boolean modoInvestigadorPrincipal;

    //Metodo de inicializacion desde Menu Principal a pantalla de consulta
    public String initReturnCU() {

        init();

        //Se cargan los proyectos almacenados y las listas para consulta
        try {
            //Si el usuario logueado es responsable de trabajos de grado
            //se carga el buscador de trabajos de grado por programa
            if (isEsResponsableTrabajosDeGrado()) {
                cargarDatosInicialesCUConsultarTrabajosDeGrado();
                consultarTrabajosDeGradoPorUnidadPolicialAndIdEstado(idUnidadPolicial, IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION);
            }
            //Si el usuario logueado es investigador principal se cargan los proyectos
            //que tiene asignados
            if (isEsInvestigadorPrincipal()) {
                consultarTrabajosDeGradoAsignadosInvestigadorPrincipal();
            }

            //Si el usuario logueado es generador de consulta se cargan todos los proyectos
            //de tipo trabajo de grado
            if (isEsUsuarioGeneradorDeConsulta()) {
                cargarDatosInicialesCUConsultarTrabajosDeGradoGeneradorConsulta();
                consultarTrabajosDeGradoGeneradorConsultaPorUnidadPolicial(idUnidadPolicial);
            }
        } catch (Exception ex) {
            Logger.getLogger(CuTr01IngresarModificarTrabajoDeGradoFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return navigationFaces.redirectCuTr01ConsultarTrabajosDeGradoRedirect();

    }

    //Metodo de inicializacion desde boton agregar nuevo trabajo de grado    
    public String initReturnCU_Agregar_Trabajo_de_Grado() {

        init();

        try {
            lblTitulo = keyPropertiesFactory.value("cu_tr_01_lbl_registrar_trabajo_de_grado");

            //Se cargan las listas iniciales para agregar un nuevo trabajo de grado
            cargarDatosInicialesCUAgregarTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar talento humano a un trabajo de grado
            cargarDatosInicialesCUAgregarTalentoHumanoTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar documentacion a un trabajo de grado
            cargarDatosInicialesCUAgregarDocumentacionTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar asesorias a un trabajo de grado
            cargarDatosInicialesCUAgregarAsesoriasTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar otras instituciones a un trabajo de grado
            cargarDatosInicialesCUAgregarOtrasInstitucionesTrabajoDeGrado();

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Trabajo de Grado", e);
        }
        return navigationFaces.redirectCuTr01IngresarModificarTrabajosDeGradoRedirect();
    }

    //Metodo de inicializacion de los datos de un trabajo de grado        
    public void initDatosTrabajoDeGrado(Long idProyecto) {

        try {

            //Se consulta el proyecto a modificar
            proyectoSeleccionado = iProyectoLocal.obtenerProyectoPorId(idProyecto);

            //Se cargan las listas iniciales para agregar un nuevo trabajo de grado
            cargarDatosInicialesCUAgregarTrabajoDeGrado();
            //Se cargan las listas por el id_unidad_policial del proyecto mas no del usuario si es para generar consulta
            if (isEsUsuarioGeneradorDeConsulta()) {
                cargarDatosInicialesCUConsultarTrabajoDeGradoGeneradorConsulta();
            }
            //Validar datos de la pestaña 1 para actualizacion  - informacion basica proyecto      
            if (proyectoSeleccionado.getProgramas() != null) {
                idNombreProgramaSeleccionado = proyectoSeleccionado.getProgramas().getIdPrograma();
            }

            if (proyectoSeleccionado.getLinea() != null) {
                lineaSeleccionada = proyectoSeleccionado.getLinea().getIdLinea();
                areaCienciaTecnologiaSeleccionada = proyectoSeleccionado.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia();

                //CARGAMOS LA LISTA DE LINEA CON SUS RESPECTIVA AREA
                handleAreaCienciaTecnologiaSeleccionadaChange();
            }
            if (proyectoSeleccionado.getFechaEstimadaInicio() != null && proyectoSeleccionado.getFechaEstimadaFinalizacion() != null) {
                fechaEstimadaInicioTrabajoDeGrado = proyectoSeleccionado.getFechaEstimadaInicio();
                fechaEstimadaFinalizacionTrabajoDeGrado = proyectoSeleccionado.getFechaEstimadaFinalizacion();

                //Se calculan los meses de duracion del proyecto
                handleCalcularDuracionDelProyecto(null);
            }

            if (proyectoSeleccionado.getEstado() == null || proyectoSeleccionado.getEstado().getIdConstantes() == null) {

                //Estado en ejecucion
                proyectoSeleccionado.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION));

            }

            //Se cargan las listas iniciales para agregar talento humano a un trabajo de grado
            cargarDatosInicialesCUAgregarTalentoHumanoTrabajoDeGrado();

            //Validar datos de la pestaña 2 para actualizacion - talento humano        
            listaInvestigadoresTrabajoDeGrado = cargarListaInvestigadorTrabajoDeGrado(proyectoSeleccionado.getIdProyecto());
            listaAsesoresTrabajoDeGrado = cargarListaAsesorTrabajoDeGrado(proyectoSeleccionado.getIdProyecto());

            //Se cargan las listas iniciales para agregar documentacion a un trabajo de grado
            cargarDatosInicialesCUAgregarDocumentacionTrabajoDeGrado();

            //Validar datos de la pestaña 3 pra actualizacion - documentacion        
            cargarListaDocumentacionTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar la evaluacion a un trabajo de grado
            //Validar los datos de la pestaña 4 para actualizacion - evaluacion
            cargarListaEvaluadoresTrabajoDeGrado();
            cargarEvaluacionTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar asesorias a un trabajo de grado
            cargarDatosInicialesCUAgregarAsesoriasTrabajoDeGrado();

            //Validar los datos de la pestaña 5 para actualizacion - Asesorias
            cargarlistaAsesoriasTrabajoDeGrado();

            //Se cargan las listas iniciales para agregar otras instituciones a un trabajo de grado
            cargarDatosInicialesCUAgregarOtrasInstitucionesTrabajoDeGrado();

            //Validar los datos de la pestaña 6 para actualizacion - Otras instituciones
            cargarListaOtrasUnidadesPolicialesVinculadas();
            cargarListaOtrasInstitucionesVinculadas();

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Trabajo de Grado", e);
        }
        //return navigationFaces.redirectCuTr01IngresarModificarTrabajosDeGradoRedirect();        
    }

    //Metodo de inicializacion desde boton actualizar nuevo trabajo de grado    
    public String initReturnCU_Desde_Consulta_Trabajos_de_grado(Long idProyecto) {

        init();
        lblTitulo = keyPropertiesFactory.value("cu_tr_01_lbl_actualizar_trabajo_de_grado");

        modoSoloLectura = false;
        modoInvestigadorPrincipal = false;

        initDatosTrabajoDeGrado(idProyecto);

        return navigationFaces.redirectCuTr01IngresarModificarTrabajosDeGradoRedirect();
    }

    //Metodo de inicializacion desde boton consultar trabajo de grado para generador de consulta    
    public String initReturnCU_Desde_Consulta_Trabajos_de_grado_Generador_de_consulta(Long idProyecto) {

        init();
        lblTitulo = keyPropertiesFactory.value("cu_tr_01_lbl_generador_de_consulta_trabajo_de_grado");
        modoSoloLectura = true;

        initDatosTrabajoDeGrado(idProyecto);

        return navigationFaces.redirectCuTr01IngresarModificarTrabajosDeGradoRedirect();
    }

    //Metodo de inicializacion desde boton consultar trabajo de grado para generador de consulta    
    public String initReturnCU_Desde_CuPr08(Long idProyecto) {

        init();
        lblTitulo = keyPropertiesFactory.value("cu_tr_01_lbl_generador_de_consulta_trabajo_de_grado");
        modoSoloLectura = true;

        initDatosTrabajoDeGrado(idProyecto);

        llamadoCuPr08 = true;

        return navigationFaces.redirectCuTr01IngresarModificarTrabajosDeGradoRedirect();
    }

    //Metodo de inicializacion desde boton consultar trabajo de grado para investigador principal    
    public String initReturnCU_Desde_Consulta_Trabajos_de_grado_Investigador_principal(Long idProyecto) {

        init();
        lblTitulo = keyPropertiesFactory.value("cu_tr_01_lbl_consulta_investigador_principal");
        modoInvestigadorPrincipal = true;

        initDatosTrabajoDeGrado(idProyecto);

        return navigationFaces.redirectCuTr01IngresarModificarTrabajosDeGradoRedirect();
    }

    //Constructor inicial
    @javax.annotation.PostConstruct
    public void init() {

        try {

            llamadoCuPr08 = false;
            //Datos generales
            setIdTabSeleccionado(0);
            idUnidadPolicial = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial();
            setNombreEscuela(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getNombre());
            setProyectoSeleccionado(new Proyecto());
            modoSoloLectura = false;
            modoInvestigadorPrincipal = false;

            //Datos de la pestaña de consulta
            trabajosDeGrado = new ArrayList<Proyecto>();
            idProgramaSeleccionadoConsulta = null;
            listaNombreProgramaConsultarItem = null;
            trabajosDeGradoAsigndosInvestigadorPrincipal = new ArrayList<Proyecto>();
            trabajosDeGradoGeneradorDeConsulta = new ArrayList<Proyecto>();
            idProgramaSeleccionadoGeneradorConsulta = null;
            listaNombreProgramaConsultarGeneradorConsultaItem = null;

            //Datos de la pestaña 1
            idNombreProgramaSeleccionado = null;
            areaCienciaTecnologiaSeleccionada = null;
            lineaSeleccionada = null;
            duracionMesesEstimadosTrabajoDeGrado = 0;
            listaNombreProgramaItem = null;
            listaAreaCienciaTecnologiaItem = null;
            listaLineaCienciaTecnologiaItem = null;
            mostrarNumeroActaComiteTrabajoDeGrado = true;
            mostrarFechaEstimadaInicioTrabajoDeGrado = true;
            mostrarFechaEstimadaFinTrabajoDeGrado = true;
            mostrarFechaAprobacionComiteTrabajoDeGrado = true;
            fechaEstimadaInicioTrabajoDeGrado = new Date();
            fechaEstimadaFinalizacionTrabajoDeGrado = new Date();

            //Datos de la pestaña 2
            identificacionFuncionario = null;
            //ciudadFuncionario = null;
            vistaFuncionario = null;
            listaInvestigadoresTrabajoDeGrado = new ArrayList<InvestigadorProyecto>();
            listaAsesoresTrabajoDeGrado = new ArrayList<InvestigadorProyecto>();
            //institucionExterna = null;
            idTipoVinculacionSeleccionado = null;
            idTipoInvestigadorSeleccionado = null;
            investigadorProyecto = new InvestigadorProyecto();
            ciudadFuncionario = null;
            listaTipoVinculacionItem = null;
            listaTipoInvestigadorItem = null;

            //Datos de la pestaña 3
            documentacionProyectoSeleccionado = null;
            eventArchivoSubido = null;
            listaDocumentacionTrabajoDeGradoDTO = null;
            documentacionTrabajoDeGradoDTOEliminarSeleccionado = null;
            documentacionTrabajoDeGradoDTODescargarSeleccionado = null;
            documentacionProyectoSeleccionado = new DocumentacionProyecto();
            listaSelectItemTipoArchivo = null;
            documentacionProyectoSeleccionado.setTipoDocumento(new Constantes());
            comentarioProyectoDocumentoSeleccionado = new ComentarioProyecto();
            listaComentarioProyectoDTODocumentoSeleccionado = null;
            comentarioProyectoDocumentacionProyectoDTOEliminarSeleccionado = null;
            agregarComentarioDocumento = false;
            verComentarioDocumento = false;
            documentacionTrabajoDeGradoDTOComentariarSeleccionado = null;
            nombreTipoDocumentoComentariarSeleccionado = null;

            //Datos de la pestaña 4
            //fechaSustentacionTrabajoDeGrado = null;
            notaTrabajoDeGrado = null;
            notaSustentacionTrabajoDeGrado = null;
            notaFinalTrabajoDeGrado = null;
            listaEvaluadoresTrabajoDeGrado = new ArrayList<EvaluadoresProyectoGrado>();
            //listaEvaluacionTrabajoDeGrado = new ArrayList<EvaluacionProyectoGrado>();
            listaEvaluadoresTrabajoDeGradoDTO = null;

            fechaSustentacionTrabajoDeGrado = null;
            identificacionFuncionarioEvaluador = null;
            ciudadFuncionarioEvaluador = null;
            vistaFuncionarioEvaluador = null;
            evaluadoresProyectoGrado = null;
            evaluacionProyectogrado = new EvaluacionProyectoGrado();

            //Datos de la pestaña 5
            asesoriaTrabajoDeGrado = null;
            modalidadesAsesoria = null;
            resultadoAsesoria = null;
            listaAsesoriasTrabajoDeGrado = null;
            modalidadesAsesoriaSeleccionadas = new ArrayList<String>();
            modalidadesAsesoria = new ArrayList<ModalidadAsesoriaProyecto>();
            listaAsesoriasTrabajoDeGrado = new ArrayList<AsesoriaProyecto>();
            listaModalidadAsesoriaProyectoItem = null;

            //Datos de la pestaña 6
            idOtraUnidadPolicialSeleccionada = null;
            aporteInvestigacionUnidadPolicial = null;
            listaOtrasUnidadesPolicialesSemilleroProyectoDTO = null;
            idOtraInstitucionSeleccionada = null;
            otraNuevaInstitucion = null;
            aporteInvestigacionOtrasInstituciones = null;
            listaOtrasInstitucionesProyectoDTO = null;
            listaOtrasUnidadesPolicialesItem = null;
            listaOtrasInstitucionesItem = null;
            mostrarDetalleOtraNuevaInstitucion = false;
            listaOtrasUnidadesPolicialesSemilleroProyectoDTO = new ArrayList<SemilleroProyectoDTO>();
            listaOtrasInstitucionesProyectoDTO = new ArrayList<InstitucionesProyectoDTO>();

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 ", e);

        }

    }

    //Metodo que carga las listas necesarias para la consulta de trabajos de grado almacenados
    private void cargarDatosInicialesCUConsultarTrabajosDeGrado() throws Exception {            
            //Se carga la lista de programas Activos por unidad policial para consultar los trabajos de grado
            listaNombreProgramaConsultarItem = UtilidadesItem.getListaSel(
                    iProgramasLocal.getProgramasByActivoAndIdUnidadPolicial(this.idUnidadPolicial),
                    "idPrograma",
                    "nombre");        
    }

    private void cargarDatosInicialesCUConsultarTrabajosDeGradoGeneradorConsulta() throws Exception {

        //Se carga la lista de programas Activos por unidad policial para consultar los trabajos de grado
        if (loginFaces.getPerfilUsuarioDTO().getUnidadPolicial() != null
                && loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial() != null) {

            setListaNombreProgramaConsultarGeneradorConsultaItem(UtilidadesItem.getListaSel(
                    iProgramasLocal.getProgramasByActivoAndIdUnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()),
                    "idPrograma",
                    "nombre"));

        }
    }

    //Metodo que carga las listas para agregar informacion basica de trabajo de grado
    private void cargarDatosInicialesCUAgregarTrabajoDeGrado() throws Exception {

        //Se carga la lista de programas Activos por unidad policial
        listaNombreProgramaItem = UtilidadesItem.getListaSel(
                iProgramasLocal.getProgramasByActivoAndIdUnidadPolicial(this.idUnidadPolicial),
                "idPrograma",
                "nombre");

        //Se carga la lista de Areas de ciencia y tecnologia
        listaAreaCienciaTecnologiaItem = UtilidadesItem.getListaSel(iAreaCienciaTecnologia.getAreaCienciaTecnologias(),
                "idAreaCienciaTecnologia",
                "nombre");

    }

    //Metodo que carga las listas para agregar informacion basica de trabajo de grado
    private void cargarDatosInicialesCUConsultarTrabajoDeGradoGeneradorConsulta() throws Exception {

        //Se carga la lista de programas Activos por unidad policial
        listaNombreProgramaItem = UtilidadesItem.getListaSel(
                iProgramasLocal.getProgramasByActivoAndIdUnidadPolicial(proyectoSeleccionado.getUnidadPolicial().getIdUnidadPolicial()),
                "idPrograma",
                "nombre");

        //Se carga la lista de Areas de ciencia y tecnologia
        listaAreaCienciaTecnologiaItem = UtilidadesItem.getListaSel(iAreaCienciaTecnologia.getAreaCienciaTecnologias(),
                "idAreaCienciaTecnologia",
                "nombre");

    }

    //Metodo que carga las listas para agregar talento humano a un trabajo de grado
    private void cargarDatosInicialesCUAgregarTalentoHumanoTrabajoDeGrado() throws Exception {

        listaTipoVinculacionItem = UtilidadesItem.getListaSel(
                iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_VINCULACION_TRABAJO_GRADO),
                "idConstantes",
                "valor");

        listaTipoInvestigadorItem = UtilidadesItem.getListaSel(
                iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_INVESTIGADOR_TRABAJO_GRADO),
                "idConstantes",
                "valor");

    }

    //Metodo que carga las listas para agregar documentacion a un trabajo de grado
    private void cargarDatosInicialesCUAgregarDocumentacionTrabajoDeGrado() throws Exception {

        listaSelectItemTipoArchivo = UtilidadesItem.getListaSel(
                iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_DOCUMENTO_TRABAJO_GRADO),
                "idConstantes",
                "valor");

    }

    //Metodo que carga las listas para agregar asesorias a un trabajo de grado
    private void cargarDatosInicialesCUAgregarAsesoriasTrabajoDeGrado() throws Exception {

        listaModalidadAsesoriaProyectoItem = UtilidadesItem.getListaSel(
                iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_MODALIDADES_ASESORIAS_TRABAJO_DE_GRADO),
                "idConstantes",
                "valor");

    }

    //Metodo que carga las listas para agregar otras instituciones a un trabajo de grado
    private void cargarDatosInicialesCUAgregarOtrasInstitucionesTrabajoDeGrado() throws Exception {

        listaOtrasUnidadesPolicialesItem = UtilidadesItem.getListaSel(
                iUnidadPolicialLocal.getUnidadPolicialExcepto(
                        loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()),
                "idUnidadPolicial",
                "nombre");

        listaOtrasInstitucionesItem = UtilidadesItem.getListaSel(
                iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO),
                "idConstantes",
                "valor");

    }

    public void handleCalcularDuracionDelProyecto(SelectEvent event) {

        Date startDate = fechaEstimadaInicioTrabajoDeGrado;
        Date endDate = fechaEstimadaFinalizacionTrabajoDeGrado;

        if (startDate == null || endDate == null) {
            return;
        }
        //Se invoca al metodo de dateUtils
        duracionMesesEstimadosTrabajoDeGrado = DatesUtils.calcularMesesEntreFechas(startDate, endDate);

    }

    /**
     * Método que carga las lienas dependiendo del Aera Seleccionada
     */
    public void handleAreaCienciaTecnologiaSeleccionadaChange() {

        if (areaCienciaTecnologiaSeleccionada == null) {

            setListaLineaCienciaTecnologiaItem(new ArrayList<SelectItem>());
            return;
        }

        try {

            setListaLineaCienciaTecnologiaItem(UtilidadesItem.getListaSel(iLineaLocal.getLineasPorArea(areaCienciaTecnologiaSeleccionada), "idLinea", "nombre"));

        } catch (JpaDinaeException e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-TR-01 Agregar Nuevo Trabajo de Grado (handleAreaCienciaTecnologiaSeleccionadaChange) ", e);
        }

    }

    /**
     * Handle para el cargue de archivos
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {

            eventArchivoSubido = event;
            documentacionProyectoSeleccionado.setNombreArchivo(event.getFile().getFileName());
            adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(event.getFile().getFileName()));

        } catch (Exception e) {

            adicionaMensajeError(e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Documentacion Trabajo de Grado (handleFileUpload) ", e);
        }
    }

    //Listener para el cambio de tabs
    public void onTabChange(TabChangeEvent event) {

        setIdTabSeleccionado(0);
        if (event == null || event.getTab() == null) {
            return;
        }
        if ("idTabViewInformacionBasicaTrabajoDeGrado".equals(event.getTab().getId())) {
            setIdTabSeleccionado(0);
        } else if ("idTabViewTalentoHumanoTrabajoDeGrado".equals(event.getTab().getId())) {
            setIdTabSeleccionado(1);
        } else if ("idTabViewDocumentacionTrabajoDeGrado".equals(event.getTab().getId())) {
            setIdTabSeleccionado(2);
        } else if ("idTabViewEvaluacionTrabajoDeGrado".equals(event.getTab().getId())) {
            setIdTabSeleccionado(3);
        } else if ("idTabViewAsesoriasTrabajoDeGrado".equals(event.getTab().getId())) {
            setIdTabSeleccionado(4);
        } else if ("idTabViewOtrasInstitucionesTrabajoDeGrado".equals(event.getTab().getId())) {
            setIdTabSeleccionado(5);
        }
    }

    /**
     * @param idUnidadPolicial
     * @param idEstado Método que realiza la consulta de trabajos de grado por
     * el idUnidadPolicial del usuario Logueado
     */
    public void consultarTrabajosDeGradoPorUnidadPolicialAndIdEstado(Long idUnidadPolicial, Long idEstado) {

        try {
            //VALIDACIONES 
            //El usuario logueado debe tener una unidad policial asociada
            if (idUnidadPolicial == null || idUnidadPolicial.equals(0L)) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_usuario_sin_unidad_policial"));
                return;
            }

            trabajosDeGrado = iProyectoLocal.getProyectosTipoTrabajoDeGradoByIdUnidadPolicialAndIdEstado(idUnidadPolicial, idEstado);

            //No se encontraron trabajos de grado asociados al programa seleccionado
            if (trabajosDeGrado == null || trabajosDeGrado.isEmpty()) {
                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_consultar_trabajos_de_grado_vacio"));
                return;
            }
            for (Proyecto unTrabajoDeGrado : trabajosDeGrado) {

                unTrabajoDeGrado.setNombreInvestigadorPrincipal(obtenerInvestigadorPrincipalTrabajoDeGrado(unTrabajoDeGrado.getIdProyecto()));

            }
            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajos_de_grado_consultados_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Consultar Trabajo de Grado(consultarTrabajosDeGrado) ", e);
        }

    }

    /**
     * @param idUnidadPolicial
     * @param idEstado Método que realiza la consulta de trabajos de grado por
     * el idUnidadPolicial del usuario Logueado para el generador de consulta
     */
    public void consultarTrabajosDeGradoGeneradorConsultaPorUnidadPolicial(Long idUnidadPolicial) {

        try {
            //VALIDACIONES 
            //El usuario logueado debe tener una unidad policial asociada
            if (idUnidadPolicial == null || idUnidadPolicial.equals(0L)) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_usuario_sin_unidad_policial"));
                return;
            }
            trabajosDeGradoGeneradorDeConsulta = new ArrayList<Proyecto>();

            trabajosDeGradoGeneradorDeConsulta = iProyectoLocal.getProyectosTipoTrabajoDeGradoByIdUnidadPolicial(idUnidadPolicial);

            //No se encontraron trabajos de grado asociados al programa seleccionado
            if (trabajosDeGradoGeneradorDeConsulta == null || trabajosDeGradoGeneradorDeConsulta.isEmpty()) {
                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_consultar_trabajos_de_grado_vacio"));
                return;
            }
            for (Proyecto unTrabajoDeGradoGeneradorConsulta : trabajosDeGradoGeneradorDeConsulta) {

                unTrabajoDeGradoGeneradorConsulta.setNombreInvestigadorPrincipal(obtenerInvestigadorPrincipalTrabajoDeGrado(unTrabajoDeGradoGeneradorConsulta.getIdProyecto()));

            }
            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajos_de_grado_consultados_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Consultar Trabajo de Grado(consultarTrabajosDeGrado) ", e);
        }

    }

    /**
     * Método que realiza la consulta de trabajos de grado por el idPrograma
     * para el responsable de trabajos de grado
     */
    public void consultarTrabajosDeGrado() {

        try {
            //VALIDACIONES 
            //Debe haberse seleccionado un programa para consultar los trabajos de grado
            if (idProgramaSeleccionadoConsulta == null || idProgramaSeleccionadoConsulta.equals(0L)) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_seleccione_programa_consulta"));
                return;
            }

            //trabajosDeGrado = iProyectoLocal.getProyectosByIdPrograma(idProgramaSeleccionadoConsulta);
            trabajosDeGrado = iProyectoLocal.getProyectosByIdProgramaAndIdEstado(idProgramaSeleccionadoConsulta,
                    IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION);

            //No se encontraron trabajos de grado asociados al programa seleccionado
            if (trabajosDeGrado == null || trabajosDeGrado.isEmpty()) {
                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_consultar_trabajos_de_grado_vacio"));
                return;
            }
            for (Proyecto unTrabajoDeGrado : trabajosDeGrado) {

                unTrabajoDeGrado.setNombreInvestigadorPrincipal(obtenerInvestigadorPrincipalTrabajoDeGrado(unTrabajoDeGrado.getIdProyecto()));

            }
            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajos_de_grado_consultados_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Consultar Trabajo de Grado(consultarTrabajosDeGrado) ", e);
        }

    }

    /**
     * Método que consulta los trabajos de grado asignados a un investigador
     * principal
     */
    public void consultarTrabajosDeGradoAsignadosInvestigadorPrincipal() {

        try {
            //VALIDACIONES 
            //Debe haberse logueado como investigador principal
            /*if(idProgramaSeleccionadoConsulta == null || idProgramaSeleccionadoConsulta.equals(0L)){            
             adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_seleccione_programa_consulta"));
             return;
             }*/
            trabajosDeGradoAsigndosInvestigadorPrincipal = new ArrayList<Proyecto>();
            //Se buscan los trabajos de grado asignados al numero de identificacion del usuario logueado
            //trabajosDeGradoAsigndosInvestigadorPrincipal = iProyectoLocal.getProyectosAsignadosInvestigadorPrincipalByIdentificacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

            //Se buscan los trabajos de grado en ejecucion asignados al numero de identificacion del usuario logueado
            trabajosDeGradoAsigndosInvestigadorPrincipal = iProyectoLocal.getProyectosAsignadosInvestigadorPrincipalByIdentificacionAndIdEstado(
                    loginFaces.getPerfilUsuarioDTO().getIdentificacion(),
                    IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION);

            //No se encontraron trabajos de grado asignados al investigador principal logeuado
            if (trabajosDeGradoAsigndosInvestigadorPrincipal == null || trabajosDeGradoAsigndosInvestigadorPrincipal.isEmpty()) {
                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_consultar_trabajos_de_grado_asignados_investigador_principal_vacio"));
                return;
            }
            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajos_de_grado_asignados_investigador_principal_consultados_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Consultar Trabajo de Grado(consultarTrabajosDeGradoAsignadosInvestigadorPrincipal) ", e);
        }

    }

    /**
     * Método que consulta los trabajos de grado para el usuario generador de
     * consulta
     */
    public void consultarTrabajosDeGradoGeneradorDeConsulta() {

        try {
            //VALIDACIONES 
            //Debe seleccionado un programa para la consulta
            if (idProgramaSeleccionadoGeneradorConsulta == null || idProgramaSeleccionadoGeneradorConsulta.equals(0L)) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_seleccione_programa_consulta"));
                return;
            }
            trabajosDeGradoGeneradorDeConsulta = new ArrayList<Proyecto>();
            //Se buscan todos los trabajos de grado
            //trabajosDeGradoGeneradorDeConsulta = iProyectoLocal.getAllProyectosTipoTrabajoDeGrado();
            trabajosDeGradoGeneradorDeConsulta = iProyectoLocal.getProyectosByIdPrograma(idProgramaSeleccionadoGeneradorConsulta);

            //No se encontraron trabajos de grado
            if (trabajosDeGradoGeneradorDeConsulta == null || trabajosDeGradoGeneradorDeConsulta.isEmpty()) {
                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_consultar_trabajos_de_grado_generador_de_consulta_vacio"));
                return;
            }

            //Se debe buscar los investigadores principales para los trabajos de grado consultados                    
            for (Proyecto unTrabajoDeGradoConsulta : trabajosDeGradoGeneradorDeConsulta) {
                unTrabajoDeGradoConsulta.setNombreInvestigadorPrincipal(obtenerInvestigadorPrincipalTrabajoDeGrado(unTrabajoDeGradoConsulta.getIdProyecto()));
            }

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajos_de_grado_generador_de_consulta_consultados_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Consultar Trabajo de Grado(consultarTrabajosDeGradoGeneradorDeConsulta) ", e);
        }

    }

    //Metodo que obtiene el investigador principal de un proyecto    
    public String obtenerInvestigadorPrincipalTrabajoDeGrado(Long idProyecto) {

        String nombreInvestigadorPrincipalUntrabajoDeGrado = "";
        List<InvestigadorProyecto> investigadoresTrabajoDeGrado = new ArrayList<InvestigadorProyecto>();

        try {
            //Se consultan los investigadores para cada trabajo de grado encontrado
            //Se asigna el investigador principal si existe en la lista de investigadores
            Constantes constanteInvestigadorPrincipal = iConstantesLocal.getConstantesPorIdConstante(IConstantes.TIPO_INVESTIGADOR_TRABAJO_GRADO_INVESTIGADOR_PRINCIPAL);
            Long idConstanteInvestigadorPrincipal = constanteInvestigadorPrincipal.getIdConstantes();

            //Se obtiene el investigador del trabajo de grado para cada proyecto de la lista trabajosDeGrado                
            investigadoresTrabajoDeGrado = cargarListaInvestigadorTrabajoDeGrado(idProyecto);

            if (investigadoresTrabajoDeGrado != null && investigadoresTrabajoDeGrado.size() > 0) {

                for (InvestigadorProyecto unInvestigadorProyecto : investigadoresTrabajoDeGrado) {
                    if (unInvestigadorProyecto.getTipoInvestigador().getIdConstantes() == idConstanteInvestigadorPrincipal) {
                        nombreInvestigadorPrincipalUntrabajoDeGrado = unInvestigadorProyecto.getNombreCompleto();
                        break;
                    } else {
                        nombreInvestigadorPrincipalUntrabajoDeGrado = "No Asignado";
                    }
                }
                //return nombreInvestigadorPrincipalUntrabajoDeGrado;
            } else {
                nombreInvestigadorPrincipalUntrabajoDeGrado = "No Asignado";
            }
            //return nombreInvestigadorPrincipalUntrabajoDeGrado;

        } catch (Exception e) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Consultar Trabajo de Grado(obtenerInvestigadorPrincipalTrabajoDeGrado) ", e);
        }
        return nombreInvestigadorPrincipalUntrabajoDeGrado;

    }

    //Metodo para regresar a consultar trabajos de grado
    public void regresarConsultarTrabajosDeGrado() throws IOException {

        trabajosDeGrado = new ArrayList<Proyecto>();
        trabajosDeGradoAsigndosInvestigadorPrincipal = new ArrayList<Proyecto>();
        trabajosDeGradoGeneradorDeConsulta = new ArrayList<Proyecto>();
        proyectoSeleccionado = null;

        if (llamadoCuPr08) {

            navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCu08ConsultarTrabajosYProyectos());
            return;
        }

        //Se cargan los proyectos almacenados y las listas para consulta
        try {
            //Si el usuario logueado es responsable de trabajos de grado
            //se carga el buscador de trabajos de grado por programa
            if (isEsResponsableTrabajosDeGrado()) {
                cargarDatosInicialesCUConsultarTrabajosDeGrado();
                consultarTrabajosDeGradoPorUnidadPolicialAndIdEstado(idUnidadPolicial, IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION);
            }
            //Si el usuario logueado es investigador principal se cargan los proyectos
            //que tiene asignados
            if (isEsInvestigadorPrincipal()) {
                consultarTrabajosDeGradoAsignadosInvestigadorPrincipal();
            }

            //Si el usuario logueado es generador de consulta se cargan todos los proyectos
            //de tipo trabajo de grado
            if (isEsUsuarioGeneradorDeConsulta()) {
                cargarDatosInicialesCUConsultarTrabajosDeGradoGeneradorConsulta();
                consultarTrabajosDeGradoGeneradorConsultaPorUnidadPolicial(idUnidadPolicial);
            }

        } catch (Exception ex) {
            Logger.getLogger(CuTr01IngresarModificarTrabajoDeGradoFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        navigationFaces.redirectFacesCuTr01ConsultarTrabajosDeGrado();

    }

    /**
     * Método que realiza el proceso de modificación de proyecto
     */
    public void guardarProyecto() {

        try {

            Date fechaHoy = new Date();
            //VALIDACIONES

            //Debe haberse seleccionado un programa para el trabajo de grado
            if (idNombreProgramaSeleccionado == null || idNombreProgramaSeleccionado.equals(0L)) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_seleccione_programa"));
                return;
            }

            /*
             if (isMostrarFechaEstimadaInicioTrabajoDeGrado()) {

             //FECHA INICIO DEBE SER MAYOR A LA FECHA DEL SISTEMA.
             if (co.gov.policia.dinae.util.DatesUtils.compareDate(fechaEstimadaInicioTrabajoDeGrado, fechaHoy) == -1) {

             adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_fecha_inicio_mayor"));
             return;
             }

             }*/
            if (isMostrarFechaEstimadaFinTrabajoDeGrado()) {

                //FECHA FIN DEBE SER MAYOR A LA FECHA DEL SISTEMA.
                if (co.gov.policia.dinae.util.DatesUtils.compareDate(fechaEstimadaFinalizacionTrabajoDeGrado, fechaHoy) == -1) {

                    adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_fecha_fin_mayor"));
                    return;
                }

            }

            if (isMostrarFechaEstimadaInicioTrabajoDeGrado() && isMostrarFechaEstimadaFinTrabajoDeGrado()) {

                //VALIDAMOS QUE LA FECHA FINAL NO SEA MENOR A LA FECHA INICIAL
                if (co.gov.policia.dinae.util.DatesUtils.compareDate(fechaEstimadaFinalizacionTrabajoDeGrado, fechaEstimadaInicioTrabajoDeGrado) == -1) {

                    adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_fecha_fin_mayor_a_fecha_inicial"));
                    return;
                }

            }

            //FECHA DE APROBACION COMITE DEBE SER MENOR A LA FECHA DEL SISTEMA.
            if (isMostrarFechaAprobacionComiteTrabajoDeGrado()) {
                //if (co.gov.policia.dinae.util.DatesUtils.compareDate(proyectoSeleccionado.getFechaAprobacionComite2(), fechaHoy) == 1) {
                if (proyectoSeleccionado.getFechaAprobacionComite2().after(fechaHoy)) {
                    adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_fecha_aprobacion_comite_mayor"));
                    return;
                }
            }

            //Se setea el id del programa seleccionado
            proyectoSeleccionado.setProgramas(new Programas(idNombreProgramaSeleccionado));

            //Se setea la linea a la que pertenece el programa
            proyectoSeleccionado.setLinea(new Linea(lineaSeleccionada));

            //Se setean las fechas estimadas de inicio y finalizacion del proyecto
            proyectoSeleccionado.setFechaEstimadaInicio(fechaEstimadaInicioTrabajoDeGrado);
            proyectoSeleccionado.setFechaEstimadaFinalizacion(fechaEstimadaFinalizacionTrabajoDeGrado);

            //USUARIO ROL QUE ACTUALIZA EL REGISTRO
            proyectoSeleccionado.setUsuarioRol(new UsuarioRol(
                    loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                            IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));

            //Se asigna la unidad policial de quien realiza el registro
            proyectoSeleccionado.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));

            //Se asigna el estado En ejecucion (110) al proyecto de grado
            proyectoSeleccionado.setEstado(iConstantesLocal.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION));

            //ASIGNAMOS CODIGO DEL PROYECTO
            if (proyectoSeleccionado.getConcecutivoProyectoGrado() == null) {

                int ultimoConcecutivoProyectoGrado = iProyectoLocal.obtenerUltimoConcecutivoTrabajoGrado();
                proyectoSeleccionado.setConcecutivoProyectoGrado(String.valueOf(ultimoConcecutivoProyectoGrado + 1));

            }

            //Se guarda el proyecto
            proyectoSeleccionado = iProyectoLocal.guardarProyecto(proyectoSeleccionado);

            //CREAMOS LOS INDICADORES PARA ESTE PROYECTO
            //crearIndicadoresProyecto();
            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_proyecto_fue_alamacenado_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Nuevo Trabajo de Grado(guardarProyecto) ", e);
        }

    }

    /**
     * Método para buscar funcionarios en el sistema SIATH.
     *
     */
    public void obtenerDatosFuncionario() {

        //setVistaFuncionario(null);
        vistaFuncionario = null;

        if (identificacionFuncionario == null || identificacionFuncionario.trim().length() == 0) {
            adicionaMensajeError("El número de documento esta vacio.");
            return;
        }

        try {

            //VERIFICAMOS QUE EL FUNCIONARIO A AGREGAR NO EXISTA EN LA LISTA de investigadores
            if (listaInvestigadoresTrabajoDeGrado != null && !listaInvestigadoresTrabajoDeGrado.isEmpty()) {
                for (InvestigadorProyecto unInvestigadoresTrabajoDeGrado : listaInvestigadoresTrabajoDeGrado) {
                    if (unInvestigadoresTrabajoDeGrado.getIdentificacion().equals(identificacionFuncionario)) {
                        adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_add_investigador_ya_existe"));
                        return;
                    }
                }

            }

            //VERIFICAMOS QUE EL FUNCIONARIO A AGREGAR NO EXISTA EN LA LISTA de asesores            
            if (listaAsesoresTrabajoDeGrado != null && !listaAsesoresTrabajoDeGrado.isEmpty()) {
                for (InvestigadorProyecto unAsesorTrabajoDeGrado : listaInvestigadoresTrabajoDeGrado) {
                    if (unAsesorTrabajoDeGrado.getIdentificacion().equals(identificacionFuncionario)) {
                        adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_add_asesor_ya_existe"));
                        return;
                    }
                }

            }

            //Se busca el funcionario en la tabla VistaFuncionario o en la tabla Investigadores
            //vistaFuncionario = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacionFuncionario);
            vistaFuncionario = obtenerFuncionarioDeVistaFuncionaroOInvestigadoresTalentoHumano(identificacionFuncionario);

            if (vistaFuncionario == null) {

                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_identificacion_no_encontrada"));
                vistaFuncionario = null;
            }

            //Se debe obtener la ciudad del funcionario
            if (vistaFuncionario != null) {
                ciudadFuncionario = vistaFuncionario.getCiudadExpedicionDocumento();
            }
        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Nuevo Trabajo de Grado (obtenerDatosFuncionario) ", e);

        }

    }

    /**
     * Método para buscar funcionarios Evaluadores en el sistema SIATH y en la
     * tabla Investigadores.
     *
     */
    public void obtenerDatosFuncionarioEvaluador() {

        vistaFuncionarioEvaluador = null;

        if (identificacionFuncionarioEvaluador == null || identificacionFuncionarioEvaluador.trim().length() == 0) {
            adicionaMensajeError("El número de documento esta vacio.");
            return;
        }

        try {
            //Verificamos que el evaluador a asociar no este en la lista de evaluadores
            if (listaEvaluadoresTrabajoDeGrado != null && !listaEvaluadoresTrabajoDeGrado.isEmpty()) {
                for (EvaluadoresProyectoGrado unEvaluadorTrabajoDeGrado : listaEvaluadoresTrabajoDeGrado) {
                    if (unEvaluadorTrabajoDeGrado.getIdentificacion().equals(identificacionFuncionarioEvaluador)) {
                        adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_add_investigador_ya_existe"));
                        return;
                    }
                }

            }

            //Se busca en la vistaFuncionario el evaluador a agregar
            //vistaFuncionarioEvaluador = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacionFuncionarioEvaluador);
            vistaFuncionarioEvaluador = obtenerFuncionarioDeVistaFuncionaroOInvestigadores(identificacionFuncionarioEvaluador);

            //Se debe obtener la ciudad del funcionario
            if (vistaFuncionarioEvaluador != null) {
                ciudadFuncionarioEvaluador = vistaFuncionarioEvaluador.getCiudadExpedicionDocumento();
            }
            if (getVistaFuncionarioEvaluador() == null) {

                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_identificacion_no_encontrada"));
                vistaFuncionarioEvaluador = null;
            }
        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Nuevo Trabajo de Grado (obtenerDatosFuncionarioEvaluador) ", e);

        }

    }

    //Metodo para obtener una vistFuncioanrio de la tabla vistaFuncionario o de la tabla Investigadores para la asignacion de Talento Humano
    public VistaFuncionario obtenerFuncionarioDeVistaFuncionaroOInvestigadoresTalentoHumano(String identificacion) {

        VistaFuncionario vistaFuncionarioEncontrado = new VistaFuncionario();
        try {
            //Se busca la identificacion en la tabla VistaFuncionario
            vistaFuncionarioEncontrado = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacion);

            //Si no se encuentra en la tabla VistaFuncionario se busca en la tabla Investigadores
            if (vistaFuncionarioEncontrado == null) {

                Investigador investigadorFuncionario = new Investigador();
                investigadorFuncionario = iInvestigadorLocal.getInvestigadorPorNumeroIdentificacion(identificacion);

                //Si se encuentra un investigador, se completan los datos de funcionario encontrado con los del investigador
                if (investigadorFuncionario != null) {
                    vistaFuncionarioEncontrado = convertirInvestigadorEnVistaFuncionario(investigadorFuncionario);
                    //Se asocia el tipo de vinculacion de acuerdo al que esta en la tabla de investigadores
                    idTipoVinculacionSeleccionado = investigadorFuncionario.getTipoVinculacion().getIdConstantes();
                    //Se recargan las listas de selectItems con los tipos de vinculacion para proyecto
                    listaTipoVinculacionItem = UtilidadesItem.getListaSel(
                            iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_VINCULACION_PROYECTO),
                            "idConstantes",
                            "valor");

                } else {
                    vistaFuncionarioEncontrado = null;
                }
            }

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Nuevo Trabajo de Grado (obtenerFuncionarioDeVistaFuncionaroOInvestigadores) ", e);

        }
        return vistaFuncionarioEncontrado;

    }

    //Metodo para obtener una vistFuncioanrio de la tabla vistaFuncionario o de la tabla Investigadores
    public VistaFuncionario obtenerFuncionarioDeVistaFuncionaroOInvestigadores(String identificacion) {

        VistaFuncionario vistaFuncionarioEncontrado = new VistaFuncionario();

        try {
            //Se busca la identificacion en la tabla VistaFuncionario            
            vistaFuncionarioEncontrado = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacion);

            //Si no se encuentra en la tabla VistaFuncionario se busca en la tabla Investigadores
            if (vistaFuncionarioEncontrado == null) {

                Investigador investigadorFuncionario = new Investigador();
                investigadorFuncionario = iInvestigadorLocal.getInvestigadorPorNumeroIdentificacion(identificacion);

                //Si se encuentra un investigador, se completan los datos de funcionario encontrado con los del investigador
                if (investigadorFuncionario != null) {
                    vistaFuncionarioEncontrado = convertirInvestigadorEnVistaFuncionario(investigadorFuncionario);
                } else {
                    vistaFuncionarioEncontrado = null;
                }
            }

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Nuevo Trabajo de Grado (obtenerFuncionarioDeVistaFuncionaroOInvestigadores) ", e);

        }
        return vistaFuncionarioEncontrado;

    }

    //Metodo que convierte los datos de Investigador a VistaFuncionario
    public VistaFuncionario convertirInvestigadorEnVistaFuncionario(Investigador investigador) {

        VistaFuncionario vistaFuncionarioInvestigador = new VistaFuncionario();

        vistaFuncionarioInvestigador.setIdentificacion(investigador.getNumeroIdentificacion());
        vistaFuncionarioInvestigador.setNombreCompleto(investigador.getNombres() + " " + investigador.getApellidos());
        vistaFuncionarioInvestigador.setCorreo(investigador.getCorreoElectronico());
        vistaFuncionarioInvestigador.setGrado(investigador.getTipoVinculacion().getValor());
        vistaFuncionarioInvestigador.setCargo("INVESTIGADOR (A) Y/O ANALISTA");
        vistaFuncionarioInvestigador.setNombres(investigador.getNombres());
        vistaFuncionarioInvestigador.setApellidos(investigador.getApellidos());
        vistaFuncionarioInvestigador.setCodigoCargo("24207");
        vistaFuncionarioInvestigador.setDireccionEmpleado(investigador.getDireccion());
        vistaFuncionarioInvestigador.setDepartamentoReside(investigador.getNombreDeptoResidencia());
        vistaFuncionarioInvestigador.setCodigoDepartamentoReside(investigador.getCodigoDeptoResidencia());
        vistaFuncionarioInvestigador.setCiudadReside(investigador.getCodigoCiudadResidencia());
        vistaFuncionarioInvestigador.setCodigoCiudadReside(investigador.getCodigoCiudadResidencia());
        vistaFuncionarioInvestigador.setTelefono(investigador.getTelefono());
        vistaFuncionarioInvestigador.setCiudadExpedicionDocumento(investigador.getNombreCiudadExpedicion());
        vistaFuncionarioInvestigador.setCodigoCiudadExpedicionDocumento(investigador.getCodigoCiudadExpedicion());
        vistaFuncionarioInvestigador.setDescripcion(investigador.getFuncionInvestigador());

        return vistaFuncionarioInvestigador;

    }

    /**
     * Método calcular el promedio de las notas del trabajo de grado
     *
     */
    public void calcularNotaFinalTrabajoDeGrado() {

        /*if (notaTrabajoDeGrado == null) {
         adicionaMensajeError("Por favor ingrese la nota de trabajo");
         }
        
         else if (notaSustentacionTrabajoDeGrado == null) {
         adicionaMensajeError("Por favor ingrese la nota de la sustentacion");
         }*/
        if (notaTrabajoDeGrado != null && notaSustentacionTrabajoDeGrado != null) {

            try {

                BigDecimal divisor = new BigDecimal("2.0");
                BigDecimal sumando = new BigDecimal("0.0");
                sumando = notaTrabajoDeGrado.add(notaSustentacionTrabajoDeGrado);
                notaFinalTrabajoDeGrado = sumando.divide(divisor);

                notaFinalTrabajoDeGrado.setScale(2, RoundingMode.FLOOR);

            } catch (Exception e) {

                adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Calcular promedio de notas (calcularNotaFinalTrabajoDeGrado) ", e);

            }
        }
    }

    //Metodo que agrega un nuevo investigador o Asesor al trabajo de grado
    //Se requiere que el trabajo de grado halla sido guardado
    public void agregarInvestigadorOAsesorTrabajoDeGrado() {

        try {

            if (!validarAgregarInactivarInvertigadorOAsesor()) {
                return;
            }

            agregarInvestigadorOAsesorTrabajoDeGrado(IConstantes.FUNCIONARIO_INVESTIGADOR_ACTIVO);

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar / Modificar Trabajo de grado (agregarInvestigadorOAsesorTrabajoDeGrado) ", e);

        }

    }

    /**
     * Metodo que valida si es posible agregar y activar un investigador o asesr
     * al trabajo de grado
     *
     * @return
     */
    public boolean validarAgregarInactivarInvertigadorOAsesor() {

        try {
            //Validamos que el proyecto halla sido guardado en la Base de Datos
            Proyecto proyecto = iProyectoLocal.obtenerProyectoPorId(proyectoSeleccionado.getIdProyecto());

            if (vistaFuncionario == null || vistaFuncionario.getIdentificacion() == null) {

                adicionaMensajeError("Primero ingrese un documento de identidad válido");
                return false;

            } else if (proyecto == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_trabajo_de_grado_no_guardado"));
                return false;
            } //Si se va a asociar un investigador principal validamos que ID_UNIDAD_POLICIAL coincida con COD_UNID_LAB de VISTA_FUNCIONARIO
            /**
             * else if
             * (idTipoInvestigadorSeleccionado.equals(IConstantes.TIPO_INVESTIGADOR_TRABAJO_GRADO_INVESTIGADOR_PRINCIPAL))
             * {
             *
             * if
             * (!String.valueOf(idUnidadPolicial).equals(vistaFuncionario.getCodigoUnidadLaboral()))
             * {
             * adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_investigador_principal_no_pertenece_unidad_policial"));
             * return false; }
      }
             */

        } catch (JpaDinaeException e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-TR-01 Agregar Talento humano Trabajo de Grado (validarAgregarInactivarInvertigadorOAsesor) ", e);
        }
        return true;
    }

    /**
     * Metodo que agrega y guarda un investigador o asesor a un trabajo de grado
     *
     * @param activoInactivo
     * @throws Exception
     */
    private void agregarInvestigadorOAsesorTrabajoDeGrado(Character activoInactivo) throws Exception {

        investigadorProyecto.setActivo(activoInactivo);
        investigadorProyecto.setCargo(vistaFuncionario.getCargo());
        investigadorProyecto.setCorreo(vistaFuncionario.getCorreo());
        investigadorProyecto.setGrado(vistaFuncionario.getGrado());
        investigadorProyecto.setIdentificacion(vistaFuncionario.getIdentificacion());
        investigadorProyecto.setNombreCompleto(vistaFuncionario.getNombreCompleto());
        investigadorProyecto.setProyecto(proyectoSeleccionado);
        investigadorProyecto.setTelefono(vistaFuncionario.getTelefono());
        investigadorProyecto.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));
        investigadorProyecto.setFechaRegistro(new Date());
        investigadorProyecto.setTipoVinculacion(iConstantesLocal.getConstantesPorIdConstante(idTipoVinculacionSeleccionado));
        investigadorProyecto.setTipoInvestigador(iConstantesLocal.getConstantesPorIdConstante(idTipoInvestigadorSeleccionado));
        investigadorProyecto.setFuncionProyecto(iConstantesLocal.getConstantesPorIdConstante(idTipoInvestigadorSeleccionado).getValor());
        //investigadorProyecto.setInstitucionExterna(institucionExterna);

        //Se guarda el investigador o el asesor
        iInvestigadorProyectoLocal.guardarInvestigadorProyecto(investigadorProyecto);

        //Si el investigador guardado es el investigador principal se debe asignar crear el usuario rol en el sistema
        if (investigadorProyecto.getTipoInvestigador().equals(new Constantes(IConstantes.TIPO_INVESTIGADOR_TRABAJO_GRADO_INVESTIGADOR_PRINCIPAL))) {
            guardarInvestigadorPrincipalEnUsiarioRolYUsuarioUnidadPolicial(investigadorProyecto.getIdentificacion(), investigadorProyecto.getUnidadPolicial().getIdUnidadPolicial());
        }

        //Se recargan las listas de investigadores y asesores del trabajo de grado
        listaInvestigadoresTrabajoDeGrado = cargarListaInvestigadorTrabajoDeGrado(proyectoSeleccionado.getIdProyecto());
        listaAsesoresTrabajoDeGrado = cargarListaAsesorTrabajoDeGrado(proyectoSeleccionado.getIdProyecto());

        //Se reinician los objetos para agregar otro investigador o asesor al trabajo de grado
        vistaFuncionario = null;
        investigadorProyecto = new InvestigadorProyecto();
        idTipoVinculacionSeleccionado = null;
        idTipoInvestigadorSeleccionado = null;
        identificacionFuncionario = null;
        ciudadFuncionario = null;

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajo_de_grado_invest"));

    }

    //Metodo que guarda el investigador principal en Usuario_rol y usuario_unidad_policial
    public void guardarInvestigadorPrincipalEnUsiarioRolYUsuarioUnidadPolicial(String identificacion, Long idUnidadPolicialInvestigadorPrincipal) throws Exception {

        try {

            //Validaciones
            //El usuario NO debe estar en USUARIO_ROL como ROL 30 (Investigador Principal)
            List<RolUsuarioDTO> listaRolUsuarioDTOPorIdentificacion = iUsuarioRolLocal.getAllRolUsuarioDTOByIdentificacion(identificacion);

            if (listaRolUsuarioDTOPorIdentificacion != null && listaRolUsuarioDTOPorIdentificacion.size() > 0) {
                for (RolUsuarioDTO unRolUsuarioDTO : listaRolUsuarioDTOPorIdentificacion) {
                    if (unRolUsuarioDTO.getIdRol().equals(IConstantesRole.INVESTIGADOR_PRINCIPAL)) {
                        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_investigador_principal_ya_tiene_rol"));
                        return;
                    }
                }
            }
            //Se debe guardar por aparte el usuario rol y por aparte el usuariounidadpolicial
            guardarUsuarioRolInvestigadorPrincipalPorIdentificacion(identificacion);

            //Validaciones
            //El usuario NO debe tener la Unidadpolicial asociada
            List<UnidadPolicialDTO> listaUnidadPolicialDTOPorIdentificacion = iUsuarioUnidadPolicialLocal.getAllUnidadPolicialDTOPorIdentificacion(identificacion);
            if (listaUnidadPolicialDTOPorIdentificacion != null && listaUnidadPolicialDTOPorIdentificacion.size() > 0) {
                for (UnidadPolicialDTO unUnidadPolicialDTO : listaUnidadPolicialDTOPorIdentificacion) {
                    if (unUnidadPolicialDTO.getIdUnidadPolicial().equals(idUnidadPolicial)) {
                        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_investigador_principal_ya_pertenece_unidad"));
                        return;
                    }
                }
            }
            guardarUsuarioUnidadPolicialPorIdentificacionEIdUnidadPolicial(identificacion, idUnidadPolicial);
        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 guardarInvestigadorPrincipalEnUsiarioRolYUsuarioUnidadPolicial) ", e);

        }
    }

    //Metodo que guarda el usuario rol de un investigador principal
    public void guardarUsuarioRolInvestigadorPrincipalPorIdentificacion(String identificacion) throws Exception {

        try {

            UsuarioRol usuarioRolInvestigadorPrincipal = new UsuarioRol();
            usuarioRolInvestigadorPrincipal.setIdentificadorUsuario(identificacion);
            usuarioRolInvestigadorPrincipal.setRol(new Rol(IConstantesRole.INVESTIGADOR_PRINCIPAL));
            usuarioRolInvestigadorPrincipal.setActivo("S");

            iUsuarioRolLocal.guardarUsuarioRol(usuarioRolInvestigadorPrincipal);

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_investigador_principal_usuario_rol_guardado_ok"));

        } catch (Exception e) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 guardarUsuarioRolInvestigadorPrincipalPorIdentificacion) ", e);

        }
    }

    //Metodo que guarda el usuario unidad policial por identificacion y unidad policial
    public void guardarUsuarioUnidadPolicialPorIdentificacionEIdUnidadPolicial(String identificacion, Long idUnidadPolicialInvestigadorPrincipal) throws Exception {

        try {

            UsuarioUnidadPolicial usuarioUnidadPolicialInvestigadorPrincipal = new UsuarioUnidadPolicial();
            usuarioUnidadPolicialInvestigadorPrincipal.setIdentificadorUsuario(identificacion);
            usuarioUnidadPolicialInvestigadorPrincipal.setUnidadPolicial(new UnidadPolicial(idUnidadPolicialInvestigadorPrincipal));

            iUsuarioUnidadPolicialLocal.guardarUsuarioUnidadPolicial(usuarioUnidadPolicialInvestigadorPrincipal);
            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_investigador_principal_usuario_unidad_policial_guardado_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 guardarUsuarioRolInvestigadorPrincipalPorIdentificacion) ", e);

        }
    }

    /**
     * Metodo que carga la lista de investigadores asociados a un trabajo de
     * grado
     *
     * @param idProyecto
     * @return
     * @throws Exception
     */
    private List<InvestigadorProyecto> cargarListaInvestigadorTrabajoDeGrado(Long idProyecto) throws Exception {

        if (idProyecto == null) {

            return new ArrayList<InvestigadorProyecto>();

        }
        return iInvestigadorProyectoLocal.getListaInvestigadorProyectoTipoInvestigadorPorProyecto(idProyecto);

    }

    /**
     * Metodo que carga la lista de asesores asociados a un trabajo de grado
     *
     * @param idProyecto
     * @return
     * @throws Exception
     */
    private List<InvestigadorProyecto> cargarListaAsesorTrabajoDeGrado(Long idProyecto) throws Exception {

        if (idProyecto == null) {

            return new ArrayList<InvestigadorProyecto>();

        }
        return iInvestigadorProyectoLocal.getListaInvestigadorProyectoTipoAsesorPorProyecto(idProyecto);

    }

    /**
     *
     * @return
     */
    public String guardarDocumentacion() {

        try {

            if (eventArchivoSubido == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_documentacion_archivo_obligatorio"));
                return null;
            }
            //VERIFICAMOS SI LA RUTA FISICA DONDE SE ALMACENAN LOS ARCHIVOS EXISTE
            //Y LOS PERMISOS SON VALIDOS
            File directorioFinal = null;

            try {

                directorioFinal = new File(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion"));

            } catch (NullPointerException e) {
                adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
                return null;
            }

            if (!directorioFinal.exists()) {
                adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
                return null;
            }

            if (directorioFinal.isFile()) {
                adicionaMensajeError("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
                return null;
            }

            if (!directorioFinal.canWrite()) {
                adicionaMensajeError("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion")));
                return null;
            }

            String[] archivo = almacenarArchivoFisico();

            documentacionProyectoSeleccionado.setProyecto(proyectoSeleccionado);
            documentacionProyectoSeleccionado.setNombreArchivo(archivo[0] != null ? archivo[0] : documentacionProyectoSeleccionado.getNombreArchivo());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
            documentacionProyectoSeleccionado.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : documentacionProyectoSeleccionado.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO
            documentacionProyectoSeleccionado.setFechaPresentacion(new Date());
            documentacionProyectoSeleccionado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

            //Se debe registrar el idUsuarioRol de quien carga el documento, si es investigador principal o responsable de trabajos de grado
            if (isModoInvestigadorPrincipal()) {
                documentacionProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
                        loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                                IConstantesRole.INVESTIGADOR_PRINCIPAL).getIdUsuarioRol()));
            } else {
                documentacionProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
                        loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                                IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));
            }
            iDocumentacionProyectoLocal.guardarDocumentacionProyecto(documentacionProyectoSeleccionado);

            //Se reinician las variables de documento
            documentacionProyectoSeleccionado = new DocumentacionProyecto();
            documentacionProyectoSeleccionado.setTipoDocumento(new Constantes());
            eventArchivoSubido = null;

            cargarListaDocumentacionTrabajoDeGrado();

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_documentacion_btn_guardar_info_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Guardar Documentacion Trabajo de Grado (guardarDocumentacion) ", e);

        }
        return null;
    }

    /**
     * Metodo que almacena un archivo en un directorio físico Retorna true o
     * false dependiendo si el archivo fue creado correctamente
     *
     * @return
     */
    private String[] almacenarArchivoFisico() {

        //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
        try {

            String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();
            String extension = "";
            int i = nombreArchivoOriginal.lastIndexOf('.');
            if (i > 0) {
                extension = nombreArchivoOriginal.substring(i);
            }
            String nombreArchivoFisico = "DOCUMENTACION_TRABAJO_DE_GRADO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

            copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion"), eventArchivoSubido.getFile().getInputstream(),
                    nombreArchivoFisico);

            //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
            return new String[]{nombreArchivoOriginal, nombreArchivoFisico};

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Guardar Documentacion Trabajo de Grado (almacenarArchivoFisico) ", e);
        }

        return null;

    }

    /**
     *
     * @throws Exception
     */
    private void cargarListaDocumentacionTrabajoDeGrado() throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

            listaDocumentacionTrabajoDeGradoDTO = new ArrayList<DocumentacionProyectoDTO>();
            return;
        }

        listaDocumentacionTrabajoDeGradoDTO = iDocumentacionProyectoLocal.getListaDocumentacionProyectoDTOByIdProyecto(proyectoSeleccionado.getIdProyecto());

        //Se debe validar si cada documento tiene comentarios asociados para visualizar el link Ver Comentarios        
        if (listaDocumentacionTrabajoDeGradoDTO != null && listaDocumentacionTrabajoDeGradoDTO.size() > 0) {
            for (DocumentacionProyectoDTO unDocumentacionProyectoDTO : listaDocumentacionTrabajoDeGradoDTO) {
                List<ComentarioProyecto> listaValidacionComentariosDocumentacionProyecto = new ArrayList<ComentarioProyecto>();
                listaValidacionComentariosDocumentacionProyecto = iComentarioProyectoLocal.getListaComentarioProyectoPorIdDocumProyecto(unDocumentacionProyectoDTO.getIdDocumProyecto());
                if (listaValidacionComentariosDocumentacionProyecto != null && listaValidacionComentariosDocumentacionProyecto.size() > 0) {
                    unDocumentacionProyectoDTO.setTieneComentarios(true);
                }
            }

        }

    }

    //Metodo que invoca la visualizacion de los comentarios de un documento asociado al trabajo de grado  
    public void verComentariosProyectoDocumentacionTrabajoDeGrado(DocumentacionProyectoDTO documento) {

        try {

            //Se reinicializa la lista cada vez que se desee ver los comentarios de un tipo de documento
            listaComentarioProyectoDTODocumentoSeleccionado = new ArrayList<ComentarioProyectoDTO>();

            //Se asigna el obeto DocumentacionProyectoDTO enviado desde el formulario            
            documentacionTrabajoDeGradoDTOComentariarSeleccionado = documento;

            //Validaciones
            if (documentacionTrabajoDeGradoDTOComentariarSeleccionado == null) {
                return;
            }

            //Se asigna el titulo del documento a visualizar comentarios
            nombreTipoDocumentoComentariarSeleccionado = documentacionTrabajoDeGradoDTOComentariarSeleccionado.getNombreTipoDocumento();

            verComentarioDocumento = true;

            //Se obtiene la lista de documentacionProyectoDTO a partir del documentacionTrabajoDeGradoDTO
            cargarListaComentarioProyectoDocumentacionProyecto(documentacionTrabajoDeGradoDTOComentariarSeleccionado.getIdDocumProyecto());

            //Se reinicializa el objeto consultado
            documentacionTrabajoDeGradoDTOComentariarSeleccionado = null;

            //adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_evidencia_btn_eliminar_documentacion_ok"));
        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Ver Comentarios Documentacion Trabajo de Grado (verComentariosProyectoDocumentacionTrabajoDeGrado) ", e);

        }

    }

    //Metodo que habilita el guardado de un comentarioProyecto asociado a un objeto DocumentacionProyectoDTO    
    public void agregarComentariosProyectoDocumentacionTrabajoDeGrado(DocumentacionProyectoDTO documento) {

        try {

            //Se asigna el obeto DocumentacionProyectoDTO enviado desde el formulario            
            documentacionTrabajoDeGradoDTOComentariarSeleccionado = documento;

            //Validaciones
            if (documentacionTrabajoDeGradoDTOComentariarSeleccionado == null) {
                return;
            }

            //Se asigna el titulo del documento a visualizar comentarios
            nombreTipoDocumentoComentariarSeleccionado = documentacionTrabajoDeGradoDTOComentariarSeleccionado.getNombreTipoDocumento();

            agregarComentarioDocumento = true;

            //Se recarga la lista de comentarios para el tipo de documento seleccionado
            verComentarioDocumento = true;
            cargarListaComentarioProyectoDocumentacionProyecto(documentacionTrabajoDeGradoDTOComentariarSeleccionado.getIdDocumProyecto());

            //Se crea el objeto comentarioProyecto a ser persistido y se asocia con el IdDocumProyecto y el IdProyecto del documento seleccionado
            comentarioProyectoDocumentoSeleccionado = new ComentarioProyecto();
            comentarioProyectoDocumentoSeleccionado.setDocumentacionProyecto(new DocumentacionProyecto(documentacionTrabajoDeGradoDTOComentariarSeleccionado.getIdDocumProyecto()));
            comentarioProyectoDocumentoSeleccionado.setProyecto(new Proyecto(documentacionTrabajoDeGradoDTOComentariarSeleccionado.getIdProyecto()));

            //Se reinicializa el objeto consultado
            documentacionTrabajoDeGradoDTOComentariarSeleccionado = null;

            //adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_evidencia_btn_eliminar_documentacion_ok"));
        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Ver Comentarios Documentacion Trabajo de Grado (agregarComentariosProyectoDocumentacionTrabajoDeGrado) ", e);

        }

    }

    /**
     * Metodo que carga la lista de comentarios asociados a la
     * documentacionProyecto de un proyecto Este metodo se usa por demanda desde
     * un actionListener de la tabla
     *
     * @throws Exception
     */
    private void cargarListaComentarioProyectoDocumentacionProyecto(Long idDocumentacionProyecto) throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null || listaDocumentacionTrabajoDeGradoDTO.isEmpty()) {

            listaComentarioProyectoDTODocumentoSeleccionado = new ArrayList<ComentarioProyectoDTO>();
            return;
        }

        listaComentarioProyectoDTODocumentoSeleccionado = iComentarioProyectoLocal.getListaComentarioProyectoDTOPorIdProyectoAndIdDocumProyectoTrabajoGrado(proyectoSeleccionado.getIdProyecto(), idDocumentacionProyecto);

    }

    /**
     * Metodo que guarda un comentarioProyecto asociado a un objeto
     * DocumentacionProyectoDTO previamente creado
     *
     * @return
     */
    public String guardarComentarioDocumento() {

        try {

            //Validaciones
            if (comentarioProyectoDocumentoSeleccionado == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_documentacion_comentario_guardar_comentario"));
                return null;
            }

            if (comentarioProyectoDocumentoSeleccionado.getComentario() == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_documentacion_comentario_documento_vacio"));
                return null;
            }
            comentarioProyectoDocumentoSeleccionado.setIdentificacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
            comentarioProyectoDocumentoSeleccionado.setNombreCompleto(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());

            //Se debe registrar el idUsuarioRol de quien carga el documento, si es investigador principal o responsable de trabajos de grado
            if (isModoInvestigadorPrincipal()) {
                comentarioProyectoDocumentoSeleccionado.setUsuarioRol(new UsuarioRol(
                        loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                                IConstantesRole.INVESTIGADOR_PRINCIPAL).getIdUsuarioRol()));
            } else {
                comentarioProyectoDocumentoSeleccionado.setUsuarioRol(new UsuarioRol(
                        loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                                IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));
            }
            //Se guarda el comentario proyecto
            iComentarioProyectoLocal.guardarComentarioProyecto(comentarioProyectoDocumentoSeleccionado);

            //Se recarga la lista de documentacion proyecto
            cargarListaDocumentacionTrabajoDeGrado();

            //se recarga la lista de comentarios persistidos
            listaComentarioProyectoDTODocumentoSeleccionado = new ArrayList<ComentarioProyectoDTO>();
            cargarListaComentarioProyectoDocumentacionProyecto(comentarioProyectoDocumentoSeleccionado.getDocumentacionProyecto().getIdDocumProyecto());

            //Se reinician las variables para comentarios
            comentarioProyectoDocumentoSeleccionado = new ComentarioProyecto();
            documentacionTrabajoDeGradoDTOComentariarSeleccionado = null;
            agregarComentarioDocumento = false;
            //Permite ver los comentarios despues de guardarlos
            verComentarioDocumento = true;

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_documentacion_btn_guardar_comentario_info_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Guardar Documentacion Trabajo de Grado (guardarDocumentacion) ", e);

        }
        return null;
    }

    /**
     * Metodo usado para la descarga de un archivo
     *
     * @return
     */
    public StreamedContent getDownloadContentProperty() {

        try {

            if (documentacionTrabajoDeGradoDTODescargarSeleccionado != null && documentacionTrabajoDeGradoDTODescargarSeleccionado.getNombreArchivo() != null) {

                String name = keyPropertiesFactory.value("cu_tr_01_dir_file_archivo_documentacion") + documentacionTrabajoDeGradoDTODescargarSeleccionado.getNombreArchivoFisico();

                InputStream stream = new FileInputStream(name);
                String contentType = "application/octet-stream";

                StreamedContent download = new DefaultStreamedContent(stream, contentType, documentacionTrabajoDeGradoDTODescargarSeleccionado.getNombreArchivo());

                documentacionTrabajoDeGradoDTODescargarSeleccionado = null;

                return download;
            }

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
        }
        return null;
    }

    /**
     * Metoo encargado de eliminar un documento
     */
    public void eliminarDocumento() {

        try {

            if (documentacionTrabajoDeGradoDTOEliminarSeleccionado == null) {
                return;
            }
            //Se eliminan los hijos de Documentacion Proyecto
            List<ComentarioProyecto> listaComentarioProyectoEliminar = new ArrayList<ComentarioProyecto>();
            listaComentarioProyectoEliminar = iComentarioProyectoLocal.getListaComentarioProyectoPorIdDocumProyecto(documentacionTrabajoDeGradoDTOEliminarSeleccionado.getIdDocumProyecto());

            if (listaComentarioProyectoEliminar != null && listaComentarioProyectoEliminar.size() > 0) {
                for (ComentarioProyecto unComentarioProyectoEliminar : listaComentarioProyectoEliminar) {
                    iComentarioProyectoLocal.eliminarComentarioProyectoPorIdComentarioProyecto(unComentarioProyectoEliminar.getIdComentarioProyecto());
                }
            }

            //Se elimina la documentacion proyecto
            iDocumentacionProyectoLocal.eliminarDocumentacionProyecto(documentacionTrabajoDeGradoDTOEliminarSeleccionado.getIdDocumProyecto());

            documentacionTrabajoDeGradoDTOEliminarSeleccionado = null;

            //Se impide ver el pane de comentarios del documento eliminado
            verComentarioDocumento = false;

            //Se actualiza la lista
            cargarListaDocumentacionTrabajoDeGrado();

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_evidencia_btn_eliminar_documentacion_ok"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Ingresar Documentacion Trabajo de Grado (eliminarDocumento) ", e);

        }

    }

    /**
     * Metodo que asocia un evaluador a un trabajo de grado antes de ser
     * guardado con una evaluacion de trabajo de grado
     *
     * @throws Exception
     */
    public void asociarEvaluadorTrabajoDeGrado() throws Exception {

        //Validaciones
        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {
            listaEvaluadoresTrabajoDeGrado = new ArrayList<EvaluadoresProyectoGrado>();
        }

        if (vistaFuncionarioEvaluador == null || vistaFuncionarioEvaluador.getIdentificacion().equals("")) {
            adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_falta_funcionario_evaluador"));
            return;
        }

        //Se crea el objeto evaluador Evaluador proyecto
        evaluadoresProyectoGrado = new EvaluadoresProyectoGrado();
        evaluadoresProyectoGrado.setIdentificacion(vistaFuncionarioEvaluador.getIdentificacion());
        evaluadoresProyectoGrado.setIdUsuarioRol(loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol());
        evaluadoresProyectoGrado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
        evaluadoresProyectoGrado.setGrado(vistaFuncionarioEvaluador.getGrado());
        evaluadoresProyectoGrado.setCorreo(vistaFuncionarioEvaluador.getCorreo());
        evaluadoresProyectoGrado.setCargo(vistaFuncionarioEvaluador.getCargo());
        evaluadoresProyectoGrado.setTelefono(vistaFuncionarioEvaluador.getTelefono());
        evaluadoresProyectoGrado.setNombreCompleto(vistaFuncionarioEvaluador.getNombreCompleto());
        evaluadoresProyectoGrado.setFechaRegistro(new Date());

        listaEvaluadoresTrabajoDeGrado.add(evaluadoresProyectoGrado);

        //Se refresca la lista de evaluadoresTrabajoDeGradoDTO (la que se muestra)
        //cargarListaEvaluadoresTrabajoDeGrado();
        //Se reinician los objetos para agregar otra evaluacion
        vistaFuncionarioEvaluador = null;
        evaluadoresProyectoGrado = new EvaluadoresProyectoGrado();
        identificacionFuncionarioEvaluador = null;

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_asociar_evaluador_trabajo_de_grado"));

    }

    /**
     * Metodo que agrega y guarda un evaluador a un trabajo de grado
     *
     * @throws Exception
     */
    public void guardarEvaluacionYEvaluadoresTrabajoDeGrado() throws Exception {

        try {

            //Validaciones
            if (notaTrabajoDeGrado == null || notaTrabajoDeGrado.compareTo(BigDecimal.ZERO) < 0) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_falta_nota_trabajo_de_grado"));
                return;
            }
            if (notaSustentacionTrabajoDeGrado == null || notaSustentacionTrabajoDeGrado.compareTo(BigDecimal.ZERO) < 0) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_falta_nota_trabajo_de_sustentacion"));
                return;
            }
            if (listaEvaluadoresTrabajoDeGrado == null || listaEvaluadoresTrabajoDeGrado.size() < 1) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_falta_asociar_evaluadores"));
                return;
            }
            if (fechaSustentacionTrabajoDeGrado == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_err_falta_fecha_sustentacion"));
                return;
            }

            /*Se crea el objeto evaluacion proyecto (Padre)
             if(evaluacionProyectogrado.getIdEvaluacionProyGrado() == null ){
             evaluacionProyectogrado = new EvaluacionProyectoGrado();
             }*/
            evaluacionProyectogrado = new EvaluacionProyectoGrado();
            evaluacionProyectogrado.setFechaSustentacion(fechaSustentacionTrabajoDeGrado);
            evaluacionProyectogrado.setNotaTrabajo(notaTrabajoDeGrado);
            evaluacionProyectogrado.setNotaSustentacion(notaSustentacionTrabajoDeGrado);
            evaluacionProyectogrado.setNotaFinal(notaFinalTrabajoDeGrado);
            evaluacionProyectogrado.setProyecto(proyectoSeleccionado);
            evaluacionProyectogrado.setFechaRegistro(new Date());
            evaluacionProyectogrado.setUsuarioRol(new UsuarioRol(
                    loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                            IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));
            evaluacionProyectogrado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

            //Se asocia la evaluacionProyectogrado a cada uno de los evaluadores de la lista        
            for (EvaluadoresProyectoGrado unEvaluadorProyectoGrado : listaEvaluadoresTrabajoDeGrado) {
                unEvaluadorProyectoGrado.setEvaluacionProyectoGrado(evaluacionProyectogrado);
            }

            //Se Agrega la lista de evaluadores a la evaluacionProyectoGradoa persistir        
            evaluacionProyectogrado.setEvaluadoresProyectoGradoList(listaEvaluadoresTrabajoDeGrado);

            //Se guarda la evaluacion
            evaluacionProyectogrado = iEvaluacionProyectoGradoLocal.guardarEvaluacionProyectoGrado(evaluacionProyectogrado);

            //Se asigna el estado Evaluado (112) al proyecto de grado
            proyectoSeleccionado.setEstado(iConstantesLocal.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO));

            //Se guarda el proyecto con el nuevo estado
            proyectoSeleccionado = iProyectoLocal.guardarProyecto(proyectoSeleccionado);

            //Se refrescala lista de evaluadores de trabajo de grado
            cargarListaEvaluadoresTrabajoDeGrado();

            //Se refresca los datos de la evaluacion
            cargarEvaluacionTrabajoDeGrado();

            //Se reinician los objetos para agregar otra evaluacion
            vistaFuncionarioEvaluador = null;
            evaluadoresProyectoGrado = new EvaluadoresProyectoGrado();
            //listaEvaluadoresTrabajoDeGrado = new ArrayList<EvaluadoresProyectoGrado>();
            //evaluacionProyectogrado = new EvaluacionProyectoGrado();        
            identificacionFuncionarioEvaluador = null;
            //notaTrabajoDeGrado = null;
            //notaSustentacionTrabajoDeGrado = null;
            //notaFinalTrabajoDeGrado = null;
            //fechaSustentacionTrabajoDeGrado = null;

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajo_de_grado_evaluador"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01", e);

        }

    }

    /**
     * Metodo que recarga la lista de evaluadores y evaluaciones del trabajo de
     * grado
     *
     * @throws Exception
     */
    private void cargarListaEvaluadoresTrabajoDeGrado() throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

            if (listaEvaluadoresTrabajoDeGrado == null || listaEvaluadoresTrabajoDeGrado.isEmpty()) {
                //listaEvaluadoresTrabajoDeGradoDTO = new ArrayList<EvaluadoresProyectoGradoDTO>();
                listaEvaluadoresTrabajoDeGrado = new ArrayList<EvaluadoresProyectoGrado>();
            }
            return;
        }

        //Se carga la lista de evaluadores asociados a la evaluacion (para pdoer actualizar)
        //List<EvaluadoresProyectoGrado> listaEvaluadoresTrabajoDeGradoPersistidos = new ArrayList<EvaluadoresProyectoGrado>();
        //listaEvaluadoresTrabajoDeGradoPersistidos = iEvaluadoresProyectoGradoLocal.getListaEvaluadoresProyectoByIdProyecto(proyectoSeleccionado.getIdProyecto());
        listaEvaluadoresTrabajoDeGrado = iEvaluadoresProyectoGradoLocal.getListaEvaluadoresProyectoByIdProyecto(proyectoSeleccionado.getIdProyecto());

        /*if(listaEvaluadoresTrabajoDeGradoPersistidos != null && listaEvaluadoresTrabajoDeGradoPersistidos.size() > 0){
         listaEvaluadoresTrabajoDeGrado.addAll(listaEvaluadoresTrabajoDeGradoPersistidos);
         }*/
        //Se busca el nombre completo para cada evaluador        
        if (listaEvaluadoresTrabajoDeGrado != null && listaEvaluadoresTrabajoDeGrado.size() > 0) {
            for (EvaluadoresProyectoGrado unEvaluadorProyectoGrado : listaEvaluadoresTrabajoDeGrado) {
                VistaFuncionario vistaFuncionarioEvaluadorPersistido = new VistaFuncionario();
                //vistaFuncionarioEvaluadorPersistido = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(unEvaluadorProyectoGrado.getIdentificacion());
                vistaFuncionarioEvaluadorPersistido = obtenerFuncionarioDeVistaFuncionaroOInvestigadores(unEvaluadorProyectoGrado.getIdentificacion());
                if (vistaFuncionarioEvaluadorPersistido != null) {
                    unEvaluadorProyectoGrado.setNombreCompleto(vistaFuncionarioEvaluadorPersistido.getNombreCompleto());
                }
            }
        }

    }

    /**
     * Metodo que recarga los datos de la evaluacion de un trabajo de grado
     *
     * @throws Exception
     */
    private void cargarEvaluacionTrabajoDeGrado() throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {
            evaluacionProyectogrado = new EvaluacionProyectoGrado();
            return;
        }
        //Se carga la evaluacion asociada al trabajo de grado
        evaluacionProyectogrado = iEvaluacionProyectoGradoLocal.getEvaluacionProyectoByIdProyecto(proyectoSeleccionado.getIdProyecto());

        if (evaluacionProyectogrado != null) {
            notaTrabajoDeGrado = evaluacionProyectogrado.getNotaTrabajo();
            notaSustentacionTrabajoDeGrado = evaluacionProyectogrado.getNotaSustentacion();
            notaFinalTrabajoDeGrado = evaluacionProyectogrado.getNotaFinal();
            fechaSustentacionTrabajoDeGrado = evaluacionProyectogrado.getFechaSustentacion();
        }
    }

    /**
     * Metodo que agrega y guarda un evaluador a un trabajo de grado
     *
     * @throws Exception
     */
    public void guardarAsesoriaTrabajoDeGrado() throws Exception {

        try {
            asesoriaTrabajoDeGrado = new AsesoriaProyecto();
            modalidadesAsesoria = new ArrayList<ModalidadAsesoriaProyecto>();

            if (resultadoAsesoria == null || resultadoAsesoria.trim().length() == 0 || resultadoAsesoria.trim().equals("<br>")) {
                adicionaMensajeError("El Resultado Asesoría es obligatorio.");
                return;
            }

            asesoriaTrabajoDeGrado.setResultadoAsesoria(resultadoAsesoria);
            asesoriaTrabajoDeGrado.setProyecto(proyectoSeleccionado);
            asesoriaTrabajoDeGrado.setUsuarioRol(new UsuarioRol(
                    loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                            IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));
            asesoriaTrabajoDeGrado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

            //Se crea un objeto ModalidadAsesoriaProyecto por cada tipo de modalidad seleccionada de la lista modalidadesAsesoriaSeleccionadas
            for (String unaModalidadAsesoriaProyectoSeleccionada : modalidadesAsesoriaSeleccionadas) {

                ModalidadAsesoriaProyecto unaModalidadAsesoriaProyecto = new ModalidadAsesoriaProyecto();
                unaModalidadAsesoriaProyecto.setTipoModalidad(new Constantes(Long.parseLong(unaModalidadAsesoriaProyectoSeleccionada)));
                unaModalidadAsesoriaProyecto.setUsuarioRol(new UsuarioRol(
                        loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                                IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));
                unaModalidadAsesoriaProyecto.setFechaRegistro(new Date());
                unaModalidadAsesoriaProyecto.setAsesoriaProyecto(asesoriaTrabajoDeGrado);

                //Se agrega la modalidadAsesoria a la lista a ser persistida
                modalidadesAsesoria.add(unaModalidadAsesoriaProyecto);
            }

            //Se vinculan las modalidadesAsesoriaProyecto con la asesoriaProyecto a guardar
            asesoriaTrabajoDeGrado.setModalidadesAsesoriaProyecto(modalidadesAsesoria);

            //Se guarda la asesoriaProyecto junto a sus modalidadAsesoriaProyecto
            asesoriaTrabajoDeGrado = iAsesoriaProyectoLocal.guardarAsesoriaProyecto(asesoriaTrabajoDeGrado);

            //Se recarga la lista de asesorias del trabajo de grado
            cargarlistaAsesoriasTrabajoDeGrado();

            //Se carga la lista de modalidades por cada asesoria del trabajo de grado
            //Se reinician los objetos para agregar otra asesoria
            asesoriaTrabajoDeGrado = new AsesoriaProyecto();
            modalidadesAsesoria = null;
            modalidadesAsesoriaSeleccionadas = null;
            resultadoAsesoria = null;

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_trabajo_de_grado_asesoria"));

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Otra Unidad Policial (guardarAsesoriaTrabajoDeGrado) ", e);

        }
    }

    /**
     * Metodo que recarga la lista de otras unidades policiales vinculadas
     *
     * @throws Exception
     */
    private void cargarlistaAsesoriasTrabajoDeGrado() throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

            listaAsesoriasTrabajoDeGrado = new ArrayList<AsesoriaProyecto>();
            return;
        }

        listaAsesoriasTrabajoDeGrado = iAsesoriaProyectoLocal.getListaAsesoriaProyectoByIdProyecto(
                proyectoSeleccionado.getIdProyecto());

        //Se obtiene el valor de cada modalidad asociada a una asesoria y los datos del evaluador de cada asesoria encontrada
        if (listaAsesoriasTrabajoDeGrado != null && listaAsesoriasTrabajoDeGrado.size() > 0) {
            for (AsesoriaProyecto unaAsesoriaTrabajoDeGrado : listaAsesoriasTrabajoDeGrado) {

                VistaFuncionario vistaFuncionarioAsesoriaEvaluador = new VistaFuncionario();
                vistaFuncionarioAsesoriaEvaluador = obtenerFuncionarioDeVistaFuncionaroOInvestigadores(unaAsesoriaTrabajoDeGrado.getUsuarioRol().getIdentificadorUsuario());

                unaAsesoriaTrabajoDeGrado.setNombresYApellidosEvaluador(vistaFuncionarioAsesoriaEvaluador.getNombreCompleto());
                unaAsesoriaTrabajoDeGrado.setGradoEvaluador(vistaFuncionarioAsesoriaEvaluador.getGrado());

                for (ModalidadAsesoriaProyecto unaModalidadAsesoriaProyecto : unaAsesoriaTrabajoDeGrado.getModalidadesAsesoriaProyecto()) {
                    unaModalidadAsesoriaProyecto.getTipoModalidad().setValor(iConstantesLocal.getConstantesPorIdConstante(unaModalidadAsesoriaProyecto.getTipoModalidad().getIdConstantes()).getValor());
                }
            }
        }

    }

    /**
     * Metodo que vincula una Unidad policial al trabajo de grado
     *
     * @param actionEvent
     */
    public void vincularOtraUnidadPolicialTrabajoDeGrado(ActionEvent actionEvent) {

        try {

            if (idOtraUnidadPolicialSeleccionada == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_msg_seleccione_uniadad"));
                return;
            }

            //Verificamos que la unidad policial no se halla adicionado previamente
            for (SemilleroProyectoDTO unSemilleroProyectoDTO : listaOtrasUnidadesPolicialesSemilleroProyectoDTO) {

                if (unSemilleroProyectoDTO.getIdUnidadPolicial().equals(idOtraUnidadPolicialSeleccionada)) {
                    adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_msg_seleccione_unidad_policial_ya_existe"));
                    return;
                }
            }

            SemilleroProyecto semilleroProyecto = new SemilleroProyecto();
            semilleroProyecto.setProyecto(proyectoSeleccionado);
            semilleroProyecto.setUnidadPolicial(new UnidadPolicial(idOtraUnidadPolicialSeleccionada));
            semilleroProyecto.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            semilleroProyecto.setAporteInvestigacion(aporteInvestigacionUnidadPolicial);

            iSemilleroProyectoLocal.guardarSemilleroProyecto(semilleroProyecto);

            cargarListaOtrasUnidadesPolicialesVinculadas();

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_adicionar_uniadad_policial_add_ok"));

            idOtraUnidadPolicialSeleccionada = null;
            aporteInvestigacionUnidadPolicial = null;
            mostrarDetalleOtraNuevaInstitucion = false;

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Otra Unidad Policial (vincularOtraUnidadPolicialTrabajoDeGrado) ", e);

        }
    }

    /**
     * Metodo que recarga la lista de otras unidades policiales vinculadas
     *
     * @throws Exception
     */
    private void cargarListaOtrasUnidadesPolicialesVinculadas() throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

            listaOtrasUnidadesPolicialesSemilleroProyectoDTO = new ArrayList<SemilleroProyectoDTO>();
            return;
        }

        listaOtrasUnidadesPolicialesSemilleroProyectoDTO = iSemilleroProyectoLocal.getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(
                proyectoSeleccionado.getIdProyecto());

    }

    //Metodo que permite agregar otra nueva institucion que no este en la lista
    public void handleOtrasInstitucionesChange() {
        mostrarDetalleOtraNuevaInstitucion = IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO.equals(idOtraInstitucionSeleccionada);
    }

    /**
     * Metodo que guarda otra institucion a un proyecto de grado
     *
     * @param actionEvent
     */
    public void vincularOtraInstitucionTrabajoDeGrado(ActionEvent actionEvent) {

        try {

            if (idOtraInstitucionSeleccionada == null) {
                adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_msg_seleccione_otras_intituciones_err_faltante"));
                return;
            }

            //Verificamos que la institucion no halla sido agregada previamente
            for (InstitucionesProyectoDTO unaInstitucionesProyectoDTO : listaOtrasInstitucionesProyectoDTO) {

                //Si la institucion seleccionada es OTRA se continua con la insercion
                if (IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO.equals(unaInstitucionesProyectoDTO.getIdInstitucion())) {
                    continue;
                }

                if (unaInstitucionesProyectoDTO.getIdInstitucion().equals(idOtraInstitucionSeleccionada)) {
                    adicionaMensajeError(keyPropertiesFactory.value("cu_tr_01_lbl_error_msg_seleccion_institucion_ya_existe"));
                    return;
                }
            }

            InstitucionesProyecto institucionesProyecto = new InstitucionesProyecto();
            institucionesProyecto.setProyecto(proyectoSeleccionado);
            institucionesProyecto.setAporteInvestigicacion(aporteInvestigacionOtrasInstituciones);
            institucionesProyecto.setInstitucion(new Constantes(idOtraInstitucionSeleccionada));
            //USUARIO ROL QUE ACTUALIZA EL REGISTRO
            institucionesProyecto.setUsuarioRol(new UsuarioRol(
                    loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                            IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA).getIdUsuarioRol()));

            if (IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO.equals(idOtraInstitucionSeleccionada)) {
                institucionesProyecto.setValorOtroTipo(otraNuevaInstitucion);
            }

            iInstitucionesProyectoLocal.guardarInstitucionesProyecto(institucionesProyecto);

            cargarListaOtrasInstitucionesVinculadas();

            adicionaMensajeInformativo(keyPropertiesFactory.value("cu_tr_01_lbl_info_adicionar_otra_institucion_add_ok"));

            idOtraInstitucionSeleccionada = null;
            aporteInvestigacionOtrasInstituciones = null;
            otraNuevaInstitucion = null;

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 Agregar Otra Institucion (vincularOtraInstitucionTrabajoDeGrado) ", e);

        }
    }

    /**
     * Metodo que carga la lista de instituciones vinculadas al trabajo de grado
     *
     * @throws Exception
     */
    private void cargarListaOtrasInstitucionesVinculadas() throws Exception {

        if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

            listaOtrasInstitucionesProyectoDTO = new ArrayList<InstitucionesProyectoDTO>();
            return;
        }

        listaOtrasInstitucionesProyectoDTO = iInstitucionesProyectoLocal.getListaInstitucionesProyectoPorProyecto(
                proyectoSeleccionado.getIdProyecto());

    }

    //SETERS AND GETTERS
    public List<Proyecto> getTrabajosDeGrado() {
        return trabajosDeGrado;
    }

    public void setTrabajosDeGrado(List<Proyecto> trabajosDeGrado) {
        this.trabajosDeGrado = trabajosDeGrado;
    }

    /**
     * @return the lblTitulo
     */
    public String getLblTitulo() {
        return lblTitulo;
    }

    /**
     * @param lblTitulo the lblTitulo to set
     */
    public void setLblTitulo(String lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * @return the idTabSeleccionado
     */
    public int getIdTabSeleccionado() {
        return idTabSeleccionado;
    }

    /**
     * @param idTabSeleccionado the idTabSeleccionado to set
     */
    public void setIdTabSeleccionado(int idTabSeleccionado) {
        this.idTabSeleccionado = idTabSeleccionado;
    }

    /**
     * @return the nombreEscuela
     */
    public String getNombreEscuela() {
        return nombreEscuela;
    }

    /**
     * @param nombreEscuela the nombreEscuela to set
     */
    public void setNombreEscuela(String nombreEscuela) {
        this.nombreEscuela = nombreEscuela;
    }

    /**
     * @return the idUnidadPolicial
     */
    public Long getIdUnidadPolicial() {
        return idUnidadPolicial;
    }

    /**
     * @param idUnidadPolicial the idUnidadPolicial to set
     */
    public void setIdUnidadPolicial(Long idUnidadPolicial) {
        this.idUnidadPolicial = idUnidadPolicial;
    }

    /**
     * @return the proyectoSeleccionado
     */
    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    /**
     * @param proyectoSeleccionado the proyectoSeleccionado to set
     */
    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    /**
     * @return the nombreProgramaSeleccionado
     */
    public Long getIdNombreProgramaSeleccionado() {
        return idNombreProgramaSeleccionado;
    }

    /**
     * @param idNombreProgramaSeleccionado the nombreProgramaSeleccionado to set
     */
    public void setIdNombreProgramaSeleccionado(Long idNombreProgramaSeleccionado) {
        this.idNombreProgramaSeleccionado = idNombreProgramaSeleccionado;
    }

    /**
     * @return the listaNombreProgramaItem
     */
    public List<SelectItem> getListaNombreProgramaItem() {
        return listaNombreProgramaItem;
    }

    /**
     * @param listaNombreProgramaItem the listaNombreProgramaItem to set
     */
    public void setListaNombreProgramaItem(List<SelectItem> listaNombreProgramaItem) {
        this.listaNombreProgramaItem = listaNombreProgramaItem;
    }

    /**
     * @return the areaCienciaTecnologiaSeleccionada
     */
    public Long getAreaCienciaTecnologiaSeleccionada() {
        return areaCienciaTecnologiaSeleccionada;
    }

    /**
     * @param areaCienciaTecnologiaSeleccionada the
     * areaCienciaTecnologiaSeleccionada to set
     */
    public void setAreaCienciaTecnologiaSeleccionada(Long areaCienciaTecnologiaSeleccionada) {
        this.areaCienciaTecnologiaSeleccionada = areaCienciaTecnologiaSeleccionada;
    }

    /**
     * @return the listaAreaCienciaTecnologiaItem
     */
    public List<SelectItem> getListaAreaCienciaTecnologiaItem() {
        return listaAreaCienciaTecnologiaItem;
    }

    /**
     * @param listaAreaCienciaTecnologiaItem the listaAreaCienciaTecnologiaItem
     * to set
     */
    public void setListaAreaCienciaTecnologiaItem(List<SelectItem> listaAreaCienciaTecnologiaItem) {
        this.listaAreaCienciaTecnologiaItem = listaAreaCienciaTecnologiaItem;
    }

    /**
     * @return the lineaSeleccionada
     */
    public Long getLineaSeleccionada() {
        return lineaSeleccionada;
    }

    /**
     * @param lineaSeleccionada the lineaSeleccionada to set
     */
    public void setLineaSeleccionada(Long lineaSeleccionada) {
        this.lineaSeleccionada = lineaSeleccionada;
    }

    /**
     * @return the listaLineaCienciaTecnologiaItem
     */
    public List<SelectItem> getListaLineaCienciaTecnologiaItem() {
        return listaLineaCienciaTecnologiaItem;
    }

    /**
     * @param listaLineaCienciaTecnologiaItem the
     * listaLineaCienciaTecnologiaItem to set
     */
    public void setListaLineaCienciaTecnologiaItem(List<SelectItem> listaLineaCienciaTecnologiaItem) {
        this.listaLineaCienciaTecnologiaItem = listaLineaCienciaTecnologiaItem;
    }

    /**
     * @return the mostrarFechaEstimadaInicioTrabajoDeGrado
     */
    public boolean isMostrarFechaEstimadaInicioTrabajoDeGrado() {
        return mostrarFechaEstimadaInicioTrabajoDeGrado;
    }

    /**
     * @param mostrarFechaEstimadaInicioTrabajoDeGrado the
     * mostrarFechaEstimadaInicioTrabajoDeGrado to set
     */
    public void setMostrarFechaEstimadaInicioTrabajoDeGrado(boolean mostrarFechaEstimadaInicioTrabajoDeGrado) {
        this.mostrarFechaEstimadaInicioTrabajoDeGrado = mostrarFechaEstimadaInicioTrabajoDeGrado;
    }

    /**
     * @return the mostrarFechaEstimadaFinTrabajoDeGrado
     */
    public boolean isMostrarFechaEstimadaFinTrabajoDeGrado() {
        return mostrarFechaEstimadaFinTrabajoDeGrado;
    }

    /**
     * @param mostrarFechaEstimadaFinTrabajoDeGrado the
     * mostrarFechaEstimadaFinTrabajoDeGrado to set
     */
    public void setMostrarFechaEstimadaFinTrabajoDeGrado(boolean mostrarFechaEstimadaFinTrabajoDeGrado) {
        this.mostrarFechaEstimadaFinTrabajoDeGrado = mostrarFechaEstimadaFinTrabajoDeGrado;
    }

    /**
     * @return the mostrarFechaAprobacionComiteTrabajoDeGrado
     */
    public boolean isMostrarFechaAprobacionComiteTrabajoDeGrado() {
        return mostrarFechaAprobacionComiteTrabajoDeGrado;
    }

    /**
     * @param mostrarFechaAprobacionComiteTrabajoDeGrado the
     * mostrarFechaAprobacionComiteTrabajoDeGrado to set
     */
    public void setMostrarFechaAprobacionComiteTrabajoDeGrado(boolean mostrarFechaAprobacionComiteTrabajoDeGrado) {
        this.mostrarFechaAprobacionComiteTrabajoDeGrado = mostrarFechaAprobacionComiteTrabajoDeGrado;
    }

    /**
     * @return the mostrarNumeroActaComiteTrabajoDeGrado
     */
    public boolean isMostrarNumeroActaComiteTrabajoDeGrado() {
        return mostrarNumeroActaComiteTrabajoDeGrado;
    }

    /**
     * @param mostrarNumeroActaComiteTrabajoDeGrado the
     * mostrarNumeroActaComiteTrabajoDeGrado to set
     */
    public void setMostrarNumeroActaComiteTrabajoDeGrado(boolean mostrarNumeroActaComiteTrabajoDeGrado) {
        this.mostrarNumeroActaComiteTrabajoDeGrado = mostrarNumeroActaComiteTrabajoDeGrado;
    }

    /**
     * @return the duracionMesesEstimadosTrabajoDeGrado
     */
    public int getDuracionMesesEstimadosTrabajoDeGrado() {
        return duracionMesesEstimadosTrabajoDeGrado;
    }

    /**
     * @param duracionMesesEstimadosTrabajoDeGrado the
     * duracionMesesEstimadosTrabajoDeGrado to set
     */
    public void setDuracionMesesEstimadosTrabajoDeGrado(int duracionMesesEstimadosTrabajoDeGrado) {
        this.duracionMesesEstimadosTrabajoDeGrado = duracionMesesEstimadosTrabajoDeGrado;
    }

    /**
     * @return the fechaEstimadaInicioTrabajoDeGrado
     */
    public Date getFechaEstimadaInicioTrabajoDeGrado() {
        return fechaEstimadaInicioTrabajoDeGrado;
    }

    /**
     * @param fechaEstimadaInicioTrabajoDeGrado the
     * fechaEstimadaInicioTrabajoDeGrado to set
     */
    public void setFechaEstimadaInicioTrabajoDeGrado(Date fechaEstimadaInicioTrabajoDeGrado) {
        this.fechaEstimadaInicioTrabajoDeGrado = fechaEstimadaInicioTrabajoDeGrado;
    }

    /**
     * @return the fechaEstimadaFinalizacionTrabajoDeGrado
     */
    public Date getFechaEstimadaFinalizacionTrabajoDeGrado() {
        return fechaEstimadaFinalizacionTrabajoDeGrado;
    }

    /**
     * @param fechaEstimadaFinalizacionTrabajoDeGrado the
     * fechaEstimadaFinalizacionTrabajoDeGrado to set
     */
    public void setFechaEstimadaFinalizacionTrabajoDeGrado(Date fechaEstimadaFinalizacionTrabajoDeGrado) {
        this.fechaEstimadaFinalizacionTrabajoDeGrado = fechaEstimadaFinalizacionTrabajoDeGrado;
    }

    /**
     * @return the identificacionFuncionario
     */
    public String getIdentificacionFuncionario() {
        return identificacionFuncionario;
    }

    /**
     * @param identificacionFuncionario the identificacionFuncionario to set
     */
    public void setIdentificacionFuncionario(String identificacionFuncionario) {
        this.identificacionFuncionario = identificacionFuncionario;
    }

    /**
     * @return the ciudadFuncionario
     */
    public String getCiudadFuncionario() {
        return ciudadFuncionario;
    }

    /**
     * @param ciudadFuncionario the ciudadFuncionario to set
     */
    public void setCiudadFuncionario(String ciudadFuncionario) {
        this.ciudadFuncionario = ciudadFuncionario;
    }

    /**
     * @return the vistaFuncionario
     */
    public VistaFuncionario getVistaFuncionario() {
        return vistaFuncionario;
    }

    /**
     * @param vistaFuncionario the vistaFuncionario to set
     */
    public void setVistaFuncionario(VistaFuncionario vistaFuncionario) {
        this.vistaFuncionario = vistaFuncionario;
    }

    /**
     * @return the listaInvestigadoresTrabajoDeGrado
     */
    public List<InvestigadorProyecto> getListaInvestigadoresTrabajoDeGrado() {
        return listaInvestigadoresTrabajoDeGrado;
    }

    /**
     * @param listaInvestigadoresTrabajoDeGrado the
     * listaInvestigadoresTrabajoDeGrado to set
     */
    public void setListaInvestigadoresTrabajoDeGrado(List<InvestigadorProyecto> listaInvestigadoresTrabajoDeGrado) {
        this.listaInvestigadoresTrabajoDeGrado = listaInvestigadoresTrabajoDeGrado;
    }

    /**
     * @return the listaAsesoresProyecto
     */
    public List<InvestigadorProyecto> getListaAsesoresTrabajoDeGrado() {
        return listaAsesoresTrabajoDeGrado;
    }

    /**
     * @param listaAsesoresTrabajoDeGrado the listaAsesoresProyecto to set
     */
    public void setListaAsesoresTrabajoDeGrado(List<InvestigadorProyecto> listaAsesoresTrabajoDeGrado) {
        this.listaAsesoresTrabajoDeGrado = listaAsesoresTrabajoDeGrado;
    }

    /**
     * @return the listaTipoVinculacionItem
     */
    public List<SelectItem> getListaTipoVinculacionItem() {
        return listaTipoVinculacionItem;
    }

    /**
     * @param listaTipoVinculacionItem the listaTipoVinculacionItem to set
     */
    public void setListaTipoVinculacionItem(List<SelectItem> listaTipoVinculacionItem) {
        this.listaTipoVinculacionItem = listaTipoVinculacionItem;
    }

    /**
     * @return the listaTipoInvestigadorItem
     */
    public List<SelectItem> getListaTipoInvestigadorItem() {
        return listaTipoInvestigadorItem;
    }

    /**
     * @param listaTipoInvestigadorItem the listaTipoInvestigadorItem to set
     */
    public void setListaTipoInvestigadorItem(List<SelectItem> listaTipoInvestigadorItem) {
        this.listaTipoInvestigadorItem = listaTipoInvestigadorItem;
    }

    /**
     * @return the institucionExterna
     */
    /*
     public String getInstitucionExterna() {
     return institucionExterna;
     }
     */
    /**
     * @param institucionExterna the institucionExterna to set
     */
    /*
     public void setInstitucionExterna(String institucionExterna) {
     this.institucionExterna = institucionExterna;
     }
     */
    /**
     * @return the idTipoVinculacionSeleccionado
     */
    public Long getIdTipoVinculacionSeleccionado() {
        return idTipoVinculacionSeleccionado;
    }

    /**
     * @param idTipoVinculacionSeleccionado the idTipoVinculacionSeleccionado to
     * set
     */
    public void setIdTipoVinculacionSeleccionado(Long idTipoVinculacionSeleccionado) {
        this.idTipoVinculacionSeleccionado = idTipoVinculacionSeleccionado;
    }

    /**
     * @return the idTipoInvestigadorSeleccionado
     */
    public Long getIdTipoInvestigadorSeleccionado() {
        return idTipoInvestigadorSeleccionado;
    }

    /**
     * @param idTipoInvestigadorSeleccionado the idTipoInvestigadorSeleccionado
     * to set
     */
    public void setIdTipoInvestigadorSeleccionado(Long idTipoInvestigadorSeleccionado) {
        this.idTipoInvestigadorSeleccionado = idTipoInvestigadorSeleccionado;
    }

    /**
     * @return the investigadorProyecto
     */
    public InvestigadorProyecto getInvestigadorProyecto() {
        return investigadorProyecto;
    }

    /**
     * @param investigadorProyecto the investigadorProyecto to set
     */
    public void setInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
        this.investigadorProyecto = investigadorProyecto;
    }

    /**
     * @return the documentacionProyectoSeleccionado
     */
    public DocumentacionProyecto getDocumentacionProyectoSeleccionado() {
        return documentacionProyectoSeleccionado;
    }

    /**
     * @param documentacionProyectoSeleccionado the
     * documentacionProyectoSeleccionado to set
     */
    public void setDocumentacionProyectoSeleccionado(DocumentacionProyecto documentacionProyectoSeleccionado) {
        this.documentacionProyectoSeleccionado = documentacionProyectoSeleccionado;
    }

    /**
     * @return the listaSelectItemTipoArchivo
     */
    public List<SelectItem> getListaSelectItemTipoArchivo() {
        return listaSelectItemTipoArchivo;
    }

    /**
     * @param listaSelectItemTipoArchivo the listaSelectItemTipoArchivo to set
     */
    public void setListaSelectItemTipoArchivo(List<SelectItem> listaSelectItemTipoArchivo) {
        this.listaSelectItemTipoArchivo = listaSelectItemTipoArchivo;
    }

    /**
     * @return the nombreArchivoSubido
     */
    public String getNombreArchivoSubido() {
        if (documentacionProyectoSeleccionado == null && documentacionProyectoSeleccionado.getNombreArchivo() != null) {
            return "";
        }
        return documentacionProyectoSeleccionado.getNombreArchivo();
    }

    /**
     * @return the listaDocumentacionTrabajoDeGradoDTO
     */
    public List<DocumentacionProyectoDTO> getListaDocumentacionTrabajoDeGradoDTO() {
        return listaDocumentacionTrabajoDeGradoDTO;
    }

    /**
     * @param listaDocumentacionTrabajoDeGradoDTO the
     * listaDocumentacionProyectoDTO to set
     */
    public void setListaDocumentacionTrabajoDeGradoDTO(List<DocumentacionProyectoDTO> listaDocumentacionTrabajoDeGradoDTO) {
        this.listaDocumentacionTrabajoDeGradoDTO = listaDocumentacionTrabajoDeGradoDTO;
    }

    /**
     * @return the documentacionTrabajoDeGradoDTODescargarSeleccionado
     */
    public DocumentacionProyectoDTO getDocumentacionTrabajoDeGradoDTODescargarSeleccionado() {
        return documentacionTrabajoDeGradoDTODescargarSeleccionado;
    }

    /**
     * @param documentacionTrabajoDeGradoDTODescargarSeleccionado the
     * documentacionTrabajoDeGradoDTODescargarSeleccionado to set
     */
    public void setDocumentacionTrabajoDeGradoDTODescargarSeleccionado(DocumentacionProyectoDTO documentacionTrabajoDeGradoDTODescargarSeleccionado) {
        this.documentacionTrabajoDeGradoDTODescargarSeleccionado = documentacionTrabajoDeGradoDTODescargarSeleccionado;
    }

    /**
     * @return the documentacionTrabajoDeGradoDTOEliminarSeleccionado
     */
    public DocumentacionProyectoDTO getDocumentacionTrabajoDeGradoDTOEliminarSeleccionado() {
        return documentacionTrabajoDeGradoDTOEliminarSeleccionado;
    }

    /**
     * @param documentacionTrabajoDeGradoDTOEliminarSeleccionado the
     * documentacionTrabajoDeGradoDTOEliminarSeleccionado to set
     */
    public void setDocumentacionTrabajoDeGradoDTOEliminarSeleccionado(DocumentacionProyectoDTO documentacionTrabajoDeGradoDTOEliminarSeleccionado) {
        this.documentacionTrabajoDeGradoDTOEliminarSeleccionado = documentacionTrabajoDeGradoDTOEliminarSeleccionado;
    }

    /**
     * @return the fechaSustentacionTrabajoDeGrado
     */
    public Date getFechaSustentacionTrabajoDeGrado() {
        return fechaSustentacionTrabajoDeGrado;
    }

    /**
     * @param fechaSustentacionTrabajoDeGrado the
     * fechaSustentacionTrabajoDeGrado to set
     */
    public void setFechaSustentacionTrabajoDeGrado(Date fechaSustentacionTrabajoDeGrado) {
        this.fechaSustentacionTrabajoDeGrado = fechaSustentacionTrabajoDeGrado;
    }

    /**
     * @return the notaTrabajoDeGrado
     */
    public BigDecimal getNotaTrabajoDeGrado() {
        return notaTrabajoDeGrado;
    }

    /**
     * @param notaTrabajoDeGrado the notaTrabajoDeGrado to set
     */
    public void setNotaTrabajoDeGrado(BigDecimal notaTrabajoDeGrado) {
        this.notaTrabajoDeGrado = notaTrabajoDeGrado;
    }

    /**
     * @return the notaSustentacionTrabajoDeGrado
     */
    public BigDecimal getNotaSustentacionTrabajoDeGrado() {
        return notaSustentacionTrabajoDeGrado;
    }

    /**
     * @param notaSustentacionTrabajoDeGrado the notaSustentacionTrabajoDeGrado
     * to set
     */
    public void setNotaSustentacionTrabajoDeGrado(BigDecimal notaSustentacionTrabajoDeGrado) {
        this.notaSustentacionTrabajoDeGrado = notaSustentacionTrabajoDeGrado;
    }

    /**
     * @return the notaFinalTrabajoDeGrado
     */
    public BigDecimal getNotaFinalTrabajoDeGrado() {
        return notaFinalTrabajoDeGrado;
    }

    /**
     * @param notaFinalTrabajoDeGrado the notaFinalTrabajoDeGrado to set
     */
    public void setNotaFinalTrabajoDeGrado(BigDecimal notaFinalTrabajoDeGrado) {
        this.notaFinalTrabajoDeGrado = notaFinalTrabajoDeGrado;
    }

    /**
     * @return the listaEvaluadoresTrabajoDeGrado
     */
    public List<EvaluadoresProyectoGrado> getListaEvaluadoresTrabajoDeGrado() {
        return listaEvaluadoresTrabajoDeGrado;
    }

    /**
     * @param listaEvaluadoresTrabajoDeGrado the listaEvaluadoresTrabajoDeGrado
     * to set
     */
    public void setListaEvaluadoresTrabajoDeGrado(List<EvaluadoresProyectoGrado> listaEvaluadoresTrabajoDeGrado) {
        this.listaEvaluadoresTrabajoDeGrado = listaEvaluadoresTrabajoDeGrado;
    }

    /**
     * @return the modalidadesAsesoria
     */
    public List<ModalidadAsesoriaProyecto> getModalidadesAsesoria() {
        return modalidadesAsesoria;
    }

    /**
     * @param modalidadesAsesoria the modalidadesAsesoria to set
     */
    public void setModalidadesAsesoria(List<ModalidadAsesoriaProyecto> modalidadesAsesoria) {
        this.modalidadesAsesoria = modalidadesAsesoria;
    }

    /**
     * @return the resultadoAsesoria
     */
    public String getResultadoAsesoria() {
        return resultadoAsesoria;
    }

    /**
     * @param resultadoAsesoria the resultadoAsesoria to set
     */
    public void setResultadoAsesoria(String resultadoAsesoria) {
        this.resultadoAsesoria = resultadoAsesoria;
    }

    /**
     * @return the listaAsesoriasTrabajoDeGrado
     */
    public List<AsesoriaProyecto> getListaAsesoriasTrabajoDeGrado() {
        return listaAsesoriasTrabajoDeGrado;
    }

    /**
     * @param listaAsesoriasTrabajoDeGrado the listaAsesoriasTrabajoDeGrado to
     * set
     */
    public void setListaAsesoriasTrabajoDeGrado(List<AsesoriaProyecto> listaAsesoriasTrabajoDeGrado) {
        this.listaAsesoriasTrabajoDeGrado = listaAsesoriasTrabajoDeGrado;
    }

    /**
     * @return the listaOtrasUnidadesPolicialesItem
     */
    public List<SelectItem> getListaOtrasUnidadesPolicialesItem() {
        return listaOtrasUnidadesPolicialesItem;
    }

    /**
     * @param listaOtrasUnidadesPolicialesItem the
     * listaOtrasUnidadesPolicialesItem to set
     */
    public void setListaOtrasUnidadesPolicialesItem(List<SelectItem> listaOtrasUnidadesPolicialesItem) {
        this.listaOtrasUnidadesPolicialesItem = listaOtrasUnidadesPolicialesItem;
    }

    /**
     * @return the idOtraUnidadPolicialSeleccionada
     */
    public Long getIdOtraUnidadPolicialSeleccionada() {
        return idOtraUnidadPolicialSeleccionada;
    }

    /**
     * @param idOtraUnidadPolicialSeleccionada the
     * idOtraUnidadPolicialSeleccionada to set
     */
    public void setIdOtraUnidadPolicialSeleccionada(Long idOtraUnidadPolicialSeleccionada) {
        this.idOtraUnidadPolicialSeleccionada = idOtraUnidadPolicialSeleccionada;
    }

    /**
     * @return the aporteInvestigacionUnidadPolicial
     */
    public String getAporteInvestigacionUnidadPolicial() {
        return aporteInvestigacionUnidadPolicial;
    }

    /**
     * @param aporteInvestigacionUnidadPolicial the
     * aporteInvestigacionUnidadPolicial to set
     */
    public void setAporteInvestigacionUnidadPolicial(String aporteInvestigacionUnidadPolicial) {
        this.aporteInvestigacionUnidadPolicial = aporteInvestigacionUnidadPolicial;
    }

    /**
     * @return the listaOtrasUnidadesPolicialesSemilleroProyectoDTO
     */
    public List<SemilleroProyectoDTO> getListaOtrasUnidadesPolicialesSemilleroProyectoDTO() {
        return listaOtrasUnidadesPolicialesSemilleroProyectoDTO;
    }

    /**
     * @param listaOtrasUnidadesPolicialesSemilleroProyectoDTO the
     * listaOtrasUnidadesPolicialesSemilleroProyectoDTO to set
     */
    public void setListaOtrasUnidadesPolicialesSemilleroProyectoDTO(List<SemilleroProyectoDTO> listaOtrasUnidadesPolicialesSemilleroProyectoDTO) {
        this.listaOtrasUnidadesPolicialesSemilleroProyectoDTO = listaOtrasUnidadesPolicialesSemilleroProyectoDTO;
    }

    /**
     * @return the idOtraInstitucionSeleccionada
     */
    public Long getIdOtraInstitucionSeleccionada() {
        return idOtraInstitucionSeleccionada;
    }

    /**
     * @param idOtraInstitucionSeleccionada the idOtraInstitucionSeleccionada to
     * set
     */
    public void setIdOtraInstitucionSeleccionada(Long idOtraInstitucionSeleccionada) {
        this.idOtraInstitucionSeleccionada = idOtraInstitucionSeleccionada;
    }

    /**
     * @return the listaOtrasInstitucionesItem
     */
    public List<SelectItem> getListaOtrasInstitucionesItem() {
        return listaOtrasInstitucionesItem;
    }

    /**
     * @param listaOtrasInstitucionesItem the listaOtrasInstitucionesItem to set
     */
    public void setListaOtrasInstitucionesItem(List<SelectItem> listaOtrasInstitucionesItem) {
        this.listaOtrasInstitucionesItem = listaOtrasInstitucionesItem;
    }

    /**
     * @return the mostrarDetalleOtraNuevaInstitucion
     */
    public boolean isMostrarDetalleOtraNuevaInstitucion() {
        return mostrarDetalleOtraNuevaInstitucion;
    }

    /**
     * @param mostrarDetalleOtraNuevaInstitucion the
     * mostrarDetalleOtraNuevaInstitucion to set
     */
    public void setMostrarDetalleOtraNuevaInstitucion(boolean mostrarDetalleOtraNuevaInstitucion) {
        this.mostrarDetalleOtraNuevaInstitucion = mostrarDetalleOtraNuevaInstitucion;
    }

    /**
     * @return the otraNuevaInstitucion
     */
    public String getOtraNuevaInstitucion() {
        return otraNuevaInstitucion;
    }

    /**
     * @param otraNuevaInstitucion the otraNuevaInstitucion to set
     */
    public void setOtraNuevaInstitucion(String otraNuevaInstitucion) {
        this.otraNuevaInstitucion = otraNuevaInstitucion;
    }

    /**
     * @return the aporteInvestigacionOtrasInstituciones
     */
    public String getAporteInvestigacionOtrasInstituciones() {
        return aporteInvestigacionOtrasInstituciones;
    }

    /**
     * @param aporteInvestigacionOtrasInstituciones the
     * aporteInvestigacionOtrasInstituciones to set
     */
    public void setAporteInvestigacionOtrasInstituciones(String aporteInvestigacionOtrasInstituciones) {
        this.aporteInvestigacionOtrasInstituciones = aporteInvestigacionOtrasInstituciones;
    }

    /**
     * @return the listaOtrasInstitucionesProyectoDTO
     */
    public List<InstitucionesProyectoDTO> getListaOtrasInstitucionesProyectoDTO() {
        return listaOtrasInstitucionesProyectoDTO;
    }

    /**
     * @param listaOtrasInstitucionesProyectoDTO the
     * listaOtrasInstitucionesProyectoDTO to set
     */
    public void setListaOtrasInstitucionesProyectoDTO(List<InstitucionesProyectoDTO> listaOtrasInstitucionesProyectoDTO) {
        this.listaOtrasInstitucionesProyectoDTO = listaOtrasInstitucionesProyectoDTO;
    }

    //Getter para visualizar los tabs si el trabajo de grado es nuevo o ya ha sido guardado
    public boolean isEsNuevoTrabajoDeGrado() {
        return proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null;
    }

    //Getter para habilitar los elementos para el usuario responsable de trabajos de grado
    public boolean isEsResponsableTrabajosDeGrado() {
        return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.RESPONSABLE_DE_TRABAJOS_DE_GRADO_EN_LA_ESCUELA);
    }

    //Getter para habilitar los elementos para el usuario generador de consulta
    public boolean isEsUsuarioGeneradorDeConsulta() {
        return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.GENERADOR_DE_CONSULTAS);
    }

    //Getter habilitar los elementos para el usuario Investigador Principal
    public boolean isEsInvestigadorPrincipal() {
        return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.INVESTIGADOR_PRINCIPAL);
    }

    /**
     * @return the idProgramaSeleccionadoConsulta
     */
    public Long getIdProgramaSeleccionadoConsulta() {
        return idProgramaSeleccionadoConsulta;
    }

    /**
     * @param idProgramaSeleccionadoConsulta the idProgramaSeleccionadoConsulta
     * to set
     */
    public void setIdProgramaSeleccionadoConsulta(Long idProgramaSeleccionadoConsulta) {
        this.idProgramaSeleccionadoConsulta = idProgramaSeleccionadoConsulta;
    }

    /**
     * @return the listaNombreProgramaConsultarItem
     */
    public List<SelectItem> getListaNombreProgramaConsultarItem() {
        return listaNombreProgramaConsultarItem;
    }

    /**
     * @param listaNombreProgramaConsultarItem the
     * listaNombreProgramaConsultarItem to set
     */
    public void setListaNombreProgramaConsultarItem(List<SelectItem> listaNombreProgramaConsultarItem) {
        this.listaNombreProgramaConsultarItem = listaNombreProgramaConsultarItem;
    }

    /**
     * @return the comentarioProyectoDocumentoSeleccionado
     */
    public ComentarioProyecto getComentarioProyectoDocumentoSeleccionado() {
        return comentarioProyectoDocumentoSeleccionado;
    }

    /**
     * @param comentarioProyectoDocumentoSeleccionado the
     * comentarioProyectoDocumentoSeleccionado to set
     */
    public void setComentarioProyectoDocumentoSeleccionado(ComentarioProyecto comentarioProyectoDocumentoSeleccionado) {
        this.comentarioProyectoDocumentoSeleccionado = comentarioProyectoDocumentoSeleccionado;
    }

    /**
     * @return the listaComentarioProyectoDTODocumentoSeleccionado
     */
    public List<ComentarioProyectoDTO> getListaComentarioProyectoDTODocumentoSeleccionado() {
        return listaComentarioProyectoDTODocumentoSeleccionado;
    }

    /**
     * @param listaComentarioProyectoDTODocumentoSeleccionado the
     * listaComentarioProyectoDTODocumentoSeleccionado to set
     */
    public void setListaComentarioProyectoDTODocumentoSeleccionado(List<ComentarioProyectoDTO> listaComentarioProyectoDTODocumentoSeleccionado) {
        this.listaComentarioProyectoDTODocumentoSeleccionado = listaComentarioProyectoDTODocumentoSeleccionado;
    }

    /**
     * @return the documentacionProyectoDTOEliminarSeleccionado
     */
    public ComentarioProyectoDTO getDocumentacionProyectoDTOEliminarSeleccionado() {
        return comentarioProyectoDocumentacionProyectoDTOEliminarSeleccionado;
    }

    /**
     * @param documentacionProyectoDTOEliminarSeleccionado the
     * documentacionProyectoDTOEliminarSeleccionado to set
     */
    public void setDocumentacionProyectoDTOEliminarSeleccionado(ComentarioProyectoDTO documentacionProyectoDTOEliminarSeleccionado) {
        this.comentarioProyectoDocumentacionProyectoDTOEliminarSeleccionado = documentacionProyectoDTOEliminarSeleccionado;
    }

    /**
     * @return the documentacionTrabajoDeGradoDTOComentariarSeleccionado
     */
    public DocumentacionProyectoDTO getDocumentacionTrabajoDeGradoDTOComentariarSeleccionado() {
        return documentacionTrabajoDeGradoDTOComentariarSeleccionado;
    }

    /**
     * @param documentacionTrabajoDeGradoDTOComentariarSeleccionado the
     * documentacionTrabajoDeGradoDTOComentariarSeleccionado to set
     */
    public void setDocumentacionTrabajoDeGradoDTOComentariarSeleccionado(DocumentacionProyectoDTO documentacionTrabajoDeGradoDTOComentariarSeleccionado) {
        this.documentacionTrabajoDeGradoDTOComentariarSeleccionado = documentacionTrabajoDeGradoDTOComentariarSeleccionado;
    }

    /**
     * @return the agregarComentarioDocumento
     */
    public boolean isAgregarComentarioDocumento() {
        return agregarComentarioDocumento;
    }

    /**
     * @param agregarComentarioDocumento the agregarComentarioDocumento to set
     */
    public void setAgregarComentarioDocumento(boolean agregarComentarioDocumento) {
        this.agregarComentarioDocumento = agregarComentarioDocumento;
    }

    /**
     * @return the verComentarioDocumento
     */
    public boolean isVerComentarioDocumento() {
        return verComentarioDocumento;
    }

    /**
     * @param verComentarioDocumento the verComentarioDocumento to set
     */
    public void setVerComentarioDocumento(boolean verComentarioDocumento) {
        this.verComentarioDocumento = verComentarioDocumento;
    }

    /**
     * @return the nombreTipoDocumentoComentariarSeleccionado
     */
    public String getNombreTipoDocumentoComentariarSeleccionado() {
        return nombreTipoDocumentoComentariarSeleccionado;
    }

    /**
     * @param nombreTipoDocumentoComentariarSeleccionado the
     * nombreTipoDocumentoComentariarSeleccionado to set
     */
    public void setNombreTipoDocumentoComentariarSeleccionado(String nombreTipoDocumentoComentariarSeleccionado) {
        this.nombreTipoDocumentoComentariarSeleccionado = nombreTipoDocumentoComentariarSeleccionado;
    }

    /**
     * @return the identificacionFuncionarioEvaluador
     */
    public String getIdentificacionFuncionarioEvaluador() {
        return identificacionFuncionarioEvaluador;
    }

    /**
     * @param identificacionFuncionarioEvaluador the
     * identificacionFuncionarioEvaluador to set
     */
    public void setIdentificacionFuncionarioEvaluador(String identificacionFuncionarioEvaluador) {
        this.identificacionFuncionarioEvaluador = identificacionFuncionarioEvaluador;
    }

    /**
     * @return the ciudadFuncionarioEvaluador
     */
    public String getCiudadFuncionarioEvaluador() {
        return ciudadFuncionarioEvaluador;
    }

    /**
     * @param ciudadFuncionarioEvaluador the ciudadFuncionarioEvaluador to set
     */
    public void setCiudadFuncionarioEvaluador(String ciudadFuncionarioEvaluador) {
        this.ciudadFuncionarioEvaluador = ciudadFuncionarioEvaluador;
    }

    /**
     * @return the vistaFuncionarioEvaluador
     */
    public VistaFuncionario getVistaFuncionarioEvaluador() {
        return vistaFuncionarioEvaluador;
    }

    /**
     * @param vistaFuncionarioEvaluador the vistaFuncionarioEvaluador to set
     */
    public void setVistaFuncionarioEvaluador(VistaFuncionario vistaFuncionarioEvaluador) {
        this.vistaFuncionarioEvaluador = vistaFuncionarioEvaluador;
    }

    /**
     * @return the evaluadoresProyectoGrado
     */
    public EvaluadoresProyectoGrado getEvaluadoresProyectoGrado() {
        return evaluadoresProyectoGrado;
    }

    /**
     * @param evaluadoresProyectoGrado the evaluadoresProyectoGrado to set
     */
    public void setEvaluadoresProyectoGrado(EvaluadoresProyectoGrado evaluadoresProyectoGrado) {
        this.evaluadoresProyectoGrado = evaluadoresProyectoGrado;
    }

    /**
     * @return the evaluacionProyectogrado
     */
    public EvaluacionProyectoGrado getEvaluacionProyectogrado() {
        return evaluacionProyectogrado;
    }

    /**
     * @param evaluacionProyectogrado the evaluacionProyectogrado to set
     */
    public void setEvaluacionProyectogrado(EvaluacionProyectoGrado evaluacionProyectogrado) {
        this.evaluacionProyectogrado = evaluacionProyectogrado;
    }

    /**
     * @return the listaEvaluadoresTrabajoDeGradoDTO
     */
    public List<EvaluadoresProyectoGradoDTO> getListaEvaluadoresTrabajoDeGradoDTO() {
        return listaEvaluadoresTrabajoDeGradoDTO;
    }

    /**
     * @param listaEvaluadoresTrabajoDeGradoDTO the
     * listaEvaluadoresTrabajoDeGradoDTO to set
     */
    public void setListaEvaluadoresTrabajoDeGradoDTO(List<EvaluadoresProyectoGradoDTO> listaEvaluadoresTrabajoDeGradoDTO) {
        this.listaEvaluadoresTrabajoDeGradoDTO = listaEvaluadoresTrabajoDeGradoDTO;
    }

    /**
     * @return the listaModalidadAsesoriaProyectoItem
     */
    public List<SelectItem> getListaModalidadAsesoriaProyectoItem() {
        return listaModalidadAsesoriaProyectoItem;
    }

    /**
     * @param listaModalidadAsesoriaProyectoItem the
     * listaModalidadAsesoriaProyectoItem to set
     */
    public void setListaModalidadAsesoriaProyectoItem(List<SelectItem> listaModalidadAsesoriaProyectoItem) {
        this.listaModalidadAsesoriaProyectoItem = listaModalidadAsesoriaProyectoItem;
    }

    /**
     * @return the asesoriaTrabajoDeGrado
     */
    public AsesoriaProyecto getAsesoriaTrabajoDeGrado() {
        return asesoriaTrabajoDeGrado;
    }

    /**
     * @param asesoriaTrabajoDeGrado the asesoriaTrabajoDeGrado to set
     */
    public void setAsesoriaTrabajoDeGrado(AsesoriaProyecto asesoriaTrabajoDeGrado) {
        this.asesoriaTrabajoDeGrado = asesoriaTrabajoDeGrado;
    }

    /**
     * @return the modalidadesAsesoriaSeleccionadas
     */
    public List<String> getModalidadesAsesoriaSeleccionadas() {
        return modalidadesAsesoriaSeleccionadas;
    }

    /**
     * @param modalidadesAsesoriaSeleccionadas the
     * modalidadesAsesoriaSeleccionadas to set
     */
    public void setModalidadesAsesoriaSeleccionadas(List<String> modalidadesAsesoriaSeleccionadas) {
        this.modalidadesAsesoriaSeleccionadas = modalidadesAsesoriaSeleccionadas;
    }

    /**
     * @return the trabajosDeGradoAsigndosInvestigadorPrincipal
     */
    public List<Proyecto> getTrabajosDeGradoAsigndosInvestigadorPrincipal() {
        return trabajosDeGradoAsigndosInvestigadorPrincipal;
    }

    /**
     * @param trabajosDeGradoAsigndosInvestigadorPrincipal the
     * trabajosDeGradoAsigndosInvestigadorPrincipal to set
     */
    public void setTrabajosDeGradoAsigndosInvestigadorPrincipal(List<Proyecto> trabajosDeGradoAsigndosInvestigadorPrincipal) {
        this.trabajosDeGradoAsigndosInvestigadorPrincipal = trabajosDeGradoAsigndosInvestigadorPrincipal;
    }

    /**
     * @return the trabajosDeGradoGeneradorDeConsulta
     */
    public List<Proyecto> getTrabajosDeGradoGeneradorDeConsulta() {
        return trabajosDeGradoGeneradorDeConsulta;
    }

    /**
     * @param trabajosDeGradoGeneradorDeConsulta the
     * trabajosDeGradoGeneradorDeConsulta to set
     */
    public void setTrabajosDeGradoGeneradorDeConsulta(List<Proyecto> trabajosDeGradoGeneradorDeConsulta) {
        this.trabajosDeGradoGeneradorDeConsulta = trabajosDeGradoGeneradorDeConsulta;
    }

    /**
     * @return the modoSoloLectura
     */
    public boolean isModoSoloLectura() {
        return modoSoloLectura;
    }

    /**
     * @param modoSoloLectura the modoSoloLectura to set
     */
    public void setModoSoloLectura(boolean modoSoloLectura) {
        this.modoSoloLectura = modoSoloLectura;
    }

    /**
     * @return the modoInvestigadorPrincipal
     */
    public boolean isModoInvestigadorPrincipal() {
        return modoInvestigadorPrincipal;
    }

    /**
     * @param modoInvestigadorPrincipal the modoInvestigadorPrincipal to set
     */
    public void setModoInvestigadorPrincipal(boolean modoInvestigadorPrincipal) {
        this.modoInvestigadorPrincipal = modoInvestigadorPrincipal;
    }

    /**
     * @return the idProgramaSeleccionadoGeneradorConsulta
     */
    public Long getIdProgramaSeleccionadoGeneradorConsulta() {
        return idProgramaSeleccionadoGeneradorConsulta;
    }

    /**
     * @param idProgramaSeleccionadoGeneradorConsulta the
     * idProgramaSeleccionadoGeneradorConsulta to set
     */
    public void setIdProgramaSeleccionadoGeneradorConsulta(Long idProgramaSeleccionadoGeneradorConsulta) {
        this.idProgramaSeleccionadoGeneradorConsulta = idProgramaSeleccionadoGeneradorConsulta;
    }

    /**
     * @return the listaNombreProgramaConsultarGeneradorConsultaItem
     */
    public List<SelectItem> getListaNombreProgramaConsultarGeneradorConsultaItem() {
        return listaNombreProgramaConsultarGeneradorConsultaItem;
    }

    /**
     * @param listaNombreProgramaConsultarGeneradorConsultaItem the
     * listaNombreProgramaConsultarGeneradorConsultaItem to set
     */
    public void setListaNombreProgramaConsultarGeneradorConsultaItem(List<SelectItem> listaNombreProgramaConsultarGeneradorConsultaItem) {
        this.listaNombreProgramaConsultarGeneradorConsultaItem = listaNombreProgramaConsultarGeneradorConsultaItem;
    }

    /**
     * @return the listaEvaluacionTrabajoDeGrado
     *
     * public List <EvaluacionProyectoGrado> getListaEvaluacionTrabajoDeGrado()
     * { return listaEvaluacionTrabajoDeGrado; }
     */
    /**
     * @param listaEvaluacionTrabajoDeGrado the listaEvaluacionTrabajoDeGrado to
     * set
     *
     * public void setListaEvaluacionTrabajoDeGrado(List
     * <EvaluacionProyectoGrado> listaEvaluacionTrabajoDeGrado) {
     * this.listaEvaluacionTrabajoDeGrado = listaEvaluacionTrabajoDeGrado; }
     */
}
