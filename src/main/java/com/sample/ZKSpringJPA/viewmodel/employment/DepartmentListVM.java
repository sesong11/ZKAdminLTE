package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;

import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.entity.employment.Department;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.services.employment.DepartmentService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
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
public class DepartmentListVM {

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
        departments = new ListModelList<>(departmentService.findAll());
        department = new Department();
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

    //endregion
}
