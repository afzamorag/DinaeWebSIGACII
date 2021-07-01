package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IGrupoInvestigacionLocal;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class GrupoInvestigacionEjbBean implements IGrupoInvestigacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idGrupoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public GrupoInvestigacion obtenerGrupoInvestigacionPorId(Long idGrupoInvestigacion) throws JpaDinaeException {
    try {

      return entityManager.find(GrupoInvestigacion.class, idGrupoInvestigacion);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param tema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public GrupoInvestigacion guardarGrupoInvestigacion(GrupoInvestigacion tema) throws JpaDinaeException {
    try {

      if (tema.getIdGrupoInvestigacion() == null) {

        entityManager.persist(tema);

      } else {

        entityManager.merge(tema);

      }

      return tema;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<GrupoInvestigacion> getListaGrupoInvestigacionTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("GrupoInvestigacion.findAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  @Override
  public List<GrupoInvestigacion> getListaGrupoInvestigacionTodosEstados() throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("GrupoInvestigacion.findAllEstados")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

}
