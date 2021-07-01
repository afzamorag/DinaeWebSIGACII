package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICriterioEvaluacionLocal;
import co.gov.policia.dinae.interfaces.IDetalleEvaluacionLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.CriterioEvaluacion;
import co.gov.policia.dinae.modelo.DetalleEvaluacion;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuPr12EvaluacionProyectosInvestigacionFaces")
@javax.enterprise.context.SessionScoped
public class CuPr12EvaluacionProyectosInvestigacion extends JSFUtils implements Serializable {

  private static final BigDecimal NOTA_MAXIMA = new BigDecimal(5.0);

  private static final BigDecimal NOTA_MINIMA = new BigDecimal(1.0);

  private static final String EXCELENTE = "EXCELENTE";

  private static final String BUENO = "BUENO";

  private static final String ACEPTABLE = "ACEPTABLE";

  private static final String DEFICIENTE = "DEFICIENTE";

  @EJB
  private IProyectoLocal _iProyecto;

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IUnidadPolicialLocal _iUnidades;

  @EJB
  private ICriterioEvaluacionLocal _iCriterioEvaluacion;

  @EJB
  private IVistaFuncionarioLocal _iVistaFuncionario;

  @EJB
  private IInvestigadorLocal _iInvestigador;

  @EJB
  private IDetalleEvaluacionLocal _iDetalleEvaluacion;

  private ListGenericDataModel<ProyectoDTO> _listaProyectosInvestigacion;

  private List<Constantes> _listaTiposProyecto;

  private List<UnidadPolicial> _listaUnidadesPoliciales;

  private List<CriterioEvaluacion> _listaCriteriosEvaluacion;

  private List<InvestigadorProyectoDTO> _investigadorSeleccionado;

  private Long _idUnidad;

  private Long _idTipoProyecto;

  private String _codigoProyecto;

  private ProyectoDTO _proyectoSeleccionado;

  private String _identificacion;

  private String _cualificacionTotal;

  private String _calificacionTotal;

  private String _sugerencias;

  private UsuarioRol _usuarioRol;

  private ProyectoDTO proyectoSeleccionadoConsultaCuPr06;

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  private boolean mostrarGuardar;

  /**
   *
   * @return
   */
  public String initReturnCU() {

    String navega = null;

    try {

      limpiarFiltros();

      mostrarGuardar = false;

      proyectoSeleccionadoConsultaCuPr06 = null;

      cargarListaTiposProyecto();
      cargarListaUnidadesPoliciales();
      cargarCriteriosEvaluacion();

      RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_LA_EVALUACION_DE_LOS_PROYECTOS_DE_INVESTIGACION_VICIN);
      _usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

      navega = navigationFaces.redirectCuPr12EvaluacionProyectosInvestigacionRedirect();

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;
  }

