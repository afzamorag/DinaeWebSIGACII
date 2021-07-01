package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Édder Javier Peña Barranco
 * @since 2013/11/22
 */
public class FuenteFinancieraDTO implements Serializable {

  /**
   * Identificador de la fuente financiera
   */
  private Long idFuenteProyecto;
  /**
   * Nombre de la fuente financiera
   */
  private String nombreFuente;
  /**
   * Identificador del tipo de fuente
   */
  private Long idTipoFuente;
  /**
   * Nombre del tipo de fuente
   */
  private String tipoFuente;
  /**
   * Fecha de registro de la fuente financiera
   */
  private Date fechaRegistro;
  /**
   * Determina si la fuente es una fuente base o no
   */
  private String fuenteBase;

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public String getNombreFuente() {
    return nombreFuente;
  }

  public void setNombreFuente(String nombreFuente) {
    this.nombreFuente = nombreFuente;
  }

  public Long getIdTipoFuente() {
    return idTipoFuente;
  }

  public void setIdTipoFuente(Long idTipoFuente) {
    this.idTipoFuente = idTipoFuente;
  }

  public String getTipoFuente() {
    return tipoFuente;
  }

  public void setTipoFuente(String tipoFuente) {
    this.tipoFuente = tipoFuente;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getFuenteBase() {
    return fuenteBase;
  }

  public void setFuenteBase(String fuenteBase) {
    this.fuenteBase = fuenteBase;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 89 * hash + (this.idFuenteProyecto != null ? this.idFuenteProyecto.hashCode() : 0);
    hash = 89 * hash + (this.nombreFuente != null ? this.nombreFuente.hashCode() : 0);
    hash = 89 * hash + (this.idTipoFuente != null ? this.idTipoFuente.hashCode() : 0);
    hash = 89 * hash + (this.tipoFuente != null ? this.tipoFuente.hashCode() : 0);
    hash = 89 * hash + (this.fechaRegistro != null ? this.fechaRegistro.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final FuenteFinancieraDTO other = (FuenteFinancieraDTO) obj;
    if (this.idFuenteProyecto != other.idFuenteProyecto && (this.idFuenteProyecto == null || !this.idFuenteProyecto.equals(other.idFuenteProyecto))) {
      return false;
    }
    if ((this.nombreFuente == null) ? (other.nombreFuente != null) : !this.nombreFuente.equals(other.nombreFuente)) {
      return false;
    }
    if (this.idTipoFuente != other.idTipoFuente && (this.idTipoFuente == null || !this.idTipoFuente.equals(other.idTipoFuente))) {
      return false;
    }
    if ((this.tipoFuente == null) ? (other.tipoFuente != null) : !this.tipoFuente.equals(other.tipoFuente)) {
      return false;
    }
    if (this.fechaRegistro != other.fechaRegistro && (this.fechaRegistro == null || !this.fechaRegistro.equals(other.fechaRegistro))) {
      return false;
    }
    return true;
  }

}
