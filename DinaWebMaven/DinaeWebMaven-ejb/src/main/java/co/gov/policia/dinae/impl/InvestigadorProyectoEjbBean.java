package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.interfaces.IHorasInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import co.gov.policia.dinae.modelo.HorasInvestigador;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class InvestigadorProyectoEjbBean implements IInvestigadorProyectoLocal, Serializable {

  @Inject
  @GenericSDOQualifier
  private SDO sdo;  
    
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;
  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  @EJB
  private IFuenteProyectoLocal iFuenteProyecto;

  @EJB
  private IHorasInvestigadorLocal iHorasInvestigador;

  /**
   *
   * @param listaInvestigadorProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void guardarHorasInvestigacionLista(List<InvestigadorProyecto> listaInvestigadorProyecto) throws JpaDinaeException {

    try {

      for (InvestigadorProyecto investigadorProyecto : listaInvestigadorProyecto) {

        guardarInvestigadorProyecto(investigadorProyecto);

      }

    } catch (Exception e) {

    }
  }

  /**
   *
   * @param idInvestigadorProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InvestigadorProyecto obtenerInvestigadorProyectoPorId(Long idInvestigadorProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(InvestigadorProyecto.class, idInvestigadorProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param investigadorProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InvestigadorProyecto guardarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) throws JpaDinaeException {
    try {

      if (investigadorProyecto.getIdInvestigadorProyecto() == null) {

        entityManager.persist(investigadorProyecto);

      } else {

        entityManager.merge(investigadorProyecto);

      }

      investigadorProyecto = entityManager.find(InvestigadorProyecto.class, investigadorProyecto.getIdInvestigadorProyecto());

      if (investigadorProyecto.getProyecto() != null && investigadorProyecto.getProyecto().getIdProyecto() != null) {
        //ACTUALIZAMOS LA FECHA DEL ACTUALIZACION DEL PROYECTO
        entityManager.createNamedQuery("Proyecto.UpdateFechaActualiacionProyecto")
                .setParameter("fechaActualizacion", new Date())
                .setParameter("idProyecto", investigadorProyecto.getProyecto().getIdProyecto())
                .executeUpdate();

      }
      return investigadorProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idInvestigadorProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarInvestigadorProyecto(Long idInvestigadorProyecto) throws JpaDinaeException {
    try {
      //LO ELIMINAMOS
      InvestigadorProyecto invest;
      invest = (InvestigadorProyecto) entityManager.createNamedQuery("InvestigadorProyecto.dindById")
              .setParameter("idInvestigadorProyecto", idInvestigadorProyecto)
              .getSingleResult();
      
      entityManager.remove(invest);     
      entityManager.flush();     

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
  public List<InvestigadorProyecto> getListaInvestigadorProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyecto.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @param idTipoVinculacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigadorProyecto> getListaInvestigadorProyectoPorProyectoYtipoVinculacion(Long idProyecto, Long idTipoVinculacion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyecto.findAllPorProyectoYtipoVinculacion")
              .setParameter("idProyecto", idProyecto)
              .setParameter("idTipoVinculacion", idTipoVinculacion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   * @param idProyecto
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigadorProyectoDTO> getListaInvestigadorProyectoDTOPorProyectoYcompromisoProyecto(Long idProyecto, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTO = entityManager.createNamedQuery("InvestigadorProyectoDTO.findAllPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

      //POR CADA INVESTIGADOR CONSULTAMOS LAS HORAS TRABAJADAS EN EL PERIODO
      for (InvestigadorProyectoDTO investigadorProyectoDTO : listaInvestigadorProyectoDTO) {

        //SETEAMOS EL ID DEL PROYECTO AL OBJETO
        investigadorProyectoDTO.setIdProyecto(idProyecto);

        HorasInvestigador horasInvestigador = iHorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto(
                idProyecto,
                idCompromisoProyecto,
                investigadorProyectoDTO.getIdInvestigadorProyecto());

        if (horasInvestigador != null) {
          investigadorProyectoDTO.setHorasInvestigacionTabajadasPeriodo(horasInvestigador.getHorasInvestigacionTrabajadasPeriodo());
        }

        if (investigadorProyectoDTO.getIdFuenteProyecto() != null) {
          FuenteProyecto fuente = iFuenteProyecto.getFuenteFinancieraById(investigadorProyectoDTO.getIdFuenteProyecto());
          investigadorProyectoDTO.setNombreFuenteFinanciera(fuente.getNombreFuente());
        }

        //SE ASIGNA LA UNIDAD POLICIAL A CADA INVESTIGADOR
        String codigoUnidadLabora = iVistaFuncionarioLocal.getCodigoUnidadLaborarVistaFuncionarioPorIdentificacion(investigadorProyectoDTO.getIdentificacion());
        if (codigoUnidadLabora != null) {

          try {

            Long idUnidadPolicial = Long.valueOf(codigoUnidadLabora.trim());
            UnidadPolicial unidadPolicial = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(idUnidadPolicial);
            if (unidadPolicial != null) {

              investigadorProyectoDTO.setUnidadPolicial(unidadPolicial.getNombre());
              investigadorProyectoDTO.setIdUnidadPolicial(unidadPolicial.getIdUnidadPolicial());
              investigadorProyectoDTO.setCodigoUnidadPolicial(unidadPolicial.getSiglaFisica());
            }
          } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error al cargar datos de la UNIDAD_POLICIAL ID = " + codigoUnidadLabora, e);
          }

        }

      }

      return listaInvestigadorProyectoDTO;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param listaInvestigadorProyectoDTO
   * @throws JpaDinaeException
   */
  @Override
  public void guardarHorasInvestigacionPeriodoLista(List<InvestigadorProyectoDTO> listaInvestigadorProyectoDTO, Long idCompromisoProyecto) throws JpaDinaeException {

    try {

      for (InvestigadorProyectoDTO investigadorProyectoDTO : listaInvestigadorProyectoDTO) {

        //CONSULTAMOS SI EXISTE UN OBJETO HORAS INVESTIGADOR
        HorasInvestigador horasInvestigador = iHorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto(
                investigadorProyectoDTO.getIdProyecto(),
                idCompromisoProyecto,
                investigadorProyectoDTO.getIdInvestigadorProyecto());

        if (horasInvestigador == null) {
          //EL INVESTIGADOR NO TIENE HORA ASIGNADA
          //CREAMOS EL OBJETO
          horasInvestigador = new HorasInvestigador();
          horasInvestigador.setProyecto(new Proyecto(investigadorProyectoDTO.getIdProyecto()));
          horasInvestigador.setCompromisoProyecto(new CompromisoProyecto(idCompromisoProyecto));
          horasInvestigador.setInvestigadorProyecto(new InvestigadorProyecto(investigadorProyectoDTO.getIdInvestigadorProyecto()));
        }

        horasInvestigador.setHorasInvestigacionTrabajadasPeriodo(investigadorProyectoDTO.getHorasInvestigacionTabajadasPeriodo());

        if (horasInvestigador.getIdHorasInvestigador() == null) {

          entityManager.persist(horasInvestigador);

        } else {

          entityManager.merge(horasInvestigador);

        }

      }

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
  public int cuentaInvestigadorProyectoPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      Object cuenta = entityManager.createNamedQuery("InvestigadorProyecto.CuentaPorProyecto")
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

  @Override
  public InvestigadorProyecto getInvestigadorProyectoByIdentificacion(String identificacion) throws JpaDinaeException {
    try {
      return (InvestigadorProyecto) entityManager.createNamedQuery("InvestigadorProyecto.findAllByIdentificacion").setParameter("identificacion", identificacion).getSingleResult();
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
  public List<InvestigadorProyecto> getListaInvestigadorProyectoTipoInvestigadorPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyecto.findInvestigadorProyectoTipoInvestigadorPorProyecto")
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
  public List<InvestigadorProyecto> getListaInvestigadorProyectoTipoAsesorPorProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyecto.findInvestigadorProyectoTipoAsesorPorProyecto")
              .setParameter("idProyecto", idProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  @Override
  public List<InvestigadorProyecto> getListaInvestigadorProyectoByIdentificacion(String numIdentificacion) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("InvestigadorProyecto.findAllByIdentificacionAndProyectoActivo").
              setParameter("identificacion", numIdentificacion).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param idPlantaTrabajoImpl
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigadorProyecto> getListaInvestigadorProyectoPorPlanTrabajoImpl(Long idPlantaTrabajoImpl) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyecto.findAllPorPlanTrabajoImpl")
              .setParameter("idPlanTrabajo", idPlantaTrabajoImpl)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws Exception
   */
  @Override
  public Double getSumaCalculoHorasTotalInvestigadorProyecto(Long idProyecto) throws Exception {

    try {

      Object suma = entityManager.createNamedQuery("InvestigadorProyecto.SUMAcalculoHOrasProyecto")
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

      if (suma == null) {
        return 0D;
      }

      return ((Number) suma).doubleValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idEventoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigadorProyecto> getListaInvestigadorProyectoPorEventoInvestigacion(Long idEventoInvestigacion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InvestigadorProyecto.findAllPorEventoInvestigacion")
              .setParameter("idEventoInvestigacion", idEventoInvestigacion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }
}
