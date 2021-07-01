package co.gov.policia.dinae.converter;

import co.gov.policia.dinae.modelo.TipoUnidad;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author juan
 */
public class TipoUnidadeConverter implements javax.faces.convert.Converter, Serializable {

  private final List<TipoUnidad> listaTipoUnidad;

  public TipoUnidadeConverter(List<TipoUnidad> listaTipoUnidad) {
    this.listaTipoUnidad = listaTipoUnidad;
  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {

    if (value == null) {
      return null;
    }
    for (TipoUnidad tipoUnidad : listaTipoUnidad) {
      if (tipoUnidad.getNombre().equals(value)) {
        return tipoUnidad;
      }
    }
    return null;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {

    if (value == null) {
      return "-";
    }
    TipoUnidad tu = (TipoUnidad) value;
    return tu.getNombre();
  }

}
