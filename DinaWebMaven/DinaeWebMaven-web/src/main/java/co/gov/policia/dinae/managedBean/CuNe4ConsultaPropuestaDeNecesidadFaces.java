package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadCuNe07;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuNe4ConsultaPropuestaDeNecesidad")
@javax.enterprise.context.SessionScoped
public class CuNe4ConsultaPropuestaDeNecesidadFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuNe7PropuestaNecesidadFaces cuNe7PropuestaNecesidadFaces;

  @EJB
  private ILineaLocal iLineaLocal;

  @EJB
  private IAreaCienciaTecnologiaLocal iAreaCienciaTecnologia;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IPropuestaNecesidadLocal iPropuestaNecesidadLocal;

  private List<SelectItem> listaLineaItem;
  private List<SelectItem> listaAreaCienciaTecnologiaItem;
  private List<SelectItem> tiposEstadoPropuestas;
  private Long areaCienciaTecnologiaSeleccionada;
  private Long lineaSeleccionada;
  private String tema;
  private String estadoPropuesta;
  private List<SelectItem> tiposGradosFuncionarios;
  private String gradoFuncionario;
  private List<SelectItem> unidadesPoliciales;
  private Long idUnidadPolicial;
  private Date fechaInicio;
  private Date fechaFinal;
  private List<PropuestaNecesidad> listadoPropuestas;
  private ListGenericDataModel listaPropuestaModel;
  private PropuestaNecesidad propuestaSeleccionada;

  private List<Long> listaIdUnidades;

  private String accionUrlRegresar;
  private boolean mostrarBtnCopiar;
  private IPropuestaNecesidadCuNe07 iPropuestaNecesidadCuNe07;

  /**
   *
   * @return
   */
  public String initReturnCU() {
    init();
    return navigationFaces.redirectCuNe04ConsultarPropuestaNecesidad();
  }

  @javax.annotation.PostConstruct
  public void init() {

    listaPropuestaModel = null;
    listaIdUnidades = null;
    listaLineaItem = null;
    listaAreaCienciaTecnologiaItem = null;
    tiposEstadoPropuestas = null;
    areaCienciaTecnologiaSeleccionada = null;
    lineaSeleccionada = null;
    tema = null;
    estadoPropuesta = null;
    tiposGradosFuncionarios = null;
    gradoFuncionario = null;
    unidadesPoliciales = null;
    idUnidadPolicial = null;
    fechaInicio = null;
    fechaFinal = null;
    listadoPropuestas = null;
    propuestaSeleccionada = null;
    accionUrlRegresar = null;
    mostrarBtnCopiar = false;
  }

  public List<SelectItem> getListaAreaCienciaTecnologiaItem() {
    if (listaAreaCienciaTecnologiaItem == null) {
      try {
        //CARGAMOS LA LISTA DE AREAS
        listaAreaCienciaTecnologiaItem = UtilidadesItem.getListaSel(iAreaCienciaTecnologia.getAreaCienciaTecnologias(), "idAreaCienciaTecnologia", "nombre");
      } catch (Exception e) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-04 Consulta de propuestas de necesidades de investigación (getListaAreaCienciaTecnologiaItem) ", e);
      }
    }
    return listaAreaCienciaTecnologiaItem;
  }

  public void setListaAreaCienciaTecnologiaItem(List<SelectItem> listaAreaCienciaTecnologiaItem) {

    this.listaAreaCienciaTecnologiaItem = listaAreaCienciaTecnologiaItem;
  }

  public Long getAreaCienciaTecnologiaSeleccionada() {
    return areaCienciaTecnologiaSeleccionada;
  }

  public void setAreaCienciaTecnologiaSeleccionada(Long areaCienciaTecnologiaSeleccionada) {
    this.areaCienciaTecnologiaSeleccionada = areaCienciaTecnologiaSeleccionada;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public String getTema() {
    return tema;
  }

  public String getEstadoPropuesta() {
    return estadoPropuesta;
  }

  public void setEstadoPropuesta(String estadoPropuesta) {
    this.estadoPropuesta = estadoPropuesta;
  }

  public List<SelectItem> getTiposEstadoPropuestas() {
    try {
      if (this.tiposEstadoPropuestas == null) {
        this.tiposEstadoPropuestas = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_PROPUESTA_NECESIDAD), "idConstantes", "valor");

      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-04 Consulta de propuestas de necesidades de investigación (getTiposEstadoPropuestas) ", ex);
    }
    return tiposEstadoPropuestas;
  }

  public void setTiposEstadoPropuestas(List<SelectItem> tiposEstadoPropuestas) {
    this.tiposEstadoPropuestas = tiposEstadoPropuestas;
  }

  public List<SelectItem> getTiposGradosFuncionarios() {
    try {
      if (tiposGradosFuncionarios == null) {
        tiposGradosFuncionarios = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_GRADO_FUNCIONARIO), "codigo", "valor");
      }

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuNe4ConsultaPropuestaDeNecesidadFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return tiposGradosFuncionarios;
  }

  public void setTiposGradosFuncionarios(List<SelectItem> tiposGradosFuncionarios) {
    this.tiposGradosFuncionarios = tiposGradosFuncionarios;
  }

  public String getGradoFuncionario() {
    return gradoFuncionario;
  }

  public void setGradoFuncionario(String gradoFuncionario) {
    this.gradoFuncionario = gradoFuncionario;
  }

  public List<SelectItem> getUnidadesPoliciales() {
    try {
      if (unidadesPoliciales == null) {
        unidadesPoliciales = UtilidadesItem.getListaSel(iUnidadPolicialLocal.getUnidadPolicial(), "idUnidadPolicial", "siglaFisicaYnombreUnidad");
      }

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuNe4ConsultaPropuestaDeNecesidadFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return unidadesPoliciales;
  }

  public void setUnidadesPoliciales(List<SelectItem> unidadesPoliciales) {
    this.unidadesPoliciales = unidadesPoliciales;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFinal() {
    return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
  }

  public List<PropuestaNecesidad> getListadoPropuestas() {
    return listadoPropuestas;
  }

  public void setListadoPropuestas(List<PropuestaNecesidad> listadoPropuestas) {
    this.listadoPropuestas = listadoPropuestas;
  }

  public ListGenericDataModel getListaPropuestaModel() {
    return listaPropuestaModel;
  }

  public void setListaPropuestaModel(ListGenericDataModel listaPropuestaModel) {
    this.listaPropuestaModel = listaPropuestaModel;
  }

  public void setAccionUrlRegresar(String accionUrlRegresar) {
    this.accionUrlRegresar = accionUrlRegresar;
  }

  public String getAccionUrlRegresar() {
    return accionUrlRegresar;
  }

  public List<Long> getListaIdUnidades() {
    return listaIdUnidades;
  }

  private Boolean fechasValidasFormulario() {
    if (this.fechaInicio != null || this.fechaFinal != null) {
      if (this.fechaInicio == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_4_error_fecha_inicio"));
        return false;
      } else if (this.fechaFinal == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_4_error_fecha_final"));
        return false;
      } else {
        int comparacion = DatesUtils.compareDate(this.fechaInicio, this.fechaFinal);
        if (comparacion > 0) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_ne_4_error_fechas"));
          return false;
        }
      }
    }
    return true;
  }

  /**
   *
   */
  public void limpiarParametrosBusqueda() {

    areaCienciaTecnologiaSeleccionada = null;
    lineaSeleccionada = null;
    tema = null;
    estadoPropuesta = null;
    tiposGradosFuncionarios = null;
    gradoFuncionario = null;
    unidadesPoliciales = null;
    idUnidadPolicial = null;
    fechaInicio = null;
    fechaFinal = null;
    listadoPropuestas = new ArrayList<PropuestaNecesidad>();
    listaPropuestaModel = new ListGenericDataModel(listadoPropuestas);
    propuestaSeleccionada = null;

  }

  /**
   * Método que carga las lienas dependiendo del Aera Seleccionada
   */
  public void handleAreaCienciaTecnologiaSeleccionadaChange() {

    if (areaCienciaTecnologiaSeleccionada != null) {

      try {

        listaLineaItem = UtilidadesItem.getListaSel(iLineaLocal.getLineasPorArea(areaCienciaTecnologiaSeleccionada),
                "idLinea",
                "nombre");

      } catch (Exception e) {

        adicionaMensajeError("No se ha podido recuperar las propuestas");
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-04 Consulta de propuestas de necesidades de investigación (handleAreaCienciaTecnologiaSeleccionadaChange) ", e);
      }
    } else {

      listaLineaItem = new ArrayList<SelectItem>();
    }
  }

  /**
   * Este metodo es invocado desde el CU NE 02
   *
   * @param accionUrlRegresar
   * @param mostrarBtnCopiar
   * @param iPropuestaNecesidadCuNe07
   */
  public void fijarPropuesta(String accionUrlRegresar, boolean mostrarBtnCopiar, IPropuestaNecesidadCuNe07 iPropuestaNecesidadCuNe07) {

    this.accionUrlRegresar = accionUrlRegresar;
    this.mostrarBtnCopiar = mostrarBtnCopiar;
    this.iPropuestaNecesidadCuNe07 = iPropuestaNecesidadCuNe07;
    listaPropuestaModel = new ListGenericDataModel(new ArrayList());
  }

  /**
   * Este metodo es invocado desde el CU NE 02
   *
   * @param accionUrlRegresar
   * @param mostrarBtnCopiar
   * @param iPropuestaNecesidadCuNe07
   */
  public void fijarPropuesta(String accionUrlRegresar, boolean mostrarBtnCopiar, IPropuestaNecesidadCuNe07 iPropuestaNecesidadCuNe07, List<Long> listaIdUnidades) {

    this.accionUrlRegresar = accionUrlRegresar;
    this.mostrarBtnCopiar = mostrarBtnCopiar;
    this.iPropuestaNecesidadCuNe07 = iPropuestaNecesidadCuNe07;
    this.listaPropuestaModel = new ListGenericDataModel(new ArrayList());
    this.listaIdUnidades = listaIdUnidades;
  }

  /**
   *
   * @param event
   */
  public void verDatallePropuestaNecesidad(SelectEvent event) {
    try {
      if (this.propuestaSeleccionada != null) {

        cuNe7PropuestaNecesidadFaces.fijarPropuesta(
                propuestaSeleccionada,
                navigationFaces.redirectCuNe04ConsultarPropuestaNecesidad(),
                mostrarBtnCopiar,
                iPropuestaNecesidadCuNe07,
                accionUrlRegresar);

        navigationFaces.redirectFacesCuNe07PropuestaNecesidad();

      }
    } catch (IOException ex) {
      adicionaMensajeError("No se ha podido recuperar las propuestas");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-04 Consulta de propuestas de necesidades de investigación (verDatallePropuestaNecesidad) ", ex);
    }
  }

  /**
   * Método que realiza la busqueda de propuestas asociadas a los filtros seleccionados
   */
  public void buscarPropuesta() {
    try {

      if (this.fechasValidasFormulario()) {

        listadoPropuestas = iPropuestaNecesidadLocal.consultaPropuestaNecesidades(areaCienciaTecnologiaSeleccionada, lineaSeleccionada, idUnidadPolicial, tema, DatesUtils.setTime(fechaInicio, 0, 0, 0), DatesUtils.setTime(fechaFinal, 23, 59, 59), listaIdUnidades);
        listaPropuestaModel = new ListGenericDataModel(listadoPropuestas);

      } else {

        listaPropuestaModel = new ListGenericDataModel(new ArrayList());
      }

      if (listaPropuestaModel.getRowCount() == 0) {

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_4_lbl_parametros_busqueda_vacia"));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError("No se ha podido recuperar las propuestas");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-04 Consulta de propuestas de necesidades de investigación (buscarPropuesta) ", ex);
    }
  }

  public PropuestaNecesidad getPropuestaSeleccionada() {
    return propuestaSeleccionada;
  }

  public void setPropuestaSeleccionada(PropuestaNecesidad propuestaSeleccionada) {
    this.propuestaSeleccionada = propuestaSeleccionada;
  }

  public void noSeleccionPropuesta(UnselectEvent event) {
    propuestaSeleccionada = null;
  }

  public List<SelectItem> getListaLineaItem() {
    return listaLineaItem;
  }

  public void setListaLineaItem(List<SelectItem> listaLineaItem) {
    this.listaLineaItem = listaLineaItem;
  }

  public Long getLineaSeleccionada() {
    return lineaSeleccionada;
  }

  public void setLineaSeleccionada(Long lineaSeleccionada) {
    this.lineaSeleccionada = lineaSeleccionada;
  }

}
