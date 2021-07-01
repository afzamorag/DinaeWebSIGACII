/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael Guillermo Blanco Banquéz <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "PRUEBA_CAP2", catalog = "", schema = "USR_DISIGAC4")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "PruebaCap2.findAll", query = "SELECT p FROM PruebaCap2 p"),
  @NamedQuery(name = "PruebaCap2.findByColumn1", query = "SELECT p FROM PruebaCap2 p WHERE p.column1 = :column1"),
  @NamedQuery(name = "PruebaCap2.findByColumn2", query = "SELECT p FROM PruebaCap2 p WHERE p.column2 = :column2"),
  @NamedQuery(name = "PruebaCap2.findByColumn3", query = "SELECT p FROM PruebaCap2 p WHERE p.column3 = :column3"),
  @NamedQuery(name = "PruebaCap2.findByColumn4", query = "SELECT p FROM PruebaCap2 p WHERE p.column4 = :column4")})
public class PruebaCap2 implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(nullable = false, length = 20)
  private String column1;
  @Size(max = 20)
  @Column(length = 20)
  private String column2;
  @Size(max = 20)
  @Column(length = 20)
  private String column3;
  @Size(max = 20)
  @Column(length = 20)
  private String column4;

  public PruebaCap2() {
  }

  public PruebaCap2(String column1) {
    this.column1 = column1;
  }

  public String getColumn1() {
    return column1;
  }

  public void setColumn1(String column1) {
    this.column1 = column1;
  }

  public String getColumn2() {
    return column2;
  }

  public void setColumn2(String column2) {
    this.column2 = column2;
  }

  public String getColumn3() {
    return column3;
  }

  public void setColumn3(String column3) {
    this.column3 = column3;
  }

  public String getColumn4() {
    return column4;
  }

  public void setColumn4(String column4) {
    this.column4 = column4;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (column1 != null ? column1.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof PruebaCap2)) {
      return false;
    }
    PruebaCap2 other = (PruebaCap2) object;
    if ((this.column1 == null && other.column1 != null) || (this.column1 != null && !this.column1.equals(other.column1))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.PruebaCap2[ column1=" + column1 + " ]";
  }

}
