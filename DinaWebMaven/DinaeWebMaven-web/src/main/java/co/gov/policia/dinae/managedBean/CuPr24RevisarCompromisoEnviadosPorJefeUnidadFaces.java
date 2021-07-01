package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ComentarioProyectoDTO;
import co.gov.policia.dinae.dto.CompromisoProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.ComentarioCompromisoProyecto;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
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
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr24RevisarCompromisoEnviadosPorJefeUnidad")
@javax.enterprise.context.SessionScoped
public class CuPr24RevisarCompromisoEnviadosPorJefeUnidadFaces extends JSFUtils implements Serializable {

  private ListGenericDataModel<CompromisoProyectoDTO> listaCompromisoProyectoDTO;
  private CompromisoProyectoDTO compromisoProyectoDTOSeleccionado;
  private CompromisoProyectoDTO compromisoProyectoDTOSeleccionadoVista;
  private List<CompromisoProyectoDTO> listadoUnidadesNoSeHanPresentadoCompromiso;
  private List<SelectItem> listaUnidadPolicialSelectItem;
  private Long idUnidadPolicialSeleccionado;

  @javax.inject.Inject
  private CuPr11ConsultarDetalleCompromisoFaces cuPr11ConsultarDetalleCompromisoFaces;

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @javax.inject.Inject
  private CuPr21RegistrarPlanDeTrabajoFaces cuPr21RegistrarPlanDeTrabajoFaces;

  @javax.inject.Inject
  private CuPr15_1_2_AvanceImplemenacionFaces cuPr15_1_2_AvanceImplemenacionFaces;

  @EJB
  private ICompromisoImplementacionLocal iCompromisoImplementacionLocal;

  @EJB
  private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @EJB
  private IComentarioProyectoLocal iComentarioProyectoLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IMailSessionLocal iMailSessionLocal;

  private static final List<Long> listaIdEstadoCompromiso = new ArrayList<Long>();

