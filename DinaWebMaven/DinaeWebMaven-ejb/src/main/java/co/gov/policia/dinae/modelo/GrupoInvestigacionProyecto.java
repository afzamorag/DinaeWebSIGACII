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
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "GRUPO_INVEST_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "GrupoInvestigacionProyecto.findAll", query = "SELECT g FROM GrupoInvestigacionProyecto g"),
  @NamedQuery(name = "GrupoInvestigacionProyecto.EliminarPorId", query = "DELETE FROM GrupoInvestigacionProyecto g WHERE g.idGrupoInvestigacionProyecto = :idGrupoInvestigacionProyecto"),
  @NamedQuery(name = "GrupoInvestigacionProyectoDTO.findAll", query = "SELECT NEW co.gov.policia.dinae.dto.GrupoInvestigacionProyectoDTO( g.idGrupoInvestigacionProyecto, g.fechaVinculacion, g.fechaRegistro, g.proyecto.idProyecto, g.grupoInvestigacion.idGrupoInvestigacion,g.grupoInvestigacion.descripcion, g.grupoInvestigacion.codigoGrupo, g.grupoInvestigacion.fechaRegistroGrupo ) FROM GrupoInvestigacionProyecto g WHERE g.proyecto.idProyecto = :idProyecto "),
  @NamedQuery(name = "GrupoInvestigacionProyecto.findAllPorProyecto", query = "SELECT g FROM GrupoInvestigacionProyecto g WHERE g.proyecto.idProyecto = :idProyecto ")
})
public class GrupoInvestigacionProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GrupoInvestigacionProyecto_seq_gen")
  @SequenceGenerator(name = "GrupoInvestigacionProyecto_seq_gen", sequenceName = "SEC_GRUPO_INVEST_PROYECTO", allocationSize = 1)
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

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  @JoinColumn(name = "ID_GRUPO_INVESTIGACION", referencedColumnName = "ID_GRUPO_INVESTIGACION")
  @ManyToOne(optional = false)
  private GrupoInvestigacion grupoInvestigacion;

  public GrupoInvestigacionProyecto() {
  }

  public GrupoInvestigacionProyecto(Long idGrupoInvestigacionProyecto) {
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

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public GrupoInvestigacion getGrupoInvestigacion() {
    return grupoInvestigacion;
  }

  public void setGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
    this.grupoInvestigacion = grupoInvestigacion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idGrupoInvestigacionProyecto != null ? idGrupoInvestigacionProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof GrupoInvestigacionProyecto)) {
      return false;
    }
    GrupoInvestigacionProyecto other = (GrupoInvestigacionProyecto) object;
    if ((this.idGrupoInvestigacionProyecto == null && other.idGrupoInvestigacionProyecto != null) || (this.idGrupoInvestigacionProyecto != null && !this.idGrupoInvestigacionProyecto.equals(other.idGrupoInvestigacionProyecto))) {
      return false;
    }
    return true;
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
