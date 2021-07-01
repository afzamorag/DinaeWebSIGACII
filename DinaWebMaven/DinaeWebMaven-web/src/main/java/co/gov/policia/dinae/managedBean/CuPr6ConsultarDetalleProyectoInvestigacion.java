package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.CompromisoDTO;
import co.gov.policia.dinae.dto.ProyectoVersionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IEvaluacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVersionesLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.Tema;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr6ConsultarDetalleProyectoInvestigacion")
@javax.enterprise.context.SessionScoped
public class CuPr6ConsultarDetalleProyectoInvestigacion extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr1ProyectoFaces cuPr1ProyectoFaces;

  @javax.inject.Inject
  private IncludeInformacionInformeProyectoGenericoFaces includeInformacionInformeProyectoGenericoFaces;

  @javax.inject.Inject
  private AdministracionProyectoVersionFaces administracionProyectoVersionFaces;

  @EJB
  private IFuenteProyectoLocal iFuenteProyectoLocal;

  @EJB
  private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;

  private List<CompromisoDTO> listaCompromisoDTO;

  @javax.inject.Inject
  private CuPr11ConsultarDetalleCompromisoFaces cuPr11ConsultarDetalleCompromisoFaces;

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @javax.inject.Inject
  private CuPr21RegistrarPlanDeTrabajoFaces cuPr21RegistrarPlanDeTrabajoFaces;

  @javax.inject.Inject
  private CuPr15_1_2_AvanceImplemenacionFaces cuPr15_1_2_AvanceImplemenacionFaces;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private ICompromisoImplementacionLocal iCompromisoImplementacionLocal;

  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

  @EJB
  private IVersionesLocal iVersionesLocal;

  @EJB
  private IEvaluacionProyectoLocal _iEvaluacionProyecto;

  private List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba;

  private List<ProyectoVersionDTO> listaProyectoVersionDTO;

  private List<EvaluacionProyecto> _evaluacionProyectoList;

  private boolean mostrarTabInforme;
  private boolean mostrarTabAsesoria;
  private boolean mostrarTabOtrosParticipantes;
  private boolean mostrarTabPresupuesto;
  private boolean mostrarTabIndicadores;
  private boolean mostrarTabEvaluacionProyecto;

  private static final BigDecimal NOTA_MAXIMA = new BigDecimal(5.0);
  private static final BigDecimal NOTA_MINIMA = new BigDecimal(1.0);
  private static final String EXCELENTE = "EXCELENTE";
  private static final String BUENO = "BUENO";
  private static final String ACEPTABLE = "ACEPTABLE";
  private static final String DEFICIENTE = "DEFICIENTE";

  private Proyecto _proyectoSeleccionado;

  private String _cualificacionTotal;

  private String _calificacionTotal;

  private enum ORIGEN_LLAMADO_CU {

    CU_CO_02, CU_PR_19, CU_PR_25, CU_CO_03, CU_CO_04, CU_CO_05, CU_PR_20, CU_PR_24, CU_PR_08, CU_PR_12

  };

  private CuPr6ConsultarDetalleProyectoInvestigacion.ORIGEN_LLAMADO_CU registraLlamdoCU;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      mostrarTabOtrosParticipantes = false;
      mostrarTabPresupuesto = false;
      mostrarTabIndicadores = false;
      listaProyectoVersionDTO = null;
      listaJefeFuncionariosSeleccionadoAprueba = null;
      mostrarTabInforme = false;
      mostrarTabAsesoria = false;
      mostrarTabEvaluacionProyecto = false;
      registraLlamdoCU = null;
      _evaluacionProyectoList = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-06 Consultar detalle proyecto (init) ", e);
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_PR_19(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_19;

    mostrarTabInforme = true;

    mostrarTabAsesoria = true;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_PR_12(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_12;

    mostrarTabInforme = true;

    mostrarTabAsesoria = true;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_PR_20(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_20;

    mostrarTabInforme = true;

    mostrarTabAsesoria = true;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_CO_03(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_CO_03;

    mostrarTabInforme = false;

    mostrarTabAsesoria = false;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_CO_04(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_CO_04;

    mostrarTabInforme = false;

    mostrarTabAsesoria = false;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_CO_05(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_CO_05;

    mostrarTabInforme = false;

    mostrarTabAsesoria = false;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_PR_08(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_08;

    mostrarTabInforme = false;
    mostrarTabAsesoria = false;
    mostrarTabOtrosParticipantes = false;
    mostrarTabPresupuesto = false;
    mostrarTabIndicadores = false;

    //PRIMERO VERIFICAMOS SI EL PROYECTO TIENE ASIGNADO CODIGO Y QUE EL CODIGO SEA DIFERENTE DE "MVIC" = MIGRADOS
    if (cuPr1ProyectoFaces.getProyectoSeleccionado() != null
            && cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto() != null
            && (!cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MVIC") || !cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MTG"))) {

      mostrarTabInforme = true;
      mostrarTabAsesoria = true;
      mostrarTabOtrosParticipantes = true;
      mostrarTabPresupuesto = true;
      mostrarTabIndicadores = true;

    }

    //SI EL PROYECTO ES UN PROYECTO MIGRADO
    if (cuPr1ProyectoFaces.getProyectoSeleccionado() != null
            && cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto() != null
            && (cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MVIC") || cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MTG"))) {

      //SE OCULTAN ESTAS PESTAÑAS SI EL PROYECTO ES UN PROYECTO MIGRADO
      mostrarTabOtrosParticipantes = false;
      mostrarTabPresupuesto = false;
      mostrarTabIndicadores = false;

    }

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_CO_02(Long idProyecto) {

    try {

      init();

      cuPr1ProyectoFaces.initReturnCU_Desde_CU_PR_06(idProyecto);

      includeInformacionInformeProyectoGenericoFaces.setProyectoSeleccionado(cuPr1ProyectoFaces.getProyectoSeleccionado());
      includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(cuPr1ProyectoFaces.getProyectoSeleccionado());

      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_CO_02;

      //CARGAMOS LA LISTA DE COMPROMISOS
      listaCompromisoDTO = iCompromisoPeriodoLocal.getListaCompromisosProyectoPorProyecto(idProyecto);

      mostrarTabInforme = false;
      mostrarTabAsesoria = false;

      mostrarTabOtrosParticipantes = true;
      mostrarTabPresupuesto = true;
      mostrarTabIndicadores = true;

      //CARGAMOS LISTA DE JEFES DE UNIDAD QUE APRUEBA
      listaJefeFuncionariosSeleccionadoAprueba = iUsuarioUnidadPolicialLocal.getUsuarioByUnidadByRole(
              cuPr1ProyectoFaces.getProyectoSeleccionado().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);

      //TODOS LOS JEFES PERTENECEN A LA MISMA UNIDAD FUNCIONAL
      for (VistaFuncionario unaVistaFuncionario : listaJefeFuncionariosSeleccionadoAprueba) {
        unaVistaFuncionario.setUnidadPolicial(cuPr1ProyectoFaces.getProyectoSeleccionado().getUnidadPolicial());
      }

      //CARGAMOS LAS VERSIONES MIENTRAS EL PROYECTO TENGA CODIGO
      cargarListaProyectoVersion();

      //cuPr1ProyectoFaces.getProyectoSeleccionado()
      Long idEstadoProyecto = cuPr1ProyectoFaces.getProyectoSeleccionado().getEstado().getIdConstantes();

      if (IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO.compareTo(idEstadoProyecto) == 0
              || IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO.compareTo(idEstadoProyecto) == 0
              || IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION.compareTo(idEstadoProyecto) == 0) {

        _proyectoSeleccionado = cuPr1ProyectoFaces.getProyectoSeleccionado();
        cargarEvaluacionProyecto();
        mostrarTabEvaluacionProyecto = true;
      }

      return navigationFaces.redirectCuPr06ConsultarDetalleProyectoInvestigacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-06 Consultar detalle proyecto (initReturnCUProyecto) ", e);

    }

    return null;

  }

  /**
   *
   * @param idProyecto
   * @throws JpaDinaeException
   */
  private void cargarEvaluacionProyecto() throws JpaDinaeException {
    _evaluacionProyectoList = _iEvaluacionProyecto.getEvaluacionProyecto(_proyectoSeleccionado.getIdProyecto());
    calcularTotales();
  }

  /**
   *
   * @param evaluacion
   * @return
   */
  public String calcularCualificacion(BigDecimal evaluacion) {

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

  private void calcularTotales() {

    BigDecimal sumatoria = BigDecimal.ZERO;
    BigDecimal promedio = BigDecimal.ZERO;

    if (_evaluacionProyectoList != null && !_evaluacionProyectoList.isEmpty()) {
      for (EvaluacionProyecto eval : _evaluacionProyectoList) {
        sumatoria = sumatoria.add((eval.getNota() == null) ? BigDecimal.ZERO : eval.getNota());
      }

      if (sumatoria.compareTo(BigDecimal.ONE) >= 0) {
        promedio = sumatoria.divide(BigDecimal.valueOf(_evaluacionProyectoList.size()), MathContext.DECIMAL128);
      }
    }

    _calificacionTotal = promedio.toString();
    _cualificacionTotal = (promedio.compareTo(BigDecimal.ONE) >= 0) ? calcularCualificacion(promedio) : DEFICIENTE;

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaProyectoVersion() throws Exception {

    listaProyectoVersionDTO = new ArrayList<ProyectoVersionDTO>();
    //CARGAMOS LAS VERSIONES MIENTRAS EL PROYECTO TENGA CODIGO Y QUE EL CODIGO SEA DIFERENTE DE "MVIC" = MIGRADOS        
    if (cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto() != null
            && cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().trim().length() > 0
            && (!cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MVIC") || !cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MTG"))) {

      listaProyectoVersionDTO = iVersionesLocal.getListaVersiones(cuPr1ProyectoFaces.getProyectoSeleccionado().getIdProyecto());

    }

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_PR_25(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_25;

    mostrarTabInforme = true;

    mostrarTabAsesoria = true;

    return retorno;

  }

  /**
   *
   * @param idProyecto
   * @return
   */
  public String initReturnCU_Desde_CU_PR_24(Long idProyecto) {

    String retorno = initReturnCU_Desde_CU_CO_02(idProyecto);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_24;

    mostrarTabInforme = true;

    mostrarTabAsesoria = true;

    return retorno;

  }

  /**
   *
   * @param proyectoVersionDTO
   * @return
   */
  public String verVersion(ProyectoVersionDTO proyectoVersionDTO) {

    try {

      administracionProyectoVersionFaces.iniciarDatosProyectoVersion(
              proyectoVersionDTO.getIdProyecto(),
              proyectoVersionDTO.getIdVersion(),
              proyectoVersionDTO.getFechaVersion());

      return navigationFaces.redirectCuVersionProyectoRedirect();
      //return "/pages/secured/versiones/administra_informacion_proyecto_version.xhtml?faces-redirect=true&p="+idProyectoEncriptado+"&v="+idVersionEncriptado; 

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-06", e);

    }
    return null;
  }

  /**
   *
   * @param tema
   * @return
   */
  public String seleccionTema(Tema tema) {

    try {

      cuPr1ProyectoFaces.seleccionTema(tema);

      return navigationFaces.redirectCuPr06ConsultarDetalleProyectoInvestigacion();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-06 Consultar detalle proyecto (seleccionTema) ", e);

    }

    return null;
  }

  /**
   *
   * @param compromisoProyectoDTOSeleccionado
   * @return
   */
  public String verInformeCompromisoDetalleProyecto(CompromisoDTO compromisoProyectoDTOSeleccionado) {

    try {

      if (compromisoProyectoDTOSeleccionado == null) {

        return null;

      }

      //VERIFICAMOS K TIPO DE COMPROMISO ES
      if (IConstantes.ORIGEN_COMPROMISO_PROYECTO.equals(compromisoProyectoDTOSeleccionado.getOrigenCompromiso())) {

        CompromisoProyecto compromisoProyectoBusqueda = iCompromisoProyectoLocal.obtenerCompromisoProyecto(compromisoProyectoDTOSeleccionado.getIdCompromisoProyecto());
        Long idTipoCompromiso = compromisoProyectoBusqueda.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes();

        if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                || idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {
          //El sistema muestra el detalle del compromiso  (Informe de avance o informe final) 
          //CU-PR-11 Consultar detalle de compromiso
          String retorno = cuPr11ConsultarDetalleCompromisoFaces.initReturnCU_Llamado_Desde_CUPR6(compromisoProyectoDTOSeleccionado.getIdCompromisoProyecto(), true);
          navigationFaces.redirectFacesCuGenerico(retorno);

        } else if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)) {
          //El sistema identifica que el compromiso a revisar corresponde a un Formulación de proyectos.
          //1.El sistema muestra el detalle del Formulación del  proyecto. CU-PR-06
          String retorno = cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_PR_25(compromisoProyectoBusqueda.getProyecto().getIdProyecto());
          navigationFaces.redirectFacesCuGenerico(retorno);

        }
      } else if (IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION.equals(compromisoProyectoDTOSeleccionado.getOrigenCompromiso())) {

        CompromisoImplementacion compromisoImplementacionBusqueda = iCompromisoImplementacionLocal.
                obtenerCompromisoImplementacionPorId(compromisoProyectoDTOSeleccionado.getIdCompromisoProyecto());

        if (compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO)) {
          //El sistema identifica que el compromiso a revisar corresponde al plan de trabajo de la implementación.
          //1.El sistema muestra el detalle del plan de trabajo. 
          //Grupo de datos [3] que corresponde a los datos para consulta 
          //del CU-PR-21 Registrar plan de trabajo de la implementación de la investigación.
          String retorno = cuPr21RegistrarPlanDeTrabajoFaces.initReturnCU_DESDE_CU_PR_06(
                  compromisoImplementacionBusqueda.getImplementacionesProyecto().getIdImplementacionProy(),
                  compromisoImplementacionBusqueda.getIdCompromisoImplementacion(),
                  true);

          navigationFaces.redirectFacesCuGenerico(retorno);

        } else if (compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)
                || compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

          //1.El sistema muestra el detalle del informe de avance o informe final, 
          //según corresponda. Grupo de datos [5] que corresponde a los datos para consulta del 
          //CU-PR-15 Registrar informe de avance de la implementación de la investigación.
          String retorno = cuPr15_1_2_AvanceImplemenacionFaces.initReturnCU_DESDE_PR_06(
                  compromisoImplementacionBusqueda.getImplementacionesProyecto().getIdImplementacionProy(),
                  compromisoImplementacionBusqueda.getIdCompromisoImplementacion(), true);

          navigationFaces.redirectFacesCuGenerico(retorno);
        }

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-6", e);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String regresar() {

    if (registraLlamdoCU == null) {
      return null;
    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_CO_02)) {

      return navigationFaces.redirectCuCo2DetalleParticipaConvocatoriasRedirect();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_19)) {

      return navigationFaces.redirectCuPr19ConsultarProyectosVigentesAsignadosRedirect();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_20)) {

      return navigationFaces.redirectCuPr20GestionImplementacionesVigentesAsignadasRedirect();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_25)) {

      return navigationFaces.redirectCuPr25RevisarCompromisoJefeUnidad();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_CO_03)) {

      return navigationFaces.redirectCuCo3RevisarPropuestaProyectoRedirect();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_CO_04)) {

      return navigationFaces.redirectCuCo4EvaluarPropuestaConvocatoriaRedirect();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_24)) {

      return navigationFaces.redirectCuPr24RevisarCompromisoEnviadosPorJefeUnidad();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_CO_05)) {

      return navigationFaces.redirectCuCo5ConsultarPropuestasProyectosInvestigacionRedirect();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_08)) {

      return navigationFaces.redirectCu08ConsultarTrabajosYProyectos();

    }

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_12)) {

      return navigationFaces.redirectCuPr12EvaluacionProyectosInvestigacionRedirect();

    }
    return null;
  }

  public List<CompromisoDTO> getListaCompromisoDTO() {
    return listaCompromisoDTO;
  }

  public void setListaCompromisoDTO(List<CompromisoDTO> listaCompromisoDTO) {
    this.listaCompromisoDTO = listaCompromisoDTO;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarResumen() {

    try {

      HashMap mapa = new HashMap();
      mapa.put("p_id_proyecto", cuPr1ProyectoFaces.getProyectoSeleccionado().getIdProyecto().intValue());

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte5.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "5_FORMATO INVESTIGACIÓN.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarFormato() {

    try {

      HashMap mapa = new HashMap();
      mapa.put("p_id_proyecto", cuPr1ProyectoFaces.getProyectoSeleccionado().getIdProyecto().intValue());

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte1.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "1_FORMATO PROYECTO DE INVESTIGACIÓN.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarFormatoPresupuesto() {

    try {

      HashMap mapa = new HashMap();
      int numeroFuentesNoBase = iFuenteProyectoLocal.contarFuentesNOBaseProyectoByIdProyecto(cuPr1ProyectoFaces.getProyectoSeleccionado().getIdProyecto());
      mapa.put("p_id_proyecto", cuPr1ProyectoFaces.getProyectoSeleccionado().getIdProyecto().intValue());
      //p_numero_fuente EQUIVALE AL NUMERO DE FUENTES A MOSTRA
      //0, SI NO SE MUESTRAN FUENTES EXTRAS
      //1, UNA FUENTES
      //2, DOS FUENTES
      //N FUENTES.
      //MAXIMO SON 4 FUENTES
      List<String> listaNombreFuentes = null;
      if (numeroFuentesNoBase > 0) {

        listaNombreFuentes = iFuenteProyectoLocal.getListaFuentesNoBase(cuPr1ProyectoFaces.getProyectoSeleccionado().getIdProyecto());

      }
      mapa.put("p_numero_fuente", numeroFuentesNoBase);

      if (listaNombreFuentes != null && listaNombreFuentes.size() > 0) {

        mapa.put("p_nombre_fuente1", listaNombreFuentes.get(0));

      }
      if (listaNombreFuentes != null && listaNombreFuentes.size() > 1) {

        mapa.put("p_nombre_fuente2", listaNombreFuentes.get(1));

      }
      if (listaNombreFuentes != null && listaNombreFuentes.size() > 2) {

        mapa.put("p_nombre_fuente3", listaNombreFuentes.get(2));

      }
      if (listaNombreFuentes != null && listaNombreFuentes.size() > 3) {

        mapa.put("p_nombre_fuente4", listaNombreFuentes.get(3));
      }

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte6.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "6_FORMATO PRESUPUESTO.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  public boolean isMostrarTabInforme() {
    return mostrarTabInforme && !isEsProyectoMigrado();
  }

  public List<VistaFuncionario> getListaJefeFuncionariosSeleccionadoAprueba() {
    return listaJefeFuncionariosSeleccionadoAprueba;
  }

  public void setListaJefeFuncionariosSeleccionadoAprueba(List<VistaFuncionario> listaJefeFuncionariosSeleccionadoAprueba) {
    this.listaJefeFuncionariosSeleccionadoAprueba = listaJefeFuncionariosSeleccionadoAprueba;
  }

  public boolean isMostrarTabAsesoria() {
    return mostrarTabAsesoria && !isEsProyectoMigrado();
  }

  public List<ProyectoVersionDTO> getListaProyectoVersionDTO() {
    return listaProyectoVersionDTO;
  }

  public boolean isMostrarTabOtrosParticipantes() {
    return mostrarTabOtrosParticipantes && !isEsProyectoMigrado();
  }

  public void setMostrarTabOtrosParticipantes(boolean mostrarTabOtrosParticipantes) {
    this.mostrarTabOtrosParticipantes = mostrarTabOtrosParticipantes;
  }

  public boolean isMostrarTabPresupuesto() {
    return mostrarTabPresupuesto && !isEsProyectoMigrado();
  }

  public void setMostrarTabPresupuesto(boolean mostrarTabPresupuesto) {
    this.mostrarTabPresupuesto = mostrarTabPresupuesto;
  }

  public boolean isMostrarTabIndicadores() {
    return mostrarTabIndicadores && !isEsProyectoMigrado();
  }

  public void setMostrarTabIndicadores(boolean mostrarTabIndicadores) {
    this.mostrarTabIndicadores = mostrarTabIndicadores;
  }

  public boolean isEsProyectoMigrado() {
    return cuPr1ProyectoFaces.getProyectoSeleccionado() != null
            && cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto() != null
            && (cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MVIC") || cuPr1ProyectoFaces.getProyectoSeleccionado().getCodigoProyecto().startsWith("MTG"));
  }

  public boolean isMostrarTabEvaluacionProyecto() {
    return mostrarTabEvaluacionProyecto;
  }

  public void setMostrarTabEvaluacionProyecto(boolean mostrarTabEvaluacionProyecto) {
    this.mostrarTabEvaluacionProyecto = mostrarTabEvaluacionProyecto;
  }

  public List<EvaluacionProyecto> getEvaluacionProyectoList() {
    return _evaluacionProyectoList;
  }

  public void setEvaluacionProyectoList(List<EvaluacionProyecto> _evaluacionProyectoList) {
    this._evaluacionProyectoList = _evaluacionProyectoList;
  }

  public Proyecto getProyectoSeleccionado() {
    return _proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(Proyecto _proyectoSeleccionado) {
    this._proyectoSeleccionado = _proyectoSeleccionado;
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

}
