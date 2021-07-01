/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "DASHBOARD_CAPACITACION")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = DashboardCapacitacion.FIND_BY_VIGENCIA, query = "SELECT s FROM DashboardCapacitacion s WHERE s.vigencia = :vigencia")})
@XmlRootElement

public class DashboardCapacitacion implements Serializable {
    

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_VIGENCIA = "DashboardCapacitacion.findByVigencia";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "VIGENCIA", nullable = false)
    private String vigencia;    
    @Column(name = "ESCUELA", nullable = false)
    private String escuela;    
    @Column(name = "COD_ESCUELA", nullable = false)
    private Long codEscuela;
    @Column(name = "ESTRATEGIA", nullable = false)
    private String estrategia;
    @Column(name = "CARRERA", nullable = false)
    private String carrera;
    @Column(name = "M_T1", nullable = false)
    private Long mT1;
    @Column(name = "C_T1", nullable = false)
    private Long cT1;
    @Column(name = "M_T2", nullable = false)
    private Long mT2;
    @Column(name = "C_T2", nullable = false)
    private Long cT2;
    @Column(name = "M_T3", nullable = false)
    private Long mT3;
    @Column(name = "C_T3", nullable = false)
    private Long cT3;
    @Column(name = "M_T4", nullable = false)
    private Long mT4;
    @Column(name = "C_T4", nullable = false)
    private Long cT4;
    @Column(name = "META", nullable = false)
    private Long meta;
    @Column(name = "TOTAL", nullable = false)
    private Long total;
    @Column(name = "AVANCE", nullable = false)
    private double avance;

    public DashboardCapacitacion() {
    }

    public DashboardCapacitacion(Long id, String vigencia, String escuela, Long codEscuela, String estrategia, String carrera, Long mT1, Long cT1, Long mT2, Long cT2, Long mT3, Long cT3, Long mT4, Long cT4, Long meta, Long total, double avance) {
        this.id = id;
        this.vigencia = vigencia;
        this.escuela = escuela;
        this.codEscuela = codEscuela;
        this.estrategia = estrategia;
        this.carrera = carrera;
        this.mT1 = mT1;
        this.cT1 = cT1;
        this.mT2 = mT2;
        this.cT2 = cT2;
        this.mT3 = mT3;
        this.cT3 = cT3;
        this.mT4 = mT4;
        this.cT4 = cT4;
        this.meta = meta;
        this.total = total;
        this.avance = avance;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Long getmT1() {
        return mT1;
    }

    public void setmT1(Long mT1) {
        this.mT1 = mT1;
    }

    public Long getcT1() {
        return cT1;
    }

    public void setcT1(Long cT1) {
        this.cT1 = cT1;
    }

    public Long getmT2() {
        return mT2;
    }

    public void setmT2(Long mT2) {
        this.mT2 = mT2;
    }

    public Long getcT2() {
        return cT2;
    }

    public void setcT2(Long cT2) {
        this.cT2 = cT2;
    }

    public Long getmT3() {
        return mT3;
    }

    public void setmT3(Long mT3) {
        this.mT3 = mT3;
    }

    public Long getcT3() {
        return cT3;
    }

    public void setcT3(Long cT3) {
        this.cT3 = cT3;
    }

    public Long getmT4() {
        return mT4;
    }

    public void setmT4(Long mT4) {
        this.mT4 = mT4;
    }

    public Long getcT4() {
        return cT4;
    }

    public void setcT4(Long cT4) {
        this.cT4 = cT4;
    }

    public Long getMeta() {
        return meta;
    }

    public void setMeta(Long meta) {
        this.meta = meta;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public double getAvance() {
        return avance;
    }

    public void setAvance(double avance) {
        this.avance = avance;
    }

    public Long getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(Long codEscuela) {
        this.codEscuela = codEscuela;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final DashboardCapacitacion other = (DashboardCapacitacion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
