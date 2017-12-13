package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.services.employment.BranchService;

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
        view = "/view/employment/branchlist.zul",
        uuid = "branchlist",
        menuOrder = "2.2",
        displayName = "Branch List",
        menuIcon = "building"
)
public class BranchListVM {

    //region > Inject Service
    @WireVariable
    private BranchService branchService;


    //endregion

    //region > Fields
    @Getter @Setter
    private ListModelList<Branch> branches;

    @Getter @Setter
    private Branch branch;

    //endregion

    //region > Constructor
    @Init
    public void init(){
        branches = new ListModelList<>(branchService.findAll());
        branch = new Branch();
    }
    //endregion

    //region > Command
    @Command
    @NotifyChange({"branch"})
    public void create(){
        branch = branchService.create(branch);
        branches.add(branch);
    }

    @Command
    @NotifyChange({"branch"})
    public void update(){
        branch = branchService.update(branch);
    }

    @Command
    @NotifyChange({"branch"})
    public void delete(){
        branchService.delete(branch);
        branches.remove(branch);
        branch = new Branch();
    }

    @Command
    @NotifyChange({"branch"})
    public void cancel(){
        branch = new Branch();
    }

    @Command
    @NotifyChange({"branch"})
    public void select(@BindingParam("branch") final Branch branch){
        this.branch = branch;
    }
    //endregion
}
