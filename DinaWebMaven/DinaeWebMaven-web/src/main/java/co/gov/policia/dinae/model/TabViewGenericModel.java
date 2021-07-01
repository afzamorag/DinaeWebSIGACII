package co.gov.policia.dinae.model;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class TabViewGenericModel implements Serializable {

  private String titulo;
  private String urlPage;
  private String id;

  /**
   *
   * @param titulo
   * @param urlPage
   * @param id
   */
  public TabViewGenericModel(String titulo, String urlPage, String id) {
    this.titulo = titulo;
    this.urlPage = urlPage;
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getUrlPage() {
    return urlPage;
  }

  public void setUrlPage(String urlPage) {
    this.urlPage = urlPage;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
