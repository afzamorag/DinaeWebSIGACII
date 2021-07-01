package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class StringUtils implements Serializable {

  public static void main(String[] args) {
    System.out.println(transformarTextoCadenaACodigoHTML("áéíóúÁÉÍÚÓÑñ"));
  }

  /**
   *
   * @param cadena
   * @return
   */
  public static String transformarTextoCadenaACodigoHTML(String cadena) {

    cadena = cadena.replace("á", "&aacute;");
    cadena = cadena.replace("é", "&eacute;");
    cadena = cadena.replace("í", "&iacute;");
    cadena = cadena.replace("ó", "&oacute;");
    cadena = cadena.replace("ú", "&uacute;");
    cadena = cadena.replace("ñ", "&ntilde;");
    cadena = cadena.replace("ü", "&uuml;");

    cadena = cadena.replace("Á", "&Aacute;");
    cadena = cadena.replace("É", "&Eacute;");
    cadena = cadena.replace("Í", "&Iacute;");
    cadena = cadena.replace("Ó", "&Oacute;");
    cadena = cadena.replace("Ú", "&Uacute;");
    cadena = cadena.replace("Ñ", "&Ntilde;");
    cadena = cadena.replace("Ü", "&Uuml;");
    //
    return cadena;
  }

  /**
   * Método que formatea un String quitandole los espacios y ajustandolo al tamaño especificado
   *
   * @param text
   * @param length
   * @return
   */
  public static String ajustarCadenaANumeroCaracter(String text, int length) {

    if (text == null) {
      return null;
    }

    if (text.trim().length() > length) {
      return text.trim().substring(0, length);
    } else {
      return text.trim();
    }
  }

  /**
   *
   * @param cadena
   * @param maximoCaracterPorLinea
   * @return
   */
  public static String formatearCadenaCaracteresPorLineaFormatoHTML(String cadena, int maximoCaracterPorLinea) {

    if (cadena == null) {
      return null;
    }

    StringBuilder cadenaFinal = new StringBuilder();
    String[] palabras = cadena.split(" ");
    int contadorCaracterPorLinea = 0;
    for (String unaPalabra : palabras) {

      unaPalabra = unaPalabra.trim();
      //SI EL NUMERO DE CARACTERES DE UNA PALABRA ES MAS LARGA QUE EL NUMERO DE CARACTERES DE TODA LINEA
      if (unaPalabra.length() > maximoCaracterPorLinea) {
        //PARTICIONAMOS LA CADENA
        while (unaPalabra.length() > 0) {

          String cadenaPartida;
          if (unaPalabra.length() > maximoCaracterPorLinea) {

            cadenaPartida = unaPalabra.substring(0, maximoCaracterPorLinea);

          } else {

            cadenaPartida = unaPalabra.substring(0, unaPalabra.length());
          }

          cadenaFinal.append(cadenaPartida);
          cadenaFinal.append("- <br />");
          try {

            unaPalabra = unaPalabra.replaceFirst(cadenaPartida, "");

          } catch (PatternSyntaxException e) {
            //SI SE PRESENTA ALGUNA EXCETION 
            try {
              //INTENTAMOS HACER EL REEMPLAZO CON PATTHER
              Pattern.compile(cadenaPartida).matcher(unaPalabra).replaceFirst("");

            } catch (Exception ex2) {
              //SI EL PROBLEMA CONTINUA
              //FINALIZAMOS EL PROCESO
              //RETORNANDO LA MISMA CADENA
              return cadena;
            }

          }

        }
        continue;
      }

      contadorCaracterPorLinea += unaPalabra.length();

      if (unaPalabra.length() == 0) {
        continue;
      }
      if (contadorCaracterPorLinea > maximoCaracterPorLinea) {

        cadenaFinal.append("<br />");
        contadorCaracterPorLinea = unaPalabra.length();

      } else {

        cadenaFinal.append(" ");

      }
      cadenaFinal.append(unaPalabra);

    }
    return cadenaFinal.toString().trim();

  }

}
