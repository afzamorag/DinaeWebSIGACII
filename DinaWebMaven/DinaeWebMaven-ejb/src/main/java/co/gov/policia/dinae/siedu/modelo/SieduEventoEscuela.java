/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_EVENTO_ESCUELA")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = SieduEventoEscuela.FIND_ALL, query = "SELECT s FROM SieduEventoEscuela s"),
    @NamedQuery(name = SieduEventoEscuela.FIND_BY_EVENTO, query = "SELECT s FROM SieduEventoEscuela s where s.eveeEven.id = :evento"),
    @NamedQuery(name = SieduEventoEscuela.FIND_BY_CAPACITACION, query = "SELECT s FROM SieduEventoEscuela s WHERE s.eveeCapa.id = :capacitacion ORDER BY s.eveeCapa.id, s.eveeTrimes, s.eveeNroEvento"),
    @NamedQuery(name = SieduEventoEscuela.FIND_BY_CAPACITACION_AND_TRIMESTE, query = "SELECT s FROM SieduEventoEscuela s WHERE s.eveeCapa.id = :capacitacion AND s.eveeTrimes = :trimestre ORDER BY s.eveeCapa.id, s.eveeTrimes, s.eveeNroEvento"),
    @NamedQuery(name = SieduEventoEscuela.FIND_BY_PAE_CAPACITACION, query = "SELECT s FROM SieduEventoEscuela s  WHERE s.eveeCapa.id = :eveCapa AND s.eveeFechaini IS NOT NULL"),
    @NamedQuery(name = SieduEventoEscuela.FIND_BY_PAE_CAPACITACION_ALL, query = "SELECT s FROM SieduEventoEscuela s  WHERE s.eveeCapa.id = :eveCapa")
})
public class SieduEventoEscuela implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "SieduEventoEscuela.findAll";
    public static final String FIND_BY_EVENTO = "SieduEventoEscuela.findByEvento";
    public static final String FIND_BY_CAPACITACION = "SieduEventoEscuela.findByCapacitacion";
    public static final String FIND_BY_CAPACITACION_AND_TRIMESTE = "SieduEventoEscuela.findByCapacitacionAndTrimestre";
    public static final String NATIVE_QUERY_COUNT_BY_CAPACITACION_AND_TRIMESTE = "SELECT COUNT(*) FROM SIEDU_EVENTO_ESCUELA T WHERE T.EVEE_CAPA = ? AND T.EVEE_TRIMES = ? AND T.EVEE_FECHAINI IS NOT NULL";
    public static final String FIND_BY_PAE_CAPACITACION = "SieduEventoEscuela.findByPaeCapacitacion";
    public static final String FIND_BY_PAE_CAPACITACION_ALL = "SieduEventoEscuela.findByPaeCapacitacionAll";

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EVEE_EVEE", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_EVENTO_ESCUELA_SEQ_GEN")
    @SequenceGenerator(name = "SIEDU_EVENTO_ESCUELA_SEQ_GEN", sequenceName = "SIEDU_EVENTO_ESCUELA_SEQ", allocationSize = 1)
    private Long eveeEvee;
    @Column(name = "EVEE_NRO_EVENTO")
    private Integer eveeNroEvento;
    @Column(name = "EVEE_TRIMES")
    private Integer eveeTrimes;
    @Column(name = "EVEE_NRO_PARTICIPANTES")
    private Integer eveeNroParticipantes;
    @Column(name = "EVEE_NRO_DESERTADOS")
    private Integer eveeNroDesertados;
    @Column(name = "EVEE_FECHAINI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eveeFechaini;
    @Column(name = "EVEE_FECHAFIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eveeFechafin;
    @Column(name = "EVEE_CERRADO")
    private String eveeCerrado;
    @Column(name = "EVEE_DOM_FINANCIA")
    private Long eveeDomFinancia;
    @Column(name = "EVEE_COSTO_CAPA")
    private Long eveeCostoCapa;
    @Column(name = "EVEE_NRO_DIAS")
    private Integer eveeNroDias;
    @Column(name = "EVEE_NRO_CONVOCADOS")
    private Integer eveeNroConvocados;
    @Column(name = "EVE_NRO_NOAPROBADOS")
    private Integer eveNroNoaprobados;
    @Column(name = "EVE_NRO_APROBADOS")
    private Integer eveNroAprobados;
    @JoinColumns({
        @JoinColumn(name = "EVEE_UDE_UDEPE", referencedColumnName = "CONSECUTIVO"),
        @JoinColumn(name = "EVEE_UDE_FUERZA", referencedColumnName = "FUERZA")
    })
    @ManyToOne
    private UnidadDependencia eveeUdeUdepe;
    @Column(name = "EVEE_USU_CREA")
    private String eveeUsuCrea;
    @Column(name = "EVEE_FECHA_CREA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eveeFechaCrea;
    @Column(name = "EVEE_MAQUINA_CREA")
    private String eveeMaquinaCrea;
    @Column(name = "EVEE_IP_CREA")
    private String eveeIpCrea;
    @Column(name = "EVEE_USU_MOD")
    private String eveeUsuMod;
    @Column(name = "EVEE_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eveeFechaMod;
    @Column(name = "EVEE_MAQUINA_MOD")
    private String eveeMaquinaMod;
    @Column(name = "EVEE_IP_MOD")
    private String eveeIpMod;
    @Column(name = "EVEE_CALIFICACION_EVENTO")
    private Double eveeCalificacionEvento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doceEvee", fetch = FetchType.LAZY)
    private List<SieduDocenteEvento> sieduDocenteEventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coneEvee", fetch = FetchType.LAZY)
    private List<SieduConvocatoriaEvento> sieduConvocatoriaEventoList;
    @JoinColumn(name = "EVEE_PPTO", referencedColumnName = "PPTO_PPTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Presupuesto eveePpto;
    @JoinColumn(name = "EVEE_CAPA", referencedColumnName = "CAPA_CAPA", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Capacitacion eveeCapa;
    @JoinColumn(name = "EVEE_EVEN", referencedColumnName = "EVEN_EVEN")
    @ManyToOne(fetch = FetchType.LAZY)
    private SieduEvento eveeEven;
    @JoinColumn(name = "EVEE_ENTI", referencedColumnName = "ENTI_ENTI")
    @ManyToOne(fetch = FetchType.LAZY)
    private SieduEntidad eveeEnti;
    @JoinColumn(name = "EVEE_CONV", referencedColumnName = "CONV_CONV")
    @ManyToOne(fetch = FetchType.LAZY)
    private SieduConvenio eveeConv;
    @JoinColumn(name = "EVEE_LUGGEO_CAPACITA", referencedColumnName = "COD_MUNI")
    @ManyToOne(fetch = FetchType.LAZY)
    private LugarGeografico lugarGeografico;
    @JoinColumn(name = "EVEE_EVAL", referencedColumnName = "EVAL_EVAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private Evaluacion evaluacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pareEvee", fetch = FetchType.LAZY)
    private List<SieduParticipanteEvento> sieduParticipanteEventoList;

    public SieduEventoEscuela() {
    }

    public SieduEventoEscuela(Long eveeEvee) {
        this.eveeEvee = eveeEvee;
    }

    public Long getEveeEvee() {
        return eveeEvee;
    }

    public void setEveeEvee(Long eveeEvee) {
        this.eveeEvee = eveeEvee;
    }

    public Integer getEveeNroEvento() {
        return eveeNroEvento;
    }

    public void setEveeNroEvento(Integer eveeNroEvento) {
        this.eveeNroEvento = eveeNroEvento;
    }

    public Integer getEveeTrimes() {
        return eveeTrimes;
    }

    public void setEveeTrimes(Integer eveeTrimes) {
        this.eveeTrimes = eveeTrimes;
    }

    public Integer getEveeNroParticipantes() {
        return eveeNroParticipantes;
    }

    public void setEveeNroParticipantes(Integer eveeNroParticipantes) {
        this.eveeNroParticipantes = eveeNroParticipantes;
    }

    public Integer getEveeNroDesertados() {
        return eveeNroDesertados;
    }

    public void setEveeNroDesertados(Integer eveeNroDesertados) {
        this.eveeNroDesertados = eveeNroDesertados;
    }

    public Date getEveeFechaini() {
        return eveeFechaini;
    }

    public void setEveeFechaini(Date eveeFechaini) {
        this.eveeFechaini = eveeFechaini;
    }

    public Date getEveeFechafin() {
        return eveeFechafin;
    }

    public void setEveeFechafin(Date eveeFechafin) {
        this.eveeFechafin = eveeFechafin;
    }

    public String getEveeCerrado() {
        return eveeCerrado;
    }

    public void setEveeCerrado(String eveeCerrado) {
        this.eveeCerrado = eveeCerrado;
    }

    public Long getEveeDomFinancia() {
        return eveeDomFinancia;
    }

    public void setEveeDomFinancia(Long eveeDomFinancia) {
        this.eveeDomFinancia = eveeDomFinancia;
    }

    public Long getEveeCostoCapa() {
        return eveeCostoCapa;
    }

    public void setEveeCostoCapa(Long eveeCostoCapa) {
        this.eveeCostoCapa = eveeCostoCapa;
    }

    public Integer getEveeNroDias() {
        return eveeNroDias;
    }

    public void setEveeNroDias(Integer eveeNroDias) {
        this.eveeNroDias = eveeNroDias;
    }

    public Integer getEveeNroConvocados() {
        return eveeNroConvocados;
    }

    public void setEveeNroConvocados(Integer eveeNroConvocados) {
        this.eveeNroConvocados = eveeNroConvocados;
    }

    public Integer getEveNroNoaprobados() {
        return eveNroNoaprobados;
    }

    public void setEveNroNoaprobados(Integer eveNroNoaprobados) {
        this.eveNroNoaprobados = eveNroNoaprobados;
    }

    public Integer getEveNroAprobados() {
        return eveNroAprobados;
    }

    public void setEveNroAprobados(Integer eveNroAprobados) {
        this.eveNroAprobados = eveNroAprobados;
    }

    public UnidadDependencia getEveeUdeUdepe() {
        return eveeUdeUdepe;
    }

    public void setEveeUdeUdepe(UnidadDependencia eveeUdeUdepe) {
        this.eveeUdeUdepe = eveeUdeUdepe;
    }

    public String getEveeUsuCrea() {
        return eveeUsuCrea;
    }

    public void setEveeUsuCrea(String eveeUsuCrea) {
        this.eveeUsuCrea = eveeUsuCrea;
    }

    public Date getEveeFechaCrea() {
        return eveeFechaCrea;
    }

    public void setEveeFechaCrea(Date eveeFechaCrea) {
        this.eveeFechaCrea = eveeFechaCrea;
    }

    public String getEveeMaquinaCrea() {
        return eveeMaquinaCrea;
    }

    public void setEveeMaquinaCrea(String eveeMaquinaCrea) {
        this.eveeMaquinaCrea = eveeMaquinaCrea;
    }

    public String getEveeIpCrea() {
        return eveeIpCrea;
    }

    public void setEveeIpCrea(String eveeIpCrea) {
        this.eveeIpCrea = eveeIpCrea;
    }

    public String getEveeUsuMod() {
        return eveeUsuMod;
    }

    public void setEveeUsuMod(String eveeUsuMod) {
        this.eveeUsuMod = eveeUsuMod;
    }

    public Date getEveeFechaMod() {
        return eveeFechaMod;
    }

    public void setEveeFechaMod(Date eveeFechaMod) {
        this.eveeFechaMod = eveeFechaMod;
    }

    public String getEveeMaquinaMod() {
        return eveeMaquinaMod;
    }

    public void setEveeMaquinaMod(String eveeMaquinaMod) {
        this.eveeMaquinaMod = eveeMaquinaMod;
    }

    public String getEveeIpMod() {
        return eveeIpMod;
    }

    public void setEveeIpMod(String eveeIpMod) {
        this.eveeIpMod = eveeIpMod;
    }

    @XmlTransient
    public List<SieduDocenteEvento> getSieduDocenteEventoList() {
        return sieduDocenteEventoList;
    }

    public void setSieduDocenteEventoList(List<SieduDocenteEvento> sieduDocenteEventoList) {
        this.sieduDocenteEventoList = sieduDocenteEventoList;
    }

    @XmlTransient
    public List<SieduConvocatoriaEvento> getSieduConvocatoriaEventoList() {
        return sieduConvocatoriaEventoList;
    }

    public void setSieduConvocatoriaEventoList(List<SieduConvocatoriaEvento> sieduConvocatoriaEventoList) {
        this.sieduConvocatoriaEventoList = sieduConvocatoriaEventoList;
    }

    public Presupuesto getEveePpto() {
        return eveePpto;
    }

    public void setEveePpto(Presupuesto eveePpto) {
        this.eveePpto = eveePpto;
    }

    public Capacitacion getEveeCapa() {
        return eveeCapa;
    }

    public void setEveeCapa(Capacitacion eveeCapa) {
        this.eveeCapa = eveeCapa;
    }

    public SieduEvento getEveeEven() {
        return eveeEven;
    }

    public void setEveeEven(SieduEvento eveeEven) {
        this.eveeEven = eveeEven;
    }

    public SieduEntidad getEveeEnti() {
        return eveeEnti;
    }

    public void setEveeEnti(SieduEntidad eveeEnti) {
        this.eveeEnti = eveeEnti;
    }

    public SieduConvenio getEveeConv() {
        return eveeConv;
    }

    public void setEveeConv(SieduConvenio eveeConv) {
        this.eveeConv = eveeConv;
    }

    public LugarGeografico getLugarGeografico() {
        return lugarGeografico;
    }

    public void setLugarGeografico(LugarGeografico lugarGeografico) {
        this.lugarGeografico = lugarGeografico;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    @XmlTransient
    public List<SieduParticipanteEvento> getSieduParticipanteEventoList() {
        return sieduParticipanteEventoList;
    }

    public void setSieduParticipanteEventoList(List<SieduParticipanteEvento> sieduParticipanteEventoList) {
        this.sieduParticipanteEventoList = sieduParticipanteEventoList;
    }

    public Double getEveeCalificacionEvento() {
        return eveeCalificacionEvento;
    }

    public void setEveeCalificacionEvento(Double eveeCalificacionEvento) {
        this.eveeCalificacionEvento = eveeCalificacionEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eveeEvee != null ? eveeEvee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieduEventoEscuela)) {
            return false;
        }
        SieduEventoEscuela other = (SieduEventoEscuela) object;
        if ((this.eveeEvee == null && other.eveeEvee != null) || (this.eveeEvee != null && !this.eveeEvee.equals(other.eveeEvee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela[ eveeEvee=" + eveeEvee + " ]";
    }
}
