package com.sample.ZKSpringJPA.viewmodel.dashboard;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.dashboard.Application;
import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.services.dashboard.ApplicationService;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import java.util.ArrayList;
import java.util.List;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/dashboard/dashboard-setting.zul",
        uuid = "dashboard-setting",
        menuOrder = "1.3",
        displayName = "Setting Application",
        menuIcon = "cog"
)


public class DashboardVM {
    @Getter @Setter
    private Application application;
    @Getter @Setter
    private List<Application> applicationList;
    @WireVariable
    private ApplicationService applicationService;

    @Init
    public void init(){
        application = new Application();
        applicationList = new ArrayList<Application>();
    }
    @NotifyChange({"application", "applicationList"})
    @Command
    public void onSave() {
        applicationService.create(application);
        application = new Application();
        applicationList = applicationService.findAll();

    }

    @Command
    public void onUpload() {

    }
}
