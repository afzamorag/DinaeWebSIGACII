package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.HashMap;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 * @author Ramiro
 */
public class GeneradorReportesServicio implements Serializable {

  private static GeneradorReportesServicio instancia;
  private static String userName;
  private static String passw;
  private static String url;
  private static String pathReportes;
  private static String className;

  public static enum TIPO_FORMATO_REPORTE {
    PDF, XLS
  }

  /**
   *
   * @return
   */
  public static synchronized GeneradorReportesServicio getInstancia() {

    if (instancia == null) {
      instancia = new GeneradorReportesServicio();
    }

    return instancia;
  }

  /**
   *
   */
  private GeneradorReportesServicio() {

    url = KeyPropertiesFactory.getKeyValuesConexionBdGeneracionReportes().get("url");
    userName = KeyPropertiesFactory.getKeyValuesConexionBdGeneracionReportes().get("UserName");
    passw = KeyPropertiesFactory.getKeyValuesConexionBdGeneracionReportes().get("Password");
    pathReportes = KeyPropertiesFactory.getKeyValuesConexionBdGeneracionReportes().get("PathSubReportes");
    className = KeyPropertiesFactory.getKeyValuesConexionBdGeneracionReportes().get("className");

  }

  /**
   *
   * @return @throws ClassNotFoundException
   * @throws SQLException
   */
  public Connection obtenerConexion() throws ClassNotFoundException, SQLException {

    Connection connection;
    Class.forName(className);
    connection = DriverManager.getConnection(url, userName, passw);
    return connection;
  }

  /**
   * Genera el reporte en formato PDF
   *
   * @param mapa
   * @param nombreReportes
   * @return
   * @throws Exception
   */
  public byte[] generarReporte(HashMap mapa, String nombreReportes) throws Exception {

    return generarReporte(mapa, nombreReportes, TIPO_FORMATO_REPORTE.PDF);

  }

  /**
   * Genera el reporte el formato indicado, si el formato es NULL, el reporte se genera en PDF
   *
   * @param mapa
   * @param nombreReportes
   * @param tipo_formato_reporte
   * @return
   * @throws Exception
   */
  public byte[] generarReporte(HashMap mapa, String nombreReportes, TIPO_FORMATO_REPORTE tipo_formato_reporte) throws Exception {

    FileInputStream fileInputStream = new FileInputStream(new File(pathReportes.concat(nombreReportes)));

    java.sql.Connection connection = obtenerConexion();

    mapa.put("SUBREPORT_DIR", pathReportes);
    mapa.put("REPORT_CONNECTION", connection);

    JasperPrint print = JasperFillManager.fillReport(fileInputStream, mapa, connection);

    byte[] bitesPdf = null;

    if (tipo_formato_reporte == null || tipo_formato_reporte.equals(TIPO_FORMATO_REPORTE.PDF)) {

      bitesPdf = JasperExportManager.exportReportToPdf(print);

    } else if (TIPO_FORMATO_REPORTE.XLS.equals(tipo_formato_reporte)) {

      ByteArrayOutputStream output = new ByteArrayOutputStream();

      JExcelApiExporter xlsExporter = new JExcelApiExporter();
      xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
      xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
      xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
      xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
      xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);

      xlsExporter.exportReport();

      output.flush();

      bitesPdf = output.toByteArray();

    }

    connection.close();

    return bitesPdf;

  }
}
