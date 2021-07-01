package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.cache.LookUpUtilDinae;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CompromisoProyectoListener implements Serializable {

  /**
   *
   * @param compromisoProyecto
   */
  @PostUpdate
  @PostPersist
  public void actualizarFechaUltimaProyecto(CompromisoProyecto compromisoProyecto) {

    try {

      ICompromisoProyectoLocal iCompromisoProyectoLoca = LookUpUtilDinae.getInstance().lookUpCompromisoProyecto();

      iCompromisoProyectoLoca.actualizarFechaUltimaProyectoListener(compromisoProyecto);

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " ---FIN ERROR PostPersist CompromisoProyectoListenersEjbBean --- ");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
    }
  }

}
