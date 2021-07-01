/*
 * SoftStudio
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.sdo;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @param <T>
 * @param <K>
 * @version: 1.0
 * @since: 1.0
 */
public interface SDO<T, K> {

  T find(EntityManager em, K id, Class<T> entityClass);

  T findByNamedQuery(EntityManager em, String namedQuery);

  T findByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params);

  T findByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params, Class<T> entityClass);

  T findByJPQLQuery(EntityManager em, String jpqlQuery);

  T findByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params);

  T findByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params, Class<T> entityClass);

  T findByNativeQuery(EntityManager em, String nativeQuery);

  T findByNativeQuery(EntityManager em, String nativeQuery, Object... params);

  T findByNativeQuery(EntityManager em, String nativeQuery, Map<String, Object> params);

  T findByNativeQuery(EntityManager em, String nativeQuery, Map<String, Object> params, Class<T> entityClass);

  List<T> getResultList(EntityManager em, Class<T> entityClass);

  List<T> getResultListByNamedQuery(EntityManager em, String namedQuery);

  List<T> getResultListByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params);

  List<T> getResultListByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params, Class<T> entityClass);

  List<T> getResultListByJPQLQuery(EntityManager em, String jpqlQuery);

  List<T> getResultListByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params);

  List<T> getResultListByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params, Class<T> entityClass);

  List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery);

  List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Object... params);

  List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Map<Integer, Object> params);

  List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Class<T> entityClass, Object... params);

  List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Map<Integer, Object> params, Class<T> entityClass);

  List<T> getResultListByRange(EntityManager em, Class<T> entityClass, int[] range);

  T persist(EntityManager em, T entity);

  void merge(EntityManager em, T entity);

  void remove(EntityManager em, T entity);

  void remove(EntityManager em, K id, Class<T> entityClass);

  int executeNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params);

  int executeJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params);

  int executeNativeQuery(EntityManager em, String nativeQuery, Object... params);

  int count(EntityManager em, Class<T> entityClass);
}
