package com.sample.zkspring.services.employment.dao;

import com.sample.zkspring.entity.employment.Designation;
import com.sample.zkspring.services.CrudRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DesignationDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Designation> queryAll() {
        Query query = em.createQuery("SELECT b FROM Designation b");
        List<Designation> result = query.getResultList();
        return result;
    }
}
