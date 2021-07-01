/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.LugarGeograficoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.LugarGeografico;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/02
 */
@Local
public interface ILugarGeograficoLocal {

  /**
   * Método para obtener la lista de ciudades
   *
     * @param codDepartamento
     * @param descDepartamento
   * @return lista de ciudades
   * @throws JpaDinaeException
   */
  public List<LugarGeografico> getListaLugares(Long codDepartamento, String descDepartamento)
          throws JpaDinaeException;

  public List<LugarGeografico> getListaLugaresByCodDepartamento(Long codDepartamento)
          throws JpaDinaeException;

  public List<LugarGeograficoDTO> getListaDepartamentos(Long codPais)
          throws JpaDinaeException;
  /**
   * Obtiene la lista de los países de los 5 continentes
   *
   * @return Una lista con los países
   * @throws JpaDinaeException
   */
  public List<LugarGeografico> getListaPaises() throws JpaDinaeException;

  /**
   *
   * @param codMunicipio
   * @return
   * @throws JpaDinaeException
   */
  public LugarGeografico findMunicipioByCodMunicipio(Long codMunicipio) throws JpaDinaeException;
  /**
   *
   * @param tiposMun
   * @return
   * @throws JpaDinaeException
   */
  public List<LugarGeografico> findLugarGeoggraficoByTipo(List<String> tiposMun) throws JpaDinaeException;
  
}
