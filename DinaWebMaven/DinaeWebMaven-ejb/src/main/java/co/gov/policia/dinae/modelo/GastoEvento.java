package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "GASTO_EVENTO")
@NamedQueries({
  @NamedQuery(name = "GastoEvento.findAll", query = "SELECT g FROM GastoEvento g")})
public class GastoEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_GASTO_EVENTO")
  private BigDecimal idGastoEvento;
  @JoinColumn(name = "ID_EVENTO_PROYECTO", referencedColumnName = "ID_EVENTO_PROYECTO")
  @ManyToOne(optional = false)
  private EventoProyecto eventoProyecto;

  public GastoEvento() {
  }

  public GastoEvento(BigDecimal idGastoEvento) {
    this.idGastoEvento = idGastoEvento;
  }

  public BigDecimal getIdGastoEvento() {
    return idGastoEvento;
  }

  public void setIdGastoEvento(BigDecimal idGastoEvento) {
    this.idGastoEvento = idGastoEvento;
  }

  public EventoProyecto getEventoProyecto() {
    return eventoProyecto;
  }

  public void setEventoProyecto(EventoProyecto eventoProyecto) {
    this.eventoProyecto = eventoProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idGastoEvento != null ? idGastoEvento.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof GastoEvento)) {
      return false;
    }
    GastoEvento other = (GastoEvento) object;
    if ((this.idGastoEvento == null && other.idGastoEvento != null) || (this.idGastoEvento != null && !this.idGastoEvento.equals(other.idGastoEvento))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.GastoEvento[ idGastoEvento=" + idGastoEvento + " ]";
  }

}
