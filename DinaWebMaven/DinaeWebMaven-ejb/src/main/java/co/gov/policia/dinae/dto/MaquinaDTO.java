package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class MaquinaDTO implements Serializable {

  private final String ipLoginRemotoUsuario;

  /**
   *
   * @param ipLoginRemotoUsuario
   */
  public MaquinaDTO(String ipLoginRemotoUsuario) {
    this.ipLoginRemotoUsuario = ipLoginRemotoUsuario;
  }

  public String getIpLoginRemotoUsuario() {
    return ipLoginRemotoUsuario;
  }
}
