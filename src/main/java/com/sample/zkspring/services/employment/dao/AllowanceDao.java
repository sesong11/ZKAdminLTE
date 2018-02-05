package com.sample.zkspring.services.employment.dao;

import com.sample.zkspring.entity.employment.Allowance;
import com.sample.zkspring.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AllowanceDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Allowance> queryAll() {
        Query query = em.createQuery("SELECT b FROM Allowance b");
        return query.getResultList();
    }
}