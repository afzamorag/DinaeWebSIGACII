package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProgramasLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ISemilleroInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr08ConsultarTrabajosYProyectos")
@javax.enterprise.context.SessionScoped
public class CuPr08ConsultarTrabajosYProyectosFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @javax.inject.Inject
  private CuTr01IngresarModificarTrabajoDeGradoFaces cuTr01IngresarModificarTrabajoDeGradoFaces;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IAreaCienciaTecnologiaLocal iAreaCienciaTecnologiaLocal;

  @EJB
  private ILineaLocal iLineaLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IProgramasLocal iProgramasLocal;

  @EJB
  private IGrupoInvestigacionLocal iGrupoInvestigacionLocal;

  @EJB
  private ISemilleroInvestigacionLocal iSemilleroInvestigacionLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  private List<Long> tiposProyectoSeleccionados;
  private List<SelectItem> listaItemsTiposDeProyecto;
  private List<SelectItem> listaItemsUnidadesPoliciales;
  private List<SelectItem> listaItemsTiposUnidadPolicial;
  private List<SelectItem> listaItemsAreaCienciaYTecnologia;
  private List<SelectItem> listaItemsLineaCienciaYTecnologia;
  private List<SelectItem> listaItemsEstadoProyecto;
  private List<SelectItem> listaItemsOrigenesProyecto;
  private List<SelectItem> listaItemsEstadosImplementacion;
  private List<SelectItem> listaItemsNombreProgramas;
  private List<SelectItem> listaItemsGruposInvestigacion;
  private List<SelectItem> listaItemsSemilleros;
  private List<SelectItem> listaItemsConvocatorias;
  private List<SelectItem> listaItemsFormasDeVer;

  private String opcionFiltroBusquedaInicialSeleccionada;
  private String palabraClave;
  private Long idUnidadPolicial;
  private List<Long> idTiposUnidadesSeleccionadas;
  private Long idAreaCienciaTecnologiaSeleccionada;
  private Long idLineaSeleccionada;
  private BigDecimal valorProyectoRangoInicial;
  private BigDecimal valorProyectoRangoFinal;
  private boolean consultaRangoDeValores;
  private String codigoProyecto;
  private Long idEstadoProyecto;
  private Long idOrigenProyecto;
  private Long idEstadoImplementacion;
  private Long idNombreProgramaSeleccionado;
  private Date fechaPresentacionInicial;
  private Date fechaPresentacionFinal;
  private BigDecimal notaFinalRangoInicial;
  private BigDecimal notaFinalRangoFinal;
  private Long idGrupoInvestigacion;
  private Long idSemillero;
  private String nombresYApellidos;
  private Long idConvocatoria;
  private Long idFormasDeVer;
  private ProyectoDTO proyectoSeleccionado;

  /**
   * Lista de trabajos de grado o proyectos de investigación existentes
   */
  private ListGenericDataModel<ProyectoDTO> listaProyectosEncontrados;

  public String initReturnCU() throws Exception {

    try {

      init();
      //Se cargan las listas para la consulta de los filtros
      cargarDatosInicialesCUConsultarTrabajosDeGradoYProyectos();

    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }

    return navigationFaces.redirectCu08ConsultarTrabajosYProyectos();
  }

  //Constructor inicial
  @javax.annotation.PostConstruct
  public void init() {

    //Se inicializan todos los elementos en null        
    opcionFiltroBusquedaInicialSeleccionada = null;

    tiposProyectoSeleccionados = null;
    listaItemsTiposDeProyecto = null;
    listaItemsUnidadesPoliciales = null;
    listaItemsTiposUnidadPolicial = null;
    listaItemsAreaCienciaYTecnologia = null;
    listaItemsLineaCienciaYTecnologia = null;
    listaItemsEstadoProyecto = null;
    listaItemsNombreProgramas = null;
    listaItemsOrigenesProyecto = null;
    listaItemsEstadosImplementacion = null;

    listaProyectosEncontrados = null;

    limpiarInit();
  }

  private void limpiarInit() {

    palabraClave = null;
    idUnidadPolicial = null;
    idTiposUnidadesSeleccionadas = null;
    idAreaCienciaTecnologiaSeleccionada = null;
    idLineaSeleccionada = null;
    valorProyectoRangoInicial = null;
    valorProyectoRangoFinal = null;
    consultaRangoDeValores = false;
    codigoProyecto = null;
    idEstadoProyecto = null;
    idEstadoImplementacion = null;
    idNombreProgramaSeleccionado = null;
    fechaPresentacionInicial = null;
    fechaPresentacionFinal = null;
    notaFinalRangoInicial = null;
    notaFinalRangoFinal = null;
    idGrupoInvestigacion = null;
    idSemillero = null;
    nombresYApellidos = null;
    idConvocatoria = null;
    idFormasDeVer = IConstantes.TIPO_FORMAS_DE_VER_UNO_A_UNO;
    proyectoSeleccionado = null;
    idOrigenProyecto = null;

  }

  public void accionRadio() {

    try {

      limpiarInit();

      listaItemsTiposDeProyecto = new ArrayList<SelectItem>();

      if ("PROYECTO".equals(opcionFiltroBusquedaInicialSeleccionada)) {

        listaItemsTiposDeProyecto.add(new SelectItem(235L, "Investigaciones institucionales"));
        listaItemsTiposDeProyecto.add(new SelectItem(237L, "Para financiación"));

      } else if ("TRABAJO_GRADO".equals(opcionFiltroBusquedaInicialSeleccionada)) {

        listaItemsTiposDeProyecto.add(new SelectItem(236L, "Trabajos de grado"));

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-08", e);

    }

  }

  public void noSeleccionProyecto(UnselectEvent event) {
    proyectoSeleccionado = null;
  }

  /**
   *
   * @param event
   */
  public void verDatalleProyectoInvestigacion(SelectEvent event) {
    try {

      if (proyectoSeleccionado == null) {
        return;
      }

      if (proyectoSeleccionado.getCodigoProyecto() != null && proyectoSeleccionado.getCodigoProyecto().startsWith("TG")) {

        String navegar = cuTr01IngresarModificarTrabajoDeGradoFaces.initReturnCU_Desde_CuPr08(proyectoSeleccionado.getId());
        navigationFaces.redirectFacesCuGenerico(navegar);

      } else {

        navigationFaces.redirectFacesCuGenerico(cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_PR_08(proyectoSeleccionado.getId()));
      }

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-08", ex);
    }
  }

  public void handleAreaCienciaTecnologiaSeleccionadaChange() {

    if (idAreaCienciaTecnologiaSeleccionada != null) {

      try {

        listaItemsLineaCienciaYTecnologia = UtilidadesItem.getListaSel(iLineaLocal.getLineasPorArea(idAreaCienciaTecnologiaSeleccionada),
                "idLinea",
                "nombre");

      } catch (Exception e) {

        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-08", e);

      }
    } else {

      listaItemsLineaCienciaYTecnologia = new ArrayList<SelectItem>();
    }
  }
  //Inicializadores    
  //Metodo que carga las listas de consulta para los filtros

  private void cargarDatosInicialesCUConsultarTrabajosDeGradoYProyectos() throws Exception {

    listaItemsUnidadesPoliciales = UtilidadesItem.getListaSel(
            iUnidadPolicialLocal.getAllUnidadesPolicialesActivasOrdenAlfabetico(),
            "idUnidadPolicial",
            "siglaFisicaYnombreUnidad");

    listaItemsAreaCienciaYTecnologia = UtilidadesItem.getListaSel(
            iAreaCienciaTecnologiaLocal.getAreaCienciaTecnologias(),
            "idAreaCienciaTecnologia",
            "nombre");

    listaItemsEstadoProyecto = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_ESTADO_PROYECTO),
            "idConstantes",
            "valor");

    listaItemsOrigenesProyecto = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_ORIGENES_PROYECTO),
            "idConstantes",
            "valor");

    listaItemsEstadosImplementacion = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesPorTipo(IConstantes.ESTADO_IMPLEMENTACION_PROYECTO),
            "idConstantes",
            "valor");

    listaItemsNombreProgramas = UtilidadesItem.getListaSel(
            iProgramasLocal.getProgramas(),
            "idPrograma",
            "nombre");

    listaItemsGruposInvestigacion = UtilidadesItem.getListaSel(
            iGrupoInvestigacionLocal.getListaGrupoInvestigacionTodos(),
            "idGrupoInvestigacion",
            "descripcion");

    listaItemsSemilleros = UtilidadesItem.getListaSel(
            iSemilleroInvestigacionLocal.getListaSemilleroInvestigacionTodos(),
            "idSemillero",
            "nombre");

    //PERIODO con CONSECUTIVO_CONVOCATORIA
    listaItemsConvocatorias = UtilidadesItem.getListaSel(
            iPeriodoLocal.getListaConvocatoriasParaBusquedaProyectos(),
            "idPeriodo",
            "nombreConvocatorio");

    listaItemsFormasDeVer = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_FORMAS_DE_VER),
            "idConstantes",
            "valor");

  }

  /**
   * Método que activa la busqueda por rangos de valores
   *
   */
  public void activarBusquedaPorRangoDeValor() {
    consultaRangoDeValores = true;
  }

  //Metodo que realiza la busqueda de trabajos o proyectos de grado
  public void buscarTrabajoOProyecto() throws JpaDinaeException {
    try {

      //Validaciones
      //Para busqueda por rangos
      if (valorProyectoRangoInicial != null && valorProyectoRangoFinal != null) {

        int enteroComparacion = valorProyectoRangoInicial.compareTo(valorProyectoRangoFinal);
        int enteroNegativo = -1;

        if (valorProyectoRangoInicial.signum() == enteroNegativo || valorProyectoRangoFinal.signum() == enteroNegativo) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_08_lbl_error_msg_rango_valor_negativo"));
          return;
        }

        if (enteroComparacion > 0) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_08_lbl_error_msg_valor_rango_inicial_mayor_valor_rango_final"));
          return;
        }
      }

      //Para busqueda por fechas
      if (fechaPresentacionInicial != null) {
        if (fechaPresentacionFinal != null) {
          if (fechaPresentacionInicial.after(fechaPresentacionFinal)) {
            adicionaMensajeError(keyPropertiesFactory.value("cu_pr_08_lbl_error_msg_fecha_presentacion_inicial_mayor_fecha_presentacion_final"));
            return;
          }
        }
        if (fechaPresentacionFinal == null) {
          fechaPresentacionFinal = new Date();
        }
      }

      //Para busqueda por notas
      if (notaFinalRangoInicial != null) {
        if (notaFinalRangoFinal != null) {
          if (notaFinalRangoInicial.compareTo(notaFinalRangoFinal) == 1) {
            adicionaMensajeError(keyPropertiesFactory.value("cu_pr_08_lbl_error_msg_nota_rango_inicial_mayor_nota_rango_final"));
            return;
          }
        }
        if (notaFinalRangoFinal == null) {
          notaFinalRangoFinal = new BigDecimal("00.00");
        }
      }

      listaProyectosEncontrados = new ListGenericDataModel(
              iProyectoLocal.getProyectosDTOOTrabajosBusquedaPorFiltros(
                      opcionFiltroBusquedaInicialSeleccionada,
                      palabraClave,
                      tiposProyectoSeleccionados,
                      idUnidadPolicial,
                      idTiposUnidadesSeleccionadas,
                      idAreaCienciaTecnologiaSeleccionada,
                      idLineaSeleccionada,
                      valorProyectoRangoInicial,
                      valorProyectoRangoFinal,
                      codigoProyecto,
                      idEstadoProyecto,
                      idOrigenProyecto,
                      idEstadoImplementacion,
                      idNombreProgramaSeleccionado,
                      fechaPresentacionInicial,
                      fechaPresentacionFinal,
                      notaFinalRangoInicial,
                      notaFinalRangoFinal,
                      idGrupoInvestigacion,
                      idSemillero,
                      nombresYApellidos,
                      idConvocatoria,
                      idFormasDeVer)
      );

      if (listaProyectosEncontrados.getNumeroRegistro() == 0) {

        if ("PROYECTO".equals(opcionFiltroBusquedaInicialSeleccionada)) {

          adicionaMensajeError("No se encontraron registros de \"Proyectos\"");

        } else if ("TRABAJO_GRADO".equals(opcionFiltroBusquedaInicialSeleccionada)) {

          adicionaMensajeError("No se encontraron registros de \"Trabajos de grados\"");

        }
      }

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-TR-01 buscarTrabajoOProyecto) ", e);

    }

  }

  //Setters y Getters
  public String getPalabraClave() {
    return palabraClave;
  }

  public void setPalabraClave(String palabraClave) {
    this.palabraClave = palabraClave;
  }

  public List<Long> getTiposProyectoSeleccionados() {
    return tiposProyectoSeleccionados;
  }

  public void setTiposProyectoSeleccionados(List<Long> tiposProyectoSeleccionados) {
    this.tiposProyectoSeleccionados = tiposProyectoSeleccionados;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public List<SelectItem> getListaItemsUnidadesPoliciales() {
    return listaItemsUnidadesPoliciales;
  }

  public void setListaItemsUnidadesPoliciales(List<SelectItem> listaItemsUnidadesPoliciales) {
    this.listaItemsUnidadesPoliciales = listaItemsUnidadesPoliciales;
  }

  public List<Long> getIdTiposUnidadesSeleccionadas() {
    return idTiposUnidadesSeleccionadas;
  }

  public void setIdTiposUnidadesSeleccionadas(List<Long> idTiposUnidadesSeleccionadas) {
    this.idTiposUnidadesSeleccionadas = idTiposUnidadesSeleccionadas;
  }

  public Long getIdAreaCienciaTecnologiaSeleccionada() {
    return idAreaCienciaTecnologiaSeleccionada;
  }

  public void setIdAreaCienciaTecnologiaSeleccionada(Long idAreaCienciaTecnologiaSeleccionada) {
    this.idAreaCienciaTecnologiaSeleccionada = idAreaCienciaTecnologiaSeleccionada;
  }

  public List<SelectItem> getListaItemsAreaCienciaYTecnologia() {
    return listaItemsAreaCienciaYTecnologia;
  }

  public void setListaItemsAreaCienciaYTecnologia(List<SelectItem> listaItemsAreaCienciaYTecnologia) {
    this.listaItemsAreaCienciaYTecnologia = listaItemsAreaCienciaYTecnologia;
  }

  public Long getIdLineaSeleccionada() {
    return idLineaSeleccionada;
  }

  public void setIdLineaSeleccionada(Long idLineaSeleccionada) {
    this.idLineaSeleccionada = idLineaSeleccionada;
  }

  public List<SelectItem> getListaItemsLineaCienciaYTecnologia() {
    return listaItemsLineaCienciaYTecnologia;
  }

  public void setListaItemsLineaCienciaYTecnologia(List<SelectItem> listaItemsLineaCienciaYTecnologia) {
    this.listaItemsLineaCienciaYTecnologia = listaItemsLineaCienciaYTecnologia;
  }

  public BigDecimal getValorProyectoRangoInicial() {
    return valorProyectoRangoInicial;
  }

  public void setValorProyectoRangoInicial(BigDecimal valorProyectoRangoInicial) {
    this.valorProyectoRangoInicial = valorProyectoRangoInicial;
  }

  public BigDecimal getValorProyectoRangoFinal() {
    return valorProyectoRangoFinal;
  }

  public void setValorProyectoRangoFinal(BigDecimal valorProyectoRangoFinal) {
    this.valorProyectoRangoFinal = valorProyectoRangoFinal;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public Long getIdEstadoProyecto() {
    return idEstadoProyecto;
  }

  public void setIdEstadoProyecto(Long idEstadoProyecto) {
    this.idEstadoProyecto = idEstadoProyecto;
  }

  public List<SelectItem> getListaItemsEstadoProyecto() {
    return listaItemsEstadoProyecto;
  }

  public void setListaItemsEstadoProyecto(List<SelectItem> listaItemsEstadoProyecto) {
    this.listaItemsEstadoProyecto = listaItemsEstadoProyecto;
  }

  public Long getIdOrigenProyecto() {
    return idOrigenProyecto;
  }

  public void setIdOrigenProyecto(Long idOrigenProyecto) {
    this.idOrigenProyecto = idOrigenProyecto;
  }

  public List<SelectItem> getListaItemsOrigenesProyecto() {
    return listaItemsOrigenesProyecto;
  }

  public void setListaItemsOrigenesProyecto(List<SelectItem> listaItemsOrigenesProyecto) {
    this.listaItemsOrigenesProyecto = listaItemsOrigenesProyecto;
  }

  public Long getIdEstadoImplementacion() {
    return idEstadoImplementacion;
  }

  public void setIdEstadoImplementacion(Long idEstadoImplementacion) {
    this.idEstadoImplementacion = idEstadoImplementacion;
  }

  public List<SelectItem> getListaItemsEstadosImplementacion() {
    return listaItemsEstadosImplementacion;
  }

  public void setListaItemsEstadosImplementacion(List<SelectItem> listaItemsEstadosImplementacion) {
    this.listaItemsEstadosImplementacion = listaItemsEstadosImplementacion;
  }

  public Long getIdNombreProgramaSeleccionado() {
    return idNombreProgramaSeleccionado;
  }

  public void setIdNombreProgramaSeleccionado(Long idNombreProgramaSeleccionado) {
    this.idNombreProgramaSeleccionado = idNombreProgramaSeleccionado;
  }

  public List<SelectItem> getListaItemsNombreProgramas() {
    return listaItemsNombreProgramas;
  }

  public void setListaItemsNombreProgramas(List<SelectItem> listaItemsNombreProgramas) {
    this.listaItemsNombreProgramas = listaItemsNombreProgramas;
  }

  public Date getFechaPresentacionInicial() {
    return fechaPresentacionInicial;
  }

  public void setFechaPresentacionInicial(Date fechaPresentacionInicial) {
    this.fechaPresentacionInicial = fechaPresentacionInicial;
  }

  public Date getFechaPresentacionFinal() {
    return fechaPresentacionFinal;
  }

  public void setFechaPresentacionFinal(Date fechaPresentacionFinal) {
    this.fechaPresentacionFinal = fechaPresentacionFinal;
  }

  public BigDecimal getNotaFinalRangoInicial() {
    return notaFinalRangoInicial;
  }

  public void setNotaFinalRangoInicial(BigDecimal notaFinalRangoInicial) {
    this.notaFinalRangoInicial = notaFinalRangoInicial;
  }

  public BigDecimal getNotaFinalRangoFinal() {
    return notaFinalRangoFinal;
  }

  public void setNotaFinalRangoFinal(BigDecimal notaFinalRangoFinal) {
    this.notaFinalRangoFinal = notaFinalRangoFinal;
  }

  public Long getIdGrupoInvestigacion() {
    return idGrupoInvestigacion;
  }

  public void setIdGrupoInvestigacion(Long idGrupoInvestigacion) {
    this.idGrupoInvestigacion = idGrupoInvestigacion;
  }

  public List<SelectItem> getListaItemsGruposInvestigacion() {
    return listaItemsGruposInvestigacion;
  }

  public void setListaItemsGruposInvestigacion(List<SelectItem> listaItemsGruposInvestigacion) {
    this.listaItemsGruposInvestigacion = listaItemsGruposInvestigacion;
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public List<SelectItem> getListaItemsSemilleros() {
    return listaItemsSemilleros;
  }

  public void setListaItemsSemilleros(List<SelectItem> listaItemsSemilleros) {
    this.listaItemsSemilleros = listaItemsSemilleros;
  }

  public String getNombresYApellidos() {
    return nombresYApellidos;
  }

  public void setNombresYApellidos(String nombresYApellidos) {
    this.nombresYApellidos = nombresYApellidos;
  }

  public Long getIdConvocatoria() {
    return idConvocatoria;
  }

  public void setIdConvocatoria(Long idConvocatoria) {
    this.idConvocatoria = idConvocatoria;
  }

  public List<SelectItem> getListaItemsConvocatorias() {
    return listaItemsConvocatorias;
  }

  public void setListaItemsConvocatorias(List<SelectItem> listaItemsConvocatorias) {
    this.listaItemsConvocatorias = listaItemsConvocatorias;
  }

  public Long getIdFormasDeVer() {
    return idFormasDeVer;
  }

  public void setIdFormasDeVer(Long idFormasDeVer) {
    this.idFormasDeVer = idFormasDeVer;
  }

  public List<SelectItem> getListaItemsFormasDeVer() {
    return listaItemsFormasDeVer;
  }

  public void setListaItemsFormasDeVer(List<SelectItem> listaItemsFormasDeVer) {
    this.listaItemsFormasDeVer = listaItemsFormasDeVer;
  }

  /**
   * @return the listaItemsTiposUnidadPolicial
   */
  public List<SelectItem> getListaItemsTiposUnidadPolicial() {
    return listaItemsTiposUnidadPolicial;
  }

  public ListGenericDataModel<ProyectoDTO> getListaProyectosEncontrados() {
    return listaProyectosEncontrados;
  }

  public void setListaProyectosEncontrados(ListGenericDataModel<ProyectoDTO> listaProyectosEncontrados) {
    this.listaProyectosEncontrados = listaProyectosEncontrados;
  }

  /**
   * @param listaItemsTiposUnidadPolicial the listaItemsTiposUnidadPolicial to set
   */
  public void setListaItemsTiposUnidadPolicial(List<SelectItem> listaItemsTiposUnidadPolicial) {
    this.listaItemsTiposUnidadPolicial = listaItemsTiposUnidadPolicial;
  }

  /**
   * @return the consultaRangoDeValores
   */
  public boolean isConsultaRangoDeValores() {
    return consultaRangoDeValores;
  }

  /**
   * @param consultaRangoDeValores the consultaRangoDeValores to set
   */
  public void setConsultaRangoDeValores(boolean consultaRangoDeValores) {
    this.consultaRangoDeValores = consultaRangoDeValores;
  }

  /**
   * @return the listaItemsTiposDeProyecto
   */
  public List<SelectItem> getListaItemsTiposDeProyecto() {
    return listaItemsTiposDeProyecto;
  }

  /**
   * @param listaItemsTiposDeProyecto the listaItemsTiposDeProyecto to set
   */
  public void setListaItemsTiposDeProyecto(List<SelectItem> listaItemsTiposDeProyecto) {
    this.listaItemsTiposDeProyecto = listaItemsTiposDeProyecto;
  }

  public ProyectoDTO getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(ProyectoDTO proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public String getOpcionFiltroBusquedaInicialSeleccionada() {
    return opcionFiltroBusquedaInicialSeleccionada;
  }

  public void setOpcionFiltroBusquedaInicialSeleccionada(String opcionFiltroBusquedaInicialSeleccionada) {
    this.opcionFiltroBusquedaInicialSeleccionada = opcionFiltroBusquedaInicialSeleccionada;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarReporte() {

    try {

      if (listaProyectosEncontrados != null && listaProyectosEncontrados.getNumeroRegistro() > 0) {

        //ORACLE NO ACEPTA MAS DE 1000 REGISTRO EN CONSULTAS CON SELECT IN( ... )
        // POR TAL RAZON, NO PERMITIMOS AL USUARIO ESTE TIPO DE REPORTES..
        if (listaProyectosEncontrados.getNumeroRegistro() > 1000) {
          adicionaMensajeError("Error, No es posible generar el reporte, debido a que tiene más de 1000 registros..");
        }

        List<Long> listaId = new ArrayList<Long>();
        for (ProyectoDTO unProyectoDTO : listaProyectosEncontrados) {
          listaId.add(unProyectoDTO.getId());
        }
        HashMap mapa = new HashMap();
        mapa.put("p_lista_id_proyecto", listaId);

        byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte3.jasper");

        InputStream stream = new ByteArrayInputStream(bitesPdf);

        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, "3_LISTADO PROYECTOS.pdf");

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  public StreamedContent getGenerarReporteTotal() {

    try {

      if (listaProyectosEncontrados != null && listaProyectosEncontrados.getNumeroRegistro() > 0) {

        List<Long> listaId = new ArrayList<Long>();
        for (ProyectoDTO unProyectoDTO : listaProyectosEncontrados) {
          listaId.add(unProyectoDTO.getId());
        }
        HashMap mapa = new HashMap();
        mapa.put("p_lista_id_proyecto", listaId);

        byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte4.jasper");

        InputStream stream = new ByteArrayInputStream(bitesPdf);

        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, "4_LISTADO TOTALES DE PROYECTOS.pdf");

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

}
