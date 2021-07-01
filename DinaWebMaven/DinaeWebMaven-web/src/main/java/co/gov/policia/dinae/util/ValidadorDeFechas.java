package co.gov.policia.dinae.util;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Édder Peña barranco
 * @since 2014/01/07
 */
@FacesValidator("validadorDeFechas")
public class ValidadorDeFechas extends JSFUtils implements Validator {

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

    if (value == null) {
      return; // Let required="true" handle.
    }

    UIInput componenteFechaIni = (UIInput) component.getAttributes().get("componenteFechaIni");

    Date fechaIni = (Date) componenteFechaIni.getValue();
    Date fechaFin = (Date) value;

    if (fechaIni.after(fechaFin)) {
      componenteFechaIni.setValid(false);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_fecha_inicial_mayor_que_final"));
    }
    if (fechaIni.after(new Date())) {
      componenteFechaIni.setValid(false);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_fecha_inicial_mayor_que_actual"));
    }
    if (fechaFin.after(new Date())) {
      componenteFechaIni.setValid(false);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_fecha_final_mayor_que_actual"));
    }
  }

}
