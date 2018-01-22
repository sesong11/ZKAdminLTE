package com.sample.ZKSpringJPA;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.authentication.UserService;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginVM {
    @WireVariable UserService userService;
    @WireVariable
    UserCredentialService userCredentialService;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String message;

    @Getter
    private boolean authenticated;
    @NotifyChange("authenticated")
    @Init
    public void Init() throws ServletException {

        authenticated = Sessions.getCurrent().getAttribute("userCredential") != null;
        if(authenticated){
            Executions.sendRedirect("/");
        }
    }
    @NotifyChange("message")
    @Command
    public void login() throws ServletException, IOException {
        RequestCache requestCache = new HttpSessionRequestCache();
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
        try {
            request.login(this.username, this.password);
            if(isAdmin()){
                Executions.sendRedirect(response.encodeRedirectURL("/"));
                Executions.getCurrent().setVoided(true);
            }
            Employee employee = userCredentialService.getCurrentEmployee();
            if(employee!=null) {
                SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
                String password = formatter.format(employee.getDob());

                if (BCrypt.checkpw(password, employee.getUser().getPassword())) {
                    Window window = (Window) Executions.createComponents(
                            "/view/authentication/change-password.zul", null, null);
                    window.doModal();
                    return;
                }
            }
            Executions.sendRedirect("/");

        } catch (ServletException e) {
            if ("Bad credentials".equals(e.getMessage())) {
                message = "Wrong Username or Password. Please Try again.";
            } else if("No entity found for query".equals(e.getMessage())) {
                message = "No User exist. Please Try again.";
            }
            e.printStackTrace();
        }
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
