package com.sample.ZKSpringJPA.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.User;

/**
 * Data Access Object for User Entity
 */
@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	public List<User> queryAll() {
		Query query = em.createQuery("SELECT u FROM User u");
		List<User> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly = true)
	public User get(String username) {
		User u = (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username").setParameter("username", username).getSingleResult();
		return u;
	}

	@Transactional
	public User save(User user) {
		em.persist(user);
		em.flush();
		return user;
	}

	@Transactional
	public User update(User user) {
		em.merge(user);
		em.flush();
		return user;
	}

	@Transactional
	public void delete(User user) {
		Object mg = em.merge(user);
		em.remove(mg);
	}

	@Transactional(readOnly = true)
	public List<Role> queryRoles(final User user) {
		Query query = em.createQuery("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId").setParameter("userId", user.getId());
		List<Role> result = query.getResultList();
		return result;
	}
}
