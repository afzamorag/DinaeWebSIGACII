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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "INFORME_AVANCE")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "InformeAvance.findAll", query = "SELECT i FROM InformeAvance i"),
  @NamedQuery(name = "InformeAvance.findEstadoCompromisoProyectoPorIdInforme", query = "SELECT i.compromisoProyecto.estado.idConstantes FROM InformeAvance i WHERE i.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "InformeAvance.findAlllInformePorCompromisoProyectoYproyecto", query = "SELECT i FROM InformeAvance i WHERE i.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND i.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "InformeAvance.findAlllInformePorCompromisoProyectoYproyectoYnumeroIncrementaCompromisoPeriodo", query = "SELECT i FROM InformeAvance i WHERE i.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND i.proyecto.idProyecto = :idProyecto "),
  @NamedQuery(name = "InformeAvance.findByIdInformeAvance", query = "SELECT i FROM InformeAvance i WHERE i.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "InformeAvance.findByFechaInicio", query = "SELECT i FROM InformeAvance i WHERE i.fechaInicio = :fechaInicio"),
  @NamedQuery(name = "InformeAvance.findByFechaFinalizacion", query = "SELECT i FROM InformeAvance i WHERE i.fechaFinalizacion = :fechaFinalizacion"),
  @NamedQuery(name = "InformeAvance.findByFechaRegistro", query = "SELECT i FROM InformeAvance i WHERE i.fechaRegistro = :fechaRegistro"),
  @NamedQuery(name = "InformeAvance.findByNombreArchivo", query = "SELECT i FROM InformeAvance i WHERE i.nombreArchivo     = :nombreArchivo"),
  @NamedQuery(name = "InformeAvance.findByNombreArchivoFisico", query = "SELECT i FROM InformeAvance i WHERE i.nombreArchivoFisico = :nombreArchivoFisico"),
  @NamedQuery(name = "InformeAvance.findByTipoContenidoArchivo", query = "SELECT i FROM InformeAvance i WHERE i.tipoContenidoArchivo = :tipoContenidoArchivo"),
  @NamedQuery(name = "InformeAvance.findLastInformeAvanceByProyecto", query = "SELECT i FROM InformeAvance i WHERE i.proyecto.idProyecto = :idProyecto ORDER by i.fechaFinalizacion desc"),
  @NamedQuery(name = "InformeAvance.findInformeAvanceFinalByProyecto", query = "SELECT i FROM InformeAvance i WHERE i.proyecto.idProyecto = :idProyecto AND i.tipoInformeAvance.idConstantes = :idConstante AND i.compromisoProyecto.idCompromisoProyecto = :idCompromiso"),
  @NamedQuery(name = "InformeAvance.findInformeAvanceFinalByProyectoAndTipo", query = "SELECT i FROM InformeAvance i WHERE i.proyecto.idProyecto = :idProyecto AND i.tipoInformeAvance.idConstantes = :idConstante")})
public class InformeAvance implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InformeAvance_seq_gen")
  @SequenceGenerator(name = "InformeAvance_seq_gen", sequenceName = "SEC_INFORME_AVANCE", allocationSize = 1)
  @Column(name = "ID_INFORME_AVANCE")
  private Long idInformeAvance;

  @NotNull
  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicio;

  @NotNull
  @Column(name = "FECHA_FINALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFinalizacion;

  @NotNull
  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Size(max = 100)
  @Column(name = "NOMBRE_ARCHIVO")
  private String nombreArchivo;

  @Size(max = 100)
  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Size(max = 200)
  @Column(name = "TIPO_CONTENIDO_ARCHIVO")
  private String tipoContenidoArchivo;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false)
  private UsuarioRol idUsuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_TIPO_INFORME", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoInformeAvance;

  @JoinColumn(name = "ID_COMPROMISO_PROYECTO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyecto;

  public InformeAvance() {
  }

  public InformeAvance(Long idInformeAvance) {
    this.idInformeAvance = idInformeAvance;
  }

  public InformeAvance(Long idInformeAvance, Date fechaInicio, Date fechaFinalizacion, Date fechaRegistro) {
    this.idInformeAvance = idInformeAvance;
    this.fechaInicio = fechaInicio;
    this.fechaFinalizacion = fechaFinalizacion;
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdInformeAvance() {
    return idInformeAvance;
  }

  public void setIdInformeAvance(Long idInformeAvance) {
    this.idInformeAvance = idInformeAvance;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFinalizacion() {
    return fechaFinalizacion;
  }

  public void setFechaFinalizacion(Date fechaFinalizacion) {
    this.fechaFinalizacion = fechaFinalizacion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  public String getTipoContenidoArchivo() {
    return tipoContenidoArchivo;
  }

  public void setTipoContenidoArchivo(String tipoContenidoArchivo) {
    this.tipoContenidoArchivo = tipoContenidoArchivo;
  }

  public UsuarioRol getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(UsuarioRol idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idInformeAvance != null ? idInformeAvance.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof InformeAvance)) {
      return false;
    }
    InformeAvance other = (InformeAvance) object;
    if ((this.idInformeAvance == null && other.idInformeAvance != null) || (this.idInformeAvance != null && !this.idInformeAvance.equals(other.idInformeAvance))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.InformeAvance[ idInformeAvance=" + idInformeAvance + " ]";
  }

  public Constantes getTipoInformeAvance() {
    return tipoInformeAvance;
  }

  public void setTipoInformeAvance(Constantes tipoInformeAvance) {
    this.tipoInformeAvance = tipoInformeAvance;
  }

  public CompromisoProyecto getCompromisoProyecto() {
    return compromisoProyecto;
  }

  public void setCompromisoProyecto(CompromisoProyecto compromisoProyecto) {
    this.compromisoProyecto = compromisoProyecto;
  }

}
