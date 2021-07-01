package co.gov.policia.dinae.modelo;

import java.io.Serializable;
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
@Table(name = "KEY_PROPERTIES")
@NamedQueries({
  @NamedQuery(name = "KeyProperties.findAll", query = "SELECT k FROM KeyProperties k"),
  @NamedQuery(name = "KeyProperties.findPorClave", query = "SELECT k FROM KeyProperties k WHERE k.clave = :clave")
})
public class KeyProperties implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "CLAVE")
  private String clave;

  @Column(name = "VALOR")
  private String valor;

  public KeyProperties() {
  }

  /**
   *
   * @param clave
   * @param valor
   */
  public KeyProperties(String clave, String valor) {
    this.clave = clave;
    this.valor = valor;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.KeyProperties[ idKeyProperties=" + clave + " ]";
  }

}
