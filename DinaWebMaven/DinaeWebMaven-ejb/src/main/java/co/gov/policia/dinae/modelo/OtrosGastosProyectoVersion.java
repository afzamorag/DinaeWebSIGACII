package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "OTROS_GASTOS_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "OtrosGastosProyectoVersion.findAll", query = "SELECT o FROM OtrosGastosProyectoVersion o"),
  @NamedQuery(name = "OtrosGastosProyectoVersion.findOtrosGastosByProyecto", query = "SELECT o FROM OtrosGastosProyectoVersion o WHERE o.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "OtrosGastosProyectoVersionOtrosGastosProyectoDTO.findOtrosGastosByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.OtrosGastosProyectoDTO(o.idOtrosGastosProyecto, o.valor, o.fuenteProyecto.idFuenteProyecto, o.fuenteProyecto.nombreFuente, o.idTipoRubro, o.idTipoEspecieEfectivo, o.fechaRegistro, o.idUsuarioRol) FROM OtrosGastosProyectoVersion o WHERE o.fuenteProyecto.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "OtrosGastosProyectoVersion.findById", query = "SELECT o FROM OtrosGastosProyectoVersion o WHERE o.idOtrosGastosProyecto = :idOtrosGastosProyecto"),
  @NamedQuery(name = "OtrosGastosProyectoVersion.contarOtrosGastosRubro", query = "SELECT NEW co.gov.policia.dinae.dto.OtrosGastosProyectoDTO(o.idTipoRubro, COUNT(o.idOtrosGastosProyecto)) FROM OtrosGastosProyectoVersion o WHERE o.fuenteProyecto.proyecto.idProyecto = :idProyecto AND o.idTipoRubro = :idTipoRubro GROUP BY o.idTipoRubro")})
public class OtrosGastosProyectoVersion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
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

  public OtrosGastosProyectoVersion() {
  }

  public OtrosGastosProyectoVersion(Long idOtrosGastosProyecto) {
    this.idOtrosGastosProyecto = idOtrosGastosProyecto;
  }

  public OtrosGastosProyectoVersion(Long idOtrosGastosProyecto, Long valor) {
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
  public String toString() {
    return "co.gov.policia.dinae.modelo.OtrosGastosProyecto[ idOtrosGastosProyecto=" + idOtrosGastosProyecto + " ]";
  }

  @Override
  public String getLlaveModel() {

    return String.valueOf(idOtrosGastosProyecto);

  }

}
