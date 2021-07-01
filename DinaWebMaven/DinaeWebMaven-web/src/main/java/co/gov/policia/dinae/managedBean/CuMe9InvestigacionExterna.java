package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduArchivoCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduArchivoCompromisoPK;
import co.gov.policia.dinae.siedu.modelo.SieduCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExterna;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExternaParticipante;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromisoPK;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.SieduArchivoCompromisoService;
import co.gov.policia.dinae.siedu.servicios.SieduCompromisoJPAService;
import co.gov.policia.dinae.siedu.servicios.SieduCompromisoService;
import co.gov.policia.dinae.siedu.servicios.SieduEntidadService;
import co.gov.policia.dinae.siedu.servicios.SieduInvestigacionExternaParticipanteJPAService;
import co.gov.policia.dinae.siedu.servicios.SieduInvestigacionExternaParticipanteService;
import co.gov.policia.dinae.siedu.servicios.SieduInvestigacionExternaService;
import co.gov.policia.dinae.siedu.servicios.SieduObservacionCompromisoService;
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
@javax.inject.Named(value = "cuMe9InvestigacionExterna")
@javax.enterprise.context.SessionScoped
public class CuMe9InvestigacionExterna extends AbstractController implements Serializable {

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    private List<GrupoInvestigacion> grupos;
    // Filtro
    private GrupoInvestigacion grupo;
    private String identificacion;
    private SieduEntidad entidad;
    private String titulo;
    private List<SieduEntidad> entidades;
    private UploadedFile fileUploadedFile;
    private List<SieduObservacionCompromiso> lstObservaciones;
    private List<SieduObservacionCompromiso> lstObservacionComprmiso;
    private List<SieduArchivoCompromiso> lstArchivos;

    @EJB
    private SieduArchivoCompromisoService serviceArchivoCompromiso;
    @EJB
    private SieduInvestigacionExternaService serviceInvestigacionExterna;
    @EJB
    private SieduInvestigacionExternaParticipanteService serviceInvestigacionExternaPersona;
    @EJB
    private SieduCompromisoService serviceCompromiso;
    @EJB
    private SieduEntidadService serviceEntidad;
    @EJB
    private IGrupoInvestigacionLocal serviceGrupoInvestigacion;
    @EJB
    private IUnidadDependenciaLocal serviceUnidadPolicia;
    @EJB
    private SieduObservacionCompromisoService serviceObservacion;
    private APPService serviceAPP;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();

