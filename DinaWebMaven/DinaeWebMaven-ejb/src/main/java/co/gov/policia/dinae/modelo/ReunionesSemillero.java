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
@Table(name = "REUNIONES_SEMILLERO")
@NamedQueries({
  @NamedQuery(name = "ReunionesSemillero.findAll", query = "SELECT r FROM ReunionesSemillero r"),
  @NamedQuery(name = "ReunionesSemillero.findBySemillero", query = "SELECT r FROM ReunionesSemillero r WHERE r.semilleroInvestigacion.idSemillero = :idSemillero")})
public class ReunionesSemillero implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReunionesSemillero_seq_gen")
  @SequenceGenerator(name = "ReunionesSemillero_seq_gen", sequenceName = "SEC_REUNIONES_SEMILLERO", allocationSize = 1)
  @Column(name = "ID_REUNION_SEMILLERO")
  private Long idReunionSemillero;

  @Column(name = "HORA_INICIO")
  private String horaInicio;

  @Column(name = "HORA_FIN")
  private String horaFin;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO_REGISTRO")
  private String usuarioRegistro;

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
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_USUARIO_ROL_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @JoinColumn(name = "ID_SEMILLERO", referencedColumnName = "ID_SEMILLERO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SemilleroInvestigacion semilleroInvestigacion;

  @JoinColumn(name = "DIA", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes dia;

  public ReunionesSemillero() {
  }

  public ReunionesSemillero(Long idReunionSemillero) {
    this.idReunionSemillero = idReunionSemillero;
  }

  public ReunionesSemillero(Long idReunionSemillero, String horaInicio, String horaFin, Date fechaRegistro, SemilleroInvestigacion semilleroInvestigacion, Constantes dia) {
    this.idReunionSemillero = idReunionSemillero;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.fechaRegistro = fechaRegistro;
    this.semilleroInvestigacion = semilleroInvestigacion;
    this.dia = dia;
  }

  public Long getIdReunionSemillero() {
    return idReunionSemillero;
  }

  public void setIdReunionSemillero(Long idReunionSemillero) {
    this.idReunionSemillero = idReunionSemillero;
  }

  public String getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(String horaInicio) {
    this.horaInicio = horaInicio;
  }

  public String getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(String horaFin) {
    this.horaFin = horaFin;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuarioRegistro() {
    return usuarioRegistro;
  }

  public void setUsuarioRegistro(String usuarioRegistro) {
    this.usuarioRegistro = usuarioRegistro;
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

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public UsuarioRol getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(UsuarioRol usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
  }

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public Constantes getDia() {
    return dia;
  }

  public void setDia(Constantes dia) {
    this.dia = dia;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idReunionSemillero != null ? idReunionSemillero.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ReunionesSemillero)) {
      return false;
    }
    ReunionesSemillero other = (ReunionesSemillero) object;
    if ((this.idReunionSemillero == null && other.idReunionSemillero != null) || (this.idReunionSemillero != null && !this.idReunionSemillero.equals(other.idReunionSemillero))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ReunionesSemillero[ idReunionSemillero=" + idReunionSemillero + " ]";
  }

}
