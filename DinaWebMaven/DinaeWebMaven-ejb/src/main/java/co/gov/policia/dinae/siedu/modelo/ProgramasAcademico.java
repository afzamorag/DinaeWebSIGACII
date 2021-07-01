/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.modelo.Programas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PROGRAMAS_ACADEMICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramasAcademico.findAll", query = "SELECT p FROM ProgramasAcademico p"),
    @NamedQuery(name = "ProgramasAcademico.findByIdProgramaca", query = "SELECT p FROM ProgramasAcademico p WHERE p.idProgramaca = :idProgramaca"),
    @NamedQuery(name = "ProgramasAcademico.findByTipoNorma", query = "SELECT p FROM ProgramasAcademico p WHERE p.tipoNorma = :tipoNorma"),
    @NamedQuery(name = "ProgramasAcademico.findByAcreditado", query = "SELECT p FROM ProgramasAcademico p WHERE p.acreditado = :acreditado"),
    @NamedQuery(name = "ProgramasAcademico.findByAutoridadExpide", query = "SELECT p FROM ProgramasAcademico p WHERE p.autoridadExpide = :autoridadExpide"),
    @NamedQuery(name = "ProgramasAcademico.findByFechaRegistroicfes", query = "SELECT p FROM ProgramasAcademico p WHERE p.fechaRegistroicfes = :fechaRegistroicfes"),
    @NamedQuery(name = "ProgramasAcademico.findByFechaNorma", query = "SELECT p FROM ProgramasAcademico p WHERE p.fechaNorma = :fechaNorma"),
    @NamedQuery(name = "ProgramasAcademico.findByEnfasisPrograma", query = "SELECT p FROM ProgramasAcademico p WHERE p.enfasisPrograma = :enfasisPrograma"),
    @NamedQuery(name = "ProgramasAcademico.findByEnfasisProfesional", query = "SELECT p FROM ProgramasAcademico p WHERE p.enfasisProfesional = :enfasisProfesional"),
    @NamedQuery(name = "ProgramasAcademico.findByCodRegistroicfes", query = "SELECT p FROM ProgramasAcademico p WHERE p.codRegistroicfes = :codRegistroicfes"),
    @NamedQuery(name = "ProgramasAcademico.findByEnfasisAlumno", query = "SELECT p FROM ProgramasAcademico p WHERE p.enfasisAlumno = :enfasisAlumno"),
    @NamedQuery(name = "ProgramasAcademico.findByNroNorma", query = "SELECT p FROM ProgramasAcademico p WHERE p.nroNorma = :nroNorma"),
    @NamedQuery(name = "ProgramasAcademico.findByCreadoPor", query = "SELECT p FROM ProgramasAcademico p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "ProgramasAcademico.findByFechaCreacion", query = "SELECT p FROM ProgramasAcademico p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ProgramasAcademico.findByMaquinaCreacion", query = "SELECT p FROM ProgramasAcademico p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "ProgramasAcademico.findByModalidadEducacion", query = "SELECT p FROM ProgramasAcademico p WHERE p.modalidadEducacion = :modalidadEducacion"),
    @NamedQuery(name = "ProgramasAcademico.findByVigente", query = "SELECT p FROM ProgramasAcademico p WHERE p.vigente = :vigente"),
    @NamedQuery(name = "ProgramasAcademico.findByTipoPrograma", query = "SELECT p FROM ProgramasAcademico p WHERE p.tipoPrograma = :tipoPrograma"),
    @NamedQuery(name = "ProgramasAcademico.findByNivelEstudio", query = "SELECT p FROM ProgramasAcademico p WHERE p.nivelEstudio = :nivelEstudio"),
    @NamedQuery(name = "ProgramasAcademico.findByDescripcion", query = "SELECT p FROM ProgramasAcademico p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "ProgramasAcademico.findByPlanEducacion", query = "SELECT p FROM ProgramasAcademico p WHERE p.planEducacion = :planEducacion"),
    @NamedQuery(name = "ProgramasAcademico.findByPlanCapacitacion", query = "SELECT p FROM ProgramasAcademico p WHERE p.planCapacitacion = :planCapacitacion")})
