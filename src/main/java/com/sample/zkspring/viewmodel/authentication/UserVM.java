package com.sample.zkspring.viewmodel.authentication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sample.zkspring.anotation.Feature;
import com.sample.zkspring.entity.authentication.Role;
import com.sample.zkspring.entity.authentication.User;
import com.sample.zkspring.services.authentication.UserService;
import com.sample.zkspring.services.authentication.RoleService;

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
@Feature(view = "/view/authentication/users-dashboard.zul",
        uuid = "users-dashboard",
        menuName = "dashboard",
        menuOrder = "1.2",
        displayName = "Users Dashboard",
        menuIcon = "user"
)
public class UserVM {

    //region > Inject Service
    @WireVariable
    private UserService userService;
    @WireVariable
    private RoleService roleService;
    //endregion

    //region > Fields
    @Getter @Setter
    private ListModelList<User> users;

    @Getter @Setter
    private User currentUser;

    @Getter
    private List<Role> roles;

    @Getter @Setter
    private Role role;
    //endregion

    //region > Constructor
    @Init
    public void UserVM() {
        users = new ListModelList<>(userService.allUser());
        roles = roleService.findAll();
        if(currentUser == null){
            currentUser = new User();
        }
    }
    //endregion

    //region > Command

    @Command
    @NotifyChange({"currentUser"})
    public void createUser(
            @BindingParam("username") final String username,
            @BindingParam("password") final String password,
            @BindingParam("enabled") final boolean enabled
    ){
        currentUser.setUsername(username);
        currentUser.setPassword(password);
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
            @BindingParam("enabled") final boolean enabled
    ){
        currentUser.setUsername(username);
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
        Set<Role> r = userService.queryRoles(currentUser);
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
    public void addRole(){
        if(currentUser.getRoles()==null){
            currentUser.setRoles(new HashSet<>());
        }
        currentUser.getRoles().add(role);
        userService.updateUser(currentUser);
    }
    //endregion
}
