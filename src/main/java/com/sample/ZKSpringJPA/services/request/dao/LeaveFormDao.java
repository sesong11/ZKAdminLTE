package com.sample.ZKSpringJPA.services.request.dao;

import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveForm;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class LeaveFormDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    public LeaveForm findByRequestId(Long id){
        LeaveForm u = (LeaveForm) em.createQuery("SELECT l FROM LeaveForm l WHERE l.request.id = :requestId").setParameter("requestId", id).getSingleResult();
        return u;
    }
}
