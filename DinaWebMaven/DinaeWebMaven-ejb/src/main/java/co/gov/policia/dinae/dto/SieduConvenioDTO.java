package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class SieduConvenioDTO implements Serializable {

  private Short idConvenio;
  private String descripcion;

  public SieduConvenioDTO() {
  }

  public SieduConvenioDTO(Short idConvenio, String descripcion) {
    this.idConvenio = idConvenio;
    this.descripcion = descripcion;
  }

  public Short getIdConvenio() {
    return idConvenio;
  }

  public void setIdConvenio(Short idConvenio) {
    this.idConvenio = idConvenio;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
