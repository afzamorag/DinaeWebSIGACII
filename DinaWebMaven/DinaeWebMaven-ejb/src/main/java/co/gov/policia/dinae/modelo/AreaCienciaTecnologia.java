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
import javax.validation.constraints.Pattern;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "AREA_CIENCIA_TECNOLOGIA")
@NamedQueries({
  @NamedQuery(name = "AreaCienciaTecnologia.findAll", query = "SELECT a FROM AreaCienciaTecnologia a WHERE a.estado = 'ACTIVO' ORDER BY a.nombre ASC "),
  @NamedQuery(name = "AreaCienciaTecnologia.findAllNoEstado", query = "SELECT a FROM AreaCienciaTecnologia a WHERE a.estado IN ('ACTIVO','INACTIVO') ORDER BY a.nombre ASC")})
public class AreaCienciaTecnologia implements Serializable {

  @OneToMany(mappedBy = "areaCienciaTecnologia", fetch = FetchType.LAZY)
  private List<Linea> lineaList;

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AreaCienciaTecnologia_seq_gen")
  @SequenceGenerator(name = "AreaCienciaTecnologia_seq_gen", sequenceName = "SEC_AREA_CIENCIA_TECNOLOGIA", allocationSize = 1)
  @Column(name = "ID_AREA_CIENCIA_TECNOLOGIA")
  private Long idAreaCienciaTecnologia;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "ESTADO", length = 20, nullable = false)
  @Pattern(regexp = "ACTIVO|INACTIVO")
  private String estado;

  public AreaCienciaTecnologia() {
  }

  public AreaCienciaTecnologia(Long idAreaCienciaTecnologia) {
    this.idAreaCienciaTecnologia = idAreaCienciaTecnologia;
  }

  public AreaCienciaTecnologia(Long idAreaCienciaTecnologia, String nombre) {
    this.idAreaCienciaTecnologia = idAreaCienciaTecnologia;
    this.nombre = nombre;
  }

  public Long getIdAreaCienciaTecnologia() {
    return idAreaCienciaTecnologia;
  }

  public void setIdAreaCienciaTecnologia(Long idAreaCienciaTecnologia) {
    this.idAreaCienciaTecnologia = idAreaCienciaTecnologia;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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
    hash += (idAreaCienciaTecnologia != null ? idAreaCienciaTecnologia.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof AreaCienciaTecnologia)) {
      return false;
    }
    AreaCienciaTecnologia other = (AreaCienciaTecnologia) object;
    if ((this.idAreaCienciaTecnologia == null && other.idAreaCienciaTecnologia != null) || (this.idAreaCienciaTecnologia != null && !this.idAreaCienciaTecnologia.equals(other.idAreaCienciaTecnologia))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.AreaCienciaTecnologia[ idAreaCienciaTecnologia=" + idAreaCienciaTecnologia + " ]";
  }

  public List<Linea> getLineaList() {
    return lineaList;
  }

  public void setLineaList(List<Linea> lineaList) {
    this.lineaList = lineaList;
  }

}
