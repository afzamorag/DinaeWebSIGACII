package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IInstitucionesProyectoLocal;
import co.gov.policia.dinae.modelo.InstitucionesProyecto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class InstitucionesProyectoEjbBean implements IInstitucionesProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idInstitucionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InstitucionesProyecto obtenerInstitucionesProyectoPorId(Long idInstitucionesProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(InstitucionesProyecto.class, idInstitucionesProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param institucionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InstitucionesProyecto guardarInstitucionesProyecto(InstitucionesProyecto institucionesProyecto) throws JpaDinaeException {
    try {

      if (institucionesProyecto.getIdInstitucionesProyecto() == null) {

        institucionesProyecto.setFechaRegistro(new Date());
        entityManager.persist(institucionesProyecto);

      } else {

        entityManager.merge(institucionesProyecto);

      }

      //ACTUALIZAMOS LA FECHA DEL ACTUALIZACION DEL PROYECTO
      entityManager.createNamedQuery("Proyecto.UpdateFechaActualiacionProyecto")
              .setParameter("fechaActualizacion", new Date())
              .setParameter("idProyecto", institucionesProyecto.getProyecto().getIdProyecto())
              .executeUpdate();

      return institucionesProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<InstitucionesProyecto> getListaInstitucionesProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InstitucionesProyecto.findAll")
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
  public List<InstitucionesProyectoDTO> getListaInstitucionesProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InstitucionesProyectoDTO.findPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idInstitucionesProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarInstitucionInvestigacion(Long idInstitucionesProyecto) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("InstitucionesProyecto.EliminarInstitucionesProyectoPorIdInstitucionesProyecto")
              .setParameter("idInstitucionesProyecto", idInstitucionesProyecto)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

}
