package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import javax.persistence.ParameterMode;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "RESUMEN_PRES_EJECUTA_IMPL")
@NamedQueries({
  @NamedQuery(name = "ResumenPresupuestoEjecutadoImpl.findByProyectoInformeAvance", query = "SELECT r FROM ResumenPresupuestoEjecutadoImpl r WHERE r.idImplementacionProy = :idImplementacionProy AND r.idInformeAvanceImpl = :idInformeAvanceImpl AND r.tipo = 'I' ORDER BY r.orden ASC"),
  @NamedQuery(name = "ResumenPresupuestoEjecutadoImpl.findByProyectoInformeAvanceAcum", query = "SELECT r FROM ResumenPresupuestoEjecutadoImpl r WHERE r.idImplementacionProy = :idImplementacionProy AND r.tipo = 'A' ORDER BY r.orden ASC")})

@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "ResumenPresupuestoEjecutadoImpl.calcularPresupuestoEjecutadoImpl", procedureName = "PKG_CALCULO_PRESUPUESTO_IMPL.PRC_CALCULAR_PRES_EJECUTA",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "P_ID_IMPLEMENTACION_PROY", queryParameter = "idImplementacionProy", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_INFORME_AVANCE_IMPL", queryParameter = "idInformeAvanceImpl", mode = ParameterMode.IN, type = Long.class)}),

  @NamedStoredProcedureQuery(name = "ResumenPresupuestoEjecutadoImpl.calcularPresupuestoEjecutadoAcumImpl", procedureName = "PKG_CALCULO_PRESUPUESTO_IMPL.PRC_CALCULAR_PRES_ACUM",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "P_ID_IMPLEMENTACION_PROY", queryParameter = "idImplementacionProy", mode = ParameterMode.IN, type = Long.class)})
})

