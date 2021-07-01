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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INFORME_AVANCE_IMPLEMENTACION")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "InformeAvanceImplementacion.findAll", query = "SELECT i FROM InformeAvanceImplementacion i"),
  @NamedQuery(name = "InformeAvanceImplementacion.findIdEstadoCompromisoImplementacionPorIdInforme", query = "SELECT i.compromisoImplementacion.idEstadoCompromisoImpl.idConstantes FROM InformeAvanceImplementacion i WHERE i.idInformeAvanceImplementacion = :idInformeAvanceImplementacion "),
  @NamedQuery(name = "InformeAvanceImplementacion.findPorPorTipoCompromisoEImplementacionProy", query = "SELECT i FROM InformeAvanceImplementacion i WHERE i.implementacionesProyecto.idImplementacionProy = :idImplementacionProy AND i.compromisoImplementacion.idTipoCompromiso.idConstantes = :idTipoCompromiso"),
  @NamedQuery(name = "InformeAvanceImplementacion.findPorImplementacionProyectoYcompromisoProyectoImple", query = "SELECT i FROM InformeAvanceImplementacion i WHERE i.implementacionesProyecto.idImplementacionProy = :idImplementacionProy AND i.compromisoImplementacion.idCompromisoImplementacion = :idCompromisoImplementacion")
})
public class InformeAvanceImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InformeAvanceImplementacion_seq_gen")
  @SequenceGenerator(name = "InformeAvanceImplementacion_seq_gen", sequenceName = "SEC_INFORME_AVANCE_IMPL", allocationSize = 1)
  @Column(name = "ID_INFORME_AVANCE_IMPL")
  private Long idInformeAvanceImplementacion;

  @JoinColumn(name = "ID_IMPLEMENTACION_PROY", referencedColumnName = "ID_IMPLEMENTACION_PROY")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ImplementacionesProyecto implementacionesProyecto;

  @JoinColumn(name = "ID_COMPROMISO_IMPLEMENTACION", referencedColumnName = "ID_COMPROMISO_IMPLEMENTACION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoImplementacion compromisoImplementacion;

  @Column(name = "FECHA_APROBACION_COMITE")
  @Temporal(TemporalType.DATE)
  private Date fechaAprobacionComite;

  @Column(name = "NRO_ACTA_APROBACION_COMITE")
  private String numeroAprobacionComite;

  @Column(name = "IDENTIFICACION_JEFE_UNIDAD")
  private String identificacionJefeUnidad;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.DATE)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.DATE)
  private Date fechaFin;

  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "USUARIO_CREACION")
  private String usuarioCreacion;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  public InformeAvanceImplementacion() {
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   */
  public InformeAvanceImplementacion(Long idInformeAvanceImplementacion) {

    this.idInformeAvanceImplementacion = idInformeAvanceImplementacion;

  }

  public Long getIdInformeAvanceImplementacion() {
    return idInformeAvanceImplementacion;
  }

  public void setIdInformeAvanceImplementacion(Long idInformeAvanceImplementacion) {
    this.idInformeAvanceImplementacion = idInformeAvanceImplementacion;
  }

  public ImplementacionesProyecto getImplementacionesProyecto() {
    return implementacionesProyecto;
  }

  public void setImplementacionesProyecto(ImplementacionesProyecto implementacionesProyecto) {
    this.implementacionesProyecto = implementacionesProyecto;
  }

  public CompromisoImplementacion getCompromisoImplementacion() {
    return compromisoImplementacion;
  }

  public void setCompromisoImplementacion(CompromisoImplementacion compromisoImplementacion) {
    this.compromisoImplementacion = compromisoImplementacion;
  }

  public Date getFechaAprobacionComite() {
    return fechaAprobacionComite;
  }

  public void setFechaAprobacionComite(Date fechaAprobacionComite) {
    this.fechaAprobacionComite = fechaAprobacionComite;
  }

  public String getNumeroAprobacionComite() {
    return numeroAprobacionComite;
  }

  public void setNumeroAprobacionComite(String numeroAprobacionComite) {
    this.numeroAprobacionComite = numeroAprobacionComite;
  }

  public String getIdentificacionJefeUnidad() {
    return identificacionJefeUnidad;
  }

  public void setIdentificacionJefeUnidad(String identificacionJefeUnidad) {
    this.identificacionJefeUnidad = identificacionJefeUnidad;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idInformeAvanceImplementacion != null ? idInformeAvanceImplementacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof InformeAvanceImplementacion)) {
      return false;
    }
    InformeAvanceImplementacion other = (InformeAvanceImplementacion) object;
    if ((this.idInformeAvanceImplementacion == null && other.idInformeAvanceImplementacion != null) || (this.idInformeAvanceImplementacion != null && !this.idInformeAvanceImplementacion.equals(other.idInformeAvanceImplementacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.InformeAvanceImplementacion[ idInformeAvanceImplementacion=" + idInformeAvanceImplementacion + " ]";
  }
}
