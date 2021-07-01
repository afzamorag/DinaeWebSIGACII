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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "EXCEPCIONES_COMPROMISO")
@NamedQueries({
  @NamedQuery(name = "ExcepcionesCompromiso.findAll", query = "SELECT e FROM ExcepcionesCompromiso e"),
  @NamedQuery(name = "ExcepcionesCompromiso.findAllPorUnidadPolicial", query = "SELECT e FROM ExcepcionesCompromiso e WHERE e.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "ExcepcionesCompromiso.findUltimaPorUnidadPolicialYcompromisoPeriodo", query = "SELECT e FROM ExcepcionesCompromiso e WHERE e.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND e.compromisoPeriodo.idCompromisoPeriodo = :idCompromisoPeriodo ORDER BY e.fechaLimite DESC")
})
public class ExcepcionesCompromiso implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExcepcionesCompromiso_seq_gen")
  @SequenceGenerator(name = "ExcepcionesCompromiso_seq_gen", sequenceName = "SEC_EXCEPCIONES_COMPROMISO", allocationSize = 1)
  @Column(name = "ID_EXCEPCION_COMPROMISO")
  private Long idExcepcionCompromiso;

  @Column(name = "FECHA_LIMITE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaLimite;

  @Column(name = "NOMBRE_ARCHIVO_USUARIO")
  private String nombreArchivo;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Size(max = 512)
  @Column(name = "JUSTIFICACION")
  private String justificacion;

  @JoinColumn(name = "ID_COMPROMISO_PERIODO", referencedColumnName = "ID_COMPROMISO_PERIODO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoPeriodo compromisoPeriodo;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @JoinColumn(name = "ID_COMPROMISO_PROYECT_CORRECC", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyectoCorreccion;

  @Column(name = "NOMBRE_COMPROMISO_CORRECCION")
  private String nombreCompromisoCorreccion;

  public ExcepcionesCompromiso() {
  }

  public ExcepcionesCompromiso(Long idExcepcionCompromiso) {
    this.idExcepcionCompromiso = idExcepcionCompromiso;
  }

  public Long getIdExcepcionCompromiso() {
    return idExcepcionCompromiso;
  }

  public void setIdExcepcionCompromiso(Long idExcepcionCompromiso) {
    this.idExcepcionCompromiso = idExcepcionCompromiso;
  }

  public Date getFechaLimite() {
    return fechaLimite;
  }

  public void setFechaLimite(Date fechaLimite) {
    this.fechaLimite = fechaLimite;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idExcepcionCompromiso != null ? idExcepcionCompromiso.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ExcepcionesCompromiso)) {
      return false;
    }
    ExcepcionesCompromiso other = (ExcepcionesCompromiso) object;
    if ((this.idExcepcionCompromiso == null && other.idExcepcionCompromiso != null) || (this.idExcepcionCompromiso != null && !this.idExcepcionCompromiso.equals(other.idExcepcionCompromiso))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ExcepcionesCompromiso[ idExcepcionCompromiso=" + idExcepcionCompromiso + " ]";
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

  public CompromisoPeriodo getCompromisoPeriodo() {
    return compromisoPeriodo;
  }

  public void setCompromisoPeriodo(CompromisoPeriodo compromisoPeriodo) {
    this.compromisoPeriodo = compromisoPeriodo;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public CompromisoProyecto getCompromisoProyectoCorreccion() {
    return compromisoProyectoCorreccion;
  }

  public void setCompromisoProyectoCorreccion(CompromisoProyecto compromisoProyectoCorreccion) {
    this.compromisoProyectoCorreccion = compromisoProyectoCorreccion;
  }

  @Transient
  public String getNombreCompromisoPeriodoOcompromisoProyecto() {

    if (compromisoPeriodo != null) {

      return compromisoPeriodo.getNombreCompromisoNumeroIncrementa();

    }

    return nombreCompromisoCorreccion;
  }

  public String getNombreCompromisoCorreccion() {
    return nombreCompromisoCorreccion;
  }

  public void setNombreCompromisoCorreccion(String nombreCompromisoCorreccion) {
    this.nombreCompromisoCorreccion = nombreCompromisoCorreccion;
  }

}
