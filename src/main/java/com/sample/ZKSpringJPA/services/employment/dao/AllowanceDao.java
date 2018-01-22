package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AllowanceDao extends CrudRepository<Allowance> {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Allowance> queryAll() {
        Query query = em.createQuery("SELECT b FROM Allowance b");
        List<Allowance> result = query.getResultList();
        return result;
    }
}