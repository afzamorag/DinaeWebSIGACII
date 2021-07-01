package co.gov.policia.dinae.manager.managedBean;

import co.gov.policia.dinae.util.JSFUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "navigationFaces")
@javax.enterprise.context.SessionScoped
public class NavigationFaces extends JSFUtils implements Serializable {

    /**
     *
     * @param url
     * @throws IOException
     */
    public void redirectFacesCuGenerico(String url) throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(url));
    }

    public String redirectToLogin() {
        return "/pages/login/login.xhtml?faces-redirect=true";
    }

    public String toLogin() {
        return "/pages/login/login.xhtml";
    }

    public String redirectToInformacion() {
        return "pages/login/login.xhtml?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String toInformacion() {
        return "/info.xhtml";
    }

    public String redirectToWelcome() {
        return "/pages/login/welcome.xhtml?faces-redirect=true";
    }

    public void redirectFacesToWelcome() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectToWelcome()));
    }

    public String redirectCuNe01() {
        return "/pages/secured/cu_ne_1/periodo_necesidades.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuNe01() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe01()));
    }

    /**
     *
     * @return
     */
    public String redirectCuNe02() {
        return "/pages/secured/cu_ne_2/gestiona_bloque_propuestas_necesidades.xhtml";
    }

    public String redirectCuNe02Redirect() {
        return "/pages/secured/cu_ne_2/gestiona_bloque_propuestas_necesidades.xhtml?faces-redirect=true";
    }

    public String redirectCuNe02DetallePropuesta() {
        return "/pages/secured/cu_ne_2/include_detalle_propuesta.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuNe02() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe02()));
    }

    public void redirectFacesCuNe02DetallePropuesta() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe02DetallePropuesta()));
    }

    public String redirectCuNe04ConsultarPropuestaNecesidad() {
        return "/pages/secured/cu_ne_4/consultar_propuesta_necesidad_investigacion.xhtml?faces-redirect=true";
    }

    public String redirectCuNe07PropuestaNecesidad() {
        return "/pages/secured/cu_ne_7/include_detalle_propuesta_necesidad.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuNe07PropuestaNecesidad() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe07PropuestaNecesidad()));
    }

    public String redirectCuNe05JefeLoteNecesidades() {
        return "/pages/secured/cu_ne_5/include_jefe_necesidades.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuNe05JefeLoteNecesidades() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe05JefeLoteNecesidades()));
    }

    public String redirectCuNe02ComentarioPropuestaNecesidad() {
        return "/pages/secured/cu_ne_2/include_lista_comentarios.xhtml?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String redirectCuNe03() {
        return "/pages/secured/cu_ne_3/include_registro_propuesta.xhtml?faces-redirect=true";
    }

    public String redirectCuNe03Redirect() {
        return "/pages/secured/cu_ne_3/include_registro_propuesta.xhtml?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String redirectCuNe06() {
        return "/pages/secured/cu_ne_6/gestiona_evaluar_propuestas_necesidades.xhtml?faces-redirect=true";
    }

    public String redirectCuNe06Redirect() {
        return "/pages/secured/cu_ne_6/gestiona_evaluar_propuestas_necesidades.xhtml?faces-redirect=true";
    }

    public String redirectCuNe06PropuestaNecesidades() {
        return "/pages/secured/cu_ne_6/include_detalle_propuesta.xhtml?faces-redirect=true";
    }

    public String redirectCuNe06DetallePropuestaNececisidad() {
        return "/pages/secured/cu_ne_6/include_detalle_propuesta_datos.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuNe06PropuestaNecesidad() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe06DetallePropuestaNececisidad()));
    }

    public void redirectFacesCuNe06PropuestaNecesidades() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuNe06PropuestaNecesidades()));
    }

    public String redirectCuPr23Compromisos() {
        return "/pages/secured/cu_pr_23/gestiona_compromisos.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr23Compromisos() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr23Compromisos()));
    }

    public String redirectCuPr23GestionarCompromisoProyectos() {
        return "/pages/secured/cu_pr_23/gestiona_compromisos_proyecto.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr23GestionarCompromisoProyectos() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr23GestionarCompromisoProyectos()));
    }

    public String redirectCuCo1GestionarConvocatoriasRedirect() {
        return "/pages/secured/cu_co_1/gestiona_bloque_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo1GestionarConvocatorias() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo1GestionarConvocatoriasRedirect()));
    }

    public String redirectCuCo1AgregarConvocatoriasRedirect() {
        return "/pages/secured/cu_co_1/gestiona_bloque_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo1AgregarConvocatorias() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo1AgregarConvocatoriasRedirect()));
    }

    public String redirectCuCo1RegistraConvocatoriasRedirect() {
        return "/pages/secured/cu_co_1/include_registra_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo1RegistraConvocatorias() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo1RegistraConvocatoriasRedirect()));
    }

    public String redirectCuCo2ParticipaConvocatoriasRedirect() {
        return "/pages/secured/cu_co_2/gestiona_bloque_participa_convocatoria_financia.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo2ParticipaConvocatorias() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo2ParticipaConvocatoriasRedirect()));
    }

    public String redirectCuCo2DetalleParticipaConvocatoriasRedirect() {
        return "/pages/secured/cu_co_2/include_participar_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo2DetalleParticipaConvocatorias() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo2DetalleParticipaConvocatoriasRedirect()));
    }

    public String redirectCuPr1ProyectoFacesRedirect() {
        return "/pages/secured/cu_pr_1/administrar_proyecto.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr1ProyectoFaces() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr1ProyectoFacesRedirect()));
    }

    public String redirectCuCo2ComentariosProyectoRedirect() {
        return "/pages/secured/cu_co_2/include_lista_comentarios.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo1ComentariosProyecto() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo2ComentariosProyectoRedirect()));
    }

    public String redirectCuCo3RevisarPropuestaProyectoRedirect() {
        return "/pages/secured/cu_co_3/administra_revision_propuesta_proyecto.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo3RevisarPropuestaProyecto() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo3RevisarPropuestaProyectoRedirect()));
    }

    public String redirectCuCo4EvaluarPropuestaConvocatoriaRedirect() {
        return "/pages/secured/cu_co_4/gestiona_evaluar_propuestas_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo4EvaluarPropuestaConvocatoria() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo4EvaluarPropuestaConvocatoriaRedirect()));
    }

    public String redirectCuCo4RevisarPropuestaConvocatoriaRedirect() {
        return "/pages/secured/cu_co_4/include_revisar_propuesta_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo4RevisarPropuestaConvocatoria() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo4RevisarPropuestaConvocatoriaRedirect()));
    }

    public String redirectCuCo4FinanciarPropuestaConvocatoriaRedirect() {
        return "/pages/secured/cu_co_4/include_financiar_propuestas_convocatoria.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo4FinanciarPropuestaConvocatoria() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo4FinanciarPropuestaConvocatoriaRedirect()));
    }

    public String redirectCuPr22AgregarComentarioPropuestaConvocatoriaRedirect() {
        return "/pages/secured/cu_pr_22/cu_pr_22_registrar_revision_comentario_proyecto.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr22AgregarComentarioPropuestaConvocatoria() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr22AgregarComentarioPropuestaConvocatoriaRedirect()));
    }

    public String redirectCuPr19ConsultarProyectosVigentesAsignadosRedirect() {
        return "/pages/secured/cu_pr_19/administra_consular_proyectos_vigentes_asignados.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr19ConsultarProyectosVigentesAsignados() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr19ConsultarProyectosVigentesAsignadosRedirect()));
    }

    public String redirectCuPr10RegistroInformeFinalRedirect() {
        return "/pages/secured/cu_pr_10/registrar_informe_final_trabajo_investigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr10RegistroInformeFinal() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr10RegistroInformeFinalRedirect()));
    }

    public String redirectCuPr7RegistrarAvanceInvestigacionRedirect() {
        return "/pages/secured/cu_pr_7/administrar_registro_avance_investigacion_proyecto.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr7RegistrarAvanceInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr7RegistrarAvanceInvestigacionRedirect()));
    }

    public String redirectCuIv01GestionarInvestigador() {
        return "/pages/secured/cu_iv_01/gestionarInvestigador.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuIv01GestionarInvestigador() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv01GestionarInvestigador()));
    }

    public String redirectCuPr06ConsultarDetalleProyectoInvestigacion() {
        return "/pages/secured/cu_pr_6/consultar_detalle_proyecto_investigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCu06ConsultarDetalleProyectoInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr06ConsultarDetalleProyectoInvestigacion()));
    }

    public String redirectCuCo7ConvocatoriasEnsayoCriticoRedirect() {
        return "/pages/secured/cu_co_7/gestionarConvocatoriasEnsayoCritico.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo7ConvocatoriasEnsayoCritico() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo7ConvocatoriasEnsayoCriticoRedirect()));
    }

    public String redirectCuCo7IngresarEnsayoCriticoRedirect() {
        return "/pages/secured/cu_co_7/ingresarEnsayoCritico.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo7IngresarEnsayoCritico() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo7IngresarEnsayoCriticoRedirect()));
    }

    public String redirectCuTr01ConsultarTrabajosDeGradoRedirect() {
        return "/pages/secured/cu_tr_01/consultar_trabajos_de_grado.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuTr01ConsultarTrabajosDeGrado() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuTr01ConsultarTrabajosDeGradoRedirect()));
    }

    public String redirectCuTr01IngresarModificarTrabajosDeGradoRedirect() {
        return "/pages/secured/cu_tr_01/ingresar_modificar_trabajos_de_grado.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuTr01IngresarModificarTrabajosDeGrado() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuTr01IngresarModificarTrabajosDeGradoRedirect()));
    }

    public String redirectCuIv3GestionarSemillerosInvestigacion() {
        return "/pages/secured/cu_iv_3/gestionar_semilleros_investigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuIv3GestionarSemillerosInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv3GestionarSemillerosInvestigacion()));
    }

    public String redirectCuIv3RegistrarSemillerosInvestigacion() {
        return "/pages/secured/cu_iv_3/registra_semilleros_investigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuIv3RegistrarSemillerosInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv3RegistrarSemillerosInvestigacion()));
    }

    public String redirectCu08ConsultarTrabajosYProyectos() {
        return "/pages/secured/cu_pr_08/consultarTrabajosYProyectos.xhtml?faces-redirect=true";
    }

    public void redirectFaces08ConsultarTrabajosYProyectos() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCu08ConsultarTrabajosYProyectos()));
    }

    public String redirectCuPr21RegistrarPlanDeTrabajoRedirect() {
        return "/pages/secured/cu_pr_21/registrarPlanDeTrabajo.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr21RegistrarPlanDeTrabajo() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr21RegistrarPlanDeTrabajoRedirect()));
    }

    public String redirectCuCo8EvaluacionEnsayoCriticoRedirect() {
        return "/pages/secured/cu_co_8/gestionarEvaluacionEnsayoCritico.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo8EvaluacionEnsayoCritico() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo8EvaluacionEnsayoCriticoRedirect()));
    }

    public String redirectCuCo8EvaluarEnsayoCriticoCriteriosRedirect() {
        return "/pages/secured/cu_co_8/evaluarEnsayoCriticoCriterios.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo8EvaluarEnsayoCriticoCriterios() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo8EvaluarEnsayoCriticoCriteriosRedirect()));
    }

    public String redirectCuCo9ConsultarDetallesEnsayoCriticoRedirect() {
        return "/pages/secured/cu_co_9/detallesEnsayoCritico.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo9ConsultarDetallesEnsayoCritico() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo9ConsultarDetallesEnsayoCriticoRedirect()));
    }

    public String redirectCuCo8SeleccionarGanadoresRedirect() {
        return "/pages/secured/cu_co_8/seleccionarGanadorEnsayo.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuCo8SeleccionarGanadoresCritico() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo8SeleccionarGanadoresRedirect()));
    }

    public String redirectCuPr15_1_2_AvanceImplemenacion() {
        return "/pages/secured/cu_pr_15_1_2/administrar_avance_implementacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr15_1_2_AvanceImplemenacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr15_1_2_AvanceImplemenacion()));
    }

    public String redirectCuPr13BuscarProyectosParaImplementarRedirect() {
        return "/pages/secured/cu_pr_13/buscarProyectoAImplementar.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr13BuscarProyectosParaImplementacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr13BuscarProyectosParaImplementarRedirect()));
    }

    public String redirectCuPr13HabilitarImplementacionRedirect() {
        return "/pages/secured/cu_pr_13/habilitarImplementacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr13HabilitarImplementacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr13HabilitarImplementacionRedirect()));
    }

    public String redirectCuIv02ConsultarInvestigadores() {
        return "/pages/secured/cu_iv_02/consultarInvestigadores.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuIv02ConsultarInvestigadores() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv02ConsultarInvestigadores()));
    }

    public String redirectCuIv02DetallesInvestigador() {
        return "/pages/secured/cu_iv_02/detallesInvestigador.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuIv02DetallesInvestigador() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv02DetallesInvestigador()));
    }

    public String redirectCuPr25RevisarCompromisoJefeUnidad() {
        return "/pages/secured/cu_pr_25/revisar_compromiso_jefe_unidad.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr25RevisarCompromisoJefeUnidad() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr25RevisarCompromisoJefeUnidad()));
    }

    public String redirectCuPr19ConsultarProyectosVigentesAsignadosVerComentariosRedirect() {
        return "/pages/secured/cu_pr_19/include_lista_comentarios_compromisos.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr19ConsultarProyectosVigentesAsignadosVerComentarios() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr19ConsultarProyectosVigentesAsignadosVerComentariosRedirect()));
    }

    public String redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad() {
        return "/pages/secured/cu_pr_24/revisar_compromiso_enviados_por_jefe_unidad.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr24RevisarCompromisoEnviadosPorJefeUnidad() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad()));
    }

    public String redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidadIrRetroalimentacion() {
        return "/pages/secured/cu_pr_24/include_agregar_retroalimentacion_compromisos.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr24RevisarCompromisoEnviadosPorJefeUnidadIrRetroalimentacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidadIrRetroalimentacion()));
    }

    public String redirectCuAd03CriteriosEvaluacion() {
        return "/pages/secured/cu_ad_03/adminCriteriosEvaluacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuAd03CriteriosEvaluacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuAd03CriteriosEvaluacion()));
    }

    public String redirectCuPr14AvancePresupuestoProyectoRedirect() {
        return "/pages/secured/cu_pr_14/gestionarAvancePresupuestoInvestigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr14AvancePresupuestoProyecto() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr14AvancePresupuestoProyectoRedirect()));
    }

    public String redirectCuPr20GestionImplementacionesVigentesAsignadasRedirect() {
        return "/pages/secured/cu_pr_20/consultaImplementacionesVigentes.xhtml?faces-redirect=true";
    }

    public String redirectCuPr20GestionImplementacionesVigentesAsignadasVerComentariosRedirect() {
        return "/pages/secured/cu_pr_20/include_lista_comentarios_compromisos.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr20GestionImplementacionesVigentesAsignadas() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr20GestionImplementacionesVigentesAsignadasRedirect()));
    }

    public String redirectCuPr12EvaluacionProyectosInvestigacionRedirect() {
        return "/pages/secured/cu_pr_12/seleccionProyectoEvaluacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr12EvaluacionProyectosInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr12EvaluacionProyectosInvestigacionRedirect()));
    }

    public String redirectCuPr11ConsultarDetalleCompromisoRedirect() {
        return "/pages/secured/cu_pr_11_1_2/consultar_detalle_compromiso.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr11ConsultarDetalleCompromiso() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr11ConsultarDetalleCompromisoRedirect()));
    }

    public String redirectCuPr09ConsultarProyectosDeInvestigacionRedirect() {
        return "/pages/secured/cu_pr_09/consultarProyectosDeInvestigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr09ConsultarProyectosDeInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr09ConsultarProyectosDeInvestigacionRedirect()));
    }

    public String redirectCuPr09ConsultarProyectosDeInvestigacionDetalleRedirect() {
        return "/pages/secured/cu_pr_09/consultarProyectosDeInvestigacionDetalle.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr09ConsultarProyectosDeInvestigacionDetalle() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr09ConsultarProyectosDeInvestigacionDetalleRedirect()));
    }

    public String redirectCuPr121EvaluacionProyectosInvestigacionRedirect() {
        return "/pages/secured/cu_pr_12/evaluacionProyectoInvestigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesredirectCuPr121EvaluacionProyectosInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr121EvaluacionProyectosInvestigacionRedirect()));
    }

    public String redirectCuAd01AdministracionUsuarioPerfilesRedirect() {
        return "/pages/secured/cu_ad_01/adminUsuariosPerfiles.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectCuAd01AdministracionUsuarioPerfiles() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuAd01AdministracionUsuarioPerfilesRedirect()));
    }

    ///--Test:
    public String redirectCuPruebasFaces() {
        return "/pages/secured/cu_vieco/cu_gestionar_evento_escuela.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectCuCuPruebasFaces() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPruebasFaces()));
    }

    public String redirectCuAd01_1AdministracionUsuarioPerfilesModificarRolRedirect() {
        return "/pages/secured/cu_ad_01/modificarRolUsuarioAdmin.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectCuAd01_1AdministracionUsuarioPerfilesModificarRol() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuAd01_1AdministracionUsuarioPerfilesModificarRolRedirect()));
    }

    public String redirectCuAd01_2AdministracionUsuarioPerfilesModificarRolRedirect() {
        return "/pages/secured/cu_ad_01/crearModificarRolesUsuarios.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectCuAd01_2AdministracionUsuarioPerfilesModificarRol() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuAd01_2AdministracionUsuarioPerfilesModificarRolRedirect()));
    }

    public String redirectCuIv4ConsultarDetalleSemilleroRedirect() {
        return "/pages/secured/cu_iv_4/seleccionSemilleroCriteriosBusqueda.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectCuIv4ConsultarDetalleSemillero() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv4ConsultarDetalleSemilleroRedirect()));
    }

    public String redirectCuIv4_1ConsultarDetalleSemilleroRedirect() {
        return "/pages/secured/cu_iv_4/detallesSemilleroInvestigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectCuIv4_1ConsultarDetalleSemillero() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuIv4_1ConsultarDetalleSemilleroRedirect()));
    }

    public String redirectCuAdPlantillaCorreoRedirect() {
        return "/pages/secured/cu_ad_correo/gestiona_plantilla_correo.xhtml?faces-redirect=true";
    }

    public String redirectCuPr16EncargadoGeneracionArchivosSniesRedirect() {
        return "/pages/secured/cu_pr_16/encargado_generar_archivos_snies.xhtml?faces-redirect=true";
    }

    public String redirectCuPr17AgregarAsesoriaProyectoInvestigacionRedirect() {
        return "/pages/secured/cu_pr_17/asesorias_proyectos_investigacion.xhtml?faces-redirect=true";
    }

    public String redirectCuPr17AgregarAsesoriaProyectoInvestigacionDetalleRedirect() {
        return "/pages/secured/cu_pr_17/include_registrar_resultado_asesoria.xhtml?faces-redirect=true";
    }

    public String redirectCuCo5ConsultarPropuestasProyectosInvestigacionRedirect() {
        return "/pages/secured/cu_co_5/propuestasConvocatoriasFinanciacionCriteriosBusqueda.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectConsultarPropuestasProyectosInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo5ConsultarPropuestasProyectosInvestigacionRedirect()));
    }

    public String redirectCuCo6ConsultarConvocatoriasRedirect() {
        return "/pages/secured/cu_co_6/seleccionConvocatoriasCriteriosBusqueda.xhtml?faces-redirect=true";
    }

    public void redirectFacesRedirectConsultarConvocatorias() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuCo6ConsultarConvocatoriasRedirect()));
    }

    public String redirectCuVersionProyectoRedirect() {
        return "/pages/secured/versiones/administra_informacion_proyecto_version.xhtml?faces-redirect=true";
    }

    public String redirectCuPr18IngresaModificarEventoInvestigacionRedirect() {
        return "/pages/secured/cu_pr_18/gestiona_evento_investigacion.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr18IngresaModificarEventoInvestigacion() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr18IngresaModificarEventoInvestigacionRedirect()));
    }

    public String redirectCuPr18RegistrarEventoInvestigacionRedirect() {
        return "/pages/secured/cu_pr_18/include_registra_evento_investigacion.xhtml?faces-redirect=true";
    }

    public String redirectCuPr18ConsultarEventoInvestigacionRedirect() {
        return "/pages/secured/cu_pr_18/include_consulta_evento_investigacion.xhtml?faces-redirect=true";
    }

    public String redirectCuAd02AdministracionParametrosSistemaRedirect() {
        return "/pages/secured/cu_ad_02/administrarParametrosSistema.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuAd02AdministracionParametrosSistema() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuAd02AdministracionParametrosSistemaRedirect()));
    }

    public String redirectCuPr14AvancePresupuestoProyectoImplRedirect() {
        return "/pages/secured/cu_pr_14/gestionarAvancePresupuestoInvestigacionImpl.xhtml?faces-redirect=true";
    }

    public void redirectFacesCuPr14AvancePresupuestoProyectoImpl() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCuPr14AvancePresupuestoProyectoImplRedirect()));
    }

    // opciones del menu agregadas en la version 2.1
    public String redirectSIEDUAuditoria() {
        return "/pages/secured/admin/auditoria.xhtml?faces-redirect=true";
    }

    public String redirectToListTiposDominiosSIEDU() {
        return "/pages/secured/admin/tiposdominios.xhtml?faces-redirect=true";
    }

    public String redirectToDetailTipoDominioSIEDU() {
        return "/pages/secured/admin/tipodominio.xhtml?faces-redirect=true";
    }

    public String redirectSIEDUDominios() {
        return "/pages/secured/admin/dominio.xhtml?faces-redirect=true";
    }
    
    public String redirectSIEDUEntidad() {
        return "/pages/secured/pae/entidad.xhtml?faces-redirect=true";
    }
    
    public String redirectCapacitacionFuncionarioSIEDU() {
        return "/pages/secured/desarrolloPae/capacitacion_funcionario.xhtml?faces-redirect=true";
    }
    
    public String redirect_CU_SEGUIMIENTO_DESARROLLO_PAE(){
        return "/pages/secured/desarrolloPae/avance_desarrollo_pae.xhtml?faces-redirect=true";
    }

    public String redirect_CU01_CAP_ADMINISTRAR_PAE() {
        return "/pages/secured/pae/pae.xhtml?faces-redirect=true";
    }

    public String redirect_CU02_CAP_COBERTURA_PAE() {
        return "/pages/secured/pae/cobertura.xhtml?faces-redirect=true";
    }

    public String redirect_CU03_CAP_NECESIDADES_PAE() {
        return "/pages/secured/pae/necesidad.xhtml?faces-redirect=true";
    }

    public String redirect_CU04_CAP_CAPACITACION_PAE() {
        return "/pages/secured/pae/capacitacion.xhtml?faces-redirect=true";
    }

    public String redirectToList_CU05_CAP_FORMACION_PAE() {
        return "/pages/secured/pae/formaciones.xhtml?faces-redirect=true";
    }

    public String redirectToDetail_CU05_CAP_FORMACION_PAE() {
        return "/pages/secured/pae/formacion.xhtml?faces-redirect=true";
    }

    public String redirectToList_CU06_CAP_PRESUPUESTO() {
        return "/pages/secured/presupuesto/presupuestos.xhtml?faces-redirect=true";
    }

    public String redirectToDetail_CU06_CAP_PRESUPUESTO() {
        return "/pages/secured/presupuesto/presupuesto.xhtml?faces-redirect=true";
    }

    public String redirect_CU07_CAP_PERSONAL_EXTERNO() {
        return "/pages/secured/personalexterno/personalexterno.xhtml?faces-redirect=true";
    }

    public String redirectToList_CU8_CAP_PROGRAMA() {
        return "/pages/secured/programa/programas.xhtml?faces-redirect=true";
    }

    public String redirectToDetail_CU8_CAP_PROGRAMA() {
        return "/pages/secured/programa/programa.xhtml?faces-redirect=true";
    }

    public String redirect_CU09_CAP_BUSCAR_EVENTO_ESCUELA() {
        return "/pages/secured/desarrolloPae/buscar_evento_escuela.xhtml?faces-redirect=true";
    }

    public String redirectCUAdministrarParametrosEvaluaciones() {
        return "/pages/secured/evaluacion/parametrosEvaluacion.xhtml?faces-redirect=true";
    }

    public String redirectCUAdministrarEvaluaciones() {
        return "/pages/secured/evaluacion/buscarEvaluacion.xhtml?faces-redirect=true";
    }
    
    public void redirectFacesCUAdministrarEvaluaciones() throws IOException{
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCUAdministrarEvaluaciones()));
    }

    public String redirectCUDesarrolloEvaluacion() {
        return "/pages/secured/evaluacion/desarrollo/filtroDesarrolloEvaluacion.xhtml?faces-redirect=true";
    }
    
    public String redirectCUAgregarEvaluacion(){
        return "/pages/secured/evaluacion/evaluacion.xhtml?faces-redirect=true";
    }
    
    public void redirectFacesCUAgregarEvaluacion() throws IOException{
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath().concat(request.getServletPath()).concat(redirectCUAgregarEvaluacion()));
    }

    public String redirectCuMe01ParametrizarInvestigacionUnidad() {
        return "/pages/secured/cu_me_01/parametrizar_investigacion_unidad.xhtml?faces-redirect=true";
    }

    public String redirectCuMe02CrearPropuestaBanco() {
        return "/pages/secured/cu_me_02/crear_propuesta_banco.xhtml?faces-redirect=true";
    }

    public String redirectCuMe03AsignarInvestigacionBancoUnidad() {
        return "/pages/secured/cu_me_03/asignar_investigacion_banco_unidad.xhtml?faces-redirect=true";
    }

    public String redirectCuMe04AprobarPropuestaBanco() {
        return "/pages/secured/cu_me_04/aprobar_propuesta_banco.xhtml?faces-redirect=true";
    }

    public String redirectCuMe05InsertarInvestigacionUnidad() {
        return "/pages/secured/cu_me_05/insertar_investigacion_unidad.xhtml?faces-redirect=true";
    }

    public String redirectCuMe06AprobarInvestigacionUnidadVicin() {
        return "/pages/secured/cu_me_06/aprobar_investigacion_unidad_vicin.xhtml?faces-redirect=true";
    }

    public String redirectCuMe07PropiedadIntelectual() {
        return "/pages/secured/cu_me_07/propiedad_intelectual.xhtml?faces-redirect=true";
    }

    public String redirectCuMe08InvestigacionExterna() {
        return "/pages/secured/cu_me_08/investigaciones_externas.xhtml?faces-redirect=true";
    }

    public String redirectCuMe09InvestigacionExternaUnidad() {
        return "/pages/secured/cu_me_09/investigaciones_externas_unidad.xhtml?faces-redirect=true";
    }

    public String redirectCuMe10EspacioEquipoElementoInvestigacion() {
        return "/pages/secured/cu_me_10/espacios_equipos_elementos_investigacion.xhtml?faces-redirect=true";
    }

    public String redirectCuMe11InsercionNotas() {
        return "/pages/secured/cu_me_11/insercion_notas.xhtml?faces-redirect=true";
    }
    
    public String redirectHorasDocente() {
        return "/pages/secured/consultas/consulta_horas_docente.xhtml?faces-redirect=true";
    }
    
    public String redirectHorasDocenteCapacitacion() {
        return "/pages/secured/desarrolloPae/horas_docente.xhtml?faces-redirect=true";
    }

}
