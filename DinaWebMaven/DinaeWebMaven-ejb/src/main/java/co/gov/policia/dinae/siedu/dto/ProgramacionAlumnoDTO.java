package co.gov.policia.dinae.siedu.dto;

import co.gov.policia.dinae.siedu.modelo.PeriodosAlumno;
import co.gov.policia.dinae.siedu.modelo.ProgramasPensums;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class ProgramacionAlumnoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -925339302380059355L;

    private BigDecimal idProgalumno;
    private BigDecimal parcial1;
    private BigDecimal parcial2;
    private BigDecimal definitiva;
    private String habilita;
    private String maquinaCreacion;
    private String actualizadoPor;
    private Date fechaActualiza;
    private BigDecimal parcial3;
    private ProgramasPensums prpnIdProgpensum;
    private PeriodosAlumno pealIdPeriodo;
    private String nombres;
    private String apellidos;
    private String identificacion;
    
    public ProgramacionAlumnoDTO() {             
    }

    public ProgramacionAlumnoDTO(BigDecimal idProgalumno, BigDecimal parcial1, BigDecimal parcial2, BigDecimal definitiva, String habilita, String maquinaCreacion, String actualizadoPor, Date fechaActualiza, BigDecimal parcial3, ProgramasPensums prpnIdProgpensum, PeriodosAlumno pealIdPeriodo, String nombres, String apellidos, String identificacion) {
        super();
        this.idProgalumno = idProgalumno;
        this.parcial1 = parcial1;
        this.parcial2 = parcial2;
        this.definitiva = definitiva;
        this.habilita = habilita;
        this.maquinaCreacion = maquinaCreacion;
        this.actualizadoPor = actualizadoPor;
        this.fechaActualiza = fechaActualiza;
        this.parcial3 = parcial3;
        this.prpnIdProgpensum = prpnIdProgpensum;
        this.pealIdPeriodo = pealIdPeriodo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
    }   

    public BigDecimal getIdProgalumno() {
        return idProgalumno;
    }

    public void setIdProgalumno(BigDecimal idProgalumno) {
        this.idProgalumno = idProgalumno;
    }

    public BigDecimal getParcial1() {
        return parcial1;
    }

    public void setParcial1(BigDecimal parcial1) {
        this.parcial1 = parcial1;
    }

    public BigDecimal getParcial2() {
        return parcial2;
    }

    public void setParcial2(BigDecimal parcial2) {
        this.parcial2 = parcial2;
    }

    public BigDecimal getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(BigDecimal definitiva) {
        this.definitiva = definitiva;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public String getMaquinaCreacion() {
        return maquinaCreacion;
    }

    public void setMaquinaCreacion(String maquinaCreacion) {
        this.maquinaCreacion = maquinaCreacion;
    }

    public String getActualizadoPor() {
        return actualizadoPor;
    }

    public void setActualizadoPor(String actualizadoPor) {
        this.actualizadoPor = actualizadoPor;
    }

    public Date getFechaActualiza() {
        return fechaActualiza;
    }

    public void setFechaActualiza(Date fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    public BigDecimal getParcial3() {
        return parcial3;
    }

    public void setParcial3(BigDecimal parcial3) {
        this.parcial3 = parcial3;
    }

    public ProgramasPensums getPrpnIdProgpensum() {
        return prpnIdProgpensum;
    }

    public void setPrpnIdProgpensum(ProgramasPensums prpnIdProgpensum) {
        this.prpnIdProgpensum = prpnIdProgpensum;
    }

    public PeriodosAlumno getPealIdPeriodo() {
        return pealIdPeriodo;
    }

    public void setPealIdPeriodo(PeriodosAlumno pealIdPeriodo) {
        this.pealIdPeriodo = pealIdPeriodo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
        
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idProgalumno);
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
        final ProgramacionAlumnoDTO other = (ProgramacionAlumnoDTO) obj;
        if (!Objects.equals(this.idProgalumno, other.idProgalumno)) {
            return false;
        }
        return true;
    }    

}
