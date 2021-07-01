package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "RESPONSABLE_ACTIVIDAD_IMPLEMEN")
@NamedQueries({
  @NamedQuery(name = "ResponsableActividadImplementacion.findAll", query = "SELECT r FROM ResponsableActividadImplementacion r"),
  @NamedQuery(name = "ResponsableActividadImplementacion.findByIdResponsableActividadImpl", query = "SELECT r FROM ResponsableActividadImplementacion r WHERE r.idResponsableActividadImpl = :idResponsableActividadImpl")})
public class ResponsableActividadImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ResponsableActividadImpl_seq_gen")
  @SequenceGenerator(name = "ResponsableActividadImpl_seq_gen", sequenceName = "SEC_RESPONSABLE_ACTIVIDAD_IMPL", allocationSize = 1)
  @Column(name = "ID_RESPONSABLE_ACTIVIDAD_IMPL")
  private Long idResponsableActividadImpl;

  @JoinColumn(name = "ID_INVESTIGADOR_PROYECTO", referencedColumnName = "ID_INVESTIGADOR_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private InvestigadorProyecto investigadorProyecto;

  @JoinColumn(name = "ID_PLAN_TRABAJO", referencedColumnName = "ID_PLAN_TRABAJO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private PlanTrabajoImplementacion planTrabajoImplementacion;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idResponsableActividadImpl != null ? idResponsableActividadImpl.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {

    if (!(object instanceof ResponsableActividadImplementacion)) {
      return false;
    }
    ResponsableActividadImplementacion other = (ResponsableActividadImplementacion) object;
    if ((this.idResponsableActividadImpl == null && other.idResponsableActividadImpl != null) || (this.idResponsableActividadImpl != null && !this.idResponsableActividadImpl.equals(other.idResponsableActividadImpl))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ResponsableActividadImplemen[ idResponsableActividadImpl=" + idResponsableActividadImpl + " ]";
  }

  public Long getIdResponsableActividadImpl() {
    return idResponsableActividadImpl;
  }

  public void setIdResponsableActividadImpl(Long idResponsableActividadImpl) {
    this.idResponsableActividadImpl = idResponsableActividadImpl;
  }

  public InvestigadorProyecto getInvestigadorProyecto() {
    return investigadorProyecto;
  }

  public void setInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
    this.investigadorProyecto = investigadorProyecto;
  }

  public PlanTrabajoImplementacion getPlanTrabajoImplementacion() {
    return planTrabajoImplementacion;
  }

  public void setPlanTrabajoImplementacion(PlanTrabajoImplementacion planTrabajoImplementacion) {
    this.planTrabajoImplementacion = planTrabajoImplementacion;
  }

}
