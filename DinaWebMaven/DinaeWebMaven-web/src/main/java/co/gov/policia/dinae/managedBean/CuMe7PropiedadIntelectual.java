package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersona;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersonaPK;
import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualEntidad;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualEntidadPK;
import co.gov.policia.dinae.siedu.modelo.SieduPropiedadIntelectual;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.SieduEntidadService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduPropIntelectualEntidadService;
import co.gov.policia.dinae.siedu.servicios.SieduPropIntelectualPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduPropiedadIntelectualService;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.IOException;
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
import javax.faces.model.SelectItem;

import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
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
@javax.inject.Named(value = "cuMe7PropiedadIntelectual")
@javax.enterprise.context.SessionScoped
public class CuMe7PropiedadIntelectual extends AbstractController implements Serializable {

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    private List<GrupoInvestigacion> grupos;
    private List<Dominio> tiposProduccion;
    private List<SelectItem> produccionExterna;
    private List<SelectItem> registrado;
    private List<SelectItem> isbn;
    private List<SelectItem> issn;
    private List<SieduPropiedadIntelectual> propiedades;
    private SieduPropiedadIntelectual propiedadSeleccionada;
    private SieduPropIntelectualPersona funcionarioSeleccionado;
    private Date maxFecha;
    // Filtro
    private GrupoInvestigacion grupo;
    private String identificacion;
    private Dominio tipoProduccion;
    private List<SieduEntidad> entidades;
    private String documentoFuncionario;
    private Character isIsbn;
    private Character isIssn;
    private boolean renderButton;
    private boolean renderList;
    private boolean renderCamposRegistro;
    private boolean renderCampoNumeroIsbn;
    private boolean renderCampoNumeroIssn;
    private boolean renderFechaOtorga;
    private boolean edit;
    private boolean details;
    private boolean containsFuncionario;
    private boolean renderenti;
    private boolean renderdepe;
    private boolean isVicin;
    //0 solo lectura
    //1 Edición
    //2 Nuevo
    private Integer editando;
    private List<SieduPropIntelectualEntidad> propEntidades;
    private List<SieduPropIntelectualPersona> propParticipantes;
    private List<UnidadDependencia> unidades;
    private String participante;
    private Long entidad;
    private SieduPropIntelectualEntidad entidadParticipaSeleccionado;
    private SieduEntidad entidadGuardar;
    private UploadedFile fileUploadedFile;
    private boolean archivoPorSubir;

