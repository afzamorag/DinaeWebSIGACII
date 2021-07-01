/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.DominioTipo;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.siedu.servicios.APPService;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@ApplicationScoped
public class AppController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @EJB
    private APPService service;
    private SelectItem noSelectionOption;
    private SelectItem noSelection;
    private List<UnidadDependencia> lstEscuelas;
    @EJB
    private IUnidadDependenciaLocal unidadDependencia;
    @EJB
    private ILugarGeograficoLocal lugarGeografico;

    @EJB
    private IUnidadPolicialLocal unidadPolicial;

    /**
     * lista de valores para decisiones: [SI, NO]
     */
    private List<SelectItem> decisiones;

    public AppController() {
        LOG.trace("method: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("method: initialize()");
        this.lstEscuelas = new ArrayList<>();
        this.noSelectionOption = new SelectItem(-1, "");
        this.noSelection = new SelectItem(null, "");
        this.loadDecisiones();
    }

    /**
     *
     * @return
     */
    public List<Dominio> recursosFinancia() {
        LOG.trace("method: recursosFinancia()");
        try {
            return service.consultarDominios(TipoDominioEnum.TIPO_FINANCIACION.getId());
        } catch (ServiceException ex) {
            LOG.error("Error en <<sexos>> ->> mensaje ->> {}", ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<UnidadDependencia> escuelas() {
        LOG.trace("method: escuelas()");
        try {
            List<Long> tipos = new ArrayList<>();
            tipos.add(10L);
            tipos.add(15L);
            setLstEscuelas(unidadDependencia.findUnidadDependenciaVigenteByTipo(tipos));
            this.lstEscuelas.add(this.unidadDependencia.getUnidadDependenciaById(23536L)); //Agregado 15/02/2018 Para incluir a DINAE en la lista de EScuelas ya que tendrá Meta en el PAE
            return lstEscuelas;
        } catch (Exception ex) {
            LOG.error("Error en <<escuelas>> ->> mensaje ->> {}", ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<UnidadDependencia> unidades() {
        LOG.trace("method: unidades()");
        try {
            List<Long> tipos = new ArrayList<>();
            tipos.add(1L);
            tipos.add(2L);
            tipos.add(3L);
            tipos.add(4L);
            tipos.add(5L);
            tipos.add(10L);
            tipos.add(15L);
            return unidadDependencia.findUnidadDependenciaVigenteByTipo(tipos);
        } catch (Exception ex) {
            LOG.error("Error en <<escuelas>> ->> mensaje ->> {}", ex.getMessage());
        }
        return null;
    }

    public List<UnidadDependencia> unidadesPoliciales() {
        LOG.trace("method: unidadesPoliciales()");
        try {
            return this.service.consultarUnidadesVigentes();
            //return new ArrayList<UnidadPolicial>();
        } catch (Exception ex) {
            LOG.error("Error en <<unidades policiales>> ->> mensaje ->> {}", ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<LugarGeografico> municipios() {
        LOG.trace("method: municipios()");
        try {
            List<String> tiposMun = new ArrayList<>();
            tiposMun.add("CM");
            return lugarGeografico.findLugarGeoggraficoByTipo(tiposMun);
        } catch (Exception ex) {
            LOG.error("Error en <<escuelas>> ->> mensaje ->> {}", ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pais
     * @return
     */
    public List<LugarGeografico> municipios(Long pais) {
        LOG.trace("method: municipios()");
        if (pais == null || pais == 0L) {
            return null;
        }
        try {
            return service.consultarMunicipiosByPais(pais);
        } catch (Exception ex) {
            LOG.error("Error en <<escuelas>> ->> mensaje ->> {}", ex.getMessage());
            return null;
        }
    }

    public void loadDecisiones() {
        LOG.trace("method: loadDecisiones()");
        if (this.decisiones == null) {
            this.decisiones = new ArrayList<>();
        }
        this.decisiones.add(new SelectItem(DecisionEnum.SI.toString()));
        this.decisiones.add(new SelectItem(DecisionEnum.NO.toString()));
    }

    public String descDominio(Long id) throws ServiceException {
        LOG.trace("método: descDominio(Long id)");
        try {
            Dominio d = service.getDescrDominio(id);
            String s = d.getNombre();
            return s;
        } catch (ServiceException ex) {
            LOG.error("método: descDominio ->> error", ex.getMessage());
            return null;
        }
    }

    public List<Dominio> loadTipoEntidad() throws ServiceException {
        LOG.debug("método: loadTipoEmpresa()");
        try {
            List<Dominio> d = service.consultarDominios(TipoDominioEnum.TIPO_ENTIDAD.getId());
            return d;
        } catch (ServiceException ex) {
            LOG.error("método: loadTipoEmpresa() ->> error", ex.getMessage());
            return null;
        }
    }

    public List<Dominio> loadSectorEmpresa() throws ServiceException {
        LOG.debug("método: loadTipoEmpresa()");
        try {
            List<Dominio> d = service.consultarDominios(TipoDominioEnum.SECTOR_ENTIDAD.getId());
            return d;
        } catch (ServiceException ex) {
            LOG.error("método: loadTipoEmpresa() ->> error{}", ex.getMessage());
            return null;
        }
    }

    public String getMunicipio(Long id) throws ServiceException, JpaDinaeException {
        LOG.debug("metodo: getMunicipio() ->> parámetros{}", id);
        try {
            LugarGeografico l = lugarGeografico.findMunicipioByCodMunicipio(id);
            String des = l.getDescDepartamento() + " - " + l.getDescMunicipio();
            return des;
        } catch (Exception ex) {
            LOG.error("método: getMunicipio() ->> error{}", ex);
            return null;
        }
    }

    public String getPais(Long id) throws ServiceException, JpaDinaeException {
        LOG.debug("metodo: getMunicipio() ->> parámetros{}", id);
        try {
            LugarGeografico l = lugarGeografico.findMunicipioByCodMunicipio(id);
            String des = l.getDescPais();
            return des;
        } catch (Exception ex) {
            LOG.error("método: getMunicipio() ->> error{}", ex);
            return null;
        }
    }    

    public SelectItem getNoSelectionOption() {
        return noSelectionOption;
    }

    public void setNoSelectionOption(SelectItem noSelectionOption) {
        this.noSelectionOption = noSelectionOption;
    }

    public SelectItem getNoSelection() {
        return noSelection;
    }

    public void setNoSelection(SelectItem noSelection) {
        this.noSelection = noSelection;
    }

    public List<SelectItem> getDecisiones() {
        return decisiones;
    }

    public List<DominioTipo> getTiposDominios() {
        return service.getTiposDominios();
    }

    public List<Dominio> getModalidades() {
        return service.getModalidades();
    }

    public List<Dominio> getTiposIdentificaciones() {
        return service.getTiposIdentificaciones();
    }

    public List<Dominio> getEsatdosCiviles() {
        return service.getEsatdosCiviles();
    }

    public List<Dominio> getSexos() {
        return service.getSexos();
    }

    public List<Dominio> getTiposDocumentos() {
        return service.getTiposDocumentos();
    }

    public List<Dominio> getProcesos() {
        return service.getProcesos();
    }

    public List<Dominio> getEstrategias() {
        return service.getEstrategias();
    }

    public List<Dominio> getTematicas() {
        return service.getTematicas();
    }

    public List<LugarGeografico> getPaises() {
        return service.getPaises();
    }

    public List<LugarGeografico> getMunicipios() {
        return service.getMunicipios();
    }

    public List<UnidadDependencia> getUnidadesDependencias() {
        return service.getUnidadesDependencias();
    }

    public List<NivelesAcademicos> getNivelesAcademicos() {
        return service.getNivelesAcademicos();
    }

    public List<Categoria> obtenerCategorias() {
        LOG.trace("method: obtenerCategorias()");
        return service.consultarCategorias();
    }

    public List<SieduCompetencia> obtenerCompetencias() {
        LOG.trace("method: obtenerCompetencias()");
        return service.consultarCompetencias();
    }

    /**
     *
     * @param nivelAcademico
     * @return
     */
    public List<Carreras> obtenerCarreras(Long nivelAcademico) {
        LOG.trace("metodo: loadCarreras()");
        List<Carreras> carreras = null;
        if (nivelAcademico != null) {
            try {
                carreras = service.consultarCarrerasVigentes(nivelAcademico);
            } catch (Exception ex) {
                LOG.error("metodo: loadCarreras() ->> mensaje: {}", ex.getMessage());
            }
        }
        return carreras;
    }

    public BigDecimal convertFloatToBigDecimal(float f) {
        double d = f;
        BigDecimal bd = BigDecimal.valueOf(d);
        return bd;
    }

    public List<UnidadDependencia> getLstEscuelas() {
        return lstEscuelas;
    }

    public void setLstEscuelas(List<UnidadDependencia> lstEscuelas) {
        this.lstEscuelas = lstEscuelas;
    }

}
