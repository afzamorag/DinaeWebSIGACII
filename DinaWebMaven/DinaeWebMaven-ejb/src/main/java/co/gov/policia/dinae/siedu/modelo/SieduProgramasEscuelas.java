/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author andres.zamorag
 */
@Entity
@Table(name = "SIEDU_PROGRAMAS_ESCUELAS")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = SieduProgramasEscuelas.FIND_ALL, query = "SELECT a FROM SieduProgramasEscuelas a"),
    @NamedQuery(name = SieduProgramasEscuelas.FIND_BY_ESCUELA, query = "SELECT a FROM SieduProgramasEscuelas a WHERE a.codEscuela = :codEscuela"),})
public class SieduProgramasEscuelas implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_ESCUELA = "SieduProgramasEscuelas.escuela";
    public static final String FIND_ALL = "SieduProgramasEscuelas.findAll";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_PROGRAMA")
    private Long codPrograma;
    @Column(name = "PROGRAMA")
    private String programa;
    @Column(name = "COD_ESCUELA")
    private Long codEscuela;

    public SieduProgramasEscuelas() {
    }

    public Long getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(Long codPrograma) {
        this.codPrograma = codPrograma;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Long getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(Long codEscuela) {
        this.codEscuela = codEscuela;
    }

}
