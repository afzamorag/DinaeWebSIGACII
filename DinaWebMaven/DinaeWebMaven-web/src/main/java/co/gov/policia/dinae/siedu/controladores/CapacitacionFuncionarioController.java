/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.modelo.VDatosPersona;
import co.gov.policia.dinae.siedu.util.NavEnum;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.VDatosPersonaService;
import java.util.ArrayList;
import java.util.List;
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
public class CapacitacionFuncionarioController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(CapacitacionFuncionarioController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private SieduParticipanteEventoService service;
    @EJB
    private SieduPersonaService servicePersona;
    @EJB
    private VDatosPersonaService serviceVDatosPersona;
    private NavEnum optionNavEnum;
    private String identificacion;
    private SieduPersona persona;
    private List<SieduParticipanteEvento> list;
    private boolean render;
    private VDatosPersona datoPersona;

    public CapacitacionFuncionarioController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        optionNavEnum = NavEnum.NEW;
        this.identificacion = "";
        this.persona = new SieduPersona();
        this.list = new ArrayList<>();
        this.render = false;
        this.datoPersona = new VDatosPersona();
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirectCapacitacionFuncionarioSIEDU();
    }

    /**
     *
     */
    public void onBack() {
        LOG.trace("metodo: onBack()");
        initialize();
    } 

    public void onFind() throws ServiceException {
        LOG.trace("onFind()");
        try {
            this.persona = servicePersona.findByIdentificacion(this.identificacion);
            if (this.persona != null) {
                this.datoPersona = this.serviceVDatosPersona.findByIdentificacion(identificacion);
                this.setList(this.service.findCapacitacionFuncionario(this.identificacion));
                if (!this.list.isEmpty()) {
                    this.render = true;
                } else {
                    addInfoMessage(getPropertyFromBundle("capacitacionfuncionario.msg.nodatafound"));
                }
            } else {
                addErrorMessage(getPropertyFromBundle("eventoesuela.msg.error.nodatafound.sumary"), getPropertyFromBundle("eventoesuela.msg.error.nodatafound.details"));
            }
        } catch (Exception ex) {
            LOG.error("metodo: onFind() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public SieduPersona getPersona() {
        return persona;
    }

    public void setPersona(SieduPersona persona) {
        this.persona = persona;
    }

    public List<SieduParticipanteEvento> getList() {
        return list;
    }

    public void setList(List<SieduParticipanteEvento> list) {
        this.list = list;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }    

    public VDatosPersona getDatoPersona() {
        return datoPersona;
    }

    public void setDatoPersona(VDatosPersona datoPersona) {
        this.datoPersona = datoPersona;
    }
}
