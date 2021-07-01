package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ImplentacionProyectoCompromisosDTO;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PendientesView;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author cguzman: carlos.guzman@pointmind.com
 */
@Named(value = "dashboardFaces")
@SessionScoped
public class DashboardFaces extends JSFUtils implements Serializable {

  private List<PendientesView> _pendientes;

  private final SimpleDateFormat formatoFecha = new SimpleDateFormat(keyPropertiesFactory.value("general_formato_fecha"));

  @EJB
  private IPeriodoLocal _iPeriodo;

  @EJB
  private IProyectoLocal _iProyecto;

  @EJB
  private ICompromisoProyectoLocal _iCompromisoProyecto;

  @EJB
  private IPropuestaNecesidadLocal _iPropuestaNecesidad;

  @EJB
  private IEnsayoCriticoLocal _iEnsayoCritico;

  @EJB
  private IImplementacionProyectoLocal _iImplProyectos;

  @EJB
  private ICompromisoImplementacionLocal _iCompromisoImpl;

  @EJB
  private IConstantesLocal _iConstantesLocal;

  private final static Long[] ESTADOS_COMPROMISOS_VICIN = {IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN};
  private final static Long[] ESTADOS_PENDIENTES_IMPL_UNIDAD_POLICIAL = {IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION, IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO};
  private final static List<Long> ESTADOS_PROPUESTA_CONVOCATORIA_JEFE_UNIDAD = new ArrayList<Long>();

