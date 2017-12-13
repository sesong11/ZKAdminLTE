package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)

@Feature(
        view = "/view/employment/employeelist.zul",
        uuid = "employeelist",
        menuOrder = "2",
        displayName = "Employee List",
        menuIcon = "users"
)
public class EmployeeListVM {

    @WireVariable
    private EmployeeService employeeService;

    @Getter @Setter
    private List<Employee> employees;

    @Init
    public void init(){
        employees = employeeService.findAll();
    }
}
