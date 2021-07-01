package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "HISTORIAL_COMPROMISO")
@NamedQueries({
  @NamedQuery(name = "HistorialCompromiso.findAll", query = "SELECT h FROM HistorialCompromiso h")})
public class HistorialCompromiso implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_HISTORIAL_COMPROMISO")
  private BigDecimal idHistorialCompromiso;
  @Basic(optional = false)
  @NotNull
  @Column(name = "NUEVA_FECHA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date nuevaFecha;
  @Column(name = "FECHA_MODIFICACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;

  public HistorialCompromiso() {
  }

  public HistorialCompromiso(BigDecimal idHistorialCompromiso) {
    this.idHistorialCompromiso = idHistorialCompromiso;
  }

  public HistorialCompromiso(BigDecimal idHistorialCompromiso, Date nuevaFecha) {
    this.idHistorialCompromiso = idHistorialCompromiso;
    this.nuevaFecha = nuevaFecha;
  }

  public BigDecimal getIdHistorialCompromiso() {
    return idHistorialCompromiso;
  }

  public void setIdHistorialCompromiso(BigDecimal idHistorialCompromiso) {
    this.idHistorialCompromiso = idHistorialCompromiso;
  }

  public Date getNuevaFecha() {
    return nuevaFecha;
  }

  public void setNuevaFecha(Date nuevaFecha) {
    this.nuevaFecha = nuevaFecha;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idHistorialCompromiso != null ? idHistorialCompromiso.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof HistorialCompromiso)) {
      return false;
    }
    HistorialCompromiso other = (HistorialCompromiso) object;
    if ((this.idHistorialCompromiso == null && other.idHistorialCompromiso != null) || (this.idHistorialCompromiso != null && !this.idHistorialCompromiso.equals(other.idHistorialCompromiso))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.HistorialCompromiso[ idHistorialCompromiso=" + idHistorialCompromiso + " ]";
  }

}
