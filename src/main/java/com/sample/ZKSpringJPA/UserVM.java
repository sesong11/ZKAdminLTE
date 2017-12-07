package com.sample.ZKSpringJPA;

import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.services.UserService;

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
}
