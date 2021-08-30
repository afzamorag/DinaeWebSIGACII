/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.ReporteGeneralCapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.VwmPersonalCapacitado;
import java.util.List;
import java.util.Map;
import org.primefaces.model.StreamedContent;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public interface ReporteGeneralCapacitacionService {

    /**
     *
     * @param filtro
     * @return
     * @throws ServiceException
     */
    StreamedContent findByFilterTrainneds(ReporteGeneralCapacitacionFiltro filtro) throws ServiceException;

}
