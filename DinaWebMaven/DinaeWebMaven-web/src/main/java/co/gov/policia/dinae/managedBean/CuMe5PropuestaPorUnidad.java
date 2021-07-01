package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.EjecutorNecesidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IFuncionarioNecesidadLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersona;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersonaPK;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacion;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacionPK;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignadaPK;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.ComentarioService;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramaInvestigacionService;
import co.gov.policia.dinae.siedu.servicios.SieduPropuestaAsignadaService;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@javax.inject.Named(value = "cuMe5PropuestaPorUnidad")
@javax.enterprise.context.SessionScoped
public class CuMe5PropuestaPorUnidad extends AbstractController implements Serializable {

    private List<SelectItem> vigencias;
    private SieduProgramaInvestigacion programaInvestigacionSeleccionado;
    private List<SieduProgramaInvestigacion> programaInvestigacionUnidades;
    private Integer editando = 0;
    private PropuestaNecesidad propuestaSeleccionada;
    private List<AreaCienciaTecnologia> areas;
    private List<SieduBancoNecesidadPersona> funcionarios;
    private List<SieduPropuestaAsignada> propuestasAsignadas;
    private List<Comentario> comentarios;
    private String documentoFuncionario;
    private FuncionarioNecesidad funcionarioSeleccionado;
    private Integer vigencia;
    private Long idUnidadPolicial;    
    private boolean renderButtonGuardar;
    private boolean renderButtonEditar;
    private UploadedFile fileUploadedFile;
    private boolean archivoPorSubir;
    private List<FuncionarioNecesidad> funcionariosNecesidad;
    private SieduPropuestaAsignada propuestaAsignada;
    private boolean validaRol = false;    

