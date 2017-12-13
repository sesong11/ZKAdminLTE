package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.employment.Department;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DepartmentDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Department> queryAll() {
        Query query = em.createQuery("SELECT d FROM Department d");
        List<Department> result = query.getResultList();
        return result;
    }
}
