package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INDICADORES_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "IndicadoresProyectoVersion.findProyectoEindicadorBaseYcasoUso", query = "SELECT i From IndicadoresProyectoVersion i WHERE i.proyectoVersion.idProyectoVersion = :idProyectoVersion AND i.indicadorBase = :indicadorBase AND i.casoUso = :casoUso")
})
public class IndicadoresProyectoVersion implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
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

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProyectoVersion proyectoVersion;

  @Column(name = "INDICADOR_BASE")
  private Character indicadorBase;

  @Column(name = "CASO_USO")
  private String casoUso;

  @Column(name = "VALOR_NUMERADOR")
  private BigDecimal valorNumerador;

  @Column(name = "VALOR_DENOMINADOR")
  private BigDecimal valorDenominador;

  public IndicadoresProyectoVersion() {
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

  public IndicadoresProyectoVersion(Long idIndicadorProyecto) {
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

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }
}
