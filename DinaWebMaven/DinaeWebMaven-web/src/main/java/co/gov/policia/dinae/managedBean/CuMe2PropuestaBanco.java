package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidad;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersona;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersonaPK;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.VDatosPersonaService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
@javax.inject.Named(value = "cuMe2PropuestaBanco")
@javax.enterprise.context.SessionScoped
public class CuMe2PropuestaBanco extends AbstractController implements Serializable {

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    private List<AreaCienciaTecnologia> areas;
    private List<Linea> lineas;
    private List<Linea> lineas2;
    // Filtro
    private Long idAreaCienciaTecnologia;
    private Long idLinea;
    private String palabraClave;
    private boolean busco = false;
    private boolean renderBtnFile = false;
    private String documentoFuncionario;

    @EJB
    private IAreaCienciaTecnologiaLocal areaService;
    @EJB
    private ILineaLocal lineaService;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private SieduBancoNecesidadService service;
    @EJB
    private SieduBancoNecesidadPersonaService serviceBancoNecesidadPersona;
    @EJB
    private VDatosPersonaService serviceVDatosPersona;
    @EJB
    private IUnidadPolicialLocal serviceUnidadPolicial;

    private List<SieduBancoNecesidad> necesidadesBanco;
    private SieduBancoNecesidad necesidadBancoSeleccionada;
    private List<SieduBancoNecesidadPersona> funcionarios;
    private SieduBancoNecesidadPersona funcionarioSeleccionado;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();
    //0 solo lectura
    //1 EdiciÃƒÂ³n
    //2 Nuevo
    private Integer editando;
    private boolean archivoPorSubir;
    private UploadedFile fileUploadedFile;

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        busco = false;
        renderBtnFile = false;
        try {
            this.areas = areaService.getAreaCienciaTecnologias();
            this.lineas = new ArrayList<>();
            this.necesidadesBanco = new ArrayList<>();
            this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
            this.necesidadBancoSeleccionada.setLinea(new Linea());
            this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
            this.necesidadBancoSeleccionada.setUnidad(new UnidadPolicial());
            if (this.areas.size() > 0) {
                this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
            }
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe02CrearPropuestaBanco();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02", e);
        }
        return null;
    }

    public void editar(SieduBancoNecesidad pi) {
        this.necesidadBancoSeleccionada = pi;
        this.renderBtnFile = true;
        this.editando = 0;
        Map<String, Object> params = new HashMap();
        params.put("bancoNecesidad", pi.getIdentificadorBancoNecesidad());
        try {
            funcionarios = serviceBancoNecesidadPersona.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-02 BÃƒÂºsqueda de Funcionarios ", ex);
        }
    }

    public void nuevo() {
        this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
        this.necesidadBancoSeleccionada.setLinea(new Linea());
        this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
        this.necesidadBancoSeleccionada.setUnidad(new UnidadPolicial());
        UnidadPolicial unidad = new UnidadPolicial();
        this.renderBtnFile = false;
        try {
            long l = Long.parseLong(loginFaces.getPerfilUsuarioDTO().getCodigoUnidadLaboral());
            unidad = this.serviceUnidadPolicial.obtenerUnidadPolicialPorId(l);
            this.necesidadBancoSeleccionada.setUnidad(unidad);
        } catch (Exception ex) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-02 BÃƒÂºsqueda de Funcionarios ", ex);
        }
        this.necesidadBancoSeleccionada.setFechaPropuesta(new Date());
        this.necesidadBancoSeleccionada.setLinea(new Linea());
        this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
        if (this.areas.size() > 0) {
            this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
        }
        funcionarios = new ArrayList();
        this.editando = 2;
    }

    public void guardar() {
        if (!funcionarios.isEmpty()) {
            if (editando == 1) {
                try {
                    this.necesidadBancoSeleccionada.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    this.necesidadBancoSeleccionada.setBneIpMod(getHostAddress());
                    this.necesidadBancoSeleccionada.setBneMaquinaMod(getHostName());
                    this.necesidadBancoSeleccionada.setBneFechaMod(new Date());
                    try {
                        service.update(this.necesidadBancoSeleccionada);
                        for (SieduBancoNecesidadPersona bnp : this.funcionarios) {
                            if (bnp.getSieduBancoNecesidadPersonaPK().getIdBancoNecesidad() == null) {
                                bnp.setBnpeFechaCrea(new Date());
                                bnp.setBnpeIpCrea(getHostAddress());
                                bnp.setBnpeMaquinaCrea(getHostName());
                                bnp.setBnpeUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                                bnp.getSieduBancoNecesidadPersonaPK().setIdBancoNecesidad(this.necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
                                bnp.getSieduBancoNecesidadPersonaPK().setIdPersona(bnp.getPersona().getPersPers());
                                this.serviceBancoNecesidadPersona.create(bnp);
                            }
                        }
                        if (this.archivoPorSubir && this.necesidadBancoSeleccionada.getNombreArchivo() != null) {
                            try {
                                String nombreArchivoOriginal = this.necesidadBancoSeleccionada.getNombreArchivo();
                                String extension = "";
                                int i = nombreArchivoOriginal.lastIndexOf('.');
                                if (i > 0) {
                                    extension = nombreArchivoOriginal.substring(i);
                                }
                                String nombreArchivoFisico = "PROPUESTA_BANCO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                                JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                                necesidadBancoSeleccionada.setNombreArchivoFisico(nombreArchivoFisico);
                                this.necesidadBancoSeleccionada.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                                this.necesidadBancoSeleccionada.setBneIpMod(getHostAddress());
                                this.necesidadBancoSeleccionada.setBneMaquinaMod(getHostName());
                                this.necesidadBancoSeleccionada.setBneFechaMod(new Date());
                                service.update(this.necesidadBancoSeleccionada);                                
                                this.archivoPorSubir = false;
                            } catch (Exception ex) {
                                Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                        buscarInvestigaciones();
                        this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
                        this.necesidadBancoSeleccionada.setLinea(new Linea());
                        this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                        this.necesidadBancoSeleccionada.setUnidad(new UnidadPolicial());
                        if (this.areas.size() > 0) {
                            this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
                    } catch (Exception ex) {
                        LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
                        if (ExceptionUtil.isException(ex, "USR_DISIGAC2.UK_SIEDU_BCO_NECE_TITULO")) {
                            addErrorMessage(getPropertyFromBundle("banconecesidad.msg.error.titulopropuesto.summary"), getPropertyFromBundle("banconecesidad.msg.error.titulopropuesto.details"));
                        } else {
                            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                    RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                }
            } else {
                try {
                    this.necesidadBancoSeleccionada.setBneUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    this.necesidadBancoSeleccionada.setBneFechaCrea(new Date());
                    this.necesidadBancoSeleccionada.setBneIpCrea(getHostAddress());
                    this.necesidadBancoSeleccionada.setBneMaquinaCrea(getHostName());
                    this.necesidadBancoSeleccionada.setEstado('P');
                    try {
                        this.necesidadBancoSeleccionada = service.create(this.necesidadBancoSeleccionada);
                        for (SieduBancoNecesidadPersona bnp : this.funcionarios) {
                            bnp.setBnpeFechaCrea(new Date());
                            bnp.setBnpeIpCrea(getHostAddress());
                            bnp.setBnpeMaquinaCrea(getHostName());
                            bnp.setBnpeUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                            bnp.getSieduBancoNecesidadPersonaPK().setIdBancoNecesidad(this.necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
                            bnp.getSieduBancoNecesidadPersonaPK().setIdPersona(bnp.getPersona().getPersPers());
                            this.serviceBancoNecesidadPersona.create(bnp);
                        }
                        if (this.archivoPorSubir && this.necesidadBancoSeleccionada.getNombreArchivo() != null) {
                            try {
                                String nombreArchivoOriginal = this.necesidadBancoSeleccionada.getNombreArchivo();
                                String extension = "";
                                int i = nombreArchivoOriginal.lastIndexOf('.');
                                if (i > 0) {
                                    extension = nombreArchivoOriginal.substring(i);
                                }
                                String nombreArchivoFisico = "PROPUESTA_BANCO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                                JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                                necesidadBancoSeleccionada.setNombreArchivoFisico(nombreArchivoFisico);
                                this.necesidadBancoSeleccionada.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                                this.necesidadBancoSeleccionada.setBneIpMod(getHostAddress());
                                this.necesidadBancoSeleccionada.setBneMaquinaMod(getHostName());
                                this.necesidadBancoSeleccionada.setBneFechaMod(new Date());
                                service.update(this.necesidadBancoSeleccionada);
                                this.archivoPorSubir = false;
                            } catch (Exception ex) {
                                Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                        buscarInvestigaciones();
                        this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
                        this.necesidadBancoSeleccionada.setLinea(new Linea());
                        this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                        this.necesidadBancoSeleccionada.setUnidad(new UnidadPolicial());
                        if (this.areas.size() > 0) {
                            this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("continuar", true);                        
                    } catch (Exception ex) {
                        LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
                        if (ExceptionUtil.isException(ex, "USR_DISIGAC2.UK_SIEDU_BCO_NECE_TITULO")) {
                            addErrorMessage(getPropertyFromBundle("banconecesidad.msg.error.titulopropuesto.summary"), getPropertyFromBundle("banconecesidad.msg.error.titulopropuesto.details"));
                        } else {
                            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                    RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                }
            }
        } else {
            addErrorMessage(keyPropertiesFactory.value("cu_me_02_msg_propuesta_sin_funcionarios_relacionador"), keyPropertiesFactory.value("cu_me_02_msg_propuesta_sin_funcionarios_relacionador"));
            RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
        }
    }

    public void volver() {
        this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
        this.necesidadBancoSeleccionada.setLinea(new Linea());
        this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
        this.necesidadBancoSeleccionada.setUnidad(new UnidadPolicial());
    }

    public void eliminar() {
        try {
            SieduBancoNecesidadPersonaPK id = new SieduBancoNecesidadPersonaPK();
            if (this.funcionarioSeleccionado.getSieduBancoNecesidadPersonaPK().getIdBancoNecesidad() != null) {
                id.setIdPersona(this.funcionarioSeleccionado.getSieduBancoNecesidadPersonaPK().getIdPersona());
                id.setIdBancoNecesidad(this.necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
                this.serviceBancoNecesidadPersona.delete(id);
            }
            this.funcionarios.remove(this.funcionarioSeleccionado);
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }

    }

    public void agregarFuncionario() {
        if (this.documentoFuncionario != null) {
            try {
                boolean esta = false;
                for (SieduBancoNecesidadPersona p : this.funcionarios) {
                    if (p.getPersona().getPersNroid().equals(this.documentoFuncionario)) {
                        esta = true;
                        break;
                    }
                }
                if (!esta) {
                    Map<String, Object> params = new HashMap();
                    params.put("documento", this.documentoFuncionario);
                    List<SieduPersona> personas = this.servicePersona.findByFilter(params);
                    if (personas.size() > 0) {
                        //this.serviceBancoNecesidadPersona.create(bnp);
                        for (SieduPersona persona : personas) {
                            //SieduBancoNecesidad bn= new SieduBancoNecesidad();
                            SieduBancoNecesidadPersona bnp = new SieduBancoNecesidadPersona();
                            SieduBancoNecesidadPersonaPK pk = new SieduBancoNecesidadPersonaPK();
                            //pk.setIdBancoNecesidad(this.necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
                            pk.setIdPersona(persona.getPersPers());
                            bnp.setSieduBancoNecesidadPersonaPK(pk);
                            bnp.setPersona(persona);
                            this.funcionarios.add(bnp);
                        }
                        this.documentoFuncionario = "";
                    } else {
                        this.addInfoMessage(keyPropertiesFactory.value("cu_me_02_msg_funcionario_inexistente"));
                    }
                } else {
                    this.documentoFuncionario = "";
                    this.addErrorMessage("El funcionario ya se encuentra en la lista");
                }
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Búsqueda de Funcionarios ", ex);
            }
        } else {

            addErrorMessage("Debe ingresar el documento del funcionario", "Debe ingresar el documento del funcionario");

        }
    }

    public void buscarInvestigaciones() {
        Map<String, Object> params = new HashMap();
        if (this.idAreaCienciaTecnologia != null && this.idAreaCienciaTecnologia != 0) {
            params.put("area", this.idAreaCienciaTecnologia);
        }
        if (this.idLinea != null && this.idLinea != 0) {
            params.put("linea", this.idLinea);
        }
        if (this.palabraClave != null && !this.palabraClave.equals("")) {
            params.put("palabraClave", "%" + this.palabraClave + "%");
        }

        try {
            this.necesidadesBanco = this.service.findByFilter(params);
            busco = true;

        } catch (ServiceException ex) {

            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-02 BÃƒÂºsqueda de Investigaciones en el banco ", ex);
        }
        this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
        this.necesidadBancoSeleccionada.setLinea(new Linea());
        this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
        this.necesidadBancoSeleccionada.setUnidad(new UnidadPolicial());
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            fileUploadedFile = event.getFile();
            this.necesidadBancoSeleccionada.setNombreArchivo(event.getFile().getFileName());
            this.archivoPorSubir = true;
        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Carga de documento ", e);
        }
    }

    public StreamedContent getArchivo() {
        try {
            if (necesidadBancoSeleccionada != null && necesidadBancoSeleccionada.getNombreArchivo() != null) {
                String name = keyPropertiesFactory.value("cu_me_02_dir_file_archivo") + necesidadBancoSeleccionada.getNombreArchivoFisico();
                InputStream stream = new FileInputStream(name);
                String contentType = "application/octet-stream";
                StreamedContent download = new DefaultStreamedContent(stream, contentType, necesidadBancoSeleccionada.getNombreArchivo());
                return download;
            }
        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Descarga de documento ", e);
        }
        return null;
    }

    public void editar() {
        this.editando = 1;
        this.renderBtnFile = false;
    }

    public List<AreaCienciaTecnologia> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaCienciaTecnologia> areas) {
        this.areas = areas;
    }

    public Long getIdAreaCienciaTecnologia() {
        return idAreaCienciaTecnologia;
    }

    public void setIdAreaCienciaTecnologia(Long idAreaCienciaTecnologia) {
        this.idAreaCienciaTecnologia = idAreaCienciaTecnologia;
        try {
            this.lineas = this.lineaService.getLineasPorArea(idAreaCienciaTecnologia);
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Linea> getLineas2() {

        try {
            if (this.necesidadBancoSeleccionada != null && this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia() != null && this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia() != null) {
                return this.lineaService.getLineasPorArea(this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia());
            }
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }

    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public List<SieduBancoNecesidad> getNecesidadesBanco() {
        return necesidadesBanco;
    }

    public void setNecesidadesBanco(List<SieduBancoNecesidad> necesidadesBanco) {
        this.necesidadesBanco = necesidadesBanco;
    }

    public SieduBancoNecesidad getNecesidadBancoSeleccionada() {
        return necesidadBancoSeleccionada;
    }

    public void setNecesidadBancoSeleccionada(SieduBancoNecesidad necesidadBancoSeleccionada) {
        this.necesidadBancoSeleccionada = necesidadBancoSeleccionada;
    }

    public Integer getEditando() {
        return editando;
    }

    public void setEditando(Integer editando) {
        this.editando = editando;
    }

    public String getDocumentoFuncionario() {
        return documentoFuncionario;
    }

    public void setDocumentoFuncionario(String documentoFuncionario) {
        this.documentoFuncionario = documentoFuncionario;
    }

    public List<SieduBancoNecesidadPersona> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<SieduBancoNecesidadPersona> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public SieduBancoNecesidadPersona getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(SieduBancoNecesidadPersona funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public boolean isBusco() {
        return busco;
    }

    public void setBusco(boolean busco) {
        this.busco = busco;
    }

    /*public StreamedContent getArchivo(){
    InputStream stream= FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("");
    return new DefaultStreamedContent(stream, "", "");
  }*/
    public boolean isRenderBtnFile() {
        return renderBtnFile;
    }

    public void setRenderBtnFile(boolean renderBtnFile) {
        this.renderBtnFile = renderBtnFile;
    }
}
