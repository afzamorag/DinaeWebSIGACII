package co.gov.policia.dinae.modelo;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "HORAS_INVESTIGADOR")
@NamedQueries({
  @NamedQuery(name = "HorasInvestigador.findAll", query = "SELECT h FROM HorasInvestigador h"),
  @NamedQuery(name = "HorasInvestigador.SUMAhorasInvestigadorPorInformeCompromisoProyecto", query = "SELECT SUM( h.horasInvestigacionTrabajadasPeriodo ) FROM HorasInvestigador h WHERE h.compromisoProyecto.idCompromisoProyecto IN :ID_LISTA_COMPROMISO AND h.horasInvestigacionTrabajadasPeriodo IS NOT NULL"),
  @NamedQuery(name = "HorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYinvestigadorProyecto", query = "SELECT h FROM HorasInvestigador h WHERE h.proyecto.idProyecto = :idProyecto AND h.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND h.investigadorProyecto.idInvestigadorProyecto = :idInvestigadorProyecto"),
  @NamedQuery(name = "HorasInvestigador.findHorasInvestigacionPorCompromisoProyectoYProyecto", query = "SELECT h FROM HorasInvestigador h WHERE h.proyecto.idProyecto = :idProyecto AND h.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto")
})
public class HorasInvestigador implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HorasInvestigador_seq_gen")
  @SequenceGenerator(name = "HorasInvestigador_seq_gen", sequenceName = "SEC_HORAS_INVESTIGADOR", allocationSize = 1)
  @Column(name = "ID_HORAS_INVESTIGADOR")
  private Long idHorasInvestigador;

  @NotNull
  @Column(name = "HORAS_INVESTIGA_TRAB_PERI")
  private Integer horasInvestigacionTrabajadasPeriodo;

  @JoinColumn(name = "ID_INVESTIGADOR_PROYECTO", referencedColumnName = "ID_INVESTIGADOR_PROYECTO")
  @ManyToOne
  private InvestigadorProyecto investigadorProyecto;

  @JoinColumn(name = "ID_COMPROMISO_PROYECTO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne
  private CompromisoProyecto compromisoProyecto;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  public HorasInvestigador() {
  }

  public Long getIdHorasInvestigador() {
    return idHorasInvestigador;
  }

  public void setIdHorasInvestigador(Long idHorasInvestigador) {
    this.idHorasInvestigador = idHorasInvestigador;
  }

  public Integer getHorasInvestigacionTrabajadasPeriodo() {
    return horasInvestigacionTrabajadasPeriodo;
  }

  public void setHorasInvestigacionTrabajadasPeriodo(Integer horasInvestigacionTrabajadasPeriodo) {
    this.horasInvestigacionTrabajadasPeriodo = horasInvestigacionTrabajadasPeriodo;
  }

  public InvestigadorProyecto getInvestigadorProyecto() {
    return investigadorProyecto;
  }

  public void setInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
    this.investigadorProyecto = investigadorProyecto;
  }

  public CompromisoProyecto getCompromisoProyecto() {
    return compromisoProyecto;
  }

  public void setCompromisoProyecto(CompromisoProyecto compromisoProyecto) {
    this.compromisoProyecto = compromisoProyecto;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idHorasInvestigador != null ? idHorasInvestigador.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof HorasInvestigador)) {
      return false;
    }
    HorasInvestigador other = (HorasInvestigador) object;
    if ((this.idHorasInvestigador == null && other.idHorasInvestigador != null) || (this.idHorasInvestigador != null && !this.idHorasInvestigador.equals(other.idHorasInvestigador))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.HorasInvestigador[ idHorasInvestigador=" + idHorasInvestigador + " ]";
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
