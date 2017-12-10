package com.sample.ZKSpringJPA.viewmodel.authentication;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.services.authentication.RoleService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import java.util.List;
import java.util.Set;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(view = "/application/dashboard/roles-dashboard.zul",
        uuid = "roles-dashboard",
        menuName = "dashboard",
        menuOrder = "1.1",
        displayName = "Roles Dashboard",
        menuIcon = "unlock"
)
public class RolesVM {
    @WireVariable
    private RoleService roleService;

    @Getter @Setter
    private ListModelList<Role> roles;

    @Getter @Setter
    private Role role;

    @Init
    public void init() {
        roles = new ListModelList<>(roleService.findAll());
        role = new Role();
    }

    @Command
    @NotifyChange({"role"})
    public void create(@BindingParam("name") final String name) {
        role.setName(name);
        roleService.create(role);
        roles.add(role);
        role = new Role();
    }

    @Command
    @NotifyChange({"role"})
    public void select(@BindingParam("role") final Role role){
        this.role = role;
        Set<User> users = roleService.queryUsers(role);
        role.setUsers(users);
    }

    @Command
    @NotifyChange({"role"})
    public void cancel(){
        this.role = new Role();
    }

    @Command
    @NotifyChange({"role"})
    public void update(@BindingParam("name") final String name){
        role.setName(name);
        role = roleService.update(role);
    }

    @Command
    @NotifyChange({"role"})
    public void delete(){
        roleService.delete(role);
        roles.remove(role);
        role = new Role();
    }
}
