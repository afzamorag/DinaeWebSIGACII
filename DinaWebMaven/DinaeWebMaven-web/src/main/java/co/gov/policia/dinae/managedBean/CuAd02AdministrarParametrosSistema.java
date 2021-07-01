package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.ITemaLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.Tema;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.TablaGenericaParametros;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author cguzman
 */
@Named(value = "cuAd02AdministrarParametrosSistemaFaces")
@SessionScoped
public class CuAd02AdministrarParametrosSistema extends JSFUtils implements Serializable {

  private Long _idParametroSeleccionado;

  private List<SelectItem> _listaTipoParametrosView;

  private List<SelectItem> _listaAreasInvestigacionView;

  private List<AreaCienciaTecnologia> _listaAreaCienciaTecnologia;

  private List<Linea> _listaLineas;

  private List<Tema> _listaTemas;

  private List<Constantes> _listaTipoParametrosSistema;

  private List<GrupoInvestigacion> _listaGrupoInvestigacion;

  private List<Constantes> _listaParametrosTipoConstante;

  private List<TablaGenericaParametros> _tablaGenericaParametrosList;

  private ListGenericDataModel<TablaGenericaParametros> _tablaGenericaParametrosListModel;

  private TablaGenericaParametros _parametrosSeleccionado;

  @EJB
  private IGrupoInvestigacionLocal _iGrupoInvestigacion;

  @EJB
  private IAreaCienciaTecnologiaLocal _iArea;

  @EJB
  private ILineaLocal _iLinea;

  @EJB
  private ITemaLocal _iTema;

  @EJB
  private IConstantesLocal _iConstantes;

  private static final Long PARAMETRO_GRUPO_INVESTIGACION = 1008L;

  private static final Long PARAMETRO_INSTITUCIONES = 1009L;

  private static final Long PARAMETRO_AREAS = 1010L;

  private static final Long PARAMETRO_LINEAS = 1011L;

  private static final Long PARAMETRO_TIPO_INVESTIGADOR = 1012L;

  private static final Long PARAMETRO_ITEMS_PLANTEAMIENTO_PROYECTO = 1013L;

  private static final Long PARAMETRO_ITEMS_RESULTADOS_INFORME_FINAL_IMPL = 1014L;

  private static final Long PARAMETRO_ITEMS_INFO_TENER_ENCUENTA_IMPL = 1015L;

  private static final Long PARAMETRO_ITEMS_INFORME_AVANCE_IMPL = 1016L;

  private static final Long PARAMETRO_ITEMS_TENER_CUENTA_INFORME_AVANCE_IMPL = 1017L;

  private static final String INCLUDE_GRUPO_INVESTIGACION = "includePopupGrupoInvestigacion.xhtml";

  private static final String INCLUDE_AREAS = "includePopupAreas.xhtml";

  private static final String INCLUDE_CONSTANTES = "includePopupConstantes.xhtml";

  private static final String INCLUDE_TEMAS = "includePopupTemas.xhtml";

  private static final String INCLUDE_LINEAS = "includePopupLineas.xhtml";

  private static final String TITULO_POPUP_GRUPO_INVESTIGACION = "Grupo de investigación";

  private static final String TITULO_POPUP_AREAS = "Áreas de ciencia y tecnología";

  private static final String TITULO_POPUP_CONSTANTES = "Constantes";

  private static final String TITULO_POPUP_TEMAS = "Items de proyectos";

  private static final String TITULO_POPUP_LINEAS = "Líneas";

  private String _tipoTema;

  private String _tipoConstante;

  private String _sizePopup;

  private String _popupParametroSeleccionado;

  private String _tituloPopup;

  // Attributos formulario de grupos de investigacion
  private String _codigoGrupo;

  private String _nombreGrupo;

  private Date _fechaRegistro;

  private String _estadoGrupo;

  private boolean _modificarGrupo;

  // Atributos formulario areas
  @NotNull(message = "El campo 'Nombre de área' es requerido")
  private String _nombreArea;

  @NotNull(message = "El campo 'Estado' es requerido")
  private String _estadoArea;

  private boolean _modificarArea;

  // Atributos formulario constantes (Tipo Investigador, Instituciones)
  private String _valorConstante;

  private String _estadoConstante;

  private boolean _modificarConstante;

  // Atributos formulario Lineas
  private Long _idAreaLinea;

  private String _nombreLinea;

  private String _estadoLinea;

  private boolean _modificarLineas;

  // Atributos formulario temas
  private String _descripcionTema;

  private String _estadoTema;

  private boolean _modificarTema;

  private Tema _entidadTemaSeleccionada;

  private GrupoInvestigacion _entidadGrupoInvestigacionSeleccionado;

  private Constantes _entidadConstantesSeleccionada;

  private AreaCienciaTecnologia _entidadAreaSeleccionada;

