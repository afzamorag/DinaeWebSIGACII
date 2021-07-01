/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "V_DATOS_PERSONA")
@NamedQueries({
    @NamedQuery(name = "VDatosPersona.findAll", query = "SELECT s FROM VDatosPersona s"),
    @NamedQuery(name = VDatosPersona.FIND_BY_IDENTIFICACION, query = "SELECT s FROM VDatosPersona s WHERE s.nroId = :identificacion")})
@Cacheable(false)
@XmlRootElement
public class VDatosPersona implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_IDENTIFICACION = "VDatosPersona.findByIdentificacion";

    @Id
    @Column(name = "PERS_PERS", nullable = false)
    private Long persPers;

    @Column(name = "TIPOID", nullable = false)
    private String tipoId;

    @Column(name = "NROID", nullable = false)
    private String nroId;

    @Column(name = "GRADO", nullable = false)
    private String grado;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CORREO", nullable = false)
    private String correo;

    @Column(name = "TELEFONO", nullable = false)
    private Integer telefono;

    @Column(name = "UNIDAD", nullable = false)
    private String unidad;

    @Column(name = "CARGO", nullable = false)
    private String cargo;

    public Long getPersPers() {
        return persPers;
    }

    public void setPersPers(Long persPers) {
        this.persPers = persPers;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNroId() {
        return nroId;
    }

    public void setNroId(String nroId) {
        this.nroId = nroId;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
