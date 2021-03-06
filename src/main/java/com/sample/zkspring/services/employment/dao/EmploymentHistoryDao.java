package com.sample.zkspring.services.employment.dao;

import com.sample.zkspring.entity.employment.Employee;
import com.sample.zkspring.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class EmploymentHistoryDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Employee> queryAll() {
        Query query = em.createQuery("SELECT h FROM EmployeeHistory h");
        List<Employee> result = query.getResultList();
        return result;
    }
}
