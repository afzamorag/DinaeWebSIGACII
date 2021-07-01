package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CompromisoPeriodoDTO;
import co.gov.policia.dinae.dto.CriterioEvaluacionDTO;
import co.gov.policia.dinae.dto.PeriodoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICriterioEvaluacionLocal;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.EnsayoCriticoUnidadView;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author cguzman
 */
@Named(value = "cuCo6ConsultaConvocatoriasFaces")
@SessionScoped
public class CuCo6ConsultaConvocatoriasFaces extends JSFUtils implements Serializable {

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IPeriodoLocal _iPeriodo;

  @EJB
  private ICompromisoPeriodoLocal _iCompromisoPeriodo;

  @EJB
  private ICriterioEvaluacionLocal _iCriteriosEvaluacion;

  @EJB
  private IProyectoLocal _iProyecto;

  @EJB
  private IEvaluacionProyectoLocal _iEvaluacionProyecto;

  @EJB
  private IEnsayoCriticoLocal _iEnsayoCritio;

  @EJB
  private IUsuarioUnidadPolicialLocal _iUsuarioUnidadPolicial;

  @javax.inject.Inject
  private CuCo9ConsultarDetallesEnsayoCritico cuCo9ConsultarDetallesEnsayoCriticoFaces;

  private final DecimalFormat decimalFormatSinDecimal = new DecimalFormat(keyPropertiesFactory.value("general_formato_valor_cifra_sin_decimal"));
  private final DecimalFormat decimalFormatConDecimal = new DecimalFormat(keyPropertiesFactory.value("general_formato_valor_cifra_decimal"));

  private Long _idTipoConvocatoria;

  private Integer _numeroConsecutivo;

  private String _palabraClave;

  private Long _idEstadoConvocatoria;

  private Date _fechaInicioConvocatoria;

  private Date _fechaFinConvocatoria;

  private List<SelectItem> _listaItemsTipoConvocatorias;

  private List<SelectItem> _listaItemsEstadosConvocatorias;

  private List<PeriodoDTO> _listadoConvocatorias;

  private ListGenericDataModel<PeriodoDTO> _listadoConvocatoriasModel;

  private PeriodoDTO _convocatoriaSeleccionada;

  private Periodo _periodo;

  private List<CompromisoPeriodoDTO> _listadoCompromisosConvocatoria;

  private List<CriterioEvaluacionDTO> _listadoCriterioEvaluacion;

  private List<ProyectoDTO> _listadoPropuestasPresentadas;

  private int _cantidadPropuestasPresentadas;

  private int _cantidadPropuestasAprobadas;

  private List<EvaluacionProyecto> _evaluacionesProyecto;

  private EnsayoCritico ensayoCriticoSeleccionado;

  private ListGenericDataModel<EnsayoCritico> _listadoEnsayosCritico;

  /**
   *
   * @return
   */
  public String initReturnCU() {
    cargarListaTipoConvocatorias();
    cargarListaEstadosConvocatoria();
    inicializarCamposFiltros();
    return navigationFaces.redirectCuCo6ConsultarConvocatoriasRedirect();
  }

