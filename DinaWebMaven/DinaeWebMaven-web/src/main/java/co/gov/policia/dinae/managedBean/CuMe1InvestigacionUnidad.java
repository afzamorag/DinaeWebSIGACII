package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependenciaPK;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import static co.gov.policia.dinae.siedu.controladores.AbstractController.LOG;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacion;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacionPK;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramaInvestigacionService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@javax.inject.Named(value = "cuMe1InvestigacionUnidad")
@javax.enterprise.context.SessionScoped
public class CuMe1InvestigacionUnidad extends AbstractController implements Serializable {

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;

    //private List<UnidadDependencia> unidades;
    private List<SelectItem> vigencias;
    private List<Dominio> modalidades;
    private List<SieduProgramaInvestigacion> investigacionesUnidad;
    private SieduProgramaInvestigacion investigacionUnidadSeleccionada;
    private SieduProgramaInvestigacion filtro;
    private boolean busco = false;
    private UnidadDependenciaPK unidadPK;
    private Integer vigenciaActual;

    @EJB
    private APPService serviceAPP;

    @EJB
    private SieduProgramaInvestigacionService service;

    private boolean editando;
    SieduProgramaInvestigacionPK pk = new SieduProgramaInvestigacionPK();

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {

        try {
            this.vigenciaActual = Calendar.getInstance().get(Calendar.YEAR);
            this.investigacionesUnidad = new ArrayList<>();
            this.vigencias = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            this.vigencias.add(new SelectItem("" + calendar.get(Calendar.YEAR), "" + calendar.get(Calendar.YEAR)));
            this.vigencias.add(new SelectItem("" + (calendar.get(Calendar.YEAR) + 1), "" + (calendar.get(Calendar.YEAR) + 1)));
            //TODO: Cargar las vigencias que existen mas la actual
            this.filtro = new SieduProgramaInvestigacion();
            this.filtro.setUnidad(new UnidadPolicial());
            this.filtro.setSieduProgramaInvestigacionPK(new SieduProgramaInvestigacionPK());
            modalidades = serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD_PROGRAMACION.getId());
            this.investigacionUnidadSeleccionada = new SieduProgramaInvestigacion();
            this.investigacionUnidadSeleccionada.setSieduProgramaInvestigacionPK(new SieduProgramaInvestigacionPK());
            this.investigacionUnidadSeleccionada.getSieduProgramaInvestigacionPK().setUnidad(-1L);
            this.investigacionUnidadSeleccionada.setUnidad(new UnidadPolicial());
            this.editando = false;
            busco = false;
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-01", e);
        }

    }

    /**
     *
     * @return
     */
    public String initReturnCU() {

        init();

        try {
            //unidades=this.serviceAPP.consultarUnidadesVigentes();
            return navigationFaces.redirectCuMe01ParametrizarInvestigacionUnidad();

        } catch (Exception e) {

            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-01", e);

        }

        return null;
    }

    /*public List<UnidadDependencia> getUnidades() {
    return unidades;
  }

  public void setUnidades(List<UnidadDependencia> unidades) {
    this.unidades = unidades;
  }*/
    public UnidadDependenciaPK getUnidadPK() {
        return unidadPK;
    }

    public void setUnidadPK(UnidadDependenciaPK unidadPK) {
        this.unidadPK = unidadPK;
    }

    public List<SieduProgramaInvestigacion> getInvestigacionesUnidad() {
        return investigacionesUnidad;
    }

    public void setInvestigacionesUnidad(List<SieduProgramaInvestigacion> investigacionesUnidad) {
        this.investigacionesUnidad = investigacionesUnidad;
    }

    public SieduProgramaInvestigacion getInvestigacionUnidadSeleccionada() {
        return investigacionUnidadSeleccionada;
    }

    public void setInvestigacionUnidadSeleccionada(SieduProgramaInvestigacion investigacionUnidadSeleccionada) {
        this.investigacionUnidadSeleccionada = investigacionUnidadSeleccionada;
        this.editando = true;
    }

    public List<Dominio> getModalidades() {
        return modalidades;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public void setModalidades(List<Dominio> modalidades) {
        this.modalidades = modalidades;
    }

    public List<SelectItem> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<SelectItem> vigencias) {
        this.vigencias = vigencias;
    }

    public SieduProgramaInvestigacion getFiltro() {
        return filtro;
    }

    public void setFiltro(SieduProgramaInvestigacion filtro) {
        this.filtro = filtro;
    }

    public void buscarNecesidades() {
        this.investigacionUnidadSeleccionada = new SieduProgramaInvestigacion();
        this.investigacionUnidadSeleccionada.setSieduProgramaInvestigacionPK(new SieduProgramaInvestigacionPK());
        this.investigacionUnidadSeleccionada.getSieduProgramaInvestigacionPK().setUnidad(-1L);
        this.investigacionUnidadSeleccionada.setUnidad(new UnidadPolicial());
        Map<String, Object> params = new HashMap();
        if (filtro.getUnidad() != null && filtro.getUnidad().getIdUnidadPolicial() != null) {
            params.put("unidad", filtro.getUnidad().getIdUnidadPolicial());
        }
        if (filtro.getSieduProgramaInvestigacionPK() != null && filtro.getSieduProgramaInvestigacionPK().getVigencia() != null) {
            params.put("vigencia", filtro.getSieduProgramaInvestigacionPK().getVigencia());
        }
        if (filtro.getModalidad() != null && filtro.getModalidad().getId() != null) {
            params.put("modalidad", filtro.getModalidad());
        }

        try {
            this.investigacionesUnidad = this.service.findByFilter(params);
             busco = true;

        } catch (ServiceException ex) {

            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-01 Consulta de propuestas de necesidades de investigación (buscarPropuesta) ", ex);
        }
    }

    public void editar(SieduProgramaInvestigacion pi) {
        this.investigacionUnidadSeleccionada = pi;
        this.editando = true;
    }

    public void nuevo() {
        this.investigacionUnidadSeleccionada = new SieduProgramaInvestigacion();
        //this.investigacionUnidadSeleccionada.setUnidad(new UnidadPolicial());
        this.investigacionUnidadSeleccionada.setSieduProgramaInvestigacionPK(new SieduProgramaInvestigacionPK());
        this.pk.setUnidad(this.filtro.getSieduProgramaInvestigacionPK().getUnidad());
        this.pk.setVigencia(this.filtro.getSieduProgramaInvestigacionPK().getVigencia());
        this.pk.setIdModalidad(this.filtro.getSieduProgramaInvestigacionPK().getIdModalidad());
        this.editando = false;
    }

    public void guardar() {
        //TODO: Al momento de actualizar la cantidad de investigaciones para una unidad, el sistema deberá validar que la nueva cantidad sea igual o superior a la cantidad de investigaciones que ha parametrizado en el sistema la unidad al momento.
        if (this.investigacionUnidadSeleccionada.getNumeroInvestigaciones() < 1) {
            this.addErrorMessage("La cantidad mínima de investigaciones a desarrollar debe ser 1");
            RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
            return;
        }
        if (this.investigacionUnidadSeleccionada.getNumeroInvestigaciones() < this.investigacionUnidadSeleccionada.getNumeroInvestigacionesBanco()) {
            this.addErrorMessage("La cantidad de investigaciones del Banco debe ser menor");
             RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
            return;
        }
        if (editando == true) {
            try {
                this.investigacionUnidadSeleccionada.setPrinUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.investigacionUnidadSeleccionada.setPrinIpMod(getHostAddress());
                this.investigacionUnidadSeleccionada.setPrinMaquinaMod(getHostName());
                this.investigacionUnidadSeleccionada.setPrinFechaMod(new Date());
                service.update(this.investigacionUnidadSeleccionada);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                buscarNecesidades();
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
            }
        } else {
            try {                
                this.investigacionUnidadSeleccionada.setPrinUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.investigacionUnidadSeleccionada.setPrinFechaCrea(new Date());
                this.investigacionUnidadSeleccionada.setPrinIpCrea(getHostAddress());
                this.investigacionUnidadSeleccionada.setPrinMaquinaCrea(getHostName());
                service.create(this.investigacionUnidadSeleccionada);
                RequestContext.getCurrentInstance().addCallbackParam("continuar", true);
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                buscarNecesidades();
            } catch (ServiceException ex) {
                Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
                RequestContext.getCurrentInstance().addCallbackParam("continuar", false);
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        }
    }

    public void eliminar() {
        // TODO: Al seleccionar la opción de eliminar el sistema validará que no existan datos de investigaciones en curso que cumplan con los mismos criterios (Unidad, vigencia, modalidad) del registro que se intenta eliminar.
        try {
            this.service.delete(this.investigacionUnidadSeleccionada);
            this.investigacionesUnidad.remove(this.investigacionUnidadSeleccionada);
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (ServiceException ex) {
            LOG.error("metodo: eliminar() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "USR_DISIGAC2.FK_SIEDU_PROPU_ASIGN_PROGRAMA")) {
                addErrorMessage(getPropertyFromBundle("programarcapacitaciones.fk_siedu_propu_asign_programa.summary"), getPropertyFromBundle("programarcapacitaciones.fk_siedu_propu_asign_programa.detail"));
            } /*else if (ExceptionUtil.isException(ex, "USR_DISIGAC2.SIEDU_PROGRAMACION_INVEST_CK")){
                addErrorMessage("Error","Error");
            }*/else{
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public Integer getVigenciaActual() {
        return vigenciaActual;
    }

    public void setVigenciaActual(Integer vigenciaActual) {
        this.vigenciaActual = vigenciaActual;
    }

    public boolean isBusco() {
        return busco;
    }

    public void setBusco(boolean busco) {
        this.busco = busco;
    }
}
