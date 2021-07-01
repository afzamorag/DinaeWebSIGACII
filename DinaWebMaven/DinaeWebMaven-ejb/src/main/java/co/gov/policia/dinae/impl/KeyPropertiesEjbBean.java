package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IkeyPropertiesLocal;
import co.gov.policia.dinae.modelo.ConcecutivoLiberaConvocatoria;
import co.gov.policia.dinae.modelo.KeyProperties;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class KeyPropertiesEjbBean implements IkeyPropertiesLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param clave
   * @return una KeyProperties, si no existe retorna NULL
   * @throws JpaDinaeException
   */
  @Override
  public KeyProperties getKeyPropertiePorClave(String clave) throws JpaDinaeException {

    try {

      return entityManager.find(KeyProperties.class, clave);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param clave
   * @return
   * @throws JpaDinaeException
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public Long getConcecutivoConvocatoriaPropertiePorClave(String clave) throws JpaDinaeException {

    try {

      //PRIMERO CONSULTAMOS SI EXISTEN CONCECUTIVO LIBERADOS
      List<ConcecutivoLiberaConvocatoria> listaConcecutivosLiberados = entityManager.
              createNamedQuery("ConcecutivoLiberaConvocatoria.findAllPorClaveFinanciaEnsayoOrdenadorPorconcecutivoLiberado")
              .setParameter("claveFinanciaEnsayo", clave)
              .getResultList();

      if (!listaConcecutivosLiberados.isEmpty()) {
        //SI EXISTEN CONCECUTIVOS LIBERADOS
        //RETORNAMOS EL PRIMERO(MENOR) GENERADO
        ConcecutivoLiberaConvocatoria concecutivoLiberaConvocatoria = listaConcecutivosLiberados.get(0);

        Long numConcecutivo = concecutivoLiberaConvocatoria.getConcecutivoLiberado();

        entityManager.remove(concecutivoLiberaConvocatoria);

        return numConcecutivo;
      }
      //SI NO EXISTEN CONCECUTIVCOS GENERADOS, CONSULTAMOS DE EL ULTIMO CONCECUTIVO GENERADO
      KeyProperties keyProperties = entityManager.find(KeyProperties.class, clave);

      Long numConcecutivo = Long.parseLong(keyProperties.getValor());
      keyProperties.setValor(String.valueOf(numConcecutivo + 1));

      entityManager.merge(keyProperties);

      return numConcecutivo;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);
    }

  }
}
