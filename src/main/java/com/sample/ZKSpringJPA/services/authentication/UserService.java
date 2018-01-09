package com.sample.ZKSpringJPA.services.authentication;

import java.util.List;
import java.util.Set;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.authentication.UserRole;

public interface UserService {
	List<User> allUser();
	User getUserByUsername(String username);
	User addUser(User user);
	User updateUser(User user);
	void deleteUser(User user);
	Set<Role> queryRoles(User user);
}
