package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "TIPO_UNIDAD")
@NamedQueries({
  @NamedQuery(name = "TipoUnidad.findAll", query = "SELECT t FROM TipoUnidad t WHERE t.activo = 'Y'"),
  @NamedQuery(name = "TipoUnidad.findById", query = "SELECT t FROM TipoUnidad t WHERE t.idTipoUnidad = :idTipoUnidad ")
})
public class TipoUnidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoUnidad_seq_gen")
  @SequenceGenerator(name = "TipoUnidad_seq_gen", sequenceName = "SEC_TIPO_UNIDAD")
  @Column(name = "ID_TIPO_UNIDAD")
  private Long idTipoUnidad;

  @Column(name = "NOMBRE")
  private String nombre;

  @OneToMany(mappedBy = "tipoUnidad", fetch = FetchType.LAZY)
  private List<UnidadPolicial> unidadPolicialList;

  @OneToMany(mappedBy = "tipoUnidad", fetch = FetchType.LAZY)
  private List<TipoUnidadPeriodo> tipoUnidadPeriodoList;

  @Column(name = "ACTIVO")
  private String activo;

  public TipoUnidad() {
  }

  public TipoUnidad(Long idTipoUnidad) {
    this.idTipoUnidad = idTipoUnidad;
  }

  public Long getIdTipoUnidad() {
    return idTipoUnidad;
  }

  public void setIdTipoUnidad(Long idTipoUnidad) {
    this.idTipoUnidad = idTipoUnidad;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<UnidadPolicial> getUnidadPolicialList() {
    return unidadPolicialList;
  }

  public void setUnidadPolicialList(List<UnidadPolicial> unidadPolicialList) {
    this.unidadPolicialList = unidadPolicialList;
  }

  public List<TipoUnidadPeriodo> getTipoUnidadPeriodoList() {
    return tipoUnidadPeriodoList;
  }

  public void setTipoUnidadPeriodoList(List<TipoUnidadPeriodo> tipoUnidadPeriodoList) {
    this.tipoUnidadPeriodoList = tipoUnidadPeriodoList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idTipoUnidad != null ? idTipoUnidad.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TipoUnidad)) {
      return false;
    }
    TipoUnidad other = (TipoUnidad) object;
    if ((this.idTipoUnidad == null && other.idTipoUnidad != null) || (this.idTipoUnidad != null && !this.idTipoUnidad.equals(other.idTipoUnidad))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.TipoUnidad[ idTipoUnidad=" + idTipoUnidad + " ]";
  }
}
