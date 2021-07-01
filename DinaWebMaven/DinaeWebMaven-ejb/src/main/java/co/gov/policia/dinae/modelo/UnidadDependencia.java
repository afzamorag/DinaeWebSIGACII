/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "USR_REHU.UNIDADES_DEPENDENCIA")
@NamedQueries({
  @NamedQuery(name = "UnidadDependencia.findVigenteByTipo", query = "SELECT new co.gov.policia.dinae.dto.UnidadDependenciaDTO(u.consecutivo, u.descripcionDependencia, u.tipoUnidadCodigo) FROM UnidadDependencia u WHERE u.vigente='SI' and u.tipoUnidadCodigo IN (:idsTipos) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = "UnidadDependencia.findSiglaVigenteByTipo", query = "SELECT new co.gov.policia.dinae.dto.UnidadDependenciaDTO(u.consecutivo, u.siglaFisica) FROM UnidadDependencia u WHERE u.vigente='SI' and u.tipoUnidadCodigo IN (:tipos) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = "UnidadDependencia.findUnidadDependenciaVigenteByTipo", query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (:tipos) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = "UnidadDependencia.findEscuelaVigente", query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (10,15) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = UnidadDependencia.FIND_ESCUELAS_VIGENTES, query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (10,15) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = UnidadDependencia.FIND_UNIDADES_VIGENTES, query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (1,2,3,4,5,10,15,308) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = UnidadDependencia.FIND_DIRECCIONES_Y_ASESORAS_VIGENTES, query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (1,3) ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = UnidadDependencia.FIND_REGIONALES_VIGENTES, query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (308) ORDER BY u.descripcionDependencia ASC"),  
  @NamedQuery(name = UnidadDependencia.FIND_UNIDADES_FISICAS_VIGENTES_POR_REGIONAL, query = "SELECT u FROM UnidadDependencia u WHERE u.vigente = 'SI' and u.tipoUnidadCodigo in (1,2,3,4,5,10,15,308) AND u.regional = :regional ORDER BY u.descripcionDependencia ASC"),
  @NamedQuery(name = UnidadDependencia.FIND_BY_CONSECUTIVO, query = "SELECT u FROM UnidadDependencia u WHERE u.pk.consecutivo = :consecutivo")
})
@XmlRootElement
public class UnidadDependencia implements Serializable, IDataModel {

  public static final String FIND_ESCUELAS_VIGENTES = "UnidadDependencia.findEscuelasVigentes";
  public static final String FIND_UNIDADES_VIGENTES = "UnidadDependencia.findUnidadesVigentes";
  public static final String FIND_REGIONALES_VIGENTES = "UnidadDependencia.findRegionalesVigentes";
  public static final String FIND_UNIDADES_FISICAS_VIGENTES_POR_REGIONAL = "UnidadDependencia.findUnidadesFisicasVigentesPorRegional";
  public static final String FIND_BY_CONSECUTIVO = "UnidadDependencia.findByConsecutivo";
  public static final String FIND_DIRECCIONES_Y_ASESORAS_VIGENTES = "UnidadDependencia.findDireccionesAsesorasVigentes";
  public static final String FIND_REGIONALES_BY_CODI_REG = "UnidadDependencia.findRegionalesByCodiReg";
  @EmbeddedId
  private UnidadDependenciaPK pk;
  @Column(name = "CONSECUTIVO", insertable = false, updatable = false)
  private Long consecutivo;
  @Column(name = "DESCRIPCION_DEPENDENCIA")
  private String descripcionDependencia;
  @Column(name = "TIUN_CODIGO")
  private Long tipoUnidadCodigo;
  @Column(name = "SIGLA_FISICA")
  private String siglaFisica;
  private String vigente;
  @Column(name = "REGI_CODIGO")
  private Long regional;

  public UnidadDependencia() {
  }

  public UnidadDependencia(Long consecutivo) {
    this.consecutivo = consecutivo;
  }

  /**
   * @return the pk
   */
  public UnidadDependenciaPK getPk() {
    return pk;
  }

  /**
   * @param pk the pk to set
   */
  public void setPk(UnidadDependenciaPK pk) {
    this.pk = pk;
  }

  /**
   * @return the consecutivo
   */
  public Long getConsecutivo() {
    return consecutivo;
  }

  /**
   * @param consecutivo the consecutivo to set
   */
  public void setConsecutivo(Long consecutivo) {
    this.consecutivo = consecutivo;
  }

  /**
   * @return the descripcionDependencia
   */
  public String getDescripcionDependencia() {
    return descripcionDependencia;
  }

  /**
   * @param descripcionDependencia the descripcionDependencia to set
   */
  public void setDescripcionDependencia(String descripcionDependencia) {
    this.descripcionDependencia = descripcionDependencia;
  }

  /**
   * @return the tipoUnidadCodigo
   */
  public Long getTipoUnidadCodigo() {
    return tipoUnidadCodigo;
  }

  /**
   * @param tipoUnidadCodigo the tipoUnidadCodigo to set
   */
  public void setTipoUnidadCodigo(Long tipoUnidadCodigo) {
    this.tipoUnidadCodigo = tipoUnidadCodigo;
  }

  /**
   * @return the siglaFisica
   */
  public String getSiglaFisica() {
    return siglaFisica;
  }

  /**
   * @param siglaFisica the siglaFisica to set
   */
  public void setSiglaFisica(String siglaFisica) {
    this.siglaFisica = siglaFisica;
  }

  /**
   * @return the vigente
   */
  public String getVigente() {
    return vigente;
  }

  /**
   * @param vigente the vigente to set
   */
  public void setVigente(String vigente) {
    this.vigente = vigente;
  }

  /**
   * @return the regional
   */
  public Long getRegional() {
    return regional;
  }

  /**
   * @param regional the regional to set
   */
  public void setRegional(Long regional) {
    this.regional = regional;
  }

  @Override
  public String getLlaveModel() {
    return getConsecutivo().toString();
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + Objects.hashCode(this.consecutivo);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final UnidadDependencia other = (UnidadDependencia) obj;
    return Objects.equals(this.consecutivo, other.consecutivo);
  }

  @Override
  public String toString() {
    return "UnidadDependencia{" + "consecutivo=" + consecutivo + ", descripcionDependencia=" + descripcionDependencia + ", tipoUnidadCodigo=" + tipoUnidadCodigo + ", siglaFisica=" + siglaFisica + ", vigente=" + vigente + '}';
  }
}
