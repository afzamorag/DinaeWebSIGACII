package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.util.JSFUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cargueArchivoFaces")
@javax.enterprise.context.RequestScoped
public class CargueArchivoFaces extends JSFUtils implements Serializable {

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      String nombreArchivoSubido = event.getFile().getFileName();

      File archivo = new File(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general").concat(nombreArchivoSubido));

      if (archivo.exists()) {
        try {

          archivo.delete();

        } catch (SecurityException e) {
          adicionaMensajeError("No se puede eliminar el archivo ".concat(archivo.getAbsolutePath()).concat(e.getMessage() == null ? "" : e.getMessage()));

        }
      }

      almacenarArchivoFisico(event);

    } catch (Exception e) {
      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación( handleFileUpload )", e);
    }
  }

  /**
   *
   * @param eventArchivoSubido
   */
  private void almacenarArchivoFisico(FileUploadEvent eventArchivoSubido) {

    try {

      if (eventArchivoSubido != null) {

        String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();

        InputStream inputStream = eventArchivoSubido.getFile().getInputstream();
        File directorioFinal = new File(keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general"));
        OutputStream out = new FileOutputStream(new File(directorioFinal, nombreArchivoOriginal));
        int read;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
        inputStream.close();
        out.flush();
        out.close();

        adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(nombreArchivoOriginal));
      }

    } catch (Exception e) {

      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error", e);
    }

  }
}
