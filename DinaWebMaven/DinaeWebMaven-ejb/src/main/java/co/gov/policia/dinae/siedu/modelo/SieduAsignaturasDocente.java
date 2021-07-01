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
@Table(name = "SIEDU_ASIGNATURAS_DOCENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieduAsignaturasDocente.findAll", query = "SELECT s FROM SieduAsignaturasDocente s"),
    @NamedQuery(name = "SieduAsignaturasDocente.findByIdentificacion", query = "SELECT s FROM SieduAsignaturasDocente s WHERE s.identificacion = :identificacion"),
    @NamedQuery(name = "SieduAsignaturasDocente.findByNombres", query = "SELECT s FROM SieduAsignaturasDocente s WHERE s.nombres = :nombres"),
    @NamedQuery(name = "SieduAsignaturasDocente.findByApellidos", query = "SELECT s FROM SieduAsignaturasDocente s WHERE s.apellidos = :apellidos"),
    @NamedQuery(name = "SieduAsignaturasDocente.findByIdProgramacionAsignatura", query = "SELECT s FROM SieduAsignaturasDocente s WHERE s.idProgramacionAsignatura = :idProgramacionAsignatura"),
    @NamedQuery(name = "SieduAsignaturasDocente.findByIdSignatura", query = "SELECT s FROM SieduAsignaturasDocente s WHERE s.idSignatura = :idSignatura"),
    @NamedQuery(name = "SieduAsignaturasDocente.findByCorreoElectronico", query = "SELECT s FROM SieduAsignaturasDocente s WHERE s.correoElectronico = :correoElectronico")})
public class SieduAsignaturasDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGRAMACION_ASIGNATURA")
    private BigInteger idProgramacionAsignatura;
    @Size(max = 50)
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Size(max = 30)
    @Column(name = "NOMBRES")
    private String nombres;
    @Size(max = 30)
    @Column(name = "APELLIDOS")
    private String apellidos;    
    @Column(name = "ID_ASIGNATURA")
    private BigInteger idSignatura;
    @Size(max = 240)
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "USUARIO_EMPRESARIAL")
    private String usuarioEmpresarial;

    @Column(name = "EMPLEADO")
    private String empleado;
    
    public SieduAsignaturasDocente() {
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

    public BigInteger getIdProgramacionAsignatura() {
        return idProgramacionAsignatura;
    }

    public void setIdProgramacionAsignatura(BigInteger idProgramacionAsignatura) {
        this.idProgramacionAsignatura = idProgramacionAsignatura;
    }

    public BigInteger getIdSignatura() {
        return idSignatura;
    }

    public void setIdSignatura(BigInteger idSignatura) {
        this.idSignatura = idSignatura;
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
