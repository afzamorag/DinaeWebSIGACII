package co.gov.policia.dinae.managedBean;

import static co.gov.policia.dinae.constantes.IConstantes.TIPO_CRITERIO_EVALUACION_PROPUESTA_FINANCIACION;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_CRITERIO_EVALUACION_PROPUESTA_ENSAYO_CRITICO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_CRITERIO_EVALUACION_PROYECTO;
import static co.gov.policia.dinae.constantes.IConstantes.ESTADO_ACTIVO_CRITERIO_EVALUACION;
import static co.gov.policia.dinae.constantes.IConstantes.ESTADO_INACTIVO_CRITERIO_EVALUACION;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICriterioEvaluacionLocal;
import co.gov.policia.dinae.modelo.CriterioEvaluacion;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Named(value = "cuAd03AdminCriteriosEvaluacion")
@SessionScoped
public class CuAd03AdminCriteriosEvaluacionFaces extends JSFUtils implements Serializable {

  @EJB
  private ICriterioEvaluacionLocal iCriterio;

  private String criterioEvaluacion;
  private List<SelectItem> listaItemsCriteriosEvaluacion;
  private boolean mostrarCriteriosEvaluacion;
  private String nombreCriterioEvaluacion;
  private String descCriterioEvaluacion;
  private int valorCriterioEvaluacion;
  private String estadoCriterioEvaluacion;
  private boolean mostrarDatosCriterio;
  private boolean mostrarValor = false;
  private List<SelectItem> listaItemsEstados;
  private List<CriterioEvaluacion> listaCriteriosEvaluacion;
  private CriterioEvaluacion criterioSeleccionado;
  private boolean habilitarBotonAgregar;

  private boolean mostrarBotonActualizar;

  private boolean mostrarBotonGuardar;

  private void init() {
    cargarListaTiposDeCriterios();
    cargarListaEstadosCriterio();
  }

  public String initReturnCU() {
    init();
    return navigationFaces.redirectCuAd03CriteriosEvaluacion();
  }

  private void cargarListaTiposDeCriterios() {

    listaItemsCriteriosEvaluacion = new ArrayList<SelectItem>();
    listaItemsCriteriosEvaluacion.add(new SelectItem(TIPO_CRITERIO_EVALUACION_PROPUESTA_FINANCIACION,
            "Convocatoria de financiación"));
    listaItemsCriteriosEvaluacion.add(new SelectItem(TIPO_CRITERIO_EVALUACION_PROPUESTA_ENSAYO_CRITICO,
            "Convocatoria de ensayo"));
    listaItemsCriteriosEvaluacion.add(new SelectItem(TIPO_CRITERIO_EVALUACION_PROYECTO,
            "Evaluación de proyecto"));
  }

  private void cargarListaEstadosCriterio() {
    listaItemsEstados = new ArrayList<SelectItem>();
    listaItemsEstados.add(new SelectItem(ESTADO_ACTIVO_CRITERIO_EVALUACION, "Activo"));
    listaItemsEstados.add(new SelectItem(ESTADO_INACTIVO_CRITERIO_EVALUACION, "Inactivo"));
  }

  public void agregarCriterioEvaluacion(ActionEvent event) {
    if (criterioEvaluacion != null || !criterioEvaluacion.equals("")) {
      mostrarCriteriosEvaluacion = true;
      if (criterioEvaluacion.equals(TIPO_CRITERIO_EVALUACION_PROPUESTA_FINANCIACION)
              || criterioEvaluacion.equals(TIPO_CRITERIO_EVALUACION_PROPUESTA_ENSAYO_CRITICO)) {
        mostrarDatosCriterio = true;
        mostrarBotonGuardar = true;
        mostrarValor = true;
        mostrarBotonActualizar = false;
      } else if (criterioEvaluacion.equals(TIPO_CRITERIO_EVALUACION_PROYECTO)) {
        mostrarDatosCriterio = true;
        mostrarValor = false;
        mostrarBotonGuardar = true;
        mostrarBotonActualizar = false;
      } else {
        mostrarDatosCriterio = false;
        mostrarValor = false;
      }
    } else {
      mostrarCriteriosEvaluacion = false;
    }
  }