  private Linea _entidadLineaSeleccionada;

  /**
   *
   * @return
   */
  public String initReturnCU() {

    String navegar = null;

    try {
      cargarListaTipoParametros();
      navegar = navigationFaces.redirectCuAd02AdministracionParametrosSistemaRedirect();

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, e);
    }

    return navegar;
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaTipoParametros() throws Exception {
    _listaTipoParametrosSistema = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_PARAMETROS_SISTEMA);
    _listaTipoParametrosView = UtilidadesItem.getListaSel(_listaTipoParametrosSistema, "idConstantes", "valor");
  }

  /**
   *
   */
  public void cargarDatosParametroSistemaSeleccionado() {

    if (_idParametroSeleccionado != null && !(_idParametroSeleccionado.compareTo(-1L) == 0)) {
      try {

        _tablaGenericaParametrosList = new ArrayList<TablaGenericaParametros>();

        if (PARAMETRO_GRUPO_INVESTIGACION.compareTo(_idParametroSeleccionado) == 0) {
          _listaGrupoInvestigacion = _iGrupoInvestigacion.getListaGrupoInvestigacionTodosEstados();
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaGrupoInvestigacion, TablaGenericaParametros.TIPO_PARAM_GRUPO_INV);
          _popupParametroSeleccionado = INCLUDE_GRUPO_INVESTIGACION;
          _tituloPopup = TITULO_POPUP_GRUPO_INVESTIGACION;
          _sizePopup = "50%";
          inicializarDatosGrupoInvestigacion();
        }

        if (PARAMETRO_INSTITUCIONES.compareTo(_idParametroSeleccionado) == 0) {
          _listaParametrosTipoConstante = _iConstantes.findAllPorTipoNoEstado(IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO);
          String sufijo = "Instituciones";
          _tituloPopup = TITULO_POPUP_CONSTANTES + " - " + sufijo;
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaParametrosTipoConstante, TablaGenericaParametros.TIPO_PARAM_INSTITUCIONES, sufijo);
          _popupParametroSeleccionado = INCLUDE_CONSTANTES;
          _sizePopup = "50%";
          _tipoConstante = IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO;
          inicializarDatosConstantes();

        }

        if (PARAMETRO_AREAS.compareTo(_idParametroSeleccionado) == 0) {
          _listaAreaCienciaTecnologia = _iArea.findAllNoEstado();
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaAreaCienciaTecnologia, TablaGenericaParametros.TIPO_PARAM_AREA);
          _popupParametroSeleccionado = INCLUDE_AREAS;
          _tituloPopup = TITULO_POPUP_AREAS;
          _sizePopup = "50%";
          inicializarDatosAreas();
        }

        if (PARAMETRO_LINEAS.compareTo(_idParametroSeleccionado) == 0) {
          _listaLineas = _iLinea.findAllNoEstado();
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaLineas, TablaGenericaParametros.TIPO_PARAM_LINEA);
          _popupParametroSeleccionado = INCLUDE_LINEAS;
          _tituloPopup = TITULO_POPUP_LINEAS;
          _sizePopup = "50%";
          inicializarDatosLineas();
        }

        if (PARAMETRO_TIPO_INVESTIGADOR.compareTo(_idParametroSeleccionado) == 0) {
          _listaParametrosTipoConstante = _iConstantes.findAllPorTipoNoEstado(IConstantes.TIPO_INVESTIGADOR_PROYECTO);
          String sufijo = "Tipo de investigador";
          _tituloPopup = TITULO_POPUP_CONSTANTES + " - " + sufijo;
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaParametrosTipoConstante, TablaGenericaParametros.TIPO_PARAM_TIPO_INV, sufijo);
          _popupParametroSeleccionado = INCLUDE_CONSTANTES;
          _sizePopup = "50%";
          _tipoConstante = IConstantes.TIPO_INVESTIGADOR_PROYECTO;
          inicializarDatosConstantes();
        }

        if (PARAMETRO_ITEMS_PLANTEAMIENTO_PROYECTO.compareTo(_idParametroSeleccionado) == 0) {
          _listaTemas = _iTema.findAllTipoNoEstado(IConstantes.DESTINO_TEMA_CU_PR_01_PROYECTO);
          String sufijo = "Ítems para el planteamiento del proyecto";
          _tituloPopup = TITULO_POPUP_TEMAS + " - " + sufijo;
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaTemas, TablaGenericaParametros.TIPO_PARAM_ITEMS_PLANTEAMIENTO_PROYECTO, sufijo);
          _popupParametroSeleccionado = INCLUDE_TEMAS;
          _sizePopup = "50%";
          _tipoTema = IConstantes.DESTINO_TEMA_CU_PR_01_PROYECTO;
          inicializarDatosTemas();
        }

        if (PARAMETRO_ITEMS_RESULTADOS_INFORME_FINAL_IMPL.compareTo(_idParametroSeleccionado) == 0) {
          _listaTemas = _iTema.findAllTipoNoEstado(IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_FINAL_PROCESO);
          String sufijo = "Ítems para resultados obtenidos en informe de final  implementación";
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaTemas, TablaGenericaParametros.TIPO_PARAM_ITEMS_RESULTADOS_INFORME_FINAL_IMPL, sufijo);
          _popupParametroSeleccionado = INCLUDE_TEMAS;
          _tituloPopup = TITULO_POPUP_TEMAS + " - " + sufijo;
          _sizePopup = "50%";
          _tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_FINAL_PROCESO;
          inicializarDatosTemas();
        }

        if (PARAMETRO_ITEMS_INFO_TENER_ENCUENTA_IMPL.compareTo(_idParametroSeleccionado) == 0) {
          _listaTemas = _iTema.findAllTipoNoEstado(IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_INFOR_TENER_CUENTA);
          String sufijo = "Ítems en información a tener en cuenta en la implementación";
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaTemas, TablaGenericaParametros.TIPO_PARAM_ITEMS_INFO_TENER_ENCUENTA_IMPL, sufijo);
          _popupParametroSeleccionado = INCLUDE_TEMAS;
          _tituloPopup = TITULO_POPUP_TEMAS + " - " + sufijo;
          _sizePopup = "50%";
          _tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_AVANCE_INFOR_TENER_CUENTA;
          inicializarDatosTemas();
        }

        if (PARAMETRO_ITEMS_INFORME_AVANCE_IMPL.compareTo(_idParametroSeleccionado) == 0) {
          _listaTemas = _iTema.findAllTipoNoEstado(IConstantes.DESTINO_TEMA_CU_PR_15_INFO_AVANCE_PROCESO);
          String sufijo = "Ítems en informe de avance de implementación";
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaTemas, TablaGenericaParametros.TIPO_PARAM_ITEMS_INFORME_AVANCE_IMPL, sufijo);
          _popupParametroSeleccionado = INCLUDE_TEMAS;
          _tituloPopup = TITULO_POPUP_TEMAS + " - " + sufijo;
          _sizePopup = "50%";
          _tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_INFO_AVANCE_PROCESO;
          inicializarDatosTemas();

        }

        if (PARAMETRO_ITEMS_TENER_CUENTA_INFORME_AVANCE_IMPL.compareTo(_idParametroSeleccionado) == 0) {
          _listaTemas = _iTema.findAllTipoNoEstado(IConstantes.DESTINO_TEMA_CU_PR_15_FINAL_INFOR_TENER_CUENTA);
          String sufijo = "Ítems en información a tener en cuenta informe de avance implementación";
          _tablaGenericaParametrosList = TablaGenericaParametros.obtenerTablaGenericaParametros(_listaTemas, TablaGenericaParametros.TIPO_PARAM_ITEMS_TENER_CUENTA_INFORME_AVANCE_IMPL, sufijo);
          _popupParametroSeleccionado = INCLUDE_TEMAS;
          _tituloPopup = TITULO_POPUP_TEMAS + " - " + sufijo;
          _sizePopup = "60%";
          _tipoTema = IConstantes.DESTINO_TEMA_CU_PR_15_FINAL_INFOR_TENER_CUENTA;
          inicializarDatosTemas();
        }

        _tablaGenericaParametrosListModel = new ListGenericDataModel<TablaGenericaParametros>(_tablaGenericaParametrosList);

      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   *
   * @return
   */
  public String inicializarPopup() {
    try {
      cargarListadoAreas();

      inicializarDatosAreas();
      inicializarDatosConstantes();
      inicializarDatosGrupoInvestigacion();
      inicializarDatosLineas();
      inicializarDatosTemas();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuAd02AdministrarParametrosSistema.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  private void cargarListadoAreas() throws JpaDinaeException {
    _listaAreaCienciaTecnologia = _iArea.findAllNoEstado();
    _listaAreasInvestigacionView = UtilidadesItem.getListaSel(_listaAreaCienciaTecnologia, "idAreaCienciaTecnologia", "nombre");
  }

  /**
   *
   * @return
   */
  public String inicializarDatosGrupoInvestigacion() {

    _codigoGrupo = null;
    _nombreGrupo = null;
    _fechaRegistro = null;
    _estadoGrupo = null;
    _modificarGrupo = false;
    _entidadGrupoInvestigacionSeleccionado = null;
    _parametrosSeleccionado = null;

    return null;
  }

  /**
   *
   * @return
   */
  public String inicializarDatosAreas() {

    _nombreArea = null;
    _estadoArea = null;
    _modificarArea = false;
    _entidadAreaSeleccionada = null;
    _parametrosSeleccionado = null;

    return null;
  }

  /**
   *
   * @return
   */
  public String inicializarDatosConstantes() {

    _valorConstante = null;
    _estadoConstante = null;
    _modificarConstante = false;
    _entidadConstantesSeleccionada = null;
    _parametrosSeleccionado = null;

    return null;
  }

  /**
   *
   * @return
   */
  public String inicializarDatosLineas() {

    _idAreaLinea = -1L;
    _nombreLinea = null;
    _estadoLinea = null;
    _modificarLineas = false;
    _entidadLineaSeleccionada = null;
    _parametrosSeleccionado = null;

    return null;
  }

  /**
   *
   * @return
   */
  public String inicializarDatosTemas() {
    _descripcionTema = null;
    _estadoTema = null;
    _modificarTema = false;
    _entidadTemaSeleccionada = null;
    _parametrosSeleccionado = null;

    return null;
  }

  /**
   *
   * @param actionEvent
   */
  public void guardarModificarGrupoInvestigacion(ActionEvent actionEvent) {
    RequestContext context = RequestContext.getCurrentInstance();
    boolean fieldValidation = false;
    try {
      if (_entidadGrupoInvestigacionSeleccionado == null || _entidadGrupoInvestigacionSeleccionado.getIdGrupoInvestigacion() == null) {
        _entidadGrupoInvestigacionSeleccionado = new GrupoInvestigacion();
      }

      if (_codigoGrupo == null || "".equals(_codigoGrupo.trim())) {
        adicionaMensajeError("El campo 'Código del grupo' es requerido");
        return;
      }

      if (_nombreGrupo == null || "".equals(_nombreGrupo.trim())) {
        adicionaMensajeError("El campo 'Nombre del grupo' es requerido");
        return;
      }

      if (_fechaRegistro == null) {
        adicionaMensajeError("El campo 'Fecha de registro en GrupLAC' es requerido");
        return;
      }

      if (_estadoGrupo == null || "".equals(_estadoGrupo.trim())) {
        adicionaMensajeError("El campo 'Estado' es requerido");
        return;
      }

      _entidadGrupoInvestigacionSeleccionado.setEstado(_estadoGrupo);
      _entidadGrupoInvestigacionSeleccionado.setCodigoGrupo(_codigoGrupo);
      _entidadGrupoInvestigacionSeleccionado.setDescripcion(_nombreGrupo);
      _entidadGrupoInvestigacionSeleccionado.setFechaRegistroGrupo(_fechaRegistro);

      _iGrupoInvestigacion.guardarGrupoInvestigacion(_entidadGrupoInvestigacionSeleccionado);
      cargarDatosParametroSistemaSeleccionado();
      fieldValidation = true;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuAd02AdministrarParametrosSistema.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }

    context.addCallbackParam("fieldValidation", fieldValidation);
  }

  /**
   *
   * @param actionEvent
   */
  public void guardarModificarArea(ActionEvent actionEvent) {
    RequestContext context = RequestContext.getCurrentInstance();
    boolean fieldValidation = false;
    try {
      if (_entidadAreaSeleccionada == null || _entidadAreaSeleccionada.getIdAreaCienciaTecnologia() == null) {
        _entidadAreaSeleccionada = new AreaCienciaTecnologia();
      }

      if (_nombreArea == null || "".equals(_nombreArea.trim())) {
        adicionaMensajeError("El campo 'Nombre área' es requerido");
        return;
      }

      if (_estadoArea == null || "".equals(_estadoArea.trim())) {
        adicionaMensajeError("El campo 'Estado' es requerido");
        return;
      }

      _entidadAreaSeleccionada.setEstado(_estadoArea);
      _entidadAreaSeleccionada.setNombre(_nombreArea);

      _iArea.saveOrUpdate(_entidadAreaSeleccionada);
      cargarDatosParametroSistemaSeleccionado();
      fieldValidation = true;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuAd02AdministrarParametrosSistema.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }

    context.addCallbackParam("fieldValidation", fieldValidation);
  }

  /**
   *
   * @param actionEvent
   */
  public void guardarModificarLinea(ActionEvent actionEvent) {
    RequestContext context = RequestContext.getCurrentInstance();
    boolean fieldValidation = false;

    try {
      if (_entidadLineaSeleccionada == null || _entidadLineaSeleccionada.getIdLinea() == null) {
        _entidadLineaSeleccionada = new Linea();
      }

      if (_idAreaLinea == null || _idAreaLinea.compareTo(-1L) == 0) {
        adicionaMensajeError("El campo 'Área' es obligatorio");
        return;
      }

      if (_nombreLinea == null || "".equals(_nombreLinea.trim())) {
        adicionaMensajeError("El campo 'Nombre de línea' es obligatorio");
        return;
      }

      if (_estadoLinea == null || _estadoLinea.equals(_nombreLinea.trim())) {
        adicionaMensajeError("El campo 'Estado' es obligatorio");
        return;
      }

      _entidadLineaSeleccionada.setEstado(_estadoLinea);
      _entidadLineaSeleccionada.setAreaCienciaTecnologia(new AreaCienciaTecnologia(_idAreaLinea));
      _entidadLineaSeleccionada.setNombre(_nombreLinea);

      _iLinea.saveOrUpdate(_entidadLineaSeleccionada);
      cargarDatosParametroSistemaSeleccionado();
      fieldValidation = true;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuAd02AdministrarParametrosSistema.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }

    context.addCallbackParam("fieldValidation", fieldValidation);
  }

  /**
   *
   * @param actionEvent
   */
  public void guardarModificarConstante(ActionEvent actionEvent) {
    RequestContext context = RequestContext.getCurrentInstance();
    boolean fieldValidation = false;
    try {
      if (_entidadConstantesSeleccionada == null || _entidadConstantesSeleccionada.getIdConstantes() == null) {
        _entidadConstantesSeleccionada = new Constantes();
        _entidadConstantesSeleccionada.setTipo(_tipoConstante);
      }

      if (_valorConstante == null || "".equals(_valorConstante.trim())) {
        adicionaMensajeError("El campo 'Valor' es obligatorio");
        return;
      }

      if (_estadoConstante == null) {
        adicionaMensajeError("El campo 'Estado' es obligatorio");
        return;
      }

      _entidadConstantesSeleccionada.setEstado(_estadoConstante);
      _entidadConstantesSeleccionada.setValor(_valorConstante);
      _entidadConstantesSeleccionada.setCodigo(_valorConstante);

      _iConstantes.saveOrUpdate(_entidadConstantesSeleccionada);
      cargarDatosParametroSistemaSeleccionado();
      fieldValidation = true;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuAd02AdministrarParametrosSistema.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }

    context.addCallbackParam("fieldValidation", fieldValidation);
  }

  /**
   *
   * @param actionEvent
   */
  public void guardarModificarTema(ActionEvent actionEvent) {
    RequestContext context = RequestContext.getCurrentInstance();
    boolean fieldValidation = false;
    try {
      if (_entidadTemaSeleccionada == null || _entidadTemaSeleccionada.getIdTema() == null) {
        _entidadTemaSeleccionada = new Tema();
        _entidadTemaSeleccionada.setTooltip(_descripcionTema);
        _entidadTemaSeleccionada.setTipoTema(_tipoTema);
      }

      if (_descripcionTema == null || "".equals(_descripcionTema.trim())) {
        adicionaMensajeError("El campo 'Descripción del tema' es obligatorio");
        return;
      }

      if (_estadoTema == null || "".equals(_estadoTema.trim())) {
        adicionaMensajeError("El campo 'Estado' es obligatorio");
        return;
      }

      _entidadTemaSeleccionada.setEstado(_estadoTema);
      _entidadTemaSeleccionada.setDescripcionTema(_descripcionTema);

      _iTema.guardarTema(_entidadTemaSeleccionada);
      cargarDatosParametroSistemaSeleccionado();
      fieldValidation = true;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuAd02AdministrarParametrosSistema.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }

    context.addCallbackParam("fieldValidation", fieldValidation);
  }

  /**
   *
   * @param e
   */
  public void cargarDatosParametroModificar(SelectEvent e) {
    try {
      if (_parametrosSeleccionado == null) {
        return;
      }

      TablaGenericaParametros itemSeleccionado = _parametrosSeleccionado;
      Object dataObj = itemSeleccionado.getObjetoDatos();

      if (dataObj instanceof GrupoInvestigacion) {
        _entidadGrupoInvestigacionSeleccionado = (GrupoInvestigacion) dataObj;
        _codigoGrupo = _entidadGrupoInvestigacionSeleccionado.getCodigoGrupo();
        _nombreGrupo = _entidadGrupoInvestigacionSeleccionado.getDescripcion();
        _fechaRegistro = _entidadGrupoInvestigacionSeleccionado.getFechaRegistroGrupo();
        _estadoGrupo = _entidadGrupoInvestigacionSeleccionado.getEstado();
        _modificarGrupo = true;

      }

      if (dataObj instanceof AreaCienciaTecnologia) {
        _entidadAreaSeleccionada = (AreaCienciaTecnologia) dataObj;
        _nombreArea = _entidadAreaSeleccionada.getNombre();
        _estadoArea = _entidadAreaSeleccionada.getEstado();
        _modificarArea = true;
      }

      if (dataObj instanceof Linea) {
        _entidadLineaSeleccionada = (Linea) dataObj;
        _idAreaLinea = _entidadLineaSeleccionada.getAreaCienciaTecnologia().getIdAreaCienciaTecnologia();
        _nombreLinea = _entidadLineaSeleccionada.getNombre();
        _estadoLinea = _entidadLineaSeleccionada.getEstado();
        _modificarLineas = true;
        cargarListadoAreas();
      }

      if (dataObj instanceof Constantes) {
        _entidadConstantesSeleccionada = (Constantes) dataObj;
        _valorConstante = _entidadConstantesSeleccionada.getValor();
        _estadoConstante = _entidadConstantesSeleccionada.getEstado();
        _modificarGrupo = true;
      }

      if (dataObj instanceof Tema) {
        _entidadTemaSeleccionada = (Tema) dataObj;
        _descripcionTema = _entidadTemaSeleccionada.getDescripcionTema();
        _estadoTema = _entidadTemaSeleccionada.getEstado();
        _modificarTema = true;
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @param event
   */
  public void noSeleccionParametro(UnselectEvent event) {
    _parametrosSeleccionado = null;
  }

  /**
   *
   * @return
   */
  public List<SelectItem> getListaTipoParametrosView() {
    return _listaTipoParametrosView;
  }

  /**
   *
   * @param _listaTipoParametrosView
   */
  public void setListaTipoParametrosView(List<SelectItem> _listaTipoParametrosView) {
    this._listaTipoParametrosView = _listaTipoParametrosView;
  }

  /**
   *
   * @return
   */
  public List<SelectItem> getListaAreasInvestigacionView() {
    return _listaAreasInvestigacionView;
  }

  /**
   *
   * @param _listaAreasInvestigacionView
   */
  public void setListaAreasInvestigacionView(List<SelectItem> _listaAreasInvestigacionView) {
    this._listaAreasInvestigacionView = _listaAreasInvestigacionView;
  }

  /**
   *
   * @return
   */
  public List<AreaCienciaTecnologia> getListaAreaCienciaTecnologia() {
    return _listaAreaCienciaTecnologia;
  }

  /**
   *
   * @param _listaAreaCienciaTecnologia
   */
  public void setListaAreaCienciaTecnologia(List<AreaCienciaTecnologia> _listaAreaCienciaTecnologia) {
    this._listaAreaCienciaTecnologia = _listaAreaCienciaTecnologia;
  }

  /**
   *
   * @return
   */
  public List<Linea> getListaLineas() {
    return _listaLineas;
  }

  /**
   *
   * @param _listaLineas
   */
  public void setListaLineas(List<Linea> _listaLineas) {
    this._listaLineas = _listaLineas;
  }

  /**
   *
   * @return
   */
  public List<Tema> getListaTemas() {
    return _listaTemas;
  }

  /**
   *
   * @param _listaTemas
   */
  public void setListaTemas(List<Tema> _listaTemas) {
    this._listaTemas = _listaTemas;
  }

  /**
   *
   * @return
   */
  public List<Constantes> getListaTipoParametrosSistema() {
    return _listaTipoParametrosSistema;
  }

  /**
   *
   * @param _listaTipoParametrosSistema
   */
  public void setListaTipoParametrosSistema(List<Constantes> _listaTipoParametrosSistema) {
    this._listaTipoParametrosSistema = _listaTipoParametrosSistema;
  }

  /**
   *
   * @return
   */
  public List<GrupoInvestigacion> getListaGrupoInvestigacion() {
    return _listaGrupoInvestigacion;
  }

  /**
   *
   * @param _listaGrupoInvestigacion
   */
  public void setListaGrupoInvestigacion(List<GrupoInvestigacion> _listaGrupoInvestigacion) {
    this._listaGrupoInvestigacion = _listaGrupoInvestigacion;
  }

  /**
   *
   * @return
   */
  public Long getIdParametroSeleccionado() {
    return _idParametroSeleccionado;
  }

  /**
   *
   * @param _idParametroSeleccionado
   */
  public void setIdParametroSeleccionado(Long _idParametroSeleccionado) {
    this._idParametroSeleccionado = _idParametroSeleccionado;
  }

  /**
   *
   * @return
   */
  public List<Constantes> getListaParametrosTipoConstante() {
    return _listaParametrosTipoConstante;
  }

  /**
   *
   * @param _listaParametrosTipoConstante
   */
  public void setListaParametrosTipoConstante(List<Constantes> _listaParametrosTipoConstante) {
    this._listaParametrosTipoConstante = _listaParametrosTipoConstante;
  }

  /**
   *
   * @return
   */
  public List<TablaGenericaParametros> getTablaGenericaParametrosList() {
    return _tablaGenericaParametrosList;
  }

  /**
   *
   * @param _tablaGenericaParametrosList
   */
  public void setTablaGenericaParametrosList(List<TablaGenericaParametros> _tablaGenericaParametrosList) {
    this._tablaGenericaParametrosList = _tablaGenericaParametrosList;
  }

  /**
   *
   * @return
   */
  public ListGenericDataModel<TablaGenericaParametros> getTablaGenericaParametrosListModel() {
    return _tablaGenericaParametrosListModel;
  }

  /**
   *
   * @param _tablaGenericaParametrosListModel
   */
  public void setTablaGenericaParametrosListModel(ListGenericDataModel<TablaGenericaParametros> _tablaGenericaParametrosListModel) {
    this._tablaGenericaParametrosListModel = _tablaGenericaParametrosListModel;
  }

  /**
   *
   * @return
   */
  public TablaGenericaParametros getParametrosSeleccionado() {
    return _parametrosSeleccionado;
  }

  /**
   *
   * @param _parametrosSeleccionado
   */
  public void setParametrosSeleccionado(TablaGenericaParametros _parametrosSeleccionado) {
    this._parametrosSeleccionado = _parametrosSeleccionado;
  }

  /**
   *
   * @return
   */
  public String getCodigoGrupo() {
    return _codigoGrupo;
  }

  /**
   *
   * @param _codigoGrupo
   */
  public void setCodigoGrupo(String _codigoGrupo) {
    this._codigoGrupo = _codigoGrupo;
  }

  /**
   *
   * @return
   */
  public String getNombreGrupo() {
    return _nombreGrupo;
  }

  /**
   *
   * @param _nombreGrupo
   */
  public void setNombreGrupo(String _nombreGrupo) {
    this._nombreGrupo = _nombreGrupo;
  }

  /**
   *
   * @return
   */
  public Date getFechaRegistro() {
    return _fechaRegistro;
  }

  /**
   *
   * @param _fechaRegistro
   */
  public void setFechaRegistro(Date _fechaRegistro) {
    this._fechaRegistro = _fechaRegistro;
  }

  /**
   *
   * @return
   */
  public String getEstadoGrupo() {
    return _estadoGrupo;
  }

  /**
   *
   * @param _estadoGrupo
   */
  public void setEstadoGrupo(String _estadoGrupo) {
    this._estadoGrupo = _estadoGrupo;
  }

  /**
   *
   * @return
   */
  public boolean isModificarGrupo() {
    return _modificarGrupo;
  }

  /**
   *
   * @param _modificarGrupo
   */
  public void setModificarGrupo(boolean _modificarGrupo) {
    this._modificarGrupo = _modificarGrupo;
  }

  /**
   *
   * @return
   */
  public String getPopupParametroSeleccionado() {
    return _popupParametroSeleccionado;
  }

  /**
   *
   * @param _popupParametroSeleccionado
   */
  public void setPopupParametroSeleccionado(String _popupParametroSeleccionado) {
    this._popupParametroSeleccionado = _popupParametroSeleccionado;
  }

  /**
   *
   * @return
   */
  public String getNombreArea() {
    return _nombreArea;
  }

  /**
   *
   * @param _nombreArea
   */
  public void setNombreArea(String _nombreArea) {
    this._nombreArea = _nombreArea;
  }

  /**
   *
   * @return
   */
  public String getEstadoArea() {
    return _estadoArea;
  }

  /**
   *
   * @param _estadoArea
   */
  public void setEstadoArea(String _estadoArea) {
    this._estadoArea = _estadoArea;
  }

  /**
   *
   * @return
   */
  public boolean isModificarArea() {
    return _modificarArea;
  }

  /**
   *
   * @param _modificarArea
   */
  public void setModificarArea(boolean _modificarArea) {
    this._modificarArea = _modificarArea;
  }

  /**
   *
   * @return
   */
  public String getValorConstante() {
    return _valorConstante;
  }

  /**
   *
   * @param _valorConstante
   */
  public void setValorConstante(String _valorConstante) {
    this._valorConstante = _valorConstante;
  }

  /**
   *
   * @return
   */
  public String getEstadoConstante() {
    return _estadoConstante;
  }

  /**
   *
   * @param _estadoConstante
   */
  public void setEstadoConstante(String _estadoConstante) {
    this._estadoConstante = _estadoConstante;
  }

  /**
   *
   * @return
   */
  public boolean isModificarConstante() {
    return _modificarConstante;
  }

  /**
   *
   * @param _modificarConstante
   */
  public void setModificarConstante(boolean _modificarConstante) {
    this._modificarConstante = _modificarConstante;
  }

  /**
   *
   * @return
   */
  public String getTituloPopup() {
    return _tituloPopup;
  }

  /**
   *
   * @param _tituloPopup
   */
  public void setTituloPopup(String _tituloPopup) {
    this._tituloPopup = _tituloPopup;
  }

  /**
   *
   * @return
   */
  public String getSizePopup() {
    return _sizePopup;
  }

  /**
   *
   * @param _sizePopup
   */
  public void setSizePopup(String _sizePopup) {
    this._sizePopup = _sizePopup;
  }

  /**
   *
   * @return
   */
  public Long getIdAreaLinea() {
    return _idAreaLinea;
  }

  /**
   *
   * @param _idAreaLinea
   */
  public void setIdAreaLinea(Long _idAreaLinea) {
    this._idAreaLinea = _idAreaLinea;
  }

  /**
   *
   * @return
   */
  public String getNombreLinea() {
    return _nombreLinea;
  }

  /**
   *
   * @param _nombreLinea
   */
  public void setNombreLinea(String _nombreLinea) {
    this._nombreLinea = _nombreLinea;
  }

  /**
   *
   * @return
   */
  public String getEstadoLinea() {
    return _estadoLinea;
  }

  /**
   *
   * @param _estadoLinea
   */
  public void setEstadoLinea(String _estadoLinea) {
    this._estadoLinea = _estadoLinea;
  }

  /**
   *
   * @return
   */
  public boolean isModificarLineas() {
    return _modificarLineas;
  }

  /**
   *
   * @param _modificarLineas
   */
  public void setModificarLineas(boolean _modificarLineas) {
    this._modificarLineas = _modificarLineas;
  }

  /**
   *
   * @return
   */
  public String getDescripcionTema() {
    return _descripcionTema;
  }

  /**
   *
   * @param _descripcionTema
   */
  public void setDescripcionTema(String _descripcionTema) {
    this._descripcionTema = _descripcionTema;
  }

  /**
   *
   * @return
   */
  public String getEstadoTema() {
    return _estadoTema;
  }

  /**
   *
   * @param _estadoTema
   */
  public void setEstadoTema(String _estadoTema) {
    this._estadoTema = _estadoTema;
  }

  /**
   *
   * @return
   */
  public boolean isModificarTema() {
    return _modificarTema;
  }

  /**
   *
   * @param _modificarTema
   */
  public void setModificarTema(boolean _modificarTema) {
    this._modificarTema = _modificarTema;
  }

  /**
   *
   * @return
   */
  public String getTipoTema() {
    return _tipoTema;
  }

  /**
   *
   * @param _tipoTema
   */
  public void setTipoTema(String _tipoTema) {
    this._tipoTema = _tipoTema;
  }

  /**
   *
   * @return
   */
  public String getTipoConstante() {
    return _tipoConstante;
  }

  /**
   *
   * @param _tipoConstante
   */
  public void setTipoConstante(String _tipoConstante) {
    this._tipoConstante = _tipoConstante;
  }

  /**
   *
   * @return
   */
  public Tema getEntidadTemaSeleccionada() {
    return _entidadTemaSeleccionada;
  }

  /**
   *
   * @param _entidadTemaSeleccionada
   */
  public void setEntidadTemaSeleccionada(Tema _entidadTemaSeleccionada) {
    this._entidadTemaSeleccionada = _entidadTemaSeleccionada;
  }

  /**
   *
   * @return
   */
  public GrupoInvestigacion getEntidadGrupoInvestigacionSeleccionado() {
    return _entidadGrupoInvestigacionSeleccionado;
  }

  /**
   *
   * @param _entidadGrupoInvestigacionSeleccionado
   */
  public void setEntidadGrupoInvestigacionSeleccionado(GrupoInvestigacion _entidadGrupoInvestigacionSeleccionado) {
    this._entidadGrupoInvestigacionSeleccionado = _entidadGrupoInvestigacionSeleccionado;
  }

  /**
   *
   * @return
   */
  public Constantes getEntidadConstantesSeleccionada() {
    return _entidadConstantesSeleccionada;
  }

  /**
   *
   * @param _entidadConstantesSeleccionada
   */
  public void setEntidadConstantesSeleccionada(Constantes _entidadConstantesSeleccionada) {
    this._entidadConstantesSeleccionada = _entidadConstantesSeleccionada;
  }

  /**
   *
   * @return
   */
  public AreaCienciaTecnologia getEntidadAreaSeleccionada() {
    return _entidadAreaSeleccionada;
  }

  /**
   *
   * @param _entidadAreaSeleccionada
   */
  public void setEntidadAreaSeleccionada(AreaCienciaTecnologia _entidadAreaSeleccionada) {
    this._entidadAreaSeleccionada = _entidadAreaSeleccionada;
  }

  /**
   *
   * @return
   */
  public Linea getEntidadLineaSeleccionada() {
    return _entidadLineaSeleccionada;
  }

  /**
   *
   * @param _entidadLineaSeleccionada
   */
  public void setEntidadLineaSeleccionada(Linea _entidadLineaSeleccionada) {
    this._entidadLineaSeleccionada = _entidadLineaSeleccionada;
  }

}
