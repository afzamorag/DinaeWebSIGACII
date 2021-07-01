package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoEjecutadoImplLocal;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoEjecutadoLocal;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoImplLocal;
import co.gov.policia.dinae.interfaces.IResumenPresupuestoProyectoLocal;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutado;
import co.gov.policia.dinae.modelo.ResumenPresupuestoEjecutadoImpl;
import co.gov.policia.dinae.modelo.ResumenPresupuestoImpl;
import co.gov.policia.dinae.modelo.ResumenPresupuestoProyecto;
import co.gov.policia.dinae.util.DatosCalculoPresupuesto;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.PresupuestoUtil;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.component.html.HtmlOutputLabel;
import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.row.Row;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "presupuestoUtilFaces")
@javax.enterprise.context.SessionScoped
public class PresupuestoUtilMB extends JSFUtils implements Serializable {

  @EJB
  private IResumenPresupuestoProyectoLocal iResumenPresupuestoProyectoLocal;

  @EJB
  private IResumenPresupuestoImplLocal iResumenPresupuestoImpl;

  @EJB
  private ICompromisoImplementacionLocal iCompromisoImpl;

  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;

  @EJB
  private IPlanTrabajoImplementacionLocal iPlanTrabajoImplementacion;

  @EJB
  private IFuenteProyectoLocal iFuente;

  @EJB
  private IResumenPresupuestoEjecutadoLocal _iResumenPresupuestoEjecutado;

  @EJB
  private IResumenPresupuestoEjecutadoImplLocal _iResumenPresupuestoEjecutadoImpl;

  private DataTable _dataTablePresupuesto;

  private DataTable _dataTablePresupuestoAcumulado;

  private List<FuenteProyecto> _listaFuentesProyecto;

  private List<String> _porcentajesPresupuesto;

  private Double totalTotales = 0D;

  private Double totalFuentesInternas;

  private Double totalFuentesExternas;

  private boolean _mostrarPresupuestoAcumulado;

  private String _tituloPresupuesto;

  private DatosCalculoPresupuesto datoPorcentajeFooterPresupueso;

  private final DecimalFormat df = new DecimalFormat(keyPropertiesFactory.value("general_formato_valor_cifra_sin_decimal"));

  private static final String[] NOMBRE_RUBROS = {"PERSONAL", "EQUIPOS Y SOFTWARE", "EVENTOS", "VIAJES", "GASTOS ADMINISTRATIVOS", "PUBLICACIONES", "BIBLIOGRAFIA Y DOCUMENTACION", "TOTAL"};

  private Long idProyectoVersion;

  private Double rubroPresupuestadoPoliciaTotal = 0D;
  private Double rubroEjecutadoPoliciaTotal = 0D;
  private Double rubroPresupuestadoVicinTotal = 0D;
  private Double rubroEjecutadoVicinTotal = 0D;
  private Double rubroPresupuestadoUnidadTotal = 0D;
  private Double rubroEjecutadoUnidadTotal = 0D;

  private Double rubroPresupuestadoFuente1Total = 0D;
  private Double rubroEjecutadoFuente1Total = 0D;
  private Double rubroPresupuestadoFuente2Total = 0D;
  private Double rubroEjecutadoFuente2Total = 0D;
  private Double rubroPresupuestadoFuente3Total = 0D;
  private Double rubroEjecutadoFuente3Total = 0D;
  private Double rubroPresupuestadoFuente4Total = 0D;
  private Double rubroEjecutadoFuente4Total = 0D;
  private Double rubroSubtotalPresupuestadoTotal = 0D;
  private Double rubroSubtotalEjecutadoTotal = 0D;

  /**
   *
   * @param tipo
   * @param proyectoSeleccionadoId
   * @param informeAvanceSeleccionId
   * @param idProyectoVersion
   */
  public void construirPresupuesto(String tipo, Long proyectoSeleccionadoId, Long informeAvanceSeleccionId, Long idProyectoVersion) {

    this._dataTablePresupuesto = null;
    this._dataTablePresupuestoAcumulado = null;
    this.idProyectoVersion = idProyectoVersion;

    if (tipo != null && !"".equals(tipo.trim())) {

      construirPresupuestoTipo(tipo, proyectoSeleccionadoId, informeAvanceSeleccionId);
    }
  }

  /**
   *
   * @param tipo
   * @param proyectoSeleccionadoId
   * @param informeAvanceSeleccionId
   */
  public void construirPresupuesto(String tipo, Long proyectoSeleccionadoId, Long informeAvanceSeleccionId) {

    this._dataTablePresupuesto = null;
    this._dataTablePresupuestoAcumulado = null;
    idProyectoVersion = null;

    if (tipo != null && !"".equals(tipo.trim())) {

      construirPresupuestoTipo(tipo, proyectoSeleccionadoId, informeAvanceSeleccionId);
    }
  }

