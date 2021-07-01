package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "CONSECUTI_LIBERA_CONVOCATORI")
@NamedQueries({
  @NamedQuery(name = "ConsecutivoLiberaConvocatoria.findAll", query = "SELECT c FROM ConsecutivoLiberaConvocatoria c"),
  @NamedQuery(name = "ConsecutivoLiberaConvocatoria.findAllPorClaveFinanciaEnsayoOrdenadorPorconsecutivoLiberado", query = "SELECT c FROM ConsecutivoLiberaConvocatoria c WHERE c.claveFinanciaEnsayo = :claveFinanciaEnsayo ORDER BY c.consecutivoLiberado ASC")
})
public class ConsecutivoLiberaConvocatoria implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ConsecutivoLiberaConvocatoria_seq_gen")
  @SequenceGenerator(name = "ConsecutivoLiberaConvocatoria_seq_gen", sequenceName = "SEC_CONCECU_LIBERA_CONVOCATORI", allocationSize = 1)
  @Column(name = "ID_CONSECUTI_LIBERA_CONVOCATOR")
  private Long idConsecutiLiberaConvocator;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CLAVE_FINANCIA_ENSAYO")
  private String claveFinanciaEnsayo;

  @NotNull
  @Column(name = "CONSECUTIVO_LIBERADO")
  private Long consecutivoLiberado;

  public ConsecutivoLiberaConvocatoria() {
  }

  public ConsecutivoLiberaConvocatoria(Long idConsecutiLiberaConvocator) {
    this.idConsecutiLiberaConvocator = idConsecutiLiberaConvocator;
  }

  public ConsecutivoLiberaConvocatoria(Long idConsecutiLiberaConvocator, String claveFinanciaEnsayo, Long consecutivoLiberado) {
    this.idConsecutiLiberaConvocator = idConsecutiLiberaConvocator;
    this.claveFinanciaEnsayo = claveFinanciaEnsayo;
    this.consecutivoLiberado = consecutivoLiberado;
  }

  public Long getIdConsecutiLiberaConvocator() {
    return idConsecutiLiberaConvocator;
  }

  public void setIdConsecutiLiberaConvocator(Long idConsecutiLiberaConvocator) {
    this.idConsecutiLiberaConvocator = idConsecutiLiberaConvocator;
  }

  public String getClaveFinanciaEnsayo() {
    return claveFinanciaEnsayo;
  }

  public void setClaveFinanciaEnsayo(String claveFinanciaEnsayo) {
    this.claveFinanciaEnsayo = claveFinanciaEnsayo;
  }

  public Long getConsecutivoLiberado() {
    return consecutivoLiberado;
  }

  public void setConsecutivoLiberado(Long consecutivoLiberado) {
    this.consecutivoLiberado = consecutivoLiberado;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idConsecutiLiberaConvocator != null ? idConsecutiLiberaConvocator.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ConsecutivoLiberaConvocatoria)) {
      return false;
    }
    ConsecutivoLiberaConvocatoria other = (ConsecutivoLiberaConvocatoria) object;
    if ((this.idConsecutiLiberaConvocator == null && other.idConsecutiLiberaConvocator != null) || (this.idConsecutiLiberaConvocator != null && !this.idConsecutiLiberaConvocator.equals(other.idConsecutiLiberaConvocator))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ConsecutiLiberaConvocatori[ idConsecutiLiberaConvocator=" + idConsecutiLiberaConvocator + " ]";
  }

}
