/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.TipoCompetenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import java.util.List;

/**
 *
 * @author Juan Jose Buzon
 */
public interface CompetenciaService {

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduCompetencia> consultarCompetencias() throws ServiceException;

  /**
   *
   * @return @throws ServiceException
   */
  List<SieduCompetencia> consultarCompetenciasPorTipo(TipoCompetenciaEnum tipo) throws ServiceException;
    
}
