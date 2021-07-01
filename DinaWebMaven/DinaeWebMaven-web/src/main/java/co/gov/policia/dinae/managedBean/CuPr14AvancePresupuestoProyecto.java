package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.ViajesProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEjecucionEquiposProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionEventosProyectoLocal;
import co.gov.policia.dinae.interfaces.IEjecucionOtrosGastosProyLocal;
import co.gov.policia.dinae.interfaces.IEjecucionViajesProyectoLocal;
import co.gov.policia.dinae.interfaces.IEquiposInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IEventoProyectoLocal;
import co.gov.policia.dinae.interfaces.IHorasInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IOtrosGastosProyectoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IViajesProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.EjecucionEquiposProyecto;
import co.gov.policia.dinae.modelo.EjecucionEventosProyecto;
import co.gov.policia.dinae.modelo.EjecucionOtrosGastosProy;
import co.gov.policia.dinae.modelo.EjecucionViajesProyecto;
import co.gov.policia.dinae.modelo.EquiposInvestigacion;
import co.gov.policia.dinae.modelo.EventoProyecto;
import co.gov.policia.dinae.modelo.HorasInvestigador;
import co.gov.policia.dinae.modelo.InformeAvance;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.OtrosGastosProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.ViajesProyecto;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuPr14AvancePresupuestoProyecto")
@javax.enterprise.context.SessionScoped
public class CuPr14AvancePresupuestoProyecto extends JSFUtils implements Serializable {

  @EJB
  private IProyectoLocal iProyecto;

  @EJB
  private IInvestigadorProyectoLocal iInvestigador;

  @EJB
  private IInformeAvanceLocal iInformeAvance;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyecto;

  @EJB
  private IHorasInvestigadorLocal iHorasInvestigador;

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

  @javax.inject.Inject
  private CuPr7RegistrarAvanceInvestigacionFaces cuPr7RegistrarAvanceInvestigacionFaces;

  public static final String CU_PR_7 = "CU_PR_7";

  public static final String CU_PR_15 = "CU_PR_15";

  public static final String CU_PR_11 = "CU_PR_11";

  private String _ingresoCU;

  private Proyecto _proyectoSeleccionado;

  private CompromisoProyecto _compromisoSeleccioando;

  private InformeAvance _informeAvance;

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

  private Map<InvestigadorProyecto, HorasInvestigador> _mapaHorasInvestigadorCompromiso;

  private EquiposInvestigacion[] _equiposSeleccionados;

  private EventoProyecto[] _eventosSeleccionados;

  private ViajesProyectoDTO[] _viajesSeleccionados;

  private String _importPresupuestoPage;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  private boolean _soloLectura;

  @Inject
  private CuPr14AvancePresupuestoProyectoImplementacion cuPr14AvancePresupuestoProyectoImplementacionFaces;

  /**
   *
   * @param idProyecto
   * @param idCompromiso
   * @param ingresoCU
   * @param soloLectura
   * @return
   */
  public String initReturnCU(Long idProyecto, Long idCompromiso, String ingresoCU, boolean soloLectura) {

    String navegar;

    try {

      if (CU_PR_15.equals(ingresoCU)) {
        return cuPr14AvancePresupuestoProyectoImplementacionFaces.initReturnCU(idProyecto, idCompromiso, ingresoCU);
      }

      _usuarioRol = null;
      _soloLectura = soloLectura;
      _tabSeleccionado = 0;
      _compromisoSeleccioando = iCompromisoProyecto.obtenerCompromisoProyecto(idCompromiso);
      _ingresoCU = ingresoCU;
      _proyectoSeleccionado = iProyecto.obtenerProyectoPorId(idProyecto);

      cargarTituloCU();
      cargarInformacionProyecto();
      cargarListadoPersonalInvestigacionHoras();

      RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
      if (rolUsuarioDTO != null) {

        _usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

      }

      navegar = navigationFaces.redirectCuPr14AvancePresupuestoProyectoRedirect();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
      navegar = null;
    }

    return navegar;
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
        cargarListadoPersonalInvestigacionHoras();

      } else if ("tabEquiposInvestiga".equals(event.getTab().getId())) {
        _tabSeleccionado = 1;
        _equiposSeleccionados = new EquiposInvestigacion[0];
        cargarEquiposInvestigacion();

      } else if ("tabEventosRelaciona".equals(event.getTab().getId())) {
        _tabSeleccionado = 2;
        _eventosSeleccionados = new EventoProyecto[0];
        cargarListaEventosRelacionados();

      } else if ("tabViajesRelaciona".equals(event.getTab().getId())) {
        _tabSeleccionado = 3;
        _viajesSeleccionados = new ViajesProyectoDTO[0];
        cargarListaViajesRelacionados();

      } else if ("tabOtrosInforme".equals(event.getTab().getId())) {
        _tabSeleccionado = 4;
        cargarListaOtrosGastosProyecto();

      } else if ("tabPresupuestoEjecutado".equals(event.getTab().getId())) {
        _tabSeleccionado = 5;
        presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM, _proyectoSeleccionado.getIdProyecto(), _informeAvance.getIdInformeAvance());
        _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";

      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private void cargarTituloCU() {

    if (CU_PR_7.equals(_ingresoCU)) {
      _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_7");
    } else if (CU_PR_15.equals(_ingresoCU)) {
      _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_15");
    } else if (CU_PR_11.equals(_ingresoCU)) {
      _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_11");
    }
  }

