package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.DayOff;
import com.sample.ZKSpringJPA.services.employment.DayOffService;
import com.sample.ZKSpringJPA.utils.StandardFormat;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/dayofflist.zul",
        uuid = "dayofflist",
        menuOrder = "2.5",
        displayName = "Holidays",
        menuIcon = "id-card"
)
public class DayoffListVM {

    //region > Inject Service
    @WireVariable
    private DayOffService dayOffService;


    //endregion

    //region > Fields
    @Getter @Setter
    private ListModelList<DayOff> dayOffs;

    @Getter @Setter
    private DayOff dayOff;

    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();

    //endregion

    //region > Constructor
    @Init
    public void init(){
        dayOffs = new ListModelList<>(dayOffService.findAll());
        dayOff = new DayOff();
    }
    //endregion

    //region > Command
    @Command
    @NotifyChange({"dayOff"})
    public void create(){
        dayOff = dayOffService.create(dayOff);
        dayOffs.add(dayOff);
    }

    @Command
    @NotifyChange({"dayOff"})
    public void update(){
        dayOff = dayOffService.update(dayOff);
    }

    @Command
    @NotifyChange({"dayOff"})
    public void delete(){
        dayOffService.delete(dayOff);
        dayOffs.remove(dayOff);
        dayOff = new DayOff();
    }

    @Command
    @NotifyChange({"dayOff"})
    public void cancel(){
        dayOff = new DayOff();
    }

    @Command
    @NotifyChange({"dayOff"})
    public void select(@BindingParam("dayOff") final DayOff dayOff){
        this.dayOff = dayOff;
    }
    //endregion
}
