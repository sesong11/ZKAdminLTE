package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.employment.EmploymentHistory;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.services.CrudRepository;
import com.sample.ZKSpringJPA.services.employment.EmployeeHistoryService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Repository
public class EmployeeDao extends CrudRepository<Employee> {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Employee> queryAll() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        List<Employee> result = query.getResultList();
        return result;
    }

    @Transactional(readOnly = true)
    public Employee findByUser(final User user) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.user.id = :userId").setParameter("userId", user.getId());
        Employee result = (Employee) query.getSingleResult();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findPaging(final int offset, final int limit, final Class cls) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(cls);
        Root<Employee> from = cq.from(cls);
        CriteriaQuery<Employee> select = cq.select(from);
        TypedQuery<Employee> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Employee> list = typedQuery.getResultList();
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public int count(Class<Employee> cls, final String filter, final String filterBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employee> from = cq.from(cls);
        List<Predicate> predicates = new ArrayList<>();
        if("fullName".equals(filterBy))
            predicates.add(cb.like(cb.concat(cb.concat(cb.lower(from.get("firstName")), " "), cb.lower(from.get("lastName"))), "%"+filter+"%"));
        else if(filterBy != null)
            predicates.add(cb.like(cb.lower(from.get(filterBy)), "%"+filter+"%"));
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.select(cb.count(from));
        int count = em.createQuery(cq).getSingleResult().intValue();

        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findPaging(final int offset, final int limit, final Class cls, final String filter, final String filterBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(cls);
        Root<Employee> from = cq.from(cls);
        CriteriaQuery<Employee> select = cq.select(from);
        List<Predicate> predicates = new ArrayList<>();
        if("fullName".equals(filterBy))
            predicates.add(cb.like(cb.concat(cb.concat(cb.lower(from.get("firstName")), " "), cb.lower(from.get("lastName"))), "%"+filter+"%"));
        else if(filterBy != null)
            predicates.add(cb.like(cb.lower(from.get(filterBy)), "%"+filter+"%"));
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Employee> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Employee> list = typedQuery.getResultList();
        return list;
    }

    @Transactional(readOnly = true)
    public int count(Class<Employee> cls, final String filter, final String filterBy, final HashMap<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employee> employee = cq.from(Employee.class);
        Metamodel m = em.getMetamodel();
        EntityType<Employee> employee_ = m.entity(Employee.class);

        Join<Employee, EmploymentHistory> histories = employee.join(employee_.getSet("employmentHistories", EmploymentHistory.class), JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if("fullName".equals(filterBy))
            predicates.add(cb.like(cb.concat(cb.concat(cb.lower(employee.get("firstName")), " "), cb.lower(employee.get("lastName"))), "%"+filter.toLowerCase()+"%"));
        else if(filterBy != null)
            predicates.add(cb.like(cb.lower(employee.get(filterBy)), "%"+filter.toLowerCase()+"%"));
        for (String key: filters.keySet()) {
            try {
                employee.get(key);
                predicates.add(cb.equal(employee.get(key), filters.get(key)));
            }catch (IllegalArgumentException e){
                predicates.add(cb.equal(histories.get(key), filters.get(key)));
            }
        }
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.select(cb.count(employee));
        int count = em.createQuery(cq).getSingleResult().intValue();

        return count;
    }

    @Transactional(readOnly = true)
    public List<Employee> findPaging(final int offset, final int limit, final Class cls, final String filter, final String filterBy, final HashMap<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(cls);
        Root<Employee> employee = cq.from(Employee.class);
        Metamodel m = em.getMetamodel();
        CriteriaQuery<Employee> select = cq.select(employee);
        EntityType<Employee> employee_ = m.entity(Employee.class);

        Join<Employee, EmploymentHistory> histories = employee.join(employee_.getSet("employmentHistories", EmploymentHistory.class), JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if("fullName".equals(filterBy))
            predicates.add(cb.like(cb.concat(cb.concat(cb.lower(employee.get("firstName")), " "), cb.lower(employee.get("lastName"))), "%"+filter.toLowerCase()+"%"));
        else if(filterBy != null)
            predicates.add(cb.like(cb.lower(employee.get(filterBy)), "%"+filter.toLowerCase()+"%"));
        for (String key: filters.keySet()) {
            try {
                employee.get(key);
                predicates.add(cb.equal(employee.get(key), filters.get(key)));
            }catch (IllegalArgumentException e){
                predicates.add(cb.equal(histories.get(key), filters.get(key)));
            }
        }
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Employee> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Employee> list = typedQuery.getResultList();
        return list;
    }
}