package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author juan
 */
@Entity
@Table(name = "VIAJES_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "ViajesProyecto.findAll", query = "SELECT v FROM ViajesProyecto v"),
  @NamedQuery(name = "ViajesProyecto.findById", query = "SELECT v FROM ViajesProyecto v WHERE v.idViajeProyecto = :idViajeProyecto"),
  @NamedQuery(name = "ViajesProyecto.findViajesByProyecto", query = "SELECT v FROM ViajesProyecto v WHERE v.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ViajesProyecto.findViajesByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.ViajesProyectoDTO(v.idViajeProyecto, v.evento, v.costosPasajes, v.costosViaticos, v.investigadoresProyecto.idInvestigadorProyecto, v.investigadoresProyecto.nombreCompleto, v.investigadoresProyecto.grado, v.fuenteProyecto.idFuenteProyecto, v.fuenteProyecto.proyecto.idProyecto, v.fuenteProyecto.nombreFuente, v.codigoCiudadOrigen, v.nombreCiudadOrigen, v.codigoCiudadDestino, v.nombreCiudadDestino) FROM ViajesProyecto v WHERE v.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ViajesProyectoDTO.findViajesByPlanTrabajo", query = "SELECT NEW co.gov.policia.dinae.dto.ViajesProyectoDTO(v.idViajeProyecto, v.evento, v.costosPasajes, v.costosViaticos, v.investigadoresProyecto.idInvestigadorProyecto, v.investigadoresProyecto.nombreCompleto, v.investigadoresProyecto.grado, v.fuenteProyecto.idFuenteProyecto, v.fuenteProyecto.nombreFuente, v.codigoCiudadOrigen, v.nombreCiudadOrigen, v.codigoCiudadDestino, v.nombreCiudadDestino) FROM ViajesProyecto v WHERE v.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo")
})
public class ViajesProyecto implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ViajesProyecto_seq_gen")
  @SequenceGenerator(name = "ViajesProyecto_seq_gen", sequenceName = "SEC_VIAJES_PROYECTO", allocationSize = 1)
  @Column(name = "ID_VIAJE_PROYECTO")
  private Long idViajeProyecto;

  @Column(name = "EVENTO", nullable = false, length = 512)
  private String evento;

  @Column(name = "COSTOS_PASAJES", nullable = false)
  private BigDecimal costosPasajes;

  @Column(name = "COSTOS_VIATICOS", nullable = false)
  private BigDecimal costosViaticos;

  @JoinColumn(name = "ID_INVESTIGADOR_PROYECTO", referencedColumnName = "ID_INVESTIGADOR_PROYECTO")
  @ManyToOne(optional = false)
  private InvestigadorProyecto investigadoresProyecto;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne
  private FuenteProyecto fuenteProyecto;

  @Column(name = "CODIGO_CIUDAD_ORIGEN")
  private String codigoCiudadOrigen;

  @Column(name = "NOMBRE_CIUDAD_ORIGEN")
  private String nombreCiudadOrigen;

  @Column(name = "CODIGO_CIUDAD_DESTINO")
  private String codigoCiudadDestino;

  @Column(name = "NOMBRE_CIUDAD_DESTINO")
  private String nombreCiudadDestino;

  @Transient
  private boolean seleccionable;

  public ViajesProyecto() {
  }

  public ViajesProyecto(Long idViajeProyecto) {
    this.idViajeProyecto = idViajeProyecto;
  }

  public Long getIdViajeProyecto() {
    return idViajeProyecto;
  }

  public void setIdViajeProyecto(Long idViajeProyecto) {
    this.idViajeProyecto = idViajeProyecto;
  }

  public String getEvento() {
    return evento;
  }

  public void setEvento(String evento) {
    this.evento = evento;
  }

  public BigDecimal getCostosPasajes() {
    return costosPasajes;
  }

  public void setCostosPasajes(BigDecimal costosPasajes) {
    this.costosPasajes = costosPasajes;
  }

  public BigDecimal getCostosViaticos() {
    return costosViaticos;
  }

  public void setCostosViaticos(BigDecimal costosViaticos) {
    this.costosViaticos = costosViaticos;
  }

  public InvestigadorProyecto getInvestigadoresProyecto() {
    return investigadoresProyecto;
  }

  public void setInvestigadoresProyecto(InvestigadorProyecto investigadoresProyecto) {
    this.investigadoresProyecto = investigadoresProyecto;
  }

  public FuenteProyecto getFuenteProyecto() {
    return fuenteProyecto;
  }

  public void setFuenteProyecto(FuenteProyecto fuenteProyecto) {
    this.fuenteProyecto = fuenteProyecto;
  }

  public String getCodigoCiudadOrigen() {
    return codigoCiudadOrigen;
  }

  public void setCodigoCiudadOrigen(String codigoCiudadOrigen) {
    this.codigoCiudadOrigen = codigoCiudadOrigen;
  }

  public String getNombreCiudadOrigen() {
    return nombreCiudadOrigen;
  }

  public void setNombreCiudadOrigen(String nombreCiudadOrigen) {
    this.nombreCiudadOrigen = nombreCiudadOrigen;
  }

  public String getCodigoCiudadDestino() {
    return codigoCiudadDestino;
  }

  public void setCodigoCiudadDestino(String codigoCiudadDestino) {
    this.codigoCiudadDestino = codigoCiudadDestino;
  }

  public String getNombreCiudadDestino() {
    return nombreCiudadDestino;
  }

  public void setNombreCiudadDestino(String nombreCiudadDestino) {
    this.nombreCiudadDestino = nombreCiudadDestino;
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
    hash += (idViajeProyecto != null ? idViajeProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ViajesProyecto)) {
      return false;
    }
    ViajesProyecto other = (ViajesProyecto) object;
    if ((this.idViajeProyecto == null && other.idViajeProyecto != null) || (this.idViajeProyecto != null && !this.idViajeProyecto.equals(other.idViajeProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ViajesProyecto[ idViajeProyecto=" + idViajeProyecto + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (idViajeProyecto == null) {
      return null;
    }

    return idViajeProyecto.toString();
  }

}
