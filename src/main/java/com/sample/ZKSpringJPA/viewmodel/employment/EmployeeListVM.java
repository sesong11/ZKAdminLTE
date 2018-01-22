package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.*;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.services.employment.DepartmentService;
import com.sample.ZKSpringJPA.services.employment.DesignationService;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import lombok.Getter;
import lombok.Setter;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zsoup.helper.StringUtil;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)

@Feature(
        view = "/view/employment/employeelist.zul",
        uuid = "employeelist",
        menuOrder = "2.1",
        displayName = "Employee List",
        menuIcon = "users"
)
public class EmployeeListVM extends ListPagingVM {

    //region > inject
    @WireVariable
    private EmployeeService employeeService;

    @WireVariable
    private BranchService branchService;

    @WireVariable
    private DepartmentService departmentService;

    @WireVariable
    private DesignationService designationService;


    //endregion

    //region > Fields
    @Getter @Setter
    private List<Employee> employees;

    @Getter
    private List<Gender> genders;

    @Getter @Setter
    private List<Branch> branches;

    @Getter @Setter
    private Branch branch;

    @Getter @Setter
    private List<Department> departments;

    @Getter @Setter
    private Department department;

    @Getter @Setter
    private List<Designation> designations;

    @Getter @Setter
    private Designation designation;

    @Getter @Setter
    private Gender gender;

    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();
    //endregion

    //region > Constructor
    @Init
    public void init(){
        research(0, getPageSize());
        setTotalSize(employeeService.count());
        branches = new ListModelList<>(branchService.findAll());
        departments = new ListModelList<>(departmentService.findAll());
        designations = new ListModelList<>(designationService.findAll());
        this.genders = new ListModelList<>(Gender.values());
    }

    //endregion

    //region > Command
    @Command
    public void edit(@BindingParam("employee") final Employee employee){
        Executions.getCurrent().sendRedirect("?m=employee-editor&id="+employee.getId());
    }
    //endregion

    //region > Programmatic
    public void research(final int offset, final int limit){
        HashMap<String, Object> filters = new HashMap<>();
        if(gender!=null) filters.put("gender", gender);
        if(branch!=null) filters.put("branch", branch);
        if(department!=null) filters.put("department", department);
        if(designation!=null) filters.put("designation", designation);

        if(StringUtil.isBlank(getFilter()) && filters.size() == 0) {
            employees = new ListModelList<>(employeeService.findPaging(offset, limit));
            setTotalSize(employeeService.count());
        }
        else {
            employees = new ListModelList<>(employeeService.findPaging(offset, limit, getFilter(), getFilterBy(), filters));
            setTotalSize(employeeService.count(getFilter(), getFilterBy(), filters));
        }

        postNotifyChange(this,"employees");
        postNotifyChange(this,"totalSize");
    }
    @Command
    @NotifyChange({"branch", "gender", "department", "designation", "filter"})
    public void clearFilter(){
        gender = null;
        branch = null;
        department = null;
        designation = null;
        setFilter(null);
        research(0, getPageSize());
    }
    //endregion
}
