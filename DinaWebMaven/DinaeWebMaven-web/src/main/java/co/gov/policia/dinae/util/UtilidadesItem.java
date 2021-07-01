package co.gov.policia.dinae.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class UtilidadesItem implements Serializable {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT_CORTO = new SimpleDateFormat("dd/MM/yyyy");
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT_LARGO = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  /**
   *
   * @param is
   * @return
   */
  public static String getStringToInputStream(InputStream is) {

    if (is == null) {
      return null;
    }

    if (!(is instanceof ByteArrayInputStream)) {
      return null;
    }

    try {

      int size = ((ByteArrayInputStream) is).available();
      char[] theChars = new char[size];
      byte[] bytes = new byte[size];

      is.read(bytes, 0, size);
      for (int i = 0; i < size;) {
        theChars[i] = (char) (bytes[i++] & 0xff);
      }

      return new String(theChars);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   *
   * @param <T>
   * @param dbList
   * @param propId
   * @param propLabel
   * @return
   */
  public static <T> List<SelectItem> getListaSel(List<T> dbList, String propId, String propLabel) {

    List<SelectItem> list = new ArrayList<SelectItem>();

    try {
      for (T obj : dbList) {

        Object valPropId = PropertyUtils.getProperty(obj, propId);
        String valPropLabel = (String) PropertyUtils.getProperty(obj, propLabel);
        list.add(new SelectItem(valPropId, valPropLabel, valPropLabel));

      }
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex.getMessage());
    } catch (InvocationTargetException ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex.getMessage());
    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex.getMessage());
    }
    return list;
  }

  /**
   *
   * @param fecha
   * @return
   */
  public static String getFechaFormateadaFormatoCorto(Date fecha) {

    return SIMPLE_DATE_FORMAT_CORTO.format(fecha);

  }

  /**
   *
   * @param fecha
   * @return
   */
  public static String getFechaFormateadaFormatoLargo(Date fecha) {

    return SIMPLE_DATE_FORMAT_LARGO.format(fecha);

  }

  /**
   *
   * @param throwable
   * @return
   */
  public String getPrintStackTrace(Throwable throwable) {

    try {

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      throwable.printStackTrace(pw);
      return sw.toString();

    } catch (Exception e) {

      return "[ERROR]".concat(e.toString());
    }

  }
}
