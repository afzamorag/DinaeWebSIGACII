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
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "EVIDENCIA_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "EvidenciaProyecto.findAll", query = "SELECT p FROM EvidenciaProyecto p"),
  @NamedQuery(name = "EvidenciaProyecto.findAllPorProyecto", query = "SELECT e FROM EvidenciaProyecto e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EvidenciaProyectoDTO.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.modelo.EvidenciaProyectoDTO ( e.idEvidenciaProyecto, e.descripcion, e.nombreArchivo, e.nombreArchivoFisico, e.tipoArchivo.idConstantes, e.tipoArchivo.valor, e.proyecto.idProyecto ) FROM EvidenciaProyecto e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EvidenciaProyectoDTO.findAllPorProyectoYcompromisoProyecto", query = "SELECT NEW co.gov.policia.dinae.modelo.EvidenciaProyectoDTO ( e.idEvidenciaProyecto, e.descripcion, e.nombreArchivo, e.nombreArchivoFisico, e.tipoArchivo.idConstantes, e.tipoArchivo.valor, e.proyecto.idProyecto ) FROM EvidenciaProyecto e WHERE e.proyecto.idProyecto = :idProyecto AND e.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "EvidenciaProyecto.findAllPorProyectoYcompromisoProyecto", query = "SELECT e FROM EvidenciaProyecto e WHERE e.proyecto.idProyecto = :idProyecto AND e.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "EvidenciaProyecto.EliminarIndicadorPorId", query = "DELETE FROM EvidenciaProyecto e WHERE e.idEvidenciaProyecto = :idEvidenciaProyecto")
})
public class EvidenciaProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_EVIDENCIA_PROYECTO")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EvidenciaProyecto_seq_gen")
  @SequenceGenerator(name = "EvidenciaProyecto_seq_gen", sequenceName = "SEC_EVIDENCIA_PROYECTO", allocationSize = 1)
  private Long idEvidenciaProyecto;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "NOMBRE_ARCHIVO_USUARIO")
  private String nombreArchivo;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Column(name = "FECHA_INGRESO_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaIngresoRegistro;

  @Column(name = "FECHA_MODIFICA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificaRegistro;

  @JoinColumn(name = "ID_TIPO_ARCHIVO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes tipoArchivo;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  @JoinColumn(name = "ID_COMPROMISO_PROYECTO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyecto;

  public EvidenciaProyecto() {
  }

  public EvidenciaProyecto(Long idEvidenciaProyecto) {
    this.idEvidenciaProyecto = idEvidenciaProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEvidenciaProyecto != null ? idEvidenciaProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EvidenciaProyecto)) {
      return false;
    }
    EvidenciaProyecto other = (EvidenciaProyecto) object;
    if ((this.idEvidenciaProyecto == null && other.idEvidenciaProyecto != null) || (this.idEvidenciaProyecto != null && !this.idEvidenciaProyecto.equals(other.idEvidenciaProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EvidenciaProyecto[ idEvidenciaProyecto=" + idEvidenciaProyecto + " ]";
  }

  public Long getIdEvidenciaProyecto() {
    return idEvidenciaProyecto;
  }

  public void setIdEvidenciaProyecto(Long idEvidenciaProyecto) {
    this.idEvidenciaProyecto = idEvidenciaProyecto;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
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

  public Date getFechaIngresoRegistro() {
    return fechaIngresoRegistro;
  }

  public void setFechaIngresoRegistro(Date fechaIngresoRegistro) {
    this.fechaIngresoRegistro = fechaIngresoRegistro;
  }

  public Date getFechaModificaRegistro() {
    return fechaModificaRegistro;
  }

  public void setFechaModificaRegistro(Date fechaModificaRegistro) {
    this.fechaModificaRegistro = fechaModificaRegistro;
  }

  public Constantes getTipoArchivo() {
    return tipoArchivo;
  }

  public void setTipoArchivo(Constantes tipoArchivo) {
    this.tipoArchivo = tipoArchivo;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public CompromisoProyecto getCompromisoProyecto() {
    return compromisoProyecto;
  }

  public void setCompromisoProyecto(CompromisoProyecto compromisoProyecto) {
    this.compromisoProyecto = compromisoProyecto;
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
