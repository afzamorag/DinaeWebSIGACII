package co.gov.policia.dinae.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author juan
 */
public class DatesUtils implements Serializable {

  /**
   * Método que compara dos fechas sin importar las horas
   *
   * @param date1
   * @param date2
   * @return
   */
  public static int compareDate(Date date1, Date date2) {
    if (date1.getYear() == date2.getYear()
            && date1.getMonth() == date2.getMonth()
            && date1.getDate() == date2.getDate()) {
      return 0;
    } else if (date1.getYear() < date1.getYear()
            || (date1.getYear() == date2.getYear()
            && date1.getMonth() < date2.getMonth())
            || (date1.getYear() == date2.getYear()
            && date1.getMonth() == date2.getMonth()
            && date1.getDate() < date2.getDate())) {
      return -1;
    } else {
      return 1;
    }
  }

  public static Date setTime(Date date, int hour, int minute, int seconds) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, seconds);
    return calendar.getTime();
  }

  public static int calcularMesesEntreFechas(Date startDate, Date endDate) {

    int meses = 0;

    Calendar startCalendar = new GregorianCalendar();
    startCalendar.setTime(startDate);
    Calendar endCalendar = new GregorianCalendar();
    endCalendar.setTime(endDate);

    int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

    meses = diffMonth;

    return meses;
  }
}
