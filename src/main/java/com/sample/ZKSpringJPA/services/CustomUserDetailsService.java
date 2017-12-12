package com.sample.ZKSpringJPA.services;

import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sample.ZKSpringJPA.entity.authentication.User;
import org.zkoss.zk.ui.Executions;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private UserService userService;
 
    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(s);
 
        //check if this user with this username exist, if not, throw an exception
        // and stop the login process
        if (user == null) {
            throw new UsernameNotFoundException("User details not found with this username: " + s);
        }
 
        String username = user.getUsername();
        String password = user.getPassword();
        List<Role> roles = new ArrayList<>(user.getRoles());

        List<SimpleGrantedAuthority> authList = getAuthorities(roles);

        org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(username, password, authList);
 
        return u;
    }
 
    private List<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        for(Role role: roles){
            authList.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authList;
    }
}