    @EJB
    private IAreaCienciaTecnologiaLocal areaService;
    @EJB
    private ILineaLocal lineaService;
    @EJB
    private SieduBancoNecesidadPersonaService serviceBancoNecesidadPersona;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private IPropuestaNecesidadLocal servicePropuestaNecesidad;
    @EJB
    private SieduProgramaInvestigacionService serviceProgramaInvestigacion;
    @EJB
    private ComentarioService serviceComentario;
    @EJB
    private IFuncionarioNecesidadLocal serviceFuncionarioNecesidad;
    @EJB
    private IUnidadPolicialLocal serviceUnidadPolicial;
    @EJB
    private IVistaFuncionarioLocal serviceVistaFuncionario;
    @EJB
    private IConstantesLocal serviceConstantes;
    @EJB
    private SieduPropuestaAsignadaService servicePropuestaAsignada;
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
            this.funcionariosNecesidad = new ArrayList<>();
            this.areas = areaService.getAreaCienciaTecnologias();
            this.programaInvestigacionUnidades = new ArrayList<>();
            if (this.areas.size() > 0) {
                this.propuestaSeleccionada = new PropuestaNecesidad();
                this.propuestaSeleccionada.setLinea(new Linea());
                this.propuestaSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
            }
            this.vigencias = new ArrayList<>();
            this.propuestaAsignada = new SieduPropuestaAsignada();
            Calendar calendar = Calendar.getInstance();
            this.renderButtonGuardar = true;
            this.renderButtonEditar = true;
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR), "" + calendar.get(Calendar.YEAR)));
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR) + 1, "" + (calendar.get(Calendar.YEAR) + 1)));
            //modalidades = serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD_PROGRAMACION.getId());
            this.idUnidadPolicial = Long.parseLong(loginFaces.getPerfilUsuarioDTO().getCodigoUnidadLaboral());
            validaRol();                   

        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe05InsertarInvestigacionUnidad();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05", e);
        }
        return null;
    }

    /*public List<Dominio> getModalidades() {
    return modalidades;
  }

  public void setModalidades(List<Dominio> modalidades) {
    this.modalidades = modalidades;
  }*/
    public List<Linea> getLineas2() {
        try {
            if (this.propuestaSeleccionada != null && this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia() != null && this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia() != null) {
                return this.lineaService.getLineasPorArea(this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia());
            }
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    public void ver(SieduPropuestaAsignada pa) {
        this.propuestaAsignada = pa;
        Map<String, Object> params = new HashMap();
        if (pa.getPropuestaNecesidad() != null && pa.getPropuestaNecesidad().getIdPropuestaNecesidad() != null) {
            this.renderButtonGuardar = false;
            this.propuestaSeleccionada = pa.getPropuestaNecesidad();
            try {
                funcionariosNecesidad = serviceFuncionarioNecesidad.getListaFuncionarioNecesidad(propuestaSeleccionada.getIdPropuestaNecesidad());
                params.put("propuestaNecesidad", propuestaSeleccionada);
                if (Objects.equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN, this.propuestaSeleccionada.getConstantes().getIdConstantes())
                        || Objects.equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBADA, this.propuestaSeleccionada.getConstantes().getIdConstantes())) {
                    this.renderButtonEditar = false;
                } else {
                    this.renderButtonEditar = true;
                }
                comentarios = serviceComentario.findByFilter(params);
                //this.renderButton = false;
            } catch (JpaDinaeException | ServiceException ex) {
                Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "CU-ME-05 Búsqueda de Funcionarios ", ex);
            }
        } else {
            try {
                this.propuestaSeleccionada = new PropuestaNecesidad();
                this.propuestaSeleccionada.setLinea(new Linea());
                this.propuestaSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                this.propuestaSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
                long l = Long.parseLong(loginFaces.getPerfilUsuarioDTO().getCodigoUnidadLaboral());
                UnidadPolicial unidad = new UnidadPolicial();
                unidad = this.serviceUnidadPolicial.obtenerUnidadPolicialPorId(l);
                this.propuestaSeleccionada.setUnidadPolicial(unidad);
                this.propuestaSeleccionada.setFechaEnvio(new Date());
                this.renderButtonGuardar = true;
                this.renderButtonEditar = false;
                this.funcionariosNecesidad = new ArrayList<>();
            } catch (JpaDinaeException ex) {
                Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "CU-ME-05 Búsqueda de Funcionarios ", ex);
            }
        }
    }

    public void enviarAVicin() {
        this.propuestaSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN));
        this.propuestaSeleccionada.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        this.propuestaSeleccionada.setIpModifica(getHostAddress());
        this.propuestaSeleccionada.setMaquinaModifica(getHostName());
        this.propuestaSeleccionada.setFechaModificacion(new Date());
        try {
            this.servicePropuestaNecesidad.guardar(propuestaSeleccionada);
            this.addInfoMessage("Necesidad enviada exitosamente");
            this.buscarProgramaInvestigacion();
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05 Enviando a VICIN ", ex);
            this.addErrorMessage("No fué posible aprobar la necesidad");
        }
    }

    public void editar() {
        this.editando = 1;
        this.renderButtonGuardar = true;
        this.renderButtonEditar = false;
    }

    public void eliminarFuncionario() {
        try {
            if (this.funcionarioSeleccionado.getIdFuncionarioNecesidad() != null) {
                this.serviceFuncionarioNecesidad.eliminarFuncionarioNecesidad(funcionarioSeleccionado.getIdFuncionarioNecesidad());
            }
            this.funcionariosNecesidad.remove(funcionarioSeleccionado);
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void guardar() throws Exception {
        if (this.propuestaSeleccionada.getIdPropuestaNecesidad() == null) {
            if (!this.funcionariosNecesidad.isEmpty()) {
                if (this.archivoPorSubir == true && this.propuestaSeleccionada.getNombreArchivo() != null) {
                    try {
                        String nombreArchivoOriginal = this.propuestaSeleccionada.getNombreArchivo();
                        String extension = "";
                        int i = nombreArchivoOriginal.lastIndexOf('.');
                        if (i > 0) {
                            extension = nombreArchivoOriginal.substring(i);
                        }
                        String nombreArchivoFisico = "PROPUESTA_NECESIDAD".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                        JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                        this.propuestaSeleccionada.setNombreArchivoFisico(nombreArchivoFisico);
                        this.propuestaSeleccionada.setIdentificadorUsuarioCrea(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
                        this.propuestaSeleccionada.setIpCrea(getHostAddress());
                        this.propuestaSeleccionada.setMaquinaCrea(getHostName());
                        this.propuestaSeleccionada.setFechaRegistro(new Date());
                        Periodo p = new Periodo();
                        p.setIdPeriodo(2L);
                        this.propuestaSeleccionada.setPeriodo(p);
                        this.propuestaSeleccionada.setConstantes(serviceConstantes.getConstantesPorIdConstante(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_SIN_ENVIAR)); // TODO: validar
                        this.servicePropuestaNecesidad.guardarPropuestaCreaEjecutorNecesidad(this.propuestaSeleccionada, null, null);
                        this.archivoPorSubir = false;
                        for (FuncionarioNecesidad fn : funcionariosNecesidad) {
                            serviceFuncionarioNecesidad.create(fn);
                        }
                        this.propuestaAsignada.getSieduPropuestaAsignadaPK().setIdPropuestaNecesidad(propuestaSeleccionada.getIdPropuestaNecesidad());
                        this.propuestaAsignada.setPpaFechaCrea(new Date());
                        this.propuestaAsignada.setPpaIpCrea(getHostAddress());
                        this.propuestaAsignada.setPpaMaquinaCrea(getHostName());
                        this.propuestaAsignada.setPpaUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        servicePropuestaAsignada.create(propuestaAsignada);
                        buscarProgramaInvestigacion();
                        this.addInfoMessage("Propuesta de Investigación guardada exitosamente");
                        this.editando = 0;
                        this.renderButtonGuardar = false;
                        this.renderButtonEditar = true;
                        RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
                    } catch (JpaDinaeException ex) {
                        Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05 Guardando ", ex);
                        this.addErrorMessage("No fué posible guardar la propuesta");
                        RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                    }
                } else {
                    addErrorMessage(getPropertyFromBundle("propuestanecesidad.archivoanexonull.summary"), getPropertyFromBundle("propuestanecesidad.archivoanexonull.detail"));
                    RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                }
            } else {
                addErrorMessage(getPropertyFromBundle("propuestanecesidad.listafuncionarioempty.summary"), getPropertyFromBundle("propuestanecesidad.listafuncionarioempty.detail"));
                RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
            }
        } else if (!this.funcionariosNecesidad.isEmpty()) {
            try {
                if (this.archivoPorSubir == true && this.propuestaSeleccionada.getNombreArchivo() != null) {
                    String nombreArchivoOriginal = this.propuestaSeleccionada.getNombreArchivo();
                    String extension = "";
                    int i = nombreArchivoOriginal.lastIndexOf('.');
                    if (i > 0) {
                        extension = nombreArchivoOriginal.substring(i);
                    }
                    String nombreArchivoFisico = "PROPUESTA_NECESIDAD".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                    JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                    this.propuestaSeleccionada.setNombreArchivoFisico(nombreArchivoFisico);
                }
                this.propuestaSeleccionada.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
                this.propuestaSeleccionada.setIpModifica(getHostAddress());
                this.propuestaSeleccionada.setMaquinaModifica(getHostName());
                this.propuestaSeleccionada.setFechaModificacion(new Date());
                Periodo p = new Periodo();
                p.setIdPeriodo(2L);
                this.propuestaSeleccionada.setPeriodo(p);
                this.propuestaSeleccionada.setConstantes(serviceConstantes.getConstantesPorIdConstante(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_SIN_ENVIAR)); // TODO: validar
                this.servicePropuestaNecesidad.guardar(this.propuestaSeleccionada);
                this.archivoPorSubir = false;
                for (FuncionarioNecesidad fn : funcionariosNecesidad) {
                    if (fn.getIdFuncionarioNecesidad() == null) {
                        serviceFuncionarioNecesidad.create(fn);
                    }
                }
                buscarProgramaInvestigacion();
                this.addInfoMessage("Propuesta de Investigación guardada exitosamente");
                this.editando = 0;
                this.renderButtonGuardar = false;
                this.renderButtonEditar = true;
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
            } catch (JpaDinaeException ex) {
                Logger.getLogger(CuMe5PropuestaPorUnidad.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05 Guardando ", ex);
                this.addErrorMessage("No fué posible guardar la propuesta");
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
            }
        } else {
            addErrorMessage(getPropertyFromBundle("propuestanecesidad.listafuncionarioempty.summary"), getPropertyFromBundle("propuestanecesidad.listafuncionarioempty.detail"));
            RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
        }
        this.propuestasAsignadas = new ArrayList<SieduPropuestaAsignada>();        
    }

    public void buscarProgramaInvestigacion() {
        this.propuestasAsignadas = new ArrayList<>();
        Map<String, Object> params = new HashMap();
        if (this.idUnidadPolicial != null) {
            params.put("unidad", this.idUnidadPolicial);
        }
        if (this.vigencia != null) {
            params.put("vigencia", this.vigencia.toString());
        }      
        try {
            programaInvestigacionUnidades = this.serviceProgramaInvestigacion.findByFilter(params);
            //programaInvestigacionSeleccionado = this.serviceProgramaInvestigacion.findById(pk);
            if (programaInvestigacionUnidades != null) {
                for (SieduProgramaInvestigacion spi : programaInvestigacionUnidades) {
                    List<SieduPropuestaAsignada> propuestasAsignadasUnidad = spi.getPropuestasAsignadas();
                    int banco = 0;
                    int noBanco = 0;
                    for (SieduPropuestaAsignada spa : propuestasAsignadasUnidad) {
                        if (spa.getPropuestaNecesidad().getIdBancoNecesidad() != null
                                && spa.getPropuestaNecesidad().getIdBancoNecesidad() != 0
                                && spa.getPropuestaNecesidad().getIdPropuestaNecesidad() != null) {
                            banco++;
                            propuestasAsignadas.add(spa);
                        } else if (spa.getPropuestaNecesidad().getIdPropuestaNecesidad() != null) {
                            noBanco++;
                            propuestasAsignadas.add(spa);
                        }
                    }
                    while (banco < spi.getNumeroInvestigacionesBanco()) {
                        SieduPropuestaAsignada spa = new SieduPropuestaAsignada();
                        SieduPropuestaAsignadaPK pk2 = new SieduPropuestaAsignadaPK();
                        pk2.setUnidad(spi.getUnidad().getIdUnidadPolicial());
                        pk2.setIdModalidad(spi.getModalidad().getId());
                        pk2.setVigencia(spi.getSieduProgramaInvestigacionPK().getVigencia());
                        spa.setSieduPropuestaAsignadaPK(pk2);
                        spa.setPropuestaNecesidad(new PropuestaNecesidad());
                        spa.getPropuestaNecesidad().setIdBancoNecesidad(-1L);
                        spa.setModalidad(spi.getModalidad());
                        spa.setProgramaInvestigacion(spi);
                        spa.setUnidad(spi.getUnidad());
                        banco++;
                        this.propuestasAsignadas.add(spa);
                    }
                    banco = banco + noBanco;
                    while (banco < spi.getNumeroInvestigaciones()) {
                        SieduPropuestaAsignada spa = new SieduPropuestaAsignada();
                        SieduPropuestaAsignadaPK pk2 = new SieduPropuestaAsignadaPK();
                        pk2.setUnidad(spi.getUnidad().getIdUnidadPolicial());
                        pk2.setIdModalidad(spi.getModalidad().getId());
                        pk2.setVigencia(spi.getSieduProgramaInvestigacionPK().getVigencia());
                        spa.setSieduPropuestaAsignadaPK(pk2);
                        spa.setModalidad(spi.getModalidad());
                        spa.setProgramaInvestigacion(spi);
                        spa.setUnidad(spi.getUnidad());
                        banco++;
                        this.propuestasAsignadas.add(spa);
                    }
                }
                //this.propuestasAsignadas = programaInvestigacionSeleccionado.getPropuestasAsignadas();
                this.addInfoMessage("Búsqueda finalizada");
            } else {
                this.addInfoMessage("No existen registros para la unidad, vigencia y modalidad seleccionadas");
            }
        } catch (ServiceException ex) {
            this.addInfoMessage("No existen registros para la unidad, vigencia y modalidad seleccionadas", "No existen registros para launidad, vigencia y modalidad seleccionadas");
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-05 Consulta de propuestas asignadas ", ex);
        }
    }

    public void agregarFuncionario() throws JpaDinaeException {
        if (this.documentoFuncionario != null) {
            VistaFuncionario vf = serviceVistaFuncionario.getVistaFuncionarioPorIdentificacion(this.documentoFuncionario);
            if (vf != null) {
                FuncionarioNecesidad fn = new FuncionarioNecesidad();
                fn.setIdentificacion(vf.getIdentificacion());
                if (!this.funcionariosNecesidad.contains(fn)) {
                    fn.setCargo(vf.getCargo());
                    fn.setCorreo(vf.getCorreo());
                    fn.setGrado(vf.getGrado());
                    fn.setIdentificacion(vf.getIdentificacion());
                    fn.setNombreCompleto(vf.getNombreCompleto());
                    fn.setPropuestaNecesidad(propuestaSeleccionada);
                    fn.setTelefono(vf.getCelular());
                    funcionariosNecesidad.add(fn);
                    this.documentoFuncionario = "";
                } else {
                    addErrorMessage(getPropertyFromBundle("propuestanecesidad.funcionarioduplicado.summary"), getPropertyFromBundle("propuestanecesidad.funcionarioduplicado.detail"));
                }
            } else {
                this.addInfoMessage(keyPropertiesFactory.value("cu_me_02_msg_funcionario_inexistente"));
            }
        } else {
            addErrorMessage(getPropertyFromBundle("banconecesidad.msg.error.identificacionfuncionario.summary"), getPropertyFromBundle("banconecesidad.msg.error.identificacionfuncionarionodatafound.details"));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            fileUploadedFile = event.getFile();
            this.propuestaSeleccionada.setNombreArchivo(event.getFile().getFileName());
            this.archivoPorSubir = true;
        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Carga de documento ", e);
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

    public String buscarUnidadFuncionario(String identificacion) {
        String unidadFuncionario = "";
        try {
            unidadFuncionario = serviceVistaFuncionario.getSiglaLaborandoVistaFuncionarioPorIdentificacion(identificacion);
        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-05 Buscar unidad funcionario ", e);
        }
        return unidadFuncionario;
    }
    
    
    public boolean validaRol() {
        validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        return validaRol;
    }

    public FuncionarioNecesidad getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(FuncionarioNecesidad funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
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

    public List<SieduBancoNecesidadPersona> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<SieduBancoNecesidadPersona> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<SieduProgramaInvestigacion> getProgramaInvestigacionUnidades() {
        return programaInvestigacionUnidades;
    }

    public void setProgramaInvestigacionUnidades(List<SieduProgramaInvestigacion> programaInvestigacionUnidades) {
        this.programaInvestigacionUnidades = programaInvestigacionUnidades;
    }

    public boolean isRenderButtonGuardar() {
        return renderButtonGuardar;
    }

    public void setRenderButtonGuardar(boolean renderButtonGuardar) {
        this.renderButtonGuardar = renderButtonGuardar;
    }

    public List<FuncionarioNecesidad> getFuncionariosNecesidad() {
        return funcionariosNecesidad;
    }

    public void setFuncionariosNecesidad(List<FuncionarioNecesidad> funcionariosNecesidad) {
        this.funcionariosNecesidad = funcionariosNecesidad;
    }

    public boolean isRenderButtonEditar() {
        return renderButtonEditar;
    }

    public void setRenderButtonEditar(boolean renderButtonEditar) {
        this.renderButtonEditar = renderButtonEditar;
    }

    public boolean isValidaRol() {
        return validaRol;
    }

    public void setValidaRol(boolean validaRol) {
        this.validaRol = validaRol;
    }      

}
