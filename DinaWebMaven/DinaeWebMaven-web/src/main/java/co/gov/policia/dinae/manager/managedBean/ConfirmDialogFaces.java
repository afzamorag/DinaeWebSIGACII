package co.gov.policia.dinae.manager.managedBean;

import co.gov.policia.dinae.interfaces.IConfirmacionDialogoEvento;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "confirmDialogFaces")
@javax.enterprise.context.SessionScoped
public class ConfirmDialogFaces implements Serializable {

  private String titulo;
  private String mensaje;
  private String lblBtnCancelar;
  private String lblBtnContinuar;
  private IConfirmacionDialogoEvento iConfirmacionDialogoEvento;
  private String renderBtnCancelar;
  private String renderBtnContinuar;

  @javax.annotation.PostConstruct
  public void init() {

    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "INIT PostConstruct - ConfirmDialogFaces");

  }

  /**
   *
   * @param mostrarPanel
   * @param titulo
   * @param mensaje
   * @param lblBtnCancelar
   * @param lblBtnContinuar
   * @param iConfirmacionDialogoEvento
   * @param renderBtnCancelar
   * @param renderBtnContinuar
   */
  public void initConfirmDialogFaces(String titulo, String mensaje, String lblBtnCancelar, String lblBtnContinuar, IConfirmacionDialogoEvento iConfirmacionDialogoEvento, String renderBtnCancelar, String renderBtnContinuar) {

    this.titulo = titulo;
    this.mensaje = mensaje;
    this.lblBtnCancelar = lblBtnCancelar;
    this.lblBtnContinuar = lblBtnContinuar;
    this.iConfirmacionDialogoEvento = iConfirmacionDialogoEvento;
    this.renderBtnCancelar = renderBtnCancelar;
    this.renderBtnContinuar = renderBtnContinuar;
  }

  /**
   *
   */
  public void continuarConfirmacionDialogoEvento() {

    if (iConfirmacionDialogoEvento != null) {
      iConfirmacionDialogoEvento.continuarConfirmacionDialogoEvento();
    }
  }

  /**
   *
   */
  public void cancelarConfirmacionDialogoEvento() {

    if (iConfirmacionDialogoEvento != null) {
      iConfirmacionDialogoEvento.cancelarConfirmacionDialogoEvento();
    }

  }

  public String getTitulo() {
    return titulo;
  }

  public String getMensaje() {
    return mensaje;
  }

  public String getLblBtnCancelar() {
    return lblBtnCancelar;
  }

  public String getLblBtnContinuar() {
    return lblBtnContinuar;
  }

  public String getRenderBtnCancelar() {
    return renderBtnCancelar;
  }

  public String getRenderBtnContinuar() {
    return renderBtnContinuar;
  }

}
