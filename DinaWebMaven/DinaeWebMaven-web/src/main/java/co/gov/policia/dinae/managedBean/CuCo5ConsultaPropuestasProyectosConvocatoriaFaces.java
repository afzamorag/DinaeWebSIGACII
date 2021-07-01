package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.PeriodoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
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
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author cguzman
 */
@Named(value = "cuCo5ConsultaPropuestasProyectosConvocatoriaFaces")
@SessionScoped
public class CuCo5ConsultaPropuestasProyectosConvocatoriaFaces extends JSFUtils implements Serializable {

  @EJB
  private IProyectoLocal _iProyectos;

  @EJB
  private IAreaCienciaTecnologiaLocal _iAreaCienciaTecnologia;

  @EJB
  private ILineaLocal _iLinea;

  @EJB
  private IUnidadPolicialLocal _iUnidadPolicial;

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IPeriodoLocal _iPeriodo;

  private List<ProyectoDTO> _listaPropuestasProyectosInvestigacion;
  private ProyectoDTO _propuestaSeleccionada;
  private ListGenericDataModel<ProyectoDTO> _listaPropuestasProyectosInvestigacionModel;
  private Long _idArea;
  private Long _idLinea;
  private Long _idEstadoPropuesta;
  private Long _idUnidadPolicial;
  private String _palabraClave;
  private Date _fechaInicialPresentacion;
  private Date _fechaFinalPresentacion;
  private Long _idConvocatoria;
  private List<SelectItem> _listaItemsAreaSaber;
  private List<SelectItem> _listaItemsLinea;
  private List<SelectItem> _listaItemsUnidades;
  private List<SelectItem> _listaItemsEstados;
  private List<SelectItem> _listaItemsCovocatorias;
  private boolean _usuarioUnidad;
  private UnidadPolicialDTO _unidaPolicialDTO;

  public String initReturnCU() {

    inicializarCamposFiltros();
    cargarListaArea();
    cargarListadoUnidadesPolicialesActivas();
    cargarListaEstadosPropuestas();
    cargarListadoConvocatorias();

    _unidaPolicialDTO = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial();
    _usuarioUnidad = (_unidaPolicialDTO != null);

    return navigationFaces.redirectCuCo5ConsultarPropuestasProyectosInvestigacionRedirect();
  }

  /**
   *
   */
  private void cargarListaArea() {
    try {
      _listaItemsAreaSaber = UtilidadesItem.getListaSel(_iAreaCienciaTecnologia.getAreaCienciaTecnologias(), "idAreaCienciaTecnologia", "nombre");

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-05 Consultar propuestas de proyectos de investigación para convocatoria (cargarListaAreas) ", e);
    }
  }