public class ProgramasAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGRAMACA")
    private BigDecimal idProgramaca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TIPO_NORMA")
    private String tipoNorma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ACREDITADO")
    private String acreditado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "AUTORIDAD_EXPIDE")
    private String autoridadExpide;
    @Column(name = "FECHA_REGISTROICFES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroicfes;
    @Column(name = "FECHA_NORMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNorma;
    @Size(max = 2000)
    @Column(name = "ENFASIS_PROGRAMA")
    private String enfasisPrograma;
    @Size(max = 2000)
    @Column(name = "ENFASIS_PROFESIONAL")
    private String enfasisProfesional;
    @Size(max = 22)
    @Column(name = "COD_REGISTROICFES")
    private String codRegistroicfes;
    @Size(max = 2000)
    @Column(name = "ENFASIS_ALUMNO")
    private String enfasisAlumno;
    @Size(max = 5)
    @Column(name = "NRO_NORMA")
    private String nroNorma;
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
    @Column(name = "MODALIDAD_EDUCACION")
    private BigInteger modalidadEducacion;
    @Size(max = 2)
    @Column(name = "VIGENTE")
    private String vigente;
    @Column(name = "TIPO_PROGRAMA")
    private BigInteger tipoPrograma;
    @Column(name = "NIVEL_ESTUDIO")
    private BigInteger nivelEstudio;
    @Size(max = 140)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PLAN_EDUCACION")
    private BigInteger planEducacion;
    @Column(name = "PLAN_CAPACITACION")
    private BigInteger planCapacitacion;
    @JoinColumn(name = "PRGR_ID_PROGRAMA", referencedColumnName = "ID_PROGRAMA")
    @ManyToOne(optional = false)
    private Programas prgrIdPrograma;
    @JoinColumn(name = "NIAC_ID_NIVEL_ACADEMICO", referencedColumnName = "ID_NIVEL_ACADEMICO")
    @ManyToOne(optional = false)
    private NivelesAcademicos niacIdNivelAcademico;
    @JoinColumn(name = "FACU_ID_FACULTAD", referencedColumnName = "ID_FACULTAD")
    @ManyToOne(optional = false)
    private Facultades facuIdFacultad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programasAcademico")
    private Collection<Periodos> periodosCollection;

    public ProgramasAcademico() {
    }

    public ProgramasAcademico(BigDecimal idProgramaca) {
        this.idProgramaca = idProgramaca;
    }

    public ProgramasAcademico(BigDecimal idProgramaca, String tipoNorma, String acreditado, String autoridadExpide, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idProgramaca = idProgramaca;
        this.tipoNorma = tipoNorma;
        this.acreditado = acreditado;
        this.autoridadExpide = autoridadExpide;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public BigDecimal getIdProgramaca() {
        return idProgramaca;
    }

    public void setIdProgramaca(BigDecimal idProgramaca) {
        this.idProgramaca = idProgramaca;
    }

    public String getTipoNorma() {
        return tipoNorma;
    }

    public void setTipoNorma(String tipoNorma) {
        this.tipoNorma = tipoNorma;
    }

    public String getAcreditado() {
        return acreditado;
    }

    public void setAcreditado(String acreditado) {
        this.acreditado = acreditado;
    }

    public String getAutoridadExpide() {
        return autoridadExpide;
    }

    public void setAutoridadExpide(String autoridadExpide) {
        this.autoridadExpide = autoridadExpide;
    }

    public Date getFechaRegistroicfes() {
        return fechaRegistroicfes;
    }

    public void setFechaRegistroicfes(Date fechaRegistroicfes) {
        this.fechaRegistroicfes = fechaRegistroicfes;
    }

    public Date getFechaNorma() {
        return fechaNorma;
    }

    public void setFechaNorma(Date fechaNorma) {
        this.fechaNorma = fechaNorma;
    }

    public String getEnfasisPrograma() {
        return enfasisPrograma;
    }

    public void setEnfasisPrograma(String enfasisPrograma) {
        this.enfasisPrograma = enfasisPrograma;
    }

    public String getEnfasisProfesional() {
        return enfasisProfesional;
    }

    public void setEnfasisProfesional(String enfasisProfesional) {
        this.enfasisProfesional = enfasisProfesional;
    }

    public String getCodRegistroicfes() {
        return codRegistroicfes;
    }

    public void setCodRegistroicfes(String codRegistroicfes) {
        this.codRegistroicfes = codRegistroicfes;
    }

    public String getEnfasisAlumno() {
        return enfasisAlumno;
    }

    public void setEnfasisAlumno(String enfasisAlumno) {
        this.enfasisAlumno = enfasisAlumno;
    }

    public String getNroNorma() {
        return nroNorma;
    }

    public void setNroNorma(String nroNorma) {
        this.nroNorma = nroNorma;
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

    public BigInteger getModalidadEducacion() {
        return modalidadEducacion;
    }

    public void setModalidadEducacion(BigInteger modalidadEducacion) {
        this.modalidadEducacion = modalidadEducacion;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public BigInteger getTipoPrograma() {
        return tipoPrograma;
    }

    public void setTipoPrograma(BigInteger tipoPrograma) {
        this.tipoPrograma = tipoPrograma;
    }

    public BigInteger getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(BigInteger nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getPlanEducacion() {
        return planEducacion;
    }

    public void setPlanEducacion(BigInteger planEducacion) {
        this.planEducacion = planEducacion;
    }

    public BigInteger getPlanCapacitacion() {
        return planCapacitacion;
    }

    public void setPlanCapacitacion(BigInteger planCapacitacion) {
        this.planCapacitacion = planCapacitacion;
    }

    public Programas getPrgrIdPrograma() {
        return prgrIdPrograma;
    }

    public void setPrgrIdPrograma(Programas prgrIdPrograma) {
        this.prgrIdPrograma = prgrIdPrograma;
    }

    public NivelesAcademicos getNiacIdNivelAcademico() {
        return niacIdNivelAcademico;
    }

    public void setNiacIdNivelAcademico(NivelesAcademicos niacIdNivelAcademico) {
        this.niacIdNivelAcademico = niacIdNivelAcademico;
    }

    public Facultades getFacuIdFacultad() {
        return facuIdFacultad;
    }

    public void setFacuIdFacultad(Facultades facuIdFacultad) {
        this.facuIdFacultad = facuIdFacultad;
    }

    @XmlTransient
    public Collection<Periodos> getPeriodosCollection() {
        return periodosCollection;
    }

    public void setPeriodosCollection(Collection<Periodos> periodosCollection) {
        this.periodosCollection = periodosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramaca != null ? idProgramaca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramasAcademico)) {
            return false;
        }
        ProgramasAcademico other = (ProgramasAcademico) object;
        if ((this.idProgramaca == null && other.idProgramaca != null) || (this.idProgramaca != null && !this.idProgramaca.equals(other.idProgramaca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.ProgramasAcademico[ idProgramaca=" + idProgramaca + " ]";
    }
    
}
