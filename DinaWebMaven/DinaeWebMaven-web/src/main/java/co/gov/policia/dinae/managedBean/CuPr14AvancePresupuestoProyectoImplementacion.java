package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.ViajesProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecucionEquiposProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionEventosProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionOtrosGastosProyLocal;
import co.gov.policia.dinae.interfaces.IEjecucionViajesProyectoLocal;
import co.gov.policia.dinae.interfaces.IEquiposInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IEventoProyectoLocal;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceImplementacionLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IOtrosGastosProyectoLocal;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IViajesProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.EjecucionEquiposProyecto;
import co.gov.policia.dinae.modelo.EjecucionEventosProyecto;
import co.gov.policia.dinae.modelo.EjecucionOtrosGastosProy;
import co.gov.policia.dinae.modelo.EjecucionViajesProyecto;
import co.gov.policia.dinae.modelo.EquiposInvestigacion;
import co.gov.policia.dinae.modelo.EventoProyecto;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.OtrosGastosProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.ViajesProyecto;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuPr14AvancePresupuestoProyectoImpl")
@javax.enterprise.context.SessionScoped
public class CuPr14AvancePresupuestoProyectoImplementacion extends JSFUtils implements Serializable {

  @EJB
  private IImplementacionProyectoLocal iImplementacionProyecto;

  @EJB
  private IPlanTrabajoImplementacionLocal iPlanTrabajoImplementacion;

  @EJB
  private IInvestigadorProyectoLocal iInvestigador;

  @EJB
  private IInformeAvanceImplementacionLocal iInformeAvanceImplementacion;

  @EJB
  private ICompromisoImplementacionLocal iCompromisoImpl;

  @EJB
  private IEquiposInvestigacionLocal iEquipos;

  @EJB
  private IEjecucionEquiposProyectoLocal iEjecucionEquipo;

  @EJB
  private IEventoProyectoLocal iEvento;

  @EJB
  private IEjecucionEventosProyectoLocal iEjecucionEventos;

  @EJB
  private IViajesProyectoLocal iViajes;

  @EJB
  private IEjecucionViajesProyectoLocal iEjecucionViajes;

  @EJB
  private IConstantesLocal iConstantes;

  @EJB
  private IOtrosGastosProyectoLocal iOtrosGastos;

  @EJB
  private IEjecucionOtrosGastosProyLocal iEjecucionOtrosGastos;

  public static final String CU_PR_15 = "CU_PR_15";

  private String _ingresoCU;

  private ImplementacionesProyecto _implementacionProyecto;

  private CompromisoImplementacion _compromisoImplSeleccioando;

  private InformeAvanceImplementacion _informeAvanceImplementacion;

  private UsuarioRol _usuarioRol;

  private List<InvestigadorProyecto> _listaInvestigadoresProyecto;

  private List<EquiposInvestigacion> _listaEquiposInvestigacion;

  private ListGenericDataModel _listaEquiposInvestigacionModel;

  private List<EventoProyecto> _listaEventosRelacionados;

  private ListGenericDataModel _listaEventosRelacionadosModel;

  private List<ViajesProyectoDTO> _listaViajesRelacionados;

  private ListGenericDataModel _listaViajesRelacionadosModel;

  private List<OtrosGastosProyecto> _listaOtrosGastosProyecto;

  private int _tabSeleccionado;

  private String _tituloCU;

  private EquiposInvestigacion[] _equiposSeleccionados;

  private EventoProyecto[] _eventosSeleccionados;

  private ViajesProyectoDTO[] _viajesSeleccionados;

  private String _importPresupuestoPage;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  private PlanTrabajoImplementacion _planTrabajoImpl;

  @Inject
  private CuPr15_1_2_AvanceImplemenacionFaces cuPr15_1_2_AvanceImplemenacionFaces;

