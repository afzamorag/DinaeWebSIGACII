package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.HorasInvestigador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IHorasInvestigadorLocal {

  /**
   *
   * @return @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<HorasInvestigador> findAll() throws JpaDinaeException;

  /**
   *
   * @param idHorasInvestigador
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  HorasInvestigador findByIdHorasInvestigador(Long idHorasInvestigador) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @param idInvestigadorProyecto
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  HorasInvestigador findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto(Long idProyecto, Long idCompromisoProyecto, Long idInvestigadorProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<HorasInvestigador> getListaHorasInvestigacionPorCompromisoProyectoYproyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param listaIdInformesCompromisos
   * @return
   * @throws Exception
   */
  Double getSumaCalculoHorasInvestigadorProyectoPorInformCompromisoProyecto(List<Long> listaIdInformesCompromisos) throws Exception;
}
