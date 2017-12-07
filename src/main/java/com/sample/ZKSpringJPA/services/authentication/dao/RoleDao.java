package com.sample.ZKSpringJPA.services.authentication.dao;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Role> queryAll() {
        Query query = em.createQuery("SELECT r FROM Role r");
        List<Role> result = query.getResultList();
        return result;
    }

    @Transactional(readOnly = true)
    public Role find(long id) {
        Role role = em.find(Role.class, id);
        return role;
    }

    @Transactional
    public Role save(Role role) {
        em.persist(role);
        em.flush();
        return role;
    }

    @Transactional
    public Role update(Role role) {
        em.merge(role);
        em.flush();
        return role;
    }

    @Transactional
    public void delete(Role role) {
        Object mg = em.merge(role);
        em.remove(mg);
    }
}
