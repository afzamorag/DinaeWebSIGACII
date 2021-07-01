package co.gov.policia.dinae.modelo;

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

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "EJECUCION_OTROS_GASTOS_PROY")
@NamedQueries({
  @NamedQuery(name = "EjecucionOtrosGastosProy.findAll", query = "SELECT e FROM EjecucionOtrosGastosProy e"),
  @NamedQuery(name = "EjecucionOtrosGastosProy.findOtrosGastosInforme", query = "SELECT e FROM EjecucionOtrosGastosProy e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionOtrosGastosProy.findOtrosGastosInformeImplementacion", query = "SELECT e FROM EjecucionOtrosGastosProy e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "EjecucionOtrosGastosProy.findOtrosGastosInformeProyecto", query = "SELECT e FROM EjecucionOtrosGastosProy e WHERE e.informeAvance.idInformeAvance = :idInformeAvance AND e.otrosGastosProyecto.idOtrosGastosProyecto = :idOtrosGastosProyecto ORDER BY e.otrosGastosProyecto.idTipoRubro ASC "),
  @NamedQuery(name = "EjecucionOtrosGastosProy.findOtrosGastosInformeImplementacionProyecto", query = "SELECT e FROM EjecucionOtrosGastosProy e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND e.otrosGastosProyecto.idOtrosGastosProyecto = :idOtrosGastosProyecto ORDER BY e.otrosGastosProyecto.idTipoRubro ASC "),
  @NamedQuery(name = "EjecucionOtrosGastosProy.deleteOtrosGastosInformeProyecto", query = "DELETE FROM EjecucionOtrosGastosProy e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionOtrosGastosProy.SelectOtrosGastosInformeProyecto", query = "SELECT e FROM EjecucionOtrosGastosProy e WHERE e.informeAvance.idInformeAvance = :idInformeAvance"),
  @NamedQuery(name = "EjecucionOtrosGastosProy.deleteOtrosGastosInformeImplementacionProyecto", query = "DELETE FROM EjecucionOtrosGastosProy e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion"),
  @NamedQuery(name = "EjecucionOtrosGastosProy.SelectOtrosGastosInformeImplementacionProyecto", query = "SELECT e FROM EjecucionOtrosGastosProy e WHERE e.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")

})
public class EjecucionOtrosGastosProy implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EjecucionOtrosGastosProy_seq_gen")
  @SequenceGenerator(name = "EjecucionOtrosGastosProy_seq_gen", sequenceName = "SEC_EJECUCION_OTROS_GAST_PROY", allocationSize = 1)
  @Column(name = "ID_EJECUCION_GASTOS_PROY")
  private Long idEjecucionGastosProy;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO_REGISTRA")
  private String usuarioRegistra;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "VALOR_GASTADO_PERIODO")
  private Long valorGastadoPeriodo;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_INFORME_AVANCE", referencedColumnName = "ID_INFORME_AVANCE")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private InformeAvance informeAvance;

  @JoinColumn(name = "ID_OTROS_GASTOS_PROYECTO", referencedColumnName = "ID_OTROS_GASTOS_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private OtrosGastosProyecto otrosGastosProyecto;

  @JoinColumn(name = "ID_INFORME_IMPLEMENTACION", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  public EjecucionOtrosGastosProy() {
  }

  public EjecucionOtrosGastosProy(Long idEjecucionGastosProy) {
    this.idEjecucionGastosProy = idEjecucionGastosProy;
  }

  public EjecucionOtrosGastosProy(Long idEjecucionGastosProy, OtrosGastosProyecto otrosGastosProyecto) {
    this.idEjecucionGastosProy = idEjecucionGastosProy;
    this.otrosGastosProyecto = otrosGastosProyecto;
  }

  public Long getIdEjecucionGastosProy() {
    return idEjecucionGastosProy;
  }

  public void setIdEjecucionGastosProy(Long idEjecucionGastosProy) {
    this.idEjecucionGastosProy = idEjecucionGastosProy;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public Long getValorGastadoPeriodo() {
    return valorGastadoPeriodo;
  }

  public void setValorGastadoPeriodo(Long valorGastadoPeriodo) {
    this.valorGastadoPeriodo = valorGastadoPeriodo;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public InformeAvance getInformeAvance() {
    return informeAvance;
  }

  public void setInformeAvance(InformeAvance informeAvance) {
    this.informeAvance = informeAvance;
  }

  public OtrosGastosProyecto getOtrosGastosProyecto() {
    return otrosGastosProyecto;
  }

  public void setOtrosGastosProyecto(OtrosGastosProyecto otrosGastosProyecto) {
    this.otrosGastosProyecto = otrosGastosProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEjecucionGastosProy != null ? idEjecucionGastosProy.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EjecucionOtrosGastosProy)) {
      return false;
    }
    EjecucionOtrosGastosProy other = (EjecucionOtrosGastosProy) object;
    if ((this.idEjecucionGastosProy == null && other.idEjecucionGastosProy != null) || (this.idEjecucionGastosProy != null && !this.idEjecucionGastosProy.equals(other.idEjecucionGastosProy))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EjecucionOtrosGastosProy[ idEjecucionGastosProy=" + idEjecucionGastosProy + " ]";
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

  @Column(name = "CORRECION")
  private Character correccion;

  public Character getCorreccion() {
    return correccion;
  }

  public void setCorreccion(Character correccion) {
    this.correccion = correccion;
  }

}
