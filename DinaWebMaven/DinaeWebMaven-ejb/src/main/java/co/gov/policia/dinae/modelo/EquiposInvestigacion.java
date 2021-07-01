package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "EQUIPOS_INVESTIGACION")
@NamedQueries({
  @NamedQuery(name = "EquiposInvestigacion.findAll", query = "SELECT e FROM EquiposInvestigacion e"),
  @NamedQuery(name = "EquiposInvestigacion.findByIdEquipoInvestigacion", query = "SELECT e FROM EquiposInvestigacion e WHERE e.idEquipoInvestigacion = :idEquipoInvestigacion"),
  @NamedQuery(name = "EquiposInvestigacion.findByValor", query = "SELECT e FROM EquiposInvestigacion e WHERE e.valor = :valor"),
  @NamedQuery(name = "EquiposInvestigacion.findByEspecificaciones", query = "SELECT e FROM EquiposInvestigacion e WHERE e.especificaciones = :especificaciones"),
  @NamedQuery(name = "EquiposInvestigacion.findByOrigen", query = "SELECT e FROM EquiposInvestigacion e WHERE e.origen.idConstantes = :origen"),
  @NamedQuery(name = "EquiposInvestigacion.findByNombreEquipo", query = "SELECT e FROM EquiposInvestigacion e WHERE e.nombreEquipo = :nombreEquipo"),
  @NamedQuery(name = "EquiposInvestigacion.findByProyecto", query = "SELECT e FROM EquiposInvestigacion e WHERE e.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EquiposInvestigacion.findByPlanTrabajo", query = "SELECT e FROM EquiposInvestigacion e WHERE e.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo")})
public class EquiposInvestigacion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FuenteProyecto_seq_gen")
  @SequenceGenerator(name = "EquiposInvestigacion_seq_gen", sequenceName = "SEC_EQUIPOS_INVESTIGACION", allocationSize = 1)
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
  private FuenteProyecto fuenteProyecto;

  @Transient
  private boolean seleccionable;

  public EquiposInvestigacion() {
  }

  public EquiposInvestigacion(Long idEquipoInvestigacion) {
    this.idEquipoInvestigacion = idEquipoInvestigacion;
  }

  public EquiposInvestigacion(Long idEquipoInvestigacion, BigDecimal valor, String especificaciones) {
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

  public FuenteProyecto getFuenteProyecto() {
    return fuenteProyecto;
  }

  public void setFuenteProyecto(FuenteProyecto fuenteProyecto) {
    this.fuenteProyecto = fuenteProyecto;
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
    if (!(object instanceof EquiposInvestigacion)) {
      return false;
    }
    EquiposInvestigacion other = (EquiposInvestigacion) object;
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
