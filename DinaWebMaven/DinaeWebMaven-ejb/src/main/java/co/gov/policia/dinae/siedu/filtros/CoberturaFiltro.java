/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.filtros;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import java.io.Serializable;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class CoberturaFiltro implements Serializable {

  private PAE pae;
  private UnidadDependencia escuela;
  private Dominio estrategia;

  /**
   * @return the pae
   */
  public PAE getPae() {
    return pae;
  }

  /**
   * @param pae the pae to set
   */
  public void setPae(PAE pae) {
    this.pae = pae;
  }

  public UnidadDependencia getEscuela() {
    return escuela;
  }

  public void setEscuela(UnidadDependencia escuela) {
    this.escuela = escuela;
  }

  public Dominio getEstrategia() {
    return estrategia;
  }

  public void setEstrategia(Dominio estrategia) {
    this.estrategia = estrategia;
  }
}