  /**
   *
   */
  private void cargarListaTipoConvocatorias() {
    List<Constantes> tipoConvocatorias;
    try {
      tipoConvocatorias = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_CONVOCATORIA_GESTION);
      _listaItemsTipoConvocatorias = UtilidadesItem.getListaSel(tipoConvocatorias, "idConstantes", "valor");
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private void cargarListaEstadosConvocatoria() {
    List<Constantes> estadosConvocatorias;
    try {
      estadosConvocatorias = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_ESTADO_CONVOCATORIA);
      _listaItemsEstadosConvocatorias = UtilidadesItem.getListaSel(estadosConvocatorias, "idConstantes", "valor");
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public String limpiarFiltros() {
    inicializarCamposFiltros();
    return null;
  }

  /**
   *
   */
  private void inicializarCamposFiltros() {
    ensayoCriticoSeleccionado = null;
    _idEstadoConvocatoria = -1L;
    _idTipoConvocatoria = -1L;
    _numeroConsecutivo = null;
    _fechaFinConvocatoria = new Date(System.currentTimeMillis());
    _fechaInicioConvocatoria = null;
    _palabraClave = null;
    _convocatoriaSeleccionada = null;

    _listadoConvocatorias = new ArrayList<PeriodoDTO>();
    _listadoConvocatoriasModel = new ListGenericDataModel<PeriodoDTO>(_listadoConvocatorias);
  }

  public String cargarListaConvocatoriasFiltros() {

    try {
      Map<String, Object> criterios = new HashMap<String, Object>();

      if (_palabraClave != null && !"".equals(_palabraClave)) {
        criterios.put(IConstantes.CRITERIO_CONVOCATORIAS_PALABRA_CLAVE, _palabraClave);
      }

      if (_idEstadoConvocatoria != null && _idEstadoConvocatoria.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_CONVOCATORIAS_ESTADO_CONVOCATORIA, _idEstadoConvocatoria);
      }

      if (_idTipoConvocatoria != null && _idTipoConvocatoria.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_CONVOCATORIAS_TIPO_CONVOCATORIA, _idTipoConvocatoria);
      }

      if (_numeroConsecutivo != null) {
        criterios.put(IConstantes.CRITERIO_CONVOCATORIAS_CONSECUTIVO, _numeroConsecutivo);
      }

      if (_fechaInicioConvocatoria != null && _fechaFinConvocatoria != null) {
        criterios.put(IConstantes.CRITERIO_CONVOCATORIAS_FECHA_INICIAL, _fechaInicioConvocatoria);
        criterios.put(IConstantes.CRITERIO_CONVOCATORIAS_FECHA_FINAL, _fechaFinConvocatoria);
      }

      _listadoConvocatorias = _iPeriodo.getListaConvocatoriasPorTipoConvocatoriaFiltros(criterios);

      if (_listadoConvocatorias == null || _listadoConvocatorias.isEmpty()) {
        _listadoConvocatorias = new ArrayList<PeriodoDTO>();
      }

      if (_listadoConvocatorias.isEmpty()) {

        adicionaMensajeError("La consulta no retorno resultados.");

      }

      _listadoConvocatoriasModel = new ListGenericDataModel<PeriodoDTO>(_listadoConvocatorias);

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }

    return null;

  }

