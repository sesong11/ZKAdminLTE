package com.sample.ZKSpringJPA.services.authentication;

import com.sample.ZKSpringJPA.entity.authentication.RolePermission;

public interface RolePermissionService {
    RolePermission create(RolePermission rolePermission);
    RolePermission update(RolePermission rolePermission);
    void delete(RolePermission rolePermission);
}
