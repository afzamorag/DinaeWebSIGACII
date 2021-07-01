package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EvaluacionDesarrolloPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2811243523285989657L;

    @Column(name = "EVDE_EVAL")
    private Long idEvaluacion;

    @Column(name = "EVDE_PARE")
    private Long idParticipanteEvento;

    @Column(name = "EVDE_PREG")
    private Long idPregunta;

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public Long getIdParticipanteEvento() {
		return idParticipanteEvento;
	}

	public void setIdParticipanteEvento(Long idParticipanteEvento) {
		this.idParticipanteEvento = idParticipanteEvento;
	}

	public Long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Long idPregunta) {
		this.idPregunta = idPregunta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idEvaluacion == null) ? 0 : idEvaluacion.hashCode());
		result = prime
				* result
				+ ((idParticipanteEvento == null) ? 0 : idParticipanteEvento
						.hashCode());
		result = prime * result
				+ ((idPregunta == null) ? 0 : idPregunta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EvaluacionDesarrolloPK)) {
			return false;
		}
		EvaluacionDesarrolloPK other = (EvaluacionDesarrolloPK) obj;
		if (idEvaluacion == null) {
			if (other.idEvaluacion != null) {
				return false;
			}
		} else if (!idEvaluacion.equals(other.idEvaluacion)) {
			return false;
		}
		if (idParticipanteEvento == null) {
			if (other.idParticipanteEvento != null) {
				return false;
			}
		} else if (!idParticipanteEvento.equals(other.idParticipanteEvento)) {
			return false;
		}
		if (idPregunta == null) {
			if (other.idPregunta != null) {
				return false;
			}
		} else if (!idPregunta.equals(other.idPregunta)) {
			return false;
		}
		return true;
	}
    
    

}
