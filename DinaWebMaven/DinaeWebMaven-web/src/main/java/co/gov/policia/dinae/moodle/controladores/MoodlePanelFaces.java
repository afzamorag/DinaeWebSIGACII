/*
 * Catedra SAS
 * Copyrigth .201.
 */
package co.gov.policia.dinae.moodle.controladores;

import co.gov.policia.dinae.moodle.config.Server;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendHttp;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendDataService;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendHttpService;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador Panel de Control Peticiones REalizadas a Moodle
 * @author Ferney Duran - Catedra
 */
@Named(value = "moodlePanel")
@SessionScoped
public class MoodlePanelFaces extends AbstractController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MoodlePanelFaces.class);
    
    @EJB
    private LogMoodleSendDataService serviceLog;

    @EJB
    private LogMoodleSendHttpService serviceUri;
    
    private Server server;
    
    private Date fechaDesde;
    private Date fechaHasta;
    private String servicio;
    private String filtro;
    
    private List<SelectItem> lista = new ArrayList<SelectItem>();
    private List<LogMoodleSendHttp> registros;
    
    /**
     * Constructor
     */
    public MoodlePanelFaces() {
        LOG.trace("metodo: constructor()");
    }    
    
    /**
     * Se ejecuta a penas se instancia la clase
     */
    @PostConstruct
    private void init() {
        LOG.trace("metodo: initialize()");
        SelectItemGroup g1 = new SelectItemGroup("Usuarios");
        g1.setSelectItems(new SelectItem[] {new SelectItem("core_user_get_users","Buscar usuario")
                                           ,new SelectItem("core_user_create_users","Crear usuario")
                                           ,new SelectItem("core_user_update_users","Actualizar usuario")});
        lista.add(g1);
        g1 = new SelectItemGroup("Cursos");
        g1.setSelectItems(new SelectItem[] {new SelectItem("core_course_get_courses_by_field","Buscar curso por criterios")
                                           ,new SelectItem("core_course_duplicate_course","Duplicar Semilla")
                                           ,new SelectItem("core_course_update_courses","Actualizar Curso")
                                           ,new SelectItem("enrol_manual_enrol_users","Asignar usuario al curso")
                                           ,new SelectItem("enrol_manual_unenrol_users","Desasignar usuario del curso")
                                           ,new SelectItem("core_enrol_get_enrolled_users","Trae los usuarios asignados al curso")
                                           ,new SelectItem("core_course_delete_courses","Eliminar Curso")
                                           ,new SelectItem("core_course_create_courses","* Crear curso")
                                           ,new SelectItem("core_course_get_courses","* Buscar totos los cursos")});
        lista.add(g1);
        g1 = new SelectItemGroup("Categorias");
        g1.setSelectItems(new SelectItem[] {new SelectItem("core_course_get_categories","Buscar todas las cetegorias")});
        lista.add(g1);
        this.server = new Server(this.serviceLog,this.serviceUri);
    }    
    
    /**
     * Limpia el formulario
     */
    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        this.filtro = null;
        this.servicio = null;
        this.fechaDesde = null;
        this.fechaHasta = null;
        this.registros = null;
    }

    /** 
     * Busca los registros segun criterio
     */
    public void onFindRecords() {
        LOG.trace("metodo: onFindRecords()");
        this.registros = new ArrayList<LogMoodleSendHttp>();
        String sWhere ="";
        try {
            if(this.fechaDesde != null) {
                sWhere += " And  s.fecha >= '" + this.dateToString(this.fechaDesde,"yyyy-MM-dd") + "' "; 
            }
            if(this.fechaHasta != null) {
                sWhere += " And  s.fecha <= '" + this.dateToString(this.fechaHasta,"yyyy-MM-dd") + "' "; 
            }
            if(this.servicio != null && !this.servicio.trim().isEmpty()) {
                sWhere += " And  s.servicio = '" + this.servicio + "'";
            }
            if(this.filtro != null && !this.filtro.trim().isEmpty()) {
                sWhere += " And (";
                sWhere += " s.uri like '%" + this.filtro.trim().replaceAll("'", "''") + "%' or ";
                sWhere += " s.parameters like '%" + this.filtro.trim().replaceAll("'", "''") + "%') ";
            }
            this.registros.addAll(this.serviceUri.findFiltro(sWhere));
            addInfoMessage(getPropertyFromBundle("commons.msg.success.summary"), getPropertyFromBundle("commons.msg.success.detail"));            
        } catch (Exception ex) {
            LOG.error("metodo: onFindRecords() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("panelMoodle.msg.error.find.records"), getPropertyFromBundle("panelMoodle.msg.error.find.detail"));            
        }
    }
    
    public void onSendRecord(LogMoodleSendHttp record) {
        try {
            this.server.setError(null);
            String sValor = this.server.sendService(record.getUri(), record.getParameters(), false);
            record.setFechaRenvio(new Date());
            record.setResult(sValor);
            if(this.server.getError() != null) {
                record.setError(this.server.getError().getMessage());
            }
            try {
                this.serviceUri.update(record);
            } catch (Exception e) {}
            addInfoMessage(getPropertyFromBundle("commons.msg.success.summary"), getPropertyFromBundle("commons.msg.success.detail"));            
        } catch (Exception ex) {
            LOG.error("metodo: onSendRecord() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));            
        }
    }
    
    public String dateToString(Date dFecha, String sFormat) {
        String sFecha = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
            sFecha = formatter.format(dFecha);
        } catch (Exception e) {}
        return sFecha;
    }
    
    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<SelectItem> getLista() {
        return lista;
    }

    public void setLista(List<SelectItem> lista) {
        this.lista = lista;
    }

    public List<LogMoodleSendHttp> getRegistros() {
        return registros;
    }

    public void setRegistros(List<LogMoodleSendHttp> registros) {
        this.registros = registros;
    }
}
