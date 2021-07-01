package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class DominioDTO implements Serializable {

  private Short idDominio;
  private String descripcionDominio;

  /**
   *
   */
  public DominioDTO() {
  }

  public DominioDTO(Short idDominio, String descripcionDominio) {

    this.idDominio = idDominio;
    this.descripcionDominio = descripcionDominio;
  }

  public Short getidDominio() {
    return idDominio;
  }

  public void setidDOminio(Short idDominio) {
    this.idDominio = idDominio;
  }

  public String getdescripcionDominio() {
    return descripcionDominio;
  }

  public void setdescripcionDominio(String descripcionDominio) {
    this.descripcionDominio = descripcionDominio;
  }
}
