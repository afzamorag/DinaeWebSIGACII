package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.InformacionAdicionalPersonaSiatDTO;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.VistaFormacionFuncionario;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class VistaFuncionarioEjbBean implements IVistaFuncionarioLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<String> getListaTodosCorreos() throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("VistaFuncionario.findTodosCorreo")
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<VistaFuncionario> getVistaFuncionarios() throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("VistaFuncionario.findAll")
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     * Retorna un funcionario buscando por identificacion, si no existe retorna
     * null
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public VistaFuncionario getVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException {

        try {

            VistaFuncionario vistaFuncionario = (VistaFuncionario) entityManager.createNamedQuery("VistaFuncionario.findAllPorIdentificacion")
                    .setParameter("identificacion", identificacion.trim())
                    .setHint("eclipselink.refresh", "true")
                    .setMaxResults(1)
                    .getSingleResult();
            return vistaFuncionario;

        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     * Retorna un funcionario buscando por identificacion, si no existe retorna
     * null
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public String getCorreoVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException {

        try {

            return (String) entityManager.createNamedQuery("VistaFuncionario.findCorreoPorIdentificacion")
                    .setParameter("identificacion", identificacion.trim())
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public String getCodigoUnidadLaborarVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException {
        try {
            return (String) entityManager.createNamedQuery("VistaFuncionario.findCodigoUnidadLaborarPorIdentificacion")
                    .setParameter("identificacion", identificacion.trim())
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
    }
    
        /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
        @Override
    public String getSiglaLaborandoVistaFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException{
                try {
            return (String) entityManager.createNamedQuery("VistaFuncionario.findSiglaLaborandoPorIdentificacion")
                    .setParameter("identificacion", identificacion.trim())
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }    
    }

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public List<VistaFormacionFuncionario> getListaVistaFormacionFuncionarioPorIdentificacion(String identificacion) throws JpaDinaeException {

        try {

            return entityManager.createNamedQuery("VistaFormacionFuncionario.findAllByIdentificacion")
                    .setParameter("identificacion", identificacion.trim())
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }

    /**
     *
     * @param identificacion
     * @return
     * @throws JpaDinaeException
     */
    @Override
    public InformacionAdicionalPersonaSiatDTO obtenerInformacionAdicionalPersonaSIAT(String identificacion) throws JpaDinaeException {

        try {

            InformacionAdicionalPersonaSiatDTO informacionAdicionalPersonaSiatDTO = null;

            Object registro = entityManager.createNativeQuery("SELECT PROFESOR FROM INFO_ADICIONAL_PERSONA_SIAT WHERE IDENTIFICACION = ?1")
                    .setParameter(1, identificacion)
                    .getSingleResult();

            if (registro != null) {

                informacionAdicionalPersonaSiatDTO = new InformacionAdicionalPersonaSiatDTO();
                informacionAdicionalPersonaSiatDTO.setIdentificacacion(identificacion);
                informacionAdicionalPersonaSiatDTO.setProfesorPolicial(registro.toString());//PROFESOR

            }

            return informacionAdicionalPersonaSiatDTO;

        } catch (NoResultException nre) {

            return null;

        } catch (NonUniqueResultException nure) {

            return null;

        } catch (Exception e) {

            throw new JpaDinaeException(e);
        }
    }

    /**
     *
     * @param informacionAdicionalPersonaSiatDTO
     * @throws JpaDinaeException
     */
    @Override
    public void actualizarInformacionAdicionalPersonaSIAT(InformacionAdicionalPersonaSiatDTO informacionAdicionalPersonaSiatDTO) throws JpaDinaeException {

        try {

            int numeroRegistrosInsertados = entityManager.createNativeQuery("UPDATE INFO_ADICIONAL_PERSONA_SIAT SET IDENTIFICACION = ? , PROFESOR = ? WHERE IDENTIFICACION = ? ")
                    .setParameter(1, informacionAdicionalPersonaSiatDTO.getIdentificacacion())
                    .setParameter(2, informacionAdicionalPersonaSiatDTO.getProfesorPolicial())
                    .setParameter(3, informacionAdicionalPersonaSiatDTO.getIdentificacacion())
                    .executeUpdate();

            if (numeroRegistrosInsertados == 0) {

                entityManager.createNativeQuery("INSERT INTO INFO_ADICIONAL_PERSONA_SIAT (IDENTIFICACION,PROFESOR) VALUES ( ? , ? )")
                        .setParameter(1, informacionAdicionalPersonaSiatDTO.getIdentificacacion())
                        .setParameter(2, informacionAdicionalPersonaSiatDTO.getProfesorPolicial())
                        .executeUpdate();

            }

        } catch (Exception e) {

            throw new JpaDinaeException(e);
        }
    }
}