  /**
   *
   * @param idProyectoImplemetacion
   * @param idCompromisoImplementacion
   * @param ingresoCU
   * @return
   */
  public String initReturnCU(Long idProyectoImplemetacion, Long idCompromisoImplementacion, String ingresoCU) {

    String navegar;

    try {

      _usuarioRol = null;
      _tabSeleccionado = 0;
      _compromisoImplSeleccioando = iCompromisoImpl.obtenerCompromisoImplementacionPorId(idCompromisoImplementacion);
      _ingresoCU = ingresoCU;
      _implementacionProyecto = iImplementacionProyecto.getImplementacionProyecto(idProyectoImplemetacion);

      obtenerPlanTrabajoProyecto();

      cargarTituloCU();
      cargarInformacionProyecto();
      cargarListadoPersonalInvestigacionHoras();

      RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
      if (rolUsuarioDTO != null) {

        _usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

      }

      _equiposSeleccionados = new EquiposInvestigacion[0];
      _eventosSeleccionados = new EventoProyecto[0];
      _viajesSeleccionados = new ViajesProyectoDTO[0];

      cargarListadoPersonalInvestigacionHoras();
      cargarEquiposInvestigacion();
      cargarListaEventosRelacionados();
      cargarListaViajesRelacionados();
      cargarListaOtrosGastosProyecto();

      navegar = navigationFaces.redirectCuPr14AvancePresupuestoProyectoImplRedirect();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
      navegar = null;
    }

    return navegar;
  }

