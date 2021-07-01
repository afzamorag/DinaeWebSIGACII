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
@Table(name = "EJECUCION_VIAJES_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "EjecucionViajesProyecto.findAll", query = "SELECT e FROM EjecucionViajesProyecto e"),
  @NamedQuery(name = "EjecucionViajesProyecto.findViajesRelacionadoProyecto", query = "SELECT e FROM EjecucionViajesProyecto e WHERE e.informeAvance.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EjecucionViajesProyecto.findViajesRelacionadoImplementacionProyecto", query = "SELECT e FROM EjecucionViajesProyecto e WHERE e.informeAvanceImplementacion.implementacionesProyecto.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "EjecucionViajesProyecto.findViajesRelacionadoInformeProyecto", query = "SELECT e FROM EjecucionViajesProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance AND e.viajesProyecto.idViajeProyecto = :idViajeProyecto"),
  @NamedQuery(name = "EjecucionViajesProyecto.findViajesRelacionadoInformeImplementacionProyecto", query = "SELECT e FROM EjecucionViajesProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND e.viajesProyecto.idViajeProyecto = :idViajeProyecto"),
  @NamedQuery(name = "EjecucionViajesProyecto.deleteViajesRelacionadoInformeProyecto", query = "DELETE FROM EjecucionViajesProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionViajesProyecto.SelectViajesRelacionadoInformeProyecto", query = "SELECT e FROM EjecucionViajesProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionViajesProyecto.deleteViajesRelacionadoInformeImplementacionProyecto", query = "DELETE FROM EjecucionViajesProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "EjecucionViajesProyecto.SelectViajesRelacionadoInformeImplementacionProyecto", query = "SELECT e FROM EjecucionViajesProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")

})
public class EjecucionViajesProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EjecucionViajesProyecto_seq_gen")
  @SequenceGenerator(name = "EjecucionViajesProyecto_seq_gen", sequenceName = "SEC_EJECUCION_VIAJES_PROYECTO", allocationSize = 1)
  @Column(name = "ID_EJECUCION_VIAJES_PROYECTO")
  private Long idEjecucionViajesProyecto;

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

  @JoinColumn(name = "ID_VIAJE_PROYECTO", referencedColumnName = "ID_VIAJE_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ViajesProyecto viajesProyecto;

  @JoinColumn(name = "ID_INFORME_IMPLEMENTACION", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  public EjecucionViajesProyecto() {
  }

  public EjecucionViajesProyecto(Long idEjecucionViajesProyecto) {
    this.idEjecucionViajesProyecto = idEjecucionViajesProyecto;
  }

  public EjecucionViajesProyecto(Long idEjecucionViajesProyecto, ViajesProyecto viajesProyecto) {
    this.idEjecucionViajesProyecto = idEjecucionViajesProyecto;
    this.viajesProyecto = viajesProyecto;
  }

  public Long getIdEjecucionViajesProyecto() {
    return idEjecucionViajesProyecto;
  }

  public void setIdEjecucionViajesProyecto(Long idEjecucionViajesProyecto) {
    this.idEjecucionViajesProyecto = idEjecucionViajesProyecto;
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

  public ViajesProyecto getViajesProyecto() {
    return viajesProyecto;
  }

  public void setViajesProyecto(ViajesProyecto viajesProyecto) {
    this.viajesProyecto = viajesProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEjecucionViajesProyecto != null ? idEjecucionViajesProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EjecucionViajesProyecto)) {
      return false;
    }
    EjecucionViajesProyecto other = (EjecucionViajesProyecto) object;
    if ((this.idEjecucionViajesProyecto == null && other.idEjecucionViajesProyecto != null) || (this.idEjecucionViajesProyecto != null && !this.idEjecucionViajesProyecto.equals(other.idEjecucionViajesProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EjecucionViajesProyecto[ id=" + idEjecucionViajesProyecto + " ]";
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
