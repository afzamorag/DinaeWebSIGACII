package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ConstantesDTO implements Serializable {

  private Long idConstantes;
  private String tipo;
  private String codigo;
  private String valor;
  private String estado;

  /**
   *
   * @param idConstantes
   * @param tipo
   * @param codigo
   * @param valor
   */
  public ConstantesDTO(Long idConstantes, String tipo, String codigo, String valor) {
    this.idConstantes = idConstantes;
    this.tipo = tipo;
    this.codigo = codigo;
    this.valor = valor;
  }

  public ConstantesDTO(Long idConstantes, String tipo, String codigo, String valor, String estado) {
    this.idConstantes = idConstantes;
    this.tipo = tipo;
    this.codigo = codigo;
    this.valor = valor;
    this.estado = estado;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Long getIdConstantes() {
    return idConstantes;
  }

  public void setIdConstantes(Long idConstantes) {
    this.idConstantes = idConstantes;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

}
