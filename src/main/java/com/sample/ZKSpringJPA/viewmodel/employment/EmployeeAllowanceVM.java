package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.entity.employment.*;
import com.sample.ZKSpringJPA.services.employment.*;
import com.sample.ZKSpringJPA.utils.StandardFormat;
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

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeeAllowanceVM {

    //region > Injection
    @WireVariable
    private EmployeeAllowanceService employeeAllowanceService;

    @WireVariable
    private EmployeeService employeeService;

    @WireVariable
    private AllowanceService allowanceService;

    @Wire("#modalDialog")
    private Window win;
    //endregion

    //region > Fields
    @Getter
    @Setter
    private EmployeeAllowance employeeAllowance;

    @Getter @Setter
    private List<Allowance> allowances;

    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();
    //endregion

    //region > Constructor

    @Init
    public void init(
            @ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("employee") final Employee employee,
            @ExecutionArgParam("employeeAllowance") final EmployeeAllowance employeeAllowance) {
        Selectors.wireComponents(view, this, false);
        this.employeeAllowance = employeeAllowance==null?new EmployeeAllowance(): employeeAllowance;
        employeeAllowance.setEmployee(employee);
        allowances = new ListModelList<>(allowanceService.findAll());
    }
    //endregion

    //region > Command
    @Command
    public void closeThis() {
        win.detach();
    }

    @Command
    public void save() {
        employeeAllowance = employeeAllowanceService.create(employeeAllowance);
        Employee employee = employeeAllowance.getEmployee();
        employee.addAllowance(employeeAllowance);
        win.detach();
        BindUtils.postGlobalCommand(null, null, "refreshHistoryList", null);
    }

    @Command
    public void update() {
        employeeAllowance = employeeAllowanceService.update(employeeAllowance);
        win.detach();
        BindUtils.postGlobalCommand(null, null, "refreshHistoryList", null);
    }
    //endregion
}