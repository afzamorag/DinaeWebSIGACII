package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
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
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "FUENTE_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "FuenteProyectoVersion.findAll", query = "SELECT f From FuenteProyectoVersion f"),
  @NamedQuery(name = "FuenteProyectoVersion.findFuentesByProyecto", query = "SELECT f From FuenteProyectoVersion f WHERE f.proyectoVersion.idProyecto = :idProyecto ORDER BY f.idFuenteProyecto"),
  @NamedQuery(name = "FuenteProyectoVersion.findFuentesByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.FuenteProyectoDTO(f.idFuenteProyecto, f.nombreFuente, f.fuenteBase, f.usuarioRol.idUsuarioRol, f.usuarioRol.identificadorUsuario, f.usuarioRol.rol.idRol, f.proyectoVersion.idProyecto, f.proyectoVersion.codigoProyecto, f.proyectoVersion.tituloPropuesto, f.tipoFuente.idConstantes, f.tipoFuente.valor, f.fechaRegistro) From FuenteProyectoVersion f WHERE f.proyectoVersion.idProyecto = :idProyecto ORDER BY f.idFuenteProyecto"),
  @NamedQuery(name = "FuenteProyectoVersion.countFuentesBaseByProyecto", query = "SELECT COUNT( f ) From FuenteProyectoVersion f WHERE f.proyectoVersion.idProyecto = :idProyecto AND f.fuenteBase = 'Y'")
})
public class FuenteProyectoVersion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_FUENTE_PROYECTO")
  private Long idFuenteProyecto;

  @Size(min = 1, max = 100)
  @Column(name = "NOMBRE_FUENTE")
  private String nombreFuente;

  @Column(name = "FUENTE_BASE")
  private Character fuenteBase;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProyectoVersion proyectoVersion;

  @JoinColumn(name = "ID_TIPO_FUENTE", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes tipoFuente;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Transient
  private boolean muestraLink;

  public FuenteProyectoVersion() {
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public String getNombreFuente() {
    return nombreFuente;
  }

  public void setNombreFuente(String nombreFuente) {
    this.nombreFuente = nombreFuente;
  }

  public Character getFuenteBase() {
    return fuenteBase;
  }

  public void setFuenteBase(Character fuenteBase) {
    this.fuenteBase = fuenteBase;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }

  public Constantes getTipoFuente() {
    return tipoFuente;
  }

  public void setTipoFuente(Constantes tipoFuente) {
    this.tipoFuente = tipoFuente;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public boolean isMuestraLink() {
    return muestraLink;
  }

  public void setMuestraLink(boolean muestraLink) {
    this.muestraLink = muestraLink;
  }

  @Override
  public String getLlaveModel() {
    return String.valueOf(idFuenteProyecto);
  }

}
