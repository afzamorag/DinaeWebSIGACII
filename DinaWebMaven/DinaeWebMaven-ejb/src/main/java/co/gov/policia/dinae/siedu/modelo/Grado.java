/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "GRADOS", schema = "USR_REHU")
@NamedQueries({
    @NamedQuery(name = Grado.FIND_GRADOS_VIGENTES, query = "SELECT g FROM Grado g WHERE g.vigente = 'SI' ORDER BY g.descripcion DESC"),
    @NamedQuery(name = Grado.FIND_VIGENTES_BY_CATEGORY, query = "SELECT g FROM Grado g WHERE g.vigente = 'SI' AND g.categoria.id = :idCategoria ORDER BY g.descripcion DESC"),
    @NamedQuery(name = Grado.FIND_VIGENTES_BY_CATEGORIES, query = "SELECT g FROM Grado g WHERE g.vigente = 'SI' AND g.categoria IN (:idsCategorias)  ORDER BY g.descripcion DESC"),
    @NamedQuery(name = Grado.FIND_BY_ID, query = "SELECT g FROM Grado g WHERE g.id.alfabetico =:alfabetico")
})
public class Grado implements Serializable {

    private static final long serialVersionUID = 11291577102776181L;

    public static final String FIND_GRADOS_VIGENTES = "Grado.findVigente";
    public static final String FIND_VIGENTES_BY_CATEGORY = "Grado.findVigenteByCategory";
    public static final String FIND_VIGENTES_BY_CATEGORIES = "Grado.findVigenteByCategories";
    public static final String FIND_BY_ID = "Grado.findBy Id";

    @EmbeddedId
    private GradoPK id;
    
    @Column(name = "NUMERICO")
    private Long numerico;

    @Column(name = "NATURALEZA")
    private String naturaleza;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ESCALA")
    private Long escala;

    @Column(name = "SUELDO")
    private BigDecimal sueldo;

    @Column(name = "PLANTA_ACTUAL")
    private Long plantaActual;
    
    @Column(name = "PLANTA_AUTORIZADA")
    private Long plantaAutorizada;

    @Column(name = "PLANTA_IDEAL")
    private Long plantaIdeal;

    @Column(name = "DESCUENTO_COMISION_EXTERIOR")
    private Long descuentoComisionExterior;

    @Column(name = "TIEMPO_GRADO")
    private Long tiempoGrado;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA"),
        @JoinColumn(name = "FUERZA", referencedColumnName = "FUERZA", insertable = false, updatable = false)})
    private Categoria categoria;

    @Column(name = "ID_TIPO_CATEGORIA")
    private Long tipoCategoria;

    @Column(name = "MAQUINA_CREACION")
    private String maquinaCreacion;

    @Column(name = "MAQUINA_ACTUALIZA")
    private String maquinaActualiza;

    @Column(name = "FUNCION_PUBLICA")
    private Long funcionPublica;

    @Column(name = "VIGENTE")
    private String vigente;

    @Column(name = "PORCENTAJE_INCREMENTO")
    private BigDecimal porcentajeIncremento;

    @Column(name = "FECHA_INICIO_INCREMENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioIncremento;

    @Column(name = "NUMERICO_EQUIVALENTE")
    private Long numericoEquivalente;

    @Column(name = "ID_NOMINA")
    private String idNomina;

    public GradoPK getId() {
        return id;
    }

    public void setId(GradoPK id) {
        this.id = id;
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
        if (!(object instanceof Grado)) {
            return false;
        }
        Grado other = (Grado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Grado[ id=" + id + " ]";
    }
    
    public Long getNumerico() {
        return numerico;
    }

    public void setNumerico(Long numerico) {
        this.numerico = numerico;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEscala() {
        return escala;
    }

    public void setEscala(Long escala) {
        this.escala = escala;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Long getPlantaActual() {
        return plantaActual;
    }

    public void setPlantaActual(Long plantaActual) {
        this.plantaActual = plantaActual;
    }

    public Long getPlantaAutorizada() {
        return plantaAutorizada;
    }

    public void setPlantaAutorizada(Long plantaAutorizada) {
        this.plantaAutorizada = plantaAutorizada;
    }

    public Long getPlantaIdeal() {
        return plantaIdeal;
    }

    public void setPlantaIdeal(Long plantaIdeal) {
        this.plantaIdeal = plantaIdeal;
    }

    public Long getDescuentoComisionExterior() {
        return descuentoComisionExterior;
    }

    public void setDescuentoComisionExterior(Long descuentoComisionExterior) {
        this.descuentoComisionExterior = descuentoComisionExterior;
    }

    public Long getTiempoGrado() {
        return tiempoGrado;
    }

    public void setTiempoGrado(Long tiempoGrado) {
        this.tiempoGrado = tiempoGrado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(Long tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getMaquinaCreacion() {
        return maquinaCreacion;
    }

    public void setMaquinaCreacion(String maquinaCreacion) {
        this.maquinaCreacion = maquinaCreacion;
    }

    public String getMaquinaActualiza() {
        return maquinaActualiza;
    }

    public void setMaquinaActualiza(String maquinaActualiza) {
        this.maquinaActualiza = maquinaActualiza;
    }

    public Long getFuncionPublica() {
        return funcionPublica;
    }

    public void setFuncionPublica(Long funcionPublica) {
        this.funcionPublica = funcionPublica;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public BigDecimal getPorcentajeIncremento() {
        return porcentajeIncremento;
    }

    public void setPorcentajeIncremento(BigDecimal porcentajeIncremento) {
        this.porcentajeIncremento = porcentajeIncremento;
    }

    public Date getFechaInicioIncremento() {
        return fechaInicioIncremento;
    }

    public void setFechaInicioIncremento(Date fechaInicioIncremento) {
        this.fechaInicioIncremento = fechaInicioIncremento;
    }

    public Long getNumericoEquivalente() {
        return numericoEquivalente;
    }

    public void setNumericoEquivalente(Long numericoEquivalente) {
        this.numericoEquivalente = numericoEquivalente;
    }

    public String getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(String idNomina) {
        this.idNomina = idNomina;
    }

}
