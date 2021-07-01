package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.EjecutorNecesidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEjecutorNecesidadLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class EjecutorNecesidadEjbBean implements IEjecutorNecesidadLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecutorNecesidad> getEjecutorNecesidadPorPropuestaNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("EjecutorNecesidad.findAllPorPropuestaNecesidad")
              .setParameter("idEjecutorNecesidad", idPropuestaNecesidad)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecutorNecesidadDTO> getEjecutorNecesidadDTOPorPropuestaNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("EjecutorNecesidadDTO.findAllPorPropuestaNecesidad")
              .setParameter("idEjecutorNecesidad", idPropuestaNecesidad)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EjecutorNecesidad getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(Long idPropuestaNecesidad) throws JpaDinaeException {

    try {

      return (EjecutorNecesidad) entityManager.createNamedQuery("EjecutorNecesidad.findAllPorPropuestaNecesidadYRolResponsable")
              .setParameter("idEjecutorNecesidad", idPropuestaNecesidad)
              .setParameter("idRol", IConstantes.CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE)
              .getSingleResult();

    } catch (NoResultException e) {

      //NO EXISTE
      return null;

    } catch (NonUniqueResultException e) {

      throw new JpaDinaeException("Existe mas de un registro con rol: CONSTANTE_TIPO_ROL_PROYECTO_NECESIDAD_RESPONSABLE para esta propuesta: ".concat(idPropuestaNecesidad.toString()), e);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecutorNecesidad> getEjecutorNecesidadPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      if (idProyecto == null) {
        return new ArrayList<EjecutorNecesidad>();
      }

      return entityManager.createNamedQuery("EjecutorNecesidad.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EjecutorNecesidadDTO> getEjecutorNecesidadDTOPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      if (idProyecto == null) {
        return new ArrayList<EjecutorNecesidadDTO>();
      }

      return entityManager.createNamedQuery("EjecutorNecesidadDTO.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idProyecto
   * @param listadoEjecutorNecesidad
   * @throws JpaDinaeException
   */
  @Override
  public void cambiarRolReponsableProyect(Long idProyecto, List<EjecutorNecesidadDTO> listadoEjecutorNecesidad) throws JpaDinaeException {

    try {

      for (EjecutorNecesidadDTO ejecutorNecesidadDTO : listadoEjecutorNecesidad) {

        EjecutorNecesidad ejecutorNecesidad = entityManager.find(EjecutorNecesidad.class, ejecutorNecesidadDTO.getIdEjecutorNecesidad());
        ejecutorNecesidad.setRol(new Constantes(ejecutorNecesidadDTO.getIdRol()));

        entityManager.merge(ejecutorNecesidad);

      }

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }
}
