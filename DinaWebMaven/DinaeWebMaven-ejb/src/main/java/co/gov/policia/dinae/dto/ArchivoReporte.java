package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class ArchivoReporte implements Serializable {

  private byte[] arregloBytes;

  private String nombreArchivo;

  public ArchivoReporte(byte[] arregloBytes, String nombreArchivo) {
    this.arregloBytes = arregloBytes;
    this.nombreArchivo = nombreArchivo;
  }

  public byte[] getArregloBytes() {
    return arregloBytes;
  }

  public void setArregloBytes(byte[] arregloBytes) {
    this.arregloBytes = arregloBytes;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

}