  static {
    ESTADOS_PROPUESTA_CONVOCATORIA_JEFE_UNIDAD.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBACION_JEFE_UNIDAD);
  }
  private final static Long[] ESTADOS_PROPUESTA_CONVOCA_REVISION_JEFE_UNIDAD = {IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_REVISION_DE_JEFE_DE_LA_UNIDAD};

  private final static Long ESTADOS_PROPUESTA_NECESIDAD_VICIN = IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN;
  private final static Long[] ESTADOS_COMPROMISO_RESPONSABLES_PROYECTO_INVESTIGA_UNIDAD_POLICIAL = {
    IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO,
    IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN,
    IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO};

  private final static Long[] ESTADOS_COMPROMISO_PROYECTO_REVICION_JEFE_UNIDAD = {IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE};

  private boolean mostrarTablaPendientes;

  /**
   *
   * @return
   */
  public String load() {
    try {
      PerfilUsuarioDTO perfil = loginFaces.getPerfilUsuarioDTO();
      _pendientes = new ArrayList<PendientesView>();

      if (perfil != null) {
        List<RolUsuarioDTO> roles = perfil.getListaRolUsuarioDTO();
        if (!roles.isEmpty()) {

          evaluarRoles(roles);

        }
      }

    } catch (Exception ex) {
      Logger.getLogger(DashboardFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @param rolesUsuario
   */
  private void evaluarRoles(List<RolUsuarioDTO> rolesUsuario) throws Exception {

    int cont = 0;

    for (RolUsuarioDTO rol : rolesUsuario) {

      Long idRol = rol.getIdRol();

      try {

        if (IConstantesRole.AUTORIZADO_PARA_REGISTRO_DE_NECESIDADES_EN_LA_UNIDAD_POLICIAL.compareTo(idRol) == 0) {

          Long idUnidad = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial();
          cargarPendientesAutorizadoRegistroNecesidadesUnidadPolicial(idUnidad);
          cont++;
        }

        if (IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL.compareTo(idRol) == 0) {
          UnidadPolicialDTO unidad = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial();
          cargarPendientesJefeUnidadPolicial(unidad);
          cont++;
        }

        if (IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN.compareTo(idRol) == 0) {
          cargarPendientesEvaluadorPropuestasNecesidadVicin();
          cont++;
        }

        if (IConstantesRole.ENCARGADO_DE_ENSAYOS_EN_LA_UNIDAD_POLICIAL.compareTo(idRol) == 0) {
          UnidadPolicialDTO unidad = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial();
          cargarPendientesEnsayosEnlaUnidad(unidad);
          cont++;
        }

        if (IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL.compareTo(idRol) == 0) {
          UnidadPolicialDTO unidad = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial();
          cargarPendientesResponsableProyectosInvestigacionUnidadPolicial(unidad);
          cont++;
        }

        if (IConstantesRole.RESPONSABLE_DE_LA_IMPLEMENTACION_EN_LA_UNIDAD_POLICIAL.compareTo(idRol) == 0) {
          UnidadPolicialDTO unidad = loginFaces.getPerfilUsuarioDTO().getUnidadPolicial();
          cargarPendientesImplementacionesUnidadPolicial(unidad);
          cont++;
        }

        if (IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_INSTITUCIONALES_VICIN.compareTo(idRol) == 0) {
          cargarPendientesEncargadoRevisionCompromisosProyectosInstitucionalesFinanciacionVicin(keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion"));
          cont++;
        }

        if (IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN.compareTo(idRol) == 0) {
          cargarPendientesEncargadoRevisionCompromisosProyectosInstitucionalesFinanciacionVicin(keyPropertiesFactory.value("cu_co_4_codigo_proyecto_inicia_generacion"));
          cont++;
        }
      } catch (Exception e) {
        Logger.getLogger(DashboardFaces.class.getName()).log(Level.SEVERE, "DashboardFaces:evaluarRoles(List<RolUsuarioDTO> rolesUsuario)", e);
      }
    }

    Collections.sort(_pendientes);

    mostrarTablaPendientes = (cont > 0);
  }

  /**
   *
   * @param idUnidadPolicial
   */
  private void cargarPendientesAutorizadoRegistroNecesidadesUnidadPolicial(Long idUnidadPolicial) throws Exception {

    Date fechaActual = new Date();
    List<Periodo> listaPeriodo = _iPeriodo.getPeriodosPorEstadoYFechaActualEntreFechaInicioYfechaFin(fechaActual, 'P');

    if (listaPeriodo.isEmpty()) {
      return;
    }

    for (Periodo periodo : listaPeriodo) {

      //POR CADA PERIODO VERIFICAMOS QUE LA UNIDAD DEL USUARIO LOGUEADO
      //REGISTRARON PROPUESTAS DE NECESIDAD
      int contarPropuestas = _iPeriodo.getNumeroPropuestasNecesidadesDashBoard(periodo.getIdPeriodo(), idUnidadPolicial);

      if (!(contarPropuestas > 0)) {
        //SIGNIFICA QUE LA UNIDAD POLICIAL PARA ESTE PERIODO HA REALIZADO Y ENVIADO PROPUESTAS 
        continue;
      }

      int dias = calcularDiasEntreFechas(periodo.getFechaFin(), fechaActual);

      PendientesView pendiente = new PendientesView();
      pendiente.setActividadPendiente("Enviar propuestas de necesidades de investigación");
      pendiente.setAcercaDe("Año: " + periodo.getAnio());
      pendiente.setFechaLimite(periodo.getFechaFin());
      pendiente.setFechaLimiteFormateada(formatoFecha.format(periodo.getFechaFin()));
      pendiente.setDiasRestantes(dias);
      pendiente.setStyleClass(determinarStyleClass(dias));

      _pendientes.add(pendiente);
    }
  }

  /**
   *
   * @param unidadPolicialDTO
   */
  private void cargarPendientesJefeUnidadPolicial(UnidadPolicialDTO unidadPolicialDTO) throws Exception {

    Long idUnidadPolicial = unidadPolicialDTO.getIdUnidadPolicial();
    Date fechaActual = new Date();
    List<Periodo> periodos = _iPeriodo.getPeriodosFechaActualEntreFechaInicioYfechaFin(fechaActual, idUnidadPolicial, 'P');

    if (!periodos.isEmpty()) {

      for (Periodo periodo : periodos) {

        Long idPeriodo = periodo.getIdPeriodo();

        List<PropuestaNecesidad> propuestasNecesidad = _iPropuestaNecesidad.getPropuestaNecesidadPorPeriodoYunidadPolicialyEstados(idPeriodo, idUnidadPolicial, ESTADOS_PROPUESTA_CONVOCATORIA_JEFE_UNIDAD);

        if (propuestasNecesidad.isEmpty()) {
          continue;
        }

        int dias = calcularDiasEntreFechas(periodo.getFechaFin(), fechaActual);

        PendientesView pendiente = new PendientesView();
        pendiente.setActividadPendiente("Revisar propuestas de necesidades de investigación");
        pendiente.setAcercaDe(propuestasNecesidad.size() + " Propuestas");
        pendiente.setFechaLimite(periodo.getFechaFin());
        pendiente.setFechaLimiteFormateada(formatoFecha.format(periodo.getFechaFin()));
        pendiente.setDiasRestantes(dias);
        pendiente.setStyleClass(determinarStyleClass(dias));

        _pendientes.add(pendiente);

      }
    }

    List<Periodo> convocatorias = _iPeriodo.getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(
            fechaActual,
            idUnidadPolicial,
            IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA,
            IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION);

    if (!convocatorias.isEmpty()) {
      for (Periodo convocatoria : convocatorias) {

        Long idPeriodo = convocatoria.getIdPeriodo();

        List<ProyectoDTO> propuestasConvocatorias = _iProyecto.findAllPropuestaNecesidadConvocatoriaByPeriodoEstados(idPeriodo, idUnidadPolicial, ESTADOS_PROPUESTA_CONVOCA_REVISION_JEFE_UNIDAD);

        if (propuestasConvocatorias.isEmpty()) {
          continue;
        }

        int dias = calcularDiasEntreFechas(convocatoria.getFechaFin(), fechaActual);

        PendientesView pendiente = new PendientesView();
        pendiente.setActividadPendiente("Revisar propuestas para convocatoria de financiación");
        pendiente.setAcercaDe(propuestasConvocatorias.size() + " Propuestas");
        pendiente.setFechaLimite(convocatoria.getFechaFin());
        pendiente.setFechaLimiteFormateada(formatoFecha.format(convocatoria.getFechaFin()));
        pendiente.setDiasRestantes(dias);
        pendiente.setStyleClass(determinarStyleClass(dias));

        _pendientes.add(pendiente);
      }
    }

    List<CompromisoProyecto> compromisosPendientes = _iCompromisoProyecto.findAllCompromisosVigentesJefeUnidad(idUnidadPolicial, ESTADOS_COMPROMISO_PROYECTO_REVICION_JEFE_UNIDAD);

    if (!compromisosPendientes.isEmpty()) {

      for (CompromisoProyecto compromiso : compromisosPendientes) {

        Date fechaCompromiso;
        String nombreCompromiso;

        if (compromiso.getCompromisoProyectoHijo() != null) {
          continue;
        }

        fechaCompromiso = compromiso.getFechaNuevoCompromiso() == null ? compromiso.getCompromisoPeriodo().getFecha() : compromiso.getFechaNuevoCompromiso();
        nombreCompromiso = compromiso.getFechaNuevoCompromiso() == null ? compromiso.getCompromisoPeriodo().getNombreCompromisoNumeroIncrementa() : compromiso.getNombreCompromisoCorrecion();

        int dias = calcularDiasEntreFechas(fechaCompromiso, fechaActual);

        PendientesView pendiente = new PendientesView();
        pendiente.setActividadPendiente("Revisar " + nombreCompromiso);
        pendiente.setAcercaDe(compromiso.getProyecto().getCodigoProyecto());
        pendiente.setFechaLimite(fechaCompromiso);
        pendiente.setFechaLimiteFormateada(formatoFecha.format(fechaCompromiso));
        pendiente.setDiasRestantes(dias);
        pendiente.setStyleClass(determinarStyleClass(dias));

        _pendientes.add(pendiente);
      }
    }
  }

  /**
   *
   * @throws Exception
   */
  private void cargarPendientesEnsayosEnlaUnidad(UnidadPolicialDTO unidadPolicialDTO) throws Exception {

    Long idUnidadPolicial = unidadPolicialDTO.getIdUnidadPolicial();

    Long[] estadosArray = {IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_RECIBIDO, IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_LEIDO};
    List<Long> idEstados = Arrays.asList(estadosArray);

    List<Periodo> convocatoriasEnsayo = _iPeriodo.getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(
            new Date(),
            idUnidadPolicial,
            IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA,
            IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO);

    if (convocatoriasEnsayo.isEmpty()) {
      return;
    }

    for (Periodo periodo : convocatoriasEnsayo) {

      List<EnsayoCritico> ensayo = _iEnsayoCritico.findByPeriodoUnidadCobertura(unidadPolicialDTO.getSiglaPadre(), idEstados, periodo.getIdPeriodo());

      if (ensayo.isEmpty()) {
        continue;
      }

      PendientesView pendiente = new PendientesView();
      pendiente.setActividadPendiente("Evaluar ensayos criticos");
      pendiente.setAcercaDe("N/A");
      pendiente.setFechaLimite(null);
      pendiente.setFechaLimiteFormateada("N/A");

      _pendientes.add(pendiente);
    }

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarPendientesEvaluadorPropuestasNecesidadVicin() throws Exception {

    List<Periodo> periodos = _iPeriodo.getPeriodosNecesidades();

    if (!periodos.isEmpty()) {

      for (Periodo periodo : periodos) {

        Long idPeriodo = periodo.getIdPeriodo();

        int conteo = _iPropuestaNecesidad.getNumeroPropuestaNecesidadPorPeriodoYestado(idPeriodo, ESTADOS_PROPUESTA_NECESIDAD_VICIN);

        if (conteo == 0) {
          continue;
        }

        PendientesView pendiente = new PendientesView();
        pendiente.setActividadPendiente("Evaluar propuestas de necesidades de investigación");
        pendiente.setAcercaDe("N/A");
        pendiente.setFechaLimite(null);
        pendiente.setFechaLimiteFormateada("N/A");

        _pendientes.add(pendiente);

      }
    }
  }

  /**
   *
   * @param unidadPolicialDTO
   * @throws JpaDinaeException
   */
  private void cargarPendientesResponsableProyectosInvestigacionUnidadPolicial(UnidadPolicialDTO unidadPolicialDTO) throws Exception {

    List<ProyectoDTO> proyectosInvestigacionVigentes = _iProyecto.findAllProyectosInvestigacionVigentesUnidad(unidadPolicialDTO.getIdUnidadPolicial());

    Calendar cFechaActual = Calendar.getInstance();
    cFechaActual.setTime(new Date());
    cFechaActual.add(Calendar.DATE, -1);

    Date fechaActual = cFechaActual.getTime();

    Calendar fechaEnMes = Calendar.getInstance();
    fechaEnMes.setTime(new Date());
    fechaEnMes.add(Calendar.MONTH, 1);

    Date fechaMes = fechaEnMes.getTime();

    if (proyectosInvestigacionVigentes.isEmpty()) {

      return;

    }

    for (ProyectoDTO proyecto : proyectosInvestigacionVigentes) {

      List<CompromisoProyecto> compromisos = _iCompromisoProyecto.findAllCompromisosVigentesMesProyecto(unidadPolicialDTO.getIdUnidadPolicial(), proyecto.getId(), fechaActual, fechaMes, ESTADOS_COMPROMISO_RESPONSABLES_PROYECTO_INVESTIGA_UNIDAD_POLICIAL);

      if (compromisos.isEmpty()) {

        continue;

      }

      for (CompromisoProyecto compromiso : compromisos) {

        if (compromiso.getCompromisoProyectoHijo() != null) {

          continue;

        }

        Date fechaCompromiso = compromiso.getFechaNuevoCompromiso() != null ? compromiso.getFechaNuevoCompromiso() : compromiso.getCompromisoPeriodo().getFecha();

        int dias = calcularDiasEntreFechas(fechaCompromiso, new Date());

        String nombreCompromiso = compromiso.getCompromisoPeriodo().getNombreCompromisoNumeroIncrementa();
        if (compromiso.getCompromisoProyectoPadre() != null && compromiso.getCompromisoProyectoPadre().getIdCompromisoProyecto() != null) {

          nombreCompromiso = compromiso.getNombreCompromisoCorreccionNumeroIncrementa();

        }
        PendientesView pendiente = new PendientesView();
        pendiente.setActividadPendiente("Enviar " + nombreCompromiso);
        pendiente.setAcercaDe(proyecto.getCodigoProyecto());
        pendiente.setFechaLimite(fechaCompromiso);
        pendiente.setFechaLimiteFormateada(formatoFecha.format(fechaCompromiso));
        pendiente.setDiasRestantes(dias);
        pendiente.setStyleClass(determinarStyleClass(dias));

        _pendientes.add(pendiente);
      }
    }
  }

  /**
   *
   * @param unidadPolicialDTO
   * @throws JpaDinaeException
   */
  private void cargarPendientesImplementacionesUnidadPolicial(UnidadPolicialDTO unidadPolicialDTO) throws Exception {

    List<ImplentacionProyectoCompromisosDTO> implemtacionesProyectos = _iImplProyectos.findAllImplementacionesVigentes(Arrays.asList(ESTADOS_PENDIENTES_IMPL_UNIDAD_POLICIAL), unidadPolicialDTO.getIdUnidadPolicial());

    if (implemtacionesProyectos.isEmpty()) {

      return;

    }

    Long planTrabajo = IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO;

    for (ImplentacionProyectoCompromisosDTO implementa : implemtacionesProyectos) {

      CompromisoImplementacion compImpl = _iCompromisoImpl.obtenerCompromisoImplementacionPorImplementacionProyectoYtipoCompromiso(implementa.getIdImplementacionProy(), planTrabajo);

      if (compImpl == null || compImpl.getIdEstadoCompromisoImpl().getIdConstantes().equals(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO)) {

        continue;

      }

      Constantes constantesTipoCompromiso = _iConstantesLocal.getConstantesPorIdConstante(compImpl.getIdTipoCompromiso().getIdConstantes());

      PendientesView pendiente = new PendientesView();
      pendiente.setFechaLimiteFormateada("N/A");

      String descripcionTipoCompromiso = "Enviar ";
      Date fechaCompromiso = null;

      if (compImpl.getCompromisoImplementacionPadre() == null) {

        descripcionTipoCompromiso += constantesTipoCompromiso.getValor();

      } else {

        descripcionTipoCompromiso += compImpl.getNombreCompromisoCorrecion();
        fechaCompromiso = compImpl.getFechaNuevoCompromiso();
        pendiente.setFechaLimiteFormateada(formatoFecha.format(fechaCompromiso));
      }

      pendiente.setFechaLimite(fechaCompromiso);
      pendiente.setActividadPendiente(descripcionTipoCompromiso.concat(" de la implementación"));
      pendiente.setAcercaDe(implementa.getCodigoProyecto());

      _pendientes.add(pendiente);
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void cargarPendientesEncargadoRevisionCompromisosProyectosInstitucionalesFinanciacionVicin(String prefijo) throws Exception {

    int cantidadCompromisosVicin = _iCompromisoProyecto.findAllCompromisosEnviadosVicin(prefijo, ESTADOS_COMPROMISOS_VICIN);

    if (cantidadCompromisosVicin == 0) {

      return;

    }

    PendientesView pendiente = new PendientesView();
    if (keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion").equals(prefijo)) {

      pendiente.setActividadPendiente("Revisar compromisos de proyectos institucionales");

    } else if (keyPropertiesFactory.value("cu_co_4_codigo_proyecto_inicia_generacion").equals(prefijo)) {

      pendiente.setActividadPendiente("Revisar compromisos de proyectos por financiación");

    } else {

      pendiente.setActividadPendiente("Revisar compromisos");

    }
    pendiente.setAcercaDe("N/A");
    pendiente.setFechaLimite(null);
    pendiente.setFechaLimiteFormateada("N/A");
    pendiente.setDiasRestantes(0);

    _pendientes.add(pendiente);

  }

  /**
   *
   * @param fecha1
   * @param fecha2
   * @return
   */
  private static int calcularDiasEntreFechas(Date fecha1, Date fecha2) {
    try {
      SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

      String fechaFormateada1 = fmt.format(fecha1);
      String fechaFormateada2 = fmt.format(fecha2);

      Date f1 = fmt.parse(fechaFormateada1);
      Date f2 = fmt.parse(fechaFormateada2);

      Long timeMillis = f1.getTime() - f2.getTime();
      int dias = Math.round((timeMillis / (1000 * 60 * 60 * 24)));
      return dias;
    } catch (ParseException ex) {
      Logger.getLogger(DashboardFaces.class.getName()).log(Level.SEVERE, "DashboardFaces:calcularDiasEntreFechas(Date fecha1, Date fecha2)", ex);
    } catch (Exception ex) {
      Logger.getLogger(DashboardFaces.class.getName()).log(Level.SEVERE, "DashboardFaces:calcularDiasEntreFechas(Date fecha1, Date fecha2)", ex);
    }

    return -1000;
  }

  /**
   *
   * @param dias
   * @return
   */
  private static String determinarStyleClass(int dias) {

    String styleClass = "background-color: #56A700; color: white;";

    if (dias <= 0) {
      styleClass = "background-color: red; color: white;";
    } else if (dias > 0 && dias <= 5) {
      styleClass = "background-color: yellow; color: black";
    }

    return styleClass;
  }

  public List<PendientesView> getPendientes() {
    return _pendientes;
  }

  public void setPendientes(List<PendientesView> _pendientes) {
    this._pendientes = _pendientes;
  }

  public boolean isMostrarTablaPendientes() {
    return mostrarTablaPendientes;
  }

  public void setMostrarTablaPendientes(boolean mostrarTablaPendientes) {
    this.mostrarTablaPendientes = mostrarTablaPendientes;
  }
}
