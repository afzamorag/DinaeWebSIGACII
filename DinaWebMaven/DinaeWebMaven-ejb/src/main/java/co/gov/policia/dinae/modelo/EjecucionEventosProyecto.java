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
 * @author cguzman
 */
@Entity
@Table(name = "EJECUCION_EVENTOS_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "EjecucionEventosProyecto.findAll", query = "SELECT e FROM EjecucionEventosProyecto e"),
  @NamedQuery(name = "EjecucionEventosProyecto.findEventoRelacionadoProyecto", query = "SELECT e FROM EjecucionEventosProyecto e WHERE e.informeAvance.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EjecucionEventosProyecto.findEventoRelacionadoImplementacionProyecto", query = "SELECT e FROM EjecucionEventosProyecto e WHERE e.informeAvanceImplementacion.implementacionesProyecto.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "EjecucionEventosProyecto.findEventoRelacionadoInformeProyecto", query = "SELECT e FROM EjecucionEventosProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance AND e.eventoProyecto.idEventoProyecto = :idEventoProyecto"),
  @NamedQuery(name = "EjecucionEventosProyecto.findEventoRelacionadoInformeImplementacionProyecto", query = "SELECT e FROM EjecucionEventosProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND e.eventoProyecto.idEventoProyecto = :idEventoProyecto"),
  @NamedQuery(name = "EjecucionEventosProyecto.deleteEventoRelacionadoInformeProyecto", query = "DELETE FROM EjecucionEventosProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionEventosProyecto.SelectEventoRelacionadoInformeProyecto", query = "SELECT e FROM EjecucionEventosProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionEventosProyecto.deleteEventoRelacionadoInformeImplementacionProyecto", query = "DELETE FROM EjecucionEventosProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "EjecucionEventosProyecto.SelectEventoRelacionadoInformeImplementacionProyecto", query = "SELECT e FROM EjecucionEventosProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")

})
public class EjecucionEventosProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EjecucionEventosProyecto_seq_gen")
  @SequenceGenerator(name = "EjecucionEventosProyecto_seq_gen", sequenceName = "SEC_EJECUCION_EVENTOS_PROYECTO", allocationSize = 1)
  @Column(name = "ID_EJECUCION_EVENT_PROY")
  private Long idEjecucionEventProy;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO_REGISTRA")
  private String usuarioRegistra;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_INFORME_AVANCE", referencedColumnName = "ID_INFORME_AVANCE")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private InformeAvance informeAvance;

  @JoinColumn(name = "ID_INFORME_IMPLEMENTACION", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  @JoinColumn(name = "ID_EVENTO_PROYECTO", referencedColumnName = "ID_EVENTO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private EventoProyecto eventoProyecto;

  public EjecucionEventosProyecto() {
  }

  public EjecucionEventosProyecto(Long idEjecucionEventProy) {
    this.idEjecucionEventProy = idEjecucionEventProy;
  }

  public EjecucionEventosProyecto(Long idEjecucionEventProy, EventoProyecto eventoProyecto) {
    this.idEjecucionEventProy = idEjecucionEventProy;
    this.eventoProyecto = eventoProyecto;
  }

  public Long getIdEjecucionEventProy() {
    return idEjecucionEventProy;
  }

  public void setIdEjecucionEventProy(Long idEjecucionEventProy) {
    this.idEjecucionEventProy = idEjecucionEventProy;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public InformeAvance getInformeAvance() {
    return informeAvance;
  }

  public void setInformeAvance(InformeAvance informeAvance) {
    this.informeAvance = informeAvance;
  }

  public EventoProyecto getEventoProyecto() {
    return eventoProyecto;
  }

  public void setEventoProyecto(EventoProyecto eventoProyecto) {
    this.eventoProyecto = eventoProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEjecucionEventProy != null ? idEjecucionEventProy.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EjecucionEventosProyecto)) {
      return false;
    }
    EjecucionEventosProyecto other = (EjecucionEventosProyecto) object;
    if ((this.idEjecucionEventProy == null && other.idEjecucionEventProy != null) || (this.idEjecucionEventProy != null && !this.idEjecucionEventProy.equals(other.idEjecucionEventProy))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EjecucionEventosProyecto[ id=" + idEjecucionEventProy + " ]";
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

  @Column(name = "CORRECION")
  private Character correccion;

  public Character getCorreccion() {
    return correccion;
  }

  public void setCorreccion(Character correccion) {
    this.correccion = correccion;
  }

}
