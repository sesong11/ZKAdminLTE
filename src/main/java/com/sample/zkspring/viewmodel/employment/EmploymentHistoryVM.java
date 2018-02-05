package com.sample.zkspring.viewmodel.employment;

import com.sample.zkspring.entity.employment.*;
import com.sample.zkspring.services.employment.*;
import com.sample.zkspring.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.util.List;
import java.util.TreeSet;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmploymentHistoryVM {
    //region > Injection
    @WireVariable
    private EmployeeHistoryService employeeHistoryService;

    @WireVariable
    private BranchService branchService;

    @WireVariable
    private DepartmentService departmentService;

    @WireVariable
    private DesignationService designationService;

    @WireVariable
    private EmployeeService employeeService;

    @Wire("#modalDialog")
    private Window win;
    //endregion

    //region > Fields
    @Getter @Setter
    private EmploymentHistory employmentHistory;

    @Getter @Setter
    private List<HistoryType> historyTypes;

    @Getter @Setter
    private List<Branch> branches;

    @Getter @Setter
    private List<Department> departments;

    @Getter @Setter
    private List<Designation> designations;

    @Getter @Setter
    private List<Employee> supervisors;

    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();
    //endregion

    //region > Constructor

    @Init
    public void init(
            @ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("employee") final Employee employee,
            @ExecutionArgParam("employmentHistory") final EmploymentHistory employmentHistory) {
        Selectors.wireComponents(view, this, false);
        this.employmentHistory = employmentHistory==null?new EmploymentHistory(): employmentHistory;
        employmentHistory.setEmployee(employee);
        historyTypes = new ListModelList<>(HistoryType.values());
        branches = new ListModelList<>(branchService.findAll());
        departments = new ListModelList<>(departmentService.findAll());
        designations = new ListModelList<>(designationService.findAll());
        supervisors = new ListModelList<>(employeeService.findAll());
        for(Employee emp: supervisors){
            if(employee.equals(emp)){
                supervisors.remove(emp);
                break;
            }
        }
    }
    //endregion

    //region > Command
    @Command
    public void closeThis() {
        win.detach();
    }

    @Command
    public void save() {
        employmentHistory = employeeHistoryService.create(employmentHistory);
        Employee employee = employmentHistory.getEmployee();
        if(employee.getEmploymentHistories()==null){
            employee.setEmploymentHistories(new TreeSet<>());
        }
        employee.getEmploymentHistories().add(employmentHistory);
        win.detach();
        BindUtils.postGlobalCommand(null, null, "refreshHistoryList", null);
    }

    @Command
    public void update() {
        employmentHistory = employeeHistoryService.update(employmentHistory);
        win.detach();
        BindUtils.postGlobalCommand(null, null, "refreshHistoryList", null);
    }
    //endregion
}
