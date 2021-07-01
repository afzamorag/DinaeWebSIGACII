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
 * @author Édder Peña Barranco
 */
@Entity
@Table(name = "INVESTI_DESARROL_INVESTTIGADOR")
@NamedQueries({
  @NamedQuery(name = "InvestigacionDesarrollada.findAll", query = "SELECT i FROM InvestigacionDesarrollada i"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByIdInvestiDesarrolInvesttiga", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.idInvestigacionDesarrollada = :idInvestigacionDesarrollada"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByTituloInvestigacion", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.tituloInvestigacion = :tituloInvestigacion"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByResumen", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.resumen = :resumen"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByInstitucion", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.institucion = :institucion"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByFechaInicio", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.fechaInicio = :fechaInicio"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByFechaFin", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.fechaFin = :fechaFin"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByFechaIngresoRegistro", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.fechaIngresoRegistro = :fechaIngresoRegistro"),
  @NamedQuery(name = "InvestigacionDesarrollada.findByFechaModificaRegistro", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.fechaModificaRegistro = :fechaModificaRegistro"),
  @NamedQuery(name = "InvestigacionDesarrollada.findAllByIdentificacionInvestigador", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.idInvestigador.numeroIdentificacion = :identificacion"),
  @NamedQuery(name = "InvestigacionDesarrollada.findAllByIdentificacionSiat", query = "SELECT i FROM InvestigacionDesarrollada i WHERE i.identificacionSiat = :identificacionSiat")
})
public class InvestigacionDesarrollada implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_INVESTI_DESARROL_INVESTTIGA")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Investigacion_desarrollada_seq_gen")
  @SequenceGenerator(name = "Investigacion_desarrollada_seq_gen", sequenceName = "SEC_INVESTIGACIONES_DESARROLLA", allocationSize = 1)
  private Long idInvestigacionDesarrollada;

  @Column(name = "TITULO_INVESTIGACION")
  private String tituloInvestigacion;

  @Column(name = "RESUMEN")
  private String resumen;

  @Column(name = "INSTITUCION")
  private String institucion;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFin;

  @Column(name = "FECHA_INGRESO_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaIngresoRegistro;

  @Column(name = "FECHA_MODIFICA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificaRegistro;

  @JoinColumn(name = "ID_INVESTIGADOR", referencedColumnName = "ID_INVESTIGADOR")
  @ManyToOne(optional = false)
  private Investigador idInvestigador;

  @Column(name = "IDENTIFICACION_SIAT")
  private String identificacionSiat;

  public InvestigacionDesarrollada() {
  }

  public InvestigacionDesarrollada(Long idInvestigacionDesarrollada) {
    this.idInvestigacionDesarrollada = idInvestigacionDesarrollada;
  }

  public InvestigacionDesarrollada(Long idInvestigacionDesarrollada, String tituloInvestigacion, String resumen, String institucion, Date fechaInicio, Date fechaFin, Date fechaIngresoRegistro) {
    this.idInvestigacionDesarrollada = idInvestigacionDesarrollada;
    this.tituloInvestigacion = tituloInvestigacion;
    this.resumen = resumen;
    this.institucion = institucion;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.fechaIngresoRegistro = fechaIngresoRegistro;
  }

  public Long getIdInvestigacionDesarrollada() {
    return idInvestigacionDesarrollada;
  }

  public void setIdInvestiDesarrolInvesttiga(Long idInvestigacionDesarrollada) {
    this.idInvestigacionDesarrollada = idInvestigacionDesarrollada;
  }

  public String getTituloInvestigacion() {
    return tituloInvestigacion;
  }

  public void setTituloInvestigacion(String tituloInvestigacion) {
    this.tituloInvestigacion = tituloInvestigacion;
  }

  public String getResumen() {
    return resumen;
  }

  public void setResumen(String resumen) {
    this.resumen = resumen;
  }

  public String getInstitucion() {
    return institucion;
  }

  public void setInstitucion(String institucion) {
    this.institucion = institucion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Date getFechaIngresoRegistro() {
    return fechaIngresoRegistro;
  }

  public void setFechaIngresoRegistro(Date fechaIngresoRegistro) {
    this.fechaIngresoRegistro = fechaIngresoRegistro;
  }

  public Date getFechaModificaRegistro() {
    return fechaModificaRegistro;
  }

  public void setFechaModificaRegistro(Date fechaModificaRegistro) {
    this.fechaModificaRegistro = fechaModificaRegistro;
  }

  public Investigador getIdInvestigador() {
    return idInvestigador;
  }

  public void setIdInvestigador(Investigador idInvestigador) {
    this.idInvestigador = idInvestigador;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idInvestigacionDesarrollada != null ? idInvestigacionDesarrollada.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof InvestigacionDesarrollada)) {
      return false;
    }
    InvestigacionDesarrollada other = (InvestigacionDesarrollada) object;
    if ((this.idInvestigacionDesarrollada == null && other.idInvestigacionDesarrollada != null) || (this.idInvestigacionDesarrollada != null && !this.idInvestigacionDesarrollada.equals(other.idInvestigacionDesarrollada))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.InvestiDesarrolInvesttigador[ idInvestiDesarrolInvesttiga=" + idInvestigacionDesarrollada + " ]";
  }

  public String getIdentificacionSiat() {
    return identificacionSiat;
  }

  public void setIdentificacionSiat(String identificacionSiat) {
    this.identificacionSiat = identificacionSiat;
  }

}
