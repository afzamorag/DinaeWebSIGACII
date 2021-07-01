package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.OtrosEstimulosSemillero;
import co.gov.policia.dinae.modelo.TalentoEstimuloSemillero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cguzman
 */
public class EstimulosSemilleroDTO implements Serializable, IDataModel {

  private Long idOtrosEstimulosSemillero;

  private String motivoOtorgamiento;

  private String descripcionTipoEstimulo;

  private Date fechaOtorgamiento;

  private Date fechaCreacion;

  private String maquina;

  private Date fechaActualizacion;

  private String maquinaActualiza;

  private Long usuarioRol;

  private Long usuarioRolActualiza;

  private Long idSemillero;

  private String participantes;

  private List<String> listaParticipantes;

  public EstimulosSemilleroDTO() {
  }

  public EstimulosSemilleroDTO(Long idOtrosEstimulosSemillero, String motivoOtorgamiento, String descripcionTipoEstimulo, Date fechaOtorgamiento, Date fechaCreacion, String maquina, Date fechaActualizacion, String maquinaActualiza, Long usuarioRol, Long usuarioRolActualiza, Long idSemillero) {
    this.idOtrosEstimulosSemillero = idOtrosEstimulosSemillero;
    this.motivoOtorgamiento = motivoOtorgamiento;
    this.descripcionTipoEstimulo = descripcionTipoEstimulo;
    this.fechaOtorgamiento = fechaOtorgamiento;
    this.fechaCreacion = fechaCreacion;
    this.maquina = maquina;
    this.fechaActualizacion = fechaActualizacion;
    this.maquinaActualiza = maquinaActualiza;
    this.usuarioRol = usuarioRol;
    this.usuarioRolActualiza = usuarioRolActualiza;
    this.idSemillero = idSemillero;
  }

  public EstimulosSemilleroDTO(OtrosEstimulosSemillero estimulosSemillero, List<TalentoEstimuloSemillero> talentos) {
    this.idOtrosEstimulosSemillero = estimulosSemillero.getIdOtrosEstimulosSemillero();
    this.motivoOtorgamiento = estimulosSemillero.getMotivoOtorgamiento();
    this.descripcionTipoEstimulo = estimulosSemillero.getDescripcionTipoEstimulo();
    this.fechaOtorgamiento = estimulosSemillero.getFechaOtorgamiento();
    this.fechaCreacion = estimulosSemillero.getFechaCreacion();
    this.maquina = estimulosSemillero.getMaquina();
    this.fechaActualizacion = estimulosSemillero.getFechaActualizacion();
    this.maquinaActualiza = estimulosSemillero.getMaquinaActualiza();
    this.usuarioRol = estimulosSemillero.getUsuarioRol().getIdUsuarioRol();
    this.usuarioRolActualiza = estimulosSemillero.getUsuarioRolActualiza() == null ? null : estimulosSemillero.getUsuarioRolActualiza().getIdUsuarioRol();
    this.idSemillero = estimulosSemillero.getSemilleroInvestigacion().getIdSemillero();

    this.listaParticipantes = new ArrayList<String>();
    this.participantes = "";

    if (talentos != null && !talentos.isEmpty()) {

      int i = 0;
      for (TalentoEstimuloSemillero talento : talentos) {
        if (i < talentos.size() - 1) {
          this.participantes += talento.getRecursoHumanoSemillero().getNombres() + ", ";
        } else {
          this.participantes += talento.getRecursoHumanoSemillero().getNombres();
        }

        this.listaParticipantes.add(talento.getRecursoHumanoSemillero().getNombres());
        i++;
      }
    }
  }

  public Long getIdOtrosEstimulosSemillero() {
    return idOtrosEstimulosSemillero;
  }

  public void setIdOtrosEstimulosSemillero(Long idOtrosEstimulosSemillero) {
    this.idOtrosEstimulosSemillero = idOtrosEstimulosSemillero;
  }

  public String getMotivoOtorgamiento() {
    return motivoOtorgamiento;
  }

  public void setMotivoOtorgamiento(String motivoOtorgamiento) {
    this.motivoOtorgamiento = motivoOtorgamiento;
  }

  public String getDescripcionTipoEstimulo() {
    return descripcionTipoEstimulo;
  }

  public void setDescripcionTipoEstimulo(String descripcionTipoEstimulo) {
    this.descripcionTipoEstimulo = descripcionTipoEstimulo;
  }

  public Date getFechaOtorgamiento() {
    return fechaOtorgamiento;
  }

  public void setFechaOtorgamiento(Date fechaOtorgamiento) {
    this.fechaOtorgamiento = fechaOtorgamiento;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public Long getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(Long usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Long getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(Long usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public String getParticipantes() {
    return participantes;
  }

  public void setParticipantes(String participantes) {
    this.participantes = participantes;
  }

  public List<String> getListaParticipantes() {
    return listaParticipantes;
  }

  public void setListaParticipantes(List<String> listaParticipantes) {
    this.listaParticipantes = listaParticipantes;
  }

  @Override
  public String getLlaveModel() {
    if (idOtrosEstimulosSemillero == null) {
      return null;
    }

    return idOtrosEstimulosSemillero.toString();
  }
}
