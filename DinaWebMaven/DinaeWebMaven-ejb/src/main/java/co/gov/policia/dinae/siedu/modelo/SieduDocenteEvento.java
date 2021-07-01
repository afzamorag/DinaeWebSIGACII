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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_DOCENTE_EVENTO")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "SieduDocenteEvento.findAll", query = "SELECT s FROM SieduDocenteEvento s"),
    @NamedQuery(name = SieduDocenteEvento.FIND_BY_DOCEEVEE, query = "SELECT s FROM SieduDocenteEvento s WHERE s.doceEvee.eveeEvee =:doceEvee"),
    @NamedQuery(name = SieduDocenteEvento.FIND_BY_DOCEEVEE_DOCEPERS, query = "SELECT s FROM SieduDocenteEvento s WHERE s.doceEvee.eveeEvee =:doceEvee AND s.docePers.persPers =:docePers")
})

public class SieduDocenteEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_DOCEEVEE = "SieduDocenteEvento.findListById";
    public static final String FIND_BY_DOCEEVEE_DOCEPERS = "SieduDocenteEvento.findByDoceeveeDocepers";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOCE_DOCE", nullable = false)
    @SequenceGenerator(name = "DOCENTE_SEQ_GEN", sequenceName = "SIEDU_DOCENTE_EVENTO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCENTE_SEQ_GEN")
    private Long doceDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DOCE_USU_CREA", nullable = false, length = 30)
    private String doceUsuCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOCE_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date doceFechaCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DOCE_MAQUINA_CREA", nullable = false, length = 100)
    private String doceMaquinaCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DOCE_IP_CREA", nullable = false, length = 100)
    private String doceIpCrea;
    @Size(max = 30)
    @Column(name = "DOCE_USU_MOD", length = 30)
    private String doceUsuMod;
    @Column(name = "DOCE_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doceFechaMod;
    @Size(max = 100)
    @Column(name = "DOCE_MAQUINA_MOD", length = 100)
    private String doceMaquinaMod;
    @Size(max = 100)
    @Column(name = "DOCE_IP_MOD", length = 100)
    private String doceIpMod;
    @Column(name = "DOCE_FECHA_TEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doceFechaTema;
    @JoinColumn(name = "DOCE_TEMA", referencedColumnName = "TEMA_TEMA", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduTema doceTema;
    @JoinColumn(name = "DOCE_PERS", referencedColumnName = "PERS_PERS", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduPersona docePers;
    @JoinColumn(name = "DOCE_EVEE", referencedColumnName = "EVEE_EVEE", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduEventoEscuela doceEvee;

    public SieduDocenteEvento() {
    }

    public SieduDocenteEvento(Long doceDoce) {
        this.doceDoce = doceDoce;
    }

    public SieduDocenteEvento(Long doceDoce, String doceUsuCrea, Date doceFechaCrea, String doceMaquinaCrea, String doceIpCrea) {
        this.doceDoce = doceDoce;
        this.doceUsuCrea = doceUsuCrea;
        this.doceFechaCrea = doceFechaCrea;
        this.doceMaquinaCrea = doceMaquinaCrea;
        this.doceIpCrea = doceIpCrea;
    }

    public Long getDoceDoce() {
        return doceDoce;
    }

    public void setDoceDoce(Long doceDoce) {
        this.doceDoce = doceDoce;
    }

    public String getDoceUsuCrea() {
        return doceUsuCrea;
    }

    public void setDoceUsuCrea(String doceUsuCrea) {
        this.doceUsuCrea = doceUsuCrea;
    }

    public Date getDoceFechaCrea() {
        return doceFechaCrea;
    }

    public void setDoceFechaCrea(Date doceFechaCrea) {
        this.doceFechaCrea = doceFechaCrea;
    }

    public String getDoceMaquinaCrea() {
        return doceMaquinaCrea;
    }

    public void setDoceMaquinaCrea(String doceMaquinaCrea) {
        this.doceMaquinaCrea = doceMaquinaCrea;
    }

    public String getDoceIpCrea() {
        return doceIpCrea;
    }

    public void setDoceIpCrea(String doceIpCrea) {
        this.doceIpCrea = doceIpCrea;
    }

    public String getDoceUsuMod() {
        return doceUsuMod;
    }

    public void setDoceUsuMod(String doceUsuMod) {
        this.doceUsuMod = doceUsuMod;
    }

    public Date getDoceFechaMod() {
        return doceFechaMod;
    }

    public void setDoceFechaMod(Date doceFechaMod) {
        this.doceFechaMod = doceFechaMod;
    }

    public String getDoceMaquinaMod() {
        return doceMaquinaMod;
    }

    public void setDoceMaquinaMod(String doceMaquinaMod) {
        this.doceMaquinaMod = doceMaquinaMod;
    }

    public String getDoceIpMod() {
        return doceIpMod;
    }

    public void setDoceIpMod(String doceIpMod) {
        this.doceIpMod = doceIpMod;
    }

    public SieduTema getDoceTema() {
        return doceTema;
    }

    public void setDoceTema(SieduTema doceTema) {
        this.doceTema = doceTema;
    }

    public SieduPersona getDocePers() {
        return docePers;
    }

    public void setDocePers(SieduPersona docePers) {
        this.docePers = docePers;
    }

    public SieduEventoEscuela getDoceEvee() {
        return doceEvee;
    }

    public void setDoceEvee(SieduEventoEscuela doceEvee) {
        this.doceEvee = doceEvee;
    }

    public Date getDoceFechaTema() {
        return doceFechaTema;
    }

    public void setDoceFechaTema(Date doceFechaTema) {
        this.doceFechaTema = doceFechaTema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doceDoce != null ? doceDoce.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieduDocenteEvento)) {
            return false;
        }
        SieduDocenteEvento other = (SieduDocenteEvento) object;
        if ((this.doceDoce == null && other.doceDoce != null) || (this.doceDoce != null && !this.doceDoce.equals(other.doceDoce))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento[ doceDoce=" + doceDoce + " ]";
    }

}
