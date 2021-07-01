package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.dto.ProyectoVersionDTO;
import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IVersionesLocal;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyectoVersion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.TemaProyectoVersion;
import java.io.Serializable;
import java.util.ArrayList;
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
public class VersionesEjbBean implements IVersionesLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idVersion
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Long getIdProyectoVersion(Long idVersion, Long idProyecto) throws JpaDinaeException {

    try {

      return (Long) entityManager.createNamedQuery("ProyectoVersion.getIdProyectoVersionPorVersionYproyecto")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idVersion", idVersion)
              .getSingleResult();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ProyectoVersionDTO> getListaVersiones(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Versiones.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTOPorIdProyecto(Long idProyectoVersion) throws JpaDinaeException {
    try {

      return new ArrayList<AsesoriaProyectoDTO>();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresProyectoVersion> getListaIndicadoresProyectoPorProyectoEindicadorBase(Long idProyectoVersion, Character indicadorBase, String claveCasoUso) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("IndicadoresProyectoVersion.findProyectoEindicadorBaseYcasoUso")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .setParameter("indicadorBase", indicadorBase)
              .setParameter("casoUso", claveCasoUso)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InstitucionesProyectoDTO> getListaInstitucionesProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InstitucionesProyectoVersionInstitucionesProyectoDTO.findPorProyecto")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(Long idProyectoVersion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroProyectoVersionSemilleroProyectoDTO.findPorProyectoYuniadadPolicial")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyecto(Long idProyectoVersion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroProyectoVersionSemilleroProyectoDTO.findPorProyecto")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("GrupoInvestigacionProyectoVersion.findAllPorProyecto")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Long> getIDTemaProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("TemaProyectoVersion.findIDTemaProyectoPorProyecto")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigadorProyecto> getListaInvestigadorProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyectoVersion.findAllPorProyecto")
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idTema
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TemaProyectoVersion getTemaProyectoPorTemaYproyecto(Long idTema, Long idProyectoVersion) throws JpaDinaeException {

    try {

      return (TemaProyectoVersion) entityManager.createNamedQuery("TemaProyectoVersion.findTemaProyectoPorTemaYproyecto")
              .setParameter("idTema", idTema)
              .setParameter("idProyectoVersion", idProyectoVersion)
              .getSingleResult();

    } catch (NoResultException nre) {

      //NO EXISTE TEMA PARA ESTE PROYECTO
      return null;
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }
}
