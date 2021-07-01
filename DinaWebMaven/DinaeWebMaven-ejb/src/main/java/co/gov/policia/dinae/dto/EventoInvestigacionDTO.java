package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class EventoInvestigacionDTO implements Serializable {

  private Long idEventoInvestigacion;
  private String nombre;
  private Date fechaInicio;
  private Date fechaFin;
  private String valor;

  /**
   *
   */
  public EventoInvestigacionDTO() {
  }

  /**
   *
   * @param idEventoInvestigacion
   * @param nombre
   * @param fechaInicio
   * @param fechaFin
   * @param valor
   */
  public EventoInvestigacionDTO(Long idEventoInvestigacion, String nombre, Date fechaInicio, Date fechaFin, String valor) {
    this.idEventoInvestigacion = idEventoInvestigacion;
    this.nombre = nombre;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.valor = valor;
  }

  public Long getIdEventoInvestigacion() {
    return idEventoInvestigacion;
  }

  public void setIdEventoInvestigacion(Long idEventoInvestigacion) {
    this.idEventoInvestigacion = idEventoInvestigacion;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }
}
