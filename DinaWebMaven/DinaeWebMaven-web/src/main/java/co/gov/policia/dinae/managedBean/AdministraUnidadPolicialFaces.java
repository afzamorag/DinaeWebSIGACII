package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.TipoUnidad;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "administraUnidadPolicialFaces")
@javax.enterprise.context.SessionScoped
public class AdministraUnidadPolicialFaces extends JSFUtils implements Serializable {

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  private ListGenericDataModel<UnidadPolicial> listaUnidadPolicial;
  private UnidadPolicial unidadPolicialSeleccionada;

  private String filtroBusqueda;

  int cantidadUnidadesActivasEscuelas, cantidadUnidadesActivasOtros, totalUnidadadesActivasEscuelasYotros;

  /**
   *
   */
  public void init() {

    totalUnidadadesActivasEscuelasYotros = 0;
    cantidadUnidadesActivasEscuelas = 0;
    cantidadUnidadesActivasOtros = 0;
    filtroBusqueda = null;
    listaUnidadPolicial = null;

  }

  public String limpiar() {

    init();

    return "/pages/secured/unidad/administra_unidad.xhtml?faces-redirect=true";

  }

  /**
   *
   * @return
   */
  public String cargarUnidades() {

    try {

      cargarListaUnidades();

      unidadPolicialSeleccionada = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error ", e);

    }

    return "/pages/secured/unidad/administra_unidad.xhtml?faces-redirect=true";

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaUnidades() throws Exception {

    listaUnidadPolicial = new ListGenericDataModel(iUnidadPolicialLocal.getTodasUnidadesActivasInactivas(filtroBusqueda));

    //CONTAMOS LAS ESCUELAS Y OTROS
    cantidadUnidadesActivasEscuelas = iUnidadPolicialLocal.cuentaUnidadesActivasPorTipo(IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS);

    cantidadUnidadesActivasOtros = iUnidadPolicialLocal.cuentaUnidadesActivasPorTipo(IConstantes.TIPO_UNIDAD_POLICIAL_OTROS);

    totalUnidadadesActivasEscuelasYotros = cantidadUnidadesActivasEscuelas + cantidadUnidadesActivasOtros;

  }

  public String regresar() {

    return cargarUnidades();

  }

  public String actualizarUnidad() {

    try {

      if (unidadPolicialSeleccionada.getTipoUnidad() != null && unidadPolicialSeleccionada.getTipoUnidad().getIdTipoUnidad() != null) {

        unidadPolicialSeleccionada.setTipoUnidad(new TipoUnidad(unidadPolicialSeleccionada.getTipoUnidad().getIdTipoUnidad()));

      }
      iUnidadPolicialLocal.atualizarUnidadPolicial(unidadPolicialSeleccionada);

      adicionaMensajeInformativo("Unidad policial actualizada correctamente..");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error ", e);

    }

    return null;
  }

  public void noSeleccionUnidad(UnselectEvent event) {

    unidadPolicialSeleccionada = null;

  }

  /**
   *
   * @param event
   */
  public void seleccionUnidad(SelectEvent event) {

    try {

      if (unidadPolicialSeleccionada == null) {
        return;
      }

      if (unidadPolicialSeleccionada.getTipoUnidad() == null) {

        unidadPolicialSeleccionada.setTipoUnidad(new TipoUnidad());

      }

      navigationFaces.redirectFacesCuGenerico("/pages/secured/unidad/actualiza_unidad.xhtml?faces-redirect=true");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error ", e);

    }
  }

  public ListGenericDataModel<UnidadPolicial> getListaUnidadPolicial() {
    return listaUnidadPolicial;
  }

  public void setListaUnidadPolicial(ListGenericDataModel<UnidadPolicial> listaUnidadPolicial) {
    this.listaUnidadPolicial = listaUnidadPolicial;
  }

  public UnidadPolicial getUnidadPolicialSeleccionada() {
    return unidadPolicialSeleccionada;
  }

  public void setUnidadPolicialSeleccionada(UnidadPolicial unidadPolicialSeleccionada) {
    this.unidadPolicialSeleccionada = unidadPolicialSeleccionada;
  }

  public String getFiltroBusqueda() {
    return filtroBusqueda;
  }

  public void setFiltroBusqueda(String filtroBusqueda) {
    this.filtroBusqueda = filtroBusqueda;
  }

  public int getCantidadUnidadesActivasEscuelas() {
    return cantidadUnidadesActivasEscuelas;
  }

  public void setCantidadUnidadesActivasEscuelas(int cantidadUnidadesActivasEscuelas) {
    this.cantidadUnidadesActivasEscuelas = cantidadUnidadesActivasEscuelas;
  }

  public int getCantidadUnidadesActivasOtros() {
    return cantidadUnidadesActivasOtros;
  }

  public void setCantidadUnidadesActivasOtros(int cantidadUnidadesActivasOtros) {
    this.cantidadUnidadesActivasOtros = cantidadUnidadesActivasOtros;
  }

  public int getTotalUnidadadesActivasEscuelasYotros() {
    return totalUnidadadesActivasEscuelasYotros;
  }

  public void setTotalUnidadadesActivasEscuelasYotros(int totalUnidadadesActivasEscuelasYotros) {
    this.totalUnidadadesActivasEscuelasYotros = totalUnidadadesActivasEscuelasYotros;
  }

}
