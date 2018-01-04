package com.sample.ZKSpringJPA.services.dashboard.dao;
import com.sample.ZKSpringJPA.entity.dashboard.Application;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class ApplicationDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Application> queryAll() {
        Query query = em.createQuery("SELECT b FROM Application b");
        List<Application> result = query.getResultList();
        return result;
    }

    @Transactional(readOnly = true)
    public List<Application> queryEnabled() {
        Query query = em.createQuery("SELECT b FROM Application b WHERE is_enabled = true ORDER BY b.sequence ASC");
        List<Application> result = query.getResultList();
        return result;
    }
}