  /**
   *
   */
  public void seleccionarProyectoConsulta() {

    try {

      if (proyectoSeleccionadoConsultaCuPr06 == null) {

        return;

      }

      navigationFaces.redirectFacesCuGenerico(cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_PR_12(proyectoSeleccionadoConsultaCuPr06.getId()));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, e);

    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaUnidadesPoliciales() throws JpaDinaeException {

    _listaUnidadesPoliciales = _iUnidades.getAllUnidadesPolicialesActivas();
    if (_listaUnidadesPoliciales == null || _listaUnidadesPoliciales.isEmpty()) {
      _listaUnidadesPoliciales = new ArrayList<UnidadPolicial>();
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaTiposProyecto() throws JpaDinaeException {
    _listaTiposProyecto = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_DE_PROYECTO);

    if (_listaTiposProyecto == null || _listaTiposProyecto.isEmpty()) {
      _listaTiposProyecto = new ArrayList<Constantes>();
    }
  }

  /**
   *
   * @return
   */
  public String buscarFiltros() {
    try {
      _idUnidad = (_idUnidad.compareTo(-1L) == 0) ? null : _idUnidad;
      _idTipoProyecto = (_idTipoProyecto.compareTo(-1L) == 0) ? null : _idTipoProyecto;
      _codigoProyecto = (_codigoProyecto != null && !"".equals(_codigoProyecto.trim())) ? _codigoProyecto : null;

      _listaProyectosInvestigacion = new ListGenericDataModel<ProyectoDTO>(
              _iProyecto.findAllProyectosInvestigacionFiltros(
                      IConstantes.TIPO_ESTADO_PROYECTO_CULMINADO,
                      _idTipoProyecto,
                      _idUnidad,
                      _codigoProyecto));

      _idUnidad = (_idUnidad == null) ? -1L : _idUnidad;
      _idTipoProyecto = (_idTipoProyecto == null) ? -1L : _idTipoProyecto;

      if (_listaProyectosInvestigacion.getNumeroRegistro() == 0) {

        adicionaMensajeError("No se encontraron proyectos para evaluar");
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String limpiarFiltros() {
    try {

      _idTipoProyecto = -1L;
      _idUnidad = -1L;
      _codigoProyecto = null;

      _proyectoSeleccionado = null;

      _listaProyectosInvestigacion = new ListGenericDataModel<ProyectoDTO>(new ArrayList<ProyectoDTO>());

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarCriteriosEvaluacion() throws JpaDinaeException {
    _listaCriteriosEvaluacion = _iCriterioEvaluacion.getListaCriterioEvaluacionPorEstado(IConstantes.ESTADO_ACTIVO_CRITERIO_EVALUACION, IConstantes.TIPO_CRITERIO_EVALUACION_PROYECTO);
    if (_listaCriteriosEvaluacion == null || _listaCriteriosEvaluacion.isEmpty()) {
      _listaCriteriosEvaluacion = new ArrayList<CriterioEvaluacion>();
    }
  }

  /**
   *
   * @param criterioEvaluacion
   * @return
   */
  public String tomarTextoNota(CriterioEvaluacion criterioEvaluacion) {

    if (criterioEvaluacion.getEvaluacion().compareTo(NOTA_MINIMA) >= 0 && criterioEvaluacion.getEvaluacion().compareTo(NOTA_MAXIMA) <= 0) {
      criterioEvaluacion.setCualificacion(calcularCualificacion(criterioEvaluacion.getEvaluacion()));
      calcularTotales();
    } else {
      adicionaMensajeError("La calificación debe estar el rango de " + NOTA_MINIMA.toString() + " a " + NOTA_MAXIMA.toString());
      criterioEvaluacion.setEvaluacion(null);
    }

    return null;
  }

  private void calcularTotales() {

    BigDecimal sumatoria = BigDecimal.ZERO;
    BigDecimal promedio = BigDecimal.ZERO;

    if (_listaCriteriosEvaluacion != null && !_listaCriteriosEvaluacion.isEmpty()) {
      for (CriterioEvaluacion ce : _listaCriteriosEvaluacion) {
        sumatoria = sumatoria.add((ce.getEvaluacion() == null) ? BigDecimal.ZERO : ce.getEvaluacion());
      }

      if (sumatoria.compareTo(BigDecimal.ONE) >= 0) {
        promedio = sumatoria.divide(new BigDecimal(_listaCriteriosEvaluacion.size()), 1);
      }
    }

    _calificacionTotal = promedio.toString();
    _cualificacionTotal = (promedio.compareTo(BigDecimal.ONE) >= 0) ? calcularCualificacion(promedio) : DEFICIENTE;

  }

  /**
   *
   */
  private String calcularCualificacion(BigDecimal evaluacion) {

    String cualificacion = null;

    if (evaluacion.compareTo(new BigDecimal(4.0)) >= 0 && evaluacion.compareTo(NOTA_MAXIMA) <= 0) {
      cualificacion = EXCELENTE;
    }

    if (evaluacion.compareTo(new BigDecimal(3.0)) >= 0 && evaluacion.compareTo(new BigDecimal(4.0)) < 0) {
      cualificacion = BUENO;
    }
    if (evaluacion.compareTo(new BigDecimal(2.0)) >= 0 && evaluacion.compareTo(new BigDecimal(3.0)) < 0) {
      cualificacion = ACEPTABLE;
    }

    if (evaluacion.compareTo(NOTA_MINIMA) >= 0 && evaluacion.compareTo(new BigDecimal(2.0)) < 0) {
      cualificacion = DEFICIENTE;
    }

    return cualificacion;
  }

  /**
   *
   * @return
   */
  public String buscarInvestigador() {
    try {

      _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();

      if (_identificacion != null && !"".equals(_identificacion.trim())) {

        VistaFuncionario vistaFuncionario = _iVistaFuncionario.getVistaFuncionarioPorIdentificacion(_identificacion);
        InvestigadorProyectoDTO investiga;

        if (vistaFuncionario != null) {

          investiga = new InvestigadorProyectoDTO();
          investiga.setGrado(vistaFuncionario.getGrado());
          investiga.setNombreCompleto(vistaFuncionario.getNombreCompleto());
          investiga.setCorreo(vistaFuncionario.getCorreo());
          investiga.setIdentificacion(_identificacion);

          _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
          _investigadorSeleccionado.add(investiga);

          return null;
        }

        Investigador investigador = _iInvestigador.getInvestigadorPorNumeroIdentificacion(_identificacion);

        if (investigador != null) {

          investiga = new InvestigadorProyectoDTO();
          investiga.setGrado(investigador.getGrado());
          investiga.setNombreCompleto(investigador.getNombres() + " " + investigador.getApellidos());
          investiga.setCorreo(investigador.getCorreoElectronico());
          investiga.setIdentificacion(_identificacion);

          _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
          _investigadorSeleccionado.add(investiga);

          return null;
        }

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_3_lbl_identificacion_no_encontrada"));

      } else {
        adicionaMensajeError("El N° de identificación esta vacío");
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String irEvaluacionProyecto() {

    mostrarGuardar = true;
    return navigationFaces.redirectCuPr121EvaluacionProyectosInvestigacionRedirect();
  }

  /**
   *
   * @return
   */
  public String regresarBusqueda() {

    String navega = null;
    try {

      _proyectoSeleccionado = null;
      limpiarFiltros();
      cargarCriteriosEvaluacion();

      _identificacion = null;
      _sugerencias = null;

      _investigadorSeleccionado = new ArrayList<InvestigadorProyectoDTO>();

      navega = navigationFaces.redirectCuPr12EvaluacionProyectosInvestigacionRedirect();
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navega;

  }

  /**
   *
   * @param codigo
   * @return
   */
  public String tomarTipoProyecto(String codigo) {
    String tipoProyecto = null;
    try {

      if (codigo.startsWith(keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion"))) {
        tipoProyecto = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES).getValor();
      }

      if (codigo.startsWith(keyPropertiesFactory.value("cu_co_4_codigo_proyecto_inicia_generacion"))) {
        tipoProyecto = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION).getValor();
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return tipoProyecto;
  }

  /**
   *
   * @return
   */
  public String guardarEvaluacionProyecto() {
    try {
      if (_listaCriteriosEvaluacion != null && !_listaCriteriosEvaluacion.isEmpty()) {

        for (CriterioEvaluacion ce : _listaCriteriosEvaluacion) {
          if (ce.getEvaluacion() == null) {
            adicionaMensajeError("Se debe diligenciar todos los criterios de evaluacion, el criterio " + ce.getNombreCriterio() + " es obligatorio");
            return null;
          }
        }

        if (_sugerencias == null || "".equals(_sugerencias.trim())) {
          adicionaMensajeError("El campo Sugerencias y recomendaciones es obligatorio");
          return null;
        }

        if (_identificacion == null || "".equals(_identificacion.trim())) {
          adicionaMensajeError("El campo N° de identificación es obligatorio");
          return null;
        }

        if (_investigadorSeleccionado == null || _investigadorSeleccionado.isEmpty()) {
          adicionaMensajeError("Los datos del investigador son obligatorios");
          return null;
        }

        List<EvaluacionProyecto> listaEvaluacionProyecto = new ArrayList<EvaluacionProyecto>();

        for (CriterioEvaluacion ce : _listaCriteriosEvaluacion) {
          EvaluacionProyecto evaluacionProyecto = new EvaluacionProyecto();
          evaluacionProyecto.setCriterioEvaluacion(ce);
          evaluacionProyecto.setValorCriterio(ce.getValor());//VALOR DEL CRITERIO EN ESE MOMENTO
          evaluacionProyecto.setProyecto(new Proyecto(_proyectoSeleccionado.getId()));
          evaluacionProyecto.setUsuarioRol(_usuarioRol);
          evaluacionProyecto.setFechaRegistro(new Date());
          evaluacionProyecto.setNota(ce.getEvaluacion());//NOTA QUE INGRESA EL USURIO: CALIFICACION
          evaluacionProyecto.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

          listaEvaluacionProyecto.add(evaluacionProyecto);

        }

        DetalleEvaluacion detalle = new DetalleEvaluacion();
        detalle.setFechaRegistro(new Date());
        detalle.setIdentificacionEvaluador(_identificacion);
        detalle.setProyecto(new Proyecto(_proyectoSeleccionado.getId()));
        detalle.setSugerenciaRecomendacion(_sugerencias);
        detalle.setUsuarioRol(_usuarioRol);

        _iDetalleEvaluacion.saveOrUpdateYActualizarEstadoProyecto(
                listaEvaluacionProyecto,
                detalle,
                _proyectoSeleccionado.getId(),
                IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO,
                _proyectoSeleccionado.getIdPeriodo());

        adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

        mostrarGuardar = false;

        String retorno = regresarBusqueda();

        buscarFiltros();

        return retorno;

      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public ListGenericDataModel<ProyectoDTO> getListaProyectosInvestigacion() {
    return _listaProyectosInvestigacion;
  }

  public void setListaProyectosInvestigacion(ListGenericDataModel<ProyectoDTO> _listaProyectosInvestigacion) {
    this._listaProyectosInvestigacion = _listaProyectosInvestigacion;
  }

  public Long getIdUnidad() {
    return _idUnidad;
  }

  public void setIdUnidad(Long _idUnidad) {
    this._idUnidad = _idUnidad;
  }

  public Long getIdTipoProyecto() {
    return _idTipoProyecto;
  }

  public void setIdTipoProyecto(Long _idTipoProyecto) {
    this._idTipoProyecto = _idTipoProyecto;
  }

  public String getCodigoProyecto() {
    return _codigoProyecto;
  }

  public void setCodigoProyecto(String _codigoProyecto) {
    this._codigoProyecto = _codigoProyecto;
  }

  public List<Constantes> getListaTiposProyecto() {
    return _listaTiposProyecto;
  }

  public void setListaTiposProyecto(List<Constantes> _listaTiposProyecto) {
    this._listaTiposProyecto = _listaTiposProyecto;
  }

  public List<UnidadPolicial> getListaUnidadesPoliciales() {
    return _listaUnidadesPoliciales;
  }

  public void setListaUnidadesPoliciales(List<UnidadPolicial> _listaUnidadesPoliciales) {
    this._listaUnidadesPoliciales = _listaUnidadesPoliciales;
  }

  public ProyectoDTO getProyectoSeleccionado() {
    return _proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(ProyectoDTO _proyectoSeleccionado) {
    this._proyectoSeleccionado = _proyectoSeleccionado;
  }

  public List<CriterioEvaluacion> getListaCriteriosEvaluacion() {
    return _listaCriteriosEvaluacion;
  }

  public void setListaCriteriosEvaluacion(List<CriterioEvaluacion> _listaCriteriosEvaluacion) {
    this._listaCriteriosEvaluacion = _listaCriteriosEvaluacion;
  }

  public String getIdentificacion() {
    return _identificacion;
  }

  public void setIdentificacion(String _identificacion) {
    this._identificacion = _identificacion;
  }

  public String getCualificacionTotal() {
    return _cualificacionTotal;
  }

  public void setCualificacionTotal(String _cualificacionTotal) {
    this._cualificacionTotal = _cualificacionTotal;
  }

  public String getCalificacionTotal() {
    return _calificacionTotal;
  }

  public void setCalificacionTotal(String _calificacionTotal) {
    this._calificacionTotal = _calificacionTotal;
  }

  public String getSugerencias() {
    return _sugerencias;
  }

  public void setSugerencias(String _sugerencias) {
    this._sugerencias = _sugerencias;
  }

  public List<InvestigadorProyectoDTO> getInvestigadorSeleccionado() {
    return _investigadorSeleccionado;
  }

  public void setInvestigadorSeleccionado(List<InvestigadorProyectoDTO> _investigadorSeleccionado) {
    this._investigadorSeleccionado = _investigadorSeleccionado;
  }

  public ProyectoDTO getProyectoSeleccionadoConsultaCuPr06() {
    return proyectoSeleccionadoConsultaCuPr06;
  }

  public void setProyectoSeleccionadoConsultaCuPr06(ProyectoDTO proyectoSeleccionadoConsultaCuPr06) {
    this.proyectoSeleccionadoConsultaCuPr06 = proyectoSeleccionadoConsultaCuPr06;
  }

  public boolean isMostrarGuardar() {
    return mostrarGuardar;
  }

  public void setMostrarGuardar(boolean mostrarGuardar) {
    this.mostrarGuardar = mostrarGuardar;
  }

}