  /**
   *
   * @param e
   */
  public void seleccionarEnsayoYnavegar(SelectEvent se) {

    try {

      if (ensayoCriticoSeleccionado == null) {

        return;

      }

      EnsayoCriticoUnidadView ensayoCriticoUnidadView = new EnsayoCriticoUnidadView();
      ensayoCriticoUnidadView.setIdEnsayoCritico(ensayoCriticoSeleccionado.getIdEnsayoCritico());
      ensayoCriticoUnidadView.setConsultarEnsayoCritico(true);

      cuCo9ConsultarDetallesEnsayoCriticoFaces.initReturnCU(ensayoCriticoUnidadView);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, e);

    }

  }

  /**
   *
   * @param e
   */
  public void navgar(SelectEvent e) {
    try {
      if (_convocatoriaSeleccionada == null) {
        return;
      }

      _periodo = _iPeriodo.getPeriodoPorId(_convocatoriaSeleccionada.getIdPeriodo());

      CompromisoPeriodoDTO compromisoInicial = new CompromisoPeriodoDTO();
      compromisoInicial.setFecha(_periodo.getFechaFin());
      compromisoInicial.setNombreTipoCompromiso("Entrega de propuestas");
      compromisoInicial.setNumeroIncrementa(new Short("0"));
      compromisoInicial.setIdTipoCompromiso(-1L);

      _listadoCompromisosConvocatoria = new ArrayList<CompromisoPeriodoDTO>();
      _listadoCompromisosConvocatoria.add(compromisoInicial);
      _listadoCompromisosConvocatoria.addAll(_iCompromisoPeriodo.getListaCompromisoPeriodoDTOporConvocatoriaPeriodo(_periodo.getIdPeriodo()));

      String tipoCriterio = null;

      if (IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION.compareTo(_periodo.getTipoPeriodo().getIdConstantes()) == 0) {

        _listadoPropuestasPresentadas = _iProyecto.getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYestadoFinanciarYnoAprobada(_periodo.getIdPeriodo());
        _listadoEnsayosCritico = null;
        _cantidadPropuestasPresentadas = _listadoPropuestasPresentadas.size();
        _cantidadPropuestasAprobadas = _iProyecto.findAllPropuestaConvocatoriaPorConvocatoria(_periodo.getIdPeriodo()).size();
        tipoCriterio = IConstantes.TIPO_CRITERIO_EVALUACION_PROPUESTA_FINANCIACION;

      } else if (IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO.compareTo(_periodo.getTipoPeriodo().getIdConstantes()) == 0) {

        List<EnsayoCritico> listado = _iEnsayoCritio.findByPeriodo(_periodo.getIdPeriodo());
        _listadoEnsayosCritico = new ListGenericDataModel<EnsayoCritico>(listado);
        _listadoPropuestasPresentadas = null;
        _cantidadPropuestasPresentadas = listado.size();
        _cantidadPropuestasAprobadas = _iEnsayoCritio.countByPeriodoVicin(_periodo.getIdPeriodo());
        tipoCriterio = IConstantes.TIPO_CRITERIO_EVALUACION_PROPUESTA_ENSAYO_CRITICO;
      }

      _listadoCriterioEvaluacion = _iCriteriosEvaluacion.getListaCriterioEvaluacionPorEstadoDTO(
              IConstantes.ESTADO_ACTIVO_CRITERIO_EVALUACION,
              tipoCriterio);

      navigationFaces.redirectFacesCuGenerico("/pages/secured/cu_co_6/detallesConvocatoria.xhtml?faces-redirect=true");

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);

    } catch (IOException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String tomarCriteriosEvaluacionProyecto(Long idProyecto) {

    String retorno = "";

    try {
      if (idProyecto == null) {
        return retorno;
      }

      _evaluacionesProyecto = _iEvaluacionProyecto.getEvaluacionProyecto(idProyecto);

      if (!_evaluacionesProyecto.isEmpty()) {

        int contador = 1;
        for (EvaluacionProyecto eval : _evaluacionesProyecto) {

          retorno += "Criterio " + contador + " = " + decimalFormatSinDecimal.format(eval.getValorCriterio().doubleValue()) + "% <br />";
          contador += 1;
        }
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String tomarCalificacionProyecto(Long idProyecto) {

    String retorno = "";

    try {
      if (idProyecto == null) {
        return retorno;
      }

      _evaluacionesProyecto = _iEvaluacionProyecto.getEvaluacionProyecto(idProyecto);

      if (!_evaluacionesProyecto.isEmpty()) {

        BigDecimal califica = BigDecimal.ZERO;

        for (EvaluacionProyecto eval : _evaluacionesProyecto) {
          califica = califica.add(eval.getValorCriterio());
        }

        retorno = decimalFormatSinDecimal.format(califica.doubleValue()).concat("%");
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  /**
   *
   * @param idEnsayo
   * @return
   */
  public String tomarCriteriosEvaluacionEnsayoCritico(Long idEnsayo) {

    String retorno = "";

    try {
      if (idEnsayo == null) {
        return retorno;
      }

      _evaluacionesProyecto = _iEvaluacionProyecto.getCriterioPorEnsayoCritico(idEnsayo);

      if (!_evaluacionesProyecto.isEmpty()) {

        int contador = 1;
        for (EvaluacionProyecto eval : _evaluacionesProyecto) {

          retorno += "Criterio " + contador + " = " + decimalFormatConDecimal.format(eval.getValorCriterio().doubleValue()) + "% <br />";
          contador += 1;

        }
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  /**
   *
   * @param idEnsayo
   * @return
   */
  public String tomarUnidadPolicialEnsayoCritico(Long idEnsayo) {

    String retorno = "N/A";

    try {
      if (idEnsayo == null) {
        return retorno;
      }

      UsuarioRol usuarioRol = _iEnsayoCritio.findByIdEnsayoCritico(idEnsayo).getUsuarioRolCreacion();
      if (usuarioRol != null) {

        UnidadPolicialDTO unidad = _iUsuarioUnidadPolicial.findAllPorIdentificacion(usuarioRol.getIdentificadorUsuario());

        if (unidad != null) {
          retorno = unidad.getNombre();
        }
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  /**
   *
   * @param idEnsayo
   * @return
   */
  public String tomarCalificacionEnsayoCritico(Long idEnsayo) {

    String retorno = "";

    try {
      if (idEnsayo == null) {
        return retorno;
      }

      _evaluacionesProyecto = _iEvaluacionProyecto.getCriterioPorEnsayoCritico(idEnsayo);

      if (!_evaluacionesProyecto.isEmpty()) {

        BigDecimal califica = BigDecimal.ZERO;

        for (EvaluacionProyecto eval : _evaluacionesProyecto) {
          califica = califica.add(eval.getValorCriterio());
        }

        retorno = decimalFormatConDecimal.format(califica.doubleValue()).concat("%");
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo6ConsultaConvocatoriasFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return _periodo != null && _periodo.getNombreArchivo() != null && _periodo.getNombreArchivoFisico() != null;
  }

  public boolean isMostrarLinkDescargaArchivoReporteConvoca() {
    return _periodo != null && _periodo.getNombreArchivoFisicoConvocatoriaEnsayo() != null && _periodo.getNombreArchivoOriginalConvocatoriaEnsayo() != null;
  }

  public boolean isMostrarLinkDescargaArchivoReporteConvocaFinancia() {
    return _periodo != null && _periodo.getNombreArchivoFisicoPropuestaConvocatoriaReporte() != null && _periodo.getNombreArchivoOriginalPropuestaConvocatoriaReporte() != null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (_periodo != null && _periodo.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_1_dir_file_archivo_soporte") + _periodo.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _periodo.getNombreArchivo());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-06 Consultar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentPropertyReporte() {

    try {

      if (_periodo != null && _periodo.getNombreArchivoFisicoPropuestaConvocatoriaReporte() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + _periodo.getNombreArchivoOriginalPropuestaConvocatoriaReporte();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _periodo.getNombreArchivoFisicoPropuestaConvocatoriaReporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentPropertyConvocatoriaEnsayo() {

    try {

      if (_periodo != null && _periodo.getNombreArchivoFisicoConvocatoriaEnsayo() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + _periodo.getNombreArchivoFisicoConvocatoriaEnsayo();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _periodo.getNombreArchivoOriginalConvocatoriaEnsayo());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-06 Consultar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @param event
   */
  public void noSeleccionDetalles(UnselectEvent event) {
    inicializarCamposFiltros();
  }

  public Long getIdTipoConvocatoria() {
    return _idTipoConvocatoria;
  }

  public void setIdTipoConvocatoria(Long _idTipoConvocatoria) {
    this._idTipoConvocatoria = _idTipoConvocatoria;
  }

  public Integer getNumeroConsecutivo() {
    return _numeroConsecutivo;
  }

  public void setNumeroConsecutivo(Integer _numeroConsecutivo) {
    this._numeroConsecutivo = _numeroConsecutivo;
  }

  public String getPalabraClave() {
    return _palabraClave;
  }

  public void setPalabraClave(String _palabraClave) {
    this._palabraClave = _palabraClave;
  }

  public Long getIdEstadoConvocatoria() {
    return _idEstadoConvocatoria;
  }

  public void setIdEstadoConvocatoria(Long _idEstadoConvocatoria) {
    this._idEstadoConvocatoria = _idEstadoConvocatoria;
  }

  public Date getFechaInicioConvocatoria() {
    return _fechaInicioConvocatoria;
  }

  public void setFechaInicioConvocatoria(Date _fechaInicioConvocatoria) {
    this._fechaInicioConvocatoria = _fechaInicioConvocatoria;
  }

  public Date getFechaFinalConvocatoria() {
    return _fechaFinConvocatoria;
  }

  public void setFechaFinalConvocatoria(Date _fechaFinConvocatoria) {
    this._fechaFinConvocatoria = _fechaFinConvocatoria;
  }

  public List<SelectItem> getListaItemsTipoConvocatorias() {
    return _listaItemsTipoConvocatorias;
  }

  public void setListaItemsTipoConvocatorias(List<SelectItem> _listaItemsTipoConvocatorias) {
    this._listaItemsTipoConvocatorias = _listaItemsTipoConvocatorias;
  }

  public List<SelectItem> getListaItemsEstadosConvocatorias() {
    return _listaItemsEstadosConvocatorias;
  }

  public void setListaItemsEstadosConvocatorias(List<SelectItem> _listaItemsEstadosConvocatorias) {
    this._listaItemsEstadosConvocatorias = _listaItemsEstadosConvocatorias;
  }

  public List<PeriodoDTO> getListadoConvocatorias() {
    return _listadoConvocatorias;
  }

  public void setListadoConvocatorias(List<PeriodoDTO> _listadoConvocatorias) {
    this._listadoConvocatorias = _listadoConvocatorias;
  }

  public ListGenericDataModel<PeriodoDTO> getListadoConvocatoriasModel() {
    return _listadoConvocatoriasModel;
  }

  public void setListadoConvocatoriasModel(ListGenericDataModel<PeriodoDTO> _listadoConvocatoriasModel) {
    this._listadoConvocatoriasModel = _listadoConvocatoriasModel;
  }

  public PeriodoDTO getConvocatoriaSeleccionada() {
    return _convocatoriaSeleccionada;
  }

  public void setConvocatoriaSeleccionada(PeriodoDTO _convocatoriaSeleccionada) {
    this._convocatoriaSeleccionada = _convocatoriaSeleccionada;
  }

  public Periodo getPeriodo() {
    return _periodo;
  }

  public void setPeriodo(Periodo _periodo) {
    this._periodo = _periodo;
  }

  public Date getFechaFinConvocatoria() {
    return _fechaFinConvocatoria;
  }

  public void setFechaFinConvocatoria(Date _fechaFinConvocatoria) {
    this._fechaFinConvocatoria = _fechaFinConvocatoria;
  }

  public List<CompromisoPeriodoDTO> getListadoCompromisosConvocatoria() {
    return _listadoCompromisosConvocatoria;
  }

  public void setListadoCompromisosConvocatoria(List<CompromisoPeriodoDTO> _listadoCompromisosConvocatoria) {
    this._listadoCompromisosConvocatoria = _listadoCompromisosConvocatoria;
  }

  public List<CriterioEvaluacionDTO> getListadoCriterioEvaluacion() {
    return _listadoCriterioEvaluacion;
  }

  public void setListadoCriterioEvaluacion(List<CriterioEvaluacionDTO> _listadoCriterioEvaluacion) {
    this._listadoCriterioEvaluacion = _listadoCriterioEvaluacion;
  }

  public List<ProyectoDTO> getListadoPropuestasPresentadas() {
    return _listadoPropuestasPresentadas;
  }

  public void setListadoPropuestasPresentadas(List<ProyectoDTO> _listadoPropuestasPresentadas) {
    this._listadoPropuestasPresentadas = _listadoPropuestasPresentadas;
  }

  public int getCantidadPropuestasPresentadas() {
    return _cantidadPropuestasPresentadas;
  }

  public void setCantidadPropuestasPresentadas(int _cantidadPropuestasPresentadas) {
    this._cantidadPropuestasPresentadas = _cantidadPropuestasPresentadas;
  }

  public int getCantidadPropuestasAprobadas() {
    return _cantidadPropuestasAprobadas;
  }

  public void setCantidadPropuestasAprobadas(int _cantidadPropuestasAprobadas) {
    this._cantidadPropuestasAprobadas = _cantidadPropuestasAprobadas;
  }

  public ListGenericDataModel<EnsayoCritico> getListadoEnsayosCritico() {
    return _listadoEnsayosCritico;
  }

  public void setListadoEnsayosCritico(ListGenericDataModel<EnsayoCritico> _listadoEnsayosCritico) {
    this._listadoEnsayosCritico = _listadoEnsayosCritico;
  }

  public EnsayoCritico getEnsayoCriticoSeleccionado() {
    return ensayoCriticoSeleccionado;
  }

  public void setEnsayoCriticoSeleccionado(EnsayoCritico ensayoCriticoSeleccionado) {
    this.ensayoCriticoSeleccionado = ensayoCriticoSeleccionado;
  }

}
