/*
 * SoftStudio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.util.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@logike.co>
 * @version: 1.0
 * @since: 1.0
 */
public class FileUtil {

  private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

  /**
   *
   * @param pathname
   * @return
   * @throws IOException
   */
  public static InputStream findFile(String pathname) throws IOException {
    LOG.trace("method: findFile(({});", pathname);
    if (pathname == null) {
      throw new IllegalArgumentException();
    }
    File file = new File(pathname);
    InputStream is = new FileInputStream(file);
    return is;
  }

  /**
   *
   * @param pathname
   * @param is
   * @throws IOException
   */
  public static void createFile(String pathname, InputStream is) throws IOException {
    LOG.trace("method: persistFile(({});", pathname);
    if (pathname == null) {
      throw new IllegalArgumentException();
    }
    File f = new File(pathname);
    // Make sure the file or directory exists and isn't write protected
    if (f.exists()) {
      LOG.info("the exist file will be overwritten.");
    }
    OutputStream fos = new FileOutputStream(f);
    IOUtils.copy(is, fos);
  }

  /**
   *
   * @param pathname
   * @throws IOException
   */
  public static void deleteFile(String pathname) throws IOException {
    LOG.trace("method: removeFile({});", pathname);
    if (pathname == null) {
      throw new IllegalArgumentException();
    }
    File file = new File(pathname);
    // Make sure the file or directory exists and isn't write protected
    if (!file.exists()) {
      throw new FileNotFoundException("Delete: no such file: " + pathname);
    }
    if (file.delete()) {
      LOG.debug("El archivo {} ha sido eliminado", file.getAbsolutePath());
    } else {
      LOG.debug("El archivo {} no se ha eliminado", file.getAbsolutePath());
      throw new IOException("El archivo no se ha eliminado");
    }
  }

  /**
   *
   * @param prefix
   * @param suffix
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static File createTmpFile(String prefix, String suffix, InputStream inputStream) throws IOException {
    LOG.trace("method: <<createTmpFile>>");
    File tmpFile = null;
    try {
      tmpFile = File.createTempFile(prefix, suffix);
      LOG.debug("absolute path file: " + tmpFile.getAbsolutePath());
      LOG.debug("canonical path file: " + tmpFile.getCanonicalPath());
      LOG.debug("path file: " + tmpFile.getPath());
      LOG.debug("name file: " + tmpFile.getName());
      LOG.debug("parent file: " + tmpFile.getParent());
      tmpFile.deleteOnExit();
      OutputStream fileOutputStream = new FileOutputStream(tmpFile);
      IOUtils.copy(inputStream, fileOutputStream);
    } catch (IOException ex) {
      LOG.error("Error en <<processImage>> ->> mensaje ->> {}", ex.getMessage());
    }
    return tmpFile;
  }

  /**
   *
   * @param directorypath
   * @param prefix
   * @param suffix
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static File createTmpFile(String directorypath, String prefix, String suffix, InputStream inputStream) throws IOException {
    LOG.trace("method: <<createTmpFile>>");
    File tmpFile = null;
    try {
      File directory = new File(directorypath);
      if (directory.isDirectory()) {
        if (directory.canWrite()) {
          tmpFile = File.createTempFile(prefix, "." + suffix, directory);
          LOG.debug("absolute path file: " + tmpFile.getAbsolutePath());
          LOG.debug("canonical path file: " + tmpFile.getCanonicalPath());
          LOG.debug("path file: " + tmpFile.getPath());
          LOG.debug("name file: " + tmpFile.getName());
          LOG.debug("parent file: " + tmpFile.getParent());
          tmpFile.deleteOnExit();
          OutputStream fileOutputStream = new FileOutputStream(tmpFile);
          IOUtils.copy(inputStream, fileOutputStream);
        } else {
          IOException ex = new IOException("No hay permisos de escritura.");
          throw ex;
        }
      }
    } catch (IOException ex) {
      LOG.error("Error en <<processImage>> ->> mensaje ->> {}", ex.getMessage());
      throw ex;
    }
    return tmpFile;
  }

  /**
   *
   * @param path
   * @param name
   * @param content
   */
  public static void createFileFromString(String path, String name, String content) {
    LOG.debug("method: createFileFromString(path, name, content);");
    try (PrintWriter out = new PrintWriter(name)) {
      out.println(content);
    } catch (FileNotFoundException ex) {
      LOG.error("Error in method: createFileFromString()", ex);
    }
  }

  /**
   * @author Diego Poveda
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static String inputStreamToBase64(InputStream inputStream) throws IOException {
    if (inputStream == null) {
      return null;
    }
    String base64 = new String(Base64.encodeBase64(IOUtils.toByteArray(inputStream)));
    return base64;
  }

  /**
   * @author Diego Poveda
   * @param base64
   * @return
   * @throws IOException
   */
  public static InputStream base64ToInputStream(String base64) throws IOException {
    if (base64 == null) {
      return null;
    }
    InputStream is = new ByteArrayInputStream(Base64.decodeBase64(base64.getBytes()));
    return is;
  }

  /**
   *
   * @param baseName
   * @return
   */
  public static final ResourceBundle getBundle(String baseName) {
    LOG.debug("method: getBundle({});", baseName);
    if (baseName == null) {
      throw new IllegalArgumentException("baseName is null");
    }
    try {
      ResourceBundle bundle = ResourceBundle.getBundle(baseName);
      return bundle;
    } catch (MissingResourceException ex) {
      LOG.error("method: getBundle(); => message {}", ex.getMessage());
      throw ex;
    }
  }
}
