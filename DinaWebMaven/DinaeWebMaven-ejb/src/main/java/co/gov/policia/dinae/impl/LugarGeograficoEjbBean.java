package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.LugarGeograficoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.modelo.LugarGeografico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/02
 */
@Stateless
public class LugarGeograficoEjbBean implements ILugarGeograficoLocal, Serializable {

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager entityManager;

    @Override
    public List<LugarGeografico> getListaLugares(Long codDepartamento, String descDepartamento)
            throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("LugarGeografico.findAllDepartamentosByCodigoAndDesc").
                    setParameter("descDepartamento", descDepartamento).
                    setParameter("codDepartamento", codDepartamento).getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public List<LugarGeograficoDTO> getListaDepartamentos(Long codPais) throws JpaDinaeException {
        List<LugarGeograficoDTO> lista;
        try {
            lista = entityManager.createNamedQuery("LugarGeografico.findAllDepartamentosByCodigoPais")
                    .setParameter("codPais", codPais)
                    .getResultList();

        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public List<LugarGeografico> getListaLugaresByCodDepartamento(Long codDepartamento)
            throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("LugarGeografico.finAllMunicipiosByCodDepartamento").
                    setParameter("codDepartamento", codDepartamento).getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @return @throws JpaDinaeException
     */
    @Override
    public List<LugarGeografico> getListaPaises() throws JpaDinaeException {
        try {
            return entityManager.createNamedQuery("LugarGeografico.findAllPaises").getResultList();
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
        }
    }

    /**
     *
     * @param codMunicipio
     * @return
     * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
     */
    @Override
    public LugarGeografico findMunicipioByCodMunicipio(Long codMunicipio) throws JpaDinaeException {
        try {
            LugarGeografico lugarGeografico = null;

            List results = entityManager.createNamedQuery("LugarGeografico.findMunicipioByCodMunicipio", LugarGeografico.class)
                    .setParameter("codMunicipio", codMunicipio)
                    .getResultList();
            if (results != null && !results.isEmpty()) {
                lugarGeografico = (LugarGeografico) results.get(0);
            }

            return lugarGeografico;
        } catch (Exception ex) {
            throw new JpaDinaeException(ex.getMessage());
        }
    }

    @Override
    public List<LugarGeografico> findLugarGeoggraficoByTipo(List<String> tiposMun) throws JpaDinaeException {
        try {

            String tipos = "";
            for (String t : tiposMun) {
                tipos += "'" + t + "'" + ",";
            }
            if (tipos.length() > 0) {
                tipos = tipos.substring(0, tipos.length() - 1);
            }
            return entityManager.createQuery("SELECT l FROM LugarGeografico l WHERE l.tipo IN (" + tipos + ") ORDER BY l.descMunicipio ASC")
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();
        } catch (Exception e) {
            throw new JpaDinaeException(e.getMessage(), e);
        }

    }
}