    //0 solo lectura
    //1 Edición
    //2 Nuevo
    private Integer editando;
    private List<SieduCompromiso> compromisos;
    private SieduCompromiso compromisoSeleccionado;
    private List<SieduInvestigacionExternaParticipante> participantes;
    private String participante;
    private List<SieduInvestigacionExternaParticipante> propParticipantes;
    private boolean renderForm;
    private String documentoFuncionario;
    private List<SieduInvestigacionExterna> investigaciones;
    private SieduInvestigacionExterna investigacionSeleccionada;
    private boolean archivoPorSubir;
    private SieduObservacionCompromiso observacion;
    private SieduObservacionCompromisoPK observacionpk;
    private SieduArchivoCompromiso archivoCompromiso;
    private SieduArchivoCompromisoPK archivoCompromisoPK;
    private boolean renderGrid;
    private Date fechaActual;
    private SieduArchivoCompromiso archivoSeleccionado;

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        try {
            grupos = serviceGrupoInvestigacion.getListaGrupoInvestigacionTodos();
            this.investigaciones = new ArrayList<>();
            this.entidades = serviceEntidad.findAll();
            this.investigacionSeleccionada = new SieduInvestigacionExterna();
            this.renderForm = true;
            this.compromisoSeleccionado = new SieduCompromiso();
            this.archivoPorSubir = false;
            this.lstObservaciones = new ArrayList<>();
            this.lstObservacionComprmiso = new ArrayList<>();
            this.lstArchivos = new ArrayList<>();
            this.observacion = new SieduObservacionCompromiso();
            this.observacionpk = new SieduObservacionCompromisoPK();
            this.archivoCompromiso = new SieduArchivoCompromiso();
            this.archivoCompromisoPK = new SieduArchivoCompromisoPK();
            this.renderGrid = true;
            this.archivoSeleccionado = new SieduArchivoCompromiso();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-09", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe09InvestigacionExternaUnidad();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-09", e);
        }
        return null;
    }

    public void editar(SieduInvestigacionExterna pi) {
        this.renderForm = false;
        this.investigacionSeleccionada = pi;
        this.editando = 0;
        Map<String, Object> params = new HashMap();
        params.put("investigacion", this.investigacionSeleccionada);
        try {
            this.compromisos = this.serviceCompromiso.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.propParticipantes = this.serviceInvestigacionExternaPersona.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevo() {
        this.investigacionSeleccionada = new SieduInvestigacionExterna();
        this.investigacionSeleccionada.setGrupoInvestigacion(this.grupo);
        this.investigacionSeleccionada.setEntidad(this.entidad);
        this.editando = 2;
    }

    public void guardar() throws JpaDinaeException, Exception {
        if (this.archivoCompromiso.getNombreArchivo() != null && this.archivoPorSubir == true) {
            try {
                Long idArchivo = -1L;
                List<SieduArchivoCompromiso> lstArchComp = new ArrayList<>();
                Map<String, Object> params = new HashMap();
                params.put("idCompromiso", this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso());
                params.put("idInvestigacion", this.compromisoSeleccionado.getSieduCompromisoPK().getIdInvestigacion());
                lstArchComp = serviceArchivoCompromiso.findByFilter(params);
                if (lstArchComp.isEmpty()) {
                    idArchivo = 1L;
                } else {
                    idArchivo = Long.valueOf(lstArchComp.size());
                    idArchivo += 1;
                }
                String nombreArchivoOriginal = this.archivoCompromiso.getNombreArchivo();
                String extension = "";
                int i = nombreArchivoOriginal.lastIndexOf('.');
                if (i > 0) {
                    extension = nombreArchivoOriginal.substring(i);
                }
                String nombreArchivoFisico = "COMPROMISO_ARCHIVO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);
                JSFUtils.copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_me_02_dir_file_archivo"), this.fileUploadedFile.getInputstream(), nombreArchivoFisico);
                this.archivoCompromiso.setNombreArchivoFisico(nombreArchivoFisico);
                this.archivoCompromiso.setArcoUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.archivoCompromiso.setArcoIpCrea(getHostAddress());
                this.archivoCompromiso.setArcoMaquinaCrea(getHostName());
                this.archivoCompromiso.setArcoFechaCrea(new Date());
                this.archivoCompromisoPK.setIdCompromiso(this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso());
                this.archivoCompromisoPK.setIdInvestigacion(this.compromisoSeleccionado.getSieduCompromisoPK().getIdInvestigacion());
                this.archivoCompromisoPK.setIdArchivo(idArchivo);
                this.archivoCompromiso.setSieduArchivoCompromisoPK(archivoCompromisoPK);
                this.serviceArchivoCompromiso.create(this.archivoCompromiso);
                this.guardarObservacionCompromiso();
                addInfoMessage(getPropertyFromBundle("investigacionesexternasunidad.msg.confirmarenvioarchivo"));
                buscar();
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe9InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        } else {
            addErrorMessage(getPropertyFromBundle("propuestanecesidad.archivoanexonull.summary"), getPropertyFromBundle("propuestanecesidad.archivoanexonull.detail"));
        }
    }

    public void renderGridFalse(SieduCompromiso compromiso) {
        this.compromisoSeleccionado = compromiso;
        this.renderGrid = false;
        this.fechaActual = new Date();
        Map<String, Object> params = new HashMap();
        params.put("idCompromiso", compromiso.getSieduCompromisoPK().getIdCompromiso());
        try {
            this.lstObservacionComprmiso = this.serviceObservacion.findByIdCompromiso(compromiso.getSieduCompromisoPK().getIdCompromiso());
            this.lstArchivos = this.serviceArchivoCompromiso.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe9InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void renderGridTrue() {
        this.renderGrid = true;
    }
    
        public StreamedContent getArchivo() {
        try {
            if (this.archivoSeleccionado != null && this.archivoSeleccionado.getNombreArchivo() != null) {
                String name = keyPropertiesFactory.value("cu_me_02_dir_file_archivo") + this.archivoSeleccionado.getNombreArchivoFisico();
                InputStream stream = new FileInputStream(name);
                String contentType = "application/octet-stream";
                StreamedContent download = new DefaultStreamedContent(stream, contentType, this.archivoSeleccionado.getNombreArchivo());
                return download;
            }

        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Descarga de documento ", e);
        }
        return null;
    }

    public void eliminar() throws JpaDinaeException {
        try {
            this.serviceInvestigacionExterna.delete(this.investigacionSeleccionada);
            this.buscar();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe9InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void editar() {
        this.editando = 1;
        this.renderForm = true;
    }

    public void guardarObservacionCompromiso() {
        Long idObservacion = -1L;
        try {
            lstObservaciones = this.serviceObservacion.findByIdCompromiso(this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso());
            if (lstObservaciones.isEmpty()) {
                idObservacion = 1L;
            } else {
                idObservacion = Long.valueOf(lstObservaciones.size());
                idObservacion += 1;
            }
            observacionpk.setIdObservacion(idObservacion);
            observacionpk.setIdInvestigacion(this.investigacionSeleccionada.getIdInve());
            observacionpk.setIdCompromiso(this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso());
            this.observacion.setFecha(new Date());
            this.observacion.setSieduObservacionCompromisoPK(observacionpk);
            this.observacion.setObcoFechaCrea(new Date());
            this.observacion.setObcoIpCrea(getHostAddress());
            this.observacion.setObcoMaquinaCrea(getHostName());
            this.observacion.setObcoUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            this.observacion.setUsuarioObserva(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            try {
                this.serviceObservacion.create(observacion);
                this.observacion = new SieduObservacionCompromiso();
                this.compromisoSeleccionado.setEstado('E');
                this.compromisoSeleccionado.setCompFechaMod(new Date());
                this.compromisoSeleccionado.setCompIpMod(getHostAddress());
                this.compromisoSeleccionado.setCompMaquinaMod(getHostName());
                this.compromisoSeleccionado.setCompUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.serviceCompromiso.update(this.compromisoSeleccionado);
                this.compromisoSeleccionado.setObservaciones(this.serviceObservacion.findByIdCompromiso(this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso()));
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            fileUploadedFile = event.getFile();
            this.archivoCompromiso.setNombreArchivo(event.getFile().getFileName());
            this.archivoPorSubir = true;
        } catch (Exception e) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-09 Carga de documento ", e);
        }
    }

    public String avanceAprobados(SieduInvestigacionExterna registro) {
        List<SieduCompromiso> listCompromisos = registro.getCompromisos();
        float i = listCompromisos.size();
        float w = 0;
        float avance = 0;
        String porcentajeAvance = "";
        for (SieduCompromiso compromiso : listCompromisos) {
            if (compromiso.getEstado() == 'A') {
                w += 1;
            }
        }
        avance = (w/i);
        avance = (avance*100);
        porcentajeAvance = String.valueOf(avance) + '%';
        return porcentajeAvance;
    }

    //Método para contar los compromisos aprobados
    public String compromisosAprobados(SieduInvestigacionExterna registro) {
        List<SieduCompromiso> listCompromisos = registro.getCompromisos();
        int i = listCompromisos.size();
        int w = 0;
        String porcentajeAvance = "";
        for (SieduCompromiso compromiso : listCompromisos) {
            if (compromiso.getEstado() == 'A') {
                w += 1;
            }
        }
        porcentajeAvance = String.valueOf(w) + '/' + String.valueOf(i);
        return porcentajeAvance;
    }

    public void buscar() throws JpaDinaeException {
        Map<String, Object> params = new HashMap();
        UnidadDependencia unidad = new UnidadDependencia();
        unidad = this.serviceUnidadPolicia.getUnidadDependenciaById(this.loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());
        params.put("unidad", unidad);
        if (this.grupo != null) {
            params.put("grupo", this.grupo);
        }
        if (this.entidad != null) {
            params.put("entidad", this.entidad);
        }
        if (this.identificacion != null) {  //TODO
            params.put("identificacion", this.identificacion);
        }
        if (this.titulo != null) {
            params.put("titulo", "%" + this.titulo + "%");
        }
        try {
            this.investigaciones = this.serviceInvestigacionExterna.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe9InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-09 Búsqueda de Investigaciones externas ", ex);
        }
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public SieduEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(SieduEntidad entidad) {
        this.entidad = entidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<SieduInvestigacionExterna> getInvestigaciones() {
        return investigaciones;
    }

    public void setInvestigaciones(List<SieduInvestigacionExterna> investigaciones) {
        this.investigaciones = investigaciones;
    }

    public SieduInvestigacionExterna getInvestigacionSeleccionada() {
        return investigacionSeleccionada;
    }

    public void setInvestigacionSeleccionada(SieduInvestigacionExterna investigacionSeleccionada) {
        this.investigacionSeleccionada = investigacionSeleccionada;
    }

    public List<SieduCompromiso> getCompromisos() {
        return compromisos;
    }

    public void setCompromisos(List<SieduCompromiso> compromisos) {
        this.compromisos = compromisos;
    }

    public List<SieduInvestigacionExternaParticipante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<SieduInvestigacionExternaParticipante> participantes) {
        this.participantes = participantes;
    }

    public SieduCompromiso getCompromisoSeleccionado() {
        return compromisoSeleccionado;
    }

    public void setCompromisoSeleccionado(SieduCompromiso compromisoSeleccionado) {
        this.compromisoSeleccionado = compromisoSeleccionado;
    }

    public List<SieduInvestigacionExternaParticipante> getPropParticipantes() {
        return propParticipantes;
    }

    public void setPropParticipantes(List<SieduInvestigacionExternaParticipante> propParticipantes) {
        this.propParticipantes = propParticipantes;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public boolean isArchivoPorSubir() {
        return archivoPorSubir;
    }

    public void setArchivoPorSubir(boolean archivoPorSubir) {
        this.archivoPorSubir = archivoPorSubir;
    }

    public List<SieduObservacionCompromiso> getLstObservaciones() {
        return lstObservaciones;
    }

    public void setLstObservaciones(List<SieduObservacionCompromiso> lstObservaciones) {
        this.lstObservaciones = lstObservaciones;
    }

    public SieduObservacionCompromiso getObservacion() {
        return observacion;
    }

    public void setObservacion(SieduObservacionCompromiso observacion) {
        this.observacion = observacion;
    }

    public SieduObservacionCompromisoPK getObservacionpk() {
        return observacionpk;
    }

    public void setObservacionpk(SieduObservacionCompromisoPK observacionpk) {
        this.observacionpk = observacionpk;
    }

    public SieduArchivoCompromiso getArchivoCompromiso() {
        return archivoCompromiso;
    }

    public void setArchivoCompromiso(SieduArchivoCompromiso archivoCompromiso) {
        this.archivoCompromiso = archivoCompromiso;
    }

    public SieduArchivoCompromisoPK getArchivoCompromisoPK() {
        return archivoCompromisoPK;
    }

    public void setArchivoCompromisoPK(SieduArchivoCompromisoPK archivoCompromisoPK) {
        this.archivoCompromisoPK = archivoCompromisoPK;
    }

    public boolean isRenderGrid() {
        return renderGrid;
    }

    public void setRenderGrid(boolean renderGrid) {
        this.renderGrid = renderGrid;
    }

    public List<SieduObservacionCompromiso> getLstObservacionComprmiso() {
        return lstObservacionComprmiso;
    }

    public void setLstObservacionComprmiso(List<SieduObservacionCompromiso> lstObservacionComprmiso) {
        this.lstObservacionComprmiso = lstObservacionComprmiso;
    }

    public List<SieduArchivoCompromiso> getLstArchivos() {
        return lstArchivos;
    }

    public void setLstArchivos(List<SieduArchivoCompromiso> lstArchivos) {
        this.lstArchivos = lstArchivos;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public SieduArchivoCompromiso getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setArchivoSeleccionado(SieduArchivoCompromiso archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

}
