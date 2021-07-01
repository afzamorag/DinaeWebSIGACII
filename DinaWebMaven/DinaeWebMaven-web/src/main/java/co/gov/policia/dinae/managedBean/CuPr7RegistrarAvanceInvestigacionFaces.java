package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ActividadesRealizadasProyectoDTO;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.dto.ResultadosAlcanzadosProyectoDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.SugerenciasProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IActividadesRealizadasProyectoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEvidenciaProyectoLocal;
import co.gov.policia.dinae.interfaces.IHorasInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IIndicadoresProyectoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IResultadosAlcanzadosProyectoLocal;
import co.gov.policia.dinae.interfaces.ISugerenciasProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.ActividadesRealizadasProyecto;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EvidenciaProyecto;
import co.gov.policia.dinae.modelo.EvidenciaProyectoDTO;
import co.gov.policia.dinae.modelo.IndicadoresCompromisoProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyecto;
import co.gov.policia.dinae.modelo.InformeAvance;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.ResultadosAlcanzadosProyecto;
import co.gov.policia.dinae.modelo.SugerenciasProyecto;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr7RegistrarAvanceInvestigacionFaces")
@javax.enterprise.context.SessionScoped
public class CuPr7RegistrarAvanceInvestigacionFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private IncludeInformacionInformeProyectoGenericoFaces includeInformacionInformeProyectoGenericoFaces;

  @javax.inject.Inject
  private CuPr19ConsultarProyectosVigentesAsignadosFaces cuPr19ConsultarProyectosVigentesAsignadosFaces;

  @javax.inject.Inject
  private CuPr14AvancePresupuestoProyecto cuPr14AvancePresupuestoProyecto;

  @javax.inject.Inject
  private CuPr05RegistrarPresupuestoFaces cuPr05RegistrarPresupuestoFaces;

  @javax.inject.Inject
  private CuPr1ProyectoFaces cuPr1ProyectoFaces;

  @EJB
  private IHorasInvestigadorLocal iHorasInvestigadorLocal;

  @EJB
  private IInformeAvanceLocal iInformeAvanceLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IActividadesRealizadasProyectoLocal iActividadesRealizadasProyectoLocal;

  @EJB
  private IResultadosAlcanzadosProyectoLocal iResultadosAlcanzadosProyectoLocal;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private IIndicadoresProyectoLocal iIndicadoresProyectoLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private ISugerenciasProyectoLocal iSugerenciasProyectoLocal;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IEvidenciaProyectoLocal iEvidenciaProyectoLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  private String lblTitulo;
  private Date fechaInicalInforme;
  private Date fechaFinalInforme;
  private ORIGEN_LLAMADO_CU registraLlamdoCU;
  private int idTabSeleccionado;
  private Proyecto proyectoSeleccionado;
  private Long idCompromisoProyecto;

  private List<ActividadesRealizadasProyectoDTO> listaActividadesRealizadasProyectoDTO;
  private ActividadesRealizadasProyecto actividadesRealizadasProyectoSeleccionado;
  private ActividadesRealizadasProyectoDTO actividadesRealizadasProyectoDTOEliminarSeleccionado;

  private ResultadosAlcanzadosProyecto resultadosAlcanzadosProyectoSeleccionado;
  private List<ResultadosAlcanzadosProyectoDTO> listaResultadosAlcanzadosProyectoDTO;
  private ResultadosAlcanzadosProyectoDTO resultadosAlcanzadosProyectoDTOEliminarSeleccionado;

  private List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTOhorasInvestigacion;

  //INDICADORES GENERALES
  private ListGenericDataModel<IndicadoresCompromisoProyecto> listaIndicadoresCompromisoProyectoOtros;
  private List<IndicadoresProyecto> listaIndicadoresProyectoGeneral;
  private IndicadoresProyecto indicadoresProyectoEliminar;
  private IndicadoresCompromisoProyecto indicadoresCompromisoProyectoSeleccionado;

  private List<SugerenciasProyectoDTO> listaSugerenciasProyectoDTO;
  private SugerenciasProyecto sugerenciasProyectoSeleccionado;
  private SugerenciasProyectoDTO sugerenciasProyectoDTOEliminarSeleccionado;

  private List<EvidenciaProyectoDTO> listaEvidenciaProyectoDTO;
  private EvidenciaProyecto evidenciaProyectoSeleccionado;
  private EvidenciaProyectoDTO evidenciaProyectoDTOEliminarSeleccionado;
  private EvidenciaProyectoDTO evidenciaProyectoDTODescargarSeleccionado;
  private List<SelectItem> listaSelectItemTipoArchivo;
  private FileUploadEvent eventArchivoSubido;

  private CompromisoProyecto compromisoProyecto;
  private InformeAvance informeAvanceProyectoSeleccionado;

  private UsuarioRol usuarioRol;

  private String _importPresupuestoPage;

  private BigDecimal consumoHorasProyectoHorasInvertidasNumerador;
  private BigDecimal consumoHorasProyectoTotalHorasDenominador;
  private BigDecimal consumoPresupuestoConsumidoNumerador;
  private BigDecimal consumoPresupuestoProyectadoDenominador;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  private ActividadesRealizadasProyectoDTO seleccionarActividadesRealizadasProyectoDTO;
  private ResultadosAlcanzadosProyectoDTO seleccionarResultadosAlcanzadosProyectoDTO;
  private SugerenciasProyectoDTO seleccionaSugerenciasProyectoDTO;

  public static enum ORIGEN_LLAMADO_CU {

    CU_PR_19, CU_PR_10, CU_PR_11
  };

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_10(Long idProyecto, Long idCompromisoProyecto) throws Exception {

    String retorna = initReturnCU_Desde_CU_PR_19(idProyecto, idCompromisoProyecto, -1);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_10;

    return retorna;

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_11(Long idProyecto, Long idCompromisoProyecto) throws Exception {

    String retorna = initReturnCU_Desde_CU_PR_19(idProyecto, idCompromisoProyecto, -1);

    registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_11;

    return retorna;

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws Exception
   */
  public String iniciarDatosCu07(Long idProyecto, Long idCompromisoProyecto) throws Exception {

    RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);

    if (rolUsuarioDTO != null) {

      usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

    } else {
      //LLAMADO DESDE EL PR-10 COMO CONSULTA
      //NO EXISTE ROL
    }

    this.idCompromisoProyecto = idCompromisoProyecto;

    compromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);

    proyectoSeleccionado = iProyectoLocal.obtenerProyectoPorId(idProyecto);

    cuPr1ProyectoFaces.setProyectoSeleccionado(proyectoSeleccionado);

    includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(proyectoSeleccionado);

    actividadesRealizadasProyectoSeleccionado = new ActividadesRealizadasProyecto();

    sugerenciasProyectoSeleccionado = new SugerenciasProyecto();

    resultadosAlcanzadosProyectoSeleccionado = new ResultadosAlcanzadosProyecto();

    indicadoresCompromisoProyectoSeleccionado = new IndicadoresCompromisoProyecto();

    evidenciaProyectoSeleccionado = new EvidenciaProyecto();
    evidenciaProyectoSeleccionado.setTipoArchivo(new Constantes());

    //CARGAR FECHAS DE LOS INFORMES
    cargarFechaInformes();

    //CARGAR LISTA DE ACTIVIDADES REALIZADAS
    cargarListaActividadesRealizada();

    //CARGAR LISTA RESULTADOS ESPERADOS
    cargarListaResultadosEsperados();

    cargarListaInvestigadorProyectoHorasInvestigacion();

    //VERIFICAMOS SI ESTE PROYECTO YA TIENE ADICIONADO LOS INDICADORES GENERALES
    verificarExistenciaIndicadoresBaseYcrear();

    //CARGA LISTA DE INDICADORES
    cargarListaIndicadoresProyectoGeneral();
    cargarListaIndicadoresProyectoOtros();

    cargarListaSugerencia();

    cargarListaEvidenciaProyecto();

    //CARGAMOS LA LISTA SE SELECT ITEM        
    listaSelectItemTipoArchivo = UtilidadesItem.getListaSel(
            iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_EVIDENCIA_TIPO_ARCHIVO),
            "idConstantes",
            "valor");

    return navigationFaces.redirectCuPr7RegistrarAvanceInvestigacionRedirect();

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @param numeroIncrementaCompromiso
   * @return
   * @throws Exception
   */
  public String initReturnCU_Desde_CU_PR_19(Long idProyecto, Long idCompromisoProyecto, Integer numeroIncrementaCompromiso) throws Exception {

    String retorno = null;
    try {

      init();

      retorno = iniciarDatosCu07(idProyecto, idCompromisoProyecto);

      registraLlamdoCU = ORIGEN_LLAMADO_CU.CU_PR_19;

      if (compromisoProyecto.getTipoCompromisoCorreccion() == null) {
        if (numeroIncrementaCompromiso == null) {

          lblTitulo = keyPropertiesFactory.value("cu_pr_7_lbl_titulo_registrar_informe",
                  new Object[]{String.valueOf("")});
        } else if (numeroIncrementaCompromiso.equals(-1)) {

          lblTitulo = keyPropertiesFactory.value("cu_pr_7_lbl_titulo_registrar_informe",
                  new Object[]{String.valueOf("Final")});

        } else if (numeroIncrementaCompromiso > 0) {

          lblTitulo = keyPropertiesFactory.value("cu_pr_7_lbl_titulo_registrar_informe",
                  new Object[]{String.valueOf(numeroIncrementaCompromiso.toString())});
        }
      } else {
        lblTitulo = compromisoProyecto.getNombreCompromisoCorrecion();
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (initReturnCU_Desde_CU_PR_19) ", e);

    }

    return retorno;
  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      consumoHorasProyectoHorasInvertidasNumerador = null;
      consumoHorasProyectoTotalHorasDenominador = null;
      consumoPresupuestoConsumidoNumerador = null;
      consumoPresupuestoProyectadoDenominador = null;

      usuarioRol = null;
      informeAvanceProyectoSeleccionado = null;
      evidenciaProyectoDTODescargarSeleccionado = null;
      eventArchivoSubido = null;
      listaSelectItemTipoArchivo = null;
      listaEvidenciaProyectoDTO = null;
      evidenciaProyectoSeleccionado = null;
      evidenciaProyectoDTOEliminarSeleccionado = null;
      listaIndicadoresCompromisoProyectoOtros = null;
      listaIndicadoresProyectoGeneral = null;
      indicadoresProyectoEliminar = null;
      indicadoresCompromisoProyectoSeleccionado = null;
      listaInvestigadorProyectoDTOhorasInvestigacion = null;
      listaResultadosAlcanzadosProyectoDTO = null;
      actividadesRealizadasProyectoDTOEliminarSeleccionado = null;
      actividadesRealizadasProyectoSeleccionado = null;
      listaActividadesRealizadasProyectoDTO = null;
      listaSugerenciasProyectoDTO = null;
      sugerenciasProyectoSeleccionado = null;
      sugerenciasProyectoDTOEliminarSeleccionado = null;
      idCompromisoProyecto = null;
      proyectoSeleccionado = null;
      idTabSeleccionado = 0;
      fechaInicalInforme = null;
      fechaFinalInforme = null;
      lblTitulo = null;
      proyectoSeleccionado = null;
      seleccionarActividadesRealizadasProyectoDTO = null;
      seleccionarResultadosAlcanzadosProyectoDTO = null;
      seleccionaSugerenciasProyectoDTO = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (init) ", e);
    }

  }

  /**
   *
   * @return
   */
  public String guardarIndicadoresProyecto() {

    if (indicadoresCompromisoProyectoSeleccionado == null) {
      return null;
    }

    try {

      if (indicadoresCompromisoProyectoSeleccionado.getIdIndicadorCompromisoProyecto() == null) {

        indicadoresCompromisoProyectoSeleccionado.setFechaRegistro(new Date());

      } else {

        indicadoresCompromisoProyectoSeleccionado.setFechaModifica(new Date());

      }

      if (indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().getIdIndicadorProyecto() == null) {

        indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().setProyecto(proyectoSeleccionado);
        indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().setFechaRegistro(new Date());
        indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().setUsuarioRol(new UsuarioRol(
                loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                        IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol()));

        indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().setIndicadorBase(IConstantes.NO_N);
        indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().setCasoUso(IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

      }

      indicadoresCompromisoProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol()));

      indicadoresCompromisoProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));

      iIndicadoresProyectoLocal.guardarIndicadoresCompromisoProyecto(indicadoresCompromisoProyectoSeleccionado);

      cargarListaIndicadoresProyectoOtros();

      indicadoresCompromisoProyectoSeleccionado = new IndicadoresCompromisoProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_add_nuevo_indicador_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarIndicadoresProyecto) ", e);

    }
    return navigationFaces.redirectCuPr7RegistrarAvanceInvestigacionRedirect();
  }

  /**
   *
   * @return
   */
  public String cancelarIndicadoresProyecto() {

    indicadoresCompromisoProyectoSeleccionado = new IndicadoresCompromisoProyecto();
    indicadoresCompromisoProyectoSeleccionado.setIndicadoresProyecto(new IndicadoresProyecto());

    return navigationFaces.redirectCuPr7RegistrarAvanceInvestigacionRedirect();
  }

  public void noSeleccionarIndicadorProyecto(UnselectEvent event) {

    indicadoresCompromisoProyectoSeleccionado = new IndicadoresCompromisoProyecto();
    indicadoresCompromisoProyectoSeleccionado.setIndicadoresProyecto(new IndicadoresProyecto());

  }

  /**
   *
   * @param event
   * @throws java.io.IOException
   */
  public void seleccionarIndicadorProyecto(SelectEvent event) throws IOException {

    if (indicadoresCompromisoProyectoSeleccionado == null) {
      //FINALIZA EL PROCESO
      return;
    }

    navigationFaces.redirectFacesCuPr7RegistrarAvanceInvestigacion();

  }

  /**
   *
   */
  public void eliminarIndicadorOtro() {

    try {

      if (indicadoresProyectoEliminar == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iIndicadoresProyectoLocal.eliminarIndicadoresProyecto(indicadoresProyectoEliminar.getIdIndicadorProyecto());

      //ACTUALIZAMOS LA LISTA
      cargarListaIndicadoresProyectoOtros();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_1_lbl_infor_eliminar_indicador_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (eliminarIndicadorOtro) ", e);

    }

  }

  /**
   *
   */
  private void actualizarHorasValorIndicadorGeneral() {

    try {

      List<Long> listaIdInformeCompromiso = iCompromisoProyectoLocal.obtenerListaIdCompromisoProyectoAnterioresSoloInformeAvance(idCompromisoProyecto);
      //VERIFICAMOS SI EL COMPROMISO ACTUAL FUE INCLUIDO
      // DE LO CONTRARIO SE INCLUYE
      boolean encontro = false;
      for (Long unIdCompropmiso : listaIdInformeCompromiso) {

        if (unIdCompropmiso.equals(idCompromisoProyecto)) {

          encontro = true;
          break;
        }
      }

      if (!encontro) {

        listaIdInformeCompromiso.add(idCompromisoProyecto);

      }
      consumoHorasProyectoHorasInvertidasNumerador = BigDecimal.valueOf(iHorasInvestigadorLocal.getSumaCalculoHorasInvestigadorProyectoPorInformCompromisoProyecto(listaIdInformeCompromiso));
      consumoHorasProyectoTotalHorasDenominador = BigDecimal.valueOf(iInvestigadorProyectoLocal.getSumaCalculoHorasTotalInvestigadorProyecto(proyectoSeleccionado.getIdProyecto()));

      consumoPresupuestoConsumidoNumerador = null;
      consumoPresupuestoProyectadoDenominador = null;

    } catch (Exception e) {

      adicionaMensajeError("Ocurrieron errores al actualizar los valores de los indicadores generales");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 ", e);

    }
  }

  /**
   *
   * @return @throws Exception
   */
  public boolean validarDatosInforme() throws Exception {

    //VERIFICAMOS QUE LOS DATOS OBLIGATORIOS SE ENCUENTREN DILIGENCIADOS
    //- INFORMACIÓN EN EL TAB ACTIVIDADES REALIZADAS 
    if (listaActividadesRealizadasProyectoDTO == null || listaActividadesRealizadasProyectoDTO.isEmpty()) {

      adicionaMensajeError("Antes de enviar el Informe de Avance debe registrar las Actividades realizadas");
      return false;
    }

    //- INFORMACIÓN EN EL TAB RESULTADOS ALCANZADOS 
    if (listaResultadosAlcanzadosProyectoDTO == null || listaResultadosAlcanzadosProyectoDTO.isEmpty()) {

      adicionaMensajeError("Antes de enviar el Informe de Avance debe registrar los Resultados alcanzados");
      return false;
    }

    //- INGRESO DE LAS HORAS DE INVESTIGACIÓN PARA TODOS LOS INVESTIGADORES. 
    if (listaInvestigadorProyectoDTOhorasInvestigacion == null || listaInvestigadorProyectoDTOhorasInvestigacion.isEmpty()) {

      adicionaMensajeError("Antes de enviar el Informe de Avance debe registrar las Horas de Investigación");
      return false;
    }
    for (InvestigadorProyectoDTO unInvestigadorProyectoDTO : listaInvestigadorProyectoDTOhorasInvestigacion) {

      if (unInvestigadorProyectoDTO.getHorasInvestigacionTabajadasPeriodo() == null
              || unInvestigadorProyectoDTO.getHorasInvestigacionTabajadasPeriodo() < 0) {

        adicionaMensajeError("Antes de enviar el Informe de Avance debe registrar las Horas de Investigación");
        return false;
      }

    }
    //- INDICADORES SIEMPRE Y CUANDO HAYA INGRESADO INDICADORES NUEVOS.
    if (listaIndicadoresCompromisoProyectoOtros != null && listaIndicadoresCompromisoProyectoOtros.getNumeroRegistro() > 0) {
      for (IndicadoresCompromisoProyecto unIndicadoresCompromisoProyectoOtro : listaIndicadoresCompromisoProyectoOtros) {

        if (unIndicadoresCompromisoProyectoOtro.getValorNumerador() == null
                || unIndicadoresCompromisoProyectoOtro.getValorDenominador() == null) {

          adicionaMensajeError("Antes de enviar el Informe de Avance debe registrar los valores de los indicadores.");
          return false;

        }

      }
    }

    return true;

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarCompromiso(ActionEvent actionEvent) {

    try {

      boolean validar = validarDatosInforme();

      if (!validar) {

        return;

      }

      enviarDetalleCompromiso(actionEvent);

      //RETORNA AL CU DE DONDE FUE LLAMADO
      regresarFaces();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (enviarCompromiso) ", e);

    }

  }

  /**
   *
   * @param actionEvent
   * @throws Exception
   */
  public void enviarDetalleCompromiso(ActionEvent actionEvent) throws Exception {

    //CONSULTAMOS EL COMPROMISO PROYECTO AL QUE VAMOS A PUBLICAR
    CompromisoProyecto unCompromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);

    //VERIFICAMOS SI YA FUE CREADO EL INFORME AVANCE
    InformeAvance informeAvanceBusquedaVerifica = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
            unCompromisoProyecto.getIdCompromisoProyecto(),
            unCompromisoProyecto.getProyecto().getIdProyecto());

    if (informeAvanceBusquedaVerifica == null) {

      //CREAMOS EL INFORME DE AVANCE
      if (!verificarYcreaInformeAvance()) {
        //DETENEMOS EL PROCESO, OCURRIERON ERRORES
        return;
      }

    }

    unCompromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE));
    unCompromisoProyecto.setResultadoRevisionVicin(null);
    unCompromisoProyecto.setComentarioTemporal(null);
    unCompromisoProyecto.setResultadoRetroalimentacion(null);

    compromisoProyecto = iCompromisoProyectoLocal.agregarCompromisoProyecto(unCompromisoProyecto);

    try {

      iMailSessionBean.enviarMail_RolUnidadPolicial(
              IConstantes.CU_PR_07_ENVIO_COMPROMISO_INFORME,
              null,
              null,
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

    } catch (Exception e) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (enviarCompromiso) ", e);
    }

  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (evidenciaProyectoDTODescargarSeleccionado != null && evidenciaProyectoDTODescargarSeleccionado.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_7_dir_file_archivo_soporte") + evidenciaProyectoDTODescargarSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, evidenciaProyectoDTODescargarSeleccionado.getNombreArchivo());

        evidenciaProyectoDTODescargarSeleccionado = null;

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   */
  public void eliminarEvidencia() {

    try {

      if (evidenciaProyectoDTOEliminarSeleccionado == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iEvidenciaProyectoLocal.eliminarEvidenciaProyecto(evidenciaProyectoDTOEliminarSeleccionado.getIdEvidenciaProyecto());

      evidenciaProyectoDTOEliminarSeleccionado = null;

      //ACTUALIZAMOS LA LISTA
      cargarListaEvidenciaProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_evidencia_btn_guarda_info_elimina_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (eliminarIndicadorOtro) ", e);

    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaEvidenciaProyecto() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null
            || compromisoProyecto == null || compromisoProyecto.getIdCompromisoProyecto() == null) {

      listaEvidenciaProyectoDTO = new ArrayList<EvidenciaProyectoDTO>();
      return;
    }

    listaEvidenciaProyectoDTO = iEvidenciaProyectoLocal.getListaEvidenciaProyectoDTOPorProyectoYcompromisoProyecto(
            proyectoSeleccionado.getIdProyecto(),
            compromisoProyecto.getIdCompromisoProyecto());

  }

  /**
   *
   * @return
   */
  public String guardarEvidencia() {

    try {

      if (eventArchivoSubido == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_7_lbl_evidencia_archivo_obligatorio"));
        return null;
      }
      //VERIFICAMOS SI LA RUTA FISICA DONDE SE ALMACENAN LOS ARCHIVOS EXISTE
      //Y LOS PERMISOS SON VALIDOS
      File directorioFinal;

      try {

        directorioFinal = new File(keyPropertiesFactory.value("cu_co_7_dir_file_archivo_soporte"));

      } catch (NullPointerException e) {
        adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
        return null;
      }

      if (!directorioFinal.exists()) {
        adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
        return null;
      }

      if (directorioFinal.isFile()) {
        adicionaMensajeError("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
        return null;
      }

      if (!directorioFinal.canWrite()) {
        adicionaMensajeError("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
        return null;
      }

      String[] archivo = almacenarArchivoFisico();

      evidenciaProyectoSeleccionado.setCompromisoProyecto(compromisoProyecto);
      evidenciaProyectoSeleccionado.setProyecto(proyectoSeleccionado);
      evidenciaProyectoSeleccionado.setNombreArchivo(archivo[0] != null ? archivo[0] : evidenciaProyectoSeleccionado.getNombreArchivo());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      evidenciaProyectoSeleccionado.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : evidenciaProyectoSeleccionado.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

      iEvidenciaProyectoLocal.guardarEvidenciaProyecto(evidenciaProyectoSeleccionado);

      evidenciaProyectoSeleccionado = new EvidenciaProyecto();
      evidenciaProyectoSeleccionado.setTipoArchivo(new Constantes());
      eventArchivoSubido = null;

      cargarListaEvidenciaProyecto();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_evidencia_btn_guarda_info_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarEvidencia) ", e);

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

      String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();
      String extension = "";
      int i = nombreArchivoOriginal.lastIndexOf('.');
      if (i > 0) {
        extension = nombreArchivoOriginal.substring(i);
      }
      String nombreArchivoFisico = "INFORME_AVANCE_EVIDENCIA".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

      copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_co_7_dir_file_archivo_soporte"), eventArchivoSubido.getFile().getInputstream(),
              nombreArchivoFisico);

      //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
      return new String[]{nombreArchivoOriginal, nombreArchivoFisico};

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (almacenarArchivoFisico) ", e);
    }

    return null;

  }

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      eventArchivoSubido = event;
      evidenciaProyectoSeleccionado.setNombreArchivo(event.getFile().getFileName());
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(event.getFile().getFileName()));

    } catch (Exception e) {

      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (handleFileUpload) ", e);
    }
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoGeneral() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaIndicadoresProyectoGeneral = new ArrayList<IndicadoresProyecto>();
      return;
    }

    listaIndicadoresProyectoGeneral = iIndicadoresProyectoLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            proyectoSeleccionado.getIdProyecto(),
            IConstantes.YES_Y,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaIndicadoresProyectoOtros() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null) {

      listaIndicadoresCompromisoProyectoOtros = new ListGenericDataModel(new ArrayList<IndicadoresCompromisoProyecto>());
      return;
    }

    List<IndicadoresCompromisoProyecto> lista = iIndicadoresProyectoLocal.getListaIndicadoresCompromisoProyectoPorProyectoEindicadorBase(
            proyectoSeleccionado.getIdProyecto(),
            IConstantes.NO_N,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01,
            idCompromisoProyecto);

    listaIndicadoresCompromisoProyectoOtros = new ListGenericDataModel<IndicadoresCompromisoProyecto>(lista);

  }

  /**
   *
   * @throws Exception
   */
  private void verificarExistenciaIndicadoresBaseYcrear() throws Exception {

    if (proyectoSeleccionado.getIdProyecto() == null) {
      //EL PROYECTO ES NECESARIO PARA ESTE PROCESO
      return;
    }

    List<IndicadoresProyecto> listaIndicadores = iIndicadoresProyectoLocal.getListaIndicadoresProyectoPorProyectoEindicadorBase(
            proyectoSeleccionado.getIdProyecto(),
            IConstantes.YES_Y,
            IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

    if (listaIndicadores.isEmpty()) {
      //NO EXISTEN INDICADORES CREADOS PARA ESTE PROYECTO
      //SE CREAN LOS INDICADORES
      crearIndicadoresProyecto();
    }

  }

  /**
   *
   * @throws Exception
   */
  private void crearIndicadoresProyecto() throws Exception {

    if (proyectoSeleccionado.getIdProyecto() == null) {
      //ESTE PROCESO ES NECESARIO QUE EL PROYECTO EXISTA COMO ENTIDAD
      return;
    }

    //LO CONSULTAMOS PARA TENER LA ULTIMA VERSION DEL OBJETO 
    List<ConstantesDTO> listaIndicadorConstante = iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_INDICADORES_GENERALES_PROYECTO);

    List<IndicadoresProyecto> indicadoresProyectoList = new ArrayList<IndicadoresProyecto>();

    Long idUsuarioRol = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
            IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol();
    for (ConstantesDTO constantesDTO : listaIndicadorConstante) {

      IndicadoresProyecto indicadoresProyecto = new IndicadoresProyecto();
      indicadoresProyecto.setProyecto(new Proyecto(proyectoSeleccionado.getIdProyecto()));
      indicadoresProyecto.setIndicadorBase(IConstantes.YES_Y);
      indicadoresProyecto.setNombreIndicador(constantesDTO.getCodigo().trim());
      indicadoresProyecto.setUsuarioRol(new UsuarioRol(idUsuarioRol));
      indicadoresProyecto.setFechaRegistro(new Date());
      String valores[] = constantesDTO.getValor().split("/");
      indicadoresProyecto.setNombreNumerador(valores[0].trim());
      indicadoresProyecto.setNombreDenominador(valores[1].trim());
      indicadoresProyecto.setCasoUso(IConstantes.CLAVE_INDICADOR_BASE_CASO_USO_PR_01);

      indicadoresProyectoList.add(indicadoresProyecto);

    }
    proyectoSeleccionado.setIndicadoresProyectoList(indicadoresProyectoList);

    //GUARDAMOS EL PROYECTO
    proyectoSeleccionado = iProyectoLocal.guardarProyecto(proyectoSeleccionado);

  }

  /**
   *
   * @return
   */
  public String guardarHorasInvestigacion() {

    try {

      iInvestigadorProyectoLocal.guardarHorasInvestigacionPeriodoLista(
              listaInvestigadorProyectoDTOhorasInvestigacion,
              idCompromisoProyecto);

      cargarListaInvestigadorProyectoHorasInvestigacion();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_horas_investiga_btn_guarda_info_horas_inves_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarInformacionBasica) ", e);

    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaInvestigadorProyectoHorasInvestigacion() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null || idCompromisoProyecto == null) {

      listaInvestigadorProyectoDTOhorasInvestigacion = new ArrayList<InvestigadorProyectoDTO>();

    }

    listaInvestigadorProyectoDTOhorasInvestigacion = iInvestigadorProyectoLocal.getListaInvestigadorProyectoDTOPorProyectoYcompromisoProyecto(
            proyectoSeleccionado.getIdProyecto(),
            idCompromisoProyecto);

  }

  /**
   *
   * @return
   */
  public String regresar() {

    if (ORIGEN_LLAMADO_CU.CU_PR_19.equals(registraLlamdoCU)) {

      return cuPr19ConsultarProyectosVigentesAsignadosFaces.initReturnCU();

    } else if (ORIGEN_LLAMADO_CU.CU_PR_10.equals(registraLlamdoCU)) {

      return navigationFaces.redirectCuPr10RegistroInformeFinalRedirect();

    } else if (ORIGEN_LLAMADO_CU.CU_PR_11.equals(registraLlamdoCU)) {

      return navigationFaces.redirectCuPr11ConsultarDetalleCompromisoRedirect();

    }

    return null;
  }

  /**
   * Este metodo hace redireccion por medio de FACES
   *
   * @throws java.io.IOException
   */
  public void regresarFaces() throws IOException {

    if (ORIGEN_LLAMADO_CU.CU_PR_19.equals(registraLlamdoCU)) {

      cuPr19ConsultarProyectosVigentesAsignadosFaces.initReturnCU();

      navigationFaces.redirectFacesCuPr19ConsultarProyectosVigentesAsignados();

    } else if (ORIGEN_LLAMADO_CU.CU_PR_10.equals(registraLlamdoCU)) {

      navigationFaces.redirectFacesCuPr10RegistroInformeFinal();

    } else if (ORIGEN_LLAMADO_CU.CU_PR_10.equals(registraLlamdoCU)) {

      navigationFaces.redirectCuPr11ConsultarDetalleCompromisoRedirect();

    }

    _importPresupuestoPage = null;
    getSessionMap().put(PresupuestoUtil.EJECUTAR_PRESUPUESTO, false);

  }

  /**
   *
   * @param changeEvent
   */
  public void cambioFechaFinChangeEvent(ValueChangeEvent changeEvent) {

    if (changeEvent == null) {
      return;
    }

    fechaFinalInforme = (Date) changeEvent.getNewValue();
  }

  /**
   *
   * @throws Exception
   */
  private boolean verificarYcreaInformeAvance() throws Exception {

    if (fechaFinalInforme == null) {

      adicionaMensajeError(keyPropertiesFactory.value("cu_pr_7_lbl_error_fecha_fin_informe"));
      return false;
    }
    InformeAvance informeAvance = new InformeAvance();

    if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_19)) {

      informeAvance.setTipoInformeAvance(new Constantes(IConstantes.TIPO_INFORME_AVANCE_AVANCE));

    } else if (registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_10)) {

      informeAvance.setTipoInformeAvance(new Constantes(IConstantes.TIPO_INFORME_AVANCE_FINAL));

    }

    informeAvance.setProyecto(proyectoSeleccionado);

    RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
    UsuarioRol usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

    informeAvance.setIdUsuarioRol(usuarioRol);
    informeAvance.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));

    informeAvance.setFechaFinalizacion(fechaFinalInforme);
    informeAvance.setFechaInicio(fechaInicalInforme);

    //ACTUALIZAMOS EL COMPROMISO A ELABORACION 
    CompromisoProyecto unCompromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoProyecto);
    unCompromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION));

    iInformeAvanceLocal.crearInformeAvanceCompromidoProyectoActualizarCompromiso(informeAvance, unCompromisoProyecto);

    return true;
  }

  /**
   *
   * @return
   */
  public String guardarInformacionBasica() {

    try {

      verificarYcreaInformeAvance();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_btn_guarda_info_basica_guardada_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarInformacionBasica) ", e);

    }
    return null;
  }

  /**
   *
   * @throws Exception
   */
  private void guardarResultadoAlcanzadoDetalle() throws Exception {

    //GUARDAMOS
    iResultadosAlcanzadosProyectoLocal.guardarResultadosAlcanzadosProyecto(resultadosAlcanzadosProyectoSeleccionado);

    //REINICIAMOS LOS DATOS POR DEFAULT
    resultadosAlcanzadosProyectoSeleccionado = new ResultadosAlcanzadosProyecto();

    //RECARGAMOS LA LISTA
    cargarListaResultadosEsperados();

    //MOSTRAMOS MENSAJE INFORMATIVO
    adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_tab_resultado_almacenada_ok"));

  }

  /**
   *
   * @return
   */
  public String guardarResultadoAlcanzado() {

    try {

      resultadosAlcanzadosProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));
      resultadosAlcanzadosProyectoSeleccionado.setProyecto(proyectoSeleccionado);
      resultadosAlcanzadosProyectoSeleccionado.setFechaCreacion(new Date());
      resultadosAlcanzadosProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol()));
      resultadosAlcanzadosProyectoSeleccionado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      guardarResultadoAlcanzadoDetalle();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarResultadoAlcanzado) ", e);
    }

    return null;
  }

  /**
   *
   */
  public void eliminarResultadosEsperados() {

    try {

      if (resultadosAlcanzadosProyectoDTOEliminarSeleccionado == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iResultadosAlcanzadosProyectoLocal.eliminarActividadRealizada(resultadosAlcanzadosProyectoDTOEliminarSeleccionado.getIdResultadosEsperadosProyecto());

      //ACTUALIZAMOS LA LISTA
      cargarListaResultadosEsperados();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_tab_resultado_eliminada_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (eliminarResultadosEsperados) ", e);

    }

  }

  /**
   *
   */
  public void eliminarActividadRealizada() {

    try {

      if (actividadesRealizadasProyectoDTOEliminarSeleccionado == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iActividadesRealizadasProyectoLocal.eliminarActividadRealizada(actividadesRealizadasProyectoDTOEliminarSeleccionado.getIdActividadesRealizadasProyecto());

      //ACTUALIZAMOS LA LISTA
      cargarListaActividadesRealizada();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_tab_actividesde_realizada_eliminada_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (eliminarActividadRealizada) ", e);

    }

  }

  /**
   *
   */
  public void eliminarSugerencia() {

    try {

      if (sugerenciasProyectoDTOEliminarSeleccionado == null) {
        return;
      }
      //GUARDAMOS EL GRUPO
      iSugerenciasProyectoLocal.eliminarSugerenciasProyecto(sugerenciasProyectoDTOEliminarSeleccionado.getIdSugerenciaProyecto());

      //ACTUALIZAMOS LA LISTA
      cargarListaSugerencia();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_tab_sugerencia_eliminada_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (eliminarSugerencia) ", e);

    }

  }

  /**
   *
   * @throws Exception
   */
  private void guardarActividadRealizadaDetalle() throws Exception {

    //GUARDAMOS
    iActividadesRealizadasProyectoLocal.guardarActividadesRealizadasProyecto(actividadesRealizadasProyectoSeleccionado);

    //REINICIAMOS LOS DATOS POR DEFAULT
    actividadesRealizadasProyectoSeleccionado = new ActividadesRealizadasProyecto();

    //RECARGAMOS LA LISTA
    cargarListaActividadesRealizada();

    //MOSTRAMOS MENSAJE INFORMATIVO
    adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_tab_actividesde_realizada_almacenada_ok"));

  }

  /**
   *
   * @return
   */
  public String guardarActividadRealizada() {

    try {

      actividadesRealizadasProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));
      actividadesRealizadasProyectoSeleccionado.setProyecto(proyectoSeleccionado);
      actividadesRealizadasProyectoSeleccionado.setFechaCreacion(new Date());
      actividadesRealizadasProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol()));
      actividadesRealizadasProyectoSeleccionado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      guardarActividadRealizadaDetalle();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarActividadRealizada) ", e);
    }

    return null;
  }

  /**
   *
   */
  private void guardarSugerenciaDetalle() throws Exception {

    //GUARDAMOS
    iSugerenciasProyectoLocal.guardarSugerenciasProyecto(sugerenciasProyectoSeleccionado);

    //REINICIAMOS LOS DATOS POR DEFAULT
    sugerenciasProyectoSeleccionado = new SugerenciasProyecto();

    //RECARGAMOS LA LISTA
    cargarListaSugerencia();

    //MOSTRAMOS MENSAJE INFORMATIVO
    adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_7_lbl_tab_sugerencia_almacenada_ok"));

  }

  /**
   *
   * @return
   */
  public String guardarSugerencia() {

    try {

      sugerenciasProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));
      sugerenciasProyectoSeleccionado.setProyecto(proyectoSeleccionado);
      sugerenciasProyectoSeleccionado.setFechaCreacion(new Date());
      sugerenciasProyectoSeleccionado.setUsuarioRol(new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL).getIdUsuarioRol()));
      sugerenciasProyectoSeleccionado.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

      guardarSugerenciaDetalle();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (guardarActividadRealizada) ", e);
    }

    return null;
  }

  /**
   *
   */
  private void cargarListaResultadosEsperados() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null || idCompromisoProyecto == null) {

      listaResultadosAlcanzadosProyectoDTO = new ArrayList<ResultadosAlcanzadosProyectoDTO>();

    }

    listaResultadosAlcanzadosProyectoDTO = iResultadosAlcanzadosProyectoLocal.getListaResultadosAlcanzadosProyectoDTOporProyectoYcompromisoProyecto(
            proyectoSeleccionado.getIdProyecto(),
            idCompromisoProyecto);

  }

  /**
   *
   */
  private void cargarListaActividadesRealizada() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null || idCompromisoProyecto == null) {

      listaActividadesRealizadasProyectoDTO = new ArrayList<ActividadesRealizadasProyectoDTO>();

    }

    listaActividadesRealizadasProyectoDTO = iActividadesRealizadasProyectoLocal.getListaActividadesRealizadasProyectoDTOporProyectoYcompromisoProyecto(
            proyectoSeleccionado.getIdProyecto(),
            idCompromisoProyecto);

  }

  /**
   *
   */
  private void cargarListaSugerencia() throws Exception {

    if (proyectoSeleccionado == null || proyectoSeleccionado.getIdProyecto() == null || idCompromisoProyecto == null) {

      listaSugerenciasProyectoDTO = new ArrayList<SugerenciasProyectoDTO>();

    }

    listaSugerenciasProyectoDTO = iSugerenciasProyectoLocal.getListaSugerenciasProyectoDTOporProyectoYcompromisoProyecto(
            proyectoSeleccionado.getIdProyecto(),
            idCompromisoProyecto);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarFechaInformes() throws Exception {

    fechaInicalInforme = null;
    fechaFinalInforme = null;
    //PERIODO DEL INFORME: EL SISTEMA TOMA COMO FECHA INICIAL, LA FECHA FINAL DEL ANTERIOR INFORME,
    //SI CORRESPONDE AL PRIMER INFORME, EL SISTEMA MUESTRA COMO FECHA INICIAL LA FECHA
    //EN QUE FUE APROBADO EL PROYECTO.
    //POR DEFECTO LA FECHA FINAL CORRESPONDE A LA FECHA ACTUAL LA CUAL ES MODIFICABLE.

    Long idTipoCompromisoFinal = IConstantes.COMPROMISO_PERIODO_INFORME_FINAL;

    if (compromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa() == 1) {

      //ESTE SERIA EL PRIMER COMPROMISO
      informeAvanceProyectoSeleccionado = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
              idCompromisoProyecto,
              proyectoSeleccionado.getIdProyecto());

      if (informeAvanceProyectoSeleccionado == null) {

        fechaInicalInforme = proyectoSeleccionado.getFechaEstimadaInicio();
        fechaFinalInforme = new Date();

        //IMMEDIATAMENTE GUARDAMOS EL INFORME
        informeAvanceProyectoSeleccionado = new InformeAvance();
        informeAvanceProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));
        informeAvanceProyectoSeleccionado.setFechaInicio(fechaInicalInforme);
        informeAvanceProyectoSeleccionado.setFechaFinalizacion(fechaFinalInforme);
        informeAvanceProyectoSeleccionado.setFechaRegistro(new Date());

        informeAvanceProyectoSeleccionado.setIdUsuarioRol(usuarioRol);
        informeAvanceProyectoSeleccionado.setProyecto(proyectoSeleccionado);
        informeAvanceProyectoSeleccionado.setTipoInformeAvance(new Constantes(IConstantes.TIPO_INFORME_AVANCE_AVANCE));

        informeAvanceProyectoSeleccionado = iInformeAvanceLocal.saveOrUpdate(informeAvanceProyectoSeleccionado);

      }

    } else if (compromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa() > 1) {
      //SI EL COMPROMISO CON EL QUE ESTAMOS TRABAJANDO ES MAYOR A 1

      //CONSULTAMOS SI YA EXITEN EL INFORME
      informeAvanceProyectoSeleccionado = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
              idCompromisoProyecto,
              proyectoSeleccionado.getIdProyecto());

      if (informeAvanceProyectoSeleccionado == null) {

        //VERIFICAMOS EL COMPROMISO ANTERIOR A EL, QUE DEBE EXISTIR POR OBLIGACION
        //ESTO CON EL FIN DE TOMAR COMO FECHA INICIAL LA FECHA FINAL DEL COMPROMISO ANTERIOR A ESTE
        //EJEMPLO:
        //SI EL COMPROMISO ACTUAL TIENE COMO getNumeroIncrementa UN VALOR A 2
        //SIGNIFICA QUE EL COMPROMISO CON getNumeroIncrementa IGUAL A 1 DEBE EXISTIR
        CompromisoProyecto compromisoProyectoAnterior = iCompromisoProyectoLocal.obtenerCompromisoProyectoAnteriorTipoAvance(
                idCompromisoProyecto,
                IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE);

        if (compromisoProyectoAnterior == null) {
          //ESTO NUNCA DEBERÍA PRESENTARSE
          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (cargarFechaInformes) ");
          throw new RuntimeException("COMPROMISO ANTERIOR NO ENCONTRADO..");
        }

        informeAvanceProyectoSeleccionado = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
                compromisoProyectoAnterior.getIdCompromisoProyecto(),
                proyectoSeleccionado.getIdProyecto());

        if (informeAvanceProyectoSeleccionado == null) {
          //ESTO NUNCA DEBERÍA PRESENTARSE
          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07 Registrar Avance Investigacion (cargarFechaInformes) ");
          throw new RuntimeException("COMPROMISO ANTERIOR NO ENCONTRADO..");
        }

        fechaInicalInforme = informeAvanceProyectoSeleccionado.getFechaFinalizacion();
        fechaFinalInforme = new Date();

        //IMMEDIATAMENTE GUARDAMOS EL INFORME
        informeAvanceProyectoSeleccionado = new InformeAvance();
        informeAvanceProyectoSeleccionado.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));
        informeAvanceProyectoSeleccionado.setFechaInicio(fechaInicalInforme);
        informeAvanceProyectoSeleccionado.setFechaFinalizacion(fechaFinalInforme);
        informeAvanceProyectoSeleccionado.setFechaRegistro(new Date());

        informeAvanceProyectoSeleccionado.setIdUsuarioRol(usuarioRol);
        informeAvanceProyectoSeleccionado.setProyecto(proyectoSeleccionado);
        informeAvanceProyectoSeleccionado.setTipoInformeAvance(new Constantes(IConstantes.TIPO_INFORME_AVANCE_FINAL));

        informeAvanceProyectoSeleccionado = iInformeAvanceLocal.saveOrUpdate(informeAvanceProyectoSeleccionado);

      }

    } else if (compromisoProyecto.getCompromisoPeriodo().getNumeroIncrementa() == 0
            && idTipoCompromisoFinal.compareTo(compromisoProyecto.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes()) == 0) {

      informeAvanceProyectoSeleccionado = iInformeAvanceLocal.obtenerInformeAvancePorCompromidoProyectoYproyecto(
              idCompromisoProyecto,
              proyectoSeleccionado.getIdProyecto());

    }

    fechaInicalInforme = informeAvanceProyectoSeleccionado.getFechaInicio();
    fechaFinalInforme = informeAvanceProyectoSeleccionado.getFechaFinalizacion();

  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;
    _importPresupuestoPage = null;

    if (event == null || event.getTab() == null) {

      return;
    }
    if ("idcu_pr_7_lbl_tab_titulo_infobasica".equals(event.getTab().getId())) {

      idTabSeleccionado = 0;
      includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(proyectoSeleccionado);

    } else if ("idcu_pr_7_lbl_tab_titulo_actividades_realiza".equals(event.getTab().getId())) {

      idTabSeleccionado = 1;

    } else if ("idcu_pr_7_lbl_tab_titulo_resultados_alcanz".equals(event.getTab().getId())) {

      idTabSeleccionado = 2;

    } else if ("idcu_pr_7_lbl_tab_titulo_horas_investi".equals(event.getTab().getId())) {

      idTabSeleccionado = 3;

    } else if ("idcu_pr_7_lbl_tab_titulo_presupuesto_ejecu".equals(event.getTab().getId())) {

      idTabSeleccionado = 4;
      _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";
      ejecutarPresupuesto();

    } else if ("idcu_pr_7_lbl_tab_titulo_indicadores".equals(event.getTab().getId())) {

      idTabSeleccionado = 5;
      actualizarHorasValorIndicadorGeneral();
      ejecutarPresupuesto();

    } else if ("idcu_pr_7_lbl_tab_titulo_sugerencia".equals(event.getTab().getId())) {

      idTabSeleccionado = 6;

    } else if ("idcu_pr_7_lbl_tab_titulo_evidencia".equals(event.getTab().getId())) {

      idTabSeleccionado = 7;

    }

  }

  public void ejecutarPresupuesto() {

    presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_EJECUTA, proyectoSeleccionado.getIdProyecto(), informeAvanceProyectoSeleccionado.getIdInformeAvance());
    //ACTUALIZA EL SUBTOTAL EJECUTADO
    includeInformacionInformeProyectoGenericoFaces.setSubTotalEjecutadoNumerador(BigDecimal.valueOf(presupuestoUtilFaces.getRubroSubtotalEjecutadoTotal()));
  }

  public String getLblTitulo() {
    return lblTitulo;
  }

  public void setLblTitulo(String lblTitulo) {
    this.lblTitulo = lblTitulo;
  }

  public Date getFechaInicalInforme() {
    return fechaInicalInforme;
  }

  public void setFechaInicalInforme(Date fechaInicalInforme) {
    this.fechaInicalInforme = fechaInicalInforme;
  }

  public Date getFechaFinalInforme() {
    return fechaFinalInforme;
  }

  public void setFechaFinalInforme(Date fechaFinalInforme) {
    this.fechaFinalInforme = fechaFinalInforme;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public Proyecto getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public List<ActividadesRealizadasProyectoDTO> getListaActividadesRealizadasProyectoDTO() {
    return listaActividadesRealizadasProyectoDTO;
  }

  public void setListaActividadesRealizadasProyectoDTO(List<ActividadesRealizadasProyectoDTO> listaActividadesRealizadasProyectoDTO) {
    this.listaActividadesRealizadasProyectoDTO = listaActividadesRealizadasProyectoDTO;
  }

  public ActividadesRealizadasProyecto getActividadesRealizadasProyectoSeleccionado() {
    return actividadesRealizadasProyectoSeleccionado;
  }

  public void setActividadesRealizadasProyectoSeleccionado(ActividadesRealizadasProyecto actividadesRealizadasProyectoSeleccionado) {
    this.actividadesRealizadasProyectoSeleccionado = actividadesRealizadasProyectoSeleccionado;
  }

  public ActividadesRealizadasProyectoDTO getActividadesRealizadasProyectoDTOEliminarSeleccionado() {
    return actividadesRealizadasProyectoDTOEliminarSeleccionado;
  }

  public void setActividadesRealizadasProyectoDTOEliminarSeleccionado(ActividadesRealizadasProyectoDTO actividadesRealizadasProyectoDTOEliminarSeleccionado) {
    this.actividadesRealizadasProyectoDTOEliminarSeleccionado = actividadesRealizadasProyectoDTOEliminarSeleccionado;
  }

  public ResultadosAlcanzadosProyecto getResultadosAlcanzadosProyectoSeleccionado() {
    return resultadosAlcanzadosProyectoSeleccionado;
  }

  public void setResultadosAlcanzadosProyectoSeleccionado(ResultadosAlcanzadosProyecto resultadosAlcanzadosProyectoSeleccionado) {
    this.resultadosAlcanzadosProyectoSeleccionado = resultadosAlcanzadosProyectoSeleccionado;
  }

  public List<ResultadosAlcanzadosProyectoDTO> getListaResultadosAlcanzadosProyectoDTO() {
    return listaResultadosAlcanzadosProyectoDTO;
  }

  public void setListaResultadosAlcanzadosProyectoDTO(List<ResultadosAlcanzadosProyectoDTO> listaResultadosAlcanzadosProyectoDTO) {
    this.listaResultadosAlcanzadosProyectoDTO = listaResultadosAlcanzadosProyectoDTO;
  }

  public ResultadosAlcanzadosProyectoDTO getResultadosAlcanzadosProyectoDTOEliminarSeleccionado() {
    return resultadosAlcanzadosProyectoDTOEliminarSeleccionado;
  }

  public void setResultadosAlcanzadosProyectoDTOEliminarSeleccionado(ResultadosAlcanzadosProyectoDTO resultadosAlcanzadosProyectoDTOEliminarSeleccionado) {
    this.resultadosAlcanzadosProyectoDTOEliminarSeleccionado = resultadosAlcanzadosProyectoDTOEliminarSeleccionado;
  }

  public boolean isMostrarBtnInfoBasicaProyecto() {
    return idTabSeleccionado == 0 && !isSoloConsulta();
  }

  public List<InvestigadorProyectoDTO> getListaInvestigadorProyectoDTOhorasInvestigacion() {
    return listaInvestigadorProyectoDTOhorasInvestigacion;
  }

  public void setListaInvestigadorProyectoDTOhorasInvestigacion(List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTOhorasInvestigacion) {
    this.listaInvestigadorProyectoDTOhorasInvestigacion = listaInvestigadorProyectoDTOhorasInvestigacion;
  }

  public List<IndicadoresProyecto> getListaIndicadoresProyectoGeneral() {
    return listaIndicadoresProyectoGeneral;
  }

  public void setListaIndicadoresProyectoGeneral(List<IndicadoresProyecto> listaIndicadoresProyectoGeneral) {
    this.listaIndicadoresProyectoGeneral = listaIndicadoresProyectoGeneral;
  }

  public ListGenericDataModel<IndicadoresCompromisoProyecto> getListaIndicadoresCompromisoProyectoOtros() {
    return listaIndicadoresCompromisoProyectoOtros;
  }

  public void setListaIndicadoresCompromisoProyectoOtros(ListGenericDataModel<IndicadoresCompromisoProyecto> listaIndicadoresCompromisoProyectoOtros) {
    this.listaIndicadoresCompromisoProyectoOtros = listaIndicadoresCompromisoProyectoOtros;
  }

  public int getNumeroFilasListaIndicadorProyectoOtros() {
    if (listaIndicadoresCompromisoProyectoOtros == null) {
      return 0;
    }
    return listaIndicadoresCompromisoProyectoOtros.getRowCount();
  }

  public IndicadoresProyecto getIndicadoresProyectoEliminar() {
    return indicadoresProyectoEliminar;
  }

  public void setIndicadoresProyectoEliminar(IndicadoresProyecto indicadoresProyectoEliminar) {
    this.indicadoresProyectoEliminar = indicadoresProyectoEliminar;
  }

  public boolean isMostrarBtnAgregarIndicadorOtro() {
    return indicadoresCompromisoProyectoSeleccionado != null
            && indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto() != null
            && indicadoresCompromisoProyectoSeleccionado.getIndicadoresProyecto().getIdIndicadorProyecto() != null;
  }

  public IndicadoresCompromisoProyecto getIndicadoresCompromisoProyectoSeleccionado() {
    return indicadoresCompromisoProyectoSeleccionado;
  }

  public void setIndicadoresCompromisoProyectoSeleccionado(IndicadoresCompromisoProyecto indicadoresCompromisoProyectoSeleccionado) {
    this.indicadoresCompromisoProyectoSeleccionado = indicadoresCompromisoProyectoSeleccionado;
  }

  public List<SugerenciasProyectoDTO> getListaSugerenciasProyectoDTO() {
    return listaSugerenciasProyectoDTO;
  }

  public void setListaSugerenciasProyectoDTO(List<SugerenciasProyectoDTO> listaSugerenciasProyectoDTO) {
    this.listaSugerenciasProyectoDTO = listaSugerenciasProyectoDTO;
  }

  public SugerenciasProyecto getSugerenciasProyectoSeleccionado() {
    return sugerenciasProyectoSeleccionado;
  }

  public void setSugerenciasProyectoSeleccionado(SugerenciasProyecto sugerenciasProyectoSeleccionado) {
    this.sugerenciasProyectoSeleccionado = sugerenciasProyectoSeleccionado;
  }

  public SugerenciasProyectoDTO getSugerenciasProyectoDTOEliminarSeleccionado() {
    return sugerenciasProyectoDTOEliminarSeleccionado;
  }

  public void setSugerenciasProyectoDTOEliminarSeleccionado(SugerenciasProyectoDTO sugerenciasProyectoDTOEliminarSeleccionado) {
    this.sugerenciasProyectoDTOEliminarSeleccionado = sugerenciasProyectoDTOEliminarSeleccionado;
  }

  public List<EvidenciaProyectoDTO> getListaEvidenciaProyectoDTO() {
    return listaEvidenciaProyectoDTO;
  }

  public void setListaEvidenciaProyectoDTO(List<EvidenciaProyectoDTO> listaEvidenciaProyectoDTO) {
    this.listaEvidenciaProyectoDTO = listaEvidenciaProyectoDTO;
  }

  public EvidenciaProyecto getEvidenciaProyectoSeleccionado() {
    return evidenciaProyectoSeleccionado;
  }

  public void setEvidenciaProyectoSeleccionado(EvidenciaProyecto evidenciaProyectoSeleccionado) {
    this.evidenciaProyectoSeleccionado = evidenciaProyectoSeleccionado;
  }

  public EvidenciaProyectoDTO getEvidenciaProyectoDTOEliminarSeleccionado() {
    return evidenciaProyectoDTOEliminarSeleccionado;
  }

  public void setEvidenciaProyectoDTOEliminarSeleccionado(EvidenciaProyectoDTO evidenciaProyectoDTOEliminarSeleccionado) {
    this.evidenciaProyectoDTOEliminarSeleccionado = evidenciaProyectoDTOEliminarSeleccionado;
  }

  public List<SelectItem> getListaSelectItemTipoArchivo() {
    return listaSelectItemTipoArchivo;
  }

  public void setListaSelectItemTipoArchivo(List<SelectItem> listaSelectItemTipoArchivo) {
    this.listaSelectItemTipoArchivo = listaSelectItemTipoArchivo;
  }

  public String getNombreArchivoSubido() {
    if (evidenciaProyectoSeleccionado == null && evidenciaProyectoSeleccionado.getNombreArchivo() != null) {
      return "";
    }
    return evidenciaProyectoSeleccionado.getNombreArchivo();
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return evidenciaProyectoSeleccionado != null && evidenciaProyectoSeleccionado.getNombreArchivo() != null && evidenciaProyectoSeleccionado.getNombreArchivoFisico() != null;
  }

  public EvidenciaProyectoDTO getEvidenciaProyectoDTODescargarSeleccionado() {
    return evidenciaProyectoDTODescargarSeleccionado;
  }

  public void setEvidenciaProyectoDTODescargarSeleccionado(EvidenciaProyectoDTO evidenciaProyectoDTODescargarSeleccionado) {
    this.evidenciaProyectoDTODescargarSeleccionado = evidenciaProyectoDTODescargarSeleccionado;
  }

  public boolean isMostrarBtnEnviar() {

    return registraLlamdoCU != null && registraLlamdoCU.equals(ORIGEN_LLAMADO_CU.CU_PR_19);

  }

  public Long getIdCompromisoProyecto() {
    return idCompromisoProyecto;
  }

  public void setIdCompromisoProyecto(Long idCompromisoProyecto) {
    this.idCompromisoProyecto = idCompromisoProyecto;
  }

  public void setRegistraLlamdoCU(ORIGEN_LLAMADO_CU registraLlamdoCU) {
    this.registraLlamdoCU = registraLlamdoCU;
  }

  public String irAvancePresupuesto() {
    _importPresupuestoPage = null;
    String cu = CuPr14AvancePresupuestoProyecto.CU_PR_7;
    return cuPr14AvancePresupuestoProyecto.initReturnCU(proyectoSeleccionado.getIdProyecto(), compromisoProyecto.getIdCompromisoProyecto(), cu, false);
  }

  public String irPresupuestoDefinido() {
    try {
      _importPresupuestoPage = null;
      cuPr05RegistrarPresupuestoFaces.initProyectoCU07(proyectoSeleccionado.getIdProyecto(), this.idCompromisoProyecto);
      return "/pages/secured/cu_pr_05/registrarPresupuesto.xhtml?faces-redirect=true";
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr7RegistrarAvanceInvestigacionFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public String getImportPresupuestoPage() {
    return _importPresupuestoPage;
  }

  public void setImportPresupuestoPage(String _importPresupuestoPage) {
    this._importPresupuestoPage = _importPresupuestoPage;
  }

  public BigDecimal getConsumoHorasProyectoHorasInvertidasNumerador() {
    return consumoHorasProyectoHorasInvertidasNumerador;
  }

  public BigDecimal getConsumoHorasProyectoTotalHorasDenominador() {
    return consumoHorasProyectoTotalHorasDenominador;
  }

  public BigDecimal getConsumoPresupuestoConsumidoNumerador() {
    return consumoPresupuestoConsumidoNumerador;
  }

  public BigDecimal getConsumoPresupuestoProyectadoDenominador() {
    return consumoPresupuestoProyectadoDenominador;
  }

  public BigDecimal getValorIndicadorGeneral(String numeradorDenominador, Number numeroFila) {

    //PRIMERA FILA: NUMERADOR :Consumo de horas del proyecto 
    if ("NUMERADOR".equals(numeradorDenominador) && 2 == numeroFila.intValue()) {

      return consumoHorasProyectoHorasInvertidasNumerador;

    }
    //PRIMERA FILA: DENOMINADOR: Consumo de horas del proyecto 
    if ("DENOMINADOR".equals(numeradorDenominador) && 2 == numeroFila.intValue()) {

      return consumoHorasProyectoTotalHorasDenominador;

    }

    if ("NUMERADOR".equals(numeradorDenominador) && 1 == numeroFila.intValue()) {

      return includeInformacionInformeProyectoGenericoFaces.getSubTotalEjecutadoNumerador();

    }
    //PRIMERA FILA: DENOMINADOR: Consumo de horas del proyecto 
    if ("DENOMINADOR".equals(numeradorDenominador) && 1 == numeroFila.intValue()) {

      return includeInformacionInformeProyectoGenericoFaces.getValorTotalProyectoDenominador();

    }

    return null;

  }

  public String getValorOperacionSinDecimalToString(Number numeroFila) {

    Double valor1 = 0D;
    Double valor2 = 0D;

    try {

      //Consumo de horas del proyecto 
      if (2 == numeroFila.intValue()) {

        if (consumoHorasProyectoHorasInvertidasNumerador == null || consumoHorasProyectoTotalHorasDenominador == null) {
          return "0";
        }

        valor1 = consumoHorasProyectoHorasInvertidasNumerador.doubleValue();
        valor2 = consumoHorasProyectoTotalHorasDenominador.doubleValue();

      } // Consumo de presupuesto
      else if (1 == numeroFila.intValue()) {

        if (includeInformacionInformeProyectoGenericoFaces.getSubTotalEjecutadoNumerador() == null
                || includeInformacionInformeProyectoGenericoFaces.getValorTotalProyectoDenominador() == null) {
          return "0";
        }

        valor1 = includeInformacionInformeProyectoGenericoFaces.getSubTotalEjecutadoNumerador().doubleValue();
        valor2 = includeInformacionInformeProyectoGenericoFaces.getValorTotalProyectoDenominador().doubleValue();
      }

      if (valor2 == 0D) {
        valor2 = 1D;
      }

      Double resultado = valor1 / valor2;
      resultado = resultado * 100;

      return String.valueOf(resultado.intValue());

    } catch (Exception e) {

      Logger.getLogger(CuPr7RegistrarAvanceInvestigacionFaces.class.getName()).log(Level.SEVERE, null, e);

    }

    return "0";
  }

  public boolean isSoloConsulta() {

    return ORIGEN_LLAMADO_CU.CU_PR_11.equals(registraLlamdoCU);

  }

  /**
   *
   * @return
   */
  public boolean isMostrarBtnCancelarActividadSeleccionado() {

    return actividadesRealizadasProyectoSeleccionado != null && actividadesRealizadasProyectoSeleccionado.getIdActividadesRealizadasProyecto() != null;

  }

  /**
   *
   * @return
   */
  public String cancelarResultadoEsperadoSeleccionado() {

    resultadosAlcanzadosProyectoSeleccionado = new ResultadosAlcanzadosProyecto();
    seleccionarResultadosAlcanzadosProyectoDTO = new ResultadosAlcanzadosProyectoDTO();

    return null;

  }

  /**
   *
   * @return
   */
  public String cancelarSugerenciaMejoramientoSeleccionado() {

    sugerenciasProyectoSeleccionado = new SugerenciasProyecto();
    seleccionaSugerenciasProyectoDTO = new SugerenciasProyectoDTO();

    return null;

  }

  /**
   *
   * @return
   */
  public String cancelarActividadSeleccionada() {

    actividadesRealizadasProyectoSeleccionado = new ActividadesRealizadasProyecto();
    seleccionarActividadesRealizadasProyectoDTO = new ActividadesRealizadasProyectoDTO();

    return null;

  }

  /**
   *
   * @return
   */
  public String cancelarResultadoAlcanzadoSeleccionada() {

    resultadosAlcanzadosProyectoSeleccionado = new ResultadosAlcanzadosProyecto();
    seleccionarResultadosAlcanzadosProyectoDTO = new ResultadosAlcanzadosProyectoDTO();

    return null;

  }

  /**
   *
   * @return
   */
  public String actualizarSugerenciaSeleccionada() {

    try {

      guardarSugerenciaDetalle();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07", e);

    }

    return null;

  }

  /**
   *
   * @return
   */
  public String actualizarActividadSeleccionada() {

    try {

      guardarActividadRealizadaDetalle();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07", e);

    }

    return null;

  }

  /**
   *
   * @return
   */
  public String actualizarResultadoAlcanzadoSeleccionado() {

    try {

      guardarResultadoAlcanzadoDetalle();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07", e);

    }

    return null;

  }

  /**
   *
   * @param event
   */
  public void actualizarActividadRealizada(SelectEvent event) {

    try {

      if (seleccionarActividadesRealizadasProyectoDTO == null) {
        return;
      }

      actividadesRealizadasProyectoSeleccionado = iActividadesRealizadasProyectoLocal
              .obtenerActividadesRealizadasProyectoPorId(
                      seleccionarActividadesRealizadasProyectoDTO
                      .getIdActividadesRealizadasProyecto());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07", e);

    }

  }

  /**
   *
   * @param event
   */
  public void actualizarResultadoEsperado(SelectEvent event) {

    try {

      if (seleccionarResultadosAlcanzadosProyectoDTO == null) {
        return;
      }

      resultadosAlcanzadosProyectoSeleccionado = iResultadosAlcanzadosProyectoLocal
              .obtenerResultadosAlcanzadosProyectoPorId(
                      seleccionarResultadosAlcanzadosProyectoDTO
                      .getIdResultadosEsperadosProyecto());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07", e);

    }

  }

  /**
   *
   * @param event
   */
  public void actualizarSugerenciaMejoramiento(SelectEvent event) {

    try {

      if (seleccionaSugerenciasProyectoDTO == null) {
        return;
      }

      sugerenciasProyectoSeleccionado = iSugerenciasProyectoLocal
              .obtenerSugerenciasProyectoPorId(
                      seleccionaSugerenciasProyectoDTO
                      .getIdSugerenciaProyecto());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-07", e);

    }

  }

  public ActividadesRealizadasProyectoDTO getSeleccionarActividadesRealizadasProyectoDTO() {
    return seleccionarActividadesRealizadasProyectoDTO;
  }

  public void setSeleccionarActividadesRealizadasProyectoDTO(ActividadesRealizadasProyectoDTO seleccionarActividadesRealizadasProyectoDTO) {
    this.seleccionarActividadesRealizadasProyectoDTO = seleccionarActividadesRealizadasProyectoDTO;
  }

  public ResultadosAlcanzadosProyectoDTO getSeleccionarResultadosAlcanzadosProyectoDTO() {
    return seleccionarResultadosAlcanzadosProyectoDTO;
  }

  public void setSeleccionarResultadosAlcanzadosProyectoDTO(ResultadosAlcanzadosProyectoDTO seleccionarResultadosAlcanzadosProyectoDTO) {
    this.seleccionarResultadosAlcanzadosProyectoDTO = seleccionarResultadosAlcanzadosProyectoDTO;
  }

  public SugerenciasProyectoDTO getSeleccionaSugerenciasProyectoDTO() {
    return seleccionaSugerenciasProyectoDTO;
  }

  public void setSeleccionaSugerenciasProyectoDTO(SugerenciasProyectoDTO seleccionaSugerenciasProyectoDTO) {
    this.seleccionaSugerenciasProyectoDTO = seleccionaSugerenciasProyectoDTO;
  }
}
