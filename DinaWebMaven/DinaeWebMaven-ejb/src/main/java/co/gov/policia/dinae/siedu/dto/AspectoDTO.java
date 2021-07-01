package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;
import java.util.List;

import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacionPK;

public class AspectoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7320657153164110027L;

	private PreguntaEvaluacionPK preguntaEvaluacionPK;
	private Long idParametroEvaluacion;
	private String descripcion;
	private List<PreguntaDTO> preguntasDTO;

	public PreguntaEvaluacionPK getPreguntaEvaluacionPK() {
		return preguntaEvaluacionPK;
	}

	public void setPreguntaEvaluacionPK(
			PreguntaEvaluacionPK preguntaEvaluacionPK) {
		this.preguntaEvaluacionPK = preguntaEvaluacionPK;
	}

	public List<PreguntaDTO> getPreguntasDTO() {
		return preguntasDTO;
	}

	public void setPreguntasDTO(List<PreguntaDTO> preguntasDTO) {
		this.preguntasDTO = preguntasDTO;
	}

	public Long getIdParametroEvaluacion() {
		return idParametroEvaluacion;
	}

	public void setIdParametroEvaluacion(Long idParametroEvaluacion) {
		this.idParametroEvaluacion = idParametroEvaluacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
