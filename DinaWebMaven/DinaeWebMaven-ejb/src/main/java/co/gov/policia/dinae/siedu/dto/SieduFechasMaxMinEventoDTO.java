package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Andres Felipe Zamora Garzon <af.zamorag@gmail.com>
 */
public class SieduFechasMaxMinEventoDTO implements Serializable {

  private static final long serialVersionUID = 402926263827811735L;

  private Date maxFecha;
  private Date minFecha;

  /**
   *
   */
  public SieduFechasMaxMinEventoDTO() {
  }

  public SieduFechasMaxMinEventoDTO(Date maxFecha, Date minFecha) {
    this.maxFecha = maxFecha;
    this.minFecha = minFecha;
  }

  public Date getMaxFecha() {
    return maxFecha;
  }

  public void setMaxFecha(Date maxFecha) {
    this.maxFecha = maxFecha;
  }

  public Date getMinFecha() {
    return minFecha;
  }

  public void setMinFecha(Date minFecha) {
    this.minFecha = minFecha;
  }

}
