package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "CRITERIO_EVALUACION")
@NamedQueries({
  @NamedQuery(name = "CriterioEvaluacion.findAll", query = "SELECT c FROM CriterioEvaluacion c"),
  @NamedQuery(name = "CriterioEvaluacion.findAllPorEstado", query = "SELECT c FROM CriterioEvaluacion c WHERE c.estado = :estado AND c.tipo = :tipo"),
  @NamedQuery(name = "CriterioEvaluacionDTO.findAllPorEstadoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.CriterioEvaluacionDTO(c.idCriterioEvaluacion, c.nombreCriterio, c.descripcionCriterio, c.tipo, c.estado, c.valor) FROM CriterioEvaluacion c WHERE c.estado = :estado AND c.tipo = :tipo"),
  @NamedQuery(name = "CriterioEvaluacion.findAllPorTipo", query = "SELECT c FROM CriterioEvaluacion c WHERE c.tipo = :tipo")
})
public class CriterioEvaluacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_CRITERIO_EVALUACION")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CriterioEvaluacion_seq_gen")
  @SequenceGenerator(name = "CriterioEvaluacion_seq_gen", sequenceName = "SEC_CRITERIO_EVALUACION", allocationSize = 1)
  private Long idCriterioEvaluacion;

  @Column(name = "NOMBRE_CRITERIO")
  private String nombreCriterio;

  @Column(name = "DESCRIPCION_CRITERIO")
  private String descripcionCriterio;

  @Column(name = "VALOR")
  private BigDecimal valor;

  @Column(name = "TIPO")
  private String tipo;

  @Column(name = "ESTADO")
  private String estado;

  @Transient
  private BigDecimal evaluacion;

  @Transient
  private String cualificacion;

  public CriterioEvaluacion() {
  }

  public CriterioEvaluacion(Long idCriterioEvaluacion) {
    this.idCriterioEvaluacion = idCriterioEvaluacion;
  }

  public Long getIdCriterioEvaluacion() {
    return idCriterioEvaluacion;
  }

  public void setIdCriterioEvaluacion(Long idCriterioEvaluacion) {
    this.idCriterioEvaluacion = idCriterioEvaluacion;
  }

  public String getNombreCriterio() {
    return nombreCriterio;
  }

  public void setNombreCriterio(String nombreCriterio) {
    this.nombreCriterio = nombreCriterio;
  }

  public String getDescripcionCriterio() {
    return descripcionCriterio;
  }

  public void setDescripcionCriterio(String descripcionCriterio) {
    this.descripcionCriterio = descripcionCriterio;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public BigDecimal getEvaluacion() {
    return evaluacion;
  }

  public void setEvaluacion(BigDecimal evaluacion) {
    this.evaluacion = evaluacion;
  }

  public String getCualificacion() {
    return cualificacion;
  }

  public void setCualificacion(String cualificacion) {
    this.cualificacion = cualificacion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCriterioEvaluacion != null ? idCriterioEvaluacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CriterioEvaluacion)) {
      return false;
    }
    CriterioEvaluacion other = (CriterioEvaluacion) object;
    if ((this.idCriterioEvaluacion == null && other.idCriterioEvaluacion != null) || (this.idCriterioEvaluacion != null && !this.idCriterioEvaluacion.equals(other.idCriterioEvaluacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.CriterioEvaluacion[ idCriterioEvaluacion=" + idCriterioEvaluacion + " ]";
  }

}
