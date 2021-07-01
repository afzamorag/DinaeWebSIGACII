package co.gov.policia.dinae.util;

import co.gov.policia.dinae.dto.StringUtils;
import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.managedBean.ValidacionesGeneralesFaces;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public abstract class JSFUtils implements Serializable {

  @javax.inject.Inject
  protected LoginFaces loginFaces;

  @javax.inject.Inject
  protected NavigationFaces navigationFaces;

  @javax.inject.Inject
  protected ValidacionesGeneralesFaces validacionesGeneralesFaces;

  protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();

  public FacesContext getFacesContext() {
    return FacesContext.getCurrentInstance();
  }

  /**
   * Metodo que muestra un mensaje de Error al usuario
   *
   * @param msg
   */
  public void adicionaMensajeError(String msg) {

    FacesContext facesContext = getFacesContext();
    if (facesContext == null) {
      return;
    }
    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", msg));
  }

  /**
   * Metodo que muestra un mensaje de Advertencia al usuario
   *
   * @param msg
   */
  public void adicionaMensajeAdvertencia(String msg) {
    FacesContext facesContext = getFacesContext();
    if (facesContext == null) {
      return;
    }
    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: ", msg));
  }

  /**
   * Metodo que muestra un mensaje Informativo al usuario
   *
   * @param msg
   */
  public void adicionaMensajeInformativo(String msg) {
    FacesContext facesContext = getFacesContext();
    if (facesContext == null) {
      return;
    }
    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información: ", msg));
  }

  /**
   *
   * @param rutaLocal
   * @param is
   * @param nombreArchivoFinal
   * @throws Exception
   */
  public static void copiarArchivoRutaFisica(String rutaLocal, InputStream is, String nombreArchivoFinal) throws Exception {

    File directorioFinal = new File(rutaLocal);

    if (!directorioFinal.exists()) {
      throw new Exception("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(rutaLocal));
    }

    if (directorioFinal.isFile()) {
      throw new Exception("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(rutaLocal));
    }

    if (!directorioFinal.canWrite()) {
      throw new Exception("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(rutaLocal));
    }

    InputStream inputStream = is;
    OutputStream out = new FileOutputStream(new File(directorioFinal, nombreArchivoFinal));
    int read = 0;
    byte[] bytes = new byte[1024];
    while ((read = inputStream.read(bytes)) != -1) {
      out.write(bytes, 0, read);
    }
    inputStream.close();
    out.flush();
    out.close();

  }

  /**
   *
   * @param cadena
   * @param maximoCaracterPorLinea
   * @return
   */
  public String formatearCadenaCaracteresPorLineaFormatoHTML(String cadena, int maximoCaracterPorLinea) {

    return StringUtils.formatearCadenaCaracteresPorLineaFormatoHTML(cadena, maximoCaracterPorLinea);

  }

  /**
   *
   * @return
   */
  public Map<String, String> getParameterMap() {
    return getFacesContext().getExternalContext().getRequestParameterMap();
  }

  /**
   *
   * @return
   */
  public ELContext getELContext() {
    return getFacesContext().getELContext();
  }

  /**
   *
   * @return
   */
  public ExpressionFactory getExpressionFactory() {

    Application application = getFacesContext().getApplication();
    ExpressionFactory expressionFactory = application.getExpressionFactory();

    return expressionFactory;
  }

  /**
   *
   * @return
   */
  public Map<String, Object> getSessionMap() {
    return getFacesContext().getExternalContext().getSessionMap();
  }

  private static ZipOutputStream zipOutputStream;

  public static byte[] crearZIP(String... listaArchivos) throws Exception {

    if (listaArchivos == null || listaArchivos.length == 0) {
      return null;
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    zipOutputStream = new ZipOutputStream(baos);

    for (String unArchivo : listaArchivos) {
      archivosRecursivos(new File(unArchivo));
    }

    zipOutputStream.close();

    return baos.toByteArray();
  }

  public static byte[] crearZIP(List<File> listaArchivos) throws Exception {

    if (listaArchivos == null || listaArchivos.isEmpty()) {
      return null;
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    zipOutputStream = new ZipOutputStream(baos);

    for (File unArchivo : listaArchivos) {
      archivosRecursivos(unArchivo);
    }

    zipOutputStream.close();

    return baos.toByteArray();
  }

  private static void archivosRecursivos(File archivoOdirectorio) throws IOException, FileNotFoundException {

    if (archivoOdirectorio.isDirectory()) {

      String[] fileNames = archivoOdirectorio.list();
      if (fileNames != null) {

        for (String unNombreArchivo : fileNames) {

          archivosRecursivos(new File(archivoOdirectorio, unNombreArchivo));
        }
      }
    } else {

      byte[] buf = new byte[1024];
      int len;

      ZipEntry zipEntry = new ZipEntry(archivoOdirectorio.getName());

      FileInputStream fin = new FileInputStream(archivoOdirectorio);
      BufferedInputStream in = new BufferedInputStream(fin);
      zipOutputStream.putNextEntry(zipEntry);

      while ((len = in.read(buf)) >= 0) {
        zipOutputStream.write(buf, 0, len);
      }

      in.close();

      zipOutputStream.closeEntry();
    }
  }

  public HttpSession getHttpSession() {
    HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    return request.getSession();
  }

  /**
   *
   * @param cadena
   * @return
   */
  public String encriptarMensajeArreglo(String cadena) {

    if (cadena == null) {
      return null;
    }
    char array[] = cadena.toCharArray();

    for (int i = 0; i < array.length; i++) {
      array[i] = (char) (array[i] + (char) 5);
    }

    return String.valueOf(array);

  }

  /**
   *
   * @param cadena
   * @return
   */
  public String desEncriptarMensajeArreglo(String cadena) {

    if (cadena == null) {
      return null;
    }
    char arrayD[] = cadena.toCharArray();

    for (int i = 0; i < arrayD.length; i++) {
      arrayD[i] = (char) (arrayD[i] - (char) 5);
    }
    return String.valueOf(arrayD);

  }

  /**
   *
   * @return
   */
  public HttpServletRequest getRequest() {
    HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    return request;
  }

}
