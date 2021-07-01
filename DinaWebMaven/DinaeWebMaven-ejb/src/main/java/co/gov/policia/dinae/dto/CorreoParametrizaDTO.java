package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CorreoParametrizaDTO implements Serializable {

  private String codigo;
  private String label;
  private String asunto;
  private String formato;
  private byte[] texto;

  public CorreoParametrizaDTO() {
  }

  /**
   *
   * @param codigo
   * @param label
   */
  public CorreoParametrizaDTO(String codigo, String label) {
    this.codigo = codigo;
    this.label = label;
  }

  /**
   *
   * @param codigo
   * @param label
   * @param asunto
   * @param formato
   * @param texto
   */
  public CorreoParametrizaDTO(String codigo, String label, String asunto, String formato, byte[] texto) {
    this.codigo = codigo;
    this.label = label;
    this.asunto = asunto;
    this.formato = formato;
    this.texto = texto;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getAsunto() {
    return asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public String getFormato() {
    return formato;
  }

  public void setFormato(String formato) {
    this.formato = formato;
  }

  public byte[] getTexto() {
    return texto;
  }

  public void setTexto(byte[] texto) {
    this.texto = texto;
  }
}
