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
@Table(name = "UNIDAD_EJECUTORA")
@NamedQueries({
  @NamedQuery(name = "EjecutorNecesidad.findAll", query = "SELECT e FROM EjecutorNecesidad e"),
  @NamedQuery(name = "EjecutorNecesidad.findAllPorPropuestaNecesidad", query = "SELECT e FROM EjecutorNecesidad e WHERE e.propuestaNecesidad.idPropuestaNecesidad = :idEjecutorNecesidad"),
  @NamedQuery(name = "EjecutorNecesidadDTO.findAllPorPropuestaNecesidad", query = "SELECT NEW co.gov.policia.dinae.dto.EjecutorNecesidadDTO( e.idEjecutorNecesidad, e.unidadPolicial.idUnidadPolicial, e.unidadPolicial.nombre, e.rol.idConstantes, e.rol.valor ) FROM EjecutorNecesidad e WHERE e.propuestaNecesidad.idPropuestaNecesidad = :idEjecutorNecesidad"),
  @NamedQuery(name = "EjecutorNecesidadDTO.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.EjecutorNecesidadDTO( e.idEjecutorNecesidad, e.unidadPolicial.idUnidadPolicial, e.unidadPolicial.nombre, e.rol.idConstantes, e.rol.valor ) FROM EjecutorNecesidad e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EjecutorNecesidad.findAllPorProyecto", query = "SELECT e FROM EjecutorNecesidad e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EjecutorNecesidad.findAllPorPropuestaNecesidadYRolResponsable", query = "SELECT e FROM EjecutorNecesidad e WHERE e.propuestaNecesidad.idPropuestaNecesidad = :idEjecutorNecesidad AND e.rol.idConstantes = :idRol"),
  @NamedQuery(name = "EjecutorNecesidad.ContarByPeriodoYunidadPolicialYEstado", query = "SELECT COUNT(p.propuestaNecesidad) FROM EjecutorNecesidad p WHERE p.propuestaNecesidad.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.propuestaNecesidad.constantes.idConstantes = :idEstado AND p.rol.idConstantes = :idTipoRol"),})
public class EjecutorNecesidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EjecutorNecesidad_seq_gen")
  @SequenceGenerator(name = "EjecutorNecesidad_seq_gen", sequenceName = "SEC_EJECUTOR_NECESIDAD", allocationSize = 1)
  @Column(name = "ID_EJECUTOR_NECESIDAD")
  private Long idEjecutorNecesidad;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.EAGER)
  private UnidadPolicial unidadPolicial;

  @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes rol;

  @JoinColumn(name = "ID_PROPUESTA_NECESIDAD", referencedColumnName = "ID_PROPUESTA_NECESIDAD")
  @ManyToOne(fetch = FetchType.LAZY)
  private PropuestaNecesidad propuestaNecesidad;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  public EjecutorNecesidad() {
  }

  /**
   *
   * @param idEjecutorNecesidad
   * @param unidadPolicial
   * @param rol
   * @param propuestaNecesidad
   * @param proyecto
   */
  public EjecutorNecesidad(Long idEjecutorNecesidad, UnidadPolicial unidadPolicial, Constantes rol, PropuestaNecesidad propuestaNecesidad, Proyecto proyecto) {
    this.idEjecutorNecesidad = idEjecutorNecesidad;
    this.unidadPolicial = unidadPolicial;
    this.rol = rol;
    this.propuestaNecesidad = propuestaNecesidad;
    this.proyecto = proyecto;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public EjecutorNecesidad(Long idEjecutorNecesidad) {
    this.idEjecutorNecesidad = idEjecutorNecesidad;
  }

  public Long getIdEjecutorNecesidad() {
    return idEjecutorNecesidad;
  }

  public void setIdEjecutorNecesidad(Long idEjecutorNecesidad) {
    this.idEjecutorNecesidad = idEjecutorNecesidad;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEjecutorNecesidad != null ? idEjecutorNecesidad.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EjecutorNecesidad)) {
      return false;
    }
    EjecutorNecesidad other = (EjecutorNecesidad) object;
    if ((this.idEjecutorNecesidad == null && other.idEjecutorNecesidad != null) || (this.idEjecutorNecesidad != null && !this.idEjecutorNecesidad.equals(other.idEjecutorNecesidad))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EjecutorNecesidad[ idEjecutorNecesidad=" + idEjecutorNecesidad + " ]";
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public Constantes getRol() {
    return rol;
  }

  public void setRol(Constantes rol) {
    this.rol = rol;
  }

  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
    this.propuestaNecesidad = propuestaNecesidad;
  }
}
