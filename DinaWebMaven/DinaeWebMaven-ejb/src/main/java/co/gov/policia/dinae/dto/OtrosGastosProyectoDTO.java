package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class OtrosGastosProyectoDTO implements IDataModel, Serializable {

  private Long idOtrosGastosProyecto;
  private Double valor;
  private Long idFuenteProyecto;
  private String nombreFuente;
  private Long idTipoRubro;
  private Long idTipoEspecieEfectivo;
  private Date fechaRegistro;
  private Long idUsuarioRol;
  private Double valorGastadoInforme;
  private Integer conteo;

  public OtrosGastosProyectoDTO() {
  }

  public OtrosGastosProyectoDTO(Long idOtrosGastosProyecto, Double valor, Long idFuenteProyecto, String nombreFuente, Long idTipoRubro, Long idTipoEspecieEfectivo, Date fechaRegistro, Long idUsuarioRol, Double valorGastadoInforme) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
    this.valor = valor;
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuente = nombreFuente;
    this.idTipoRubro = idTipoRubro;
    this.idTipoEspecieEfectivo = idTipoEspecieEfectivo;
    this.fechaRegistro = fechaRegistro;
    this.idUsuarioRol = idUsuarioRol;
    this.valorGastadoInforme = valorGastadoInforme;
  }

  public OtrosGastosProyectoDTO(Long idOtrosGastosProyecto, Long valor, Long idFuenteProyecto, String nombreFuente, Long idTipoRubro, Long idTipoEspecieEfectivo, Date fechaRegistro, Long idUsuarioRol) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
    this.valor = valor.doubleValue();
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuente = nombreFuente;
    this.idTipoRubro = idTipoRubro;
    this.idTipoEspecieEfectivo = idTipoEspecieEfectivo;
    this.fechaRegistro = fechaRegistro;
    this.idUsuarioRol = idUsuarioRol;
  }

  public OtrosGastosProyectoDTO(Long idTipoRubro, Long conteo) {
    this.idTipoRubro = idTipoRubro;
    this.conteo = conteo.intValue();
  }

  public Long getIdOtrosGastosProyecto() {
    return idOtrosGastosProyecto;
  }

  public void setIdOtrosGastosProyecto(Long idOtrosGastosProyecto) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public String getNombreFuente() {
    return nombreFuente;
  }

  public void setNombreFuente(String nombreFuente) {
    this.nombreFuente = nombreFuente;
  }

  public Long getIdTipoRubro() {
    return idTipoRubro;
  }

  public void setIdTipoRubro(Long idTipoRubro) {
    this.idTipoRubro = idTipoRubro;
  }

  public Long getIdTipoEspecieEfectivo() {
    return idTipoEspecieEfectivo;
  }

  public void setIdTipoEspecieEfectivo(Long idTipoEspecieEfectivo) {
    this.idTipoEspecieEfectivo = idTipoEspecieEfectivo;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Double getValorGastadoInforme() {
    return valorGastadoInforme;
  }

  public void setValorGastadoInforme(Double valorGastadoInforme) {
    this.valorGastadoInforme = valorGastadoInforme;
  }

  public Integer getConteo() {
    return conteo;
  }

  public void setConteo(Integer conteo) {
    this.conteo = conteo;
  }

  @Override
  public String getLlaveModel() {
    if (null == this.idOtrosGastosProyecto) {
      return null;
    }

    return this.idOtrosGastosProyecto.toString();
  }

}
