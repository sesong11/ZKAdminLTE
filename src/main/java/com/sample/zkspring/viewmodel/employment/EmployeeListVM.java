package com.sample.zkspring.viewmodel.employment;

import com.sample.zkspring.anotation.Feature;
import com.sample.zkspring.entity.employment.Employee;
import com.sample.zkspring.services.employment.EmployeeService;
import com.sample.zkspring.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)

@Feature(
        view = "/view/employment/employeelist.zul",
        uuid = "employeelist",
        menuOrder = "2.1",
        displayName = "Employee List",
        menuIcon = "users"
)
public class EmployeeListVM {

    //region > inject
    @WireVariable
    private EmployeeService employeeService;

    //endregion

    //region > Fields
    @Getter @Setter
    private List<Employee> employees;

    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();
    //endregion

    //region > Constructor
    @Init
    public void init(){
        employees = employeeService.findAll();
    }

    //endregion

    //region > Command
    @Command
    public void edit(@BindingParam("employee") final Employee employee){
        Executions.getCurrent().sendRedirect("?m=employee-editor&id="+employee.getId());
    }
    //endregion
}