  /**
   *
   * @param tipo
   * @param proyectoSeleccionadoId
   * @param informeAvanceSeleccionId
   */
  private void construirPresupuestoTipo(String tipo, Long proyectoSeleccionadoId, Long informeAvanceSeleccionId) {

    try {

      String nombreColumna1;
      String nombreColumna2;
      _mostrarPresupuestoAcumulado = false;

      List<DatosCalculoPresupuesto> datosCalculoPresupuesto;
      List<DatosCalculoPresupuesto> datosCalculoPresupuestoAcum;
      _listaFuentesProyecto = iFuente.getListaFuentesPorProyecto(proyectoSeleccionadoId);

      if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL.equals(tipo)
              || PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL_VERSION.equals(tipo)) {

        nombreColumna1 = "Especie";
        nombreColumna2 = "Efectivo";
        _tituloPresupuesto = "Presupuesto global";

        if (proyectoSeleccionadoId == null) {
          throw new JpaDinaeException("El identificador del proyecto es necesario para generar el presupuesto global");
        }

        datosCalculoPresupuesto = cargarDatosCalculoPresupuestoGlobal(tipo, proyectoSeleccionadoId);
        if (datosCalculoPresupuesto == null) {
          datosCalculoPresupuesto = construirPresupuestoDummy();
        }
        DataTable result = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuesto);
        setDataTablePresupuesto(result);

      } else if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_EJECUTA.equals(tipo)) {

        nombreColumna1 = "Presupuestado";
        nombreColumna2 = "Ejecutado";
        _tituloPresupuesto = "Presupuesto ejecutado";

        if (proyectoSeleccionadoId == null) {
          throw new JpaDinaeException("El identificador del proyecto es necesario para generar el presupuesto ejecutado");
        }

        if (informeAvanceSeleccionId == null) {
          throw new JpaDinaeException("El identificador del informe es necesario para generar el presupuesto ejecutado");
        }

        datosCalculoPresupuesto = cargarDatosCalculoPresupuestoEjecutado(proyectoSeleccionadoId, informeAvanceSeleccionId, false);
        if (datosCalculoPresupuesto == null) {
          datosCalculoPresupuesto = construirPresupuestoDummy();
        }
        DataTable result = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuesto);
        setDataTablePresupuesto(result);

      } else if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM.equals(tipo)) {

        nombreColumna1 = "Presupuestado";
        nombreColumna2 = "Ejecutado";
        _tituloPresupuesto = "Presupuesto ejecutado";

        if (proyectoSeleccionadoId == null) {
          throw new JpaDinaeException("El identificador del proyecto es necesario para generar el presupuesto ejecutado");
        }

        if (informeAvanceSeleccionId == null) {
          throw new JpaDinaeException("El identificador del informe es necesario para generar el presupuesto ejecutado");
        }

        datosCalculoPresupuesto = cargarDatosCalculoPresupuestoEjecutado(proyectoSeleccionadoId, informeAvanceSeleccionId, false);
        if (datosCalculoPresupuesto == null) {
          datosCalculoPresupuesto = construirPresupuestoDummy();
        }

        DataTable result = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuesto);
        setDataTablePresupuesto(result);

        datosCalculoPresupuestoAcum = cargarDatosCalculoPresupuestoEjecutado(proyectoSeleccionadoId, informeAvanceSeleccionId, true);
        if (datosCalculoPresupuestoAcum == null) {
          datosCalculoPresupuestoAcum = construirPresupuestoDummy();
        }
        DataTable result2 = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuestoAcum);
        setDataTablePresupuestoAcumulado(result2);
        _mostrarPresupuestoAcumulado = true;

      } else if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL_IMPL.equals(tipo)
              || PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL_IMPL_VERSION.equals(tipo)) {

        nombreColumna1 = "Especie";
        nombreColumna2 = "Efectivo";
        _tituloPresupuesto = "Presupuesto global";

        if (proyectoSeleccionadoId == null) {
          throw new JpaDinaeException("El identificador del proyecto es necesario para generar el presupuesto global");
        }

        datosCalculoPresupuesto = cargarDatosCalculoPresupuestoGlobalImpl(tipo, proyectoSeleccionadoId);
        if (datosCalculoPresupuesto == null) {
          datosCalculoPresupuesto = construirPresupuestoDummy();
        }
        DataTable result = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuesto);
        setDataTablePresupuesto(result);

      } else if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_EJECUTA_IMPL.equals(tipo)) {

        nombreColumna1 = "Presupuestado";
        nombreColumna2 = "Ejecutado";
        _tituloPresupuesto = "Presupuesto ejecutado";

        if (proyectoSeleccionadoId == null) {
          throw new JpaDinaeException("El identificador del proyecto es necesario para generar el presupuesto ejecutado");
        }

        if (informeAvanceSeleccionId == null) {
          throw new JpaDinaeException("El identificador del informe es necesario para generar el presupuesto ejecutado");
        }

        datosCalculoPresupuesto = cargarDatosCalculoPresupuestoEjecutadoImpl(proyectoSeleccionadoId, informeAvanceSeleccionId, false);
        if (datosCalculoPresupuesto == null) {
          datosCalculoPresupuesto = construirPresupuestoDummy();
        }
        DataTable result = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuesto);
        setDataTablePresupuesto(result);

      } else if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM_IMPL.equals(tipo)) {

        nombreColumna1 = "Presupuestado";
        nombreColumna2 = "Ejecutado";
        _tituloPresupuesto = "Presupuesto ejecutado";

        if (proyectoSeleccionadoId == null) {
          throw new JpaDinaeException("El identificador del proyecto es necesario para generar el presupuesto ejecutado");
        }

        if (informeAvanceSeleccionId == null) {
          throw new JpaDinaeException("El identificador del informe es necesario para generar el presupuesto ejecutado");
        }

        datosCalculoPresupuesto = cargarDatosCalculoPresupuestoEjecutadoImpl(proyectoSeleccionadoId, informeAvanceSeleccionId, false);
        if (datosCalculoPresupuesto == null) {
          datosCalculoPresupuesto = construirPresupuestoDummy();
        }

        DataTable result = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuesto);
        setDataTablePresupuesto(result);

        datosCalculoPresupuestoAcum = cargarDatosCalculoPresupuestoEjecutadoImpl(proyectoSeleccionadoId, null, true);
        if (datosCalculoPresupuestoAcum == null) {
          datosCalculoPresupuestoAcum = construirPresupuestoDummy();
        }
        DataTable result2 = construirReportePresupuesto(tipo, nombreColumna1, nombreColumna2, datosCalculoPresupuestoAcum);
        setDataTablePresupuestoAcumulado(result2);
        _mostrarPresupuestoAcumulado = true;

      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
      Logger.getLogger(PresupuestoUtilMB.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param tipoPresupuesto
   * @param nombreColumna1
   * @param nombreColumna2
   * @param listaDatosCalculoPresupuesto
   * @return
   * @throws JpaDinaeException
   */
  private DataTable construirReportePresupuesto(String tipoPresupuesto, String nombreColumna1, String nombreColumna2, List<DatosCalculoPresupuesto> listaDatosCalculoPresupuesto) throws JpaDinaeException {

    DataTable dataTable = new DataTable();

    //EL VALUE DEL DATATABLE SERA EL NUMERO DE REGISTRO
    //LO QUE NOS INTERESA ES QUE EL DATATABLE HAGA EL RECORRIDO DEL NUMERO DE REGITROS
    //LOS VALORES DE LOS REGISTROS SERAN QUEMADOS EN ESTE METODO
    //SIGNIFICA QUE LOS VALORES: ESTAN LISTO PARA PRESENTAR AL USUARIO
    dataTable.setVar("dummy");
    dataTable.setValue(listaDatosCalculoPresupuesto);

    //COL: N°
    Column conteo = new Column();
    conteo.setRowspan(2);
    conteo.setHeaderText("N°");
    conteo.setStyle("color:white; font-size: 13px");

    //COL: Rubros
    Column columnaRubros = new Column();
    columnaRubros.setRowspan(2);
    columnaRubros.setHeaderText("Rubros");
    columnaRubros.setStyle("color:white; font-size: 13px");

    Row filaEncabezadoSuperior = new Row();
    filaEncabezadoSuperior.getChildren().add(conteo);
    filaEncabezadoSuperior.getChildren().add(columnaRubros);

    Row filaEncabezadoInferior = new Row();

    Row filaEncabezadoPiepagina = new Row();
    Column columnaTotalPie = new Column();
    columnaTotalPie.setColspan(2);
    columnaTotalPie.setFooterText("%");
    columnaTotalPie.setStyle("text-align:right; color:white; font-size: 13px");

    filaEncabezadoPiepagina.getChildren().add(columnaTotalPie);

    ValueExpression valExprNumero = getExpressionFactory().createValueExpression(getELContext(), "#{dummy.numero}", String.class);
    ValueExpression valExprRubro = getExpressionFactory().createValueExpression(getELContext(), "#{dummy.nombreRubro}", String.class);

    HtmlOutputLabel labelCont = new HtmlOutputLabel();
    labelCont.setValueExpression("value", valExprNumero);
    labelCont.setStyle("font-size: 13px");

    Column columnaNumero = new Column();
    columnaNumero.getChildren().add(labelCont);

    HtmlOutputLabel labelRubro = new HtmlOutputLabel();
    labelRubro.setValueExpression("value", valExprRubro);
    labelRubro.setStyleClass("alineadoIzquierda gris negrilla");
    labelRubro.setStyle("font-size: 13px");

    Column columnaRubro = new Column();
    columnaRubro.getChildren().add(labelRubro);

    dataTable.getChildren().add(columnaNumero);
    dataTable.getChildren().add(columnaRubro);

    int index = -1;

    if (_listaFuentesProyecto != null) {

      ValueExpression valExprStyleClass = getExpressionFactory().createValueExpression(getELContext(), "#{dummy.style}", String.class);

      for (FuenteProyecto fuente : _listaFuentesProyecto) {

        index++;

        String expresionEfectivo = null;
        String expresionEspecie = null;
        String expresionPorcentajeEfectivo = null;
        String expresionPorcentajeEspecie = null;

        if (index == 0) {

          expresionEfectivo = "#{dummy.valorEfectivoPolicialNacional}";
          expresionEspecie = "#{dummy.valorEspeciePolicialNacional}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoPoliciaNacional();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspeciePoliciaNacional();

        } else if (index == 1) {

          expresionEfectivo = "#{dummy.valorEfectivoPolicialDINAE}";
          expresionEspecie = "#{dummy.valorEspeciePolicialDINAE}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoPolicialDINAE();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspeciePolicialDINAE();

        } else if (index == 2) {

          expresionEfectivo = "#{dummy.valorEfectivoUnidadPolicial}";
          expresionEspecie = "#{dummy.valorEspecieUnidadPolicial}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoUnidadPolicial();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspecieUnidadPolicial();

        } else if (index == 3) {

          expresionEfectivo = "#{dummy.valorEfectivoAdicional1}";
          expresionEspecie = "#{dummy.valorEspecieAdicional1}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoAdicional1();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspecieAdicional1();

        } else if (index == 4) {

          expresionEfectivo = "#{dummy.valorEfectivoAdicional2}";
          expresionEspecie = "#{dummy.valorEspecieAdicional2}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoAdicional2();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspecieAdicional2();

        } else if (index == 5) {

          expresionEfectivo = "#{dummy.valorEfectivoAdicional3}";
          expresionEspecie = "#{dummy.valorEspecieAdicional3}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoAdicional3();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspecieAdicional3();

        } else if (index == 6) {

          expresionEfectivo = "#{dummy.valorEfectivoAdicional4}";
          expresionEspecie = "#{dummy.valorEspecieAdicional4}";
          expresionPorcentajeEfectivo = datoPorcentajeFooterPresupueso.getPorcentajeEfectivoAdicional4();
          expresionPorcentajeEspecie = datoPorcentajeFooterPresupueso.getPorcentajeEspecieAdicional4();

        }

        ValueExpression valExprEfectivo = getExpressionFactory().createValueExpression(getELContext(), expresionEfectivo, String.class);
        ValueExpression valExprEspecie = getExpressionFactory().createValueExpression(getELContext(), expresionEspecie, String.class);

        Column columnaFuente = new Column();
        columnaFuente.setColspan(2);
        columnaFuente.setHeaderText(fuente.getNombreFuente());
        columnaFuente.setStyle("color:white; font-size: 13px");

        filaEncabezadoSuperior.getChildren().add(columnaFuente);

        Column columnaEspecie = new Column();
        columnaEspecie.setHeaderText(nombreColumna1);
        columnaEspecie.setStyle("color:white; font-size: 13px");

        Column columnaEfectivo = new Column();
        columnaEfectivo.setHeaderText(nombreColumna2);
        columnaEfectivo.setStyle("color:white; font-size: 13px");

        filaEncabezadoInferior.getChildren().add(columnaEspecie);
        filaEncabezadoInferior.getChildren().add(columnaEfectivo);

        HtmlOutputLabel labelColumnaDatosEspecie = new HtmlOutputLabel();
        labelColumnaDatosEspecie.setValueExpression("value", valExprEspecie);
        labelColumnaDatosEspecie.setValueExpression("styleClass", valExprStyleClass);
        labelColumnaDatosEspecie.setStyle("font-size: 13px");

        Column columnaDatosEspecie = new Column();
        columnaDatosEspecie.getChildren().add(labelColumnaDatosEspecie);

        HtmlOutputLabel labelColumnaDatosEfectivo = new HtmlOutputLabel();
        labelColumnaDatosEfectivo.setValueExpression("value", valExprEfectivo);
        labelColumnaDatosEfectivo.setValueExpression("styleClass", valExprStyleClass);
        labelColumnaDatosEfectivo.setStyle("font-size: 13px");

        Column columnaDatosEfectivo = new Column();
        columnaDatosEfectivo.getChildren().add(labelColumnaDatosEfectivo);

        dataTable.getChildren().add(columnaDatosEspecie);
        dataTable.getChildren().add(columnaDatosEfectivo);

        Column columnaPorcentajeEspecie = new Column();
        columnaPorcentajeEspecie.setFooterText(expresionPorcentajeEspecie);
        columnaPorcentajeEspecie.setStyle("color:white; font-size: 13px");

        filaEncabezadoPiepagina.getChildren().add(columnaPorcentajeEspecie);

        Column columnaPorcentajeEfectivo = new Column();
        columnaPorcentajeEfectivo.setFooterText(expresionPorcentajeEfectivo);
        columnaPorcentajeEfectivo.setStyle("color:white; font-size: 13px");

        filaEncabezadoPiepagina.getChildren().add(columnaPorcentajeEfectivo);

      }

      // COLUMNA SUBTOTAL : APLICA PARA PRESUPUESTO EJECUTADO Y PRESUPUESTO ACUMULADO.
      if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_EJECUTA.equals(tipoPresupuesto)
              || PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_ACUM.equals(tipoPresupuesto)) {

        Column columnaFuente = new Column();
        columnaFuente.setColspan(2);
        columnaFuente.setHeaderText("Subtotal");
        columnaFuente.setStyle("color:white; font-size: 13px");

        filaEncabezadoSuperior.getChildren().add(columnaFuente);

        Column columnaEspecie = new Column();
        columnaEspecie.setHeaderText(nombreColumna1);
        columnaEspecie.setStyle("color:white; font-size: 13px");

        Column columnaEfectivo = new Column();
        columnaEfectivo.setHeaderText(nombreColumna2);
        columnaEfectivo.setStyle("color:white; font-size: 13px");

        filaEncabezadoInferior.getChildren().add(columnaEspecie);
        filaEncabezadoInferior.getChildren().add(columnaEfectivo);

        ValueExpression valExprEspecie = getExpressionFactory().createValueExpression(getELContext(), "#{dummy.valorSubtotalRubroPresupuestado}", String.class);

        HtmlOutputLabel labelColumnaDatosEspecie = new HtmlOutputLabel();
        labelColumnaDatosEspecie.setValueExpression("value", valExprEspecie);
        labelColumnaDatosEspecie.setValueExpression("styleClass", valExprStyleClass);
        labelColumnaDatosEspecie.setStyle("text-align:right; font-size: 13px");

        Column columnaEspecieTotal = new Column();
        columnaEspecieTotal.setFooterText(datoPorcentajeFooterPresupueso.getPorcentajeSubtotalRubroPresupuestado());
        columnaEspecieTotal.setStyle("color:white; font-size: 13px");

        filaEncabezadoPiepagina.getChildren().add(columnaEspecieTotal);

        Column columnaDatosEspecie = new Column();
        columnaDatosEspecie.getChildren().add(labelColumnaDatosEspecie);

        ValueExpression valExprEfectivo = getExpressionFactory().createValueExpression(getELContext(), "#{dummy.valorSubtotalRubroEjecutado}", String.class);

        HtmlOutputLabel labelColumnaDatosEfectivo = new HtmlOutputLabel();
        labelColumnaDatosEfectivo.setValueExpression("value", valExprEfectivo);
        labelColumnaDatosEspecie.setValueExpression("styleClass", valExprStyleClass);
        labelColumnaDatosEfectivo.setStyle("text-align:right; font-size: 13px");

        Column columnaEfectivoTotal = new Column();
        columnaEfectivoTotal.setFooterText(datoPorcentajeFooterPresupueso.getPorcentajeSubtotalRubroEjecutado());
        columnaEfectivoTotal.setStyle("color:white; font-size: 13px");

        filaEncabezadoPiepagina.getChildren().add(columnaEfectivoTotal);

        Column columnaDatosEfectivo = new Column();
        columnaDatosEfectivo.getChildren().add(labelColumnaDatosEfectivo);

        dataTable.getChildren().add(columnaDatosEspecie);
        dataTable.getChildren().add(columnaDatosEfectivo);
      }
    }

    //COLUMNA TOTAL (ULTIMA COLUMNA)
    ValueExpression valExprTotal = getExpressionFactory().createValueExpression(getELContext(), "#{dummy.totalFila}", String.class);

    HtmlOutputLabel labelColumnaTotal = new HtmlOutputLabel();
    labelColumnaTotal.setValueExpression("value", valExprTotal);
    labelColumnaTotal.setStyleClass("alineadoDerecha gris negrilla");
    labelColumnaTotal.setStyle("font-size: 13px");

    Column columnaTotal = new Column();
    columnaTotal.getChildren().add(labelColumnaTotal);

    dataTable.getChildren().add(columnaTotal);

    //ULTIMA FILA - TOTAL
    Column columnaTotales = new Column();
    columnaTotales.setRowspan(2);
    columnaTotales.setHeaderText("Total");
    columnaTotales.setStyle("color:white; font-size: 13px");

    filaEncabezadoSuperior.getChildren().add(columnaTotales);

    Column columnaTotalTotal = new Column();
    columnaTotalTotal.setFooterText("100%");
    columnaTotalTotal.setStyle("color:white; font-size: 13px");

    filaEncabezadoPiepagina.getChildren().add(columnaTotalTotal);

    ColumnGroup grupoColumnas = new ColumnGroup();
    grupoColumnas.setType("header");

    grupoColumnas.getChildren().add(filaEncabezadoSuperior);
    grupoColumnas.getChildren().add(filaEncabezadoInferior);

    ColumnGroup grupoPiePagina = new ColumnGroup();
    grupoPiePagina.setType("footer");

    grupoPiePagina.getChildren().add(filaEncabezadoPiepagina);

    dataTable.getChildren().add(grupoColumnas);
    dataTable.getChildren().add(grupoPiePagina);

    dataTable.setStyleClass("tabla");
    dataTable.setRowIndexVar("idFila");

    dataTable.setRowStyleClass("filaParDataTable");
    dataTable.setStyle("width: 100%;");

    return dataTable;
  }

  /**
   *
   * @param proyectoSeleccionadoId
   * @param informeAvanceSeleccionId
   * @return
   * @throws JpaDinaeException
   */
  private List<DatosCalculoPresupuesto> cargarDatosCalculoPresupuestoEjecutado(Long proyectoSeleccionadoId, Long informeAvanceSeleccionId, boolean esAcumulado) throws JpaDinaeException {

    List<DatosCalculoPresupuesto> datosCalculo = new ArrayList<DatosCalculoPresupuesto>();

    if (proyectoSeleccionadoId != null && ((esAcumulado && informeAvanceSeleccionId == null) || informeAvanceSeleccionId != null)) {

      List<ResumenPresupuestoEjecutado> datos;

      Long idEstado = null;

      if (informeAvanceSeleccionId != null) {

        idEstado = iCompromisoProyectoLocal.getEstadoCompromisoProyectoPorIdInformeAvance(informeAvanceSeleccionId);

      }
      //NO ES ACUMULADO
      if (!esAcumulado) {

        if (!IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO.equals(idEstado)) {
          //UNICAMENTE SE EJECUTA EL PROCEDIMIENTO, MIENTRAS EL COMPROMISO NO ESTE CUMPLIDO
          _iResumenPresupuestoEjecutado.executeStoredProcedure(proyectoSeleccionadoId, informeAvanceSeleccionId);

        }

        datos = _iResumenPresupuestoEjecutado.findByProyectoInformeAvance(proyectoSeleccionadoId, informeAvanceSeleccionId);

      } //ES ACUMULADO
      else if (!IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO.equals(idEstado)) {
        //UNICAMENTE SE EJECUTA EL PROCEDIMIENTO, MIENTRAS EL COMPROMISO NO ESTE CUMPLIDO
        _iResumenPresupuestoEjecutado.executeStoredProcedureAcum(proyectoSeleccionadoId);
        //CONSULTA EL ULTIMO ACUMULADO
        datos = _iResumenPresupuestoEjecutado.findByProyectoInformeAvanceAcum(proyectoSeleccionadoId);

      } else {
        //SE CONSULTA EL ACUMULADO DEL INFORME 
        datos = _iResumenPresupuestoEjecutado.findByProyectoInformeAvanceAcumFinal(proyectoSeleccionadoId, informeAvanceSeleccionId);

      }

      if (datos == null || datos.isEmpty()) {
        return null;
      }

      rubroPresupuestadoPoliciaTotal = 0D;
      rubroEjecutadoPoliciaTotal = 0D;
      rubroPresupuestadoVicinTotal = 0D;
      rubroEjecutadoVicinTotal = 0D;
      rubroPresupuestadoUnidadTotal = 0D;
      rubroEjecutadoUnidadTotal = 0D;

      rubroPresupuestadoFuente1Total = 0D;
      rubroEjecutadoFuente1Total = 0D;
      rubroPresupuestadoFuente2Total = 0D;
      rubroEjecutadoFuente2Total = 0D;
      rubroPresupuestadoFuente3Total = 0D;
      rubroEjecutadoFuente3Total = 0D;
      rubroPresupuestadoFuente4Total = 0D;
      rubroEjecutadoFuente4Total = 0D;
      rubroSubtotalPresupuestadoTotal = 0D;
      rubroSubtotalEjecutadoTotal = 0D;

      int numero = 1;

      Double totalFila, totalFilaPresupuestado, totalFilaEjecutado;

      for (ResumenPresupuestoEjecutado valor : datos) {

        DatosCalculoPresupuesto dato = new DatosCalculoPresupuesto();
        dato.setStyle("alineadoDerecha gris");
        dato.setNumero(numero);
        dato.setNombreRubro(valor.getNombreRubro().toUpperCase());

        Double ejecutadoPolicia = valor.getPoliciaNalEjecuta();
        Double ejecutadoVicin = valor.getVicinEjecuta();
        Double ejecutadoUnidad = valor.getUnidadEjecuta();
        Double ejecutadoFuente1 = valor.getFuente1Ejecuta();
        Double ejecutadoFuente2 = valor.getFuente2Ejecuta();
        Double ejecutadoFuente3 = valor.getFuente3Ejecuta();
        Double ejecutadoFuente4 = valor.getFuente4Ejecuta();

        Double presupuestadoPolicia = valor.getPoliciaNalPpto();
        Double presupuestadoVicin = valor.getVicinPpto();
        Double presupuestadoUnidad = valor.getUnidadPpto();
        Double presupuestadoFuente1 = valor.getFuente1Ppto();
        Double presupuestadoFuente2 = valor.getFuente2Ppto();
        Double presupuestadoFuente3 = valor.getFuente3Ppto();
        Double presupuestadoFuente4 = valor.getFuente4Ppto();

        totalFilaPresupuestado = presupuestadoPolicia + presupuestadoVicin
                + presupuestadoUnidad + presupuestadoFuente1
                + presupuestadoFuente2 + presupuestadoFuente3
                + presupuestadoFuente4;

        totalFilaEjecutado = ejecutadoPolicia + ejecutadoVicin
                + ejecutadoUnidad + ejecutadoFuente1
                + ejecutadoFuente2 + ejecutadoFuente3
                + ejecutadoFuente4;

        dato.setValorEspeciePolicialNacional(df.format(presupuestadoPolicia));
        dato.setValorEfectivoPolicialNacional(df.format(ejecutadoPolicia));
        dato.setValorEspeciePolicialDINAE(df.format(presupuestadoVicin));
        dato.setValorEfectivoPolicialDINAE(df.format(ejecutadoVicin));
        dato.setValorEspecieUnidadPolicial(df.format(presupuestadoUnidad));
        dato.setValorEfectivoUnidadPolicial(df.format(ejecutadoUnidad));
        dato.setValorEspecieAdicional1(df.format(presupuestadoFuente1));
        dato.setValorEfectivoAdicional1(df.format(ejecutadoFuente1));
        dato.setValorEspecieAdicional2(df.format(presupuestadoFuente2));
        dato.setValorEfectivoAdicional2(df.format(ejecutadoFuente2));
        dato.setValorEspecieAdicional3(df.format(presupuestadoFuente3));
        dato.setValorEfectivoAdicional3(df.format(ejecutadoFuente3));
        dato.setValorEspecieAdicional4(df.format(presupuestadoFuente4));
        dato.setValorEfectivoAdicional4(df.format(ejecutadoFuente4));

        dato.setValorSubtotalRubroPresupuestado(df.format(totalFilaPresupuestado));
        dato.setValorSubtotalRubroEjecutado(df.format(totalFilaEjecutado));

        rubroPresupuestadoPoliciaTotal += presupuestadoPolicia;
        rubroEjecutadoPoliciaTotal += ejecutadoPolicia;

        rubroPresupuestadoVicinTotal += presupuestadoVicin;
        rubroEjecutadoVicinTotal += ejecutadoVicin;

        rubroPresupuestadoUnidadTotal += presupuestadoUnidad;
        rubroEjecutadoUnidadTotal += ejecutadoUnidad;

        rubroPresupuestadoFuente1Total += presupuestadoFuente1;
        rubroEjecutadoFuente1Total += ejecutadoFuente1;

        rubroPresupuestadoFuente2Total += presupuestadoFuente2;
        rubroEjecutadoFuente2Total += ejecutadoFuente2;

        rubroPresupuestadoFuente3Total += presupuestadoFuente3;
        rubroEjecutadoFuente3Total += ejecutadoFuente3;

        rubroPresupuestadoFuente4Total += presupuestadoFuente4;
        rubroEjecutadoFuente4Total += ejecutadoFuente4;

        rubroSubtotalPresupuestadoTotal += totalFilaPresupuestado;
        rubroSubtotalEjecutadoTotal += totalFilaEjecutado;

        totalFila = totalFilaPresupuestado - totalFilaEjecutado;

        dato.setTotalFila(df.format(totalFila));

        datosCalculo.add(dato);

        numero++;
      }

      DatosCalculoPresupuesto datoTotal = new DatosCalculoPresupuesto();
      datoTotal.setNombreRubro("TOTAL");
      datoTotal.setStyle("alineadoDerecha gris negrilla");

      datoTotal.setValorEspeciePolicialNacional(df.format(rubroPresupuestadoPoliciaTotal));
      datoTotal.setValorEfectivoPolicialNacional(df.format(rubroEjecutadoPoliciaTotal));

      datoTotal.setValorEspeciePolicialDINAE(df.format(rubroPresupuestadoVicinTotal));
      datoTotal.setValorEfectivoPolicialDINAE(df.format(rubroEjecutadoVicinTotal));

      datoTotal.setValorEspecieUnidadPolicial(df.format(rubroPresupuestadoUnidadTotal));
      datoTotal.setValorEfectivoUnidadPolicial(df.format(rubroEjecutadoUnidadTotal));

      datoTotal.setValorEspecieAdicional1(df.format(rubroPresupuestadoFuente1Total));
      datoTotal.setValorEfectivoAdicional1(df.format(rubroEjecutadoFuente1Total));

      datoTotal.setValorEspecieAdicional2(df.format(rubroPresupuestadoFuente2Total));
      datoTotal.setValorEfectivoAdicional2(df.format(rubroEjecutadoFuente2Total));

      datoTotal.setValorEspecieAdicional3(df.format(rubroPresupuestadoFuente3Total));
      datoTotal.setValorEfectivoAdicional3(df.format(rubroEjecutadoFuente3Total));

      datoTotal.setValorEspecieAdicional4(df.format(rubroPresupuestadoFuente4Total));
      datoTotal.setValorEfectivoAdicional4(df.format(rubroEjecutadoFuente4Total));

      datoTotal.setValorSubtotalRubroPresupuestado(df.format(rubroSubtotalPresupuestadoTotal));
      datoTotal.setValorSubtotalRubroEjecutado(df.format(rubroSubtotalEjecutadoTotal));

      datoTotal.setTotalFila(df.format(rubroSubtotalPresupuestadoTotal - rubroSubtotalEjecutadoTotal));
      datosCalculo.add(datoTotal);

      datoPorcentajeFooterPresupueso = new DatosCalculoPresupuesto();
      datoPorcentajeFooterPresupueso.setNombreRubro("%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePoliciaNacional(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoPoliciaTotal * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPoliciaNacional(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoPoliciaTotal * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePolicialDINAE(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoVicinTotal * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPolicialDINAE(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoVicinTotal * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieUnidadPolicial(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoUnidadTotal * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoUnidadPolicial(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoUnidadTotal * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional1(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoFuente1Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional1(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoFuente1Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional2(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoFuente2Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional2(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoFuente2Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional3(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoFuente3Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional3(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoFuente3Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional4(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroPresupuestadoFuente4Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional4(rubroSubtotalPresupuestadoTotal <= 0D ? "0%" : df.format((rubroEjecutadoFuente4Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeSubtotalRubroPresupuestado("100%");
      datoPorcentajeFooterPresupueso.setPorcentajeSubtotalRubroEjecutado("100%");

    }

    return datosCalculo;
  }

  /**
   *
   * @param proyectoSeleccionadoId
   * @param informeAvanceSeleccionId
   * @return
   * @throws JpaDinaeException
   */
  private List<DatosCalculoPresupuesto> cargarDatosCalculoPresupuestoEjecutadoImpl(Long proyectoSeleccionadoId, Long informeAvanceSeleccionId, boolean esAcumulado) throws JpaDinaeException {

    List<DatosCalculoPresupuesto> datosCalculo = new ArrayList<DatosCalculoPresupuesto>();

    if (proyectoSeleccionadoId != null && ((esAcumulado && informeAvanceSeleccionId == null) || informeAvanceSeleccionId != null)) {

      List<ResumenPresupuestoEjecutadoImpl> datos;

      // tomamos el plan de trabajo oficial de la implementacion
      PlanTrabajoImplementacion planTrabajo = obtenerPlanTrabajoProyecto(proyectoSeleccionadoId);

      if (planTrabajo == null || planTrabajo.getIdPlanTrabajo() == null) {
        return datosCalculo;
      }

      if (!esAcumulado) {

        Long idEstado = iCompromisoProyectoLocal.getEstadoCompromisoProyectoPorIdInformeAvance(informeAvanceSeleccionId);

        if (!IConstantes.ESTADO_COMPROMISO_PROYECTO_CUMPLIDO.equals(idEstado)) {

          _iResumenPresupuestoEjecutado.executeStoredProcedure(proyectoSeleccionadoId, informeAvanceSeleccionId);

        }

        _iResumenPresupuestoEjecutadoImpl.calcularPresupuestoEjecutadoImpl(proyectoSeleccionadoId, informeAvanceSeleccionId);
        datos = _iResumenPresupuestoEjecutadoImpl.findByProyectoInformeAvance(proyectoSeleccionadoId, informeAvanceSeleccionId);
      } else {
        _iResumenPresupuestoEjecutadoImpl.calcularPresupuestoEjecutadoAcumImpl(proyectoSeleccionadoId);
        datos = _iResumenPresupuestoEjecutadoImpl.findByProyectoInformeAvanceAcum(proyectoSeleccionadoId);
      }

      if (datos == null || datos.isEmpty()) {
        return null;
      }

      rubroPresupuestadoPoliciaTotal = 0D;
      rubroEjecutadoPoliciaTotal = 0D;
      rubroPresupuestadoVicinTotal = 0D;
      rubroEjecutadoVicinTotal = 0D;
      rubroPresupuestadoUnidadTotal = 0D;
      rubroEjecutadoUnidadTotal = 0D;

      rubroPresupuestadoFuente1Total = 0D;
      rubroEjecutadoFuente1Total = 0D;
      rubroPresupuestadoFuente2Total = 0D;
      rubroEjecutadoFuente2Total = 0D;
      rubroPresupuestadoFuente3Total = 0D;
      rubroEjecutadoFuente3Total = 0D;
      rubroPresupuestadoFuente4Total = 0D;
      rubroEjecutadoFuente4Total = 0D;
      rubroSubtotalPresupuestadoTotal = 0D;
      rubroSubtotalEjecutadoTotal = 0D;

      _listaFuentesProyecto = iFuente.findFuentesByPlanTrabajo(planTrabajo.getIdPlanTrabajo());

      int numero = 1;

      Double totalFila, totalFilaPresupuestado, totalFilaEjecutado;

      for (ResumenPresupuestoEjecutadoImpl valor : datos) {

        DatosCalculoPresupuesto dato = new DatosCalculoPresupuesto();
        dato.setStyle("alineadoDerecha gris");
        dato.setNumero(numero);
        dato.setNombreRubro(valor.getNombreRubro().toUpperCase());

        Double ejecutadoPolicia = valor.getPoliciaNalEjecuta();
        Double ejecutadoVicin = valor.getVicinEjecuta();
        Double ejecutadoUnidad = valor.getUnidadEjecuta();
        Double ejecutadoFuente1 = valor.getFuente1Ejecuta();
        Double ejecutadoFuente2 = valor.getFuente2Ejecuta();
        Double ejecutadoFuente3 = valor.getFuente3Ejecuta();
        Double ejecutadoFuente4 = valor.getFuente4Ejecuta();

        Double presupuestadoPolicia = valor.getPoliciaNalPpto();
        Double presupuestadoVicin = valor.getVicinPpto();
        Double presupuestadoUnidad = valor.getUnidadPpto();
        Double presupuestadoFuente1 = valor.getFuente1Ppto();
        Double presupuestadoFuente2 = valor.getFuente2Ppto();
        Double presupuestadoFuente3 = valor.getFuente3Ppto();
        Double presupuestadoFuente4 = valor.getFuente4Ppto();

        totalFilaPresupuestado = presupuestadoPolicia + presupuestadoVicin
                + presupuestadoUnidad + presupuestadoFuente1
                + presupuestadoFuente2 + presupuestadoFuente3
                + presupuestadoFuente4;

        totalFilaEjecutado = ejecutadoPolicia + ejecutadoVicin
                + ejecutadoUnidad + ejecutadoFuente1
                + ejecutadoFuente2 + ejecutadoFuente3
                + ejecutadoFuente4;

        dato.setValorEspeciePolicialNacional(df.format(presupuestadoPolicia));
        dato.setValorEfectivoPolicialNacional(df.format(ejecutadoPolicia));
        dato.setValorEspeciePolicialDINAE(df.format(presupuestadoVicin));
        dato.setValorEfectivoPolicialDINAE(df.format(ejecutadoVicin));
        dato.setValorEspecieUnidadPolicial(df.format(presupuestadoUnidad));
        dato.setValorEfectivoUnidadPolicial(df.format(ejecutadoUnidad));
        dato.setValorEspecieAdicional1(df.format(presupuestadoFuente1));
        dato.setValorEfectivoAdicional1(df.format(ejecutadoFuente1));
        dato.setValorEspecieAdicional2(df.format(presupuestadoFuente2));
        dato.setValorEfectivoAdicional2(df.format(ejecutadoFuente2));
        dato.setValorEspecieAdicional3(df.format(presupuestadoFuente3));
        dato.setValorEfectivoAdicional3(df.format(ejecutadoFuente3));
        dato.setValorEspecieAdicional4(df.format(presupuestadoFuente4));
        dato.setValorEfectivoAdicional4(df.format(ejecutadoFuente4));

        dato.setValorSubtotalRubroPresupuestado(df.format(totalFilaPresupuestado));
        dato.setValorSubtotalRubroEjecutado(df.format(totalFilaEjecutado));

        rubroPresupuestadoPoliciaTotal += presupuestadoPolicia;
        rubroEjecutadoPoliciaTotal += ejecutadoPolicia;

        rubroPresupuestadoVicinTotal += presupuestadoVicin;
        rubroEjecutadoVicinTotal += ejecutadoVicin;

        rubroPresupuestadoUnidadTotal += presupuestadoUnidad;
        rubroEjecutadoUnidadTotal += ejecutadoUnidad;

        rubroPresupuestadoFuente1Total += presupuestadoFuente1;
        rubroEjecutadoFuente1Total += ejecutadoFuente1;

        rubroPresupuestadoFuente2Total += presupuestadoFuente2;
        rubroEjecutadoFuente2Total += ejecutadoFuente2;

        rubroPresupuestadoFuente3Total += presupuestadoFuente3;
        rubroEjecutadoFuente3Total += ejecutadoFuente3;

        rubroPresupuestadoFuente4Total += presupuestadoFuente4;
        rubroEjecutadoFuente4Total += ejecutadoFuente4;

        rubroSubtotalPresupuestadoTotal += totalFilaPresupuestado;
        rubroSubtotalEjecutadoTotal += totalFilaEjecutado;

        totalFila = totalFilaPresupuestado - totalFilaEjecutado;

        dato.setTotalFila(df.format(totalFila));

        datosCalculo.add(dato);

        numero++;
      }

      DatosCalculoPresupuesto datoTotal = new DatosCalculoPresupuesto();
      datoTotal.setNombreRubro("TOTAL");
      datoTotal.setStyle("alineadoDerecha gris negrilla");

      datoTotal.setValorEspeciePolicialNacional(df.format(rubroPresupuestadoPoliciaTotal));
      datoTotal.setValorEfectivoPolicialNacional(df.format(rubroEjecutadoPoliciaTotal));

      datoTotal.setValorEspeciePolicialDINAE(df.format(rubroPresupuestadoVicinTotal));
      datoTotal.setValorEfectivoPolicialDINAE(df.format(rubroEjecutadoVicinTotal));

      datoTotal.setValorEspecieUnidadPolicial(df.format(rubroPresupuestadoUnidadTotal));
      datoTotal.setValorEfectivoUnidadPolicial(df.format(rubroEjecutadoUnidadTotal));

      datoTotal.setValorEspecieAdicional1(df.format(rubroPresupuestadoFuente1Total));
      datoTotal.setValorEfectivoAdicional1(df.format(rubroEjecutadoFuente1Total));

      datoTotal.setValorEspecieAdicional2(df.format(rubroPresupuestadoFuente2Total));
      datoTotal.setValorEfectivoAdicional2(df.format(rubroEjecutadoFuente2Total));

      datoTotal.setValorEspecieAdicional3(df.format(rubroPresupuestadoFuente3Total));
      datoTotal.setValorEfectivoAdicional3(df.format(rubroEjecutadoFuente3Total));

      datoTotal.setValorEspecieAdicional4(df.format(rubroPresupuestadoFuente4Total));
      datoTotal.setValorEfectivoAdicional4(df.format(rubroEjecutadoFuente4Total));

      datoTotal.setValorSubtotalRubroPresupuestado(df.format(rubroSubtotalPresupuestadoTotal));
      datoTotal.setValorSubtotalRubroEjecutado(df.format(rubroSubtotalEjecutadoTotal));

      datoTotal.setTotalFila(df.format(rubroSubtotalPresupuestadoTotal - rubroSubtotalEjecutadoTotal));
      datosCalculo.add(datoTotal);

      datoPorcentajeFooterPresupueso = new DatosCalculoPresupuesto();
      datoPorcentajeFooterPresupueso.setNombreRubro("%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePoliciaNacional(df.format((rubroPresupuestadoPoliciaTotal * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPoliciaNacional(df.format((rubroEjecutadoPoliciaTotal * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePolicialDINAE(df.format((rubroPresupuestadoVicinTotal * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPolicialDINAE(df.format((rubroEjecutadoVicinTotal * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieUnidadPolicial(df.format((rubroPresupuestadoUnidadTotal * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoUnidadPolicial(df.format((rubroEjecutadoUnidadTotal * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional1(df.format((rubroPresupuestadoFuente1Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional1(df.format((rubroEjecutadoFuente1Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional2(df.format((rubroPresupuestadoFuente2Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional2(df.format((rubroEjecutadoFuente2Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional3(df.format((rubroPresupuestadoFuente3Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional3(df.format((rubroEjecutadoFuente3Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional4(df.format((rubroPresupuestadoFuente4Total * 100D) / rubroSubtotalPresupuestadoTotal) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional4(df.format((rubroEjecutadoFuente4Total * 100D) / rubroSubtotalEjecutadoTotal) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeSubtotalRubroPresupuestado("100%");
      datoPorcentajeFooterPresupueso.setPorcentajeSubtotalRubroEjecutado("100%");

    }

    return datosCalculo;
  }

  /**
   *
   * @param tipo
   * @param proyectoSeleccionadoId
   * @return
   * @throws JpaDinaeException
   */
  private List<DatosCalculoPresupuesto> cargarDatosCalculoPresupuestoGlobal(String tipo, Long proyectoSeleccionadoId) throws JpaDinaeException {

    List<DatosCalculoPresupuesto> datosCalculo = new ArrayList<DatosCalculoPresupuesto>();

    List<ResumenPresupuestoProyecto> listaResumenPresupuestoProyecto = null;

    if (proyectoSeleccionadoId != null) {

      if (PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL_VERSION.equals(tipo)) {

        //LLAMADO DESDE VERSIONES, NO DEBE EXECUTAR EL PROCEDIMIENTO                
        listaResumenPresupuestoProyecto = iResumenPresupuestoProyectoLocal.findByProyecto(proyectoSeleccionadoId, ("P_VERSION_" + idProyectoVersion));

      } else {

        iResumenPresupuestoProyectoLocal.executeStoredProcedure(proyectoSeleccionadoId);
        listaResumenPresupuestoProyecto = iResumenPresupuestoProyectoLocal.findByProyecto(proyectoSeleccionadoId, null);
      }

      Double rubroEspeciePoliciaTotal = 0D;
      Double rubroEfectivoPoliciaTotal = 0D;
      Double rubroEspecieVicinTotal = 0D;
      Double rubroEfectivoVicinTotal = 0D;
      Double rubroEspecieUnidadTotal = 0D;
      Double rubroEfectivoUnidadTotal = 0D;

      Double rubroEspecieFuente1Total = 0D;
      Double rubroEfectivoFuente1Total = 0D;
      Double rubroEspecieFuente2Total = 0D;
      Double rubroEfectivoFuente2Total = 0D;
      Double rubroEspecieFuente3Total = 0D;
      Double rubroEfectivoFuente3Total = 0D;
      Double rubroEspecieFuente4Total = 0D;
      Double rubroEfectivoFuente4Total = 0D;

      Double totalFtesInternas = 0D;
      Double totalFtesExternas = 0D;

      totalTotales = 0D;

      if (listaResumenPresupuestoProyecto == null || listaResumenPresupuestoProyecto.isEmpty()) {
        return null;
      }

      //LAS FUENTES
      //1. FUENTE POLICIAL
      //2. FUENTE VICIN
      //3. FUENTE UNIDAD POLICIAL
      //SON OBLIGATORIOS
      if (_listaFuentesProyecto.size() <= 2) {

        //TODAVIA NO SE HAN CREADO LAS FUENTES OBLIGATORIAS
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "PRESUPUESTO NO SE PUDO GENERAR, MOTIVO: NO SE HAN GENERADO LAS FUENTES OBLIGATORIAS PARA ESTE PROYECTO. ID = ".concat(proyectoSeleccionadoId == null ? "" : proyectoSeleccionadoId.toString()));
        return null;

      }

      int numero = 1;
      for (ResumenPresupuestoProyecto unResumenPresupuestoProyecto : listaResumenPresupuestoProyecto) {

        DatosCalculoPresupuesto registro = new DatosCalculoPresupuesto();
        registro.setStyle("alineadoDerecha gris");
        registro.setNumero(numero);
        registro.setNombreRubro(unResumenPresupuestoProyecto.getNombreRubro().toUpperCase());

        //FUENTE POLICIAL
        Double rubroEspeciePolicia = (unResumenPresupuestoProyecto.getPoliciaNacionalEspecie() == null) ? 0D : unResumenPresupuestoProyecto.getPoliciaNacionalEspecie().doubleValue();
        Double rubroEfectivoPolicia = (unResumenPresupuestoProyecto.getPoliciaNacionalEfectivo() == null) ? 0D : unResumenPresupuestoProyecto.getPoliciaNacionalEfectivo().doubleValue();
        registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(0).getNombreFuente());
        registro.setValorEspeciePolicialNacional(df.format(rubroEspeciePolicia));
        registro.setValorEfectivoPolicialNacional(df.format(rubroEfectivoPolicia));

        //FUENTE VICIN
        Double rubroEspecieVicin = (unResumenPresupuestoProyecto.getVicinEspecie() == null) ? 0D : unResumenPresupuestoProyecto.getVicinEspecie().doubleValue();
        Double rubroEfectivoVicin = (unResumenPresupuestoProyecto.getVicinEfectivo() == null) ? 0D : unResumenPresupuestoProyecto.getVicinEfectivo().doubleValue();
        registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(1).getNombreFuente());
        registro.setValorEspeciePolicialDINAE(df.format(rubroEspecieVicin));
        registro.setValorEfectivoPolicialDINAE(df.format(rubroEfectivoVicin));

        //FUENTE UNIDAD POLICIAL
        Double rubroEspecieUnidad = (unResumenPresupuestoProyecto.getUnidadEspecie() == null) ? 0D : unResumenPresupuestoProyecto.getUnidadEspecie().doubleValue();
        Double rubroEfectivoUnidad = (unResumenPresupuestoProyecto.getUnidadEfectivo() == null) ? 0D : unResumenPresupuestoProyecto.getUnidadEfectivo().doubleValue();
        registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(2).getNombreFuente());
        registro.setValorEspecieUnidadPolicial(df.format(rubroEspecieUnidad));
        registro.setValorEfectivoUnidadPolicial(df.format(rubroEfectivoUnidad));

        totalFtesInternas += rubroEspeciePolicia + rubroEfectivoPolicia + rubroEspecieVicin + rubroEfectivoVicin + rubroEspecieUnidad + rubroEfectivoUnidad;

        Double rubroEspecieFuente1 = (unResumenPresupuestoProyecto.getFuente1Especie() == null) ? 0D : unResumenPresupuestoProyecto.getFuente1Especie().doubleValue();
        Double rubroEfectivoFuente1 = (unResumenPresupuestoProyecto.getFuente1Efectivo() == null) ? 0D : unResumenPresupuestoProyecto.getFuente1Efectivo().doubleValue();

        if (unResumenPresupuestoProyecto.getEsFuente1Interna() != null && "Y".equals(unResumenPresupuestoProyecto.getEsFuente1Interna())) {
          totalFtesInternas += rubroEspecieFuente1 + rubroEfectivoFuente1;
        } else if (unResumenPresupuestoProyecto.getEsFuente1Interna() != null && "N".equals(unResumenPresupuestoProyecto.getEsFuente1Interna())) {
          totalFtesExternas += rubroEspecieFuente1 + rubroEfectivoFuente1;
        }
        if (_listaFuentesProyecto.size() > 3) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(3).getNombreFuente());
        }
        registro.setValorEspecieAdicional1(df.format(rubroEspecieFuente1));
        registro.setValorEfectivoAdicional1(df.format(rubroEfectivoFuente1));

        Double rubroEspecieFuente2 = (unResumenPresupuestoProyecto.getFuente2Especie() == null) ? 0D : unResumenPresupuestoProyecto.getFuente2Especie().doubleValue();
        Double rubroEfectivoFuente2 = (unResumenPresupuestoProyecto.getFuente2Efectivo() == null) ? 0D : unResumenPresupuestoProyecto.getFuente2Efectivo().doubleValue();

        if (unResumenPresupuestoProyecto.getEsFuente2Interna() != null && "Y".equals(unResumenPresupuestoProyecto.getEsFuente2Interna())) {
          totalFtesInternas += rubroEspecieFuente2 + rubroEfectivoFuente2;
        } else if (unResumenPresupuestoProyecto.getEsFuente2Interna() != null && "N".equals(unResumenPresupuestoProyecto.getEsFuente2Interna())) {
          totalFtesExternas += rubroEspecieFuente2 + rubroEfectivoFuente2;
        }
        if (_listaFuentesProyecto.size() > 4) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(4).getNombreFuente());
        }
        registro.setValorEspecieAdicional2(df.format(rubroEspecieFuente2));
        registro.setValorEfectivoAdicional2(df.format(rubroEfectivoFuente2));

        Double rubroEspecieFuente3 = (unResumenPresupuestoProyecto.getFuente3Especie() == null) ? 0D : unResumenPresupuestoProyecto.getFuente3Especie().doubleValue();
        Double rubroEfectivoFuente3 = (unResumenPresupuestoProyecto.getFuente3Efectivo() == null) ? 0D : unResumenPresupuestoProyecto.getFuente3Efectivo().doubleValue();

        if (unResumenPresupuestoProyecto.getEsFuente3Interna() != null && "Y".equals(unResumenPresupuestoProyecto.getEsFuente3Interna())) {
          totalFtesInternas += rubroEspecieFuente3 + rubroEfectivoFuente3;
        } else if (unResumenPresupuestoProyecto.getEsFuente3Interna() != null && "N".equals(unResumenPresupuestoProyecto.getEsFuente3Interna())) {
          totalFtesExternas += rubroEspecieFuente3 + rubroEfectivoFuente3;
        }
        if (_listaFuentesProyecto.size() > 5) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(5).getNombreFuente());
        }
        registro.setValorEspecieAdicional3(df.format(rubroEspecieFuente3));
        registro.setValorEfectivoAdicional3(df.format(rubroEfectivoFuente3));

        Double rubroEspecieFuente4 = (unResumenPresupuestoProyecto.getFuente4Especie() == null) ? 0D : unResumenPresupuestoProyecto.getFuente4Especie().doubleValue();
        Double rubroEfectivoFuente4 = (unResumenPresupuestoProyecto.getFuente4Efectivo() == null) ? 0D : unResumenPresupuestoProyecto.getFuente4Efectivo().doubleValue();

        if (unResumenPresupuestoProyecto.getEsFuente4Interna() != null && "Y".equals(unResumenPresupuestoProyecto.getEsFuente4Interna())) {
          totalFtesInternas += rubroEspecieFuente4 + rubroEfectivoFuente4;
        } else if (unResumenPresupuestoProyecto.getEsFuente4Interna() != null && "N".equals(unResumenPresupuestoProyecto.getEsFuente4Interna())) {
          totalFtesExternas += rubroEspecieFuente4 + rubroEfectivoFuente4;
        }
        if (_listaFuentesProyecto.size() > 6) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(6).getNombreFuente());
        }
        registro.setValorEspecieAdicional4(df.format(rubroEspecieFuente4));
        registro.setValorEfectivoAdicional4(df.format(rubroEfectivoFuente4));

        Double totalFila = rubroEspeciePolicia + rubroEfectivoPolicia + rubroEspecieVicin + rubroEfectivoVicin
                + rubroEspecieUnidad + rubroEfectivoUnidad + rubroEspecieFuente1 + rubroEfectivoFuente1
                + rubroEspecieFuente2 + rubroEfectivoFuente2 + rubroEspecieFuente3 + rubroEfectivoFuente3
                + rubroEspecieFuente4 + rubroEfectivoFuente4;

        rubroEspeciePoliciaTotal += rubroEspeciePolicia;
        rubroEfectivoPoliciaTotal += rubroEfectivoPolicia;

        rubroEspecieVicinTotal += rubroEspecieVicin;
        rubroEfectivoVicinTotal += rubroEfectivoVicin;

        rubroEspecieUnidadTotal += rubroEspecieUnidad;
        rubroEfectivoUnidadTotal += rubroEfectivoUnidad;

        rubroEspecieFuente1Total += rubroEspecieFuente1;
        rubroEfectivoFuente1Total += rubroEfectivoFuente1;

        rubroEspecieFuente2Total += rubroEspecieFuente2;
        rubroEfectivoFuente2Total += rubroEfectivoFuente2;

        rubroEspecieFuente3Total += rubroEspecieFuente3;
        rubroEfectivoFuente3Total += rubroEfectivoFuente3;

        rubroEspecieFuente4Total += rubroEspecieFuente4;
        rubroEfectivoFuente4Total += rubroEfectivoFuente4;

        totalTotales += totalFila;

        registro.setTotalFila(df.format(totalFila));

        datosCalculo.add(registro);

        numero++;
      }

      totalFuentesExternas = totalFtesExternas;
      totalFuentesInternas = totalFtesInternas;

      DatosCalculoPresupuesto datoTotal = new DatosCalculoPresupuesto();
      datoTotal.setStyle("alineadoDerecha gris negrilla");
      datoTotal.setNombreRubro("TOTAL");

      _porcentajesPresupuesto = new ArrayList<String>();

      datoTotal.setValorEspeciePolicialNacional(df.format(rubroEspeciePoliciaTotal));
      datoTotal.setValorEfectivoPolicialNacional(df.format(rubroEfectivoPoliciaTotal));

      datoTotal.setValorEspeciePolicialDINAE(df.format(rubroEspecieVicinTotal));
      datoTotal.setValorEfectivoPolicialDINAE(df.format(rubroEfectivoVicinTotal));

      datoTotal.setValorEspecieUnidadPolicial(df.format(rubroEspecieUnidadTotal));
      datoTotal.setValorEfectivoUnidadPolicial(df.format(rubroEfectivoUnidadTotal));

      datoTotal.setValorEfectivoAdicional1(df.format(rubroEfectivoFuente1Total));
      datoTotal.setValorEspecieAdicional1(df.format(rubroEspecieFuente1Total));

      datoTotal.setValorEfectivoAdicional2(df.format(rubroEfectivoFuente2Total));
      datoTotal.setValorEspecieAdicional2(df.format(rubroEspecieFuente2Total));

      datoTotal.setValorEfectivoAdicional3(df.format(rubroEfectivoFuente3Total));
      datoTotal.setValorEspecieAdicional3(df.format(rubroEspecieFuente3Total));

      datoTotal.setValorEfectivoAdicional4(df.format(rubroEfectivoFuente4Total));
      datoTotal.setValorEspecieAdicional4(df.format(rubroEspecieFuente4Total));

      datoTotal.setTotalFila(df.format(totalTotales));
      datosCalculo.add(datoTotal);

      //ULTIMA FILA TOTAL
      datoPorcentajeFooterPresupueso = new DatosCalculoPresupuesto();
      datoPorcentajeFooterPresupueso.setNombreRubro("%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePoliciaNacional(totalTotales <= 0D ? "0%" : df.format((rubroEspeciePoliciaTotal * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPoliciaNacional(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoPoliciaTotal * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePolicialDINAE(totalTotales <= 0D ? "0%" : df.format((rubroEspecieVicinTotal * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPolicialDINAE(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoVicinTotal * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieUnidadPolicial(totalTotales <= 0D ? "0%" : df.format((rubroEspecieUnidadTotal * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoUnidadPolicial(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoUnidadTotal * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional1(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente1Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional1(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente1Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional2(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente2Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional2(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente2Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional3(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente3Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional3(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente3Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional4(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente4Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional4(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente4Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setTotalFila("100%");

    }

    return datosCalculo;
  }

  /**
   *
   * @param tipo
   * @param proyectoSeleccionadoId
   * @return
   * @throws JpaDinaeException
   */
  private List<DatosCalculoPresupuesto> cargarDatosCalculoPresupuestoGlobalImpl(String tipo, Long idImplementacionProyecto) throws JpaDinaeException {

    List<DatosCalculoPresupuesto> datosCalculo = new ArrayList<DatosCalculoPresupuesto>();

    List<ResumenPresupuestoImpl> listaResumenPresupuestoProyectoImpl = null;

    if (idImplementacionProyecto != null) {

      // tomamos el plan de trabajo oficial de la implementacion
      PlanTrabajoImplementacion planTrabajo = obtenerPlanTrabajoProyecto(idImplementacionProyecto);

      if (planTrabajo == null || planTrabajo.getIdPlanTrabajo() == null) {
        return datosCalculo;
      }

      iResumenPresupuestoImpl.calcularPresupuestoImpl(planTrabajo.getIdPlanTrabajo(), idImplementacionProyecto);
      listaResumenPresupuestoProyectoImpl = iResumenPresupuestoImpl.findByProyectoImpl(idImplementacionProyecto, planTrabajo.getIdPlanTrabajo());

      Double rubroEspeciePoliciaTotal = 0D;
      Double rubroEfectivoPoliciaTotal = 0D;
      Double rubroEspecieVicinTotal = 0D;
      Double rubroEfectivoVicinTotal = 0D;
      Double rubroEspecieUnidadTotal = 0D;
      Double rubroEfectivoUnidadTotal = 0D;

      Double rubroEspecieFuente1Total = 0D;
      Double rubroEfectivoFuente1Total = 0D;
      Double rubroEspecieFuente2Total = 0D;
      Double rubroEfectivoFuente2Total = 0D;
      Double rubroEspecieFuente3Total = 0D;
      Double rubroEfectivoFuente3Total = 0D;
      Double rubroEspecieFuente4Total = 0D;
      Double rubroEfectivoFuente4Total = 0D;

      Double totalFtesInternas = 0D;
      Double totalFtesExternas = 0D;

      totalTotales = 0D;

      if (listaResumenPresupuestoProyectoImpl == null || listaResumenPresupuestoProyectoImpl.isEmpty()) {
        return null;
      }

      _listaFuentesProyecto = iFuente.findFuentesByPlanTrabajo(planTrabajo.getIdPlanTrabajo());

      //LAS FUENTES
      //1. FUENTE POLICIAL
      //2. FUENTE VICIN
      //3. FUENTE UNIDAD POLICIAL
      //SON OBLIGATORIOS
      if (_listaFuentesProyecto.size() <= 2) {

        //TODAVIA NO SE HAN CREADO LAS FUENTES OBLIGATORIAS
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "PRESUPUESTO NO SE PUDO GENERAR, MOTIVO: NO SE HAN GENERADO LAS FUENTES OBLIGATORIAS PARA LA IMPLEMENTACION DE ESTE PROYECTO. ID = ".concat(idImplementacionProyecto == null ? "" : idImplementacionProyecto.toString()));
        return null;

      }

      int numero = 1;
      for (ResumenPresupuestoImpl unResumenPresupuestoImpl : listaResumenPresupuestoProyectoImpl) {

        DatosCalculoPresupuesto registro = new DatosCalculoPresupuesto();
        registro.setStyle("alineadoDerecha gris");
        registro.setNumero(numero);
        registro.setNombreRubro(unResumenPresupuestoImpl.getNombreRubro().toUpperCase());

        //FUENTE POLICIAL
        Double rubroEspeciePolicia = (unResumenPresupuestoImpl.getPoliciaNacionalEspecie() == null) ? 0D : unResumenPresupuestoImpl.getPoliciaNacionalEspecie().doubleValue();
        Double rubroEfectivoPolicia = (unResumenPresupuestoImpl.getPoliciaNacionalEfectivo() == null) ? 0D : unResumenPresupuestoImpl.getPoliciaNacionalEfectivo().doubleValue();
        registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(0).getNombreFuente());
        registro.setValorEspeciePolicialNacional(df.format(rubroEspeciePolicia));
        registro.setValorEfectivoPolicialNacional(df.format(rubroEfectivoPolicia));

        //FUENTE VICIN
        Double rubroEspecieVicin = (unResumenPresupuestoImpl.getVicinEspecie() == null) ? 0D : unResumenPresupuestoImpl.getVicinEspecie().doubleValue();
        Double rubroEfectivoVicin = (unResumenPresupuestoImpl.getVicinEfectivo() == null) ? 0D : unResumenPresupuestoImpl.getVicinEfectivo().doubleValue();
        registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(1).getNombreFuente());
        registro.setValorEspeciePolicialDINAE(df.format(rubroEspecieVicin));
        registro.setValorEfectivoPolicialDINAE(df.format(rubroEfectivoVicin));

        //FUENTE UNIDAD POLICIAL
        Double rubroEspecieUnidad = (unResumenPresupuestoImpl.getUnidadEspecie() == null) ? 0D : unResumenPresupuestoImpl.getUnidadEspecie().doubleValue();
        Double rubroEfectivoUnidad = (unResumenPresupuestoImpl.getUnidadEfectivo() == null) ? 0D : unResumenPresupuestoImpl.getUnidadEfectivo().doubleValue();
        registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(2).getNombreFuente());
        registro.setValorEspecieUnidadPolicial(df.format(rubroEspecieUnidad));
        registro.setValorEfectivoUnidadPolicial(df.format(rubroEfectivoUnidad));

        totalFtesInternas += rubroEspeciePolicia + rubroEfectivoPolicia + rubroEspecieVicin + rubroEfectivoVicin + rubroEspecieUnidad + rubroEfectivoUnidad;

        Double rubroEspecieFuente1 = (unResumenPresupuestoImpl.getFuente1Especie() == null) ? 0D : unResumenPresupuestoImpl.getFuente1Especie().doubleValue();
        Double rubroEfectivoFuente1 = (unResumenPresupuestoImpl.getFuente1Efectivo() == null) ? 0D : unResumenPresupuestoImpl.getFuente1Efectivo().doubleValue();

        if (unResumenPresupuestoImpl.getEsFuente1Interna() != null && "Y".equals(unResumenPresupuestoImpl.getEsFuente1Interna())) {
          totalFtesInternas += rubroEspecieFuente1 + rubroEfectivoFuente1;
        } else if (unResumenPresupuestoImpl.getEsFuente1Interna() != null && "N".equals(unResumenPresupuestoImpl.getEsFuente1Interna())) {
          totalFtesExternas += rubroEspecieFuente1 + rubroEfectivoFuente1;
        }
        if (_listaFuentesProyecto.size() > 3) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(3).getNombreFuente());
        }
        registro.setValorEspecieAdicional1(df.format(rubroEspecieFuente1));
        registro.setValorEfectivoAdicional1(df.format(rubroEfectivoFuente1));

        Double rubroEspecieFuente2 = (unResumenPresupuestoImpl.getFuente2Especie() == null) ? 0D : unResumenPresupuestoImpl.getFuente2Especie().doubleValue();
        Double rubroEfectivoFuente2 = (unResumenPresupuestoImpl.getFuente2Efectivo() == null) ? 0D : unResumenPresupuestoImpl.getFuente2Efectivo().doubleValue();

        if (unResumenPresupuestoImpl.getEsFuente2Interna() != null && "Y".equals(unResumenPresupuestoImpl.getEsFuente2Interna())) {
          totalFtesInternas += rubroEspecieFuente2 + rubroEfectivoFuente2;
        } else if (unResumenPresupuestoImpl.getEsFuente2Interna() != null && "N".equals(unResumenPresupuestoImpl.getEsFuente2Interna())) {
          totalFtesExternas += rubroEspecieFuente2 + rubroEfectivoFuente2;
        }
        if (_listaFuentesProyecto.size() > 4) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(4).getNombreFuente());
        }
        registro.setValorEspecieAdicional2(df.format(rubroEspecieFuente2));
        registro.setValorEfectivoAdicional2(df.format(rubroEfectivoFuente2));

        Double rubroEspecieFuente3 = (unResumenPresupuestoImpl.getFuente3Especie() == null) ? 0D : unResumenPresupuestoImpl.getFuente3Especie().doubleValue();
        Double rubroEfectivoFuente3 = (unResumenPresupuestoImpl.getFuente3Efectivo() == null) ? 0D : unResumenPresupuestoImpl.getFuente3Efectivo().doubleValue();

        if (unResumenPresupuestoImpl.getEsFuente3Interna() != null && "Y".equals(unResumenPresupuestoImpl.getEsFuente3Interna())) {
          totalFtesInternas += rubroEspecieFuente3 + rubroEfectivoFuente3;
        } else if (unResumenPresupuestoImpl.getEsFuente3Interna() != null && "N".equals(unResumenPresupuestoImpl.getEsFuente3Interna())) {
          totalFtesExternas += rubroEspecieFuente3 + rubroEfectivoFuente3;
        }
        if (_listaFuentesProyecto.size() > 5) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(5).getNombreFuente());
        }
        registro.setValorEspecieAdicional3(df.format(rubroEspecieFuente3));
        registro.setValorEfectivoAdicional3(df.format(rubroEfectivoFuente3));

        Double rubroEspecieFuente4 = (unResumenPresupuestoImpl.getFuente4Especie() == null) ? 0D : unResumenPresupuestoImpl.getFuente4Especie().doubleValue();
        Double rubroEfectivoFuente4 = (unResumenPresupuestoImpl.getFuente4Efectivo() == null) ? 0D : unResumenPresupuestoImpl.getFuente4Efectivo().doubleValue();

        if (unResumenPresupuestoImpl.getEsFuente4Interna() != null && "Y".equals(unResumenPresupuestoImpl.getEsFuente4Interna())) {
          totalFtesInternas += rubroEspecieFuente4 + rubroEfectivoFuente4;
        } else if (unResumenPresupuestoImpl.getEsFuente4Interna() != null && "N".equals(unResumenPresupuestoImpl.getEsFuente4Interna())) {
          totalFtesExternas += rubroEspecieFuente4 + rubroEfectivoFuente4;
        }
        if (_listaFuentesProyecto.size() > 6) {
          registro.setNombreFuenteUnidadPolicial(_listaFuentesProyecto.get(6).getNombreFuente());
        }
        registro.setValorEspecieAdicional4(df.format(rubroEspecieFuente4));
        registro.setValorEfectivoAdicional4(df.format(rubroEfectivoFuente4));

        Double totalFila = rubroEspeciePolicia + rubroEfectivoPolicia + rubroEspecieVicin + rubroEfectivoVicin
                + rubroEspecieUnidad + rubroEfectivoUnidad + rubroEspecieFuente1 + rubroEfectivoFuente1
                + rubroEspecieFuente2 + rubroEfectivoFuente2 + rubroEspecieFuente3 + rubroEfectivoFuente3
                + rubroEspecieFuente4 + rubroEfectivoFuente4;

        rubroEspeciePoliciaTotal += rubroEspeciePolicia;
        rubroEfectivoPoliciaTotal += rubroEfectivoPolicia;

        rubroEspecieVicinTotal += rubroEspecieVicin;
        rubroEfectivoVicinTotal += rubroEfectivoVicin;

        rubroEspecieUnidadTotal += rubroEspecieUnidad;
        rubroEfectivoUnidadTotal += rubroEfectivoUnidad;

        rubroEspecieFuente1Total += rubroEspecieFuente1;
        rubroEfectivoFuente1Total += rubroEfectivoFuente1;

        rubroEspecieFuente2Total += rubroEspecieFuente2;
        rubroEfectivoFuente2Total += rubroEfectivoFuente2;

        rubroEspecieFuente3Total += rubroEspecieFuente3;
        rubroEfectivoFuente3Total += rubroEfectivoFuente3;

        rubroEspecieFuente4Total += rubroEspecieFuente4;
        rubroEfectivoFuente4Total += rubroEfectivoFuente4;

        totalTotales += totalFila;

        registro.setTotalFila(df.format(totalFila));

        datosCalculo.add(registro);

        numero++;
      }

      totalFuentesExternas = totalFtesExternas;
      totalFuentesInternas = totalFtesInternas;

      DatosCalculoPresupuesto datoTotal = new DatosCalculoPresupuesto();
      datoTotal.setStyle("alineadoDerecha gris negrilla");
      datoTotal.setNombreRubro("TOTAL");

      _porcentajesPresupuesto = new ArrayList<String>();

      datoTotal.setValorEspeciePolicialNacional(df.format(rubroEspeciePoliciaTotal));
      datoTotal.setValorEfectivoPolicialNacional(df.format(rubroEfectivoPoliciaTotal));

      datoTotal.setValorEspeciePolicialDINAE(df.format(rubroEspecieVicinTotal));
      datoTotal.setValorEfectivoPolicialDINAE(df.format(rubroEfectivoVicinTotal));

      datoTotal.setValorEspecieUnidadPolicial(df.format(rubroEspecieUnidadTotal));
      datoTotal.setValorEfectivoUnidadPolicial(df.format(rubroEfectivoUnidadTotal));

      datoTotal.setValorEfectivoAdicional1(df.format(rubroEfectivoFuente1Total));
      datoTotal.setValorEspecieAdicional1(df.format(rubroEspecieFuente1Total));

      datoTotal.setValorEfectivoAdicional2(df.format(rubroEfectivoFuente2Total));
      datoTotal.setValorEspecieAdicional2(df.format(rubroEspecieFuente2Total));

      datoTotal.setValorEfectivoAdicional3(df.format(rubroEfectivoFuente3Total));
      datoTotal.setValorEspecieAdicional3(df.format(rubroEspecieFuente3Total));

      datoTotal.setValorEfectivoAdicional4(df.format(rubroEfectivoFuente4Total));
      datoTotal.setValorEspecieAdicional4(df.format(rubroEspecieFuente4Total));

      datoTotal.setTotalFila(df.format(totalTotales));
      datosCalculo.add(datoTotal);

      //ULTIMA FILA TOTAL
      datoPorcentajeFooterPresupueso = new DatosCalculoPresupuesto();
      datoPorcentajeFooterPresupueso.setNombreRubro("%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePoliciaNacional(totalTotales <= 0D ? "0%" : df.format((rubroEspeciePoliciaTotal * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPoliciaNacional(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoPoliciaTotal * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspeciePolicialDINAE(totalTotales <= 0D ? "0%" : df.format((rubroEspecieVicinTotal * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPolicialDINAE(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoVicinTotal * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEspecieUnidadPolicial(totalTotales <= 0D ? "0%" : df.format((rubroEspecieUnidadTotal * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoUnidadPolicial(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoUnidadTotal * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional1(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente1Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional1(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente1Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional2(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente2Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional2(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente2Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional3(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente3Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional3(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente3Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional4(totalTotales <= 0D ? "0%" : df.format((rubroEfectivoFuente4Total * 100D) / totalTotales) + "%");
      datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional4(totalTotales <= 0D ? "0%" : df.format((rubroEspecieFuente4Total * 100D) / totalTotales) + "%");

      datoPorcentajeFooterPresupueso.setTotalFila("100%");

    }

    return datosCalculo;
  }

  private List<DatosCalculoPresupuesto> construirPresupuestoDummy() {

    List<DatosCalculoPresupuesto> presupuestoDummy = new ArrayList<DatosCalculoPresupuesto>();

    int index = 1;
    for (String rubro : NOMBRE_RUBROS) {

      DatosCalculoPresupuesto filaPresupuesto = new DatosCalculoPresupuesto();
      filaPresupuesto.setStyle("alineadoDerecha gris " + ("TOTAL".equals(rubro) ? "negrilla" : ""));
      filaPresupuesto.setNumero(index);
      filaPresupuesto.setNombreRubro(rubro.toUpperCase());

      filaPresupuesto.setValorEspeciePolicialNacional("0");
      filaPresupuesto.setValorEfectivoPolicialNacional("0");
      filaPresupuesto.setValorEspeciePolicialDINAE("0");
      filaPresupuesto.setValorEfectivoPolicialDINAE("0");
      filaPresupuesto.setValorEspecieUnidadPolicial("0");
      filaPresupuesto.setValorEfectivoPolicialNacional("0");
      filaPresupuesto.setValorEspecieAdicional1("0");
      filaPresupuesto.setValorEfectivoAdicional1("0");
      filaPresupuesto.setValorEspecieAdicional2("0");
      filaPresupuesto.setValorEfectivoAdicional2("0");
      filaPresupuesto.setValorEspecieAdicional3("0");
      filaPresupuesto.setValorEfectivoAdicional3("0");
      filaPresupuesto.setValorEspecieAdicional4("0");
      filaPresupuesto.setValorEfectivoAdicional4("0");

      filaPresupuesto.setValorSubtotalRubroPresupuestado("0");
      filaPresupuesto.setValorSubtotalRubroEjecutado("0");

      filaPresupuesto.setTotalFila("0");

      presupuestoDummy.add(filaPresupuesto);
    }

    totalFuentesExternas = 0D;
    totalFuentesInternas = 0D;
    totalTotales = 0D;

    datoPorcentajeFooterPresupueso = new DatosCalculoPresupuesto();
    datoPorcentajeFooterPresupueso.setNombreRubro("%");
    datoPorcentajeFooterPresupueso.setPorcentajeEspeciePoliciaNacional("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPoliciaNacional("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeEspeciePolicialDINAE("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoPolicialDINAE("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeEspecieUnidadPolicial("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoUnidadPolicial("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional1("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional1("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional2("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional2("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional3("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional3("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeEfectivoAdicional4("0%");
    datoPorcentajeFooterPresupueso.setPorcentajeEspecieAdicional4("0%");

    datoPorcentajeFooterPresupueso.setPorcentajeSubtotalRubroPresupuestado("100%");
    datoPorcentajeFooterPresupueso.setValorSubtotalRubroEjecutado("100%");

    datoPorcentajeFooterPresupueso.setTotalFila("100%");

    return presupuestoDummy;
  }

  /**
   *
   * @throws Exception
   */
  private PlanTrabajoImplementacion obtenerPlanTrabajoProyecto(Long idImplementacionProyecto) throws JpaDinaeException {

    CompromisoImplementacion compromisoPlanTrabajo = iCompromisoImpl.findByIdImplementacionProyYtipoCompromisoNoCorregido(
            idImplementacionProyecto, IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO);
    if (compromisoPlanTrabajo != null) {
      return iPlanTrabajoImplementacion.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
              idImplementacionProyecto, compromisoPlanTrabajo.getIdCompromisoImplementacion());
    }

    return null;
  }

  public DataTable getDataTablePresupuesto() {
    return _dataTablePresupuesto;
  }

  public void setDataTablePresupuesto(DataTable _dataTablePresupuesto) {
    this._dataTablePresupuesto = _dataTablePresupuesto;
  }

  public List<FuenteProyecto> getListaFuentesProyecto() {
    return _listaFuentesProyecto;
  }

  public void setListaFuentesProyecto(List<FuenteProyecto> _listaFuentesProyecto) {
    this._listaFuentesProyecto = _listaFuentesProyecto;
  }

  public List<String> getPorcentajesPresupuesto() {
    return _porcentajesPresupuesto;
  }

  public void setPorcentajesPresupuesto(List<String> _porcentajesPresupuesto) {
    this._porcentajesPresupuesto = _porcentajesPresupuesto;
  }

  public Double getTotalTotales() {
    return totalTotales;
  }

  public Double getTotalFuentesInternas() {
    return totalFuentesInternas;
  }

  public void setTotalFuentesInternas(Double totalFuentesInternas) {
    this.totalFuentesInternas = totalFuentesInternas;
  }

  public Double getTotalFuentesExternas() {
    return totalFuentesExternas;
  }

  public void setTotalFuentesExternas(Double totalFuentesExternas) {
    this.totalFuentesExternas = totalFuentesExternas;
  }

  public boolean isMostrarPresupuestoAcumulado() {
    return _mostrarPresupuestoAcumulado;
  }

  public void setMostrarPresupuestoAcumulado(boolean _mostrarPresupuestoAcumulado) {
    this._mostrarPresupuestoAcumulado = _mostrarPresupuestoAcumulado;
  }

  public DataTable getDataTablePresupuestoAcumulado() {
    return _dataTablePresupuestoAcumulado;
  }

  public void setDataTablePresupuestoAcumulado(DataTable _dataTablePresupuestoAcumulado) {
    this._dataTablePresupuestoAcumulado = _dataTablePresupuestoAcumulado;
  }

  public String getTituloPresupuesto() {
    return _tituloPresupuesto;
  }

  public void setTituloPresupuesto(String _tituloPresupuesto) {
    this._tituloPresupuesto = _tituloPresupuesto;
  }

  public DatosCalculoPresupuesto getDatoPorcentajeFooterPresupueso() {
    return datoPorcentajeFooterPresupueso;
  }

  public void setDatoPorcentajeFooterPresupueso(DatosCalculoPresupuesto datoPorcentajeFooterPresupueso) {
    this.datoPorcentajeFooterPresupueso = datoPorcentajeFooterPresupueso;
  }

  public Double getRubroSubtotalEjecutadoTotal() {

    if (rubroSubtotalEjecutadoTotal == null) {
      return 0D;
    }
    return rubroSubtotalEjecutadoTotal;
  }
}
