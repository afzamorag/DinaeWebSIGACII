package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.CompromisoProyectoDTO;
import co.gov.policia.dinae.dto.EjecutorNecesidadDTO;
import co.gov.policia.dinae.enums.EnumFuncionalidadCompromiso;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.ICuPr23GestionaCompromisoAccionRegresar;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.interfaces.IExcepcionCompromisoLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.ExcepcionesCompromiso;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.TransactionRolledbackLocalException;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "cuPr23GestionarCompromisoProyectos")
@javax.enterprise.context.SessionScoped
public class CuPr23GestionarCompromisoProyectos extends JSFUtils implements Serializable {

  private List<SelectItem> listaTipoProyectoItem;
  private List<SelectItem> listaAnioPeriodoItem;
  private List<SelectItem> listaCompromisoItem;
  private List<SelectItem> listaCompromisosPeriodoItem;
  private ICuPr23GestionaCompromisoAccionRegresar iCuPr23GestionaCompromisoAccionRegresar;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IExcepcionCompromisoLocal iExcepcionCompromisoLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private ICompromisoPeriodoLocal iCompromisoPeriodoLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IEjecutorNecesidadLocal iEjecutorNecesidadLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  private EnumFuncionalidadCompromiso enumFuncionalidadCompromiso;
  private Long idTipoProyectoItemSeleccionado;
  private Long idPeriodoItemSeleccionado;
  private String descripcionPeriodo;
  private Periodo periodoSeleccionado;
  private ListGenericDataModel<CompromisoPeriodo> listaHitosCompromisosModel;
  private CompromisoPeriodo hitoCompromisoSeleccionado;
  private boolean cerrarDialogCompromiso;
  private Long idConstantesPropuestaSeleccionada;
  private boolean desHabilitaProyectoInstitucional;

  private Long idListaItemTiposProyectosSeleccionado;
  private Long idListaItemConvocatoriasSeleccionado;
  private Long idListaItemAnioSeleccionado;
  private Long idListaItemTipoCompromisoSeleccionado;

  private List<SelectItem> listaItemTiposProyectos;
  private List<SelectItem> listaItemConvocatorias;
  private List<SelectItem> listaItemAnio;

  private int idTabSeleccionado;
  private Date fechaAnteriorCompromiso;
  private Long idUnidadPolicialCambioUnidadResponsable;

  private ExcepcionesCompromiso excepcionesCompromisoSeleccionado;

  private static final List<Long> listaIdEstadoConvocatoriaPublicada = new ArrayList<Long>();

  static {
    listaIdEstadoConvocatoriaPublicada.add(IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA);
  }

  private static final List<Long> listaIdEstadoProyectoBusquedaCambioUnidad = new ArrayList<Long>();

  static {
    listaIdEstadoProyectoBusquedaCambioUnidad.add(IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION);
    listaIdEstadoProyectoBusquedaCambioUnidad.add(IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION);
  }

  private static final List<Long> listaRolesMailJefeGrupoInvestiga = new ArrayList<Long>();

  static {
    listaRolesMailJefeGrupoInvestiga.add(IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD);
  }

  private static final List<Long> listaRolesMailJefeGrupoInvestigaYJefeUnidad = new ArrayList<Long>();

  static {
    listaRolesMailJefeGrupoInvestigaYJefeUnidad.add(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);
    listaRolesMailJefeGrupoInvestigaYJefeUnidad.add(IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD);
  }

