package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "VIAJES_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "ViajesProyectoVersion.findAll", query = "SELECT v From ViajesProyectoVersion v"),
  @NamedQuery(name = "ViajesProyectoVersion.findById", query = "SELECT v From ViajesProyectoVersion v WHERE v.idViajeProyecto = :idViajeProyecto"),
  @NamedQuery(name = "ViajesProyectoVersion.findViajesByProyecto", query = "SELECT v From ViajesProyectoVersion v WHERE v.fuenteProyectoVersion.proyectoVersion.idProyecto = :idProyecto"),
  @NamedQuery(name = "ViajesProyectoVersion.findViajesByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.ViajesProyectoDTO(v.idViajeProyecto, v.evento, v.costosPasajes, v.costosViaticos, v.investigadoresProyectoVersion.idInvestigadorProyecto, v.investigadoresProyectoVersion.nombreCompleto, v.investigadoresProyectoVersion.grado, v.fuenteProyectoVersion.idFuenteProyecto, v.fuenteProyectoVersion.proyectoVersion.idProyecto, v.fuenteProyectoVersion.nombreFuente, v.codigoCiudadOrigen, v.nombreCiudadOrigen, v.codigoCiudadDestino, v.nombreCiudadDestino) From ViajesProyectoVersion v WHERE v.fuenteProyectoVersion.proyectoVersion.idProyecto = :idProyecto")
})
public class ViajesProyectoVersion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
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
  private InvestigadorProyectoVersion investigadoresProyectoVersion;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne
  private FuenteProyectoVersion fuenteProyectoVersion;

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

  public ViajesProyectoVersion() {
  }

  public ViajesProyectoVersion(Long idViajeProyecto) {
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
    if (!(object instanceof ViajesProyectoVersion)) {
      return false;
    }
    ViajesProyectoVersion other = (ViajesProyectoVersion) object;
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
