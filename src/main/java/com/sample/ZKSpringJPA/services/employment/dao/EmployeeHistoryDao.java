package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeHistoryDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Employee> queryAll() {
        Query query = em.createQuery("SELECT h FROM EmployeeHistory h");
        List<Employee> result = query.getResultList();
        return result;
    }
}
