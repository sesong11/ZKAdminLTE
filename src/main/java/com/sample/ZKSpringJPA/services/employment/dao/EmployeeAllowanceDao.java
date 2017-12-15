package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.employment.EmployeeAllowance;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeAllowanceDao extends CrudRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List<EmployeeAllowance> queryAll() {
        Query query = em.createQuery("SELECT b FROM EmployeeAllowance b");
        List<EmployeeAllowance> result = query.getResultList();
        return result;
    }
}