package com.sample.zkspring.services.employment.dao;

import com.sample.zkspring.entity.authentication.User;
import com.sample.zkspring.entity.employment.Employee;
import com.sample.zkspring.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public Employee findByUser(final User user) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.user.id = :userId").setParameter("userId", user.getId());
        return (Employee) query.getSingleResult();
        
    }
}
