/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.TipoGastoEvento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/03
 */
@Local
public interface ITipoGastoEventoLocal {

  public TipoGastoEvento getTipoGastoEventoByNombreTipoGasto(String nombreTipoGasto,
          String tipo) throws JpaDinaeException;

  /**
   *
   * @param gastoEvento
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  public void insertTipoGasto(TipoGastoEvento gastoEvento) throws JpaDinaeException;

  /**
   *
   * @param tipoGastoEvento
   * @return
   * @throws JpaDinaeException
   */
  public TipoGastoEvento saveOrUpdate(TipoGastoEvento tipoGastoEvento) throws JpaDinaeException;

  /**
   *
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<TipoGastoEvento> findTipoGastoEventioByEvento(Long idEventoProyecto) throws JpaDinaeException;

  /**
   *
   * @param _old
   * @param _new
   * @throws JpaDinaeException
   */
  public void replaceTipoGastoProyecto(List<TipoGastoEvento> _old, List<TipoGastoEvento> _new) throws JpaDinaeException;

}
