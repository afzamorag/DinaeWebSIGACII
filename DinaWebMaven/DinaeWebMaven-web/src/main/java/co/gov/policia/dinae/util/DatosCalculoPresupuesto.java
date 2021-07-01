package co.gov.policia.dinae.util;

import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class DatosCalculoPresupuesto implements Serializable {

  private Integer numero;
  private String nombreRubro;
  private Long idRubro;
  private String style;

  private Long idFuentePolicialNacional;
  private String nombreFuentePolicialNacional;
  private String valorEspeciePolicialNacional;
  private String valorEfectivoPolicialNacional;
  private String totalEspeciePoliciaNacional;
  private String totalEfectivoPoliciaNacional;
  private String porcentajeEspeciePoliciaNacional;
  private String porcentajeEfectivoPoliciaNacional;

  private Long idFuentePolicialDINAE;
  private String nombreFuentePolicialDINAE;
  private String valorEspeciePolicialDINAE;
  private String valorEfectivoPolicialDINAE;
  private String totalEspeciePolicialDINAE;
  private String totalEfectivoPolicialDINAE;
  private String porcentajeEspeciePolicialDINAE;
  private String porcentajeEfectivoPolicialDINAE;

  private Long idFuenteUnidadPolicial;
  private String nombreFuenteUnidadPolicial;
  private String valorEspecieUnidadPolicial;
  private String valorEfectivoUnidadPolicial;
  private String totalEspecieUnidadPolicial;
  private String totalEfectivoUnidadPolicial;
  private String porcentajeEspecieUnidadPolicial;
  private String porcentajeEfectivoUnidadPolicial;

  private Long idFuenteAdicional1;
  private String nombreFuenteAdicional1;
  private String valorEspecieAdicional1;
  private String valorEfectivoAdicional1;
  private String totalEspecieAdicional1;
  private String totalEfectivoAdicional1;
  private String porcentajeEspecieAdicional1;
  private String porcentajeEfectivoAdicional1;

  private Long idFuenteAdicional2;
  private String nombreFuenteAdicional2;
  private String valorEspecieAdicional2;
  private String valorEfectivoAdicional2;
  private String totalEfectivoAdicional2;
  private String totalEspecieAdicional2;
  private String porcentajeEfectivoAdicional2;
  private String porcentajeEspecieAdicional2;

  private Long idFuenteAdicional3;
  private String nombreFuenteAdicional3;
  private String valorEspecieAdicional3;
  private String valorEfectivoAdicional3;
  private String totalEspecieAdicional3;
  private String totalEfectivoAdicional3;
  private String porcentajeEspecieAdicional3;
  private String porcentajeEfectivoAdicional3;

  private Long idFuenteAdicional4;
  private String nombreFuenteAdicional4;
  private String valorEspecieAdicional4;
  private String valorEfectivoAdicional4;
  private String totalEfectivoAdicional4;
  private String totalEspecieAdicional4;
  private String porcentajeEfectivoAdicional4;
  private String porcentajeEspecieAdicional4;

  private String totalFila;

  private String valorSubtotalRubroPresupuestado;
  private String valorSubtotalRubroEjecutado;
  private String porcentajeSubtotalRubroPresupuestado;
  private String porcentajeSubtotalRubroEjecutado;

  public DatosCalculoPresupuesto() {
  }

  /**
   *
   * @param numero
   * @param nombreRubro
   * @param idRubro
   * @param idFuentePolicialNacional
   * @param nombreFuentePolicialNacional
   * @param valorEspeciePolicialNacional
   * @param valorEfectivoPolicialNacional
   * @param idFuentePolicialDINAE
   * @param nombreFuentePolicialDINAE
   * @param valorEspeciePolicialDINAE
   * @param valorEfectivoPolicialDINAE
   * @param idFuenteUnidadPolicial
   * @param nombreFuenteUnidadPolicial
   * @param valorEspecieUnidadPolicial
   * @param valorEfectivoUnidadPolicial
   * @param idFuenteAdicional1
   * @param nombreFuenteAdicional1
   * @param valorEspecieAdicional1
   * @param valorEfectivoAdicional1
   * @param idFuenteAdicional2
   * @param nombreFuenteAdicional2
   * @param valorEspecieAdicional2
   * @param valorEfectivoAdicional2
   * @param idFuenteAdicional3
   * @param nombreFuenteAdicional3
   * @param valorEspecieAdicional3
   * @param valorEfectivoAdicional3
   * @param idFuenteAdicional4
   * @param nombreFuenteAdicional4
   * @param valorEspecieAdicional4
   * @param valorEfectivoAdicional4
   */
  public DatosCalculoPresupuesto(Integer numero, String nombreRubro, Long idRubro, Long idFuentePolicialNacional, String nombreFuentePolicialNacional, String valorEspeciePolicialNacional, String valorEfectivoPolicialNacional, Long idFuentePolicialDINAE, String nombreFuentePolicialDINAE, String valorEspeciePolicialDINAE, String valorEfectivoPolicialDINAE, Long idFuenteUnidadPolicial, String nombreFuenteUnidadPolicial, String valorEspecieUnidadPolicial, String valorEfectivoUnidadPolicial, Long idFuenteAdicional1, String nombreFuenteAdicional1, String valorEspecieAdicional1, String valorEfectivoAdicional1, Long idFuenteAdicional2, String nombreFuenteAdicional2, String valorEspecieAdicional2, String valorEfectivoAdicional2, Long idFuenteAdicional3, String nombreFuenteAdicional3, String valorEspecieAdicional3, String valorEfectivoAdicional3, Long idFuenteAdicional4, String nombreFuenteAdicional4, String valorEspecieAdicional4, String valorEfectivoAdicional4) {
    this.numero = numero;
    this.nombreRubro = nombreRubro;
    this.idRubro = idRubro;
    this.idFuentePolicialNacional = idFuentePolicialNacional;
    this.nombreFuentePolicialNacional = nombreFuentePolicialNacional;
    this.valorEspeciePolicialNacional = valorEspeciePolicialNacional;
    this.valorEfectivoPolicialNacional = valorEfectivoPolicialNacional;
    this.idFuentePolicialDINAE = idFuentePolicialDINAE;
    this.nombreFuentePolicialDINAE = nombreFuentePolicialDINAE;
    this.valorEspeciePolicialDINAE = valorEspeciePolicialDINAE;
    this.valorEfectivoPolicialDINAE = valorEfectivoPolicialDINAE;
    this.idFuenteUnidadPolicial = idFuenteUnidadPolicial;
    this.nombreFuenteUnidadPolicial = nombreFuenteUnidadPolicial;
    this.valorEspecieUnidadPolicial = valorEspecieUnidadPolicial;
    this.valorEfectivoUnidadPolicial = valorEfectivoUnidadPolicial;
    this.idFuenteAdicional1 = idFuenteAdicional1;
    this.nombreFuenteAdicional1 = nombreFuenteAdicional1;
    this.valorEspecieAdicional1 = valorEspecieAdicional1;
    this.valorEfectivoAdicional1 = valorEfectivoAdicional1;
    this.idFuenteAdicional2 = idFuenteAdicional2;
    this.nombreFuenteAdicional2 = nombreFuenteAdicional2;
    this.valorEspecieAdicional2 = valorEspecieAdicional2;
    this.valorEfectivoAdicional2 = valorEfectivoAdicional2;
    this.idFuenteAdicional3 = idFuenteAdicional3;
    this.nombreFuenteAdicional3 = nombreFuenteAdicional3;
    this.valorEspecieAdicional3 = valorEspecieAdicional3;
    this.valorEfectivoAdicional3 = valorEfectivoAdicional3;
    this.idFuenteAdicional4 = idFuenteAdicional4;
    this.nombreFuenteAdicional4 = nombreFuenteAdicional4;
    this.valorEspecieAdicional4 = valorEspecieAdicional4;
    this.valorEfectivoAdicional4 = valorEfectivoAdicional4;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public String getNombreRubro() {
    return nombreRubro;
  }

  public void setNombreRubro(String nombreRubro) {
    this.nombreRubro = nombreRubro;
  }

  public Long getIdRubro() {
    return idRubro;
  }

  public void setIdRubro(Long idRubro) {
    this.idRubro = idRubro;
  }

  public Long getIdFuentePolicialNacional() {
    return idFuentePolicialNacional;
  }

  public void setIdFuentePolicialNacional(Long idFuentePolicialNacional) {
    this.idFuentePolicialNacional = idFuentePolicialNacional;
  }

  public String getNombreFuentePolicialNacional() {
    return nombreFuentePolicialNacional;
  }

  public void setNombreFuentePolicialNacional(String nombreFuentePolicialNacional) {
    this.nombreFuentePolicialNacional = nombreFuentePolicialNacional;
  }

  public String getValorEspeciePolicialNacional() {
    return valorEspeciePolicialNacional;
  }

  public void setValorEspeciePolicialNacional(String valorEspeciePolicialNacional) {
    this.valorEspeciePolicialNacional = valorEspeciePolicialNacional;
  }

  public String getValorEfectivoPolicialNacional() {
    return valorEfectivoPolicialNacional;
  }

  public void setValorEfectivoPolicialNacional(String valorEfectivoPolicialNacional) {
    this.valorEfectivoPolicialNacional = valorEfectivoPolicialNacional;
  }

  public Long getIdFuentePolicialDINAE() {
    return idFuentePolicialDINAE;
  }

  public void setIdFuentePolicialDINAE(Long idFuentePolicialDINAE) {
    this.idFuentePolicialDINAE = idFuentePolicialDINAE;
  }

  public String getNombreFuentePolicialDINAE() {
    return nombreFuentePolicialDINAE;
  }

  public void setNombreFuentePolicialDINAE(String nombreFuentePolicialDINAE) {
    this.nombreFuentePolicialDINAE = nombreFuentePolicialDINAE;
  }

  public String getValorEspeciePolicialDINAE() {
    return valorEspeciePolicialDINAE;
  }

  public void setValorEspeciePolicialDINAE(String valorEspeciePolicialDINAE) {
    this.valorEspeciePolicialDINAE = valorEspeciePolicialDINAE;
  }

  public String getValorEfectivoPolicialDINAE() {
    return valorEfectivoPolicialDINAE;
  }

  public void setValorEfectivoPolicialDINAE(String valorEfectivoPolicialDINAE) {
    this.valorEfectivoPolicialDINAE = valorEfectivoPolicialDINAE;
  }

  public Long getIdFuenteUnidadPolicial() {
    return idFuenteUnidadPolicial;
  }

  public void setIdFuenteUnidadPolicial(Long idFuenteUnidadPolicial) {
    this.idFuenteUnidadPolicial = idFuenteUnidadPolicial;
  }

  public String getNombreFuenteUnidadPolicial() {
    return nombreFuenteUnidadPolicial;
  }

  public void setNombreFuenteUnidadPolicial(String nombreFuenteUnidadPolicial) {
    this.nombreFuenteUnidadPolicial = nombreFuenteUnidadPolicial;
  }

  public String getValorEspecieUnidadPolicial() {
    return valorEspecieUnidadPolicial;
  }

  public void setValorEspecieUnidadPolicial(String valorEspecieUnidadPolicial) {
    this.valorEspecieUnidadPolicial = valorEspecieUnidadPolicial;
  }

  public String getValorEfectivoUnidadPolicial() {
    return valorEfectivoUnidadPolicial;
  }

  public void setValorEfectivoUnidadPolicial(String valorEfectivoUnidadPolicial) {
    this.valorEfectivoUnidadPolicial = valorEfectivoUnidadPolicial;
  }

  public Long getIdFuenteAdicional1() {
    return idFuenteAdicional1;
  }

  public void setIdFuenteAdicional1(Long idFuenteAdicional1) {
    this.idFuenteAdicional1 = idFuenteAdicional1;
  }

  public String getNombreFuenteAdicional1() {
    return nombreFuenteAdicional1;
  }

  public void setNombreFuenteAdicional1(String nombreFuenteAdicional1) {
    this.nombreFuenteAdicional1 = nombreFuenteAdicional1;
  }

  public String getValorEspecieAdicional1() {
    return valorEspecieAdicional1;
  }

  public void setValorEspecieAdicional1(String valorEspecieAdicional1) {
    this.valorEspecieAdicional1 = valorEspecieAdicional1;
  }

  public String getValorEfectivoAdicional1() {
    return valorEfectivoAdicional1;
  }

  public void setValorEfectivoAdicional1(String valorEfectivoAdicional1) {
    this.valorEfectivoAdicional1 = valorEfectivoAdicional1;
  }

  public Long getIdFuenteAdicional2() {
    return idFuenteAdicional2;
  }

  public void setIdFuenteAdicional2(Long idFuenteAdicional2) {
    this.idFuenteAdicional2 = idFuenteAdicional2;
  }

  public String getNombreFuenteAdicional2() {
    return nombreFuenteAdicional2;
  }

  public void setNombreFuenteAdicional2(String nombreFuenteAdicional2) {
    this.nombreFuenteAdicional2 = nombreFuenteAdicional2;
  }

  public String getValorEspecieAdicional2() {
    return valorEspecieAdicional2;
  }

  public void setValorEspecieAdicional2(String valorEspecieAdicional2) {
    this.valorEspecieAdicional2 = valorEspecieAdicional2;
  }

  public String getValorEfectivoAdicional2() {
    return valorEfectivoAdicional2;
  }

  public void setValorEfectivoAdicional2(String valorEfectivoAdicional2) {
    this.valorEfectivoAdicional2 = valorEfectivoAdicional2;
  }

  public Long getIdFuenteAdicional3() {
    return idFuenteAdicional3;
  }

  public void setIdFuenteAdicional3(Long idFuenteAdicional3) {
    this.idFuenteAdicional3 = idFuenteAdicional3;
  }

  public String getNombreFuenteAdicional3() {
    return nombreFuenteAdicional3;
  }

  public void setNombreFuenteAdicional3(String nombreFuenteAdicional3) {
    this.nombreFuenteAdicional3 = nombreFuenteAdicional3;
  }

  public String getValorEspecieAdicional3() {
    return valorEspecieAdicional3;
  }

  public void setValorEspecieAdicional3(String valorEspecieAdicional3) {
    this.valorEspecieAdicional3 = valorEspecieAdicional3;
  }

  public String getValorEfectivoAdicional3() {
    return valorEfectivoAdicional3;
  }

  public void setValorEfectivoAdicional3(String valorEfectivoAdicional3) {
    this.valorEfectivoAdicional3 = valorEfectivoAdicional3;
  }

  public Long getIdFuenteAdicional4() {
    return idFuenteAdicional4;
  }

  public void setIdFuenteAdicional4(Long idFuenteAdicional4) {
    this.idFuenteAdicional4 = idFuenteAdicional4;
  }

  public String getNombreFuenteAdicional4() {
    return nombreFuenteAdicional4;
  }

  public void setNombreFuenteAdicional4(String nombreFuenteAdicional4) {
    this.nombreFuenteAdicional4 = nombreFuenteAdicional4;
  }

  public String getValorEspecieAdicional4() {
    return valorEspecieAdicional4;
  }

  public void setValorEspecieAdicional4(String valorEspecieAdicional4) {
    this.valorEspecieAdicional4 = valorEspecieAdicional4;
  }

  public String getValorEfectivoAdicional4() {
    return valorEfectivoAdicional4;
  }

  public void setValorEfectivoAdicional4(String valorEfectivoAdicional4) {
    this.valorEfectivoAdicional4 = valorEfectivoAdicional4;
  }

  public String getTotalFila() {
    return totalFila;
  }

  public void setTotalFila(String totalFila) {
    this.totalFila = totalFila;
  }

  public String getTotalEspeciePoliciaNacional() {
    return totalEspeciePoliciaNacional;
  }

  public void setTotalEspeciePoliciaNacional(String totalEspeciePoliciaNacional) {
    this.totalEspeciePoliciaNacional = totalEspeciePoliciaNacional;
  }

  public String getTotalEfectivoPoliciaNacional() {
    return totalEfectivoPoliciaNacional;
  }

  public void setTotalEfectivoPoliciaNacional(String totalEfectivoPoliciaNacional) {
    this.totalEfectivoPoliciaNacional = totalEfectivoPoliciaNacional;
  }

  public String getTotalEspeciePolicialDINAE() {
    return totalEspeciePolicialDINAE;
  }

  public void setTotalEspeciePolicialDINAE(String totalEspeciePolicialDINAE) {
    this.totalEspeciePolicialDINAE = totalEspeciePolicialDINAE;
  }

  public String getTotalEfectivoPolicialDINAE() {
    return totalEfectivoPolicialDINAE;
  }

  public void setTotalEfectivoPolicialDINAE(String totalEfectivoPolicialDINAE) {
    this.totalEfectivoPolicialDINAE = totalEfectivoPolicialDINAE;
  }

  public String getTotalEspecieUnidadPolicial() {
    return totalEspecieUnidadPolicial;
  }

  public void setTotalEspecieUnidadPolicial(String totalEspecieUnidadPolicial) {
    this.totalEspecieUnidadPolicial = totalEspecieUnidadPolicial;
  }

  public String getTotalEfectivoUnidadPolicial() {
    return totalEfectivoUnidadPolicial;
  }

  public void setTotalEfectivoUnidadPolicial(String totalEfectivoUnidadPolicial) {
    this.totalEfectivoUnidadPolicial = totalEfectivoUnidadPolicial;
  }

  public String getTotalEspecieAdicional1() {
    return totalEspecieAdicional1;
  }

  public void setTotalEspecieAdicional1(String totalEspecieAdicional1) {
    this.totalEspecieAdicional1 = totalEspecieAdicional1;
  }

  public String getTotalEfectivoAdicional1() {
    return totalEfectivoAdicional1;
  }

  public void setTotalEfectivoAdicional1(String totalEfectivoAdicional1) {
    this.totalEfectivoAdicional1 = totalEfectivoAdicional1;
  }

  public String getTotalEfectivoAdicional2() {
    return totalEfectivoAdicional2;
  }

  public void setTotalEfectivoAdicional2(String totalEfectivoAdicional2) {
    this.totalEfectivoAdicional2 = totalEfectivoAdicional2;
  }

  public String getTotalEspecieAdicional2() {
    return totalEspecieAdicional2;
  }

  public void setTotalEspecieAdicional2(String totalEspecieAdicional2) {
    this.totalEspecieAdicional2 = totalEspecieAdicional2;
  }

  public String getTotalEspecieAdicional3() {
    return totalEspecieAdicional3;
  }

  public void setTotalEspecieAdicional3(String totalEspecieAdicional3) {
    this.totalEspecieAdicional3 = totalEspecieAdicional3;
  }

  public String getTotalEfectivoAdicional3() {
    return totalEfectivoAdicional3;
  }

  public void setTotalEfectivoAdicional3(String totalEfectivoAdicional3) {
    this.totalEfectivoAdicional3 = totalEfectivoAdicional3;
  }

  public String getTotalEfectivoAdicional4() {
    return totalEfectivoAdicional4;
  }

  public void setTotalEfectivoAdicional4(String totalEfectivoAdicional4) {
    this.totalEfectivoAdicional4 = totalEfectivoAdicional4;
  }

  public String getTotalEspecieAdicional4() {
    return totalEspecieAdicional4;
  }

  public void setTotalEspecieAdicional4(String totalEspecieAdicional4) {
    this.totalEspecieAdicional4 = totalEspecieAdicional4;
  }

  public String getPorcentajeEspeciePoliciaNacional() {
    return porcentajeEspeciePoliciaNacional;
  }

  public void setPorcentajeEspeciePoliciaNacional(String porcentajeEspeciePoliciaNacional) {
    this.porcentajeEspeciePoliciaNacional = porcentajeEspeciePoliciaNacional;
  }

  public String getPorcentajeEfectivoPoliciaNacional() {
    return porcentajeEfectivoPoliciaNacional;
  }

  public void setPorcentajeEfectivoPoliciaNacional(String porcentajeEfectivoPoliciaNacional) {
    this.porcentajeEfectivoPoliciaNacional = porcentajeEfectivoPoliciaNacional;
  }

  public String getPorcentajeEspeciePolicialDINAE() {
    return porcentajeEspeciePolicialDINAE;
  }

  public void setPorcentajeEspeciePolicialDINAE(String porcentajeEspeciePolicialDINAE) {
    this.porcentajeEspeciePolicialDINAE = porcentajeEspeciePolicialDINAE;
  }

  public String getPorcentajeEfectivoPolicialDINAE() {
    return porcentajeEfectivoPolicialDINAE;
  }

  public void setPorcentajeEfectivoPolicialDINAE(String porcentajeEfectivoPolicialDINAE) {
    this.porcentajeEfectivoPolicialDINAE = porcentajeEfectivoPolicialDINAE;
  }

  public String getPorcentajeEspecieUnidadPolicial() {
    return porcentajeEspecieUnidadPolicial;
  }

  public void setPorcentajeEspecieUnidadPolicial(String porcentajeEspecieUnidadPolicial) {
    this.porcentajeEspecieUnidadPolicial = porcentajeEspecieUnidadPolicial;
  }

  public String getPorcentajeEfectivoUnidadPolicial() {
    return porcentajeEfectivoUnidadPolicial;
  }

  public void setPorcentajeEfectivoUnidadPolicial(String porcentajeEfectivoUnidadPolicial) {
    this.porcentajeEfectivoUnidadPolicial = porcentajeEfectivoUnidadPolicial;
  }

  public String getPorcentajeEspecieAdicional1() {
    return porcentajeEspecieAdicional1;
  }

  public void setPorcentajeEspecieAdicional1(String porcentajeEspecieAdicional1) {
    this.porcentajeEspecieAdicional1 = porcentajeEspecieAdicional1;
  }

  public String getPorcentajeEfectivoAdicional1() {
    return porcentajeEfectivoAdicional1;
  }

  public void setPorcentajeEfectivoAdicional1(String porcentajeEfectivoAdicional1) {
    this.porcentajeEfectivoAdicional1 = porcentajeEfectivoAdicional1;
  }

  public String getPorcentajeEfectivoAdicional2() {
    return porcentajeEfectivoAdicional2;
  }

  public void setPorcentajeEfectivoAdicional2(String porcentajeEfectivoAdicional2) {
    this.porcentajeEfectivoAdicional2 = porcentajeEfectivoAdicional2;
  }

  public String getPorcentajeEspecieAdicional2() {
    return porcentajeEspecieAdicional2;
  }

  public void setPorcentajeEspecieAdicional2(String porcentajeEspecieAdicional2) {
    this.porcentajeEspecieAdicional2 = porcentajeEspecieAdicional2;
  }

  public String getPorcentajeEspecieAdicional3() {
    return porcentajeEspecieAdicional3;
  }

  public void setPorcentajeEspecieAdicional3(String porcentajeEspecieAdicional3) {
    this.porcentajeEspecieAdicional3 = porcentajeEspecieAdicional3;
  }

  public String getPorcentajeEfectivoAdicional3() {
    return porcentajeEfectivoAdicional3;
  }

  public void setPorcentajeEfectivoAdicional3(String porcentajeEfectivoAdicional3) {
    this.porcentajeEfectivoAdicional3 = porcentajeEfectivoAdicional3;
  }

  public String getPorcentajeEfectivoAdicional4() {
    return porcentajeEfectivoAdicional4;
  }

  public void setPorcentajeEfectivoAdicional4(String porcentajeEfectivoAdicional4) {
    this.porcentajeEfectivoAdicional4 = porcentajeEfectivoAdicional4;
  }

  public String getPorcentajeEspecieAdicional4() {
    return porcentajeEspecieAdicional4;
  }

  public void setPorcentajeEspecieAdicional4(String porcentajeEspecieAdicional4) {
    this.porcentajeEspecieAdicional4 = porcentajeEspecieAdicional4;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getValorSubtotalRubroPresupuestado() {
    return valorSubtotalRubroPresupuestado;
  }

  public void setValorSubtotalRubroPresupuestado(String valorSubtotalRubroPresupuestado) {
    this.valorSubtotalRubroPresupuestado = valorSubtotalRubroPresupuestado;
  }

  public String getValorSubtotalRubroEjecutado() {
    return valorSubtotalRubroEjecutado;
  }

  public void setValorSubtotalRubroEjecutado(String valorSubtotalRubroEjecutado) {
    this.valorSubtotalRubroEjecutado = valorSubtotalRubroEjecutado;
  }

  public String getPorcentajeSubtotalRubroPresupuestado() {
    return porcentajeSubtotalRubroPresupuestado;
  }

  public void setPorcentajeSubtotalRubroPresupuestado(String porcentajeSubtotalRubroPresupuestado) {
    this.porcentajeSubtotalRubroPresupuestado = porcentajeSubtotalRubroPresupuestado;
  }

  public String getPorcentajeSubtotalRubroEjecutado() {
    return porcentajeSubtotalRubroEjecutado;
  }

  public void setPorcentajeSubtotalRubroEjecutado(String porcentajeSubtotalRubroEjecutado) {
    this.porcentajeSubtotalRubroEjecutado = porcentajeSubtotalRubroEjecutado;
  }

}
