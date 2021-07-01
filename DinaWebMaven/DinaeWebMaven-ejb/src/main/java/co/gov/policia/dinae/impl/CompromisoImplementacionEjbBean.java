package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CompromisoImplementacionDTO;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class CompromisoImplementacionEjbBean implements ICompromisoImplementacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @EJB
  private IConstantesLocal iConstantesLocal;

  @Override
  public void saveCompromisoImplementacion(CompromisoImplementacion compromiso) throws JpaDinaeException {
    try {
      if (compromiso.getIdCompromisoImplementacion() != null) {

        compromiso.setFechaCreacion(new Date());
        entityManager.merge(compromiso);

      } else {

        entityManager.persist(compromiso);

      }
    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param idCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Long obtenerIdTipoCompromisoImplementacionPorId(Long idCompromisoImplementacion) throws JpaDinaeException {

    try {

      return (Long) entityManager.createNamedQuery("CompromisoImplementacion.findIdTipoCompromisoImplementacionPorId")
              .setParameter("idCompromisoImplementacion", idCompromisoImplementacion)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }

  }

  /**
   *
   * @param idCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CompromisoImplementacion obtenerCompromisoImplementacionPorId(Long idCompromisoImplementacion) throws JpaDinaeException {

    try {

      return entityManager.find(CompromisoImplementacion.class, idCompromisoImplementacion);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CompromisoImplementacion> findByIdImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("CompromisoImplementacion.findByIdImplementacionProy")
              .setParameter("idImplementacionProy", idImplementacionProy)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<CompromisoImplementacionDTO> getListaCompromisoImplementacionDTOPorIdImplementacionProyecto(Long idImplementacionProy) throws JpaDinaeException {
    try {

      List<Long> idListaEstado = new ArrayList<Long>();
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_PENDIENTE);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ACEPTADO);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_NO_ACEPTADO);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_ENVIADO_A_VICIN);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO);
      idListaEstado.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_REVISADO);

      List<CompromisoImplementacion> listaCompromisoImplementacion = entityManager.createNamedQuery("CompromisoImplementacion.findByIdImplementacionProyCorreccionyListaEstado")
              .setParameter("idImplementacionProy", idImplementacionProy)
              .setParameter("idListaEstado", idListaEstado)
              .getResultList();

      List<CompromisoImplementacionDTO> listaCompromisoImplementacionDTO = new ArrayList<CompromisoImplementacionDTO>(listaCompromisoImplementacion.size());

      for (CompromisoImplementacion unCompromisoImplementacion : listaCompromisoImplementacion) {

        CompromisoImplementacionDTO compromisoImplementacionDTO = new CompromisoImplementacionDTO();
        compromisoImplementacionDTO.setIdCompromisoImplementacion(unCompromisoImplementacion.getIdCompromisoImplementacion());

        ConstantesDTO constantesEstado = iConstantesLocal.getConstantesDTOPorIdConstante(unCompromisoImplementacion.getIdEstadoCompromisoImpl().getIdConstantes());
        compromisoImplementacionDTO.setIdEstadoCompromisoImpl(constantesEstado.getIdConstantes());
        compromisoImplementacionDTO.setDescripcionEstadoCompromisoImpl(constantesEstado.getValor());

        ConstantesDTO constantesTipoCompromiso = iConstantesLocal.getConstantesDTOPorIdConstante(unCompromisoImplementacion.getIdTipoCompromiso().getIdConstantes());
        compromisoImplementacionDTO.setIdTipoCompromiso(constantesTipoCompromiso.getIdConstantes());

        if (unCompromisoImplementacion.getCompromisoImplementacionPadre() == null) {

          compromisoImplementacionDTO.setDescripcionTipoCompromiso(constantesTipoCompromiso.getValor());
          compromisoImplementacionDTO.setFecha(unCompromisoImplementacion.getFechaCompromiso());

        } else {

          compromisoImplementacionDTO.setDescripcionTipoCompromiso(unCompromisoImplementacion.getNombreCompromisoCorrecion());
          compromisoImplementacionDTO.setFecha(unCompromisoImplementacion.getFechaNuevoCompromiso());
          compromisoImplementacionDTO.setIdCompromisoPadre(unCompromisoImplementacion.getCompromisoImplementacionPadre().getIdCompromisoImplementacion());
        }

        listaCompromisoImplementacionDTO.add(compromisoImplementacionDTO);
      }

      return listaCompromisoImplementacionDTO;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param idImplementacionProyecto
   * @param idTipoCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CompromisoImplementacion obtenerCompromisoImplementacionPorImplementacionProyectoYtipoCompromiso(Long idImplementacionProyecto, Long idTipoCompromisoImplementacion) throws JpaDinaeException {

    try {

      return (CompromisoImplementacion) entityManager.createNamedQuery("CompromisoImplementacion.findByIdImplementacionProyYtipoCompromiso")
              .setParameter("idImplementacionProyecto", idImplementacionProyecto)
              .setParameter("idTipoCompromisoImplementacion", idTipoCompromisoImplementacion)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }

  }

  /**
   *
   * @param idImplementacionProyecto
   * @param idTipoCompromisoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CompromisoImplementacion findByIdImplementacionProyYtipoCompromisoNoCorregido(Long idImplementacionProyecto, Long idTipoCompromisoImplementacion) throws JpaDinaeException {
    try {

      return (CompromisoImplementacion) entityManager.createNamedQuery("CompromisoImplementacion.findByIdImplementacionProyYtipoCompromisoNoCorregido")
              .setParameter("idImplementacionProyecto", idImplementacionProyecto)
              .setParameter("idTipoCompromisoImplementacion", idTipoCompromisoImplementacion)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Long getEstadoCompromisoImplPorIdInformeAvance(Long idInformeAvanceImplementacion) throws JpaDinaeException {
    try {

      return (Long) entityManager.createNamedQuery("InformeAvanceImplementacion.findIdEstadoCompromisoImplementacionPorIdInforme")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .getSingleResult();

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }
}
