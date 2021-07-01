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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "OPCION_MENU")
@NamedQueries({
  @NamedQuery(name = "OpcionMenuDTO.findAllPorRolPublico", query = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.OpcionMenuDTO( r.idOpcionMenu, r.nombre, r.accion, r.orden, r.titulo ) FROM OpcionMenu r WHERE r.opcionMenu IS NULL AND r.accion IS NULL AND r.tipoAcceso = :tipoAcceso ORDER BY r.orden ASC "),
  @NamedQuery(name = "OpcionMenuDTO.findAllSubMenuPorRolPublico", query = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.OpcionMenuDTO( sm.idOpcionMenu, sm.nombre, sm.accion, sm.orden, sm.titulo ) FROM OpcionMenu sm WHERE sm.opcionMenu.idOpcionMenu = :idMenuPadre AND sm.tipoAcceso = :tipoAcceso ORDER BY sm.orden ASC ")
})
public class OpcionMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OpcionMenu_seq_gen")
  @SequenceGenerator(name = "OpcionMenu_seq_gen", sequenceName = "SEC_OPCION_MENU")
  @Column(name = "ID_OPCION_MENU")
  private Long idOpcionMenu;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "ACCION")
  private String accion;

  @OneToMany(mappedBy = "opcionMenu", fetch = FetchType.LAZY)
  private List<RolOpcionMenu> rolOpcionMenuList;

  @OneToMany(mappedBy = "opcionMenu", fetch = FetchType.LAZY)
  private List<OpcionMenu> opcionMenuListHijos;

  @JoinColumn(name = "ID_OPCION_MENU2", referencedColumnName = "ID_OPCION_MENU")
  @ManyToOne(fetch = FetchType.LAZY)
  private OpcionMenu opcionMenu;

  @Column(name = "TIPO_ACCESO")
  private String tipoAcceso;

  @Column(name = "ORDEN")
  private Integer orden;

  @Column(name = "TITULO")
  private String titulo;

  public OpcionMenu() {
  }

  public OpcionMenu(Long idOpcionMenu) {
    this.idOpcionMenu = idOpcionMenu;
  }

  public OpcionMenu(Long idOpcionMenu, String nombre) {
    this.idOpcionMenu = idOpcionMenu;
    this.nombre = nombre;
  }

  public Long getIdOpcionMenu() {
    return idOpcionMenu;
  }

  public void setIdOpcionMenu(Long idOpcionMenu) {
    this.idOpcionMenu = idOpcionMenu;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getAccion() {
    return accion;
  }

  public void setAccion(String accion) {
    this.accion = accion;
  }

  public List<RolOpcionMenu> getRolOpcionMenuList() {
    return rolOpcionMenuList;
  }

  public void setRolOpcionMenuList(List<RolOpcionMenu> rolOpcionMenuList) {
    this.rolOpcionMenuList = rolOpcionMenuList;
  }

  public List<OpcionMenu> getOpcionMenuListHijos() {
    return opcionMenuListHijos;
  }

  public void setOpcionMenuListHijos(List<OpcionMenu> opcionMenuListHijos) {
    this.opcionMenuListHijos = opcionMenuListHijos;
  }

  public OpcionMenu getOpcionMenu() {
    return opcionMenu;
  }

  public void setOpcionMenu(OpcionMenu opcionMenu) {
    this.opcionMenu = opcionMenu;
  }

  public String getTipoAcceso() {
    return tipoAcceso;
  }

  public void setTipoAcceso(String tipoAcceso) {
    this.tipoAcceso = tipoAcceso;
  }

  public Integer getOrden() {
    return orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idOpcionMenu != null ? idOpcionMenu.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof OpcionMenu)) {
      return false;
    }
    OpcionMenu other = (OpcionMenu) object;
    if ((this.idOpcionMenu == null && other.idOpcionMenu != null) || (this.idOpcionMenu != null && !this.idOpcionMenu.equals(other.idOpcionMenu))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.OpcionMenu[ idOpcionMenu=" + idOpcionMenu + " ]";
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

}
