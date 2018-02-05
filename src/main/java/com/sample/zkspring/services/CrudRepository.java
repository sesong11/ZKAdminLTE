package com.sample.zkspring.services;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public <T> Object find(Long id, Class<T> cls) {
        Object object = em.find(cls, id);
        return object;
    }

    @Transactional
    public Object create(Object object) {
        em.persist(object);
        em.flush();
        return object;
    }

    @Transactional
    public Object update(Object object) {
        em.merge(object);
        em.flush();
        return object;
    }

    @Transactional
    public void delete(Object object) {
        Object mg = em.merge(object);
        em.remove(mg);
    }
}
