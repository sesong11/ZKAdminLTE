package com.sample.zkspring.services.authentication;

import java.util.List;
import java.util.Set;

import com.sample.zkspring.entity.authentication.Role;
import com.sample.zkspring.entity.authentication.User;

public interface UserService {
	List<User> allUser();
	User getUserByUsername(String username);
	User addUser(User user);
	User updateUser(User user);
	void deleteUser(User user);
	Set<Role> queryRoles(User user);
}
