package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ArchivoReporte;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICriterioEvaluacionLocal;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.ComentarioProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.CriterioEvaluacion;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.CriteriosEvaluacionEnsayosView;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.EnsayoCriticoUnidadView;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuCo8EvaluacionEnsayoCriticoFaces")
@javax.enterprise.context.SessionScoped
public class CuCo8EvaluacionEnsayoCritico extends JSFUtils implements Serializable {

  private static final String PORCENTAJE = "%";

  private static final String DE = " de ";

  @EJB
  private IEnsayoCriticoLocal _iEnsayoCritico;

  @EJB
  private IUsuarioUnidadPolicialLocal _iUsuarioUnidadPolicial;

  @EJB
  private IEvaluacionProyectoLocal _iEvaluacionProyecto;

  @EJB
  private ICriterioEvaluacionLocal _iCriterioEvaluacion;

  @EJB
  private IComentarioProyectoLocal _iComentarioProyecto;

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IMailSessionLocal _iMailSessionBean;

  @EJB
  private IPeriodoLocal _iPeriodo;

  @EJB
  private IVistaFuncionarioLocal _iVistaFuncionario;

  @javax.inject.Inject
  private CuCo9ConsultarDetallesEnsayoCritico cuCo9ConsultarDetallesEnsayoCriticoFaces;

  @Inject
  private CuCo1GestionarConvocatoriasFaces cuCo1GestionarConvocatoriasFaces;

  private List<EnsayoCritico> _listadoEnsayoCriticoUnidad;

  private ListGenericDataModel _listadoEnsayoCriticoUnidadModel;

  private List<EnsayoCriticoUnidadView> _listaEnsayoCriticoUnidadView;

  private List<Periodo> _listaConvocatoriasEnsayoCritico;

  private ListGenericDataModel _listaConvocatoriasEnsayoCriticoModel;

  private EnsayoCriticoUnidadView _ensayoItemSeleccionado;

  private List<CriteriosEvaluacionEnsayosView> _listaCriteriosEnsayos;

  private String _comentariosEvaluacion;

  private List<Constantes> _itemsListaEstados;

  private List<CriterioEvaluacion> _listaCriterioEvaluacionEnsayo;

  private String _evaluacionTotal;

  private List<EvaluacionProyecto> _evaluacionesEnsayo;

  private ComentarioProyecto _comentarioProyecto;

  private boolean _deshabilitarEdicion;

  private boolean _deshabilitarEdicionEnviar;

  private boolean _esEncargadoVicin;

  private boolean _mostrarLinkGanadores;

  private Periodo _periodo;

  private boolean _esOrigenCuCo8;

