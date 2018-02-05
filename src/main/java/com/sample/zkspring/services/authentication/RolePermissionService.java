package com.sample.zkspring.services.authentication;

import com.sample.zkspring.entity.authentication.RolePermission;

public interface RolePermissionService {
    RolePermission create(RolePermission rolePermission);
    RolePermission update(RolePermission rolePermission);
    void delete(RolePermission rolePermission);
}
