package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ITemaLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVersionesLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyectoVersion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Tema;
import co.gov.policia.dinae.modelo.TemaProyectoVersion;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "administracionProyectoVersionFaces")
@javax.enterprise.context.SessionScoped
public class AdministracionProyectoVersionFaces extends JSFUtils implements Serializable {

  private ProyectoDTO proyectoSeleccionado;
  private Long idProyectoVersion;

  @EJB
  private IVersionesLocal iVersionesLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;

  @EJB
  private ITemaLocal iTemaLocal;

  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  private List<AsesoriaProyectoDTO> listaAsesoriaProyectoDTO;
  private List<IndicadoresProyectoVersion> listaIndicadoresProyectoGeneral;
  private List<IndicadoresProyectoVersion> listaIndicadoresProyectoOtros;
  private List<InstitucionesProyectoDTO> listaOtrosInstitucionesInstitucionesProyectoDTO;
  private List<SemilleroProyectoDTO> listaUnidadPolicialSemilleroProyectoDTO;
  private List<SemilleroProyectoDTO> listaSemilleroProyectoDTO;
  private List<GrupoInvestigacionProyecto> listaGrupoInvestigacionProyecto;
  private List<EjecutorNecesidad> listaUnidadPolicialEjecutoras;
  private String lblTitulo;
  private Long idLineaSeleccionada;
  private List<Tema> listaTemas;
  private List<InvestigadorProyecto> listaInvestigadoresProyecto;
  private String duracionProyecto;
  private BigDecimal valorTotalProyecto;
  private BigDecimal recursosPolicia;
  private BigDecimal recursoFuenteExterna;
  private int idTabSeleccionado;
  private List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba;
  private TemaProyectoVersion temaProyectoSeleccionado;
  private String nombreArchivoPlanteamientoProyecto;
  private String _importPresupuestoPage;
  private Date fechaVersion;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      fechaVersion = null;
      nombreArchivoPlanteamientoProyecto = null;
      temaProyectoSeleccionado = null;
      listaJefeFuncionariosSeleccionadoAprueba = null;
      idTabSeleccionado = 0;
      idProyectoVersion = null;
      listaAsesoriaProyectoDTO = null;
      listaIndicadoresProyectoOtros = null;
      listaIndicadoresProyectoGeneral = null;
      listaOtrosInstitucionesInstitucionesProyectoDTO = null;
      listaUnidadPolicialSemilleroProyectoDTO = null;
      listaSemilleroProyectoDTO = null;
      listaGrupoInvestigacionProyecto = null;
      listaInvestigadoresProyecto = null;
      listaTemas = null;
      idLineaSeleccionada = null;
      listaUnidadPolicialEjecutoras = null;
      lblTitulo = null;
      duracionProyecto = null;
      valorTotalProyecto = null;
      recursosPolicia = null;
      recursoFuenteExterna = null;
      _importPresupuestoPage = null;
      proyectoSeleccionado = new ProyectoDTO();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-VERSION ", e);
    }
  }

  /**
   *
   * @param idProyecto
   * @param idVersion
   * @param fechaVersion
   * @throws Exception
   */
  public void iniciarDatosProyectoVersion(Long idProyecto, Long idVersion, Date fechaVersion) throws Exception {

    try {

      //SE CARGAN LAS PROPIEDADES PROYECTOS
      init();

      this.fechaVersion = fechaVersion;

      //String idProyectoDesEncriptado = desEncriptarMensajeArreglo( getFacesContext().getExternalContext().getRequestParameterMap().get("p") );
      //String idVersionDescEncriptado = desEncriptarMensajeArreglo( getFacesContext().getExternalContext().getRequestParameterMap().get("v") );
      proyectoSeleccionado = iProyectoLocal.getProyectoVersionPorIdProyecto(idProyecto, idVersion);

      idProyectoVersion = iVersionesLocal.getIdProyectoVersion(idVersion, idProyecto);

      inicializarDatosProyectoPresupuesto();

      lblTitulo = keyPropertiesFactory.value("cu_pr_1_lbl_registra_propuesta_convoca");

      //LISTA DE UNIDADES EJECUTORAS
      listaUnidadPolicialEjecutoras = new ArrayList<EjecutorNecesidad>();

      idLineaSeleccionada = proyectoSeleccionado.getIdLinea();

      listaInvestigadoresProyecto = cargarListaInvestigadorProyecto();

      //CARGAMOS LA LISTA DE UNIDADES EJECUTORAS
      listaUnidadPolicialEjecutoras = iEjecutorNecesidadLocal.getEjecutorNecesidadPorProyecto(proyectoSeleccionado.getId());

      lblTitulo = keyPropertiesFactory.value("cu_pr_1_lbl_registra_propuesta_convoca");

      //CARGAMOS LA LISTA DE TEMAS
      cargarListaTemas();

      //LISTA DE GRUPO INVESTIGACION PROYECTO
      cargarListaGrupoInvestigacionProyecto();

      //CARGA LISTA DE SEMILLERO PROYECTO
      cargarListaSemilleroProyecto();

      //CARGA LISTA DE UNIDADES POLICIAL PARTICIPANTES
      cargarListaUnidadPolicialParticipante();

      //CARGA LISTA DE OTRAS INSTITUCIONES
      cargarListaOtrasUnidadesParticipantes();

      //CARGA LISTA DE INDICADORES
      cargarListaIndicadoresProyectoGeneral();
      cargarListaIndicadoresProyectoOtros();

      cargarListaAsesoriaProyecto();

      //CARGAMOS LISTA DE JEFES DE UNIDAD QUE APRUEBA
      listaJefeFuncionariosSeleccionadoAprueba = iUsuarioUnidadPolicialLocal.getUsuarioByUnidadByRole(
              proyectoSeleccionado.getIdUnidadPolicial(),
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);

      //TODOS LOS JEFES PERTENECEN A LA MISMA UNIDAD FUNCIONAL
      for (VistaFuncionario unaVistaFuncionario : listaJefeFuncionariosSeleccionadoAprueba) {

        UnidadPolicial unidadPolicial = new UnidadPolicial();
        unidadPolicial.setIdUnidadPolicial(proyectoSeleccionado.getUnidadPolicialDTO().getIdUnidadPolicial());
        unidadPolicial.setNombre(proyectoSeleccionado.getUnidadPolicialDTO().getNombre());
        unidadPolicial.setSiglaFisica(proyectoSeleccionado.getUnidadPolicialDTO().getSiglaFisicaUnidad());
        unidadPolicial.setSiglaPapa(proyectoSeleccionado.getUnidadPolicialDTO().getSiglaPadre());
        unaVistaFuncionario.setUnidadPolicial(unidadPolicial);
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (initReturnCU_Desde_CU_CO_02) ", e);

    }

  }

  /**
   *
   * @param tema
   * @return
   */
  public String seleccionTema(Tema tema) {

    if (tema == null) {

      return null;

    }

    idTabSeleccionado = 2;

    try {
      //BUSCAMOS EL TEMA PROYECTO EN CASO EXISTA
      temaProyectoSeleccionado = iVersionesLocal.getTemaProyectoPorTemaYproyecto(tema.getIdTema(), idProyectoVersion);

      if (temaProyectoSeleccionado == null) {

        temaProyectoSeleccionado = new TemaProyectoVersion();
      }

      nombreArchivoPlanteamientoProyecto = temaProyectoSeleccionado.getArchivoSoporte();

      return navigationFaces.redirectCuVersionProyectoRedirect();

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cu Version ", e);

    }

    return null;
  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;
    _importPresupuestoPage = null;
    if (event == null || event.getTab() == null) {
      return;
    }
    if ("idTabViewInformacionBasicoProyecto".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idTabViewTalentoHumano".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    } else if ("idTabViewPlanteamientoProyecto".equals(event.getTab().getId())) {
      idTabSeleccionado = 2;
    } else if ("idTabViewOtrosParticipantes".equals(event.getTab().getId())) {
      idTabSeleccionado = 3;
    } else if ("idTabViewPresupuestoProyectoDetalleProyecto".equals(event.getTab().getId())) {
      idTabSeleccionado = 4;//DETALLE PREPUESTO PROYECTO            
      _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";
      presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL_VERSION, proyectoSeleccionado.getId(), null, idProyectoVersion);
    } else if ("idTabViewVerIndicadores".equals(event.getTab().getId())) {
      idTabSeleccionado = 5;//INDICADORES
    } else if ("idTabViewVerAsesoriasProyecto".equals(event.getTab().getId())) {
      idTabSeleccionado = 6;//ASESORIAS PROYECTO
    }
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaAsesoriaProyecto() throws Exception {

    listaAsesoriaProyectoDTO = iVersionesLocal.getListaAsesoriaProyectoDTOPorIdProyecto(idProyectoVersion);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoOtros() throws Exception {

    listaIndicadoresProyectoOtros = iVersionesLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            idProyectoVersion,
            IConstantes.NO_N,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoGeneral() throws Exception {

    listaIndicadoresProyectoGeneral = iVersionesLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            idProyectoVersion,
            IConstantes.YES_Y,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaOtrasUnidadesParticipantes() throws Exception {

    listaOtrosInstitucionesInstitucionesProyectoDTO = iVersionesLocal.getListaInstitucionesProyectoPorProyecto(idProyectoVersion);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaUnidadPolicialParticipante() throws Exception {

    listaUnidadPolicialSemilleroProyectoDTO = iVersionesLocal.getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(idProyectoVersion);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaSemilleroProyecto() throws Exception {

    listaSemilleroProyectoDTO = iVersionesLocal.getListaSemilleroProyectoDTOporProyecto(idProyectoVersion);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaGrupoInvestigacionProyecto() throws Exception {

    listaGrupoInvestigacionProyecto = iVersionesLocal.getListaGrupoInvestigacionProyectoPorProyecto(idProyectoVersion);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaTemas() throws Exception {

    //CONSULTAMOS LOS TEMAS A MOSTRAR
    listaTemas = iTemaLocal.getListaTemaTodos(IConstantes.DESTINO_TEMA_CU_PR_01_PROYECTO);
    if (idProyectoVersion != null) {

      //CONSULTAMOS LOS TEMAS QUE TIENEN CARGADA INFORMACION
      //CON EL FIN DE MOSTRAR O NO LA IMAGEN DE: icono_chechk.png
      List<Long> listaIdTemasInfoCargada = iVersionesLocal.getIDTemaProyectoPorProyecto(idProyectoVersion);

      //VERIFICAMOS QUE TEMAS YA CONTIENEN INFORMACION CARGADA
      for (Tema unTema : listaTemas) {
        for (Long unIDtema : listaIdTemasInfoCargada) {

          if (unIDtema.equals(unTema.getIdTema())) {

            unTema.setMostrarImagenCheckInformacionPlanteamientoProblema(true);
            //CONTINUAMOS CON EL SIGUIENTE TEMA
            break;
          }
        }
      }
    }

  }

  /**
   *
   * @return @throws Exception
   */
  private List<InvestigadorProyecto> cargarListaInvestigadorProyecto() throws Exception {

    return iVersionesLocal.getListaInvestigadorProyectoPorProyecto(idProyectoVersion);

  }

  /**
   *
   */
  public void inicializarDatosProyectoPresupuesto() {

    try {

      List<Constantes> listaDuracionProyecto = null;

      if (proyectoSeleccionado.getIdTipoPeriodo() == null) {

        listaDuracionProyecto = iConstantesLocal.getConstantesPorTipoPorCodigo(
                IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
                IConstantes.CODIGO_DURACION_PROYECTOS_INSTITUCIONALES);

      } else if (proyectoSeleccionado.getIdTipoPeriodo() != null
              && proyectoSeleccionado.getIdTipoPeriodo().equals(
                      IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION)) {

        listaDuracionProyecto = iConstantesLocal.getConstantesPorTipoPorCodigo(
                IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
                IConstantes.CODIGO_DURACION_PROYECTOS_DE_CONVOCATORIA);

      }

      duracionProyecto = listaDuracionProyecto.get(0).getValor().trim().concat(" Meses ");
      calcularValorTotalProyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "IncludeInformacionInformeProyectoGenericoFaces (setProyectoSeleccionado) ", e);
    }

  }

  /**
   *
   */
  private void calcularValorTotalProyecto() {

    presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL, proyectoSeleccionado.getId(), null);
    valorTotalProyecto = new BigDecimal(presupuestoUtilFaces.getTotalTotales().doubleValue());
    recursosPolicia = new BigDecimal(presupuestoUtilFaces.getTotalFuentesInternas().doubleValue());
    recursoFuenteExterna = new BigDecimal(presupuestoUtilFaces.getTotalFuentesExternas().doubleValue());
  }

  public BigDecimal getValorTotalProyecto() {
    return valorTotalProyecto;
  }

  public BigDecimal getRecursosPolicia() {
    return recursosPolicia;
  }

  public BigDecimal getRecursoFuenteExterna() {
    return recursoFuenteExterna;
  }

  public String getDuracionProyecto() {

    return duracionProyecto;
  }

  public ProyectoDTO getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(ProyectoDTO proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public List<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTO() {
    return listaAsesoriaProyectoDTO;
  }

  public void setListaAsesoriaProyectoDTO(List<AsesoriaProyectoDTO> listaAsesoriaProyectoDTO) {
    this.listaAsesoriaProyectoDTO = listaAsesoriaProyectoDTO;
  }

  public List<IndicadoresProyectoVersion> getListaIndicadoresProyectoGeneral() {
    return listaIndicadoresProyectoGeneral;
  }

  public void setListaIndicadoresProyectoGeneral(List<IndicadoresProyectoVersion> listaIndicadoresProyectoGeneral) {
    this.listaIndicadoresProyectoGeneral = listaIndicadoresProyectoGeneral;
  }

  public List<IndicadoresProyectoVersion> getListaIndicadoresProyectoOtros() {
    return listaIndicadoresProyectoOtros;
  }

  public void setListaIndicadoresProyectoOtros(List<IndicadoresProyectoVersion> listaIndicadoresProyectoOtros) {
    this.listaIndicadoresProyectoOtros = listaIndicadoresProyectoOtros;
  }

  public List<InstitucionesProyectoDTO> getListaOtrosInstitucionesInstitucionesProyectoDTO() {
    return listaOtrosInstitucionesInstitucionesProyectoDTO;
  }

  public void setListaOtrosInstitucionesInstitucionesProyectoDTO(List<InstitucionesProyectoDTO> listaOtrosInstitucionesInstitucionesProyectoDTO) {
    this.listaOtrosInstitucionesInstitucionesProyectoDTO = listaOtrosInstitucionesInstitucionesProyectoDTO;
  }

  public List<SemilleroProyectoDTO> getListaUnidadPolicialSemilleroProyectoDTO() {
    return listaUnidadPolicialSemilleroProyectoDTO;
  }

  public void setListaUnidadPolicialSemilleroProyectoDTO(List<SemilleroProyectoDTO> listaUnidadPolicialSemilleroProyectoDTO) {
    this.listaUnidadPolicialSemilleroProyectoDTO = listaUnidadPolicialSemilleroProyectoDTO;
  }

  public List<SemilleroProyectoDTO> getListaSemilleroProyectoDTO() {
    return listaSemilleroProyectoDTO;
  }

  public void setListaSemilleroProyectoDTO(List<SemilleroProyectoDTO> listaSemilleroProyectoDTO) {
    this.listaSemilleroProyectoDTO = listaSemilleroProyectoDTO;
  }

  public List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyecto() {
    return listaGrupoInvestigacionProyecto;
  }

  public void setListaGrupoInvestigacionProyecto(List<GrupoInvestigacionProyecto> listaGrupoInvestigacionProyecto) {
    this.listaGrupoInvestigacionProyecto = listaGrupoInvestigacionProyecto;
  }

  public List<EjecutorNecesidad> getListaUnidadPolicialEjecutoras() {
    return listaUnidadPolicialEjecutoras;
  }

  public void setListaUnidadPolicialEjecutoras(List<EjecutorNecesidad> listaUnidadPolicialEjecutoras) {
    this.listaUnidadPolicialEjecutoras = listaUnidadPolicialEjecutoras;
  }

  public String getLblTitulo() {
    return lblTitulo;
  }

  public void setLblTitulo(String lblTitulo) {
    this.lblTitulo = lblTitulo;
  }

  public Long getIdLineaSeleccionada() {
    return idLineaSeleccionada;
  }

  public void setIdLineaSeleccionada(Long idLineaSeleccionada) {
    this.idLineaSeleccionada = idLineaSeleccionada;
  }

  public List<Tema> getListaTemas() {
    return listaTemas;
  }

  public void setListaTemas(List<Tema> listaTemas) {
    this.listaTemas = listaTemas;
  }

  public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
    return listaInvestigadoresProyecto;
  }

  public void setListaInvestigadoresProyecto(List<InvestigadorProyecto> listaInvestigadoresProyecto) {
    this.listaInvestigadoresProyecto = listaInvestigadoresProyecto;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public List<VistaFuncionario> getListaJefeFuncionariosSeleccionadoAprueba() {
    return listaJefeFuncionariosSeleccionadoAprueba;
  }

  public void setListaJefeFuncionariosSeleccionadoAprueba(List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba) {
    this.listaJefeFuncionariosSeleccionadoAprueba = listaJefeFuncionariosSeleccionadoAprueba;
  }

  public TemaProyectoVersion getTemaProyectoSeleccionado() {
    return temaProyectoSeleccionado;
  }

  public void setTemaProyectoSeleccionado(TemaProyectoVersion temaProyectoSeleccionado) {
    this.temaProyectoSeleccionado = temaProyectoSeleccionado;
  }

  public boolean isMostrarEditorPlanteamiento() {
    return temaProyectoSeleccionado != null;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return temaProyectoSeleccionado != null && temaProyectoSeleccionado.getArchivoSoporte() != null && temaProyectoSeleccionado.getNombreArchivoFisico() != null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentPropertyTemaVersionSeleccionado() {

    try {

      if (temaProyectoSeleccionado != null
              && temaProyectoSeleccionado.getArchivoSoporte() != null
              && temaProyectoSeleccionado.getNombreArchivoFisico() != null) {

        String name = keyPropertiesFactory.value("cu_pr_4_dir_file_archivo_soporte") + temaProyectoSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, temaProyectoSeleccionado.getArchivoSoporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-01 Agregar / Modificar Proyecto (getDownloadContentProperty) ", e);
    }
    return null;
  }

  public String getNombreArchivoPlanteamientoProyecto() {
    return nombreArchivoPlanteamientoProyecto;
  }

  public void setNombreArchivoPlanteamientoProyecto(String nombreArchivoPlanteamientoProyecto) {
    this.nombreArchivoPlanteamientoProyecto = nombreArchivoPlanteamientoProyecto;
  }

  public String getImportPresupuestoPage() {
    return _importPresupuestoPage;
  }

  public void setImportPresupuestoPage(String _importPresupuestoPage) {
    this._importPresupuestoPage = _importPresupuestoPage;
  }

  public Date getFechaVersion() {
    return fechaVersion;
  }
}
