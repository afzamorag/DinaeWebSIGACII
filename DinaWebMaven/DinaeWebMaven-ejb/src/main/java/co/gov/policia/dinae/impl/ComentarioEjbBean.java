package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IComentarioLocal;
import co.gov.policia.dinae.modelo.Comentario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class ComentarioEjbBean implements IComentarioLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   * Consulta todos los comentarios que tiene asiganado una propuesta
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Comentario> getComentarioNecesidadPorPropuestaNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ComentarioNecesidad.findAllPorPropuestaNecesidad")
              .setParameter("idPropuestaNecesidad", idPropuestaNecesidad)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idProyeto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Comentario> getComentarioProyectoPorProyecto(Long idProyeto) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("ComentarioProyecto.findAllPorProyecto")
              .setParameter("idProyecto", idProyeto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

}
