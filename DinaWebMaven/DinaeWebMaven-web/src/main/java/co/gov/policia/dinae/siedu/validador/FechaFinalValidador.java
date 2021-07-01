/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.validador;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesValidator("fechaFinalValidador")
public class FechaFinalValidador implements Validator {

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (value == null) {
      return;
    }
    //Leave the null handling of startDate to required="true"
    Object startDateValue = component.getAttributes().get("fechaInicial");
    if (startDateValue == null) {
      return;
    }
    Date startDate = (Date) startDateValue;
    Date endDate = (Date) value;
    if (endDate.before(startDate)) {
      FacesMessage message = new FacesMessage();
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(message);
    }
  }
}
