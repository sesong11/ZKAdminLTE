package com.sample.ZKSpringJPA.services;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CrudRepository<T> {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Object find(Long id, Class<T> cls) {
        Object object = em.find(cls, id);
        return object;
    }

    @Transactional()
    public Object create(Object object) {
        em.persist(object);
        em.flush();
        return object;
    }

    @Transactional
    public Object createOrUpdate(Object object) {
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

    @Transactional(readOnly = true)
    public int count(Class<T> cls) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(cls)));
        int count = em.createQuery(cq).getSingleResult().intValue();

        return count;
    }

    @Transactional(readOnly = true)
    public int count(Class<T> cls, final String filter, final String filterBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> from = cq.from(cls);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(from.get(filterBy)), "%"+filter+"%"));
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.select(cb.count(from));
        int count = em.createQuery(cq).getSingleResult().intValue();

        return count;
    }

    @Transactional(readOnly = true)
    public List<T> findPaging(final int offset, final int limit, final Class cls) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cls);
        Root<T> from = cq.from(cls);
        CriteriaQuery<T> select = cq.select(from);
        TypedQuery<T> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<T> list = typedQuery.getResultList();
        return list;
    }

    @Transactional(readOnly = true)
    public List<T> findPaging(final int offset, final int limit, final Class cls, final String filter, final String filterBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cls);
        Root<T> from = cq.from(cls);
        CriteriaQuery<T> select = cq.select(from);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(from.get(filterBy)), "%"+filter+"%"));
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<T> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<T> list = typedQuery.getResultList();
        return list;
    }
}
