package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "RESUMEN_PRESUPUESTO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "ResumenPresupuestoProyecto.findAll", query = "SELECT r FROM ResumenPresupuestoProyecto r"),
  @NamedQuery(name = "ResumenPresupuestoProyecto.findByProyecto", query = "SELECT r FROM ResumenPresupuestoProyecto r WHERE r.idProyecto = :idProyecto AND r.estado = 'TEMPORAL' ORDER BY r.orden ASC"),
  @NamedQuery(name = "ResumenPresupuestoProyecto.findByProyectoEstadoPresepuesto", query = "SELECT r FROM ResumenPresupuestoProyecto r WHERE r.idProyecto = :idProyecto AND r.estado = :estadoPresupuesto ORDER BY r.orden ASC"),
  @NamedQuery(name = "ResumenPresupuestoProyecto.removeAllByProyecto", query = "DELETE FROM ResumenPresupuestoProyecto r WHERE r.idProyecto = :idProyecto AND r.estado = 'TEMPORAL'")
})

@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "ResumenPresupuestoProyecto.calcularPresupuesto", procedureName = "PKG_CALCULO_PRESUPUESTO.PRC_CREAR_ACTUALIZA_PRES",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "p_id_proyecto", queryParameter = "idProyecto", mode = ParameterMode.IN, type = Long.class)
          }
  ),

  @NamedStoredProcedureQuery(name = "ResumenPresupuestoImpl.Crear_Version_PresupuestoCumplido", procedureName = "CREA_V_PRESUPUES_PRO_CUMPLIDO",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "P_ID_PROYECTO", queryParameter = "idProyecto", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_COMPROMISO_PROYECTO", queryParameter = "idCompromisoProyecto", mode = ParameterMode.IN, type = Long.class)
          }
  )
})

