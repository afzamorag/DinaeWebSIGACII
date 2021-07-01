package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class MailDTO implements Serializable {

  private String asunto;
  private Set<String> destinatarios;
  private String contenido;
  private Throwable throwable;
  private String formato;

  private ArchivoAdjuntoDTO adjunto;

  /**
   * Constructor
   *
   * @param asunto
   * @param destinatarios
   * @param contenido
   * @param throwable
   */
  public MailDTO(String asunto, Set<String> destinatarios, String contenido, Throwable throwable) {
    this.asunto = asunto;
    this.destinatarios = destinatarios;
    this.contenido = contenido;
    this.throwable = throwable;
  }

  /**
   * Constructor
   *
   * @param asunto
   * @param destinatarios
   * @param contenido
   */
  public MailDTO(String asunto, Set<String> destinatarios, String contenido) {
    this.asunto = asunto;
    this.destinatarios = destinatarios;
    this.contenido = contenido;
  }

  /**
   *
   * @param asunto
   * @param destinatarios
   * @param contenido
   * @param formato
   */
  public MailDTO(String asunto, Set<String> destinatarios, String contenido, String formato) {
    this.asunto = asunto;
    this.destinatarios = destinatarios;
    this.contenido = contenido;
    this.formato = formato;
  }

  /**
   *
   * @param asunto
   * @param destinatarios
   * @param contenido
   * @param throwable
   * @param formato
   * @param adjunto
   */
  public MailDTO(String asunto, Set<String> destinatarios, String contenido, Throwable throwable, String formato, ArchivoAdjuntoDTO adjunto) {
    this.asunto = asunto;
    this.destinatarios = destinatarios;
    this.contenido = contenido;
    this.throwable = throwable;
    this.formato = formato;
    this.adjunto = adjunto;
  }

  public String getAsunto() {
    return asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public Set<String> getDestinatarios() {
    if (destinatarios == null) {
      destinatarios = new HashSet<String>();
    }
    return destinatarios;
  }

  public void setDestinatarios(Set<String> destinatarios) {
    this.destinatarios = destinatarios;
  }

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  public String getFormato() {
    return formato == null ? "text/plain" : formato;
  }

  public void setFormato(String formato) {
    this.formato = formato;
  }

  public ArchivoAdjuntoDTO getAdjunto() {
    return adjunto;
  }

  public void setAdjunto(ArchivoAdjuntoDTO adjunto) {
    this.adjunto = adjunto;
  }

}
