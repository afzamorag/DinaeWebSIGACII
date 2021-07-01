package co.gov.policia.dinae.modelo;

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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INSTITUCIONES_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "InstitucionesProyectoVersionInstitucionesProyectoDTO.findPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.InstitucionesProyectoDTO( s.idInstitucionesProyecto, s.aporteInvestigicacion, s.institucion.idConstantes, s.institucion.valor, s.proyectoVersion.idProyecto, s.valorOtroTipo ) FROM InstitucionesProyectoVersion s  WHERE s.proyectoVersion.idProyectoVersion = :idProyectoVersion"),})
public class InstitucionesProyectoVersion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_INSTITUCION_PROYECTO")
  private Long idInstitucionesProyecto;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_INSTITUCION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes institucion;

  @Column(name = "APORTE_INVESTIGACION")
  private String aporteInvestigicacion;

  @Column(name = "VALOR_OTRO_TIPO")
  private String valorOtroTipo;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ProyectoVersion proyectoVersion;

  public InstitucionesProyectoVersion() {
  }

  public InstitucionesProyectoVersion(Long idInstitucionesProyecto) {
    this.idInstitucionesProyecto = idInstitucionesProyecto;
  }

  public Long getIdInstitucionesProyecto() {
    return idInstitucionesProyecto;
  }

  public void setIdInstitucionesProyecto(Long idInstitucionesProyecto) {
    this.idInstitucionesProyecto = idInstitucionesProyecto;
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

  public Constantes getInstitucion() {
    return institucion;
  }

  public void setInstitucion(Constantes institucion) {
    this.institucion = institucion;
  }

  public String getAporteInvestigicacion() {
    return aporteInvestigicacion;
  }

  public void setAporteInvestigicacion(String aporteInvestigicacion) {
    this.aporteInvestigicacion = aporteInvestigicacion;
  }

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }

  public String getValorOtroTipo() {
    return valorOtroTipo;
  }

  public void setValorOtroTipo(String valorOtroTipo) {
    this.valorOtroTipo = valorOtroTipo;
  }

}
