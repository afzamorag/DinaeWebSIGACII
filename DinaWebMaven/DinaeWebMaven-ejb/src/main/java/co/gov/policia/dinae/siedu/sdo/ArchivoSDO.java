/*
 * Softstudio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.sdo;

import javax.persistence.EntityManager;

/**
 * @author: Diego Poveda.
 * @param <Archivo>
 * @param <Long>
 * @name:
 * @descripcion:
 * @version: 1.0
 * @since: JDK_1.7
 */
public interface ArchivoSDO<Archivo, Long> extends SDO<Archivo, Long> {

  Archivo persist(EntityManager em, Archivo entity, String path);

  void merge(EntityManager em, Archivo entity, String path);

  void remove(EntityManager em, Archivo entity, String path);

  void remove(EntityManager em, Long id, Class<Archivo> entityClass, String path);
}
