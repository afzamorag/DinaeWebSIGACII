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
@Table(name = "ROL")
@NamedQueries({
  @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
  @NamedQuery(name = "RolDTO.findAll", query = "SELECT NEW co.gov.policia.dinae.dto.RolDTO(r.idRol, r.nombre, r.requiereUnidadPolicial) FROM Rol r ORDER BY r.nombre ASC"),
  @NamedQuery(name = "RolDTO.findRolesByIds", query = "SELECT NEW co.gov.policia.dinae.dto.RolDTO(r.idRol, r.nombre, r.requiereUnidadPolicial) FROM Rol r WHERE r.idRol IN :roles ORDER BY r.nombre ASC "),
  @NamedQuery(name = "RolDTO.findRolesUnidad", query = "SELECT NEW co.gov.policia.dinae.dto.RolDTO(r.idRol, r.nombre, r.requiereUnidadPolicial) FROM Rol r WHERE r.nombre NOT LIKE '%VICIN%' ORDER BY r.nombre ASC ")})
public class Rol implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Rol_seq_gen")
  @SequenceGenerator(name = "Rol_seq_gen", sequenceName = "SEC_ROL")
  @Column(name = "ID_ROL")
  private Long idRol;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "REQUIERE_UNIDAD_POLICIAL")
  private Character requiereUnidadPolicial;

  @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
  private List<RolOpcionMenu> rolOpcionMenuList;

  @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
  private List<UsuarioRol> usuarioRolList;

  public Rol() {
  }

  public Rol(Long idRol) {
    this.idRol = idRol;
  }

  public Rol(Long idRol, String nombre) {
    this.idRol = idRol;
    this.nombre = nombre;
  }

  public Long getIdRol() {
    return idRol;
  }

  public void setIdRol(Long idRol) {
    this.idRol = idRol;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<RolOpcionMenu> getRolOpcionMenuList() {
    return rolOpcionMenuList;
  }

  public void setRolOpcionMenuList(List<RolOpcionMenu> rolOpcionMenuList) {
    this.rolOpcionMenuList = rolOpcionMenuList;
  }

  public List<UsuarioRol> getUsuarioRolList() {
    return usuarioRolList;
  }

  public void setUsuarioRolList(List<UsuarioRol> usuarioRolList) {
    this.usuarioRolList = usuarioRolList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idRol != null ? idRol.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Rol)) {
      return false;
    }
    Rol other = (Rol) object;
    if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.Rol[ idRol=" + idRol + " ]";
  }

  public Character getRequiereUnidadPolicial() {
    return requiereUnidadPolicial;
  }

  public void setRequiereUnidadPolicial(Character requiereUnidadPolicial) {
    this.requiereUnidadPolicial = requiereUnidadPolicial;
  }

}
