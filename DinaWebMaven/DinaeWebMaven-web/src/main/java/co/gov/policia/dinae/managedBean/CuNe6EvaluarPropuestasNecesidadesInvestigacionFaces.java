package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ArchivoReporte;
import co.gov.policia.dinae.dto.EjecutorNecesidadDTO;
import co.gov.policia.dinae.dto.PropuestaNecesidadDTO;
import co.gov.policia.dinae.enums.EnumFuncionalidadCompromiso;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.IConfirmacionDialogoEvento;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICuPr23GestionaCompromisoAccionRegresar;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IFuncionarioNecesidadLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadCuNe07;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialPeriodoLocal;
import co.gov.policia.dinae.manager.managedBean.ConfirmDialogFaces;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.JSFUtils;
import static co.gov.policia.dinae.util.JSFUtils.copiarArchivoRutaFisica;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "cuNe6EvaluarPropuestasNecesidadesInvestigacionFaces")
@javax.enterprise.context.SessionScoped
public class CuNe6EvaluarPropuestasNecesidadesInvestigacionFaces extends JSFUtils
        implements Serializable, ICuPr23GestionaCompromisoAccionRegresar, IConfirmacionDialogoEvento, IPropuestaNecesidadCuNe07 {

  @javax.inject.Inject
  private CuNe4ConsultaPropuestaDeNecesidadFaces cuNe4ConsultaPropuestaDeNecesidadFaces;

  @javax.inject.Inject
  private ConfirmDialogFaces confirmDialogFaces;

  @javax.inject.Inject
  private CuPr23GestionarCompromisoProyectos cuPr23GestionarCompromisoProyectos;

  @javax.inject.Inject
  private CuNe7PropuestaNecesidadFaces cuNe7PropuestaNecesidadFaces;

  @EJB
  private IUnidadPolicialPeriodoLocal iUnidadPolicialPeriodoLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @EJB
  private IPropuestaNecesidadLocal iPropuestaNecesidadLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;

  @EJB
  private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;

  @EJB
  private IFuncionarioNecesidadLocal iFuncionarioNecesidadLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  @EJB
  private IProyectoLocal iProyectoLocal;

  private ListGenericDataModel listaPeriodoModel;
  private ListGenericDataModel<PropuestaNecesidad> listaPropuestaNecesidadModel;
  private Periodo periodoSeleccionado;
  private PropuestaNecesidad propuestaNecesidadSeleccionada;
  private Long unidadPolicialSeleccionada;
  private List<SelectItem> listaItemLineaUnidadPolicial;
  private List<SelectItem> listaItemUnidadPolicialVincula;
  private List<SelectItem> listaItemRolVincula;
  private List<UnidadPolicial> listadoUnidadesNoSeHanPresentado;
  private List<EjecutorNecesidadDTO> listadoEjecutorNecesidad;
  private Long idUnidadPolicialVinculaSeleccionado;
  private Long idRolVinculaEjecutorSelecionado;
  private List<CompromisoPeriodo> listaCompromisoPeriodo;
  private boolean mostrarConformacionDialogo;
  private int idTabSeleccionado;

  private static final List<Long> listaIdRolesEnviarMailJefesUnidad = new ArrayList<Long>(1);

  static {
    listaIdRolesEnviarMailJefesUnidad.add(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    return navigationFaces.redirectCuNe06Redirect();

  }

  @javax.annotation.PostConstruct
  public void init() {

    try {

      idTabSeleccionado = 0;
      mostrarConformacionDialogo = false;
      periodoSeleccionado = null;
      unidadPolicialSeleccionada = null;
      listadoUnidadesNoSeHanPresentado = null;
      listaItemUnidadPolicialVincula = null;
      listaItemRolVincula = null;
      listadoEjecutorNecesidad = null;
      idUnidadPolicialVinculaSeleccionado = null;
      idRolVinculaEjecutorSelecionado = null;

      //CARGAMOS LA LISTA DE PERIODOS
      List<Periodo> listaPeriodos = iPeriodoLocal.getPeriodosPorEstadoYFechaActualEntreFechaInicioYfechaFin(new Date(), IConstantes.ESTADO_PERIODO_PUBLICADO);

      listaPeriodoModel = new ListGenericDataModel(listaPeriodos);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación", e);
    }

  }

  /**
   * Metodo que es invocado desde el CU NE 01
   *
   * @param periodo
   * @throws Exception
   */
  public void initPeriodo(Periodo periodo) throws Exception {

    try {

      init();

      this.periodoSeleccionado = periodo;
      //CARGAMOS LA LISTA DE PROPUESTAS NECESIDADES 
      //ASOCIADOS AL PERIODO SELECCIONADO
      cargarListaPropuestas(periodoSeleccionado, null);

      //CARGAMOS LA LISTA DE UNIDADES QUE NO HAN PRESENTADO PROPUESTAS
      listadoUnidadesNoSeHanPresentado = iUnidadPolicialPeriodoLocal.getUnidadPolicialNoPresentanPropuesta(periodoSeleccionado.getIdPeriodo(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA);

      //CARGAMOS LISTA DE COMPROMISOS
      cargarListaCompromiso(periodoSeleccionado);

      navigationFaces.redirectFacesCuNe06PropuestaNecesidades();

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (enviarPropuesta)", e);
      throw e;

    }

  }

  /**
   * Ya no ha seleccionado una propuesta necesidad
   *
   * @param event
   */
  public void noSeleccionPropuestaNecesidadSeleccionada(UnselectEvent event) {
    propuestaNecesidadSeleccionada = null;
  }

  /**
   *
   */
  @Override
  public void continuarConfirmacionDialogoEvento() {

    try {

      //SE CAMBIA EL ESTADO DE LA PROPUESTA A 'PRE-APROBADA'
      propuestaNecesidadSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA));
      iPropuestaNecesidadLocal.guardar(propuestaNecesidadSeleccionada);

      //MOSTRAMOS MENSAJE INFORMATIVO
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_propue_actualiza_ok"));

      //REDIRECCIONAMOS
      initPeriodo(periodoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-06 Evaluar propuestas de necesidades de investigación (continuarConfirmacionDialogoEvento)", e);

    }

  }

  /**
   *
   */
  @Override
  public void cancelarConfirmacionDialogoEvento() {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "-cancelarConfirmacionDialogoEvento-");
  }

  /**
   *
   * @return
   */
  public String preAprobarPropuestaSeleccionada() {
    try {

      //VERIFICAMOS SI LA PROPUESTA YA SE ENCUENTRA EN ESTADO PREAPROBADA
      if (IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA.equals(
              propuestaNecesidadSeleccionada.getConstantes().getIdConstantes())) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_6_lbl_btn_mensaje_prop_ya_esta_preaprobada"));
        return null;

      }

      mostrarConformacionDialogo = false;
      //CONSULTAMOS LAS PROPUESTAS PRE-APROBADAS ACTUALMENTE PARA DICHA UNIDAD
      int contarPropuestasPreaprobadas = iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadPorPeriodoYunidadYestado(
              propuestaNecesidadSeleccionada.getPeriodo().getIdPeriodo(),
              propuestaNecesidadSeleccionada.getUnidadPolicialReponsableEjecutora().getIdUnidadPolicial(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA);

      if (contarPropuestasPreaprobadas > 0) {

        mostrarConformacionDialogo = true;

        String mensaje = keyPropertiesFactory.value("cu_ne_6_lbl_btn_mensaje_prop_preaprobada", new String[]{String.valueOf(contarPropuestasPreaprobadas)});
        confirmDialogFaces.initConfirmDialogFaces(
                keyPropertiesFactory.value("cu_ne_6_lbl_btn_dialog_confirmacion"),
                mensaje,
                keyPropertiesFactory.value("cu_ne_6_lbl_btn_dialog_config_cancel"),
                keyPropertiesFactory.value("cu_ne_6_lbl_btn_dialog_config_ok"),
                this,
                ":idForm_cu_ne_6_gestiona_evaluar_propuestas_necesidades_detalle_propuesta",
                ":idForm_cu_ne_6_gestiona_evaluar_propuestas_necesidades_detalle_propuesta");
      } else {

        continuarPreAprobarPropuestas();

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (preAprobarPropuestaSeleccionada)", e);
    }

    return null;
  }

  /**
   *
   */
  public void regresarListPropuesta() {

    try {

      //ACTUALIZAMOS LA VISTA
      initPeriodo(periodoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (regresarListPropuesta)", e);

    }
    //return navigationFaces.redirectCuNe06PropuestaNecesidades();

  }

  /**
   *
   * @return
   */
  public String rechazarPropuestaSeleccionada() {
    try {

      //SE CAMBIA EL ESTADO DE LA PROPUESTA A 'Rechazada temporalmente''
      propuestaNecesidadSeleccionada.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA));
      iPropuestaNecesidadLocal.guardar(propuestaNecesidadSeleccionada);

      //MOSTRAMOS MENSAJE INFORMATIVO
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_propue_rechazada"));

      //REDIRECCIONAMOS
      initPeriodo(periodoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (rechazarPropuestaSeleccionada)", e);
    }

    return "";
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarPropuestaNecesidadSeleccionada(SelectEvent event) {

    if (propuestaNecesidadSeleccionada == null) {
      //FINALIZA EL PROCESO
      return;
    }

    try {
      mostrarConformacionDialogo = false;

      cuNe7PropuestaNecesidadFaces.fijarPropuesta(propuestaNecesidadSeleccionada, navigationFaces.redirectCuNe06PropuestaNecesidades(), false, null, null);

      //CARGMOS LAS INIDADES POLICIALES QUE PUEDE VINCULAR
      cargarListaItemUnidadPolicialVincula(propuestaNecesidadSeleccionada.getIdPropuestaNecesidad());

      //CARGAMOS LOS ROLES 
      listaItemRolVincula = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ROL_PROYECTO_NECESIDAD),
              "idConstantes", "valor");

      //CARGAMOS LA LISTA DE  EJECUTORES
      cargarListaEjecutorNecesidad(propuestaNecesidadSeleccionada);

      navigationFaces.redirectFacesCuNe06PropuestaNecesidad();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (consultarBancoNecesidad)", e);

    }

  }

  /**
   *
   * @param periodo
   * @throws JpaDinaeException
   */
  private void cargarListaCompromiso(Periodo periodo) throws JpaDinaeException {

    listaCompromisoPeriodo = iCompromisoPeriodoLocal.buscarCompromisoPeriodo(periodo.getIdPeriodo());

  }

  /**
   * Ya no ha seleccionado un periodo
   *
   * @param event
   */
  public void noSeleccionPeriodo(UnselectEvent event) {
    periodoSeleccionado = null;
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarPropuestaSeleccionPeriodo(SelectEvent event) {

    if (periodoSeleccionado == null) {
      //FINALIZA EL PROCESO
      return;
    }

    try {

      initPeriodo(periodoSeleccionado);

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (enviarPropuestaSeleccionPeriodo)", e);
    }

  }

  /**
   *
   */
  public void handleUnidadPolicialSeleccionadaChange() {

    if (unidadPolicialSeleccionada == null) {
      //VER TODAS LAS PROPUESTAS INDEPENDIENTEMENTE DE LAS UNIDADES
      cargarListaPropuestas(periodoSeleccionado, null);
    } else {
      //VER TODOAS LAS PROPUESTAS POR UNIDAD POLICIAL SELECCIONADA
      cargarListaPropuestas(periodoSeleccionado, unidadPolicialSeleccionada);
    }

  }

  /**
   * Metodo que carga la lista de propuesta asociados a un periodo
   *
   * @param periodoSeleccionado
   */
  private void cargarListaPropuestas(Periodo periodoSeleccionado, Long idUnidadPolicialSeleccionada) {

    try {

      //SE MUESTRAN LOS DATOS DE LAS PROPUESTAS ASOCIADAS AL PERIODO SELECCIONADO, 
      //CON EL ESTADO ''ENVIADA A VICIN '' O 'PRE-APROBADA ' O 'REVISADA'
      List<PropuestaNecesidad> listaPropuestaNecesidad = iPropuestaNecesidadLocal.getPropuestaNecesidadPorPeriodoYEstado(periodoSeleccionado.getIdPeriodo(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA,
              idUnidadPolicialSeleccionada);

      listaPropuestaNecesidadModel = new ListGenericDataModel(listaPropuestaNecesidad);

      //CARGA LAS UNIDADES POLICIALES
      //LA LISTA SE CARGA DE ACUERDO A LAS UNIDADES INVOLUCRADAS EN LA LISTA DE PROPUESTAS.
      listaItemLineaUnidadPolicial = new ArrayList<SelectItem>();
      List<PropuestaNecesidadDTO> listaPropuestaCargaItem = iPropuestaNecesidadLocal.getPropuestaNecesidadDTOPorPeriodoYEstado(periodoSeleccionado.getIdPeriodo(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA,
              null);
      Set<Long> validaAdiciona = new HashSet<Long>();
      for (PropuestaNecesidadDTO unaPropuestaNecesidadDTO : listaPropuestaCargaItem) {

        //PRIMERO VALIDAMOS SI EL OBJETO YA FUE ADICIONADO A LA LISTA DE ITEM
        if (validaAdiciona.contains(unaPropuestaNecesidadDTO.getId())) {
          //SI YA FUE ADICIONADO ENTONCES, CONTINUAMOS CON EL SIGUIENTE VALOR
          continue;
        }
        //LO ADICONAMOS A LA LISTA DE ITEM
        listaItemLineaUnidadPolicial.add(new SelectItem(
                unaPropuestaNecesidadDTO.getId(), unaPropuestaNecesidadDTO.getNombre()));
        //LO ADICIONAMOS A LA LISTA DE VALIDACION, QUE NOS PERMITE SABER SI EL OBJETO FUE INGRESADO A LA LISTA DE ITEM
        validaAdiciona.add(unaPropuestaNecesidadDTO.getId());
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (cargarListaPropuestas)", e);

    }
  }

  /**
   *
   * @param propuestaNecesidadRecibida
   */
  @Override
  public void setearPropuestaNecesidadCuNe07(PropuestaNecesidad propuestaNecesidadRecibida) {

    try {

      //CONSULTAMOS LA PROPUESTA
      PropuestaNecesidad propuestaNecesidad = iPropuestaNecesidadLocal.consultar(propuestaNecesidadRecibida.getIdPropuestaNecesidad());
      propuestaNecesidad.setIdPropuestaNecesidad(null);

      //CONSULTAMOS LA LISTA DE FUNCIONARIOS
      List<FuncionarioNecesidad> listaFuncionariosNecesidad = iFuncionarioNecesidadLocal.getListaFuncionarioNecesidad(propuestaNecesidadRecibida.getIdPropuestaNecesidad());
      for (FuncionarioNecesidad funcionarioNecesidad : listaFuncionariosNecesidad) {

        funcionarioNecesidad.setIdFuncionarioNecesidad(null);
      }

      //LE ACTUALIZAMOS EL PERIODO AL ACTUAL
      propuestaNecesidad.setPeriodo(periodoSeleccionado);
      propuestaNecesidad.setFechaRegistro(new Date());
      propuestaNecesidad.setFuncionarioNecesidadList(listaFuncionariosNecesidad);
      propuestaNecesidad.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      propuestaNecesidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN));
      propuestaNecesidad.setConcecutivo(null);

      iPropuestaNecesidadLocal.guardarPropuestaCreaEjecutorNecesidad(
              propuestaNecesidad,
              null,
              propuestaNecesidad.getUnidadPolicial().getIdUnidadPolicial());

      //ACTUALIZAMOS LA PROPUESTA COPIADA, INDICANDO QUE ESTA PROPOPUESTA FUE COPIADA
      propuestaNecesidadRecibida.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_COPIADA));

      iPropuestaNecesidadLocal.guardar(propuestaNecesidadRecibida);

      //VER TODAS LAS PROPUESTAS INDEPENDIENTEMENTE DE LAS UNIDADES
      cargarListaPropuestas(periodoSeleccionado, null);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (setearPropuestaNecesidadCuNe07)", e);

    }
  }

  /**
   *
   * @return
   */
  public String consultarBancoNecesidad() {

    try {

      cuNe4ConsultaPropuestaDeNecesidadFaces.fijarPropuesta(
              navigationFaces.redirectCuNe06PropuestaNecesidades(),
              true,
              this);

      return navigationFaces.redirectCuNe04ConsultarPropuestaNecesidad();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (consultarBancoNecesidad)", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String agregarCompromiso() {

    try {

      //SE VERIFICA QUE POR LOS MENOS UNA PROPUESTA SE ENCUENTRE EN ESTADO PRE-APROBADO
      //CONSULTAMOS LAS PROPUESTAS PRE-APROBADAS ACTUALMENTE PARA DICHA UNIDAD
      int contarPropuestasPreaprobadas = iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadPorPeriodoYestado(
              periodoSeleccionado.getIdPeriodo(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA);

      if (contarPropuestasPreaprobadas == 0) {
        //SE DETIENE EL PROCESO,
        //PARA PODER INGRESAR COMPROMISOS, POR LO MENOS DEBE HABER UNA PROPUESTA EN ESTADO PRE-APROBADA
        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_err_add_compromiso"));
        return null;
      }

      //HACEMOS LLAMADO AL CU-PR-23-Gestionar compromisos de proyectos
      cuPr23GestionarCompromisoProyectos.funcionalidad(EnumFuncionalidadCompromiso.PROPUESTA_NECESIDAD,
              periodoSeleccionado, this);

      return navigationFaces.redirectCuPr23Compromisos();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (agregarCompromiso)", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String modificarCompromiso() {

    try {

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (consultarBancoNecesidad)", e);

    }
    return null;
  }

  /**
   *
   * @param actionEvent
   */
  public void publicarPropuestas(ActionEvent actionEvent) {

    //CONSULTAMOS LAS PROPUESTAS ASOCIADAS AL PERIODO SELECCIONADO, 
    //CON EL ESTADO ''ENVIADA A VICIN '' O 'PRE-APROBADA ' O 'REVISADA'
    try {

      //SISTEMA VERIFICA QUE LOS COMPROMISOS PARA EL PERIODO HAYAN SIDO INGRESADO
      if (listaCompromisoPeriodo == null || listaCompromisoPeriodo.isEmpty()) {
        //MUESTRA MENSAJE: 'DEBE INGRESAR LOS COMPROMISOS PARA PODER PUBLICAR ', NO ES POSIBLE PUBLICAR LOS RESULTADOS          

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_add_comprimiso_faltantes"));
        return;
      }

      String mensajeValidacion = validacionesGeneralesFaces.validarCompromisosPeriodo(periodoSeleccionado.getIdPeriodo());

      if (mensajeValidacion != null) {
        adicionaMensajeError(keyPropertiesFactory.value(mensajeValidacion));
        return;
      }

      List<PropuestaNecesidad> listaPropuestaNecesidadPublicar = iPropuestaNecesidadLocal.getPropuestaNecesidadPorPeriodoYEstado(periodoSeleccionado.getIdPeriodo(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA,
              null);

      int contadorPropuesta = 0;
      //VERIFICAMOS QUE TODAS LAS PROPUESTAS ESTEN EN ESTADO PRE-APROBADA O REVISADA
      for (PropuestaNecesidad unaPropuestaNecesidadConsultaUnidad : listaPropuestaNecesidadPublicar) {

        contadorPropuesta++;
        //LAS PROPUESTAS DEBEN ESTAR EN UN ESTADO "PRE-APROBADA O REVISADA"
        if (unaPropuestaNecesidadConsultaUnidad.getConstantes().getIdConstantes().equals(
                IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA)
                || unaPropuestaNecesidadConsultaUnidad.getConstantes().getIdConstantes().equals(
                        IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA)) {

          continue;
        }
        //PROPUESTA EN ESTADO DIFERENTE A "PRE-APROBADA O REVISADA"
        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_propuesta_estado_vicin_incorrecto_publica", new String[]{String.valueOf(contadorPropuesta)}));
        return;
      }

      /*
            ESTE PROCESO SE COMENTA, POR SOLICITUD DEL TENIENTE CONTRERAS
            //CONSULTAMOS LOS IDS DE TODAS LAS UNIDADES DEL PERIODO
            List<Long> listaIdsUnidadesPropuestaUnidad = iUnidadPolicialPeriodoLocal.getIdsUnidadPolicialPorPeriodo(periodoSeleccionado.getIdPeriodo());

            //DE ACUERDO A LA LISTA DE UNIDADES CONSULTAMOS,
            //SE VERIFICA QUE TODAS LAS UNIDADES DEL PERIODO TENGAN AL MENOS UNA PROPUESTA PREAPROBADA
            for (Long unIdUnidadPolicialConsulta : listaIdsUnidadesPropuestaUnidad) {

                int contarPropuestasPreAprobadasPorPeriodoYunidad = iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadDesdeEjecutorPorPeriodoYunidadYestado(
                        periodoSeleccionado.getIdPeriodo(),
                        unIdUnidadPolicialConsulta,
                        IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA,
                        IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE);

                if (contarPropuestasPreAprobadasPorPeriodoYunidad == 0) {
                    
                    //SE DETIENE EN PROCESO, YA QUE TODOS LAS UNIDADES PARA DICHO PERIODO DEBEN TENER AL MENOS UNA PROPUESTA EN ESTADO PRE-APROBADO
                    adicionaMensajeError(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_unidades_sin_propues_aprobada"));
                    return;
                }
            }
       */
      byte[] bitesPdf;
      //GENERAMOS EL REPORTE
      try {

        HashMap mapa = new HashMap();
        mapa.put("p_id_periodo", periodoSeleccionado.getIdPeriodo().intValue());

        bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte15.jasper");

      } catch (Exception e) {

        adicionaMensajeError("ERROR, Se presentaron errores al general el reporte JASPER");
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-06(publicarPropuestas)", e);

        return;
      }

      String iniciaCodigoVic = keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion");
      if (iniciaCodigoVic.contains("-----NOT FOUND-----")) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_6_error_no_existe_codigo_proyecto_inicia_generacion"));
        return;
      }

      List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
      //EL SISTEMA CAMBIA EL ESTADO DE TODAS LAS PROPUESTAS DEL LISTADO DE ''PRE-APROBADA ' A 'APROBADA' 
      //Y LOS DE ESTADO 'REVISADA' A 'NO APROBADA'
      int contarProyecto = iProyectoLocal.contarTodosLosProyectoPorPeriodo(periodoSeleccionado.getIdPeriodo());
      Constantes constantes = iConstantesLocal.getConstantesPorIdConstante(IConstantes.DURACION_PROYECTOS_INSTITUCIONALES);
      int numeroMesesEstimacionProyecto = Integer.parseInt(constantes.getValor());

      UsuarioRol usuarioRol = new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN).getIdUsuarioRol());

      for (PropuestaNecesidad unaPropuestaNecesidadConsultaUnidad : listaPropuestaNecesidadPublicar) {

        if (unaPropuestaNecesidadConsultaUnidad.getConstantes().getIdConstantes().equals(
                IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA)) {

          EjecutorNecesidad ejecutorNecesidadResponsable = iEjecutorNecesidadLocal.getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(unaPropuestaNecesidadConsultaUnidad.getIdPropuestaNecesidad());
          if (ejecutorNecesidadResponsable == null
                  || ejecutorNecesidadResponsable.getUnidadPolicial() == null
                  || ejecutorNecesidadResponsable.getUnidadPolicial().getSiglaFisica() == null) {

            adicionaMensajeError("Error, Verifique la sigla física de la Unidad Policial asociada a la propuesta: " + unaPropuestaNecesidadConsultaUnidad.getTema());
            return;

          }

          unaPropuestaNecesidadConsultaUnidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBADA));
          //POR CADA PROPROPUESTA APROBADA SE CREA UN PROYECTO
          //CREA UN PROYECTO DE INVESTIGACIÓN POR CADA PROPUESTA CON EL ESTADO 'APROBADA', 
          //ASIGNÁNDOLE EL CÓDIGO DE INVESTIGACIÓN DE ACUERDO AL MÉTODO ESTABLECIDO(VER REQUERIMIENTOS ESPECIALES), 
          //ASIGNÁNDOLE EL ÁREA Y LA LÍNEA DE INVESTIGACIÓN Y EL TEMA PROPUESTO COMO TITULO PROPUESTO
          //VIC - [Consecutivo de proyectos en el periodo][Año]- [Código interno de la Unidad Policial o Escuela]
          String codigoInternoUnidad = ejecutorNecesidadResponsable.getUnidadPolicial().getSiglaFisica();
          String codigoProyecto = iniciaCodigoVic.concat("-");//VIC 
          contarProyecto += 1;
          codigoProyecto = codigoProyecto.concat(String.valueOf(contarProyecto));//[Consecutivo de proyectos en el periodo]
          codigoProyecto = codigoProyecto.concat(String.valueOf(periodoSeleccionado.getAnio()));//[Año]
          codigoProyecto = codigoProyecto.concat("-");
          codigoProyecto = codigoProyecto.concat(codigoInternoUnidad);//[Código interno de la Unidad Policial o Escuela]

          Date fechaHoy = new Date();
          Proyecto proyecto = new Proyecto();
          proyecto.setCodigoProyecto(codigoProyecto);
          proyecto.setLinea(unaPropuestaNecesidadConsultaUnidad.getLinea());
          proyecto.setTituloPropuesto(unaPropuestaNecesidadConsultaUnidad.getTema());
          proyecto.setTema(unaPropuestaNecesidadConsultaUnidad.getTema());
          proyecto.setPeriodo(unaPropuestaNecesidadConsultaUnidad.getPeriodo());
          proyecto.setUsuarioRol(usuarioRol);
          proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION));
          proyecto.setUnidadPolicial(ejecutorNecesidadResponsable.getUnidadPolicial());
          proyecto.setPropuestaNecesidad(unaPropuestaNecesidadConsultaUnidad);
          proyecto.setFechaEstimadaInicio(fechaHoy);
          Calendar fechaFinalEstimadaProyecto = Calendar.getInstance();
          fechaFinalEstimadaProyecto.setTime(fechaHoy);
          fechaFinalEstimadaProyecto.add(Calendar.MONTH, numeroMesesEstimacionProyecto);
          proyecto.setFechaEstimadaFinalizacion(fechaFinalEstimadaProyecto.getTime());
          proyecto.setFechaActualizacion(fechaHoy);

          //CREAMOS LOS COMPROMISOS PROYECTOS
          List<CompromisoProyecto> listaCompromisosProyecto = new ArrayList<CompromisoProyecto>();
          //CONSULTAMOS LOS COMPROMISOS DE ESTE PERIODO
          List<CompromisoPeriodo> listaComprimiso = iCompromisoPeriodoLocal.buscarCompromisoPeriodo(
                  unaPropuestaNecesidadConsultaUnidad.getPeriodo().getIdPeriodo());

          for (CompromisoPeriodo unCompromisoPeriodo : listaComprimiso) {

            CompromisoProyecto compromisoProyecto = new CompromisoProyecto();
            compromisoProyecto.setCompromisoPeriodo(unCompromisoPeriodo);
            compromisoProyecto.setProyecto(proyecto);
            compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE));
            compromisoProyecto.setFechaCreacion(new Date());
            compromisoProyecto.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
            compromisoProyecto.setUsuarioRegistro(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
            compromisoProyecto.setUsuarioRolRegistra(usuarioRol);

            listaCompromisosProyecto.add(compromisoProyecto);

          }

          //CREAMOS LAS UNIDADES EJECUTORAS PARA EL PROYECTO
          List<EjecutorNecesidadDTO> listadoEjecutorNecesidadDTOPropuesta = iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorPropuestaNecesidad(
                  unaPropuestaNecesidadConsultaUnidad.getIdContantes());

          List<EjecutorNecesidad> listaEjecutorNecesidadProyecto = new ArrayList<EjecutorNecesidad>();

          for (EjecutorNecesidadDTO unaEjecutorNecesidadDTO : listadoEjecutorNecesidadDTOPropuesta) {

            EjecutorNecesidad ejecutorNecesidadLocal = new EjecutorNecesidad();
            ejecutorNecesidadLocal.setPropuestaNecesidad(new PropuestaNecesidad(unaPropuestaNecesidadConsultaUnidad.getIdPropuestaNecesidad()));
            ejecutorNecesidadLocal.setProyecto(proyecto);
            ejecutorNecesidadLocal.setRol(new Constantes(unaEjecutorNecesidadDTO.getIdRol()));
            ejecutorNecesidadLocal.setUnidadPolicial(proyecto.getUnidadPolicial());

            listaEjecutorNecesidadProyecto.add(ejecutorNecesidadLocal);

          }

          proyecto.setEjecutorNecesidadList(listaEjecutorNecesidadProyecto);
          proyecto.setCompromisoProyectoList(listaCompromisosProyecto);

          listaProyectos.add(proyecto);

        } else if (unaPropuestaNecesidadConsultaUnidad.getConstantes().getIdConstantes().equals(
                IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA)) {

          unaPropuestaNecesidadConsultaUnidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_APROBADA));
        }

        //ACTUALIZAMOS EL CAMPO ROL_ACTUAL
        //CON EL OBJETIVO SE SABER EN DONDE SE ENCUENTRA LA PROPUESTA
        //ESTO SE REALIZA PARA CORREGIR 
        //LA INCIDENCIA #0002754: Mientras no se publiquen los resultados de las necesidades, el estado debe ser 'Enviada a VICIN'.
        unaPropuestaNecesidadConsultaUnidad.setRolActual(IConstantes.PROPUESTA_NECESIDAD_PUBLICADA_JEFE_UNIDAD);

      }

      //CARGAMOS LA LISTA DE PROPUESTAS NECESIDADES CON LOS NUEVOS ESTADOS
      cargarListaPropuestas(periodoSeleccionado, null);

      //GENERAMOS EL NOMBRE DEL ARCHIVO DEL REPORTE
      String nombreReporteUnico = "PROP_NECES_PERIODO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
      String nombreReporte = "PROP_NECES_PERIODO_" + (periodoSeleccionado.getAnio() == null ? periodoSeleccionado.getIdPeriodo().toString() : periodoSeleccionado.getAnio().toString()) + ".pdf";

      //SE ACTUALIZAN LAS PROPUESTAS DE NECESIDAD
      iPropuestaNecesidadLocal.guardarListaPropuestaYgenerarProyecto(
              listaPropuestaNecesidadPublicar,
              listaProyectos,
              nombreReporteUnico,
              nombreReporte,
              periodoSeleccionado.getIdPeriodo());

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_6_lbl_mensaje_propuestas_actualizadas_ok_publicar"));

      try {

        //VOLVEMOS A GENERAR EL REPORTE
        HashMap mapa = new HashMap();
        mapa.put("p_id_periodo", periodoSeleccionado.getIdPeriodo().intValue());

        bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte15.jasper");

        copiarArchivoRutaFisica(
                keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general"),
                new ByteArrayInputStream(bitesPdf),
                nombreReporteUnico);

        //0002625: Al publicar por VICIN debe enviar correo al rol Jefe de Unidad
        Map<String, Object> parametrosContenido = new HashMap<String, Object>();
        parametrosContenido.put("{_parametro1_}", String.valueOf(periodoSeleccionado.getAnio()));
        parametrosContenido.put(IConstantes.CONTENIDO_ADJUNTO_MAIL, new ArchivoReporte(bitesPdf, "RESULTADO_PROPUESTAS.pdf"));

        //EL SISTEMA ENVÍA COMUNICACIÓN A LOS JEFES DE UNIDAD CON UN REPORTE DE ACUERDO AL ANEXO. MAIL
        iMailSessionBean.enviarMail_ListaRoles(
                IConstantes.CU_NE_06_RESULTADO_REVISION,
                null,
                parametrosContenido,
                listaIdRolesEnviarMailJefesUnidad
        );
      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-06 Evaluar propuestas de necesidades de investigación ENVIAR MAIL - (publicarPropuestas)", e);
      }

      navigationFaces.redirectFacesCuNe01();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (publicarPropuestas)", e);

    }

  }

  /**
   *
   */
  public void vincularUnidadFuncional() {

    if (idUnidadPolicialVinculaSeleccionado == null) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_ne_7_datos_incompleato_unida_fun"));
      return;
    }
    if (idRolVinculaEjecutorSelecionado == null) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_ne_7_datos_incompleato_rol"));
      return;
    }

    try {

      //GUARDAMOS EL EJECUTOR
      iPropuestaNecesidadLocal.guardarPropuestaCreaEjecutorNecesidad(propuestaNecesidadSeleccionada, idRolVinculaEjecutorSelecionado, idUnidadPolicialVinculaSeleccionado);

      //REINICIAMOS LAS VARIABLES
      idUnidadPolicialVinculaSeleccionado = null;
      idRolVinculaEjecutorSelecionado = null;

      //MENSAJE INFORMATIVO
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_7_rol_vincula_ingresado_correcto"));

      //ACTUALIZAMOS LA LISTA DE EJECUTORES
      cargarListaEjecutorNecesidad(propuestaNecesidadSeleccionada);

      //ACTUALIMAS LA LISTA DE UNIDAD POLICIAL VINCULA
      cargarListaItemUnidadPolicialVincula(propuestaNecesidadSeleccionada.getIdPropuestaNecesidad());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación - (vincularUnidadFuncional)", e);

    }
  }

  /**
   *
   * @param idPeriodo
   * @throws Exception
   */
  private void cargarListaItemUnidadPolicialVincula(Long idPropuestaNecesidad) throws Exception {

    //CARGMOS LAS INIDADES POLICIALES QUE PUEDE VINCULAR
    listaItemUnidadPolicialVincula = UtilidadesItem.getListaSel(
            iUnidadPolicialLocal.getUnidadPolicialDiferenteAEjecutorPropuestaUnidad(idPropuestaNecesidad),
            "idUnidadPolicial", "nombre");

  }

  /**
   *
   * @param propuestaNecesidadSeleccionada
   */
  private void cargarListaEjecutorNecesidad(PropuestaNecesidad propuestaNecesidadSeleccionada) throws Exception {

    //CARGAMOS LA LISTA DE  EJECUTORES
    listadoEjecutorNecesidad = iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorPropuestaNecesidad(propuestaNecesidadSeleccionada.getIdPropuestaNecesidad());

  }

  public ListGenericDataModel getListaPeriodoModel() {
    return listaPeriodoModel;
  }

  public void setListaPeriodoModel(ListGenericDataModel listaPeriodoModel) {
    this.listaPeriodoModel = listaPeriodoModel;
  }

  public Periodo getPeriodoSeleccionado() {
    return periodoSeleccionado;
  }

  public void setPeriodoSeleccionado(Periodo periodoSeleccionado) {
    this.periodoSeleccionado = periodoSeleccionado;
  }

  public Long getUnidadPolicialSeleccionada() {
    return unidadPolicialSeleccionada;
  }

  public void setUnidadPolicialSeleccionada(Long unidadPolicialSeleccionada) {
    this.unidadPolicialSeleccionada = unidadPolicialSeleccionada;
  }

  public List<SelectItem> getListaItemLineaUnidadPolicial() {
    return listaItemLineaUnidadPolicial;
  }

  public void setListaItemLineaUnidadPolicial(List<SelectItem> listaItemLineaUnidadPolicial) {
    this.listaItemLineaUnidadPolicial = listaItemLineaUnidadPolicial;
  }

  public ListGenericDataModel<PropuestaNecesidad> getListaPropuestaNecesidadModel() {
    return listaPropuestaNecesidadModel;
  }

  public void setListaPropuestaNecesidadModel(ListGenericDataModel<PropuestaNecesidad> listaPropuestaNecesidadModel) {
    this.listaPropuestaNecesidadModel = listaPropuestaNecesidadModel;
  }

  public PropuestaNecesidad getPropuestaNecesidadSeleccionada() {
    return propuestaNecesidadSeleccionada;
  }

  public void setPropuestaNecesidadSeleccionada(PropuestaNecesidad propuestaNecesidadSeleccionada) {
    this.propuestaNecesidadSeleccionada = propuestaNecesidadSeleccionada;
  }

  public List<UnidadPolicial> getListadoUnidadesNoSeHanPresentado() {
    return listadoUnidadesNoSeHanPresentado;
  }

  public void setListadoUnidadesNoSeHanPresentado(List<UnidadPolicial> listadoUnidadesNoSeHanPresentado) {
    this.listadoUnidadesNoSeHanPresentado = listadoUnidadesNoSeHanPresentado;
  }

  public List<SelectItem> getListaItemUnidadPolicialVincula() {
    return listaItemUnidadPolicialVincula;
  }

  public void setListaItemUnidadPolicialVincula(List<SelectItem> listaItemUnidadPolicialVincula) {
    this.listaItemUnidadPolicialVincula = listaItemUnidadPolicialVincula;
  }

  public List<SelectItem> getListaItemRolVincula() {
    return listaItemRolVincula;
  }

  public void setListaItemRolVincula(List<SelectItem> listaItemRolVincula) {
    this.listaItemRolVincula = listaItemRolVincula;
  }

  public List<EjecutorNecesidadDTO> getListadoEjecutorNecesidad() {
    return listadoEjecutorNecesidad;
  }

  public void setListadoEjecutorNecesidad(List<EjecutorNecesidadDTO> listadoEjecutorNecesidad) {
    this.listadoEjecutorNecesidad = listadoEjecutorNecesidad;
  }

  public Long getIdUnidadPolicialVinculaSeleccionado() {
    return idUnidadPolicialVinculaSeleccionado;
  }

  public void setIdUnidadPolicialVinculaSeleccionado(Long idUnidadPolicialVinculaSeleccionado) {
    this.idUnidadPolicialVinculaSeleccionado = idUnidadPolicialVinculaSeleccionado;
  }

  public Long getIdRolVinculaEjecutorSelecionado() {
    return idRolVinculaEjecutorSelecionado;
  }

  public void setIdRolVinculaEjecutorSelecionado(Long idRolVinculaEjecutorSelecionado) {
    this.idRolVinculaEjecutorSelecionado = idRolVinculaEjecutorSelecionado;
  }

  public List<CompromisoPeriodo> getListaCompromisoPeriodo() {
    return listaCompromisoPeriodo;
  }

  public void setListaCompromisoPeriodo(List<CompromisoPeriodo> listaCompromisoPeriodo) {
    this.listaCompromisoPeriodo = listaCompromisoPeriodo;
  }

  @Override
  public void actualizarListaCompromisoLlamado() {

    try {
      cargarListaCompromiso(periodoSeleccionado);

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (actualizarListaCompromisoLlamado)", ex);
    }
  }

  /**
   *
   */
  private void continuarPreAprobarPropuestas() {

    try {

      continuarConfirmacionDialogoEvento();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-06 Evaluar propuestas de necesidades de investigación (continuarPreAprobarPropuestas)", e);

    }
  }

  /**
   *
   * @return
   */
  public boolean isMostrarConformacionDialogo() {
    return mostrarConformacionDialogo;
  }

  /**
   *
   * @return
   */
  public boolean isMostrarBtnAccionAprobarYrechazar() {

    if (propuestaNecesidadSeleccionada == null || propuestaNecesidadSeleccionada.getConstantes() == null) {
      return false;
    }

    return IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA.equals(propuestaNecesidadSeleccionada.getConstantes().getIdConstantes())
            || IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN.equals(propuestaNecesidadSeleccionada.getConstantes().getIdConstantes());
  }

  /**
   *
   * @return
   */
  public boolean isMostrarBtnAccionesPublicarPreAprobarYrechazar() {

    if (listaPropuestaNecesidadModel == null || listaPropuestaNecesidadModel.getRowCount() == 0) {
      return false;
    }

    Iterator<PropuestaNecesidad> iteraPropuesta = listaPropuestaNecesidadModel.iterator();
    while (iteraPropuesta.hasNext()) {

      PropuestaNecesidad pn = iteraPropuesta.next();
      if (pn.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN)
              || pn.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA)
              || pn.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA)) {

        continue;
      }
      //SI ALGUNA DE LAS PROPUESTA ES DIFERENTE A LOS ESTADOS ANETRIORES
      //SE OCULTA LOS BOTONES DE ACCIONES
      return false;
    }
    //SE MUESTRAN LOS BOTONES DE ACCIONES
    return true;
  }

  public int getNumeroRegistroDataTable() {

    if (listaPropuestaNecesidadModel == null) {
      return 0;
    }

    return listaPropuestaNecesidadModel.getRowCount();

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
    if ("idtabcu_ne_6_lbl_listado_propuesta".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idtabcu_ne_6_lbl_unidades_no_se_han_presentado".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    }
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public boolean isPestaniaPrincipal() {

    return idTabSeleccionado == 0;

  }

}
