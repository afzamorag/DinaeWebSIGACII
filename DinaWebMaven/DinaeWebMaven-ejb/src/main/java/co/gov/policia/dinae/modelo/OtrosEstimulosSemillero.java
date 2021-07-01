package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "OTROS_ESTIMULOS_SEMILLERO")
@NamedQueries({
  @NamedQuery(name = "OtrosEstimulosSemillero.findAll", query = "SELECT o FROM OtrosEstimulosSemillero o"),
  @NamedQuery(name = "OtrosEstimulosSemillero.findBySemilleroInvestigacion", query = "SELECT o FROM OtrosEstimulosSemillero o WHERE o.semilleroInvestigacion.idSemillero = :idSemillero ORDER BY o.fechaOtorgamiento ASC")})
public class OtrosEstimulosSemillero implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OtrosEstimulosSemillero_seq_gen")
  @SequenceGenerator(name = "OtrosEstimulosSemillero_seq_gen", sequenceName = "SEC_OTROS_ESTIMULOS_SEMILLERO", allocationSize = 1)
  @Column(name = "ID_OTROS_ESTIMULOS_SEMILLERO")
  private Long idOtrosEstimulosSemillero;

  @Basic(optional = false)
  @Column(name = "MOTIVO_OTORGAMIENTO")
  private String motivoOtorgamiento;

  @Basic(optional = false)
  @Column(name = "DESCRIPCION_TIPO_ESTIMULO")
  private String descripcionTipoEstimulo;

  @Basic(optional = false)
  @Column(name = "FECHA_OTORGAMIENTO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaOtorgamiento;

  @Basic(optional = false)
  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Basic(optional = false)
  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Basic(optional = false)
  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_USUARIO_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @JoinColumn(name = "ID_SEMILLERO", referencedColumnName = "ID_SEMILLERO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SemilleroInvestigacion semilleroInvestigacion;

  public OtrosEstimulosSemillero() {
  }

  public OtrosEstimulosSemillero(Long idOtrosEstimulosSemillero) {
    this.idOtrosEstimulosSemillero = idOtrosEstimulosSemillero;
  }

  public OtrosEstimulosSemillero(Long idOtrosEstimulosSemillero, String motivoOtorgamiento, String descripcionTipoEstimulo, Date fechaOtorgamiento, Date fechaCreacion, String maquina, String maquinaActualiza, UsuarioRol usuarioRol, SemilleroInvestigacion semilleroInvestigacion) {
    this.idOtrosEstimulosSemillero = idOtrosEstimulosSemillero;
    this.motivoOtorgamiento = motivoOtorgamiento;
    this.descripcionTipoEstimulo = descripcionTipoEstimulo;
    this.fechaOtorgamiento = fechaOtorgamiento;
    this.fechaCreacion = fechaCreacion;
    this.maquina = maquina;
    this.maquinaActualiza = maquinaActualiza;
    this.usuarioRol = usuarioRol;
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public Long getIdOtrosEstimulosSemillero() {
    return idOtrosEstimulosSemillero;
  }

  public void setIdOtrosEstimulosSemillero(Long idOtrosEstimulosSemillero) {
    this.idOtrosEstimulosSemillero = idOtrosEstimulosSemillero;
  }

  public String getMotivoOtorgamiento() {
    return motivoOtorgamiento;
  }

  public void setMotivoOtorgamiento(String motivoOtorgamiento) {
    this.motivoOtorgamiento = motivoOtorgamiento;
  }

  public String getDescripcionTipoEstimulo() {
    return descripcionTipoEstimulo;
  }

  public void setDescripcionTipoEstimulo(String descripcionTipoEstimulo) {
    this.descripcionTipoEstimulo = descripcionTipoEstimulo;
  }

  public Date getFechaOtorgamiento() {
    return fechaOtorgamiento;
  }

  public void setFechaOtorgamiento(Date fechaOtorgamiento) {
    this.fechaOtorgamiento = fechaOtorgamiento;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idOtrosEstimulosSemillero != null ? idOtrosEstimulosSemillero.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof OtrosEstimulosSemillero)) {
      return false;
    }
    OtrosEstimulosSemillero other = (OtrosEstimulosSemillero) object;
    if ((this.idOtrosEstimulosSemillero == null && other.idOtrosEstimulosSemillero != null) || (this.idOtrosEstimulosSemillero != null && !this.idOtrosEstimulosSemillero.equals(other.idOtrosEstimulosSemillero))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.OtrosEstimulosSemillero[ idOtrosEstimulosSemillero=" + idOtrosEstimulosSemillero + " ]";
  }

}
