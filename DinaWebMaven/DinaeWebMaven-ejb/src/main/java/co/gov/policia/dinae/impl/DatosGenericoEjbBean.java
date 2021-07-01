package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.interfaces.IDatosGenericoLocal;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DatosGenericoEjbBean implements IDatosGenericoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Resource
  private UserTransaction tx;

  /**
   *
   * @param mapa
   * @param inputStream
   * @return
   * @throws Exception
   */
  @Override
  public byte[] generarReporte(HashMap mapa, InputStream inputStream) throws Exception {

//        try {
//            
//            tx.begin();
//        
//            java.sql.Connection connection = entityManager.unwrap(java.sql.Connection.class);
//            
//            mapa.put("REPORT_CONNECTION", connection);
//            
//            JasperPrint print = JasperFillManager.fillReport(inputStream, mapa, connection);
//
//            byte[] bitesPdf = JasperExportManager.exportReportToPdf(print);
//
//            connection.close();
//
//            return bitesPdf;
//            
//        }
//        catch (Exception e) {
//             throw e;
//        }
//        finally {
//            try {
//                tx.rollback();
//            } catch (Exception e2) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e2.getMessage(), e2);
//            }
//            
//        }
    return null;

  }

}
