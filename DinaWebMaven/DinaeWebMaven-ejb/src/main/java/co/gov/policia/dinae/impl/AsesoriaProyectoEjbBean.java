package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAsesoriaProyectoLocal;
import co.gov.policia.dinae.modelo.AsesoriaProyecto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author RafaelGomez
 */
@Stateless
public class AsesoriaProyectoEjbBean implements IAsesoriaProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idAsesoriaProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public AsesoriaProyecto getAsesoriaProyecto(Long idAsesoriaProyecto) throws JpaDinaeException {

    try {

      return entityManager.find(AsesoriaProyecto.class, idAsesoriaProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param asesoriaProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public AsesoriaProyecto guardarAsesoriaProyecto(AsesoriaProyecto asesoriaProyecto) throws JpaDinaeException {
    try {

      if (asesoriaProyecto.getIdAsesoriaProyecto() == null) {

        asesoriaProyecto.setFechaRegistro(new Date());
        entityManager.persist(asesoriaProyecto);

      } else {

        asesoriaProyecto.setFechaActualizacion(new Date());
        entityManager.merge(asesoriaProyecto);
      }

      return asesoriaProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<AsesoriaProyecto> getListaAsesoriaProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("AsesoriaProyecto.findAllByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTOPorIdProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("AsesoriaProyectoDTO.findAllByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }
}
