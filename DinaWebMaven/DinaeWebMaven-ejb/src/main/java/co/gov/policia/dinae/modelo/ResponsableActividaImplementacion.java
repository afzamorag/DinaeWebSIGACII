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
  @NamedQuery(name = "ResponsableActividaImplementacion.findAll", query = "SELECT a FROM ResponsableActividaImplementacion a"),})
public class ResponsableActividaImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ResponsableActividaImplementacion_seq_gen")
  @SequenceGenerator(name = "ResponsableActividaImplementacion_seq_gen", sequenceName = "SEC_RESPONSABLE_ACTIVIDAD_IMPL", allocationSize = 1)
  @Column(name = "ID_RESPONSABLE_ACTIVIDAD_IMPL")
  private Long idResponsableActividadImplementacion;

  @JoinColumn(name = "ID_INVESTIGADOR_PROYECTO", referencedColumnName = "ID_INVESTIGADOR_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private InvestigadorProyecto investigadorProyecto;

  @JoinColumn(name = "ID_ACTIVIDAD_PLAN_TRB_IMPL", referencedColumnName = "ID_ACTIV_PLAN_TRABAJO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ActividadesPlanImplementacion actividadesPlanImplementacion;

  public ResponsableActividaImplementacion() {
  }

  /**
   *
   * @param idResponsableActividadImplementacion
   */
  public ResponsableActividaImplementacion(Long idResponsableActividadImplementacion) {
    this.idResponsableActividadImplementacion = idResponsableActividadImplementacion;
  }

  public Long getIdResponsableActividadImplementacion() {
    return idResponsableActividadImplementacion;
  }

  public void setIdResponsableActividadImplementacion(Long idResponsableActividadImplementacion) {
    this.idResponsableActividadImplementacion = idResponsableActividadImplementacion;
  }

  public InvestigadorProyecto getInvestigadorProyecto() {
    return investigadorProyecto;
  }

  public void setInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
    this.investigadorProyecto = investigadorProyecto;
  }

  public ActividadesPlanImplementacion getActividadesPlanImplementacion() {
    return actividadesPlanImplementacion;
  }

  public void setActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) {
    this.actividadesPlanImplementacion = actividadesPlanImplementacion;
  }

}
