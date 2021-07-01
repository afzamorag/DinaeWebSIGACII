package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "EQUIPOS_INVESTIGACION_VERSION")
@NamedQueries({
  @NamedQuery(name = "EquiposInvestigacionVersion.findAll", query = "SELECT e From EquiposInvestigacionVersion e"),
  @NamedQuery(name = "EquiposInvestigacionVersion.findByIdEquipoInvestigacion", query = "SELECT e From EquiposInvestigacionVersion e WHERE e.idEquipoInvestigacion = :idEquipoInvestigacion"),
  @NamedQuery(name = "EquiposInvestigacionVersion.findByValor", query = "SELECT e From EquiposInvestigacionVersion e WHERE e.valor = :valor"),
  @NamedQuery(name = "EquiposInvestigacionVersion.findByEspecificaciones", query = "SELECT e From EquiposInvestigacionVersion e WHERE e.especificaciones = :especificaciones"),
  @NamedQuery(name = "EquiposInvestigacionVersion.findByOrigen", query = "SELECT e From EquiposInvestigacionVersion e WHERE e.origen.idConstantes = :origen"),
  @NamedQuery(name = "EquiposInvestigacionVersion.findByNombreEquipo", query = "SELECT e From EquiposInvestigacionVersion e WHERE e.nombreEquipo = :nombreEquipo"),
  @NamedQuery(name = "EquiposInvestigacionVersion.findByProyecto", query = "SELECT e From EquiposInvestigacionVersion e WHERE e.fuenteProyectoVersion.proyectoVersion.idProyecto = :idProyecto")})
public class EquiposInvestigacionVersion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_EQUIPO_INVESTIGACION")
  private Long idEquipoInvestigacion;

  @Column(name = "VALOR")
  private BigDecimal valor;

  @Column(name = "ESPECIFICACIONES")
  private String especificaciones;

  @JoinColumn(name = "ID_ORIGEN", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes origen;

  @Column(name = "NOMBRE_EQUIPO")
  private String nombreEquipo;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private FuenteProyectoVersion fuenteProyectoVersion;

  @Transient
  private boolean seleccionable;

  public EquiposInvestigacionVersion() {
  }

  public EquiposInvestigacionVersion(Long idEquipoInvestigacion) {
    this.idEquipoInvestigacion = idEquipoInvestigacion;
  }

  public EquiposInvestigacionVersion(Long idEquipoInvestigacion, BigDecimal valor, String especificaciones) {
    this.idEquipoInvestigacion = idEquipoInvestigacion;
    this.valor = valor;
    this.especificaciones = especificaciones;
  }

  public Long getIdEquipoInvestigacion() {
    return idEquipoInvestigacion;
  }

  public void setIdEquipoInvestigacion(Long idEquipoInvestigacion) {
    this.idEquipoInvestigacion = idEquipoInvestigacion;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getEspecificaciones() {
    return especificaciones;
  }

  public void setEspecificaciones(String especificaciones) {
    this.especificaciones = especificaciones;
  }

  public Constantes getOrigen() {
    return origen;
  }

  public void setOrigen(Constantes origen) {
    this.origen = origen;
  }

  public String getNombreEquipo() {
    return nombreEquipo;
  }

  public void setNombreEquipo(String nombreEquipo) {
    this.nombreEquipo = nombreEquipo;
  }

  public FuenteProyectoVersion getFuenteProyectoVersion() {
    return fuenteProyectoVersion;
  }

  public void setFuenteProyectoVersion(FuenteProyectoVersion fuenteProyectoVersion) {
    this.fuenteProyectoVersion = fuenteProyectoVersion;
  }

  public boolean isSeleccionable() {
    return seleccionable;
  }

  public void setSeleccionable(boolean seleccionable) {
    this.seleccionable = seleccionable;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEquipoInvestigacion != null ? idEquipoInvestigacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EquiposInvestigacionVersion)) {
      return false;
    }
    EquiposInvestigacionVersion other = (EquiposInvestigacionVersion) object;
    if ((this.idEquipoInvestigacion == null && other.idEquipoInvestigacion != null) || (this.idEquipoInvestigacion != null && !this.idEquipoInvestigacion.equals(other.idEquipoInvestigacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EquiposInvestigacion[ idEquipoInvestigacion=" + idEquipoInvestigacion + " ]";
  }

  @Override
  public String getLlaveModel() {

    if (idEquipoInvestigacion == null) {
      return null;
    }

    return idEquipoInvestigacion.toString();
  }

}
