package com.sample.ZKSpringJPA.services.request.dao;

import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RequestDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Request> queryAll() {
        Query query = em.createQuery("SELECT r FROM Request r");
        List<Request> result = query.getResultList();
        return result;
    }
}
