package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "CORREO_PARAMETRIZA")
@NamedQueries({
  @NamedQuery(name = "CorreoParametriza.findAll", query = "SELECT c FROM CorreoParametriza c"),
  @NamedQuery(name = "CorreoParametrizaDTO.findColdigoLabelAll", query = "SELECT NEW co.gov.policia.dinae.dto.CorreoParametrizaDTO( c.codigo, c.label ) FROM CorreoParametriza c"),
  @NamedQuery(name = "CorreoParametrizaDTO.findColdigoLabelAllProperties", query = "SELECT NEW co.gov.policia.dinae.dto.CorreoParametrizaDTO( c.codigo, c.label, c.asunto, c.formato, c.texto ) FROM CorreoParametriza c")
})
public class CorreoParametriza implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "CODIGO")
  private String codigo;

  @Column(name = "ASUNTO")
  private String asunto;

  @Column(name = "FORMATO")
  private String formato;

  @Lob()
  @Column(name = "TEXTO")
  private byte[] texto;

  @Column(name = "LABEL")
  private String label;

  public CorreoParametriza() {
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getAsunto() {
    return asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public byte[] getTexto() {
    return texto;
  }

  public void setTexto(byte[] texto) {
    this.texto = texto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (codigo != null ? codigo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CorreoParametriza)) {
      return false;
    }
    CorreoParametriza other = (CorreoParametriza) object;
    if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
      return false;
    }
    return true;
  }

  public String getFormato() {
    return formato;
  }

  public void setFormato(String formato) {
    this.formato = formato;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
