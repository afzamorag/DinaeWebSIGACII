package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.PeriodoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ITipoUnidadLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.UnidadPolicialPeriodo;
import co.gov.policia.dinae.modelo.TipoUnidad;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuCo1GestionarConvocatoriasFaces")
@javax.enterprise.context.SessionScoped
public class CuCo1GestionarConvocatoriasFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuCo4EvaluarPropuestaConvocatoriaFaces co4EvaluarPropuestaConvocatoriaFaces;

  private List<PeriodoDTO> listaConvocatoriaFinanciacion;
  private List<PeriodoDTO> listaConvocatoriaEnsayo;

  private Periodo convocatoriaSeleccionada;
  private Long idTipoConvocatoriaSeleccionada;
  private Long concecutivoConvocatoria;

  private FileUploadEvent eventArchivoSubido;
  private String nombreArchivoSubido;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private ITipoUnidadLocal iTipoUnidadLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @Inject
  private CuCo8EvaluacionEnsayoCritico cuCo8EvaluacionEnsayoCritico;

  @EJB
  private IEnsayoCriticoLocal _iEnsayoCritico;

  private List<SelectItem> listaTipoConvocatoriaItem;

  private static final List<Long> listaIdEstadoConvocatoriaNuevaPublicadaYabierta = new ArrayList<Long>();

  static {
    listaIdEstadoConvocatoriaNuevaPublicadaYabierta.add(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA);
    listaIdEstadoConvocatoriaNuevaPublicadaYabierta.add(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA);
    listaIdEstadoConvocatoriaNuevaPublicadaYabierta.add(IConstantes.TIPO_ESTADO_CONVOCATORIA_ABIERTA);
  }

  private List<UnidadPolicialDTO> listaUnidadesPolicialesDTOperiodo;

  private static final List<Long> listaIdEstadoPropuestaConvocatoriaEnviadaVicinYevaluada = new ArrayList<Long>();

  static {
    listaIdEstadoPropuestaConvocatoriaEnviadaVicinYevaluada.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ENVIADA_A_VICIN);
    listaIdEstadoPropuestaConvocatoriaEnviadaVicinYevaluada.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EVALUADA);
  }

  private static final List<Long> listaRoleCorreoIdRolJefesUnidad = new ArrayList<Long>();

  static {
    listaRoleCorreoIdRolJefesUnidad.add(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);
    listaRoleCorreoIdRolJefesUnidad.add(IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL);
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    return navigationFaces.redirectCuCo1GestionarConvocatoriasRedirect();

  }

  @javax.annotation.PostConstruct
  public void init() {
    try {

      limpiarNuevaConvocatoria();

      //CONSULTAMOS LA LISTA DE CONVOCATORIAS POR SEPARADO
      //SE MUESTRA DE MANERA INDEPENDIENTE  LA LISTA DE LAS CONVOCATORIAS PARA FINANCIACIÓN Y
      //LA LISTA DE CONVOCATORIAS PARA CONCURSO DE ENSAYO CRÍTICO VIGENTES
      listaConvocatoriaFinanciacion = iPeriodoLocal.getListaConvocatoriasPorTipoConvocatoria(
              IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION,
              listaIdEstadoConvocatoriaNuevaPublicadaYabierta);

      listaConvocatoriaEnsayo = iPeriodoLocal.getListaConvocatoriasPorTipoConvocatoria(
              IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO,
              listaIdEstadoConvocatoriaNuevaPublicadaYabierta);

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (init)", e);
    }
  }

  /**
   *
   */
  private void limpiarNuevaConvocatoria() {

    listaUnidadesPolicialesDTOperiodo = new ArrayList<UnidadPolicialDTO>();
    idTipoConvocatoriaSeleccionada = null;
    convocatoriaSeleccionada = new Periodo();
    concecutivoConvocatoria = null;
    nombreArchivoSubido = null;
    eventArchivoSubido = null;
  }

  /**
   *
   */
  private boolean cargarDatosFormularioCovocatoria() {

    try {

      //SE CARGA LA LISTA DE TIPOS CONVOCATORIAS
      listaTipoConvocatoriaItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_CONVOCATORIA_GESTION),
              "idConstantes",
              "valor");

      listaUnidadesPolicialesDTOperiodo = iUnidadPolicialLocal.getAllUnidadesPolicialesActivasDTO();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-01 Gestionar convocatorias (limpiarNuevaCovocatoria)", e);

      return false;
    }
    return true;
  }

  /**
   *
   * @param convocatoriaSeleccionadaDTO
   * @return
   */
  public String modificarConvocatoriaFinanciaEnsayo(PeriodoDTO convocatoriaSeleccionadaDTO) {

    try {

      if (!validaPuedeModificarConvocatoria(convocatoriaSeleccionadaDTO)) {

        //El sistema muestra el mensaje de error :  No se puede modificar una convocatoria que se encuentra en estado  'Culminada'
        adicionaMensajeError(keyPropertiesFactory.value("cu_co_1_lbl_error_no_puede_mod_convo_estado_culminada"));
        return null;

      }

      //LIMPIAMOS LAS VARIABLES
      limpiarNuevaConvocatoria();

      //CARGAR DATOS QUE SE MUESTRAN EL FORMULARIO
      boolean retornoErrores = cargarDatosFormularioCovocatoria();
      if (!retornoErrores) {
        //EXISTIERON ERRORES
        return null;
      }

      this.convocatoriaSeleccionada = iPeriodoLocal.getPeriodoPorId(convocatoriaSeleccionadaDTO.getIdPeriodo());
      this.idTipoConvocatoriaSeleccionada = convocatoriaSeleccionada.getTipoPeriodo().getIdConstantes();
      this.concecutivoConvocatoria = convocatoriaSeleccionada.getConcecutivo();

      nombreArchivoSubido = convocatoriaSeleccionada.getNombreArchivo();

      //CARGAMOS LA UNIDADES
      listaUnidadesPolicialesDTOperiodo = iUnidadPolicialLocal.getAllUnidadesPolicialesActivasDTO();
      //BUSCAMOS LAS UNIDADES SELECCIONADAS
      List<Long> lista = iPeriodoLocal.getIdsUnidadesPolicialesPorPeriodo(convocatoriaSeleccionadaDTO.getIdPeriodo());
      for (Long unIdPeriodoSeleccionado : lista) {
        for (UnidadPolicialDTO unaUnidadDTO : listaUnidadesPolicialesDTOperiodo) {
          if (unIdPeriodoSeleccionado.equals(unaUnidadDTO.getIdUnidadPolicial())) {
            unaUnidadDTO.setSeleccionado(true);
            break;
          }
        }
      }

      return navigationFaces.redirectCuCo1RegistraConvocatoriasRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-01 Gestionar convocatorias (modificarConvocatoriaFinancia)", e);
    }

    return null;
  }

  /**
   *
   * @param convocatoriaSeleccionada
   * @return
   */
  public String revisarConvocatoriaFinancia(PeriodoDTO convocatoriaSeleccionada) {

    try {

      //PRIMERO VERIFICAMOS QUE EXISTAN PROPUESTAS E VALUAR
      int cuenta = iProyectoLocal.contarProyectoPropuestaConvocatoriaDTOPorPeriodoYlistaEstado(
              convocatoriaSeleccionada.getIdPeriodo(),
              listaIdEstadoPropuestaConvocatoriaEnviadaVicinYevaluada);

      if (cuenta == 0) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_co_1_lbl_err_no_existen_propuestas_convoca_evalua"));
        return null;
      }
      Periodo periodo = iPeriodoLocal.getPeriodoPorId(convocatoriaSeleccionada.getIdPeriodo());

      return co4EvaluarPropuestaConvocatoriaFaces.initReturnCU(periodo);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (revisarConvocatoriaFinancia)", e);
    }

    return null;
  }

  /**
   *
   * @param convocatoriaSeleccionada
   * @return
   */
  public String revisarConvocatoriaEnsayo(PeriodoDTO convocatoriaSeleccionada) {

    try {

      List<Long> idEstados = cuCo8EvaluacionEnsayoCritico.tomarFiltrosUsuarioRol(loginFaces.getPerfilUsuarioDTO());
      int cuenta = _iEnsayoCritico.findByPeriodoAllUnidades(idEstados, convocatoriaSeleccionada.getIdPeriodo()).size();

      if (cuenta == 0) {
        adicionaMensajeError("No existen ensayos a evaluar.");
        return null;
      }

      return cuCo8EvaluacionEnsayoCritico.redireccionarEvaluarEnsayos(convocatoriaSeleccionada.getIdPeriodo());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 ", e);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public boolean isMostrarLinkAdicionarConvocatoriaFinancia() {

    //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA EL ROL DE: ENCARGADO_DE_CONVOCATORIAS_EN_VICIN
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return false;
    }
    return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ENCARGADO_DE_CONVOCATORIAS_EN_VICIN);
  }

  public boolean isMostrarBtnModificarConvocatoriaFinancia() {

    //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA EL ROL DE: ENCARGADO_DE_CONVOCATORIAS_EN_VICIN
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return false;
    }
    return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ENCARGADO_DE_CONVOCATORIAS_EN_VICIN);
  }

  public boolean mostrarBtnRevisarConvocatoriaFinancia(PeriodoDTO convocatoria) {

    //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA EL ROL DE: ENCARGADO_DE_CONVOCATORIAS_EN_VICIN
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return false;
    }
    return convocatoria.getIdEstadoConvocatorio().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)
            && loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_CONVOCATORIAS_EN_LA_VICIN);

  }

  public boolean mostrarBtnRevisarConvocatoriaEnsayo(PeriodoDTO convocatoria) {

    //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA EL ROL DE: ENCARGADO_DE_CONVOCATORIAS_EN_VICIN
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return false;
    }
    return convocatoria.getIdEstadoConvocatorio().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)
            && loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN);

  }

  /**
   *
   * @return true o false si el proceso de guardar se ejecuto correctamente
   */
  private boolean validarYGuardarConvocatoria(Long idEstadoConvocatoria) {

    try {
      Date fechaHoy = new Date();
      //VALIDACIONES
      //FECHA INICIO DEBE SER MAYOR A LA FECHA DEL SISTEMA.
      if (convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA)) {

        if (co.gov.policia.dinae.util.DatesUtils.compareDate(convocatoriaSeleccionada.getFechaInicio(), fechaHoy) == -1) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_co_1_lbl_err_fecha_inicio_mayor"));
          return false;
        }
      }

      //FECHA FIN DEBE SER MAYOR A LA FECHA DEL SISTEMA.
      if (co.gov.policia.dinae.util.DatesUtils.compareDate(convocatoriaSeleccionada.getFechaFin(), fechaHoy) == -1) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_co_1_lbl_err_fecha_fin_mayor"));
        return false;
      }
      //VALIDAMOS QUE LA FECHA FINAL NO SEA MENOR A LA FECHA INICIAL
      if (co.gov.policia.dinae.util.DatesUtils.compareDate(convocatoriaSeleccionada.getFechaFin(), convocatoriaSeleccionada.getFechaInicio()) == -1) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_co_1_lbl_err_fecha_fin_mayor_a_fecha_inicial"));
        return false;
      }
      //VALIDAMOS QUE LAS CONDICIONES DE LA CONVOCATORIA EXISTA
      if (convocatoriaSeleccionada.getIdPeriodo() == null && eventArchivoSubido == null) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_co_1_lbl_err_archivo_condicion_convocatoria"));
        return false;

      }

      if (getContadorUnidadesSeleccionadas() == 0) {
        adicionaMensajeError("No se han seleccionado las unidades para esta convocatoria.");
        return false;
      }
      convocatoriaSeleccionada.setConcecutivo(concecutivoConvocatoria);
      convocatoriaSeleccionada.setEstadoConvocatorio(new Constantes(idEstadoConvocatoria));

      if (convocatoriaSeleccionada.getIdPeriodo() == null) {

        convocatoriaSeleccionada.setIdentificadorUsuarioCrea(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

      } else {

        convocatoriaSeleccionada.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

      }

      convocatoriaSeleccionada.setTipoPeriodo(new Constantes(idTipoConvocatoriaSeleccionada));

      List<TipoUnidadPeriodo> listaTipoUnidadConvocatoria = new ArrayList<TipoUnidadPeriodo>();

      Long idTipoUnidadUnidaPolicial = null;
      Long idTipoUnidadOtros = null;
      for (UnidadPolicialDTO unidadPolicialDTO : listaUnidadesPolicialesDTOperiodo) {

        if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS)) {
          idTipoUnidadUnidaPolicial = IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS;
        } else if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_OTROS)) {
          idTipoUnidadOtros = IConstantes.TIPO_UNIDAD_POLICIAL_OTROS;
        }

        if (idTipoUnidadOtros != null && idTipoUnidadUnidaPolicial != null) {
          break;
        }

      }

      if (idTipoUnidadUnidaPolicial != null) {
        listaTipoUnidadConvocatoria.add(new TipoUnidadPeriodo(
                new TipoUnidad(idTipoUnidadUnidaPolicial),
                convocatoriaSeleccionada));
      }
      if (idTipoUnidadOtros != null) {
        listaTipoUnidadConvocatoria.add(new TipoUnidadPeriodo(
                new TipoUnidad(idTipoUnidadOtros),
                convocatoriaSeleccionada));
      }

      List<UnidadPolicialPeriodo> listaUnidadPolicialConvocatoria = new ArrayList<UnidadPolicialPeriodo>();

      for (UnidadPolicialDTO unaUnidadDTO : listaUnidadesPolicialesDTOperiodo) {

        if (unaUnidadDTO.isSeleccionado()) {

          listaUnidadPolicialConvocatoria.add(new UnidadPolicialPeriodo(
                  iUnidadPolicialLocal.obtenerUnidadPolicialPorId(unaUnidadDTO.getIdUnidadPolicial()),
                  convocatoriaSeleccionada));
        }

      }

      String[] archivo = {null, null};
      if (eventArchivoSubido != null) {
        archivo = almacenarArchivoFisico();
      }

      convocatoriaSeleccionada.setNombreArchivo(archivo[0] != null ? archivo[0] : convocatoriaSeleccionada.getNombreArchivo());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      convocatoriaSeleccionada.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : convocatoriaSeleccionada.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

      //SETEAMOS LAS LISTAS
      convocatoriaSeleccionada.setTipoUnidadPeriodoList(listaTipoUnidadConvocatoria);
      convocatoriaSeleccionada.setUnidadPolicialPeriodoList(listaUnidadPolicialConvocatoria);

      //GUARDAMOS LA CONVOCATORIA
      convocatoriaSeleccionada = iPeriodoLocal.guardarConvocatoria(convocatoriaSeleccionada);

      return true;

    } catch (Exception e) {

      convocatoriaSeleccionada.setIdPeriodo(null);

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (validarYGuardarConvocatoria)", e);
    }

    return false;

  }

  /**
   *
   * @return
   */
  public String guardarConvocatoria() {

    try {

      //VALIDAMOS Y GUARDAMOS
      if (!validarYGuardarConvocatoria(convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes())) {
        //OCURRIERON ERRORES
        return null;
      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_co_1_lbl_convocatoria_almacenada_ok"));

      //ACTUALIZAMOS LAS LISTA DE CONVOCATORIA
      init();

      return navigationFaces.redirectCuCo1AgregarConvocatoriasRedirect();

    } catch (Exception e) {

      convocatoriaSeleccionada.setIdPeriodo(null);

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (guardarConvocatoria)", e);

    }
    return null;

  }

  /**
   *
   * @return
   */
  public String publicarConvocatoria() {

    try {

      //VALIDAMOS Y GUARDAMOS
      if (!validarYGuardarConvocatoria(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)) {
        //OCURRIERON ERRORES
        return null;
      }

      Long idTipoUnidadUnidaPolicial = null;
      Long idTipoUnidadOtros = null;
      for (UnidadPolicialDTO unidadPolicialDTO : listaUnidadesPolicialesDTOperiodo) {

        if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS)) {
          idTipoUnidadUnidaPolicial = IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS;
        } else if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_OTROS)) {
          idTipoUnidadOtros = IConstantes.TIPO_UNIDAD_POLICIAL_OTROS;
        }

        if (idTipoUnidadOtros != null && idTipoUnidadUnidaPolicial != null) {
          break;
        }

      }

      List<Long> listaIdTipoUnidades = new ArrayList<Long>();
      if (idTipoUnidadUnidaPolicial != null) {
        listaIdTipoUnidades.add(idTipoUnidadUnidaPolicial);
      }
      if (idTipoUnidadOtros != null) {
        listaIdTipoUnidades.add(idTipoUnidadOtros);
      }

      try {

        //ENVIA CORREO A LOS JEFES DE LAS UNIDADES
        Map<String, String> parametrosAsunto = new HashMap<String, String>();
        parametrosAsunto.put("{_parametro1_}", String.valueOf(convocatoriaSeleccionada.getConcecutivo()));
        parametrosAsunto.put("{_parametro2_}", convocatoriaSeleccionada.getNombreConvocatorio());

        Map<String, Object> parametrosContenido = new HashMap<String, Object>();
        parametrosContenido.put("{_parametro1_}", UtilidadesItem.getFechaFormateadaFormatoCorto(convocatoriaSeleccionada.getFechaInicio()));
        parametrosContenido.put("{_parametro2_}", UtilidadesItem.getFechaFormateadaFormatoCorto(convocatoriaSeleccionada.getFechaFin()));

        //EL ENVIO DE CORREO DEPENDE DEL TIPO DE CONVOCATORIA
        if (IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION.equals(idTipoConvocatoriaSeleccionada)) {

          iMailSessionBean.enviarMail_ListaTipoUnidades_ListaRoles(
                  IConstantes.CU_CO_01_PUBLICACION_CONVOCATORIA_FINANCIACION,
                  parametrosAsunto,
                  parametrosContenido,
                  listaIdTipoUnidades,
                  listaRoleCorreoIdRolJefesUnidad);

        } else if (IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO.equals(idTipoConvocatoriaSeleccionada)) {

          List<String> listaCorreos = iVistaFuncionarioLocal.getListaTodosCorreos();

          iMailSessionBean.enviarMail_ListaCorreo(
                  IConstantes.CU_CO_01_PUBLICACION_CONVOCATORIA_ENSAYO,
                  parametrosAsunto,
                  parametrosContenido,
                  listaCorreos);

        }

      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 ", e);
      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_co_1_lbl_convocatoria_almacenada_ok"));

      //ACTUALIZAMOS LAS LISTA DE CONVOCATORIA
      init();

      return navigationFaces.redirectCuCo1AgregarConvocatoriasRedirect();

    } catch (Exception e) {

      convocatoriaSeleccionada.setIdPeriodo(null);

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (publicarConvocatoria)", e);

    }
    return null;

  }

  /**
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico() {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      if (eventArchivoSubido != null) {

        String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = "CONVOCATORIA_".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_co_1_dir_file_archivo_soporte"), eventArchivoSubido.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-01 Gestionar convocatorias( guardarPropuesta )", e);
    }

    return null;

  }

  public String agreagaNuevaConvocatoria() {

    //LIMPIAMOS LAS VARIABLES
    limpiarNuevaConvocatoria();

    //CARGAR DATOS QUE SE MUESTRAN EL FORMULARIO
    boolean retornoErrores = cargarDatosFormularioCovocatoria();
    if (!retornoErrores) {
      //EXISTIERON ERRORES
      return null;
    }

    convocatoriaSeleccionada.setEstadoConvocatorio(new Constantes(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA));

    return navigationFaces.redirectCuCo1RegistraConvocatoriasRedirect();
  }

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      eventArchivoSubido = event;
      nombreArchivoSubido = event.getFile().getFileName();
      adicionaMensajeInformativo("El archivo " + nombreArchivoSubido + " fue cargado correctamente, los cambios se verán reflejados al guardar.");

    } catch (Exception e) {
      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-01 Gestionar convocatorias( handleFileUpload )", e);
    }
  }

  public List<PeriodoDTO> getListaConvocatoriaFinanciacion() {
    return listaConvocatoriaFinanciacion;
  }

  public void setListaConvocatoriaFinanciacion(List<PeriodoDTO> listaConvocatoriaFinanciacion) {
    this.listaConvocatoriaFinanciacion = listaConvocatoriaFinanciacion;
  }

  public List<PeriodoDTO> getListaConvocatoriaEnsayo() {
    return listaConvocatoriaEnsayo;
  }

  public void setListaConvocatoriaEnsayo(List<PeriodoDTO> listaConvocatoriaEnsayo) {
    this.listaConvocatoriaEnsayo = listaConvocatoriaEnsayo;
  }

  public Periodo getConvocatoriaSeleccionada() {
    return convocatoriaSeleccionada;
  }

  public void setConvocatoriaSeleccionada(Periodo convocatoriaSeleccionada) {
    this.convocatoriaSeleccionada = convocatoriaSeleccionada;
  }

  public Long getIdTipoConvocatoriaSeleccionada() {
    return idTipoConvocatoriaSeleccionada;
  }

  public void setIdTipoConvocatoriaSeleccionada(Long idTipoConvocatoriaSeleccionada) {
    this.idTipoConvocatoriaSeleccionada = idTipoConvocatoriaSeleccionada;
  }

  public List<SelectItem> getListaTipoConvocatoriaItem() {
    return listaTipoConvocatoriaItem;
  }

  public void setListaTipoConvocatoriaItem(List<SelectItem> listaTipoConvocatoriaItem) {
    this.listaTipoConvocatoriaItem = listaTipoConvocatoriaItem;
  }

  public Long getConcecutivoConvocatoria() {
    return concecutivoConvocatoria;
  }

  public void setConcecutivoConvocatoria(Long concecutivoConvocatoria) {
    this.concecutivoConvocatoria = concecutivoConvocatoria;
  }

  public ITipoUnidadLocal getiTipoUnidadLocal() {
    return iTipoUnidadLocal;
  }

  public void setiTipoUnidadLocal(ITipoUnidadLocal iTipoUnidadLocal) {
    this.iTipoUnidadLocal = iTipoUnidadLocal;
  }

  /**
   *
   * @return
   */
  private boolean validaPuedeModificarConvocatoria(PeriodoDTO convocatoriaSeleccionada) {

    if (convocatoriaSeleccionada == null) {
      return false;
    }
    //SE VALIDA QUE LA CONVOCATORIA ESCOGIDA PARA MODIFICAR SE ENCUENTRE EN ESTADO  
    //‘NUEVA’, ‘PUBLICADA’ O ‘ABIERTA’ 
    //Y EL SISTEMA MUESTRA LOS DATOS ASOCIADOS A LA 
    //CONVOCATORIA SELECCIONADA, GRUPO DE DATOS [3].  
    return convocatoriaSeleccionada.getIdEstadoConvocatorio().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA)
            || convocatoriaSeleccionada.getIdEstadoConvocatorio().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)
            || convocatoriaSeleccionada.getIdEstadoConvocatorio().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_ABIERTA);

  }

  public boolean isPuedeSoloFechaFin() {
    //SI LA CONVOCATORIA SE ENCUENTRA EN ESTADO ‘PUBLICADA’ O ‘ABIERTA’ 
    //EL SISTEMA SÓLO PERMITE LA MODIFICACIÓN DE LA FECHA FIN. 
    if (convocatoriaSeleccionada == null) {
      return false;
    }

    return convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)
            || convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA)
            || convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_ABIERTA);

  }

  public boolean isPuedeEditarCamposConvocatoria() {
    //SI LA CONVOCATORIA SE ENCUENTRA EN ESTADO ‘PUBLICADA’ O ‘ABIERTA’ 
    //EL SISTEMA SÓLO PERMITE LA MODIFICACIÓN DE LA FECHA FIN. 
    if (convocatoriaSeleccionada == null) {
      return false;
    }

    return convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA)
            || convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_ABIERTA);

  }

  public boolean isMostrarBotonPublicar() {

    if (convocatoriaSeleccionada == null) {
      return false;
    }

    return convocatoriaSeleccionada.getEstadoConvocatorio().getIdConstantes().equals(IConstantes.TIPO_ESTADO_CONVOCATORIA_NUEVA);

  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (convocatoriaSeleccionada != null && convocatoriaSeleccionada.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_1_dir_file_archivo_soporte") + convocatoriaSeleccionada.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, convocatoriaSeleccionada.getNombreArchivo());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  public String getNombreArchivoSubido() {
    return nombreArchivoSubido;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return convocatoriaSeleccionada != null && convocatoriaSeleccionada.getNombreArchivo() != null && convocatoriaSeleccionada.getNombreArchivoFisico() != null;
  }

  public int getContadorUnidadesSeleccionadas() {
    if (listaUnidadesPolicialesDTOperiodo == null || listaUnidadesPolicialesDTOperiodo.isEmpty()) {
      return 0;
    }
    int contadorUnidadesSeleccionadas = 0;
    for (UnidadPolicialDTO unaUnidadDTO : listaUnidadesPolicialesDTOperiodo) {
      if (unaUnidadDTO.isSeleccionado()) {
        contadorUnidadesSeleccionadas += 1;
      }
    }
    return contadorUnidadesSeleccionadas;
  }

  public List<UnidadPolicialDTO> getListaUnidadesPolicialesDTOperiodo() {
    return listaUnidadesPolicialesDTOperiodo;
  }

  public void setListaUnidadesPolicialesDTOperiodo(List<UnidadPolicialDTO> listaUnidadesPolicialesDTOperiodo) {
    this.listaUnidadesPolicialesDTOperiodo = listaUnidadesPolicialesDTOperiodo;
  }

}
