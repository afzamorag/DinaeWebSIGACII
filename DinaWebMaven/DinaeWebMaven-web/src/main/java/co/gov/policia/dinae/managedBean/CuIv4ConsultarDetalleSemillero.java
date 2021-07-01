/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CronogramaSemilleroDTO;
import co.gov.policia.dinae.dto.EstimulosSemilleroDTO;
import co.gov.policia.dinae.dto.EventosCapacitacionSemilleroDTO;
import co.gov.policia.dinae.dto.HorarioReunionesDTO;
import co.gov.policia.dinae.dto.RecursoHumanoSemilleroDTO;
import co.gov.policia.dinae.dto.SemilleroInvestigacionDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICronogramaSemilleroLocal;
import co.gov.policia.dinae.interfaces.IEventosCapacitacionSemilleroLocal;
import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.interfaces.IOtrosEstimulosSemilleroLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IRecursoHumanoSemilleroLocal;
import co.gov.policia.dinae.interfaces.IReunionesSemilleroLocal;
import co.gov.policia.dinae.interfaces.ISemilleroInvestigacionLocal;
import co.gov.policia.dinae.interfaces.ISemilleroProyectoLocal;
import co.gov.policia.dinae.interfaces.ISemillerosImplementacionLocal;
import co.gov.policia.dinae.interfaces.ITalentoEstimuloSemilleroLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.CronogramaSemillero;
import co.gov.policia.dinae.modelo.EventosCapacitacionSemillero;
import co.gov.policia.dinae.modelo.OtrosEstimulosSemillero;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.RecursoHumanoSemillero;
import co.gov.policia.dinae.modelo.ReunionesSemillero;
import co.gov.policia.dinae.modelo.SemilleroInvestigacion;
import co.gov.policia.dinae.modelo.SemilleroProyecto;
import co.gov.policia.dinae.modelo.SemillerosImplementacion;
import co.gov.policia.dinae.modelo.TalentoEstimuloSemillero;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author cguzman
 */
@Named(value = "cuIv4ConsultarDetalleSemilleroFaces")
@SessionScoped
public class CuIv4ConsultarDetalleSemillero extends JSFUtils implements Serializable {

  @EJB
  private ISemilleroInvestigacionLocal _iSemilleroInvestigacion;

  @EJB
  private IUnidadPolicialLocal _iUnidadPolicialLocal;

  @EJB
  private IRecursoHumanoSemilleroLocal _iRecursoHumanoSemillero;

  @EJB
  private ISemilleroProyectoLocal _iSemilleroProyecto;

  @EJB
  private ISemillerosImplementacionLocal _iSemilleroImpl;

  @EJB
  private IEventosCapacitacionSemilleroLocal _iEventosSemilleros;

  @EJB
  private ILugarGeograficoLocal iCiudad;

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IReunionesSemilleroLocal _iReunionesSemillero;

  @EJB
  private IOtrosEstimulosSemilleroLocal _iEstimulosSemillero;

  @EJB
  private ITalentoEstimuloSemilleroLocal _iTalentoEstimuloSemillero;

  @EJB
  private ICronogramaSemilleroLocal _iCronogramaSemillero;

  @EJB
  private IProyectoLocal _iProyectoLocal;

  private List<SemilleroInvestigacionDTO> _listaSemillerosBusqueda;

  private ListGenericDataModel<SemilleroInvestigacionDTO> _listaSemillerosBusquedaModel;

  private SemilleroInvestigacionDTO semilleroSeleccionado;

  private List<UnidadPolicialDTO> _listaUnidadesPoliciales;

  private List<Integer> _listaAniosCreacion;

  private List<RecursoHumanoSemilleroDTO> _listaRecursoHumanoSemillero;

  private List<SemilleroProyecto> _listaSemillerosProyecto;

  private List<SemillerosImplementacion> _listaSemillerosImplementacion;

  private List<HorarioReunionesDTO> _listaHorarioReuniones;

  private List<EventosCapacitacionSemilleroDTO> _listaEventosSemillero;

  private List<EstimulosSemilleroDTO> _listaEstimulosSemillero;

