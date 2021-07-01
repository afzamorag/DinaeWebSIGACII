package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/02
 */
@Entity
@Table(name = "LUGAR_GEOGRAFICO")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = LugarGeografico.FIND_ALL_PAISES, query = "SELECT l FROM LugarGeografico l WHERE l.descDepartamento IN ('AFRICA', 'AMERICA', 'ASIA', 'EUROPA', 'ASIA') AND l.tipo = 'PA' ORDER BY l.descMunicipio"),
    @NamedQuery(name = LugarGeografico.FIND_ALL_MUNICIPIOS, query = "SELECT l FROM LugarGeografico l WHERE l.tipo = 'CM' ORDER BY l.descMunicipio"),
    @NamedQuery(name = LugarGeografico.FIND_DEPARTAMENTOS_BY_CODIGO_DESC, query = "SELECT l FROM LugarGeografico l WHERE l.descDepartamento = :descDepartamento AND l.codDepartamento = :codDepartamento ORDER BY l.descMunicipio"),
    @NamedQuery(name = LugarGeografico.FIND_DEPARTAMENTOS_BY_CODIGO_PAIS, query = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.LugarGeograficoDTO (l.codDepartamento, l.descDepartamento) FROM LugarGeografico l WHERE l.codPais = :codPais ORDER BY l.descDepartamento"),
    @NamedQuery(name = LugarGeografico.FIND_MUNICIPIOS_BY_CODIGO_DEPARTAMENTO, query = "SELECT l FROM LugarGeografico l WHERE l.codDepartamento = :codDepartamento ORDER BY l.descMunicipio"),
    @NamedQuery(name = LugarGeografico.FIND_MUNICIPIO_BY_CODIGO, query = "SELECT l FROM LugarGeografico l WHERE l.codMunicipio = :codMunicipio")
})
public class LugarGeografico implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL_PAISES = "LugarGeografico.findAllPaises";
    public static final String FIND_ALL_MUNICIPIOS = "LugarGeografico.findAllMuicipios";
    public static final String FIND_DEPARTAMENTOS_BY_CODIGO_DESC = "LugarGeografico.findAllDepartamentosByCodigoAndDesc";
    public static final String FIND_DEPARTAMENTOS_BY_CODIGO_PAIS = "LugarGeografico.findAllDepartamentosByCodigoPais";
    public static final String FIND_MUNICIPIOS_BY_CODIGO_DEPARTAMENTO = "LugarGeografico.finAllMunicipiosByCodDepartamento";
    public static final String FIND_MUNICIPIO_BY_CODIGO = "LugarGeografico.findMunicipioByCodMunicipio";
    @Column(name = "COD_PAIS")
    private Long codPais;
    @Column(name = "DESC_PAIS")
    private String descPais;
    @Column(name = "COD_DEPTO")
    private Long codDepartamento;
    @Column(name = "DESC_DEPTO", nullable = true)
    private String descDepartamento;
    @Id
    @Column(name = "COD_MUNI")
    private Long codMunicipio;
    @Column(name = "DESC_MUNI", nullable = true)
    private String descMunicipio;
    @Column(name = "TIPO")
    private String tipo;

    public LugarGeografico() {
    }

    public LugarGeografico(Long codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public Long getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(Long codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getDescDepartamento() {
        return descDepartamento;
    }

    public void setDescDepartamento(String descDepartamento) {
        this.descDepartamento = descDepartamento;
    }

    public Long getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(Long codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public String getDescMunicipio() {
        return descMunicipio;
    }

    public void setDescMunicipio(String descMunicipio) {
        this.descMunicipio = descMunicipio;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getCodPais() {
        return codPais;
    }

    public void setCodPais(Long codPais) {
        this.codPais = codPais;
    }

    public String getDescPais() {
        return descPais;
    }

    public void setDescPais(String descPais) {
        this.descPais = descPais;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codMunicipio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LugarGeografico other = (LugarGeografico) obj;
        if (!Objects.equals(this.codMunicipio, other.codMunicipio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LugarGeografico{" + "codDepartamento=" + codDepartamento + ", descDepartamento=" + descDepartamento + ", codMunicipio=" + codMunicipio + ", descMunicipio=" + descMunicipio + '}';
    }

}
