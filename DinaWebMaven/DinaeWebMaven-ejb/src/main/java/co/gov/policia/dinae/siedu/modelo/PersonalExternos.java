/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andres.zamorag
 */
@Entity
@Table(name = "PERSONAL_EXTERNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalExternos.findAll", query = "SELECT p FROM PersonalExternos p"),
    @NamedQuery(name = "PersonalExternos.findByIdAlumexterno", query = "SELECT p FROM PersonalExternos p WHERE p.idAlumexterno = :idAlumexterno"),
    @NamedQuery(name = "PersonalExternos.findByTipoIdentificacion", query = "SELECT p FROM PersonalExternos p WHERE p.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "PersonalExternos.findByIdentificacion", query = "SELECT p FROM PersonalExternos p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "PersonalExternos.findByNombres", query = "SELECT p FROM PersonalExternos p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "PersonalExternos.findByApellidos", query = "SELECT p FROM PersonalExternos p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "PersonalExternos.findByGrupoSanguineo", query = "SELECT p FROM PersonalExternos p WHERE p.grupoSanguineo = :grupoSanguineo"),
    @NamedQuery(name = "PersonalExternos.findByFactorRh", query = "SELECT p FROM PersonalExternos p WHERE p.factorRh = :factorRh"),
    @NamedQuery(name = "PersonalExternos.findBySexo", query = "SELECT p FROM PersonalExternos p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "PersonalExternos.findByEstadoAlumno", query = "SELECT p FROM PersonalExternos p WHERE p.estadoAlumno = :estadoAlumno"),
    @NamedQuery(name = "PersonalExternos.findByTipoPersonal", query = "SELECT p FROM PersonalExternos p WHERE p.tipoPersonal = :tipoPersonal"),
    @NamedQuery(name = "PersonalExternos.findByTarjetaProfesional", query = "SELECT p FROM PersonalExternos p WHERE p.tarjetaProfesional = :tarjetaProfesional"),
    @NamedQuery(name = "PersonalExternos.findByFechaNacimiento", query = "SELECT p FROM PersonalExternos p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "PersonalExternos.findByEstadoCivil", query = "SELECT p FROM PersonalExternos p WHERE p.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "PersonalExternos.findByDireccionResidencia", query = "SELECT p FROM PersonalExternos p WHERE p.direccionResidencia = :direccionResidencia"),
    @NamedQuery(name = "PersonalExternos.findByTelefono", query = "SELECT p FROM PersonalExternos p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "PersonalExternos.findByLugeCodigo", query = "SELECT p FROM PersonalExternos p WHERE p.lugeCodigo = :lugeCodigo"),    
    @NamedQuery(name = "PersonalExternos.findByCreadoPor", query = "SELECT p FROM PersonalExternos p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "PersonalExternos.findByFechaCreacion", query = "SELECT p FROM PersonalExternos p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PersonalExternos.findByMaquinaCreacion", query = "SELECT p FROM PersonalExternos p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "PersonalExternos.findByGradoAlfabetico", query = "SELECT p FROM PersonalExternos p WHERE p.gradoAlfabetico = :gradoAlfabetico"),
    @NamedQuery(name = "PersonalExternos.findByInstituto", query = "SELECT p FROM PersonalExternos p WHERE p.instituto = :instituto"),
    @NamedQuery(name = "PersonalExternos.findByCorreoElectronico", query = "SELECT p FROM PersonalExternos p WHERE p.correoElectronico = :correoElectronico")})
