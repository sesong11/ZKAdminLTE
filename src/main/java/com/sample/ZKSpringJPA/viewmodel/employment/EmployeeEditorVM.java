package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.employment.EmploymentHistory;
import com.sample.ZKSpringJPA.entity.employment.Gender;
import com.sample.ZKSpringJPA.services.authentication.UserService;
import com.sample.ZKSpringJPA.services.employment.EmployeeHistoryService;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import com.sample.ZKSpringJPA.utils.StandardDateTime;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/employee-editor.zul",
        uuid = "employee-editor",
        menuOrder = "2.0.2"
)
public class EmployeeEditorVM {

    //region > Inject
    @WireVariable
    private EmployeeService employeeService;

    @WireVariable
    private EmployeeHistoryService employeeHistoryService;

    @WireVariable
    private UserService userService;

    //endregion

    //region > Fields
    @Getter @Setter
    private List<Gender> genders;

    @Getter @Setter
    private int selectedGender;

    @Getter @Setter
    private Employee employee;

    @Getter
    private final String standardDateFormat = StandardDateTime.getStandardDateFormat();

    //endregion

    //region > Constructor
    @Init
    public void init(){
        genders = new ListModelList<>(Gender.values());
        employee = new Employee();
        String id = Executions.getCurrent().getParameter("id");
        if(id!=null) {
            employee = employeeService.find(Long.parseLong(id));
        }
    }

    //endregion

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

    @Command
    public void addHistory(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employee", employee);
        map.put("employmentHistory", new EmploymentHistory());
        Window window = (Window) Executions.createComponents(
                "/view/employment/employee-history.zul", null, map);
        window.doModal();
    }

    @Command
    public void selectHistory(@BindingParam("item") final EmploymentHistory employmentHistory){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employee", employee);
        map.put("employmentHistory", employmentHistory);
        Window window = (Window) Executions.createComponents(
                "/view/employment/employee-history.zul", null, map);
        window.doModal();
    }

    @Command
    @NotifyChange({"employee"})
    public void deleteHistory(@BindingParam("item") final EmploymentHistory employmentHistory){
        employeeHistoryService.delete(employmentHistory);
        employee.getEmploymentHistories().remove(employmentHistory);
    }

    @GlobalCommand
    @Command
    @NotifyChange("employee")
    public void refreshHistoryList(){}

    @Command
    @NotifyChange("employee")
    public void createUser() {
        User user = new User();
        user.setEnabled(true);
        user.setUsername(employee.getLastName().toLowerCase()+"_"+employee.getFirstName().toLowerCase());
        user = userService.addUser(user);
        employee.setUser(user);
        update();
    }
    //endregion

}
