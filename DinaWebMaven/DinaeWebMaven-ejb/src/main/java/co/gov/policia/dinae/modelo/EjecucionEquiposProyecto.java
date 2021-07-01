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
 * @author cguzman
 */
@Entity
@Table(name = "EJECUCION_EQUIPOS_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "EjecucionEquiposProyecto.findAll", query = "SELECT e FROM EjecucionEquiposProyecto e"),
  @NamedQuery(name = "EjecucionEquiposProyecto.findEquipoRelacionadoProyecto", query = "SELECT e FROM EjecucionEquiposProyecto e WHERE e.informeAvance.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EjecucionEquiposProyecto.findEquipoRelacionadoImplementacionProyecto", query = "SELECT e FROM EjecucionEquiposProyecto e WHERE e.informeAvanceImplementacion.implementacionesProyecto.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "EjecucionEquiposProyecto.findEquipoRelacionadoInformeProyecto", query = "SELECT e FROM EjecucionEquiposProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance AND e.equipoInvestigacion.idEquipoInvestigacion = :idEquipoInvestigacion"),
  @NamedQuery(name = "EjecucionEquiposProyecto.findEquipoRelacionadoInformeImplementacionProyecto", query = "SELECT e FROM EjecucionEquiposProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND e.equipoInvestigacion.idEquipoInvestigacion = :idEquipoInvestigacion"),
  @NamedQuery(name = "EjecucionEquiposProyecto.deleteEquipoRelacionadoInformeProyecto", query = "DELETE FROM EjecucionEquiposProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionEquiposProyecto.SelectEquipoRelacionadoInformeProyecto", query = "SELECT e FROM EjecucionEquiposProyecto e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionEquiposProyecto.deleteEquipoRelacionadoInformeImplementacionProyecto", query = "DELETE FROM EjecucionEquiposProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "EjecucionEquiposProyecto.SelectEquipoRelacionadoInformeImplementacionProyecto", query = "SELECT e FROM EjecucionEquiposProyecto e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")

})
public class EjecucionEquiposProyecto implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EjecucionEquiposProyecto_seq_gen")
  @SequenceGenerator(name = "EjecucionEquiposProyecto_seq_gen", sequenceName = "SEC_EJECUCION_EQUIPOS_PROYECTO", allocationSize = 1)
  @Column(name = "ID_EJECUCION_EQUIP_PROY")
  private Long idEjecucionEquipProy;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO_REGISTRA")
  private String usuarioRegistra;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_INFORME_AVANCE", referencedColumnName = "ID_INFORME_AVANCE")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private InformeAvance informeAvance;

  @JoinColumn(name = "ID_EQUIPO_INVESTIGACION", referencedColumnName = "ID_EQUIPO_INVESTIGACION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private EquiposInvestigacion equipoInvestigacion;

  @JoinColumn(name = "ID_INFORME_IMPLEMENTACION", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  public EjecucionEquiposProyecto() {
  }

  public EjecucionEquiposProyecto(Long idEjecucionEquipProy) {
    this.idEjecucionEquipProy = idEjecucionEquipProy;
  }

  public EjecucionEquiposProyecto(Long idEjecucionEquipProy, EquiposInvestigacion equipoInvestigacion) {
    this.idEjecucionEquipProy = idEjecucionEquipProy;
    this.equipoInvestigacion = equipoInvestigacion;
  }

  public Long getIdEjecucionEquipProy() {
    return idEjecucionEquipProy;
  }

  public void setIdEjecucionEquipProy(Long idEjecucionEquipProy) {
    this.idEjecucionEquipProy = idEjecucionEquipProy;
  }

  public EquiposInvestigacion getEquipoInvestigacion() {
    return equipoInvestigacion;
  }

  public void setEquipoInvestigacion(EquiposInvestigacion equipoInvestigacion) {
    this.equipoInvestigacion = equipoInvestigacion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public InformeAvance getInformeAvance() {
    return informeAvance;
  }

  public void setIdInformeAvance(InformeAvance informeAvance) {
    this.informeAvance = informeAvance;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEjecucionEquipProy != null ? idEjecucionEquipProy.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EjecucionEquiposProyecto)) {
      return false;
    }
    EjecucionEquiposProyecto other = (EjecucionEquiposProyecto) object;
    if ((this.idEjecucionEquipProy == null && other.idEjecucionEquipProy != null) || (this.idEjecucionEquipProy != null && !this.idEjecucionEquipProy.equals(other.idEjecucionEquipProy))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EjecucionEquiposProyecto[ idEjecucionEquipProy=" + idEjecucionEquipProy + " ]";
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
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
