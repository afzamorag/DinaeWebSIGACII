package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.util.DatesUtils;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr16EncargadoGeneracionArchivosSniesFaces")
@javax.enterprise.context.SessionScoped
public class CuPr16EncargadoGeneracionArchivosSniesFaces extends JSFUtils implements Serializable {

  private String tipoProyectoSeleccionado;
  private List<SelectItem> listaItemTipoProyectoSeleccionado;
  private Date fechaInicialReporteSeleccionado;
  private Date fechaFinalReporteSeleccionado;

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT_AAAAMMDD = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT_DDMMAA_REPORTE = new SimpleDateFormat("dd/MM/yyyy");

  @javax.annotation.PostConstruct
  public void init() {

    try {

      fechaInicialReporteSeleccionado = null;
      fechaFinalReporteSeleccionado = null;
      listaItemTipoProyectoSeleccionado = null;
      tipoProyectoSeleccionado = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-16", e);

    }
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      listaItemTipoProyectoSeleccionado = new ArrayList<SelectItem>();
      listaItemTipoProyectoSeleccionado.add(new SelectItem("VIC", "Proyecto institucional"));
      listaItemTipoProyectoSeleccionado.add(new SelectItem("CONV", "De convocatoria de financiación"));
      listaItemTipoProyectoSeleccionado.add(new SelectItem("PROYECTO_GRADO", "Trabajo de grado"));

      return navigationFaces.redirectCuPr16EncargadoGeneracionArchivosSniesRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-16", e);

    }

