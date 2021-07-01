package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.UnidadDependenciaDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.modelo.UnidadDependencia;
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
public class UnidadDepedenciaEjbBean implements IUnidadDependenciaLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;

    /**
     *
     * @param idUnidadPolicial
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<UnidadDependenciaDTO> getUnidadDependenciaVigenteByTipo(List<Long> listaTipoUnidad) throws JpaDinaeException {

        try {
            String tipos = "";
            for (Long t : listaTipoUnidad) {
                tipos += t + ",";
            }
            if (tipos.length() > 0) {
                tipos = tipos.substring(0, tipos.length() - 1);
            }

            return entityManager.createQuery("SELECT new co.gov.policia.dinae.dto.UnidadDependenciaDTO(u.consecutivo, u.descripcionDependencia, u.tipoUnidadCodigo) FROM UnidadDependencia u WHERE u.vigente='SI' and u.tipoUnidadCodigo IN (" + tipos + ") ORDER BY u.descripcionDependencia ASC")
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public List<UnidadDependencia> findUnidadDependenciaVigenteByTipo(List<Long> listaTipoUnidad) throws JpaDinaeException {
        try {

            String tipos = "";
            for (Long t : listaTipoUnidad) {
                tipos += t + ",";
            }
            if (tipos.length() > 0) {
                tipos = tipos.substring(0, tipos.length() - 1);
            }
            return entityManager.createQuery("SELECT u FROM UnidadDependencia u WHERE u.vigente='SI' and u.tipoUnidadCodigo IN (" + tipos + ") ORDER BY u.descripcionDependencia ASC")
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    @Override
    public List<UnidadDependencia> getUnidadDependenciaVigenteByEscuela() throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("UnidadDependencia.findEscuelaVigente")
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }
    
    @Override
    public UnidadDependencia getUnidadDependenciaById(Long consecutivo) throws JpaDinaeException {
        UnidadDependencia unidad = new UnidadDependencia();
        try {
            unidad = (UnidadDependencia) entityManager.createNamedQuery(UnidadDependencia.FIND_BY_CONSECUTIVO)
                    .setParameter("consecutivo", consecutivo)
                    .setHint("eclipselink.refresh", "true")
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
        return unidad;
    }

}
