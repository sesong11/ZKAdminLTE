package com.sample.ZKSpringJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sample.ZKSpringJPA.entity.authentication.User;

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
        String role = "ROLE_USER";
 
        List<SimpleGrantedAuthority> authList = getAuthorities(role);
 
        //get the encoded password
        //String encodedPassword = passwordEncoder.encode(password);
 
        org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(username, password, authList);
 
        return u;
    }
 
    private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
 
        //you can also add different roles here
        //for example, the user is also an admin of the site, then you can add ROLE_ADMIN
        //so that he can view pages that are ROLE_ADMIN specific
        if (role != null && role.trim().length() > 0) {
            if (role.equals("admin")) {
                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
 
        return authList;
    }
}
