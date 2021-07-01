package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "GRADOS_VALORES")
@NamedQueries({
  @NamedQuery(name = "GradosValores.findAll", query = "SELECT c FROM GradosValores c"),
  @NamedQuery(name = "GradosValores.findGradValorPorAnioVegenciaYGrado", query = "SELECT c FROM GradosValores c WHERE c.anioVigencia = :anioVigencia AND c.grado = :grado")
})
public class GradosValores implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_GRADO_VALOR")
  private Long idGradosValores;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "VALOR_HORA")
  private BigDecimal valorHora;

  @Column(name = "ANIO_VIGENCIA")
  private Integer anioVigencia;

  public GradosValores() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idGradosValores != null ? idGradosValores.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof GradosValores)) {
      return false;
    }
    GradosValores other = (GradosValores) object;
    if ((this.idGradosValores == null && other.idGradosValores != null) || (this.idGradosValores != null && !this.idGradosValores.equals(other.idGradosValores))) {
      return false;
    }
    return true;
  }

  public Long getIdGradosValores() {
    return idGradosValores;
  }

  public void setIdGradosValores(Long idGradosValores) {
    this.idGradosValores = idGradosValores;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public BigDecimal getValorHora() {
    return valorHora;
  }

  public void setValorHora(BigDecimal valorHora) {
    this.valorHora = valorHora;
  }

  public Integer getAnioVigencia() {
    return anioVigencia;
  }

  public void setAnioVigencia(Integer anioVigencia) {
    this.anioVigencia = anioVigencia;
  }

}
