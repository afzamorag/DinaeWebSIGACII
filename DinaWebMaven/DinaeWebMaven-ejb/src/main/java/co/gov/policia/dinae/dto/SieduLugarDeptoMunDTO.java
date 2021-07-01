package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class SieduLugarDeptoMunDTO implements Serializable {

  private int codigo;
  private String municipio;

  public SieduLugarDeptoMunDTO() {
  }

  public SieduLugarDeptoMunDTO(int codigo, String municipio) {
    this.codigo = codigo;
    this.municipio = municipio;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getMunicipio() {
    return municipio;
  }

  public void setMunicipio(String municipio) {
    this.municipio = municipio;
  }

}
