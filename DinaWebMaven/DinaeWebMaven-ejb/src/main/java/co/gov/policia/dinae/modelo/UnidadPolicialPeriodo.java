package co.gov.policia.dinae.modelo;

import java.io.Serializable;
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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "UNIDAD_POLICIAL_PERIODO")
@NamedQueries({
  @NamedQuery(name = "UnidadPolicialPeriodo.findAll", query = "SELECT u FROM UnidadPolicialPeriodo u"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllCorreoUnidadPolicialPorPeriodoEisNotNullCorreo", query = "SELECT u.unidadPolicial.mail FROM UnidadPolicialPeriodo u WHERE u.periodo.idPeriodo = :idPeriodo AND u.unidadPolicial.mail IS NOT NULL"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllIdsUnidadPolicialPeriodo", query = "SELECT u.unidadPolicial.idUnidadPolicial FROM UnidadPolicialPeriodo u WHERE u.unidadPolicial.tipoUnidad.idTipoUnidad IN (SELECT tu.tipoUnidad.idTipoUnidad FROM TipoUnidadPeriodo tu WHERE tu.periodo.idPeriodo = :idPeriodo ) AND u.unidadPolicial.activo = 'Y' AND u.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllPeriodoByFechaActualEntreFechaInicioYfechaFinYestado", query = "SELECT p.periodo FROM UnidadPolicialPeriodo p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND :fechaActual BETWEEN p.periodo.fechaInicio AND p.periodo.fechaFin AND p.periodo.estado = :estadoPeriodo AND p.periodo.tipoRegistro = 'NECESIDAD' ORDER BY p.periodo.idPeriodo DESC"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllConvocatoriasByFechaActualEntreFechaInicioYfechaFinYestado", query = "SELECT p.periodo FROM UnidadPolicialPeriodo p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND :fechaActual BETWEEN p.periodo.fechaInicio AND p.periodo.fechaFin AND p.periodo.estado = :estadoPeriodo AND p.periodo.tipoRegistro = 'CONVOCATORIA' ORDER BY p.periodo.idPeriodo DESC"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoria", query = "SELECT p.periodo FROM UnidadPolicialPeriodo p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND :fechaActual BETWEEN p.periodo.fechaInicio AND p.periodo.fechaFin AND p.periodo.estadoConvocatorio.idConstantes = :idEstadoConvocatoria ORDER BY p.periodo.idPeriodo DESC"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria", query = "SELECT p.periodo FROM UnidadPolicialPeriodo p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND :fechaActual BETWEEN p.periodo.fechaInicio AND p.periodo.fechaFin AND p.periodo.estadoConvocatorio.idConstantes = :idEstadoConvocatoria AND p.periodo.tipoPeriodo.idConstantes = :idTipoPeriodo ORDER BY p.periodo.idPeriodo DESC"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllPeriodosUnidadPolicialYestadoConvocatoriaYtipoConvocatoria", query = "SELECT p.periodo FROM UnidadPolicialPeriodo p WHERE p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.periodo.estadoConvocatorio.idConstantes = :idEstadoConvocatoria AND p.periodo.tipoPeriodo.idConstantes = :idTipoPeriodo ORDER BY p.periodo.idPeriodo DESC"),
  @NamedQuery(name = "UnidadPolicialPeriodo.findAllUnidadesNoHanPresentadoUnidadesByEstado", query = "SELECT DISTINCT u.unidadPolicial FROM UnidadPolicialPeriodo u WHERE u.unidadPolicial.tipoUnidad.idTipoUnidad IN (SELECT tu.tipoUnidad.idTipoUnidad FROM TipoUnidadPeriodo tu WHERE tu.periodo.idPeriodo = :idPeriodo ) AND u.unidadPolicial.activo = 'Y' AND u.unidadPolicial.idUnidadPolicial NOT IN (SELECT DISTINCT p.unidadPolicial.idUnidadPolicial FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND ( p.constantes.idConstantes = :idEstado1 OR p.constantes.idConstantes = :idEstado2 OR p.constantes.idConstantes = :idEstado3 )) ORDER BY u.unidadPolicial.nombre ASC ")
})
public class UnidadPolicialPeriodo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UnidadPolicialPeriodo_seq_gen")
  @SequenceGenerator(name = "UnidadPolicialPeriodo_seq_gen", sequenceName = "SEC_UNIDAD_POLICIAL_PERIODO", allocationSize = 1)
  @Column(name = "ID_UNIDAD_POLICIAL_PERIODO")
  private Long idUnidadPolicialPeriodo;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @JoinColumn(name = "ID_PERIODO", referencedColumnName = "ID_PERIODO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Periodo periodo;

  public UnidadPolicialPeriodo() {
  }

  public UnidadPolicialPeriodo(Long idUnidadPolicialPeriodo) {
    this.idUnidadPolicialPeriodo = idUnidadPolicialPeriodo;
  }

  public UnidadPolicialPeriodo(UnidadPolicial unidadPolicial, Periodo periodo) {

    this.unidadPolicial = unidadPolicial;
    this.periodo = periodo;
  }

  public Long getIdUnidadPolicialPeriodo() {
    return idUnidadPolicialPeriodo;
  }

  public void setIdUnidadPolicialPeriodo(Long idUnidadPolicialPeriodo) {
    this.idUnidadPolicialPeriodo = idUnidadPolicialPeriodo;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idUnidadPolicialPeriodo != null ? idUnidadPolicialPeriodo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UnidadPolicialPeriodo)) {
      return false;
    }
    UnidadPolicialPeriodo other = (UnidadPolicialPeriodo) object;
    if ((this.idUnidadPolicialPeriodo == null && other.idUnidadPolicialPeriodo != null) || (this.idUnidadPolicialPeriodo != null && !this.idUnidadPolicialPeriodo.equals(other.idUnidadPolicialPeriodo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.UnidadPolicialPeriodo[ idUnidadPolicialPeriodo=" + idUnidadPolicialPeriodo + " ]";
  }

}
