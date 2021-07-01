package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.GrupoInvestigacion;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_PROPIEDAD_INTELECTUAL")
@NamedQueries({
    @NamedQuery(name = "SieduPropiedadIntelectual.findAll", query = "SELECT s FROM SieduPropiedadIntelectual s ")})
@Cacheable(false)
@XmlRootElement
public class SieduPropiedadIntelectual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SieduPropiedadIntelectual_seq_gen")
    @SequenceGenerator(name = "SieduPropiedadIntelectual_seq_gen", sequenceName = "siedu_propiedad_intelect_seq", allocationSize = 1)
    @Column(name = "PIN_PIN")
    private Long idPin;

    @JoinColumn(name = "PIN_DOM_TIPO", referencedColumnName = "ID_DOMINIO")
    @ManyToOne(optional = true)
    private Dominio tipo;

    @Basic(optional = true)
    @Size(min = 1, max = 500)
    @Column(name = "PIN_DESCRIPCION", nullable = true, length = 500)
    private String descripcion;

    @Column(name = "PIN_ES_EXTERNA", nullable = true)
    private Character esExterna;

    @JoinColumn(name = "PIN_ENTI_UBICACION", referencedColumnName = "ENTI_ENTI")
    @ManyToOne(optional = true)
    private SieduEntidad entidad;

    @JoinColumns({
        @JoinColumn(name = "PIN_UDE_CONSECUTIVO_UBICACION", referencedColumnName = "CONSECUTIVO"),
        @JoinColumn(name = "PIN_UDE_FUERZA_UBICACION", referencedColumnName = "FUERZA")
    })
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UnidadDependencia unidad;

    @JoinColumn(name = "PIN_ID_GRUPO_INVESTIGACION", referencedColumnName = "ID_GRUPO_INVESTIGACION")
    @ManyToOne(optional = true)
    private GrupoInvestigacion grupoInvestigacion;

    @Basic(optional = false)
    @Column(name = "PIN_FECHA_PUBLICA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublica;

    @Column(name = "PIN_REGISTRADO", nullable = false)
    private Character registrado;

    @Basic(optional = true)
    @Column(name = "PIN_FECHA_REGISTRO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Basic(optional = true)
    @Size(min = 1, max = 50)
    @Column(name = "PIN_NRO_REGISTRO", nullable = true, length = 50)
    private String nroRegistro;
        
    @Basic(optional = true)
    @Size(min = 1, max = 30)
    @Column(name = "PIN_ISBN", nullable = true, length = 30)
    private String isbn;

    @Basic(optional = true)
    @Size(min = 1, max = 30)
    @Column(name = "PIN_ISSN", nullable = true, length = 30)
    private String issn;


    @Basic(optional = true)
    @Column(name = "PIN_FECHA_OTORGAMIENTO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOtorgamiento;

    @Size(max = 30)
    @Column(name = "PIN_USU_CREA", length = 30)
    private String pinUsuCrea;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PIN_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pinFechaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PIN_MAQUINA_CREA", nullable = false, length = 100)
    private String pinMaquinaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PIN_IP_CREA", nullable = false, length = 100)
    private String pinIpCrea;

    @Size(max = 30)
    @Column(name = "PIN_USU_MOD", length = 30)
    private String pinUsuMod;

    @Column(name = "PIN_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pinFechaMod;

    @Size(max = 100)
    @Column(name = "PIN_MAQUINA_MOD", length = 100)
    private String pinMaquinaMod;

    @Size(max = 100)
    @Column(name = "PIN_IP_MOD", length = 100)
    private String pinIpMod;

    @Size(max = 100)
    @Column(name = "PIN_NOMBRE_ARCHIVO", length = 100)
    private String pinNombreArchivo;

    @Size(max = 100)
    @Column(name = "PIN_NOMBRE_ARCHIVO_FISICO", length = 100)
    private String pinNombreArchivoFisico;

    public SieduPropiedadIntelectual() {
    }

    public SieduPropiedadIntelectual(Long idPin, Dominio tipo, String descripcion, Character esExterna, SieduEntidad entidad, UnidadDependencia unidad, GrupoInvestigacion grupoInvestigacion, Date fechaPublica, Character registrado, Date fechaRegistro, String nroRegistro, Date fechaOtorgamiento, String isbn, String issn, String pinUsuCrea, Date pinFechaCrea, String pinMaquinaCrea, String pinIpCrea, String pinUsuMod, Date pinFechaMod, String pinMaquinaMod, String pinIpMod) {
        this.idPin = idPin;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.esExterna = esExterna;
        this.entidad = entidad;
        this.unidad = unidad;
        this.grupoInvestigacion = grupoInvestigacion;
        this.fechaPublica = fechaPublica;
        this.registrado = registrado;
        this.fechaRegistro = fechaRegistro;
        this.nroRegistro = nroRegistro;
        this.fechaOtorgamiento = fechaOtorgamiento;
        this.isbn = isbn;
        this.issn = issn;
        this.pinUsuCrea = pinUsuCrea;
        this.pinFechaCrea = pinFechaCrea;
        this.pinMaquinaCrea = pinMaquinaCrea;
        this.pinIpCrea = pinIpCrea;
        this.pinUsuMod = pinUsuMod;
        this.pinFechaMod = pinFechaMod;
        this.pinMaquinaMod = pinMaquinaMod;
        this.pinIpMod = pinIpMod;
    }

    @XmlTransient
    public Long getIdPin() {
        return idPin;
    }

    public void setIdPin(Long idPin) {
        this.idPin = idPin;
    }

    @XmlTransient
    public Dominio getTipo() {
        return tipo;
    }

    public void setTipo(Dominio tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getEsExterna() {
        return esExterna;
    }

    public void setEsExterna(Character esExterna) {
        this.esExterna = esExterna;
    }

    @XmlTransient
    public SieduEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(SieduEntidad entidad) {
        this.entidad = entidad;
    }

    @XmlTransient
    public UnidadDependencia getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadDependencia unidad) {
        this.unidad = unidad;
    }

    @XmlTransient
    public GrupoInvestigacion getGrupoInvestigacion() {
        return grupoInvestigacion;
    }

    public void setGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
        this.grupoInvestigacion = grupoInvestigacion;
    }

    @XmlTransient
    public Date getFechaPublica() {
        return fechaPublica;
    }

    public void setFechaPublica(Date fechaPublica) {
        this.fechaPublica = fechaPublica;
    }

    public Character getRegistrado() {
        return registrado;
    }

    public void setRegistrado(Character registrado) {
        this.registrado = registrado;
    }

    @XmlTransient
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public String getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(String nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    @XmlTransient
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @XmlTransient
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }
    
    @XmlTransient
    public Date getFechaOtorgamiento() {
        return fechaOtorgamiento;
    }

    public void setFechaOtorgamiento(Date fechaOtorgamiento) {
        this.fechaOtorgamiento = fechaOtorgamiento;
    }

    
    @XmlTransient
    public String getPinUsuCrea() {
        return pinUsuCrea;
    }

    public void setPinUsuCrea(String pinUsuCrea) {
        this.pinUsuCrea = pinUsuCrea;
    }

    @XmlTransient
    public Date getPinFechaCrea() {
        return pinFechaCrea;
    }

    public void setPinFechaCrea(Date pinFechaCrea) {
        this.pinFechaCrea = pinFechaCrea;
    }

    @XmlTransient
    public String getPinMaquinaCrea() {
        return pinMaquinaCrea;
    }

    public void setPinMaquinaCrea(String pinMaquinaCrea) {
        this.pinMaquinaCrea = pinMaquinaCrea;
    }

    @XmlTransient
    public String getPinIpCrea() {
        return pinIpCrea;
    }

    public void setPinIpCrea(String pinIpCrea) {
        this.pinIpCrea = pinIpCrea;
    }

    @XmlTransient
    public String getPinUsuMod() {
        return pinUsuMod;
    }

    public void setPinUsuMod(String pinUsuMod) {
        this.pinUsuMod = pinUsuMod;
    }

    @XmlTransient
    public Date getPinFechaMod() {
        return pinFechaMod;
    }

    public void setPinFechaMod(Date pinFechaMod) {
        this.pinFechaMod = pinFechaMod;
    }

    @XmlTransient
    public String getPinMaquinaMod() {
        return pinMaquinaMod;
    }

    public void setPinMaquinaMod(String pinMaquinaMod) {
        this.pinMaquinaMod = pinMaquinaMod;
    }

    @XmlTransient
    public String getPinIpMod() {
        return pinIpMod;
    }

    public void setPinIpMod(String pinIpMod) {
        this.pinIpMod = pinIpMod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idPin);
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
        final SieduPropiedadIntelectual other = (SieduPropiedadIntelectual) obj;
        if (!Objects.equals(this.idPin, other.idPin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SieduPropiedadIntelectual{" + "idPin=" + idPin + ", tipo=" + tipo + ", descripcion=" + descripcion + ", esExterna=" + esExterna + ", entidad=" + entidad + ", unidad=" + unidad + ", grupoInvestigacion=" + grupoInvestigacion + ", fechaPublica=" + fechaPublica + ", registrado=" + registrado + ", fechaRegistro=" + fechaRegistro + ", nroRegistro=" + nroRegistro + ", fechaOtorgamiento=" + fechaOtorgamiento + ", isbn=" + isbn + ", issn=" + issn + ", pinUsuCrea=" + pinUsuCrea + ", pinFechaCrea=" + pinFechaCrea + ", pinMaquinaCrea=" + pinMaquinaCrea + ", pinIpCrea=" + pinIpCrea + ", pinUsuMod=" + pinUsuMod + ", pinFechaMod=" + pinFechaMod + ", pinMaquinaMod=" + pinMaquinaMod + ", pinIpMod=" + pinIpMod + '}';
    }

    public String getPinNombreArchivo() {
        return pinNombreArchivo;
    }

    public void setPinNombreArchivo(String pinNombreArchivo) {
        this.pinNombreArchivo = pinNombreArchivo;
    }

    public String getPinNombreArchivoFisico() {
        return pinNombreArchivoFisico;
    }

    public void setPinNombreArchivoFisico(String pinNombreArchivoFisico) {
        this.pinNombreArchivoFisico = pinNombreArchivoFisico;
    }
}
