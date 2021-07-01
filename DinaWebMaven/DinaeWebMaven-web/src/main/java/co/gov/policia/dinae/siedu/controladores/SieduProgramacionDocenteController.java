/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.ProgramacionAlumnos;
import co.gov.policia.dinae.siedu.modelo.SieduProgramacionAlumno;
import co.gov.policia.dinae.siedu.modelo.SieduProgramacionDocente;
import co.gov.policia.dinae.siedu.modelo.SieduProgramasEscuelas;
import co.gov.policia.dinae.siedu.servicios.SieduProgramacionAlumnoService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramacionAlumnosService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramacionDocenteService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramasEscuelasService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named(value = "cuMe11InsercionNotas")
@SessionScoped
public class SieduProgramacionDocenteController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SieduProgramacionDocenteController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private SieduProgramasEscuelasService servicesProgramas;
    @EJB
    private SieduProgramacionDocenteService serviceProgramacionDocente;
    @EJB
    private SieduProgramacionAlumnoService serviceProgramacionAlumno;
    @EJB
    private SieduProgramacionAlumnosService programacionAlumnosService;
    private Long idEscuela;
    private Long idPrograma;
    private String documentoDocente;
    private SieduProgramacionDocente programacionDocenteSelected;
    private AppController appController;
    private List<SieduProgramasEscuelas> programas;
    private List<SieduProgramacionDocente> lstProgramacionDocente;
    private List<SieduProgramacionAlumno> lstAlumnos;
    private boolean showDetails;
    private boolean editRowAlumn;
    private boolean seaca;
    private boolean regco;

    public SieduProgramacionDocenteController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.idEscuela = 0L;
        this.idPrograma = 0L;
        this.programas = new ArrayList<>();
        this.documentoDocente = "";
        this.lstProgramacionDocente = new ArrayList<>();
        this.programacionDocenteSelected = new SieduProgramacionDocente();
        this.showDetails = false;
        this.lstAlumnos = new ArrayList<>();
        this.appController = new AppController();
        this.setSeaca(this.isSecad());
        this.setRegco(this.isRegcon());
        try{
        this.findProgramacionDocente(seaca, regco);
        } catch(Exception ex){
            LOG.error("metodo: initialize() ->> mensaje: {}", ex.getMessage());
        }
        //loadList();
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        initialize();
        releaseControllers();
        return navigationFaces.redirectCuMe11InsercionNotas();
    }

    /**
     *
     * @return
     */
    public boolean isSecad() {
        LOG.trace("isSecad()");
        return this.loginFaces.getPerfilUsuarioDTO().validarRol(38L);
    }

    /**
     *
     * @return
     */
    public boolean isRegcon() {
        LOG.debug("isRegcoin");
        return this.loginFaces.getPerfilUsuarioDTO().validarRol(39L);
    }

    public void findProgramacionDocente(boolean secad, boolean regco) throws ServiceException {
        if (!this.seaca && !this.regco) {
            Map<String, Object> params = new HashMap();
            params.put("identificacion", this.loginFaces.getPerfilUsuarioDTO().getIdentificacion());
            params.put("vigente", "SI");
            this.lstProgramacionDocente = this.serviceProgramacionDocente.findByFilter(params);
        }
        if(!this.seaca && this.regco){
            this.idEscuela = Long.valueOf(this.loginFaces.getPerfilUsuarioDTO().getCodigoUnidadLaboral());
            this.setProgramas(this.servicesProgramas.findByEscuela(this.idEscuela));            
        }
    }

    /**
     *
     */
    public void loadList() {
        LOG.trace("metodo: loadList()");
        try {
            this.idEscuela = 0L;
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public List<SieduProgramasEscuelas> findProgramasByEscuela() throws ServiceException {
        LOG.trace("metodo: findProgramasByEscuela()");
        try {
            programas = this.servicesProgramas.findByEscuela(this.idEscuela);
            return programas;
        } catch (Exception ex) {
            LOG.error("método: findProgramasByEscuela() -<< mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
        return null;
    }

    public List<SieduProgramacionDocente> findProgramacionDocente() throws ServiceException {
        LOG.trace("metodo: findProgramacionDocente()");
        Map<String, Object> params = new HashMap();
        if (this.documentoDocente == null && this.idEscuela == null && this.idPrograma == null) {
            addErrorMessage(getPropertyFromBundle("insercionnotas.msg.error.nullfilters.summary"), getPropertyFromBundle("insercionnotas.msg.error.nullfilters.detail"));
        } else {
            if (this.idEscuela != null) {
                params.put("codEscuela", this.idEscuela);
            }
            if (this.idPrograma != null) {
                params.put("codigoPrograma", this.idPrograma);
            }
            if (this.documentoDocente != null) {
                params.put("identificacion", this.documentoDocente);
            }
            try {
                this.lstProgramacionDocente = this.serviceProgramacionDocente.findByFilter(params);
                return this.lstProgramacionDocente;
            } catch (Exception ex) {
                LOG.error("método: findProgramacionDocente() -<< mensaje: {}", ex.getMessage());
                addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            }
        }
        return null;
    }

    public void findAlumnosAsignatura() throws ServiceException {
        LOG.trace("metodo: findAlumnosAsignatura()");
        try {
            this.lstAlumnos = serviceProgramacionAlumno.findByIdProgDocente(this.programacionDocenteSelected.getIdProgDocente());
        } catch (Exception ex) {
            LOG.error("metodo: findAlumnosAsignatura() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void onRowSelect(SelectEvent event) throws ServiceException {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        setProgramacionDocenteSelected((SieduProgramacionDocente) event.getObject());
        this.showDetails = true;
        this.editRowAlumn = this.programacionDocenteSelected.getVigente().equals("SI") || this.seaca;
        this.findAlumnosAsignatura();
    }

    public void save() throws ServiceException {
        this.programacionAlumnosService.update(this.convertProgramacionAlumno(lstAlumnos));
    }

    public List<ProgramacionAlumnos> convertProgramacionAlumno(List<SieduProgramacionAlumno> lst) throws ServiceException {
        LOG.trace("metodo: convertProgramacionAlumno()");
        List<ProgramacionAlumnos> lstProgAlumno = new ArrayList<>();
        ProgramacionAlumnos progAlumno = new ProgramacionAlumnos();
        for (SieduProgramacionAlumno a : lst) {
            progAlumno = this.programacionAlumnosService.findById(a.getIdAlumno());
            progAlumno.setActualizadoPor(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            progAlumno.setDefinitiva(calculateDef(a.getParcial1(), a.getParcial2(), a.getParcial3()));
            progAlumno.setFechaActualiza(new Date());
            progAlumno.setParcial1(a.getParcial1());
            progAlumno.setParcial2(a.getParcial2());
            progAlumno.setParcial3(a.getParcial3());
            if (progAlumno.getDefinitiva().compareTo(this.programacionDocenteSelected.getMinimoProbatorio()) >= 0) {
                progAlumno.setHabilita("NO");
            }
            lstProgAlumno.add(progAlumno);
        }
        return lstProgAlumno;
    }

    public BigDecimal calculateDef(BigDecimal parcial1, BigDecimal parcial2, BigDecimal parcial3) {
        BigDecimal definitiva = new BigDecimal("0.0");
        BigDecimal p30 = new BigDecimal("0.3");
        BigDecimal p40 = new BigDecimal("0.4");
        definitiva = definitiva.add(parcial1.multiply(p30));
        definitiva = definitiva.add(parcial2.multiply(p30));
        definitiva = definitiva.add(parcial3.multiply(p40));
        return definitiva;
    }

    public void onBack() {
        this.showDetails = false;
    }

    public Long getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(Long idEscuela) {
        this.idEscuela = idEscuela;
    }

    /*public List<UnidadDependencia> getListEscuelas() {
        return listEscuelas;
    }

    public void setListEscuelas(List<UnidadDependencia> listEscuelas) {
        this.listEscuelas = listEscuelas;
    }*/
    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public List<SieduProgramasEscuelas> getProgramas() {
        return programas;
    }

    public void setProgramas(List<SieduProgramasEscuelas> programas) {
        this.programas = programas;
    }

    public String getDocumentoDocente() {
        return documentoDocente;
    }

    public void setDocumentoDocente(String documentoDocente) {
        this.documentoDocente = documentoDocente;
    }

    public List<SieduProgramacionDocente> getLstProgramacionDocente() {
        return lstProgramacionDocente;
    }

    public void setLstProgramacionDocente(List<SieduProgramacionDocente> lstProgramacionDocente) {
        this.lstProgramacionDocente = lstProgramacionDocente;
    }

    public SieduProgramacionDocente getProgramacionDocenteSelected() {
        return programacionDocenteSelected;
    }

    public void setProgramacionDocenteSelected(SieduProgramacionDocente programacionDocenteSelected) {
        this.programacionDocenteSelected = programacionDocenteSelected;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }

    public List<SieduProgramacionAlumno> getLstAlumnos() {
        return lstAlumnos;
    }

    public void setLstAlumnos(List<SieduProgramacionAlumno> lstAlumnos) {
        this.lstAlumnos = lstAlumnos;
    }

    public boolean isEditRowAlumn() {
        return editRowAlumn;
    }

    public void setEditRowAlumn(boolean editRowAlumn) {
        this.editRowAlumn = editRowAlumn;
    }

    public boolean isRegco() {
        return regco;
    }

    public void setRegco(boolean regco) {
        this.regco = regco;
    }

    public boolean isSeaca() {
        return seaca;
    }

    public void setSeaca(boolean seaca) {
        this.seaca = seaca;
    }

}
