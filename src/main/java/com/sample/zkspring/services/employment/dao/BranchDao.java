package com.sample.zkspring.services.employment.dao;

import com.sample.zkspring.entity.employment.Branch;
import com.sample.zkspring.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class BranchDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Branch> queryAll() {
        Query query = em.createQuery("SELECT b FROM Branch b");
        List<Branch> result = query.getResultList();
        return result;
    }
}
