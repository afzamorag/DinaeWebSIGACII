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
@Table(name = "TALENTO_ESTIMULO_SEMILLERO")
@NamedQueries({
  @NamedQuery(name = "TalentoEstimuloSemillero.findAll", query = "SELECT t FROM TalentoEstimuloSemillero t"),
  @NamedQuery(name = "TalentoEstimuloSemillero.findByOtrosEstimulosSemillero", query = "SELECT t FROM TalentoEstimuloSemillero t WHERE t.otrosEstimulosSemillero.idOtrosEstimulosSemillero = :idOtrosEstimulosSemillero")})
public class TalentoEstimuloSemillero implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TalentoEstimuloSemillero_seq_gen")
  @SequenceGenerator(name = "TalentoEstimuloSemillero_seq_gen", sequenceName = "SEC_TALENTO_ESTIMULO_SEMILLERO", allocationSize = 1)
  @Column(name = "ID_TALENTO_ESTIMULO_SEMILLERO")
  private Long idTalentoEstimuloSemillero;

  @Basic(optional = false)
  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Basic(optional = false)
  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_RECURSO_HUMANO_SEMILLERO", referencedColumnName = "ID_RECURSO_HUMANO_SEMI")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private RecursoHumanoSemillero recursoHumanoSemillero;

  @JoinColumn(name = "ID_OTROS_ESTIMULOS_SEMILLERO", referencedColumnName = "ID_OTROS_ESTIMULOS_SEMILLERO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private OtrosEstimulosSemillero otrosEstimulosSemillero;

  public TalentoEstimuloSemillero() {
  }

  public TalentoEstimuloSemillero(Long idTalentoEstimuloSemillero) {
    this.idTalentoEstimuloSemillero = idTalentoEstimuloSemillero;
  }

  public TalentoEstimuloSemillero(Long idTalentoEstimuloSemillero, RecursoHumanoSemillero recursoHumanoSemillero, Date fechaCreacion, String maquina, UsuarioRol usuarioRol, OtrosEstimulosSemillero otrosEstimulosSemillero) {
    this.idTalentoEstimuloSemillero = idTalentoEstimuloSemillero;
    this.recursoHumanoSemillero = recursoHumanoSemillero;
    this.fechaCreacion = fechaCreacion;
    this.maquina = maquina;
    this.usuarioRol = usuarioRol;
    this.otrosEstimulosSemillero = otrosEstimulosSemillero;
  }

  public Long getIdTalentoEstimuloSemillero() {
    return idTalentoEstimuloSemillero;
  }

  public void setIdTalentoEstimuloSemillero(Long idTalentoEstimuloSemillero) {
    this.idTalentoEstimuloSemillero = idTalentoEstimuloSemillero;
  }

  public RecursoHumanoSemillero getRecursoHumanoSemillero() {
    return recursoHumanoSemillero;
  }

  public void setRecursoHumanoSemillero(RecursoHumanoSemillero recursoHumanoSemillero) {
    this.recursoHumanoSemillero = recursoHumanoSemillero;
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

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public OtrosEstimulosSemillero getOtrosEstimulosSemillero() {
    return otrosEstimulosSemillero;
  }

  public void setOtrosEstimulosSemillero(OtrosEstimulosSemillero otrosEstimulosSemillero) {
    this.otrosEstimulosSemillero = otrosEstimulosSemillero;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idTalentoEstimuloSemillero != null ? idTalentoEstimuloSemillero.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TalentoEstimuloSemillero)) {
      return false;
    }
    TalentoEstimuloSemillero other = (TalentoEstimuloSemillero) object;
    if ((this.idTalentoEstimuloSemillero == null && other.idTalentoEstimuloSemillero != null) || (this.idTalentoEstimuloSemillero != null && !this.idTalentoEstimuloSemillero.equals(other.idTalentoEstimuloSemillero))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.TalentoEstimuloSemillero[ idTalentoEstimuloSemillero=" + idTalentoEstimuloSemillero + " ]";
  }

}
