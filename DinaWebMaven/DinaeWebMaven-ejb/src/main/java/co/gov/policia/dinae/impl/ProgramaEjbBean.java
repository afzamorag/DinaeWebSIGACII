package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.ConsultaProgramaDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IProgramaLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class ProgramaEjbBean implements IProgramaLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   * @param escuela
   * @param nivelAcademico
   * @param metodologia
   * @param vigencia
   * @return
   * @throws JpaDinaeException
   */
  @Override

  public List<ConsultaProgramaDTO> getProgramaPorNivelEstrategia(Long escuela, Long nivelAcademico, Long metodologia, Long vigencia) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("SieduProgramasPae.findByIdNivelAcademicoModalidadUndeCodEscuela")
              .setParameter("escuela", escuela)
              .setParameter("nivelAcademico", nivelAcademico)
              .setParameter("metodologia", metodologia)
              .setParameter("vigencia", vigencia)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }

  }

}
