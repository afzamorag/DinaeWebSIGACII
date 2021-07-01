package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class EventoSemilleroProyectoDTO implements Serializable {

  private String dia;
  private Date horaInicio;
  private Date horaFin;

  public EventoSemilleroProyectoDTO() {
  }

  /**
   *
   * @param dia
   * @param horaInicio
   * @param horaFin
   */
  public EventoSemilleroProyectoDTO(String dia, Date horaInicio, Date horaFin) {
    this.dia = dia;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
  }

  public String getDia() {
    return dia;
  }

  public void setDia(String dia) {
    this.dia = dia;
  }

  public Date getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(Date horaInicio) {
    this.horaInicio = horaInicio;
  }

  public Date getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(Date horaFin) {
    this.horaFin = horaFin;
  }
}
