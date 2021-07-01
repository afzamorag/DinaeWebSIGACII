package co.gov.policia.dinae.impl;
import co.gov.policia.dinae.dto.NivelAcademicoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.INivelAcademicoLocal;
import co.gov.policia.dinae.modelo.NivelAcademico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class NivelAcademicoEjbBean implements INivelAcademicoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<NivelAcademicoDTO> getNivelAcademicoVigenteByTipo() throws JpaDinaeException {

    try {

      return entityManager.createQuery("SELECT new co.gov.policia.dinae.dto.NivelAcademicoDTO(u.consecutivo, u.descripcion) FROM NivelAcademico u ORDER BY u.descripcion ASC")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }
    
@Override
  
  public NivelAcademico getNivelAcademicoByID(Long id) throws JpaDinaeException {
      

    try {
      
      NivelAcademico nivelAcademico = (NivelAcademico) entityManager.createNamedQuery("nivelAcademicofindByID")
              .setParameter("idNivelAcademico", id)
              .setHint("eclipselink.refresh", "true")
              .setMaxResults(1)
              .getSingleResult();
      return nivelAcademico;
           
              

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }
}
