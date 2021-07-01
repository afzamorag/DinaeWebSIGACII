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
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "COMENTARIO")
@NamedQueries({
  @NamedQuery(name = "ComentarioNecesidad.findAll", query = "SELECT c FROM Comentario c"),
  @NamedQuery(name = "ComentarioNecesidad.findAllPorPropuestaNecesidad", query = "SELECT c FROM Comentario c WHERE c.propuestaNecesidad.idPropuestaNecesidad = :idPropuestaNecesidad ORDER BY c.fecha DESC"),
  @NamedQuery(name = "ComentarioProyecto.findAllPorProyecto", query = "SELECT c FROM Comentario c WHERE c.proyecto.idProyecto = :idProyecto ORDER BY c.fecha DESC")
})
public class Comentario implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_COMENTARIO_NECESIDAD")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Comentario_seq_gen")
  @SequenceGenerator(name = "Comentario_seq_gen", sequenceName = "SEC_COMENTARIO_NECESIDAD", allocationSize = 1)
  private Long idComentarioNecesidad;

  @Column(name = "FECHA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;

  @Column(name = "AUTOR")
  private String autor;

  @Column(name = "COMENTARIO")
  private String comentario;

  @JoinColumn(name = "ID_PROPUESTA_NECESIDAD", referencedColumnName = "ID_PROPUESTA_NECESIDAD")
  @ManyToOne(fetch = FetchType.LAZY)
  private PropuestaNecesidad propuestaNecesidad;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  public Comentario() {
  }

  public Comentario(Long idComentarioNecesidad) {
    this.idComentarioNecesidad = idComentarioNecesidad;
  }

  public Long getIdComentarioNecesidad() {
    return idComentarioNecesidad;
  }

  public void setIdComentarioNecesidad(Long idComentarioNecesidad) {
    this.idComentarioNecesidad = idComentarioNecesidad;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idComentarioNecesidad != null ? idComentarioNecesidad.hashCode() : 0);
    return hash;
  }

  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
    this.propuestaNecesidad = propuestaNecesidad;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Comentario)) {
      return false;
    }
    Comentario other = (Comentario) object;
    if ((this.idComentarioNecesidad == null && other.idComentarioNecesidad != null) || (this.idComentarioNecesidad != null && !this.idComentarioNecesidad.equals(other.idComentarioNecesidad))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ComentarioNecesidad[ idComentarioNecesidad=" + idComentarioNecesidad + " ]";
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

}
