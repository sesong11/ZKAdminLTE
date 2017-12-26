package com.sample.ZKSpringJPA.services.request.dao;

import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.RequestStatus;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.services.CrudRepository;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Repository
public class RequestDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserCredentialService userCredentialService;

    @Transactional(readOnly = true)
    public List<Request> queryAll() {
        Query query = em.createQuery("SELECT r FROM Request r");
        List<Request> result = query.getResultList();
        return result;
    }

    public List<Request> findPaging(final int offset, final int limit) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Request> criteriaQuery = criteriaBuilder
                .createQuery(Request.class);
        Root<Request> from = criteriaQuery.from(Request.class);
        CriteriaQuery<Request> select = criteriaQuery.select(from);
        TypedQuery<Request> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Request> list = typedQuery.getResultList();
        return list;
    }

    public Long count(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Request.class)));
        Long count = em.createQuery(countQuery)
                .getSingleResult();
        return count;
    }

    public TreeSet<Approval> findApproval(Long id) {
        Query query = em.createQuery("SELECT a FROM Approval a WHERE a.request.id = :requestId").setParameter("requestId", id);
        TreeSet<Approval> result = new TreeSet<>(query.getResultList());
        return result;
    }

    public List<Request> findMyRequest(int offset, int limit, final RequestStatus requestStatus) {
        Employee currentEmployee = userCredentialService.getCurrentEmployee();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Request> criteriaQuery = criteriaBuilder
                .createQuery(Request.class);
        Root<Request> from = criteriaQuery.from(Request.class);
        CriteriaQuery<Request> select = criteriaQuery.select(from);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(from.get("status"), requestStatus));
        predicates.add(criteriaBuilder.equal(from.get("requestFor"), currentEmployee));
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        criteriaQuery.select(from);
        TypedQuery<Request> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Request> list = typedQuery.getResultList();
        return list;
    }

    public Long findMyRequestCounter(RequestStatus requestStatus) {
        Employee currentEmployee = userCredentialService.getCurrentEmployee();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Request> from = countQuery.from(Request.class);
        countQuery.select(criteriaBuilder.count(from));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(from.get("status"), requestStatus));
        predicates.add(criteriaBuilder.equal(from.get("requestFor"), currentEmployee));
        countQuery.where(predicates.toArray(new Predicate[]{}));

        Long count = em.createQuery(countQuery).getSingleResult();
        return count;
    }
}
