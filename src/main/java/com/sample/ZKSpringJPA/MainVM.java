package com.sample.ZKSpringJPA;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.employment.Employee;

import com.sample.ZKSpringJPA.services.authentication.UserService;
import com.sample.ZKSpringJPA.utils.FeaturesScanner;
import com.sample.ZKSpringJPA.utils.Menu;
import com.sample.ZKSpringJPA.utils.UserCredentialService;

import com.sun.org.apache.xalan.internal.Version;
import lombok.Getter;
import lombok.Setter;

import org.apache.tools.ant.Project;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainVM {

	@WireVariable
	private UserCredentialService userCredentialService;

	@WireVariable
	private UserService userService;

	@Getter @Setter
	private Menu menu;

	@Getter
	private User currentUser;

	@Getter
	private Employee currentEmployee;

	private String urlParam = "/application/dashboard/dashboard-v1.zul";

	public String getUrlParam() {return urlParam; }
	@Init
	public void init() throws ClassNotFoundException {
		currentEmployee = userCredentialService.getCurrentEmployee();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user = isAdmin()? null: userService.getUserByUsername(currentPrincipalName);

		if(isAdmin() && FeaturesScanner.getFeatures().size()==0){
			FeaturesScanner.scanFeatures();
		}
		String param = Executions.getCurrent().getParameter("m");
		menu = new Menu();
		Feature feature = menu.scanMenu(user);
		if(param==null && isAuthenticated("default")){
			urlParam = "/view/request/dashboard.zul";
		}else {
			if (feature == null) {
				urlParam = "/view/error/404.zul";
			} else if (isAuthenticated(feature.uuid())) {
				urlParam = feature.view();
			} else {
				urlParam = "/view/error/401.zul";
			}
		}
	}

	private boolean isAuthenticated(final String feature) throws ClassNotFoundException{
		if(isAdmin()) return true;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		try{
			User user = userService.getUserByUsername(currentPrincipalName);
			for(Role role:user.getRoles()){
				for(RolePermission rolePermission: role.getPermissions()){
					if(rolePermission.getFeature().equals(feature)){
						return true;
					}
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
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

	@Command
	public void logout() throws ServletException {
		FeaturesScanner.getFeatures().clear();
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		Executions.sendRedirect(response.encodeRedirectURL("/logout"));
		Executions.getCurrent().setVoided(true);
	}

	@Command
    public void changePassword() {
        Window window = (Window)Executions.createComponents(
                "/view/authentication/change-password.zul", null, null);
        window.doModal();
    }

    @Command
    public void cancel() {

    }
}