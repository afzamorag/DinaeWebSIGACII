package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IExcepcionCompromisoLocal;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.ExcepcionesCompromiso;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class ExcepcionCompromisoEjbBean implements IExcepcionCompromisoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param excepcionesCompromiso
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ExcepcionesCompromiso guardarExcepcionesCompromiso(ExcepcionesCompromiso excepcionesCompromiso) throws JpaDinaeException {

    try {

      if (excepcionesCompromiso.getCompromisoProyectoCorreccion() != null
              && excepcionesCompromiso.getCompromisoProyectoCorreccion().getIdCompromisoProyecto() != null) {

        CompromisoProyecto compromisoProyectoCorreccion = entityManager.find(CompromisoProyecto.class, excepcionesCompromiso.getCompromisoProyectoCorreccion().getIdCompromisoProyecto());
        compromisoProyectoCorreccion.setFechaNuevoCompromiso(excepcionesCompromiso.getFechaLimite());
        compromisoProyectoCorreccion.setFechaCompromiso(excepcionesCompromiso.getFechaLimite());
        excepcionesCompromiso.setNombreCompromisoCorreccion(compromisoProyectoCorreccion.getProyecto().getCodigoProyecto().concat(" - ").concat(compromisoProyectoCorreccion.getNombreCompromisoCorreccionNumeroIncrementa()));

        entityManager.merge(compromisoProyectoCorreccion);
      }

      if (excepcionesCompromiso.getIdExcepcionCompromiso() == null) {

        entityManager.persist(excepcionesCompromiso);

      } else {

        entityManager.merge(excepcionesCompromiso);

      }

      return excepcionesCompromiso;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ExcepcionesCompromiso> getListaExcepcionesCompromisoPorUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ExcepcionesCompromiso.findAllPorUnidadPolicial")
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ExcepcionesCompromiso getUltimaExcepcionesCompromisoPorUnidadPolicialYcompromisoPeriodo(Long idUnidadPolicial, Long idCompromisoPeriodo) throws JpaDinaeException {

    try {

      return (ExcepcionesCompromiso) entityManager.createNamedQuery("ExcepcionesCompromiso.findUltimaPorUnidadPolicialYcompromisoPeriodo")
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("idCompromisoPeriodo", idCompromisoPeriodo)
              .setMaxResults(1)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

}
