package com.sample.ZKSpringJPA.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.services.authentication.UserService;

@Service("userService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User getUserByUsername(String username) {
		return userDao.get(username);
	}

	@Override
	public User addUser(User user) {
		return userDao.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userDao.update(user);
	}

	@Override
	public void deleteUser(User user) {
		userDao.delete(user);
	}

	@Override public Set<Role> queryRoles(final User user) {
		return userDao.queryRoles(user);
	}

	@Override
	public List<User> allUser(){
		return userDao.queryAll();
	}

}