public class ResumenPresupuestoProyecto implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_RESUMEN_PRES_PROY")
  private BigDecimal idResumenPresProy;
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_PROYECTO")
  private Long idProyecto;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NOMBRE_RUBRO")
  private String nombreRubro;
  @Column(name = "POLICIA_NACIONAL_ESPECIE")
  private BigDecimal policiaNacionalEspecie;
  @Column(name = "POLICIA_NACIONAL_EFECTIVO")
  private BigDecimal policiaNacionalEfectivo;
  @Column(name = "VICIN_ESPECIE")
  private BigDecimal vicinEspecie;
  @Column(name = "VICIN_EFECTIVO")
  private BigDecimal vicinEfectivo;
  @Column(name = "UNIDAD_ESPECIE")
  private BigDecimal unidadEspecie;
  @Column(name = "UNIDAD_EFECTIVO")
  private BigDecimal unidadEfectivo;
  @Column(name = "FUENTE1_ESPECIE")
  private BigDecimal fuente1Especie;
  @Column(name = "FUENTE1_EFECTIVO")
  private BigDecimal fuente1Efectivo;
  @Column(name = "FUENTE2_ESPECIE")
  private BigDecimal fuente2Especie;
  @Column(name = "FUENTE2_EFECTIVO")
  private Long fuente2Efectivo;
  @Column(name = "FUENTE3_ESPECIE")
  private Long fuente3Especie;
  @Column(name = "FUENTE3_EFECTIVO")
  private Long fuente3Efectivo;
  @Column(name = "FUENTE4_ESPECIE")
  private Long fuente4Especie;
  @Column(name = "FUENTE4_EFECTIVO")
  private Long fuente4Efectivo;
  @Column(name = "ES_FUENTE1_INTERNA")
  private String esFuente1Interna;
  @Column(name = "ES_FUENTE2_INTERNA")
  private String esFuente2Interna;
  @Column(name = "ES_FUENTE3_INTERNA")
  private String esFuente3Interna;
  @Column(name = "ES_FUENTE4_INTERNA")
  private String esFuente4Interna;
  @Column(name = "ORDEN")
  private Short orden;
  @Column(name = "ESTADO")
  private String estado;

  public ResumenPresupuestoProyecto() {
  }

  public ResumenPresupuestoProyecto(BigDecimal idResumenPresProy) {
    this.idResumenPresProy = idResumenPresProy;
  }

  public ResumenPresupuestoProyecto(BigDecimal idResumenPresProy, Long idProyecto, String nombreRubro) {
    this.idResumenPresProy = idResumenPresProy;
    this.idProyecto = idProyecto;
    this.nombreRubro = nombreRubro;
  }

  public BigDecimal getIdResumenPresProy() {
    return idResumenPresProy;
  }

  public void setIdResumenPresProy(BigDecimal idResumenPresProy) {
    this.idResumenPresProy = idResumenPresProy;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getNombreRubro() {
    return nombreRubro;
  }

  public void setNombreRubro(String nombreRubro) {
    this.nombreRubro = nombreRubro;
  }

  public BigDecimal getPoliciaNacionalEspecie() {
    return policiaNacionalEspecie;
  }

  public void setPoliciaNacionalEspecie(BigDecimal policiaNacionalEspecie) {
    this.policiaNacionalEspecie = policiaNacionalEspecie;
  }

  public BigDecimal getPoliciaNacionalEfectivo() {
    return policiaNacionalEfectivo;
  }

  public void setPoliciaNacionalEfectivo(BigDecimal policiaNacionalEfectivo) {
    this.policiaNacionalEfectivo = policiaNacionalEfectivo;
  }

  public BigDecimal getVicinEspecie() {
    return vicinEspecie;
  }

  public void setVicinEspecie(BigDecimal vicinEspecie) {
    this.vicinEspecie = vicinEspecie;
  }

  public BigDecimal getVicinEfectivo() {
    return vicinEfectivo;
  }

  public void setVicinEfectivo(BigDecimal vicinEfectivo) {
    this.vicinEfectivo = vicinEfectivo;
  }

  public BigDecimal getUnidadEspecie() {
    return unidadEspecie;
  }

  public void setUnidadEspecie(BigDecimal unidadEspecie) {
    this.unidadEspecie = unidadEspecie;
  }

  public BigDecimal getUnidadEfectivo() {
    return unidadEfectivo;
  }

  public void setUnidadEfectivo(BigDecimal unidadEfectivo) {
    this.unidadEfectivo = unidadEfectivo;
  }

  public BigDecimal getFuente1Especie() {
    return fuente1Especie;
  }

  public void setFuente1Especie(BigDecimal fuente1Especie) {
    this.fuente1Especie = fuente1Especie;
  }

  public BigDecimal getFuente1Efectivo() {
    return fuente1Efectivo;
  }

  public void setFuente1Efectivo(BigDecimal fuente1Efectivo) {
    this.fuente1Efectivo = fuente1Efectivo;
  }

  public BigDecimal getFuente2Especie() {
    return fuente2Especie;
  }

  public void setFuente2Especie(BigDecimal fuente2Especie) {
    this.fuente2Especie = fuente2Especie;
  }

  public Long getFuente2Efectivo() {
    return fuente2Efectivo;
  }

  public void setFuente2Efectivo(Long fuente2Efectivo) {
    this.fuente2Efectivo = fuente2Efectivo;
  }

  public Long getFuente3Especie() {
    return fuente3Especie;
  }

  public void setFuente3Especie(Long fuente3Especie) {
    this.fuente3Especie = fuente3Especie;
  }

  public Long getFuente3Efectivo() {
    return fuente3Efectivo;
  }

  public void setFuente3Efectivo(Long fuente3Efectivo) {
    this.fuente3Efectivo = fuente3Efectivo;
  }

  public Long getFuente4Especie() {
    return fuente4Especie;
  }

  public void setFuente4Especie(Long fuente4Especie) {
    this.fuente4Especie = fuente4Especie;
  }

  public Long getFuente4Efectivo() {
    return fuente4Efectivo;
  }

  public void setFuente4Efectivo(Long fuente4Efectivo) {
    this.fuente4Efectivo = fuente4Efectivo;
  }

  public String getEsFuente1Interna() {
    return esFuente1Interna;
  }

  public void setEsFuente1Interna(String esFuente1Interna) {
    this.esFuente1Interna = esFuente1Interna;
  }

  public String getEsFuente2Interna() {
    return esFuente2Interna;
  }

  public void setEsFuente2Interna(String esFuente2Interna) {
    this.esFuente2Interna = esFuente2Interna;
  }

  public String getEsFuente3Interna() {
    return esFuente3Interna;
  }

  public void setEsFuente3Interna(String esFuente3Interna) {
    this.esFuente3Interna = esFuente3Interna;
  }

  public String getEsFuente4Interna() {
    return esFuente4Interna;
  }

  public void setEsFuente4Interna(String esFuente4Interna) {
    this.esFuente4Interna = esFuente4Interna;
  }

  public Short getOrden() {
    return orden;
  }

  public void setOrden(Short orden) {
    this.orden = orden;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idResumenPresProy != null ? idResumenPresProy.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ResumenPresupuestoProyecto)) {
      return false;
    }
    ResumenPresupuestoProyecto other = (ResumenPresupuestoProyecto) object;
    if ((this.idResumenPresProy == null && other.idResumenPresProy != null) || (this.idResumenPresProy != null && !this.idResumenPresProy.equals(other.idResumenPresProy))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ResumenPresupuestoProyecto[ idResumenPresProy=" + idResumenPresProy + " ]";
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}
