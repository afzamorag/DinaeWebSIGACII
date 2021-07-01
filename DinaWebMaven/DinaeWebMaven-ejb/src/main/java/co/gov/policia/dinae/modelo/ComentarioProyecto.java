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
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "COMENTARIO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "ComentarioProyecto.findAll", query = "SELECT c FROM ComentarioProyecto c"),
  @NamedQuery(name = "ComentarioProyecto.findAllPorItemYproyecto", query = "SELECT c FROM ComentarioProyecto c WHERE c.item.idConstantes = :idTitem AND c.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ComentarioProyecto.findAllPorIdProyecto", query = "SELECT c FROM ComentarioProyecto c WHERE c.proyecto.idProyecto = :idProyecto ORDER BY c.fechaModificacion DESC"),
  @NamedQuery(name = "ComentarioProyectoDTO.findAllPorIdProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ComentarioProyectoDTO( c.idComentarioProyecto, c.comentario, c.proyecto.idProyecto, c.item.idConstantes, c.item.valor, c.comentarioEnviar, c.identificacion, c.nombreCompleto ) FROM ComentarioProyecto c WHERE c.proyecto.idProyecto = :idProyecto ORDER BY c.fechaModificacion DESC"),
  @NamedQuery(name = "ComentarioProyectoDTO.findAllPorIdProyectoAndIdDocumProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ComentarioProyectoDTO( c.idComentarioProyecto, c.comentario, c.proyecto.idProyecto, c.item.idConstantes, c.item.valor, c.comentarioEnviar, c.identificacion, c.nombreCompleto, c.documentacionProyecto.idDocumProyecto, c.fechaRegistro) FROM ComentarioProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.documentacionProyecto.idDocumProyecto = :idDocumProyecto ORDER BY c.fechaModificacion DESC"),
  @NamedQuery(name = "ComentarioProyectoDTO.findAllPorIdProyectoAndIdDocumProyectoTrabajoGrado", query = "SELECT NEW co.gov.policia.dinae.dto.ComentarioProyectoDTO( c.idComentarioProyecto, c.comentario, c.proyecto.idProyecto, c.identificacion, c.nombreCompleto, c.documentacionProyecto.idDocumProyecto, c.fechaRegistro) FROM ComentarioProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.documentacionProyecto.idDocumProyecto = :idDocumProyecto ORDER BY c.fechaModificacion DESC"),
  @NamedQuery(name = "ComentarioProyecto.findComentarioByEnsayo", query = "SELECT c FROM ComentarioProyecto c WHERE c.ensayoCritico.idEnsayoCritico = :idEnsayoCritico"),
  @NamedQuery(name = "ComentarioProyecto.findComentarioByEnsayoAutor", query = "SELECT c FROM ComentarioProyecto c WHERE c.ensayoCritico.idEnsayoCritico = :idEnsayoCritico AND c.autorComentario.idConstantes = :autorComentario"),
  @NamedQuery(name = "ComentarioProyecto.findAllPorIdDocumProyecto", query = "SELECT c FROM ComentarioProyecto c WHERE c.documentacionProyecto.idDocumProyecto = :idDocumProyecto"),
  @NamedQuery(name = "ComentarioProyecto.eliminarComentarioProyectoPorIdComentarioProyecto", query = "DELETE FROM ComentarioProyecto c WHERE c.idComentarioProyecto = :idComentarioProyecto")

})
public class ComentarioProyecto implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ComentarioProyecto_seq_gen")
  @SequenceGenerator(name = "ComentarioProyecto_seq_gen", sequenceName = "SEC_COMENTARIO_PROYECTO", allocationSize = 1)
  @Column(name = "ID_COMENTARIO_PROYECTO")
  private Long idComentarioProyecto;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "COMENTARIO")
  private String comentario;

  @Column(name = "FECHA_MODIFICACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_ITEM", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes item;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "COMENTARIO_ENVIAR")
  private Character comentarioEnviar;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "NOMBRE_COMPLETO")
  private String nombreCompleto;

  @JoinColumn(name = "ID_DOCUMENTACION_PROYECTO", referencedColumnName = "ID_DOCUM_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private DocumentacionProyecto documentacionProyecto;

  @JoinColumn(name = "ID_ENSAYO_CRITICO", referencedColumnName = "ID_ENSAYO_CRITICO")
  @ManyToOne(fetch = FetchType.LAZY)
  private EnsayoCritico ensayoCritico;

  @JoinColumn(name = "AUTOR_COMENTARIO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes autorComentario;

  public ComentarioProyecto() {
  }

  public ComentarioProyecto(Long idComentarioProyecto) {
    this.idComentarioProyecto = idComentarioProyecto;
  }

  public Long getIdComentarioProyecto() {
    return idComentarioProyecto;
  }

  public void setIdComentarioProyecto(Long idComentarioProyecto) {
    this.idComentarioProyecto = idComentarioProyecto;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public Constantes getAutorComentario() {
    return autorComentario;
  }

  public void setAutorComentario(Constantes autorComentario) {
    this.autorComentario = autorComentario;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idComentarioProyecto != null ? idComentarioProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ComentarioProyecto)) {
      return false;
    }
    ComentarioProyecto other = (ComentarioProyecto) object;
    if ((this.idComentarioProyecto == null && other.idComentarioProyecto != null) || (this.idComentarioProyecto != null && !this.idComentarioProyecto.equals(other.idComentarioProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ComentarioProyecto[ idComentarioProyecto=" + idComentarioProyecto + " ]";
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public Constantes getItem() {
    return item;
  }

  public void setItem(Constantes item) {
    this.item = item;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Character getComentarioEnviar() {
    if (comentarioEnviar == null) {
      comentarioEnviar = 'N';
    }
    return comentarioEnviar;
  }

  public void setComentarioEnviar(Character comentarioEnviar) {
    this.comentarioEnviar = comentarioEnviar;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public DocumentacionProyecto getDocumentacionProyecto() {
    return documentacionProyecto;
  }

  public void setDocumentacionProyecto(DocumentacionProyecto documentacionProyecto) {
    this.documentacionProyecto = documentacionProyecto;
  }

  public EnsayoCritico getEnsayoCritico() {
    return ensayoCritico;
  }

  public void setEnsayoCritico(EnsayoCritico ensayoCritico) {
    this.ensayoCritico = ensayoCritico;
  }

}
