/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.modelo.HorasDocenteCapacitacion;
import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.servicios.SieduDocenteEventoService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class HorasDocenteCapacitacionController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(HorasDocenteCapacitacionController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private SieduDocenteEventoService service;
    private List<HorasDocenteCapacitacion> list;
    private Date fechaInicioConsulta;
    private Date fechaFinConsulta;
    private Date fechaCreaConsulta;
    private boolean renderComponent;
    private boolean renderForm;
    private boolean renderGeneral;
    private Long escuela;
    private SieduDocenteEvento horasDocenteCapacitacionSelected;
    private String identificacion;

    public HorasDocenteCapacitacionController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        identificacion = "";
        this.renderComponent = false;
        this.renderForm = true;
        this.renderGeneral = true;
        horasDocenteCapacitacionSelected = new SieduDocenteEvento();
        escuela = -1L;
        if (!loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ADMINISTRADOR_SISTEMA) || !loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.PARAMETRIZADOR_PAE)) {
            escuela = loginFaces.getPerfilUsuarioDTO().getUnidadDependencia();
            renderComponent = false;
        } else {
            renderComponent = true;
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirectHorasDocenteCapacitacion();
    }

    /**
     *
     */
    public void findListByFecha() {
        LOG.trace("metodo: loadList()");
        if (fechaInicioConsulta != null && fechaFinConsulta != null) {
            Map<String, Object> params = new HashMap<>();
            try {
                params.put("escuela", escuela);
                params.put("fechaini", fechaInicioConsulta);
                params.put("fechafin", fechaFinConsulta);
                params.put("fecharegistro", fechaCreaConsulta);
                if(identificacion != null){
                    params.put("identificacion", identificacion);
                }
                list = service.findByPeriod(params);
                if (!this.list.isEmpty()) {                    
                    this.renderGeneral = true;
                }
            } catch (Exception ex) {
                LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
                addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            }
        }
    }

    public void onRowSelect() {
        LOG.trace("metodo: loadList()");
        if (fechaInicioConsulta != null && fechaFinConsulta != null) {
            Map<String, Object> params = new HashMap<>();
            try {
                params.put("fechaini", fechaInicioConsulta);
                params.put("fechafin", fechaFinConsulta);
                if (horasDocenteCapacitacionSelected.getDocePers() != null) {
                    params.put("docente", horasDocenteCapacitacionSelected.getDocePers().getPersPers());
                }
                list = service.findByPeriod(params);
                if (!this.list.isEmpty()) {
                    this.renderComponent = true;
                    this.renderGeneral = true;
                }
            } catch (Exception ex) {
                LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
                addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            }
        }
    }

    public List<HorasDocenteCapacitacion> getList() {
        return list;
    }

    public void setList(List<HorasDocenteCapacitacion> list) {
        this.list = list;
    }

    public Date getFechaInicioConsulta() {
        return fechaInicioConsulta;
    }

    public void setFechaInicioConsulta(Date fechaInicioConsulta) {
        this.fechaInicioConsulta = fechaInicioConsulta;
    }

    public Date getFechaFinConsulta() {
        return fechaFinConsulta;
    }

    public void setFechaFinConsulta(Date fechaFinConsulta) {
        this.fechaFinConsulta = fechaFinConsulta;
    }

    public Date getFechaCreaConsulta() {
        return fechaCreaConsulta;
    }

    public void setFechaCreaConsulta(Date fechaCreaConsulta) {
        this.fechaCreaConsulta = fechaCreaConsulta;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public boolean isRenderGeneral() {
        return renderGeneral;
    }

    public void setRenderGeneral(boolean renderGeneral) {
        this.renderGeneral = renderGeneral;
    }

    public SieduDocenteEvento getHorasDocenteCapacitacionSelected() {
        return horasDocenteCapacitacionSelected;
    }

    public void setHorasDocenteCapacitacionSelected(SieduDocenteEvento horasDocenteCapacitacionSelected) {
        this.horasDocenteCapacitacionSelected = horasDocenteCapacitacionSelected;
    }

    public Long getEscuela() {
        return escuela;
    }

    public void setEscuela(Long escuela) {
        this.escuela = escuela;
    }

    public boolean isRenderComponent() {
        return renderComponent;
    }

    public void setRenderComponent(boolean renderComponent) {
        this.renderComponent = renderComponent;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

}
