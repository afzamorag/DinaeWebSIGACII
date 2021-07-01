package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.modelo.Constantes;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class HorarioReunionesDTO implements Serializable {

  private Long idHorarioReunion;

  private String diaSemana;

  private Date horaInicio;

  private Date horaFin;

  private String horaInicioString;

  private String horaFinString;

  private boolean esValidoHoraInicio;

  private boolean esValidoHoraFin;

  private Long idDiaSemana;

  private final SimpleDateFormat _format = new SimpleDateFormat("HH:mm");

  public HorarioReunionesDTO() {
  }

  public HorarioReunionesDTO(Constantes diasSemana) {
    this.idDiaSemana = diasSemana.getIdConstantes();
    this.diaSemana = diasSemana.getValor();
    this.horaInicio = null;
    this.horaFin = null;
    this.horaInicioString = null;
    this.horaFinString = null;
    this.esValidoHoraInicio = true;
    this.esValidoHoraFin = true;
  }

  public Long getIdHorarioReunion() {
    return idHorarioReunion;
  }

  public void setIdHorarioReunion(Long idHorarioReunion) {
    this.idHorarioReunion = idHorarioReunion;
  }

  public String getDiaSemana() {
    return diaSemana;
  }

  public void setDiaSemana(String diaSemana) {
    this.diaSemana = diaSemana;
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

  public String getHoraInicioString() {
    if (this.horaInicio != null) {
      horaInicioString = _format.format(this.horaInicio);
    }

    return horaInicioString;
  }

  public void setHoraInicioString(String horaInicioString) {
    this.horaInicioString = horaInicioString;
  }

  public String getHoraFinString() {
    if (this.horaFin != null) {
      horaFinString = _format.format(this.horaFin);
    }

    return horaFinString;
  }

  public void setHoraFinString(String horaFinString) {
    this.horaFinString = horaFinString;
  }

  public boolean isEsValidoHoraInicio() {
    return esValidoHoraInicio;
  }

  public void setEsValidoHoraInicio(boolean esValidoHoraInicio) {
    this.esValidoHoraInicio = esValidoHoraInicio;
  }

  public boolean isEsValidoHoraFin() {
    return esValidoHoraFin;
  }

  public void setEsValidoHoraFin(boolean esValidoHoraFin) {
    this.esValidoHoraFin = esValidoHoraFin;
  }

  public Long getIdDiaSemana() {
    return idDiaSemana;
  }

  public void setIdDiaSemana(Long idDiaSemana) {
    this.idDiaSemana = idDiaSemana;
  }

}
