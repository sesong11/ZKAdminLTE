package com.sample.ZKSpringJPA.viewmodel.authentication;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.services.authentication.RolePermissionService;
import com.sample.ZKSpringJPA.services.authentication.RoleService;
import com.sample.ZKSpringJPA.utils.FeaturesScanner;
import com.sample.ZKSpringJPA.utils.Menu;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.*;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(view = "/view/authentication/roles-dashboard.zul",
        uuid = "roles-dashboard",
        menuName = "dashboard",
        menuOrder = "1.1",
        displayName = "Roles Dashboard",
        menuIcon = "unlock"
)
public class RolesVM {
    @WireVariable
    private RoleService roleService;

    @WireVariable
    RolePermissionService rolePermissionService;

    @Getter @Setter
    private ListModelList<RolePermission> permissions;

    @Getter @Setter
    private ListModelList<Role> roles;

    @Getter @Setter
    private Role role;

    @Getter @Setter
    private ListModelList<Feature> allFeatures;

    @Init
    public void init() throws ClassNotFoundException {
        roles = new ListModelList<>(roleService.findAll());
        role = new Role();
        loadPermission();
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
        if(role.getPermissions()!=null)
        for(RolePermission rolePermission: role.getPermissions()){
            String featureName = rolePermission.getFeature();
            RolePermission r = addRolePermission(featureName);
            permissions.remove(r);
        }
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

    private void loadPermission() throws ClassNotFoundException {
        Map<String, Feature> featureMap = FeaturesScanner.getFeatures();
        List<Feature> features = new ListModelList<>(featureMap.values());
        for(Feature feature: features){
            if(this.permissions==null){
                this.permissions = new ListModelList<>();
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setFeature(feature.uuid());
            rolePermission.setRole(role);
            permissions.add(rolePermission);
        }
    }

    @Command
    @NotifyChange({"role", "permissions"})
    public void removePermissions(@BindingParam("grid")final Grid grid){
        Rows rows = grid.getRows();
        List<Row> lstRow = rows.getChildren();
        List<RolePermission> roleTemplateList = new ArrayList<>();

        for(Row row: lstRow){
            RolePermission rolePermission = row.getValue();
            Checkbox checkbox = (Checkbox) row.getChildren().get(0);
            if(checkbox.isChecked())
                roleTemplateList.add(removeRolePermission(rolePermission.getFeature()));
        }

        for(RolePermission rolePermission: roleTemplateList){
            rolePermission.setRole(this.role);
            rolePermissionService.delete(rolePermission);
            this.role.remove(rolePermission);
            rolePermission.setId(0);
            permissions.add(rolePermission);
        }
    }

    @Command
    @NotifyChange({"role", "permissions"})
    public void addPermissions(@BindingParam("grid")final Grid grid){
        Rows rows = grid.getRows();
        List<Row> lstRow = rows.getChildren();
        List<RolePermission> roleTemplateList = new ArrayList<>();

        for(Row row: lstRow){
            RolePermission rolePermission = row.getValue();
            Checkbox checkbox = (Checkbox) row.getChildren().get(0);
            if(checkbox.isChecked())
                roleTemplateList.add(addRolePermission(rolePermission.getFeature()));
        }
        permissions.removeAll(roleTemplateList);

        for(RolePermission rolePermission: roleTemplateList){
            rolePermission.setRole(this.role);
            rolePermission = rolePermissionService.create(rolePermission);
            this.role.add(rolePermission);
        }
    }

    private RolePermission addRolePermission(final String featureName) {
        RolePermission tempPermission = null;
        for (RolePermission rolePermission: permissions){
            if(rolePermission.getFeature().equals(featureName)){
                tempPermission = rolePermission;
                return tempPermission;
            }
        }
        return null;
    }

    private RolePermission removeRolePermission(final String featureName) {
        RolePermission tempPermission = null;
        for (RolePermission rolePermission: this.role.getPermissions()){
            if(rolePermission.getFeature().equals(featureName)){
                tempPermission = rolePermission;
                return tempPermission;
            }
        }
        return null;
    }
}