  public String regresar() {

    String navegar = null;

    try {
      if (CU_PR_7.equals(_ingresoCU)) {

        cuPr7RegistrarAvanceInvestigacionFaces.ejecutarPresupuesto();
        navegar = navigationFaces.redirectCuPr7RegistrarAvanceInvestigacionRedirect();

      } else if (CU_PR_15.equals(_ingresoCU)) {

        _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_15");

      } else if (CU_PR_11.equals(_ingresoCU)) {

        _tituloCU = keyPropertiesFactory.value("cu_pr_14_lbl_titulo_from_cu_pr_11");
        /*consultarDetalleCompromisoFaces.setIdTabSeleccionado(4);
                presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM, idProyecto, _informeAvance.getIdInformeAvance());
                consultarDetalleCompromisoFaces.setImportPresupuestoPage("../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml");
                consultarDetalleCompromisoFaces.initReturnCU_Llamado_Desde_CUPR25(idCompromiso, _soloLectura);*/
        navegar = navigationFaces.redirectCuPr11ConsultarDetalleCompromisoRedirect();
      }

      _importPresupuestoPage = null;

    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navegar;

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListadoPersonalInvestigacionHoras() throws JpaDinaeException {

    if (_proyectoSeleccionado == null || _proyectoSeleccionado.getIdProyecto() == null || _compromisoSeleccioando == null) {
      _listaInvestigadoresProyecto = new ArrayList<InvestigadorProyecto>();
    }

    _listaInvestigadoresProyecto = iInvestigador.getListaInvestigadorProyectoPorProyecto(_proyectoSeleccionado.getIdProyecto());
    _mapaHorasInvestigadorCompromiso = new HashMap<InvestigadorProyecto, HorasInvestigador>();
  }

  /**
   *
   * @param inv
   * @return
   */
  public Integer tomarHorasInvestigadorCompromiso(InvestigadorProyecto inv) {
    Integer horas = null;
    try {
      HorasInvestigador horasInvestigador = iHorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto(
              _proyectoSeleccionado.getIdProyecto(),
              _compromisoSeleccioando.getIdCompromisoProyecto(),
              inv.getIdInvestigadorProyecto());

      if (horasInvestigador != null) {
        _mapaHorasInvestigadorCompromiso.put(inv, horasInvestigador);
        horas = horasInvestigador.getHorasInvestigacionTrabajadasPeriodo();
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
    }

    return horas;
  }

  /**
   *
   * @param inv
   * @param especie
   * @return
   */
  public String calcularValoresInvestigador(InvestigadorProyecto inv, int especie) {

    String result = "0";

    if (_mapaHorasInvestigadorCompromiso != null && !_mapaHorasInvestigadorCompromiso.keySet().isEmpty()) {

      DecimalFormat df = new DecimalFormat(keyPropertiesFactory.value("general_formato_valor_cifra_sin_decimal"));
      boolean calcular;

      if (especie == 1) {
        calcular = (inv.getOrigenSiafOinvestigador() != null && 'S' == (inv.getOrigenSiafOinvestigador().charValue()));

      } else {
        calcular = (inv.getOrigenSiafOinvestigador() != null && 'S' != (inv.getOrigenSiafOinvestigador().charValue()));

      }

      if (calcular) {
        Integer horas = _mapaHorasInvestigadorCompromiso.get(inv).getHorasInvestigacionTrabajadasPeriodo();
        Double calculo = inv.getValorHora().multiply(new BigDecimal(horas)).doubleValue();
        result = df.format(calculo);
      }
    }
    return result;

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarInformacionProyecto() throws JpaDinaeException {

    Long idCompromiso = _compromisoSeleccioando.getIdCompromisoProyecto();
    Long idProyecto = _proyectoSeleccionado.getIdProyecto();

    _informeAvance = iInformeAvance.obtenerInformeAvancePorCompromidoProyectoYproyecto(idCompromiso, idProyecto);
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarEquiposInvestigacion() throws JpaDinaeException {

    _listaEquiposInvestigacion = iEquipos.findEquiposByProyecto(_proyectoSeleccionado.getIdProyecto());
    if (_listaEquiposInvestigacion == null || _listaEquiposInvestigacion.isEmpty()) {
      _listaEquiposInvestigacion = new ArrayList<EquiposInvestigacion>();
    }

    Long idInformeAvance = _informeAvance.getIdInformeAvance();

    List<EjecucionEquiposProyecto> ejecucionEquiposProyecto = iEjecucionEquipo.findEquipoRelacionadoProyecto(_proyectoSeleccionado.getIdProyecto());
    _equiposSeleccionados = new EquiposInvestigacion[_listaEquiposInvestigacion.size()];
    int i = 0;

    cicloInicial:
    for (EquiposInvestigacion equipo : _listaEquiposInvestigacion) {

      Long idEquipo = equipo.getIdEquipoInvestigacion();
      EjecucionEquiposProyecto equipoSeleccionado = iEjecucionEquipo.findEquipoRelacionadoInformeProyecto(idInformeAvance, idEquipo);

      if (equipoSeleccionado != null) {
        _equiposSeleccionados[i] = equipoSeleccionado.getEquipoInvestigacion();
        equipo.setSeleccionable(true);
        i++;
      } else {

        equipo.setSeleccionable(true);
        for (EjecucionEquiposProyecto eep : ejecucionEquiposProyecto) {

          Long idEquipoInvestiga = eep.getEquipoInvestigacion().getIdEquipoInvestigacion();
          Long idInformeEquipo = eep.getInformeAvance().getIdInformeAvance();

          if (idEquipoInvestiga.compareTo(idEquipo) == 0 && idInformeAvance.compareTo(idInformeEquipo) != 0) {
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
        iEjecucionEquipo.deleteEquipoRelacionadoInformeProyecto(_informeAvance.getIdInformeAvance());

        for (EquiposInvestigacion equipo : _equiposSeleccionados) {

          if (equipo == null) {
            break;
          }

          EjecucionEquiposProyecto equiposInforme = new EjecucionEquiposProyecto();
          equiposInforme.setEquipoInvestigacion(equipo);
          equiposInforme.setFechaRegistro(fechaRegistro);
          equiposInforme.setIdInformeAvance(_informeAvance);
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
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaEventosRelacionados() throws JpaDinaeException {
    _listaEventosRelacionados = iEvento.getListaEventosPorProyecto(_proyectoSeleccionado.getIdProyecto());
    if (_listaEventosRelacionados == null || _listaEventosRelacionados.isEmpty()) {
      _listaEventosRelacionados = new ArrayList<EventoProyecto>();
    }

    Long idInformeAvance = _informeAvance.getIdInformeAvance();

    List<EjecucionEventosProyecto> eventosProyecto = iEjecucionEventos.findEventoRelacionadoProyecto(_proyectoSeleccionado.getIdProyecto());
    _eventosSeleccionados = new EventoProyecto[_listaEventosRelacionados.size()];
    int i = 0;

    cicloInicial:
    for (EventoProyecto evento : _listaEventosRelacionados) {

      Long idEvento = evento.getIdEventoProyecto();
      EjecucionEventosProyecto eventoSeleccionado = iEjecucionEventos.findEventoRelacionadoInformeProyecto(idInformeAvance, idEvento);

      if (eventoSeleccionado != null) {
        _eventosSeleccionados[i] = eventoSeleccionado.getEventoProyecto();
        evento.setSeleccionable(true);
        i++;
      } else {

        evento.setSeleccionable(true);
        for (EjecucionEventosProyecto eep : eventosProyecto) {

          Long idEventoProyecto = eep.getEventoProyecto().getIdEventoProyecto();
          Long idInformeEquipo = eep.getInformeAvance().getIdInformeAvance();

          if (idEventoProyecto.compareTo(idEvento) == 0 && idInformeAvance.compareTo(idInformeEquipo) != 0) {
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
        iEjecucionEventos.deleteEventoRelacionadoInformeProyecto(_informeAvance.getIdInformeAvance());

        for (EventoProyecto evento : _eventosSeleccionados) {

          if (evento == null) {
            break;
          }

          EjecucionEventosProyecto eventoInforme = new EjecucionEventosProyecto();
          eventoInforme.setEventoProyecto(evento);
          eventoInforme.setFechaRegistro(fechaRegistro);
          eventoInforme.setInformeAvance(_informeAvance);
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
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarListaViajesRelacionados() throws JpaDinaeException {
    _listaViajesRelacionados = iViajes.findViajesByProyectoDTO(_proyectoSeleccionado.getIdProyecto());
    if (_listaViajesRelacionados == null || _listaViajesRelacionados.isEmpty()) {
      _listaViajesRelacionados = new ArrayList<ViajesProyectoDTO>();
    }

    Long idInformeAvance = _informeAvance.getIdInformeAvance();

    List<EjecucionViajesProyecto> viajesProyecto = iEjecucionViajes.findViajesRelacionadoProyecto(_proyectoSeleccionado.getIdProyecto());
    _viajesSeleccionados = new ViajesProyectoDTO[_listaViajesRelacionados.size()];
    int i = 0;

    cicloInicial:
    for (ViajesProyectoDTO viaje : _listaViajesRelacionados) {

      Long idViaje = viaje.getIdViajeProyecto();
      EjecucionViajesProyecto viajeSeleccionado = iEjecucionViajes.findViajesRelacionadoInformeProyecto(idInformeAvance, idViaje);

      if (viajeSeleccionado != null) {
        _viajesSeleccionados[i] = viaje;
        viaje.setSeleccionable(true);
        i++;
      } else {

        viaje.setSeleccionable(true);
        for (EjecucionViajesProyecto evp : viajesProyecto) {

          Long idViajeProyecto = evp.getViajesProyecto().getIdViajeProyecto();
          Long idInformeEquipo = evp.getInformeAvance().getIdInformeAvance();

          if (idViajeProyecto.compareTo(idViaje) == 0 && idInformeAvance.compareTo(idInformeEquipo) != 0) {
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
        iEjecucionViajes.deleteViajesRelacionadoInformeProyecto(_informeAvance.getIdInformeAvance());

        for (ViajesProyectoDTO viaje : _viajesSeleccionados) {

          if (viaje == null) {
            break;
          }

          EjecucionViajesProyecto viajeInforme = new EjecucionViajesProyecto();
          viajeInforme.setViajesProyecto(new ViajesProyecto(viaje.getIdViajeProyecto()));
          viajeInforme.setFechaRegistro(fechaRegistro);
          viajeInforme.setInformeAvance(_informeAvance);
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
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   */
  public void cargarListaOtrosGastosProyecto() {
    try {
      _listaOtrosGastosProyecto = iOtrosGastos.findOtrosGastosByProyecto(_proyectoSeleccionado.getIdProyecto());
      if (_listaOtrosGastosProyecto == null || _listaOtrosGastosProyecto.isEmpty()) {
        _listaOtrosGastosProyecto = new ArrayList<OtrosGastosProyecto>();
      }

      Long idInforme = _informeAvance.getIdInformeAvance();

      for (OtrosGastosProyecto otro : _listaOtrosGastosProyecto) {

        Long idOtrosGastosProyecto = otro.getIdOtrosGastosProyecto();
        EjecucionOtrosGastosProy ejOtrosGastos = iEjecucionOtrosGastos.findOtrosGastosInformeProyecto(idInforme, idOtrosGastosProyecto);

        if (ejOtrosGastos == null) {
          continue;
        }

        otro.setValorGastadoInforme(ejOtrosGastos.getValorGastadoPeriodo().doubleValue());
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
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
        iEjecucionOtrosGastos.deleteOtrosGastosInformeProyecto(_informeAvance.getIdInformeAvance());

        for (OtrosGastosProyecto otros : _listaOtrosGastosProyecto) {

          if (otros.getValorGastadoInforme() == null) {
            continue;
          }

          EjecucionOtrosGastosProy otrosGastosInforme = new EjecucionOtrosGastosProy();
          otrosGastosInforme.setOtrosGastosProyecto(otros);
          otrosGastosInforme.setValorGastadoPeriodo(otros.getValorGastadoInforme().longValue());
          otrosGastosInforme.setFechaRegistro(fechaRegistro);
          otrosGastosInforme.setInformeAvance(_informeAvance);
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
      Logger.getLogger(CuPr14AvancePresupuestoProyecto.class.getName()).log(Level.SEVERE, null, ex);
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
  public Proyecto getProyectoSeleccionado() {
    return _proyectoSeleccionado;
  }

  /**
   *
   * @param _proyectoSeleccionado
   */
  public void setProyectoSeleccionado(Proyecto _proyectoSeleccionado) {
    this._proyectoSeleccionado = _proyectoSeleccionado;
  }

  /**
   *
   * @return
   */
  public CompromisoProyecto getCompromisoSeleccioando() {
    return _compromisoSeleccioando;
  }

  /**
   *
   * @param _compromisoSeleccioando
   */
  public void setCompromisoSeleccioando(CompromisoProyecto _compromisoSeleccioando) {
    this._compromisoSeleccioando = _compromisoSeleccioando;
  }

  /**
   *
   * @return
   */
  public InformeAvance getInformeAvance() {
    return _informeAvance;
  }

  /**
   *
   * @param _informeAvance
   */
  public void setInformeAvance(InformeAvance _informeAvance) {
    this._informeAvance = _informeAvance;
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

  public boolean isSoloLectura() {
    return _soloLectura;
  }

  public void setSoloLectura(boolean _soloLectura) {
    this._soloLectura = _soloLectura;
  }

}