  /**
   *
   */
  public void cargarLineasPorAreaCienciaTecnologiaSeleccionada() {
    if (_idArea != null) {
      try {
        _listaItemsLinea = UtilidadesItem.getListaSel(_iLinea.getLineasPorArea(_idArea), "idLinea", "nombre");
      } catch (Exception e) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-CO-05 Consultar propuestas de proyectos de investigación para convocatoria (cargarLineasPorAreaCienciaTecnologiaSeleccionada) ", e);
      }
    } else {
      _listaItemsLinea = new ArrayList<SelectItem>();
    }
  }

  /**
   *
   */
  private void cargarListadoUnidadesPolicialesActivas() {
    try {
      List<UnidadPolicialDTO> listaUnidadesDTO = _iUnidadPolicial.getAllUnidadesPolicialesActivasDTO();
      _listaItemsUnidades = UtilidadesItem.getListaSel(listaUnidadesDTO, "idUnidadPolicial", "nombre");
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo5ConsultaPropuestasProyectosConvocatoriaFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private void cargarListaEstadosPropuestas() {
    try {
      Constantes estadoNoAprobada = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_APROBADA);
      Constantes estadoParaFinanciar = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_PARA_FINALIZAR);

      List<Constantes> constantesEspecificas = new ArrayList<Constantes>();

      constantesEspecificas.add(estadoNoAprobada);
      constantesEspecificas.add(estadoParaFinanciar);

      _listaItemsEstados = UtilidadesItem.getListaSel(constantesEspecificas, "idConstantes", "valor");
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo5ConsultaPropuestasProyectosConvocatoriaFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private void cargarListadoConvocatorias() {
    try {
      List<PeriodoDTO> convocatorias = _iPeriodo.getListaConvocatoriasPorTipoConvocatoria(
              IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION,
              null);
      _listaItemsCovocatorias = UtilidadesItem.getListaSel(convocatorias, "idPeriodo", "descripcionConcecutivo");
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo5ConsultaPropuestasProyectosConvocatoriaFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public String cargarListaPropuestasProyectosInvestigacionFiltros() {

    try {
      Map<String, Object> criterios = new HashMap<String, Object>();

      if (_idUnidadPolicial != null && _idUnidadPolicial.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_UNIDAD_POLICIAL, _idUnidadPolicial);
      }

      if (_unidaPolicialDTO != null && _unidaPolicialDTO.getIdUnidadPolicial() != null) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_UNIDAD_POLICIAL, _unidaPolicialDTO.getIdUnidadPolicial());
      }

      if (_palabraClave != null && !"".equals(_palabraClave)) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_PALABRA_CLAVE, _palabraClave);
      }

      if (_idEstadoPropuesta != null && _idEstadoPropuesta.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_ESTADO, _idEstadoPropuesta);
      }

      if (_idLinea != null && _idLinea.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_LINEA, _idLinea);
      }

      if (_idArea != null && _idArea.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_AREA, _idArea);
      }

      if (_fechaInicialPresentacion != null && _fechaFinalPresentacion != null) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_FECHA_INICIAL, _fechaInicialPresentacion);
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_FECHA_FINAL, _fechaFinalPresentacion);
      }

      if (_idConvocatoria != null && _idConvocatoria.compareTo(-1L) != 0) {
        criterios.put(IConstantes.CRITERIO_PROPUESTAS_PROYECTO_INV_CONVOCATORIA, _idConvocatoria);
      }

      _listaPropuestasProyectosInvestigacion = _iProyectos.getListaPropuestasProyectosInvestigacionFiltros(criterios);

      _listaPropuestasProyectosInvestigacionModel = new ListGenericDataModel<ProyectoDTO>(_listaPropuestasProyectosInvestigacion);

      if (_listaPropuestasProyectosInvestigacion.isEmpty()) {
        adicionaMensajeError("No existen propuestas que cumplan con los filtros ingresados");
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }

    return null;

  }

  public String inicializarCamposFiltros() {

    _idUnidadPolicial = null;
    _palabraClave = null;
    _idEstadoPropuesta = -1L;
    _idArea = -1L;
    _idLinea = -1L;
    _fechaFinalPresentacion = null;
    _fechaInicialPresentacion = null;
    _idConvocatoria = null;

    cargarLineasPorAreaCienciaTecnologiaSeleccionada();

    _listaPropuestasProyectosInvestigacion = new ArrayList<ProyectoDTO>();
    _listaPropuestasProyectosInvestigacionModel = new ListGenericDataModel<ProyectoDTO>(_listaPropuestasProyectosInvestigacion);

    return null;
  }

  @Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion consultarDetalleProyectoInvestigacion;

  /**
   *
   * @param e
   */
  public void navgarCuPr06(SelectEvent e) {

    try {

      if (_propuestaSeleccionada == null) {
        return;
      }

      consultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_CO_05(_propuestaSeleccionada.getId());

      navigationFaces.redirectFacesCu06ConsultarDetalleProyectoInvestigacion();

    } catch (Exception ex) {

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

  public List<ProyectoDTO> getListaPropuestasProyectosInvestigacion() {
    return _listaPropuestasProyectosInvestigacion;
  }

  public void setListaPropuestasProyectosInvestigacion(List<ProyectoDTO> _listaPropuestasProyectosInvestigacion) {
    this._listaPropuestasProyectosInvestigacion = _listaPropuestasProyectosInvestigacion;
  }

  public ListGenericDataModel<ProyectoDTO> getListaPropuestasProyectosInvestigacionModel() {
    return _listaPropuestasProyectosInvestigacionModel;
  }

  public void setListaPropuestasProyectosInvestigacionModel(ListGenericDataModel<ProyectoDTO> _listaPropuestasProyectosInvestigacionModel) {
    this._listaPropuestasProyectosInvestigacionModel = _listaPropuestasProyectosInvestigacionModel;
  }

  public Long getIdArea() {
    return _idArea;
  }

  public void setIdArea(Long _idArea) {
    this._idArea = _idArea;
  }

  public Long getIdLinea() {
    return _idLinea;
  }

  public void setIdLinea(Long _idLinea) {
    this._idLinea = _idLinea;
  }

  public Long getIdEstadoPropuesta() {
    return _idEstadoPropuesta;
  }

  public void setIdEstadoPropuesta(Long _idEstadoPropuesta) {
    this._idEstadoPropuesta = _idEstadoPropuesta;
  }

  public Long getIdUnidadPolicial() {
    return _idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long _idUnidadPolicial) {
    this._idUnidadPolicial = _idUnidadPolicial;
  }

  public String getPalabraClave() {
    return _palabraClave;
  }

  public void setPalabraClave(String _palabraClave) {
    this._palabraClave = _palabraClave;
  }

  public Date getFechaInicialPresentacion() {
    return _fechaInicialPresentacion;
  }

  public void setFechaInicialPresentacion(Date _fechaInicialPresentacion) {
    this._fechaInicialPresentacion = _fechaInicialPresentacion;
  }

  public Date getFechaFinalPresentacion() {
    return _fechaFinalPresentacion;
  }

  public void setFechaFinalPresentacion(Date _fechaFinalPresentacion) {
    this._fechaFinalPresentacion = _fechaFinalPresentacion;
  }

  public ProyectoDTO getPropuestaSeleccionada() {
    return _propuestaSeleccionada;
  }

  public void setPropuestaSeleccionada(ProyectoDTO _propuestaSeleccionada) {
    this._propuestaSeleccionada = _propuestaSeleccionada;
  }

  public List<SelectItem> getListaItemsAreaSaber() {
    return _listaItemsAreaSaber;
  }

  public void setListaItemsAreaSaber(List<SelectItem> _listaItemsAreaSaber) {
    this._listaItemsAreaSaber = _listaItemsAreaSaber;
  }

  public Long getIdConvocatoria() {
    return _idConvocatoria;
  }

  public void setIdConvocatoria(Long _idConvocatoria) {
    this._idConvocatoria = _idConvocatoria;
  }

  public List<SelectItem> getListaItemsLinea() {
    return _listaItemsLinea;
  }

  public void setListaItemsLinea(List<SelectItem> _listaItemsLinea) {
    this._listaItemsLinea = _listaItemsLinea;
  }

  public List<SelectItem> getListaItemsUnidades() {
    return _listaItemsUnidades;
  }

  public void setListaItemsUnidades(List<SelectItem> _listaItemsUnidades) {
    this._listaItemsUnidades = _listaItemsUnidades;
  }

  public List<SelectItem> getListaItemsEstados() {
    return _listaItemsEstados;
  }

  public void setListaItemsEstados(List<SelectItem> _listaItemsEstados) {
    this._listaItemsEstados = _listaItemsEstados;
  }

  public List<SelectItem> getListaItemsCovocatorias() {
    return _listaItemsCovocatorias;
  }

  public void setListaItemsCovocatorias(List<SelectItem> _listaItemsCovocatorias) {
    this._listaItemsCovocatorias = _listaItemsCovocatorias;
  }

  public boolean isUsuarioUnidad() {
    return _usuarioUnidad;
  }

  public void setUsuarioUnidad(boolean _usuarioUnidad) {
    this._usuarioUnidad = _usuarioUnidad;
  }

  public UnidadPolicialDTO getUnidaPolicialDTO() {
    return _unidaPolicialDTO;
  }

  public void setUnidaPolicialDTO(UnidadPolicialDTO _unidaPolicialDTO) {
    this._unidaPolicialDTO = _unidaPolicialDTO;
  }
}
