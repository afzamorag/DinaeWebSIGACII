package co.gov.policia.dinae.util;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class LookUpWeblogic implements Serializable {

  private static LookUpWeblogic lookUpWeblogic;
  private final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();
  private final Map<String, String> mapaUsuarioIdentificacion;

  /**
   *
   * @param args
   */
  public static void main(String args[]) {

    System.out.println((new LookUpWeblogic().getInformacionUsuarioWebLogic("description")));

  }

  /**
   *
   */
  private LookUpWeblogic() {

    mapaUsuarioIdentificacion = new HashMap<String, String>();

  }

  public static LookUpWeblogic getInstance() {

    if (lookUpWeblogic == null) {
      lookUpWeblogic = new LookUpWeblogic();
    }
    return lookUpWeblogic;
  }

  /**
   *
   * @param nombreUsuario
   * @return
   */
  public Object getInformacionUsuarioWebLogic(String nombreUsuario) {

    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "SOLICITANDO IDENTIFICACION DE USUARIO {0}", nombreUsuario);

    if (nombreUsuario == null) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "** ERRORRR - USUARIO NULO **");
      return null;
    }

    if (mapaUsuarioIdentificacion.containsKey(nombreUsuario)) {

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "USUARIO EN MAPA, RETORNANDO C.C {0} ", mapaUsuarioIdentificacion.get(nombreUsuario));
      return mapaUsuarioIdentificacion.get(nombreUsuario);

    }

    DirContext ctx = null;
    try {

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "INICIANDO CONEXIÓN CON LDAP-OID");
      Hashtable ht = new Hashtable();

      Iterator<Map.Entry<String, String>> itaIterator = keyPropertiesFactory.getKeyValuesParammetrosConexionLDAPWebLogic().entrySet().iterator();
      while (itaIterator.hasNext()) {

        Map.Entry<String, String> valores = itaIterator.next();
        ht.put(valores.getKey(), valores.getValue());

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "PROPIEDAD(KEY,VALUE): ( {0} , {1} ) ", new Object[]{valores.getKey(), valores.getValue()});

      }

      ctx = new InitialDirContext(ht);
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "INICIA CONTEXTO");

      Attributes listaAtributos = ctx.getAttributes(
              String.format(keyPropertiesFactory.getPatronBusquedaLDAPweblogic(),
                      new Object[]{nombreUsuario}));

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "ATRIBUTOS: {0}", listaAtributos);

      String identificacion = buscarAtributo(keyPropertiesFactory.getAtributoBusquedaLDAPweblogic(), listaAtributos);

      mapaUsuarioIdentificacion.put(nombreUsuario, identificacion);
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "IDENTIFICACIÓN ENCONTRADA: ( USUARIO , CC ): ( {0} , {1} ) ", new Object[]{nombreUsuario, identificacion});

      return identificacion;

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LookUpWeblogic - ERROR.. NO SE PUDO HACER CONECTAR AL SERVIDOR WEBLOGIC", e);
      throw new RuntimeException("ERROR.. NO SE PUDO CONECTAR AL SERVIDOR WEBLOGIC");

    } finally {
      try {

        if (ctx != null) {
          ctx.close();
        }
      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "FATAL -- LookUpWeblogic", e);
      }
    }

  }

  /**
   *
   * @param atributoBuscar
   * @param listaAtributos
   * @return
   */
  private String buscarAtributo(String atributoBuscar, Attributes listaAtributos) {

    if (listaAtributos == null) {

      return null;

    }

    try {
      for (NamingEnumeration ae = listaAtributos.getAll(); ae.hasMore();) {

        Attribute attr = (Attribute) ae.next();

        if (!atributoBuscar.equals(attr.getID())) {
          continue;
        }

        String salida = null;

        NamingEnumeration enumeration = attr.getAll();
        while (enumeration.hasMore()) {

          salida = (String) enumeration.next();
          break;
        }

        return salida;
      }
    } catch (NamingException e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);

    }

    return null;
  }
}
