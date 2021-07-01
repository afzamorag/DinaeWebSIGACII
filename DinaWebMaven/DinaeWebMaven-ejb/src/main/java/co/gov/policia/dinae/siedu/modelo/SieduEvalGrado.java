/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Juan Jose Buzon
 */
@Entity
@Table(name = "SIEDU_EVAL_GRADO")
@NamedQueries({
  @NamedQuery(name = SieduEvalGrado.FIND_BY_EVALUATION, query = "SELECT eg FROM SieduEvalGrado eg WHERE eg.evaluacion.id = :idEvaluacion"),
  @NamedQuery(name = SieduEvalGrado.DELETE_BY_EVALUATION, query = "DELETE FROM SieduEvalGrado eg WHERE eg.evaluacion.id = :idEvaluacion")})
public class SieduEvalGrado implements Serializable {

  private static final long serialVersionUID = 1628207869177893990L;

  public static final String FIND_BY_EVALUATION = "SieduEvalGrado.findByEvaluation";
  public static final String DELETE_BY_EVALUATION = "SieduEvalGrado.deleteByEvaluation";

  @EmbeddedId
  private SieduEvalGradoPK id;

  @Column(name = "EVGR_USU_CREA")
  private String evgrUsuCrea;

  @Column(name = "EVGR_FECHA_CREA")
  @Temporal(TemporalType.DATE)
  private Date evgrFechaCrea;

  @Column(name = "EVGR_MAQUINA_CREA")
  private String evgrMaquinaCrea;

  @Column(name = "EVGR_IP_CREA")
  private String evgrIpCrea;

  @Column(name = "EVGR_USU_MOD")
  private String evgrUsuMod;

  @Column(name = "EVGR_FECHA_MOD")
  @Temporal(TemporalType.DATE)
  private Date evgr_fecha_mod;

  @Column(name = "EVGR_MAQUINA_MOD")
  private String evgrMaquinaMod;

  @Column(name = "EVGR_IP_MOD")
  private String evgrIpMod;

  @ManyToOne
  @JoinColumn(name = "EVGR_EVAL", insertable = false, updatable = false)
  private Evaluacion evaluacion;

  //, insertable = false, updatable = false    
  @ManyToOne(optional = false)
  @JoinColumns({
    @JoinColumn(name = "EVGR_GRAD_FUERZA", referencedColumnName = "FUERZA", insertable = false, updatable = false ),
    @JoinColumn(name = "EVGR_GRAD_ALFABETICO", referencedColumnName = "ALFABETICO", insertable = false, updatable = false )})
  private Grado grado;

  public String getEvgrUsuCrea() {
    return evgrUsuCrea;
  }

  public void setEvgrUsuCrea(String evgrUsuCrea) {
    this.evgrUsuCrea = evgrUsuCrea;
  }

  public Date getEvgrFechaCrea() {
    return evgrFechaCrea;
  }

  public void setEvgrFechaCrea(Date evgrFechaCrea) {
    this.evgrFechaCrea = evgrFechaCrea;
  }

  public String getEvgrMaquinaCrea() {
    return evgrMaquinaCrea;
  }

  public void setEvgrMaquinaCrea(String evgrMaquinaCrea) {
    this.evgrMaquinaCrea = evgrMaquinaCrea;
  }

  public String getEvgrIpCrea() {
    return evgrIpCrea;
  }

  public void setEvgrIpCrea(String evgrIpCrea) {
    this.evgrIpCrea = evgrIpCrea;
  }

  public String getEvgrUsuMod() {
    return evgrUsuMod;
  }

  public void setEvgrUsuMod(String evgrUsuMod) {
    this.evgrUsuMod = evgrUsuMod;
  }

  public Date getEvgr_fecha_mod() {
    return evgr_fecha_mod;
  }

  public void setEvgrFechaMod(Date evgr_fecha_mod) {
    this.evgr_fecha_mod = evgr_fecha_mod;
  }

  public String getEvgrMaquinaMod() {
    return evgrMaquinaMod;
  }

  public void setEvgrMaquinaMod(String evgrMaquinaMod) {
    this.evgrMaquinaMod = evgrMaquinaMod;
  }

  public String getEvgrIpMod() {
    return evgrIpMod;
  }

  public void setEvgrIpMod(String evgrIpMod) {
    this.evgrIpMod = evgrIpMod;
  }

  public SieduEvalGradoPK getId() {
    return id;
  }

  public void setId(SieduEvalGradoPK id) {
    this.id = id;
  }

  public Evaluacion getEvaluacion() {
    return evaluacion;
  }

  public void setEvaluacion(Evaluacion evaluacion) {
    this.evaluacion = evaluacion;
  }

  public Grado getGrado() {
    return grado;
  }

  public void setGrado(Grado grado) {
    this.grado = grado;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.id);
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
    final SieduEvalGrado other = (SieduEvalGrado) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduEvalGrado[ id=" + id
            + " ]";
  }

}
