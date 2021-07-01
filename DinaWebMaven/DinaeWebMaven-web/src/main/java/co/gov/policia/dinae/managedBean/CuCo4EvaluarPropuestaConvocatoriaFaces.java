package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ArchivoReporte;
import co.gov.policia.dinae.dto.ComentarioProyectoDTO;
import co.gov.policia.dinae.dto.CriterioEvaluacionDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.enums.EnumFuncionalidadCompromiso;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IComentarioProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICriterioEvaluacionLocal;
import co.gov.policia.dinae.interfaces.ICuPr23GestionaCompromisoAccionRegresar;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.ComentarioProyecto;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.CriterioEvaluacion;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuCo4EvaluarPropuestaConvocatoriaFaces")
@javax.enterprise.context.SessionScoped
public class CuCo4EvaluarPropuestaConvocatoriaFaces extends JSFUtils implements Serializable, ICuPr23GestionaCompromisoAccionRegresar {

  private Periodo periodoSeleccionado;
  private List<SelectItem> listaItemUnidadPolicialFiltroBusqueda;
  private List<SelectItem> listaItemEstadoFiltroBusqueda;
  private List<SelectItem> listaItemItemComentario;
  private List<SelectItem> listaItemItemEstadoPropuestaFinancia;
  private Long itemComentarioSeleccionado;
  private Long unidadPolicialSeleccionada;
  private Long estadoSeleccionada;
  private ListGenericDataModel<ProyectoDTO> listaPropuestaConvocatorias;
  private ProyectoDTO propuestaSeleccionada;
  private List<ProyectoDTO> listaPropuestaConvocatoriasAfinanciar;
  private ProyectoDTO proyectoDTOSeleccionadoConsulta;
  private ProyectoDTO proyectoDTOSeleccionadoRevisar;
  private List<CriterioEvaluacionDTO> listaCriterioPropuestaConvocatoriaDTO;
  private CriterioEvaluacionDTO criterioEvaluacionDTO100porciento;
  private ComentarioProyecto comentarioProyectoSeleccionado;
  private List<ComentarioProyectoDTO> listaComentarioProyecto;
  private String regresarDesdeComentario;
  private List<CompromisoPeriodo> listaCompromisoPeriodo;

  @javax.inject.Inject
  private PresupuestoUtilMB presupuestoUtilMB;

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @javax.inject.Inject
  private CuPr23GestionarCompromisoProyectos cuPr23GestionarCompromisoProyectos;

  private static final List<Long> listaIdFiltroPropuestaRevisar = new ArrayList<Long>();

