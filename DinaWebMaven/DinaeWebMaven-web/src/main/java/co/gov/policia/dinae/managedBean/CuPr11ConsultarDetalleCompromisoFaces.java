package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ActividadesRealizadasProyectoDTO;
import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.dto.ResultadosAlcanzadosProyectoDTO;
import co.gov.policia.dinae.dto.SugerenciasProyectoDTO;
import co.gov.policia.dinae.interfaces.IActividadesRealizadasProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IEvidenciaProyectoLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresProyectoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IReseniaInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IResultadosAlcanzadosProyectoLocal;
import co.gov.policia.dinae.interfaces.IResultadosInvestigacionLocal;
import co.gov.policia.dinae.interfaces.ISugerenciasProyectoLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.EvidenciaProyectoDTO;
import co.gov.policia.dinae.modelo.IndicadoresCompromisoProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyecto;
import co.gov.policia.dinae.modelo.InformeAvance;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.ReseniaInvestigacion;
import co.gov.policia.dinae.modelo.ResultadosInvestigacion;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
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
@javax.inject.Named(value = "cuPr11ConsultarDetalleCompromisoFaces")
@javax.enterprise.context.SessionScoped
public class CuPr11ConsultarDetalleCompromisoFaces extends JSFUtils implements Serializable {

  @EJB
  private ISugerenciasProyectoLocal iSugerenciasProyectoLocal;

  @EJB
  private IIndicadoresProyectoLocal iIndicadoresProyectoLocal;

  @EJB
  private IResultadosAlcanzadosProyectoLocal iResultadosAlcanzadosProyectoLocal;

  @EJB
  private IActividadesRealizadasProyectoLocal iActividadesRealizadasProyectoLocal;

  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private IInformeAvanceLocal iInformeAvanceLocal;

  @EJB
  private IReseniaInvestigacionLocal iReseniaInvestigacionLocal;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;

  @EJB
  private IEvidenciaProyectoLocal iEvidenciaProyectoLocal;

  @EJB
  private IResultadosInvestigacionLocal iResultadosInvestigacion;

  @javax.inject.Inject
  private IncludeInformacionInformeProyectoGenericoFaces includeInformacionInformeProyectoGenericoFaces;

  @javax.inject.Inject
  private CuPr7RegistrarAvanceInvestigacionFaces cuPr7RegistrarAvanceInvestigacionFaces;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  @Inject
  private CuPr14AvancePresupuestoProyecto cuPr14AvancePresupuestoProyectoFaces;

  private CuPr11ConsultarDetalleCompromisoFaces.ORIGEN_LLAMADO_CU registraLlamdoCU;

  private enum ORIGEN_LLAMADO_CU {

    CU_PR_25, CU_PR_06, CU_PR_24
  };

  private int idTabSeleccionado;
  private CompromisoProyecto compromisoProyectoSeleccionado;
  private ReseniaInvestigacion reseniaInvestigacionSeleccionada;
  private Proyecto proyectoSeleccionado;
  private boolean soloLectura;
  private InformeAvance informeAvanceSeleccionado;
  private Long idTipoCompromisoSeleccionado;
  private List<EjecutorNecesidad> listaUnidadPolicialEjecutoras;
  private List<InvestigadorProyecto> listaInvestigadoresProyecto;
  private List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba;
  private List<ActividadesRealizadasProyectoDTO> listaActividadesRealizadasProyectoDTO;
  private List<ResultadosAlcanzadosProyectoDTO> listaResultadosAlcanzadosProyectoDTO;
  private List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTOhorasInvestigacion;
  private List<IndicadoresCompromisoProyecto> listaIndicadoresCompromisoProyectoOtros;
  private List<IndicadoresProyecto> listaIndicadoresProyectoGeneral;
  private List<SugerenciasProyectoDTO> listaSugerenciasProyectoDTO;
  private List<EvidenciaProyectoDTO> listaEvidenciaProyectoDTO;
  private EvidenciaProyectoDTO evidenciaProyectoDTODescargarSeleccionado;
  private String lblTituloInforme;
  private String _importPresupuestoPage;
  private List<ResultadosInvestigacion> listaResultadosInvestigacion;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      listaResultadosInvestigacion = null;
      lblTituloInforme = null;
      evidenciaProyectoDTODescargarSeleccionado = null;
      listaEvidenciaProyectoDTO = null;
      listaSugerenciasProyectoDTO = null;
      listaIndicadoresProyectoGeneral = null;
      listaIndicadoresCompromisoProyectoOtros = null;
      listaInvestigadorProyectoDTOhorasInvestigacion = null;
      listaResultadosAlcanzadosProyectoDTO = null;
      listaActividadesRealizadasProyectoDTO = null;
      listaJefeFuncionariosSeleccionadoAprueba = null;
      listaInvestigadoresProyecto = null;
      listaUnidadPolicialEjecutoras = null;
      idTipoCompromisoSeleccionado = null;
      informeAvanceSeleccionado = null;
      soloLectura = true;
      idTabSeleccionado = 0;
      compromisoProyectoSeleccionado = null;
      reseniaInvestigacionSeleccionada = null;
      proyectoSeleccionado = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11  ", e);
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @throws Exception
   */
  private void cargarDatosCompromiso(Long idCompromisoProyecto) throws Exception {

    compromisoProyectoSeleccionado = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);
    proyectoSeleccionado = compromisoProyectoSeleccionado.getProyecto();

    includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(proyectoSeleccionado);

    idTipoCompromisoSeleccionado = compromisoProyectoSeleccionado.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes();

    lblTituloInforme = compromisoProyectoSeleccionado.getNombreCompromisoCorreccionNumeroIncrementa();

    reseniaInvestigacionSeleccionada = new ReseniaInvestigacion();
    //EL SISTEMA IDENTIFICA SI EL ACTOR SELECCIONÓ UN INFORME FINAL.
    if (idTipoCompromisoSeleccionado.equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {

      informeAvanceSeleccionado = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
              compromisoProyectoSeleccionado.getIdCompromisoProyecto(),
              proyectoSeleccionado.getIdProyecto());

      if (informeAvanceSeleccionado != null) {

        reseniaInvestigacionSeleccionada = iReseniaInvestigacionLocal.findByInformeFinalProyecto(
                informeAvanceSeleccionado.getIdInformeAvance(),
                proyectoSeleccionado.getIdProyecto());

        listaResultadosInvestigacion = iResultadosInvestigacion.findByInformeAvanceProyecto(
                informeAvanceSeleccionado.getIdInformeAvance(),
                proyectoSeleccionado.getIdProyecto());

      }

    } else if (idTipoCompromisoSeleccionado.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE) || idTipoCompromisoSeleccionado.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)) {

      //CARGAMOS LA LISTA DE UNIDADES EJECUTORAS
      listaUnidadPolicialEjecutoras = iEjecutorNecesidadLocal.getEjecutorNecesidadPorProyecto(proyectoSeleccionado.getIdProyecto());
      //SI NO TIENEN UNIDAD EJECUTORA, LE ASIGNA POR DEFECTOR LA UNIDAD DEL PROYECTO, CON ROL RESPONSABLE
      if (listaUnidadPolicialEjecutoras.isEmpty()) {

        EjecutorNecesidad ejecutorNecesidad = new EjecutorNecesidad();
        ejecutorNecesidad.setProyecto(proyectoSeleccionado);

        ejecutorNecesidad.setRol(iConstantesLocal.getConstantesPorIdConstante(
                IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE));

        ejecutorNecesidad.setUnidadPolicial(proyectoSeleccionado.getUnidadPolicial());

        listaUnidadPolicialEjecutoras.add(ejecutorNecesidad);

      }

      //CARGAMOS LA LISTA DE INVESTIGADORES TIPO INVESTIGADOR
      listaInvestigadoresProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorProyectoYtipoVinculacion(
              proyectoSeleccionado.getIdProyecto(),
              IConstantes.TIPO_INVESTIGADOR_PROYECTO_INVESTIGADOR_PRINCIPAL);

      //CARGAMOS LISTA DE JEFES DE UNIDAD QUE APRUEBA
      listaJefeFuncionariosSeleccionadoAprueba = iUsuarioUnidadPolicialLocal.getUsuarioByUnidadByRole(
              proyectoSeleccionado.getUnidadPolicial().getIdUnidadPolicial(),
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);

      //TODOS LOS JEFES PERTENECEN A LA MISMA UNIDAD FUNCIONAL
      for (VistaFuncionario unaVistaFuncionario : listaJefeFuncionariosSeleccionadoAprueba) {
        unaVistaFuncionario.setUnidadPolicial(proyectoSeleccionado.getUnidadPolicial());
      }

      //CARGAR LISTA DE ACTIVIDADES REALIZADAS
      listaActividadesRealizadasProyectoDTO = iActividadesRealizadasProyectoLocal.getListaActividadesRealizadasProyectoDTOporProyectoYcompromisoProyecto(
              proyectoSeleccionado.getIdProyecto(),
              idCompromisoProyecto);

      //CARGAR LISTA RESULTADOS ESPERADOS
      listaResultadosAlcanzadosProyectoDTO = iResultadosAlcanzadosProyectoLocal.getListaResultadosAlcanzadosProyectoDTOporProyectoYcompromisoProyecto(
              proyectoSeleccionado.getIdProyecto(),
              idCompromisoProyecto);

      listaInvestigadorProyectoDTOhorasInvestigacion = iInvestigadorProyectoLocal.getListaInvestigadorProyectoDTOPorProyectoYcompromisoProyecto(
              proyectoSeleccionado.getIdProyecto(),
              idCompromisoProyecto);

      //CARGA LISTA DE INDICADORES GENERAL
      listaIndicadoresProyectoGeneral = iIndicadoresProyectoLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
              proyectoSeleccionado.getIdProyecto(),
              IConstantes.YES_Y,
              IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

      //CARGA LA LISTA DE INDICADORES OTROS
      listaIndicadoresCompromisoProyectoOtros = iIndicadoresProyectoLocal.getListaIndicadoresCompromisoProyectoPorProyectoEindicadorBase(
              proyectoSeleccionado.getIdProyecto(),
              IConstantes.NO_N,
              IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01,
              idCompromisoProyecto);

      listaSugerenciasProyectoDTO = iSugerenciasProyectoLocal.getListaSugerenciasProyectoDTOporProyectoYcompromisoProyecto(
              proyectoSeleccionado.getIdProyecto(),
              idCompromisoProyecto);

      listaEvidenciaProyectoDTO = iEvidenciaProyectoLocal.getListaEvidenciaProyectoDTOPorProyectoYcompromisoProyecto(
              proyectoSeleccionado.getIdProyecto(),
              idCompromisoProyecto);

      informeAvanceSeleccionado = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
              compromisoProyectoSeleccionado.getIdCompromisoProyecto(),
              proyectoSeleccionado.getIdProyecto());
    }

  }

  /**
   *
   * @return
   */
  public String invocarCuPr7() {

    try {

      return cuPr7RegistrarAvanceInvestigacionFaces.initReturnCU_Desde_CU_PR_11(
              proyectoSeleccionado.getIdProyecto(),
              compromisoProyectoSeleccionado.getIdCompromisoProyecto());

    } catch (Exception ex) {

      adicionaMensajeError("Error indeterminado al tratar de invocar al CU-PR-7: " + ex.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11 ", ex);
    }
    return null;
  }

  public StreamedContent getDownloadContentPropertyInfomeAvanceSeleccionado() {
    try {
      if (informeAvanceSeleccionado != null && informeAvanceSeleccionado.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + informeAvanceSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, informeAvanceSeleccionado.getNombreArchivo());
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11 ", e);
    }
    return null;
  }

  /**
   * Llamado desde CU PR 25
   *
   * @param idCompromisoProyecto
   * @param soloLectura
   * @return
   */
  public String initReturnCU_Llamado_Desde_CUPR6(Long idCompromisoProyecto, boolean soloLectura) {

    try {

      String retorno = initReturnCU();
      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_06;
      this.soloLectura = soloLectura;

      cargarDatosCompromiso(idCompromisoProyecto);

      return retorno;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11  ", e);

    }

    return null;
  }

  /**
   * Llamado desde CU PR 25
   *
   * @param idCompromisoProyecto
   * @param soloLectura
   * @return
   */
  public String initReturnCU_Llamado_Desde_CUPR25(Long idCompromisoProyecto, boolean soloLectura) {

    try {

      String retorno = initReturnCU();
      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_25;
      this.soloLectura = soloLectura;

      cargarDatosCompromiso(idCompromisoProyecto);

      return retorno;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11  ", e);

    }

    return null;
  }

  /**
   * Llamado desde CU PR 25
   *
   * @param idCompromisoProyecto
   * @param soloLectura
   * @return
   */
  public String initReturnCU_Llamado_Desde_CUPR24(Long idCompromisoProyecto, boolean soloLectura) {

    try {

      String retorno = initReturnCU();

      cargarDatosCompromiso(idCompromisoProyecto);

      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_24;
      this.soloLectura = soloLectura;

      return retorno;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11  ", e);

    }

    return null;
  }

  /**
   * Llamado desde menu
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      return navigationFaces.redirectCuPr11ConsultarDetalleCompromisoRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-11  ", e);

    }

    return null;
  }

  public String regresar() {

    if (ORIGEN_LLAMADO_CU.CU_PR_25.equals(registraLlamdoCU)) {

      return navigationFaces.redirectCuPr25RevisarCompromisoJefeUnidad();

    }

    if (ORIGEN_LLAMADO_CU.CU_PR_06.equals(registraLlamdoCU)) {

      return navigationFaces.redirectCuPr06ConsultarDetalleProyectoInvestigacion();

    }

    if (ORIGEN_LLAMADO_CU.CU_PR_24.equals(registraLlamdoCU)) {

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad();

    }

    return null;

  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;
    if (event == null || event.getTab() == null) {
      return;
    }
    if ("itabtabcu_pr_11_lbl_tab_informacion_basica".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idtabcu_pr_11_lbl_tab_resenia_investigacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    } else if ("idcu_pr_11_lbl_tab_titulo_actividades_realiza".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    } else if ("idcu_pr_11_lbl_tab_titulo_resultados_alcanz".equals(event.getTab().getId())) {
      idTabSeleccionado = 2;
    } else if ("idcu_pr_11_lbl_tab_titulo_horas_investi".equals(event.getTab().getId())) {
      idTabSeleccionado = 3;
    } else if ("idcu_pr_11_lbl_tab_titulo_presupuesto_ejecu".equals(event.getTab().getId())) {
      idTabSeleccionado = 4;
      presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM, proyectoSeleccionado.getIdProyecto(), informeAvanceSeleccionado.getIdInformeAvance());
      _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";
    } else if ("idcu_pr_11_lbl_tab_titulo_indicadores".equals(event.getTab().getId())) {
      idTabSeleccionado = 5;
    } else if ("idcu_pr_11_lbl_tab_titulo_sugerencia".equals(event.getTab().getId())) {
      idTabSeleccionado = 6;
    } else if ("idcu_pr_11_lbl_tab_titulo_evidencia".equals(event.getTab().getId())) {
      idTabSeleccionado = 7;
    }

  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (evidenciaProyectoDTODescargarSeleccionado != null && evidenciaProyectoDTODescargarSeleccionado.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_7_dir_file_archivo_soporte") + evidenciaProyectoDTODescargarSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, evidenciaProyectoDTODescargarSeleccionado.getNombreArchivo());

        evidenciaProyectoDTODescargarSeleccionado = null;

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  public String irAvancePresupuestoCuPr14() {
    return cuPr14AvancePresupuestoProyectoFaces.initReturnCU(proyectoSeleccionado.getIdProyecto(), compromisoProyectoSeleccionado.getIdCompromisoProyecto(), CuPr14AvancePresupuestoProyecto.CU_PR_11, true);
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public CompromisoProyecto getCompromisoProyectoSeleccionado() {
    return compromisoProyectoSeleccionado;
  }

  public void setCompromisoProyectoSeleccionado(CompromisoProyecto compromisoProyectoSeleccionado) {
    this.compromisoProyectoSeleccionado = compromisoProyectoSeleccionado;
  }

  public boolean isSoloLectura() {
    return soloLectura;
  }

  public void setSoloLectura(boolean soloLectura) {
    this.soloLectura = soloLectura;
  }

  public Proyecto getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public ReseniaInvestigacion getReseniaInvestigacionSeleccionada() {
    return reseniaInvestigacionSeleccionada;
  }

  public void setReseniaInvestigacionSeleccionada(ReseniaInvestigacion reseniaInvestigacionSeleccionada) {
    this.reseniaInvestigacionSeleccionada = reseniaInvestigacionSeleccionada;
  }

  public boolean isCompromisoInformeAvance() {
      if (IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE.equals(idTipoCompromisoSeleccionado) ||IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS.equals(idTipoCompromisoSeleccionado))
          return true;
      else
          return false;
  }
    
  public boolean isCompromisoInformeFinal() {
    return IConstantes.COMPROMISO_PERIODO_INFORME_FINAL.equals(idTipoCompromisoSeleccionado);
  }

  public List<EjecutorNecesidad> getListaUnidadPolicialEjecutoras() {
    return listaUnidadPolicialEjecutoras;
  }

  public void setListaUnidadPolicialEjecutoras(List<EjecutorNecesidad> listaUnidadPolicialEjecutoras) {
    this.listaUnidadPolicialEjecutoras = listaUnidadPolicialEjecutoras;
  }

  public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
    return listaInvestigadoresProyecto;
  }

  public void setListaInvestigadoresProyecto(List<InvestigadorProyecto> listaInvestigadoresProyecto) {
    this.listaInvestigadoresProyecto = listaInvestigadoresProyecto;
  }

  public List<VistaFuncionario> getListaJefeFuncionariosSeleccionadoAprueba() {
    return listaJefeFuncionariosSeleccionadoAprueba;
  }

  public void setListaJefeFuncionariosSeleccionadoAprueba(List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba) {
    this.listaJefeFuncionariosSeleccionadoAprueba = listaJefeFuncionariosSeleccionadoAprueba;
  }

  public List<ActividadesRealizadasProyectoDTO> getListaActividadesRealizadasProyectoDTO() {
    return listaActividadesRealizadasProyectoDTO;
  }

  public void setListaActividadesRealizadasProyectoDTO(List<ActividadesRealizadasProyectoDTO> listaActividadesRealizadasProyectoDTO) {
    this.listaActividadesRealizadasProyectoDTO = listaActividadesRealizadasProyectoDTO;
  }

  public List<ResultadosAlcanzadosProyectoDTO> getListaResultadosAlcanzadosProyectoDTO() {
    return listaResultadosAlcanzadosProyectoDTO;
  }

  public void setListaResultadosAlcanzadosProyectoDTO(List<ResultadosAlcanzadosProyectoDTO> listaResultadosAlcanzadosProyectoDTO) {
    this.listaResultadosAlcanzadosProyectoDTO = listaResultadosAlcanzadosProyectoDTO;
  }

  public List<InvestigadorProyectoDTO> getListaInvestigadorProyectoDTOhorasInvestigacion() {
    return listaInvestigadorProyectoDTOhorasInvestigacion;
  }

  public void setListaInvestigadorProyectoDTOhorasInvestigacion(List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTOhorasInvestigacion) {
    this.listaInvestigadorProyectoDTOhorasInvestigacion = listaInvestigadorProyectoDTOhorasInvestigacion;
  }

  public List<IndicadoresCompromisoProyecto> getListaIndicadoresCompromisoProyectoOtros() {
    return listaIndicadoresCompromisoProyectoOtros;
  }

  public void setListaIndicadoresCompromisoProyectoOtros(List<IndicadoresCompromisoProyecto> listaIndicadoresCompromisoProyectoOtros) {
    this.listaIndicadoresCompromisoProyectoOtros = listaIndicadoresCompromisoProyectoOtros;
  }

  public List<IndicadoresProyecto> getListaIndicadoresProyectoGeneral() {
    return listaIndicadoresProyectoGeneral;
  }

  public void setListaIndicadoresProyectoGeneral(List<IndicadoresProyecto> listaIndicadoresProyectoGeneral) {
    this.listaIndicadoresProyectoGeneral = listaIndicadoresProyectoGeneral;
  }

  public List<SugerenciasProyectoDTO> getListaSugerenciasProyectoDTO() {
    return listaSugerenciasProyectoDTO;
  }

  public void setListaSugerenciasProyectoDTO(List<SugerenciasProyectoDTO> listaSugerenciasProyectoDTO) {
    this.listaSugerenciasProyectoDTO = listaSugerenciasProyectoDTO;
  }

  public List<EvidenciaProyectoDTO> getListaEvidenciaProyectoDTO() {
    return listaEvidenciaProyectoDTO;
  }

  public void setListaEvidenciaProyectoDTO(List<EvidenciaProyectoDTO> listaEvidenciaProyectoDTO) {
    this.listaEvidenciaProyectoDTO = listaEvidenciaProyectoDTO;
  }

  public EvidenciaProyectoDTO getEvidenciaProyectoDTODescargarSeleccionado() {
    return evidenciaProyectoDTODescargarSeleccionado;
  }

  public void setEvidenciaProyectoDTODescargarSeleccionado(EvidenciaProyectoDTO evidenciaProyectoDTODescargarSeleccionado) {
    this.evidenciaProyectoDTODescargarSeleccionado = evidenciaProyectoDTODescargarSeleccionado;
  }

  public String getLblTituloInforme() {
    return lblTituloInforme;
  }

  public void setLblTituloInforme(String lblTituloInforme) {
    this.lblTituloInforme = lblTituloInforme;
  }

  public String getImportPresupuestoPage() {
    return _importPresupuestoPage;
  }

  public void setImportPresupuestoPage(String _importPresupuestoPage) {
    this._importPresupuestoPage = _importPresupuestoPage;
  }

  public InformeAvance getInformeAvanceSeleccionado() {
    return informeAvanceSeleccionado;
  }

  public List<ResultadosInvestigacion> getListaResultadosInvestigacion() {
    return listaResultadosInvestigacion;
  }

  public void setListaResultadosInvestigacion(List<ResultadosInvestigacion> listaResultadosInvestigacion) {
    this.listaResultadosInvestigacion = listaResultadosInvestigacion;
  }
}
