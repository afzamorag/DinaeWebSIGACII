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
 * @author juan
 */
@Entity
@Table(name = "PLAN_TRABAJO_IMPLEMENTACION")
@NamedQueries({
  @NamedQuery(name = "PlanTrabajoImplementacion.findAll", query = "SELECT p FROM PlanTrabajoImplementacion p"),
  @NamedQuery(name = "PlanTrabajoImplementacion.findByImplementacionProyecto", query = "SELECT p FROM PlanTrabajoImplementacion p WHERE p.implementacionesProyecto.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "PlanTrabajoImplementacion.findPorImplementacionProyectoYcompromisoProyectoImple", query = "SELECT i FROM PlanTrabajoImplementacion i WHERE i.implementacionesProyecto.idImplementacionProy = :idImplementacionProy AND i.compromisoImplementacion.idCompromisoImplementacion = :idCompromisoImplementacion")
})
public class PlanTrabajoImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_PLAN_TRABAJO")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PlanTrabajoSeqGen")
  @SequenceGenerator(name = "PlanTrabajoSeqGen", sequenceName = "SEC_PLAN_TRABAJO_IMP", allocationSize = 1)
  private Long idPlanTrabajo;

  @Column(name = "FECHA_APROBACION_COMITE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAprobacionComite;

  @Column(name = "NRO_ACTA_APROBACION")
  private String nroActaAprobacion;

  @Column(name = "OBJETIVO_GRAL_IMPLEMENTACION")
  private String objetivoGralImplementacion;

  @Column(name = "OBJETIVOS_ESPECIFICOS")
  private String objetivosEspecificos;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_IMPLEMENTACION_PROY", referencedColumnName = "ID_IMPLEMENTACION_PROY")
  @ManyToOne(fetch = FetchType.LAZY)
  private ImplementacionesProyecto implementacionesProyecto;

  @JoinColumn(name = "ID_COMPROMISO_IMPLEMENTACION", referencedColumnName = "ID_COMPROMISO_IMPLEMENTACION")
  @ManyToOne(fetch = FetchType.LAZY)
  private CompromisoImplementacion compromisoImplementacion;

  public PlanTrabajoImplementacion() {
  }

  public PlanTrabajoImplementacion(Long idPlanTrabajo) {
    this.idPlanTrabajo = idPlanTrabajo;
  }

  public Long getIdPlanTrabajo() {
    return idPlanTrabajo;
  }

  public void setIdPlanTrabajo(Long idPlanTrabajo) {
    this.idPlanTrabajo = idPlanTrabajo;
  }

  public Date getFechaAprobacionComite() {
    return fechaAprobacionComite;
  }

  public void setFechaAprobacionComite(Date fechaAprobacionComite) {
    this.fechaAprobacionComite = fechaAprobacionComite;
  }

  public String getNroActaAprobacion() {
    return nroActaAprobacion;
  }

  public void setNroActaAprobacion(String nroActaAprobacion) {
    this.nroActaAprobacion = nroActaAprobacion;
  }

  public String getObjetivoGralImplementacion() {
    return objetivoGralImplementacion;
  }

  public void setObjetivoGralImplementacion(String objetivoGralImplementacion) {
    this.objetivoGralImplementacion = objetivoGralImplementacion;
  }

  public String getObjetivosEspecificos() {
    return objetivosEspecificos;
  }

  public void setObjetivosEspecificos(String objetivosEspecificos) {
    this.objetivosEspecificos = objetivosEspecificos;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public ImplementacionesProyecto getImplementacionesProyecto() {
    return implementacionesProyecto;
  }

  public void setImplementacionesProyecto(ImplementacionesProyecto implementacionesProyecto) {
    this.implementacionesProyecto = implementacionesProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPlanTrabajo != null ? idPlanTrabajo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof PlanTrabajoImplementacion)) {
      return false;
    }
    PlanTrabajoImplementacion other = (PlanTrabajoImplementacion) object;
    if ((this.idPlanTrabajo == null && other.idPlanTrabajo != null) || (this.idPlanTrabajo != null && !this.idPlanTrabajo.equals(other.idPlanTrabajo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.PlanTrabajoImplementacion[ idPlanTrabajo=" + idPlanTrabajo + " ]";
  }

  public CompromisoImplementacion getCompromisoImplementacion() {
    return compromisoImplementacion;
  }

  public void setCompromisoImplementacion(CompromisoImplementacion compromisoImplementacion) {
    this.compromisoImplementacion = compromisoImplementacion;
  }

}
