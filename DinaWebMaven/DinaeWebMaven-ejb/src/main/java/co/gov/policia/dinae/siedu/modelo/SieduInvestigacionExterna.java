package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_INVESTIGACION_EXTERNA")
@NamedQueries({
    @NamedQuery(name = "SieduInvestigacionExterna.findAll", query = "SELECT s FROM SieduInvestigacionExterna s")})
@Cacheable(false)
@XmlRootElement
public class SieduInvestigacionExterna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SieduInvestigacionExterna_seq_gen")
    @SequenceGenerator(name = "SieduInvestigacionExterna_seq_gen", sequenceName = "siedu_investiga_externa_seq", allocationSize = 1)
    @Column(name = "INVE_INVE")
    private Long idInve;

    @JoinColumn(name = "INVE_ENTI", referencedColumnName = "ENTI_ENTI")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduEntidad entidad;

    @Basic(optional = false)
    @Size(min = 1, max = 500)
    @Column(name = "INVE_TITULO", nullable = false, length = 500)
    private String titulo;

    @JoinColumn(name = "INVE_ID_GRUPO_INVESTIGACION", referencedColumnName = "ID_GRUPO_INVESTIGACION")
    @ManyToOne(optional = true)
    private GrupoInvestigacion grupoInvestigacion;

    @Basic(optional = true)
    @Size(min = 1, max = 50)
    @Column(name = "INVE_CONVOCATORIA", nullable = true, length = 50)
    private String convocatoria;

    @JoinColumns({
        @JoinColumn(name = "INVE_UDE_CONSECUTIVO", referencedColumnName = "CONSECUTIVO"),
        @JoinColumn(name = "INVE_UDE_FUERZA", referencedColumnName = "FUERZA")
    })
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnidadDependencia unidad;

    /*@JoinColumns({
    @JoinColumn(name = "INVE_ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadPolicial unidad;*/
    @Basic(optional = false)
    @Column(name = "INVE_FECHA_APROBACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacion;

    @OneToMany(mappedBy = "investigacionExterna", fetch = FetchType.LAZY)
    private List<SieduCompromiso> compromisos;

    @OneToMany(mappedBy = "investigacionExterna", fetch = FetchType.LAZY)
    private List<SieduInvestigacionExternaParticipante> participantes;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "INVE_USU_CREA", nullable = false, length = 30)
    private String inveUsuCrea;

    @Basic(optional = false)
    @NotNull
    @Column(name = "INVE_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inveFechaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "INVE_MAQUINA_CREA", nullable = false, length = 100)
    private String inveMaquinaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "INVE_IP_CREA", nullable = false, length = 100)
    private String inveIpCrea;

    @Size(max = 30)
    @Column(name = "INVE_USU_MOD", length = 30)
    private String inveUsuMod;

    @Column(name = "INVE_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inveFechaMod;

    @Size(max = 100)
    @Column(name = "INVE_MAQUINA_MOD", length = 100)
    private String inveMaquinaMod;

    @Size(max = 100)
    @Column(name = "INVE_IP_MOD", length = 100)
    private String inveIpMod;

    public SieduInvestigacionExterna() {
    }

    public SieduInvestigacionExterna(Long idInve, SieduEntidad entidad, String titulo, GrupoInvestigacion grupoInvestigacion, String convocatoria, UnidadDependencia unidad, Date fechaAprobacion, List<SieduCompromiso> compromisos, List<SieduInvestigacionExternaParticipante> participantes, String inveUsuCrea, Date inveFechaCrea, String inveMaquinaCrea, String inveIpCrea, String inveUsuMod, Date inveFechaMod, String inveMaquinaMod, String inveIpMod) {
        this.idInve = idInve;
        this.entidad = entidad;
        this.titulo = titulo;
        this.grupoInvestigacion = grupoInvestigacion;
        this.convocatoria = convocatoria;
        this.unidad = unidad;
        this.fechaAprobacion = fechaAprobacion;
        this.compromisos = compromisos;
        this.participantes = participantes;
        this.inveUsuCrea = inveUsuCrea;
        this.inveFechaCrea = inveFechaCrea;
        this.inveMaquinaCrea = inveMaquinaCrea;
        this.inveIpCrea = inveIpCrea;
        this.inveUsuMod = inveUsuMod;
        this.inveFechaMod = inveFechaMod;
        this.inveMaquinaMod = inveMaquinaMod;
        this.inveIpMod = inveIpMod;
    }

    public Long getIdInve() {
        return idInve;
    }

    public void setIdInve(Long idInve) {
        this.idInve = idInve;
    }

    public SieduEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(SieduEntidad entidad) {
        this.entidad = entidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public GrupoInvestigacion getGrupoInvestigacion() {
        return grupoInvestigacion;
    }

    public void setGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
        this.grupoInvestigacion = grupoInvestigacion;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getInveUsuCrea() {
        return inveUsuCrea;
    }

    public void setInveUsuCrea(String inveUsuCrea) {
        this.inveUsuCrea = inveUsuCrea;
    }

    public Date getInveFechaCrea() {
        return inveFechaCrea;
    }

    public void setInveFechaCrea(Date inveFechaCrea) {
        this.inveFechaCrea = inveFechaCrea;
    }

    public String getInveMaquinaCrea() {
        return inveMaquinaCrea;
    }

    public void setInveMaquinaCrea(String inveMaquinaCrea) {
        this.inveMaquinaCrea = inveMaquinaCrea;
    }

    public String getInveIpCrea() {
        return inveIpCrea;
    }

    public void setInveIpCrea(String inveIpCrea) {
        this.inveIpCrea = inveIpCrea;
    }

    public String getInveUsuMod() {
        return inveUsuMod;
    }

    public void setInveUsuMod(String inveUsuMod) {
        this.inveUsuMod = inveUsuMod;
    }

    public Date getInveFechaMod() {
        return inveFechaMod;
    }

    public void setInveFechaMod(Date inveFechaMod) {
        this.inveFechaMod = inveFechaMod;
    }

    public String getInveMaquinaMod() {
        return inveMaquinaMod;
    }

    public void setInveMaquinaMod(String inveMaquinaMod) {
        this.inveMaquinaMod = inveMaquinaMod;
    }

    public String getInveIpMod() {
        return inveIpMod;
    }

    public void setInveIpMod(String inveIpMod) {
        this.inveIpMod = inveIpMod;
    }

    public List<SieduCompromiso> getCompromisos() {
        return compromisos;
    }

    public void setCompromisos(List<SieduCompromiso> compromisos) {
        this.compromisos = compromisos;
    }

    public List<SieduInvestigacionExternaParticipante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<SieduInvestigacionExternaParticipante> participantes) {
        this.participantes = participantes;
    }

    public UnidadDependencia getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadDependencia unidad) {
        this.unidad = unidad;
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idInve);
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
        final SieduInvestigacionExterna other = (SieduInvestigacionExterna) obj;
        if (!Objects.equals(this.idInve, other.idInve)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SieduInvestigacionExterna{" + "idInve=" + idInve + ", entidad=" + entidad + ", titulo=" + titulo + ", grupoInvestigacion=" + grupoInvestigacion + ", convocatoria=" + convocatoria + ", unidad=" + unidad + ", fechaAprobacion=" + fechaAprobacion + ", inveUsuCrea=" + inveUsuCrea + ", inveFechaCrea=" + inveFechaCrea + ", inveMaquinaCrea=" + inveMaquinaCrea + ", inveIpCrea=" + inveIpCrea + ", inveUsuMod=" + inveUsuMod + ", inveFechaMod=" + inveFechaMod + ", inveMaquinaMod=" + inveMaquinaMod + ", inveIpMod=" + inveIpMod + '}';
    }

}
