package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;
import java.util.Objects;

public class EventoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -925339302380059355L;

    private Long idCarrera;
    private Long fuerza;
    private String descripcionCarrera;
    private Long idEvento;
    private Long idModalidad;
    private Long idProceso;
    private Long idParticipanteEvento;

    public EventoDTO() {
    }

    public EventoDTO(Long idEvento) {
        this.idEvento = idEvento;
    }

    public EventoDTO(Long idCarrera, Long fuerza, String descripcionCarrera,
            Long idEvento, Long idModalidad, Long idProceso,
            Long idParticipanteEvento) {
        super();
        this.idCarrera = idCarrera;
        this.fuerza = fuerza;
        this.descripcionCarrera = descripcionCarrera;
        this.idEvento = idEvento;
        this.idModalidad = idModalidad;
        this.idProceso = idProceso;
        this.idParticipanteEvento = idParticipanteEvento;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Long getFuerza() {
        return fuerza;
    }

    public void setFuerza(Long fuerza) {
        this.fuerza = fuerza;
    }

    public String getDescripcionCarrera() {
        return descripcionCarrera;
    }

    public void setDescripcionCarrera(String descripcionCarrera) {
        this.descripcionCarrera = descripcionCarrera;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Long idModalidad) {
        this.idModalidad = idModalidad;
    }

    public Long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    public Long getIdParticipanteEvento() {
        return idParticipanteEvento;
    }

    public void setIdParticipanteEvento(Long idParticipanteEvento) {
        this.idParticipanteEvento = idParticipanteEvento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.idEvento);
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
        final EventoDTO other = (EventoDTO) obj;
        if (!Objects.equals(this.idEvento, other.idEvento)) {
            return false;
        }
        return true;
    }

}
