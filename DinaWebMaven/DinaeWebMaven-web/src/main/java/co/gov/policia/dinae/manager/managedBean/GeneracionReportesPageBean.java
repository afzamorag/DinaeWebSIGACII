package co.gov.policia.dinae.manager.managedBean;

import java.io.Serializable;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class GeneracionReportesPageBean implements Serializable {

  // Filtros de busqueda
  /**
   * Enlaza con el reporte de las rutas en PDF generado en Jasper
   */
  private Integer idProyecto;

  public void setIdProyecto(Integer idProyecto) {
    this.idProyecto = idProyecto;
  }

  public Integer getIdProyecto() {
    return idProyecto;
  }

  public String imprimir() {
    try {
      HashMap mapa = new HashMap(40);

      mapa.put("p_id_proyecto", this.getIdProyecto());

      //GeneradorReportesServicio.getInstancia().reporteador(mapa,GeneradorReportesServicio.REPORTE1);
      return null;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Context getInitialContext() throws NamingException {
    return new InitialContext();
  }

}
