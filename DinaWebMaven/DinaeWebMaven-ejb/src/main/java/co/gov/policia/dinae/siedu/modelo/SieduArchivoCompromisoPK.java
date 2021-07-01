package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

import javax.persistence.Embeddable;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class SieduArchivoCompromisoPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;
  
  @Column(name = "ARCO_INVE")
  private Long idInvestigacion;

  @Column(name = "ARCO_COMP")
  private Long idCompromiso;
  
  @Column(name = "ARCO_ARCO")
  private Long idArchivo;

  public SieduArchivoCompromisoPK() {
  }

  public SieduArchivoCompromisoPK(Long idInvestigacion, Long idCompromiso, Long idArchivo) {
    this.idInvestigacion = idInvestigacion;
    this.idCompromiso = idCompromiso;
    this.idArchivo = idArchivo;
  }

  public Long getIdInvestigacion() {
    return idInvestigacion;
  }

  public void setIdInvestigacion(Long idInvestigacion) {
    this.idInvestigacion = idInvestigacion;
  }

  public Long getIdCompromiso() {
    return idCompromiso;
  }

  public void setIdCompromiso(Long idCompromiso) {
    this.idCompromiso = idCompromiso;
  }

  public Long getIdArchivo() {
    return idArchivo;
  }

  public void setIdArchivo(Long idArchivo) {
    this.idArchivo = idArchivo;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + Objects.hashCode(this.idInvestigacion);
    hash = 41 * hash + Objects.hashCode(this.idCompromiso);
    hash = 41 * hash + Objects.hashCode(this.idArchivo);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SieduArchivoCompromisoPK other = (SieduArchivoCompromisoPK) obj;
    if (!Objects.equals(this.idInvestigacion, other.idInvestigacion)) {
      return false;
    }
    if (!Objects.equals(this.idCompromiso, other.idCompromiso)) {
      return false;
    }
    if (!Objects.equals(this.idArchivo, other.idArchivo)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduArchivoCompromisoPK{" + "idInvestigacion=" + idInvestigacion + ", idCompromiso=" + idCompromiso + ", idArchivo=" + idArchivo + '}';
  }


}
