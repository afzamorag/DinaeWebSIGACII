/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface ITipoUnidadPeriodoLocal {

  List<TipoUnidadPeriodo> getTipoUnidadPeriodoByPeriodo(Periodo periodo) throws JpaDinaeException;
}
