package com.sample.ZKSpringJPA;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.Log;
import com.sample.ZKSpringJPA.services.MyService;

import java.lang.reflect.Field;
import java.util.*;

import com.sample.ZKSpringJPA.utils.Menu;
import com.sample.ZKSpringJPA.viewmodel.authentication.RolesVM;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
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
		scanMenu();
		System.out.println("menu: "+menu.getSubMenu().size());
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

	private void scanMenu() throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider scanner =
				new ClassPathScanningCandidateComponentProvider(false);

		scanner.addIncludeFilter(new AnnotationTypeFilter(Feature.class));

		Map<String, Feature> menus = new TreeMap<>();
		for (BeanDefinition bd : scanner.findCandidateComponents("com.sample.ZKSpringJPA.viewmodel")){
			String className = bd.getBeanClassName();
			System.out.println("className: "+className);
			Feature[] features = Class.forName(className).getAnnotationsByType(Feature.class);
			for (Feature feature: features) {
				menus.put(feature.menuOrder(), feature);
				String param = Executions.getCurrent().getParameter("m");
				if(param!=null && param.toLowerCase().equals(feature.uuid())){
					urlParam = feature.view();
				}
			}
		}
		List<Feature> list = new ArrayList<>(menus.values());
		for(Feature feature: list){
			menu.addMenu(feature, feature.menuOrder());
		}
	}
}
