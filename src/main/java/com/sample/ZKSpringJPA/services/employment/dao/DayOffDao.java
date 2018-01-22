package com.sample.ZKSpringJPA.services.employment.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import com.google.common.base.Predicates;
import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.entity.employment.DayOff;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.services.CrudRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DayOffDao extends CrudRepository<DayOff> {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<DayOff> queryAll() {
        Query query = em.createQuery("SELECT d FROM DayOff d");
        List<DayOff> result = query.getResultList();
        return result;
    }

    public int countDayOff(final Date start, final Date to) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DayOff> cq = cb.createQuery(DayOff.class);
        Root<DayOff> dayOff = cq.from(DayOff.class);

        CriteriaQuery<DayOff> select = cq.select(dayOff);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.greaterThan(dayOff.get("date"), start));
        predicates.add(cb.lessThan(dayOff.get("date"), to));
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.select(dayOff);

        TypedQuery<DayOff> tq = em.createQuery(select);
        List<DayOff> list = tq.getResultList();
        int count  = 0;
        Calendar calendar = Calendar.getInstance();
        for(DayOff d: list){
            calendar.setTime(d.getDate());
            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                continue;
            count ++;
        }
        return count;
    }
}
