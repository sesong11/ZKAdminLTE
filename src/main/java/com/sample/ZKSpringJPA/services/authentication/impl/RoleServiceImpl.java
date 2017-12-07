package com.sample.ZKSpringJPA.services.authentication.impl;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.services.authentication.RoleService;
import com.sample.ZKSpringJPA.services.authentication.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.queryAll();
    }

    @Override
    public Role find(long id) {
        return roleDao.find(id);
    }

    @Override
    public Role create(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }
}
