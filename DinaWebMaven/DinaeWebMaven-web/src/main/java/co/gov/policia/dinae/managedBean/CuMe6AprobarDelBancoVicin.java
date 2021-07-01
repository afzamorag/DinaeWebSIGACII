package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import java.io.InputStream;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.EjecutorNecesidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IFuncionarioNecesidadLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersona;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersonaPK;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacion;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacionPK;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.ComentarioService;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramaInvestigacionService;
import co.gov.policia.dinae.siedu.servicios.SieduPropuestaAsignadaService;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.TransactionRolledbackLocalException;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@javax.inject.Named(value = "cuMe6AprobarDelBancoVicin")
@javax.enterprise.context.SessionScoped
public class CuMe6AprobarDelBancoVicin extends AbstractController implements Serializable {

    private List<SelectItem> vigencias;
    private SieduProgramaInvestigacion programaInvestigacionSeleccionado;
    private Integer editando = 0;
    private PropuestaNecesidad propuestaSeleccionada;
    private List<AreaCienciaTecnologia> areas;
    private List<FuncionarioNecesidad> funcionarios;
    private List<SieduPropuestaAsignada> propuestasAsignadas;
    private List<Comentario> comentarios;
    private Comentario comentarioSeleccionado;
    private String documentoFuncionario;
    private SieduBancoNecesidadPersona funcionarioSeleccionado;
    private String observacion;
    private Integer vigencia;
    private Long idUnidadPolicial;
    private Dominio modalidad;
    private boolean renderForm;
    private List<EjecutorNecesidadDTO> listadoEjecutorNecesidad;
    private List<UnidadPolicial> listaItemUnidadPolicialVincula;
    private Long idUnidadPolicialVinculaSeleccionado;
    private Long idRolVinculaEjecutorSelecionado;
    private List<ConstantesDTO> listaItemRolVincula;
    private List<CompromisoPeriodo> listaCompromiso;
    private CompromisoPeriodo compromisoSeleccionado;
    private List<Constantes> tipoCompromisoList;
    private Long idCompromiso;
    private List<Dominio> modalidades;
    private boolean renderPanel;
    private boolean renderButton;

