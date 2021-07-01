package co.gov.policia.dinae.cache;

import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IDatosGenericoLocal;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class LookUpUtilDinae implements Serializable {

  private static LookUpUtilDinae lookUpUtilDinae;
  private final Context context;

  /**
   *
   * @throws Exception
   */
  private LookUpUtilDinae() throws Exception {

    context = new InitialContext();
  }

  /**
   *
   * @return @throws Exception
   */
  public static LookUpUtilDinae getInstance() throws Exception {

    if (lookUpUtilDinae == null) {
      lookUpUtilDinae = new LookUpUtilDinae();
    }

    return lookUpUtilDinae;
  }

  /**
   *
   * @return @throws Exception
   */
  public ICompromisoProyectoLocal lookUpCompromisoProyecto() throws Exception {

    return (ICompromisoProyectoLocal) context.lookup("java:module/CompromisoProyectoEjbBean");
  }

  /**
   *
   * @return @throws Exception
   */
  public IDatosGenericoLocal lookUpDatosGenerico() throws Exception {

    return (IDatosGenericoLocal) context.lookup("java:global/DatosGenericoEjbBean!co.gov.policia.dinae.interfaces.IDatosGenericoLocal");
  }
}
