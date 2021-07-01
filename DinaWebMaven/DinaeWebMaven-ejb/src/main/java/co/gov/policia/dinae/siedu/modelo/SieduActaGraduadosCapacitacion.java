/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JUAN.CIFUENTES
 */
@Entity
@Table(name = "SIEDU_ACTA_GRADUADOS_CAPACITA", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"GRAD_EVEE"})})
@NamedQueries({
    @NamedQuery(name = "SieduActaGraduadosCapacita.findAll", query = "SELECT s FROM SieduActaGraduadosCapacita s")
})
@Cacheable(false)

@XmlRootElement
public class SieduActaGraduadosCapacitacion implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRAD_GRAD", nullable = false, precision = 0, scale = -127)
    @SequenceGenerator(name = "SIEDU_ACTA_GRAD_CAPACITA_SEQ_GEN", sequenceName = "SIEDU_ACTA_GRAD_CAPACITA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_ACTA_GRAD_CAPACITA_SEQ_GEN")
    private Long gradGrad;
        
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "eveeEnti", fetch = FetchType.LAZY)
    private SieduEventoEscuela gradEvee;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRAD_FECHA_GRADO", nullable = false)
    @XmlAttribute
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date gradFechaGrado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRAD_NRO_LIBRO", nullable = false)
    @XmlAttribute
    private Long gradNroLibro;
        
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRAD_NRO_ACTA", nullable = false)
    @XmlAttribute
    private Long gradNroActa;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRAD_FECHA_ACTA", nullable = false)
    @XmlAttribute
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date gradFechaActa;
        
    @Basic(optional = false)
    @NotNull
    @Column(name = "GRAD_FOLIO_ACTA", nullable = false)
    @XmlAttribute
    private Long gradFolioActa;
        
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_FECHA_CREA", nullable = true)
    @XmlAttribute
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date gradFechaCrea;
       
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_USU_CREA", nullable = true)
    @XmlAttribute
    private String gradUsuCrea;
       
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_MAQUINA_CREA", nullable = true)
    @XmlAttribute
    private String gradMaquinaCrea;
        
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_IP_CREA", nullable = true)
    @XmlAttribute
    private String gradIpCrea;
        
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_FECHA_MOD", nullable = true)
    @XmlAttribute
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date gradFechaMod;
    
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_USU_MOD", nullable = true)
    @XmlAttribute
    private String gradUsuMod;
       
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_MAQUINA_MOD", nullable = true)
    @XmlAttribute
    private String gradMaquinaMod;
        
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_IP_MOD", nullable = true)
    @XmlAttribute
    private String gradIpMod;
        
    @Basic(optional = true)
    @NotNull
    @Column(name = "GRAD_GRADUADOS", nullable = true)
    @XmlAttribute
    private String gradGraduados;
    

public SieduActaGraduadosCapacitacion() {
    }


public SieduActaGraduadosCapacitacion(Long gradGrad) {
    this.gradGrad = gradGrad;
    }

