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
public class SieduProgramaInvestigacionPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;

  /*@JoinColumns({
    @JoinColumn(name = "PRIN_UDE_CONSECUTIVO", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "PRIN_UDE_FUERZA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadDependencia unidad;*/
  
  @Column(name = "PRIN_ID_UNIDAD_POLICIAL")
  private Long unidad;

  @Column(name = "PRIN_VIGENCIA")
  private String vigencia;

  /*@JoinColumn(name = "PRIN_DOM_MODALIDAD", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Dominio modalidad;*/
  
  @Column(name = "PRIN_DOM_MODALIDAD")
  private Long idModalidad;

  /*public UnidadDependencia getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadDependencia unidad) {
    this.unidad = unidad;
  }*/

  @Override
  public int hashCode() {
    int hash = 3;
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
    final SieduProgramaInvestigacionPK other = (SieduProgramaInvestigacionPK) obj;
    if (!Objects.equals(this.vigencia, other.vigencia)) {
      return false;
    }
    /*if (!Objects.equals(this.unidad, other.unidad)) {
      return false;
    }*/
    /*if (!Objects.equals(this.modalidad, other.modalidad)) {
      return false;
    }*/
    return true;
  }

  public String getVigencia() {
    return vigencia;
  }

  public void setVigencia(String vigencia) {
    this.vigencia = vigencia;
  }

  /*public Dominio getModalidad() {
    return modalidad;
  }

  public void setModalidad(Dominio modalidad) {
    this.modalidad = modalidad;
  }*/

  public Long getIdModalidad() {
    return idModalidad;
  }

  public void setIdModalidad(Long idModalidad) {
    this.idModalidad = idModalidad;
  }

  public Long getUnidad() {
    return unidad;
  }

  public void setUnidad(Long unidad) {
    this.unidad = unidad;
  }

  
  

}
