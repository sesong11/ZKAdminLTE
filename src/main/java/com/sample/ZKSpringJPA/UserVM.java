package com.sample.ZKSpringJPA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.services.UserService;
import com.sample.ZKSpringJPA.services.authentication.RoleService;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.zul.ListModelList;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserVM {

    @WireVariable UserService userService;
    @WireVariable RoleService roleService;

    @Getter @Setter
    private ListModelList<User> users;

    @Getter @Setter
    private User currentUser;

    @Init
    public void UserVM() {
        users = new ListModelList<>(userService.allUser());
        if(currentUser == null){
            currentUser = new User();
        }
    }

    @Command
    @NotifyChange({"currentUser"})
    public void createUser(
            @BindingParam("username") final String username,
            @BindingParam("password") final String password,
            @BindingParam("email") final String email,
            @BindingParam("enabled") final boolean enabled
    ){
        currentUser.setUsername(username);
        currentUser.setPassword(password);
        currentUser.setEmail(email);
        currentUser.setEnabled(enabled);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(currentUser.getPassword());
        currentUser.setPassword(encodedPassword);
        currentUser = userService.addUser(currentUser);
        users.add(currentUser);
    }

    @Command
    public void updateUser(
            @BindingParam("username") final String username,
            @BindingParam("email") final String email,
            @BindingParam("enabled") final boolean enabled
    ){
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setEnabled(enabled);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(currentUser.getPassword());
        currentUser.setPassword(encodedPassword);
        currentUser = userService.updateUser(currentUser);
    }

    @Command
    @NotifyChange({"currentUser"})
    public void selectUser(@BindingParam("user") final User user) {
        this.currentUser = user;
        List<Role> r = userService.queryRoles(currentUser);
        currentUser.setRoles(r);
        System.out.println(r.size());
    }

    @Command
    @NotifyChange({"currentUser"})
    public void deleteUser(){
        userService.deleteUser(currentUser);
        users.remove(currentUser);
        currentUser = new User();
    }

    @Command
    @NotifyChange({"currentUser"})
    public void cancel(){
        currentUser = new User();
    }

    @Command
    @NotifyChange({"currentUser"})
    public void addRole(@BindingParam("roleName") final String roleName){
        Role role = new Role();
        role.setName(roleName);
        role = roleService.create(role);
        if(currentUser.getRoles()==null){
            currentUser.setRoles(new ArrayList<>());
        }
        currentUser.getRoles().add(role);
        userService.updateUser(currentUser);
    }
}