    @EJB
    private SieduEntidadService serviceEntidad;
    @EJB
    private SieduPropiedadIntelectualService servicePropiedadIntelectual;
    @EJB
    private IGrupoInvestigacionLocal serviceGrupoInvestigacion;
    @EJB
    private APPService serviceAPP;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private SieduPropIntelectualPersonaService servicePropIntelecPerService;
    @EJB
    private SieduPropIntelectualEntidadService servicePropIntelecEntService;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        try {
            grupos = serviceGrupoInvestigacion.getListaGrupoInvestigacionTodos();
            tiposProduccion = serviceAPP.consultarDominios(TipoDominioEnum.TIPO_PRODUCCION_INTELECTUAL.getId());
            produccionExterna = new ArrayList<>();
            produccionExterna.add(new SelectItem(('S'), "SI"));
            produccionExterna.add(new SelectItem(('N'), "NO"));
            this.propiedades = new ArrayList<>();
            this.entidades = serviceEntidad.findAll();
            this.registrado = new ArrayList<>();
            this.registrado.add(new SelectItem(('S'), "SI"));
            this.registrado.add(new SelectItem(('N'), "NO"));
            this.isbn = new ArrayList<>();
            this.isbn.add(new SelectItem(('S'), "SI"));
            this.isbn.add(new SelectItem(('N'), "NO"));
            this.issn = new ArrayList<>();
            this.issn.add(new SelectItem(('S'), "SI"));
            this.issn.add(new SelectItem(('N'), "NO"));
            this.propiedadSeleccionada = new SieduPropiedadIntelectual();
            this.propiedadSeleccionada.setGrupoInvestigacion(this.grupo);
            this.propiedadSeleccionada.setTipo(this.tipoProduccion);
            funcionarioSeleccionado = new SieduPropIntelectualPersona();
            documentoFuncionario = "";
            this.propParticipantes = new ArrayList<>();
            setRenderButton(false);
            entidadGuardar = new SieduEntidad();
            propEntidades = new ArrayList<>();
            entidadParticipaSeleccionado = new SieduPropIntelectualEntidad();
            this.renderList = true;
            this.renderCamposRegistro = false;
            this.renderCampoNumeroIsbn = false;
            this.renderFechaOtorga = false;
            this.edit = false;
            this.maxFecha = new Date();
            loadUnidades();
            this.isIsbn = '\0';
            this.isIssn = '\0';
            this.renderCampoNumeroIssn = false;
            this.details = false;
            this.renderenti = false;
            this.renderdepe = false;
            this.isVicin = false;
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-07", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe07PropiedadIntelectual();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-07", e);
        }
        return null;
    }

    private void loadUnidades() {
        LOG.trace("metodo: loadUnidades()");
        try {
            setUnidades(this.serviceAPP.consultarUnidadesVigentes());
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void onBack() {
        this.details = false;
    }

    public void onRowSelect(SelectEvent event) {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        this.setPropiedadSeleccionada((SieduPropiedadIntelectual) event.getObject());
        this.editando = 0;
        this.edit = true;
        this.details = true;        
        if (this.propiedadSeleccionada.getEntidad() != null) {
            this.renderenti = true;
        }
        if (this.propiedadSeleccionada.getUnidad() != null) {
            this.renderdepe = true;
        }
        if (this.propiedadSeleccionada.getRegistrado() == 'S') {
            this.renderCamposRegistro = true;
        }
        if (this.propiedadSeleccionada.getIsbn() != null) {
            this.renderCampoNumeroIsbn = true;
        }
        if (this.propiedadSeleccionada.getIssn() != null) {
            this.renderCampoNumeroIssn = true;
        }
        if (this.propiedadSeleccionada.getRegistrado() == 'S') {
            this.renderCamposRegistro = true;
        } else {
            this.renderCamposRegistro = false;
        }
        if(this.loginFaces.getPerfilUsuarioDTO().validarRol(34L) || this.propiedadSeleccionada.getPinUsuCrea().equals(this.loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado())){
            this.isVicin = true;
        }
    }

    public void editar(SelectEvent event) {
        this.setPropiedadSeleccionada((SieduPropiedadIntelectual) event.getObject());
        this.editando = 0;
        this.edit = true;
        this.details = true;
        /*if (this.propiedadSeleccionada.getTipo().getNombre().contains("PATENTE")){
            this.renderFechaOtorga = true;
        }*/
        if (this.propiedadSeleccionada.getEntidad() != null) {
            this.renderenti = true;
        }
        if (this.propiedadSeleccionada.getUnidad() != null) {
            this.renderdepe = true;
        }
        if (this.propiedadSeleccionada.getRegistrado() == 'S') {
            this.renderCamposRegistro = true;
        }
        if (this.propiedadSeleccionada.getIsbn() != null) {
            this.renderCampoNumeroIsbn = true;
        }
        if (this.propiedadSeleccionada.getIssn() != null) {
            this.renderCampoNumeroIssn = true;
        }
        if (this.propiedadSeleccionada.getRegistrado() == 'S') {
            this.renderCamposRegistro = true;
        } else {
            this.renderCamposRegistro = false;
        }
    }

    public void nuevo() {
        this.propiedadSeleccionada = new SieduPropiedadIntelectual();
        this.propiedadSeleccionada.setGrupoInvestigacion(this.grupo);
        this.propiedadSeleccionada.setTipo(this.tipoProduccion);
        //this.propiedadSeleccionada.setEsExterna('N');
        //this.propiedadSeleccionada.setRegistrado('N');
        this.renderList = false;
        this.editando = 2;
        this.edit = false;
        this.details = true;
    }

    public void cargarEntidadesUnidades() {
        if (this.propiedadSeleccionada.getEsExterna() == 'S') {
            this.renderList = true;
        } else {
            this.renderList = false;
        }
    }

    public void guardar() throws Exception {
        if (editando == 1) {
            try {
                String nombreArchivoOriginal = this.propiedadSeleccionada.getPinNombreArchivo();
                String extension = "";
                int i = nombreArchivoOriginal.lastIndexOf('.');
                if (i > 0) {
                    extension = nombreArchivoOriginal.substring(i);
                }
                String nombreArchivoFisico = "PROPIEDAD_INTELECTUAL".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                this.propiedadSeleccionada.setPinNombreArchivoFisico(nombreArchivoFisico);
                this.propiedadSeleccionada.setPinUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.propiedadSeleccionada.setPinIpMod(getHostAddress());
                this.propiedadSeleccionada.setPinMaquinaMod(getHostName());
                this.propiedadSeleccionada.setPinFechaMod(new Date());
                servicePropiedadIntelectual.update(this.propiedadSeleccionada);
                for (SieduPropIntelectualPersona p : this.propParticipantes) {
                    if (p.getSieduPropIntelectualPersonaPK().getIdPin() != null) {
                        this.servicePropIntelecPerService.update(p);
                    } else {
                        p.getSieduPropIntelectualPersonaPK().setIdPin(this.propiedadSeleccionada.getIdPin());
                        this.servicePropIntelecPerService.create(p);
                    }
                }
                for (SieduPropIntelectualEntidad e : this.propEntidades) {
                    if (e.getSieduPropIntelectualEntidadPK().getIdPin() != null) {
                        this.servicePropIntelecEntService.update(e);
                    } else {
                        e.getSieduPropIntelectualEntidadPK().setIdPin(this.propiedadSeleccionada.getIdPin());
                        this.servicePropIntelecEntService.create(e);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
                addInfoMessage("Propiedad Intelectual almacenada exitosamente");
                buscar();
                this.details = true;
            } catch (ServiceException ex) {
                RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                Logger.getLogger(CuMe7PropiedadIntelectual.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        } else {
            try {
                String nombreArchivoOriginal = this.propiedadSeleccionada.getPinNombreArchivo();
                String extension = "";
                int i = nombreArchivoOriginal.lastIndexOf('.');
                if (i > 0) {
                    extension = nombreArchivoOriginal.substring(i);
                }
                String nombreArchivoFisico = "PROPIEDAD_INTELECTUAL".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                this.propiedadSeleccionada.setPinUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.propiedadSeleccionada.setPinFechaCrea(new Date());
                this.propiedadSeleccionada.setPinIpCrea(getHostAddress());
                this.propiedadSeleccionada.setPinMaquinaCrea(getHostName());
                this.propiedadSeleccionada = servicePropiedadIntelectual.create(this.propiedadSeleccionada);
                for (SieduPropIntelectualPersona p : this.propParticipantes) {
                    if (p.getSieduPropIntelectualPersonaPK().getIdPin() == null) {
                        p.getSieduPropIntelectualPersonaPK().setIdPin(this.propiedadSeleccionada.getIdPin());
                        p.setPinpFechaCrea(new Date());
                        p.setPinpIpCrea(getHostAddress());
                        p.setPinpMaquinaCrea(getHostName());
                        p.setPinpUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        this.servicePropIntelecPerService.create(p);
                    }
                }
                for (SieduPropIntelectualEntidad e : this.propEntidades) {
                    if (e.getSieduPropIntelectualEntidadPK().getIdPin() == null) {
                        e.getSieduPropIntelectualEntidadPK().setIdPin(this.propiedadSeleccionada.getIdPin());
                        e.setPineFechaCrea(new Date());
                        e.setPineIpCrea(getHostAddress());
                        e.setPineMaquinaCrea(getHostName());
                        e.setPineUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        this.servicePropIntelecEntService.create(e);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                buscar();
                this.details = true;
                this.edit = true;
            } catch (ServiceException ex) {
                RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        }
        editando = 0;
    }

    public void eliminar() {
        try {
            this.servicePropIntelecPerService.deleteByPropiedadId(this.propiedadSeleccionada.getIdPin());
            this.servicePropIntelecEntService.deleteByPropiedadId(this.propiedadSeleccionada.getIdPin());
            this.servicePropiedadIntelectual.delete(this.propiedadSeleccionada);
            this.buscar();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void eliminarFuncionario() throws ServiceException {
        try {
            //SieduPropIntelectualPersonaPK id = new SieduPropIntelectualPersonaPK();
            //id.setIdPin(this.propiedadSeleccionada.getIdPin());
            if (funcionarioSeleccionado.getSieduPropIntelectualPersonaPK().getIdPin() != null) {
                //id.setIdPersona(this.funcionarioSeleccionado.getSieduPropIntelectualPersonaPK().getIdPersona());
                this.servicePropIntelecPerService.delete(funcionarioSeleccionado);
                this.propParticipantes.remove(funcionarioSeleccionado);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            } else {
                this.propParticipantes.remove(funcionarioSeleccionado);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            }
        } catch (Exception ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void eliminarEntidad() throws ServiceException {
        try {
            if (entidadParticipaSeleccionado.getSieduPropIntelectualEntidadPK().getIdPin() != null) {
                this.servicePropIntelecEntService.delete(entidadParticipaSeleccionado);
                this.propEntidades.remove(entidadParticipaSeleccionado);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            } else {
                this.propEntidades.remove(entidadParticipaSeleccionado);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            }
        } catch (Exception ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void buscar() {
        Map<String, Object> params = new HashMap();
        if (this.grupo != null) {
            params.put("grupo", this.grupo);
        }
        if (this.identificacion != null) {  //TODO
            params.put("identificacion", this.identificacion);
        }
        if (this.tipoProduccion != null) {
            params.put("tipoProduccion", this.tipoProduccion);
        }
        try {
            this.propiedades = this.servicePropiedadIntelectual.findByFilter(params);
            setRenderButton(true);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-07 Búsqueda de Propiedad Intelectual ", ex);
        }
    }

    public void agregarEntidad() {
        if (this.entidadGuardar != null) {
            try {
                SieduPropIntelectualEntidad bnp = new SieduPropIntelectualEntidad();
                SieduPropIntelectualEntidadPK pk = new SieduPropIntelectualEntidadPK();
                pk.setIdEntidad(entidadGuardar.getEntiEnti());
                bnp.setSieduPropIntelectualEntidadPK(pk);
                bnp.setEntidad(entidadGuardar);
                if (!propEntidades.contains(bnp)) {
                    propEntidades.add(bnp);
                } else {
                    addErrorMessage(getPropertyFromBundle("propiedadIntelectual.msg.error.entidadduplicada.summary"), getPropertyFromBundle("propiedadIntelectual.msg.error.entidadduplicada.details"));
                }
            } catch (Exception ex) {
                Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-07 Agregar entidades ", ex);
            }
        } else {
            addErrorMessage(getPropertyFromBundle("propiedadIntelectual.msg.error.entidadvacia.summary"), getPropertyFromBundle("propiedadIntelectual.msg.error.entidadvacia.detail"));
        }
    }

    public void agregarFuncionario() {
        this.containsFuncionario = false;
        if (this.documentoFuncionario != null) {
            try {
                for (SieduPropIntelectualPersona p : propParticipantes) {
                    if (p.getPersona().getPersNroid().equals(this.documentoFuncionario)) {
                        this.containsFuncionario = true;
                        break;
                    }
                }
                if (this.containsFuncionario == false) {
                    Map<String, Object> params = new HashMap();
                    params.put("documento", this.documentoFuncionario);
                    List<SieduPersona> personas = this.servicePersona.findByFilter(params);
                    if (personas.size() > 0) {
                        //this.serviceBancoNecesidadPersona.create(bnp);
                        for (SieduPersona persona : personas) {
                            //SieduBancoNecesidad bn= new SieduBancoNecesidad();
                            SieduPropIntelectualPersona bnp = new SieduPropIntelectualPersona();
                            SieduPropIntelectualPersonaPK pk = new SieduPropIntelectualPersonaPK();
                            //pk.setIdPin(this.propiedadSeleccionada.getIdPin());
                            pk.setIdPersona(persona.getPersPers());
                            bnp.setSieduPropIntelectualPersonaPK(pk);
                            bnp.setPersona(persona);
                            this.propParticipantes.add(bnp);
                            this.documentoFuncionario = "";
                            break;
                        }
                    } else {
                        this.addInfoMessage(keyPropertiesFactory.value("cu_me_02_msg_funcionario_inexistente"));
                    }
                } else {
                    this.addErrorMessage(keyPropertiesFactory.value("cu_me_07_msg_funcionario_existente"));
                }

            } catch (ServiceException ex) {
                Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-07 Búsqueda de Funcionarios ", ex);
            }
        } else {
            addErrorMessage(getPropertyFromBundle("banconecesidad.msg.error.identificacionfuncionario.summary"), getPropertyFromBundle("banconecesidad.msg.error.identificacionfuncionarionodatafound.details"));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            fileUploadedFile = event.getFile();
            this.propiedadSeleccionada.setPinNombreArchivo(event.getFile().getFileName());
            this.archivoPorSubir = true;
        } catch (Exception e) {
            Logger.getLogger(CuMe7PropiedadIntelectual.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-07 Carga de documento ", e);
        }
    }

    public StreamedContent getArchivo() {

        try {
            if (this.propiedadSeleccionada != null && this.propiedadSeleccionada.getPinNombreArchivo() != null) {
                String name = keyPropertiesFactory.value("cu_me_02_dir_file_archivo") + this.propiedadSeleccionada.getPinNombreArchivoFisico();
                InputStream stream = new FileInputStream(name);
                String contentType = "application/octet-stream";
                StreamedContent download = new DefaultStreamedContent(stream, contentType, this.propiedadSeleccionada.getPinNombreArchivo());
                return download;
            }

        } catch (Exception e) {
            Logger.getLogger(CuMe7PropiedadIntelectual.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-07 Descarga de documento ", e);
        }
        return null;
    }

    public void onRegistroChange() {
        if (this.propiedadSeleccionada.getRegistrado() == 'S') {
            this.renderCamposRegistro = true;
        } else {
            this.renderCamposRegistro = false;
            this.propiedadSeleccionada.setFechaRegistro(null);
            this.propiedadSeleccionada.setNroRegistro("");
        }
    }

    public void onIsbnChange() {
        if (this.isIsbn == 'S') {
            renderCampoNumeroIsbn = true;
        } else {
            renderCampoNumeroIsbn = false;
            this.propiedadSeleccionada.setIsbn("");
        }
    }

    public void onTipoPropiedadChange() {
        if (this.propiedadSeleccionada.getTipo().getNombre().contains("PATENTE")) {
            this.renderFechaOtorga = true;
        } else {
            this.renderFechaOtorga = false;
            this.propiedadSeleccionada.setFechaOtorgamiento(null);
        }
    }

    public void onIssnChange() {
        if (this.isIssn == 'S') {
            renderCampoNumeroIssn = true;
        } else {
            renderCampoNumeroIssn = false;
            this.propiedadSeleccionada.setIssn("");
        }
    }

    public void onBuscar() {
        edit = false;
    }

    public void editar() {
        this.editando = 1;
        this.edit = false;
        if (this.propiedadSeleccionada.getIsbn() != null) {
            this.setIsIsbn('S');
        } else {
            this.setIsIsbn('N');
        }
        if (this.propiedadSeleccionada.getIssn() != null) {
            this.setIsIssn('S');
        } else {
            this.setIsIssn('N');
        }
        if (this.propiedadSeleccionada.getTipo().getNombre().contains("PATENTE")){
            this.renderFechaOtorga = true;
        }
        this.isVicin = false;
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

    public GrupoInvestigacion getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoInvestigacion grupo) {
        this.grupo = grupo;
    }

    public List<GrupoInvestigacion> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoInvestigacion> grupos) {
        this.grupos = grupos;
    }

    public List<SieduEntidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<SieduEntidad> entidades) {
        this.entidades = entidades;
    }

    public List<Dominio> getTiposProduccion() {
        return tiposProduccion;
    }

    public void setTiposProduccion(List<Dominio> tiposProduccion) {
        this.tiposProduccion = tiposProduccion;
    }

    public List<SelectItem> getProduccionExterna() {
        return produccionExterna;
    }

    public void setProduccionExterna(List<SelectItem> produccionExterna) {
        this.produccionExterna = produccionExterna;
    }

    public List<SelectItem> getRegistrado() {
        return registrado;
    }

    public void setRegistrado(List<SelectItem> registrado) {
        this.registrado = registrado;
    }

    public List<SieduPropiedadIntelectual> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<SieduPropiedadIntelectual> propiedades) {
        this.propiedades = propiedades;
    }

    public SieduPropiedadIntelectual getPropiedadSeleccionada() {
        return propiedadSeleccionada;
    }

    public void setPropiedadSeleccionada(SieduPropiedadIntelectual propiedadSeleccionada) {
        this.propiedadSeleccionada = propiedadSeleccionada;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Dominio getTipoProduccion() {
        return tipoProduccion;
    }

    public void setTipoProduccion(Dominio tipoProduccion) {
        this.tipoProduccion = tipoProduccion;
    }

    public List<SieduPropIntelectualEntidad> getPropEntidades() {
        return propEntidades;
    }

    public void setPropEntidades(List<SieduPropIntelectualEntidad> propEntidades) {
        this.propEntidades = propEntidades;
    }

    public List<SieduPropIntelectualPersona> getPropParticipantes() {
        return propParticipantes;
    }

    public void setPropParticipantes(List<SieduPropIntelectualPersona> propParticipantes) {
        this.propParticipantes = propParticipantes;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }

    public SieduPropIntelectualPersona getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(SieduPropIntelectualPersona funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public boolean isRenderButton() {
        return renderButton;
    }

    public void setRenderButton(boolean renderButton) {
        this.renderButton = renderButton;
    }

    public SieduPropIntelectualEntidad getEntidadParticipaSeleccionado() {
        return entidadParticipaSeleccionado;
    }

    public void setEntidadParticipaSeleccionado(SieduPropIntelectualEntidad entidadParticipaSeleccionado) {
        this.entidadParticipaSeleccionado = entidadParticipaSeleccionado;
    }

    public SieduEntidad getEntidadGuardar() {
        return entidadGuardar;
    }

    public void setEntidadGuardar(SieduEntidad entidadGuardar) {
        this.entidadGuardar = entidadGuardar;
    }

    public boolean isRenderList() {
        return renderList;
    }

    public void setRenderList(boolean renderList) {
        this.renderList = renderList;
    }

    public boolean isArchivoPorSubir() {
        return archivoPorSubir;
    }

    public void setArchivoPorSubir(boolean archivoPorSubir) {
        this.archivoPorSubir = archivoPorSubir;
    }

    public List<UnidadDependencia> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadDependencia> unidades) {
        this.unidades = unidades;
    }

    public boolean isRenderCamposRegistro() {
        return renderCamposRegistro;
    }

    public void setRenderCamposRegistro(boolean renderCamposRegistro) {
        this.renderCamposRegistro = renderCamposRegistro;
    }

    public boolean isRenderFechaOtorga() {
        return renderFechaOtorga;
    }

    public void setRenderFechaOtorga(boolean renderFechaOtorga) {
        this.renderFechaOtorga = renderFechaOtorga;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public Date getMaxFecha() {
        return maxFecha;
    }

    public void setMaxFecha(Date maxFecha) {
        this.maxFecha = maxFecha;
    }

    public List<SelectItem> getIsbn() {
        return isbn;
    }

    public void setIsbn(List<SelectItem> isbn) {
        this.isbn = isbn;
    }

    public boolean isRenderCampoNumeroIsbn() {
        return renderCampoNumeroIsbn;
    }

    public void setRenderCampoNumeroIsbn(boolean renderCampoNumeroIsbn) {
        this.renderCampoNumeroIsbn = renderCampoNumeroIsbn;
    }

    public Character getIsIssn() {
        return isIssn;
    }

    public void setIsIssn(Character isIssn) {
        this.isIssn = isIssn;
    }

    public boolean isRenderCampoNumeroIssn() {
        return renderCampoNumeroIssn;
    }

    public void setRenderCampoNumeroIssn(boolean renderCampoNumeroIssn) {
        this.renderCampoNumeroIssn = renderCampoNumeroIssn;
    }

    public List<SelectItem> getIssn() {
        return issn;
    }

    public void setIssn(List<SelectItem> issn) {
        this.issn = issn;
    }

    public Character getIsIsbn() {
        return isIsbn;
    }

    public void setIsIsbn(Character isIsbn) {
        this.isIsbn = isIsbn;
    }

    public boolean isDetails() {
        return details;
    }

    public void setDetails(boolean details) {
        this.details = details;
    }

    public boolean isContainsFuncionario() {
        return containsFuncionario;
    }

    public void setContainsFuncionario(boolean containsFuncionario) {
        this.containsFuncionario = containsFuncionario;
    }

    public boolean isRenderenti() {
        return renderenti;
    }

    public void setRenderenti(boolean renderenti) {
        this.renderenti = renderenti;
    }

    public boolean isRenderdepe() {
        return renderdepe;
    }

    public void setRenderdepe(boolean renderdepe) {
        this.renderdepe = renderdepe;
    }

    public boolean isIsVicin() {
        return isVicin;
    }

    public void setIsVicin(boolean isVicin) {
        this.isVicin = isVicin;
    }

}
