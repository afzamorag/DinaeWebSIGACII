package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/10
 */
@Entity
@Table(name = "FORMACION_INVESTIGADOR")
@NamedQueries({
  @NamedQuery(name = "FormacionInvestigador.findAllByIdentificacionInvestigador", query = "SELECT f FROM FormacionInvestigador f WHERE f.idInvestigador.numeroIdentificacion = :identificacion")})
public class FormacionInvestigador implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FormacionInvestigador_seq_gen")
  @SequenceGenerator(name = "FormacionInvestigador_seq_gen", sequenceName = "SEC_FORMACION_INVESTIGADOR", allocationSize = 1)
  @Column(name = "ID_FORMACION_INV")
  private Long idFormacionInv;

  @NotNull
  @JoinColumn(name = "ID_NIVEL_FORMACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(cascade = CascadeType.MERGE)
  private Constantes nivelFormacion;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "TITULO_OBTENIDO")
  private String tituloObtenido;

  @Column(name = "FECHA_FINALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFinalizacion;

  @JoinColumn(name = "ID_INVESTIGADOR", referencedColumnName = "ID_INVESTIGADOR")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Investigador idInvestigador;

  @JoinColumn(name = "ID_AREA_SABER", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(cascade = CascadeType.MERGE)
  private Constantes areaSaber;

  public FormacionInvestigador() {
  }

  public FormacionInvestigador(Long idFormacionInv) {
    this.idFormacionInv = idFormacionInv;
  }

  public FormacionInvestigador(Long idFormacionInv, Constantes nivelFormacion, String tituloObtenido) {
    this.idFormacionInv = idFormacionInv;
    this.nivelFormacion = nivelFormacion;
    this.tituloObtenido = tituloObtenido;
  }

  public Long getIdFormacionInv() {
    return idFormacionInv;
  }

  public void setIdFormacionInv(Long idFormacionInv) {
    this.idFormacionInv = idFormacionInv;
  }

  public Constantes getNivelFormacion() {
    return nivelFormacion;
  }

  public void setNivelFormacion(Constantes nivelFormacion) {
    this.nivelFormacion = nivelFormacion;
  }

  public String getTituloObtenido() {
    return tituloObtenido;
  }

  public void setTituloObtenido(String tituloObtenido) {
    this.tituloObtenido = tituloObtenido;
  }

  public Date getFechaFinalizacion() {
    return fechaFinalizacion;
  }

  public void setFechaFinalizacion(Date fechaFinalizacion) {
    this.fechaFinalizacion = fechaFinalizacion;
  }

  public Investigador getIdInvestigador() {
    return idInvestigador;
  }

  public void setIdInvestigador(Investigador idInvestigador) {
    this.idInvestigador = idInvestigador;
  }

  public Constantes getAreaSaber() {
    return areaSaber;
  }

  public void setAreaSaber(Constantes idAreaSaber) {
    this.areaSaber = idAreaSaber;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idFormacionInv != null ? idFormacionInv.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FormacionInvestigador)) {
      return false;
    }
    FormacionInvestigador other = (FormacionInvestigador) object;
    if ((this.idFormacionInv == null && other.idFormacionInv != null) || (this.idFormacionInv != null && !this.idFormacionInv.equals(other.idFormacionInv))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.FormacionInvestigador[ idFormacionInv=" + idFormacionInv + " ]";
  }

}
