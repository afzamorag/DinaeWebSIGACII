package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "RESENIA_INVESTIGACION")
@NamedQueries({
  @NamedQuery(name = "ReseniaInvestigacion.findAll", query = "SELECT r FROM ReseniaInvestigacion r"),
  @NamedQuery(name = "ReseniaInvestigacion.findByIdReseniaInvestigacion", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.idReseniaInvestigacion = :idReseniaInvestigacion"),
  @NamedQuery(name = "ReseniaInvestigacion.findByResumenPalabras", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.resumenPalabras = :resumenPalabras"),
  @NamedQuery(name = "ReseniaInvestigacion.findByPalabrasClave", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.palabrasClave = :palabrasClave"),
  @NamedQuery(name = "ReseniaInvestigacion.findByAbstract1", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.abstract1 = :abstract1"),
  @NamedQuery(name = "ReseniaInvestigacion.findByKeywords", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.keywords = :keywords"),
  @NamedQuery(name = "ReseniaInvestigacion.findByConclusion", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.conclusion = :conclusion"),
  @NamedQuery(name = "ReseniaInvestigacion.findByRecomendaciones", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.recomendaciones = :recomendaciones"),
  @NamedQuery(name = "ReseniaInvestigacion.findByFechaRegistro", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.fechaRegistro = :fechaRegistro"),
  @NamedQuery(name = "ReseniaInvestigacion.findByIdUsuarioRol", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.idUsuarioRol = :idUsuarioRol"),
  @NamedQuery(name = "ReseniaInvestigacion.findByInformeFinalProyecto", query = "SELECT r FROM ReseniaInvestigacion r WHERE r.idInformeAvance.idInformeAvance = :idInformeAvance AND r.idProyecto.idProyecto = :idProyecto")})
public class ReseniaInvestigacion implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReseniaInvestigacion_seq_gen")
  @SequenceGenerator(name = "ReseniaInvestigacion_seq_gen", sequenceName = "SEC_RESENIA_INVESTIGACION", allocationSize = 1)
  @Column(name = "ID_RESENIA_INVESTIGACION")
  private Long idReseniaInvestigacion;

  @Column(name = "RESUMEN_PALABRAS")
  private String resumenPalabras;

  @Column(name = "PALABRAS_CLAVE")
  private String palabrasClave;

  @Column(name = "ABSTRACT")
  private String abstract1;

  @Column(name = "KEYWORDS")
  private String keywords;

  @Column(name = "CONCLUSION")
  private String conclusion;

  @Column(name = "RECOMENDACIONES")
  private String recomendaciones;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "ID_USUARIO_ROL")
  private Long idUsuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false)
  private Proyecto idProyecto;

  @JoinColumn(name = "ID_INFORME_AVANCE", referencedColumnName = "ID_INFORME_AVANCE")
  @ManyToOne(optional = false)
  private InformeAvance idInformeAvance;

  public ReseniaInvestigacion() {
  }

  public ReseniaInvestigacion(Long idReseniaInvestigacion) {
    this.idReseniaInvestigacion = idReseniaInvestigacion;
  }

  public ReseniaInvestigacion(Long idReseniaInvestigacion, String resumenPalabras, Date fechaRegistro, Long idUsuarioRol) {
    this.idReseniaInvestigacion = idReseniaInvestigacion;
    this.resumenPalabras = resumenPalabras;
    this.fechaRegistro = fechaRegistro;
    this.idUsuarioRol = idUsuarioRol;
  }

  public Long getIdReseniaInvestigacion() {
    return idReseniaInvestigacion;
  }

  public void setIdReseniaInvestigacion(Long idReseniaInvestigacion) {
    this.idReseniaInvestigacion = idReseniaInvestigacion;
  }

  public String getResumenPalabras() {
    return resumenPalabras;
  }

  public void setResumenPalabras(String resumenPalabras) {
    this.resumenPalabras = resumenPalabras;
  }

  public String getPalabrasClave() {
    return palabrasClave;
  }

  public void setPalabrasClave(String palabrasClave) {
    this.palabrasClave = palabrasClave;
  }

  public String getAbstract1() {
    return abstract1;
  }

  public void setAbstract1(String abstract1) {
    this.abstract1 = abstract1;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getConclusion() {
    return conclusion;
  }

  public void setConclusion(String conclusion) {
    this.conclusion = conclusion;
  }

  public String getRecomendaciones() {
    return recomendaciones;
  }

  public void setRecomendaciones(String recomendaciones) {
    this.recomendaciones = recomendaciones;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Proyecto getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Proyecto idProyecto) {
    this.idProyecto = idProyecto;
  }

  public InformeAvance getIdInformeAvance() {
    return idInformeAvance;
  }

  public void setIdInformeAvance(InformeAvance idInformeAvance) {
    this.idInformeAvance = idInformeAvance;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idReseniaInvestigacion != null ? idReseniaInvestigacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ReseniaInvestigacion)) {
      return false;
    }
    ReseniaInvestigacion other = (ReseniaInvestigacion) object;
    if ((this.idReseniaInvestigacion == null && other.idReseniaInvestigacion != null) || (this.idReseniaInvestigacion != null && !this.idReseniaInvestigacion.equals(other.idReseniaInvestigacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ReseniaInvestigacion[ idReseniaInvestigacion=" + idReseniaInvestigacion + " ]";
  }

}
