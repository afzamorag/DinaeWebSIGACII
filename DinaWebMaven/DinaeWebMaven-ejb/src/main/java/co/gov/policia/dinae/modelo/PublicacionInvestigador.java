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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Édder Peña Barranco
 * @since 2014/01/08
 */
@Entity
@Table(name = "PUBLICACION_INVESTIGADOR")
@NamedQueries({
  @NamedQuery(name = "PublicacionInvestigador.findAll", query = "SELECT p FROM PublicacionInvestigador p"),
  @NamedQuery(name = "PublicacionInvestigador.findByIdPublicacionInvestigador", query = "SELECT p FROM PublicacionInvestigador p WHERE p.idPublicacionInvestigador = :idPublicacionInvestigador"),
  @NamedQuery(name = "PublicacionInvestigador.findByIdTipoPublicacion", query = "SELECT p FROM PublicacionInvestigador p WHERE p.tipoPublicacion.idConstantes = :idTipoPublicacion"),
  @NamedQuery(name = "PublicacionInvestigador.findByTituloPublicacion", query = "SELECT p FROM PublicacionInvestigador p WHERE p.tituloPublicacion = :tituloPublicacion"),
  @NamedQuery(name = "PublicacionInvestigador.findByFechaPublicacion", query = "SELECT p FROM PublicacionInvestigador p WHERE p.fechaPublicacion = :fechaPublicacion"),
  @NamedQuery(name = "PublicacionInvestigador.findByCodigoPaisPublicacion", query = "SELECT p FROM PublicacionInvestigador p WHERE p.codigoPaisPublicacion = :codigoPaisPublicacion"),
  @NamedQuery(name = "PublicacionInvestigador.findByCodigoIssn", query = "SELECT p FROM PublicacionInvestigador p WHERE p.codigoIssn = :codigoIssn"),
  @NamedQuery(name = "PublicacionInvestigador.findByNombreRevista", query = "SELECT p FROM PublicacionInvestigador p WHERE p.nombreRevista = :nombreRevista"),
  @NamedQuery(name = "PublicacionInvestigador.findByCodigoIsbn", query = "SELECT p FROM PublicacionInvestigador p WHERE p.codigoIsbn = :codigoIsbn"),
  @NamedQuery(name = "PublicacionInvestigador.findByNombreLibro", query = "SELECT p FROM PublicacionInvestigador p WHERE p.nombreLibro = :nombreLibro"),
  @NamedQuery(name = "PublicacionInvestigador.findByEditorialLibro", query = "SELECT p FROM PublicacionInvestigador p WHERE p.editorialLibro = :editorialLibro"),
  @NamedQuery(name = "PublicacionInvestigador.findAllByIdentificacionInvestigador", query = "SELECT p FROM PublicacionInvestigador p WHERE p.investigador.numeroIdentificacion = :identificacion"),
  @NamedQuery(name = "PublicacionInvestigador.findAllByIdentificacionVistaFuncionario", query = "SELECT p FROM PublicacionInvestigador p WHERE p.identificacionSiat = :identificacionSiat")
})
public class PublicacionInvestigador implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PublicacionInvestigador_seq_gen")
  @SequenceGenerator(name = "PublicacionInvestigador_seq_gen", sequenceName = "SEC_PUBLICACION_INVESTIGADOR", allocationSize = 1)
  @Column(name = "ID_PUBLICACION_INVESTIGADOR")
  private Long idPublicacionInvestigador;

  @JoinColumn(name = "ID_TIPO_PUBLICACION", referencedColumnName = "ID_CONSTANTES")
  @OneToOne(optional = false)
  private Constantes tipoPublicacion;

  @Column(name = "TITULO_PUBLICACION")
  private String tituloPublicacion;

  @Column(name = "RESUMEN_PUBLICACION")
  private String resumenPublicacion;

  @Column(name = "FECHA_PUBLICACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaPublicacion;

  @Column(name = "CODIGO_PAIS_PUBLICACION")
  private String codigoPaisPublicacion;

  @Column(name = "NOMBRE_PAIS_PUBLICACION")
  private String nombrePaisPublicacion;

  @Column(name = "CODIGO_ISSN")
  private String codigoIssn;

  @Column(name = "NOMBRE_REVISTA")
  private String nombreRevista;

  @Column(name = "VOLUMEN_REVISTA")
  private String volumenRevista;

  @Column(name = "FASCICULO_REVISTA")
  private String fasciculoRevista;

  @Column(name = "SERIE_REVISTA")
  private String serieRevista;

  @Column(name = "PAGINA_INICIO_REVISTA")
  private Integer paginaInicioRevista;

  @Column(name = "CODIGO_ISBN")
  private String codigoIsbn;

  @Column(name = "NOMBRE_LIBRO")
  private String nombreLibro;

  @Column(name = "EDITORIAL_LIBRO")
  private String editorialLibro;

  @Column(name = "PAGINA_INICIO_LIBRO")
  private Integer paginaInicioLibro;

  @JoinColumn(name = "ID_INVESTIGADOR", referencedColumnName = "ID_INVESTIGADOR")
  @ManyToOne(fetch = FetchType.LAZY)
  private Investigador investigador;

  @Column(name = "IDENTIFICACION_SIAT")
  private String identificacionSiat;

  public PublicacionInvestigador() {
  }

  public PublicacionInvestigador(Long idPublicacionInvestigador) {
    this.idPublicacionInvestigador = idPublicacionInvestigador;
  }

  public PublicacionInvestigador(Long idPublicacionInvestigador, Constantes tipoPublicacion, String tituloPublicacion, Date fechaPublicacion) {
    this.idPublicacionInvestigador = idPublicacionInvestigador;
    this.tipoPublicacion = tipoPublicacion;
    this.tituloPublicacion = tituloPublicacion;
    this.fechaPublicacion = fechaPublicacion;
  }

  public Long getIdPublicacionInvestigador() {
    return idPublicacionInvestigador;
  }

  public void setIdPublicacionInvestigador(Long idPublicacionInvestigador) {
    this.idPublicacionInvestigador = idPublicacionInvestigador;
  }

  public Constantes getTipoPublicacion() {
    return tipoPublicacion;
  }

  public void setTipoPublicacion(Constantes tipoPublicacion) {
    this.tipoPublicacion = tipoPublicacion;
  }

  public String getTituloPublicacion() {
    return tituloPublicacion;
  }

  public void setTituloPublicacion(String tituloPublicacion) {
    this.tituloPublicacion = tituloPublicacion;
  }

  public String getResumenPublicacion() {
    return resumenPublicacion;
  }

  public void setResumenPublicacion(String resumenPublicacion) {
    this.resumenPublicacion = resumenPublicacion;
  }

  public Date getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(Date fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public String getCodigoPaisPublicacion() {
    return codigoPaisPublicacion;
  }

  public void setCodigoPaisPublicacion(String codigoPaisPublicacion) {
    this.codigoPaisPublicacion = codigoPaisPublicacion;
  }

  public String getNombrePaisPublicacion() {
    return nombrePaisPublicacion;
  }

  public void setNombrePaisPublicacion(String nombrePaisPublicacion) {
    this.nombrePaisPublicacion = nombrePaisPublicacion;
  }

  public String getCodigoIssn() {
    return codigoIssn;
  }

  public void setCodigoIssn(String codigoIssn) {
    this.codigoIssn = codigoIssn;
  }

  public String getNombreRevista() {
    return nombreRevista;
  }

  public void setNombreRevista(String nombreRevista) {
    this.nombreRevista = nombreRevista;
  }

  public String getVolumenRevista() {
    return volumenRevista;
  }

  public void setVolumenRevista(String volumenRevista) {
    this.volumenRevista = volumenRevista;
  }

  public String getFasciculoRevista() {
    return fasciculoRevista;
  }

  public void setFasciculoRevista(String fasciculoRevista) {
    this.fasciculoRevista = fasciculoRevista;
  }

  public String getSerieRevista() {
    return serieRevista;
  }

  public void setSerieRevista(String serieRevista) {
    this.serieRevista = serieRevista;
  }

  public Integer getPaginaInicioRevista() {
    return paginaInicioRevista;
  }

  public void setPaginaInicioRevista(Integer paginaInicioRevista) {
    this.paginaInicioRevista = paginaInicioRevista;
  }

  public String getCodigoIsbn() {
    return codigoIsbn;
  }

  public void setCodigoIsbn(String codigoIsbn) {
    this.codigoIsbn = codigoIsbn;
  }

  public String getNombreLibro() {
    return nombreLibro;
  }

  public void setNombreLibro(String nombreLibro) {
    this.nombreLibro = nombreLibro;
  }

  public String getEditorialLibro() {
    return editorialLibro;
  }

  public void setEditorialLibro(String editorialLibro) {
    this.editorialLibro = editorialLibro;
  }

  public Integer getPaginaInicioLibro() {
    return paginaInicioLibro;
  }

  public void setPaginaInicioLibro(Integer paginaInicioLibro) {
    this.paginaInicioLibro = paginaInicioLibro;
  }

  public Investigador getInvestigador() {
    return investigador;
  }

  public void setInvestigador(Investigador idInvestigador) {
    this.investigador = idInvestigador;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPublicacionInvestigador != null ? idPublicacionInvestigador.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof PublicacionInvestigador)) {
      return false;
    }
    PublicacionInvestigador other = (PublicacionInvestigador) object;
    if ((this.idPublicacionInvestigador == null && other.idPublicacionInvestigador != null) || (this.idPublicacionInvestigador != null && !this.idPublicacionInvestigador.equals(other.idPublicacionInvestigador))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.PublicacionInvestigador[ idPublicacionInvestigador=" + idPublicacionInvestigador + " ]";
  }

  public String getIdentificacionSiat() {
    return identificacionSiat;
  }

  public void setIdentificacionSiat(String identificacionSiat) {
    this.identificacionSiat = identificacionSiat;
  }

}
