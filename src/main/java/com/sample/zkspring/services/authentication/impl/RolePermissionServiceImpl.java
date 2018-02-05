package com.sample.zkspring.services.authentication.impl;

import com.sample.zkspring.entity.authentication.RolePermission;
import com.sample.zkspring.services.authentication.RolePermissionService;
import com.sample.zkspring.services.authentication.dao.RolePermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("rolePermissionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RolePermissionServiceImpl implements RolePermissionService{

    @Autowired
    RolePermissionDao rolePermissiondoa;

    @Override
    public RolePermission create(RolePermission rolePermission) {
        return rolePermissiondoa.create(rolePermission);
    }

    @Override
    public RolePermission update(RolePermission rolePermission) {
        return rolePermissiondoa.update(rolePermission);
    }

    @Override
    public void delete(RolePermission rolePermission) {
        rolePermissiondoa.delete(rolePermission);
    }
}