  static {
    listaIdFiltroPropuestaRevisar.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EVALUADA);
    listaIdFiltroPropuestaRevisar.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ENVIADA_A_VICIN);
  }

  private static final List<Long> listaIdFiltroPropuestaFinanciar = new ArrayList<Long>();

  static {

    listaIdFiltroPropuestaFinanciar.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EVALUADA);
  }

  @EJB
  private IMailSessionLocal iMailSessionBean;

  @EJB
  private ICriterioEvaluacionLocal iCriterioEvaluacionLocal;

  @EJB
  private IEvaluacionProyectoLocal iEvaluacionProyectoLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IComentarioProyectoLocal iComentarioProyectoLocal;

  @EJB
  private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @javax.annotation.PostConstruct
  public void init() {

    try {

      propuestaSeleccionada = null;
      listaCompromisoPeriodo = null;
      regresarDesdeComentario = null;
      listaItemItemEstadoPropuestaFinancia = null;
      listaPropuestaConvocatoriasAfinanciar = null;
      listaComentarioProyecto = null;
      itemComentarioSeleccionado = null;
      listaItemItemComentario = null;
      criterioEvaluacionDTO100porciento = null;
      listaCriterioPropuestaConvocatoriaDTO = null;
      proyectoDTOSeleccionadoConsulta = null;
      proyectoDTOSeleccionadoRevisar = null;
      unidadPolicialSeleccionada = null;
      listaItemUnidadPolicialFiltroBusqueda = null;
      listaItemEstadoFiltroBusqueda = null;
      periodoSeleccionado = null;
      listaPropuestaConvocatorias = null;
      comentarioProyectoSeleccionado = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias", e);
    }

  }

  /**
   *
   * @param periodoSeleccionado
   * @return
   */
  public String initReturnCU(Periodo periodoSeleccionado) {

    init();

    try {

      this.periodoSeleccionado = periodoSeleccionado;

      //CARGAMOS LA LISTA DE UNIDADES QUE SE ENCUENTRAN EN LA LISTA DE PROPUESTAS
      listaItemUnidadPolicialFiltroBusqueda = new ArrayList<SelectItem>();

      List<UnidadPolicialDTO> listaUnidadPolicialDTO = iProyectoLocal.getListaUnidadesPorPeriodoYlistaIdestadoFiltro(
              periodoSeleccionado.getIdPeriodo(),
              listaIdFiltroPropuestaRevisar);

      Set<Long> validaAdiciona = new HashSet<Long>();
      for (UnidadPolicialDTO unaUnidadPolicialDTO : listaUnidadPolicialDTO) {

        //PRIMERO VALIDAMOS SI EL OBJETO YA FUE ADICIONADO A LA LISTA DE ITEM
        if (validaAdiciona.contains(unaUnidadPolicialDTO.getIdUnidadPolicial())) {
          //SI YA FUE ADICIONADO ENTONCES, CONTINUAMOS CON EL SIGUIENTE VALOR
          continue;
        }
        //LO ADICONAMOS A LA LISTA DE ITEM
        listaItemUnidadPolicialFiltroBusqueda.add(new SelectItem(
                unaUnidadPolicialDTO.getIdUnidadPolicial(), unaUnidadPolicialDTO.getNombre()));
        //LO ADICIONAMOS A LA LISTA DE VALIDACION, QUE NOS PERMITE SABER SI EL OBJETO FUE INGRESADO A LA LISTA DE ITEM
        validaAdiciona.add(unaUnidadPolicialDTO.getIdUnidadPolicial());
      }

      //CARGAMOS LA LISTA DE ESTADO
      listaItemEstadoFiltroBusqueda = new ArrayList<SelectItem>();
      listaItemEstadoFiltroBusqueda.add(
              new SelectItem(
                      IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ENVIADA_A_VICIN,
                      keyPropertiesFactory.value("cu_co_4_item_opcion_estado_enviada_vicin")));

      listaItemEstadoFiltroBusqueda.add(
              new SelectItem(
                      IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EVALUADA,
                      keyPropertiesFactory.value("cu_co_4_item_opcion_estado_evaluada")));

      listaItemItemComentario = UtilidadesItem.getListaSel(
              iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ITEM_COMENTARIO_REVISION_PROYECTO),
              "idConstantes",
              "valor");

      //CARGAMOS LAS PROPUESTAS
      cargarListaPropuestasConvocatorias(periodoSeleccionado, null, null);

      //CARGAMOS LOS ESTADOS DE LAS PROPUESTAS A FINANCIAR
      listaItemItemEstadoPropuestaFinancia = new ArrayList<SelectItem>();
      listaItemItemEstadoPropuestaFinancia.add(
              new SelectItem(
                      IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_APROBADA,
                      keyPropertiesFactory.value("cu_co_4_lbl_datatable_select_item_estado_aprobado")));

      listaItemItemEstadoPropuestaFinancia.add(
              new SelectItem(
                      IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_NO_APROBADA,
                      keyPropertiesFactory.value("cu_co_4_lbl_datatable_select_item_estado_no_aprobado")));

      return navigationFaces.redirectCuCo4EvaluarPropuestaConvocatoriaRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (initReturnCU)", e);

    }
    return null;

  }

  public void noSeleccionPropuesta(UnselectEvent event) {
    propuestaSeleccionada = null;
  }

  /**
   *
   * @param event
   */
  public void verDatallePropuestaNecesidad(SelectEvent event) {
    try {
      if (propuestaSeleccionada != null) {

        String retorno = cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_CO_04(propuestaSeleccionada.getId());
        navigationFaces.redirectFacesCuGenerico(retorno);

      }

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias  (verDatallePropuestaNecesidad) ", ex);
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

  @Override
  public void actualizarListaCompromisoLlamado() {

    try {

      cargarListaCompromiso(periodoSeleccionado);

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (actualizarListaCompromisoLlamado)", ex);
    }
  }

  /**
   *
   * @return
   */
  public String agregarCompromiso() {

    try {

      //HACEMOS LLAMADO AL CU-PR-23-Gestionar compromisos de proyectos
      cuPr23GestionarCompromisoProyectos.funcionalidad(EnumFuncionalidadCompromiso.PROPUESTA_CONVOCATORIA,
              periodoSeleccionado, this);

      return navigationFaces.redirectCuPr23Compromisos();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (agregarCompromiso)", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String guardarPropuestasRevisadas() {

    try {

      iProyectoLocal.actualizarEstadoYpresupuestoPropuestasConvocatoriasFinancia(listaPropuestaConvocatoriasAfinanciar);

      //ACTUALIZAMOS LA LISTA DE PROYECTOA FINANCIA
      seleccionarProyectosAfinanciar();

      adicionaMensajeInformativo("La información fue almacenada correctamente.");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (guardarPropuestasRevisadas)", e);

    }
    return null;

  }

  /**
   *
   * @param proyectoDTO
   * @return
   */
  public String agregaComentariosDesdeFinanciarPropuesta(ProyectoDTO proyectoDTO) {

    try {

      regresarDesdeComentario = navigationFaces.redirectCuCo4FinanciarPropuestaConvocatoriaRedirect();

      comentarioProyectoSeleccionado = new ComentarioProyecto();

      this.proyectoDTOSeleccionadoRevisar = proyectoDTO;

      //CARGAMOS LOS COMENTARIOS PROYECTOS
      cargaListaComentarioProyecto();

      return navigationFaces.redirectCuPr22AgregarComentarioPropuestaConvocatoriaRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (agregaComentariosDesdeFinanciarPropuesta)", e);

    }
    return null;

  }

  /**
   *
   * @return
   */
  public String seleccionarProyectosAfinanciar() {

    try {

      //VERIFICAMOS QUE TODAS LAS PROPUESTAS DE CONVOCATORIAS ESTEN EN ESTADO EVALUADA
      for (ProyectoDTO unProyectoDTO : listaPropuestaConvocatorias) {

        //SI EL ESTADO ES DIFERENTE DE EVALUDA, FINALIZAMOS EL PROCESO
        if (!unProyectoDTO.getIdEstado().equals(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EVALUADA)) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_error_no_han_seleccionado_propuestas_evaluar"));
          return null;
        }
      }

      listaPropuestaConvocatoriasAfinanciar = iProyectoLocal.getListaProyectoPropuestaConvocatoriaDTOaFinanciarPorPeriodoYlistaIdEstado(
              periodoSeleccionado.getIdPeriodo(),
              listaIdFiltroPropuestaFinanciar);

      if (listaPropuestaConvocatoriasAfinanciar.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_error_no_propuestas_para_financiar"));
        return null;
      }

      for (ProyectoDTO unProyectoDTOfinanciar : listaPropuestaConvocatoriasAfinanciar) {
        presupuestoUtilMB.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL, unProyectoDTOfinanciar.getId(), null);
        unProyectoDTOfinanciar.setPresupuestoSolicitado(BigDecimal.valueOf(presupuestoUtilMB.getTotalTotales()));
      }

      //CARGAMOS LA LISTA DE COMPROMISOS
      cargarListaCompromiso(periodoSeleccionado);

      return navigationFaces.redirectCuCo4FinanciarPropuestaConvocatoriaRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (initReturnCU)", e);

    }
    return null;
  }

  /**
   *
   */
  public void recargarValorTotal() {

    try {

      if (listaCriterioPropuestaConvocatoriaDTO == null || listaCriterioPropuestaConvocatoriaDTO.isEmpty()) {
        return;
      }
      //OBTENEMOS EL ULTIMO VALOR DE LA LISTA, QUE ES EL EQUIVALENTE AL TOTAL
      CriterioEvaluacionDTO criterioEvaluacionDTO = listaCriterioPropuestaConvocatoriaDTO.get(listaCriterioPropuestaConvocatoriaDTO.size() - 1);
      //SUMAMOS LOS VALORES 
      int suma100PorcientoValorIngresado = 0;
      for (CriterioEvaluacionDTO unCriterioEvaluacionDTO : listaCriterioPropuestaConvocatoriaDTO) {

        if (unCriterioEvaluacionDTO.isTotal() || unCriterioEvaluacionDTO.getValorIngresadoConvocatoria() == null) {
          continue;
        }
        if (unCriterioEvaluacionDTO.getValorIngresadoConvocatoria() > unCriterioEvaluacionDTO.getValorMaximoConvocatoria()) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_lbl_criterio_excede_valor_maximo", new Object[]{unCriterioEvaluacionDTO.getCriterio()}));
          return;
        }

        suma100PorcientoValorIngresado += unCriterioEvaluacionDTO.getValorIngresadoConvocatoria() == null ? 0 : unCriterioEvaluacionDTO.getValorIngresadoConvocatoria();
      }
      criterioEvaluacionDTO.setValorIngresadoConvocatoria(suma100PorcientoValorIngresado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (recargarValorTotal)", e);

    }

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
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (selecccionarComentarioEnviar)", e);

    }
  }

  public String agregarComentarioPropuestaEvaluada() {

    try {

      //CARGAMOS LOS COMENTARIOS PROYECTOS
      cargaListaComentarioProyecto();
      comentarioProyectoSeleccionado = new ComentarioProyecto();

      return navigationFaces.redirectCuPr22AgregarComentarioPropuestaConvocatoriaRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (initReturnCU)", e);

    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargaListaComentarioProyecto() throws Exception {

    if (proyectoDTOSeleccionadoRevisar == null || proyectoDTOSeleccionadoRevisar.getId() == null) {
      listaComentarioProyecto = new ArrayList<ComentarioProyectoDTO>();
      return;
    }
    listaComentarioProyecto = iComentarioProyectoLocal.getComentarioProyectoDTOPorProyecto(proyectoDTOSeleccionadoRevisar.getId());

  }

  public String modificarComentarioPropuestaEvaluada(ComentarioProyectoDTO comentarioProyectoDTO) {

    try {

      itemComentarioSeleccionado = comentarioProyectoDTO.getIdItem();
      comentarioProyectoSeleccionado = iComentarioProyectoLocal.obtenerComentarioProyectoPorId(comentarioProyectoDTO.getIdComentarioProyecto());

      return navigationFaces.redirectCuPr22AgregarComentarioPropuestaConvocatoriaRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (modificarComentarioPropuestaEvaluada)", e);
    }
    return null;
  }

  public String cancelarModificarComentarioPropuestaEvaluada() {

    try {

      itemComentarioSeleccionado = null;
      comentarioProyectoSeleccionado = new ComentarioProyecto();

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
  public String guardarComentarioPropuestaEvaluada() {

    try {

      //SI ES UN COMENTARIO NUEVO
      if (comentarioProyectoSeleccionado.getIdComentarioProyecto() == null) {

        //VERIFICAMOS QUE EL ITEM COMENTARIO NO EXISTA
        List<ComentarioProyecto> listaComentarioPro = iComentarioProyectoLocal.getComentarioProyectoPorItemYproyecto(itemComentarioSeleccionado, proyectoDTOSeleccionadoRevisar.getId());
        if (!listaComentarioPro.isEmpty()) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_22_error_item_registrado_actualmente"));
          return null;
        }

        comentarioProyectoSeleccionado.setItem(new Constantes(itemComentarioSeleccionado));
        comentarioProyectoSeleccionado.setProyecto(new Proyecto(proyectoDTOSeleccionadoRevisar.getId()));
        comentarioProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
                loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_CONVOCATORIAS_EN_LA_VICIN).getIdUsuarioRol()
        ));
        comentarioProyectoSeleccionado.setIdentificacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        comentarioProyectoSeleccionado.setNombreCompleto(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());

      }

      //GUARDAMOS EL COMENTARIO
      iComentarioProyectoLocal.guardarComentarioProyecto(comentarioProyectoSeleccionado);
      comentarioProyectoSeleccionado = new ComentarioProyecto();
      itemComentarioSeleccionado = null;

      //ACTUALIZAMOS LA LISTA DE COMENTARIOS
      cargaListaComentarioProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_22_info_comentario_guardado_ok"));

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (guardarComentarioPropuestaEvaluada)", e);
    }
    return null;
  }

  /**
   *
   * @param actionEvent
   */
  public void publicarPropuestasRevisadas(ActionEvent actionEvent) {

    try {

      //SE VALIDA QUE TODAS LAS PROPUESTAS TENGAN UN ESTADO ASOCIADO (PARA FINANCIAR O NO APROBADA), 
      //SE VALIDA QUE LAS PROPUESTAS EN ESTADO PARA FINANCIAR TENGAN ASIGNADO EL PRESUPUESTO APROBADO
      if (listaPropuestaConvocatoriasAfinanciar == null || listaPropuestaConvocatoriasAfinanciar.isEmpty()) {
        return;
      }

      for (ProyectoDTO unProyectoDTO : listaPropuestaConvocatoriasAfinanciar) {

        if (IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_APROBADA.equals(unProyectoDTO.getEstadoTemporalFinancia())
                || IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_NO_APROBADA.equals(unProyectoDTO.getEstadoTemporalFinancia())) {
          //PROYECTO CON ID VALIDO
          if (unProyectoDTO.getEstadoTemporalFinancia().equals(IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_APROBADA)) {
            //VERIFICAMOS QUE EL VALOR A FINANCIAR ESTE CORRECTO
            if (unProyectoDTO.getValorFinanciar() == null) {
              adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_mensaje_propuestas_sin_valor_financiar_correcto"));
              return;
            }
          }
          continue;
        }
        //PROYECTO CON ESTADO INCORRECTO
        adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_mensaje_prouestas_sin_estado_publica"));
        return;
      }

      //SE VALIDA QUE SE HAYA INGRESADO POR LO MENOS UN COMPROMISO PARA LA CONVOCATORIA
      if (listaCompromisoPeriodo == null || listaCompromisoPeriodo.isEmpty()) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_mensaje_compromiso_faltantes_publica"));
        return;
      }

      String mensajeValidacion = validacionesGeneralesFaces.validarCompromisosPeriodo(periodoSeleccionado.getIdPeriodo());

      if (mensajeValidacion != null) {
        adicionaMensajeError(keyPropertiesFactory.value(mensajeValidacion));
        return;
      }

      byte[] bitesPdf;
      //VERIFICAMOS QUE EL REPORTE EJECUTE CORRECTAMMENTE
      try {

        HashMap mapa = new HashMap();
        mapa.put("p_id_periodo", periodoSeleccionado.getIdPeriodo().intValue());

        bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte16.jasper");

      } catch (Exception e) {

        adicionaMensajeError("ERROR, Se presentaron errores al general el reporte JASPER");
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROR", e);

        return;
      }

      //ACTUALIAMOS LAS PROPUESTAS DE CONVOCATORIAS
      Set<Long> listaUnidadesPolicialesInvolucradas = iProyectoLocal.actualizarEstadoPropuestasConvocatoriasPublicar(listaPropuestaConvocatoriasAfinanciar, loginFaces.getPerfilUsuarioDTO());
      //Genera un archivo en pdf con la relación  de todos las propuestas evaluadas 
      //(Ver requerimiento especial),envía un correo electrónico a los 
      //Jefes de Unidad y a los Jefes de Grupo de Investigación involucrados
      //adjuntando el archivo,  y  crea el proyecto  con el estado 'En ejecución' y  
      //asigna el código del proyecto de investigación de acuerdo al algoritmo (Ver requerimiento especial).

      if (listaUnidadesPolicialesInvolucradas != null && !listaUnidadesPolicialesInvolucradas.isEmpty()) {

        try {

          //GENERAMOS EL REPORTE
          //EN ESTE INSTANTE LOS PROYECTOS SE ENCUENTRAN EN SUS ESTADOS FINALES
          HashMap mapa = new HashMap();
          mapa.put("p_id_periodo", periodoSeleccionado.getIdPeriodo().intValue());

          bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte16.jasper");

          String nombreReporteUnico = "RESULT_PROP_CONVOC_".concat(periodoSeleccionado.getIdPeriodo().toString()).concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf");
          String nombreReporte = "RESULTADO_PROP_CONVOCATORIA_" + (periodoSeleccionado.getConcecutivo() == null ? periodoSeleccionado.getIdPeriodo().toString() : periodoSeleccionado.getConcecutivo().toString()) + ".pdf";

          copiarArchivoRutaFisica(
                  keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general"),
                  new ByteArrayInputStream(bitesPdf),
                  nombreReporteUnico);

          //GUARDAMOS EL REPORTE
          iPeriodoLocal.actualizarConvocatoriaReportePropuestaConvocatoria(
                  periodoSeleccionado.getIdPeriodo(),
                  nombreReporte,
                  nombreReporteUnico);

          for (Long unaUnidadPolicial : listaUnidadesPolicialesInvolucradas) {

            Map<String, Object> parametrosContenido = new HashMap<String, Object>();
            parametrosContenido.put(IConstantes.CONTENIDO_ADJUNTO_MAIL, new ArchivoReporte(bitesPdf, "RESULTADO_CONVOCATORIAS.pdf"));
            iMailSessionBean.enviarMail_RolUnidadPolicial(
                    IConstantes.CU_CO_04_PUBLICAR_RESULTADOS,
                    null,
                    parametrosContenido,
                    IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
                    unaUnidadPolicial);

            parametrosContenido.put(IConstantes.CONTENIDO_ADJUNTO_MAIL, new ArchivoReporte(bitesPdf, "RESULTADO_CONVOCATORIAS.pdf"));
            iMailSessionBean.enviarMail_RolUnidadPolicial(
                    IConstantes.CU_CO_04_PUBLICAR_RESULTADOS,
                    null,
                    parametrosContenido,
                    IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD,
                    unaUnidadPolicial
            );

          }
        } catch (Exception e) {

          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 ERROR ENVIAR MAIL", e);

        }
      }

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_co_4_mensaje_propuestas_convoca_publicadas_ok"));

      navigationFaces.redirectFacesCuCo1GestionarConvocatorias();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (publicarPropuestasRevisadas)", e);

    }

  }

  /**
   *
   * @return
   */
  public String guardarPropuestasEvaluadas() {

    try {

      if (listaCriterioPropuestaConvocatoriaDTO == null || listaCriterioPropuestaConvocatoriaDTO.isEmpty()) {
        return null;
      }

      List<EvaluacionProyecto> listaEvaluacionCriterio = new ArrayList<EvaluacionProyecto>();

      for (CriterioEvaluacionDTO unCriterioEvaluacionDTO : listaCriterioPropuestaConvocatoriaDTO) {

        if (unCriterioEvaluacionDTO.isTotal()) {
          continue;
        }
        if (unCriterioEvaluacionDTO.getValorIngresadoConvocatoria() > unCriterioEvaluacionDTO.getValorMaximoConvocatoria()) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_co_4_lbl_criterio_excede_valor_maximo", new Object[]{unCriterioEvaluacionDTO.getCriterio()}));
          return null;
        }

        EvaluacionProyecto evaluacionProyecto = iEvaluacionProyectoLocal.getEvaluacionProyectoPorProyectoYCriterio(
                proyectoDTOSeleccionadoRevisar.getId(),
                unCriterioEvaluacionDTO.getIdCriterio());

        if (evaluacionProyecto == null) {

          evaluacionProyecto = new EvaluacionProyecto();
          evaluacionProyecto.setCriterioEvaluacion(new CriterioEvaluacion(unCriterioEvaluacionDTO.getIdCriterio()));
          evaluacionProyecto.setProyecto(new Proyecto(proyectoDTOSeleccionadoRevisar.getId()));

        }
        evaluacionProyecto.setValorCriterio(BigDecimal.valueOf(unCriterioEvaluacionDTO.getValorIngresadoConvocatoria()));
        listaEvaluacionCriterio.add(evaluacionProyecto);
      }

      Proyecto proyecto = iProyectoLocal.obtenerProyectoPorId(proyectoDTOSeleccionadoRevisar.getId());
      proyecto.setEstado(new Constantes(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EVALUADA));
      proyecto.setEvaluacionProyectoList(listaEvaluacionCriterio);

      iProyectoLocal.guardarProyecto(proyecto);

      //ACTUALIZAMOS LA LISTA DE LAS PROPUESTAS
      cargarListaPropuestasConvocatorias(periodoSeleccionado, null, null);

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_co_4_lbl_criterios_almacendados_correctamente"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (guardarPropuestasEvaluadas)", e);
    }
    return null;
  }

  public boolean mostrarCampoValorFinancia(String valor) {

    if (valor == null || valor.trim().length() == 0) {
      return false;
    }
    return valor.equals(IConstantes.ESTADO_TEMPORAL_PROPUESTA_FINANCIA_APROBADA);
  }

  public void handleEstadoTemporalFinanciaSeleccionadaChange() {

  }

  /**
   *
   * @param propuestaConvocatoriaSeleccionada
   * @return
   */
  public String revisarConvocatoriaDetalle(ProyectoDTO propuestaConvocatoriaSeleccionada) {

    try {

      regresarDesdeComentario = navigationFaces.redirectCuCo4RevisarPropuestaConvocatoriaRedirect();

      this.proyectoDTOSeleccionadoRevisar = propuestaConvocatoriaSeleccionada;

      listaCriterioPropuestaConvocatoriaDTO = new ArrayList<CriterioEvaluacionDTO>();

      List<CriterioEvaluacion> listaConstantes = iCriterioEvaluacionLocal.getListaCriterioEvaluacionPorEstado(
              IConstantes.ESTADO_ACTIVO_CRITERIO_EVALUACION,
              IConstantes.TIPO_CRITERIO_EVALUACION_PROPUESTA_FINANCIACION);

      int suma100PorcientoValorMaximo = 0;
      int suma100PorcientoValorIngresado = 0;

      for (CriterioEvaluacion unCriterioEvaluacion : listaConstantes) {

        int valor = unCriterioEvaluacion.getValor().intValue();
        //CONSULTAMOS EL VALOR DE CADA CRITERIO ALMACENADO 
        BigDecimal valorIngresadoCriterio = iEvaluacionProyectoLocal.getValorIngresadoEvaluacionProyectoPorProyectoYCriterio(
                proyectoDTOSeleccionadoRevisar.getId(),
                unCriterioEvaluacion.getIdCriterioEvaluacion());

        suma100PorcientoValorIngresado += valorIngresadoCriterio == null ? 0 : valorIngresadoCriterio.intValue();

        listaCriterioPropuestaConvocatoriaDTO.add(new CriterioEvaluacionDTO(
                unCriterioEvaluacion.getIdCriterioEvaluacion(),
                unCriterioEvaluacion.getNombreCriterio(),
                valor,
                valorIngresadoCriterio == null ? null : valorIngresadoCriterio.intValue(),
                true
        ));

        suma100PorcientoValorMaximo += unCriterioEvaluacion.getValor().intValue();

      }
      //REGISTRO DEL 100 PORCIENTO -- ULTIMO REGISTRO
      criterioEvaluacionDTO100porciento = new CriterioEvaluacionDTO(
              null,
              "TOTAL",
              suma100PorcientoValorMaximo,
              suma100PorcientoValorIngresado,
              false
      );
      criterioEvaluacionDTO100porciento.setTotal(true);

      listaCriterioPropuestaConvocatoriaDTO.add(criterioEvaluacionDTO100porciento);
      return navigationFaces.redirectCuCo4RevisarPropuestaConvocatoriaRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (revisarConvocatoriaDetalle)", e);

    }
    return null;
  }

  public void noSeleccionPropuestaConvocatoriaSeleccionada(UnselectEvent event) {
    proyectoDTOSeleccionadoConsulta = null;
  }

  /**
   *
   * @param event
   */
  public void enviarPropuestaConvocatoriaSeleccionada(SelectEvent event) {

    if (proyectoDTOSeleccionadoConsulta == null) {
      return;
    }
  }

  /**
   *
   * @param periodoSeleccionado
   * @param idUnidadPolicialSeleccionada
   */
  private void cargarListaPropuestasConvocatorias(Periodo periodoSeleccionado, Long idUnidadPolicialSeleccionada, Long idEstado) throws JpaDinaeException {

    if (idUnidadPolicialSeleccionada == null && idEstado == null) {

      List<ProyectoDTO> lista = iProyectoLocal.getListaProyectoPropuestaConvocatoriaDTOPorPeriodo(
              periodoSeleccionado.getIdPeriodo(),
              listaIdFiltroPropuestaRevisar);

      listaPropuestaConvocatorias = new ListGenericDataModel(lista);

    } else if (idUnidadPolicialSeleccionada != null) {

      List<ProyectoDTO> lista = iProyectoLocal.getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYunidadPolicial(
              periodoSeleccionado.getIdPeriodo(),
              idUnidadPolicialSeleccionada,
              listaIdFiltroPropuestaRevisar);

      listaPropuestaConvocatorias = new ListGenericDataModel(lista);

    } else if (idEstado != null) {

      List<ProyectoDTO> lista = iProyectoLocal.getListaProyectoPropuestaConvocatoriaDTOPorPeriodoYestado(
              periodoSeleccionado.getIdPeriodo(),
              estadoSeleccionada);

      listaPropuestaConvocatorias = new ListGenericDataModel(lista);
    }

  }

  /**
   *
   */
  public void handleUnidadPolicialSeleccionadaChange() {

    try {

      if (unidadPolicialSeleccionada == null) {
        //VER TODAS LAS PROPUESTAS INDEPENDIENTEMENTE DE LAS UNIDADES
        cargarListaPropuestasConvocatorias(periodoSeleccionado, null, null);
      } else {
        //VER TODOAS LAS PROPUESTAS POR UNIDAD POLICIAL SELECCIONADA
        cargarListaPropuestasConvocatorias(periodoSeleccionado, unidadPolicialSeleccionada, null);
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (handleUnidadPolicialSeleccionadaChange)", e);

    }

  }

  /**
   *
   */
  public void handleEstadoPropouestaConvocaSeleccionadaChange() {

    try {

      if (estadoSeleccionada == null) {
        //VER TODAS LAS PROPUESTAS INDEPENDIENTEMENTE DE LAS UNIDADES
        cargarListaPropuestasConvocatorias(periodoSeleccionado, null, null);
      } else {
        //VER TODOAS LAS PROPUESTAS POR UNIDAD POLICIAL SELECCIONADA
        cargarListaPropuestasConvocatorias(periodoSeleccionado, null, estadoSeleccionada);
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias (handleUnidadPolicialSeleccionadaChange)", e);

    }

  }

  public Periodo getPeriodoSeleccionado() {
    return periodoSeleccionado;
  }

  public void setPeriodoSeleccionado(Periodo periodoSeleccionado) {
    this.periodoSeleccionado = periodoSeleccionado;
  }

  public List<SelectItem> getListaItemUnidadPolicialFiltroBusqueda() {
    return listaItemUnidadPolicialFiltroBusqueda;
  }

  public void setListaItemUnidadPolicialFiltroBusqueda(List<SelectItem> listaItemUnidadPolicialFiltroBusqueda) {
    this.listaItemUnidadPolicialFiltroBusqueda = listaItemUnidadPolicialFiltroBusqueda;
  }

  public List<SelectItem> getListaItemEstadoFiltroBusqueda() {
    return listaItemEstadoFiltroBusqueda;
  }

  public void setListaItemEstadoFiltroBusqueda(List<SelectItem> listaItemEstadoFiltroBusqueda) {
    this.listaItemEstadoFiltroBusqueda = listaItemEstadoFiltroBusqueda;
  }

  public Long getUnidadPolicialSeleccionada() {
    return unidadPolicialSeleccionada;
  }

  public void setUnidadPolicialSeleccionada(Long unidadPolicialSeleccionada) {
    this.unidadPolicialSeleccionada = unidadPolicialSeleccionada;
  }

  public ProyectoDTO getProyectoDTOSeleccionadoConsulta() {
    return proyectoDTOSeleccionadoConsulta;
  }

  public ListGenericDataModel<ProyectoDTO> getListaPropuestaConvocatorias() {
    return listaPropuestaConvocatorias;
  }

  public void setListaPropuestaConvocatorias(ListGenericDataModel<ProyectoDTO> listaPropuestaConvocatorias) {
    this.listaPropuestaConvocatorias = listaPropuestaConvocatorias;
  }

  public void setProyectoDTOSeleccionadoConsulta(ProyectoDTO proyectoDTOSeleccionadoConsulta) {
    this.proyectoDTOSeleccionadoConsulta = proyectoDTOSeleccionadoConsulta;
  }

  public ProyectoDTO getProyectoDTOSeleccionadoRevisar() {
    return proyectoDTOSeleccionadoRevisar;
  }

  public void setProyectoDTOSeleccionadoRevisar(ProyectoDTO proyectoDTOSeleccionadoRevisar) {
    this.proyectoDTOSeleccionadoRevisar = proyectoDTOSeleccionadoRevisar;
  }

  public Long getEstadoSeleccionada() {
    return estadoSeleccionada;
  }

  public void setEstadoSeleccionada(Long estadoSeleccionada) {
    this.estadoSeleccionada = estadoSeleccionada;
  }

  public CriterioEvaluacionDTO getCriterioEvaluacionDTO100porciento() {
    return criterioEvaluacionDTO100porciento;
  }

  public List<CriterioEvaluacionDTO> getListaCriterioPropuestaConvocatoriaDTO() {
    return listaCriterioPropuestaConvocatoriaDTO;
  }

  public void setListaCriterioPropuestaConvocatoriaDTO(List<CriterioEvaluacionDTO> listaCriterioPropuestaConvocatoriaDTO) {
    this.listaCriterioPropuestaConvocatoriaDTO = listaCriterioPropuestaConvocatoriaDTO;
  }

  public List<SelectItem> getListaItemItemComentario() {
    return listaItemItemComentario;
  }

  public void setListaItemItemComentario(List<SelectItem> listaItemItemComentario) {
    this.listaItemItemComentario = listaItemItemComentario;
  }

  public Long getItemComentarioSeleccionado() {
    return itemComentarioSeleccionado;
  }

  public void setItemComentarioSeleccionado(Long itemComentarioSeleccionado) {
    this.itemComentarioSeleccionado = itemComentarioSeleccionado;
  }

  public ComentarioProyecto getComentarioProyectoSeleccionado() {
    return comentarioProyectoSeleccionado;
  }

  public void setComentarioProyectoSeleccionado(ComentarioProyecto comentarioProyectoSeleccionado) {
    this.comentarioProyectoSeleccionado = comentarioProyectoSeleccionado;
  }

  public List<ComentarioProyectoDTO> getListaComentarioProyecto() {
    return listaComentarioProyecto;
  }

  public void setListaComentarioProyecto(List<ComentarioProyectoDTO> listaComentarioProyecto) {
    this.listaComentarioProyecto = listaComentarioProyecto;
  }

  public List<ProyectoDTO> getListaPropuestaConvocatoriasAfinanciar() {
    return listaPropuestaConvocatoriasAfinanciar;
  }

  public void setListaPropuestaConvocatoriasAfinanciar(List<ProyectoDTO> listaPropuestaConvocatoriasAfinanciar) {
    this.listaPropuestaConvocatoriasAfinanciar = listaPropuestaConvocatoriasAfinanciar;
  }

  public List<SelectItem> getListaItemItemEstadoPropuestaFinancia() {
    return listaItemItemEstadoPropuestaFinancia;
  }

  public void setListaItemItemEstadoPropuestaFinancia(List<SelectItem> listaItemItemEstadoPropuestaFinancia) {
    this.listaItemItemEstadoPropuestaFinancia = listaItemItemEstadoPropuestaFinancia;
  }

  public String getRegresarDesdeComentario() {
    return regresarDesdeComentario;
  }

  public List<CompromisoPeriodo> getListaCompromisoPeriodo() {
    return listaCompromisoPeriodo;
  }

  public void setListaCompromisoPeriodo(List<CompromisoPeriodo> listaCompromisoPeriodo) {
    this.listaCompromisoPeriodo = listaCompromisoPeriodo;
  }

  public ProyectoDTO getPropuestaSeleccionada() {
    return propuestaSeleccionada;
  }

  public void setPropuestaSeleccionada(ProyectoDTO propuestaSeleccionada) {
    this.propuestaSeleccionada = propuestaSeleccionada;
  }

  public int getNumeroRegistroPropuesta() {

    if (listaPropuestaConvocatorias == null) {
      return 0;
    }

    return listaPropuestaConvocatorias.getNumeroRegistro();

  }
}
