/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.util.date;

import java.util.Calendar;
import java.util.Date;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class DateUtil {

  // assert: startDate must be before endDate
  /**
   * Calcula la cantidad de días entre 2 instancias de Calendar
   *
   * @param startDate Fecha inicial
   * @param endDate Fecha final
   * @return número de días
   */
  public static Integer daysBetween(final Calendar startDate, final Calendar endDate) {
    Calendar sDate = (Calendar) startDate.clone();
    Integer daysBetween = 0;
    int y1 = sDate.get(Calendar.YEAR);
    int y2 = endDate.get(Calendar.YEAR);
    int m1 = sDate.get(Calendar.MONTH);
    int m2 = endDate.get(Calendar.MONTH);
    int d1 = sDate.get(Calendar.DAY_OF_MONTH);
    int d2 = endDate.get(Calendar.DAY_OF_MONTH);
    if (y1 == y2 && m1 == m2 && d1 == d2) {
      return 0;
    }
    // **year optimization**
    while (((y2 - y1) * 12 + (m2 - m1)) > 12) {
      // move to Jan 01
      if (sDate.get(Calendar.MONTH) == Calendar.JANUARY && sDate.get(Calendar.DAY_OF_MONTH) == sDate.getActualMinimum(Calendar.DAY_OF_MONTH)) {
        daysBetween += sDate.getActualMaximum(Calendar.DAY_OF_YEAR);
        sDate.add(Calendar.YEAR, 1);
      } else {
        int diff = 1 + sDate.getActualMaximum(Calendar.DAY_OF_YEAR) - sDate.get(Calendar.DAY_OF_YEAR);
        sDate.add(Calendar.DAY_OF_YEAR, diff);
        daysBetween += diff;
      }
      y1 = sDate.get(Calendar.YEAR);
    }
    // ** optimize for month **
    // while the difference is more than a month, add a month to start month
    while ((m2 - m1) % 12 > 1) {
      daysBetween += sDate.getActualMaximum(Calendar.DAY_OF_MONTH);
      sDate.add(Calendar.MONTH, 1);
      m1 = sDate.get(Calendar.MONTH);
    }
    // process remainder date
    while (sDate.before(endDate)) {
      sDate.add(Calendar.DAY_OF_MONTH, 1);
      daysBetween++;
    }
    return daysBetween;
  }

  /**
   *
   * @param fecha
   * @return
   */
  public Integer calcularEdad(Date fecha) {
    if (fecha == null) {
      return null;
    }
    Calendar fechaNacimiento = Calendar.getInstance();
    //Se asigna la fecha recibida a la fecha de nacimiento.
    fechaNacimiento.setTime(fecha);
    //Se crea un objeto con la fecha actual
    Calendar fechaActual = Calendar.getInstance();
    //Se restan la fecha actual y la fecha de nacimiento
    int año = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
    int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
    int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
    //Se ajusta el año dependiendo el mes y el día
    if (mes < 0 || (mes == 0 && dia < 0)) {
      año--;
    }
    return año;
  }

}
