package com.sample.ZKSpringJPA.services.employment.dao;

import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class BranchDao extends CrudRepository<Branch> {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Branch> queryAll() {
        Query query = em.createQuery("SELECT b FROM Branch b");
        List<Branch> result = query.getResultList();
        return result;
    }
}
