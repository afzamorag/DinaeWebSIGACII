package co.gov.policia.dinae.util;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class PendientesView implements Comparable<PendientesView>, Serializable {

  private String actividadPendiente;

  private String acercaDe;

  private Date fechaLimite;

  private String fechaLimiteFormateada;

  private int diasRestantes;

  private String styleClass;

  /**
   *
   * Full constructor
   *
   * @param actividadPendiente
   * @param acercaDe
   * @param fechaLimite
   * @param fechaLimiteFormateada
   * @param diasRestantes
   */
  public PendientesView(String actividadPendiente, String acercaDe, Date fechaLimite, String fechaLimiteFormateada, int diasRestantes) {
    this.actividadPendiente = actividadPendiente;
    this.acercaDe = acercaDe;
    this.fechaLimite = fechaLimite;
    this.fechaLimiteFormateada = fechaLimiteFormateada;
    this.diasRestantes = diasRestantes;
  }

  /**
   *
   * Minimal Constructor
   *
   * @param actividadPendiente
   * @param acercaDe
   * @param fechaLimite
   * @param diasRestantes
   */
  public PendientesView(String actividadPendiente, String acercaDe, Date fechaLimite, int diasRestantes) {
    this.actividadPendiente = actividadPendiente;
    this.acercaDe = acercaDe;
    this.fechaLimite = fechaLimite;
    this.diasRestantes = diasRestantes;
  }

  /**
   * Default constructor
   */
  public PendientesView() {
  }

  public String getActividadPendiente() {
    return actividadPendiente;
  }

  public void setActividadPendiente(String actividadPendiente) {
    this.actividadPendiente = actividadPendiente;
  }

  public String getAcercaDe() {
    return acercaDe;
  }

  public void setAcercaDe(String acercaDe) {
    this.acercaDe = acercaDe;
  }

  public Date getFechaLimite() {
    return fechaLimite;
  }

  public void setFechaLimite(Date fechaLimite) {
    this.fechaLimite = fechaLimite;
  }

  public String getFechaLimiteFormateada() {
    return fechaLimiteFormateada;
  }

  public void setFechaLimiteFormateada(String fechaLimiteFormateada) {
    this.fechaLimiteFormateada = fechaLimiteFormateada;
  }

  public int getDiasRestantes() {
    return diasRestantes;
  }

  public void setDiasRestantes(int diasRestantes) {
    this.diasRestantes = diasRestantes;
  }

  public String getStyleClass() {
    return styleClass;
  }

  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }

  @Override
  public int compareTo(PendientesView o) {

    if (fechaLimite == null || o.getFechaLimite() == null) {
      return -1;
    }
    return fechaLimite.compareTo(o.getFechaLimite());

  }

}
