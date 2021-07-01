/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
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
//@Entity
//@Table(name = "SIEDU_EVAL_CATEGORIA")
//@NamedQueries({
//		@NamedQuery(name = SieduEvalCategoria.FIND_BY_EVALUATION, query = "SELECT ec FROM SieduEvalCategoria ec WHERE ec.evaluacion.id = :idEvaluacion"),
//		@NamedQuery(name = SieduEvalCategoria.DELETE_BY_EVALUATION, query = "DELETE FROM SieduEvalCategoria ec WHERE ec.evaluacion.id = :idEvaluacion") })
public class SieduEvalCategoria implements Serializable {

	private static final long serialVersionUID = -6490514327074833776L;

//	public static final String FIND_BY_EVALUATION = "SieduEvalCategoria.findByEvaluation";
//	public static final String DELETE_BY_EVALUATION = "SieduEvalCategoria.deleteByEvaluation";

	@EmbeddedId
	private SiudeEvalCategoriaPK id;

	@Column(name = "EVCR_USU_CREA")
	private String usuarioCrea;

	@Column(name = "EVCR_FECHA_CREA")
	@Temporal(TemporalType.DATE)
	private Date fechaCrea;

	@Column(name = "EVCR_MAQUINA_CREA")
	private String maquinaCrea;

	@Column(name = "EVCR_IP_CREA")
	private String ipCrea;

	@Column(name = "EVCR_USU_MOD")
	private String usuarioModifica;

	@Column(name = "EVCR_FECHA_MOD")
	@Temporal(TemporalType.DATE)
	private Date fechaModifica;

	@Column(name = "EVCR_MAQUINA_MOD")
	private String maquinaModifica;

	@Column(name = "EVCR_IP_MOD")
	private String ipModica;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "EVCR_FUERZA", referencedColumnName = "FUERZA", insertable = false, updatable = false),
			@JoinColumn(name = "EVCR_ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA", insertable = false, updatable = false) })
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "EVCR_EVAL", insertable = false, updatable = false)
	private Evaluacion evaluacion;

	public String getUsuarioCrea() {
		return usuarioCrea;
	}

	public void setUsuarioCrea(String usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public Date getFechaCrea() {
		return fechaCrea;
	}

	public void setFechaCrea(Date fechaCrea) {
		this.fechaCrea = fechaCrea;
	}

	public String getMaquinaCrea() {
		return maquinaCrea;
	}

	public void setMaquinaCrea(String maquinaCrea) {
		this.maquinaCrea = maquinaCrea;
	}

	public String getIpCrea() {
		return ipCrea;
	}

	public void setIpCrea(String ipCrea) {
		this.ipCrea = ipCrea;
	}

	public String getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public String getMaquinaModifica() {
		return maquinaModifica;
	}

	public void setMaquinaModifica(String maquinaModifica) {
		this.maquinaModifica = maquinaModifica;
	}

	public String getIpModica() {
		return ipModica;
	}

	public void setIpModica(String ipModica) {
		this.ipModica = ipModica;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public SiudeEvalCategoriaPK getId() {
		return id;
	}

	public void setId(SiudeEvalCategoriaPK id) {
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
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SieduEvalCategoria)) {
			return false;
		}
		SieduEvalCategoria other = (SieduEvalCategoria) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "co.gov.policia.dinae.siedu.modelo.SieduEvalCategoria[ id=" + id
				+ " ]";
	}

}
