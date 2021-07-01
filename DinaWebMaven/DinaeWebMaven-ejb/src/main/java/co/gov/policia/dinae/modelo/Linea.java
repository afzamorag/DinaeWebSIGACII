package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "LINEA")
@NamedQueries({
  @NamedQuery(name = "Linea.findAll", query = "SELECT l FROM Linea l WHERE l.estado = 'ACTIVO' ORDER BY l.nombre ASC "),
  @NamedQuery(name = "Linea.findAllPorArea", query = "SELECT l FROM Linea l WHERE l.areaCienciaTecnologia.idAreaCienciaTecnologia = :idAreaCienciaTecnologia AND l.estado = 'ACTIVO' ORDER BY l.nombre ASC "),
  @NamedQuery(name = "Linea.findAllNoEstado", query = "SELECT l FROM Linea l WHERE l.estado IN ('ACTIVO','INACTIVO') ORDER BY l.nombre, l.areaCienciaTecnologia.nombre ASC ")})
public class Linea implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Linea_seq_gen")
  @SequenceGenerator(name = "Linea_seq_gen", sequenceName = "SEC_LINEA", allocationSize = 1)
  @Column(name = "ID_LINEA")
  private Long idLinea;

  @Column(name = "NOMBRE")
  private String nombre;

  @JoinColumn(name = "ID_AREA_CIENCIA_TECNOLOGIA", referencedColumnName = "ID_AREA_CIENCIA_TECNOLOGIA")
  @ManyToOne(fetch = FetchType.LAZY)
  private AreaCienciaTecnologia areaCienciaTecnologia;

  @OneToMany(mappedBy = "linea", fetch = FetchType.LAZY)
  private List<PropuestaNecesidad> propuestaNecesidadList;

  @Column(name = "ESTADO", length = 20, nullable = false)
  @Pattern(regexp = "ACTIVO|INACTIVO")
  private String estado;

  public Linea() {
  }

  public Linea(Long idLinea) {
    this.idLinea = idLinea;
  }

  public Linea(Long idLinea, String nombre) {
    this.idLinea = idLinea;
    this.nombre = nombre;
  }

  public Long getIdLinea() {
    return idLinea;
  }

  public void setIdLinea(Long idLinea) {
    this.idLinea = idLinea;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public AreaCienciaTecnologia getAreaCienciaTecnologia() {
    return areaCienciaTecnologia;
  }

  public void setAreaCienciaTecnologia(AreaCienciaTecnologia areaCienciaTecnologia) {
    this.areaCienciaTecnologia = areaCienciaTecnologia;
  }

  public List<PropuestaNecesidad> getPropuestaNecesidadList() {
    return propuestaNecesidadList;
  }

  public void setPropuestaNecesidadList(List<PropuestaNecesidad> propuestaNecesidadList) {
    this.propuestaNecesidadList = propuestaNecesidadList;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idLinea != null ? idLinea.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Linea)) {
      return false;
    }
    Linea other = (Linea) object;
    if ((this.idLinea == null && other.idLinea != null) || (this.idLinea != null && !this.idLinea.equals(other.idLinea))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.Linea[ idLinea=" + idLinea + " ]";
  }

}
