package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IEnsayoCriticoLocal {

  /**
   *
   * @param ensayoCritico
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  EnsayoCritico saveOrUpdate(EnsayoCritico ensayoCritico) throws JpaDinaeException;

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  EnsayoCritico findByIdEnsayoCritico(Long idEnsayoCritico) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<EnsayoCritico> findByPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param identificadorUsuario
   * @return
   * @throws JpaDinaeException
   */
  EnsayoCritico findByPeriodoUsuario(Long idPeriodo, String identificadorUsuario) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<EnsayoCritico> findAll() throws JpaDinaeException;

  /**
   *
   * @param siglaPapa
   * @param idEstados
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<EnsayoCritico> findByPeriodoUnidadCobertura(String siglaPapa, List<Long> idEstados, Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idEstados
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<EnsayoCritico> findByPeriodoAllUnidades(List<Long> idEstados, Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  public int countByPeriodoVicin(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idConvocatoria
   * @throws JpaDinaeException
   */
  public void ejecutarProcedimientoResultadoConvocatoriaEnsayo(Long idConvocatoria) throws JpaDinaeException;
}