  /**
   *
   * @return
   */
  public String initReturnCU() {
    try {

      if (loginFaces.getPerfilUsuarioDTO().getUnidadPolicial() == null) {

        adicionaMensajeError("Para acceder a esta opción es indispensable que el usuario pertenezca a una Unidad Policial");
        return null;
      }

      _listaConvocatoriasEnsayoCritico = _iPeriodo.getPeriodosUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA,
              IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO);

      _listaConvocatoriasEnsayoCriticoModel = new ListGenericDataModel(_listaConvocatoriasEnsayoCritico);
      return "/pages/secured/cu_co_8/gestionarConvocatoriasEnsayoCritico.xhtml?faces-redirect=true";

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarPropuestaSeleccionConvocatoriaEnsayo(SelectEvent event) {
    try {
      if (_periodo == null) {
        return;
      }

      _esOrigenCuCo8 = true;

      String redirect = redireccionarEvaluarEnsayos(_periodo.getIdPeriodo());
      if (redirect != null) {
        navigationFaces.redirectFacesCuCo8EvaluacionEnsayoCritico();
      }

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + ex.getMessage());
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public String regresarOrigen() {

    _periodo = null;

    if (_esOrigenCuCo8) {
      return initReturnCU();
    } else {
      return cuCo1GestionarConvocatoriasFaces.initReturnCU();
    }
  }

  /**
   *
   * @return
   */
  public String regresarSeleccionGanadores() {
    return redireccionarEvaluarEnsayos(_periodo.getIdPeriodo());
  }

  /**
   * Ya no ha seleccionado un periodo
   *
   * @param event
   */
  public void noSeleccionConvocatoria(UnselectEvent event) {
    _periodo = null;
  }

  /**
   *
   * @param idPeriodo
   * @return
   */
  public String redireccionarEvaluarEnsayos(Long idPeriodo) {
    try {

      this._periodo = _iPeriodo.getPeriodoPorId(idPeriodo);

      PerfilUsuarioDTO perfilUsuarioDTO = loginFaces.getPerfilUsuarioDTO();
      UnidadPolicialDTO unidadPolicialDTO = perfilUsuarioDTO.getUnidadPolicial();

      List<Long> idEstados = tomarFiltrosUsuarioRol(perfilUsuarioDTO);

      if (_esEncargadoVicin) {

        _listadoEnsayoCriticoUnidad = _iEnsayoCritico.findByPeriodoAllUnidades(idEstados, _periodo.getIdPeriodo());

      } else {
        _listadoEnsayoCriticoUnidad = _iEnsayoCritico.findByPeriodoUnidadCobertura(unidadPolicialDTO.getSiglaPadre(), idEstados, _periodo.getIdPeriodo());
      }

      if (_listadoEnsayoCriticoUnidad == null || _listadoEnsayoCriticoUnidad.isEmpty()) {
        _listadoEnsayoCriticoUnidad = new ArrayList<EnsayoCritico>();
      }

      cargarDatosTablaEnsayos();
      _mostrarLinkGanadores = determinarSeleccionGanadores();

      return navigationFaces.redirectCuCo8EvaluacionEnsayoCriticoRedirect();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarDatosTablaEnsayos() throws JpaDinaeException {

    _listaEnsayoCriticoUnidadView = new ArrayList<EnsayoCriticoUnidadView>(_listadoEnsayoCriticoUnidad.size());

    Map<Long, List<EnsayoCritico>> ensayosUnidades = mapaUnidaddes(_listadoEnsayoCriticoUnidad);
    Set<Long> idUnidades = ensayosUnidades.keySet();

    Long estadoEnviadoVicin = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_ENVIADO_VICIN;
    String recibido = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_RECIBIDO).getValor();

    for (Long id : idUnidades) {
      List<EnsayoCritico> ensayosUnidad = ensayosUnidades.get(id);

      int cont = 1;

      for (EnsayoCritico ensayo : ensayosUnidad) {

        EnsayoCriticoUnidadView view = new EnsayoCriticoUnidadView();

        view.setEnsayo(ensayo);
        view.setIdUnidadPolicial(ensayo.getIdUnidadPolicial());
        view.setNombreUnidad(ensayo.getNombreUnidadPolicial());
        view.setConsecutivoEnsayo(cont + DE + ensayosUnidad.size());
        view.setFechaPresentacion(ensayo.getFechaPresentacion());
        view.setIdEnsayoCritico(ensayo.getIdEnsayoCritico());
        view.setTituloEnsayo(ensayo.getTituloEnsayo());
        view.setIdPeriodo(_periodo.getIdPeriodo());

        if (_esEncargadoVicin) {
          view.setEsEstadoVicin(true);
          view.setEstadoEnsayo((ensayo.getEstadoCuCo10() != null && ensayo.getEstadoCuCo10().getIdConstantes().compareTo(estadoEnviadoVicin) == 0) ? recibido : ensayo.getEstadoCuCo10().getValor());
          view.setEstadoCalifacion((ensayo.getCalificacionCuCo10() == null) ? -1L : ensayo.getCalificacionCuCo10().getIdConstantes());
        } else {

          String estado;

          if (ensayo.getEstadoCuCo10() != null) {
            estado = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_ENVIADO_VICIN).getValor();
          } else {
            estado = ensayo.getEstadoCuCo8().getValor();
          }

          view.setEstadoEnsayo(estado);
          view.setEstadoCalifacion((ensayo.getCalificacionCuCo8() == null) ? -1L : ensayo.getCalificacionCuCo8().getIdConstantes());
        }

        Double evaluacionEnsayo = obtenerEvaluacionCriterios(ensayo.getIdEnsayoCritico());
        DecimalFormat formato = new DecimalFormat(keyPropertiesFactory.value("general_formato_valor_cifra_decimal"));
        String evaluacion = formato.format(evaluacionEnsayo);

        view.setEvaluacionEnsayo(evaluacion + PORCENTAJE);

        _listaEnsayoCriticoUnidadView.add(view);
        cont++;

      }
    }

    _listadoEnsayoCriticoUnidadModel = new ListGenericDataModel(_listaEnsayoCriticoUnidadView);
  }

  /**
   *
   * @param perfil
   * @return
   */
  public List<Long> tomarFiltrosUsuarioRol(PerfilUsuarioDTO perfil) {

    List<Long> filtros = new ArrayList<Long>();

    if (perfil.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_ENSAYOS_EN_LA_UNIDAD_POLICIAL) != null) {

      _esEncargadoVicin = false;
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_RECIBIDO);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_LEIDO);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO_UNIDAD);

    } else if (perfil.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN) != null) {

      _esEncargadoVicin = true;
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_ENVIADO_VICIN);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_LEIDO_VICIN);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO);
      filtros.add(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_GANADOR);

    }

    return filtros;
  }

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  private Double obtenerEvaluacionCriterios(Long idEnsayoCritico) throws JpaDinaeException {

    Double result = 0D;
    List<EvaluacionProyecto> listaEvaluacionesEnsayo = _iEvaluacionProyecto.getCriterioPorEnsayoCritico(idEnsayoCritico);
    for (EvaluacionProyecto _eval : listaEvaluacionesEnsayo) {

      if (_esEncargadoVicin) {
        result += (_eval.getValorCriterioVicin() == null) ? 0L : _eval.getValorCriterioVicin().doubleValue();
        continue;
      }
      result += (_eval.getValorCriterio() == null) ? 0L : _eval.getValorCriterio().doubleValue();
    }

    return result;
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarEnsayoCriticoEvaluacion(SelectEvent event) {
    try {
      if (_ensayoItemSeleccionado == null) {
        return;
      }

      cuCo9ConsultarDetallesEnsayoCriticoFaces.initReturnCU(_ensayoItemSeleccionado);

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + ex.getMessage());
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param ensayoCriticoUnidadView
   * @return
   */
  public String iniciarEvaluacion(EnsayoCriticoUnidadView ensayoCriticoUnidadView) {
    try {
      _ensayoItemSeleccionado = ensayoCriticoUnidadView;
      cargarCriteriosEvaluacion();
      cargarEvaluacionAnterior();
      return navigationFaces.redirectCuCo8EvaluarEnsayoCriticoCriteriosRedirect();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + ex.getMessage());
      Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarCriteriosEvaluacion() throws JpaDinaeException {
    _listaCriterioEvaluacionEnsayo = _iCriterioEvaluacion.getListaCriterioEvaluacionPorEstado(IConstantes.ESTADO_ACTIVO_CRITERIO_EVALUACION, IConstantes.TIPO_CRITERIO_EVALUACION_PROPUESTA_ENSAYO_CRITICO);
    if (_listaCriterioEvaluacionEnsayo == null || _listaCriterioEvaluacionEnsayo.isEmpty()) {
      _listaCriterioEvaluacionEnsayo = new ArrayList<CriterioEvaluacion>();
    }
  }

  /**
   *
   */
  private void cargarEvaluacionAnterior() throws JpaDinaeException {

    _evaluacionesEnsayo = _iEvaluacionProyecto.getCriterioPorEnsayoCritico(_ensayoItemSeleccionado.getIdEnsayoCritico());
    if (_evaluacionesEnsayo != null && !_evaluacionesEnsayo.isEmpty()) {

      BigDecimal suma100PorcientoValorIngresado = new BigDecimal(BigInteger.ZERO);

      eval:
      for (EvaluacionProyecto _eval : _evaluacionesEnsayo) {

        for (CriterioEvaluacion _criterio : _listaCriterioEvaluacionEnsayo) {

          if (_eval.getCriterioEvaluacion().getIdCriterioEvaluacion().compareTo(_criterio.getIdCriterioEvaluacion()) == 0) {

            BigDecimal evaluacion;

            if (_esEncargadoVicin) {

              evaluacion = (_eval.getValorCriterioVicin() == null) ? BigDecimal.ZERO : _eval.getValorCriterioVicin();
              _criterio.setEvaluacion((evaluacion.compareTo(BigDecimal.ZERO) == 0) ? null : evaluacion);

            } else {
              evaluacion = _eval.getValorCriterio();
              _criterio.setEvaluacion(evaluacion);
            }

            suma100PorcientoValorIngresado = suma100PorcientoValorIngresado.add(evaluacion);
            continue eval;
          }
        }
      }

      Long autorComentario;

      if (_esEncargadoVicin) {
        autorComentario = IConstantes.TIPO_AUTOR_COMENTARIO_EVALUACION_ENSAYO_VICIN;
      } else {
        autorComentario = IConstantes.TIPO_AUTOR_COMENTARIO_EVALUACION_ENSAYO_JEFE_UNIDAD;
      }

      _comentarioProyecto = _iComentarioProyecto.findComentarioByEnsayoAutor(_ensayoItemSeleccionado.getIdEnsayoCritico(), autorComentario);

      if (_esEncargadoVicin && _comentarioProyecto == null) {
        inicializarComentarioProyecto();
      }

      _evaluacionTotal = suma100PorcientoValorIngresado.toPlainString();

    } else {
      _evaluacionTotal = "0";
      _evaluacionesEnsayo = new ArrayList<EvaluacionProyecto>();
      for (CriterioEvaluacion _criterio : _listaCriterioEvaluacionEnsayo) {
        EvaluacionProyecto evaluacion = new EvaluacionProyecto();
        evaluacion.setEnsayoCritico(new EnsayoCritico(_ensayoItemSeleccionado.getIdEnsayoCritico()));
        evaluacion.setCriterioEvaluacion(_criterio);
        _evaluacionesEnsayo.add(evaluacion);
      }

      inicializarComentarioProyecto();
    }

  }

  private void inicializarComentarioProyecto() {

    PerfilUsuarioDTO perfilUsuarioDTO = loginFaces.getPerfilUsuarioDTO();
    Long autorComentario;
    RolUsuarioDTO rolUsuarioDTO;

    if (_esEncargadoVicin) {
      rolUsuarioDTO = perfilUsuarioDTO.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN);
      autorComentario = IConstantes.TIPO_AUTOR_COMENTARIO_EVALUACION_ENSAYO_VICIN;
    } else {
      rolUsuarioDTO = perfilUsuarioDTO.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_ENSAYOS_EN_LA_UNIDAD_POLICIAL);
      autorComentario = IConstantes.TIPO_AUTOR_COMENTARIO_EVALUACION_ENSAYO_JEFE_UNIDAD;
    }

    UsuarioRol usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

    _comentarioProyecto = new ComentarioProyecto();
    _comentarioProyecto.setEnsayoCritico(new EnsayoCritico(_ensayoItemSeleccionado.getIdEnsayoCritico()));
    _comentarioProyecto.setUsuarioRol(usuarioRol);
    _comentarioProyecto.setFechaRegistro(new Date());
    _comentarioProyecto.setIdentificacion(perfilUsuarioDTO.getIdentificacion());
    _comentarioProyecto.setNombreCompleto(perfilUsuarioDTO.getNombreCompleto());
    _comentarioProyecto.setAutorComentario(new Constantes(autorComentario));
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEstadosGanador() throws JpaDinaeException {

    if (_esEncargadoVicin) {
      _itemsListaEstados = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_ESTADO_EVALUACION_VICIN_ENSAYO);
    } else {
      _itemsListaEstados = _iConstantes.getConstantesPorTipo(IConstantes.TIPO_ESTADO_EVALUACION_UNIDAD_ENSAYO);
    }

    if (_itemsListaEstados == null || _itemsListaEstados.isEmpty()) {
      _itemsListaEstados = new ArrayList<Constantes>();
    }
  }

  /**
   *
   * @param _ensayos
   * @return
   */
  private Map<Long, List<EnsayoCritico>> mapaUnidaddes(List<EnsayoCritico> _ensayos) throws JpaDinaeException {

    Map<Long, List<EnsayoCritico>> _mapaEnsayos = new HashMap<Long, List<EnsayoCritico>>();

    for (EnsayoCritico e : _ensayos) {
      UnidadPolicialDTO unidadPolicial = _iUsuarioUnidadPolicial.findAllPorIdentificacion(e.getIdentificacion());

      if (unidadPolicial != null) {
        Long idUnidadEnsayo = unidadPolicial.getIdUnidadPolicial();
        if (_mapaEnsayos.containsKey(idUnidadEnsayo)) {
          e.setNombreUnidadPolicial(unidadPolicial.getNombre());
          e.setIdUnidadPolicial(idUnidadEnsayo);
          _mapaEnsayos.get(idUnidadEnsayo).add(e);
        } else {
          List<EnsayoCritico> valores = new ArrayList<EnsayoCritico>();
          e.setNombreUnidadPolicial(unidadPolicial.getNombre());
          e.setIdUnidadPolicial(idUnidadEnsayo);
          valores.add(e);
          _mapaEnsayos.put(idUnidadEnsayo, valores);
        }
      }

    }

    return _mapaEnsayos;
  }

  /**
   *
   */
  public void recargarValorTotal() {

    try {

      BigDecimal suma100PorcientoValorIngresado = new BigDecimal(BigInteger.ZERO);
      for (CriterioEvaluacion _eval : _listaCriterioEvaluacionEnsayo) {

        if (_eval.getEvaluacion() != null && _eval.getEvaluacion().compareTo(new BigDecimal(BigInteger.ZERO)) > 0) {

          if (_eval.getEvaluacion().compareTo(_eval.getValor()) > 0) {
            _eval.setEvaluacion(null);
            adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_lbl_criterio_excede_valor_maximo", new Object[]{_eval.getNombreCriterio()}));
            return;
          }
        } else {
          continue;
        }

        BigDecimal augend = (_eval.getEvaluacion() == null) ? new BigDecimal(BigInteger.ZERO) : _eval.getEvaluacion();
        suma100PorcientoValorIngresado = suma100PorcientoValorIngresado.add(augend);
      }

      _evaluacionTotal = suma100PorcientoValorIngresado.toPlainString();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (recargarValorTotal)", e);

    }

  }

  /**
   *
   * @return
   */
  public String guardarEvaluacion() {

    String navega = null;

    try {
      validarCamposEvaluacion();

      criterio:
      for (CriterioEvaluacion _criterio : _listaCriterioEvaluacionEnsayo) {
        for (EvaluacionProyecto _eval : _evaluacionesEnsayo) {
          if (_criterio.getIdCriterioEvaluacion().compareTo(_eval.getCriterioEvaluacion().getIdCriterioEvaluacion()) == 0) {
            if (_esEncargadoVicin) {
              _eval.setValorCriterioVicin(_criterio.getEvaluacion());
            } else {
              _eval.setValorCriterio(_criterio.getEvaluacion());
            }

            _iEvaluacionProyecto.guardarEvaluacionProyecto(_eval);
            continue criterio;
          }
        }
      }

      EnsayoCritico ensayoCritico = _iEnsayoCritico.findByIdEnsayoCritico(_ensayoItemSeleccionado.getIdEnsayoCritico());

      if (_esEncargadoVicin) {

        ensayoCritico.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN));
        ensayoCritico.setIdentificaEvaluaCuCo10(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        ensayoCritico.setNombreApellidoEvaluaCuCo10(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());

      } else {

        ensayoCritico.setEstadoCuCo8(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO));
        ensayoCritico.setIdentificaEvaluaCuCo8(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        ensayoCritico.setNombreApellidoEvaluaCuCo8(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());
      }

      _iEnsayoCritico.saveOrUpdate(ensayoCritico);

      if (_comentarioProyecto.getComentario() != null) {
        _iComentarioProyecto.guardarComentarioProyecto(_comentarioProyecto);
      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));
      navega = redireccionarEvaluarEnsayos(_periodo.getIdPeriodo());

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
    }

    return navega;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  public void validarCamposEvaluacion() throws JpaDinaeException {

    if (_listaCriterioEvaluacionEnsayo == null || _listaCriterioEvaluacionEnsayo.isEmpty()) {
      throw new JpaDinaeException("No existen criterios para realizar la evaluación");
    }

    for (CriterioEvaluacion _eval : _listaCriterioEvaluacionEnsayo) {
      if (_eval.getEvaluacion() == null) {
        throw new JpaDinaeException("El valor de la evaluación del criterio: " + _eval.getNombreCriterio() + " es obligatorio, se deben evaluar todos los criterios");
      }
    }
  }

  /**
   *
   */
  public void validarExistenciaCriteriosEvaluacion() {
    try {

      if (!getFacesContext().isPostback()) {

        cargarCriteriosEvaluacion();

        if (_listaCriterioEvaluacionEnsayo == null || _listaCriterioEvaluacionEnsayo.isEmpty()) {
          adicionaMensajeError(keyPropertiesFactory.value("No existen criterios para realizar la evaluación"));
          return;
        }

        cargarEvaluacionAnterior();
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param e
   */
  public void regresar(ActionEvent e) {
    cuCo9ConsultarDetallesEnsayoCriticoFaces.initReturnCU(_ensayoItemSeleccionado);
  }

  /**
   *
   * @return
   */
  public String irSeleccionarGanadores() {
    try {
      cargarDatosTablaEnsayos();
      _deshabilitarEdicion = false;
      _deshabilitarEdicionEnviar = false;

      String estadoEvaluado;
      if (_esEncargadoVicin) {
        estadoEvaluado = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN).getValor();
      } else {
        estadoEvaluado = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO).getValor();
      }

      String estadoGanador = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_GANADOR).getValor();
      String estadoNoAprobadoVicin = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO).getValor();

      int cont = 0;
      for (EnsayoCriticoUnidadView ecuv : _listaEnsayoCriticoUnidadView) {

        if (estadoEvaluado.equals(ecuv.getEstadoEnsayo()) || estadoGanador.equals(ecuv.getEstadoEnsayo())
                || estadoNoAprobadoVicin.equals(ecuv.getEstadoEnsayo())) {
          cont++;
        }

        if ((!_esEncargadoVicin && ecuv.getEnsayo().getEstadoCuCo10() != null) || (_esEncargadoVicin && (estadoGanador.equals(ecuv.getEstadoEnsayo())
                || estadoNoAprobadoVicin.equals(ecuv.getEstadoEnsayo())))) {
          _deshabilitarEdicionEnviar = true;
        }
      }

      cargarListaEstadosGanador();

      if (cont < _listaEnsayoCriticoUnidadView.size()) {
        _deshabilitarEdicion = true;
        adicionaMensajeAdvertencia("Para diligenciar este formulario se debe evaluar la totalidad de los ensayos de la unidad de cobertura");
      }

      return navigationFaces.redirectCuCo8SeleccionarGanadoresRedirect();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String guardarCalificacionEnsayos() {

    try {
      for (EnsayoCriticoUnidadView ecuv : _listaEnsayoCriticoUnidadView) {

        if (ecuv.getEstadoCalifacion().compareTo(-1L) != 0) {

          EnsayoCritico ensayo = ecuv.getEnsayo();

          if (_esEncargadoVicin) {
            ensayo.setCalificacionCuCo10(new Constantes(ecuv.getEstadoCalifacion()));
          } else {
            ensayo.setCalificacionCuCo8(new Constantes(ecuv.getEstadoCalifacion()));
          }
          _iEnsayoCritico.saveOrUpdate(ensayo);
        }
      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @param e
   */
  @SuppressWarnings("empty-statement")
  public void enviarEnsayosGanadoresVicin(ActionEvent e) {

    try {

      if (!validarSeleccionGanadoresEnvioVicin()) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_co_8_mensaje_asignar_msg_ensayo"));
        return;
      }

      Long estadoAprobado;

      if (_esEncargadoVicin) {

        estadoAprobado = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_EVALUACION_VICIN_ENSAYO_GANADOR).getIdConstantes();

      } else {

        estadoAprobado = _iConstantes.getConstantesPorIdConstante(IConstantes.TIPO_ESTADO_EVALUACION_UNIDAD_ENSAYO_APROBADO).getIdConstantes();
      }

      List<String> correosUsuarios = new ArrayList<String>();
      List<EnsayoCritico> ensayosRollback = new ArrayList<EnsayoCritico>();

      for (EnsayoCriticoUnidadView ecuv : _listaEnsayoCriticoUnidadView) {

        EnsayoCritico ensayo = ecuv.getEnsayo();

        EnsayoCritico ensayoRollback = ecuv.getEnsayo();
        ensayosRollback.add(ensayoRollback);

        if (_esEncargadoVicin) {

          ensayo.setCalificacionCuCo10(new Constantes(ecuv.getEstadoCalifacion()));

          if (estadoAprobado.compareTo(ecuv.getEstadoCalifacion()) == 0) {
            ensayo.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_GANADOR));
          } else {
            ensayo.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO));
          }

          VistaFuncionario funcionario = _iVistaFuncionario.getVistaFuncionarioPorIdentificacion(ensayo.getIdentificacion());

          if (funcionario.getCorreo() != null) {
            correosUsuarios.add(funcionario.getCorreo());
          }

        } else {

          ensayo.setCalificacionCuCo8(new Constantes(ecuv.getEstadoCalifacion()));

          if (estadoAprobado.compareTo(ecuv.getEstadoCalifacion()) == 0) {
            ensayo.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_ENVIADO_VICIN));
          } else {
            ensayo.setEstadoCuCo8(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO_UNIDAD));
          }
        }

        _iEnsayoCritico.saveOrUpdate(ensayo);
      }

      String nombreReporte;
      //EL ENVIO DE CORREO NO DEBE AFECTAR EL PROCESO
      try {

        List<Long> listaIdRoles = new ArrayList<Long>();
        String codigoMail;
        Map<String, Object> parametrosContenido = new HashMap<String, Object>();

        if (!_esEncargadoVicin) {

          listaIdRoles.add(IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN);
          UnidadPolicialDTO unidad = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial();

          codigoMail = IConstantes.CU_CO_08_INFORMAR_ENVIO_ENSAYOS_PREVIO_REVISA;
          parametrosContenido.put("{_parametro1_}", unidad.getNombre());

        } else {

          listaIdRoles.add(IConstantesRole.ENCARGADO_DE_ENSAYOS_EN_LA_UNIDAD_POLICIAL);
          codigoMail = IConstantes.CU_CO_10_INFORMAR_RESULTADO_EVALUACION_ENSAYOS;

          byte[] reportByteArray;
          try {

            _iEnsayoCritico.ejecutarProcedimientoResultadoConvocatoriaEnsayo(_periodo.getIdPeriodo());

            HashMap mapa = new HashMap();
            mapa.put("P_NUMERO_CONVOCATORIA", _periodo.getConcecutivo().toString());
            mapa.put("P_FECHA_PUBLICACION", new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));

            reportByteArray = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "/reporteCU_CO_10.jasper");

            String nombreReporteUnico = "CONVOCA_ENSAYO_CRITICO_".concat(_periodo.getConcecutivo().toString()).concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
            nombreReporte = "CONV-ENS_" + _periodo.getConcecutivo().toString() + (_periodo.getAnio() != null ? _periodo.getAnio() : "") + ".pdf";

            copiarArchivoRutaFisica(
                    keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general"),
                    new ByteArrayInputStream(reportByteArray),
                    nombreReporteUnico);

            //GUARDAMOS EL REPORTE
            _iPeriodo.actualizarConvocatoriaReporteEnsayoCritico(_periodo.getIdPeriodo(), nombreReporteUnico, nombreReporte);

          } catch (Exception ex) {

            if (!ensayosRollback.isEmpty()) {
              for (EnsayoCritico rollback : ensayosRollback) {

                rollback.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN));
                _iEnsayoCritico.saveOrUpdate(rollback);
              }
            }

            adicionaMensajeError("ERROR, Se presentaron errores al general el reporte JASPER ");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-10(publicarResultadoConvocatoriaEnsayoCritico)", ex);

            return;
          }

          parametrosContenido.put(IConstantes.CONTENIDO_ADJUNTO_MAIL, new ArchivoReporte(reportByteArray, nombreReporte));

          if (!correosUsuarios.isEmpty()) {

            try {

              _iMailSessionBean.enviarMail_ListaCorreo(codigoMail, null, parametrosContenido, correosUsuarios);

              if (!listaIdRoles.isEmpty()) {

                _iMailSessionBean.enviarMail_ListaRoles(codigoMail, null, parametrosContenido, listaIdRoles);

              }

            } catch (Exception ex1) {

              Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex1);

            }
          }
        }

      } catch (Exception ex1) {

        if (!ensayosRollback.isEmpty()) {
          for (EnsayoCritico rollback : ensayosRollback) {
            rollback.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN));
            _iEnsayoCritico.saveOrUpdate(rollback);
          }
        }

        Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex1);
        return;

      }

      _deshabilitarEdicionEnviar = true;
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo8EvaluacionEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private boolean validarSeleccionGanadoresEnvioVicin() {

    for (EnsayoCriticoUnidadView ecuv : _listaEnsayoCriticoUnidadView) {
      if (ecuv.getEstadoCalifacion().compareTo(-1L) == 0) {
        return false;
      }
    }

    return true;
  }

  /**
   *
   * @return
   */
  private boolean determinarSeleccionGanadores() throws JpaDinaeException {

    Long estadoEvaluado = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO;
    Long estadoEvaluadoVicin = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN;

    boolean mostrarLink = false;
    int cont = 0;

    for (EnsayoCritico ecuv : _listadoEnsayoCriticoUnidad) {

      if (_esEncargadoVicin) {
        if ((ecuv.getEstadoCuCo10() != null && ecuv.getEstadoCuCo10().getIdConstantes().compareTo(estadoEvaluadoVicin) == 0)) {
          cont++;
        }

      } else if (ecuv.getEstadoCuCo8().getIdConstantes().compareTo(estadoEvaluado) == 0
              && (ecuv.getEstadoCuCo10() == null && ecuv.getCalificacionCuCo10() == null)) {
        cont++;
      }
    }

    if (cont == _listaEnsayoCriticoUnidadView.size()) {
      mostrarLink = true;
      adicionaMensajeAdvertencia("Para diligenciar este formulario se debe evaluar la totalidad de los ensayos de la unidad de cobertura");
    }

    return mostrarLink;

  }

  /**
   * Ya no ha seleccionado un periodo
   *
   * @param event
   */
  public void noSeleccionEnsayoEvaluacion(UnselectEvent event) {
    _ensayoItemSeleccionado = null;
  }

  public List<EnsayoCritico> getListadoEnsayoCriticoUnidad() {
    return _listadoEnsayoCriticoUnidad;
  }

  public void setListadoEnsayoCriticoUnidad(List<EnsayoCritico> _listadoEnsayoCriticoUnidad) {
    this._listadoEnsayoCriticoUnidad = _listadoEnsayoCriticoUnidad;
  }

  public ListGenericDataModel getListadoEnsayoCriticoUnidadModel() {
    return _listadoEnsayoCriticoUnidadModel;
  }

  public void setListadoEnsayoCriticoUnidadModel(ListGenericDataModel _listadoEnsayoCriticoUnidadModel) {
    this._listadoEnsayoCriticoUnidadModel = _listadoEnsayoCriticoUnidadModel;
  }

  public List<EnsayoCriticoUnidadView> getListaEnsayoCriticoUnidadView() {
    return _listaEnsayoCriticoUnidadView;
  }

  public void setListaEnsayoCriticoUnidadView(List<EnsayoCriticoUnidadView> _listaEnsayoCriticoUnidadView) {
    this._listaEnsayoCriticoUnidadView = _listaEnsayoCriticoUnidadView;
  }

  public EnsayoCriticoUnidadView getEnsayoItemSeleccionado() {
    return _ensayoItemSeleccionado;
  }

  public void setEnsayoItemSeleccionado(EnsayoCriticoUnidadView _ensayoItemSeleccionado) {
    this._ensayoItemSeleccionado = _ensayoItemSeleccionado;
  }

  public List<CriteriosEvaluacionEnsayosView> getListaCriteriosEnsayos() {
    return _listaCriteriosEnsayos;
  }

  public void setListaCriteriosEnsayos(List<CriteriosEvaluacionEnsayosView> _listaCriteriosEnsayos) {
    this._listaCriteriosEnsayos = _listaCriteriosEnsayos;
  }

  public String getComentariosEvaluacion() {
    return _comentariosEvaluacion;
  }

  public void setComentariosEvaluacion(String _comentariosEvaluacion) {
    this._comentariosEvaluacion = _comentariosEvaluacion;
  }

  public List<Constantes> getItemsListaEstados() {
    return _itemsListaEstados;
  }

  public void setItemsListaEstados(List<Constantes> _itemsListaEstados) {
    this._itemsListaEstados = _itemsListaEstados;
  }

  public List<CriterioEvaluacion> getListaCriterioEvaluacionEnsayo() {
    return _listaCriterioEvaluacionEnsayo;
  }

  public void setListaCriterioEvaluacionEnsayo(List<CriterioEvaluacion> _listaCriterioEvaluacionEnsayo) {
    this._listaCriterioEvaluacionEnsayo = _listaCriterioEvaluacionEnsayo;
  }

  public String getEvaluacionTotal() {
    return _evaluacionTotal;
  }

  public void setEvaluacionTotal(String _evaluacionTotal) {
    this._evaluacionTotal = _evaluacionTotal;
  }

  public ComentarioProyecto getComentarioProyecto() {
    return _comentarioProyecto;
  }

  public void setComentarioProyecto(ComentarioProyecto _comentarioProyecto) {
    this._comentarioProyecto = _comentarioProyecto;
  }

  public boolean isDeshabilitarEdicion() {
    return _deshabilitarEdicion;
  }

  public void setDeshabilitarEdicion(boolean _deshabilitarEdicion) {
    this._deshabilitarEdicion = _deshabilitarEdicion;
  }

  public boolean isDeshabilitarEdicionEnviar() {
    return _deshabilitarEdicionEnviar;
  }

  public void setDeshabilitarEdicionEnviar(boolean _deshabilitarEdicionEnviar) {
    this._deshabilitarEdicionEnviar = _deshabilitarEdicionEnviar;
  }

  public boolean isEsEncargadoVicin() {
    return _esEncargadoVicin;
  }

  public void setEsEncargadoVicin(boolean _esEncargadoVicin) {
    this._esEncargadoVicin = _esEncargadoVicin;
  }

  private boolean isFechaVencimuientoConvocatoria() {
    //RETORNA TRUE CUANDO LA FECHA FIN SEA MENOR A LA FECHA DEL SISTEMA
    return _periodo != null && _periodo.getFechaFin() != null && (DatesUtils.compareDate(_periodo.getFechaFin(), new Date()) == -1);
  }

  public boolean isMostrarLinkGanadores() {
    return _mostrarLinkGanadores && isFechaVencimuientoConvocatoria();
  }

  public void setMostrarLinkGanadores(boolean _mostrarLinkGanadores) {
    this._mostrarLinkGanadores = _mostrarLinkGanadores;
  }

  public List<Periodo> getListaConvocatoriasEnsayoCritico() {
    return _listaConvocatoriasEnsayoCritico;
  }

  public void setListaConvocatoriasEnsayoCritico(List<Periodo> _listaConvocatoriasEnsayoCritico) {
    this._listaConvocatoriasEnsayoCritico = _listaConvocatoriasEnsayoCritico;
  }

  public ListGenericDataModel getListaConvocatoriasEnsayoCriticoModel() {
    return _listaConvocatoriasEnsayoCriticoModel;
  }

  public void setListaConvocatoriasEnsayoCriticoModel(ListGenericDataModel _listaConvocatoriasEnsayoCriticoModel) {
    this._listaConvocatoriasEnsayoCriticoModel = _listaConvocatoriasEnsayoCriticoModel;
  }

  public List<EvaluacionProyecto> getEvaluacionesEnsayo() {
    return _evaluacionesEnsayo;
  }

  public void setEvaluacionesEnsayo(List<EvaluacionProyecto> _evaluacionesEnsayo) {
    this._evaluacionesEnsayo = _evaluacionesEnsayo;
  }

  public Periodo getPeriodo() {
    return _periodo;
  }

  public void setPeriodo(Periodo _periodo) {
    this._periodo = _periodo;
  }

  public boolean isEsOrigenCuCo8() {
    return _esOrigenCuCo8;
  }

  public void setEsOrigenCuCo8(boolean _esOrigenCuCo8) {
    this._esOrigenCuCo8 = _esOrigenCuCo8;
  }
}
