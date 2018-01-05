package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EmployeeDao extends CrudRepository {
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

    @Transactional(readOnly = true)
    public List<Employee> findPaging(final int offset, final int limit) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder
                .createQuery(Employee.class);
        Root<Employee> from = criteriaQuery.from(Employee.class);
        CriteriaQuery<Employee> select = criteriaQuery.select(from);
        TypedQuery<Employee> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Employee> list = typedQuery.getResultList();
        return list;
    }
}