  private List<SelectItem> listaItemsUnidadesPoliciales;
  private String idCompromisoPeriodoSeleccionado;
  private List<SelectItem> listaItemTipoCompromiso;
  private FileUploadEvent eventArchivoSubido;
  private List<ExcepcionesCompromiso> listaExcepcionesCompromiso;
  private ExcepcionesCompromiso excepcionesCompromisoDescargaSeleccionado;
  private List<EjecutorNecesidadDTO> listadoEjecutorNecesidad;
  private ListGenericDataModel<Proyecto> listaProyectoCambioUnidad;
  private Proyecto proyectoCambioUnidadSeleccionado;
  private List<SelectItem> listaItemRolVincula;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      listaItemRolVincula = null;
      proyectoCambioUnidadSeleccionado = null;
      listaProyectoCambioUnidad = null;
      listadoEjecutorNecesidad = null;
      idUnidadPolicialCambioUnidadResponsable = null;
      listaExcepcionesCompromiso = null;
      eventArchivoSubido = null;
      excepcionesCompromisoSeleccionado = null;
      listaItemsUnidadesPoliciales = null;
      idCompromisoPeriodoSeleccionado = null;
      listaItemsUnidadesPoliciales = null;
      listaItemTipoCompromiso = null;
      idTabSeleccionado = 0;
      fechaAnteriorCompromiso = null;
      listaItemTiposProyectos = null;
      listaItemConvocatorias = null;
      listaItemAnio = null;
      idListaItemTiposProyectosSeleccionado = null;
      idListaItemConvocatoriasSeleccionado = null;
      idListaItemAnioSeleccionado = null;
      idListaItemTipoCompromisoSeleccionado = null;
      enumFuncionalidadCompromiso = null;
      idTipoProyectoItemSeleccionado = null;
      idPeriodoItemSeleccionado = null;
      descripcionPeriodo = null;
      periodoSeleccionado = null;
      listaHitosCompromisosModel = null;
      hitoCompromisoSeleccionado = null;
      cerrarDialogCompromiso = false;
      idConstantesPropuestaSeleccionada = null;
      desHabilitaProyectoInstitucional = false;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-23", e);
    }

  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      excepcionesCompromisoSeleccionado = new ExcepcionesCompromiso();
      excepcionesCompromisoSeleccionado.setUnidadPolicial(new UnidadPolicial());

      listaItemsUnidadesPoliciales = UtilidadesItem.getListaSel(
              iUnidadPolicialLocal.getAllUnidadesPolicialesActivasOrdenAlfabetico(),
              "idUnidadPolicial",
              "siglaFisicaYnombreUnidad");

      cargarListaCambosBusqueda();

      return navigationFaces.redirectCuPr23GestionarCompromisoProyectos();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-23", e);

    }

    return null;
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
    if ("idTabViewFechasExtemporaneas".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idTabViewUnidadPolicialResponsable".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    }
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (excepcionesCompromisoDescargaSeleccionado != null && excepcionesCompromisoDescargaSeleccionado.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_pr_23_dir_file_archivo_soporte") + excepcionesCompromisoDescargaSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, excepcionesCompromisoDescargaSeleccionado.getNombreArchivo());

        excepcionesCompromisoDescargaSeleccionado = null;

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23", e);
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
      excepcionesCompromisoSeleccionado.setNombreArchivo(event.getFile().getFileName());
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(event.getFile().getFileName()));

    } catch (Exception e) {

      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23", e);
    }
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
    //CARGAMOS LOS ROLES 
    listaItemRolVincula = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ROL_PROYECTO_NECESIDAD),
            "idConstantes", "valor");

  }

  public void noSeleccionProyecto(UnselectEvent event) {

    proyectoCambioUnidadSeleccionado = null;
    listadoEjecutorNecesidad = null;

  }

  public void verDatalleProyecto(SelectEvent event) {
    try {

      if (proyectoCambioUnidadSeleccionado == null) {

        return;

      }

      cargarListaEjecutorNecesidad(proyectoCambioUnidadSeleccionado.getIdProyecto());

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-04 Evaluar propuestas de convocatorias  (verDatallePropuestaNecesidad) ", ex);
    }
  }

  /**
   *
   */
  public void handleFiltroBusquedaTipoProyectoChange() {

    try {

      idListaItemConvocatoriasSeleccionado = null;
      idListaItemAnioSeleccionado = null;
      listaItemConvocatorias = null;
      listaHitosCompromisosModel = null;
      listaItemAnio = null;

      excepcionesCompromisoSeleccionado = new ExcepcionesCompromiso();
      excepcionesCompromisoSeleccionado.setUnidadPolicial(new UnidadPolicial());

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

      navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuPr23GestionarCompromisoProyectos());

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

        listaHitosCompromisosModel = new ListGenericDataModel(new ArrayList<CompromisoPeriodo>());

        navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuPr23GestionarCompromisoProyectos());

        return;
      }

      List<CompromisoPeriodo> lista = iCompromisoPeriodoLocal.getListaCompromisoPeriodoPorConvocatoriaConvocatoria(idListaItemConvocatoriasSeleccionado);

      listaHitosCompromisosModel = new ListGenericDataModel(lista);

      listaItemTipoCompromiso = UtilidadesItem.getListaSel(
              lista,
              "idCompromisoPeriodoYtipo",
              "nombreCompromisoNumeroIncrementa");

      if (listaHitosCompromisosModel.getNumeroRegistro() == 0) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_24_lbl_no_tipo_compromiso_convocatoria_lista_vacia"));
      }

      navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuPr23GestionarCompromisoProyectos());

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

        listaHitosCompromisosModel = new ListGenericDataModel(new ArrayList<CompromisoPeriodo>());

        navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuPr23GestionarCompromisoProyectos());

        return;
      }

      List<CompromisoPeriodo> lista = iCompromisoPeriodoLocal.getListaCompromisoPeriodoPorIdPeriodo(idListaItemAnioSeleccionado);
      listaHitosCompromisosModel = new ListGenericDataModel(lista);

      listaItemTipoCompromiso = UtilidadesItem.getListaSel(
              lista,
              "idCompromisoPeriodoYtipo",
              "nombreCompromisoNumeroIncrementa");

      if (listaHitosCompromisosModel.getNumeroRegistro() == 0) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_24_lbl_no_tipo_compromiso_convocatoria_lista_vacia"));
      }

      navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuPr23GestionarCompromisoProyectos());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-24 ", e);

    }

  }

  /**
   *
   * @param enumFuncionalidadCompromiso
   * @param periodoSeleccionado
   * @param iCuPr23GestionaCompromisoAccionRegresar
   * @throws Exception
   */
  public void funcionalidad(EnumFuncionalidadCompromiso enumFuncionalidadCompromiso, Periodo periodoSeleccionado, ICuPr23GestionaCompromisoAccionRegresar iCuPr23GestionaCompromisoAccionRegresar) throws Exception {

    this.desHabilitaProyectoInstitucional = false;
    this.iCuPr23GestionaCompromisoAccionRegresar = iCuPr23GestionaCompromisoAccionRegresar;
    this.enumFuncionalidadCompromiso = enumFuncionalidadCompromiso;
    this.periodoSeleccionado = periodoSeleccionado;
    this.descripcionPeriodo = "";
    this.hitoCompromisoSeleccionado = null;
    this.idTipoProyectoItemSeleccionado = null;
    this.idPeriodoItemSeleccionado = null;
    this.listaAnioPeriodoItem = new ArrayList<SelectItem>();

    if (EnumFuncionalidadCompromiso.PROPUESTA_NECESIDAD.equals(enumFuncionalidadCompromiso)) {

      //CARGAMOS LOS TIPOS DE PROYECTOS
      listaTipoProyectoItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_COMPROMISO_NECESIDAD_PROYECTO), "idConstantes", "valor");
      idTipoProyectoItemSeleccionado = IConstantes.TIPO_COMPROMISO_NECESIDAD_DE_PROYECTOS_INSTITUCIONALES;

      //CARGAMOS LA LISTA DE AÑO
      listaAnioPeriodoItem = new ArrayList<SelectItem>();
      listaAnioPeriodoItem.add(new SelectItem(periodoSeleccionado.getIdPeriodo(), periodoSeleccionado.getAnio().toString()));

      idPeriodoItemSeleccionado = periodoSeleccionado.getIdPeriodo();
      descripcionPeriodo = periodoSeleccionado.getDescripcion();

      //CARGAMOS TIPO_COMPROMISO_PERIODO
      listaCompromisosPeriodoItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_COMPROMISO_PERIODO), "idConstantes", "valor");

      //CARGAMOS LA LISTA DE HITOS PERIODO
      cargarHitosPeriodo();

      desHabilitaProyectoInstitucional = true;

    } else if (EnumFuncionalidadCompromiso.COMPROMISO.equals(enumFuncionalidadCompromiso)) {

      listaCompromisoItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_COMPROMISO_COMPROMISO), "idConstantes", "valor");

    } else if (EnumFuncionalidadCompromiso.PROPUESTA_CONVOCATORIA.equals(enumFuncionalidadCompromiso)) {

      listaTipoProyectoItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_COMPROMISO_NECESIDAD_PROYECTO), "idConstantes", "valor");
      idTipoProyectoItemSeleccionado = IConstantes.TIPO_COMPROMISO_NECESIDAD_DE_CONVOCATORIA_DE_FINANCIACION;

      listaAnioPeriodoItem = new ArrayList<SelectItem>();

      Calendar fechaInicio = Calendar.getInstance();
      fechaInicio.setTime(periodoSeleccionado.getFechaInicio());
      String anio = String.valueOf(fechaInicio.get(Calendar.YEAR));

      listaAnioPeriodoItem.add(new SelectItem(periodoSeleccionado.getIdPeriodo(), anio));

      idPeriodoItemSeleccionado = periodoSeleccionado.getIdPeriodo();
      descripcionPeriodo = periodoSeleccionado.getDescripcion();

      //CARGAMOS TIPO_COMPROMISO_PERIODO
      listaCompromisosPeriodoItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_COMPROMISO_PERIODO), "idConstantes", "valor");

      //CARGAMOS LA LISTA DE HITOS PERIODO
      cargarHitosPeriodo();

      desHabilitaProyectoInstitucional = true;
    }

  }

  /**
   *
   * @return
   */
  public String guardarExcecionFechaComtemporanea() {

    try {

      //SI EL COMPROMISO ES DE TIPO FORMULACION DE PROYECTO
      //O INFORME DE AVANCE
      if (idCompromisoPeriodoSeleccionado == null) {
        return null;
      }

      String nombreCompromisoMail = "Compromiso";
      Long idCompromisoPeriodoProyecto = Long.parseLong(idCompromisoPeriodoSeleccionado.split(":")[0]);

      if (idCompromisoPeriodoSeleccionado.endsWith("COMPROMISO_PERIODO")) {

        CompromisoPeriodo compromisoPeriodo = iCompromisoPeriodoLocal.obtenerCompromisoPeriodoPorId(idCompromisoPeriodoProyecto);

        Long idTipoCompromiso = compromisoPeriodo.getTipoCompromiso().getIdConstantes();
        if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)
                || idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)) {

          //LA FECHA NO PUEDE SER MAYOR A SUS CONSECUENTES                
          CompromisoPeriodo compromisoConcecuente = iCompromisoPeriodoLocal.getFechaConcecuenteCompromisoPeriodo(
                  compromisoPeriodo.getPeriodo().getIdPeriodo(),
                  compromisoPeriodo.getFecha());

          //LA FECHA DEL COMPROMISO A ACTUALIZAR DEBER SER MENOR O IGUAL AL COMPROMISO ENCONTRADO
          if (compromisoConcecuente != null && DatesUtils.compareDate(excepcionesCompromisoSeleccionado.getFechaLimite(), compromisoConcecuente.getFecha()) >= 0) {

            adicionaMensajeError("La fecha no debe ser mayor a la registrada en el ".concat(compromisoConcecuente.getNombreCompromisoNumeroIncrementa()));
            return null;

          }
        }

        nombreCompromisoMail = compromisoPeriodo.getNombreCompromisoNumeroIncrementa();
        excepcionesCompromisoSeleccionado.setCompromisoPeriodo(new CompromisoPeriodo(idCompromisoPeriodoProyecto));

      } else if (idCompromisoPeriodoSeleccionado.endsWith("COMPROMISO_PROYECTO")) {

        CompromisoProyecto compromisoProyecto = iCompromisoProyectoLocal.obtenerCompromisoProyecto(idCompromisoPeriodoProyecto);
        nombreCompromisoMail = compromisoProyecto.getNombreCompromisoCorrecion();
        excepcionesCompromisoSeleccionado.setCompromisoProyectoCorreccion(new CompromisoProyecto(idCompromisoPeriodoProyecto));

      }

      if (eventArchivoSubido != null) {

        String[] archivo = almacenarArchivoFisico();

        excepcionesCompromisoSeleccionado.setNombreArchivo(archivo[0] != null ? archivo[0] : excepcionesCompromisoSeleccionado.getNombreArchivo());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
        excepcionesCompromisoSeleccionado.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : excepcionesCompromisoSeleccionado.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

      }

      excepcionesCompromisoSeleccionado = iExcepcionCompromisoLocal.guardarExcepcionesCompromiso(excepcionesCompromisoSeleccionado);

      Long idUnidadPolicial = excepcionesCompromisoSeleccionado.getUnidadPolicial().getIdUnidadPolicial();
      cargarListaUnidadPolicialPorCompromiso(idUnidadPolicial);

      //ENVIAR MAIL
      try {

        enviarMailCambioFechaCompromiso(
                idUnidadPolicial,
                nombreCompromisoMail,
                UtilidadesItem.getFechaFormateadaFormatoCorto(hitoCompromisoSeleccionado.getFecha()),
                listaRolesMailJefeGrupoInvestiga
        );
      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);
      }

      idCompromisoPeriodoSeleccionado = null;
      excepcionesCompromisoSeleccionado = new ExcepcionesCompromiso();
      excepcionesCompromisoSeleccionado.setUnidadPolicial(new UnidadPolicial(idUnidadPolicial));

      adicionaMensajeInformativo("Excepción registrada correctamente");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);

    }

    return null;
  }

  /**
   *
   * @param propuestaNecesidadSeleccionada
   */
  private void cargarListaEjecutorNecesidad(Long idProyecto) throws Exception {

    //CARGAMOS LA LISTA DE  EJECUTORES DEL PROYECTO
    listadoEjecutorNecesidad = iEjecutorNecesidadLocal.getEjecutorNecesidadDTOPorProyecto(idProyecto);

  }

  public String cambiarResponsableProyecto() {

    try {

      //VERIFICAMOS QUE EXISTA UNA UNIDAD EJECUTOA RESPONSABLE POR PROYECTO 
      int numeroUnidadEjecutorasResponsable = 0;
      Long idUnidadPolicialResponsable = null;
      for (EjecutorNecesidadDTO ejecutorNecesidadDTO : listadoEjecutorNecesidad) {

        if (ejecutorNecesidadDTO.getIdRol().equals(IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE)) {

          numeroUnidadEjecutorasResponsable += 1;
          idUnidadPolicialResponsable = ejecutorNecesidadDTO.getIdUnidadPolicial();

        }

      }

      //SIEMPRE DEBE HABER UNA UNIDAD EJECUTORA RESPONSABLE POR PROYECTO
      if (numeroUnidadEjecutorasResponsable == 0) {

        adicionaMensajeError("Debe haber una Unidad Policial con Rol Responsable");
        return null;
      } else if (numeroUnidadEjecutorasResponsable > 1) {

        adicionaMensajeError("Solo debe haber una Unidad Policial con Rol Responsable");
        return null;
      }

      iEjecutorNecesidadLocal.cambiarRolReponsableProyect(proyectoCambioUnidadSeleccionado.getIdProyecto(), listadoEjecutorNecesidad);

      //ACTUALIZAMOS LA LISTA DE UNIDADES EJECUTORAS
      cargarListaEjecutorNecesidad(proyectoCambioUnidadSeleccionado.getIdProyecto());

      //ENVIAMOS MAIL
      try {

        enviarMailCambioResponsable(
                idUnidadPolicialResponsable,
                proyectoCambioUnidadSeleccionado.getCodigoProyecto(),
                listaRolesMailJefeGrupoInvestiga);

      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);

      }

      adicionaMensajeInformativo("Las unidades ejecutoras fueron actualizadas correctamente");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);

    }

    return null;

  }

  /**
   *
   */
  public void handleFiltroUnidadPolicialCambioUnidadResponableChange() {

    try {

      if (idUnidadPolicialCambioUnidadResponsable == null) {
        return;
      }

      //CARGAMOS LOS PROYECTOS ASOCIADOS A ESTRA UNIDAD
      cargarListaProyectoPorUnidadPolicial(idUnidadPolicialCambioUnidadResponsable);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);

    }
  }

  /**
   *
   * @param idUnidadPolicialCambioUnidadResponsable
   * @throws Exception
   */
  private void cargarListaProyectoPorUnidadPolicial(Long idUnidadPolicialCambioUnidadResponsable) throws Exception {

    List<Proyecto> lista = iProyectoLocal.getProyectoPorUnidadPolicialYListaIdEstado(idUnidadPolicialCambioUnidadResponsable, listaIdEstadoProyectoBusquedaCambioUnidad);
    listaProyectoCambioUnidad = new ListGenericDataModel<Proyecto>(lista);

  }

  /**
   *
   */
  public void handleFiltroUnidadPolicialChange() {

    try {

      if (excepcionesCompromisoSeleccionado == null) {

        cargarListaUnidadPolicialPorCompromiso(null);

      } else {

        cargarListaUnidadPolicialPorCompromiso(excepcionesCompromisoSeleccionado.getUnidadPolicial().getIdUnidadPolicial());
        //CONSULTAMOS LOS COMPROMISOS CORRECCIONES QUE EXITAN PARA LOS PROYECTOS DE ESTA UNIDAD
        if (idListaItemTiposProyectosSeleccionado != null && excepcionesCompromisoSeleccionado.getUnidadPolicial().getIdUnidadPolicial() != null) {

          List<CompromisoProyectoDTO> listaCompromisoProyectoDTO = iCompromisoProyectoLocal.getCompromisoProyectosCorreccionPorTipoProyectoYUnidad(
                  idListaItemTiposProyectosSeleccionado,
                  excepcionesCompromisoSeleccionado.getUnidadPolicial().getIdUnidadPolicial());

          //ACTUALIZAMOS LA LISTA DE ITEM
          if (isMostrarFiltroBusquedaAnio()) {

            handleFiltroBusquedaTipoCompromisoPorAnioChange();

          } else if (isMostrarFiltroBusquedaConvocatoria()) {

            handleFiltroBusquedaTipoCompromisoPorConvocatoriaChange();

          }

          if (listaCompromisoProyectoDTO != null && !listaCompromisoProyectoDTO.isEmpty()) {

            idCompromisoPeriodoSeleccionado = null;
            for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

              listaItemTipoCompromiso.add(new SelectItem(
                      unCompromisoProyectoDTO.getIdCompromisoPeriodoYtipo(),
                      unCompromisoProyectoDTO.getCodigoProyecto().concat(" - ").concat(unCompromisoProyectoDTO.getNombreCompromisoCorreccion())));

            }
          }

        }

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);

    }
  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaUnidadPolicialPorCompromiso(Long idUnidadPolicial) throws Exception {

    if (idUnidadPolicial == null) {

      listaExcepcionesCompromiso = new ArrayList<ExcepcionesCompromiso>();
      return;

    }
    listaExcepcionesCompromiso = iExcepcionCompromisoLocal.getListaExcepcionesCompromisoPorUnidadPolicial(idUnidadPolicial);

  }

  private String[] almacenarArchivoFisico() {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();
      String extension = "";
      int i = nombreArchivoOriginal.lastIndexOf('.');
      if (i > 0) {
        extension = nombreArchivoOriginal.substring(i);
      }
      String nombreArchivoFisico = "EXCEPCION_COMPROMISO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

      copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_pr_23_dir_file_archivo_soporte"), eventArchivoSubido.getFile().getInputstream(),
              nombreArchivoFisico);

      //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
      return new String[]{nombreArchivoOriginal, nombreArchivoFisico};

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23 ", e);
    }

    return null;

  }

  /**
   *
   */
  public void actualizarCompromiso() {

    try {

      if (hitoCompromisoSeleccionado.getFecha() == null) {

        adicionaMensajeError("La fecha ingresada no ha sido modificada");
        return;
      }
      //VERIFICAMOS QUE LA NUEVA FECHA INGRESADA NO SEA MENOR A LA FECHA ANTERIOR QUE TENIA COMPROMISO
      if (DatesUtils.compareDate(hitoCompromisoSeleccionado.getFecha(), fechaAnteriorCompromiso) <= 0) {

        adicionaMensajeError("La nueva fecha del compromiso debe ser mayor a la anterior");
        return;

      }

      //SI EL COMPROMISO ES DE TIPO FORMULACION DE PROYECTO
      //O INFORME DE AVANCE
      if (hitoCompromisoSeleccionado.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)
              || hitoCompromisoSeleccionado.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)) {

        //LA FECHA NO PUEDE SER MAYOR A SUS CONSECUENTES                
        CompromisoPeriodo compromisoConcecuente = iCompromisoPeriodoLocal.getFechaConcecuenteCompromisoPeriodo(
                hitoCompromisoSeleccionado.getPeriodo().getIdPeriodo(),
                fechaAnteriorCompromiso);

        //LA FECHA DEL COMPROMISO A ACTUALIZAR DEBER SER MENOR O IGUAL AL COMPROMISO ENCONTRADO
        if (compromisoConcecuente != null && DatesUtils.compareDate(hitoCompromisoSeleccionado.getFecha(), compromisoConcecuente.getFecha()) >= 0) {

          adicionaMensajeError("La fecha no debe ser mayor a la registrada en el ".concat(compromisoConcecuente.getNombreCompromisoNumeroIncrementa()));
          //DEJAMOS LA FECHA DEL COMPROMISO CON SU FECHA ORIGINAL
          hitoCompromisoSeleccionado.setFecha(fechaAnteriorCompromiso);
          return;

        }

      }

      hitoCompromisoSeleccionado.setIdentificacionActualiza(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      hitoCompromisoSeleccionado = iCompromisoPeriodoLocal.guardarCompromisoPeriodo(hitoCompromisoSeleccionado);

      //ACTUALIZAMOS LA LISTA 
      if (idListaItemConvocatoriasSeleccionado != null) {

        handleFiltroBusquedaTipoCompromisoPorConvocatoriaChange();
      }

      if (idListaItemAnioSeleccionado != null) {

        handleFiltroBusquedaTipoCompromisoPorAnioChange();
      }

      try {

        //ENVIAR MAIL
        enviarMailCambioFechaCompromiso(
                null,
                hitoCompromisoSeleccionado.getNombreCompromisoNumeroIncrementa(),
                UtilidadesItem.getFechaFormateadaFormatoCorto(hitoCompromisoSeleccionado.getFecha()),
                listaRolesMailJefeGrupoInvestigaYJefeUnidad
        );
      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23", e);

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23", e);
    }

  }

  /**
   *
   */
  public void guardarCompromiso() {

    try {

      if (hitoCompromisoSeleccionado == null || idConstantesPropuestaSeleccionada == null
              || hitoCompromisoSeleccionado.getFecha() == null) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_23_lbl_datos_incorrectos_add_compromiso"));
        cerrarDialogCompromiso = false;
        return;
      }

      //La fecha ingresada es menor a la fecha actual.
      if (co.gov.policia.dinae.util.DatesUtils.compareDate(hitoCompromisoSeleccionado.getFecha(), new Date()) == -1) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_23_lbl_fechas_invalida_fecha_actual"));
        cerrarDialogCompromiso = false;
        return;

      }
      //VERIFICAMOS QUE PARA "FORMULACION DE PROYECTO E INFORME FINAL SOLO SE PUEDEN ADICIONAR UN SOLO REGISTRO"
      //ESTO SOLO APLICA CUANDO SE ESTA INSERTANDO UN NUEVO COMPROMISO
      if (hitoCompromisoSeleccionado.getIdCompromisoPeriodo() == null) {
        if (idConstantesPropuestaSeleccionada.equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)
                || idConstantesPropuestaSeleccionada.equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {

          //CONTEMOS CUANTOS EXISTEN
          int cantidad = iCompromisoPeriodoLocal.contarCompromisoPeriodoPorPeriodoYTipoCompromiso(
                  periodoSeleccionado.getIdPeriodo(),
                  idConstantesPropuestaSeleccionada);

          if (cantidad >= 1) {

            //CARGAMOS LA LISTA DE HITOS PERIODO
            cargarHitosPeriodo();

            adicionaMensajeError(keyPropertiesFactory.value("cu_pr_23_lbl_existe_compromiso_tipo"));
            return;
          }
        }

        hitoCompromisoSeleccionado.setTipoCompromiso(new Constantes(idConstantesPropuestaSeleccionada));
        hitoCompromisoSeleccionado.setPeriodo(periodoSeleccionado);

      }

      List<CompromisoPeriodo> listaHitos;

      //SE VALIDA LA INFORMACIÓN INGRESADA, ALMACENA LOS DATOS AGREGADOS O MODIFICADOS DEL COMPROMISO, 
      //SI EL TIPO DE COMPROMISO ES 'INFORME DE AVANCE', EL SISTEMA AGREGA UN CONSECUTIVO AL INFORME.
      if (idConstantesPropuestaSeleccionada.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)) {
        //CONSULTAMOS LA LISTA DE CompromisoPeriodo existentes
        listaHitos = iCompromisoPeriodoLocal.buscarCompromisoPeriodoPorPeriodoYTipoCompromiso(
                periodoSeleccionado.getIdPeriodo(), idConstantesPropuestaSeleccionada);

        //ADICIONAMOS A LA LISTA EL NUEVO COMPROMISO
        //SOLO ADICIONAMOS A LA LISTA, CUANDO ES UN NUEVO COMPROMISO
        if (hitoCompromisoSeleccionado.getIdCompromisoPeriodo() == null) {

          listaHitos.add(hitoCompromisoSeleccionado);

        } else {
          //BUSCAMOS Y ACTUALIZAMOS EL REGISTRO MODIFICADO
          for (CompromisoPeriodo unHitosPeriodo : listaHitos) {

            //SI UN COMPROMISO SE ESTA MODIFICANDO, ACTUALIZAMOS A LOS DATOS SELECCIONADOS POR EL USUARIO
            if (unHitosPeriodo.getIdCompromisoPeriodo().equals(hitoCompromisoSeleccionado.getIdCompromisoPeriodo())) {

              unHitosPeriodo.setFecha(hitoCompromisoSeleccionado.getFecha());
              unHitosPeriodo.setPeriodo(hitoCompromisoSeleccionado.getPeriodo());
              unHitosPeriodo.setTipoCompromiso(hitoCompromisoSeleccionado.getTipoCompromiso());
              break;

            }
          }
        }

        //ORDENAMOS LA LISTA POR FECHA
        Collections.sort(listaHitos);
        //ASIGNAMOS LOS CONCECUTIVOS
        short incrementa = 0;
        for (CompromisoPeriodo unHitosPeriodo : listaHitos) {

          unHitosPeriodo.setNumeroIncrementa(++incrementa);

        }

      } else {

        listaHitos = new ArrayList<CompromisoPeriodo>(1);
        listaHitos.add(hitoCompromisoSeleccionado);

      }

      iCompromisoPeriodoLocal.agregarCompromisoPeriodo(listaHitos, loginFaces.getPerfilUsuarioDTO().getIdentificacion());

      hitoCompromisoSeleccionado = null;
      cerrarDialogCompromiso = true;

      //CARGAMOS LA LISTA DE HITOS PERIODO
      cargarHitosPeriodo();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_23_lbl_compromiso_almacenado_ok"));

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-PR-23 Gestionar compromisos de proyectos (guardarCompromiso)", e);
    }
  }

  /**
   *
   */
  public void agregarNuevoCompromiso() {

    hitoCompromisoSeleccionado = new CompromisoPeriodo();
    cerrarDialogCompromiso = false;
    idConstantesPropuestaSeleccionada = null;

  }

  /**
   *
   */
  public void cancelarNuevoCompromiso() {

    hitoCompromisoSeleccionado = null;
    cerrarDialogCompromiso = true;
    idConstantesPropuestaSeleccionada = null;

  }

  /**
   * Ya no ha seleccionado un hitoCompromisoSeleccionado
   *
   * @param event
   */
  public void noSeleccionHitoCompromiso(UnselectEvent event) {
    hitoCompromisoSeleccionado = null;
    fechaAnteriorCompromiso = null;
  }

  /**
   * Se ha seleccionado un hitoCompromisoSeleccionado
   *
   * @param event
   */
  public void hitoCompromisoSeleccion(SelectEvent event) {

    if (hitoCompromisoSeleccionado == null) {
      return;
    }
    cerrarDialogCompromiso = false;
    idConstantesPropuestaSeleccionada = hitoCompromisoSeleccionado.getTipoCompromiso().getIdConstantes();
    fechaAnteriorCompromiso = hitoCompromisoSeleccionado.getFecha();

  }

  /**
   * Metodo que carga la lista de hitos periodo
   *
   * @throws Exception
   */
  private void cargarHitosPeriodo() throws Exception {

    listaHitosCompromisosModel = new ListGenericDataModel(iCompromisoPeriodoLocal.buscarCompromisoPeriodo(periodoSeleccionado.getIdPeriodo()));

  }

  /**
   *
   * @return
   */
  public String regresar() {

    iCuPr23GestionaCompromisoAccionRegresar.actualizarListaCompromisoLlamado();

    if (EnumFuncionalidadCompromiso.PROPUESTA_NECESIDAD.equals(enumFuncionalidadCompromiso)) {

      return navigationFaces.redirectCuNe06PropuestaNecesidades();

    } else if (EnumFuncionalidadCompromiso.PROPUESTA_CONVOCATORIA.equals(enumFuncionalidadCompromiso)) {

      return navigationFaces.redirectCuCo4FinanciarPropuestaConvocatoriaRedirect();

    }
    return null;
  }

  /**
   *
   * @return
   */
  public boolean isEsPropuestaNecesidad() {
    return EnumFuncionalidadCompromiso.PROPUESTA_NECESIDAD.equals(enumFuncionalidadCompromiso);
  }

  /**
   *
   * @return
   */
  public boolean isEsCompromiso() {
    return EnumFuncionalidadCompromiso.COMPROMISO.equals(enumFuncionalidadCompromiso);
  }

  /**
   *
   * @return
   */
  public String getLabelTipoProyecto() {

    if (EnumFuncionalidadCompromiso.PROPUESTA_NECESIDAD.equals(enumFuncionalidadCompromiso)) {
      return keyPropertiesFactory.value("cu_pr_23_lbl_proyectos_intitu");
    } else if (EnumFuncionalidadCompromiso.COMPROMISO.equals(enumFuncionalidadCompromiso)) {
      return keyPropertiesFactory.value("cu_pr_23_lbl_convocatori_finan");
    } else if (EnumFuncionalidadCompromiso.PROPUESTA_CONVOCATORIA.equals(enumFuncionalidadCompromiso)) {
      return keyPropertiesFactory.value("cu_pr_23_lbl_convocatori_finan");
    }
    return "-";
  }

  /**
   *
   * @param e
   */
  public void eliminarCompromisoPeriodo(ActionEvent e) {

    try {
      if (hitoCompromisoSeleccionado != null) {

        iCompromisoPeriodoLocal.eliminarCompromisoPeriodo(hitoCompromisoSeleccionado);

        List<CompromisoPeriodo> lista = iCompromisoPeriodoLocal.getListaCompromisoPeriodoPorConvocatoriaConvocatoria(idListaItemConvocatoriasSeleccionado);
        listaHitosCompromisosModel = new ListGenericDataModel(lista);

        adicionaMensajeInformativo("El compromiso fué eliminado correctamente");

      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr05RegistrarPresupuestoFaces.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransactionRolledbackLocalException ex) {
      adicionaMensajeError("No es posible eliminar el compromiso seleccionado ya que poseé items asociados");
    }
  }

  public List<SelectItem> getListaTipoProyectoItem() {
    return listaTipoProyectoItem;
  }

  public void setListaTipoProyectoItem(List<SelectItem> listaTipoProyectoItem) {
    this.listaTipoProyectoItem = listaTipoProyectoItem;
  }

  public Long getIdTipoProyectoItemSeleccionado() {
    return idTipoProyectoItemSeleccionado;
  }

  public void setIdTipoProyectoItemSeleccionado(Long idTipoProyectoItemSeleccionado) {
    this.idTipoProyectoItemSeleccionado = idTipoProyectoItemSeleccionado;
  }

  public List<SelectItem> getListaAnioPeriodoItem() {
    return listaAnioPeriodoItem;
  }

  public void setListaAnioPeriodoItem(List<SelectItem> listaAnioPeriodoItem) {
    this.listaAnioPeriodoItem = listaAnioPeriodoItem;
  }

  public Long getIdPeriodoItemSeleccionado() {
    return idPeriodoItemSeleccionado;
  }

  public void setIdPeriodoItemSeleccionado(Long idPeriodoItemSeleccionado) {
    this.idPeriodoItemSeleccionado = idPeriodoItemSeleccionado;
  }

  public String getDescripcionPeriodo() {
    return descripcionPeriodo;
  }

  public List<SelectItem> getListaCompromisoItem() {
    return listaCompromisoItem;
  }

  public void setListaCompromisoItem(List<SelectItem> listaCompromisoItem) {
    this.listaCompromisoItem = listaCompromisoItem;
  }

  public CompromisoPeriodo getHitoCompromisoSeleccionado() {
    return hitoCompromisoSeleccionado;
  }

  public void setHitoCompromisoSeleccionado(CompromisoPeriodo hitoCompromisoSeleccionado) {
    this.hitoCompromisoSeleccionado = hitoCompromisoSeleccionado;
  }

  public List<SelectItem> getListaCompromisosPeriodoItem() {
    return listaCompromisosPeriodoItem;
  }

  public void setListaCompromisosPeriodoItem(List<SelectItem> listaCompromisosPeriodoItem) {
    this.listaCompromisosPeriodoItem = listaCompromisosPeriodoItem;
  }

  public boolean isCerrarDialogCompromiso() {
    return cerrarDialogCompromiso;
  }

  public Long getIdConstantesPropuestaSeleccionada() {
    return idConstantesPropuestaSeleccionada;
  }

  public void setIdConstantesPropuestaSeleccionada(Long idConstantesPropuestaSeleccionada) {
    this.idConstantesPropuestaSeleccionada = idConstantesPropuestaSeleccionada;
  }

  public boolean isDesHabilitaProyectoInstitucional() {
    return desHabilitaProyectoInstitucional;
  }

  public boolean isModificarCompromiso() {
    return hitoCompromisoSeleccionado != null && hitoCompromisoSeleccionado.getIdCompromisoPeriodo() != null;
  }

  public Periodo getPeriodoSeleccionado() {
    return periodoSeleccionado;
  }

  public void setPeriodoSeleccionado(Periodo periodoSeleccionado) {
    this.periodoSeleccionado = periodoSeleccionado;
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

  public ListGenericDataModel<CompromisoPeriodo> getListaHitosCompromisosModel() {
    return listaHitosCompromisosModel;
  }

  public void setListaHitosCompromisosModel(ListGenericDataModel<CompromisoPeriodo> listaHitosCompromisosModel) {
    this.listaHitosCompromisosModel = listaHitosCompromisosModel;
  }

  public boolean isMostrarFiltroBusquedaAnio() {

    return idListaItemTiposProyectosSeleccionado != null && idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_PROYECTOS_INSTITUCIONALES);

  }

  public boolean isMostrarFiltroBusquedaConvocatoria() {

    return idListaItemTiposProyectosSeleccionado != null && idListaItemTiposProyectosSeleccionado.equals(IConstantes.TIPO_DE_PROYECTO_DE_CONVOCATORIA_DE_FINANCIACION);

  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public List<SelectItem> getListaItemsUnidadesPoliciales() {
    return listaItemsUnidadesPoliciales;
  }

  public void setListaItemsUnidadesPoliciales(List<SelectItem> listaItemsUnidadesPoliciales) {
    this.listaItemsUnidadesPoliciales = listaItemsUnidadesPoliciales;
  }

  public String getIdCompromisoPeriodoSeleccionado() {
    return idCompromisoPeriodoSeleccionado;
  }

  public void setIdCompromisoPeriodoSeleccionado(String idCompromisoPeriodoSeleccionado) {
    this.idCompromisoPeriodoSeleccionado = idCompromisoPeriodoSeleccionado;
  }

  public List<SelectItem> getListaItemTipoCompromiso() {
    return listaItemTipoCompromiso;
  }

  public void setListaItemTipoCompromiso(List<SelectItem> listaItemTipoCompromiso) {
    this.listaItemTipoCompromiso = listaItemTipoCompromiso;
  }

  public ExcepcionesCompromiso getExcepcionesCompromisoSeleccionado() {
    return excepcionesCompromisoSeleccionado;
  }

  public void setExcepcionesCompromisoSeleccionado(ExcepcionesCompromiso excepcionesCompromisoSeleccionado) {
    this.excepcionesCompromisoSeleccionado = excepcionesCompromisoSeleccionado;
  }

  public String getNombreArchivoSubido() {
    if (excepcionesCompromisoSeleccionado == null && excepcionesCompromisoSeleccionado.getNombreArchivo() != null) {
      return "";
    }
    return excepcionesCompromisoSeleccionado.getNombreArchivo();
  }

  public Date getFechaAnteriorCompromiso() {
    return fechaAnteriorCompromiso;
  }

  public void setFechaAnteriorCompromiso(Date fechaAnteriorCompromiso) {
    this.fechaAnteriorCompromiso = fechaAnteriorCompromiso;
  }

  public List<ExcepcionesCompromiso> getListaExcepcionesCompromiso() {
    return listaExcepcionesCompromiso;
  }

  public void setListaExcepcionesCompromiso(List<ExcepcionesCompromiso> listaExcepcionesCompromiso) {
    this.listaExcepcionesCompromiso = listaExcepcionesCompromiso;
  }

  public boolean isMostrarTabDetalleException() {

    return listaItemTipoCompromiso != null && listaItemTipoCompromiso.size() > 0;

  }

  public Long getIdUnidadPolicialCambioUnidadResponsable() {
    return idUnidadPolicialCambioUnidadResponsable;
  }

  public void setIdUnidadPolicialCambioUnidadResponsable(Long idUnidadPolicialCambioUnidadResponsable) {
    this.idUnidadPolicialCambioUnidadResponsable = idUnidadPolicialCambioUnidadResponsable;
  }

  public List<EjecutorNecesidadDTO> getListadoEjecutorNecesidad() {
    return listadoEjecutorNecesidad;
  }

  public void setListadoEjecutorNecesidad(List<EjecutorNecesidadDTO> listadoEjecutorNecesidad) {
    this.listadoEjecutorNecesidad = listadoEjecutorNecesidad;
  }

  public ListGenericDataModel<Proyecto> getListaProyectoCambioUnidad() {
    return listaProyectoCambioUnidad;
  }

  public void setListaProyectoCambioUnidad(ListGenericDataModel<Proyecto> listaProyectoCambioUnidad) {
    this.listaProyectoCambioUnidad = listaProyectoCambioUnidad;
  }

  public Proyecto getProyectoCambioUnidadSeleccionado() {
    return proyectoCambioUnidadSeleccionado;
  }

  public void setProyectoCambioUnidadSeleccionado(Proyecto proyectoCambioUnidadSeleccionado) {
    this.proyectoCambioUnidadSeleccionado = proyectoCambioUnidadSeleccionado;
  }

  public List<SelectItem> getListaItemRolVincula() {
    return listaItemRolVincula;
  }

  public void setListaItemRolVincula(List<SelectItem> listaItemRolVincula) {
    this.listaItemRolVincula = listaItemRolVincula;
  }

  public boolean isMostrarPanelDatellProyectoSeleccionadoUnidad() {
    return proyectoCambioUnidadSeleccionado != null;
  }

  public int getNumeroRegistroProyectosUnidad() {

    if (listaProyectoCambioUnidad == null) {
      return 0;
    }

    return listaProyectoCambioUnidad.getNumeroRegistro();

  }

  /**
   *
   * @param idUnidadPolicialResponsable
   * @param nombreCompromiso
   * @param fecha
   * @param listaRolesMailJefeGrupoInvestiga
   */
  private void enviarMailCambioFechaCompromiso(Long idUnidadPolicialResponsable, String nombreCompromiso, String fecha, List<Long> listaRolesMailJefeGrupoInvestiga) {

    try {

      Map<String, Object> parametrosContenido = new HashMap<String, Object>();
      parametrosContenido.put("{_parametro1_}", nombreCompromiso);
      parametrosContenido.put("{_parametro2_}", fecha);

      if (idUnidadPolicialResponsable != null) {

        //EL SISTEMA ENVÍA COMUNICACIÓN A LOS JEFES DE UNIDAD CON UN REPORTE DE ACUERDO AL ANEXO. MAIL
        iMailSessionBean.enviarMail_RolUnidadPolicial(
                IConstantes.CU_PR_23_INFORMAR_CAMBIO_FECHA_COMPROMISO,
                null,
                parametrosContenido,
                IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
                idUnidadPolicialResponsable
        );
      }

      //EL SISTEMA ENVÍA A LISTA DE ROLES
      iMailSessionBean.enviarMail_ListaRoles(
              IConstantes.CU_PR_23_INFORMAR_CAMBIO_FECHA_COMPROMISO,
              null,
              parametrosContenido,
              listaRolesMailJefeGrupoInvestiga
      );

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23", e);
    }

  }

  /**
   *
   * @param idUnidadPolicialResponsable
   * @param codigoProyecto
   * @param listaRolesMailJefeGrupoInvestiga
   */
  private void enviarMailCambioResponsable(Long idUnidadPolicialResponsable, String codigoProyecto, List<Long> listaRolesMailJefeGrupoInvestiga) {

    try {

      Map<String, Object> parametrosContenido = new HashMap<String, Object>();
      parametrosContenido.put("{_parametro1_}", codigoProyecto);

      if (idUnidadPolicialResponsable != null) {

        //EL SISTEMA ENVÍA COMUNICACIÓN A LOS JEFES DE UNIDAD CON UN REPORTE DE ACUERDO AL ANEXO. MAIL
        iMailSessionBean.enviarMail_RolUnidadPolicial(
                IConstantes.CU_PR_23_INFORMAR_CAMBIO_RESPONSABLE,
                null,
                parametrosContenido,
                IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
                idUnidadPolicialResponsable
        );
      }

      //EL SISTEMA ENVÍA A LISTA DE ROLES
      iMailSessionBean.enviarMail_ListaRoles(
              IConstantes.CU_PR_23_INFORMAR_CAMBIO_FECHA_COMPROMISO,
              null,
              parametrosContenido,
              listaRolesMailJefeGrupoInvestiga
      );

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-23", e);
    }

  }

  public ExcepcionesCompromiso getExcepcionesCompromisoDescargaSeleccionado() {
    return excepcionesCompromisoDescargaSeleccionado;
  }

  public void setExcepcionesCompromisoDescargaSeleccionado(ExcepcionesCompromiso excepcionesCompromisoDescargaSeleccionado) {
    this.excepcionesCompromisoDescargaSeleccionado = excepcionesCompromisoDescargaSeleccionado;
  }

  public boolean isMostrarTabFechasExtemporaneas() {
    return idListaItemTiposProyectosSeleccionado != null && (idListaItemAnioSeleccionado != null || idListaItemConvocatoriasSeleccionado != null);
  }
}
