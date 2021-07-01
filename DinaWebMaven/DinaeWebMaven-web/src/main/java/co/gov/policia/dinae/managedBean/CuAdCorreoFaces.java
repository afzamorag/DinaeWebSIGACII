package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.modelo.CorreoParametriza;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuAdCorreoFaces")
@javax.enterprise.context.SessionScoped
public class CuAdCorreoFaces extends JSFUtils implements Serializable {

  @EJB
  private IMailSessionLocal iMailSessionBean;

  private List<SelectItem> listaCodigoCorreoItem;
  private String codigoCorreoSeleccionado;
  private CorreoParametriza correoParametrizaSeleccionado;
  private CorreoParametriza correoParametrizaSeleccionadoTest;
  private String textoCadenaCorreo;
  private int idTabSeleccionado;
  private String keyAsunto1;
  private String keyAsunto2;
  private String keyAsunto3;
  private String keyAsunto4;
  private String keyAsunto5;
  private String valueAsunto1;
  private String valueAsunto2;
  private String valueAsunto3;
  private String valueAsunto4;
  private String valueAsunto5;

  private String keyContenido1;
  private String keyContenido2;
  private String keyContenido3;
  private String keyContenido4;
  private String keyContenido5;
  private String valueContenido1;
  private String valueContenido2;
  private String valueContenido3;
  private String valueContenido4;
  private String valueContenido5;

  private String correoTest;

  @javax.annotation.PostConstruct
  public void init() {

    try {

      correoTest = null;

      keyAsunto1 = keyAsunto2 = keyAsunto3 = keyAsunto4 = keyAsunto5 = null;
      valueAsunto1 = valueAsunto2 = valueAsunto3 = valueAsunto4 = valueAsunto5 = null;

      keyContenido1 = keyContenido2 = keyContenido3 = keyContenido4 = keyContenido5 = null;
      valueContenido1 = valueContenido2 = valueContenido3 = valueContenido4 = valueContenido5 = null;

      idTabSeleccionado = 0;
      textoCadenaCorreo = null;
      listaCodigoCorreoItem = null;
      codigoCorreoSeleccionado = null;
      correoParametrizaSeleccionado = null;
      correoParametrizaSeleccionadoTest = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-Ad-Correo", e);
    }
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      listaCodigoCorreoItem = listaCodigoCorreoItem = UtilidadesItem.getListaSel(
              iMailSessionBean.getListCorreoEnvioDTO(),
              "codigo",
              "label");

      return navigationFaces.redirectCuAdPlantillaCorreoRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-Ad-Correo", e);

    }

