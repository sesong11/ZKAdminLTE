package com.sample.ZKSpringJPA.viewmodel.request.leaveform;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.EmployeeAllowance;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import java.util.HashMap;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/forms/leave-form.zul",
        uuid = "leave-form",
        menuOrder = "3.0.1",
        displayName = "Leave Request",
        menuIcon = "tag"
)
public class LeaveFormVM {
    //region > Inject Services
    @WireVariable
    private EmployeeService employeeService;

    //endregion

    //region > Component
    @Command
    public void selectRequestFor(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findAll());
        map.put("employeeAllowance", new EmployeeAllowance());
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }
    //endregion
}
