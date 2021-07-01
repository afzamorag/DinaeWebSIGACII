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
@Table(name = "ROL_OPCION_MENU")
@NamedQueries({
  @NamedQuery(name = "RolOpcionMenu.findAll", query = "SELECT r FROM RolOpcionMenu r"),
  @NamedQuery(name = "RolOpcionMenu.findAllPorRolPrivado", query = "SELECT NEW co.gov.policia.dinae.dto.OpcionMenuDTO( r.opcionMenu.idOpcionMenu, r.opcionMenu.nombre, r.opcionMenu.accion, r.opcionMenu.orden, r.opcionMenu.titulo ) FROM RolOpcionMenu r WHERE r.rol.idRol = :idRol AND r.opcionMenu.opcionMenu IS NULL AND r.opcionMenu.tipoAcceso = :tipoAcceso ORDER BY r.opcionMenu.orden ASC "),
  @NamedQuery(name = "RolOpcionMenu.findAllPorRolPublicoYOprivado", query = "SELECT r.opcionMenu FROM RolOpcionMenu r WHERE r.opcionMenu.accion IS NULL AND ( r.opcionMenu.tipoAcceso = :tipoAccesopr AND r.rol.idRol = :idRolUsuario ) ORDER BY r.opcionMenu.orden ASC "),
  @NamedQuery(name = "RolOpcionMenu.findAllSubMenuPorRolPrivado", query = "SELECT r.opcionMenu FROM RolOpcionMenu r WHERE r.opcionMenu.opcionMenu.idOpcionMenu = :idMenuPadre AND r.opcionMenu.accion IS NOT NULL AND r.opcionMenu.tipoAcceso = :tipoAccesopr AND r.rol.idRol = :idRolUsuario ORDER BY r.opcionMenu.orden ASC ")})
public class RolOpcionMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RolOpcionMenu_seq_gen")
  @SequenceGenerator(name = "RolOpcionMenu_seq_gen", sequenceName = "SEC_ROL_OPCION_MENU")
  @Column(name = "ID_ROL_OPCION_MENU")
  private Long idRolOpcionMenu;

  @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private Rol rol;

  @JoinColumn(name = "ID_OPCION_MENU", referencedColumnName = "ID_OPCION_MENU")
  @ManyToOne(fetch = FetchType.LAZY)
  private OpcionMenu opcionMenu;

  public RolOpcionMenu() {
  }

  public RolOpcionMenu(Long idRolOpcionMenu) {
    this.idRolOpcionMenu = idRolOpcionMenu;
  }

  public Long getIdRolOpcionMenu() {
    return idRolOpcionMenu;
  }

  public void setIdRolOpcionMenu(Long idRolOpcionMenu) {
    this.idRolOpcionMenu = idRolOpcionMenu;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public OpcionMenu getOpcionMenu() {
    return opcionMenu;
  }

  public void setOpcionMenu(OpcionMenu opcionMenu) {
    this.opcionMenu = opcionMenu;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idRolOpcionMenu != null ? idRolOpcionMenu.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof RolOpcionMenu)) {
      return false;
    }
    RolOpcionMenu other = (RolOpcionMenu) object;
    if ((this.idRolOpcionMenu == null && other.idRolOpcionMenu != null) || (this.idRolOpcionMenu != null && !this.idRolOpcionMenu.equals(other.idRolOpcionMenu))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.RolOpcionMenu[ idRolOpcionMenu=" + idRolOpcionMenu + " ]";
  }

}
