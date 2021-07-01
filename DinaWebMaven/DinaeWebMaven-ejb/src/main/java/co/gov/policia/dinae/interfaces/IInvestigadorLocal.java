package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.InvestigadorDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.FormacionInvestigador;
import co.gov.policia.dinae.modelo.InvestigacionDesarrollada;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.PublicacionInvestigador;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Édder Peña Barranco
 */
@Local
public interface IInvestigadorLocal {

  /**
   * Sirve para persistir la información del investigador
   *
   * @param investigador Datos del investigador a persistir
   * @param formaciones
   * @throws JpaDinaeException
   */
  public Investigador insertDatosInvestigador(Investigador investigador, List<FormacionInvestigador> formaciones) throws JpaDinaeException;

  /**
   *
   * @param numIdInvestigador
   * @return
   * @throws JpaDinaeException
   */
  public List<FormacionInvestigador> getFormacionInvestigadorByNumIdentificacion(String numIdInvestigador)
          throws JpaDinaeException;

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  public Investigador getInvestigadorPorNumeroIdentificacion(String numIdentificacion)
          throws JpaDinaeException;

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  public List<InvestigacionDesarrollada> getInvestigacionesDesarrolladasByNumIdentificacion(String numIdentificacion) throws JpaDinaeException;

  /**
   *
   * @param esInvestigador
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  public List<InvestigacionDesarrollada> getInvestigacionesDesarrolladasByNumIdentificacion(boolean esInvestigador, String numIdentificacion) throws JpaDinaeException;

  /**
   *
   * @param investigacion
   * @throws JpaDinaeException
   */
  public void guardarInvestigacionDesarrollada(InvestigacionDesarrollada investigacion) throws JpaDinaeException;

  /**
   *
   * @param criterioUnidadPolicial
   * @param criterioIdentificacion
   * @param criterioNombres
   * @param criterioApellidos
   * @param criterioEstado
   * @param criterioProfesorPolicial
   * @param criterioNivelesFormacion
   * @param criterioCaracteristicas
   * @param criterioGrado
   * @param criterioVinculacion
   * @return
   * @throws JpaDinaeException
   */
  public List<Investigador> getInvestigadoresByFiltros(Long criterioUnidadPolicial, String criterioIdentificacion,
          String criterioNombres, String criterioApellidos, Long criterioEstado, String criterioProfesorPolicial,
          List<Long> criterioNivelesFormacion, List<Long> criterioCaracteristicas, String criterioGrado,
          Long criterioVinculacion) throws JpaDinaeException;

  /**
   *
   * @param criterioUnidadPolicial
   * @param criterioIdentificacion
   * @param criterioNombres
   * @param criterioApellidos
   * @param criterioEstado
   * @param criterioProfesorPolicial
   * @param criterioNivelesFormacion
   * @param criterioCaracteristicas
   * @param criterioGrado
   * @param criterioVinculacion
   * @return
   * @throws JpaDinaeException
   */
  public List<InvestigadorDTO> getInvestigadoresYPersonalSiatFiltros(Long criterioUnidadPolicial, String criterioIdentificacion,
          String criterioNombres, String criterioApellidos, Long criterioEstado, String criterioProfesorPolicial,
          List<Long> criterioNivelesFormacion, List<Long> criterioCaracteristicas, String criterioGrado,
          Long criterioVinculacion) throws JpaDinaeException;

  /**
   *
   * @param publicacion
   * @throws JpaDinaeException
   */
  public void guardarPublicacionInvestigador(PublicacionInvestigador publicacion) throws JpaDinaeException;

  /**
   *
   * @param numIdentificacionInv
   * @return
   * @throws JpaDinaeException
   */
  public List<PublicacionInvestigador> getPublicacionesByNumIdentificacion(String numIdentificacionInv)
          throws JpaDinaeException;

  /**
   *
   * @param esInvestiugador
   * @param numIdentificacionInv
   * @return
   * @throws JpaDinaeException
   */
  public List<PublicacionInvestigador> getPublicacionesByNumIdentificacion(boolean esInvestiugador, String numIdentificacionInv)
          throws JpaDinaeException;

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  public VistaFuncionario getInvestigadorSIATHByIdentificacion(String numIdentificacion)
          throws JpaDinaeException;

  /**
   *
   * @param sesion
   * @param identificacion
   * @param origen
   * @throws JpaDinaeException
   */
  void buscarNivelesAcademicos(String sesion, String identificacion, String origen) throws JpaDinaeException;
}
