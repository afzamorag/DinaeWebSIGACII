/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.validation.constraints.NotNull;
/**
 *
 * @author andres.zamorag
 */
@Entity
@Table(name = "ALUMNOS_ASIGNATURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlumnosAsignatura.findAll", query = "SELECT a FROM AlumnosAsignatura a"),
    @NamedQuery(name = "AlumnosAsignatura.findByIdProgalumno", query = "SELECT a FROM AlumnosAsignatura a WHERE a.idProgalumno = :idProgalumno"),
    @NamedQuery(name = "AlumnosAsignatura.findByIdProgpensum", query = "SELECT a FROM AlumnosAsignatura a WHERE a.idProgpensum = :idProgpensum"),
    @NamedQuery(name = "AlumnosAsignatura.findByIdentificacion", query = "SELECT a FROM AlumnosAsignatura a WHERE a.identificacion = :identificacion"),
    @NamedQuery(name = "AlumnosAsignatura.findByNombres", query = "SELECT a FROM AlumnosAsignatura a WHERE a.nombres = :nombres"),
    @NamedQuery(name = "AlumnosAsignatura.findByApellidos", query = "SELECT a FROM AlumnosAsignatura a WHERE a.apellidos = :apellidos")
})
public class AlumnosAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull        
    @Column(name = "ID_PROGALUMNO")
    private BigInteger idProgalumno;
    @Column(name = "ID_PROGPENSUM")
    private Integer idProgpensum;
    @Size(max = 50)
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Size(max = 30)
    @Column(name = "NOMBRES")
    private String nombres;
    @Size(max = 30)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "USUARIO_EMPRESARIAL")
    private String usuarioEmpresarial;
   
    @Column(name = "EMPLEADO")
    private String empleado;
    
    public AlumnosAsignatura() {
    }

    public BigInteger getIdProgalumno() {
        return idProgalumno;
    }

    public void setIdProgalumno(BigInteger idProgalumno) {
        this.idProgalumno = idProgalumno;
    }

    public Integer getIdProgpensum() {
        return idProgpensum;
    }

    public void setIdProgpensum(Integer idProgpensum) {
        this.idProgpensum = idProgpensum;
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
    
     public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getUsuarioEmpresarial() {
        return usuarioEmpresarial;
    }

    public void setUsuarioEmpresarial(String usuarioEmpresarial) {
        this.usuarioEmpresarial = usuarioEmpresarial;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
    
}
