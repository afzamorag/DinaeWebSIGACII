package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ComentarioProyectoDTO implements Serializable {

  private Long idComentarioProyecto;
  private String comentario;
  private Long idProyecto;
  private Long idItem;
  private String valorItem;
  private Character comentarioEnviar;
  private boolean enviarComentarioCheck;
  private String identificacion;
  private String nombreCompleto;
  private Long idDocumProyecto;
  private Date fechaRegistro;

  public ComentarioProyectoDTO() {
  }

  /**
   *
   * @param idComentarioProyecto
   * @param comentario
   * @param idItem
   * @param valorItem
   * @param comentarioEnviar
   * @param fechaRegistro
   */
  public ComentarioProyectoDTO(Long idComentarioProyecto, String comentario, Long idItem, String valorItem, Character comentarioEnviar, Date fechaRegistro) {
    this.idComentarioProyecto = idComentarioProyecto;
    this.comentario = comentario;
    this.idItem = idItem;
    this.valorItem = valorItem;
    this.comentarioEnviar = comentarioEnviar;
    this.fechaRegistro = fechaRegistro;
  }

  /**
   *
   * @param idComentarioProyecto
   * @param comentario
   * @param idProyecto
   * @param idItem
   * @param valorItem
   * @param comentarioEnviar
   * @param identificacion
   * @param nombreCompleto
   */
  public ComentarioProyectoDTO(Long idComentarioProyecto, String comentario, Long idProyecto, Long idItem,
          String valorItem, Character comentarioEnviar, String identificacion, String nombreCompleto) {
    this.idComentarioProyecto = idComentarioProyecto;
    this.comentario = comentario;
    this.idProyecto = idProyecto;
    this.idItem = idItem;
    this.valorItem = valorItem;
    this.comentarioEnviar = comentarioEnviar;
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
  }

  /**
   *
   * @param idComentarioProyecto
   * @param comentario
   * @param idProyecto
   * @param idItem
   * @param valorItem
   * @param comentarioEnviar
   * @param identificacion
   * @param nombreCompleto
   * @param idDocumProyecto
   * @param fechaRegistro
   */
  public ComentarioProyectoDTO(Long idComentarioProyecto, String comentario, Long idProyecto, Long idItem, String valorItem, Character comentarioEnviar, String identificacion, String nombreCompleto, Long idDocumProyecto, Date fechaRegistro) {
    this.idComentarioProyecto = idComentarioProyecto;
    this.comentario = comentario;
    this.idProyecto = idProyecto;
    this.idItem = idItem;
    this.valorItem = valorItem;
    this.comentarioEnviar = comentarioEnviar;
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.idDocumProyecto = idDocumProyecto;
    this.fechaRegistro = fechaRegistro;
  }

  /**
   *
   * @param idComentarioProyecto
   * @param comentario
   * @param idProyecto
   * @param identificacion
   * @param nombreCompleto
   * @param idDocumProyecto
   * @param fechaRegistro
   */
  public ComentarioProyectoDTO(Long idComentarioProyecto, String comentario, Long idProyecto, String identificacion, String nombreCompleto, Long idDocumProyecto, Date fechaRegistro) {
    this.idComentarioProyecto = idComentarioProyecto;
    this.comentario = comentario;
    this.idProyecto = idProyecto;
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.idDocumProyecto = idDocumProyecto;
    this.fechaRegistro = fechaRegistro;
  }

  public String getValorItem() {
    return valorItem;
  }

  public void setValorItem(String valorItem) {
    this.valorItem = valorItem;
  }

  public Long getIdComentarioProyecto() {
    return idComentarioProyecto;
  }

  public void setIdComentarioProyecto(Long idComentarioProyecto) {
    this.idComentarioProyecto = idComentarioProyecto;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public Long getIdItem() {
    return idItem;
  }

  public void setIdItem(Long idItem) {
    this.idItem = idItem;
  }

  public Character getComentarioEnviar() {
    if (comentarioEnviar == null) {
      comentarioEnviar = 'N';
    }
    return comentarioEnviar;
  }

  public void setComentarioEnviar(Character comentarioEnviar) {
    this.comentarioEnviar = comentarioEnviar;
  }

  public boolean isEnviarComentarioCheck() {
    return enviarComentarioCheck;
  }

  public void setEnviarComentarioCheck(boolean enviarComentarioCheck) {
    this.enviarComentarioCheck = enviarComentarioCheck;
    if (enviarComentarioCheck) {
      comentarioEnviar = 'Y';
    } else {
      comentarioEnviar = 'N';
    }
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  /**
   * @return the idDocumProyecto
   */
  public Long getIdDocumProyecto() {
    return idDocumProyecto;
  }

  /**
   * @param idDocumProyecto the idDocumProyecto to set
   */
  public void setIdDocumProyecto(Long idDocumProyecto) {
    this.idDocumProyecto = idDocumProyecto;
  }

  /**
   * @return the fechaRegistro
   */
  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  /**
   * @param fechaRegistro the fechaPresentacion to set
   */
  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

}
