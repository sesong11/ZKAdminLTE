package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.request.FormType;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zul.ListModelList;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/dashboard.zul",
        uuid = "1-request-dashboard",
        menuOrder = "3.1",
        displayName = "Dashboard",
        menuIcon = "bar-chart"
)
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestMainVM {
    //region > Inject Services

    //endregion

    //region > Fields
    @Getter
    public final List<FormType> requestForms = new ListModelList<>(FormType.values());
    //endregion

    //region > Constructor
    @Init
    public void init(){

    }
    //endregion

    //region > Commands
    @Command
    public void newRequest(@BindingParam("form") final FormType form){
        System.out.println("Form Request: "+form.getName());
    }
    //endregion
}
