package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "includeInformacionInformeProyectoGenericoFaces")
@javax.enterprise.context.SessionScoped
public class IncludeInformacionInformeProyectoGenericoFaces extends JSFUtils implements Serializable {

  @EJB
  private IConstantesLocal iConstantesLocal;

  private Proyecto proyectoSeleccionado;
  private String duracionProyecto;

  private BigDecimal valorTotalProyecto;

  private BigDecimal recursosPolicia;

  private BigDecimal recursoFuenteExterna;

  private BigDecimal valorTotalProyectoDenominador;
  private BigDecimal subTotalEjecutadoNumerador;

  @Inject
  private PresupuestoUtilMB presupuestoUtilFaces;

  public Proyecto getProyectoSeleccionado() {
    return proyectoSeleccionado;
  }

  /**
   *
   * @param proyectoSeleccionado
   */
  public void inicializarDatosProyectosMigrados(Proyecto proyectoSeleccionado) {

    subTotalEjecutadoNumerador = null;
    valorTotalProyectoDenominador = null;
    duracionProyecto = null;
    valorTotalProyecto = BigDecimal.ZERO;
    recursosPolicia = BigDecimal.ZERO;
    recursoFuenteExterna = BigDecimal.ZERO;

    this.proyectoSeleccionado = proyectoSeleccionado;

  }

  /**
   *
   * @param proyectoSeleccionado
   */
  public void inicializarDatosProyecto(Proyecto proyectoSeleccionado) {

    try {

      duracionProyecto = null;
      valorTotalProyecto = BigDecimal.ZERO;
      recursosPolicia = BigDecimal.ZERO;
      recursoFuenteExterna = BigDecimal.ZERO;
      subTotalEjecutadoNumerador = BigDecimal.ZERO;
      valorTotalProyectoDenominador = BigDecimal.ZERO;

      this.proyectoSeleccionado = proyectoSeleccionado;

      List<Constantes> listaDuracionProyecto = null;

      if (proyectoSeleccionado.getPeriodo() != null && proyectoSeleccionado.getPeriodo().getTipoPeriodo() == null) {

        listaDuracionProyecto = iConstantesLocal.getConstantesPorTipoPorCodigo(
                IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
                IConstantes.CODIGO_DURACION_PROYECTOS_INSTITUCIONALES);

      } else if (proyectoSeleccionado.getPeriodo() != null && proyectoSeleccionado.getPeriodo().getTipoPeriodo() != null
              && proyectoSeleccionado.getPeriodo().getTipoPeriodo().getIdConstantes().equals(
                      IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION)) {

        listaDuracionProyecto = iConstantesLocal.getConstantesPorTipoPorCodigo(
                IConstantes.DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL,
                IConstantes.CODIGO_DURACION_PROYECTOS_DE_CONVOCATORIA);

      }

      if (listaDuracionProyecto != null && !listaDuracionProyecto.isEmpty()) {

        duracionProyecto = listaDuracionProyecto.get(0).getValor().trim().concat(" Meses ");

      }

      calcularValorTotalProyecto();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "IncludeInformacionInformeProyectoGenericoFaces (setProyectoSeleccionado) ", e);
    }

  }

  /**
   *
   * @throws Exception
   */
  private void calcularValorTotalProyecto() throws Exception {

    presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL, proyectoSeleccionado.getIdProyecto(), null);
    valorTotalProyecto = BigDecimal.valueOf(presupuestoUtilFaces.getTotalTotales());
    recursosPolicia = BigDecimal.valueOf(presupuestoUtilFaces.getTotalFuentesInternas());
    recursoFuenteExterna = BigDecimal.valueOf(presupuestoUtilFaces.getTotalFuentesExternas());

    valorTotalProyectoDenominador = BigDecimal.valueOf(presupuestoUtilFaces.getTotalTotales());

  }

  public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
    this.proyectoSeleccionado = proyectoSeleccionado;
  }

  public BigDecimal getValorTotalProyecto() {
    return valorTotalProyecto;
  }

  public BigDecimal getRecursosPolicia() {
    return recursosPolicia;
  }

  public BigDecimal getRecursoFuenteExterna() {
    return recursoFuenteExterna;
  }

  public String getDuracionProyecto() {

    return duracionProyecto;
  }

  public BigDecimal getValorTotalProyectoDenominador() {
    return valorTotalProyectoDenominador;
  }

  public BigDecimal getSubTotalEjecutadoNumerador() {
    return subTotalEjecutadoNumerador;
  }

  public void setSubTotalEjecutadoNumerador(BigDecimal subTotalEjecutadoNumerador) {
    this.subTotalEjecutadoNumerador = subTotalEjecutadoNumerador;
  }

}
