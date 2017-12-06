package com.sample.ZKSpringJPA.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.sample.ZKSpringJPA.entity.User;
import com.sample.ZKSpringJPA.services.UserService;

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
	public List<User> allUser(){
		return userDao.queryAll();
	}

}