  /**
   *
   * @throws Exception
   */
  private void obtenerPlanTrabajoProyecto() throws JpaDinaeException {

    CompromisoImplementacion compromisoPlanTrabajo = iCompromisoImpl.findByIdImplementacionProyYtipoCompromisoNoCorregido(
            _implementacionProyecto.getIdImplementacionProy(), IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO);
    if (compromisoPlanTrabajo != null) {
      _planTrabajoImpl = iPlanTrabajoImplementacion.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
              _implementacionProyecto.getIdImplementacionProy(), compromisoPlanTrabajo.getIdCompromisoImplementacion());
    }
  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {
    try {

      _importPresupuestoPage = null;

      if (event == null || event.getTab() == null) {
        return;
      }

      if ("tabPersonalInvestiga".equals(event.getTab().getId())) {
        _tabSeleccionado = 0;

      } else if ("tabEquiposInvestiga".equals(event.getTab().getId())) {

        _tabSeleccionado = 1;

      } else if ("tabEventosRelaciona".equals(event.getTab().getId())) {

        _tabSeleccionado = 2;

      } else if ("tabViajesRelaciona".equals(event.getTab().getId())) {

        _tabSeleccionado = 3;

      } else if ("tabOtrosInforme".equals(event.getTab().getId())) {

        _tabSeleccionado = 4;

      } else if ("tabPresupuestoEjecutado".equals(event.getTab().getId())) {

        _tabSeleccionado = 5;
        presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM_IMPL, _implementacionProyecto.getIdImplementacionProy(), _informeAvanceImplementacion.getIdInformeAvanceImplementacion());
        _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";

      }

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private void cargarTituloCU() {

    if (CU_PR_15.equals(_ingresoCU)) {
      _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_15");
    }
  }

  public String regresar() {

    String navegar = null;

    try {
      if (CU_PR_15.equals(_ingresoCU)) {
        _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_15");
        cuPr15_1_2_AvanceImplemenacionFaces.setIdTabSeleccionado(4);
        navegar = cuPr15_1_2_AvanceImplemenacionFaces.initReturnCU_DESDE_PR_20(_implementacionProyecto.getIdImplementacionProy(), _compromisoImplSeleccioando.getIdCompromisoImplementacion(), false);
      }

      _importPresupuestoPage = null;

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navegar;

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListadoPersonalInvestigacionHoras() throws JpaDinaeException {

    if (_implementacionProyecto == null || _implementacionProyecto.getIdImplementacionProy() == null || _compromisoImplSeleccioando == null) {
      _listaInvestigadoresProyecto = new ArrayList<InvestigadorProyecto>();
    }

    _listaInvestigadoresProyecto = iInvestigador.getListaInvestigadorProyectoPorPlanTrabajoImpl(_planTrabajoImpl.getIdPlanTrabajo());
  }

  /**
   *
   * @param inv
   * @param especie
   * @return
   */
  public String calcularValoresInvestigador(InvestigadorProyecto inv, int especie) {

    try {

      String result = "0";
      DecimalFormat df = new DecimalFormat(keyPropertiesFactory.value("general_formato_valor_cifra_sin_decimal"));
      boolean calcular;

      if (especie == 1) {

        calcular = (inv.getOrigenSiafOinvestigador() != null && 'S' == (inv.getOrigenSiafOinvestigador().charValue()));

      } else {

        calcular = (inv.getOrigenSiafOinvestigador() != null && 'S' != (inv.getOrigenSiafOinvestigador().charValue()));

      }

      if (calcular) {

        Integer horas = tomarHorasInvestigadorCompromiso(inv);
        Double calculo = inv.getValorHoraInvestigadorImplementacion().multiply(new BigDecimal(horas)).doubleValue();
        result = df.format(calculo);
      }
      return result;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, e);

    }
    return null;
  }

  /**
   *
   * @param inv
   * @return
   */
  public Integer tomarHorasInvestigadorCompromiso(InvestigadorProyecto inv) {
    Integer horas = null;
    try {

      if (IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE.equals(_compromisoImplSeleccioando.getIdTipoCompromiso().getIdConstantes())) {
        horas = inv.getHorasDedicadasImplementacion().intValue();
      } else {
        horas = inv.getHorasDedicadasAvanceFinalImplementacion().intValue();
      }

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
    }

    return horas;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarInformacionProyecto() throws JpaDinaeException {

    Long idCompromisoImpl = _compromisoImplSeleccioando.getIdCompromisoImplementacion();
    Long idProyectoImpl = _implementacionProyecto.getIdImplementacionProy();

    _informeAvanceImplementacion = iInformeAvanceImplementacion.findInformeAvanceImplementacionFinaByIdImplemenatcionProYIdCompromisoProImpl(idProyectoImpl, idCompromisoImpl);
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarEquiposInvestigacion() throws JpaDinaeException {

    _equiposSeleccionados = new EquiposInvestigacion[0];

    _listaEquiposInvestigacion = iEquipos.findByPlanTrabajo(_planTrabajoImpl.getIdPlanTrabajo());
    if (_listaEquiposInvestigacion == null || _listaEquiposInvestigacion.isEmpty()) {
      _listaEquiposInvestigacion = new ArrayList<EquiposInvestigacion>();
    }

    Long idInformeAvanceImpl = _informeAvanceImplementacion.getIdInformeAvanceImplementacion();

    List<EjecucionEquiposProyecto> ejecucionEquiposProyecto = iEjecucionEquipo.findEquipoRelacionadoImplementacionProyecto(_implementacionProyecto.getIdImplementacionProy());
    _equiposSeleccionados = new EquiposInvestigacion[_listaEquiposInvestigacion.size()];
    int i = 0;

    cicloInicial:
    for (EquiposInvestigacion equipo : _listaEquiposInvestigacion) {

      Long idEquipo = equipo.getIdEquipoInvestigacion();
      EjecucionEquiposProyecto equipoSeleccionado = iEjecucionEquipo.findEquipoRelacionadoInformeImplementacionProyecto(idInformeAvanceImpl, idEquipo);

      if (equipoSeleccionado != null) {
        _equiposSeleccionados[i] = equipoSeleccionado.getEquipoInvestigacion();
        equipo.setSeleccionable(true);
        i++;
      } else {

        equipo.setSeleccionable(true);
        for (EjecucionEquiposProyecto eep : ejecucionEquiposProyecto) {

          Long idEquipoInvestiga = eep.getEquipoInvestigacion().getIdEquipoInvestigacion();
          Long idInformeImplEquipo = eep.getInformeAvanceImplementacion().getIdInformeAvanceImplementacion();

          if (idEquipoInvestiga.compareTo(idEquipo) == 0 && idInformeAvanceImpl.compareTo(idInformeImplEquipo) != 0) {
            equipo.setSeleccionable(false);
            continue cicloInicial;
          }
        }
      }
    }

    _listaEquiposInvestigacionModel = new ListGenericDataModel(_listaEquiposInvestigacion);
  }

  /**
   *
   * @return
   */
  public String guardarEquiposInforme() {

    try {

      if (_usuarioRol == null) {

        adicionaMensajeError("Este usuario no tiene permisos para actualizar registros");
        return null;

      }
      if (_equiposSeleccionados != null && _equiposSeleccionados.length > 0) {

        Date fechaRegistro = new Date();
        iEjecucionEquipo.deleteEquipoRelacionadoInformeImplementacionProyecto(_informeAvanceImplementacion.getIdInformeAvanceImplementacion());

        for (EquiposInvestigacion equipo : _equiposSeleccionados) {

          if (equipo == null) {
            break;
          }

          EjecucionEquiposProyecto equiposInforme = new EjecucionEquiposProyecto();
          equiposInforme.setEquipoInvestigacion(equipo);
          equiposInforme.setFechaRegistro(fechaRegistro);
          equiposInforme.setInformeAvanceImplementacion(_informeAvanceImplementacion);
          equiposInforme.setUsuarioRol(_usuarioRol);
          equiposInforme.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
          equiposInforme.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

          iEjecucionEquipo.saveOrUpdate(equiposInforme);

        }

        cargarEquiposInvestigacion();
        adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

      } else {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_14_tab_1_lbl_info"));
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEventosRelacionados() throws JpaDinaeException {

    _eventosSeleccionados = new EventoProyecto[0];

    _listaEventosRelacionados = iEvento.findAllByPlanTrabajo(_planTrabajoImpl.getIdPlanTrabajo());
    if (_listaEventosRelacionados == null || _listaEventosRelacionados.isEmpty()) {
      _listaEventosRelacionados = new ArrayList<EventoProyecto>();
    }

    Long idInformeImplementacionAvance = _informeAvanceImplementacion.getIdInformeAvanceImplementacion();

    List<EjecucionEventosProyecto> eventosProyecto = iEjecucionEventos.findEventoRelacionadoImplementacionProyecto(_implementacionProyecto.getIdImplementacionProy());
    _eventosSeleccionados = new EventoProyecto[_listaEventosRelacionados.size()];
    int i = 0;

    cicloInicial:
    for (EventoProyecto evento : _listaEventosRelacionados) {

      Long idEvento = evento.getIdEventoProyecto();
      EjecucionEventosProyecto eventoSeleccionado = iEjecucionEventos.findEventoRelacionadoInformeImplementacionProyecto(idInformeImplementacionAvance, idEvento);

      if (eventoSeleccionado != null) {
        _eventosSeleccionados[i] = eventoSeleccionado.getEventoProyecto();
        evento.setSeleccionable(true);
        i++;
      } else {

        evento.setSeleccionable(true);
        for (EjecucionEventosProyecto eep : eventosProyecto) {

          Long idEventoProyecto = eep.getEventoProyecto().getIdEventoProyecto();
          Long idInformeImplEvento = eep.getInformeAvanceImplementacion().getIdInformeAvanceImplementacion();

          if (idEventoProyecto.compareTo(idEvento) == 0 && idInformeImplementacionAvance.compareTo(idInformeImplEvento) != 0) {
            evento.setSeleccionable(false);
            continue cicloInicial;
          }
        }
      }
    }

    _listaEventosRelacionadosModel = new ListGenericDataModel(_listaEventosRelacionados);
  }

  /**
   *
   * @return
   */
  public String guardarEventosInforme() {

    try {

      if (_usuarioRol == null) {

        adicionaMensajeError("Este usuario no tiene permisos para actualizar registros");
        return null;

      }
      if (_eventosSeleccionados != null && _eventosSeleccionados.length > 0) {

        Date fechaRegistro = new Date();
        iEjecucionEventos.deleteEventoRelacionadoInformeImplementacionProyecto(_informeAvanceImplementacion.getIdInformeAvanceImplementacion());

        for (EventoProyecto evento : _eventosSeleccionados) {

          if (evento == null) {
            break;
          }

          EjecucionEventosProyecto eventoInforme = new EjecucionEventosProyecto();
          eventoInforme.setEventoProyecto(evento);
          eventoInforme.setFechaRegistro(fechaRegistro);
          eventoInforme.setInformeAvanceImplementacion(_informeAvanceImplementacion);
          eventoInforme.setUsuarioRol(_usuarioRol);
          eventoInforme.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
          eventoInforme.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

          iEjecucionEventos.saveOrUpdate(eventoInforme);

        }

        cargarListaEventosRelacionados();
        adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

      } else {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_14_tab_2_lbl_info"));
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaViajesRelacionados() throws JpaDinaeException {

    _viajesSeleccionados = new ViajesProyectoDTO[0];

    _listaViajesRelacionados = iViajes.findViajesByPlanTrabajo(_planTrabajoImpl.getIdPlanTrabajo());
    if (_listaViajesRelacionados == null || _listaViajesRelacionados.isEmpty()) {
      _listaViajesRelacionados = new ArrayList<ViajesProyectoDTO>();
    }

    Long idInformeAvanceImplementacion = _informeAvanceImplementacion.getIdInformeAvanceImplementacion();

    List<EjecucionViajesProyecto> viajesProyecto = iEjecucionViajes.findViajesRelacionadoImplementacionProyecto(_implementacionProyecto.getIdImplementacionProy());
    _viajesSeleccionados = new ViajesProyectoDTO[_listaViajesRelacionados.size()];
    int i = 0;

    cicloInicial:
    for (ViajesProyectoDTO viaje : _listaViajesRelacionados) {

      Long idViaje = viaje.getIdViajeProyecto();
      EjecucionViajesProyecto viajeSeleccionado = iEjecucionViajes.findViajesRelacionadoInformeImplementacionProyecto(idInformeAvanceImplementacion, idViaje);

      if (viajeSeleccionado != null) {
        _viajesSeleccionados[i] = viaje;
        viaje.setSeleccionable(true);
        i++;
      } else {

        viaje.setSeleccionable(true);
        for (EjecucionViajesProyecto evp : viajesProyecto) {

          Long idViajeProyecto = evp.getViajesProyecto().getIdViajeProyecto();
          Long idInformeImplViaje = evp.getInformeAvanceImplementacion().getIdInformeAvanceImplementacion();

          if (idViajeProyecto.compareTo(idViaje) == 0 && idInformeAvanceImplementacion.compareTo(idInformeImplViaje) != 0) {
            viaje.setSeleccionable(false);
            continue cicloInicial;
          }
        }
      }
    }

    _listaViajesRelacionadosModel = new ListGenericDataModel(_listaViajesRelacionados);
  }

  /**
   *
   * @return
   */
  public String guardarViajesInforme() {

    try {

      if (_usuarioRol == null) {

        adicionaMensajeError("Este usuario no tiene permisos para actualizar registros");
        return null;

      }
      if (_viajesSeleccionados != null && _viajesSeleccionados.length > 0) {

        Date fechaRegistro = new Date();
        iEjecucionViajes.deleteViajesRelacionadoInformeImplementacionProyecto(_implementacionProyecto.getIdImplementacionProy());

        for (ViajesProyectoDTO viaje : _viajesSeleccionados) {

          if (viaje == null) {
            break;
          }

          EjecucionViajesProyecto viajeInforme = new EjecucionViajesProyecto();
          viajeInforme.setViajesProyecto(new ViajesProyecto(viaje.getIdViajeProyecto()));
          viajeInforme.setFechaRegistro(fechaRegistro);
          viajeInforme.setInformeAvanceImplementacion(_informeAvanceImplementacion);
          viajeInforme.setUsuarioRol(_usuarioRol);
          viajeInforme.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
          viajeInforme.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

          iEjecucionViajes.saveOrUpdate(viajeInforme);

        }

        cargarListaViajesRelacionados();
        adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

      } else {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_14_tab_3_lbl_info"));
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   */
  public void cargarListaOtrosGastosProyecto() {
    try {
      _listaOtrosGastosProyecto = iOtrosGastos.findOtrosGastosByPlanTrabajo(_planTrabajoImpl.getIdPlanTrabajo());
      if (_listaOtrosGastosProyecto == null || _listaOtrosGastosProyecto.isEmpty()) {
        _listaOtrosGastosProyecto = new ArrayList<OtrosGastosProyecto>();
      }

      Long idInformeImplementacion = _informeAvanceImplementacion.getIdInformeAvanceImplementacion();

      for (OtrosGastosProyecto otro : _listaOtrosGastosProyecto) {

        Long idOtrosGastosProyecto = otro.getIdOtrosGastosProyecto();
        EjecucionOtrosGastosProy ejOtrosGastos = iEjecucionOtrosGastos.findOtrosGastosInformeImplementacionProyecto(idInformeImplementacion, idOtrosGastosProyecto);

        if (ejOtrosGastos == null) {
          continue;
        }

        otro.setValorGastadoInforme(ejOtrosGastos.getValorGastadoPeriodo().doubleValue());
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param idConstante
   * @return
   */
  public String tomarValorConstante(Long idConstante) {
    try {
      return iConstantes.getConstantesPorIdConstante(idConstante).getValor();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String guardarOtrosGastosInforme() {

    try {

      if (_usuarioRol == null) {

        adicionaMensajeError("Este usuario no tiene permisos para actualizar registros");
        return null;

      }
      if (_listaOtrosGastosProyecto != null && !_listaOtrosGastosProyecto.isEmpty()) {

        Date fechaRegistro = new Date();
        iEjecucionOtrosGastos.deleteOtrosGastosInformeImplementacionProyecto(_informeAvanceImplementacion.getIdInformeAvanceImplementacion());

        for (OtrosGastosProyecto otros : _listaOtrosGastosProyecto) {

          if (otros.getValorGastadoInforme() == null) {
            continue;
          }

          EjecucionOtrosGastosProy otrosGastosInforme = new EjecucionOtrosGastosProy();
          otrosGastosInforme.setOtrosGastosProyecto(otros);
          otrosGastosInforme.setValorGastadoPeriodo(otros.getValorGastadoInforme().longValue());
          otrosGastosInforme.setFechaRegistro(fechaRegistro);
          otrosGastosInforme.setInformeAvanceImplementacion(_informeAvanceImplementacion);
          otrosGastosInforme.setUsuarioRol(_usuarioRol);
          otrosGastosInforme.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
          otrosGastosInforme.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getIdentificacion());

          iEjecucionOtrosGastos.saveOrUpdate(otrosGastosInforme);

        }

        cargarListaOtrosGastosProyecto();
        adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

      } else {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_14_tab_4_lbl_info"));
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyectoImplementacion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String getTituloCU() {
    return _tituloCU;
  }

  /**
   *
   * @param _tituloCU
   */
  public void setTituloCU(String _tituloCU) {
    this._tituloCU = _tituloCU;
  }

  /**
   *
   * @return
   */
  public ImplementacionesProyecto getImplementacionProyecto() {
    return _implementacionProyecto;
  }

  /**
   *
   * @param _implementacionProyecto
   */
  public void setImplementacionProyecto(ImplementacionesProyecto _implementacionProyecto) {
    this._implementacionProyecto = _implementacionProyecto;
  }

  /**
   *
   * @return
   */
  public CompromisoImplementacion getCompromisoImplSeleccioando() {
    return _compromisoImplSeleccioando;
  }

  /**
   *
   * @param _compromisoImplSeleccioando
   */
  public void setCompromisoImplSeleccioando(CompromisoImplementacion _compromisoImplSeleccioando) {
    this._compromisoImplSeleccioando = _compromisoImplSeleccioando;
  }

  /**
   *
   * @return
   */
  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return _informeAvanceImplementacion;
  }

  /**
   *
   * @param _informeAvanceImplementacion
   */
  public void setInformeAvanceImplementacion(InformeAvanceImplementacion _informeAvanceImplementacion) {
    this._informeAvanceImplementacion = _informeAvanceImplementacion;
  }

  /**
   *
   * @return
   */
  public int getTabSeleccionado() {
    return _tabSeleccionado;
  }

  /**
   *
   * @param _tabSeleccionado
   */
  public void setTabSeleccionado(int _tabSeleccionado) {
    this._tabSeleccionado = _tabSeleccionado;
  }

  /**
   *
   * @return
   */
  public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
    return _listaInvestigadoresProyecto;
  }

  /**
   *
   * @param _listaInvestigadoresProyecto
   */
  public void setListaInvestigadoresProyecto(List<InvestigadorProyecto> _listaInvestigadoresProyecto) {
    this._listaInvestigadoresProyecto = _listaInvestigadoresProyecto;
  }

  /**
   *
   * @return
   */
  public List<EquiposInvestigacion> getListaEquiposInvestigacion() {
    return _listaEquiposInvestigacion;
  }

  /**
   *
   * @param _listaEquiposInvestigacion
   */
  public void setListaEquiposInvestigacion(List<EquiposInvestigacion> _listaEquiposInvestigacion) {
    this._listaEquiposInvestigacion = _listaEquiposInvestigacion;
  }

  /**
   *
   * @return
   */
  public EquiposInvestigacion[] getEquiposSeleccionados() {
    return _equiposSeleccionados;
  }

  /**
   *
   * @param _equiposSeleccionados
   */
  public void setEquiposSeleccionados(EquiposInvestigacion[] _equiposSeleccionados) {
    this._equiposSeleccionados = _equiposSeleccionados;
  }

  /**
   *
   * @return
   */
  public ListGenericDataModel getListaEquiposInvestigacionModel() {
    return _listaEquiposInvestigacionModel;
  }

  /**
   *
   * @param _listaEquiposInvestigacionModel
   */
  public void setListaEquiposInvestigacionModel(ListGenericDataModel _listaEquiposInvestigacionModel) {
    this._listaEquiposInvestigacionModel = _listaEquiposInvestigacionModel;
  }

  /**
   *
   * @return
   */
  public List<EventoProyecto> getListaEventosRelacionados() {
    return _listaEventosRelacionados;
  }

  /**
   *
   * @param _listaEventosRelacionados
   */
  public void setListaEventosRelacionados(List<EventoProyecto> _listaEventosRelacionados) {
    this._listaEventosRelacionados = _listaEventosRelacionados;
  }

  /**
   *
   * @return
   */
  public ListGenericDataModel getListaEventosRelacionadosModel() {
    return _listaEventosRelacionadosModel;
  }

  /**
   *
   * @param _listaEventosRelacionadosModel
   */
  public void setListaEventosRelacionadosModel(ListGenericDataModel _listaEventosRelacionadosModel) {
    this._listaEventosRelacionadosModel = _listaEventosRelacionadosModel;
  }

  /**
   *
   * @return
   */
  public EventoProyecto[] getEventosSeleccionados() {
    return _eventosSeleccionados;
  }

  /**
   *
   * @param _eventosSeleccionados
   */
  public void setEventosSeleccionados(EventoProyecto[] _eventosSeleccionados) {
    this._eventosSeleccionados = _eventosSeleccionados;
  }

  /**
   *
   * @return
   */
  public List<ViajesProyectoDTO> getListaViajesRelacionados() {
    return _listaViajesRelacionados;
  }

  /**
   *
   * @param _listaViajesRelacionados
   */
  public void setListaViajesRelacionados(List<ViajesProyectoDTO> _listaViajesRelacionados) {
    this._listaViajesRelacionados = _listaViajesRelacionados;
  }

  /**
   *
   * @return
   */
  public ListGenericDataModel getListaViajesRelacionadosModel() {
    return _listaViajesRelacionadosModel;
  }

  /**
   *
   * @param _listaViajesRelacionadosModel
   */
  public void setListaViajesRelacionadosModel(ListGenericDataModel _listaViajesRelacionadosModel) {
    this._listaViajesRelacionadosModel = _listaViajesRelacionadosModel;
  }

  /**
   *
   * @return
   */
  public ViajesProyectoDTO[] getViajesSeleccionados() {
    return _viajesSeleccionados;
  }

  /**
   *
   * @param _viajesSeleccionados
   */
  public void setViajesSeleccionados(ViajesProyectoDTO[] _viajesSeleccionados) {
    this._viajesSeleccionados = _viajesSeleccionados;
  }

  /**
   *
   * @return
   */
  public List<OtrosGastosProyecto> getListaOtrosGastosProyecto() {
    return _listaOtrosGastosProyecto;
  }

  /**
   *
   * @param _listaOtrosGastosProyecto
   */
  public void setListaOtrosGastosProyecto(List<OtrosGastosProyecto> _listaOtrosGastosProyecto) {
    this._listaOtrosGastosProyecto = _listaOtrosGastosProyecto;
  }

  public String getImportPresupuestoPage() {
    return _importPresupuestoPage;
  }

  public void setImportPresupuestoPage(String _importPresupuestoPage) {
    this._importPresupuestoPage = _importPresupuestoPage;
  }

  public PlanTrabajoImplementacion getPlanTrabajoImpl() {
    return _planTrabajoImpl;
  }

  public void setPlanTrabajoImpl(PlanTrabajoImplementacion _planTrabajoImpl) {
    this._planTrabajoImpl = _planTrabajoImpl;
  }

}
