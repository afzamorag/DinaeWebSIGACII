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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "DETALLE_EVALUACION")
public class DetalleEvaluacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DetalleEvaluacion_seq_gen")
  @SequenceGenerator(name = "DetalleEvaluacion_seq_gen", sequenceName = "SEC_DETALLE_EVALUACION", allocationSize = 1)
  @Column(name = "ID_DETALLE_EVALUACION")
  private Long idDetalleEvaluacion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "SUGERENCIA_RECOMENDACION", nullable = false, length = 512)
  private String sugerenciaRecomendacion;

  @Column(name = "CARGO_EVALUADOR")
  private String cargoEvaluador;

  @Column(name = "INSTITUCION")
  private String institucion;

  @Column(name = "IDENTIFICACION_EVALUADOR")
  private String identificacionEvaluador;

  public DetalleEvaluacion() {
  }

  public Long getIdDetalleEvaluacion() {
    return idDetalleEvaluacion;
  }

  public void setIdDetalleEvaluacion(Long idDetalleEvaluacion) {
    this.idDetalleEvaluacion = idDetalleEvaluacion;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
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

  public String getSugerenciaRecomendacion() {
    return sugerenciaRecomendacion;
  }

  public void setSugerenciaRecomendacion(String sugerenciaRecomendacion) {
    this.sugerenciaRecomendacion = sugerenciaRecomendacion;
  }

  public String getCargoEvaluador() {
    return cargoEvaluador;
  }

  public void setCargoEvaluador(String cargoEvaluador) {
    this.cargoEvaluador = cargoEvaluador;
  }

  public String getInstitucion() {
    return institucion;
  }

  public void setInstitucion(String institucion) {
    this.institucion = institucion;
  }

  public String getIdentificacionEvaluador() {
    return identificacionEvaluador;
  }

  public void setIdentificacionEvaluador(String identificacionEvaluador) {
    this.identificacionEvaluador = identificacionEvaluador;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idDetalleEvaluacion != null ? idDetalleEvaluacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof DetalleEvaluacion)) {
      return false;
    }
    DetalleEvaluacion other = (DetalleEvaluacion) object;
    if ((this.idDetalleEvaluacion == null && other.idDetalleEvaluacion != null) || (this.idDetalleEvaluacion != null && !this.idDetalleEvaluacion.equals(other.idDetalleEvaluacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.DetalleEvaluacion[ id=" + idDetalleEvaluacion + " ]";
  }

}
