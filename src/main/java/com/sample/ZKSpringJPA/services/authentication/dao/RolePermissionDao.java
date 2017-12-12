package com.sample.ZKSpringJPA.services.authentication.dao;

import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RolePermissionDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public RolePermission create(RolePermission permission) {
        em.persist(permission);
        em.flush();
        return permission;
    }

    @Transactional
    public RolePermission update(RolePermission permission) {
        em.merge(permission);
        em.flush();
        return permission;
    }

    @Transactional
    public void delete(RolePermission permission) {
        Object mg = em.merge(permission);
        em.remove(mg);
    }
}
