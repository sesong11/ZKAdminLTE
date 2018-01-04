package com.sample.ZKSpringJPA.viewmodel.dashboard;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.dashboard.Application;
import com.sample.ZKSpringJPA.entity.employment.Allowance;
import com.sample.ZKSpringJPA.services.dashboard.ApplicationService;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/dashboard/dashboard-setting.zul",
        uuid = "dashboard-setting",
        menuOrder = "1.3",
        displayName = "Setting Dashboard",
        menuIcon = "cog"
)


public class DashboardVM {
    @Getter @Setter
    private Application application;
    @Getter @Setter
    private List<Application> applicationList;
    @Getter @Setter
    private List<Application> applications;
    @Getter @Setter
    private List<Application> tabList;
    @WireVariable
    private ApplicationService applicationService;
    @Getter @Setter
    private Media media;

    @Init
    public void init(){
        application = new Application();
        tabList = new ArrayList<Application>();
        applicationList = applicationService.queryEnabled();
        applications = applicationService.findAll();
        for(Application application : applicationList) {
            if(application.getParentId() == 0) {
                tabList.add(application);
            }
        }

    }
    @NotifyChange({"application", "applications"})
    @Command
    public void onSave() throws IOException {
        java.util.Date date= new java.util.Date();
        application.setCreatedAt(new Timestamp(date.getTime()));
        if(media != null) {
            String path = saveFile(media);
            application.setImageSrc(path);
        }
        if(null == application.getId()) {
            applicationService.create(application);
        } else {
            applicationService.update(application);
        }
        applications = applicationService.findAll();
        application = new Application();
    }

    @NotifyChange("media")
    @Command
    public void upload(BindContext ctx) {
        UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
        media = event.getMedia();
    }

    @NotifyChange("application")
    @Command
    public void clear(){
        application = new Application();
    }

    private String saveFile(Media media) throws IOException {
        byte[] bytes = media.getByteData();
        String name, type, path = "img/dashboard/";
        name = application.getTitle()+"." + media.getFormat();
        path += name;

        FileOutputStream out = new FileOutputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath(path));
        out.write(bytes);

        return path;

    }
}
