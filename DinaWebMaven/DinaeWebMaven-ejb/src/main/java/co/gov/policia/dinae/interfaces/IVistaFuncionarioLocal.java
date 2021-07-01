package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.InformacionAdicionalPersonaSiatDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.VistaFormacionFuncionario;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IVistaFuncionarioLocal {

    /**
     *
     * @return @throws JpaDinaeException
     */
    List<String> getListaTodosCorreos() throws JpaDinaeException;

    /**
     *
     * @return @throws JpaDinaeException
     */
    List<VistaFuncionario> getVistaFuncionarios() throws JpaDinaeException;

    /**
     * Retorna un funcionario buscando por identificacion, si no existe retorna
     * null
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    VistaFuncionario getVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException;

    /**
     * Retorna un funcionario buscando por identificacion, si no existe retorna
     * null
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    String getCorreoVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException;

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    String getCodigoUnidadLaborarVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException;

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    String getSiglaLaborandoVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException;

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    List<VistaFormacionFuncionario> getListaVistaFormacionFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException;

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    InformacionAdicionalPersonaSiatDTO obtenerInformacionAdicionalPersonaSIAT(String identificacion) throws JpaDinaeException;

    /**
     *
     * @param informacionAdicionalPersonaSiatDTO
     * @throws JpaDinaeException
     */
    void actualizarInformacionAdicionalPersonaSIAT(InformacionAdicionalPersonaSiatDTO informacionAdicionalPersonaSiatDTO) throws JpaDinaeException;

}
