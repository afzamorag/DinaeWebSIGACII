package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "EVENTO_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "EventoProyectoVersion.findAll", query = "SELECT e FROM EventoProyectoVersion e"),
  @NamedQuery(name = "EventoProyectoVersion.findAllByProyecto", query = "SELECT e FROM EventoProyectoVersion e WHERE e.proyectoVersion.idProyecto = :idProyecto"),
  @NamedQuery(name = "EventoProyectoVersionEventoProyectoDTO.findAllByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.EventoProyectoDTO(e.idEventoProyecto, e.costo, e.tituloEvento, e.objetivoEvento, e.codigoCiudad, e.nombreCiudad, e.fechaRegistro, e.fuenteProyectoVersion.idFuenteProyecto, e.fuenteProyectoVersion.nombreFuente, e.proyectoVersion.idProyecto, e.proyectoVersion.tituloPropuesto, e.usuarioRol.idUsuarioRol) FROM EventoProyectoVersion e WHERE e.proyectoVersion.idProyecto = :idProyecto")})
public class EventoProyectoVersion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_EVENTO_PROYECTO")
  private Long idEventoProyecto;

  @Column(name = "COSTO")
  private BigDecimal costo;

  @Column(name = "TITULO_EVENTO")
  private String tituloEvento;

  @Column(name = "OBJETIVO_EVENTO")
  private String objetivoEvento;

  @Column(name = "CODIGO_CIUDAD")
  private String codigoCiudad;

  @Column(name = "NOMBRE_CIUDAD")
  private String nombreCiudad;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne(optional = false)
  private FuenteProyectoVersion fuenteProyectoVersion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProyectoVersion proyectoVersion;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Transient
  private String tipoGastoEvento;

  @Transient
  private boolean seleccionable;

  public EventoProyectoVersion() {
  }

  public EventoProyectoVersion(Long idEventoProyecto) {
    this.idEventoProyecto = idEventoProyecto;
  }

  public EventoProyectoVersion(Long idEventoProyecto, BigDecimal costo) {
    this.idEventoProyecto = idEventoProyecto;
    this.costo = costo;
  }

  public Long getIdEventoProyecto() {
    return idEventoProyecto;
  }

  public void setIdEventoProyecto(Long idEventoProyecto) {
    this.idEventoProyecto = idEventoProyecto;
  }

  public BigDecimal getCosto() {
    return costo;
  }

  public void setCosto(BigDecimal costo) {
    this.costo = costo;
  }

  public String getTituloEvento() {
    return tituloEvento;
  }

  public void setTituloEvento(String tituloEvento) {
    this.tituloEvento = tituloEvento;
  }

  public String getObjetivoEvento() {
    return objetivoEvento;
  }

  public void setObjetivoEvento(String objetivoEvento) {
    this.objetivoEvento = objetivoEvento;
  }

  public String getCodigoCiudad() {
    return codigoCiudad;
  }

  public void setCodigoCiudad(String codigoCiudad) {
    this.codigoCiudad = codigoCiudad;
  }

  public String getNombreCiudad() {
    return nombreCiudad;
  }

  public void setNombreCiudad(String nombreCiudad) {
    this.nombreCiudad = nombreCiudad;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getTipoGastoEvento() {
    return tipoGastoEvento;
  }

  public void setTipoGastoEvento(String tipoGastoEvento) {
    this.tipoGastoEvento = tipoGastoEvento;
  }

  public boolean isSeleccionable() {
    return seleccionable;
  }

  public void setSeleccionable(boolean seleccionable) {
    this.seleccionable = seleccionable;
  }

  @Override
  public String getLlaveModel() {

    return String.valueOf(idEventoProyecto);
  }

  public FuenteProyectoVersion getFuenteProyectoVersion() {
    return fuenteProyectoVersion;
  }

  public void setFuenteProyectoVersion(FuenteProyectoVersion fuenteProyectoVersion) {
    this.fuenteProyectoVersion = fuenteProyectoVersion;
  }

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }

}