  public String guardarCriterioEvaluacion() {
    int total = valorCriterioEvaluacion;
    for (CriterioEvaluacion ce : listaCriteriosEvaluacion) {
      if (criterioSeleccionado != null && criterioSeleccionado.getIdCriterioEvaluacion() != null) {
        if (criterioSeleccionado.getIdCriterioEvaluacion().equals(ce.getIdCriterioEvaluacion())) {
          break;
        }
      }
      total += ce.getValor().intValue();
    }
    if (total > 100) {
      adicionaMensajeError("Los valores para los criterios del mismo tipo no deben ser mayores a 100");
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE,
              "Los valores para los criterios del mismo tipo no deben ser mayores a 100");
      return null;
    }
    CriterioEvaluacion criterio = new CriterioEvaluacion();
    if (criterioSeleccionado != null && criterioSeleccionado.getIdCriterioEvaluacion() != null) {
      criterio.setIdCriterioEvaluacion(criterioSeleccionado.getIdCriterioEvaluacion());
    }

    criterio.setNombreCriterio(nombreCriterioEvaluacion);
    criterio.setDescripcionCriterio(descCriterioEvaluacion);
    criterio.setValor(new BigDecimal(valorCriterioEvaluacion));
    criterio.setEstado(estadoCriterioEvaluacion);
    criterio.setTipo(criterioEvaluacion);
    criterio.setEvaluacion(BigDecimal.ZERO);
    try {
      iCriterio.guardarCriterioEvaluacion(criterio);
      adicionaMensajeInformativo("Se guardó el criterio de evaluación con éxito");
      listaCriteriosEvaluacion = iCriterio.getListaCriterioEvaluacionPorTipo(criterioEvaluacion);
      borrarFormulario();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }

  public String borrarFormulario() {
    criterioSeleccionado = null;
    mostrarDatosCriterio = false;
    nombreCriterioEvaluacion = null;
    descCriterioEvaluacion = null;
    valorCriterioEvaluacion = 0;
    estadoCriterioEvaluacion = "-1";
    mostrarBotonActualizar = false;
    mostrarBotonGuardar = false;
    return "";
  }

  public void validarCriterioEvaluacion() {
    try {
      listaCriteriosEvaluacion = iCriterio.getListaCriterioEvaluacionPorTipo(criterioEvaluacion);
      nombreCriterioEvaluacion = null;
      descCriterioEvaluacion = null;
      valorCriterioEvaluacion = 0;
      estadoCriterioEvaluacion = "-1";

      habilitarBotonAgregar = !"-1".equals(criterioEvaluacion);

    } catch (JpaDinaeException ex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }
  }

  public void seleccionarCriterio() {

    try {

      if (criterioSeleccionado != null) {

        nombreCriterioEvaluacion = criterioSeleccionado.getNombreCriterio();
        descCriterioEvaluacion = criterioSeleccionado.getDescripcionCriterio();
        valorCriterioEvaluacion = criterioSeleccionado.getValor().intValue();
        estadoCriterioEvaluacion = criterioSeleccionado.getEstado();
        mostrarDatosCriterio = true;
        mostrarBotonActualizar = true;
        mostrarBotonGuardar = false;

        mostrarValor = !TIPO_CRITERIO_EVALUACION_PROYECTO.equals(criterioSeleccionado.getTipo());

      } else {
        nombreCriterioEvaluacion = null;
        descCriterioEvaluacion = null;
        valorCriterioEvaluacion = 0;
        estadoCriterioEvaluacion = null;
      }

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

    }

  }

  /**
   *
   * @return
   */
  public String getCriterioEvaluacion() {
    return criterioEvaluacion;
  }

