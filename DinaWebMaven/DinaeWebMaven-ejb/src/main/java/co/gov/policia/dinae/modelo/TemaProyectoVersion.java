package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "TEMA_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "TemaProyectoVersion.findIDTemaProyectoPorProyecto", query = "SELECT DISTINCT t.tema.idTema FROM TemaProyectoVersion t WHERE t.proyectoVersion.idProyectoVersion = :idProyectoVersion"),
  @NamedQuery(name = "TemaProyectoVersion.findTemaProyectoPorTemaYproyecto", query = "SELECT t FROM TemaProyectoVersion t WHERE t.tema.idTema = :idTema AND t.proyectoVersion.idProyectoVersion = :idProyectoVersion"),})
public class TemaProyectoVersion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_TEMA_PROYECTO")
  private Long idTemaProyecto;

  @Column(name = "TEXTO")
  private String texto;

  @Column(name = "ARCHIVO_SOPORTE")
  private String archivoSoporte;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Column(name = "TIPO_CONTENIDO_ARCHIVO")
  private String tipoContenidoArchivo;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO")
  private String usuario;

  @JoinColumn(name = "ID_TEMA", referencedColumnName = "ID_TEMA")
  @ManyToOne
  private Tema tema;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne
  private ProyectoVersion proyectoVersion;

  @Column(name = "TIPO_TAB_INFORMACION_IMPL")
  private String tipoTabInformacionImpl;

  public TemaProyectoVersion() {
  }

  public TemaProyectoVersion(Long idTemaProyecto) {
    this.idTemaProyecto = idTemaProyecto;
  }

  public Long getIdTemaProyecto() {
    return idTemaProyecto;
  }

  public void setIdTemaProyecto(Long idTemaProyecto) {
    this.idTemaProyecto = idTemaProyecto;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public String getArchivoSoporte() {
    return archivoSoporte;
  }

  public void setArchivoSoporte(String archivoSoporte) {
    this.archivoSoporte = archivoSoporte;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Tema getTema() {
    return tema;
  }

  public void setTema(Tema tema) {
    this.tema = tema;
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

  public String getTipoTabInformacionImpl() {
    return tipoTabInformacionImpl;
  }

  public void setTipoTabInformacionImpl(String tipoTabInformacionImpl) {
    this.tipoTabInformacionImpl = tipoTabInformacionImpl;
  }

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }

}
