/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "HORAS_DOCENTE_CAPACITACION_VW")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = HorasDocenteCapacitacion.FIND_ALL, query = "SELECT s FROM HorasDocenteCapacitacion s")})
@XmlRootElement

public class HorasDocenteCapacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "HorasDocenteCapacitacion.all";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "COD_ESCUELA", nullable = false)
    private Long codEscuela;
    @Column(name = "ESCUELA", nullable = false)
    private String escuela;
    @Column(name = "ID_PERSONA", nullable = false)
    private Long idPersona;
    @Column(name = "IDENTIFICACION", nullable = false)
    private String identificacion;
    @Column(name = "DOCENTE", nullable = false)
    private String docente;
    @Column(name = "PROGRAMA", nullable = false)
    private String programa;
    @Column(name = "TEMA", nullable = false)
    private String tema;
    @Column(name = "HORA", nullable = false)
    private Integer hora;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "FECHA_REGISTRO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    
    public HorasDocenteCapacitacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(Long codEscuela) {
        this.codEscuela = codEscuela;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
