package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;

import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.entity.employment.Department;
import com.sample.ZKSpringJPA.entity.employment.Designation;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.services.employment.DepartmentService;
import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zsoup.helper.StringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/departmentlist.zul",
        uuid = "departmentlist",
        menuOrder = "2.4",
        displayName = "Department List",
        menuIcon = "building-o"
)
public class DepartmentListVM extends ListPagingVM {

    //region > Inject Service
    @WireVariable
    private DepartmentService departmentService;


    //endregion

    //region > Fields
    @Getter
    @Setter
    private ListModelList<Department> departments;

    @Getter @Setter
    private Department department;

    //endregion

    //region > Constructor
    @Init
    public void init(){
        departments = new ListModelList<>(departmentService.findPaging(0, getPageSize()));
        department = new Department();
        setTotalSize(departmentService.count());
    }
    //endregion

    //region > Command
    @Command
    @NotifyChange({"department"})
    public void create(){
        department = departmentService.create(department);
        departments.add(department);
    }

    @Command
    @NotifyChange({"department"})
    public void update(){
        department = departmentService.update(department);
    }

    @Command
    @NotifyChange({"department"})
    public void delete(){
        departmentService.delete(department);
        departments.remove(department);
        department = new Department();
    }

    @Command
    @NotifyChange({"department"})
    public void cancel(){
        department = new Department();
    }

    @Command
    @NotifyChange({"department"})
    public void select(@BindingParam("department") final Department department){
        this.department = department;
    }

    @Override
    public void research(int offset, int limit) {
        if(StringUtil.isBlank(getFilter())) {
            departments = new ListModelList<>(departmentService.findPaging(offset, limit));
            setTotalSize(departmentService.count());
        }
        else {
            departments = new ListModelList<>(departmentService.findPaging(offset, limit, getFilter().toLowerCase(), getFilterBy()));
            setTotalSize(departmentService.count(getFilter().toLowerCase(), getFilterBy()));
        }
        postNotifyChange(this,"departments");
        postNotifyChange(this,"totalSize");
    }

    //endregion
}
