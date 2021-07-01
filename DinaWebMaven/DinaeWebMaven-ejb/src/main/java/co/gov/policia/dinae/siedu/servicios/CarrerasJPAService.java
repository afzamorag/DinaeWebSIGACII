package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Carreras1;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan Carlos Cifuentes Murcia (juan.cifuentes@correo.policia.gov.co)
 */
@Stateless
public class CarrerasJPAService implements CarrerasService, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;
  private String creadoPor = "USR_EDUC";

  /**
   *
   * @param nivelAcademico
   * @return @throws JpaDinaeException
   */
  @Override

  public List<Carreras1> getCarreraVigenteByNivelAcademico(Long nivelAcademico) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery(Carreras1.FIND_BY_NIVEL_ACADEMICO_AND_CREADO, Carreras1.class)
              .setParameter("nivelAcademico", nivelAcademico)
              .setParameter("creadoPor", creadoPor)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override

  public Carreras1 getCarreraVigenteByidCarrera(Long idCarrera) throws JpaDinaeException {

    try {

      Carreras1 carrera = (Carreras1) entityManager.createNamedQuery(Carreras1.FIND_BY_ID, Carreras1.class)
              .setParameter("idCarrera", idCarrera)
              .setHint("eclipselink.refresh", "true")
              .setMaxResults(1)
              .getSingleResult();
      return carrera;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  public String getCreadoPor() {
    return creadoPor;
  }

  public void setCreadoPor(String creadoPor) {
    this.creadoPor = creadoPor;
  }

}