    @EJB
    private IAreaCienciaTecnologiaLocal areaService;
    @EJB
    private ILineaLocal lineaService;
    @EJB
    private SieduBancoNecesidadPersonaService serviceBancoNecesidadPersona;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private SieduPropuestaAsignadaService servicePropuestaAsignada;
    @EJB
    private IPropuestaNecesidadLocal servicePropuestaNecesidad;
    @EJB
    private SieduProgramaInvestigacionService serviceProgramaInvestigacion;
    @EJB
    private ComentarioService serviceComentario;
    @EJB
    private IFuncionarioNecesidadLocal serviceFuncionarioNecesidad;
    @EJB
    private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;
    @EJB
    private IUnidadPolicialLocal iUnidadPolicialLocal;
    @EJB
    private IConstantesLocal iConstantesLocal;
    @EJB
    private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;
    @EJB
    private IProyectoLocal iProyectoLocal;
    @EJB
    private APPService serviceAPP;

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        try {
            this.areas = areaService.getAreaCienciaTecnologias();
            if (this.areas.size() > 0) {
                this.propuestaSeleccionada = new PropuestaNecesidad();
                this.propuestaSeleccionada.setLinea(new Linea());
                this.propuestaSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
            }
            this.vigencias = new ArrayList<>();
            this.propuestasAsignadas = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR) - 1, "" + (calendar.get(Calendar.YEAR) - 1)));
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR), "" + calendar.get(Calendar.YEAR)));
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR) + 1, "" + (calendar.get(Calendar.YEAR) + 1)));
            this.renderForm = true;
            this.listadoEjecutorNecesidad = new ArrayList<>();
            this.listaItemUnidadPolicialVincula = new ArrayList<>();
            this.idUnidadPolicialVinculaSeleccionado = -1L;
            this.idRolVinculaEjecutorSelecionado = -1L;
            this.listaItemRolVincula = new ArrayList<>();
            this.listaCompromiso = new ArrayList<>();
            this.compromisoSeleccionado = new CompromisoPeriodo();
            this.tipoCompromisoList = new ArrayList<>();
            this.idCompromiso = -1L;
            modalidades = serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD_PROGRAMACION.getId());
            renderPanel = true;
            if (this.propuestaSeleccionada.getConstantes() != null) {
                renderButton = (IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA.compareTo(this.propuestaSeleccionada.getConstantes().getIdConstantes()) != 0);
            }
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-06", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe06AprobarInvestigacionUnidadVicin();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-06", e);
        }
        return null;
    }

    public void ver(SieduPropuestaAsignada spa) throws JpaDinaeException {
        this.propuestaSeleccionada = spa.getPropuestaNecesidad();
        Map<String, Object> params = new HashMap();
        try {
            funcionarios = serviceFuncionarioNecesidad.getListaFuncionarioNecesidad(propuestaSeleccionada.getIdPropuestaNecesidad());
            params.put("propuestaNecesidad", propuestaSeleccionada);
            comentarios = serviceComentario.findByFilter(params);
            this.renderForm = false;
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-06 Búsqueda de Funcionarios ", ex);
        }
    }

    public void verCompromisos(SieduPropuestaAsignada spa) throws JpaDinaeException {
        this.propuestaSeleccionada = spa.getPropuestaNecesidad();
        Map<String, Object> params = new HashMap();
        try {
            funcionarios = serviceFuncionarioNecesidad.getListaFuncionarioNecesidad(propuestaSeleccionada.getIdPropuestaNecesidad());
            params.put("propuestaNecesidad", propuestaSeleccionada);
            comentarios = serviceComentario.findByFilter(params);
            this.renderForm = false;
            this.listadoEjecutorNecesidad = this.iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorPropuestaNecesidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
            this.listaItemUnidadPolicialVincula = this.iUnidadPolicialLocal.getUnidadPolicialDiferenteAEjecutorPropuestaUnidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
            this.listaItemRolVincula = this.iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ROL_PROYECTO_NECESIDAD);
            this.listaCompromiso = this.iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidad(this.propuestaSeleccionada);

        } catch (ServiceException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-06 Búsqueda de Funcionarios ", ex);
        }
    }

    public void preaprobar() throws JpaDinaeException, Exception {
        Constantes constantePre = new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA);
        this.listadoEjecutorNecesidad = this.iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorPropuestaNecesidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
        this.listaItemUnidadPolicialVincula = this.iUnidadPolicialLocal.getUnidadPolicialDiferenteAEjecutorPropuestaUnidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
        this.listaItemRolVincula = this.iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ROL_PROYECTO_NECESIDAD);
        this.listaCompromiso = this.iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidad(this.propuestaSeleccionada);
        this.tipoCompromisoList = iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_COMPROMISO_PERIODO);
        this.propuestaSeleccionada.setConstantes(constantePre);
        this.renderPanel = false;
    }

    public void guardarCompromiso() {
        try {
            if (this.compromisoSeleccionado.getTipoCompromiso() == null || this.propuestaSeleccionada == null
                    || this.compromisoSeleccionado.getFecha() == null) {
                addErrorMessage(keyPropertiesFactory.value("cu_pr_23_lbl_datos_incorrectos_add_compromiso"));
                return;
            }
            //La fecha ingresada es menor a la fecha actual.
            if (co.gov.policia.dinae.util.DatesUtils.compareDate(this.compromisoSeleccionado.getFecha(), new Date()) == -1) {
                addErrorMessage(keyPropertiesFactory.value("cu_pr_23_lbl_fechas_invalida_fecha_actual"));
                return;
            }
            //VERIFICAMOS QUE PARA "FORMULACION DE PROYECTO E INFORME FINAL SOLO SE PUEDEN ADICIONAR UN SOLO REGISTRO"
            //ESTO SOLO APLICA CUANDO SE ESTA INSERTANDO UN NUEVO COMPROMISO
            if (this.compromisoSeleccionado.getIdCompromisoPeriodo() == null) {
                if (this.compromisoSeleccionado.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)
                        || this.compromisoSeleccionado.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {
                    //CONTEMOS CUANTOS EXISTEN
                    List<CompromisoPeriodo> lst = this.iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidadAndTipoCompromiso(this.propuestaSeleccionada, this.compromisoSeleccionado.getTipoCompromiso());
                    if (!lst.isEmpty()) {
                        addErrorMessage(keyPropertiesFactory.value("cu_pr_23_lbl_existe_compromiso_tipo"));
                        return;
                    }
                }
                this.compromisoSeleccionado.setPeriodo(this.propuestaSeleccionada.getPeriodo());
                this.compromisoSeleccionado.setPropuestaNecesidad(this.propuestaSeleccionada);
            }
            List<CompromisoPeriodo> listaHitos;
            //SE VALIDA LA INFORMACIÓN INGRESADA, ALMACENA LOS DATOS AGREGADOS O MODIFICADOS DEL COMPROMISO, 
            //SI EL TIPO DE COMPROMISO ES 'INFORME DE AVANCE', EL SISTEMA AGREGA UN CONSECUTIVO AL INFORME.
            if (this.compromisoSeleccionado.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)) {
                //CONSULTAMOS LA LISTA DE CompromisoPeriodo existentes
                listaHitos = this.iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidadAndTipoCompromiso(this.propuestaSeleccionada, this.compromisoSeleccionado.getTipoCompromiso());
                //ADICIONAMOS A LA LISTA EL NUEVO COMPROMISO
                //SOLO ADICIONAMOS A LA LISTA, CUANDO ES UN NUEVO COMPROMISO
                if (this.compromisoSeleccionado.getIdCompromisoPeriodo() == null) {
                    listaHitos.add(this.compromisoSeleccionado);
                } else {
                    //BUSCAMOS Y ACTUALIZAMOS EL REGISTRO MODIFICADO
                    for (CompromisoPeriodo unHitosPeriodo : listaHitos) {
                        //SI UN COMPROMISO SE ESTA MODIFICANDO, ACTUALIZAMOS A LOS DATOS SELECCIONADOS POR EL USUARIO
                        if (unHitosPeriodo.getIdCompromisoPeriodo().equals(this.compromisoSeleccionado.getIdCompromisoPeriodo())) {
                            unHitosPeriodo.setFecha(this.compromisoSeleccionado.getFecha());
                            unHitosPeriodo.setPeriodo(this.compromisoSeleccionado.getPeriodo());
                            unHitosPeriodo.setTipoCompromiso(this.compromisoSeleccionado.getTipoCompromiso());
                            break;
                        }
                    }
                }
                //ORDENAMOS LA LISTA POR FECHA
                Collections.sort(listaHitos);
                //ASIGNAMOS LOS CONCECUTIVOS
                short incrementa = 0;
                for (CompromisoPeriodo unHitosPeriodo : listaHitos) {
                    unHitosPeriodo.setNumeroIncrementa(++incrementa);
                }

            } else {
                listaHitos = new ArrayList<CompromisoPeriodo>(1);
                Short numeroIncrementa = 2;
                if (this.compromisoSeleccionado.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)) {
                    this.compromisoSeleccionado.setNumeroIncrementa(numeroIncrementa);
                }
                listaHitos.add(this.compromisoSeleccionado);
            }
            this.iCompromisoPeriodoLocal.agregarCompromisoPeriodo(listaHitos, loginFaces.getPerfilUsuarioDTO().getIdentificacion());
            this.compromisoSeleccionado = new CompromisoPeriodo();
            //CARGAMOS LA LISTA DE HITOS PERIODO
            this.listaCompromiso = this.iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidad(this.propuestaSeleccionada);
            addInfoMessage(keyPropertiesFactory.value("cu_pr_23_lbl_compromiso_almacenado_ok"));
        } catch (Exception e) {
            addErrorMessage(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-PR-23 Gestionar compromisos de proyectos (guardarCompromiso)", e);
        }
    }

    public void eliminarCompromisoPeriodo() {
        try {
            if (this.compromisoSeleccionado != null) {
                iCompromisoPeriodoLocal.eliminarCompromisoPeriodo(this.compromisoSeleccionado);
                this.listaCompromiso = this.iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidad(this.propuestaSeleccionada);
                addInfoMessage("El compromiso fué eliminado correctamente");
            }
        } catch (JpaDinaeException ex) {
            addErrorMessage(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransactionRolledbackLocalException ex) {
            addErrorMessage("No es posible eliminar el compromiso seleccionado ya que poseé items asociados");
        }
    }

    public void publicarPropuestas() {
        //CONSULTAMOS LAS PROPUESTAS ASOCIADAS AL PERIODO SELECCIONADO, 
        //CON EL ESTADO ''ENVIADA A VICIN '' O 'PRE-APROBADA ' O 'REVISADA'
        try {
            //SISTEMA VERIFICA QUE LOS COMPROMISOS PARA EL PERIODO HAYAN SIDO INGRESADO
            if (this.listaCompromiso == null || this.listaCompromiso.isEmpty()) {
                //MUESTRA MENSAJE: 'DEBE INGRESAR LOS COMPROMISOS PARA PODER PUBLICAR ', NO ES POSIBLE PUBLICAR LOS RESULTADOS  
                addErrorMessage(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_add_comprimiso_faltantes"));
                return;
            }
            String mensajeValidacion = validarCompromisosPeriodoByPropuestaNacesidad(this.propuestaSeleccionada);
            if (mensajeValidacion != null) {
                addErrorMessage(keyPropertiesFactory.value(mensajeValidacion));
                return;
            }
            if (IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA.compareTo(this.propuestaSeleccionada.getConstantes().getIdConstantes()) != 0) {
                addErrorMessage("La propuesta debe estar en estado Pre-Aprobado");
                return;
            }
            /*byte[] bitesPdf;
            //GENERAMOS EL REPORTE - CREADOR DE REPORTES
            try {
                HashMap mapa = new HashMap();
                mapa.put("p_id_periodo", periodoSeleccionado.getIdPeriodo().intValue());
                bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte15.jasper");
            } catch (Exception e) {
                adicionaMensajeError("ERROR, Se presentaron errores al general el reporte JASPER");
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-06(publicarPropuestas)", e);
                return;
            }*/
            String iniciaCodigoVic = keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion");
            if (iniciaCodigoVic.contains("-----NOT FOUND-----")) {
                addErrorMessage(keyPropertiesFactory.value("cu_ne_6_error_no_existe_codigo_proyecto_inicia_generacion"));
                return;
            }
            //EL SISTEMA CAMBIA EL ESTADO DE TODAS LAS PROPUESTAS DEL LISTADO DE ''PRE-APROBADA ' A 'APROBADA' 
            //Y LOS DE ESTADO 'REVISADA' A 'NO APROBADA' 
            List<SieduPropuestaAsignada> lstPropuestaasignada = this.servicePropuestaAsignada.findByVigencia(this.propuestaSeleccionada);
            List<Long> lstLong = new ArrayList<>();
            for (SieduPropuestaAsignada s : lstPropuestaasignada) {
                lstLong.add(s.getPropuestaNecesidad().getIdPropuestaNecesidad());
            }
            Proyecto proyecto = new Proyecto();
            int contarProyecto = iProyectoLocal.contarProyectoByVigencia(lstLong);
            Constantes constantes = iConstantesLocal.getConstantesPorIdConstante(IConstantes.DURACION_PROYECTOS_INSTITUCIONALES);
            int numeroMesesEstimacionProyecto = Integer.parseInt(constantes.getValor());
            UsuarioRol usuarioRol = new UsuarioRol(loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN).getIdUsuarioRol());
            if (this.propuestaSeleccionada.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA)) {
                EjecutorNecesidad ejecutorNecesidadResponsable = iEjecutorNecesidadLocal.getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(this.propuestaSeleccionada.getIdPropuestaNecesidad());
                if (ejecutorNecesidadResponsable == null || ejecutorNecesidadResponsable.getUnidadPolicial() == null || ejecutorNecesidadResponsable.getUnidadPolicial().getSiglaFisica() == null) {
                    addErrorMessage("Error, Verifique la sigla física de la Unidad Policial asociada a la propuesta: " + this.propuestaSeleccionada.getTema());
                    return;
                }

                this.propuestaSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBADA));
                //POR CADA PROPROPUESTA APROBADA SE CREA UN PROYECTO
                //CREA UN PROYECTO DE INVESTIGACIÓN POR CADA PROPUESTA CON EL ESTADO 'APROBADA', 
                //ASIGNÁNDOLE EL CÓDIGO DE INVESTIGACIÓN DE ACUERDO AL MÉTODO ESTABLECIDO(VER REQUERIMIENTOS ESPECIALES), 
                //ASIGNÁNDOLE EL ÁREA Y LA LÍNEA DE INVESTIGACIÓN Y EL TEMA PROPUESTO COMO TITULO PROPUESTO
                //VIC - [Consecutivo de proyectos en el periodo][Año]- [Código interno de la Unidad Policial o Escuela]
                String codigoInternoUnidad = ejecutorNecesidadResponsable.getUnidadPolicial().getSiglaFisica();
                String codigoProyecto = iniciaCodigoVic.concat("-");//VIC 
                contarProyecto += 1;
                codigoProyecto = codigoProyecto.concat(String.valueOf(contarProyecto));//[Consecutivo de proyectos en el periodo]
                codigoProyecto = codigoProyecto.concat(String.valueOf(lstPropuestaasignada.get(0).getSieduPropuestaAsignadaPK().getVigencia()));//[Año]
                codigoProyecto = codigoProyecto.concat("-");
                codigoProyecto = codigoProyecto.concat(codigoInternoUnidad);//[Código interno de la Unidad Policial o Escuela]
                Date fechaHoy = new Date();
                proyecto.setCodigoProyecto(codigoProyecto);
                proyecto.setLinea(this.propuestaSeleccionada.getLinea());
                proyecto.setTituloPropuesto(this.propuestaSeleccionada.getTema());
                proyecto.setTema(this.propuestaSeleccionada.getTema());
                proyecto.setPeriodo(this.propuestaSeleccionada.getPeriodo());
                proyecto.setUsuarioRol(usuarioRol);
                proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION));
                proyecto.setUnidadPolicial(ejecutorNecesidadResponsable.getUnidadPolicial());
                proyecto.setPropuestaNecesidad(this.propuestaSeleccionada);
                proyecto.setFechaEstimadaInicio(fechaHoy);
                Calendar fechaFinalEstimadaProyecto = Calendar.getInstance();
                fechaFinalEstimadaProyecto.setTime(fechaHoy);
                fechaFinalEstimadaProyecto.add(Calendar.MONTH, numeroMesesEstimacionProyecto);
                proyecto.setFechaEstimadaFinalizacion(fechaFinalEstimadaProyecto.getTime());
                proyecto.setFechaActualizacion(fechaHoy);
                //CREAMOS LOS COMPROMISOS PROYECTOS
                List<CompromisoProyecto> listaCompromisosProyecto = new ArrayList<CompromisoProyecto>();
                //CONSULTAMOS LOS COMPROMISOS DE ESTE PERIODO
                List<CompromisoPeriodo> listaComprimiso = iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidad(this.propuestaSeleccionada);
                for (CompromisoPeriodo unCompromisoPeriodo : listaComprimiso) {
                    CompromisoProyecto compromisoProyecto = new CompromisoProyecto();
                    compromisoProyecto.setCompromisoPeriodo(unCompromisoPeriodo);
                    compromisoProyecto.setProyecto(proyecto);
                    compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
                    compromisoProyecto.setFechaCreacion(new Date());
                    compromisoProyecto.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
                    compromisoProyecto.setUsuarioRegistro(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
                    compromisoProyecto.setUsuarioRolRegistra(usuarioRol);
                    listaCompromisosProyecto.add(compromisoProyecto);
                }
                //CREAMOS LAS UNIDADES EJECUTORAS PARA EL PROYECTO
                List<EjecutorNecesidadDTO> listadoEjecutorNecesidadDTOPropuesta = iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorPropuestaNecesidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
                List<EjecutorNecesidad> listaEjecutorNecesidadProyecto = new ArrayList<EjecutorNecesidad>();
                for (EjecutorNecesidadDTO unaEjecutorNecesidadDTO : listadoEjecutorNecesidadDTOPropuesta) {
                    EjecutorNecesidad ejecutorNecesidadLocal = new EjecutorNecesidad();
                    ejecutorNecesidadLocal.setPropuestaNecesidad(new PropuestaNecesidad(this.propuestaSeleccionada.getIdPropuestaNecesidad()));
                    ejecutorNecesidadLocal.setProyecto(proyecto);
                    ejecutorNecesidadLocal.setRol(new Constantes(unaEjecutorNecesidadDTO.getIdRol()));
                    ejecutorNecesidadLocal.setUnidadPolicial(this.iUnidadPolicialLocal.obtenerUnidadPolicialPorId(unaEjecutorNecesidadDTO.getIdUnidadPolicial()));
                    listaEjecutorNecesidadProyecto.add(ejecutorNecesidadLocal);
                }
                proyecto.setEjecutorNecesidadList(listaEjecutorNecesidadProyecto);
                proyecto.setCompromisoProyectoList(listaCompromisosProyecto);

            } else if (this.propuestaSeleccionada.getConstantes().getIdConstantes().equals(
                    IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA)) {
                this.propuestaSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA));
            }

            //ACTUALIZAMOS EL CAMPO ROL_ACTUAL
            //CON EL OBJETIVO SE SABER EN DONDE SE ENCUENTRA LA PROPUESTA
            //ESTO SE REALIZA PARA CORREGIR 
            //LA INCIDENCIA #0002754: Mientras no se publiquen los resultados de las necesidades, el estado debe ser 'Enviada a VICIN'.
            this.propuestaSeleccionada.setRolActual(IConstantes.PROPUESTA_NECESIDAD_PUBLICADA_JEFE_UNIDAD);
            /*     

            //GENERAMOS EL NOMBRE DEL ARCHIVO DEL REPORTE
            String nombreReporteUnico = "PROP_NECES_PERIODO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
            String nombreReporte = "PROP_NECES_PERIODO_" + (periodoSeleccionado.getAnio() == null ? periodoSeleccionado.getIdPeriodo().toString() : periodoSeleccionado.getAnio().toString()) + ".pdf";
             */
            //SE ACTUALIZAN LAS PROPUESTAS DE NECESIDAD
            servicePropuestaNecesidad.guardarPropuestaYgenerarProyecto(
                    this.propuestaSeleccionada,
                    proyecto);

            addInfoMessage(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_propuestas_actualizadas_ok_publicar"));

            navigationFaces.redirectFacesCuNe01();

        } catch (Exception e) {

            addErrorMessage(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-NE-06 Evaluar propuestas de necesidades de investigación - (publicarPropuestas)", e);

        }

    }

    public String validarCompromisosPeriodoByPropuestaNacesidad(PropuestaNecesidad propuestaNecesidad) throws JpaDinaeException {
        List<CompromisoPeriodo> compromisoPeriodos = iCompromisoPeriodoLocal.buscarCompromisoPeriodoByIdPropuestaNecesidad(propuestaNecesidad);
        int contFormulacion = 0, contInformeAvance = 0, contInformeFinal = 0;
        if (compromisoPeriodos.isEmpty()) {
            return "La propuesta no posee compromisos asociados";
        }
        for (CompromisoPeriodo compromiso : compromisoPeriodos) {
            if (IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO.compareTo(compromiso.getTipoCompromiso().getIdConstantes()) == 0) {
                contFormulacion++;
            } else if (IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE.compareTo(compromiso.getTipoCompromiso().getIdConstantes()) == 0) {
                contInformeAvance++;
            } else if (IConstantes.COMPROMISO_PERIODO_INFORME_FINAL.compareTo(compromiso.getTipoCompromiso().getIdConstantes()) == 0) {
                contInformeFinal++;
            }
        }
        if (contFormulacion == 0) {
            return IConstantes.VALIDACIONES_NECESIDADES_ERROR_FORMULACION_REQUERIDA;
        }
        if (contInformeAvance == 0) {
            return IConstantes.VALIDACIONES_NECESIDADES_ERROR_INFROME_AVANCE_REQUERIDO;
        }
        if (contInformeFinal == 0) {
            return IConstantes.VALIDACIONES_NECESIDADES_ERROR_INFORME_FINAL_REQUERIDO;
        }
        return null;
    }

    public void vincularUnidadFuncional() {
        if (idUnidadPolicialVinculaSeleccionado == null) {
            addErrorMessage(getPropertyFromBundle("aprobarpropuestanecesidad.vincularunidad.summary"), getPropertyFromBundle("aprobarpropuestanecesidad.vincularunidad.detail"));
            return;
        }
        if (idRolVinculaEjecutorSelecionado == null) {
            addErrorMessage(getPropertyFromBundle("aprobarpropuestanecesidad.vincularunidad.summary"), getPropertyFromBundle("aprobarpropuestanecesidad.vincularunidad.detail"));
            return;
        }
        try {
            //GUARDAMOS EL EJECUTOR
            this.servicePropuestaNecesidad.guardarPropuestaCreaEjecutorNecesidad(this.propuestaSeleccionada, this.idRolVinculaEjecutorSelecionado, this.idUnidadPolicialVinculaSeleccionado);
            //REINICIAMOS LAS VARIABLES
            idUnidadPolicialVinculaSeleccionado = -1L;
            idRolVinculaEjecutorSelecionado = -1L;
            //MENSAJE INFORMATIVO
            addInfoMessage(getPropertyFromBundle("aprobarpropuestanecesidad.vincularunidad.correct"));
            //ACTUALIZAMOS LA LISTA DE EJECUTORES
            this.listadoEjecutorNecesidad = iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorPropuestaNecesidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
            //ACTUALIMAS LA LISTA DE UNIDAD POLICIAL VINCULA
            this.listaItemUnidadPolicialVincula = iUnidadPolicialLocal.getUnidadPolicialDiferenteAEjecutorPropuestaUnidad(this.propuestaSeleccionada.getIdPropuestaNecesidad());
        } catch (Exception e) {
            addErrorMessage(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-NE-06 Evaluar propuestas de necesidades de investigación - (vincularUnidadFuncional)", e);
        }
    }

    public void aprobar() {
        this.propuestaSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBADA));
        try {
            this.servicePropuestaNecesidad.guardar(propuestaSeleccionada);
            this.buscarProgramaInvestigacion();
            this.addInfoMessage("Propuesta aprobada exitosamente");
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-06 Aprobando ", ex);
            this.addErrorMessage("No fué posible aprobar la propuesta");
        }
    }

    public void rechazar() {
        this.propuestaSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA));
        try {
            this.servicePropuestaNecesidad.guardar(propuestaSeleccionada);
            this.buscarProgramaInvestigacion();
            this.addInfoMessage("Propuesta rechazada exitosamente");
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-06 Rechazando ", ex);
            this.addErrorMessage("No fué posible rechazar la propuesta");
        }
    }

    public void editar() {
        this.editando = 1;
    }

    public void buscarProgramaInvestigacion() {
        Map<String, Object> params = new HashMap();
        if (this.idUnidadPolicial != null) {
            params.put("idUnidadPolicial", this.idUnidadPolicial);
        }
        if (this.vigencia != null) {
            params.put("vigencia", "" + this.vigencia);
        }
        if (this.modalidad != null) {
            params.put("idModalidad", this.modalidad.getId());
        }
        try {
            List<Long> lst = new ArrayList<>();
            lst.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN);
            lst.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA);
            lst.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBADA);
            lst.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION);
            params.put("idConstante", lst);
            this.propuestasAsignadas = servicePropuestaAsignada.findByFilter(params);
            if (!this.propuestasAsignadas.isEmpty()) {
                this.addInfoMessage("Búsqueda finalizada");
            } else {
                this.addInfoMessage("No existen registros para la unidad, vigencia y modalidad seleccionadas");
            }
        } catch (ServiceException ex) {
            this.addInfoMessage("No existen registros para la unidad, vigencia y modalidad seleccionadas", "No existen registros para launidad, vigencia y modalidad seleccionadas");
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-06 Consulta de propuestas asignadas ", ex);
        }
    }

    public void eliminar() throws ServiceException {
        try {
            this.comentarios.remove(comentarioSeleccionado);
            this.serviceComentario.delete(comentarioSeleccionado);
            this.addInfoMessage("Observación eliminada exitosamente");
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-06 eliminar ", ex);
            this.addErrorMessage("No fué posible eliminar la observación");
        }

    }

    public void onBack() {
        this.renderForm = true;
        this.renderPanel = true;
        this.propuestasAsignadas = new ArrayList<>();
        this.propuestaSeleccionada = new PropuestaNecesidad();
    }

    public void agregarObservacion() {
        if (this.observacion != null && !this.observacion.trim().equals("")) {
            Comentario comentario = new Comentario();
            comentario.setFecha(new Date());
            comentario.setComentario(this.observacion);
            comentario.setAutor(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            comentario.setPropuestaNecesidad(propuestaSeleccionada);
            comentario.setIdentificacion(this.loginFaces.getPerfilUsuarioDTO().getIdentificacion());
            try {
                this.serviceComentario.create(comentario);
                this.comentarios.add(comentario);
            } catch (Exception ex) {
                Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-06 Agregar comentario ", ex);
                this.addErrorMessage("No fué posible agregar comentario");
            }

        } else {
            this.addErrorMessage("La observación no puede estar vacía");
        }
    }

    public StreamedContent getArchivo() {
        try {
            if (propuestaSeleccionada != null && propuestaSeleccionada.getNombreArchivo() != null) {
                String name = keyPropertiesFactory.value("cu_me_02_dir_file_archivo") + propuestaSeleccionada.getNombreArchivoFisico();
                InputStream stream = new FileInputStream(name);
                String contentType = "application/octet-stream";
                StreamedContent download = new DefaultStreamedContent(stream, contentType, propuestaSeleccionada.getNombreArchivo());
                return download;
            }
        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05 Descarga de documento ", e);
        }
        return null;
    }

    public Dominio getModalidad() {
        return modalidad;
    }

    public void setModalidad(Dominio modalidad) {
        this.modalidad = modalidad;
    }

    public PropuestaNecesidad getPropuestaSeleccionada() {
        return propuestaSeleccionada;
    }

    public void setPropuestaSeleccionada(PropuestaNecesidad propuestaSeleccionada) {
        this.propuestaSeleccionada = propuestaSeleccionada;
    }

    public String getDocumentoFuncionario() {
        return documentoFuncionario;
    }

    public void setDocumentoFuncionario(String documentoFuncionario) {
        this.documentoFuncionario = documentoFuncionario;
    }

    public SieduBancoNecesidadPersona getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(SieduBancoNecesidadPersona funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public List<SelectItem> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<SelectItem> vigencias) {
        this.vigencias = vigencias;
    }

    public SieduProgramaInvestigacion getProgramaInvestigacionSeleccionado() {
        return programaInvestigacionSeleccionado;
    }

    public void setProgramaInvestigacionSeleccionado(SieduProgramaInvestigacion programaInvestigacionSeleccionado) {
        this.programaInvestigacionSeleccionado = programaInvestigacionSeleccionado;
    }

    public Integer getEditando() {
        return editando;
    }

    public void setEditando(Integer editando) {
        this.editando = editando;
    }

    public List<AreaCienciaTecnologia> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaCienciaTecnologia> areas) {
        this.areas = areas;
    }

    public List<Linea> getLineas2() {

        try {
            return this.lineaService.getLineasPorArea(this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia());
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Comentario getComentarioSeleccionado() {
        return comentarioSeleccionado;
    }

    public void setComentarioSeleccionado(Comentario comentarioSeleccionado) {
        this.comentarioSeleccionado = comentarioSeleccionado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<FuncionarioNecesidad> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioNecesidad> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<SieduPropuestaAsignada> getPropuestasAsignadas() {
        return propuestasAsignadas;
    }

    public void setPropuestasAsignadas(List<SieduPropuestaAsignada> propuestasAsignadas) {
        this.propuestasAsignadas = propuestasAsignadas;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Long getIdUnidadPolicial() {
        return idUnidadPolicial;
    }

    public void setIdUnidadPolicial(Long idUnidadPolicial) {
        this.idUnidadPolicial = idUnidadPolicial;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public List<EjecutorNecesidadDTO> getListadoEjecutorNecesidad() {
        return listadoEjecutorNecesidad;
    }

    public void setListadoEjecutorNecesidad(List<EjecutorNecesidadDTO> listadoEjecutorNecesidad) {
        this.listadoEjecutorNecesidad = listadoEjecutorNecesidad;
    }

    public List<UnidadPolicial> getListaItemUnidadPolicialVincula() {
        return listaItemUnidadPolicialVincula;
    }

    public void setListaItemUnidadPolicialVincula(List<UnidadPolicial> listaItemUnidadPolicialVincula) {
        this.listaItemUnidadPolicialVincula = listaItemUnidadPolicialVincula;
    }

    public Long getIdUnidadPolicialVinculaSeleccionado() {
        return idUnidadPolicialVinculaSeleccionado;
    }

    public void setIdUnidadPolicialVinculaSeleccionado(Long idUnidadPolicialVinculaSeleccionado) {
        this.idUnidadPolicialVinculaSeleccionado = idUnidadPolicialVinculaSeleccionado;
    }

    public Long getIdRolVinculaEjecutorSelecionado() {
        return idRolVinculaEjecutorSelecionado;
    }

    public void setIdRolVinculaEjecutorSelecionado(Long idRolVinculaEjecutorSelecionado) {
        this.idRolVinculaEjecutorSelecionado = idRolVinculaEjecutorSelecionado;
    }

    public List<ConstantesDTO> getListaItemRolVincula() {
        return listaItemRolVincula;
    }

    public void setListaItemRolVincula(List<ConstantesDTO> listaItemRolVincula) {
        this.listaItemRolVincula = listaItemRolVincula;
    }

    public List<CompromisoPeriodo> getListaCompromiso() {
        return listaCompromiso;
    }

    public void setListaCompromiso(List<CompromisoPeriodo> listaCompromiso) {
        this.listaCompromiso = listaCompromiso;
    }

    public CompromisoPeriodo getCompromisoSeleccionado() {
        return compromisoSeleccionado;
    }

    public void setCompromisoSeleccionado(CompromisoPeriodo compromisoSeleccionado) {
        this.compromisoSeleccionado = compromisoSeleccionado;
    }

    public List<Constantes> getTipoCompromisoList() {
        return tipoCompromisoList;
    }

    public void setTipoCompromisoList(List<Constantes> tipoCompromisoList) {
        this.tipoCompromisoList = tipoCompromisoList;
    }

    public Long getIdCompromiso() {
        return idCompromiso;
    }

    public void setIdCompromiso(Long idCompromiso) {
        this.idCompromiso = idCompromiso;
    }

    public List<Dominio> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<Dominio> modalidades) {
        this.modalidades = modalidades;
    }

    public boolean isRenderPanel() {
        return renderPanel;
    }

    public void setRenderPanel(boolean renderPanel) {
        this.renderPanel = renderPanel;
    }

    public boolean isRenderButton() {
        return renderButton;
    }

    public void setRenderButton(boolean renderButton) {
        this.renderButton = renderButton;
    }

}
