package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class HistorialEstadoProyectosMigradosDTO implements Serializable {

  private Date fecha;
  private Long idEstado;
  private String observacion;

  /**
   *
   */
  public HistorialEstadoProyectosMigradosDTO() {
  }

  /**
   *
   * @param fecha
   * @param idEstado
   * @param observacion
   */
  public HistorialEstadoProyectosMigradosDTO(Date fecha, Long idEstado, String observacion) {
    this.fecha = fecha;
    this.idEstado = idEstado;
    this.observacion = observacion;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Long getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(Long idEstado) {
    this.idEstado = idEstado;
  }

  /**
   *
   * @return
   */
  public String getDescripcionEstado() {

    //1=86, 2=110, 3=111, 4=112, 5=112, 6=186
    /*
        1	PROPUESTA
        2	DESARROLLO
        3	TERMINADO
        4	SUSTENTADO
        5	PUBLICADO
        6	NO REPORTADO
     */
    if (Long.valueOf(86).equals(idEstado)) {
      return "PROPUESTA";
    }
    if (Long.valueOf(110).equals(idEstado)) {
      return "DESARROLLO";
    }
    if (Long.valueOf(111).equals(idEstado)) {
      return "TERMINADO";
    }
    if (Long.valueOf(112).equals(idEstado)) {
      return "SUSTENTADO";
    }
    if (Long.valueOf(186).equals(idEstado)) {
      return "NO REPORTADO";
    }

    return "";

  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

}
