package com.sample.ZKSpringJPA;

import java.util.List;

import com.sample.ZKSpringJPA.entity.User;
import com.sample.ZKSpringJPA.services.UserService;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserVM {

    @WireVariable UserService userService;

    @Getter @Setter
    private List<User> users;

    @Init
    public void UserVM() {
        users = userService.allUser();
    }
}
