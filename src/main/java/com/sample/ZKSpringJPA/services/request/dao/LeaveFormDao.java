package com.sample.ZKSpringJPA.services.request.dao;

import com.sample.ZKSpringJPA.entity.employment.AllowanceType;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.employment.EmploymentHistory;
import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.RequestStatus;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveForm;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveType;
import com.sample.ZKSpringJPA.services.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class LeaveFormDao extends CrudRepository {
    @PersistenceContext
    private EntityManager em;

    public LeaveForm findByRequestId(Long id){
        LeaveForm u = (LeaveForm) em.createQuery("SELECT l FROM LeaveForm l WHERE l.request.id = :requestId").setParameter("requestId", id).getSingleResult();
        return u;
    }

    @Transactional(readOnly = true)
    public double countUsed(final Employee employee, final AllowanceType allowanceType, final int year) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LeaveForm> cq = cb.createQuery(LeaveForm.class);
        Metamodel m = em.getMetamodel();
        EntityType<LeaveForm> LeaveForm_ = m.entity(LeaveForm.class);
        Root<LeaveForm> leaveForm = cq.from(LeaveForm.class);

        Join<LeaveForm, Request> request = leaveForm.join(LeaveForm_.getSingularAttribute("request", Request.class));

        CriteriaQuery<LeaveForm> select = cq.select(leaveForm);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(request.get("requestFor"), employee));
        predicates.add(cb.equal(request.get("decisionStatus"), DecisionStatus.APPROVED));
        predicates.add(cb.equal(request.get("status"), RequestStatus.CLOSED));
        predicates.add(cb.equal(cb.function("year", Integer.class, leaveForm.get("fromDate")), year));
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.select(leaveForm);
        TypedQuery<LeaveForm> tq = em.createQuery(select);
        List<LeaveForm> list = tq.getResultList();
        double balance = 0.0;
        for(LeaveForm f: list){
            if(f.getLeaveType().getAllowanceType() == allowanceType){
                balance += f.getTotalDays();
            }
        }
        return balance;
    }
}
