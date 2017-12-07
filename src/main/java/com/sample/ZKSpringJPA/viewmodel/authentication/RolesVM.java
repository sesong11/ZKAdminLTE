package com.sample.ZKSpringJPA.viewmodel.authentication;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.services.authentication.RoleService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RolesVM {
    @WireVariable
    private RoleService roleService;

    @Getter @Setter
    private List<Role> roles;

    @Getter @Setter
    private Role role;

    @Init
    public void init() {
        roles = roleService.findAll();
    }

    @Command
    public void create(@BindingParam("role") final Role role) {
        this.role = role;
        roleService.create(role);
        roles.add(role);
    }
}
