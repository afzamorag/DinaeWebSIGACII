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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "COMENTARIO_COMPROMISO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAll", query = "SELECT c FROM ComentarioCompromisoProyecto c"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorIdCompromisoProyecto", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorIdCompromisoProyectoYorigenCU", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND c.autorCasoUso = :autorCasoUso ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorIdCompromisoImplementacionYorigenCU", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoImplementacion.idCompromisoImplementacion = :idCompromisoImplementacion AND c.autorCasoUso = :autorCasoUso ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorIdCompromisoImplementacion", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoImplementacion.idCompromisoImplementacion = :idCompromisoImplementacion ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorCompromisoProyecto", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorIdCompromisoProyectoYorigenCUeItem", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND c.autorCasoUso = :autorCasoUso AND c.item.idConstantes = :idItem ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.findAllPorIdCompromisoImplementacionYorigenCUeItem", query = "SELECT c FROM ComentarioCompromisoProyecto c WHERE c.compromisoImplementacion.idCompromisoImplementacion = :idCompromisoImplementacion AND c.autorCasoUso = :autorCasoUso AND c.item.idConstantes = :idItem ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.ComentarioProyectoDTOfindAllPorIdCompromisoProyectoYorigenCU", query = "SELECT NEW co.gov.policia.dinae.dto.ComentarioProyectoDTO( c.idComentarioCompromisoProyecto, c.comentario, c.item.idConstantes, c.item.valor, c.comentarioEnviar, c.fechaRegistro ) FROM ComentarioCompromisoProyecto c WHERE c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND c.autorCasoUso = :autorCasoUso ORDER BY c.fechaRegistro DESC"),
  @NamedQuery(name = "ComentarioCompromisoProyecto.ComentarioProyectoDTOfindAllPorIdCompromisoImplementacionYorigenCU", query = "SELECT NEW co.gov.policia.dinae.dto.ComentarioProyectoDTO( c.idComentarioCompromisoProyecto, c.comentario, c.item.idConstantes, c.item.valor, c.comentarioEnviar, c.fechaRegistro ) FROM ComentarioCompromisoProyecto c WHERE c.compromisoImplementacion.idCompromisoImplementacion = :idCompromisoImplementacion AND c.autorCasoUso = :autorCasoUso ORDER BY c.fechaRegistro DESC")
})
public class ComentarioCompromisoProyecto implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ComentarioCompromisoProyecto_seq_gen")
  @SequenceGenerator(name = "ComentarioCompromisoProyecto_seq_gen", sequenceName = "SEC_COMENTARIO_COMPROMISO_PROY", allocationSize = 1)
  @Column(name = "ID_COMENTARIO_COMPROMISO_PROYE")
  private Long idComentarioCompromisoProyecto;

  @JoinColumn(name = "ID_COMPROMISO_PROYECTO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyecto;

  @JoinColumn(name = "ID_COMPROMISO_IMPLEMENTACION", referencedColumnName = "ID_COMPROMISO_IMPLEMENTACION")
  @ManyToOne(fetch = FetchType.LAZY)
  private CompromisoImplementacion compromisoImplementacion;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "COMENTARIO")
  private String comentario;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "NOMBRE_COMPLETO")
  private String nombreCompleto;

  @Column(name = "AUTOR_CASO_USO")
  private String autorCasoUso;

  @JoinColumn(name = "ID_ITEM", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.EAGER)
  private Constantes item;

  @Column(name = "COMENTARIO_ENVIAR")
  private Character comentarioEnviar;

  @Transient
  private String comentarioDetalle;

  public Long getIdComentarioCompromisoProyecto() {
    return idComentarioCompromisoProyecto;
  }

  public void setIdComentarioCompromisoProyecto(Long idComentarioCompromisoProyecto) {
    this.idComentarioCompromisoProyecto = idComentarioCompromisoProyecto;
  }

  public CompromisoProyecto getCompromisoProyecto() {
    return compromisoProyecto;
  }

  public void setCompromisoProyecto(CompromisoProyecto compromisoProyecto) {
    this.compromisoProyecto = compromisoProyecto;
  }

  public CompromisoImplementacion getCompromisoImplementacion() {
    return compromisoImplementacion;
  }

  public void setCompromisoImplementacion(CompromisoImplementacion compromisoImplementacion) {
    this.compromisoImplementacion = compromisoImplementacion;
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

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getAutorCasoUso() {
    return autorCasoUso;
  }

  public void setAutorCasoUso(String autorCasoUso) {
    this.autorCasoUso = autorCasoUso;
  }

  public Constantes getItem() {
    return item;
  }

  public void setItem(Constantes item) {
    this.item = item;
  }

  public Character getComentarioEnviar() {
    return comentarioEnviar;
  }

  public void setComentarioEnviar(Character comentarioEnviar) {
    this.comentarioEnviar = comentarioEnviar;
  }

  public String getComentarioDetalle() {

    comentarioDetalle = "";
    if (item != null && item.getValor() != null) {

      comentarioDetalle = item.getValor().concat(" - ");

    }

    comentarioDetalle = comentarioDetalle.concat(comentario);

    return comentarioDetalle;
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
