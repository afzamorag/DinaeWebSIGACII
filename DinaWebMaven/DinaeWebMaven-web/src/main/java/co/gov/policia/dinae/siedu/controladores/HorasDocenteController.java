/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.HorasDocenteSigac;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduEntidadService;
import co.gov.policia.dinae.siedu.servicios.SieduHorasDocenteService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class HorasDocenteController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(HorasDocenteController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private SieduHorasDocenteService service;
    private List<HorasDocenteSigac> list;
    private Date fechaInicioConsulta;
    private Date fechaFinConsulta;
    private Date fechaCreaConsulta;
    private boolean renderComponent;
    private boolean renderForm;
    private boolean renderGeneral;
    private HorasDocenteSigac horasDocenteSigacSelected;
    private List<HorasDocenteSigac> listDetail;
    private List<HorasDocenteSigac> listGeneral;
    private HorasDocenteSigac horasDocenteSigacGeneral;
    private List<HorasDocenteSigac> listAll;

    public HorasDocenteController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.renderComponent = false;
        this.renderForm = true;
        this.renderGeneral = true;
        this.horasDocenteSigacSelected = new HorasDocenteSigac();
        this.horasDocenteSigacGeneral = new HorasDocenteSigac(); 
        this.listGeneral = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirectHorasDocente();
    }

    /**
     *
     */
    public void findListByFecha() {
        LOG.trace("metodo: loadList()");
        if (fechaInicioConsulta != null && fechaInicioConsulta != null) {
            try {
                list = service.findByFechas(this.fechaInicioConsulta, this.fechaFinConsulta, this.fechaCreaConsulta);
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
    
   public void findAllListByFecha() {
        LOG.trace("metodo: findAllListByFecha()");
        if (fechaInicioConsulta != null && fechaInicioConsulta != null) {
            try {
                this.listAll = service.findAllByFechas(this.fechaInicioConsulta, this.fechaFinConsulta, this.fechaCreaConsulta);
                this.renderGeneral = false;
            } catch (Exception ex) {
                LOG.error("metodo: findAllListByFecha() ->> mensaje: {}", ex.getMessage());
                addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            }
        }
    }
   
   public void onBackGeneral() {
        LOG.trace("metodo: onBack()");
        this.renderGeneral = true;
    }

    public void onBack() {
        LOG.trace("metodo: onBack()");
        this.renderComponent = false;
    }

    public void onBackDetail() {
        LOG.trace("metodo: onBack()");
        this.renderForm = true;
    }

    public void findGeneral() throws ServiceException {
        LOG.trace("metodo: findGeneral()");
        try {            
            this.listGeneral = new ArrayList<>();
            this.listGeneral = service.findByPeriodo(this.fechaInicioConsulta, this.fechaFinConsulta, this.fechaCreaConsulta);            
        } catch (Exception ex) {
            LOG.error("metodo: metodo: findGeneral() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void onRowSelect(SelectEvent event) throws ServiceException {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        setHorasDocenteSigacSelected((HorasDocenteSigac) event.getObject());
        try {
            this.listDetail = this.service.findByFechasIdentificacion(this.fechaInicioConsulta, this.fechaFinConsulta, this.fechaCreaConsulta, this.horasDocenteSigacSelected.getIdentificacion(), this.horasDocenteSigacSelected.getCodEscuela());
            if (!this.listDetail.isEmpty()) {
                this.renderForm = false;
            }
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public List<HorasDocenteSigac> getList() {
        return list;
    }

    public void setList(List<HorasDocenteSigac> list) {
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

    public boolean isRenderComponent() {
        return renderComponent;
    }

    public void setRenderComponent(boolean renderComponent) {
        this.renderComponent = renderComponent;
    }

    public HorasDocenteSigac getHorasDocenteSigacSelected() {
        return horasDocenteSigacSelected;
    }

    public void setHorasDocenteSigacSelected(HorasDocenteSigac horasDocenteSigacSelected) {
        this.horasDocenteSigacSelected = horasDocenteSigacSelected;
    }

    public List<HorasDocenteSigac> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<HorasDocenteSigac> listDetail) {
        this.listDetail = listDetail;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public HorasDocenteSigac getHorasDocenteSigacGeneral() {
        return horasDocenteSigacGeneral;
    }

    public void setHorasDocenteSigacGeneral(HorasDocenteSigac horasDocenteSigacGeneral) {
        this.horasDocenteSigacGeneral = horasDocenteSigacGeneral;
    }

    public List<HorasDocenteSigac> getListGeneral() {
        return listGeneral;
    }

    public void setListGeneral(List<HorasDocenteSigac> listGeneral) {
        this.listGeneral = listGeneral;
    }

    public List<HorasDocenteSigac> getListAll() {
        return listAll;
    }

    public void setListAll(List<HorasDocenteSigac> listAll) {
        this.listAll = listAll;
    }

    public boolean isRenderGeneral() {
        return renderGeneral;
    }

    public void setRenderGeneral(boolean renderGeneral) {
        this.renderGeneral = renderGeneral;
    }

    /**
     *
     */
}
