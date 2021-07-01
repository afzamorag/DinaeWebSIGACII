package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.UnidadDependenciaDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface IUnidadDependenciaLocal {

  /**
   *
   * @param listaTipoUnidad
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadDependenciaDTO> getUnidadDependenciaVigenteByTipo(List<Long> listaTipoUnidad) throws JpaDinaeException;
  List<UnidadDependencia> getUnidadDependenciaVigenteByEscuela() throws JpaDinaeException;
  List<UnidadDependencia> findUnidadDependenciaVigenteByTipo(List<Long> listaTipoUnidad) throws JpaDinaeException;
  UnidadDependencia getUnidadDependenciaById(Long consecutivo) throws JpaDinaeException;
}
