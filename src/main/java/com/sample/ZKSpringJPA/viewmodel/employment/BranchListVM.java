package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.services.employment.BranchService;

import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zsoup.helper.StringUtil;
import org.zkoss.zul.ListModelList;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/branch-list.zul",
        uuid = "branch-list",
        menuOrder = "2.2",
        displayName = "Branch List",
        menuIcon = "building"
)
public class BranchListVM extends ListPagingVM {

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
        branches = new ListModelList<>(branchService.findPaging(0, getPageSize()));
        branch = new Branch();
        setTotalSize(branchService.count());
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

    @Override
    public void research(int offset, int limit) {
        if(StringUtil.isBlank(getFilter())) {
            branches = new ListModelList<>(branchService.findPaging(offset, limit));
            setTotalSize(branchService.count());
        }
        else {
            branches = new ListModelList<>(branchService.findPaging(offset, limit, getFilter().toLowerCase(), getFilterBy()));
            setTotalSize(branchService.count(getFilter().toLowerCase(), getFilterBy()));
        }
        postNotifyChange(this,"totalSize");
        postNotifyChange(this,"branches");
    }
    //endregion
}
