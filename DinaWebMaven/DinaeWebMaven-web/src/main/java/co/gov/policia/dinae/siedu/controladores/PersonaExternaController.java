/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.constantes.EstadoCivilEnum;
import co.gov.policia.dinae.siedu.constantes.GrupoSanguineoEnum;
import co.gov.policia.dinae.siedu.constantes.RHEnum;
import co.gov.policia.dinae.siedu.constantes.SexoEnum;
import co.gov.policia.dinae.siedu.constantes.TipoDocumentoIdentificacionEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ValidationsException;
import co.gov.policia.dinae.siedu.filtros.PersonaExternaFiltro;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.PersonaExterna;
import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.PersonaExternaService;
import co.gov.policia.dinae.siedu.servicios.SieduEntidadService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class PersonaExternaController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaExternaController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private PersonaExternaService service;
    @EJB
    private SieduEntidadService serviceEntidad;
    private PersonaExterna selected;
    private PersonaExternaFiltro filtro;
    private List<PersonaExterna> list;
    private boolean editable;
    private NavEnum optionNavEnum;
    private List<SelectItem> tiposIdentificaciones;
    private List<SelectItem> estadosCiviles;
    private List<SelectItem> sexos;
    private List<SelectItem> rhs;
    private List<SelectItem> gruposSanguineos;
    private boolean validaRol = false;
    private List<SieduEntidad> entidades;

    public PersonaExternaController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.filtro = new PersonaExternaFiltro();
        this.initializeList();
        this.loadTiposIdentificaciones();
        this.loadEstadosCiviles();
        this.loadSexos();
        this.loadRHs();
        this.loadGruposSanguineos();
        this.validaRol();
        this.loadListEntidad();
    }

    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirect_CU07_CAP_PERSONAL_EXTERNO();
    }

    private void initializeList() {
        LOG.trace("metodo: initializeList()");
        this.optionNavEnum = NavEnum.LIST;
        this.selected = null;
        this.editable = false;
    }

    public void loadListEntidad() {
        LOG.trace("metodo: loadListEntidad()");
        try {
            this.entidades = this.serviceEntidad.findAll();
        } catch (Exception ex) {
            LOG.error("metodo: loadListEntidad() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void onLoadList() {
        LOG.trace("metodo: onLoadList()");
        try {
            setList(this.service.findByFilter(getFiltro()));
        } catch (Exception ex) {
            LOG.error("metodo: onLoadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void initializeEdit() {
        LOG.trace("metodo: initializeEdit()");
        setEditable(true);
    }

    public boolean validaRol() {
        validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        return validaRol;
    }

    public void onCreate() {
        LOG.trace("metodo: onCreate()");
        this.optionNavEnum = NavEnum.DETAILS;
        this.selected = new PersonaExterna();
        initializeEdit();
    }

    public void onRowSelect(SelectEvent event) {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        this.selected = (PersonaExterna) event.getObject();
        this.editable = false;
        this.optionNavEnum = NavEnum.DETAILS;
    }

    public boolean enableEdit() {
        return !this.editable
                && this.selected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onEdit() {
        LOG.trace("metodo: onEdit()");
        this.initializeEdit();
    }

    public boolean enableDelete() {
        return !this.editable
                && this.selected != null
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onDelete() {
        LOG.trace("metodo: onDelete()");
        try {
            this.selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            this.service.delete(getSelected());
            this.onLoadList();
            this.initializeList();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "_FK")) {
                addErrorMessage(getPropertyFromBundle("cobertura.msg.error.delete.fk.summary"), getPropertyFromBundle("cobertura.msg.error.delete.fk.detail"));
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
            }
        }
    }

    public void onSave() {
        LOG.trace("metodo: onSave()");
        try {
            validate();
            try {
                if (this.selected.getId() == null) {
                    this.selected.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    //Se agrega teniendo en cuenta la estructua de la tabla USR_REHU.LUGARES_GEOGRAFICOS, la recursividad de la misma no tiene un nivel de jerarquia definido,
                    //la división politica de los paises difiere. Jerarquia definida de acuerdo a la de Colombia: 1. País - 2. Departamento - 3. Ciudad
                    if (this.selected.getNacimientoCiudad().getCodMunicipio() == null) {
                        //Si el lugar geográfico seleccionado no tiene 3er nivel se asigna el valor del 2do nivel.
                        this.selected.getNacimientoCiudad().setCodMunicipio(this.selected.getNacimientoCiudad().getCodDepartamento());
                        this.selected.getNacimientoCiudad().setDescMunicipio(this.selected.getNacimientoCiudad().getDescDepartamento());
                        this.selected.getNacimientoPais().setCodMunicipio(this.selected.getNacimientoCiudad().getCodDepartamento());
                    } else {
                        //Asignación de ciudad al país seleccionado.
                        this.selected.getNacimientoPais().setCodMunicipio(this.selected.getNacimientoCiudad().getCodMunicipio());
                    }
                    if (this.selected.getResidenciaCiudad().getCodMunicipio() == null) {
                        //Si no existe 3er nivel, se asigna el valor de 2do nivel al 3er.
                        this.selected.getResidenciaCiudad().setCodMunicipio(this.selected.getResidenciaCiudad().getCodDepartamento());
                        this.selected.getResidenciaCiudad().setDescMunicipio(this.selected.getResidenciaCiudad().getDescDepartamento());
                    }
                    if (this.selected.getResidenciaCiudad().getCodMunicipio() == null) {
                        //Si el lugar geográfico seleccionado no tiene 3er nivel se asigna el valor del 2do nivel.
                        this.selected.getResidenciaCiudad().setCodMunicipio(this.selected.getResidenciaCiudad().getCodDepartamento());
                        this.selected.getResidenciaCiudad().setDescMunicipio(this.selected.getResidenciaCiudad().getDescDepartamento());
                        this.selected.getResidenciaPais().setCodMunicipio(this.selected.getResidenciaCiudad().getCodDepartamento());
                    } else {
                        this.selected.getResidenciaPais().setCodMunicipio(this.selected.getResidenciaCiudad().getCodMunicipio());
                    }
                    //Se asigna 2do nivel al país seleccionado.
                    this.selected.getNacimientoPais().setCodDepartamento(this.selected.getNacimientoCiudad().getCodDepartamento());
                    this.selected.getResidenciaPais().setCodDepartamento(this.selected.getResidenciaCiudad().getCodDepartamento());
                    //Persistir el registro
                    this.service.create(getSelected());
                    this.onLoadList();
                } else {
                    this.selected.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    if (this.selected.getNacimientoCiudad().getCodMunicipio() == null) {
                        //Si el lugar geográfico seleccionado no tiene 3er nivel se asigna el valor del 2do nivel.
                        this.selected.getNacimientoCiudad().setCodMunicipio(this.selected.getNacimientoCiudad().getCodDepartamento());
                        this.selected.getNacimientoCiudad().setDescMunicipio(this.selected.getNacimientoCiudad().getDescDepartamento());
                        this.selected.getNacimientoPais().setCodMunicipio(this.selected.getNacimientoCiudad().getCodDepartamento());
                    } else {
                        //Asignación de ciudad al país seleccionado.
                        this.selected.getNacimientoPais().setCodMunicipio(this.selected.getNacimientoCiudad().getCodMunicipio());
                    }
                    if (this.selected.getResidenciaCiudad().getCodMunicipio() == null) {
                        //Si no existe 3er nivel, se asigna el valor de 2do nivel al 3er.
                        this.selected.getResidenciaCiudad().setCodMunicipio(this.selected.getResidenciaCiudad().getCodDepartamento());
                        this.selected.getResidenciaCiudad().setDescMunicipio(this.selected.getResidenciaCiudad().getDescDepartamento());
                    }
                    if (this.selected.getResidenciaCiudad().getCodMunicipio() == null) {
                        //Si el lugar geográfico seleccionado no tiene 3er nivel se asigna el valor del 2do nivel.
                        this.selected.getResidenciaCiudad().setCodMunicipio(this.selected.getResidenciaCiudad().getCodDepartamento());
                        this.selected.getResidenciaCiudad().setDescMunicipio(this.selected.getResidenciaCiudad().getDescDepartamento());
                        this.selected.getResidenciaPais().setCodMunicipio(this.selected.getResidenciaCiudad().getCodDepartamento());
                    } else {
                        this.selected.getResidenciaPais().setCodMunicipio(this.selected.getResidenciaCiudad().getCodMunicipio());
                    }
                    //Se asigna 2do nivel al país seleccionado.
                    this.selected.getNacimientoPais().setCodDepartamento(this.selected.getNacimientoCiudad().getCodDepartamento());
                    this.selected.getResidenciaPais().setCodDepartamento(this.selected.getResidenciaCiudad().getCodDepartamento());
                    this.service.update(getSelected());
                    this.onLoadList();
                }
                this.editable = false;
                addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            } catch (Exception ex) {
                LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
                if (ExceptionUtil.isException(ex, "SIEDU_EMPLEADO_EXTERNO_UK")) {
                    addErrorMessage(getPropertyFromBundle("personalexterno.msg.error.save.uk.summary"), getPropertyFromBundle("personalexterno.msg.error.save.uk.detail"));
                } else {
                    addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                }
            }
        } catch (ValidationsException ex) {
            LOG.error("metodo: onSave() ->> mensaje ->> {}", ex.getMessage());
        }
    }

    private void validate() throws ValidationsException {
        LOG.trace("metodo: validate()");
        if (service.validarNumeroIdentificacion(selected.getIdentificacionNro())) {
            addErrorMessage(getPropertyFromBundle("personalexterno.msg.error.save.uk.empleado.summary"), getPropertyFromBundle("personalexterno.msg.error.save.uk.empleado.detail"));
            throw new ValidationsException(getPropertyFromBundle("personalexterno.msg.error.save.uk.empleado.detail"));
        }
    }

    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        this.initializeList();
    }

    public void onBackToList() {
        LOG.trace("metodo: onBackToList()");
        this.initializeList();
    }

    public boolean isShowList() {
        boolean showList = (optionNavEnum == NavEnum.LIST);
        return showList;
    }

    public String validateCivilState(String estado) {
        LOG.trace("metodo: validateCivilState()");
        String state = "";
        for (EstadoCivilEnum c : EstadoCivilEnum.values()) {
            if (c.getLowValue().equals(estado)) {
                state = c.getMeaning().toUpperCase();
                break;
            }
        }
        return state;
    }

    private void loadTiposIdentificaciones() {
        LOG.trace("metodo: loadTiposIdentificaciones()");
        this.tiposIdentificaciones = new ArrayList<>();
        for (TipoDocumentoIdentificacionEnum c : TipoDocumentoIdentificacionEnum.values()) {
            this.tiposIdentificaciones.add(new SelectItem(c.getLowValue(), c.getMeaning()));
        }
    }

    private void loadEstadosCiviles() {
        LOG.trace("metodo: loadEstadosCiviles()");
        this.estadosCiviles = new ArrayList<>();
        for (EstadoCivilEnum c : EstadoCivilEnum.values()) {
            this.estadosCiviles.add(new SelectItem(c.getLowValue(), c.getMeaning()));
        }
    }

    private void loadSexos() {
        LOG.trace("metodo: loadSexos()");
        this.sexos = new ArrayList<>();
        for (SexoEnum c : SexoEnum.values()) {
            this.sexos.add(new SelectItem(c.getLowValue(), c.getMeaning()));
        }
    }

    public String validateSex(String sexo) {
        LOG.trace("metodo: validateSex()");
        String sex = "";
        for (SexoEnum c : SexoEnum.values()) {
            if (c.getLowValue().equals(sexo)) {
                sex = c.getMeaning().toUpperCase();
                break;
            }
        }
        return sex;
    }

    private void loadRHs() {
        LOG.trace("metodo: loadRHs()");
        this.rhs = new ArrayList<>();
        for (RHEnum c : RHEnum.values()) {
            this.rhs.add(new SelectItem(c.getLowValue(), c.getMeaning()));
        }
    }

    public String validateRHs(String rh) {
        LOG.trace("metodo: loadRHs()");
        String rhs = "";
        for (RHEnum c : RHEnum.values()) {
            if (c.getLowValue().equals(rh)) {
                rhs = c.getMeaning();
                break;
            }
        }
        return rhs;
    }

    private void loadGruposSanguineos() {
        LOG.trace("metodo: loadGruposSanguineos()");
        this.gruposSanguineos = new ArrayList<>();
        for (GrupoSanguineoEnum c : GrupoSanguineoEnum.values()) {
            this.gruposSanguineos.add(new SelectItem(c.getLowValue(), c.getMeaning()));
        }
    }

    public boolean isShowDetails() {
        return optionNavEnum == NavEnum.DETAILS;
    }

    public String getMinDate() {
        String year = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer currentYear = calendar.get(Calendar.YEAR);
        Integer minYear = currentYear - 80;
        year = minYear.toString();
        //Calendar firtsDate = new GregorianCalendar(minYear, 0, 1);
        return year;
    }

    public String getMaxDate() {
        String year = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer currentYear = calendar.get(Calendar.YEAR);
        Integer maxYear = currentYear - 14;
        year = maxYear.toString();
        //Calendar lastDate = new GregorianCalendar(maxYear, 11, 31);
        return year;
    }

    public PersonaExterna getSelected() {
        return selected;
    }

    public void setSelected(PersonaExterna selected) {
        this.selected = selected;
    }

    public PersonaExternaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(PersonaExternaFiltro filtro) {
        this.filtro = filtro;
    }

    public List<PersonaExterna> getList() {
        return list;
    }

    public void setList(List<PersonaExterna> list) {
        this.list = list;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<SelectItem> getTiposIdentificaciones() {
        return tiposIdentificaciones;
    }

    public List<SelectItem> getEstadosCiviles() {
        return estadosCiviles;
    }

    public List<SelectItem> getSexos() {
        return sexos;
    }

    public List<SelectItem> getRhs() {
        return rhs;
    }

    public List<SelectItem> getGruposSanguineos() {
        return gruposSanguineos;
    }

    public boolean isValidaRol() {
        return validaRol;
    }

    public void setValidaRol(boolean validaRol) {
        this.validaRol = validaRol;
    }

    public List<SieduEntidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<SieduEntidad> entidades) {
        this.entidades = entidades;
    }
}
