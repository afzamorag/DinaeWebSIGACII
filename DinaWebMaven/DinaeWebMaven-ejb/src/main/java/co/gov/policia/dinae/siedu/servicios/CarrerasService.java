package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Carreras1;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan Carlos Cifuentes Murcia (juan.cifuentes@correo.policia.gov.co)
 */
@Local
public interface CarrerasService {

  /**
   *
     * @param nivelAcademico
   * @return @throws JpaDinaeException
   */
  List<Carreras1> getCarreraVigenteByNivelAcademico(Long nivelAcademico) throws JpaDinaeException;
  
  /**
   *
     * @param idCarrera
   * @return @throws JpaDinaeException
   */
  Carreras1 getCarreraVigenteByidCarrera(Long idCarrera) throws JpaDinaeException;

}
