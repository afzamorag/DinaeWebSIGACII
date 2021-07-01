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
@Table(name = "INDICADOR_COMPROMISO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "IndicadoresCompromisoProyecto.findAll", query = "SELECT i FROM IndicadoresCompromisoProyecto i"),
  @NamedQuery(name = "IndicadoresCompromisoProyecto.findAllPorCompromisoProyecto", query = "SELECT i FROM IndicadoresCompromisoProyecto i WHERE i.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "IndicadoresCompromisoProyecto.findPorCompromisoProyectoEindicadorProyecto", query = "SELECT i FROM IndicadoresCompromisoProyecto i WHERE i.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto AND i.indicadoresProyecto.idIndicadorProyecto = :idIndicadorProyecto ")
})
public class IndicadoresCompromisoProyecto implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IndicadoresCompromisoProyecto_seq_gen")
  @SequenceGenerator(name = "IndicadoresCompromisoProyecto_seq_gen", sequenceName = "SEC_INDICADOR_COMPROMI_PROYEC", allocationSize = 1)
  @Column(name = "ID_INDICADOR_COMPROMISO_PROYEC")
  private Long idIndicadorCompromisoProyecto;

  @Column(name = "VALOR_NUMERADOR")
  private BigDecimal valorNumerador;

  @Column(name = "VALOR_DENOMINADOR")
  private BigDecimal valorDenominador;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FECHA_MODIFICA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModifica;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_INDICADOR_PROYECTO", referencedColumnName = "ID_INDICADOR_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private IndicadoresProyecto indicadoresProyecto;

  @JoinColumn(name = "ID_COMPROMISO_PROYECTO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyecto;

  public IndicadoresCompromisoProyecto() {
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

  public Long getIdIndicadorCompromisoProyecto() {
    return idIndicadorCompromisoProyecto;
  }

  public void setIdIndicadorCompromisoProyecto(Long idIndicadorCompromisoProyecto) {
    this.idIndicadorCompromisoProyecto = idIndicadorCompromisoProyecto;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Date getFechaModifica() {
    return fechaModifica;
  }

  public void setFechaModifica(Date fechaModifica) {
    this.fechaModifica = fechaModifica;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public IndicadoresProyecto getIndicadoresProyecto() {
    return indicadoresProyecto;
  }

  public void setIndicadoresProyecto(IndicadoresProyecto indicadoresProyecto) {
    this.indicadoresProyecto = indicadoresProyecto;
  }

  public CompromisoProyecto getCompromisoProyecto() {
    return compromisoProyecto;
  }

  public void setCompromisoProyecto(CompromisoProyecto compromisoProyecto) {
    this.compromisoProyecto = compromisoProyecto;
  }

  @Override
  @Transient
  public String getLlaveModel() {

    if (indicadoresProyecto == null) {
      return null;
    }
    return String.valueOf(indicadoresProyecto.getIdIndicadorProyecto());
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
