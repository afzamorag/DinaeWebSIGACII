package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INDICADORES_PLAN_TRABAJO")
@NamedQueries({
  @NamedQuery(name = "IndicadoresPlanTrabajo.findAll", query = "SELECT i FROM IndicadoresPlanTrabajo i"),
  @NamedQuery(name = "IndicadoresPlanTrabajo.EliminarIndicadorPorId", query = "DELETE FROM IndicadoresPlanTrabajo i WHERE i.idIndicadorPlanTrabajo = :idIndicadorPlanTrabajo"),
  @NamedQuery(name = "IndicadoresPlanTrabajo.findPlanTrabajoImplementacion", query = "SELECT i FROM IndicadoresPlanTrabajo i WHERE i.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo")
})
public class IndicadoresPlanTrabajo implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IndicadoresPlanTrabajo_seq_gen")
  @SequenceGenerator(name = "IndicadoresPlanTrabajo_seq_gen", sequenceName = "SEC_INDICADORES_PLAN_TRABAJO", allocationSize = 1)
  @Column(name = "ID_INDICADOR_PLAN_TRAB")
  private Long idIndicadorPlanTrabajo;

  @Column(name = "NOMBRE_INDICADOR")
  private String nombreIndicador;

  @Column(name = "NOMBRE_NUMERADOR")
  private String nombreNumerador;

  @Column(name = "NOMBRE_DENOMINADOR")
  private String nombreDenominador;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_PLAN_TRABAJO", referencedColumnName = "ID_PLAN_TRABAJO")
  @ManyToOne(fetch = FetchType.LAZY)
  private PlanTrabajoImplementacion planTrabajoImplementacion;

  public IndicadoresPlanTrabajo() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idIndicadorPlanTrabajo != null ? idIndicadorPlanTrabajo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof IndicadoresPlanTrabajo)) {
      return false;
    }
    IndicadoresPlanTrabajo other = (IndicadoresPlanTrabajo) object;
    if ((this.idIndicadorPlanTrabajo == null && other.idIndicadorPlanTrabajo != null) || (this.idIndicadorPlanTrabajo != null && !this.idIndicadorPlanTrabajo.equals(other.idIndicadorPlanTrabajo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.IndicadoresPlanTrabajo[ idIndicadorPlanTrabajo=" + idIndicadorPlanTrabajo + " ]";
  }

  @Override
  @Transient
  public String getLlaveModel() {
    return String.valueOf(idIndicadorPlanTrabajo);
  }

  public Long getIdIndicadorPlanTrabajo() {
    return idIndicadorPlanTrabajo;
  }

  public void setIdIndicadorPlanTrabajo(Long idIndicadorPlanTrabajo) {
    this.idIndicadorPlanTrabajo = idIndicadorPlanTrabajo;
  }

  public String getNombreIndicador() {
    return nombreIndicador;
  }

  public void setNombreIndicador(String nombreIndicador) {
    this.nombreIndicador = nombreIndicador;
  }

  public String getNombreNumerador() {
    return nombreNumerador;
  }

  public void setNombreNumerador(String nombreNumerador) {
    this.nombreNumerador = nombreNumerador;
  }

  public String getNombreDenominador() {
    return nombreDenominador;
  }

  public void setNombreDenominador(String nombreDenominador) {
    this.nombreDenominador = nombreDenominador;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public PlanTrabajoImplementacion getPlanTrabajoImplementacion() {
    return planTrabajoImplementacion;
  }

  public void setPlanTrabajoImplementacion(PlanTrabajoImplementacion planTrabajoImplementacion) {
    this.planTrabajoImplementacion = planTrabajoImplementacion;
  }

}
