package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author juan Carlos Cifuentes Murcia <juan.cifuentes@correo.policia.gov.co>
 */
public class LugarGeograficoDTO implements Serializable {

  private Long idDepartamento;
  private String descDepartamento;

  /**
   *
   */
  public LugarGeograficoDTO() {
  }

  public LugarGeograficoDTO(Long codDepartamento, String descDepartamento) {

    this.idDepartamento = codDepartamento;
    this.descDepartamento = descDepartamento;
  }

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getDescDepartamento() {
        return descDepartamento;
    }

    public void setDescDepartamento(String descDepartamento) {
        this.descDepartamento = descDepartamento;
    }
  
}
