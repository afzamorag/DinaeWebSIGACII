package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TEMA_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "TemaProyecto.findAll", query = "SELECT t FROM TemaProyecto t"),
  @NamedQuery(name = "TemaProyecto.findAllPorTema", query = "SELECT t FROM TemaProyecto t WHERE t.tema.idTema = :idTema"),
  @NamedQuery(name = "TemaProyecto.findTemaProyectoPorTemaYproyecto", query = "SELECT t FROM TemaProyecto t WHERE t.tema.idTema = :idTema AND t.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "TemaProyecto.findTemaProyectoPorTemaEinformeAvanceImplementacion", query = "SELECT t FROM TemaProyecto t WHERE t.tema.idTema = :idTema AND t.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "TemaProyecto.findTemaProyectoPorInformeAvanceImplementacion", query = "SELECT t FROM TemaProyecto t WHERE t.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "TemaProyecto.findIDTemaProyectoPorProyecto", query = "SELECT DISTINCT t.tema.idTema FROM TemaProyecto t WHERE t.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "TemaProyecto.findIDTemaProyectoPorInformeAvanceImplementacion", query = "SELECT DISTINCT t.tema.idTema FROM TemaProyecto t WHERE t.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion  AND t.tipoTabInformacionImpl = :tipoTabInformacionImpl"),
  @NamedQuery(name = "TemaProyecto.CuentaTemaProyectoPorProyecto", query = "SELECT DISTINCT COUNT(t.tema.idTema) FROM TemaProyecto t WHERE t.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "TemaProyecto.CuentaTemaProyectoPorInformeAvanceYtipoTemaTabInformacion", query = "SELECT DISTINCT COUNT( t ) FROM TemaProyecto t WHERE t.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND t.tipoTabInformacionImpl = :tipoTabInformacionImpl")
})
public class TemaProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TemaProyecto_seq_gen")
  @SequenceGenerator(name = "TemaProyecto_seq_gen", sequenceName = "SEC_TEMA_PROYECTO", allocationSize = 1)
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

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  @JoinColumn(name = "ID_INFORME_AVANCE_IMPL", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne
  private InformeAvanceImplementacion informeAvanceImplementacion;

  @Column(name = "TIPO_TAB_INFORMACION_IMPL")
  private String tipoTabInformacionImpl;

  public TemaProyecto() {
  }

  public TemaProyecto(Long idTemaProyecto) {
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

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idTemaProyecto != null ? idTemaProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TemaProyecto)) {
      return false;
    }
    TemaProyecto other = (TemaProyecto) object;
    if ((this.idTemaProyecto == null && other.idTemaProyecto != null) || (this.idTemaProyecto != null && !this.idTemaProyecto.equals(other.idTemaProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.TemaProyecto[ idTemaProyecto=" + idTemaProyecto + " ]";
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

  public String getTipoTabInformacionImpl() {
    return tipoTabInformacionImpl;
  }

  public void setTipoTabInformacionImpl(String tipoTabInformacionImpl) {
    this.tipoTabInformacionImpl = tipoTabInformacionImpl;
  }

}
