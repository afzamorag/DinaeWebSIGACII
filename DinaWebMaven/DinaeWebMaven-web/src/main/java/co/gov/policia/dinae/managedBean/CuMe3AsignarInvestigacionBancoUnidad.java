package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IFuncionarioNecesidadLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadDependenciaPK;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidad;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidadPersona;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacion;
import co.gov.policia.dinae.siedu.modelo.SieduProgramaInvestigacionPK;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignadaPK;
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
@javax.inject.Named(value = "cuMe3AsignarInvestigacionBancoUnidad")
@javax.enterprise.context.SessionScoped
public class CuMe3AsignarInvestigacionBancoUnidad extends AbstractController implements Serializable {

    private SieduProgramaInvestigacion filtro;
    private List<SelectItem> vigencias;
    private List<Dominio> modalidades;
    private SieduProgramaInvestigacion programaInvestigacionSeleccionado;
    private List<SieduProgramaInvestigacion> programaInvesgacionesUnidades;
    private List<SieduPropuestaAsignada> propuestasAsignadas;
    private List<SieduPropuestaAsignada> propuestasAsignadasUnidad;
    private SieduPropuestaAsignada propuestaAsignadaSeleccionada;
    private Integer editando = 0;
    private List<SieduBancoNecesidad> necesidadesBanco;
    private SieduBancoNecesidad necesidadBancoSeleccionada;
    private List<AreaCienciaTecnologia> areas;
    private List<SieduBancoNecesidadPersona> funcionarios;
    private List<Linea> lineas;
    private List<Linea> lineas2;
    private boolean renderButon;
    protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();
    // Filtro
    private Long idAreaCienciaTecnologia;
    private Long idLinea;
    private String palabraClave;

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
    @EJB
    private IConstantesLocal serviceConstantes;
    @EJB
    private IVistaFuncionarioLocal serviceVistaFuncionario;
    @EJB
    private IFuncionarioNecesidadLocal serviceFuncionarioNecesidad;

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;

