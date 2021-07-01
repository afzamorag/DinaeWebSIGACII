package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.constantes.IConstantes;
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
@Table(name = "COMPROMISO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "CompromisoProyecto.findAll", query = "SELECT c FROM CompromisoProyecto c"),
  @NamedQuery(name = "CompromisoProyecto.findIdTipoCompromiso", query = "SELECT c.compromisoPeriodo.tipoCompromiso.idConstantes FROM CompromisoProyecto c WHERE c.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "CompromisoProyecto.finLIstaIDCompromisoProyectoAnterioresPorTipoInforme", query = "SELECT c.idCompromisoProyecto FROM CompromisoProyecto c WHERE c.estado.idConstantes = :idEstadoCompromiso AND c.compromisoPeriodo.tipoCompromiso.idConstantes = :idTipoCompromiso AND c.proyecto.idProyecto = (SELECT cp.proyecto.idProyecto FROM CompromisoProyecto cp WHERE cp.idCompromisoProyecto = :idCompromisoProyecto) AND c.compromisoPeriodo.numeroIncrementa <= (SELECT cpi.compromisoPeriodo.numeroIncrementa FROM CompromisoProyecto cpi WHERE cpi.idCompromisoProyecto = :idCompromisoProyecto)"),
  @NamedQuery(name = "CompromisoProyecto.contarCompromisoroyectoPorProyectoYlistaEstado", query = "SELECT COUNT(c) FROM CompromisoProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.estado.idConstantes IN :idListaEstadoCompromiso"),
  @NamedQuery(name = "CompromisoProyectoDTO.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.CompromisoDTO( c.compromisoPeriodo.idCompromisoPeriodo , c.idCompromisoProyecto, c.estado.valor, c.compromisoPeriodo.tipoCompromiso.valor, c.compromisoPeriodo.fecha, c.compromisoPeriodo.fecha , c.compromisoPeriodo.numeroIncrementa ) FROM CompromisoProyecto c WHERE c.proyecto.idProyecto = :idProyecto "),
  @NamedQuery(name = "CompromisoProyecto.findCompromisoProyectoAnteriorPorTipoCompromisoPeriodoYPeriodoYnumeroIncrementa", query = "SELECT c FROM CompromisoProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND  c.compromisoPeriodo.tipoCompromiso.idConstantes = :idConstantes AND c.compromisoPeriodo.periodo.idPeriodo = :idPeriodo AND c.compromisoPeriodo.numeroIncrementa = :numeroIncrementa AND c.compromisoProyectoHijo IS NULL"),
  @NamedQuery(name = "CompromisoProyecto.findCompromisoProyectoUltimoInformeAvancePorTipoCompromisoPeriodoYPeriodoYnumeroIncrementa", query = "SELECT c FROM CompromisoProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND  c.compromisoPeriodo.tipoCompromiso.idConstantes = :idConstantes AND c.compromisoPeriodo.periodo.idPeriodo = :idPeriodo AND c.compromisoProyectoHijo IS NULL ORDER BY c.compromisoPeriodo.numeroIncrementa DESC"),
  @NamedQuery(name = "CompromisoProyecto.findByIdProyectoListaEstado", query = "SELECT c FROM CompromisoProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.estado.idConstantes IN :idListaEstado ORDER BY c.compromisoPeriodo.tipoCompromiso.idConstantes ASC, c.compromisoPeriodo.numeroIncrementa ASC "),
  @NamedQuery(name = "CompromisoProyecto.findAllCompromisosVigentes", query = "SELECT DISTINCT c FROM CompromisoProyecto c WHERE c.proyecto.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND c.compromisoProyectoHijo IS NULL AND c.proyecto.codigoProyecto IS NOT NULL AND (c.compromisoPeriodo.fecha >= :fechaActual OR c.fechaNuevoCompromiso >= :fechaActual) AND c.estado.idConstantes NOT IN :estados"),
  @NamedQuery(name = "CompromisoProyecto.findAllCompromisosVigentesJefeUnidad", query = "SELECT c FROM CompromisoProyecto c WHERE c.proyecto.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND c.estado.idConstantes IN :estados"),
  @NamedQuery(name = "CompromisoProyecto.findAllCompromisosVigentesMesProyecto", query = "SELECT DISTINCT c FROM CompromisoProyecto c WHERE c.proyecto.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND c.proyecto.idProyecto = :idProyecto AND ((c.compromisoPeriodo.fecha >= :fechaActual AND c.compromisoPeriodo.fecha <= :fechaMes) OR (c.fechaNuevoCompromiso >= :fechaActual AND c.fechaNuevoCompromiso <= :fechaMes)) AND c.estado.idConstantes NOT IN :estados"),
  @NamedQuery(name = "CompromisoProyecto.findIdCompromisosProyectoInformeFinal", query = "SELECT c.idCompromisoProyecto FROM CompromisoProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoPeriodo.tipoCompromiso.idConstantes = :idTipoCompromiso AND c.estado.idConstantes = :idEstadoCompromiso")
})
//@EntityListeners({ CompromisoProyectoListener.class })
public class CompromisoProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CompromisoProyecto_seq_gen")
  @SequenceGenerator(name = "CompromisoProyecto_seq_gen", sequenceName = "SEC_COMPROMISO_PROYECTO", allocationSize = 1)
  @Column(name = "ID_COMPROMISO_PROYECTO")
  private Long idCompromisoProyecto;

  @JoinColumn(name = "ESTADO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes estado;

  @Column(name = "USUARIO_REGISTRO")
  private String usuarioRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL_REGISTRA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolRegistra;

  @JoinColumn(name = "ID_USUARIO_ROL_ACT", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "USUARIO_ACTUALIZA")
  private String usuarioActualiza;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  @JoinColumn(name = "ID_COMPROMISO_PERIODO", referencedColumnName = "ID_COMPROMISO_PERIODO")
  @ManyToOne
  private CompromisoPeriodo compromisoPeriodo;

  @JoinColumn(name = "ID_RESULTADO_VICIN", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes resultadoRevisionVicin;

  @Column(name = "COMENTARIO_TEMPORAL")
  private String comentarioTemporal;

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

  @JoinColumn(name = "ID_COMPROMISO_PADRE", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyectoPadre;

  @JoinColumn(name = "ID_COMPROMISO_HIJO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyectoHijo;

  @Column(name = "FECHA_ENVIO_VICIN")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvioVicin;

  public CompromisoProyecto() {
  }

  public CompromisoProyecto(Long idCompromisoProyecto) {
    this.idCompromisoProyecto = idCompromisoProyecto;
  }

  public Long getIdCompromisoProyecto() {
    return idCompromisoProyecto;
  }

  public void setIdCompromisoProyecto(Long idCompromisoProyecto) {
    this.idCompromisoProyecto = idCompromisoProyecto;
  }

  public Constantes getEstado() {
    return estado;
  }

  public void setEstado(Constantes estado) {
    this.estado = estado;
  }

  public String getUsuarioRegistro() {
    return usuarioRegistro;
  }

  public void setUsuarioRegistro(String usuarioRegistro) {
    this.usuarioRegistro = usuarioRegistro;
  }

  public UsuarioRol getUsuarioRolRegistra() {
    return usuarioRolRegistra;
  }

  public void setUsuarioRolRegistra(UsuarioRol usuarioRolRegistra) {
    this.usuarioRolRegistra = usuarioRolRegistra;
  }

  public UsuarioRol getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(UsuarioRol usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioActualiza() {
    return usuarioActualiza;
  }

  public void setUsuarioActualiza(String usuarioActualiza) {
    this.usuarioActualiza = usuarioActualiza;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCompromisoProyecto != null ? idCompromisoProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CompromisoProyecto)) {
      return false;
    }
    CompromisoProyecto other = (CompromisoProyecto) object;
    if ((this.idCompromisoProyecto == null && other.idCompromisoProyecto != null) || (this.idCompromisoProyecto != null && !this.idCompromisoProyecto.equals(other.idCompromisoProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.CompromisoProyecto[ idCompromisoProyecto=" + idCompromisoProyecto + " ]";
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public CompromisoPeriodo getCompromisoPeriodo() {
    return compromisoPeriodo;
  }

  public void setCompromisoPeriodo(CompromisoPeriodo compromisoPeriodo) {
    this.compromisoPeriodo = compromisoPeriodo;
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

  public CompromisoProyecto getCompromisoProyectoPadre() {
    return compromisoProyectoPadre;
  }

  public void setCompromisoProyectoPadre(CompromisoProyecto compromisoProyectoPadre) {
    this.compromisoProyectoPadre = compromisoProyectoPadre;
  }

  public CompromisoProyecto getCompromisoProyectoHijo() {
    return compromisoProyectoHijo;
  }

  public void setCompromisoProyectoHijo(CompromisoProyecto compromisoProyectoHijo) {
    this.compromisoProyectoHijo = compromisoProyectoHijo;
  }

  @Transient
  public String getNombreCompromisoCorreccionNumeroIncrementa() {

    if (nombreCompromisoCorrecion != null && nombreCompromisoCorrecion.trim().length() > 0) {
      return nombreCompromisoCorrecion;
    }

    if (compromisoPeriodo == null || compromisoPeriodo.getTipoCompromiso() == null) {
      return "";
    }
    return compromisoPeriodo.getTipoCompromiso().getValor().concat(
            compromisoPeriodo.getTipoCompromiso().getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
            ? " ".concat(String.valueOf(compromisoPeriodo.getNumeroIncrementa())) : "");

  }

  @Transient
  public Date getFechaCompromisoCorreccionNumeroIncrementa() {

    if (fechaCompromiso != null) {
      return fechaCompromiso;
    }

    if (compromisoPeriodo == null) {
      return null;
    }
    return compromisoPeriodo.getFecha();

  }

  public Date getFechaEnvioVicin() {
    return fechaEnvioVicin;
  }

  public void setFechaEnvioVicin(Date fechaEnvioVicin) {
    this.fechaEnvioVicin = fechaEnvioVicin;
  }

}
