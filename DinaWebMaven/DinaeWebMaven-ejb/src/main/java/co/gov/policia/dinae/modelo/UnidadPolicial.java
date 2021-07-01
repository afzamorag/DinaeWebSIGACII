package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "UNIDAD_POLICIAL")
@NamedQueries({
  @NamedQuery(name = "UnidadPolicial.findAllActiviasInactivas", query = "SELECT u FROM UnidadPolicial u ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.cuentaUnidadesActivasYtipoUnidad", query = "SELECT COUNT(u) FROM UnidadPolicial u WHERE u.activo = 'Y' AND u.tipoUnidad.idTipoUnidad = :idTipoUnidad"),
  @NamedQuery(name = "UnidadPolicial.findAll", query = "SELECT u FROM UnidadPolicial u WHERE u.activo = 'Y' ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicialDTO.findAllActivas", query = "SELECT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO(u.idUnidadPolicial, u.nombre, u.siglaFisica, u.siglaPapa, u.tipoUnidad.idTipoUnidad, u.tipoUnidad.nombre, u.mail) FROM UnidadPolicial u WHERE u.activo = 'Y' ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.findUnidadPolicialPorSiglaFisica", query = "SELECT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO( u.idUnidadPolicial, u.nombre ) FROM UnidadPolicial u WHERE u.siglaFisica = :siglaFisica ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.findUnidadPolicialDiferenteAEjecutorPropuestaUnidad", query = "SELECT u FROM UnidadPolicial u WHERE u.activo = 'Y' AND u.idUnidadPolicial NOT IN( SELECT e.unidadPolicial.idUnidadPolicial FROM EjecutorNecesidad e WHERE e.propuestaNecesidad.idPropuestaNecesidad = :idPropuestaNecesidad) ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.findAllExcepto", query = "SELECT u FROM UnidadPolicial u WHERE u.activo = 'Y' AND u.idUnidadPolicial != :idUnidadPolicial ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.findAllDto", query = "SELECT new co.gov.policia.dinae.dto.UnidadPolicialDTO(u.idUnidadPolicial,u.nombre, u.siglaFisica) FROM UnidadPolicial u WHERE u.activo = 'Y' ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.findAllUnidadPolicialByTipoUnidad", query = "SELECT u FROM UnidadPolicial u WHERE u.tipoUnidad.idTipoUnidad IN (:listaTipoUnidad) AND u.activo = 'Y' ORDER BY u.siglaFisica ASC"),
  @NamedQuery(name = "UnidadPolicial.findAllOrderByNombre", query = "SELECT u FROM UnidadPolicial u WHERE u.activo = 'Y' ORDER BY u.siglaFisica ASC")
})
@XmlRootElement
public class UnidadPolicial implements Serializable, IDataModel {

  

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UnidadPolicial_seq_gen")
  @SequenceGenerator(name = "UnidadPolicial_seq_gen", sequenceName = "SEC_UNIDAD_POLICIAL", allocationSize = 1)
  @Column(name = "ID_UNIDAD_POLICIAL")
  @XmlAttribute
  private Long idUnidadPolicial;

  @XmlAttribute
  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "SIGLA_FISICA")
  private String siglaFisica;

  @Column(name = "SIGLA_DEPENDE")
  private String siglaDepende;

  @Column(name = "SIGLA_PAPA")
  private String siglaPapa;

  @Column(name = "DIRECCION")
  private String direccion;

  @Column(name = "TELEFONO")
  private String telefono;

  @Column(name = "MAIL")
  private String mail;

  @Column(name = "ACTIVO")
  private Character activo;

  @OneToMany(mappedBy = "unidadPolicial", fetch = FetchType.LAZY)
  private List<PropuestaNecesidad> propuestaNecesidadList;

  @OneToMany(mappedBy = "unidadPolicial", fetch = FetchType.LAZY)
  private List<UnidadPolicialPeriodo> unidadPolicialPeriodoList;

  @JoinColumn(name = "ID_TIPO_UNIDAD", referencedColumnName = "ID_TIPO_UNIDAD")
  @ManyToOne(fetch = FetchType.EAGER)
  private TipoUnidad tipoUnidad;
  
  @Transient
  private String nombreTipoUnidad;

  public UnidadPolicial() {
  }

  public UnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getSiglaFisica() {
    return siglaFisica;
  }

  public void setSiglaFisica(String siglaFisica) {
    this.siglaFisica = siglaFisica;
  }

  public String getSiglaDepende() {
    return siglaDepende;
  }

  public void setSiglaDepende(String siglaDepende) {
    this.siglaDepende = siglaDepende;
  }

  public String getSiglaPapa() {
    return siglaPapa;
  }

  public void setSiglaPapa(String siglaPapa) {
    this.siglaPapa = siglaPapa;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public List<PropuestaNecesidad> getPropuestaNecesidadList() {
    return propuestaNecesidadList;
  }

  public void setPropuestaNecesidadList(List<PropuestaNecesidad> propuestaNecesidadList) {
    this.propuestaNecesidadList = propuestaNecesidadList;
  }

  public List<UnidadPolicialPeriodo> getUnidadPolicialPeriodoList() {
    return unidadPolicialPeriodoList;
  }

  public void setUnidadPolicialPeriodoList(List<UnidadPolicialPeriodo> unidadPolicialPeriodoList) {
    this.unidadPolicialPeriodoList = unidadPolicialPeriodoList;
  }

  public TipoUnidad getTipoUnidad() {
    return tipoUnidad;
  }

  public void setTipoUnidad(TipoUnidad tipoUnidad) {
    this.tipoUnidad = tipoUnidad;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idUnidadPolicial != null ? idUnidadPolicial.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UnidadPolicial)) {
      return false;
    }
    UnidadPolicial other = (UnidadPolicial) object;
    if ((this.idUnidadPolicial == null && other.idUnidadPolicial != null) || (this.idUnidadPolicial != null && !this.idUnidadPolicial.equals(other.idUnidadPolicial))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.UnidadPolicial[ idUnidadPolicial=" + idUnidadPolicial + " ]";
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Character getActivo() {
    return activo;
  }

  public void setActivo(Character activo) {
    this.activo = activo;
  }

  public String getSiglaFisicaYnombreUnidad() {

    if (siglaFisica != null && nombre != null) {
      return siglaFisica.concat(" - ").concat(nombre);
    }

    return nombre;
  }

  @Override
  public String getLlaveModel() {

    return idUnidadPolicial.toString();

  }

  public String getNombreTipoUnidad() {
    return tipoUnidad != null ? tipoUnidad.getNombre() : "";
  }
}
