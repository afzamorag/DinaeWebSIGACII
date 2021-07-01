package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.interfaces.IPropuestaNecesidadCuNe07;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author juan
 */
@javax.inject.Named(value = "cuNe7PropuestaNecesidadFaces")
@javax.enterprise.context.SessionScoped
public class CuNe7PropuestaNecesidadFaces extends JSFUtils implements Serializable {

  private String accionUrlRegresar;
  private String accionUrlRegresarCopiar;
  private PropuestaNecesidad propuestaNecesidad;
  private boolean mostrarBtnCopiar;
  private IPropuestaNecesidadCuNe07 iPropuestaNecesidadCuNe07;
  private StreamedContent downloadContentProperty;

  public CuNe7PropuestaNecesidadFaces() {
  }

  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  /**
   * Metodo que invoca el caso de uso CU NE 07
   *
   * @param propuestaNecesidad
   * @param accionUrlRegresar
   * @param mostrarBtnCopiar
   * @param iPropuestaNecesidadCuNe07
   * @param accionUrlRegresarCopiar
   */
  public void fijarPropuesta(PropuestaNecesidad propuestaNecesidad, String accionUrlRegresar, boolean mostrarBtnCopiar, IPropuestaNecesidadCuNe07 iPropuestaNecesidadCuNe07, String accionUrlRegresarCopiar) {
    this.propuestaNecesidad = propuestaNecesidad;
    this.accionUrlRegresar = accionUrlRegresar;
    this.mostrarBtnCopiar = mostrarBtnCopiar;
    this.iPropuestaNecesidadCuNe07 = iPropuestaNecesidadCuNe07;
    this.accionUrlRegresarCopiar = accionUrlRegresarCopiar;
  }

  public String regresar() {
    return accionUrlRegresar;
  }

  /**
   *
   * @return
   */
  public String copiarPropuestaNecesidad() {

    if (iPropuestaNecesidadCuNe07 != null) {
      iPropuestaNecesidadCuNe07.setearPropuestaNecesidadCuNe07(propuestaNecesidad);
    }
    return accionUrlRegresarCopiar;

  }

  public boolean isMostrarBtnCopiar() {
    return mostrarBtnCopiar;
  }

  public StreamedContent getDownloadContentProperty() {

    try {

      String name = keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte") + propuestaNecesidad.getNombreArchivoFisico();

      InputStream stream = new FileInputStream(name);
      String contentType = "application/octet-stream";

      if (propuestaNecesidad.getTipoContenidoArchivo() != null && propuestaNecesidad.getTipoContenidoArchivo().trim().length() > 0) {
        contentType = propuestaNecesidad.getTipoContenidoArchivo();
      }

      downloadContentProperty = new DefaultStreamedContent(stream, contentType, propuestaNecesidad.getNombreArchivo());

      return downloadContentProperty;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-07 - (getDownloadContentProperty)", e);
    }
    return null;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return propuestaNecesidad != null && propuestaNecesidad.getNombreArchivo() != null && propuestaNecesidad.getNombreArchivoFisico() != null;
  }
}
