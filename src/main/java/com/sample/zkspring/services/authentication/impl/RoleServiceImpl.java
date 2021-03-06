package com.sample.zkspring.services.authentication.impl;

import com.sample.zkspring.entity.authentication.Role;
import com.sample.zkspring.entity.authentication.RolePermission;
import com.sample.zkspring.entity.authentication.User;
import com.sample.zkspring.services.authentication.RoleService;
import com.sample.zkspring.services.authentication.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    @Override
    public Set<User> queryUsers(final Role role){
        return roleDao.queryUsers(role);
    }

    @Override
    public Set<RolePermission> queryPermissions(final Role role){
        return roleDao.queryPermissions(role);
    }
}
