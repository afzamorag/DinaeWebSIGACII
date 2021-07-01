package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "TIPO_UNIDAD_PERIODO")
@NamedQueries({
  @NamedQuery(name = "TipoUnidadPeriodo.findAll", query = "SELECT t FROM TipoUnidadPeriodo t"),
  @NamedQuery(name = "TipoUnidadPeriodo.findByPeriodo", query = "SELECT t FROM TipoUnidadPeriodo t WHERE t.periodo.idPeriodo = :idPeriodo")
})
public class TipoUnidadPeriodo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoUnidadPeriodo_seq_gen")
  @SequenceGenerator(name = "TipoUnidadPeriodo_seq_gen", sequenceName = "SEC_TIPO_UNIDAD_PERIODO", allocationSize = 1)
  @Column(name = "ID_TIPO_UNIDAD_PERIODO")
  private Long idTipoUnidadPeriodo;

  @JoinColumn(name = "ID_TIPO_UNIDAD", referencedColumnName = "ID_TIPO_UNIDAD")
  @ManyToOne(fetch = FetchType.LAZY)
  private TipoUnidad tipoUnidad;

  @JoinColumn(name = "ID_PERIODO", referencedColumnName = "ID_PERIODO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Periodo periodo;

  public TipoUnidadPeriodo() {
  }

  public TipoUnidadPeriodo(Long idTipoUnidadPeriodo) {
    this.idTipoUnidadPeriodo = idTipoUnidadPeriodo;
  }

  public TipoUnidadPeriodo(TipoUnidad tipoUnidad, Periodo periodo) {

    this.tipoUnidad = tipoUnidad;
    this.periodo = periodo;
  }

  public Long getIdTipoUnidadPeriodo() {
    return idTipoUnidadPeriodo;
  }

  public void setIdTipoUnidadPeriodo(Long idTipoUnidadPeriodo) {
    this.idTipoUnidadPeriodo = idTipoUnidadPeriodo;
  }

  public TipoUnidad getTipoUnidad() {
    return tipoUnidad;
  }

  public void setTipoUnidad(TipoUnidad tipoUnidad) {
    this.tipoUnidad = tipoUnidad;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idTipoUnidadPeriodo != null ? idTipoUnidadPeriodo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TipoUnidadPeriodo)) {
      return false;
    }
    TipoUnidadPeriodo other = (TipoUnidadPeriodo) object;
    if ((this.idTipoUnidadPeriodo == null && other.idTipoUnidadPeriodo != null) || (this.idTipoUnidadPeriodo != null && !this.idTipoUnidadPeriodo.equals(other.idTipoUnidadPeriodo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.TipoUnidadPeriodo[ idTipoUnidadPeriodo=" + idTipoUnidadPeriodo + " ]";
  }

}