    return null;

  }

  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;

    if (event == null || event.getTab() == null) {
      return;
    }
    if ("idTabDetalleCorreo".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("idTabTestCorreo".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    }
  }

  /**
   *
   * @return
   */
  public String limpiar() {

    correoParametrizaSeleccionado = null;

    correoParametrizaSeleccionadoTest = null;

    codigoCorreoSeleccionado = null;

    keyAsunto1 = keyAsunto2 = keyAsunto3 = keyAsunto4 = keyAsunto5 = null;
    valueAsunto1 = valueAsunto2 = valueAsunto3 = valueAsunto4 = valueAsunto5 = null;

    keyContenido1 = keyContenido2 = keyContenido3 = keyContenido4 = keyContenido5 = null;
    valueContenido1 = valueContenido2 = valueContenido3 = valueContenido4 = valueContenido5 = null;

    idTabSeleccionado = 0;

    correoTest = null;

    return navigationFaces.redirectCuAdPlantillaCorreoRedirect();

  }

  /**
   *
   * @return
   */
  public String guardarCorreo() {

    try {

      correoParametrizaSeleccionado.setTexto(textoCadenaCorreo.getBytes());

      correoParametrizaSeleccionadoTest = iMailSessionBean.actualizarCorreoParametriza(correoParametrizaSeleccionado);

      textoCadenaCorreo = new String(correoParametrizaSeleccionado.getTexto());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-Ad-Correo", e);

    }

    return null;

  }

  /**
   *
   * @return
   */
  public String probarCorreo() {

    try {

      Map<String, String> parametrosAsunto = new HashMap<String, String>();
      if (keyAsunto1 != null && keyAsunto1.trim().length() > 0 && valueAsunto1 != null && valueAsunto1.trim().length() > 0) {
        parametrosAsunto.put(keyAsunto1, valueAsunto1);
      }

      if (keyAsunto2 != null && keyAsunto2.trim().length() > 0 && valueAsunto2 != null && valueAsunto2.trim().length() > 0) {
        parametrosAsunto.put(keyAsunto2, valueAsunto2);
      }

      if (keyAsunto3 != null && keyAsunto3.trim().length() > 0 && valueAsunto3 != null && valueAsunto3.trim().length() > 0) {
        parametrosAsunto.put(keyAsunto3, valueAsunto3);
      }

      if (keyAsunto4 != null && keyAsunto4.trim().length() > 0 && valueAsunto4 != null && valueAsunto4.trim().length() > 0) {
        parametrosAsunto.put(keyAsunto4, valueAsunto4);
      }

      if (keyAsunto5 != null && keyAsunto5.trim().length() > 0 && valueAsunto5 != null && valueAsunto5.trim().length() > 0) {
        parametrosAsunto.put(keyAsunto5, valueAsunto5);
      }

      Map<String, Object> parametrosContenido = new HashMap<String, Object>();
      if (keyContenido1 != null && keyContenido1.trim().length() > 0 && valueContenido1 != null && valueContenido1.trim().length() > 0) {
        parametrosContenido.put(keyContenido1, valueContenido1);
      }

      if (keyContenido2 != null && keyContenido2.trim().length() > 0 && valueContenido2 != null && valueContenido2.trim().length() > 0) {
        parametrosContenido.put(keyContenido2, valueContenido2);
      }

      if (keyContenido3 != null && keyContenido3.trim().length() > 0 && valueContenido3 != null && valueContenido3.trim().length() > 0) {
        parametrosContenido.put(keyContenido3, valueContenido3);
      }

      if (keyContenido4 != null && keyContenido4.trim().length() > 0 && valueContenido4 != null && valueContenido4.trim().length() > 0) {
        parametrosContenido.put(keyContenido4, valueContenido4);
      }

      if (keyContenido5 != null && keyContenido5.trim().length() > 0 && valueContenido5 != null && valueContenido5.trim().length() > 0) {
        parametrosContenido.put(keyContenido5, valueContenido5);
      }

      List<String> listaCorreo = new ArrayList<String>();
      listaCorreo.add(correoTest);

      iMailSessionBean.enviarMail_ListaCorreo(codigoCorreoSeleccionado, parametrosAsunto, parametrosContenido, listaCorreo);

      adicionaMensajeInformativo("Correo Enviado correctamente");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-Ad-Correo", e);

    }

    return null;

  }

  public void handleSeleccionarCorreo() {

    if (codigoCorreoSeleccionado == null) {
      return;
    }

    try {

      correoParametrizaSeleccionado = iMailSessionBean.getCorreoParametriza(codigoCorreoSeleccionado);
      correoParametrizaSeleccionadoTest = correoParametrizaSeleccionado;

      textoCadenaCorreo = new String(correoParametrizaSeleccionado.getTexto());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-Ad-Correo", e);

    }

  }

  public List<SelectItem> getListaCodigoCorreoItem() {
    return listaCodigoCorreoItem;
  }

  public String getCodigoCorreoSeleccionado() {
    return codigoCorreoSeleccionado;
  }

  public void setCodigoCorreoSeleccionado(String codigoCorreoSeleccionado) {
    this.codigoCorreoSeleccionado = codigoCorreoSeleccionado;
  }

  public CorreoParametriza getCorreoParametrizaSeleccionado() {
    return correoParametrizaSeleccionado;
  }

  public void setCorreoParametrizaSeleccionado(CorreoParametriza correoParametrizaSeleccionado) {
    this.correoParametrizaSeleccionado = correoParametrizaSeleccionado;
  }

  public boolean isMostrarDetalleMailSeleccionado() {

    return correoParametrizaSeleccionado != null;

  }

  public String getTextoCadenaCorreo() {
    return textoCadenaCorreo;
  }

  public void setTextoCadenaCorreo(String textoCadenaCorreo) {
    this.textoCadenaCorreo = textoCadenaCorreo;
  }

  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public String getKeyAsunto1() {
    return keyAsunto1;
  }

  public void setKeyAsunto1(String keyAsunto1) {
    this.keyAsunto1 = keyAsunto1;
  }

  public String getKeyAsunto2() {
    return keyAsunto2;
  }

  public void setKeyAsunto2(String keyAsunto2) {
    this.keyAsunto2 = keyAsunto2;
  }

  public String getKeyAsunto3() {
    return keyAsunto3;
  }

  public void setKeyAsunto3(String keyAsunto3) {
    this.keyAsunto3 = keyAsunto3;
  }

  public String getKeyAsunto4() {
    return keyAsunto4;
  }

  public void setKeyAsunto4(String keyAsunto4) {
    this.keyAsunto4 = keyAsunto4;
  }

  public String getKeyAsunto5() {
    return keyAsunto5;
  }

  public void setKeyAsunto5(String keyAsunto5) {
    this.keyAsunto5 = keyAsunto5;
  }

  public String getValueAsunto1() {
    return valueAsunto1;
  }

  public void setValueAsunto1(String valueAsunto1) {
    this.valueAsunto1 = valueAsunto1;
  }

  public String getValueAsunto2() {
    return valueAsunto2;
  }

  public void setValueAsunto2(String valueAsunto2) {
    this.valueAsunto2 = valueAsunto2;
  }

  public String getValueAsunto3() {
    return valueAsunto3;
  }

  public void setValueAsunto3(String valueAsunto3) {
    this.valueAsunto3 = valueAsunto3;
  }

  public String getValueAsunto4() {
    return valueAsunto4;
  }

  public void setValueAsunto4(String valueAsunto4) {
    this.valueAsunto4 = valueAsunto4;
  }

  public String getValueAsunto5() {
    return valueAsunto5;
  }

  public void setValueAsunto5(String valueAsunto5) {
    this.valueAsunto5 = valueAsunto5;
  }

  public String getKeyContenido1() {
    return keyContenido1;
  }

  public void setKeyContenido1(String keyContenido1) {
    this.keyContenido1 = keyContenido1;
  }

  public String getKeyContenido2() {
    return keyContenido2;
  }

  public void setKeyContenido2(String keyContenido2) {
    this.keyContenido2 = keyContenido2;
  }

  public String getKeyContenido3() {
    return keyContenido3;
  }

  public void setKeyContenido3(String keyContenido3) {
    this.keyContenido3 = keyContenido3;
  }

  public String getKeyContenido4() {
    return keyContenido4;
  }

  public void setKeyContenido4(String keyContenido4) {
    this.keyContenido4 = keyContenido4;
  }

  public String getKeyContenido5() {
    return keyContenido5;
  }

  public void setKeyContenido5(String keyContenido5) {
    this.keyContenido5 = keyContenido5;
  }

  public String getValueContenido1() {
    return valueContenido1;
  }

  public void setValueContenido1(String valueContenido1) {
    this.valueContenido1 = valueContenido1;
  }

  public String getValueContenido2() {
    return valueContenido2;
  }

  public void setValueContenido2(String valueContenido2) {
    this.valueContenido2 = valueContenido2;
  }

  public String getValueContenido3() {
    return valueContenido3;
  }

  public void setValueContenido3(String valueContenido3) {
    this.valueContenido3 = valueContenido3;
  }

  public String getValueContenido4() {
    return valueContenido4;
  }

  public void setValueContenido4(String valueContenido4) {
    this.valueContenido4 = valueContenido4;
  }

  public String getValueContenido5() {
    return valueContenido5;
  }

  public void setValueContenido5(String valueContenido5) {
    this.valueContenido5 = valueContenido5;
  }

  public String getCorreoTest() {
    return correoTest;
  }

  public void setCorreoTest(String correoTest) {
    this.correoTest = correoTest;
  }

  public CorreoParametriza getCorreoParametrizaSeleccionadoTest() {
    return correoParametrizaSeleccionadoTest;
  }

  public void setCorreoParametrizaSeleccionadoTest(CorreoParametriza correoParametrizaSeleccionadoTest) {
    this.correoParametrizaSeleccionadoTest = correoParametrizaSeleccionadoTest;
  }

}
