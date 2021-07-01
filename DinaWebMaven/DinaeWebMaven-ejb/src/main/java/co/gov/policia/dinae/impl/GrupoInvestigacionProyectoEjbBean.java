package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.GrupoInvestigacionProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionProyectoLocal;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
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
public class GrupoInvestigacionProyectoEjbBean implements IGrupoInvestigacionProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idGrupoInvestigacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public GrupoInvestigacionProyecto obtenerGrupoInvestigacionProyectoPorId(Long idGrupoInvestigacionProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(GrupoInvestigacionProyecto.class, idGrupoInvestigacionProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idGrupoInvestigacionProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarGrupoInvestigacionProyecto(Long idGrupoInvestigacionProyecto) throws JpaDinaeException {
    try {

      entityManager.createNamedQuery("GrupoInvestigacionProyecto.EliminarPorId")
              .setParameter("idGrupoInvestigacionProyecto", idGrupoInvestigacionProyecto)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param grupoInvestigacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public GrupoInvestigacionProyecto guardarGrupoInvestigacionProyecto(GrupoInvestigacionProyecto grupoInvestigacionProyecto) throws JpaDinaeException {
    try {

      if (grupoInvestigacionProyecto.getIdGrupoInvestigacionProyecto() == null) {

        grupoInvestigacionProyecto.setFechaRegistro(new Date());
        entityManager.persist(grupoInvestigacionProyecto);

      } else {

        entityManager.merge(grupoInvestigacionProyecto);

      }
      //ACTUALIZAMOS LA FECHA DEL ACTUALIZACION DEL PROYECTO
      entityManager.createNamedQuery("Proyecto.UpdateFechaActualiacionProyecto")
              .setParameter("fechaActualizacion", new Date())
              .setParameter("idProyecto", grupoInvestigacionProyecto.getProyecto().getIdProyecto())
              .executeUpdate();

      return grupoInvestigacionProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("GrupoInvestigacionProyecto.findAll")
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
  public List<GrupoInvestigacionProyectoDTO> getListaGrupoInvestigacionProyectoDTOPorProyecto(Long idProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("GrupoInvestigacionProyectoDTO.findAll")
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
  public List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("GrupoInvestigacionProyecto.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

}
