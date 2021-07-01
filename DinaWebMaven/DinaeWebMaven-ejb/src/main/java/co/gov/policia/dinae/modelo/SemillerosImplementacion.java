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
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "SEMILLEROS_IMPLEMENTACION")
@NamedQueries({
  @NamedQuery(name = "SemillerosImplementacion.findAll", query = "SELECT s FROM SemillerosImplementacion s"),
  @NamedQuery(name = "SemillerosImplementacion.findAllByImplementacionProyecto", query = "SELECT s FROM SemillerosImplementacion s WHERE s.implementacionesProyecto.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "SemillerosImplementacion.findAllBySemilleroInvestigacion", query = "SELECT s FROM SemillerosImplementacion s WHERE s.semilleroInvestigacion.idSemillero = :idSemillero")})
public class SemillerosImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SemillerosImplementacion_seq_gen")
  @SequenceGenerator(name = "SemillerosImplementacion_seq_gen", sequenceName = "SEC_SEMILLEROS_IMPLEMENTACION", allocationSize = 1)
  @Column(name = "ID_SEMILLERO_IMPLEMETACION")
  private Long idSemilleroImplemetacion;

  @JoinColumn(name = "ID_SEMILLERO_INVESTIGACION", referencedColumnName = "ID_SEMILLERO")
  @ManyToOne(fetch = FetchType.LAZY)
  private SemilleroInvestigacion semilleroInvestigacion;

  @JoinColumn(name = "ID_IMPLEMENTACION_PROY", referencedColumnName = "ID_IMPLEMENTACION_PROY")
  @ManyToOne(fetch = FetchType.LAZY)
  private ImplementacionesProyecto implementacionesProyecto;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Size(max = 50)
  @Column(name = "USUARIO")
  private String usuario;

  @Size(max = 50)
  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "ID_IMPL_PROY_ELIMINADO")
  private Long idSemilleroImplemetacionEliminado;

  public SemillerosImplementacion() {
  }

  public Long getIdSemilleroImplemetacion() {
    return idSemilleroImplemetacion;
  }

  public void setIdSemilleroImplemetacion(Long idSemilleroImplemetacion) {
    this.idSemilleroImplemetacion = idSemilleroImplemetacion;
  }

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public ImplementacionesProyecto getImplementacionesProyecto() {
    return implementacionesProyecto;
  }

  public void setImplementacionesProyecto(ImplementacionesProyecto implementacionesProyecto) {
    this.implementacionesProyecto = implementacionesProyecto;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
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
    hash += (idSemilleroImplemetacion != null ? idSemilleroImplemetacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SemillerosImplementacion)) {
      return false;
    }
    SemillerosImplementacion other = (SemillerosImplementacion) object;
    if ((this.idSemilleroImplemetacion == null && other.idSemilleroImplemetacion != null) || (this.idSemilleroImplemetacion != null && !this.idSemilleroImplemetacion.equals(other.idSemilleroImplemetacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.SemillerosImplementacion[ idSemilleroImplemetacion=" + idSemilleroImplemetacion + " ]";
  }

  public Long getIdSemilleroImplemetacionEliminado() {
    return idSemilleroImplemetacionEliminado;
  }

  public void setIdSemilleroImplemetacionEliminado(Long idSemilleroImplemetacionEliminado) {
    this.idSemilleroImplemetacionEliminado = idSemilleroImplemetacionEliminado;
  }
}
