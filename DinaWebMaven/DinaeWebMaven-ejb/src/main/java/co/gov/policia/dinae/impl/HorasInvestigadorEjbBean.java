package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IHorasInvestigadorLocal;
import co.gov.policia.dinae.modelo.HorasInvestigador;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cguzman
 */
@Stateless
public class HorasInvestigadorEjbBean implements IHorasInvestigadorLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  @Override
  public List<HorasInvestigador> findAll() throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("HorasInvestigador.findAll", HorasInvestigador.class)
              .getResultList();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idHorasInvestigador
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public HorasInvestigador findByIdHorasInvestigador(Long idHorasInvestigador) throws JpaDinaeException {
    try {
      return entityManager.find(HorasInvestigador.class, idHorasInvestigador);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @param idInvestigadorProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public HorasInvestigador findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto(Long idProyecto, Long idCompromisoProyecto, Long idInvestigadorProyecto) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("HorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto", HorasInvestigador.class)
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .setParameter("idInvestigadorProyecto", idInvestigadorProyecto)
              .getResultList();
      if (results != null && !results.isEmpty()) {
        return (HorasInvestigador) results.get(0);
      }

      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<HorasInvestigador> getListaHorasInvestigacionPorCompromisoProyectoYproyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("HorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYProyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param listaIdInformesCompromisos
   * @return
   * @throws Exception
   */
  @Override
  public Double getSumaCalculoHorasInvestigadorProyectoPorInformCompromisoProyecto(List<Long> listaIdInformesCompromisos) throws Exception {

    try {

      Object suma = entityManager.createNamedQuery("HorasInvestigador.SUMAhorasInvestigadorPorInformeCompromisoProyecto")
              .setParameter("ID_LISTA_COMPROMISO", listaIdInformesCompromisos)
              .getSingleResult();

      if (suma == null) {
        return 0D;
      }

      return ((Number) suma).doubleValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }
}
