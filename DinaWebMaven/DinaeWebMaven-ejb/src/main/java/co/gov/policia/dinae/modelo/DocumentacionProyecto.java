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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "DOCUMENTACION_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "DocumentacionProyecto.findAll", query = "SELECT d FROM DocumentacionProyecto d"),
  @NamedQuery(name = "DocumentacionProyecto.findAllByIdProyecto", query = "SELECT d FROM DocumentacionProyecto d WHERE d.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "DocumentacionProyecto.findAllByIdProyectoAndIdUsuarioRol", query = "SELECT d FROM DocumentacionProyecto d WHERE d.proyecto.idProyecto = :idProyecto AND d.usuarioRol.idUsuarioRol = :idUsuarioRol"),
  @NamedQuery(name = "DocumentacionProyectoDTO.findAllByIdProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.DocumentacionProyectoDTO ( d.idDocumProyecto, d.descripcion, d.nombreArchivo, d.nombreArchivoFisico, d.fechaPresentacion, d.comentarioDocumento, d.tipoDocumento.valor, d.maquina, d.proyecto.idProyecto, d.usuarioRol.idUsuarioRol) FROM DocumentacionProyecto d WHERE d.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "DocumentacionProyecto.EliminarDocumentacionProyectoPorId", query = "DELETE FROM DocumentacionProyecto e WHERE e.idDocumProyecto = :idDocumProyecto")
})
public class DocumentacionProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DocumentacionProyecto_seq_gen")
  @SequenceGenerator(name = "DocumentacionProyecto_seq_gen", sequenceName = "SEC_DOCUMENTACION_PROYECTO", allocationSize = 1)
  @Column(name = "ID_DOCUM_PROYECTO")
  private Long idDocumProyecto;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "NOMBRE_ARCHIVO_USUARIO")
  private String nombreArchivo;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @NotNull
  @Column(name = "FECHA_PRESENTACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaPresentacion;

  @Size(max = 512)
  @Column(name = "COMENTARIO_DOCUMENTO")
  private String comentarioDocumento;

  @JoinColumn(name = "ID_TIPO_DOCUMENTO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes tipoDocumento;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  public DocumentacionProyecto() {
  }

  public DocumentacionProyecto(Long idDocumProyecto) {
    this.idDocumProyecto = idDocumProyecto;
  }

  public Long getIdDocumProyecto() {
    return idDocumProyecto;
  }

  public void setIdDocumProyecto(Long idDocumProyecto) {
    this.idDocumProyecto = idDocumProyecto;
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

  public Date getFechaPresentacion() {
    return fechaPresentacion;
  }

  public void setFechaPresentacion(Date fechaPresentacion) {
    this.fechaPresentacion = fechaPresentacion;
  }

  public String getComentarioDocumento() {
    return comentarioDocumento;
  }

  public void setComentarioDocumento(String comentarioDocumento) {
    this.comentarioDocumento = comentarioDocumento;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public Constantes getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Constantes tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
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
    hash += (idDocumProyecto != null ? idDocumProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof DocumentacionProyecto)) {
      return false;
    }
    DocumentacionProyecto other = (DocumentacionProyecto) object;
    if ((this.idDocumProyecto == null && other.idDocumProyecto != null) || (this.idDocumProyecto != null && !this.idDocumProyecto.equals(other.idDocumProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.DocumentacionProyecto[ idDocumProyecto=" + idDocumProyecto + " ]";
  }

}
