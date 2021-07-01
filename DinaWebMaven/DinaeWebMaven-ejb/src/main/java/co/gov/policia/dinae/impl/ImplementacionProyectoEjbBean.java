package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.ImplementacionesProyectoDTO;
import co.gov.policia.dinae.dto.ImplentacionProyectoCompromisosDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/21
 */
@Stateless
public class ImplementacionProyectoEjbBean implements IImplementacionProyectoLocal, Serializable {

  /**
   * EntityManager
   */
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idImplementacionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ImplementacionesProyecto getImplementacionProyecto(Long idImplementacionesProyecto) throws JpaDinaeException {

    try {

      return entityManager.find(ImplementacionesProyecto.class, idImplementacionesProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }

  /**
   *
   * @param implementacionProy
   * @param compromisoPlanTrabajo
   * @param compromisoInformeAvance
   * @param compromisoInformeFinal
   * @throws JpaDinaeException
   */
  @Override
  public void habilitarImplementacionProyecto(ImplementacionesProyecto implementacionProy,
          CompromisoImplementacion compromisoPlanTrabajo, CompromisoImplementacion compromisoInformeAvance,
          CompromisoImplementacion compromisoInformeFinal) throws JpaDinaeException {
    try {
      entityManager.persist(implementacionProy);
      compromisoPlanTrabajo.setImplementacionesProyecto(implementacionProy);
      entityManager.persist(compromisoPlanTrabajo);
      compromisoInformeAvance.setImplementacionesProyecto(implementacionProy);
      entityManager.persist(compromisoInformeAvance);
      compromisoInformeFinal.setImplementacionesProyecto(implementacionProy);
      entityManager.persist(compromisoInformeFinal);

      //ACTUALIZAMOS EL ESTADO DEL PROYECTO
      entityManager.createNamedQuery("Proyecto.UpdateEstadoProyectoAEnImplementacion")
              .setParameter("estadoImplementacionPro", new Constantes(IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION)) //NUEVO ESTADO DEL PROYECTO
              .setParameter("idEstadoPermiteActualizacion", IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO)//ESTADO EN QUE DEBE ESTAR EL PROYECTO PARA QUE APLICA LA ACTUALIZACION
              .setParameter("idProyecto", implementacionProy.getProyecto().getIdProyecto())//PROYETCO A ACTUALIZAR
              .executeUpdate();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param listaIdEstadoImpl
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ImplentacionProyectoCompromisosDTO> findAllImplementacionesVigentes(List<Long> listaIdEstadoImpl, Long idUnidadPolicial) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ImplentacionProyectoCompromisosDTO.findAllImplementacionesVigentes").
              setParameter("listaIdEstadoImpl", listaIdEstadoImpl)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getResultList();

    } catch (Exception ex) {

      throw new JpaDinaeException(ex);
    }
  }

  /**
   *
   * @param idProyecto
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ImplementacionesProyecto getImplementacionProyectoByProyectoAndUnidadPolicial(Long idProyecto,
          Long idUnidadPolicial) throws JpaDinaeException {
    try {
      return (ImplementacionesProyecto) entityManager.
              createNamedQuery("ImplementacionesProyecto.findByIdProyectoAndIdUnidadPolicial").
              setParameter("idProyecto", idProyecto).setParameter("idUnidadPolicial", idUnidadPolicial).
              getSingleResult();
    } catch (NoResultException nre) {
      Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, nre.getLocalizedMessage());
      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ImplementacionesProyectoDTO> getImplementacionProyectoByProyecto(Long idProyecto) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("ImplementacionesProyectoDTO.findByIdProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex);
    }

  }
}
