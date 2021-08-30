/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ANDRES.ZAMORAG
 */
public class ReportBuild {

    private Field[] fields;
    private Class clazz;

    public Sheet getSheet(Object obj, Sheet sh) {
        CellStyle cS = this.createStyleHeaderRow(sh);
        this.setStyleHeaderRow(sh, cS, obj);
        return sh;
    }

    public StreamedContent writeExcel(Workbook wb) {
        String pattern = "dd-MM-yyyy-HH_mm_ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String excelFileName = "Reporte_" + date + ".xlsx";
        StreamedContent reporteXLS = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            out.close();
            InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
            reporteXLS = new DefaultStreamedContent(inputStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", excelFileName);
        } catch (IOException e) {
            e.getMessage();
        }
        return reporteXLS;
    }

    public void setStyleHeaderRow(Sheet sheet, CellStyle cellStyle, Object obj) {
        int i = 0;
        Row row = sheet.createRow(i);
        this.getFields(obj);
        for (Field field : fields) {
            if (!field.getName().toUpperCase().contains("PERSISTENCE")) {
                Cell cellId = row.createCell(i);
                cellId.setCellStyle(cellStyle);
                cellId.setCellValue(field.getName().toUpperCase());
                i++;
            }
        }
    }

    public Field[] getFields(Object obj) {
        clazz = obj.getClass();
        fields = clazz.getDeclaredFields();
        return fields;
    }

    private CellStyle createStyleHeaderRow(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        return cellStyle;
    }

    private CellStyle createStyleRow(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        return cellStyle;
    }

}
