package com.sample.ZKSpringJPA;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.Log;
import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.services.MyService;

import java.lang.reflect.Field;
import java.util.*;

import com.sample.ZKSpringJPA.services.UserService;
import com.sample.ZKSpringJPA.utils.Menu;
import com.sample.ZKSpringJPA.viewmodel.authentication.RolesVM;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zsoup.helper.HttpConnection;
import org.zkoss.zsoup.helper.StringUtil;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MyViewModel {

	@WireVariable
	private MyService myService;

	@WireVariable
	private UserService userService;

	@Getter @Setter
	private Menu menu;

	private ListModelList<Log> logListModel;
	private String message;

	private String urlParam = "/application/dashboard/dashboard-v1.zul";

	public String getUrlParam() {return urlParam; }
	@Init
	public void init() throws ClassNotFoundException {
		List<Log> logList = myService.getLogs();
		logListModel = new ListModelList<Log>(logList);
		String param = Executions.getCurrent().getParameter("p");

		if(param!=null){
			urlParam = "/application/"+param+".zul";
		}

		menu = new Menu();
		Feature feature = menu.scanMenu();
		if(feature == null){
			urlParam = "/view/error/404.zul";
		}
		else if(isAuthenticated(feature.uuid())){
			urlParam = feature.view();
		}else{
			urlParam = "/view/error/401.zul";
		}
	}

	private boolean isAuthenticated(final String feature){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		for(GrantedAuthority authority:authentication.getAuthorities()){
			if(authority.getAuthority().equals("ROLE_ADMIN")) return true;
		}
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

	public ListModel<Log> getLogListModel() {
		return logListModel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Command
	public void addLog() {
		if(Strings.isBlank(message)) {
			return;
		}
		Log log = new Log(message);
		log = myService.addLog(log);
		logListModel.add(log);
	}

	@Command
	public void logout() throws ServletException {
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		Executions.sendRedirect(response.encodeRedirectURL("/logout"));
		Executions.getCurrent().setVoided(true);
	}

	@Command
	public void deleteLog(@BindingParam("log") Log log) {
		myService.deleteLog(log);
		logListModel.remove(log);
	}

}
