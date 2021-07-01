package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.SemilleroInvestigacionDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ISemilleroInvestigacionLocal;
import co.gov.policia.dinae.modelo.SemilleroInvestigacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class SemilleroInvestigacionEjbBean implements ISemilleroInvestigacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idSemilleroInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public SemilleroInvestigacion obtenerSemilleroInvestigacionPorId(Long idSemilleroInvestigacion) throws JpaDinaeException {
    try {

      return entityManager.find(SemilleroInvestigacion.class, idSemilleroInvestigacion);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param semilleroInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public SemilleroInvestigacion guardarSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) throws JpaDinaeException {
    try {

      if (semilleroInvestigacion.getIdSemillero() == null) {

        semilleroInvestigacion.setFechaRegistro(new Date());
        entityManager.persist(semilleroInvestigacion);

      } else {
        semilleroInvestigacion.setFechaActualizacion(new Date());
        entityManager.merge(semilleroInvestigacion);

      }

      return semilleroInvestigacion;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<SemilleroInvestigacion> getListaSemilleroInvestigacionTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroInvestigacion.findAll")
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
  public List<SemilleroInvestigacion> getListaSemilleroInvestigacionPorUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroInvestigacion.findAllPorUnidadPolicial")
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
  public List<SemilleroInvestigacionDTO> getListaSemilleroInvestigacionDTOPorUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("SemilleroInvestigacionDTO.findAllPorUnidadPolicial")
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param criterios
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<SemilleroInvestigacionDTO> buscarSemilleroCriterios(Map<String, Object> criterios) throws JpaDinaeException {
    try {

      CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
      CriteriaQuery<SemilleroInvestigacionDTO> queryDTO = criteria.createQuery(SemilleroInvestigacionDTO.class);
      Root<SemilleroInvestigacion> tablaOrigen = queryDTO.from(SemilleroInvestigacion.class);

      CriteriaQuery<SemilleroInvestigacionDTO> select = queryDTO.select(criteria.construct(SemilleroInvestigacionDTO.class,
              tablaOrigen.get("idSemillero"),
              tablaOrigen.get("nombre"),
              tablaOrigen.get("unidadPolicial").get("idUnidadPolicial"),
              tablaOrigen.get("unidadPolicial").get("nombre"),
              tablaOrigen.get("fechaRegistro"),
              tablaOrigen.get("fechaInicio"),
              tablaOrigen.get("fechaFin"),
              tablaOrigen.get("trabajoIndependiente"),
              tablaOrigen.get("temaTituloTrabajo"),
              tablaOrigen.get("unidadPolicial").get("siglaFisica")));

      if (!criterios.keySet().isEmpty()) {

        List<Predicate> partesWhere = new ArrayList<Predicate>();

        if (criterios.containsKey(IConstantes.CRITERIO_SEMILLERO_UNIDAD_POLICIAL)) {

          Long idUnidad = (Long) criterios.get(IConstantes.CRITERIO_SEMILLERO_UNIDAD_POLICIAL);
          partesWhere.add(criteria.equal(tablaOrigen.get("unidadPolicial").get("idUnidadPolicial"), idUnidad));

        }

        if (criterios.containsKey(IConstantes.CRITERIO_SEMILLERO_PALABRA_CLAVE)) {

          String nombre = (String) criterios.get(IConstantes.CRITERIO_SEMILLERO_PALABRA_CLAVE);
          Expression<String> literal = criteria.upper(criteria.literal("%" + nombre + "%"));
          Expression<String> campo = tablaOrigen.get("nombre");
          partesWhere.add(criteria.like(criteria.upper(campo), literal));
        }

        if (criterios.containsKey(IConstantes.CRITERIO_SEMILLERO_ESTADO_SEMILLERO)) {

          boolean activo = ("A".equals((String) criterios.get(IConstantes.CRITERIO_SEMILLERO_ESTADO_SEMILLERO)));

          Date fechaActual = new Date();
          Predicate sentence;

          if (activo) {

            Expression<Date> fechaInicio = tablaOrigen.get("fechaInicio");
            Expression<Date> fechaFin = tablaOrigen.get("fechaFin");
            Expression<Date> literal = criteria.literal(fechaActual);

            sentence = criteria.between(literal, fechaInicio, fechaFin);
          } else {

            Expression<Date> fechaFin = tablaOrigen.get("fechaFin");
            Expression<Date> literal = criteria.literal(fechaActual);

            sentence = criteria.greaterThanOrEqualTo(literal, fechaFin);
          }

          partesWhere.add(sentence);
        }

        if (criterios.containsKey(IConstantes.CRITERIO_SEMILLERO_ANIO_CREACION)) {

          Expression<String> literal = criteria.literal("YYYY");
          Expression<Integer> anioField = criteria.function("to_char", Integer.class, tablaOrigen.get("fechaRegistro"), literal);

          Expression<Integer> anioCreacion = criteria.literal((Integer) criterios.get(IConstantes.CRITERIO_SEMILLERO_ANIO_CREACION));

          partesWhere.add(criteria.equal(anioField, anioCreacion));
        }

        if (criterios.containsKey(IConstantes.CRITERIO_SEMILLERO_TRABAJO_INDEPENDIENTE)) {

          Expression<String> parametroTrabajo = criteria.literal((String) criterios.get(IConstantes.CRITERIO_SEMILLERO_TRABAJO_INDEPENDIENTE));
          partesWhere.add(criteria.equal(tablaOrigen.get("trabajoIndependiente"), parametroTrabajo));

        }

        select.where(partesWhere.toArray(new Predicate[partesWhere.size()]));
      }

      return entityManager.createQuery(select)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<SemilleroInvestigacionDTO> findAllDTO() throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("SemilleroInvestigacion.findAllDTO")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idSemillero
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public SemilleroInvestigacionDTO findByIdSemillero(Long idSemillero) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("SemilleroInvestigacionDTO.findByIdSemillero", SemilleroInvestigacionDTO.class)
              .setParameter("idSemillero", idSemillero)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (SemilleroInvestigacionDTO) results.get(0);
      }

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }
}
