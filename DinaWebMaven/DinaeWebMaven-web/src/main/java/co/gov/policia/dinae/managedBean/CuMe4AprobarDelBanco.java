package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidad;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersona;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacion;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacionPK;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramaInvestigacionService;
import co.gov.policia.dinae.siedu.servicios.SieduPropuestaAsignadaService;
import java.io.FileInputStream;
import java.io.InputStream;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@javax.inject.Named(value = "cuMe4AprobarDelBanco")
@javax.enterprise.context.SessionScoped
public class CuMe4AprobarDelBanco extends AbstractController implements Serializable {

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    //EJB's
    @EJB
    private IAreaCienciaTecnologiaLocal areaService;
    @EJB
    private ILineaLocal lineaService;
    @EJB
    private SieduBancoNecesidadPersonaService serviceBancoNecesidadPersona;
    @EJB
    private SieduBancoNecesidadService serviceBancoNecesidad;
    @EJB
    private SieduPropuestaAsignadaService servicePropuestaAsignada;
    @EJB
    private IPropuestaNecesidadLocal servicePropuestaNecesidad;
    @EJB
    private SieduProgramaInvestigacionService serviceProgramaInvestigacion;
    @EJB
    private APPService serviceAPP;
    
    private Integer vigencia;
    private Long idUnidadPolicial;
    private List<SelectItem> vigencias;
    private SieduProgramaInvestigacion programaInvestigacionSeleccionado;
    private Integer editando;
    private SieduBancoNecesidad necesidadBancoSeleccionada;
    private List<AreaCienciaTecnologia> areas;
    private List<SieduBancoNecesidadPersona> funcionarios;
    private List<SieduBancoNecesidad> necesidades;
    private List<Linea> lineas2;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        editando = 0;
        try {
            this.areas = areaService.getAreaCienciaTecnologias();
            if (this.areas.size() > 0) {
                this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
                this.necesidadBancoSeleccionada.setLinea(new Linea());
                this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
            }
            this.vigencias = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            // TODO : Todos los años que tengan por aprobar en el banco Usando año de la Fecha propuesta
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR), "" + calendar.get(Calendar.YEAR)));
            this.vigencias.add(new SelectItem(calendar.get(Calendar.YEAR) + 1, "" + (calendar.get(Calendar.YEAR) + 1)));
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-04", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe04AprobarPropuestaBanco();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-04", e);
        }
        return null;
    }

    public StreamedContent getArchivo() {
        try {
            if (necesidadBancoSeleccionada != null && necesidadBancoSeleccionada.getNombreArchivo() != null) {
                String name = keyPropertiesFactory.value("cu_me_02_dir_file_archivo") + necesidadBancoSeleccionada.getNombreArchivoFisico();
                InputStream stream = new FileInputStream(name);
                String contentType = "application/octet-stream";
                StreamedContent download = new DefaultStreamedContent(stream, contentType, necesidadBancoSeleccionada.getNombreArchivo());
                return download;
            }

        } catch (Exception e) {
            Logger.getLogger(CuMe4AprobarDelBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Descarga de documento ", e);
        }
        return null;
    }

    public void buscarNecesidades() {
        Map<String, Object> params = new HashMap();
        if (this.idUnidadPolicial != null) {
            params.put("unidad", this.idUnidadPolicial);
        }
        if (this.vigencia != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(vigencia, 0, 1, 0, 0, 0);
            params.put("desde", cal.getTime());
            cal.set(vigencia + 1, 0, 1, 0, 0, 0);
            params.put("hasta", cal.getTime());
        }
        params.put("estado", 'P');
        try {
            this.necesidades = this.serviceBancoNecesidad.findByFilter(params);
            if (this.necesidades.isEmpty()) {
                this.addErrorMessage("No existen registros para la unidad y vigencia seleccionadas");
            }
        } catch (ServiceException ex) {
            this.addInfoMessage("Hubo problemas recuperando las necesidades", "Hubo problemas recuperando las necesidades");
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-04 Consulta Necesidades del Banco ", ex);
        }
    }

    public void ver(SieduBancoNecesidad pa) {
        this.necesidadBancoSeleccionada = pa;
        this.editando = 0;
        Map<String, Object> params = new HashMap();
        params.put("bancoNecesidad", necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
        try {
            funcionarios = serviceBancoNecesidadPersona.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-04 Búsqueda de Funcionarios ", ex);
        }
    }

    public void aprobar() {
        this.necesidadBancoSeleccionada.setEstado('A');
        try {
            this.necesidadBancoSeleccionada.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            this.necesidadBancoSeleccionada.setBneIpMod(getHostAddress());
            this.necesidadBancoSeleccionada.setBneMaquinaMod(getHostName());
            this.necesidadBancoSeleccionada.setBneFechaMod(new Date());
            this.serviceBancoNecesidad.update(this.necesidadBancoSeleccionada);
            this.buscarNecesidades();
            this.addInfoMessage("Necesidad aprobada exitosamente");
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-04 Aprobando ", ex);
            this.addErrorMessage("No fué posible aprobar la necesidad");
        }
    }

    public void rechazar() {
        this.necesidadBancoSeleccionada.setEstado('N');
        try {
            this.necesidadBancoSeleccionada.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            this.necesidadBancoSeleccionada.setBneIpMod(getHostAddress());
            this.necesidadBancoSeleccionada.setBneMaquinaMod(getHostName());
            this.necesidadBancoSeleccionada.setBneFechaMod(new Date());
            this.serviceBancoNecesidad.update(this.necesidadBancoSeleccionada);
            this.buscarNecesidades();
            this.addInfoMessage("Necesidad rechazada exitosamente");
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-04 Rechazando ", ex);
            this.addErrorMessage("No fué posible rechazar la necesidad");
        }
    }

    public void guardar() {
        try {
            this.necesidadBancoSeleccionada.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            this.necesidadBancoSeleccionada.setBneIpMod(getHostAddress());
            this.necesidadBancoSeleccionada.setBneMaquinaMod(getHostName());
            this.necesidadBancoSeleccionada.setBneFechaMod(new Date());
            this.serviceBancoNecesidad.update(this.necesidadBancoSeleccionada);
            this.buscarNecesidades();
            this.addInfoMessage("Necesidad guardada exitosamente");
            this.editando = 0;
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-04 Guardando ", ex);
            this.addErrorMessage("No fué posible guardar la necesidad");
        }
    }

    public List<SelectItem> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<SelectItem> vigencias) {
        this.vigencias = vigencias;
    }

    public SieduProgramaInvestigacion getProgramaInvestigacionSeleccionado() {
        return programaInvestigacionSeleccionado;
    }

    public void setProgramaInvestigacionSeleccionado(SieduProgramaInvestigacion programaInvestigacionSeleccionado) {
        this.programaInvestigacionSeleccionado = programaInvestigacionSeleccionado;
    }

    public Integer getEditando() {
        return editando;
    }

    public void setEditando(Integer editando) {
        this.editando = editando;
    }

    public SieduBancoNecesidad getNecesidadBancoSeleccionada() {
        return necesidadBancoSeleccionada;
    }

    public void setNecesidadBancoSeleccionada(SieduBancoNecesidad necesidadBancoSeleccionada) {
        this.necesidadBancoSeleccionada = necesidadBancoSeleccionada;
    }

    public List<AreaCienciaTecnologia> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaCienciaTecnologia> areas) {
        this.areas = areas;
    }

    public List<Linea> getLineas2() {

        try {
            return this.lineaService.getLineasPorArea(this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().getIdAreaCienciaTecnologia());
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    public List<SieduBancoNecesidadPersona> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<SieduBancoNecesidadPersona> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<SieduBancoNecesidad> getNecesidades() {
        return necesidades;
    }

    public void setNecesidades(List<SieduBancoNecesidad> necesidades) {
        this.necesidades = necesidades;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Long getIdUnidadPolicial() {
        return idUnidadPolicial;
    }

    public void setIdUnidadPolicial(Long idUnidadPolicial) {
        this.idUnidadPolicial = idUnidadPolicial;
    }

    public void editar() {
        this.editando = 1;
    }
}
