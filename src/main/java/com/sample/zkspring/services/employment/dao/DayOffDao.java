package com.sample.zkspring.services.employment.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sample.zkspring.entity.employment.DayOff;
import com.sample.zkspring.services.CrudRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DayOffDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<DayOff> queryAll() {
        Query query = em.createQuery("SELECT d FROM DayOff d");
        List<DayOff> result = query.getResultList();
        return result;
    }
}