  private List<CronogramaSemillero> _listaCronogramaSemillero;

  private List<CronogramaSemilleroDTO> _listaCronogramaSemilleroDTO;

  private Integer _anioCreacion;

  private Long _idUnidadPolicial;

  private String _palabrasClaveNombre;

  private String _trabajoIndependiente;

  private String _estadoSemillero;

  private UnidadPolicial _unidadPolicial;

  private SemilleroInvestigacion _semilleroInvestigacion;

  private Integer _tabIndex;

  private Object _entidadOrigen;

  private boolean _esCuPr02;

  /**
   *
   * @return
   */
  public String initReturnCU() {

    String navega = null;

    try {

      _tabIndex = 0;

      cargarListaUnidadesPoliciales();
      cargarAniosCreacion();

      inicializarCamposFiltros();

      navega = navigationFaces.redirectCuIv4ConsultarDetalleSemilleroRedirect();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;
  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {
    try {
      _tabIndex = 0;

      if (event == null || event.getTab() == null) {
        return;
      }

      if ("detallesBasicosSemillero".equals(event.getTab().getId())) {
        _tabIndex = 0;

      } else if ("detallesTalentoHumanoSemillero".equals(event.getTab().getId())) {

        _tabIndex = 1;
        cargarDetallesRecursoHumanoSemillero();

      } else if ("detallesInvestigacionesSemillero".equals(event.getTab().getId())) {

        _tabIndex = 2;
        cargarListaInvestigacionesSemillero();
        cargarListaImplementacionesSemillero();

      } else if ("detallesEventosSemillero".equals(event.getTab().getId())) {

        _tabIndex = 3;
        cargarListaHorariosReuniones();
        cargarListaEventoSemillero();

      } else if ("detallesEstimulosSemilleros".equals(event.getTab().getId())) {

        _tabIndex = 4;
        cargarListaEstimulosSemillero();
      }
    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @param idSemillero
   * @return
   */
  public String invocacionCuPr02(Long idSemillero) {
    String navega = null;
    try {
      if (idSemillero == null) {
        return navega;
      }

      semilleroSeleccionado = _iSemilleroInvestigacion.findByIdSemillero(idSemillero);
      _semilleroInvestigacion = _iSemilleroInvestigacion.obtenerSemilleroInvestigacionPorId(idSemillero);
      _unidadPolicial = _semilleroInvestigacion.getUnidadPolicial();

      _tabIndex = 0;
      _esCuPr02 = true;

      navega = "/pages/secured/cu_iv_4/detallesSemilleroInvestigacion.xhtml?faces-redirect=true";

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaUnidadesPoliciales() throws JpaDinaeException {

    _listaUnidadesPoliciales = _iUnidadPolicialLocal.getAllUnidadesPolicialesActivasDTO();
    if (_listaUnidadesPoliciales == null || _listaUnidadesPoliciales.isEmpty()) {
      _listaUnidadesPoliciales = new ArrayList<UnidadPolicialDTO>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarAniosCreacion() throws JpaDinaeException {
    List<SemilleroInvestigacionDTO> _allSemilleros = _iSemilleroInvestigacion.findAllDTO();
    SimpleDateFormat formato = new SimpleDateFormat("yyyy");

    Set<Integer> anios = new HashSet<Integer>();

    if (!_allSemilleros.isEmpty()) {

      for (SemilleroInvestigacionDTO semillero : _allSemilleros) {
        Integer anio = Integer.parseInt(formato.format(semillero.getFechaRegistro()));
        anios.add(anio);
      }
    }
    _listaAniosCreacion = new ArrayList<Integer>();
    _listaAniosCreacion.addAll(anios);
  }

  /**
   *
   * @return
   */
  public String buscarSemillerosCriterios() {

    try {
      Map<String, Object> criterios = new HashMap<String, Object>();

      if (_idUnidadPolicial != null && _idUnidadPolicial.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_SEMILLERO_UNIDAD_POLICIAL, _idUnidadPolicial);
      }

      if (_palabrasClaveNombre != null && !"".equals(_palabrasClaveNombre)) {
        criterios.put(IConstantes.CRITERIO_SEMILLERO_PALABRA_CLAVE, _palabrasClaveNombre);
      }

      if (_estadoSemillero != null && !"-1".equals(_estadoSemillero)) {
        criterios.put(IConstantes.CRITERIO_SEMILLERO_ESTADO_SEMILLERO, _estadoSemillero);
      }

      if (_anioCreacion != null && _anioCreacion.compareTo(-1) != 0) {
        criterios.put(IConstantes.CRITERIO_SEMILLERO_ANIO_CREACION, _anioCreacion);
      }

      if (_trabajoIndependiente != null) {
        criterios.put(IConstantes.CRITERIO_SEMILLERO_TRABAJO_INDEPENDIENTE, _trabajoIndependiente);
      }

      _listaSemillerosBusqueda = _iSemilleroInvestigacion.buscarSemilleroCriterios(criterios);

      _listaSemillerosBusquedaModel = new ListGenericDataModel<SemilleroInvestigacionDTO>(_listaSemillerosBusqueda);

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public String inicializarCamposFiltros() {

    _idUnidadPolicial = null;
    _palabrasClaveNombre = null;
    _estadoSemillero = "-1";
    _anioCreacion = -1;
    _trabajoIndependiente = null;

    _listaSemillerosBusqueda = new ArrayList<SemilleroInvestigacionDTO>();
    _listaSemillerosBusquedaModel = new ListGenericDataModel<SemilleroInvestigacionDTO>(_listaSemillerosBusqueda);

    _tabIndex = 0;

    return null;
  }

  /**
   *
   * @param e
   */
  public void cargarDetallesSemillero(SelectEvent e) {
    try {
      if (semilleroSeleccionado == null) {
        return;
      }

      _semilleroInvestigacion = _iSemilleroInvestigacion.obtenerSemilleroInvestigacionPorId(semilleroSeleccionado.getIdSemillero());
      _unidadPolicial = _semilleroInvestigacion.getUnidadPolicial();

      navigationFaces.redirectFacesRedirectCuIv4_1ConsultarDetalleSemillero();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param event
   */
  public void noSeleccionDetalles(UnselectEvent event) {
    inicializarCamposFiltros();
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarDetallesRecursoHumanoSemillero() throws JpaDinaeException {
    List<RecursoHumanoSemillero> recursos = _iRecursoHumanoSemillero.findAllBySemillero(semilleroSeleccionado.getIdSemillero());
    _listaRecursoHumanoSemillero = new ArrayList<RecursoHumanoSemilleroDTO>(recursos.size());

    for (RecursoHumanoSemillero recurso : recursos) {
      _listaRecursoHumanoSemillero.add(new RecursoHumanoSemilleroDTO(recurso));
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaInvestigacionesSemillero() throws JpaDinaeException {
    _listaSemillerosProyecto = _iSemilleroProyecto.findAllBySemilleroInvestigacion(semilleroSeleccionado.getIdSemillero());
    if (_listaSemillerosProyecto == null || _listaSemillerosProyecto.isEmpty()) {
      _listaSemillerosProyecto = new ArrayList<SemilleroProyecto>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaImplementacionesSemillero() throws JpaDinaeException {
    _listaSemillerosImplementacion = _iSemilleroImpl.findAllBySemilleroInvestigacion(semilleroSeleccionado.getIdSemillero());
    if (_listaSemillerosImplementacion == null || _listaSemillerosImplementacion.isEmpty()) {
      _listaSemillerosImplementacion = new ArrayList<SemillerosImplementacion>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEventoSemillero() throws JpaDinaeException {
    List<EventosCapacitacionSemillero> eventosSemillero = _iEventosSemilleros.findByIdSemillero(semilleroSeleccionado.getIdSemillero());
    _listaEventosSemillero = new ArrayList<EventosCapacitacionSemilleroDTO>();

    if (!eventosSemillero.isEmpty()) {
      for (EventosCapacitacionSemillero evento : eventosSemillero) {
        _listaEventosSemillero.add(new EventosCapacitacionSemilleroDTO(evento));
      }
    }
  }

  /**
   *
   * @param idCiudad
   * @return
   */
  public String tomarNombreCiudad(Long idCiudad) {
    if (idCiudad != null) {
      try {
        return iCiudad.findMunicipioByCodMunicipio(idCiudad).getDescMunicipio();

      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return null;
  }

  /**
   *
   * @param evento
   * @return
   */
  public String calcularMostrarAsistentesEventos(EventosCapacitacionSemilleroDTO evento) {

    if (evento != null) {

      Integer numeroOficiales = evento.getNumeroOficiales().intValue();
      Integer numeroSuboficiales = evento.getNumeroSuboficiales() == null ? 0 : evento.getNumeroSuboficiales().intValue();
      Integer numeroEstudiantes = evento.getNumeroEstudiantes() == null ? 0 : evento.getNumeroEstudiantes().intValue();
      Integer numeroNoUniformados = evento.getNumeroNoUniformados() == null ? 0 : evento.getNumeroNoUniformados().intValue();

      return String.valueOf((numeroOficiales + numeroSuboficiales + numeroEstudiantes + numeroNoUniformados));
    }

    return null;
  }

  /**
   *
   * @param archivoEvidencia
   * @param archivoEvidenciaOrigen
   * @return
   */
  public StreamedContent descargarArchivoEvidenciaFotoEventoTabla(String archivoEvidencia, String archivoEvidenciaOrigen) {
    try {
      if (archivoEvidencia != null && archivoEvidenciaOrigen != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + archivoEvidenciaOrigen;

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, archivoEvidencia);
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-IV-04 Consultar Semilleros (descargarArchivoEvidenciaFoto)", e);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaHorariosReuniones() throws JpaDinaeException, ParseException {

    List<Constantes> listaTipoDiasSemana = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_DIAS_SEMANA);
    final SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
    _listaHorarioReuniones = new ArrayList<HorarioReunionesDTO>();

    if (!listaTipoDiasSemana.isEmpty()) {

      for (Constantes dias : listaTipoDiasSemana) {
        HorarioReunionesDTO horario = new HorarioReunionesDTO(dias);
        _listaHorarioReuniones.add(horario);
      }
    }

    List<ReunionesSemillero> reunionesSemilleros = _iReunionesSemillero.findBySemillero(semilleroSeleccionado.getIdSemillero());
    if (!reunionesSemilleros.isEmpty()) {

      for (HorarioReunionesDTO horario : _listaHorarioReuniones) {
        for (ReunionesSemillero reunion : reunionesSemilleros) {
          if (horario.getIdDiaSemana().compareTo(reunion.getDia().getIdConstantes()) == 0) {

            Date fechaInicial = formato.parse(reunion.getHoraInicio());
            Date fechaFinal = formato.parse(reunion.getHoraFin());

            horario.setHoraInicio(fechaInicial);
            horario.setHoraFin(fechaFinal);

            horario.setIdHorarioReunion(reunion.getIdReunionSemillero());
          }
        }
      }
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEstimulosSemillero() throws JpaDinaeException {

    List<OtrosEstimulosSemillero> estimulosSemillero = _iEstimulosSemillero.findBySemilleroInvestigacion(semilleroSeleccionado.getIdSemillero());
    _listaEstimulosSemillero = new ArrayList<EstimulosSemilleroDTO>();

    if (!estimulosSemillero.isEmpty()) {
      for (OtrosEstimulosSemillero otros : estimulosSemillero) {
        List<TalentoEstimuloSemillero> talentos = _iTalentoEstimuloSemillero.findByOtrosEstimulosSemillero(otros.getIdOtrosEstimulosSemillero());
        EstimulosSemilleroDTO item = new EstimulosSemilleroDTO(otros, talentos);
        _listaEstimulosSemillero.add(item);
      }
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaCronogramaSemillero() throws JpaDinaeException {

    _listaCronogramaSemillero = new ArrayList<CronogramaSemillero>();

    if (_entidadOrigen instanceof SemilleroProyecto) {
      SemilleroProyecto semilleroProyecto = (SemilleroProyecto) _entidadOrigen;
      _listaCronogramaSemillero = _iCronogramaSemillero.findAllActivitiesBySemilleroProyecto(semilleroProyecto.getIdSemilleroProyecto());
    } else if (_entidadOrigen instanceof SemillerosImplementacion) {
      SemillerosImplementacion semilleroImpl = (SemillerosImplementacion) _entidadOrigen;
      _listaCronogramaSemillero = _iCronogramaSemillero.findAllActivitiesBySemillerosImplementacion(semilleroImpl.getIdSemilleroImplemetacion());
    } else {
      _listaCronogramaSemillero = _iCronogramaSemillero.findAllActivitiesBySemillero(semilleroSeleccionado.getIdSemillero());

    }

    _listaCronogramaSemilleroDTO = new ArrayList<CronogramaSemilleroDTO>(_listaCronogramaSemillero.size());

    for (CronogramaSemillero crono : _listaCronogramaSemillero) {
      CronogramaSemilleroDTO item = new CronogramaSemilleroDTO(crono);
      _listaCronogramaSemilleroDTO.add(item);
    }
  }

  /**
   *
   * @return
   */
  public String irCronogramaTrabajoSemillero() {
    try {
      cargarListaCronogramaSemillero();
      return "/pages/secured/cu_iv_4/detallesCronogramaSemillero.xhtml?faces-redirect=true";
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv4ConsultarDetalleSemillero.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String regresarTab3() {

    String navega = null;

    try {
      cargarListaInvestigacionesSemillero();
      cargarListaImplementacionesSemillero();
      _tabIndex = 2;
      navega = "/pages/secured/cu_iv_4/detallesSemilleroInvestigacion.xhtml?faces-redirect=true";
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv3GestionarSemillerosInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;
  }

  public String regresar() {

    if (!_esCuPr02) {
      inicializarCamposFiltros();
      return navigationFaces.redirectCuIv4ConsultarDetalleSemilleroRedirect();
    } else {
      return navigationFaces.redirectCuPr1ProyectoFacesRedirect();
    }
  }

  public List<SemilleroInvestigacionDTO> getListaSemillerosBusqueda() {
    return _listaSemillerosBusqueda;
  }

  public void setListaSemillerosBusqueda(List<SemilleroInvestigacionDTO> _listaSemillerosBusqueda) {
    this._listaSemillerosBusqueda = _listaSemillerosBusqueda;
  }

  public ListGenericDataModel<SemilleroInvestigacionDTO> getListaSemillerosBusquedaModel() {
    return _listaSemillerosBusquedaModel;
  }

  public void setListaSemillerosBusquedaModel(ListGenericDataModel<SemilleroInvestigacionDTO> _listaSemillerosBusquedaModel) {
    this._listaSemillerosBusquedaModel = _listaSemillerosBusquedaModel;
  }

  public List<UnidadPolicialDTO> getListaUnidadesPoliciales() {
    return _listaUnidadesPoliciales;
  }

  public void setListaUnidadesPoliciales(List<UnidadPolicialDTO> _listaUnidadesPoliciales) {
    this._listaUnidadesPoliciales = _listaUnidadesPoliciales;
  }

  public List<Integer> getListaAniosCreacion() {
    return _listaAniosCreacion;
  }

  public void setListaAniosCreacion(List<Integer> _listaAniosCreacion) {
    this._listaAniosCreacion = _listaAniosCreacion;
  }

  public Integer getAnioCreacion() {
    return _anioCreacion;
  }

  public void setAnioCreacion(Integer _anioCreacion) {
    this._anioCreacion = _anioCreacion;
  }

  public Long getIdUnidadPolicial() {
    return _idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long _idUnidadPolicial) {
    this._idUnidadPolicial = _idUnidadPolicial;
  }

  public String getPalabrasClaveNombre() {
    return _palabrasClaveNombre;
  }

  public void setPalabrasClaveNombre(String _palabrasClaveNombre) {
    this._palabrasClaveNombre = _palabrasClaveNombre;
  }

  public String getTrabajoIndependiente() {
    return _trabajoIndependiente;
  }

  public void setTrabajoIndependiente(String _trabajoIndependiente) {
    this._trabajoIndependiente = _trabajoIndependiente;
  }

  public String getEstadoSemillero() {
    return _estadoSemillero;
  }

  public void setEstadoSemillero(String _estadoSemillero) {
    this._estadoSemillero = _estadoSemillero;
  }

  public SemilleroInvestigacionDTO getSemilleroSeleccionado() {
    return semilleroSeleccionado;
  }

  public void setSemilleroSeleccionado(SemilleroInvestigacionDTO semilleroSeleccionado) {
    this.semilleroSeleccionado = semilleroSeleccionado;
  }

  public UnidadPolicial getUnidadPolicial() {
    return _unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial _unidadPolicial) {
    this._unidadPolicial = _unidadPolicial;
  }

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return _semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion _semilleroInvestigacion) {
    this._semilleroInvestigacion = _semilleroInvestigacion;
  }

  public Integer getTabIndex() {
    return _tabIndex;
  }

  public void setTabIndex(Integer _tabIndex) {
    this._tabIndex = _tabIndex;
  }

  public List<RecursoHumanoSemilleroDTO> getListaRecursoHumanoSemillero() {
    return _listaRecursoHumanoSemillero;
  }

  public void setListaRecursoHumanoSemillero(List<RecursoHumanoSemilleroDTO> _listaRecursoHumanoSemillero) {
    this._listaRecursoHumanoSemillero = _listaRecursoHumanoSemillero;
  }

  public List<SemilleroProyecto> getListaSemillerosProyecto() {
    return _listaSemillerosProyecto;
  }

  public void setListaSemillerosProyecto(List<SemilleroProyecto> _listaSemillerosProyecto) {
    this._listaSemillerosProyecto = _listaSemillerosProyecto;
  }

  public List<SemillerosImplementacion> getListaSemillerosImplementacion() {
    return _listaSemillerosImplementacion;
  }

  public void setListaSemillerosImplementacion(List<SemillerosImplementacion> _listaSemillerosImplementacion) {
    this._listaSemillerosImplementacion = _listaSemillerosImplementacion;
  }

  public Object getEntidadOrigen() {
    return _entidadOrigen;
  }

  public void setEntidadOrigen(Object _entidadOrigen) {
    this._entidadOrigen = _entidadOrigen;
  }

  public List<HorarioReunionesDTO> getListaHorarioReuniones() {
    return _listaHorarioReuniones;
  }

  public void setListaHorarioReuniones(List<HorarioReunionesDTO> _listaHorarioReuniones) {
    this._listaHorarioReuniones = _listaHorarioReuniones;
  }

  public List<EventosCapacitacionSemilleroDTO> getListaEventosSemillero() {
    return _listaEventosSemillero;
  }

  public void setListaEventosSemillero(List<EventosCapacitacionSemilleroDTO> _listaEventosSemillero) {
    this._listaEventosSemillero = _listaEventosSemillero;
  }

  public List<EstimulosSemilleroDTO> getListaEstimulosSemillero() {
    return _listaEstimulosSemillero;
  }

  public void setListaEstimulosSemillero(List<EstimulosSemilleroDTO> _listaEstimulosSemillero) {
    this._listaEstimulosSemillero = _listaEstimulosSemillero;
  }

  public List<CronogramaSemillero> getListaCronogramaSemillero() {
    return _listaCronogramaSemillero;
  }

  public void setListaCronogramaSemillero(List<CronogramaSemillero> _listaCronogramaSemillero) {
    this._listaCronogramaSemillero = _listaCronogramaSemillero;
  }

  public List<CronogramaSemilleroDTO> getListaCronogramaSemilleroDTO() {
    return _listaCronogramaSemilleroDTO;
  }

  public void setListaCronogramaSemilleroDTO(List<CronogramaSemilleroDTO> _listaCronogramaSemilleroDTO) {
    this._listaCronogramaSemilleroDTO = _listaCronogramaSemilleroDTO;
  }

}
