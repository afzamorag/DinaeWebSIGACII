/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.ReporteGeneralCapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.VwmPersonalCapacitado;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.servicios.ReporteGeneralCapacitacionService;
import co.gov.policia.dinae.siedu.util.ReportBuild;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Sheet;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ANDRES.ZAMORAG
 */
@Named
@SessionScoped
public class ReporteGeneralCapacitacionController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SieduEventoEscuelaController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private PAEService servicePAE;
    @EJB
    APPService serviceAPP;
    @EJB
    ReporteGeneralCapacitacionService service;
    @EJB
    private IUnidadDependenciaLocal unidadDependencia;

    private ReporteGeneralCapacitacionFiltro reporteFiltro;

    private List<PAE> vigencias;
    private List<Dominio> modalidad;
    private List<NivelesAcademicos> listNivelAcademico;
    private List<UnidadDependencia> listEscuelas;
    private List<Dominio> estrategia;
    private List<Integer> listTrimestres;
    private boolean validaRol = false;
    private boolean validaRolTelem = false;
    private Long nivelAcademico = -1L;

    public ReporteGeneralCapacitacionController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.reporteFiltro = new ReporteGeneralCapacitacionFiltro();
        this.loadVigencias();
        this.loadModalidad();
        this.validaRol();
        this.validaRolTelem();
        this.loadNivelesAcademicos();
        this.loadEstrategia();
        this.loadTrimestres();
        this.loadEscuelas();
    }

    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        this.initialize();
        releaseControllers();
        return this.navigationFaces.redirectCuReporteGeneralCapacitacionImplRedirect();
    }

    public boolean validaRol() {
        this.validaRol = this.loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || this.loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        return this.validaRol;
    }

    public boolean validaRolTelem() {
        if (this.loginFaces.getPerfilUsuarioDTO() != null) {
            this.validaRolTelem = this.loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
            return this.validaRolTelem;
        }
        return false;
    }

    private void loadVigencias() {
        LOG.trace("metodo: loadVigencias()");
        try {
            this.setVigencias(this.servicePAE.consultarVigencias());
        } catch (ServiceException ex) {
            LOG.error("metodo: loadVigencias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadModalidad() {
        LOG.trace("metodo: loadEstrategias()");
        try {
            setModalidad(this.serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD.getId()));
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadNivelesAcademicos() {
        LOG.trace("metodo: loadNivelesAcademicos()");
        try {
            setListNivelAcademico(this.serviceAPP.consultarNivelesAcademicos());
        } catch (Exception ex) {
            LOG.error("metodo: loadCarreras() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadEstrategia() {
        LOG.trace("metodo: loadEstrategias()");
        try {
            this.setEstrategia(this.serviceAPP.consultarDominios(TipoDominioEnum.ESTRATEGIA.getId()));
        } catch (Exception ex) {
            LOG.error("metodo: loadEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadTrimestres() {
        this.listTrimestres = new ArrayList<>();
        this.listTrimestres.add(1);
        this.listTrimestres.add(2);
        this.listTrimestres.add(3);
        this.listTrimestres.add(4);
    }

    private void loadEscuelas() {
        LOG.trace("metodo: loadEscuelas()");
        try {
            setListEscuelas(this.serviceAPP.consultarEscuelasVigentes());
            this.listEscuelas.add(this.unidadDependencia.getUnidadDependenciaById(23536L));
        } catch (Exception ex) {
            LOG.error("metodo: loadEscuelas() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public StreamedContent findByFilterTrainneds() {
        LOG.trace("metodo: findByFilter()");
        StreamedContent file = null;
        try {
            if (this.validaRol == false) {
                this.reporteFiltro.setEscuela(loginFaces.getPerfilUsuarioDTO().getUnidadDependencia());
            }
            file = this.service.findByFilterTrainneds(reporteFiltro);
        } catch (ServiceException ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
        return file;
    }

    public ReporteGeneralCapacitacionFiltro getReporteFiltro() {
        return reporteFiltro;
    }

    public void setReporteFiltro(ReporteGeneralCapacitacionFiltro reporteFiltro) {
        this.reporteFiltro = reporteFiltro;
    }

    public List<PAE> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<PAE> vigencias) {
        this.vigencias = vigencias;
    }

    public boolean isValidaRol() {
        return validaRol;
    }

    public void setValidaRol(boolean validaRol) {
        this.validaRol = validaRol;
    }

    public boolean isValidaRolTelem() {
        return validaRolTelem;
    }

    public void setValidaRolTelem(boolean validaRolTelem) {
        this.validaRolTelem = validaRolTelem;
    }

    public List<Dominio> getModalidad() {
        return modalidad;
    }

    public void setModalidad(List<Dominio> modalidad) {
        this.modalidad = modalidad;
    }

    public Long getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(Long nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public List<NivelesAcademicos> getListNivelAcademico() {
        return listNivelAcademico;
    }

    public void setListNivelAcademico(List<NivelesAcademicos> listNivelAcademico) {
        this.listNivelAcademico = listNivelAcademico;
    }

    public List<Dominio> getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(List<Dominio> estrategia) {
        this.estrategia = estrategia;
    }

    public List<Integer> getListTrimestres() {
        return listTrimestres;
    }

    public void setListTrimestres(List<Integer> listTrimestres) {
        this.listTrimestres = listTrimestres;
    }

    public List<UnidadDependencia> getListEscuelas() {
        return listEscuelas;
    }

    public void setListEscuelas(List<UnidadDependencia> listEscuelas) {
        this.listEscuelas = listEscuelas;
    }

}
