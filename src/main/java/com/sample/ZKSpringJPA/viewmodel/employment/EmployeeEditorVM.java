package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.employment.Gender;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
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

import java.util.ArrayList;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/employee-editor.zul",
        uuid = "employee-editor",
        menuOrder = "2.0.2"
)
public class EmployeeEditorVM {

    @WireVariable
    private EmployeeService employeeService;

    @Getter @Setter
    private List<Gender> genders;

    @Getter @Setter
    private int selectedGender;

    @Getter @Setter
    private Employee employee;

    @Init
    public void init(){
        genders = new ListModelList<>(Gender.values());
        employee = new Employee();
        String id = Executions.getCurrent().getParameter("id");
        if(id!=null) {
            employee = employeeService.find(Long.parseLong(id));
        }
    }

    //region > Command

    @Command
    @NotifyChange({"employee"})
    public void save(){
        employee = employeeService.create(employee);
    }

    @Command
    @NotifyChange({"employee"})
    public void update(){
        employee = employeeService.update(employee);
    }
    //endregion

}
