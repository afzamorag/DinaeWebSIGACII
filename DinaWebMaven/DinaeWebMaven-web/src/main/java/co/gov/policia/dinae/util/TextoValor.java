package co.gov.policia.dinae.util;

import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class TextoValor implements Serializable {

  private String _texto;

  private Object _valor;

  public TextoValor(String _texto, String _valor) {
    this._texto = _texto;
    this._valor = _valor;
  }

  public String getTexto() {
    return _texto;
  }

  public void setTexto(String _texto) {
    this._texto = _texto;
  }

  public Object getValor() {
    return _valor;
  }

  public void setValor(Object _valor) {
    this._valor = _valor;
  }

}
