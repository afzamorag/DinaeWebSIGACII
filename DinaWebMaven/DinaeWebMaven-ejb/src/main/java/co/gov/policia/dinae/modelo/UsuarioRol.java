package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "USUARIO_ROL")
@NamedQueries({
  @NamedQuery(name = "UsuarioRol.findAll", query = "SELECT u FROM UsuarioRol u"),
  @NamedQuery(name = "UsuarioRol.findAllPorIdentificacion", query = "SELECT NEW co.gov.policia.dinae.dto.RolUsuarioDTO(u.idUsuarioRol, u.rol.idRol, u.rol.nombre, u.rol.requiereUnidadPolicial, u.activo) FROM UsuarioRol u WHERE u.identificadorUsuario = :identificacion AND u.activo = 'S'"),
  @NamedQuery(name = "UsuarioRol.findAllPorIdentificacionYroles", query = "SELECT NEW co.gov.policia.dinae.dto.RolUsuarioDTO(u.idUsuarioRol, u.rol.idRol, u.rol.nombre, u.rol.requiereUnidadPolicial, u.activo) FROM UsuarioRol u WHERE u.rol.idRol IN :roles AND u.identificadorUsuario = :identificacion AND u.activo = 'S'"),
  @NamedQuery(name = "IdentificacionUsuarioRol.findJefeUnidadPorUnidadPolicialYrol", query = "SELECT u.identificadorUsuario FROM UsuarioRol u WHERE u.rol.idRol = :idRol AND u.activo = 'S'"),
  @NamedQuery(name = "UsuarioRol.findByUsuarioYRol", query = "SELECT u FROM UsuarioRol u WHERE u.rol.idRol = :idRol AND u.identificadorUsuario = :identificadorUsuario AND u.activo = 'S'"),
  @NamedQuery(name = "UsuarioRol.findByUsuarioYAdminRoles", query = "SELECT u FROM UsuarioRol u WHERE u.rol.idRol IN :roles AND u.identificadorUsuario = :identificadorUsuario AND u.activo = 'S'")

})
public class UsuarioRol implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_USUARIO_ROL")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UsuarioRol_seq_gen")
  @SequenceGenerator(name = "UsuarioRol_seq_gen", sequenceName = "SEC_USUARIO_ROL", allocationSize = 1)
  private Long idUsuarioRol;

  @Column(name = "IDENTIFICADOR_USUARIO")
  private String identificadorUsuario;

  @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private Rol rol;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFin;

  @Column(name = "ACTIVO")
  private String activo;

  public UsuarioRol() {
  }

  public UsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public UsuarioRol(Long idUsuarioRol, String identificadorUsuario) {
    this.idUsuarioRol = idUsuarioRol;
    this.identificadorUsuario = identificadorUsuario;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public String getIdentificadorUsuario() {
    return identificadorUsuario;
  }

  public void setIdentificadorUsuario(String identificadorUsuario) {
    this.identificadorUsuario = identificadorUsuario;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idUsuarioRol != null ? idUsuarioRol.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UsuarioRol)) {
      return false;
    }
    UsuarioRol other = (UsuarioRol) object;
    if ((this.idUsuarioRol == null && other.idUsuarioRol != null) || (this.idUsuarioRol != null && !this.idUsuarioRol.equals(other.idUsuarioRol))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.UsuarioRol[ idUsuarioRol=" + idUsuarioRol + " ]";
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getActivo() {
    return activo;
  }

  public void setActivo(String activo) {
    this.activo = activo;
  }

}
