package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IFuncionarioNecesidadLocal {

  /**
   *
   * @param idFuncionario
   * @throws JpaDinaeException
   */
  void eliminarFuncionarioNecesidad(Long idFuncionario) throws JpaDinaeException;

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  List<FuncionarioNecesidad> getListaFuncionarioNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException;
  
 /**
  * 
  * @param record
  * @return
  * @throws ServiceException 
  */ 
    FuncionarioNecesidad create(FuncionarioNecesidad record) throws ServiceException;

}
