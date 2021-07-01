package co.gov.policia.dinae.impl;

import static co.gov.policia.dinae.constantes.IConstantes.ESTADO_VINCULACION_INVESTIGADOR_VINCULADO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_CARACTERISTICAS_INVESTIGADOR_ID_PUBLICACIONES;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_CARACTERISTICAS_INVESTIGADOR_ID_INVESTIGACIONES;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_EN_EJECUCION;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_CULMINADO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_PROYECTO_IMPLEMENTADO;
import co.gov.policia.dinae.dto.InvestigadorDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FormacionInvestigador;
import co.gov.policia.dinae.modelo.InvestigacionDesarrollada;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.PublicacionInvestigador;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/10
 */
@Stateless
public class InvestigadorEjbBean implements IInvestigadorLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param investigador
   * @param formaciones
   * @throws JpaDinaeException
   */
  @Override
  public Investigador insertDatosInvestigador(Investigador investigador, List<FormacionInvestigador> formaciones) throws JpaDinaeException {
    try {
      if (investigador != null) {

        if (investigador.getIdInvestigador() == null) {

          entityManager.persist(investigador);

        } else {

          entityManager.merge(investigador);

        }

      } else if (formaciones != null && !formaciones.isEmpty()) {

        for (FormacionInvestigador formacionInvestigador : formaciones) {

          if (formacionInvestigador.getIdFormacionInv() == null) {

            entityManager.persist(formacionInvestigador);

          } else {

            //NO SE HACE UPDATE POR QUE LAS FORMACIONES NO SE ACTUALIZAN
          }

        }
      }

      return investigador;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param numIdInvestigador
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<FormacionInvestigador> getFormacionInvestigadorByNumIdentificacion(String numIdInvestigador)
          throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("FormacionInvestigador.findAllByIdentificacionInvestigador").
              setParameter("identificacion", numIdInvestigador).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Investigador getInvestigadorPorNumeroIdentificacion(String numIdentificacion) throws JpaDinaeException {
    try {
      return (Investigador) entityManager.createNamedQuery("Investigador.findByNumeroIdentificacion").
              setParameter("numeroIdentificacion", numIdentificacion)
              .getSingleResult();
    } catch (NoResultException nre) {
      return null;
    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigacionDesarrollada> getInvestigacionesDesarrolladasByNumIdentificacion(String numIdentificacion) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("InvestigacionDesarrollada.findAllByIdentificacionInvestigador").
              setParameter("identificacion", numIdentificacion).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param esInvestigador
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigacionDesarrollada> getInvestigacionesDesarrolladasByNumIdentificacion(boolean esInvestigador, String numIdentificacion) throws JpaDinaeException {
    try {
      if (esInvestigador) {

        return entityManager.createNamedQuery("InvestigacionDesarrollada.findAllByIdentificacionInvestigador")
                .setParameter("identificacion", numIdentificacion)
                .getResultList();
      } else {
        return entityManager.createNamedQuery("InvestigacionDesarrollada.findAllByIdentificacionSiat")
                .setParameter("identificacionSiat", numIdentificacion)
                .getResultList();
      }

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param investigacion
   * @throws JpaDinaeException
   */
  @Override
  public void guardarInvestigacionDesarrollada(InvestigacionDesarrollada investigacion) throws JpaDinaeException {
    try {
      if (investigacion.getIdInvestigacionDesarrollada() == null) {
        entityManager.persist(investigacion);
      } else {
        entityManager.merge(investigacion);
      }
    } catch (Exception ex) {
      throw new JpaDinaeException(ex);
    }
  }

  /**
   *
   * @param criterioUnidadPolicial
   * @param criterioIdentificacion
   * @param criterioNombres
   * @param criterioApellidos
   * @param criterioEstado
   * @param criterioProfesorPolicial
   * @param criterioNivelesFormacion
   * @param criterioCaracteristicas
   * @param criterioGrado
   * @param criterioVinculacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Investigador> getInvestigadoresByFiltros(Long criterioUnidadPolicial, String criterioIdentificacion, String criterioNombres,
          String criterioApellidos, Long criterioEstado, String criterioProfesorPolicial, List<Long> criterioNivelesFormacion,
          List<Long> criterioCaracteristicas, String criterioGrado, Long criterioVinculacion) throws JpaDinaeException {

    List<InvestigadorProyecto> listaInvestigadoresProyecto = new ArrayList<InvestigadorProyecto>();
    List<Investigador> listaInvestigadores;
    List<Investigador> retorno = new ArrayList<Investigador>();
    boolean comparar = false;

    StringBuilder query = new StringBuilder();
    query.append("SELECT i FROM Investigador i \n");
    StringBuilder queryInvProy = new StringBuilder();

    // SE BUSCA EN LA TABLA INVESTIGADORES_PROYECTO
    if ((criterioEstado != null && criterioEstado > 0)
            || (criterioUnidadPolicial != null && criterioUnidadPolicial > 0)
            || (criterioGrado != null && !criterioGrado.equals(""))) {
      queryInvProy.append("SELECT ip FROM InvestigadorProyecto ip");
      if (criterioUnidadPolicial != null && criterioUnidadPolicial > 0) {
        queryInvProy.append("\nWHERE ip.unidadPolicial.idUnidadPolicial = :idUnidadPolicial");
      }
      if (criterioEstado != null && criterioEstado > 0) {
        if (queryInvProy.toString().contains("WHERE")) {
          queryInvProy.append("\nAND");
        } else {
          queryInvProy.append("\nWHERE");
        }
        queryInvProy.append(" ip.proyecto.estado.idConstantes IN :estados");
      }
      if (criterioGrado != null && !criterioGrado.equals("")) {
        if (queryInvProy.toString().contains("WHERE")) {
          queryInvProy.append("\nAND");
        } else {
          queryInvProy.append("\nWHERE");
        }
        queryInvProy.append(" ip.grado like :grado");
      }
      Query queryIP = entityManager.createQuery(queryInvProy.toString());
      if (criterioUnidadPolicial != null && criterioUnidadPolicial > 0) {
        queryIP.setParameter("idUnidadPolicial", criterioUnidadPolicial);
      }
      List<Long> listaEstados = new ArrayList<Long>();
      if (criterioEstado != null && criterioEstado > 0) {
        if (criterioEstado.equals(ESTADO_VINCULACION_INVESTIGADOR_VINCULADO)) {
          listaEstados.add(TIPO_ESTADO_PROYECTO_EN_EJECUCION);
          listaEstados.add(TIPO_ESTADO_PROYECTO_EN_IMPLEMENTACION);
        } else {
          listaEstados.add(TIPO_ESTADO_PROYECTO_CULMINADO);
          listaEstados.add(TIPO_ESTADO_PROYECTO_EVALUADO);
          listaEstados.add(TIPO_ESTADO_PROYECTO_IMPLEMENTADO);
        }
        queryIP.setParameter("estados", listaEstados);
      }
      if (criterioGrado != null && !criterioGrado.equals("")) {
        queryIP.setParameter("grado", criterioGrado);
      }
      comparar = true;
      listaInvestigadoresProyecto = queryIP.getResultList();
    }

    // SE BUSCA EN LA TABLA INVESTIGADOR    
    if (criterioIdentificacion != null && !criterioIdentificacion.equals("")) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      query.append(" i.numeroIdentificacion LIKE :numeroIdentificacion");
    }
    if (criterioNombres != null && !criterioNombres.equals("")) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      query.append(" upper(i.nombres) LIKE upper(:nombres)");
    }
    if (criterioApellidos != null && !criterioApellidos.equals("")) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      query.append(" upper(i.apellidos) LIKE upper(:apellidos)");
    }
    if (criterioProfesorPolicial != null && !criterioProfesorPolicial.equals("")) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      query.append(" i.profesorPolicial = :profesorPolicial");
    }
    if (criterioNivelesFormacion != null && !criterioNivelesFormacion.isEmpty()) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      query.append(" i.formacionInvestigadorList = :formacionInvestigadorList");
    }
    if (criterioCaracteristicas != null && !criterioCaracteristicas.isEmpty()) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      for (Long caracteristica : criterioCaracteristicas) {
        if (caracteristica.equals(TIPO_CARACTERISTICAS_INVESTIGADOR_ID_PUBLICACIONES)) {

        } else if (caracteristica.equals(TIPO_CARACTERISTICAS_INVESTIGADOR_ID_INVESTIGACIONES)) {
          query.append(" inde.idInvestigacionDesarrollada IS NOT NULL");
        }
      }
    }
    if (criterioVinculacion != null && criterioVinculacion > 0) {
      if (query.toString().contains("WHERE")) {
        query.append(" \nAND");
      } else {
        query.append(" \nWHERE");
      }
      query.append(" i.tipoVinculacion.idConstantes = :tipoVinculacion");
    }

    try {
      Query queryCreado = entityManager.createQuery(query.toString());
      if (criterioIdentificacion != null && !criterioIdentificacion.equals("")) {
        queryCreado.setParameter("numeroIdentificacion", "%" + criterioIdentificacion + "%");
      }
      if (criterioNombres != null && !criterioNombres.equals("")) {
        queryCreado.setParameter("nombres", "%" + criterioNombres + "%");
      }
      if (criterioApellidos != null && !criterioApellidos.equals("")) {
        queryCreado.setParameter("apellidos", "%" + criterioApellidos + "%");
      }

      if (criterioProfesorPolicial != null && !criterioProfesorPolicial.equals("")) {
        Character esProfesorPolicial;
        if (criterioProfesorPolicial.equals("Si")) {
          esProfesorPolicial = 'Y';
        } else {
          esProfesorPolicial = 'N';
        }
        queryCreado.setParameter("profesorPolicial", esProfesorPolicial);
      }
      if (criterioNivelesFormacion != null && !criterioNivelesFormacion.isEmpty()) {
        List<Constantes> formaciones = new ArrayList<Constantes>();
        for (Long idFormacion : criterioNivelesFormacion) {
          formaciones.add(new Constantes(idFormacion));
        }
        queryCreado.setParameter("formacionInvestigadorList", formaciones);
      }
      if (criterioVinculacion != null && criterioVinculacion > 0) {
        queryCreado.setParameter("tipoVinculacion", criterioVinculacion);
      }

      listaInvestigadores = queryCreado.getResultList();
      if (comparar) {
        for (Investigador inv : listaInvestigadores) {
          for (InvestigadorProyecto ip : listaInvestigadoresProyecto) {
            if (inv.getNumeroIdentificacion().equals(ip.getIdentificacion())) {
              inv.setGrado(ip.getGrado());
              retorno.add(inv);
              break;
            }
          }
        }
      } else {
        retorno = listaInvestigadores;
      }
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
    return retorno;
  }

  /**
   *
   * @param criterioUnidadPolicial
   * @param criterioIdentificacion
   * @param criterioNombres
   * @param criterioApellidos
   * @param criterioEstado
   * @param criterioProfesorPolicial
   * @param criterioNivelesFormacion
   * @param criterioCaracteristicas
   * @param criterioGrado
   * @param criterioVinculacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<InvestigadorDTO> getInvestigadoresYPersonalSiatFiltros(Long criterioUnidadPolicial, String criterioIdentificacion, String criterioNombres,
          String criterioApellidos, Long criterioEstado, String criterioProfesorPolicial, List<Long> criterioNivelesFormacion,
          List<Long> criterioCaracteristicas, String criterioGrado, Long criterioVinculacion) throws JpaDinaeException {

    try {

      Long[] arregloCriterioNivelesFormacion = new Long[]{null, null, null, null, null};
      if (criterioNivelesFormacion.size() > 0) {

        arregloCriterioNivelesFormacion[0] = criterioNivelesFormacion.get(0);

      } else if (criterioNivelesFormacion.size() > 1) {

        arregloCriterioNivelesFormacion[1] = criterioNivelesFormacion.get(1);

      } else if (criterioNivelesFormacion.size() > 2) {

        arregloCriterioNivelesFormacion[2] = criterioNivelesFormacion.get(2);

      } else if (criterioNivelesFormacion.size() > 3) {

        arregloCriterioNivelesFormacion[3] = criterioNivelesFormacion.get(3);

      } else if (criterioNivelesFormacion.size() > 4) {

        arregloCriterioNivelesFormacion[4] = criterioNivelesFormacion.get(4);

      }

      Long[] arregloCriterioCaracteristicas = new Long[]{null, null};
      if (criterioCaracteristicas.size() > 0) {

        arregloCriterioCaracteristicas[0] = criterioCaracteristicas.get(0);

      } else if (criterioCaracteristicas.size() > 1) {

        arregloCriterioCaracteristicas[1] = criterioCaracteristicas.get(1);

      }

      String sesion = String.valueOf(System.currentTimeMillis());

      entityManager.createNamedQuery("Investigador.BuscardorPersonal")
              .setParameter("P_SESION", sesion)
              .setParameter("P_ID_UNIDAD_POLICIAL", criterioUnidadPolicial)
              .setParameter("P_IDENTIFICACION", criterioIdentificacion)
              .setParameter("P_NOMBRES", criterioNombres)
              .setParameter("P_APELLIDOS", criterioApellidos)
              .setParameter("P_PROFESOR_POLICIAL", criterioProfesorPolicial)
              .setParameter("P_ID_ESTADO", criterioEstado)
              .setParameter("P_ID_NIVEL_PREGRADO", arregloCriterioNivelesFormacion[0])
              .setParameter("P_ID_NIVEL_POSTGRADO", arregloCriterioNivelesFormacion[1])
              .setParameter("P_ID_NIVEL_ESPECILIZACION", arregloCriterioNivelesFormacion[2])
              .setParameter("P_ID_NIVEL_MAESTRIA", arregloCriterioNivelesFormacion[3])
              .setParameter("P_ID_NIVEL_DOCTORADO", arregloCriterioNivelesFormacion[4])
              .setParameter("P_ID_CARAC_CON_PUBLICACION", arregloCriterioCaracteristicas[0])
              .setParameter("P_ID_CARAC_CON_INVESTIGACION", arregloCriterioCaracteristicas[1])
              .setParameter("P_ID_GRADO", criterioGrado)
              .setParameter("P_ID_VINCULA", criterioVinculacion)
              .executeUpdate();

      return entityManager.createNamedQuery("SalidaBusquedaInvestigadorSiat.findAllPorSesion")
              .setParameter("sesion", sesion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param publicacion
   * @throws JpaDinaeException
   */
  @Override
  public void guardarPublicacionInvestigador(PublicacionInvestigador publicacion) throws JpaDinaeException {
    try {
      if (publicacion.getIdPublicacionInvestigador() != null) {
        entityManager.merge(publicacion);
      } else {
        entityManager.persist(publicacion);
      }
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param numIdentificacionInv
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<PublicacionInvestigador> getPublicacionesByNumIdentificacion(String numIdentificacionInv) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("PublicacionInvestigador.findAllByIdentificacionInvestigador")
              .setParameter("identificacion", numIdentificacionInv)
              .getResultList();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param esInvestiugador
   * @param numIdentificacionInv
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<PublicacionInvestigador> getPublicacionesByNumIdentificacion(boolean esInvestiugador, String numIdentificacionInv) throws JpaDinaeException {
    try {
      if (esInvestiugador) {

        return entityManager.createNamedQuery("PublicacionInvestigador.findAllByIdentificacionInvestigador")
                .setParameter("identificacion", numIdentificacionInv)
                .getResultList();

      } else {
        return entityManager.createNamedQuery("PublicacionInvestigador.findAllByIdentificacionVistaFuncionario")
                .setParameter("identificacionSiat", numIdentificacionInv)
                .getResultList();
      }

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param numIdentificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public VistaFuncionario getInvestigadorSIATHByIdentificacion(String numIdentificacion)
          throws JpaDinaeException {
    try {
      return (VistaFuncionario) entityManager.createNamedQuery("VistaFuncionario.findAllPorIdentificacion").
              setParameter("identificacion", numIdentificacion)
              .getSingleResult();
    } catch (NoResultException nre) {
      Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, nre.getLocalizedMessage());
      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param sesion
   * @param identificacion
   * @param origen
   * @throws JpaDinaeException
   */
  @Override
  public void buscarNivelesAcademicos(String sesion, String identificacion, String origen) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("Investigador.BuscardorEstudiosPersonal")
              .setParameter("P_SESION", sesion)
              .setParameter("P_IDENTIFICACION", identificacion)
              .setParameter("P_ORIGEN", origen)
              .executeUpdate();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }
}
