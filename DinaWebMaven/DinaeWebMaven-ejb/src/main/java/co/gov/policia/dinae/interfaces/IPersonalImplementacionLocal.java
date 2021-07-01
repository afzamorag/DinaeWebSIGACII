package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.PersonalImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Peña Barrancop
 * @since 2013/12/16
 */
@Local
public interface IPersonalImplementacionLocal {

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  public PersonalImplementacion getPersonalImplementacionByIdentificacion(String numIdentificacion)
          throws JpaDinaeException;

  /**
   *
   * @param personalImpl
   * @return
   * @throws JpaDinaeException
   */
  public PersonalImplementacion savePersonalImplementacion(PersonalImplementacion personalImpl)
          throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  public List<PersonalImplementacion> getPersonalImplementacionListPorIdInformeImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException;
}
