package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;
import java.util.Objects;

public class SeguimientoPAEDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -925339302380059355L;
    
    private String escuela;
    private String estrategia;
    private int meta; 
    private int total;

    public SeguimientoPAEDTO() {
    }

    public SeguimientoPAEDTO(String escuela, String estrategia, int meta, int total) {
        super();
        this.escuela = escuela;
        this.estrategia = estrategia;
        this.meta = meta;
        this.total = total;
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

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.escuela);
        hash = 47 * hash + Objects.hashCode(this.estrategia);
        hash = 47 * hash + Objects.hashCode(this.meta);
        hash = 47 * hash + Objects.hashCode(this.total);
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
        final SeguimientoPAEDTO other = (SeguimientoPAEDTO) obj;
        if (!Objects.equals(this.escuela, other.escuela)) {
            return false;
        }
        if (!Objects.equals(this.estrategia, other.estrategia)) {
            return false;
        }
        if (!Objects.equals(this.meta, other.meta)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SeguimientoPAEDTO{" + "escuela=" + escuela + ", estrategia=" + estrategia + ", meta=" + meta + ", total=" + total + '}';
    }   

}
