package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "GRUPO_INVESTIGACION")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = "GrupoInvestigacion.findAll", query = "SELECT g FROM GrupoInvestigacion g WHERE g.estado = 'ACTIVO' ORDER BY g.descripcion ASC"),
  @NamedQuery(name = "GrupoInvestigacion.findAllEstados", query = "SELECT g FROM GrupoInvestigacion g ORDER BY g.descripcion ASC")})

@XmlRootElement

public class GrupoInvestigacion implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GrupoInvestigacion_seq_gen")
  @SequenceGenerator(name = "GrupoInvestigacion_seq_gen", sequenceName = "SEC_GRUPO_INVESTIGACION", allocationSize = 1)
  @Column(name = "ID_GRUPO_INVESTIGACION")
  private Long idGrupoInvestigacion;

  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "DESCRIPCION")
  private String descripcion;

  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "CODIGO_GRUPO")
  private String codigoGrupo;

  @Column(name = "FECHA_REGISTRO_GRUPO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistroGrupo;

  @Column(name = "ESTADO", length = 20, nullable = false)
  @Pattern(regexp = "ACTIVO|INACTIVO")
  private String estado;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoInvestigacion")
  private List<GrupoInvestigacionProyecto> grupoInvestProyectoList;

  public GrupoInvestigacion() {
  }

  public GrupoInvestigacion(Long idGrupoInvestigacion) {
    this.idGrupoInvestigacion = idGrupoInvestigacion;
  }

  public Long getIdGrupoInvestigacion() {
    return idGrupoInvestigacion;
  }

  public void setIdGrupoInvestigacion(Long idGrupoInvestigacion) {
    this.idGrupoInvestigacion = idGrupoInvestigacion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getCodigoGrupo() {
    return codigoGrupo;
  }

  public void setCodigoGrupo(String codigoGrupo) {
    this.codigoGrupo = codigoGrupo;
  }

  public Date getFechaRegistroGrupo() {
    return fechaRegistroGrupo;
  }

  public void setFechaRegistroGrupo(Date fechaRegistroGrupo) {
    this.fechaRegistroGrupo = fechaRegistroGrupo;
  }

    @XmlTransient
  public List<GrupoInvestigacionProyecto> getGrupoInvestProyectoList() {
    return grupoInvestProyectoList;
  }

  public void setGrupoInvestProyectoList(List<GrupoInvestigacionProyecto> grupoInvestProyectoList) {
    this.grupoInvestProyectoList = grupoInvestProyectoList;
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
    hash += (getIdGrupoInvestigacion() != null ? getIdGrupoInvestigacion().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof GrupoInvestigacion)) {
      return false;
    }
    GrupoInvestigacion other = (GrupoInvestigacion) object;
    if ((this.getIdGrupoInvestigacion() == null && this.getIdGrupoInvestigacion() != null) || (this.getIdGrupoInvestigacion() != null && !this.idGrupoInvestigacion.equals(other.idGrupoInvestigacion))) {
      return false;
    }
    return true;
  }

    @Override
    public String toString() {
        return "GrupoInvestigacion{" + "idGrupoInvestigacion=" + idGrupoInvestigacion + ", descripcion=" + descripcion + ", codigoGrupo=" + codigoGrupo + ", fechaRegistroGrupo=" + fechaRegistroGrupo + ", estado=" + estado + ", grupoInvestProyectoList=" + grupoInvestProyectoList + '}';
    }



}
