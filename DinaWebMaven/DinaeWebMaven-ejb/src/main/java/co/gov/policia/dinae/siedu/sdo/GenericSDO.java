/*
 * SoftStudio
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.sdo;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @param <T>
 * @param <K>
 * @version: 1.0
 * @since: 1.0
 * @see SDO
 */
@GenericSDOQualifier
public class GenericSDO<T, K> implements SDO<T, K> {

  private static final Logger LOG = LoggerFactory.getLogger(GenericSDO.class);

  @Override
  public T find(EntityManager em, K id, Class<T> entityClass) {
    LOG.trace("entry method: find() params -->> em -> {}, id -> {}, entityClass -> {}", em, id, entityClass);
    return em.find(entityClass, id);
  }

  @Override
  public T findByNamedQuery(EntityManager em, String namedQuery) {
    LOG.trace("entry method: findByNamedQuery() params -->> em -> {}, namedQuery -> {}", em, namedQuery);
    javax.persistence.Query q = em.createNamedQuery(namedQuery);
    return (T) q.getSingleResult();
  }

  @Override
  public T findByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params) {
    LOG.trace("entry method: findByNamedQuery() params -->> em -> {}, namedQuery -> {}, params -> {}", em, namedQuery, params);
    javax.persistence.Query q = em.createNamedQuery(namedQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public T findByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params, Class<T> entityClass) {
    LOG.trace("entry method: findByNamedQuery() params -->> em -> {}, namedQuery -> {}, params -> {}, entityClass -> {}", em, namedQuery, params, entityClass);
    javax.persistence.Query q = em.createNamedQuery(namedQuery, entityClass);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public T findByJPQLQuery(EntityManager em, String jpqlQuery) {
    LOG.trace("entry method: findByJPQLQuery() params -->> em -> {}, jpqlQuery -> {}", em, jpqlQuery);
    javax.persistence.Query q = em.createQuery(jpqlQuery);
    return (T) q.getSingleResult();
  }

  @Override
  public T findByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params) {
    LOG.trace("entry method: findByJPQLQuery() params -->> em -> {}, jpqlQuery -> {}, params -> {}", em, jpqlQuery, params);
    javax.persistence.Query q = em.createQuery(jpqlQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public T findByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params, Class<T> entityClass) {
    LOG.trace("entry method: findByJPQLQuery() params -->> em -> {}, jpqlQuery -> {}, params -> {}, entityClass -> {}", em, jpqlQuery, params, entityClass);
    javax.persistence.Query q = em.createQuery(jpqlQuery, entityClass);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public T findByNativeQuery(EntityManager em, String nativeQuery) {
    LOG.trace("entry method: findByNativeQuery() params -->> em -> {}, nativeQuery -> {}", em, nativeQuery);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery);
    return (T) q.getSingleResult();
  }

  @Override
  public T findByNativeQuery(EntityManager em, String nativeQuery, Object... params) {
    LOG.trace("entry method: findByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}", em, nativeQuery, params);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery);
    if (params != null) {
      int i = 1;
      for (Object param : params) {
        if (param != null) {
          LOG.debug("param [" + i + "]: " + param);
          q.setParameter(i, param);
        }
        i++;
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public T findByNativeQuery(EntityManager em, String nativeQuery, Map<String, Object> params) {
    LOG.trace("entry method: findByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}", em, nativeQuery, params);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public T findByNativeQuery(EntityManager em, String nativeQuery, Map<String, Object> params, Class<T> entityClass) {
    LOG.trace("entry method: findByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}, entityClass -> {}", em, nativeQuery, params, entityClass);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery, entityClass);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return (T) q.getSingleResult();
  }

  @Override
  public List<T> getResultList(EntityManager em, Class<T> entityClass) {
    LOG.trace("entry method: getResultList() params -->> em -> {}, class -> {}", em, entityClass);
    javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    return em.createQuery(cq).getResultList();
  }

  @Override
  public List<T> getResultListByNamedQuery(EntityManager em, String namedQuery) {
    LOG.trace("entry method: getResultListByNamedQuery() params -->> em -> {}, namedQuery -> {}", em, namedQuery);
    javax.persistence.Query q = em.createNamedQuery(namedQuery);
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params) {
    LOG.trace("entry method: getResultListByNamedQuery() params -->> em -> {}, namedQuery -> {}, params -> {}", em, namedQuery, params);
    javax.persistence.Query q = em.createNamedQuery(namedQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params, Class<T> entityClass) {
    LOG.trace("entry method: getResultListByNamedQuery() params -->> em -> {}, namedQuery -> {}, params -> {}, entityClass -> {}", em, namedQuery, params, entityClass);
    javax.persistence.Query q = em.createNamedQuery(namedQuery, entityClass);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByJPQLQuery(EntityManager em, String jpqlQuery) {
    LOG.trace("entry method: getResultListByJPQLQuery() params -->> em -> {}, jpqlQuery -> {}", em, jpqlQuery);
    javax.persistence.Query q = em.createQuery(jpqlQuery);
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params) {
    LOG.trace("entry method: getResultListByJPQLQuery() params -->> em -> {}, jpqlQuery -> {}, params -> {}", em, jpqlQuery, params);
    javax.persistence.Query q = em.createQuery(jpqlQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params, Class<T> entityClass) {
    LOG.trace("entry method: getResultListByJPQLQuery() params -->> em -> {}, jpqlQuery -> {}, params -> {}, entityClass -> {}", em, jpqlQuery, params, entityClass);
    javax.persistence.Query q = em.createQuery(jpqlQuery, entityClass);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery) {
    LOG.trace("entry method: getResultListByNativeQuery() params -->> em -> {}, nativeQuery -> {}", em, nativeQuery);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery);
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Object... params) {
    LOG.trace("entry method: getResultListByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}", em, nativeQuery, params);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery);
    if (params != null) {
      int i = 1;
      for (Object param : params) {
        if (param != null) {
          LOG.debug("param [" + i + "]: " + param);
          q.setParameter(i, param);
        }
        i++;
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Map<Integer, Object> params) {
    LOG.trace("entry method: getResultListByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}", em, nativeQuery, params);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get((Integer) entry.getKey()));
        q.setParameter((Integer) entry.getKey(), params.get((Integer) entry.getKey()));
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Class<T> entityClass, Object... params) {
    LOG.trace("entry method: getResultListByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}, entityClass -> {}", em, nativeQuery, params, entityClass);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery, entityClass);
    if (params != null) {
      int i = 1;
      for (Object param : params) {
        if (param != null) {
          LOG.debug("param [" + i + "]: " + param);
          q.setParameter(i, param);
        }
        i++;
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByNativeQuery(EntityManager em, String nativeQuery, Map<Integer, Object> params, Class<T> entityClass) {
    LOG.trace("entry method: getResultListByNativeQuery() params -->> em -> {}, nativeQuery -> {}, params -> {}, entityClass -> {}", em, nativeQuery, params, entityClass);
    javax.persistence.Query q = em.createNativeQuery(nativeQuery, entityClass);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get((Integer) entry.getKey()));
        q.setParameter((Integer) entry.getKey(), params.get((Integer) entry.getKey()));
      }
    }
    return q.getResultList();
  }

  @Override
  public List<T> getResultListByRange(EntityManager em, Class<T> entityClass, int[] range) {
    LOG.trace("entry method: getResultListByRange() params -->> em -> {}, entityClass -> {}, range[] -> {}", em, entityClass, range);
    javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    javax.persistence.Query q = em.createQuery(cq);
    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  @Override
  public T persist(EntityManager em, T entity) {
    LOG.trace("entry method: persist() params -->> em -> {}, entity -> {}", em, entity);
    em.persist(entity);
    return entity;
  }

  @Override
  public void merge(EntityManager em, T entity) {
    LOG.trace("entry method: merge() params -->> em -> {}, entity -> {}", em, entity);
    em.merge(entity);
  }

  @Override
  public void remove(EntityManager em, T entity) {
    LOG.trace("entry method: remove() params -->> em -> {}, entity -> {}", em, entity);
    em.remove(em.merge(entity));
  }

  @Override
  public void remove(EntityManager em, K id, Class<T> entityClass) {
    LOG.trace("entry method: remove() params -->> em -> {}, id -> {}, entityClass -> {}", em, id, entityClass);
    em.remove(em.find(entityClass, id));
  }

  @Override
  public int executeNamedQuery(EntityManager em, String namedQuery, Map<String, Object> params) {
    LOG.trace("entry method: <<executeNamedQuery>> params -->> em -> {}, namedQuery -> {}, params -> {}", em, namedQuery, params);
    Query q = em.createNamedQuery(namedQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    int registrosAfectados = q.executeUpdate();
    LOG.info("entry method: <<executeNamedQuery>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
    return registrosAfectados;
  }

  @Override
  public int executeJPQLQuery(EntityManager em, String jpqlQuery, Map<String, Object> params) {
    LOG.trace("entry method: <<executeJPQLQuery>>  params -->> em -> {}, jpqlQuery -> {}, params -> {}", em, jpqlQuery, params);
    Query q = em.createQuery(jpqlQuery);
    if (params != null) {
      for (Map.Entry entry : params.entrySet()) {
        LOG.debug(entry.getKey().toString() + ": " + params.get(entry.getKey().toString()));
        q.setParameter(entry.getKey().toString(), params.get(entry.getKey().toString()));
      }
    }
    int registrosAfectados = q.executeUpdate();
    LOG.info("entry method: <<executeJPQLQuery>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
    return registrosAfectados;
  }

  @Override
  public int executeNativeQuery(EntityManager em, String nativeQuery, Object... params) {
    LOG.trace("entry method: <<executeNativeQuery>> params -->> em -> {}, nativeQuery -> {}, params -> {}", em, nativeQuery, params);
    Query q = em.createNativeQuery(nativeQuery);
    if (params != null) {
      int i = 1;
      for (Object parametro : params) {
        if (parametro != null) {
          q.setParameter(i, parametro);
        }
        i++;
      }
    }
    int registrosAfectados = q.executeUpdate();
    LOG.info("left method: <<executeNativeQuery>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
    return registrosAfectados;
  }

  @Override
  public int count(EntityManager em, Class<T> entityClass) {
    LOG.trace("entry method: count() params -->> em -> {}, entityClass -> {}", em, entityClass);
    javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
    cq.select(em.getCriteriaBuilder().count(rt));
    javax.persistence.Query q = em.createQuery(cq);
    return ((Long) q.getSingleResult()).intValue();
  }
}
