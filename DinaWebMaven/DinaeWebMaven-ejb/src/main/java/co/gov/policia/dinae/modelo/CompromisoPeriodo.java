package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "COMPROMISO_PERIODO")
@NamedQueries({
  @NamedQuery(name = "CompromisoPeriodo.findAll", query = "SELECT h FROM CompromisoPeriodo h"),
  @NamedQuery(name = "CompromisoPeriodo.findDatePorPeriodoYFechaActualCompromisoActual", query = "SELECT h FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo AND h.fecha > :fechaCompromisoActual "),
  @NamedQuery(name = "CompromisoPeriodoDTO.findAllCompormisoPeriodoProyectosInstitucionalesPorAnioYestadoPublicado", query = "SELECT NEW co.gov.policia.dinae.dto.CompromisoPeriodoDTO( h.idCompromisoPeriodo, h.tipoCompromiso.idConstantes, h.tipoCompromiso.valor, h.numeroIncrementa, h.fecha ) FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo AND h.periodo.estado = 'P' ORDER BY h.fecha, h.numeroIncrementa ASC"),
  @NamedQuery(name = "CompromisoPeriodo.findAllCompormisoPeriodoProyectosInstitucionalesPorAnioYestadoPublicado", query = "SELECT h FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo AND h.periodo.estado = 'P' ORDER BY h.fecha, h.numeroIncrementa ASC"),
  @NamedQuery(name = "CompromisoPeriodoDTO.findAllCompormisoPeriodoProyectosConvocatoriaPorConvocatoria", query = "SELECT NEW co.gov.policia.dinae.dto.CompromisoPeriodoDTO( h.idCompromisoPeriodo, h.tipoCompromiso.idConstantes, h.tipoCompromiso.valor, h.numeroIncrementa, h.fecha  ) FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo ORDER BY h.fecha, h.numeroIncrementa ASC"),
  @NamedQuery(name = "CompromisoPeriodo.findAllCompormisoPeriodoProyectosConvocatoriaPorConvocatoria", query = "SELECT h FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo ORDER BY h.fecha, h.numeroIncrementa ASC"),
  @NamedQuery(name = "CompromisoPeriodo.findByPeriodo", query = "SELECT h FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "CompromisoPeriodo.findByPeriodoOrdenarPorFecha", query = "SELECT h FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo ORDER BY h.fecha ASC"),
  @NamedQuery(name = "CompromisoPeriodo.findPorPeriodoYtipoCompromiso", query = "SELECT h FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo AND h.tipoCompromiso.idConstantes = :idCompromiso"),
  @NamedQuery(name = "CompromisoPeriodo.contarPorPeriodoYtiposCompromisoFormulacionEinformeFinal", query = "SELECT COUNT(h) FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo AND h.tipoCompromiso.idConstantes = :idTipoCompromiso "),
  @NamedQuery(name = "CompromisoPeriodo.contarPorPeriodo", query = "SELECT COUNT(h) FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo "),
  @NamedQuery(name = "CompromisoPeriodo.conteoValidacionComprimisosPeriodo", query = "SELECT NEW co.gov.policia.dinae.dto.ValidacionCompromisoPeriodoDTO(h.tipoCompromiso.idConstantes, h.periodo.idPeriodo,  COUNT(h)) FROM CompromisoPeriodo h WHERE h.periodo.idPeriodo = :idPeriodo GROUP BY h.tipoCompromiso.idConstantes, h.periodo.idPeriodo"),
  @NamedQuery(name = "CompromisoPeriodo.finByIdPropuestaNecesidad", query = "SELECT h FROM CompromisoPeriodo h WHERE h.propuestaNecesidad = :propuestaNecesidad"),
  @NamedQuery(name = "CompromisoPeriodo.finByIdPropuestaNecesidadAndTipoCompromiso", query = "SELECT h FROM CompromisoPeriodo h WHERE h.propuestaNecesidad = :propuestaNecesidad AND h.tipoCompromiso = :tipoCompromiso")
})
public class CompromisoPeriodo implements Serializable, IDataModel, Comparable<CompromisoPeriodo> {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CompromisoPeriodo_seq_gen")
  @SequenceGenerator(name = "CompromisoPeriodo_seq_gen", sequenceName = "SEC_COMPROMISO_PERIODO", allocationSize = 1)
  @Column(name = "ID_COMPROMISO_PERIODO")
  private Long idCompromisoPeriodo;

  @Column(name = "NUMERO_INCREMENTA")
  private short numeroIncrementa;

  @Column(name = "FECHA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;

  @JoinColumn(name = "ID_PERIODO", referencedColumnName = "ID_PERIODO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Periodo periodo;

  @JoinColumn(name = "TIPO_COMPROMISO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.EAGER)
  private Constantes tipoCompromiso;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FECHA_ACTUALIZA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualiza;

  @Column(name = "IDENTIFICACION_REGISTRO")
  private String identificacionRegistro;

  @Column(name = "IDENTIFICACION_ACTUALIZA")
  private String identificacionActualiza;
  
  @JoinColumn(name = "ID_PROPUESTA_NECESIDAD", referencedColumnName = "ID_PROPUESTA_NECESIDAD")
  @ManyToOne(fetch = FetchType.EAGER)
  private PropuestaNecesidad propuestaNecesidad;

  public CompromisoPeriodo() {
  }

  public CompromisoPeriodo(Long idCompromisoPeriodo) {

    this.idCompromisoPeriodo = idCompromisoPeriodo;

  }

  public CompromisoPeriodo(Long idCompromisoPeriodo, short numeroIncrementa, Date fecha, Periodo periodo, Constantes tipoCompromiso, String nombreCompromisoNumeroIncrementa) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
    this.numeroIncrementa = numeroIncrementa;
    this.fecha = fecha;
    this.periodo = periodo;
    this.tipoCompromiso = tipoCompromiso;
    this.nombreCompromisoNumeroIncrementa = nombreCompromisoNumeroIncrementa;
  }

  public Long getIdCompromisoPeriodo() {
    return idCompromisoPeriodo;
  }

  public void setIdCompromisoPeriodo(Long idCompromisoPeriodo) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
  }

  public short getNumeroIncrementa() {
    return numeroIncrementa;
  }

  public void setNumeroIncrementa(short numeroIncrementa) {
    this.numeroIncrementa = numeroIncrementa;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
  }

  public Constantes getTipoCompromiso() {
    return tipoCompromiso;
  }

  public void setTipoCompromiso(Constantes tipoCompromiso) {
    this.tipoCompromiso = tipoCompromiso;
  }

    public PropuestaNecesidad getPropuestaNecesidad() {
        return propuestaNecesidad;
    }

    public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
        this.propuestaNecesidad = propuestaNecesidad;
    }  

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCompromisoPeriodo != null ? idCompromisoPeriodo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CompromisoPeriodo)) {
      return false;
    }
    CompromisoPeriodo other = (CompromisoPeriodo) object;
    if ((this.idCompromisoPeriodo == null && other.idCompromisoPeriodo != null) || (this.idCompromisoPeriodo != null && !this.idCompromisoPeriodo.equals(other.idCompromisoPeriodo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.CompromisoPeriodo[ idCompromisoPeriodo=" + idCompromisoPeriodo + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (idCompromisoPeriodo == null) {
      return null;
    }
    return idCompromisoPeriodo.toString();
  }

  @Override
  public int compareTo(CompromisoPeriodo o) {

    return fecha.compareTo(o.getFecha());

  }

  @Transient
  private String nombreCompromisoNumeroIncrementa;

  public String getNombreCompromisoNumeroIncrementa() {
    if (tipoCompromiso == null) {
      return "";
    }
    return tipoCompromiso.getValor().concat(
            tipoCompromiso.getIdConstantes().equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
            ? " ".concat(String.valueOf(numeroIncrementa)) : "");
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Date getFechaActualiza() {
    return fechaActualiza;
  }

  public void setFechaActualiza(Date fechaActualiza) {
    this.fechaActualiza = fechaActualiza;
  }

  public String getIdentificacionRegistro() {
    return identificacionRegistro;
  }

  public void setIdentificacionRegistro(String identificacionRegistro) {
    this.identificacionRegistro = identificacionRegistro;
  }

  public String getIdentificacionActualiza() {
    return identificacionActualiza;
  }

  public void setIdentificacionActualiza(String identificacionActualiza) {
    this.identificacionActualiza = identificacionActualiza;
  }

  public String getIdCompromisoPeriodoYtipo() {

    return idCompromisoPeriodo.toString().concat(":COMPROMISO_PERIODO");

  }

}
