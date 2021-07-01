package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ITemaProyectoLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.TemaProyecto;
import java.io.Serializable;
import java.util.Date;
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
public class TemaProyectoEjbBean implements ITemaProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idTemaProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TemaProyecto obtenerTemaProyectoPorId(Long idTemaProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(TemaProyecto.class, idTemaProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param temaProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TemaProyecto guardarTemaProyecto(TemaProyecto temaProyecto) throws JpaDinaeException {
    try {

      if (temaProyecto.getIdTemaProyecto() == null) {

        temaProyecto.setFechaRegistro(new Date());
        entityManager.persist(temaProyecto);

      } else {

        entityManager.merge(temaProyecto);

      }

      return temaProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param temaProyecto
   * @param idEstadoConvocatoria
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TemaProyecto guardarTemaProyectoYactualizarEstadoPropuestaConvocatoria(TemaProyecto temaProyecto, Long idEstadoConvocatoria) throws JpaDinaeException {
    try {

      if (temaProyecto.getIdTemaProyecto() == null) {

        temaProyecto.setFechaRegistro(new Date());
        entityManager.persist(temaProyecto);

      } else {

        entityManager.merge(temaProyecto);

      }

      Proyecto proyecto = entityManager.find(Proyecto.class, temaProyecto.getProyecto().getIdProyecto());
      proyecto.setFechaActualizacion(new Date());
      //SOLO SE ACTUALIZA EL ESTADO DEL PROYECTO SI ESTE ES UNA PROPUESTA DE CONVOCATORIA
      if (proyecto.getCodigoProyecto() == null || proyecto.getCodigoProyecto().trim().length() == 0) {

        proyecto.setEstado(new Constantes(idEstadoConvocatoria));

      }
      entityManager.merge(proyecto);

      return temaProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idTema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<TemaProyecto> getListaTemaProyectoPorTema(Long idTema) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("TemaProyecto.findAllPorTema")
              .setParameter("idTema", idTema)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idTema
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TemaProyecto getTemaProyectoPorTemaYproyecto(Long idTema, Long idProyecto) throws JpaDinaeException {

    try {

      return (TemaProyecto) entityManager.createNamedQuery("TemaProyecto.findTemaProyectoPorTemaYproyecto")
              .setParameter("idTema", idTema)
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

    } catch (NoResultException nre) {

      //NO EXISTE TEMA PARA ESTE PROYECTO
      return null;
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
  public List<Long> getIDTemaProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("TemaProyecto.findIDTemaProyectoPorProyecto")
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
  public int cuentaTemaProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      Object cuenta = entityManager.createNamedQuery("TemaProyecto.CuentaTemaProyectoPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

      if (cuenta == null) {
        return 0;
      }

      return ((Number) cuenta).intValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param tipoInformacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Long> getIDTemaProyectoPorInformeAvanceImplementacion(Long idInformeAvanceImplementacion, String tipoInformacion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("TemaProyecto.findIDTemaProyectoPorInformeAvanceImplementacion")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .setParameter("tipoTabInformacionImpl", tipoInformacion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  @Override
  public TemaProyecto getTemaProyectoPorTemaEinformeAvanceImplementacion(Long idTema, Long idInformeAvanceImplementacion) throws JpaDinaeException {

    try {

      return (TemaProyecto) entityManager.createNamedQuery("TemaProyecto.findTemaProyectoPorTemaEinformeAvanceImplementacion")
              .setParameter("idTema", idTema)
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .getSingleResult();

    } catch (NoResultException nre) {

      //NO EXISTE TEMA PARA ESTE INFORME AVANCE IMPL
      return null;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   * }
   *
   * @param idInformeAvance
   * @param tipoTabInformacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int cuentaTemaProyectoPorInformeAvanceYtipoTemaTabInformacion(Long idInformeAvance, String tipoTabInformacion) throws JpaDinaeException {

    try {

      Object cuenta = entityManager.createNamedQuery("TemaProyecto.CuentaTemaProyectoPorInformeAvanceYtipoTemaTabInformacion")
              .setParameter("idInformeAvanceImplementacion", idInformeAvance)
              .setParameter("tipoTabInformacionImpl", tipoTabInformacion)
              .getSingleResult();

      if (cuenta == null) {
        return 0;
      }

      return ((Number) cuenta).intValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<TemaProyecto> getListaTemaProyectoPorInformeAvanceImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("TemaProyecto.findTemaProyectoPorInformeAvanceImplementacion")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }
}
