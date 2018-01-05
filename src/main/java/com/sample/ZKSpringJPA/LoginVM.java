package com.sample.ZKSpringJPA;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sample.ZKSpringJPA.services.authentication.UserService;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginVM {
    @WireVariable UserService userService;
    @WireVariable
    UserCredentialService userCredentialService;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

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

    @Command
    public void login() throws ServletException, IOException {
        RequestCache requestCache = new HttpSessionRequestCache();
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
        try {
            request.login(this.username, this.password);
 //           SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//            if (savedRequest != null) {
                Executions.sendRedirect(response.encodeRedirectURL("/"));
//            } else {
//                System.out.println("fail");
//            }
            Executions.getCurrent().setVoided(true);
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }
}
