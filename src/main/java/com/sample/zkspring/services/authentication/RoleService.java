package com.sample.zkspring.services.authentication;

import com.sample.zkspring.entity.authentication.Role;
import com.sample.zkspring.entity.authentication.RolePermission;
import com.sample.zkspring.entity.authentication.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAll();
    Role find(long id);
    Role create(Role role);
    Role update(Role role);
    void delete(Role role);
    Set<User> queryUsers(final Role role);
    Set<RolePermission> queryPermissions(final Role role);
}
