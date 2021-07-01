/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "LOG_MOODLE_SEND_HTTP")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = LogMoodleSendHttp.FIND_ALL,       query = "SELECT s FROM LogMoodleSendHttp s ORDER BY s.fecha DESC"),
  @NamedQuery(name = LogMoodleSendHttp.FIND_FILTRO,    query = "SELECT s FROM LogMoodleSendHttp s WHERE 1=1 "),
  @NamedQuery(name = LogMoodleSendHttp.DELETE_ESTADO,  query = "DELETE   FROM LogMoodleSendHttp s WHERE s.estado = :ESTADO "),
  @NamedQuery(name = LogMoodleSendHttp.DELETE_HISTORY, query = "DELETE   FROM LogMoodleSendHttp s WHERE s.fecha < :FECHA")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class LogMoodleSendHttp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL        = "LogMoodleSendHttp.findAll";
    public static final String FIND_FILTRO     = "LogMoodleSendHttp.findFiltro";
    public static final String DELETE_HISTORY  = "LogMoodleSendHttp.deleteHistory";
    public static final String DELETE_ESTADO   = "LogMoodleSendHttp.deleteEstado";
    
    @Id
    @Basic(optional = false)
    @NotNull    
    @Column(name = "LMH_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOG_MOODLE_HTTP")
    @SequenceGenerator(name = "SEQ_LOG_MOODLE_HTTP", sequenceName = "SEQ_LOG_MOODLE_HTTP", allocationSize = 1)
    private Long id;  

    @Basic(optional = false)
    @NotNull
    @Column(name = "LMH_FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;  

    @Basic(optional = false)
    @NotNull
    @Column(name = "LMH_SERVICE", nullable = false)
    private String servicio; 
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "LMH_URI", nullable = false)
    private String uri; 

    @Basic(optional = false)
    @NotNull
    @Column(name = "LMH_ESTADO", nullable = false)
    private Long estado = 1l; 
    
    @Column(name = "LMH_PARAMETERS", nullable = true)
    private String parameters; 
    
    @Column(name = "LMH_RESULT", nullable = true)
    private String result; 

    @Column(name = "LMH_ERROR", nullable = true)
    private String error; 

    @Column(name = "LMH_RENVIO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRenvio;  
    
    @Transient
    private String resultado;
    
    public String toXmlItem(String pre) {
        String sXml = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        sXml += pre + "<item>\n"; 
        sXml += pre + "\t<id>" + this.id + "</id>\n"; 
        sXml += pre + "\t<fecha><![CDATA[" + dateFormat.format(this.fecha) + "]]></fecha>\n"; 
        sXml += pre + "\t<servicio><![CDATA[" + this.servicio + "]]></servicio>\n"; 
        sXml += pre + "\t<uri><![CDATA[" + this.uri + "]]></uri>\n"; 
        sXml += pre + "\t<estado>" + this.id + "</estado>\n"; 
        if(this.parameters != null && !this.parameters.isEmpty()) {
            sXml += pre + "\t<parameters><![CDATA[" + this.parameters + "]]></parameters>\n"; 
        } else {
            sXml += pre + "\t<parameters/>\n";
        }
        if(this.result != null && !this.result.isEmpty()) {
            sXml += pre + "\t<result><![CDATA[" + this.result + "]]></result>\n"; 
        } else {
            sXml += pre + "\t<result/>\n";
        }
        if(this.error != null && !this.error.isEmpty()) {
            sXml += pre + "\t<error><![CDATA[" + this.error + "]]></error>\n"; 
        } else {
            sXml += pre + "\t<error/>\n";
        }
        if(this.fechaRenvio != null) {
           sXml += pre + "\t<fechaRenvio><![CDATA[" + dateFormat.format(this.fechaRenvio) + "]]></fechaRenvio>\n"; 
        } else {
            sXml += pre + "\t<fechaRenvio/>\n";
        }
        sXml += pre + "<item>\n"; 
        return sXml;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getFechaRenvio() {
        return fechaRenvio;
    }

    public void setFechaRenvio(Date fechaRenvio) {
        this.fechaRenvio = fechaRenvio;
    }

    public String getResultado() {
        if(this.resultado == null) {
            if(this.error != null) {
                this.resultado = this.error;
            } else {
                this.resultado = this.result;
            }
            if(this.resultado != null && this.resultado.length() > 100) {
                this.resultado = this.resultado.substring(0, 99) + "....";
            }
        }
        return this.resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogMoodleSendHttp)) {
            return false;
        }
        LogMoodleSendHttp other = (LogMoodleSendHttp) object;
        if((this.id == null && other.id != null) || (this.id != null && other.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.LogMoodleSendHttp[ id=" + id + " ]";
    }
}
