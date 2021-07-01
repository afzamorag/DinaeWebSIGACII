package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "CONCECUTI_LIBERA_CONVOCATORI")
@NamedQueries({
  @javax.persistence.NamedQuery(name = "ConcecutivoLiberaConvocatoria.findAll", query = "SELECT c FROM ConcecutivoLiberaConvocatoria c"),
  @javax.persistence.NamedQuery(name = "ConcecutivoLiberaConvocatoria.findAllPorClaveFinanciaEnsayoOrdenadorPorconcecutivoLiberado", query = "SELECT c FROM ConcecutivoLiberaConvocatoria c WHERE c.claveFinanciaEnsayo = :claveFinanciaEnsayo ORDER BY c.concecutivoLiberado ASC")})
public class ConcecutivoLiberaConvocatoria
        implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ConcecutivoLiberaConvocatoria_seq_gen")
  @SequenceGenerator(name = "ConcecutivoLiberaConvocatoria_seq_gen", sequenceName = "SEC_CONCECU_LIBERA_CONVOCATORI", allocationSize = 1)
  @Column(name = "ID_CONCECUTI_LIBERA_CONVOCATOR")
  private Long idConcecutiLiberaConvocator;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CLAVE_FINANCIA_ENSAYO")
  private String claveFinanciaEnsayo;

  @NotNull
  @Column(name = "CONCECUTIVO_LIBERADO")
  private Long concecutivoLiberado;

  public ConcecutivoLiberaConvocatoria() {
  }

  public ConcecutivoLiberaConvocatoria(Long idConcecutiLiberaConvocator) {
    this.idConcecutiLiberaConvocator = idConcecutiLiberaConvocator;
  }

  public ConcecutivoLiberaConvocatoria(Long idConcecutiLiberaConvocator, String claveFinanciaEnsayo, Long concecutivoLiberado) {
    this.idConcecutiLiberaConvocator = idConcecutiLiberaConvocator;
    this.claveFinanciaEnsayo = claveFinanciaEnsayo;
    this.concecutivoLiberado = concecutivoLiberado;
  }

  public Long getIdConcecutiLiberaConvocator() {
    return this.idConcecutiLiberaConvocator;
  }

  public void setIdConcecutiLiberaConvocator(Long idConcecutiLiberaConvocator) {
    this.idConcecutiLiberaConvocator = idConcecutiLiberaConvocator;
  }

  public String getClaveFinanciaEnsayo() {
    return this.claveFinanciaEnsayo;
  }

  public void setClaveFinanciaEnsayo(String claveFinanciaEnsayo) {
    this.claveFinanciaEnsayo = claveFinanciaEnsayo;
  }

  public Long getConcecutivoLiberado() {
    return this.concecutivoLiberado;
  }

  public void setConcecutivoLiberado(Long concecutivoLiberado) {
    this.concecutivoLiberado = concecutivoLiberado;
  }

  public int hashCode() {
    int hash = 0;
    hash += (this.idConcecutiLiberaConvocator != null ? this.idConcecutiLiberaConvocator.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object) {
    if (!(object instanceof ConcecutivoLiberaConvocatoria)) {
      return false;
    }
    ConcecutivoLiberaConvocatoria other = (ConcecutivoLiberaConvocatoria) object;
    if (((this.idConcecutiLiberaConvocator == null) && (other.idConcecutiLiberaConvocator != null)) || ((this.idConcecutiLiberaConvocator != null) && (!this.idConcecutiLiberaConvocator.equals(other.idConcecutiLiberaConvocator)))) {
      return false;
    }
    return true;
  }

  public String toString() {
    return "co.gov.policia.dinae.modelo.ConcecutiLiberaConvocatori[ idConcecutiLiberaConvocator=" + this.idConcecutiLiberaConvocator + " ]";
  }
}