  static {
    listaIdEstadoCompromiso.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN);
    listaIdEstadoCompromiso.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_REVISADO);
  }

  private static final List<Long> listaIdEstadoCompromisoContar = new ArrayList<Long>();

  static {

    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE);
    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);
    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN);
    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);
    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ACEPTADO);
    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_REVISADO);
    listaIdEstadoCompromisoContar.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO);
  }

  private static final List<Long> listaIdEstadoCompromisoNohanPresentado = new ArrayList<Long>();

  static {

    listaIdEstadoCompromisoNohanPresentado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE);
    listaIdEstadoCompromisoNohanPresentado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);
    listaIdEstadoCompromisoNohanPresentado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ACEPTADO);
    listaIdEstadoCompromisoNohanPresentado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);
    listaIdEstadoCompromisoNohanPresentado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO);
  }

  private static final List<Long> listaIdEstadoConvocatoriaPublicada = new ArrayList<Long>();

  static {
    listaIdEstadoConvocatoriaPublicada.add(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA);
  }

  private String tituloProyecto;
  private Long itemComentarioSeleccionado;
  private int idTabSeleccionado;
  private List<SelectItem> listaItemItemComentario;
  private ComentarioCompromisoProyecto comentarioCompromisoProyectoSeleccionado;
  private List<ComentarioProyectoDTO> listaComentarioCompromisoProyectoSeleccionado;

  private List<SelectItem> listaItemResultadoRetroalimentacion;

  //FILTROS DE BUSQUEDA
  private List<SelectItem> listaItemTiposProyectos;
  private List<SelectItem> listaItemConvocatorias;
  private List<SelectItem> listaItemAnio;
  private List<SelectItem> listaItemTipoCompromiso;
  private Date fechaConsulta;
  private Long idListaItemTiposProyectosSeleccionado;
  private Long idListaItemConvocatoriasSeleccionado;
  private Long idListaItemAnioSeleccionado;
  private Long idListaItemTipoCompromisoSeleccionado;

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      fechaConsulta = new Date();

      listaItemResultadoRetroalimentacion = UtilidadesItem.getListaSel(
              iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_RESULTADO_RETROALIMENTACION_COMPROMISO),
              "idConstantes",
              "valor");

      listaItemItemComentario = UtilidadesItem.getListaSel(
              iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ITEM_COMENTARIO_REVISION_PROYECTO),
              "idConstantes",
              "valor");

      cargarListaCambosBusqueda();

      cargarListaUnidadesNoSeHanPresentado();

      //LA PRIMERA VEZ SE CARGAN TODOS LOS COMPROMISOS
      //EL FILTRO DE UNIDAD POLICIAL ES NULL
      cargarListaCompromisos(null);

      cargarComboItemUnidadPolicial();

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24  ", e);

    }

    return null;

  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      compromisoProyectoDTOSeleccionadoVista = null;
      idUnidadPolicialSeleccionado = null;
      listaUnidadPolicialSelectItem = null;
      fechaConsulta = null;
      listaItemTiposProyectos = null;
      listaItemConvocatorias = null;
      listaItemAnio = null;
      listaItemTipoCompromiso = null;
      listadoUnidadesNoSeHanPresentadoCompromiso = null;
      listaItemResultadoRetroalimentacion = null;
      comentarioCompromisoProyectoSeleccionado = null;
      listaItemItemComentario = null;
      tituloProyecto = null;
      itemComentarioSeleccionado = null;
      idTabSeleccionado = 0;
      compromisoProyectoDTOSeleccionado = null;
      listaCompromisoProyectoDTO = null;
      listaComentarioCompromisoProyectoSeleccionado = null;
      idListaItemTiposProyectosSeleccionado = null;
      idListaItemConvocatoriasSeleccionado = null;
      idListaItemAnioSeleccionado = null;
      idListaItemTipoCompromisoSeleccionado = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24  ", e);
    }

  }

  /**
   *
   * @param event
   */
  public void seleccionComprimiso(SelectEvent event) {
    try {

      if (compromisoProyectoDTOSeleccionadoVista == null) {

        return;

      }

      //VERIFICAMOS K TIPO DE COMPROMISO ES
      if (IConstantes.ORIGEN_COMPROMISO_PROYECTO.equals(compromisoProyectoDTOSeleccionadoVista.getOrigenCompromiso())) {

        CompromisoProyecto compromisoProyectoBusqueda = iCompromisoProyectoLocal.obtenerCompromisoProyecto(compromisoProyectoDTOSeleccionadoVista.getIdCompromiso());
        Long idTipoCompromiso = compromisoProyectoBusqueda.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes();

        if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                || idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL) || idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE_DOS)) {
          //El sistema muestra el detalle del compromiso  (Informe de avance o informe final) 
          //CU-PR-11 Consultar detalle de compromiso
          String retorno = cuPr11ConsultarDetalleCompromisoFaces.initReturnCU_Llamado_Desde_CUPR24(compromisoProyectoDTOSeleccionadoVista.getIdCompromiso(), true);
          navigationFaces.redirectFacesCuGenerico(retorno);

        } else if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)) {
          //El sistema identifica que el compromiso a revisar corresponde a un Formulación de proyectos.
          //1.El sistema muestra el detalle del Formulación del  proyecto. CU-PR-06
          String retorno = cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_PR_24(compromisoProyectoBusqueda.getProyecto().getIdProyecto());
          navigationFaces.redirectFacesCuGenerico(retorno);

        }
      } else if (IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION.equals(compromisoProyectoDTOSeleccionadoVista.getOrigenCompromiso())) {

        CompromisoImplementacion compromisoImplementacionBusqueda = iCompromisoImplementacionLocal.
                obtenerCompromisoImplementacionPorId(compromisoProyectoDTOSeleccionadoVista.getIdCompromiso());

        if (compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO)) {
          //El sistema identifica que el compromiso a revisar corresponde al plan de trabajo de la implementación.
          //1.El sistema muestra el detalle del plan de trabajo. 
          //Grupo de datos [3] que corresponde a los datos para consulta 
          //del CU-PR-21 Registrar plan de trabajo de la implementación de la investigación.
          String retorno = cuPr21RegistrarPlanDeTrabajoFaces.initReturnCU_DESDE_CU_PR_24(
                  compromisoImplementacionBusqueda.getImplementacionesProyecto().getIdImplementacionProy(),
                  compromisoImplementacionBusqueda.getIdCompromisoImplementacion(),
                  true);

          navigationFaces.redirectFacesCuGenerico(retorno);

        } else if (compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)
                || compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

          //1.El sistema muestra el detalle del informe de avance o informe final, 
          //según corresponda. Grupo de datos [5] que corresponde a los datos para consulta del 
          //CU-PR-15 Registrar informe de avance de la implementación de la investigación.
          String retorno = cuPr15_1_2_AvanceImplemenacionFaces.initReturnCU_DESDE_PR_24(
                  compromisoImplementacionBusqueda.getImplementacionesProyecto().getIdImplementacionProy(),
                  compromisoImplementacionBusqueda.getIdCompromisoImplementacion(), true);

          navigationFaces.redirectFacesCuGenerico(retorno);
        }

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);
    }
  }

  public void noSeleccionCompromiso(UnselectEvent event) {

    compromisoProyectoDTOSeleccionadoVista = null;

  }

  /**
   *
   * @return
   */
  public String buscarUnidadesFuncionalesNoPresentanCompromisos() {

    try {

      listadoUnidadesNoSeHanPresentadoCompromiso = iCompromisoProyectoLocal.getListaUnidadPolicialNohanPresentadoPropuestaCompromisoEimplementacion(
              listaIdEstadoCompromisoNohanPresentado,
              idListaItemTiposProyectosSeleccionado,
              idListaItemConvocatoriasSeleccionado,
              idListaItemAnioSeleccionado,
              idListaItemTipoCompromisoSeleccionado);

      if (listadoUnidadesNoSeHanPresentadoCompromiso.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_24_lbl_busqueda_vacioa_de_compromisos"));

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24  ", e);
    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaCambosBusqueda() throws Exception {

    listaItemTiposProyectos = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_DE_PROYECTO),
            "idConstantes",
            "valor");

    listaItemConvocatorias = new ArrayList<SelectItem>();

    listaItemAnio = new ArrayList<SelectItem>();

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
    if ("idtab_include_listado_compromisos_enviados_jefe_unidad".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idtab_include_listado_unidades_no_se_han_presentado_compromisos".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    }
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaUnidadesNoSeHanPresentado() throws Exception {
    //listadoUnidadesNoSeHanPresentado
  }

  /**
   *
   * @throws Exception
   */
  private void cargarComboItemUnidadPolicial() throws Exception {

    //CARGAMOS LA LISTA DE UNIDAD POLICIAL AL COMBO
    listaUnidadPolicialSelectItem = new ArrayList<SelectItem>();
    Set<Long> listaIdUnidadPolicialItem = new HashSet<Long>();

    for (CompromisoProyectoDTO compromisoProyectoDTO : listaCompromisoProyectoDTO) {

      if (listaIdUnidadPolicialItem.add(compromisoProyectoDTO.getIdUnidadPolicial())) {

        listaUnidadPolicialSelectItem.add(new SelectItem(compromisoProyectoDTO.getIdUnidadPolicial(), compromisoProyectoDTO.getNombreUnidadPolicial()));

      }
    }

  }

  /**
   *
   */
  public void handleUnidadPolicialSeleccionadaChange() {

    try {

      cargarListaCompromisos(idUnidadPolicialSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24  ", e);

    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaCompromisos(Long idUnidadPolicial) throws Exception {

    //VERIFICAMOS EL PERFIL ROL QUE TIENE EL USUARIO
    String codigoProyectoInstitucional = null;
    String codigoProyectoConvocatoria = null;
    if (loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_INSTITUCIONALES_VICIN)) {

      codigoProyectoInstitucional = keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion");

    }
    if (loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN)) {

      codigoProyectoConvocatoria = keyPropertiesFactory.value("cu_co_4_codigo_proyecto_inicia_generacion");

    }

    listaCompromisoProyectoDTO = new ListGenericDataModel<CompromisoProyectoDTO>(
            iCompromisoProyectoLocal.getCompromisoProyectoDTOEnvidosAvicinporListaEstado(
                    listaIdEstadoCompromiso,
                    codigoProyectoInstitucional,
                    codigoProyectoConvocatoria,
                    idUnidadPolicial));

  }

  /**
   *
   * @return
   */
  public String guardarComentarioCompromiso() {

    try {

      if (compromisoProyectoDTOSeleccionado == null) {

        return null;
      }

      Long idCompromisoProyecto = null;
      Long idCompromisoImplementacion = null;

      if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

        idCompromisoProyecto = compromisoProyectoDTOSeleccionado.getIdCompromiso();

      } else if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

        idCompromisoImplementacion = compromisoProyectoDTOSeleccionado.getIdCompromiso();

      }

      //VERIFICAMOS QUE EL ITEM COMENTARIO NO EXISTA 
      //SOLO APLICA PARA LOS ITEM DIFERENTE DE OTROS
      if (!IConstantes.TIPO_ITEM_COMENTARIO_REVISION_PROYECTO_OTRO.equals(itemComentarioSeleccionado)) {

        List<ComentarioCompromisoProyecto> listaComentarioPro = iComentarioProyectoLocal
                .getListaComentarioCompromisoProyectoPorIdCompromisoProyectoItemSeleccionado(
                        idCompromisoProyecto,
                        idCompromisoImplementacion,
                        IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_24,
                        itemComentarioSeleccionado);

        if (!listaComentarioPro.isEmpty()) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_22_error_item_registrado_actualmente"));
          return null;
        }
      }

      comentarioCompromisoProyectoSeleccionado.setItem(new Constantes(itemComentarioSeleccionado));

      //VERIFICAMOS SI EL USUARIO TIENE EL ROL: ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN
      RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN);
      if (rolUsuarioDTO == null) {

        //SI NO TIENE EL ROL ANTERIOR (ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN) 
        //DEBE TENER EL ROL: ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_INSTITUCIONALES_VICIN
        rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_INSTITUCIONALES_VICIN);
      }
      comentarioCompromisoProyectoSeleccionado.setUsuarioRol(new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol()));

      comentarioCompromisoProyectoSeleccionado.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      comentarioCompromisoProyectoSeleccionado.setIdentificacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      comentarioCompromisoProyectoSeleccionado.setNombreCompleto(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());

      comentarioCompromisoProyectoSeleccionado.setAutorCasoUso(IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_24);

      if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

        comentarioCompromisoProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(compromisoProyectoDTOSeleccionado.getIdCompromiso()));

      } else if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

        comentarioCompromisoProyectoSeleccionado.setCompromisoImplementacion(new CompromisoImplementacion(compromisoProyectoDTOSeleccionado.getIdCompromiso()));

      }

      //GUARDAMOS EL COMENTARIO
      iComentarioProyectoLocal.guardarCompromisoProyecto(comentarioCompromisoProyectoSeleccionado);

      comentarioCompromisoProyectoSeleccionado = new ComentarioCompromisoProyecto();

      itemComentarioSeleccionado = null;

      //ACTUALIZAMOS LA LISTA DE COMENTARIOS
      cargaListaComentarioProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_22_info_comentario_guardado_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);
    }
    return null;
  }

  /**
   *
   */
  public void handleFiltroBusquedaTipoProyectoChange() {

    try {

      listaItemConvocatorias = null;
      listaItemTipoCompromiso = null;
      listaItemAnio = null;

      if (idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES)) {

        listaItemAnio = new ArrayList<SelectItem>();
        //CARGAMOS TODOS LOS AÑOS QUE SE HAN HABILITADOS PARA PROYECTOS INSTITUCIONALES
        List<Periodo> listaAnios = iPeriodoLocal.getTodosAniosHabilitadosParaProyectosInstitucionales();

        for (Periodo unPeriodo : listaAnios) {

          String descripcion = unPeriodo.getDescripcion().trim();
          if (descripcion.length() > 100) {

            descripcion = descripcion.substring(0, 99).concat("...");//SOLO TOMAMOS 100 CARACTERES

          }
          listaItemAnio.add(new SelectItem(unPeriodo.getIdPeriodo(), unPeriodo.getAnio().toString().concat(" - ").concat(descripcion)));

        }
      } else if (idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION)) {

        listaItemConvocatorias = UtilidadesItem.getListaSel(iPeriodoLocal.getListaConvocatoriasPorTipoConvocatoria(
                IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION,
                listaIdEstadoConvocatoriaPublicada),
                "idPeriodo",
                "numeroConcecutivoConsulta");
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);

    }

  }

  /**
   *
   */
  public void handleFiltroBusquedaTipoCompromisoPorAnioChange() {

    try {

      if (idListaItemAnioSeleccionado == null) {

        listaItemTipoCompromiso = new ArrayList<SelectItem>();
        return;
      }

      listaItemTipoCompromiso = UtilidadesItem.getListaSel(
              iCompromisoPeriodoLocal.getListaCompromisoPeriodoDTOporIdPeriodo(idListaItemAnioSeleccionado),
              "idCompromisoPeriodo",
              "nombreCompromisoNumeroIncrementa");

      if (listaItemTipoCompromiso.isEmpty()) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_24_lbl_no_tipo_compromiso_convocatoria_lista_vacia"));
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);

    }

  }

  /**
   *
   */
  public void handleFiltroBusquedaTipoCompromisoPorConvocatoriaChange() {

    try {

      if (idListaItemConvocatoriasSeleccionado == null) {

        listaItemTipoCompromiso = new ArrayList<SelectItem>();
        return;
      }

      listaItemTipoCompromiso = UtilidadesItem.getListaSel(
              iCompromisoPeriodoLocal.getListaCompromisoPeriodoDTOporConvocatoriaPeriodo(idListaItemConvocatoriasSeleccionado),
              "idCompromisoPeriodo",
              "nombreCompromisoNumeroIncrementa");

      if (listaItemTipoCompromiso.isEmpty()) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_24_lbl_no_tipo_compromiso_convocatoria_lista_vacia"));
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);

    }

  }

  public void handleResultadoRetroalimentacionChange() {

  }

  /**
   *
   * @param event
   */
  public void selecccionarComentarioEnviar(ValueChangeEvent event) {

    try {

      event.getNewValue();
      //ACTUALIZAMOS EL COMENTARIO
      //iComentarioProyectoLocal.guardarComentarioProyecto(comentarioProyectoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);

    }
  }

  /**
   *
   * @param comentarioProyectoDTO
   * @return
   */
  public String modificarComentario(ComentarioProyectoDTO comentarioProyectoDTO) {

    try {

      itemComentarioSeleccionado = comentarioProyectoDTO.getIdItem();
      comentarioCompromisoProyectoSeleccionado = iComentarioProyectoLocal.obtenerComentariCompromisoProyecto(comentarioProyectoDTO.getIdComentarioProyecto());

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidadIrRetroalimentacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (modificarComentarioPropuestaEvaluada)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String cancelarModificarComentario() {

    try {

      itemComentarioSeleccionado = null;
      comentarioCompromisoProyectoSeleccionado = new ComentarioCompromisoProyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);
    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargaListaComentarioProyecto() throws Exception {

    if (compromisoProyectoDTOSeleccionado == null || compromisoProyectoDTOSeleccionado.getIdCompromiso() == null) {

      listaComentarioCompromisoProyectoSeleccionado = new ArrayList<ComentarioProyectoDTO>();
      return;
    }

    Long idCompromisoProyecto = null;
    Long idCompromisoImplementacion = null;

    if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

      idCompromisoProyecto = compromisoProyectoDTOSeleccionado.getIdCompromiso();

    } else if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION)) {

      idCompromisoImplementacion = compromisoProyectoDTOSeleccionado.getIdCompromiso();

    }

    listaComentarioCompromisoProyectoSeleccionado = iComentarioProyectoLocal.getComentarioCompromisoProyectoPorCompromiso(
            idCompromisoProyecto,
            idCompromisoImplementacion,
            IConstantes.ORIGEN_COMENTARIO_COMPROMISO_PROYECTO_CU_PR_24);

  }

  /**
   *
   * @return
   */
  public String guardarRetroalimentacion() {

    try {

      if (compromisoProyectoDTOSeleccionado.getIdResultadoRetroalimentacion().equals(
              IConstantes.ID_TIPO_RESULTADO_RETROALIMENTACION_COMPROMISO_REQUIERE_CORRECCION)) {

        if (DatesUtils.compareDate(compromisoProyectoDTOSeleccionado.getFechaNuevoCompromiso(), new Date()) <= 0) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_24_lbl_error_fecha_nuevo_compromiso_mayor"));
          return null;

        }
      }

      iCompromisoProyectoLocal.actualizarResultadoRetroalimentacionCompromisoProyectoEimplementacion(
              compromisoProyectoDTOSeleccionado.getFechaNuevoCompromiso(),
              compromisoProyectoDTOSeleccionado.getIdResultadoRetroalimentacion(),
              compromisoProyectoDTOSeleccionado.getIdCompromiso(),
              compromisoProyectoDTOSeleccionado.getOrigenCompromiso());

      //ACTUALIZAMOS LA LISTA DE COMPROMISOS, PARA QUE SE ACTUALIZEN LOS ESTADOS
      cargarListaCompromisos(null);

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_24_lbl_guardar_retroalimentacion"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);
    }

    return null;
  }

  /**
   *
   * @param actionEvent
   */
  public void enviarCorreccionesUnidadPolicial(ActionEvent actionEvent) {

    try {

      //VERIFICAMOS QUE LA FECHA ESTE SELECCIONADA
      if (compromisoProyectoDTOSeleccionado.getFechaNuevoCompromiso() == null) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_24_lbl_error_fecha_nuevo_compromiso_falta"));
        return;
      }

      //VALIDAMOS LA FECHA
      if (DatesUtils.compareDate(compromisoProyectoDTOSeleccionado.getFechaNuevoCompromiso(), new Date()) <= 0) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_24_lbl_error_fecha_nuevo_compromiso_mayor"));
        return;
      }

      //VERIFICAMOS QUE EXISTA POR LE MENOS UN COMENTARIO A ENVIAR
      if (listaComentarioCompromisoProyectoSeleccionado == null || listaComentarioCompromisoProyectoSeleccionado.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_24_lbl_error_comentario_enviar_porlomenosuno_falta"));
        return;
      }

      //ACTUALIZAMOS EL COMPROMISO
      iCompromisoProyectoLocal.actualizarCompromisoProyectoEimplementacionResultadoRetroalimentacionEnviarCompromisoUnidadPolicial(
              compromisoProyectoDTOSeleccionado.getFechaNuevoCompromiso(),
              compromisoProyectoDTOSeleccionado.getIdResultadoRetroalimentacion(),
              compromisoProyectoDTOSeleccionado.getIdCompromiso(),
              compromisoProyectoDTOSeleccionado.getOrigenCompromiso(),
              IConstantes.ESTADO_COMPROMISO_PROYECTO_CORRECCION_COMPROMISO);

      Map<String, String> parametrosAsunto = new HashMap<String, String>();
      parametrosAsunto.put("{_parametro1_}", compromisoProyectoDTOSeleccionado.getCompromiso());

      Map<String, Object> parametrosContenido = new HashMap<String, Object>();
      parametrosContenido.put("{_parametro1_}", compromisoProyectoDTOSeleccionado.getCompromiso());

      try {

        //ENVÍA MAIL AL JEFE DE LA UNIDAD
        iMailSessionLocal.enviarMail_RolUnidadPolicial(
                IConstantes.CU_PR_24_INFORMA_CORRECCION_COMPROMISO,
                parametrosAsunto,
                parametrosContenido,
                IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
                compromisoProyectoDTOSeleccionado.getIdUnidadPolicial()
        );
        //Y AL RESPONSABLE DE PROYECTOS DE INVESTIGACIÓN EN LA UNIDAD POLICIAL RESPECTIVA
        iMailSessionLocal.enviarMail_RolUnidadPolicial(
                IConstantes.CU_PR_24_INFORMA_CORRECCION_COMPROMISO,
                parametrosAsunto,
                parametrosContenido,
                IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL,
                compromisoProyectoDTOSeleccionado.getIdUnidadPolicial()
        );
      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);
      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_24_lbl_compromiso_enviado_correccion_unidad"));

      String retorno = initReturnCU();

      navigationFaces.redirectFacesCuGenerico(retorno);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);

    }

  }

  /**
   *
   * @param actionEvent
   */
  public void aceptarCumplimiento(ActionEvent actionEvent) {

    try {

      if (compromisoProyectoDTOSeleccionado.getOrigenCompromiso().equals(IConstantes.ORIGEN_COMPROMISO_PROYECTO)) {

        //HACEMOS LAS VERIFICACIONES ANTES DE ACEPTAR EL COMPROMISO.
        //ACTUALIZAMOS EL COMPROMISO
        if (compromisoProyectoDTOSeleccionado.getIdTipoCompromiso().equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {

          //SI EL INFORME ES: "COMPROMISO_PERIODO_INFORME_FINAL"
          //VERIFICAMOS QUE LOS INFORMES: "COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO" Y "COMPROMISO_PERIODO_INFORME_DE_AVANCE"
          //SE ENCUENTREN CUMPLIDOS
          int numeroCompromisosPorEnviar = iCompromisoProyectoLocal.contarCompromisoroyectoPorProyectoYlistaEstado(
                  compromisoProyectoDTOSeleccionado.getIdProyecto(),
                  listaIdEstadoCompromisoContar);
          //VERIFICAMOS SI EL NUMERO DE COMPROMISO ES IGUAL A 1
          //ESTO SIGNIFICA QUE SOLO FALTA POR ACEPTAR EL COMPROMISO "COMPROMISO_PERIODO_INFORME_FINAL" ACTUAL
          if (numeroCompromisosPorEnviar != 1) {

            adicionaMensajeError("Para aceptar el cumplimiento del compromiso: ".concat(compromisoProyectoDTOSeleccionado.getCompromiso().concat(", los demas compromisos deben estar aceptados.")));
            return;

          }

        }

      }

      iCompromisoProyectoLocal.actualizarCompromisoProyectoEimplementacionResultadoRetroalimentacionAceptarCumplimiento(
              compromisoProyectoDTOSeleccionado.getFechaNuevoCompromiso(),
              compromisoProyectoDTOSeleccionado.getIdResultadoRetroalimentacion(),
              compromisoProyectoDTOSeleccionado.getIdCompromiso(),
              compromisoProyectoDTOSeleccionado.getOrigenCompromiso(),
              IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO);

      String retorno = initReturnCU();

      navigationFaces.redirectFacesCuGenerico(retorno);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24  ", e);

    }

  }

  /**
   *
   * @param compromisoProyectoDTO
   * @param comentario
   */
  public void handledFijarComentario(CompromisoProyectoDTO compromisoProyectoDTO, String comentario) {
    compromisoProyectoDTO.setComentario(comentario);
  }

  /**
   *
   * @param compromisoProyectoDTO
   * @return
   */
  public String seleccionComprimisoProyecto(CompromisoProyectoDTO compromisoProyectoDTO) {

    try {

      this.compromisoProyectoDTOSeleccionado = compromisoProyectoDTO;
      tituloProyecto = compromisoProyectoDTO.getTituloProyecto();
      comentarioCompromisoProyectoSeleccionado = new ComentarioCompromisoProyecto();

      itemComentarioSeleccionado = null;

      //ACTUALIZAMOS LA LISTA DE COMENTARIOS
      cargaListaComentarioProyecto();

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidadIrRetroalimentacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24  ", e);

    }
    return null;

  }

  public ListGenericDataModel<CompromisoProyectoDTO> getListaCompromisoProyectoDTO() {
    return listaCompromisoProyectoDTO;
  }

  public void setListaCompromisoProyectoDTO(ListGenericDataModel<CompromisoProyectoDTO> listaCompromisoProyectoDTO) {
    this.listaCompromisoProyectoDTO = listaCompromisoProyectoDTO;
  }

  public CompromisoProyectoDTO getCompromisoProyectoDTOSeleccionado() {
    return compromisoProyectoDTOSeleccionado;
  }

  public void setCompromisoProyectoDTOSeleccionado(CompromisoProyectoDTO compromisoProyectoDTOSeleccionado) {
    this.compromisoProyectoDTOSeleccionado = compromisoProyectoDTOSeleccionado;
  }

  public int getNumeroRegistroCompromisos() {

    if (listaCompromisoProyectoDTO == null) {
      return 0;
    }

    return listaCompromisoProyectoDTO.getNumeroRegistro();
  }

  public boolean isMostrarBtns() {

    return listaCompromisoProyectoDTO != null && listaCompromisoProyectoDTO.getNumeroRegistro() > 0;

  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public String getTituloProyecto() {
    return tituloProyecto;
  }

  public void setTituloProyecto(String tituloProyecto) {
    this.tituloProyecto = tituloProyecto;
  }

  public Long getItemComentarioSeleccionado() {
    return itemComentarioSeleccionado;
  }

  public void setItemComentarioSeleccionado(Long itemComentarioSeleccionado) {
    this.itemComentarioSeleccionado = itemComentarioSeleccionado;
  }

  public List<SelectItem> getListaItemItemComentario() {
    return listaItemItemComentario;
  }

  public void setListaItemItemComentario(List<SelectItem> listaItemItemComentario) {
    this.listaItemItemComentario = listaItemItemComentario;
  }

  public ComentarioCompromisoProyecto getComentarioCompromisoProyectoSeleccionado() {
    return comentarioCompromisoProyectoSeleccionado;
  }

  public void setComentarioCompromisoProyectoSeleccionado(ComentarioCompromisoProyecto comentarioCompromisoProyectoSeleccionado) {
    this.comentarioCompromisoProyectoSeleccionado = comentarioCompromisoProyectoSeleccionado;
  }

  public List<ComentarioProyectoDTO> getListaComentarioCompromisoProyectoSeleccionado() {
    return listaComentarioCompromisoProyectoSeleccionado;
  }

  public void setListaComentarioCompromisoProyectoSeleccionado(List<ComentarioProyectoDTO> listaComentarioCompromisoProyectoSeleccionado) {
    this.listaComentarioCompromisoProyectoSeleccionado = listaComentarioCompromisoProyectoSeleccionado;
  }

  public List<SelectItem> getListaItemResultadoRetroalimentacion() {
    return listaItemResultadoRetroalimentacion;
  }

  public void setListaItemResultadoRetroalimentacion(List<SelectItem> listaItemResultadoRetroalimentacion) {
    this.listaItemResultadoRetroalimentacion = listaItemResultadoRetroalimentacion;
  }

  public boolean isMostrarBtnAceptarCumplimientoCompromiso() {
    return compromisoProyectoDTOSeleccionado != null
            && IConstantes.ID_TIPO_RESULTADO_RETROALIMENTACION_COMPROMISO_NO_REQUIERE_CORRECCION.equals(compromisoProyectoDTOSeleccionado.getIdResultadoRetroalimentacion());

  }

  public boolean isMostrarBtnEnviarCorreccionesUnidadPolicial() {
    return compromisoProyectoDTOSeleccionado != null
            && IConstantes.ID_TIPO_RESULTADO_RETROALIMENTACION_COMPROMISO_REQUIERE_CORRECCION.equals(compromisoProyectoDTOSeleccionado.getIdResultadoRetroalimentacion());

  }

  public List<CompromisoProyectoDTO> getListadoUnidadesNoSeHanPresentadoCompromiso() {
    return listadoUnidadesNoSeHanPresentadoCompromiso;
  }

  public List<SelectItem> getListaItemTiposProyectos() {
    return listaItemTiposProyectos;
  }

  public void setListaItemTiposProyectos(List<SelectItem> listaItemTiposProyectos) {
    this.listaItemTiposProyectos = listaItemTiposProyectos;
  }

  public List<SelectItem> getListaItemConvocatorias() {
    return listaItemConvocatorias;
  }

  public void setListaItemConvocatorias(List<SelectItem> listaItemConvocatorias) {
    this.listaItemConvocatorias = listaItemConvocatorias;
  }

  public List<SelectItem> getListaItemAnio() {
    return listaItemAnio;
  }

  public void setListaItemAnio(List<SelectItem> listaItemAnio) {
    this.listaItemAnio = listaItemAnio;
  }

  public List<SelectItem> getListaItemTipoCompromiso() {
    return listaItemTipoCompromiso;
  }

  public void setListaItemTipoCompromiso(List<SelectItem> listaItemTipoCompromiso) {
    this.listaItemTipoCompromiso = listaItemTipoCompromiso;
  }

  public Date getFechaConsulta() {
    return fechaConsulta;
  }

  public void setFechaConsulta(Date fechaConsulta) {
    this.fechaConsulta = fechaConsulta;
  }

  public Long getIdListaItemTiposProyectosSeleccionado() {
    return idListaItemTiposProyectosSeleccionado;
  }

  public void setIdListaItemTiposProyectosSeleccionado(Long idListaItemTiposProyectosSeleccionado) {
    this.idListaItemTiposProyectosSeleccionado = idListaItemTiposProyectosSeleccionado;
  }

  public Long getIdListaItemConvocatoriasSeleccionado() {
    return idListaItemConvocatoriasSeleccionado;
  }

  public void setIdListaItemConvocatoriasSeleccionado(Long idListaItemConvocatoriasSeleccionado) {
    this.idListaItemConvocatoriasSeleccionado = idListaItemConvocatoriasSeleccionado;
  }

  public Long getIdListaItemAnioSeleccionado() {
    return idListaItemAnioSeleccionado;
  }

  public void setIdListaItemAnioSeleccionado(Long idListaItemAnioSeleccionado) {
    this.idListaItemAnioSeleccionado = idListaItemAnioSeleccionado;
  }

  public Long getIdListaItemTipoCompromisoSeleccionado() {
    return idListaItemTipoCompromisoSeleccionado;
  }

  public void setIdListaItemTipoCompromisoSeleccionado(Long idListaItemTipoCompromisoSeleccionado) {
    this.idListaItemTipoCompromisoSeleccionado = idListaItemTipoCompromisoSeleccionado;
  }

  public boolean isMostrarFiltroBusquedaAnio() {

    return idListaItemTiposProyectosSeleccionado != null && idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES);

  }

  public boolean isMostrarFiltroBusquedaTipoCompromiso() {

    return listaItemTipoCompromiso != null && !listaItemTipoCompromiso.isEmpty();

  }

  public boolean isMostrarFiltroBusquedaConvocatoria() {

    return idListaItemTiposProyectosSeleccionado != null && idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION);

  }

  public Long getIdUnidadPolicialSeleccionado() {
    return idUnidadPolicialSeleccionado;
  }

  public void setIdUnidadPolicialSeleccionado(Long idUnidadPolicialSeleccionado) {
    this.idUnidadPolicialSeleccionado = idUnidadPolicialSeleccionado;
  }

  public List<SelectItem> getListaUnidadPolicialSelectItem() {
    return listaUnidadPolicialSelectItem;
  }

  public void setListaUnidadPolicialSelectItem(List<SelectItem> listaUnidadPolicialSelectItem) {
    this.listaUnidadPolicialSelectItem = listaUnidadPolicialSelectItem;
  }

  public CompromisoProyectoDTO getCompromisoProyectoDTOSeleccionadoVista() {
    return compromisoProyectoDTOSeleccionadoVista;
  }

  public void setCompromisoProyectoDTOSeleccionadoVista(CompromisoProyectoDTO compromisoProyectoDTOSeleccionadoVista) {
    this.compromisoProyectoDTOSeleccionadoVista = compromisoProyectoDTOSeleccionadoVista;
  }
}
