package com.sample.ZKSpringJPA.utils;

import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.authentication.UserService;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

@Service("userCredentialService")
public class UserCredentialService {
    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    public User getUserCredential(){
        if(isAdmin()) return null;
        Session sess = Sessions.getCurrent();
        User cre = (User) sess.getAttribute("userCredential");
        if(cre==null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            cre = userService.getUserByUsername(currentPrincipalName);
            sess.setAttribute("userCredential",cre);
        }
        return cre;
    }

    public Employee getCurrentEmployee(){
        if(isAdmin()) return null;
        Session sess = Sessions.getCurrent();
        Employee employee = (Employee) sess.getAttribute("employeeCredential");
        if(employee == null){
            employee = employeeService.findByUser(getUserCredential());
        }
        return employee;
    }
    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority authority:authentication.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}