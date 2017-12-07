package com.sample.ZKSpringJPA.services.authentication;

import com.sample.ZKSpringJPA.entity.authentication.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role find(long id);
    Role create(Role role);
    Role update(Role role);
    void delete(Role role);
}
