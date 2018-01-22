package com.sample.ZKSpringJPA.viewmodel.component;

import com.sample.ZKSpringJPA.entity.employment.*;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.services.employment.DepartmentService;
import com.sample.ZKSpringJPA.services.employment.DesignationService;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeeSelectorVM extends ListPagingVM {

    //region > Inject Service
    @Wire("#modalDialog")
    private Window win;

    @WireVariable
    private BranchService branchService;

    @WireVariable
    private DepartmentService departmentService;

    @WireVariable
    private DesignationService designationService;
    //endregion

    //region > Fields

    @Getter @Setter
    private String receiver;

    @Getter @Setter
    private Employee employee;
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
    public void init(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("employees") final ListModelList<Employee> employees,
                     @ExecutionArgParam("totalSize") final int totalSize,
                     @ExecutionArgParam("receiver") final String receiver){
        Selectors.wireComponents(view, this, false);
        this.setTotalSize(totalSize);
        this.employees = employees;
        this.receiver = receiver;
        branches = new ListModelList<>(branchService.findAll());
        departments = new ListModelList<>(departmentService.findAll());
        designations = new ListModelList<>(designationService.findAll());
        this.genders = new ListModelList<>(Gender.values());
    }
    //endregion

    //region > Command
    @Command
    public void select(@BindingParam("item")final Employee employee){
        win.detach();
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employee", employee);
        BindUtils.postGlobalCommand(null, null, receiver, map);
    }
    @Command
    public void closeThis() {
        win.detach();
    }

    @Override
    public void research(int offset, int limit) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("windows", this);
        map.put("employees", employees);
        map.put("pageSize", getPageSize());
        map.put("activePage", getActivePage());
        map.put("filter", getFilter());
        map.put("filterBy", getFilterBy());
        HashMap<String, Object> filters = new HashMap<>();
        if(getGender() != null) filters.put("gender", getGender());
        if(getBranch() != null) filters.put("branch", getBranch());
        if(getDepartment() != null) filters.put("department", getDepartment());
        if(getDesignation() != null) filters.put("designation", getDesignation());
        map.put("filters", filters);
        BindUtils.postGlobalCommand(null, null, receiver+"Filter", map);
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
