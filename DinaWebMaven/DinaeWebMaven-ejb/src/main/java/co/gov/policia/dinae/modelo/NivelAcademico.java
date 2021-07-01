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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "NIVELES_ACADEMICOS")
@NamedQueries({
  @NamedQuery(name = "nivelAcademico.findNivelByTipo", query = "SELECT new co.gov.policia.dinae.dto.NivelAcademicoDTO(u.consecutivo, u.descripcion) FROM NivelAcademico u ORDER BY u.descripcion ASC"),
  @NamedQuery(name = "nivelAcademicofindByID", query = "SELECT u FROM NivelAcademico u  Where u.consecutivo = :idNivelAcademico")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class NivelAcademico implements Serializable, IDataModel {

  @Id
  @Column(name = "ID_NIVEL_ACADEMICO")
  @XmlAttribute
  private Long consecutivo;
  @Column(name = "DESCRIPCION")
  @XmlAttribute
  private String descripcion;

  public Long getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(Long consecutivo) {
    this.consecutivo = consecutivo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public String getLlaveModel() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + Objects.hashCode(this.consecutivo);
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
    final NivelAcademico other = (NivelAcademico) obj;
    if (!Objects.equals(this.consecutivo, other.consecutivo)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "NivelAcademico{" + "consecutivo=" + consecutivo + '}';
  }
}