    /**
     *
     */
    @javax.annotation.PostConstruct
    public void init() {
        try {
            this.areas = areaService.getAreaCienciaTecnologias();
            if (this.areas.size() > 0) {
                this.necesidadBancoSeleccionada = new SieduBancoNecesidad();
                this.necesidadBancoSeleccionada.setLinea(new Linea());
                this.necesidadBancoSeleccionada.getLinea().setAreaCienciaTecnologia(new AreaCienciaTecnologia());
                this.necesidadBancoSeleccionada.getLinea().getAreaCienciaTecnologia().setIdAreaCienciaTecnologia(this.areas.get(0).getIdAreaCienciaTecnologia());
            }
            this.lineas = new ArrayList<>();
            this.vigencias = new ArrayList<>();
            this.programaInvesgacionesUnidades = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            this.vigencias.add(new SelectItem("" + calendar.get(Calendar.YEAR), "" + calendar.get(Calendar.YEAR)));
            this.vigencias.add(new SelectItem("" + (calendar.get(Calendar.YEAR) + 1), "" + (calendar.get(Calendar.YEAR) + 1)));
            //TODO: Cargar las vigencias que existen mas la actual
            this.filtro = new SieduProgramaInvestigacion();
            this.filtro.setUnidad(new UnidadPolicial());
            this.filtro.setSieduProgramaInvestigacionPK(new SieduProgramaInvestigacionPK());
            modalidades = serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD_PROGRAMACION.getId());
            this.propuestasAsignadas = new ArrayList<>();
            this.propuestasAsignadasUnidad = new ArrayList<>();
            this.renderButon = true;
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-03", e);
        }
    }

    /**
     *
     * @return
     */
    public String initReturnCU() {
        init();
        try {
            return navigationFaces.redirectCuMe03AsignarInvestigacionBancoUnidad();
        } catch (Exception e) {
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-03", e);
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
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-02 Descarga de documento ", e);
        }
        return null;
    }

    public void buscarProgramaInvestigacion() {
        this.propuestasAsignadas = new ArrayList<>();
        this.propuestasAsignadasUnidad = new ArrayList<>();
        Map<String, Object> params = new HashMap();
        if (this.filtro.getUnidad().getIdUnidadPolicial() != null) {
            params.put("unidad", this.filtro.getUnidad().getIdUnidadPolicial());
        }
        if (this.filtro.getSieduProgramaInvestigacionPK().getVigencia() != null) {
            params.put("vigencia", this.filtro.getSieduProgramaInvestigacionPK().getVigencia());
        }
        if (this.filtro.getModalidad() != null) {
            params.put("modalidad", this.filtro.getModalidad().getId());
        }
        try {
            this.programaInvesgacionesUnidades = serviceProgramaInvestigacion.findByFilter(params);
            if (this.programaInvesgacionesUnidades != null) {
                for (SieduProgramaInvestigacion spi : programaInvesgacionesUnidades) {
                    this.propuestasAsignadasUnidad = spi.getPropuestasAsignadas();
                    int cuantos = 0;
                    for (SieduPropuestaAsignada pa : this.propuestasAsignadasUnidad) {
                        if (pa.getPropuestaNecesidad().getIdBancoNecesidad() != null && pa.getPropuestaNecesidad().getIdBancoNecesidad() != 0) {
                            cuantos++;
                            this.propuestasAsignadas.add(pa);
                        }
                    }
                    while (cuantos < spi.getNumeroInvestigacionesBanco()) {
                        SieduPropuestaAsignada spa = new SieduPropuestaAsignada();
                        SieduPropuestaAsignadaPK pk2 = new SieduPropuestaAsignadaPK();
                        pk2.setUnidad(spi.getUnidad().getIdUnidadPolicial());
                        pk2.setIdModalidad(spi.getModalidad().getId());
                        pk2.setVigencia(spi.getSieduProgramaInvestigacionPK().getVigencia());
                        spa.setSieduPropuestaAsignadaPK(pk2);
                        spa.setModalidad(spi.getModalidad());
                        spa.setProgramaInvestigacion(spi);
                        spa.setUnidad(spi.getUnidad());
                        cuantos++;
                        this.propuestasAsignadas.add(spa);
                    }
                }
            } else {
                this.addInfoMessage("No se han parametrizado las propuestas de investigaciones para la unidad, vigencia y modalidad seleccionadas");
            }
        } catch (ServiceException ex) {
            this.addInfoMessage("No existen registros para la unidad, vigencia y modalidad seleccionadas", "No existen registros para launidad, vigencia y modalidad seleccionadas");
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-03 Consulta de programa investigación ", ex);
        }

        /* SieduProgramaInvestigacionPK pk = new SieduProgramaInvestigacionPK();
        pk.setUnidad(filtro.getUnidad().getIdUnidadPolicial());
        pk.setIdModalidad(filtro.getModalidad().getId());
        pk.setVigencia(filtro.getSieduProgramaInvestigacionPK().getVigencia());
        try {
            programaInvestigacionSeleccionado = this.serviceProgramaInvestigacion.findById(pk);
            if (programaInvestigacionSeleccionado != null) {
                this.propuestasAsignadas = programaInvestigacionSeleccionado.getPropuestasAsignadas();
                int cuantos = 0;
                for (SieduPropuestaAsignada pa : this.propuestasAsignadas) {
                    if (pa.getPropuestaNecesidad().getIdBancoNecesidad() != null && pa.getPropuestaNecesidad().getIdBancoNecesidad() != 0) {
                        cuantos++;
                    }
                }
                while (cuantos < programaInvestigacionSeleccionado.getNumeroInvestigacionesBanco()) {
                    SieduPropuestaAsignada pa = new SieduPropuestaAsignada();
                    SieduPropuestaAsignadaPK pk2 = new SieduPropuestaAsignadaPK();
                    pk2.setUnidad(filtro.getUnidad().getIdUnidadPolicial());
                    pk2.setIdModalidad(filtro.getModalidad().getId());
                    pk2.setVigencia(filtro.getSieduProgramaInvestigacionPK().getVigencia());
                    pa.setSieduPropuestaAsignadaPK(pk2);
                    pa.setModalidad(filtro.getModalidad());
                    pa.setProgramaInvestigacion(programaInvestigacionSeleccionado);
                    pa.setUnidad(filtro.getUnidad());
                    cuantos++;
                    this.propuestasAsignadas.add(pa);
                }
            } else {
                this.addInfoMessage("No se han parametrizado las propuestas de investigaciones para la unidad, vigencia y modalidad seleccionadas");
            }
            this.addInfoMessage("Búsqueda finalizada");
        } catch (ServiceException ex) {
            this.addInfoMessage("No existen registros para la unidad, vigencia y modalidad seleccionadas", "No existen registros para launidad, vigencia y modalidad seleccionadas");
            Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-03 Consulta de programa investigación ", ex);
        }*/
    }

    public void ver(SieduPropuestaAsignada pa) {
        this.renderButon = true;
        this.propuestaAsignadaSeleccionada = pa;
        try {
            necesidadBancoSeleccionada = serviceBancoNecesidad.findById(pa.getPropuestaNecesidad().getIdBancoNecesidad());
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-03 Búsqueda de Banco Necesidad ", ex);
        }
        Map<String, Object> params = new HashMap();
        params.put("bancoNecesidad", necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
        try {
            funcionarios = serviceBancoNecesidadPersona.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-03 Búsqueda de Funcionarios ", ex);
        }
    }

    public void asignar(SieduBancoNecesidad sbn) {
        necesidadBancoSeleccionada = sbn;
        PropuestaNecesidad pn = new PropuestaNecesidad();
        pn.setUnidadPolicial(sbn.getUnidad());
        pn.setLinea(sbn.getLinea());
        //pn.setFechaEnvio(sbn.getFechaPropuesta()); // TODO: validar
        pn.setTema(sbn.getTema());
        pn.setTitulo(sbn.getTitulo());
        pn.setResultadoEsperado(sbn.getResultadoEsperado());
        pn.setFuenteInformacion(sbn.getFuenteInformacion());
        pn.setPosibleActividad(sbn.getPosibleActividad());
        pn.setNombreArchivo(sbn.getNombreArchivo());
        pn.setNombreArchivoFisico(sbn.getNombreArchivoFisico());
        pn.setIdBancoNecesidad(sbn.getIdentificadorBancoNecesidad());
        Periodo p = new Periodo();
        p.setIdPeriodo(2L);
        pn.setPeriodo(p); // TODO: validar
        pn.setFechaRegistro(new Date());
        pn.setIdentificadorUsuarioCrea(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        pn.setIpCrea(getHostAddress());
        pn.setMaquinaCrea(getHostName());
        try {
            pn.setConstantes(serviceConstantes.getConstantesPorIdConstante(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_SIN_ENVIAR)); // TODO: validar
            pn = this.servicePropuestaNecesidad.guardarPropuestaCreaEjecutorNecesidad(pn, null, null);
            Map<String, Object> params = new HashMap();
            params.put("bancoNecesidad", necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
            this.funcionarios = serviceBancoNecesidadPersona.findByFilter(params);
            for (SieduBancoNecesidadPersona bnp : this.funcionarios) {
                FuncionarioNecesidad fn = new FuncionarioNecesidad();
                fn.setIdentificacion(bnp.getPersona().getPersNroid());
                VistaFuncionario vf = serviceVistaFuncionario.getVistaFuncionarioPorIdentificacion(bnp.getPersona().getPersNroid());
                fn.setCargo(vf.getCargo());
                fn.setCorreo(vf.getCorreo());
                fn.setGrado(vf.getGrado());
                fn.setNombreCompleto(vf.getNombreCompleto());
                fn.setPropuestaNecesidad(pn);
                if (vf.getCelular() == null) {
                    fn.setTelefono("0000000000");
                } else {
                    fn.setTelefono(vf.getCelular());
                }
                this.serviceFuncionarioNecesidad.create(fn);
            }
            sbn.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            sbn.setBneIpMod(getHostAddress());
            sbn.setBneMaquinaMod(getHostName());
            sbn.setBneFechaMod(new Date());
            sbn.setEstado('S');
            this.serviceBancoNecesidad.update(sbn);
            if (propuestaAsignadaSeleccionada.getPropuestaNecesidad() == null) {
                propuestaAsignadaSeleccionada.getSieduPropuestaAsignadaPK().setIdPropuestaNecesidad(pn.getIdPropuestaNecesidad());
                propuestaAsignadaSeleccionada.setPpaFechaCrea(new Date());
                propuestaAsignadaSeleccionada.setPpaIpCrea(getHostAddress());
                propuestaAsignadaSeleccionada.setPpaMaquinaCrea(getHostName());
                propuestaAsignadaSeleccionada.setPpaUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                SieduPropuestaAsignada spa = this.servicePropuestaAsignada.create(propuestaAsignadaSeleccionada);
            } else {
                propuestaAsignadaSeleccionada.getSieduPropuestaAsignadaPK().setIdPropuestaNecesidad(pn.getIdPropuestaNecesidad());
                propuestaAsignadaSeleccionada.setPpaFechaMod(new Date());
                propuestaAsignadaSeleccionada.setPpaIpMod(getHostAddress());
                propuestaAsignadaSeleccionada.setPpaMaquinaMod(getHostName());
                propuestaAsignadaSeleccionada.setPpaUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                this.servicePropuestaAsignada.update(propuestaAsignadaSeleccionada);
            }
            propuestaAsignadaSeleccionada.setPropuestaNecesidad(pn);
            this.funcionarios = new ArrayList<>();
            buscarInvestigaciones();
            buscarProgramaInvestigacion();
            this.addInfoMessage("Propuesta de investigación asignada");
        } catch (JpaDinaeException | ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelarAsignacion() {
        //this.propuestaAsignadaSeleccionada = pa;
        try {
            PropuestaNecesidad paremove;
            paremove = this.propuestaAsignadaSeleccionada.getPropuestaNecesidad();
            SieduBancoNecesidad sbn = this.serviceBancoNecesidad.findById(this.propuestaAsignadaSeleccionada.getPropuestaNecesidad().getIdBancoNecesidad());
            this.servicePropuestaAsignada.delete(this.propuestaAsignadaSeleccionada);
            paremove.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
            paremove.setIpModifica(getHostAddress());
            paremove.setMaquinaModifica(getHostName());
            this.servicePropuestaNecesidad.eliminar(paremove);
            this.propuestaAsignadaSeleccionada.setPropuestaNecesidad(null);
            sbn.setEstado('A');
            sbn.setBneUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
            sbn.setBneIpMod(getHostAddress());
            sbn.setBneMaquinaMod(getHostName());
            sbn.setBneFechaMod(new Date());
            this.serviceBancoNecesidad.update(sbn);
            this.addInfoMessage("Propuesta de investigación desasignada");
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarInvestigaciones() {
        Map<String, Object> params = new HashMap();
        if (this.idAreaCienciaTecnologia != null && this.idAreaCienciaTecnologia != 0) {
            params.put("area", this.idAreaCienciaTecnologia);
        }
        if (this.idLinea != null && this.idLinea != 0) {
            params.put("linea", this.idLinea);
        }
        if (this.palabraClave != null && !this.palabraClave.equals("")) {
            params.put("palabraClave", "%" + this.palabraClave + "%");
        }
        params.put("estado", 'A');
        try {
            this.necesidadesBanco = this.serviceBancoNecesidad.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe2PropuestaBanco.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-03 Búsqueda de Investigaciones en el banco ", ex);
        }
    }

    public List<Dominio> getModalidades() {
        return modalidades;
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

    public List<SieduPropuestaAsignada> getPropuestasAsignadas() {
        return propuestasAsignadas;
    }

    public void setPropuestasAsignadas(List<SieduPropuestaAsignada> propuestasAsignadas) {
        this.propuestasAsignadas = propuestasAsignadas;
    }

    public List<SieduPropuestaAsignada> getPropuestasAsignadasUnidad() {
        return propuestasAsignadasUnidad;
    }

    public void setPropuestasAsignadasUnidad(List<SieduPropuestaAsignada> propuestasAsignadasUnidad) {
        this.propuestasAsignadasUnidad = propuestasAsignadasUnidad;
    }

    public SieduProgramaInvestigacion getProgramaInvestigacionSeleccionado() {
        return programaInvestigacionSeleccionado;
    }

    public void setProgramaInvestigacionSeleccionado(SieduProgramaInvestigacion programaInvestigacionSeleccionado) {
        this.programaInvestigacionSeleccionado = programaInvestigacionSeleccionado;
    }

    public void seleccionar(SieduPropuestaAsignada pa) {
        this.necesidadesBanco = new ArrayList<>();
        this.propuestaAsignadaSeleccionada = pa;
    }

    public SieduPropuestaAsignada getPropuestaAsignadaSeleccionada() {
        return propuestaAsignadaSeleccionada;
    }

    public void setPropuestaAsignadaSeleccionada(SieduPropuestaAsignada propuestaAsignadaSeleccionada) {
        this.propuestaAsignadaSeleccionada = propuestaAsignadaSeleccionada;
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

    public void ver2(SieduBancoNecesidad sbn) {
        this.renderButon = false;
        necesidadBancoSeleccionada = sbn;
        Map<String, Object> params = new HashMap();
        params.put("bancoNecesidad", necesidadBancoSeleccionada.getIdentificadorBancoNecesidad());
        try {
            funcionarios = serviceBancoNecesidadPersona.findByFilter(params);
        } catch (ServiceException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "CU-ME-03 Búsqueda de Funcionarios ", ex);
        }
    }

    public List<SieduBancoNecesidadPersona> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<SieduBancoNecesidadPersona> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Long getIdAreaCienciaTecnologia() {
        return idAreaCienciaTecnologia;
    }

    public void setIdAreaCienciaTecnologia(Long idAreaCienciaTecnologia) {
        this.idAreaCienciaTecnologia = idAreaCienciaTecnologia;
        try {
            this.lineas = this.lineaService.getLineasPorArea(idAreaCienciaTecnologia);
        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuMe3AsignarInvestigacionBancoUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }

    public List<SieduBancoNecesidad> getNecesidadesBanco() {
        return necesidadesBanco;
    }

    public void setNecesidadesBanco(List<SieduBancoNecesidad> necesidadesBanco) {
        this.necesidadesBanco = necesidadesBanco;
    }

    public List<SieduProgramaInvestigacion> getProgramaInvesgacionesUnidades() {
        return programaInvesgacionesUnidades;
    }

    public void setProgramaInvesgacionesUnidades(List<SieduProgramaInvestigacion> programaInvesgacionesUnidades) {
        this.programaInvesgacionesUnidades = programaInvesgacionesUnidades;
    }

    public boolean isRenderButon() {
        return renderButon;
    }

    public void setRenderButon(boolean renderButon) {
        this.renderButon = renderButon;
    }

}