    return null;

  }

  public StreamedContent getReporte() {

    try {

      if (tipoProyectoSeleccionado == null || tipoProyectoSeleccionado.trim().length() == 0) {

        adicionaMensajeError("Seleccione el tipo de proyecto");
        return null;
      }

      if (DatesUtils.compareDate(fechaInicialReporteSeleccionado, fechaFinalReporteSeleccionado) == 1) {

        adicionaMensajeError("La Fecha inicio creación proyecto debe ser menor o igual a la Fecha fin creación proyecto");
        return null;
      }

      List<File> listaArchivosAzip = new ArrayList<File>();

      HashMap mapa = new HashMap();
      mapa.put("p_tipo_proyecto", tipoProyectoSeleccionado);
      mapa.put("p_fecha_inicio", SIMPLE_DATE_FORMAT_DDMMAA_REPORTE.format(fechaInicialReporteSeleccionado));
      mapa.put("p_fecha_fin", SIMPLE_DATE_FORMAT_DDMMAA_REPORTE.format(fechaFinalReporteSeleccionado));

      byte[] byte_SNIES_GRUPOS_INVESTIGACION = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_GRUPOS_INVESTIGACION.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_GRUPOS_INVESTIGACION = File.createTempFile("SNIES_GRUPOS_INVESTIGACION_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_GRUPOS_INVESTIGACION, byte_SNIES_GRUPOS_INVESTIGACION);

      byte[] byte_SNIES_INVESTIGADORES_EXTERNOS = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_INVESTIGADORES_EXTERNOS.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_INVESTIGADORES_EXTERNOS = File.createTempFile("SNIES_INVESTIGADORES_EXTERNOS_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_INVESTIGADORES_EXTERNOS, byte_SNIES_INVESTIGADORES_EXTERNOS);

      byte[] byte_SNIES_PARTICIPANTE = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_PARTICIPANTE.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_PARTICIPANTE = File.createTempFile("SNIES_PARTICIPANTE_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_PARTICIPANTE, byte_SNIES_PARTICIPANTE);

      byte[] byte_SNIES_PROY_INVEST_PRODUCTO = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_PROY_INVEST_PRODUCTO.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_PROY_INVEST_PRODUCTO = File.createTempFile("SNIES_PROY_INVEST_PRODUCTO_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_PROY_INVEST_PRODUCTO, byte_SNIES_PROY_INVEST_PRODUCTO);

      byte[] byte_SNIES_PROY_INVEST_EXT_IES = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_PROY_INVEST_EXT_IES.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_PROY_INVEST_EXT_IES = File.createTempFile("SNIES_PROY_INVEST_EXT_IES_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_PROY_INVEST_EXT_IES, byte_SNIES_PROY_INVEST_EXT_IES);

      byte[] byte_SNIES_PROY_INVEST_GASTO = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_PROY_INVEST_GASTO.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_PROY_INVEST_GASTO = File.createTempFile("SNIES_PROY_INVEST_GASTO_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_PROY_INVEST_GASTO, byte_SNIES_PROY_INVEST_GASTO);

      byte[] byte_SNIES_PROY_INVESTIG_IES = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "SNIES_PROY_INVESTIG_IES.jasper", GeneradorReportesServicio.TIPO_FORMATO_REPORTE.XLS);
      File archivo_SNIES_PROY_INVESTIG_IES = File.createTempFile("SNIES_PROY_INVESTIG_IES_", ".xls");
      FileUtils.writeByteArrayToFile(archivo_SNIES_PROY_INVESTIG_IES, byte_SNIES_PROY_INVESTIG_IES);

      listaArchivosAzip.add(archivo_SNIES_GRUPOS_INVESTIGACION);
      listaArchivosAzip.add(archivo_SNIES_INVESTIGADORES_EXTERNOS);
      listaArchivosAzip.add(archivo_SNIES_PARTICIPANTE);
      listaArchivosAzip.add(archivo_SNIES_PROY_INVEST_PRODUCTO);
      listaArchivosAzip.add(archivo_SNIES_PROY_INVEST_EXT_IES);
      listaArchivosAzip.add(archivo_SNIES_PROY_INVEST_GASTO);
      listaArchivosAzip.add(archivo_SNIES_PROY_INVESTIG_IES);

      byte[] archivosZip = crearZIP(listaArchivosAzip);

      String contentType = "application/octet-stream";
      String nombreArchivoSalida = SIMPLE_DATE_FORMAT_AAAAMMDD.format(new Date()).concat(".zip");

      StreamedContent download = new DefaultStreamedContent(new ByteArrayInputStream(archivosZip), contentType, nombreArchivoSalida);

      try {

        FileUtils.deleteQuietly(archivo_SNIES_GRUPOS_INVESTIGACION);
        FileUtils.deleteQuietly(archivo_SNIES_INVESTIGADORES_EXTERNOS);
        FileUtils.deleteQuietly(archivo_SNIES_PARTICIPANTE);
        FileUtils.deleteQuietly(archivo_SNIES_PROY_INVEST_PRODUCTO);
        FileUtils.deleteQuietly(archivo_SNIES_PROY_INVEST_EXT_IES);
        FileUtils.deleteQuietly(archivo_SNIES_PROY_INVEST_GASTO);
        FileUtils.deleteQuietly(archivo_SNIES_PROY_INVESTIG_IES);

      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-16", e);

      }

      return download;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-16", e);

    }

    return null;
  }

  public String getTipoProyectoSeleccionado() {
    return tipoProyectoSeleccionado;
  }

  public void setTipoProyectoSeleccionado(String tipoProyectoSeleccionado) {
    this.tipoProyectoSeleccionado = tipoProyectoSeleccionado;
  }

  public List<SelectItem> getListaItemTipoProyectoSeleccionado() {
    return listaItemTipoProyectoSeleccionado;
  }

  public void setListaItemTipoProyectoSeleccionado(List<SelectItem> listaItemTipoProyectoSeleccionado) {
    this.listaItemTipoProyectoSeleccionado = listaItemTipoProyectoSeleccionado;
  }

  public Date getFechaInicialReporteSeleccionado() {
    return fechaInicialReporteSeleccionado;
  }

  public void setFechaInicialReporteSeleccionado(Date fechaInicialReporteSeleccionado) {
    this.fechaInicialReporteSeleccionado = fechaInicialReporteSeleccionado;
  }

  public Date getFechaFinalReporteSeleccionado() {
    return fechaFinalReporteSeleccionado;
  }

  public void setFechaFinalReporteSeleccionado(Date fechaFinalReporteSeleccionado) {
    this.fechaFinalReporteSeleccionado = fechaFinalReporteSeleccionado;
  }

}
