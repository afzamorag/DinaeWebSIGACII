/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(
        name = "SIEDU_PAE_CAPACITACION",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"CAPA_PAE", "CAPA_UDE_FUERZA", "CAPA_UDE_ESCU", "CAPA_ID_CARRERA", "CAPA_DOM_MODAL", "CAPA_DOM_PROCE", "CAPA_DOM_ESTRA"})
        })
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = Capacitacion.FIND_ALL, query = "SELECT s FROM Capacitacion s")
})
@XmlRootElement
public class Capacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "Capacitacion.findAll";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_CAPA", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PAE_CAPACITACION_SEQ_GEN")
    @SequenceGenerator(name = "SIEDU_PAE_CAPACITACION_SEQ_GEN", sequenceName = "SIEDU_CAPACITACION_SEQ", allocationSize = 1)
    private Long id;
    @JoinColumn(name = "CAPA_PAE", referencedColumnName = "PAE_PAE")
    @ManyToOne(optional = false)
    private PAE pae;
    @JoinColumns({
        @JoinColumn(name = "CAPA_UDE_ESCU", referencedColumnName = "CONSECUTIVO"),
        @JoinColumn(name = "CAPA_UDE_FUERZA_ESCU", referencedColumnName = "FUERZA")
    })
    @ManyToOne(optional = false)
    private UnidadDependencia escuela;

    @JoinColumn(name = "CAPA_DOM_PROCE", referencedColumnName = "ID_DOMINIO")
    @ManyToOne(optional = false)
    private Dominio proceso;

    @JoinColumn(name = "CAPA_DOM_ESTRA", referencedColumnName = "ID_DOMINIO")
    @ManyToOne(optional = false)
    private Dominio estrategia;

    @JoinColumns({
        @JoinColumn(name = "CAPA_ID_CARRERA", referencedColumnName = "ID_CARRERA"),
        @JoinColumn(name = "CAPA_FUERZA_CARRERA", referencedColumnName = "FUERZA")
    })
    @ManyToOne(optional = false)
    private Carreras carrera;
    @JoinColumn(name = "CAPA_DOM_MODAL", referencedColumnName = "ID_DOMINIO")
    @ManyToOne(optional = false)
    private Dominio modalidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_PPTO", nullable = false)
    private String manejaPresupuesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_NRO_NECE", nullable = false)
    private Integer necesidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_ESTADO", nullable = false)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_EVEN_T1", nullable = false)
    private Integer nroEventosT1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_EVEN_T2", nullable = false)
    private Integer nroEventosT2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_EVEN_T3", nullable = false)
    private Integer nroEventosT3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_EVEN_T4", nullable = false)
    private Integer nroEventosT4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_TOTAL_EVENTOS", nullable = false)
    private Integer totalEventos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_PERS_T1", nullable = false)
    private Integer nroParticipantesT1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_PERS_T2", nullable = false)
    private Integer nroParticipantesT2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_PERS_T3", nullable = false)
    private Integer nroParticipantesT3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_PERS_T4", nullable = false)
    private Integer nroParticipantesT4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_TOTAL_PERSONAS", nullable = false)
    private Integer totalParticipantes;
    @Column(name = "CAPA_EXTERNO")
    private String externo;
    @OneToMany(mappedBy = "capacitacion", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Presupuesto> presupuestos;
    @OneToMany(mappedBy = "capacitacion", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<NecesidadConsolida> necesidadesConsolida;
    @OneToMany(mappedBy = "eveeCapa", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<SieduEventoEscuela> eventoEscuela;
    // auditoria
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CAPA_USU_CREA", nullable = false, length = 30)
    private String creaUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPA_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creaFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CAPA_MAQUINA_CREA", nullable = false, length = 100)
    private String creaMaquina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CAPA_IP_CREA", nullable = false, length = 100)
    private String creaIP;
    @Size(max = 30)
    @Column(name = "CAPA_USU_MOD", length = 30)
    private String modUsuario;
    @Column(name = "CAPA_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modFecha;
    @Size(max = 100)
    @Column(name = "CAPA_MAQUINA_MOD", length = 100)
    private String modMaquina;
    @Size(max = 100)
    @Column(name = "CAPA_IP_MOD", length = 100)
    private String modIP;

    public Capacitacion() {
    }

    public Capacitacion(Long capaCapa) {
        this.id = capaCapa;
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
     * @return the pae
     */
    public PAE getPae() {
        return pae;
    }

    /**
     * @param pae the pae to set
     */
    public void setPae(PAE pae) {
        this.pae = pae;
    }

    /**
     * @return the escuela
     */
    public UnidadDependencia getEscuela() {
        return escuela;
    }

    /**
     * @param escuela the escuela to set
     */
    public void setEscuela(UnidadDependencia escuela) {
        this.escuela = escuela;
    }

    /**
     * @return the proceso
     */
    public Dominio getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(Dominio proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the estrategia
     */
    public Dominio getEstrategia() {
        return estrategia;
    }

    /**
     * @param estrategia the estrategia to set
     */
    public void setEstrategia(Dominio estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * @return the carrera
     */
    public Carreras getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(Carreras carrera) {
        this.carrera = carrera;
    }

    /**
     * @return the necesidad
     */
    public Integer getNecesidad() {
        return necesidad;
    }

    /**
     * @param necesidad the necesidad to set
     */
    public void setNecesidad(Integer necesidad) {
        this.necesidad = necesidad;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the totalParticipantes
     */
    public Integer getTotalParticipantes() {
        return totalParticipantes;
    }

    /**
     * @param totalParticipantes the totalParticipantes to set
     */
    public void setTotalParticipantes(Integer totalParticipantes) {
        this.totalParticipantes = totalParticipantes;
    }

    /**
     * @return the totalEventos
     */
    public Integer getTotalEventos() {
        return totalEventos;
    }

    /**
     * @param totalEventos the totalEventos to set
     */
    public void setTotalEventos(Integer totalEventos) {
        this.totalEventos = totalEventos;
    }

    /**
     * @return the modalidad
     */
    public Dominio getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(Dominio modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the manejaPresupuesto
     */
    public String getManejaPresupuesto() {
        return manejaPresupuesto;
    }

    /**
     * @param manejaPresupuesto the manejaPresupuesto to set
     */
    public void setManejaPresupuesto(String manejaPresupuesto) {
        this.manejaPresupuesto = manejaPresupuesto;
    }

    /**
     * @return the nroEventosT1
     */
    public Integer getNroEventosT1() {
        return nroEventosT1;
    }

    /**
     * @param nroEventosT1 the nroEventosT1 to set
     */
    public void setNroEventosT1(Integer nroEventosT1) {
        this.nroEventosT1 = nroEventosT1;
    }

    /**
     * @return the nroParticipantesT1
     */
    public Integer getNroParticipantesT1() {
        return nroParticipantesT1;
    }

    /**
     * @param nroParticipantesT1 the nroParticipantesT1 to set
     */
    public void setNroParticipantesT1(Integer nroParticipantesT1) {
        this.nroParticipantesT1 = nroParticipantesT1;
    }

    /**
     * @return the nroEventosT2
     */
    public Integer getNroEventosT2() {
        return nroEventosT2;
    }

    /**
     * @param nroEventosT2 the nroEventosT2 to set
     */
    public void setNroEventosT2(Integer nroEventosT2) {
        this.nroEventosT2 = nroEventosT2;
    }

    /**
     * @return the nroParticipantesT2
     */
    public Integer getNroParticipantesT2() {
        return nroParticipantesT2;
    }

    /**
     * @param nroParticipantesT2 the nroParticipantesT2 to set
     */
    public void setNroParticipantesT2(Integer nroParticipantesT2) {
        this.nroParticipantesT2 = nroParticipantesT2;
    }

    /**
     * @return the nroEventosT3
     */
    public Integer getNroEventosT3() {
        return nroEventosT3;
    }

    /**
     * @param nroEventosT3 the nroEventosT3 to set
     */
    public void setNroEventosT3(Integer nroEventosT3) {
        this.nroEventosT3 = nroEventosT3;
    }

    /**
     * @return the nroParticipantesT3
     */
    public Integer getNroParticipantesT3() {
        return nroParticipantesT3;
    }

    /**
     * @param nroParticipantesT3 the nroParticipantesT3 to set
     */
    public void setNroParticipantesT3(Integer nroParticipantesT3) {
        this.nroParticipantesT3 = nroParticipantesT3;
    }

    /**
     * @return the nroEventosT4
     */
    public Integer getNroEventosT4() {
        return nroEventosT4;
    }

    /**
     * @param nroEventosT4 the nroEventosT4 to set
     */
    public void setNroEventosT4(Integer nroEventosT4) {
        this.nroEventosT4 = nroEventosT4;
    }

    /**
     * @return the nroParticipantesT4
     */
    public Integer getNroParticipantesT4() {
        return nroParticipantesT4;
    }

    /**
     * @param nroParticipantesT4 the nroParticipantesT4 to set
     */
    public void setNroParticipantesT4(Integer nroParticipantesT4) {
        this.nroParticipantesT4 = nroParticipantesT4;
    }

    /**
     * @return the externo
     */
    public String getExterno() {
        return externo;
    }

    /**
     * @param externo the externo to set
     */
    public void setExterno(String externo) {
        this.externo = externo;
    }

    /**
     * @return the presupuestos
     */
    @XmlTransient
    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    /**
     * @param presupuestos the presupuestos to set
     */
    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    /**
     * @return the creaUsuario
     */
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
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
    @XmlTransient
    public String getModIP() {
        return modIP;
    }

    /**
     * @param modIP the modIP to set
     */
    public void setModIP(String modIP) {
        this.modIP = modIP;
    }

    @XmlTransient
    public List<NecesidadConsolida> getNecesidadesConsolida() {
        return necesidadesConsolida;
    }

    public void setNecesidadesConsolida(List<NecesidadConsolida> necesidadesConsolida) {
        this.necesidadesConsolida = necesidadesConsolida;
    }

    @XmlTransient
    public List<SieduEventoEscuela> getEventoEscuela() {
        return eventoEscuela;
    }

    public void setEventoEscuela(List<SieduEventoEscuela> eventoEscuela) {
        this.eventoEscuela = eventoEscuela;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Capacitacion)) {
            return false;
        }
        Capacitacion other = (Capacitacion) object;
        return !(!Objects.equals(this.id, other.id)
                || !Objects.equals(this.nroEventosT1, other.nroEventosT1)
                || !Objects.equals(this.nroEventosT2, other.nroEventosT2)
                || !Objects.equals(this.nroEventosT3, other.nroEventosT3)
                || !Objects.equals(this.nroEventosT4, other.nroEventosT4)
                || !Objects.equals(this.nroParticipantesT1, other.nroParticipantesT1)
                || !Objects.equals(this.nroParticipantesT2, other.nroParticipantesT2)
                || !Objects.equals(this.nroParticipantesT3, other.nroParticipantesT3)
                || !Objects.equals(this.nroParticipantesT4, other.nroParticipantesT4)
                || !Objects.equals(this.totalEventos, other.totalEventos)
                || !Objects.equals(this.totalParticipantes, other.totalParticipantes));
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Capacitacion[ capaCapa=" + getId() + " ]";
    }
}