  public void setCriterioEvaluacion(String criterioEvaluacion) {
    this.criterioEvaluacion = criterioEvaluacion;
  }

  public List<SelectItem> getListaItemsCriteriosEvaluacion() {
    return listaItemsCriteriosEvaluacion;
  }

  public void setListaItemsCriteriosEvaluacion(List<SelectItem> listaItemsCriteriosEvaluacion) {
    this.listaItemsCriteriosEvaluacion = listaItemsCriteriosEvaluacion;
  }

  public boolean isMostrarCriteriosEvaluacion() {
    return mostrarCriteriosEvaluacion;
  }

  public void setMostrarCriteriosEvaluacion(boolean mostrarCriteriosEvaluacion) {
    this.mostrarCriteriosEvaluacion = mostrarCriteriosEvaluacion;
  }

  public String getNombreCriterioEvaluacion() {
    return nombreCriterioEvaluacion;
  }

  public void setNombreCriterioEvaluacion(String nombreCriterioEvaluacion) {
    this.nombreCriterioEvaluacion = nombreCriterioEvaluacion;
  }

  public String getDescCriterioEvaluacion() {
    return descCriterioEvaluacion;
  }

  public void setDescCriterioEvaluacion(String descCriterioEvaluacion) {
    this.descCriterioEvaluacion = descCriterioEvaluacion;
  }

  public int getValorCriterioEvaluacion() {
    return valorCriterioEvaluacion;
  }

  public void setValorCriterioEvaluacion(int valorCriterioEvaluacion) {
    this.valorCriterioEvaluacion = valorCriterioEvaluacion;
  }

  public String getEstadoCriterioEvaluacion() {
    return estadoCriterioEvaluacion;
  }

  public void setEstadoCriterioEvaluacion(String estadoCriterioEvaluacion) {
    this.estadoCriterioEvaluacion = estadoCriterioEvaluacion;
  }

  public boolean isMostrarDatosCriterio() {
    return mostrarDatosCriterio;
  }

  public void setMostrarDatosCriterio(boolean mostrarDatosCriterio) {
    this.mostrarDatosCriterio = mostrarDatosCriterio;
  }

  public boolean isMostrarValor() {
    return mostrarValor;
  }

  public void setMostrarValor(boolean mostrarValor) {
    this.mostrarValor = mostrarValor;
  }

  public List<SelectItem> getListaItemsEstados() {
    return listaItemsEstados;
  }

  public void setListaItemsEstados(List<SelectItem> listaItemsEstados) {
    this.listaItemsEstados = listaItemsEstados;
  }

  public List<CriterioEvaluacion> getListaCriteriosEvaluacion() {
    return listaCriteriosEvaluacion;
  }

  public void setListaCriteriosEvaluacion(List<CriterioEvaluacion> listaCriteriosEvaluacion) {
    this.listaCriteriosEvaluacion = listaCriteriosEvaluacion;
  }

  public CriterioEvaluacion getCriterioSeleccionado() {
    return criterioSeleccionado;
  }

  public void setCriterioSeleccionado(CriterioEvaluacion criterioSeleccionado) {
    this.criterioSeleccionado = criterioSeleccionado;
  }

  public boolean isHabilitarBotonAgregar() {
    return habilitarBotonAgregar;
  }

  public void setHabilitarBotonAgregar(boolean habilitarBotonAgregar) {
    this.habilitarBotonAgregar = habilitarBotonAgregar;
  }

  public boolean isMostrarBotonActualizar() {
    return mostrarBotonActualizar;
  }

  public void setMostrarBotonActualizar(boolean mostrarBotonActualizar) {
    this.mostrarBotonActualizar = mostrarBotonActualizar;
  }

  public boolean isMostrarBotonGuardar() {
    return mostrarBotonGuardar;
  }

  public void setMostrarBotonGuardar(boolean mostrarBotonGuardar) {
    this.mostrarBotonGuardar = mostrarBotonGuardar;
  }

}
