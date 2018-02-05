package com.sample.zkspring.viewmodel.employment;

import com.sample.zkspring.anotation.Feature;
import com.sample.zkspring.entity.employment.Designation;
import com.sample.zkspring.services.employment.DesignationService;

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
        view = "/view/employment/designationlist.zul",
        uuid = "designationlist",
        menuOrder = "2.3",
        displayName = "Designation List",
        menuIcon = "id-card"
)
public class DesignationListVM {

    //region > Inject Service
    @WireVariable
    private DesignationService designationService;


    //endregion

    //region > Fields
    @Getter @Setter
    private ListModelList<Designation> designations;

    @Getter @Setter
    private Designation designation;

    //endregion

    //region > Constructor
    @Init
    public void init(){
        designations = new ListModelList<>(designationService.findAll());
        designation = new Designation();
    }
    //endregion

    //region > Command
    @Command
    @NotifyChange({"designation"})
    public void create(){
        designation = designationService.create(designation);
        designations.add(designation);
    }

    @Command
    @NotifyChange({"designation"})
    public void update(){
        designation = designationService.update(designation);
    }

    @Command
    @NotifyChange({"designation"})
    public void delete(){
        designationService.delete(designation);
        designations.remove(designation);
        designation = new Designation();
    }

    @Command
    @NotifyChange({"designation"})
    public void cancel(){
        designation = new Designation();
    }

    @Command
    @NotifyChange({"designation"})
    public void select(@BindingParam("designation") final Designation designation){
        this.designation = designation;
    }
    //endregion
}
