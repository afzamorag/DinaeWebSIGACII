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
@Table(name = "USR_EDUC.PERSONAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a"),    
    @NamedQuery(name = Alumnos.FIND_BY_IDENTIFICACION, query = "SELECT a FROM Alumnos a WHERE a.identificacion = :identificacion"),
    @NamedQuery(name = "Alumnos.findByNombres", query = "SELECT a FROM Alumnos a WHERE a.nombres = :nombres"),
    @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT a FROM Alumnos a WHERE a.apellidos = :apellidos")
})
public class Alumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_IDENTIFICACION = "Alumnos.findByIdentificacion";
    @Id
    @Basic(optional = false)
    @NotNull    
    @Size(max = 50)
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Size(max = 30)
    @Column(name = "NOMBRES")
    private String nombres;
    @Size(max = 30)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "CORREO_FUNCIONARIO")
    private String correoElectronico;
    @Column(name = "USUARIO_EMPRESARIAL")
    private String usuarioEmpresarial;
   
    @Column(name = "EMPLEADO")
    private String empleado;
    
    public Alumnos() {
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
