package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.AntiguedadDTO;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class UtilJPA {

  /**
   *
   * @param fechaInicial
   * @param fechaFinal
   * @return
   */
  public static AntiguedadDTO calcularAntiguedadEntreDosfecha(Date fechaInicial, Date fechaFinal) {

    AntiguedadDTO antiguedadDTO = new AntiguedadDTO();
    antiguedadDTO.setAnosAntiguedad(0L);
    antiguedadDTO.setMesesAntiguiedad(0L);
    antiguedadDTO.setDiasAntiguedad(0L);

    Calendar fecha1 = Calendar.getInstance();
    Calendar fecha2 = Calendar.getInstance();

    // LAS FECHAS LAS VOLVEMOS CALENDAR PARA UN MEJOR MANEJO.
    fecha1.setTime(fechaInicial);
    fecha2.setTime(fechaFinal);

    long fechaInicialMs = fechaInicial.getTime();
    long fechaFinalMs = fechaFinal.getTime();

    // CON LAS FECHAS REQUERIDAS LAS CONVERTIMOS A MILISEGUNDOS PARA
    // LUEGO OBTENER LOS DIAS ENTRE UNA Y OTRA
    long diferencia = fechaFinalMs - fechaInicialMs;
    // CON LA FORMULA OBTENEMOS A DIAS = MILISEGUNDOS*SEGUNDOS*MINUTOS*HORAS
    double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
    // LUEGO APLICAMOS LAS CONVERSIONES DE DIAS PARA LOS AÑOS Y MESES
    // TENIENDO EN CUENTA LAS CONSTANTES APLICADAS
    // UN AÑO EQUIVALE A 365.25 DIAS Y UN MES 30.4375 DIAS Y CON LOS MOD
    // RESTAMOS DE LOS DIAS TOTALES
    double anio = dias / 365.25;
    double diasTemp = (dias % 365.25);
    dias = diasTemp;
    double mess = dias / 30.4375;
    diasTemp = (dias % 30.4375);
    dias = diasTemp;

    // SE SETEAN LOS DATOS CALCULADOS LA DTO DE SALIDA
    antiguedadDTO.setAnosAntiguedad((long) anio);
    antiguedadDTO.setMesesAntiguiedad((long) mess);
    antiguedadDTO.setDiasAntiguedad((long) (dias));

    return antiguedadDTO;
  }

  /**
   * Lista de valores: EJEMPLOS: (Sin Comillas){1,2,3} o {VALOR1,VALOR2,VALOR3} - (Con Comillas){'1','2','3'} o {'VALOR1','VALOR2','VALOR3'}
   *
   * @param list
   * @param adicionarComillas Adiciona o no comillas a cada valor
   * @return
   */
  public static String generateCollection(List list, boolean adicionarComillas) {

    if (list == null || list.isEmpty()) {
      return "()";
    }
    StringBuilder result = new StringBuilder("( ");

    for (Iterator it = list.iterator(); it.hasNext();) {

      Object ob = it.next();

      if (adicionarComillas) {

        result.append("'");
        result.append(ob.toString());
        result.append("'");

      } else {

        result.append(ob.toString());
      }

      if (it.hasNext()) {
        result.append(" , ");
      }
    }
    result.append(" )");

    return result.toString();
  }
}
