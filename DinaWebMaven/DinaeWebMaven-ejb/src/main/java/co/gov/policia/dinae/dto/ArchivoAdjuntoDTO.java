package co.gov.policia.dinae.dto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author cguzman
 */
public class ArchivoAdjuntoDTO implements Serializable {

  private String _nombreArchivo;

  private String _tipoArchivo;

  private String _ruta;

  private File _archivo;

  private String _nombreTemporal;

  private byte[] _contenidoArchivo;

  public ArchivoAdjuntoDTO(byte[] contenidoArchivo, String nombreArchivo) {
    try {
      cargarPropiedades(contenidoArchivo, nombreArchivo);
    } catch (IOException ex) {
      Logger.getLogger(ArchivoAdjuntoDTO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public ArchivoAdjuntoDTO(String nombreArchivo, String ruta) {
    try {
      cargarPropiedades(nombreArchivo, ruta);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(ArchivoAdjuntoDTO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param contenidoArchivo
   * @param nombreArchivo
   * <p>
   * nombre del archivo con extension. </p>
   *
   */
  private void cargarPropiedades(byte[] contenido, String nombreArchivo) throws IOException {

    File fileTemp;
    this._tipoArchivo = nombreArchivo.split("\\.")[1];
    fileTemp = File.createTempFile(nombreArchivo, "." + this._tipoArchivo);

    FileUtils.writeByteArrayToFile(fileTemp, contenido);

    this._archivo = fileTemp;
    this._contenidoArchivo = contenido;
    this._nombreArchivo = nombreArchivo;
    this._nombreTemporal = fileTemp.getName();
    this._ruta = fileTemp.getPath();

  }

  /**
   *
   * @param nombreArchivo
   * @param ruta
   * @throws FileNotFoundException
   */
  private void cargarPropiedades(String nombreArchivo, String ruta) throws FileNotFoundException {

    this._nombreArchivo = nombreArchivo;
    this._tipoArchivo = nombreArchivo.split("\\.")[1];
    this._ruta = ruta;
    this._archivo = new File(ruta + "/" + nombreArchivo);
    this._contenidoArchivo = tomarBytesArchivo();

  }

  /**
   *
   * @return @throws FileNotFoundException
   */
  private byte[] tomarBytesArchivo() throws FileNotFoundException {

    FileInputStream fis = new FileInputStream(this._archivo);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];

    try {
      for (int readNum; (readNum = fis.read(buf)) != -1;) {
        bos.write(buf, 0, readNum);
      }
    } catch (IOException ex) {
      Logger.getLogger(ArchivoAdjuntoDTO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return bos.toByteArray();
  }

  public String getNombreArchivo() {
    return _nombreArchivo;
  }

  public void setNombreArchivo(String _nombreArchivo) {
    this._nombreArchivo = _nombreArchivo;
  }

  public String getTipoArchivo() {
    return _tipoArchivo;
  }

  public void setTipoArchivo(String _tipoArchivo) {
    this._tipoArchivo = _tipoArchivo;
  }

  public String getRuta() {
    return _ruta;
  }

  public void setRuta(String _ruta) {
    this._ruta = _ruta;
  }

  public byte[] getContenidoArchivo() {
    return _contenidoArchivo;
  }

  public void setContenidoArchivo(byte[] _contenidoArchivo) {
    this._contenidoArchivo = _contenidoArchivo;
  }

  public File getArchivo() {
    return _archivo;
  }

  public void setArchivo(File _archivo) {
    this._archivo = _archivo;
  }

  public String getNombreTemporal() {
    return _nombreTemporal;
  }

  public void setNombreTemporal(String _nombreTemporal) {
    this._nombreTemporal = _nombreTemporal;
  }

}
