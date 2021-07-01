package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "TEMA")
@NamedQueries({
  @NamedQuery(name = "Tema.findAll", query = "SELECT t FROM Tema t WHERE t.tipoTema = :tipoTema AND t.estado = 'ACTIVO'"),
  @NamedQuery(name = "Tema.CuentaAll", query = "SELECT COUNT(t) FROM Tema t WHERE t.tipoTema = :tipoTema AND t.estado = 'ACTIVO'"),
  @NamedQuery(name = "Tema.findAllNoEstado", query = "SELECT t FROM Tema t ORDER BY t.tipoTema"),
  @NamedQuery(name = "Tema.findAllTipoNoEstado", query = "SELECT t FROM Tema t WHERE t.tipoTema = :tipoTema ORDER BY t.tipoTema")
})
public class Tema implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Tema_seq_gen")
  @SequenceGenerator(name = "Tema_seq_gen", sequenceName = "SEC_TEMA", allocationSize = 1)
  @Column(name = "ID_TEMA")
  private Long idTema;

  @NotNull
  @Size(min = 1, max = 512)
  @Column(name = "DESCRIPCION_TEMA")
  private String descripcionTema;

  @Size(min = 1, max = 512)
  @Column(name = "TOOLTIP")
  private String tooltip;

  @Column(name = "TIPO_TEMA")
  private String tipoTema;

  @Column(name = "ESTADO", length = 20, nullable = false)
  @Pattern(regexp = "ACTIVO|INACTIVO")
  private String estado;

  @OneToMany(mappedBy = "tema")
  private List<TemaProyecto> temaProyectoList;

  @Transient
  private boolean mostrarImagenCheckInformacionPlanteamientoProblema;

  public Tema() {
  }

  public Tema(Long idTema) {
    this.idTema = idTema;
  }

  public Tema(Long idTema, String descripcionTema) {
    this.idTema = idTema;
    this.descripcionTema = descripcionTema;
  }

  public Long getIdTema() {
    return idTema;
  }

  public void setIdTema(Long idTema) {
    this.idTema = idTema;
  }

  public String getDescripcionTema() {
    return descripcionTema;
  }

  public void setDescripcionTema(String descripcionTema) {
    this.descripcionTema = descripcionTema;
  }

  public List<TemaProyecto> getTemaProyectoList() {
    return temaProyectoList;
  }

  public void setTemaProyectoList(List<TemaProyecto> temaProyectoList) {
    this.temaProyectoList = temaProyectoList;
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
    hash += (idTema != null ? idTema.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Tema)) {
      return false;
    }
    Tema other = (Tema) object;
    if ((this.idTema == null && other.idTema != null) || (this.idTema != null && !this.idTema.equals(other.idTema))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.Tema[ idTema=" + idTema + " ]";
  }

  public boolean isMostrarImagenCheckInformacionPlanteamientoProblema() {
    return mostrarImagenCheckInformacionPlanteamientoProblema;
  }

  public void setMostrarImagenCheckInformacionPlanteamientoProblema(boolean mostrarImagenCheckInformacionPlanteamientoProblema) {
    this.mostrarImagenCheckInformacionPlanteamientoProblema = mostrarImagenCheckInformacionPlanteamientoProblema;
  }

  public String getTooltip() {
    return tooltip;
  }

  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  public String getTipoTema() {
    return tipoTema;
  }

  public void setTipoTema(String tipoTema) {
    this.tipoTema = tipoTema;
  }

}
