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
 * @author juan
 */
@Entity
@Table(name = "COMPROMISO_IMPLEMENTACION")
@NamedQueries({
  @NamedQuery(name = "CompromisoImplementacion.findAll", query = "SELECT c FROM CompromisoImplementacion c"),
  @NamedQuery(name = "CompromisoImplementacion.findIdTipoCompromisoImplementacionPorId", query = "SELECT c.idTipoCompromiso.idConstantes FROM CompromisoImplementacion c WHERE c.idCompromisoImplementacion = :idCompromisoImplementacion"),
  @NamedQuery(name = "CompromisoImplementacion.findByIdCompromisoImplementacion", query = "SELECT c FROM CompromisoImplementacion c WHERE c.idCompromisoImplementacion = :idCompromisoImplementacion"),
  @NamedQuery(name = "CompromisoImplementacion.findByIdImplementacionProy", query = "SELECT c FROM CompromisoImplementacion c WHERE c.implementacionesProyecto.idImplementacionProy = :idImplementacionProy ORDER BY c.idTipoCompromiso.idConstantes ASC "),
  @NamedQuery(name = "CompromisoImplementacion.findByIdImplementacionProyYtipoCompromiso", query = "SELECT c FROM CompromisoImplementacion c WHERE c.implementacionesProyecto.idImplementacionProy = :idImplementacionProyecto AND c.idTipoCompromiso.idConstantes = :idTipoCompromisoImplementacion AND c.idEstadoCompromisoImpl.idConstantes NOT IN (108) "),
  @NamedQuery(name = "CompromisoImplementacionDTO.findByIdImplementacionProy", query = "SELECT NEW co.gov.policia.dinae.dto.CompromisoImplementacionDTO ( c.idCompromisoImplementacion, c.fechaNuevoCompromiso, c.idEstadoCompromisoImpl.idConstantes, c.idEstadoCompromisoImpl.valor, c.idTipoCompromiso.idConstantes, c.idTipoCompromiso.valor ) FROM CompromisoImplementacion c WHERE c.implementacionesProyecto.idImplementacionProy = :idImplementacionProy ORDER BY c.idTipoCompromiso.idConstantes ASC "),
  @NamedQuery(name = "CompromisoImplementacion.findByIdImplementacionProyCorreccionyListaEstado", query = "SELECT c FROM CompromisoImplementacion c WHERE c.implementacionesProyecto.idImplementacionProy = :idImplementacionProy AND c.idEstadoCompromisoImpl.idConstantes IN :idListaEstado ORDER BY c.idTipoCompromiso.idConstantes ASC "),
  @NamedQuery(name = "CompromisoImplementacion.findByIdProyectoListaEstado", query = "SELECT c FROM CompromisoImplementacion c WHERE c.implementacionesProyecto.proyecto.idProyecto = :idProyecto AND c.idEstadoCompromisoImpl.idConstantes IN :idListaEstado ORDER BY c.idTipoCompromiso.idConstantes ASC "),
  @NamedQuery(name = "CompromisoImplementacion.findByUsuario", query = "SELECT c FROM CompromisoImplementacion c WHERE c.usuario = :usuario"),
  @NamedQuery(name = "CompromisoImplementacion.findByFechaCreacion", query = "SELECT c FROM CompromisoImplementacion c WHERE c.fechaCreacion = :fechaCreacion"),
  @NamedQuery(name = "CompromisoImplementacion.findByIdImplementacionProyYtipoCompromisoNoCorregido", query = "SELECT c FROM CompromisoImplementacion c WHERE c.implementacionesProyecto.idImplementacionProy = :idImplementacionProyecto AND c.idTipoCompromiso.idConstantes = :idTipoCompromisoImplementacion AND c.compromisoImplementacionHijo IS NULL AND c.idEstadoCompromisoImpl.idConstantes NOT IN (108)")
})
public class CompromisoImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CompromisoImplementacionSeqGen")
  @SequenceGenerator(name = "CompromisoImplementacionSeqGen", sequenceName = "SEC_COMPROMISO_IMPLEMENTACION", allocationSize = 1)
  @Column(name = "ID_COMPROMISO_IMPLEMENTACION")
  private Long idCompromisoImplementacion;

  @JoinColumn(name = "ID_IMPLEMENTACION_PROY", referencedColumnName = "ID_IMPLEMENTACION_PROY")
  @ManyToOne
  private ImplementacionesProyecto implementacionesProyecto;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "USUARIO_ACTUALIZA")
  private String usuarioActualiza;

  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol idUsuarioRol;

  @JoinColumn(name = "ID_USUARIO_ROL_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol idUsuarioRolActualiza;

  @JoinColumn(name = "ID_ESTADO_COMPROMISO_IMPL", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes idEstadoCompromisoImpl;

  @JoinColumn(name = "ID_TIPO_COMPROMISO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes idTipoCompromiso;

  @JoinColumn(name = "ID_RESULTADO_VICIN", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes resultadoRevisionVicin;

  @Column(name = "COMENTARIO_TEMPORAL")
  private String comentarioTemporal;

  @Transient
  private String nombreCompromiso;

  @Transient
  private String estadoCompromiso;

  @Column(name = "FECHA_NUEVO_COMPROMISO")
  @Temporal(TemporalType.DATE)
  private Date fechaNuevoCompromiso;

  @JoinColumn(name = "ID_RESULTADO_RETROALIMENTA", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes resultadoRetroalimentacion;

  @Column(name = "FECHA_COMPROMISO")
  @Temporal(TemporalType.DATE)
  private Date fechaCompromiso;

  @JoinColumn(name = "ID_COMPROMISO_CORRECCION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoCompromisoCorreccion;

  @Column(name = "NOMBRE_COMPROMISO_CORRECCION")
  private String nombreCompromisoCorrecion;

  @JoinColumn(name = "ID_COMPROMISO_PADRE", referencedColumnName = "ID_COMPROMISO_IMPLEMENTACION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoImplementacion compromisoImplementacionPadre;

  @JoinColumn(name = "ID_COMPROMISO_HIJO", referencedColumnName = "ID_COMPROMISO_IMPLEMENTACION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoImplementacion compromisoImplementacionHijo;

  @Column(name = "FECHA_ENVIO_VICIN")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvioVicin;

  public CompromisoImplementacion() {
  }

  public CompromisoImplementacion(Long idCompromisoImplementacion) {
    this.idCompromisoImplementacion = idCompromisoImplementacion;
  }

  public Long getIdCompromisoImplementacion() {
    return idCompromisoImplementacion;
  }

  public void setIdCompromisoImplementacion(Long idCompromisoImplementacion) {
    this.idCompromisoImplementacion = idCompromisoImplementacion;
  }

  public ImplementacionesProyecto getImplementacionesProyecto() {
    return implementacionesProyecto;
  }

  public void setImplementacionesProyecto(ImplementacionesProyecto implementacionesProyecto) {
    this.implementacionesProyecto = implementacionesProyecto;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getUsuarioActualiza() {
    return usuarioActualiza;
  }

  public void setUsuarioActualiza(String usuarioActualiza) {
    this.usuarioActualiza = usuarioActualiza;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public UsuarioRol getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(UsuarioRol idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public UsuarioRol getIdUsuarioRolActualiza() {
    return idUsuarioRolActualiza;
  }

  public void setIdUsuarioRolActualiza(UsuarioRol idUsuarioRolActualiza) {
    this.idUsuarioRolActualiza = idUsuarioRolActualiza;
  }

  public Constantes getIdEstadoCompromisoImpl() {
    return idEstadoCompromisoImpl;
  }

  public void setIdEstadoCompromisoImpl(Constantes idEstadoCompromisoImpl) {
    this.idEstadoCompromisoImpl = idEstadoCompromisoImpl;
  }

  public Constantes getIdTipoCompromiso() {
    return idTipoCompromiso;
  }

  public void setIdTipoCompromiso(Constantes idTipoCompromiso) {
    this.idTipoCompromiso = idTipoCompromiso;
  }

  public Constantes getResultadoRevisionVicin() {
    return resultadoRevisionVicin;
  }

  public void setResultadoRevisionVicin(Constantes resultadoRevisionVicin) {
    this.resultadoRevisionVicin = resultadoRevisionVicin;
  }

  public String getComentarioTemporal() {
    return comentarioTemporal;
  }

  public void setComentarioTemporal(String comentarioTemporal) {
    this.comentarioTemporal = comentarioTemporal;
  }

  public String getNombreCompromiso() {
    nombreCompromiso = getIdTipoCompromiso().getValor();
    return nombreCompromiso;
  }

  public void setNombreCompromiso(String nombreCompromiso) {
    this.nombreCompromiso = nombreCompromiso;
  }

  public String getEstadoCompromiso() {
    estadoCompromiso = getIdEstadoCompromisoImpl().getValor();
    return estadoCompromiso;
  }

  public void setEstadoCompromiso(String estadoCompromiso) {
    this.estadoCompromiso = estadoCompromiso;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCompromisoImplementacion != null ? idCompromisoImplementacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CompromisoImplementacion)) {
      return false;
    }
    CompromisoImplementacion other = (CompromisoImplementacion) object;
    if ((this.idCompromisoImplementacion == null && other.idCompromisoImplementacion != null) || (this.idCompromisoImplementacion != null && !this.idCompromisoImplementacion.equals(other.idCompromisoImplementacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.CompromisoImplementacion[ idCompromisoImplementacion=" + idCompromisoImplementacion + " ]";
  }

  public Date getFechaNuevoCompromiso() {
    return fechaNuevoCompromiso;
  }

  public void setFechaNuevoCompromiso(Date fechaNuevoCompromiso) {
    this.fechaNuevoCompromiso = fechaNuevoCompromiso;
  }

  public Constantes getResultadoRetroalimentacion() {
    return resultadoRetroalimentacion;
  }

  public void setResultadoRetroalimentacion(Constantes resultadoRetroalimentacion) {
    this.resultadoRetroalimentacion = resultadoRetroalimentacion;
  }

  public Date getFechaCompromiso() {
    return fechaCompromiso;
  }

  public void setFechaCompromiso(Date fechaCompromiso) {
    this.fechaCompromiso = fechaCompromiso;
  }

  public Constantes getTipoCompromisoCorreccion() {
    return tipoCompromisoCorreccion;
  }

  public void setTipoCompromisoCorreccion(Constantes tipoCompromisoCorreccion) {
    this.tipoCompromisoCorreccion = tipoCompromisoCorreccion;
  }

  public String getNombreCompromisoCorrecion() {
    return nombreCompromisoCorrecion;
  }

  public void setNombreCompromisoCorrecion(String nombreCompromisoCorrecion) {
    this.nombreCompromisoCorrecion = nombreCompromisoCorrecion;
  }

  public CompromisoImplementacion getCompromisoImplementacionPadre() {
    return compromisoImplementacionPadre;
  }

  public void setCompromisoImplementacionPadre(CompromisoImplementacion compromisoImplementacionPadre) {
    this.compromisoImplementacionPadre = compromisoImplementacionPadre;
  }

  public CompromisoImplementacion getCompromisoImplementacionHijo() {
    return compromisoImplementacionHijo;
  }

  public void setCompromisoImplementacionHijo(CompromisoImplementacion compromisoImplementacionHijo) {
    this.compromisoImplementacionHijo = compromisoImplementacionHijo;
  }

  public Date getFechaEnvioVicin() {
    return fechaEnvioVicin;
  }

  public void setFechaEnvioVicin(Date fechaEnvioVicin) {
    this.fechaEnvioVicin = fechaEnvioVicin;
  }

}
