package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INDICADORES_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "IndicadoresProyecto.findAll", query = "SELECT i FROM IndicadoresProyecto i"),
  @NamedQuery(name = "IndicadoresProyecto.EliminarIndicadorPorId", query = "DELETE FROM IndicadoresProyecto i WHERE i.idIndicadorProyecto = :idIndicadorProyecto"),
  @NamedQuery(name = "IndicadoresProyecto.findProyectoEindicadorBaseYcasoUso", query = "SELECT i FROM IndicadoresProyecto i WHERE i.proyecto.idProyecto = :idProyecto AND i.indicadorBase = :indicadorBase AND i.casoUso = :casoUso")
})
public class IndicadoresProyecto implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IndicadoresProyecto_seq_gen")
  @SequenceGenerator(name = "IndicadoresProyecto_seq_gen", sequenceName = "SEC_INDICADORES_PROYECTO", allocationSize = 1)
  @Column(name = "ID_INDICADOR_PROYECTO")
  private Long idIndicadorProyecto;

  @Column(name = "NOMBRE_INDICADOR")
  private String nombreIndicador;

  @Column(name = "NOMBRE_NUMERADOR")
  private String nombreNumerador;

  @Column(name = "NOMBRE_DENOMINADOR")
  private String nombreDenominador;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @Column(name = "INDICADOR_BASE")
  private Character indicadorBase;

  @Column(name = "CASO_USO")
  private String casoUso;

  @Column(name = "VALOR_NUMERADOR")
  private BigDecimal valorNumerador;

  @Column(name = "VALOR_DENOMINADOR")
  private BigDecimal valorDenominador;

  public IndicadoresProyecto() {
  }

  public BigDecimal getValorNumerador() {
    return valorNumerador;
  }

  public void setValorNumerador(BigDecimal valorNumerador) {
    this.valorNumerador = valorNumerador;
  }

  public BigDecimal getValorDenominador() {
    return valorDenominador;
  }

  public void setValorDenominador(BigDecimal valorDenominador) {
    this.valorDenominador = valorDenominador;
  }

  public String getValorOperacionSinDecimalToString() {

    if (valorNumerador == null || valorDenominador == null) {
      return "0";
    }

    Double valor1 = valorNumerador.doubleValue();
    Double valor2 = valorDenominador.doubleValue();

    Double resultado = valor1 / valor2;
    resultado = resultado * 100;

    return String.valueOf(resultado.intValue());
  }

  public IndicadoresProyecto(Long idIndicadorProyecto) {
    this.idIndicadorProyecto = idIndicadorProyecto;
  }

  public Long getIdIndicadorProyecto() {
    return idIndicadorProyecto;
  }

  public void setIdIndicadorProyecto(Long idIndicadorProyecto) {
    this.idIndicadorProyecto = idIndicadorProyecto;
  }

  public String getNombreIndicador() {
    return nombreIndicador;
  }

  public void setNombreIndicador(String nombreIndicador) {
    this.nombreIndicador = nombreIndicador;
  }

  public String getNombreNumerador() {
    return nombreNumerador;
  }

  public void setNombreNumerador(String nombreNumerador) {
    this.nombreNumerador = nombreNumerador;
  }

  public String getNombreDenominador() {
    return nombreDenominador;
  }

  public void setNombreDenominador(String nombreDenominador) {
    this.nombreDenominador = nombreDenominador;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public Character getIndicadorBase() {
    return indicadorBase;
  }

  public void setIndicadorBase(Character indicadorBase) {
    this.indicadorBase = indicadorBase;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idIndicadorProyecto != null ? idIndicadorProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof IndicadoresProyecto)) {
      return false;
    }
    IndicadoresProyecto other = (IndicadoresProyecto) object;
    if ((this.idIndicadorProyecto == null && other.idIndicadorProyecto != null) || (this.idIndicadorProyecto != null && !this.idIndicadorProyecto.equals(other.idIndicadorProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.IndicadoresProyecto[ indicadoresProyectoPK=" + idIndicadorProyecto + " ]";
  }

  public String getCasoUso() {
    return casoUso;
  }

  public void setCasoUso(String casoUso) {
    this.casoUso = casoUso;
  }

  @Override
  @Transient
  public String getLlaveModel() {
    return String.valueOf(idIndicadorProyecto);
  }

}
