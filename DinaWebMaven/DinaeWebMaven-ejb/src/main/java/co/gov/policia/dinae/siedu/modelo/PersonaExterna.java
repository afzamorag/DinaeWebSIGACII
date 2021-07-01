/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.LugarGeografico;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_EMPLEADO_EXTERNO")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = PersonaExterna.FIND_ALL, query = "SELECT s FROM PersonaExterna s")
})
@XmlRootElement
public class PersonaExterna implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "PersonaExterna.findAll";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPE_EMPE", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_EMPLEADO_EXTERNO_SEQ_GEN")
    @SequenceGenerator(name = "SIEDU_EMPLEADO_EXTERNO_SEQ_GEN", sequenceName = "SIEDU_EMPLEADO_EXTERNO_SEQ", allocationSize = 1)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMPE_TIPOID", nullable = false, length = 2)
    private String identificacionTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EMPE_NROID", nullable = false, length = 20)
    private String identificacionNro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMPE_NOMBRES", nullable = false, length = 30)
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMPE_APELLIDOS", nullable = false, length = 30)
    private String apellidos;
    @JoinColumn(name = "EMPE_LUGGEO_PAINAC", referencedColumnName = "COD_PAIS", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LugarGeografico nacimientoPais;
    @JoinColumn(name = "EMPE_LUGGEO_CIUNAC", referencedColumnName = "COD_MUNI", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LugarGeografico nacimientoCiudad;
    @JoinColumn(name = "EMPE_LUGGEO_PAIRES", referencedColumnName = "COD_PAIS", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LugarGeografico residenciaPais;
    @JoinColumn(name = "EMPE_LUGGEO_CIURES", referencedColumnName = "COD_MUNI", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LugarGeografico residenciaCiudad;
    @Size(max = 100)
    @Column(name = "EMPE_BARRIO_RES", length = 100)
    private String residenciaBarrio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMPE_DIRE_RES", nullable = false, length = 100)
    private String residenciaDireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMPE_ESTADO_CIVIL", nullable = false, length = 2)
    private String estadoCivil;
    @JoinColumn(name = "EMPE_ENTIDAD", referencedColumnName = "ENTI_ENTI")
    @ManyToOne(optional = false)
    private SieduEntidad entidadDependencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPE_FECHA_NAC", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMPE_SEXO", nullable = false, length = 2)
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMPE_FACTORRH", nullable = false, length = 2)
    private String factorRH;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMPE_GRUPO_SANG", nullable = false, length = 2)
    private String grupoSanguineo;
    // auditoria
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMPE_USU_CREA", nullable = false, length = 30)
    private String creaUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPE_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creaFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMPE_MAQUINA_CREA", nullable = false, length = 100)
    private String creaMaquina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMPE_IP_CREA", nullable = false, length = 100)
    private String creaIP;
    @Size(max = 30)
    @Column(name = "EMPE_USU_MOD", length = 30)
    private String modUsuario;
    @Column(name = "EMPE_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modFecha;
    @Size(max = 100)
    @Column(name = "EMPE_MAQUINA_MOD", length = 100)
    private String modMaquina;
    @Size(max = 100)
    @Column(name = "EMPE_IP_MOD", length = 100)
    private String modIP;
    @Size(max = 100)
    @Column(name = "EMPE_EMAIL", length = 100)
    private String email;

    public PersonaExterna() {
    }

    public PersonaExterna(Long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the identificacionTipo
     */
    public String getIdentificacionTipo() {
        return identificacionTipo;
    }

    /**
     * @param identificacionTipo the identificacionTipo to set
     */
    public void setIdentificacionTipo(String identificacionTipo) {
        this.identificacionTipo = identificacionTipo;
    }

    /**
     * @return the identificacionNro
     */
    public String getIdentificacionNro() {
        return identificacionNro;
    }

    /**
     * @param identificacionNro the identificacionNro to set
     */
    public void setIdentificacionNro(String identificacionNro) {
        this.identificacionNro = identificacionNro;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nacimientoPais
     */
    public LugarGeografico getNacimientoPais() {
        return nacimientoPais;
    }

    /**
     * @param nacimientoPais the nacimientoPais to set
     */
    public void setNacimientoPais(LugarGeografico nacimientoPais) {
        this.nacimientoPais = nacimientoPais;
    }

    /**
     * @return the nacimientoCiudad
     */
    public LugarGeografico getNacimientoCiudad() {
        return nacimientoCiudad;
    }

    /**
     * @param nacimientoCiudad the nacimientoCiudad to set
     */
    public void setNacimientoCiudad(LugarGeografico nacimientoCiudad) {
        this.nacimientoCiudad = nacimientoCiudad;
    }

    /**
     * @return the residenciaPais
     */
    public LugarGeografico getResidenciaPais() {
        return residenciaPais;
    }

    /**
     * @param residenciaPais the residenciaPais to set
     */
    public void setResidenciaPais(LugarGeografico residenciaPais) {
        this.residenciaPais = residenciaPais;
    }

    /**
     * @return the residenciaCiudad
     */
    public LugarGeografico getResidenciaCiudad() {
        return residenciaCiudad;
    }

    /**
     * @param residenciaCiudad the residenciaCiudad to set
     */
    public void setResidenciaCiudad(LugarGeografico residenciaCiudad) {
        this.residenciaCiudad = residenciaCiudad;
    }

    /**
     * @return the residenciaBarrio
     */
    public String getResidenciaBarrio() {
        return residenciaBarrio;
    }

    /**
     * @param residenciaBarrio the residenciaBarrio to set
     */
    public void setResidenciaBarrio(String residenciaBarrio) {
        this.residenciaBarrio = residenciaBarrio;
    }

    /**
     * @return the residenciaDireccion
     */
    public String getResidenciaDireccion() {
        return residenciaDireccion;
    }

    /**
     * @param residenciaDireccion the residenciaDireccion to set
     */
    public void setResidenciaDireccion(String residenciaDireccion) {
        this.residenciaDireccion = residenciaDireccion;
    }

    /**
     * @return the estadoCivil
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the entidadDependencia
     */
    public SieduEntidad getEntidadDependencia() {
        return entidadDependencia;
    }

    /**
     * @param entidadDependencia the entidadDependencia to set
     */
    public void setEntidadDependencia(SieduEntidad entidadDependencia) {
        this.entidadDependencia = entidadDependencia;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the factorRH
     */
    public String getFactorRH() {
        return factorRH;
    }

    /**
     * @param factorRH the factorRH to set
     */
    public void setFactorRH(String factorRH) {
        this.factorRH = factorRH;
    }

    /**
     * @return the grupoSanguineo
     */
    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    /**
     * @param grupoSanguineo the grupoSanguineo to set
     */
    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    /**
     * @return the creaUsuario
     */
    public String getCreaUsuario() {
        return creaUsuario;
    }

    /**
     * @param creaUsuario the creaUsuario to set
     */
    public void setCreaUsuario(String creaUsuario) {
        this.creaUsuario = creaUsuario;
    }

    /**
     * @return the creaFecha
     */
    public Date getCreaFecha() {
        return creaFecha;
    }

    /**
     * @param creaFecha the creaFecha to set
     */
    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    /**
     * @return the creaMaquina
     */
    public String getCreaMaquina() {
        return creaMaquina;
    }

    /**
     * @param creaMaquina the creaMaquina to set
     */
    public void setCreaMaquina(String creaMaquina) {
        this.creaMaquina = creaMaquina;
    }

    /**
     * @return the creaIP
     */
    public String getCreaIP() {
        return creaIP;
    }

    /**
     * @param creaIP the creaIP to set
     */
    public void setCreaIP(String creaIP) {
        this.creaIP = creaIP;
    }

    /**
     * @return the modUsuario
     */
    public String getModUsuario() {
        return modUsuario;
    }

    /**
     * @param modUsuario the modUsuario to set
     */
    public void setModUsuario(String modUsuario) {
        this.modUsuario = modUsuario;
    }

    /**
     * @return the modFecha
     */
    public Date getModFecha() {
        return modFecha;
    }

    /**
     * @param modFecha the modFecha to set
     */
    public void setModFecha(Date modFecha) {
        this.modFecha = modFecha;
    }

    /**
     * @return the modMaquina
     */
    public String getModMaquina() {
        return modMaquina;
    }

    /**
     * @param modMaquina the modMaquina to set
     */
    public void setModMaquina(String modMaquina) {
        this.modMaquina = modMaquina;
    }

    /**
     * @return the modIP
     */
    public String getModIP() {
        return modIP;
    }

    /**
     * @param modIP the modIP to set
     */
    public void setModIP(String modIP) {
        this.modIP = modIP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.getId());
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
        final PersonaExterna other = (PersonaExterna) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonaExterna{" + "id=" + getId() + '}';
    }
}
