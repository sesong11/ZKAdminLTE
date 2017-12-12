package com.sample.ZKSpringJPA.services.authentication.dao;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import com.sample.ZKSpringJPA.entity.authentication.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public Set<User> queryUsers(final Role role) {
        Query query = em.createQuery("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId").setParameter("roleId", role.getId());
        Set<User> result = new HashSet<>(query.getResultList());
        return result;
    }

    @Transactional(readOnly = true)
    public Set<RolePermission> queryPermissions(final Role role) {
        Query query = em.createQuery("SELECT p FROM RolePermission p WHERE p.role.id = :roleId").setParameter("roleId", role.getId());
        Set<RolePermission> result = new HashSet<RolePermission>(query.getResultList());
        return result;
    }
}
