package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "HISTORIAL_ESTADOS_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "HistorialEstadoProyecto.consultarPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.HistorialEstadoProyectosMigradosDTO( h.fechaInicio, h.estado.idConstantes, h.observacion ) FROM HistorialEstadoProyecto h WHERE h.proyecto.idProyecto = :idProyecto")
})
public class HistorialEstadoProyecto implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_HISTORIAL_ESTADO_PROY")
  private Long idHistorialEstadoProyecto;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes estado;

  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicio;

  @Column(name = "OBSERVACION")
  private String observacion;

  public HistorialEstadoProyecto() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idHistorialEstadoProyecto != null ? idHistorialEstadoProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof HistorialEstadoProyecto)) {
      return false;
    }
    HistorialEstadoProyecto other = (HistorialEstadoProyecto) object;
    if ((this.idHistorialEstadoProyecto == null && other.idHistorialEstadoProyecto != null) || (this.idHistorialEstadoProyecto != null && !this.idHistorialEstadoProyecto.equals(other.idHistorialEstadoProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.HistorialEstadoProyecto[ idComentarioNecesidad=" + idHistorialEstadoProyecto + " ]";
  }

  public Long getIdHistorialEstadoProyecto() {
    return idHistorialEstadoProyecto;
  }

  public void setIdHistorialEstadoProyecto(Long idHistorialEstadoProyecto) {
    this.idHistorialEstadoProyecto = idHistorialEstadoProyecto;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public Constantes getEstado() {
    return estado;
  }

  public void setEstado(Constantes estado) {
    this.estado = estado;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

}
