package co.gov.policia.dinae.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@XmlRootElement
public class NivelAcademicoDTO implements Serializable {

  private Long consecutivo;
  private String descripcion;

  /**
   *
   */
  public NivelAcademicoDTO() {
  }

  public NivelAcademicoDTO(Long consecutivo, String descripcion) {
    this.consecutivo = consecutivo;
    this.descripcion = descripcion;
  }

  public Long getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(Long consecutivo) {
    this.consecutivo = consecutivo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
