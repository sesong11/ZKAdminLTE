package com.sample.zkspring.services.employment.dao;

import com.sample.zkspring.entity.employment.Department;
import com.sample.zkspring.services.CrudRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
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
