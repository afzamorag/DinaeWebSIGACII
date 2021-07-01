package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "PROGRAMAS")
@NamedQueries({
    @NamedQuery(name = "Programas.findAll", query = "SELECT p FROM Programas p ORDER BY p.nombre"),
    @NamedQuery(name = "Programas.findProgramasByActivo", query = "SELECT p FROM Programas p WHERE p.activo = 'Y'"),
    @NamedQuery(name = "Programas.findProgramaByNombre", query = "SELECT p FROM Programas p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Programas.findProgramaByIdPrograma", query = "SELECT p FROM Programas p WHERE p.idPrograma = :idPrograma"),
    @NamedQuery(name = "Programas.findProgramasByActivoAndIdUnidadPolicial", query = "SELECT p FROM Programas p WHERE p.activo = 'Y' AND (p.idPrograma IN (SELECT pe.idPrograma FROM ProgramasEscuelas  pe WHERE pe.codEscuela = :idUnidadPolicial))")
})
public class Programas implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id    
    @NotNull
    @Column(name = "ID_PROGRAMA")
    private Long idPrograma;
    
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Size(max = 1)
    @Column(name = "ACTIVO")
    private String activo;
    
    @NotNull
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Column(name = "ID_USUARIO_ROL")
    private BigInteger idUsuarioRol;
    
    @Column(name = "ID_UNIDAD_POLICIAL")
    private Long idUnidadPolicial;    

    public Programas() {
    }

    public Programas(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Programas(Long idPrograma, Date fechaRegistro) {
        this.idPrograma = idPrograma;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigInteger getIdUsuarioRol() {
        return idUsuarioRol;
    }

    public void setIdUsuarioRol(BigInteger idUsuarioRol) {
        this.idUsuarioRol = idUsuarioRol;
    }

    /*public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrograma != null ? idPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programas)) {
            return false;
        }
        Programas other = (Programas) object;
        if ((this.idPrograma == null && other.idPrograma != null) || (this.idPrograma != null && !this.idPrograma.equals(other.idPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.modelo.Programas[ idPrograma=" + idPrograma + " ]";
    }

    /**
     * @return the idUnidadPolicial
     */
    public Long getIdUnidadPolicial() {
        return idUnidadPolicial;
    }

    /**
     * @param idUnidadPolicial the idUnidadPolicial to set
     */
    public void setIdUnidadPolicial(Long idUnidadPolicial) {
        this.idUnidadPolicial = idUnidadPolicial;
    }
    
}