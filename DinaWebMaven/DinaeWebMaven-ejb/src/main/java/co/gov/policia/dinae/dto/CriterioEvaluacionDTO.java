package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CriterioEvaluacionDTO implements Serializable {

  private Long idCriterio;
  private String criterio;
  private String descripcion;
  private String tipo;
  private String estado;
  private Integer valorMaximoConvocatoria;
  private Integer valorIngresadoConvocatoria;
  private boolean editable;
  private boolean total = false;

  public CriterioEvaluacionDTO() {
  }

  /**
   *
   * @param idCriterio
   * @param criterio
   * @param valorMaximoConvocatoria
   * @param valorIngresadoConvocatoria
   * @param editable
   */
  public CriterioEvaluacionDTO(Long idCriterio, String criterio, Integer valorMaximoConvocatoria, Integer valorIngresadoConvocatoria, boolean editable) {

    this.idCriterio = idCriterio;
    this.criterio = criterio;
    this.valorMaximoConvocatoria = valorMaximoConvocatoria;
    this.valorIngresadoConvocatoria = valorIngresadoConvocatoria;
    this.editable = editable;
  }

  /**
   *
   * @param idCriterio
   * @param criterio
   * @param descripcion
   * @param tipo
   * @param estado
   * @param valorMaximoConvocatoria
   */
  public CriterioEvaluacionDTO(Long idCriterio, String criterio, String descripcion, String tipo, String estado, BigDecimal valor) {
    this.idCriterio = idCriterio;
    this.criterio = criterio;
    this.descripcion = descripcion;
    this.tipo = tipo;
    this.estado = estado;
    this.valorMaximoConvocatoria = valor.intValue();
  }

  public String getCriterio() {
    return criterio;
  }

  public void setCriterio(String criterio) {
    this.criterio = criterio;
  }

  public Integer getValorMaximoConvocatoria() {
    return valorMaximoConvocatoria;
  }

  public void setValorMaximoConvocatoria(Integer valorMaximoConvocatoria) {
    this.valorMaximoConvocatoria = valorMaximoConvocatoria;
  }

  public Integer getValorIngresadoConvocatoria() {
    return valorIngresadoConvocatoria;
  }

  public void setValorIngresadoConvocatoria(Integer valorIngresadoConvocatoria) {
    this.valorIngresadoConvocatoria = valorIngresadoConvocatoria;
  }

  public Long getIdCriterio() {
    return idCriterio;
  }

  public void setIdCriterio(Long idCriterio) {
    this.idCriterio = idCriterio;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public boolean isTotal() {
    return total;
  }

  public void setTotal(boolean total) {
    this.total = total;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}
