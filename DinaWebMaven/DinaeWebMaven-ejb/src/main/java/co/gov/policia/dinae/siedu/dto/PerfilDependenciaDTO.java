package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;

/**
 *
 * @author Andres Felipe Zamora Garzon <af.zamorag@gmail.com>
 */
public class PerfilDependenciaDTO implements Serializable {

  private static final long serialVersionUID = 402926263827811735L;

  private String rolDepenencia;
  private Long unidadDependencia;

  public PerfilDependenciaDTO() {
  }

  public PerfilDependenciaDTO(String rolDepenencia, Long unidadDependencia) {
    this.rolDepenencia = rolDepenencia;
    this.unidadDependencia = unidadDependencia;
  }

  public String getRolDepenencia() {
    return rolDepenencia;
  }

  public void setRolDepenencia(String rolDepenencia) {
    this.rolDepenencia = rolDepenencia;
  }

  public Long getUnidadDependencia() {
    return unidadDependencia;
  }

  public void setUnidadDependencia(Long unidadDependencia) {
    this.unidadDependencia = unidadDependencia;
  }
}
