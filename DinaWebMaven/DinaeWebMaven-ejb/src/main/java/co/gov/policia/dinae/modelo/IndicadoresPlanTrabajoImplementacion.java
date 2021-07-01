package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INDICADORES_INFORME_AVANCE_IMP")
@NamedQueries({
  @NamedQuery(name = "IndicadoresPlanTrabajoImplementacion.findAll", query = "SELECT i FROM IndicadoresPlanTrabajoImplementacion i"),
  @NamedQuery(name = "IndicadoresPlanTrabajoImplementacion.findPorPlanTrabajoImplYidInformeAvanceImplementacion", query = "SELECT i FROM IndicadoresPlanTrabajoImplementacion i WHERE i.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND i.indicadoresPlanTrabajo.idIndicadorPlanTrabajo = :idIndicadorPlanTrabajo"),
  @NamedQuery(name = "IndicadoresPlanTrabajoImplementacion.findPorInformeAvanceImplementacion", query = "SELECT i FROM IndicadoresPlanTrabajoImplementacion i WHERE i.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")
})
public class IndicadoresPlanTrabajoImplementacion implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IndicadoresPlanTrabajoImplementacion_seq_gen")
  @SequenceGenerator(name = "IndicadoresPlanTrabajoImplementacion_seq_gen", sequenceName = "SEC_INDICADOR_INFOR_AVANCE_IMP", allocationSize = 1)
  @Column(name = "ID_INDICADORES_AVANCE_IMPL")
  private Long idIndicadorPlanTrabajoImplementacion;

  @Column(name = "VALOR_NUMERADOR")
  private BigDecimal valorNumerador;

  @Column(name = "VALOR_DENOMINADOR")
  private BigDecimal valorDenominador;

  @JoinColumn(name = "ID_INDICADOR_PLAN_TRABAJO", referencedColumnName = "ID_INDICADOR_PLAN_TRAB")
  @ManyToOne(fetch = FetchType.LAZY)
  private IndicadoresPlanTrabajo indicadoresPlanTrabajo;

  @JoinColumn(name = "ID_INFORME_AVANCE_IMPL", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  public IndicadoresPlanTrabajoImplementacion() {
  }

  public String getValorOperacionSinDecimalToString() {

    if (valorNumerador == null || valorDenominador == null) {
      return "0";
    }

    Double valor1 = valorNumerador.doubleValue();
    Double valor2 = valorDenominador.doubleValue();

    Double resultado = valor1 / valor2;
    resultado = resultado * 100;

    return String.valueOf(resultado.intValue());
  }

  @Override
  @Transient
  public String getLlaveModel() {

    return String.valueOf(idIndicadorPlanTrabajoImplementacion);
  }

  public Long getIdIndicadorPlanTrabajoImplementacion() {
    return idIndicadorPlanTrabajoImplementacion;
  }

  public void setIdIndicadorPlanTrabajoImplementacion(Long idIndicadorPlanTrabajoImplementacion) {
    this.idIndicadorPlanTrabajoImplementacion = idIndicadorPlanTrabajoImplementacion;
  }

  public BigDecimal getValorNumerador() {
    return valorNumerador;
  }

  public void setValorNumerador(BigDecimal valorNumerador) {
    this.valorNumerador = valorNumerador;
  }

  public BigDecimal getValorDenominador() {
    return valorDenominador;
  }

  public void setValorDenominador(BigDecimal valorDenominador) {
    this.valorDenominador = valorDenominador;
  }

  public IndicadoresPlanTrabajo getIndicadoresPlanTrabajo() {
    return indicadoresPlanTrabajo;
  }

  public void setIndicadoresPlanTrabajo(IndicadoresPlanTrabajo indicadoresPlanTrabajo) {
    this.indicadoresPlanTrabajo = indicadoresPlanTrabajo;
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

}
