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
@Table(name = "GRUPO_INVEST_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "GrupoInvestigacionProyectoVersion.findAllPorProyecto", query = "SELECT g From GrupoInvestigacionProyectoVersion g WHERE g.proyectoVersion.idProyectoVersion = :idProyectoVersion ")
})
public class GrupoInvestigacionProyectoVersion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_GRUPO_INVEST_PROYECTO")
  private Long idGrupoInvestigacionProyecto;

  @Column(name = "FECHA_VINCULACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaVinculacion;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "ID_USUARIO_ROL")
  private Long idUsuarioRol;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "USUARIO")
  private String identificacion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne
  private ProyectoVersion proyectoVersion;

  @JoinColumn(name = "ID_GRUPO_INVESTIGACION", referencedColumnName = "ID_GRUPO_INVESTIGACION")
  @ManyToOne(optional = false)
  private GrupoInvestigacion grupoInvestigacion;

  public GrupoInvestigacionProyectoVersion() {
  }

  public GrupoInvestigacionProyectoVersion(Long idGrupoInvestigacionProyecto) {
    this.idGrupoInvestigacionProyecto = idGrupoInvestigacionProyecto;
  }

  public Long getIdGrupoInvestigacionProyecto() {
    return idGrupoInvestigacionProyecto;
  }

  public void setIdGrupoInvestigacionProyecto(Long idGrupoInvestigacionProyecto) {
    this.idGrupoInvestigacionProyecto = idGrupoInvestigacionProyecto;
  }

  public Date getFechaVinculacion() {
    return fechaVinculacion;
  }

  public void setFechaVinculacion(Date fechaVinculacion) {
    this.fechaVinculacion = fechaVinculacion;
  }

  public GrupoInvestigacion getGrupoInvestigacion() {
    return grupoInvestigacion;
  }

  public void setGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
    this.grupoInvestigacion = grupoInvestigacion;
  }

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.GrupoInvestProyecto[ idGrupoInvestProyecto=" + idGrupoInvestigacionProyecto + " ]";
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

}
