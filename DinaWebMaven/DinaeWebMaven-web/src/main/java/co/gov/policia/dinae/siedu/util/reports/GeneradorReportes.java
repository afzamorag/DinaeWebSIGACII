/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.util.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOdsReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.jexl2.JexlEngine;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class GeneradorReportes {

  private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GeneradorReportes.class);

  public static OutputStream generar(Map<String, String> parametrosGenerador, String nombreReporte, HttpSession session, HttpServletRequest request, HttpServletResponse response, Map<String, Object> parametrosReporte, JRDataSource jrDatasource) throws IOException, Exception {
    try {
      String tipo = parametrosGenerador.get("tipo"); //pdf, xml, html, rtf, xls, jxl, csv, odt, ods, docx, xlsx, pptx, xhtml, jxls
      //String dataSource = parametrosR.get("datasource");
      //String tipoDatasource = parametrosR.get("tipoDatasource"); //1:Connection, 2:JRDataSource, 
      //String reporte = parametrosR.get("reporte");
      //String mb = parametrosR.get("mb");

      /*if (reporte == null) {
       reporte = "prueba";
       }*/
      File reportFile = new File(nombreReporte);
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("BASE_DIR", reportFile.getParentFile());
      parameters.put("CLIENTE", "PONAL");
      parameters.put("NIT", "");
      parameters.put("LOGO_CLIENTE", "Logo.png");
      String otros = parametrosGenerador.get("otros");
      if (otros != null && !otros.equals("")) {
        StringTokenizer st = new StringTokenizer(otros, "|");
        while (st.hasMoreTokens()) {
          String parametro = st.nextToken();
          StringTokenizer st2 = new StringTokenizer(parametro, "^");
          String campo = st2.nextToken();
          String valor = "";
          if (st2.hasMoreTokens()) {
            valor = st2.nextToken();
          }
          parameters.put(campo, valor);
        }
      }
      if (parametrosReporte != null) {
        for (String s : parametrosReporte.keySet()) {
          parameters.put(s, parametrosReporte.get(s));
        }
      }
      InitialContext initialContext = new InitialContext();
      DataSource dataSource = null;//(DataSource) initialContext.lookup("java:jdbc/DinaeWebDS");
      String salida = nombreReporte + (int) (Math.random() * 1000000000);
      System.out.println(new File(salida).getAbsolutePath());
      JasperPrint jasperPrint = null;
      if (!tipo.equals("jxls")) {
        if (jrDatasource != null) {
          jasperPrint = JasperFillManager.fillReport(nombreReporte, parameters, jrDatasource);
        } else {
          jasperPrint = JasperFillManager.fillReport(nombreReporte, parameters, dataSource.getConnection());
        }
      }
      long start = System.currentTimeMillis();
      OutputStream os;
      if (response != null) {
        os = response.getOutputStream();
      } else {
        os = new ByteArrayOutputStream();
      }
      switch (tipo) {
        case "pdf":
          JasperExportManager.exportReportToPdfStream(jasperPrint, os);
          break;
        case "html":
          response.setContentType("text/html");
          JasperExportManager.exportReportToHtmlFile(jasperPrint, salida + ".html");
          break;
        case "xml":
          JasperExportManager.exportReportToXmlStream(jasperPrint, os);
          break;
        case "rtf": {
          response.setContentType("application/rtf");
          JRRtfExporter exporter = new JRRtfExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleWriterExporterOutput(os));
          exporter.exportReport();
          break;
        }
        case "xls": {
          //				response.setContentType("application/x-msexcel");
          JRXlsExporter exporter = new JRXlsExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
          configuration.setOnePagePerSheet(true);
          exporter.setConfiguration(configuration);
          exporter.exportReport();
          break;
        }
        case "jxl": {          
          net.sf.jasperreports.engine.export.JExcelApiExporter exporter = new net.sf.jasperreports.engine.export.JExcelApiExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          net.sf.jasperreports.export.SimpleJxlReportConfiguration configuration = new net.sf.jasperreports.export.SimpleJxlReportConfiguration();
          configuration.setOnePagePerSheet(true);
          exporter.setConfiguration(configuration);
          exporter.exportReport();
          break;
        }
        case "csv": {
          response.setContentType("text/csv");
          JRCsvExporter exporter = new JRCsvExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleWriterExporterOutput(os));
          exporter.exportReport();
          break;
        }
        case "odt": {
          response.setContentType("application/vnd.oasis.opendocument.text");
          JROdtExporter exporter = new JROdtExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          exporter.exportReport();
          break;
        }
        case "ods": {
          response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
          JROdsExporter exporter = new JROdsExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          SimpleOdsReportConfiguration configuration = new SimpleOdsReportConfiguration();
          configuration.setOnePagePerSheet(true);
          exporter.setConfiguration(configuration);
          exporter.exportReport();
          break;
        }
        case "docx": {
          response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
          JRDocxExporter exporter = new JRDocxExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          exporter.exportReport();
          break;
        }
        case "xlsx": {
          response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
          JRXlsxExporter exporter = new JRXlsxExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
          configuration.setOnePagePerSheet(true);
          exporter.setConfiguration(configuration);
          exporter.exportReport();
          break;
        }
        case "pptx": {
          response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
          JRPptxExporter exporter = new JRPptxExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
          exporter.exportReport();
          break;
        }
        case "xhtml": {
          response.setContentType("application/xhtml+xml");
          net.sf.jasperreports.engine.export.JRXhtmlExporter exporter = new net.sf.jasperreports.engine.export.JRXhtmlExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleHtmlExporterOutput(os));
          exporter.exportReport();
          break;
        }
        case "jxls":
          JexlEngine engine = new JexlEngine();
          engine.setDebug(false);
          engine.setLenient(false);
          engine.setSilent(true);
          XLSTransformer transformer = new XLSTransformer();
          File f = new File(nombreReporte);
          File salida2 = new File("salida" + (int) (Math.random() * 1000000000) + ".xls");
          try {
            transformer.transformXLS(nombreReporte, parametrosReporte, salida2.getAbsolutePath());
            FileInputStream fis = new FileInputStream(salida2);
            int tamano = fis.available();
            byte d[] = new byte[tamano];
            fis.read(d);
            os.write(d);
          } catch (InvalidFormatException e) {
            LOG.error("case: jxls ->> mensaje ->> {}", e.getMessage());
          }
          break;
        default:
          break;
      }
      os.flush();
      System.err.println("Creation time : " + (System.currentTimeMillis() - start));
      return os;
    } catch (JRException e) {
      LOG.error("Exception: JRException ->> mensaje ->> {}", e.getMessage());
      e.printStackTrace();
      errores(response, e);
    } catch (SQLException e) {
      LOG.error("Exception: SQLException ->> mensaje ->> {}", e.getMessage());
      e.printStackTrace();
      errores(response, e);
    } catch (SecurityException e) {
      LOG.error("Exception: SecurityException ->> mensaje ->> {}", e.getMessage());
      e.printStackTrace();
      errores(response, e);
    } catch (IllegalArgumentException e) {
      LOG.error("Exception: IllegalArgumentException ->> mensaje ->> {}", e.getMessage());
      e.printStackTrace();
      errores(response, e);
    } catch (Exception e) {
      LOG.error("Exception: Exception ->> mensaje ->> {}", e.getMessage());
      e.printStackTrace();
      errores(response, e);
    }
    return null;
  }

  public static void errores(HttpServletResponse response, Exception e) {
    try {
      PrintWriter out = response.getWriter();
      response.setContentType("text/html");
      out.println("<html><head><title>Errores al Generar Reporte</title></head><body><h1>Errores al generar el reporte</h1>");
      out.println("Mensaje: " + e.getMessage() + "<p>");
      out.println("Pila: ");
      e.printStackTrace(out);
      out.println("<p></body></html>");
      out.close();
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }
}
