package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduArchivoCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduCompromisoPK;
import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExterna;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExternaParticipante;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExternaParticipantePK;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromisoPK;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.SieduArchivoCompromisoService;
import co.gov.policia.dinae.siedu.servicios.SieduCompromisoService;
import co.gov.policia.dinae.siedu.servicios.SieduEntidadService;
import co.gov.policia.dinae.siedu.servicios.SieduInvestigacionExternaService;
import co.gov.policia.dinae.siedu.servicios.SieduInvestigacionExternaParticipanteService;
import co.gov.policia.dinae.siedu.servicios.SieduObservacionCompromisoService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@javax.inject.Named(value = "cuMe8InvestigacionExterna")
@javax.enterprise.context.SessionScoped
public class CuMe8InvestigacionExterna extends AbstractController implements Serializable {

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
    //EJB's
    @EJB
    private SieduPersonaService servicePersona;
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
    private APPService serviceAPP;
    @EJB
    private SieduObservacionCompromisoService serviceObservacion;
    @EJB
    private SieduArchivoCompromisoService serviceArchivoCompromiso;
    //
    private List<SieduInvestigacionExterna> investigaciones;
    private SieduInvestigacionExterna investigacionSeleccionada;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();
    private String documentoFuncionario;
    private List<SieduInvestigacionExternaParticipante> propParticipantes;
    private List<SieduEntidad> entidades;
    private List<SieduObservacionCompromiso> lstObservaciones;
    //0 solo lectura
    //1 Edición
    //2 Nuevo
    private Integer editando;
    private List<SieduCompromiso> compromisos;
    private SieduCompromiso compromisoSeleccionado;
    private List<SieduInvestigacionExternaParticipante> participantes;
    private String participante;
    private boolean busco = false;
    private SieduInvestigacionExternaParticipante funcionarioSeleccionado;
    private SieduCompromiso compromisoNvo;
    private SieduObservacionCompromiso observacion;
    private boolean renderPanel;
    private boolean renderboton;
    private SieduCompromisoPK compromisoPK;
    private SieduObservacionCompromisoPK observacionpk;
    private DateFormat sdf;
    private Date date;
    private String fechaMin;
    private boolean compromisoDetalle;
    private SieduArchivoCompromiso archivoSeleccionado;
    private List<SieduArchivoCompromiso> lstArchivos;
    private SieduArchivoCompromiso archivoActual;

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        try {
            //this.grupo = new GrupoInvestigacion();
            this.grupos = new ArrayList<>();
            this.entidades = new ArrayList<>();
            this.grupos = serviceGrupoInvestigacion.getListaGrupoInvestigacionTodos();
            this.investigaciones = new ArrayList<>();
            this.entidades = serviceEntidad.findAll();
            this.investigacionSeleccionada = new SieduInvestigacionExterna();
            this.busco = false;
            this.propParticipantes = new ArrayList<>();
            this.compromisos = new ArrayList<>();
            this.compromisoNvo = new SieduCompromiso();
            this.participante = "";
            this.compromisoPK = new SieduCompromisoPK();
            this.compromisoSeleccionado = new SieduCompromiso();
            this.compromisoSeleccionado.setSieduCompromisoPK(compromisoPK);
            this.observacion = new SieduObservacionCompromiso();
            this.observacion.setSieduObservacionCompromisoPK(new SieduObservacionCompromisoPK());
            this.renderPanel = true;
            this.entidad = new SieduEntidad();
            this.renderboton = false;
            this.lstObservaciones = new ArrayList<>();
            this.observacionpk = new SieduObservacionCompromisoPK();
            this.funcionarioSeleccionado = new SieduInvestigacionExternaParticipante();
            this.sdf = new SimpleDateFormat("yyyy/MM/dd");
            this.date = new Date();
            this.fechaMin = "";
            this.fechaMin = sdf.format(date);
            this.compromisoDetalle = true;
            this.archivoSeleccionado = new SieduArchivoCompromiso();
            this.lstArchivos = new ArrayList<>();
            this.archivoActual = new SieduArchivoCompromiso();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-08", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe08InvestigacionExterna();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-08", e);
        }
        return null;
    }

    public void editar(SieduInvestigacionExterna pi) {
        this.renderPanel = false;
        this.investigacionSeleccionada = pi;
        this.observacion = new SieduObservacionCompromiso();
        this.editando = 0;
        this.renderboton = false;
        this.compromisoSeleccionado = new SieduCompromiso();
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

    public void onRowSelect() {
        //this.compromisoSeleccionado = (SieduCompromiso) event.getObject();
        this.observacion = new SieduObservacionCompromiso();
    }

    public void nuevo() {
        this.investigacionSeleccionada = new SieduInvestigacionExterna();
        this.propParticipantes = new ArrayList<>();
        this.compromisos = new ArrayList<>();
        this.compromisoNvo = new SieduCompromiso();
        this.renderPanel = false;
        this.editando = 2;
        this.renderboton = true;
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
                this.compromisoSeleccionado.setObservaciones(this.serviceObservacion.findByIdCompromiso(this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso()));
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
        }
    }

    public void guardar() {
        if (editando == 1) {
            try {
                this.investigacionSeleccionada.setInveUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.investigacionSeleccionada.setInveIpMod(getHostAddress());
                this.investigacionSeleccionada.setInveMaquinaMod(getHostName());
                this.investigacionSeleccionada.setInveFechaMod(new Date());
                serviceInvestigacionExterna.update(this.investigacionSeleccionada);
                for (SieduInvestigacionExternaParticipante p : propParticipantes) {
                    if (p.getSieduInvestigacionExternaParticipantePK().getIdInvestigacionExterna() == null) {
                        p.getSieduInvestigacionExternaParticipantePK().setIdInvestigacionExterna(this.investigacionSeleccionada.getIdInve());
                        this.serviceInvestigacionExternaPersona.create(p);
                    }
                }
                for (SieduCompromiso c : compromisos) {
                    if (c.getSieduCompromisoPK().getIdCompromiso() != null) {
                        c.setCompIpMod(getHostAddress());
                        c.setCompMaquinaMod(getHostName());
                        c.setCompUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        c.setCompFechaMod(new Date());
                        this.serviceCompromiso.update(c);
                    } else {
                        c.setCompIpCrea(getHostAddress());
                        c.setCompMaquinaCrea(getHostName());
                        c.setCompUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        c.setCompFechaCrea(new Date());
                        this.serviceCompromiso.create(c);
                    }
                }
                if (!(this.observacion.getObservacion() == null)) {
                    this.guardarObservacionCompromiso();
                }
                addInfoMessage("Investigacion Externa almacenada exitosamente");
                buscar();

            } catch (ServiceException ex) {
                Logger.getLogger(CuMe7PropiedadIntelectual.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        } else {
            try {
                this.investigacionSeleccionada.setInveUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.investigacionSeleccionada.setInveFechaCrea(new Date());
                this.investigacionSeleccionada.setInveIpCrea(getHostAddress());
                this.investigacionSeleccionada.setInveMaquinaCrea(getHostName());
                this.investigacionSeleccionada = serviceInvestigacionExterna.create(this.investigacionSeleccionada);
                for (SieduInvestigacionExternaParticipante p : propParticipantes) {
                    p.getSieduInvestigacionExternaParticipantePK().setIdInvestigacionExterna(this.investigacionSeleccionada.getIdInve());
                    //p.setInvestigacionExterna(this.investigacionSeleccionada);
                    p.setInvpFechaCrea(new Date());
                    p.setInvpIpCrea(getHostAddress());
                    p.setInvpMaquinaCrea(getHostName());
                    p.setInvpUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    this.serviceInvestigacionExternaPersona.create(p);
                }
                for (SieduCompromiso c : compromisos) {
                    c.getSieduCompromisoPK().setIdInvestigacion(investigacionSeleccionada.getIdInve());
                    if (c.getSieduCompromisoPK().getIdCompromiso() != null) {
                        c.setCompIpMod(getHostAddress());
                        c.setCompMaquinaMod(getHostName());
                        c.setCompUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        c.setCompFechaMod(new Date());
                        this.serviceCompromiso.update(c);
                    } else {
                        c.setCompIpCrea(getHostAddress());
                        c.setCompMaquinaCrea(getHostName());
                        c.setCompUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                        c.setCompFechaCrea(new Date());
                        this.serviceCompromiso.create(c);
                    }
                }
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                buscar();
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        }
        editando = 0;
        this.renderboton = false;
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

    public void detallesCompromiso() throws ServiceException {
        this.compromisoDetalle = false;
        Map<String, Object> params = new HashMap();
        params.put("idCompromiso", this.compromisoSeleccionado.getSieduCompromisoPK().getIdCompromiso());
        this.lstArchivos = this.serviceArchivoCompromiso.findByFilter(params);
        this.archivoActual = lstArchivos.get(lstArchivos.size() - 1);
    }

    public void detallesCompromisoTrue() {
        this.compromisoDetalle = true;
    }

    public void eliminar() {
        try {
            this.serviceInvestigacionExterna.delete(this.investigacionSeleccionada);
            this.buscar();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (ServiceException ex) {
            if (ExceptionUtil.isException(ex, "USR_DISIGAC2.FK_SIEDU_INV_EXT_PART_INV_EXT")) {
                Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("investigacionesexternasvicin.programarcapacitaciones.fk_siedu_propu_asign_programa.summary"), getPropertyFromBundle("investigacionesexternasvicin.programarcapacitaciones.fk_siedu_propu_asign_programa.detail"));
            } else {
                Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            }
        }

    }

    public void editar() {
        this.editando = 1;
        this.renderboton = true;

    }

    //Método calcular el avance de entrega de los compromisos programados para cada investigación externa
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
        avance = (w / i) * 100;
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

    public void buscar() {
        Map<String, Object> params = new HashMap();
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
            busco = true;
            this.investigaciones = this.serviceInvestigacionExterna.findByFilter(params);

        } catch (ServiceException ex) {

            Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-08 Búsqueda de Investigaciones externas ", ex);
        }

    }

    public void agregarCompromiso() {
        try {
            if (compromisoNvo.getDescripcion() != null && compromisoNvo.getResultadoEsperado() != null && compromisoNvo.getFechaEntrega() != null) {
                SieduCompromisoPK pk = new SieduCompromisoPK();
                pk.setIdInvestigacion(investigacionSeleccionada.getIdInve());
                compromisoNvo.setSieduCompromisoPK(pk);
                compromisoNvo.setEstado('P');
                compromisos.add(compromisoNvo);
                compromisoNvo = new SieduCompromiso();
            } else {
                this.addErrorMessage(getPropertyFromBundle("investigacionesexternasvicin.compromisoagregar.error.summary"), getPropertyFromBundle("investigacionesexternasvicin.compromisoagregar.error.detail"));
            }

        } catch (Exception ex) {
            Logger.getLogger(CuMe8InvestigacionExterna.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-08 Agregar compromisos ", ex);
        }
    }

    public void agregarFuncionario() {
        if (this.documentoFuncionario != null) {
            try {
                Map<String, Object> params = new HashMap();
                params.put("documento", this.documentoFuncionario);
                List<SieduPersona> personas = this.servicePersona.findByFilter(params);
                if (personas.size() > 0) {
                    for (SieduPersona persona : personas) {
                        SieduInvestigacionExternaParticipante bnp = new SieduInvestigacionExternaParticipante();
                        SieduInvestigacionExternaParticipantePK pk = new SieduInvestigacionExternaParticipantePK();
                        //pk.setIdInvestigacionExterna(this.investigacionSeleccionada.getIdInve());
                        pk.setIdPersona(persona.getPersPers());
                        bnp.setSieduInvestigacionExternaParticipantePK(pk);
                        bnp.setPersona(persona);
                        if (!propParticipantes.contains(bnp)) {
                            this.propParticipantes.add(bnp);
                            this.documentoFuncionario = "";
                        } else {
                            this.addErrorMessage(getPropertyFromBundle("investigacionesexternasvicin.msg.error.funcionarioduplicado.summary"), getPropertyFromBundle("investigacionesexternasvicin.msg.error.funcionarioduplicado.details"));
                        }
                    }
                } else {
                    this.addInfoMessage(keyPropertiesFactory.value("cu_me_02_msg_funcionario_inexistente"));
                }

            } catch (ServiceException ex) {
                Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-08 Búsqueda de Funcionarios ", ex);
            }
        } else {
            addErrorMessage(getPropertyFromBundle("banconecesidad.msg.error.identificacionfuncionario.summary"), getPropertyFromBundle("banconecesidad.msg.error.identificacionfuncionarionodatafound.details"));
        }
    }

    public void eliminarFuncionario() throws ServiceException {
        try {
            //SieduInvestigacionExternaParticipantePK id = new SieduInvestigacionExternaParticipantePK();
            //id.setIdInvestigacionExterna(this.investigacionSeleccionada.getIdInve());
            if (funcionarioSeleccionado.getSieduInvestigacionExternaParticipantePK().getIdInvestigacionExterna() != null) {
                //id.setIdPersona(this.funcionarioSeleccionado.getSieduInvestigacionExternaParticipantePK().getIdPersona());
                this.serviceInvestigacionExternaPersona.delete(funcionarioSeleccionado);
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

    public void eliminarCompromiso() throws ServiceException {
        try {
            if (compromisoSeleccionado.getSieduCompromisoPK().getIdInvestigacion() != null) {
                this.compromisoSeleccionado.setCompIpMod(getHostAddress());
                this.compromisoSeleccionado.setCompMaquinaMod(getHostName());
                this.compromisoSeleccionado.setCompUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.compromisoSeleccionado.setCompFechaMod(new Date());
                this.serviceCompromiso.delete(compromisoSeleccionado);
                this.compromisos.remove(compromisoSeleccionado);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            } else {
                this.compromisos.remove(compromisoSeleccionado);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
            }
        } catch (Exception ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void botonVolver() {
        this.compromisos = new ArrayList<>();
        this.propParticipantes = new ArrayList<>();
        this.renderPanel = true;
    }

    public void rechazarCompromiso() {
        this.compromisoSeleccionado.setEstado('R');
        try {
            this.serviceCompromiso.update(this.compromisoSeleccionado);
        } catch (Exception ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void aprobarCompromiso() {
        this.compromisoSeleccionado.setEstado('A');
        try {
            this.serviceCompromiso.update(this.compromisoSeleccionado);
        } catch (Exception ex) {
            Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
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

    public boolean isBusco() {
        return busco;
    }

    public void setBusco(boolean busco) {
        this.busco = busco;
    }

    public List<SieduInvestigacionExternaParticipante> getPropParticipantes() {
        return propParticipantes;
    }

    public void setPropParticipantes(List<SieduInvestigacionExternaParticipante> propParticipantes) {
        this.propParticipantes = propParticipantes;
    }

    public SieduInvestigacionExternaParticipante getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(SieduInvestigacionExternaParticipante funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public void cancelarEdicion() {
        this.editando = 0;
        this.renderboton = false;
    }

    public SieduCompromiso getCompromisoSeleccionado() {
        return compromisoSeleccionado;
    }

    public void setCompromisoSeleccionado(SieduCompromiso compromisoSeleccionado) {
        this.compromisoSeleccionado = compromisoSeleccionado;
    }

    public SieduCompromiso getCompromisoNvo() {
        return compromisoNvo;
    }

    public void setCompromisoNvo(SieduCompromiso compromisoNvo) {
        this.compromisoNvo = compromisoNvo;
    }

    public SieduObservacionCompromiso getObservacion() {
        return observacion;
    }

    public void setObservacion(SieduObservacionCompromiso observacion) {
        this.observacion = observacion;
    }

    public boolean isRenderPanel() {
        return renderPanel;
    }

    public void setRenderPanel(boolean renderPanel) {
        this.renderPanel = renderPanel;
    }

    public boolean isRenderboton() {
        return renderboton;
    }

    public void setRenderboton(boolean renderboton) {
        this.renderboton = renderboton;
    }

    public SieduCompromisoPK getCompromisoPK() {
        return compromisoPK;
    }

    public void setCompromisoPK(SieduCompromisoPK compromisoPK) {
        this.compromisoPK = compromisoPK;
    }

    public List<SieduObservacionCompromiso> getLstObservaciones() {
        return lstObservaciones;
    }

    public void setLstObservaciones(List<SieduObservacionCompromiso> lstObservaciones) {
        this.lstObservaciones = lstObservaciones;
    }

    public SieduObservacionCompromisoPK getObservacionpk() {
        return observacionpk;
    }

    public void setObservacionpk(SieduObservacionCompromisoPK observacionpk) {
        this.observacionpk = observacionpk;
    }

    public String getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(String fechaMin) {
        this.fechaMin = fechaMin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompromisoDetalle() {
        return compromisoDetalle;
    }

    public void setCompromisoDetalle(boolean compromisoDetalle) {
        this.compromisoDetalle = compromisoDetalle;
    }

    public SieduArchivoCompromiso getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setArchivoSeleccionado(SieduArchivoCompromiso archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

    public List<SieduArchivoCompromiso> getLstArchivos() {
        return lstArchivos;
    }

    public void setLstArchivos(List<SieduArchivoCompromiso> lstArchivos) {
        this.lstArchivos = lstArchivos;
    }

    public SieduArchivoCompromiso getArchivoActual() {
        return archivoActual;
    }

    public void setArchivoActual(SieduArchivoCompromiso archivoActual) {
        this.archivoActual = archivoActual;
    }

}
