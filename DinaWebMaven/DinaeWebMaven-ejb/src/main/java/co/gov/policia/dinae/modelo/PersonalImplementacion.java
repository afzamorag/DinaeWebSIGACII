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
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "PERSONAL_INF_AVANCE_IMPL")
@NamedQueries({
  @NamedQuery(name = "PersonalImplementacion.findAll", query = "SELECT p FROM PersonalImplementacion p"),
  @NamedQuery(name = "PersonalImplementacion.findAllByIdidInformeImplementacion", query = "SELECT p FROM PersonalImplementacion p WHERE p.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")})
public class PersonalImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonalImplementacionSeqGen")
  @SequenceGenerator(name = "PersonalImplementacionSeqGen", sequenceName = "SEC_PERSONAL_IMPLEMENTACION", allocationSize = 1)
  @Column(name = "ID_PERSONAL_INF_AVANCE_IMPL")
  private Long idPersonalImplementacion;

  @JoinColumn(name = "ID_INFORME_AVANCE_IMPLEMENTA", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  @Column(name = "HORAS_EJECUTADAS")
  private Integer horasEjecutadas;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO_REGISTRO")
  private String usuarioRegistro;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "NOMBRE_COMPLETO")
  private String nombreCompleto;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "ORIGEN_SIAF_O_INVESTI")
  private Character origenSiafOinvestigador;

  public PersonalImplementacion() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPersonalImplementacion != null ? idPersonalImplementacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof PersonalImplementacion)) {
      return false;
    }
    PersonalImplementacion other = (PersonalImplementacion) object;
    if ((this.idPersonalImplementacion == null && other.idPersonalImplementacion != null) || (this.idPersonalImplementacion != null && !this.idPersonalImplementacion.equals(other.idPersonalImplementacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.PersonalImplementacion[ idPersonalImplementacion=" + idPersonalImplementacion + " ]";
  }

  public Long getIdPersonalImplementacion() {
    return idPersonalImplementacion;
  }

  public void setIdPersonalImplementacion(Long idPersonalImplementacion) {
    this.idPersonalImplementacion = idPersonalImplementacion;
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

  public Integer getHorasEjecutadas() {
    return horasEjecutadas;
  }

  public void setHorasEjecutadas(Integer horasEjecutadas) {
    this.horasEjecutadas = horasEjecutadas;
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

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public Character getOrigenSiafOinvestigador() {
    return origenSiafOinvestigador;
  }

  public void setOrigenSiafOinvestigador(Character origenSiafOinvestigador) {
    this.origenSiafOinvestigador = origenSiafOinvestigador;
  }

}
