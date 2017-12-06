package com.sample.ZKSpringJPA.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sample.ZKSpringJPA.entity.User;

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
}
