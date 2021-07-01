/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_PERSONA")
@NamedQueries({
  @NamedQuery(name = SieduPersona.FIND_ALL, query = "SELECT s FROM SieduPersona s"),
  @NamedQuery(name = SieduPersona.FIND_BY_IDENTIFICACION, query = "SELECT s FROM SieduPersona s WHERE s.persNroid = :identificacion"),
  @NamedQuery(name = SieduPersona.FIND_BY_EMPLEADO_EXTERNO, query = "SELECT s FROM SieduPersona s WHERE s.persEmpe.id = :personaExterna")
})
@XmlRootElement
public class SieduPersona implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "SieduPersona.findAll";
  public static final String FIND_BY_IDENTIFICACION = "SieduPersona.findByIdentificacion";
  public static final String FIND_BY_EMPLEADO_EXTERNO = "SieduPersona.findByEmpleadoExterno";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "PERS_PERS", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PERSONA_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_PERSONA_SEQ_GEN", sequenceName = "SIEDU_PERSONA_SEQ", allocationSize = 1)
  private Long persPers;
  
  @Size(max = 20)
  @Column(name = "PERS_NROID", length = 20)
  private String persNroid;
  
  @Size(max = 2)
  @Column(name = "PERS_TPOID", length = 2)
  private String persTpoid;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PERS_NOMBRES", nullable = false, length = 30)
  private String persNombres;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PERS_APELLIDOS", nullable = false, length = 30)
  private String persApellidos;
  
  @Column(name = "PERS_EMP_UNDE_FUERZA")
  private Short persEmpUndeFuerza;
  
  @Column(name = "PERS_EMP_UNDE_UDEPE")
  private BigInteger persEmpUndeUdepe;
  
  @Column(name = "PERS_EMP_CONSECUTIVO")
  private BigInteger persEmpConsecutivo;
  
  
  @JoinColumns({
    @JoinColumn(name = "PERS_EMP_UNDE_FUERZA", referencedColumnName = "FUERZA", insertable = false, updatable = false),
    @JoinColumn(name = "PERS_EMP_CONSECUTIVO", referencedColumnName = "CONSECUTIVO", insertable = false, updatable = false)
  })
  @ManyToOne(optional = false)
  private UnidadDependencia unidad;
  
  
  
  @Basic(optional = false)
  @Size(min = 1, max = 5)
  @Column(name = "PERS_GRADO", nullable = true, length = 30)
  private String persGrado;
  
  @Basic(optional = false)
  @Size(min = 1, max = 240)
  @Column(name = "PERS_CORREO", nullable = true, length = 240)
  private String persCorreo;  
  
  @Basic(optional = false)
  @Size(min = 1, max = 120)
  @Column(name = "PERS_CARGO", nullable = true, length = 120)
  private String persCargo;
  
  @Basic(optional = false)
  @Column(name = "PERS_TELEFONO", nullable = true)
  private Long persTelefono;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PERS_USU_CREA", nullable = false, length = 30)
  private String persUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PERS_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date persFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PERS_MAQUINA_CREA", nullable = false, length = 100)
  private String persMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PERS_IP_CREA", nullable = false, length = 100)
  private String persIpCrea;
  
  @Size(max = 30)
  @Column(name = "PERS_USU_MOD", length = 30)
  private String persUsuMod;
  
  @Column(name = "PERS_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date persFechaMod;
  
  @Size(max = 100)
  @Column(name = "PERS_MAQUINA_MOD", length = 100)
  private String persMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "PERS_IP_MOD", length = 100)
  private String persIpMod;
  
  @Size(max = 50)
  @Column(name = "PERS_USUARIO_EMPRESARIAL")
  private String persUsuarioEmpresarial;
  
  @Size(max = 100)
  @Column(name = "PERS_EMAIL")
  private String persEmail;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "docePers", fetch = FetchType.LAZY)
  private List<SieduDocenteEvento> sieduDocenteEventoList;
  @JoinColumn(name = "PERS_EMPE", referencedColumnName = "EMPE_EMPE")
  @ManyToOne(fetch = FetchType.LAZY)
  private PersonaExterna persEmpe;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "parePers", fetch = FetchType.LAZY)
  private List<SieduParticipanteEvento> sieduParticipanteEventoList;

  public SieduPersona() {
  }

    public SieduPersona(Long persPers, String persNroid, String persTpoid, String persNombres, String persApellidos, Short persEmpUndeFuerza, BigInteger persEmpUndeUdepe, BigInteger persEmpConsecutivo, UnidadDependencia unidad, String persGrado, String persCorreo, String persCargo, Long persTelefono, String persUsuCrea, Date persFechaCrea, String persMaquinaCrea, String persIpCrea, String persUsuMod, Date persFechaMod, String persMaquinaMod, String persIpMod, String persUsuarioEmpresarial, PersonaExterna persEmpe) {
        this.persPers = persPers;
        this.persNroid = persNroid;
        this.persTpoid = persTpoid;
        this.persNombres = persNombres;
        this.persApellidos = persApellidos;
        this.persEmpUndeFuerza = persEmpUndeFuerza;
        this.persEmpUndeUdepe = persEmpUndeUdepe;
        this.persEmpConsecutivo = persEmpConsecutivo;
        this.unidad = unidad;
        this.persGrado = persGrado;
        this.persCorreo = persCorreo;
        this.persCargo = persCargo;
        this.persTelefono = persTelefono;
        this.persUsuCrea = persUsuCrea;
        this.persFechaCrea = persFechaCrea;
        this.persMaquinaCrea = persMaquinaCrea;
        this.persIpCrea = persIpCrea;
        this.persUsuMod = persUsuMod;
        this.persFechaMod = persFechaMod;
        this.persMaquinaMod = persMaquinaMod;
        this.persIpMod = persIpMod;
        this.persUsuarioEmpresarial = persUsuarioEmpresarial;
        this.persEmpe = persEmpe;
    } 

  public SieduPersona(Long persPers) {
    this.persPers = persPers;
  }

  public Long getPersPers() {
    return persPers;
  }

  public void setPersPers(Long persPers) {
    this.persPers = persPers;
  }

  public String getPersNroid() {
    return persNroid;
  }

  public void setPersNroid(String persNroid) {
    this.persNroid = persNroid;
  }

  public String getPersTpoid() {
    return persTpoid;
  }

  public void setPersTpoid(String persTpoid) {
    this.persTpoid = persTpoid;
  }

  public String getPersNombres() {
    return persNombres;
  }

  public void setPersNombres(String persNombres) {
    this.persNombres = persNombres;
  }

  public String getPersApellidos() {
    return persApellidos;
  }

  public void setPersApellidos(String persApellidos) {
    this.persApellidos = persApellidos;
  }

  public Short getPersEmpUndeFuerza() {
    return persEmpUndeFuerza;
  }

  public void setPersEmpUndeFuerza(Short persEmpUndeFuerza) {
    this.persEmpUndeFuerza = persEmpUndeFuerza;
  }

  public BigInteger getPersEmpUndeUdepe() {
    return persEmpUndeUdepe;
  }

  public void setPersEmpUndeUdepe(BigInteger persEmpUndeUdepe) {
    this.persEmpUndeUdepe = persEmpUndeUdepe;
  }

  public BigInteger getPersEmpConsecutivo() {
    return persEmpConsecutivo;
  }

  public void setPersEmpConsecutivo(BigInteger persEmpConsecutivo) {
    this.persEmpConsecutivo = persEmpConsecutivo;
  }

  @XmlTransient
  public String getPersUsuCrea() {
    return persUsuCrea;
  }

  public void setPersUsuCrea(String persUsuCrea) {
    this.persUsuCrea = persUsuCrea;
  }

  @XmlTransient
  public Date getPersFechaCrea() {
    return persFechaCrea;
  }

  public void setPersFechaCrea(Date persFechaCrea) {
    this.persFechaCrea = persFechaCrea;
  }

  @XmlTransient
  public String getPersMaquinaCrea() {
    return persMaquinaCrea;
  }

  public void setPersMaquinaCrea(String persMaquinaCrea) {
    this.persMaquinaCrea = persMaquinaCrea;
  }

  @XmlTransient
  public String getPersIpCrea() {
    return persIpCrea;
  }

  public void setPersIpCrea(String persIpCrea) {
    this.persIpCrea = persIpCrea;
  }

  @XmlTransient
  public String getPersUsuMod() {
    return persUsuMod;
  }

  public void setPersUsuMod(String persUsuMod) {
    this.persUsuMod = persUsuMod;
  }

  @XmlTransient
  public Date getPersFechaMod() {
    return persFechaMod;
  }

  public void setPersFechaMod(Date persFechaMod) {
    this.persFechaMod = persFechaMod;
  }

  @XmlTransient
  public String getPersMaquinaMod() {
    return persMaquinaMod;
  }

  public void setPersMaquinaMod(String persMaquinaMod) {
    this.persMaquinaMod = persMaquinaMod;
  }

  @XmlTransient
  public String getPersIpMod() {
    return persIpMod;
  }

  public void setPersIpMod(String persIpMod) {
    this.persIpMod = persIpMod;
  }

  public PersonaExterna getPersEmpe() {
    return persEmpe;
  }

  public void setPersEmpe(PersonaExterna persEmpe) {
    this.persEmpe = persEmpe;
  }

  @XmlTransient
  public List<SieduDocenteEvento> getSieduDocenteEventoList() {
    return sieduDocenteEventoList;
  }

  public void setSieduDocenteEventoList(List<SieduDocenteEvento> sieduDocenteEventoList) {
    this.sieduDocenteEventoList = sieduDocenteEventoList;
  }

  @XmlTransient
  public List<SieduParticipanteEvento> getSieduParticipanteEventoList() {
    return sieduParticipanteEventoList;
  }

  public void setSieduParticipanteEventoList(List<SieduParticipanteEvento> sieduParticipanteEventoList) {
    this.sieduParticipanteEventoList = sieduParticipanteEventoList;
  }

  @XmlTransient
  public String getPersGrado() {
    return persGrado;
  }

  public void setPersGrado(String persGrado) {
    this.persGrado = persGrado;
  }

  @XmlTransient
  public String getPersCorreo() {
    return persCorreo;
  }

  public void setPersCorreo(String persCorreo) {
    this.persCorreo = persCorreo;
  }

  @XmlTransient
  public String getPersCargo() {
    return persCargo;
  }

  public void setPersCargo(String persCargo) {
    this.persCargo = persCargo;
  }

  @XmlTransient
  public Long getPersTelefono() {
    return persTelefono;
  }

  public void setPersTelefono(Long persTelefono) {
    this.persTelefono = persTelefono;
  }

    public String getPersUsuarioEmpresarial() {
        return persUsuarioEmpresarial;
    }

    public void setPersUsuarioEmpresarial(String persUsuarioEmpresarial) {
        this.persUsuarioEmpresarial = persUsuarioEmpresarial;
    }
  
  

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + Objects.hashCode(this.persPers);
    hash = 97 * hash + Objects.hashCode(this.persNroid);
    hash = 97 * hash + Objects.hashCode(this.persTpoid);
    hash = 97 * hash + Objects.hashCode(this.persNombres);
    hash = 97 * hash + Objects.hashCode(this.persApellidos);
    hash = 97 * hash + Objects.hashCode(this.persEmpUndeFuerza);
    hash = 97 * hash + Objects.hashCode(this.persEmpUndeUdepe);
    hash = 97 * hash + Objects.hashCode(this.persEmpConsecutivo);
    hash = 97 * hash + Objects.hashCode(this.persGrado);
    hash = 97 * hash + Objects.hashCode(this.persCorreo);
    hash = 97 * hash + Objects.hashCode(this.persCargo);
    hash = 97 * hash + Objects.hashCode(this.persTelefono);
    hash = 97 * hash + Objects.hashCode(this.persUsuCrea);
    hash = 97 * hash + Objects.hashCode(this.persFechaCrea);
    hash = 97 * hash + Objects.hashCode(this.persMaquinaCrea);
    hash = 97 * hash + Objects.hashCode(this.persIpCrea);
    hash = 97 * hash + Objects.hashCode(this.persUsuMod);
    hash = 97 * hash + Objects.hashCode(this.persFechaMod);
    hash = 97 * hash + Objects.hashCode(this.persMaquinaMod);
    hash = 97 * hash + Objects.hashCode(this.persIpMod);
    hash = 97 * hash + Objects.hashCode(this.sieduDocenteEventoList);
    hash = 97 * hash + Objects.hashCode(this.persEmpe);
    hash = 97 * hash + Objects.hashCode(this.sieduParticipanteEventoList);
    hash = 97 * hash + Objects.hashCode(this.persUsuarioEmpresarial);
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
    final SieduPersona other = (SieduPersona) obj;
    if (!Objects.equals(this.persNroid, other.persNroid)) {
      return false;
    }
    if (!Objects.equals(this.persTpoid, other.persTpoid)) {
      return false;
    }
    if (!Objects.equals(this.persNombres, other.persNombres)) {
      return false;
    }
    if (!Objects.equals(this.persApellidos, other.persApellidos)) {
      return false;
    }
    if (!Objects.equals(this.persGrado, other.persGrado)) {
      return false;
    }
    if (!Objects.equals(this.persCorreo, other.persCorreo)) {
      return false;
    }
    if (!Objects.equals(this.persCargo, other.persCargo)) {
      return false;
    }
    if (!Objects.equals(this.persUsuCrea, other.persUsuCrea)) {
      return false;
    }
    if (!Objects.equals(this.persMaquinaCrea, other.persMaquinaCrea)) {
      return false;
    }
    if (!Objects.equals(this.persIpCrea, other.persIpCrea)) {
      return false;
    }
    if (!Objects.equals(this.persUsuMod, other.persUsuMod)) {
      return false;
    }
    if (!Objects.equals(this.persMaquinaMod, other.persMaquinaMod)) {
      return false;
    }
    if (!Objects.equals(this.persIpMod, other.persIpMod)) {
      return false;
    }
    if (!Objects.equals(this.persPers, other.persPers)) {
      return false;
    }
    if (!Objects.equals(this.persEmpUndeFuerza, other.persEmpUndeFuerza)) {
      return false;
    }
    if (!Objects.equals(this.persEmpUndeUdepe, other.persEmpUndeUdepe)) {
      return false;
    }
    if (!Objects.equals(this.persEmpConsecutivo, other.persEmpConsecutivo)) {
      return false;
    }
    if (!Objects.equals(this.persTelefono, other.persTelefono)) {
      return false;
    }
    if (!Objects.equals(this.persFechaCrea, other.persFechaCrea)) {
      return false;
    }
    if (!Objects.equals(this.persFechaMod, other.persFechaMod)) {
      return false;
    }
    if (!Objects.equals(this.sieduDocenteEventoList, other.sieduDocenteEventoList)) {
      return false;
    }
    if (!Objects.equals(this.persEmpe, other.persEmpe)) {
      return false;
    }
    if (!Objects.equals(this.sieduParticipanteEventoList, other.sieduParticipanteEventoList)) {
      return false;
    }
    if(!Objects.equals(this.persUsuarioEmpresarial, other.persUsuarioEmpresarial)){
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduPersona{" + "persPers=" + persPers + ", persNroid=" + persNroid + ", persTpoid=" + persTpoid + ", persNombres=" + persNombres + ", persApellidos=" + persApellidos + ", persEmpUndeFuerza=" + persEmpUndeFuerza + ", persEmpUndeUdepe=" + persEmpUndeUdepe + ", persEmpConsecutivo=" + persEmpConsecutivo + ", persGrado=" + persGrado + ", persCorreo=" + persCorreo + ", persCargo=" + persCargo + ", persTelefono=" + persTelefono + ", persUsuCrea=" + persUsuCrea + ", persFechaCrea=" + persFechaCrea + ", persMaquinaCrea=" + persMaquinaCrea + ", persIpCrea=" + persIpCrea + ", persUsuMod=" + persUsuMod + ", persFechaMod=" + persFechaMod + ", persMaquinaMod=" + persMaquinaMod + ", persIpMod=" + persIpMod + ", sieduDocenteEventoList=" + sieduDocenteEventoList + ", persEmpe=" + persEmpe + ", sieduParticipanteEventoList=" + sieduParticipanteEventoList + "persUsuarioEmpresarial" + persUsuarioEmpresarial + '}';
  }

  public UnidadDependencia getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadDependencia unidad) {
    this.unidad = unidad;
  }

    public String getPersEmail() {
        return persEmail;
    }

    public void setPersEmail(String persEmail) {
        this.persEmail = persEmail;
    }

}
