package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "OTROS_GASTOS_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "OtrosGastosProyecto.findAll", query = "SELECT o FROM OtrosGastosProyecto o"),
  @NamedQuery(name = "OtrosGastosProyecto.findOtrosGastosByProyecto", query = "SELECT o FROM OtrosGastosProyecto o WHERE o.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "OtrosGastosProyecto.findOtrosGastosByPlanTrabajo", query = "SELECT o FROM OtrosGastosProyecto o WHERE o.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo"),
  @NamedQuery(name = "OtrosGastosProyectoDTO.findOtrosGastosByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.OtrosGastosProyectoDTO(o.idOtrosGastosProyecto, o.valor, o.fuenteProyecto.idFuenteProyecto, o.fuenteProyecto.nombreFuente, o.idTipoRubro, o.idTipoEspecieEfectivo, o.fechaRegistro, o.idUsuarioRol) FROM OtrosGastosProyecto o WHERE o.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "OtrosGastosProyectoDTO.findOtrosGastosByPlanTrabajoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.OtrosGastosProyectoDTO(o.idOtrosGastosProyecto, o.valor, o.fuenteProyecto.idFuenteProyecto, o.fuenteProyecto.nombreFuente, o.idTipoRubro, o.idTipoEspecieEfectivo, o.fechaRegistro, o.idUsuarioRol) FROM OtrosGastosProyecto o WHERE o.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo"),
  @NamedQuery(name = "OtrosGastosProyecto.findById", query = "SELECT o FROM OtrosGastosProyecto o WHERE o.idOtrosGastosProyecto = :idOtrosGastosProyecto"),
  @NamedQuery(name = "OtrosGastosProyecto.contarOtrosGastosRubro", query = "SELECT NEW co.gov.policia.dinae.dto.OtrosGastosProyectoDTO(o.idTipoRubro, COUNT(o.idOtrosGastosProyecto)) FROM OtrosGastosProyecto o WHERE o.fuenteProyecto.proyecto.idProyecto = :idProyecto AND o.idTipoRubro = :idTipoRubro GROUP BY o.idTipoRubro"),
  @NamedQuery(name = "OtrosGastosProyecto.contarOtrosGastosRubroPlanTrabajo", query = "SELECT NEW co.gov.policia.dinae.dto.OtrosGastosProyectoDTO(o.idTipoRubro, COUNT(o.idOtrosGastosProyecto)) FROM OtrosGastosProyecto o WHERE o.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo AND o.idTipoRubro = :idTipoRubro GROUP BY o.idTipoRubro")})
public class OtrosGastosProyecto implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OtrosGastosProyecto_seq_gen")
  @SequenceGenerator(name = "OtrosGastosProyecto_seq_gen", sequenceName = "SEC_OTROS_GASTOS_PROYECTO", allocationSize = 1)
  @Column(name = "ID_OTROS_GASTOS_PROYECTO")
  private Long idOtrosGastosProyecto;

  @Column(name = "VALOR")
  private Long valor;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne(optional = false)
  private FuenteProyecto fuenteProyecto;

  @Column(name = "ID_TIPO_RUBRO")
  private Long idTipoRubro;

  @Column(name = "ID_TIPO_ESPECIE_EFECTIVO")
  private Long idTipoEspecieEfectivo;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "ID_USUARIO_ROL")
  private Long idUsuarioRol;

  @Transient
  private Double valorGastadoInforme;

  public OtrosGastosProyecto() {
  }

  public OtrosGastosProyecto(Long idOtrosGastosProyecto) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
  }

  public OtrosGastosProyecto(Long idOtrosGastosProyecto, Long valor) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
    this.valor = valor;
  }

  public Long getIdOtrosGastosProyecto() {
    return idOtrosGastosProyecto;
  }

  public void setIdOtrosGastosProyecto(Long idOtrosGastosProyecto) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
  }

  public Long getValor() {
    return valor;
  }

  public void setValor(Long valor) {
    this.valor = valor;
  }

  public FuenteProyecto getFuenteProyecto() {
    return fuenteProyecto;
  }

  public void setFuenteProyecto(FuenteProyecto fuenteProyecto) {
    this.fuenteProyecto = fuenteProyecto;
  }

  public Long getIdTipoRubro() {
    return idTipoRubro;
  }

  public void setIdTipoRubro(Long idTipoRubro) {
    this.idTipoRubro = idTipoRubro;
  }

  public Long getIdTipoEspecieEfectivo() {
    return idTipoEspecieEfectivo;
  }

  public void setIdTipoEspecieEfectivo(Long idTipoEspecieEfectivo) {
    this.idTipoEspecieEfectivo = idTipoEspecieEfectivo;
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

  public Double getValorGastadoInforme() {
    return valorGastadoInforme;
  }

  public void setValorGastadoInforme(Double valorGastadoInforme) {
    this.valorGastadoInforme = valorGastadoInforme;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idOtrosGastosProyecto != null ? idOtrosGastosProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof OtrosGastosProyecto)) {
      return false;
    }
    OtrosGastosProyecto other = (OtrosGastosProyecto) object;
    if ((this.idOtrosGastosProyecto == null && other.idOtrosGastosProyecto != null) || (this.idOtrosGastosProyecto != null && !this.idOtrosGastosProyecto.equals(other.idOtrosGastosProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.OtrosGastosProyecto[ idOtrosGastosProyecto=" + idOtrosGastosProyecto + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (idOtrosGastosProyecto == null) {
      return null;
    }

    return idOtrosGastosProyecto.toString();
  }

}
