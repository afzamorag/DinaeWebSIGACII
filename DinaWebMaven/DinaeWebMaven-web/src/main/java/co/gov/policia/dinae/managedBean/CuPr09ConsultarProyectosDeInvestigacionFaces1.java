package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IReseniaInvestigacionLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.InformeAvance;
import co.gov.policia.dinae.modelo.ReseniaInvestigacion;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Édder Peña Barranco
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 * @since 2013/12/15
 */
@javax.inject.Named(value = "cuPr09ConsultarProyectosDeInvestigacion")
@javax.enterprise.context.SessionScoped
public class CuPr09ConsultarProyectosDeInvestigacionFaces1 extends JSFUtils implements Serializable {

  @EJB
  private IAreaCienciaTecnologiaLocal iAreaCienciaTecnologiaLocal;

  @EJB
  private ILineaLocal iLineaLocal;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IInformeAvanceLocal iInformaAvanceLocal;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IReseniaInvestigacionLocal iReseniaInvestigacionLocal;

  private String palabraClave;
  private Long idAreaCienciaTecnologiaSeleccionada;
  private List<SelectItem> listaItemsAreaCienciaYTecnologia;
  private Long idLineaSeleccionada;
  private List<SelectItem> listaItemsLineaCienciaYTecnologia;
  private Long idEstadoImplementacion;
  private List<SelectItem> listaItemsEstadosImplementacion;
  private Date fechaPresentacionInicial;
  private Date fechaPresentacionFinal;
  private ProyectoDTO propuestaSeleccionada;
  private ReseniaInvestigacion reseniaInvetigacion;
  private ListGenericDataModel<ProyectoDTO> listaProyectosEncontradosModel;

  public String initReturnCU() throws Exception {

    try {
      init();
      //Se cargan las listas para la consulta de los filtros
      cargarDatosInicialesCUConsultarProyectosDeInvestigacion();

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-09 Consultar Proyectos de Investiación", ex);
    }

    return navigationFaces.redirectCuPr09ConsultarProyectosDeInvestigacionRedirect();
  }

  //Constructor inicial
  @javax.annotation.PostConstruct
  public void init() {

    //Se inicializan todos los elementos en null
    palabraClave = null;
    idAreaCienciaTecnologiaSeleccionada = null;
    listaItemsAreaCienciaYTecnologia = null;
    idLineaSeleccionada = null;
    listaItemsLineaCienciaYTecnologia = null;
    idEstadoImplementacion = null;
    listaItemsEstadosImplementacion = null;
    fechaPresentacionInicial = null;
    fechaPresentacionFinal = null;
    setPropuestaSeleccionada(null);
    reseniaInvetigacion = null;
    listaProyectosEncontradosModel = null;
  }

  //Inicializadores    
  //Metodo que carga las listas de consulta para los filtros
  private void cargarDatosInicialesCUConsultarProyectosDeInvestigacion() throws Exception {

    listaItemsAreaCienciaYTecnologia = UtilidadesItem.getListaSel(
            iAreaCienciaTecnologiaLocal.getAreaCienciaTecnologias(),
            "idAreaCienciaTecnologia",
            "nombre");

    String noImplementada = keyPropertiesFactory.value("cu_pr_09_propertie_no_implementada");
    String enProcesoImplementacion = keyPropertiesFactory.value("cu_pr_09_propertie_en_proceso_implementacion");
    String implementada = keyPropertiesFactory.value("cu_pr_09_propertie_implementada");

    listaItemsEstadosImplementacion = new ArrayList<SelectItem>();
    listaItemsEstadosImplementacion.add(new SelectItem(IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO, noImplementada));
    listaItemsEstadosImplementacion.add(new SelectItem(IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION, enProcesoImplementacion));
    listaItemsEstadosImplementacion.add(new SelectItem(IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO, implementada));

  }

