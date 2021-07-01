package co.gov.policia.dinae.util;

import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class CriteriosEvaluacionEnsayosView implements Serializable {

  private Long idEnsayoCritico;

  private String _citerio;

  private String _valorMaximo;

  private Double _evaluacion;

  public Long getIdEnsayoCritico() {
    return idEnsayoCritico;
  }

  public void setIdEnsayoCritico(Long idEnsayoCritico) {
    this.idEnsayoCritico = idEnsayoCritico;
  }

  public String getCiterio() {
    return _citerio;
  }

  public void setCiterio(String _citerio) {
    this._citerio = _citerio;
  }

  public String getValorMaximo() {
    return _valorMaximo;
  }

  public void setValorMaximo(String _valorMaximo) {
    this._valorMaximo = _valorMaximo;
  }

  public Double getEvaluacion() {
    return _evaluacion;
  }

  public void setEvaluacion(Double _evaluacion) {
    this._evaluacion = _evaluacion;
  }

}