public class ResumenPresupuestoEjecutadoImpl implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_RESUMEN_PRES_EJECUTA_IMPL")
  private Long idResumenPresEjecutaImpl;
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_IMPLEMENTACION_PROYECTO")
  private Long idImplementacionProy;
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_INFORME_AVANCE_IMPL")
  private Long idInformeAvanceImpl;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NOMBRE_RUBRO")
  private String nombreRubro;
  @Column(name = "POLICIA_NAL_EJECUTA")
  private Double policiaNalEjecuta;
  @Column(name = "VICIN_EJECUTA")
  private Double vicinEjecuta;
  @Column(name = "UNIDAD_EJECUTA")
  private Double unidadEjecuta;
  @Column(name = "FUENTE1_EJECUTA")
  private Double fuente1Ejecuta;
  @Column(name = "FUENTE2_EJECUTA")
  private Double fuente2Ejecuta;
  @Column(name = "FUENTE3_EJECUTA")
  private Double fuente3Ejecuta;
  @Column(name = "FUENTE4_EJECUTA")
  private Double fuente4Ejecuta;
  @Column(name = "POLICIA_NAL_PPTO")
  private Double policiaNalPpto;
  @Column(name = "VICIN_PPTO")
  private Double vicinPpto;
  @Column(name = "UNIDAD_PPTO")
  private Double unidadPpto;
  @Column(name = "FUENTE1_PPTO")
  private Double fuente1Ppto;
  @Column(name = "FUENTE2_PPTO")
  private Double fuente2Ppto;
  @Column(name = "FUENTE3_PPTO")
  private Double fuente3Ppto;
  @Column(name = "FUENTE4_PPTO")
  private Double fuente4Ppto;
  @Column(name = "ORDEN")
  private Short orden;
  @Column(name = "TIPO")
  private String tipo;

  public ResumenPresupuestoEjecutadoImpl() {
  }

  public ResumenPresupuestoEjecutadoImpl(Long idResumenPresEjecutaImpl) {
    this.idResumenPresEjecutaImpl = idResumenPresEjecutaImpl;
  }

  public ResumenPresupuestoEjecutadoImpl(Long idResumenPresEjecutaImpl, Long idImplementacionProy, Long idInformeAvanceImpl, String nombreRubro) {
    this.idResumenPresEjecutaImpl = idResumenPresEjecutaImpl;
    this.idImplementacionProy = idImplementacionProy;
    this.idInformeAvanceImpl = idInformeAvanceImpl;
    this.nombreRubro = nombreRubro;
  }

  public Long getIdResumenPresEjecutaImpl() {
    return idResumenPresEjecutaImpl;
  }

  public void setIdResumenPresEjecutaImpl(Long idResumenPresEjecutaImpl) {
    this.idResumenPresEjecutaImpl = idResumenPresEjecutaImpl;
  }

  public Long getIdImplementacionProy() {
    return idImplementacionProy;
  }

  public void setIdImplementacionProy(Long idImplementacionProy) {
    this.idImplementacionProy = idImplementacionProy;
  }

  public Long getIidInformeAvanceImpl() {
    return idInformeAvanceImpl;
  }

  public void setIdInformeAvanceImpl(Long idInformeAvanceImpl) {
    this.idInformeAvanceImpl = idInformeAvanceImpl;
  }

  public String getNombreRubro() {
    return nombreRubro;
  }

  public void setNombreRubro(String nombreRubro) {
    this.nombreRubro = nombreRubro;
  }

  public Double getPoliciaNalEjecuta() {
    return policiaNalEjecuta;
  }

  public void setPoliciaNalEjecuta(Double policiaNalEjecuta) {
    this.policiaNalEjecuta = policiaNalEjecuta;
  }

  public Double getVicinEjecuta() {
    return vicinEjecuta;
  }

  public void setVicinEjecuta(Double vicinEjecuta) {
    this.vicinEjecuta = vicinEjecuta;
  }

  public Double getUnidadEjecuta() {
    return unidadEjecuta;
  }

  public void setUnidadEjecuta(Double unidadEjecuta) {
    this.unidadEjecuta = unidadEjecuta;
  }

  public Double getFuente1Ejecuta() {
    return fuente1Ejecuta;
  }

  public void setFuente1Ejecuta(Double fuente1Ejecuta) {
    this.fuente1Ejecuta = fuente1Ejecuta;
  }

  public Double getFuente2Ejecuta() {
    return fuente2Ejecuta;
  }

  public void setFuente2Ejecuta(Double fuente2Ejecuta) {
    this.fuente2Ejecuta = fuente2Ejecuta;
  }

  public Double getFuente3Ejecuta() {
    return fuente3Ejecuta;
  }

  public void setFuente3Ejecuta(Double fuente3Ejecuta) {
    this.fuente3Ejecuta = fuente3Ejecuta;
  }

  public Double getFuente4Ejecuta() {
    return fuente4Ejecuta;
  }

  public void setFuente4Ejecuta(Double fuente4Ejecuta) {
    this.fuente4Ejecuta = fuente4Ejecuta;
  }

  public Short getOrden() {
    return orden;
  }

  public void setOrden(Short orden) {
    this.orden = orden;
  }

  public Double getPoliciaNalPpto() {
    return policiaNalPpto;
  }

  public void setPoliciaNalPpto(Double policiaNalPpto) {
    this.policiaNalPpto = policiaNalPpto;
  }

  public Double getVicinPpto() {
    return vicinPpto;
  }

  public void setVicinPpto(Double vicinPpto) {
    this.vicinPpto = vicinPpto;
  }

  public Double getUnidadPpto() {
    return unidadPpto;
  }

  public void setUnidadPpto(Double unidadPpto) {
    this.unidadPpto = unidadPpto;
  }

  public Double getFuente1Ppto() {
    return fuente1Ppto;
  }

  public void setFuente1Ppto(Double fuente1Ppto) {
    this.fuente1Ppto = fuente1Ppto;
  }

  public Double getFuente2Ppto() {
    return fuente2Ppto;
  }

  public void setFuente2Ppto(Double fuente2Ppto) {
    this.fuente2Ppto = fuente2Ppto;
  }

  public Double getFuente3Ppto() {
    return fuente3Ppto;
  }

  public void setFuente3Ppto(Double fuente3Ppto) {
    this.fuente3Ppto = fuente3Ppto;
  }

  public Double getFuente4Ppto() {
    return fuente4Ppto;
  }

  public void setFuente4Ppto(Double fuente4Ppto) {
    this.fuente4Ppto = fuente4Ppto;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idResumenPresEjecutaImpl != null ? idResumenPresEjecutaImpl.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ResumenPresupuestoEjecutadoImpl)) {
      return false;
    }
    ResumenPresupuestoEjecutadoImpl other = (ResumenPresupuestoEjecutadoImpl) object;
    if ((this.idResumenPresEjecutaImpl == null && other.idResumenPresEjecutaImpl != null) || (this.idResumenPresEjecutaImpl != null && !this.idResumenPresEjecutaImpl.equals(other.idResumenPresEjecutaImpl))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutado[ idResumenPresEjecuta=" + idResumenPresEjecutaImpl + " ]";
  }

}
