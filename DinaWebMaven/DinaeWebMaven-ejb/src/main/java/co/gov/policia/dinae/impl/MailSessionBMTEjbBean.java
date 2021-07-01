package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IMailSessionBMTLocal;
import co.gov.policia.dinae.modelo.CorreoEnvio;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MailSessionBMTEjbBean implements IMailSessionBMTLocal, Serializable {

  @Resource
  private UserTransaction utx;

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param correoEnvio
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CorreoEnvio actualizarCorreoEnvio(CorreoEnvio correoEnvio) throws JpaDinaeException {

    try {

      utx.begin();

      if (correoEnvio.getId() == null) {

        entityManager.persist(correoEnvio);
      } else {

        entityManager.merge(correoEnvio);

      }

      utx.commit();

      return correoEnvio;

    } catch (Exception e) {

      try {

        utx.rollback();

      } catch (Exception e1) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "MailSessionBMTEjbBean BMT", e1);
      }

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "MailSessionBMTEjbBean BMT", e);
    }

    return null;

  }

}