public SieduActaGraduadosCapacitacion (Long gradGrad, SieduEventoEscuela gradEvee, Date gradFechaGrado, Long gradNroLibro, Long gradNroActa, Date gradFechaActa, Long gradFolioActa, Date gradFechaCrea, String gradUsuCrea, String gradMaquinaCrea, String gradIpCrea, Date gradFechaMod, String gradUsuMod, String gradMaquinaMod, String gradIpMod, String gradGraduados){
    this.gradGrad = gradGrad;
    this.gradEvee = gradEvee;
    this.gradFechaGrado = gradFechaGrado;
    this.gradNroLibro = gradNroLibro;
    this.gradNroActa = gradNroActa;
    this.gradFechaActa = gradFechaActa;
    this.gradFolioActa = gradFolioActa;
    this.gradFechaCrea = gradFechaCrea;
    this.gradUsuCrea = gradUsuCrea;
    this.gradMaquinaCrea = gradMaquinaCrea;
    this.gradIpCrea = gradIpCrea;
    this.gradFechaMod = gradFechaMod;
    this.gradUsuMod = gradUsuMod;
    this.gradMaquinaMod = gradMaquinaMod;
    this.gradIpMod = gradIpMod;
    this.gradGraduados = gradGraduados;
}

    public Long getGradGrad() {
        return gradGrad;
    }

    public void setGradGrad(Long gradGrad) {
        this.gradGrad = gradGrad;
    }

    public SieduEventoEscuela getGradEvee() {
        return gradEvee;
    }

    public void setGradEvee(SieduEventoEscuela gradEvee) {
        this.gradEvee = gradEvee;
    }

    public Date getGradFechaGrado() {
        return gradFechaGrado;
    }

    public void setGradFechaGrado(Date gradFechaGrado) {
        this.gradFechaGrado = gradFechaGrado;
    }

    public Long getGradNroLibro() {
        return gradNroLibro;
    }

    public void setGradNroLibro(Long gradNroLibro) {
        this.gradNroLibro = gradNroLibro;
    }

    public Long getGradNroActa() {
        return gradNroActa;
    }

    public void setGradNroActa(Long gradNroActa) {
        this.gradNroActa = gradNroActa;
    }

    public Date getGradFechaActa() {
        return gradFechaActa;
    }

    public void setGradFechaActa(Date gradFechaActa) {
        this.gradFechaActa = gradFechaActa;
    }

    public Long getGradFolioActa() {
        return gradFolioActa;
    }

    public void setGradFolioActa(Long gradFolioActa) {
        this.gradFolioActa = gradFolioActa;
    }

    public Date getGradFechaCrea() {
        return gradFechaCrea;
    }

    public void setGradFechaCrea(Date gradFechaCrea) {
        this.gradFechaCrea = gradFechaCrea;
    }

    public String getGradUsuCrea() {
        return gradUsuCrea;
    }

    public void setGradUsuCrea(String gradUsuCrea) {
        this.gradUsuCrea = gradUsuCrea;
    }

    public String getGradMaquinaCrea() {
        return gradMaquinaCrea;
    }

    public void setGradMaquinaCrea(String gradMaquinaCrea) {
        this.gradMaquinaCrea = gradMaquinaCrea;
    }

    public String getGradIpCrea() {
        return gradIpCrea;
    }

    public void setGradIpCrea(String gradIpCrea) {
        this.gradIpCrea = gradIpCrea;
    }

    public Date getGradFechaMod() {
        return gradFechaMod;
    }

    public void setGradFechaMod(Date gradFechaMod) {
        this.gradFechaMod = gradFechaMod;
    }

    public String getGradUsuMod() {
        return gradUsuMod;
    }

    public void setGradUsuMod(String gradUsuMod) {
        this.gradUsuMod = gradUsuMod;
    }

    public String getGradMaquinaMod() {
        return gradMaquinaMod;
    }

    public void setGradMaquinaMod(String gradMaquinaMod) {
        this.gradMaquinaMod = gradMaquinaMod;
    }

    public String getGradIpMod() {
        return gradIpMod;
    }

    public void setGradIpMod(String gradIpMod) {
        this.gradIpMod = gradIpMod;
    }

    public String getGradGraduados() {
        return gradGraduados;
    }

    public void setGradGraduados(String gradGraduados) {
        this.gradGraduados = gradGraduados;
    }
      

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradGrad != null ? gradGrad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieduActaGraduadosCapacitacion)) {
            return false;
        }
        SieduActaGraduadosCapacitacion other = (SieduActaGraduadosCapacitacion) object;
        if ((this.gradGrad == null && other.gradGrad != null) || (this.gradGrad != null && !this.gradGrad.equals(other.gradGrad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.SieduActaGraduadosCapacitacion[ gradGrad=" + gradGrad + " ]";
    }
    
}





