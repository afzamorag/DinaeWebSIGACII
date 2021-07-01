package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author RafaelGomez
 */
@Entity
@Table(name = "MODALIDAD_ASESORIA_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "ModalidadAsesoriaProyecto.findAll", query = "SELECT a FROM ModalidadAsesoriaProyecto a")
})
public class ModalidadAsesoriaProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ModalidadAsesoriaProyecto_seq_gen")
  @SequenceGenerator(name = "ModalidadAsesoriaProyecto_seq_gen", sequenceName = "SEC_MODALIDAD_ASESORIA_PROYE", allocationSize = 1)
  @Column(name = "ID_MODALIDAD_PROYECTO")
  private Long idModalidadAsesoriaProyecto;

  @JoinColumn(name = "ID_ASESORIA_PROYECTO", referencedColumnName = "ID_ASESORIA_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private AsesoriaProyecto asesoriaProyecto;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_TIPO_MODALIDAD", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.EAGER)
  private Constantes tipoModalidad;

  @NotNull
  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.DATE)
  private Date fechaRegistro;

  public ModalidadAsesoriaProyecto() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idModalidadAsesoriaProyecto != null ? idModalidadAsesoriaProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ModalidadAsesoriaProyecto)) {
      return false;
    }
    ModalidadAsesoriaProyecto other = (ModalidadAsesoriaProyecto) object;
    if ((this.idModalidadAsesoriaProyecto == null && other.idModalidadAsesoriaProyecto != null) || (this.idModalidadAsesoriaProyecto != null && !this.idModalidadAsesoriaProyecto.equals(other.idModalidadAsesoriaProyecto))) {
      return false;
    }
    return true;
  }

  public Long getIdModalidadAsesoriaProyecto() {
    return idModalidadAsesoriaProyecto;
  }

  public void setIdModalidadAsesoriaProyecto(Long idModalidadAsesoriaProyecto) {
    this.idModalidadAsesoriaProyecto = idModalidadAsesoriaProyecto;
  }

  public AsesoriaProyecto getAsesoriaProyecto() {
    return asesoriaProyecto;
  }

  public void setAsesoriaProyecto(AsesoriaProyecto asesoriaProyecto) {
    this.asesoriaProyecto = asesoriaProyecto;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Constantes getTipoModalidad() {
    return tipoModalidad;
  }

  public void setTipoModalidad(Constantes tipoModalidad) {
    this.tipoModalidad = tipoModalidad;
  }

}
