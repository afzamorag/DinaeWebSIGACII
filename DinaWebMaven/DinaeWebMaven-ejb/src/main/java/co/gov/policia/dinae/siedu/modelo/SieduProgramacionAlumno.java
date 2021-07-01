/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "SIEDU_PROGRAMACION_ALUMNO")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = SieduProgramacionAlumno.FIND_ALL, query = "SELECT a FROM SieduProgramacionAlumno a"),
    @NamedQuery(name = SieduProgramacionAlumno.FIND_BY_IDENTIFICACION, query = "SELECT a FROM SieduProgramacionAlumno a WHERE a.identificacion = :identificacion"),
    @NamedQuery(name = SieduProgramacionAlumno.FIND_BY_ID_PROG_DOCENTE, query = "SELECT a FROM SieduProgramacionAlumno a WHERE a.idProgDocente = :idProgDocente")
})
public class SieduProgramacionAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_IDENTIFICACION = "SieduProgramacionAlumno.findByIdentificacion";
    public static final String FIND_ALL = "SieduProgramacionAlumno.findAll";
    public static final String FIND_BY_ID_PROG_DOCENTE = "SieduProgramacionAlumno.findByIdProgDocente";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ALUMNO")
    private BigDecimal idAlumno;
    @Column(name = "ID_PROG_DOCENTE")
    private BigDecimal idProgDocente;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "PARCIAL_1")
    private BigDecimal parcial1;
    @Column(name = "PARCIAL_2")
    private BigDecimal parcial2;
    @Column(name = "PARCIAL_3")
    private BigDecimal parcial3;
    @Column(name = "DEFINITIVA")
    private BigDecimal definitiva;
    @Column(name = "HABILITA")
    private String habilita;

    public SieduProgramacionAlumno() {
    }

    public BigDecimal getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(BigDecimal idAlumno) {
        this.idAlumno = idAlumno;
    }

    public BigDecimal getIdProgDocente() {
        return idProgDocente;
    }

    public void setIdProgDocente(BigDecimal idProgDocente) {
        this.idProgDocente = idProgDocente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public BigDecimal getParcial1() {
        return parcial1;
    }

    public void setParcial1(BigDecimal parcial1) {
        this.parcial1 = parcial1;
    }

    public BigDecimal getParcial2() {
        return parcial2;
    }

    public void setParcial2(BigDecimal parcial2) {
        this.parcial2 = parcial2;
    }

    public BigDecimal getParcial3() {
        return parcial3;
    }

    public void setParcial3(BigDecimal parcial3) {
        this.parcial3 = parcial3;
    }

    public BigDecimal getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(BigDecimal definitiva) {
        this.definitiva = definitiva;
    }

    
    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

}