  //Metodos
  /**
   * Método que carga las lineas dependiendo del Aera Seleccionada
   */
  public void handleAreaCienciaTecnologiaSeleccionadaChange() {

    idLineaSeleccionada = null;

    if (idAreaCienciaTecnologiaSeleccionada == null) {
      listaItemsLineaCienciaYTecnologia = new ArrayList<SelectItem>();
      return;
    }

    try {

      listaItemsLineaCienciaYTecnologia = UtilidadesItem.getListaSel(
              iLineaLocal.getLineasPorArea(idAreaCienciaTecnologiaSeleccionada),
              "idLinea",
              "nombre");

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-PR-09 Consultar Proyectos de Investiación (handleAreaCienciaTecnologiaSeleccionadaChange) ", e);
    }

  }

  //Metodo que realiza la busqueda de trabajos o proyectos de grado
  public void buscarTrabajoOProyecto() throws JpaDinaeException {
    try {

      //Para busqueda por fechas 
      if (fechaPresentacionInicial != null && fechaPresentacionFinal == null) {

        adicionaMensajeError("Para búsqueda entre fechas, se deben ingresar la fechas, inicial y final");
        return;
      }

      if (fechaPresentacionInicial != null && fechaPresentacionFinal != null) {

        if (fechaPresentacionInicial.after(fechaPresentacionFinal)) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_08_lbl_error_msg_fecha_presentacion_inicial_mayor_fecha_presentacion_final"));
          return;
        }
      }

      List<Long> listaEstadosProyecto = new ArrayList<Long>();
      if (idEstadoImplementacion == null || idEstadoImplementacion == -1L) {

        listaEstadosProyecto.add(IConstantes.TIPO_ESTADO_PROYECTO_CULMINADO);
        listaEstadosProyecto.add(IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO);
        listaEstadosProyecto.add(IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION);
        listaEstadosProyecto.add(IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO);

      } else {

        listaEstadosProyecto.add(idEstadoImplementacion);
      }

      List<ProyectoDTO> listaProyectoDTO = iProyectoLocal.getProyectosDTOOTrabajosBusquedaPorFiltros(palabraClave,
              null,
              null,
              null,
              idAreaCienciaTecnologiaSeleccionada,
              idLineaSeleccionada,
              null,
              null,
              null,
              listaEstadosProyecto,
              null,
              null,
              null,
              fechaPresentacionInicial,
              fechaPresentacionFinal,
              null,
              null,
              null,
              null,
              null,
              null,
              null);

      if (listaProyectoDTO.isEmpty()) {

        adicionaMensajeError("No se encontraron resultados..");

      }

      listaProyectosEncontradosModel = new ListGenericDataModel(listaProyectoDTO);

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-09 Consultar Proyectos de Investiación) ", e);

    }

  }

  //Metodo que permite limpiar los campos de filtrado
  public void limpiarCampos() throws JpaDinaeException, Exception {

    initReturnCU();

  }

  //Metodo que permite limpiar los campos de filtrado
  public void regresar() throws JpaDinaeException, Exception {

    navigationFaces.redirectFacesCuPr09ConsultarProyectosDeInvestigacion();

  }

  /**
   *
   * @param event
   */
  public void verDatalleProyectoInvestigacion(SelectEvent event) {
    try {
      if (getPropuestaSeleccionada() != null) {

        Long idCompromisoProyecto = iCompromisoProyectoLocal.getIdCompromisoProyectoFinalPorProyectoYtipoCompromisoYEstadoCompromiso(
                getPropuestaSeleccionada().getId(),
                IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO,
                IConstantes.COMPROMISO_PERIODO_INFORME_FINAL);

        InformeAvance informeAvance = iInformaAvanceLocal.findInformeAvanceFinalByProyecto(
                getPropuestaSeleccionada().getId(),
                IConstantes.TIPO_INFORME_AVANCE_FINAL,
                idCompromisoProyecto);

        if (informeAvance != null) {

          this.reseniaInvetigacion = iReseniaInvestigacionLocal.findByInformeFinalProyecto(
                  informeAvance.getIdInformeAvance(),
                  getPropuestaSeleccionada().getId());

        }

        String retorno = "/pages/secured/cu_pr_09/include_resenia_investigacion.xhtml?faces-redirect=true";

        navigationFaces.redirectFacesCuGenerico(retorno);

      }

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-09 Consultar Proyectos de Investiación  (verDatallePropuestaNecesidad) ", ex);
    }
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarResumen() {

    try {

      if (propuestaSeleccionada != null && propuestaSeleccionada.getId() != null) {

        HashMap mapa = new HashMap();
        mapa.put("p_id_proyecto", propuestaSeleccionada.getId().intValue());

        byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte2.jasper");

        InputStream stream = new ByteArrayInputStream(bitesPdf);

        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, "2_FORMATO RESEÑA DE INVESTIGACIÓN.pdf");

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  //Setters y Getters
  public String getPalabraClave() {
    return palabraClave;
  }

  public void setPalabraClave(String palabraClave) {
    this.palabraClave = palabraClave;
  }

  public Long getIdAreaCienciaTecnologiaSeleccionada() {
    return idAreaCienciaTecnologiaSeleccionada;
  }

  public void setIdAreaCienciaTecnologiaSeleccionada(Long idAreaCienciaTecnologiaSeleccionada) {
    this.idAreaCienciaTecnologiaSeleccionada = idAreaCienciaTecnologiaSeleccionada;
  }

  public List<SelectItem> getListaItemsAreaCienciaYTecnologia() {
    return listaItemsAreaCienciaYTecnologia;
  }

  public void setListaItemsAreaCienciaYTecnologia(List<SelectItem> listaItemsAreaCienciaYTecnologia) {
    this.listaItemsAreaCienciaYTecnologia = listaItemsAreaCienciaYTecnologia;
  }

  public Long getIdLineaSeleccionada() {
    return idLineaSeleccionada;
  }

  public void setIdLineaSeleccionada(Long idLineaSeleccionada) {
    this.idLineaSeleccionada = idLineaSeleccionada;
  }

  public List<SelectItem> getListaItemsLineaCienciaYTecnologia() {
    return listaItemsLineaCienciaYTecnologia;
  }

  public void setListaItemsLineaCienciaYTecnologia(List<SelectItem> listaItemsLineaCienciaYTecnologia) {
    this.listaItemsLineaCienciaYTecnologia = listaItemsLineaCienciaYTecnologia;
  }

  public Long getIdEstadoImplementacion() {
    return idEstadoImplementacion;
  }

  public void setIdEstadoImplementacion(Long idEstadoImplementacion) {
    this.idEstadoImplementacion = idEstadoImplementacion;
  }

  public List<SelectItem> getListaItemsEstadosImplementacion() {
    return listaItemsEstadosImplementacion;
  }

  public void setListaItemsEstadosImplementacion(List<SelectItem> listaItemsEstadosImplementacion) {
    this.listaItemsEstadosImplementacion = listaItemsEstadosImplementacion;
  }

  public Date getFechaPresentacionInicial() {
    return fechaPresentacionInicial;
  }

  public void setFechaPresentacionInicial(Date fechaPresentacionInicial) {
    this.fechaPresentacionInicial = fechaPresentacionInicial;
  }

  public Date getFechaPresentacionFinal() {
    return fechaPresentacionFinal;
  }

  public void setFechaPresentacionFinal(Date fechaPresentacionFinal) {
    this.fechaPresentacionFinal = fechaPresentacionFinal;
  }

  public void noSeleccionPropuesta(UnselectEvent event) {
    setPropuestaSeleccionada(null);
  }

  public ReseniaInvestigacion getReseniaInvetigacion() {
    return reseniaInvetigacion;
  }

  public void setReseniaInvetigacion(ReseniaInvestigacion reseniaInvetigacion) {
    this.reseniaInvetigacion = reseniaInvetigacion;
  }

  public ListGenericDataModel<ProyectoDTO> getListaProyectosEncontradosModel() {
    return listaProyectosEncontradosModel;
  }

  public void setListaProyectosEncontradosModel(ListGenericDataModel<ProyectoDTO> listaProyectosEncontradosModel) {
    this.listaProyectosEncontradosModel = listaProyectosEncontradosModel;
  }

  public ProyectoDTO getPropuestaSeleccionada() {
    return propuestaSeleccionada;
  }

  public void setPropuestaSeleccionada(ProyectoDTO propuestaSeleccionada) {
    this.propuestaSeleccionada = propuestaSeleccionada;
  }

}