public class PersonalExternos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ALUMEXTERNO")
    private Integer idAlumexterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    @Size(max = 50)
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "GRUPO_SANGUINEO")
    private String grupoSanguineo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "FACTOR_RH")
    private String factorRh;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SEXO")
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ESTADO_ALUMNO")
    private String estadoAlumno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TIPO_PERSONAL")
    private String tipoPersonal;
    @Size(max = 15)
    @Column(name = "TARJETA_PROFESIONAL")
    private String tarjetaProfesional;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Size(max = 2)
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    @Size(max = 50)
    @Column(name = "DIRECCION_RESIDENCIA")
    private String direccionResidencia;
    @Column(name = "TELEFONO")
    private Long telefono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LUGE_CODIGO")
    private int lugeCodigo;   
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CREADO_POR")
    private String creadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "MAQUINA_CREACION")
    private String maquinaCreacion;
    @Size(max = 5)
    @Column(name = "GRADO_ALFABETICO")
    private String gradoAlfabetico;
    @Size(max = 60)
    @Column(name = "INSTITUTO")
    private String instituto;
    @Size(max = 240)
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @OneToMany(mappedBy = "peexIdAlumexterno")
    private Collection<PeriodosAlumno> periodosAlumnoCollection;
    @OneToMany(mappedBy = "peexIdAlumexterno")
    private Collection<PensumDocentes> pensumDocentesCollection;

    public PersonalExternos() {
    }

    public PersonalExternos(Integer idAlumexterno) {
        this.idAlumexterno = idAlumexterno;
    }

    public PersonalExternos(Integer idAlumexterno, String tipoIdentificacion, String nombres, String apellidos, String grupoSanguineo, String factorRh, String sexo, String estadoAlumno, String tipoPersonal, int lugeCodigo, short undeFuerza, BigInteger undeConsecutivo, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idAlumexterno = idAlumexterno;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.grupoSanguineo = grupoSanguineo;
        this.factorRh = factorRh;
        this.sexo = sexo;
        this.estadoAlumno = estadoAlumno;
        this.tipoPersonal = tipoPersonal;
        this.lugeCodigo = lugeCodigo;       
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public Integer getIdAlumexterno() {
        return idAlumexterno;
    }

    public void setIdAlumexterno(Integer idAlumexterno) {
        this.idAlumexterno = idAlumexterno;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFactorRh() {
        return factorRh;
    }

    public void setFactorRh(String factorRh) {
        this.factorRh = factorRh;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoAlumno() {
        return estadoAlumno;
    }

    public void setEstadoAlumno(String estadoAlumno) {
        this.estadoAlumno = estadoAlumno;
    }

    public String getTipoPersonal() {
        return tipoPersonal;
    }

    public void setTipoPersonal(String tipoPersonal) {
        this.tipoPersonal = tipoPersonal;
    }

    public String getTarjetaProfesional() {
        return tarjetaProfesional;
    }

    public void setTarjetaProfesional(String tarjetaProfesional) {
        this.tarjetaProfesional = tarjetaProfesional;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public int getLugeCodigo() {
        return lugeCodigo;
    }

    public void setLugeCodigo(int lugeCodigo) {
        this.lugeCodigo = lugeCodigo;
    }
    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMaquinaCreacion() {
        return maquinaCreacion;
    }

    public void setMaquinaCreacion(String maquinaCreacion) {
        this.maquinaCreacion = maquinaCreacion;
    }

    public String getGradoAlfabetico() {
        return gradoAlfabetico;
    }

    public void setGradoAlfabetico(String gradoAlfabetico) {
        this.gradoAlfabetico = gradoAlfabetico;
    }

    public String getInstituto() {
        return instituto;
    }

    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @XmlTransient
    public Collection<PeriodosAlumno> getPeriodosAlumnoCollection() {
        return periodosAlumnoCollection;
    }

    public void setPeriodosAlumnoCollection(Collection<PeriodosAlumno> periodosAlumnoCollection) {
        this.periodosAlumnoCollection = periodosAlumnoCollection;
    }

    @XmlTransient
    public Collection<PensumDocentes> getPensumDocentesCollection() {
        return pensumDocentesCollection;
    }

    public void setPensumDocentesCollection(Collection<PensumDocentes> pensumDocentesCollection) {
        this.pensumDocentesCollection = pensumDocentesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlumexterno != null ? idAlumexterno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonalExternos)) {
            return false;
        }
        PersonalExternos other = (PersonalExternos) object;
        if ((this.idAlumexterno == null && other.idAlumexterno != null) || (this.idAlumexterno != null && !this.idAlumexterno.equals(other.idAlumexterno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.PersonalExternos[ idAlumexterno=" + idAlumexterno + " ]";
    }
    
}
