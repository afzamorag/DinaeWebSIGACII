/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author ANDRES.ZAMORAG
 */
@Entity
public class SieduHorasDocenteEventoDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String HORAS_DICTADAS_EVENTO = "SELECT DISTINCT D.DOCE_EVEE AS idEvento, D.DOCE_TEMA AS idTema, T.TEMA_HORAS AS horasTema FROM SIEDU_DOCENTE_EVENTO D, SIEDU_TEMA T WHERE D.DOCE_TEMA = T.TEMA_TEMA AND D.DOCE_EVEE = ?1";

  private Long idEvento;
  @Id
  private Long idTema;
  private Short horasTema;

  public Long getIdEvento() {
    return idEvento;
  }

  public void setIdEvento(Long idEvento) {
    this.idEvento = idEvento;
  }

  public Long getIdTema() {
    return idTema;
  }

  public void setIdTema(Long idTema) {
    this.idTema = idTema;
  }

  public Short getHorasTema() {
    return horasTema;
  }

  public void setHorasTema(Short horasTema) {
    this.horasTema = horasTema;
  }

}
