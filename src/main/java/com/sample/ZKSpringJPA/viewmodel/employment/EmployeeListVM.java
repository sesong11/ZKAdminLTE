package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import com.sample.ZKSpringJPA.utils.StandardFormat;
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
import org.zkoss.zul.ListModelList;

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

    @Getter @Setter
    private int pageSize = StandardFormat.getDefaultPageSize();

    @Getter @Setter
    private int totalSize;

    @Getter @Setter
    private int activePage;

    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();
    //endregion

    //region > Constructor
    @Init
    public void init(){
        research(0, pageSize);
        totalSize = employeeService.count();
    }

    //endregion

    //region > Command
    @Command
    public void edit(@BindingParam("employee") final Employee employee){
        Executions.getCurrent().sendRedirect("?m=employee-editor&id="+employee.getId());
    }
    @Command
    public void changeActivePage(@BindingParam("index") final int activePage){
        this.activePage = activePage;
        int offset = activePage* pageSize;
        research(offset, pageSize);
    }
    @Command
    @NotifyChange({"pageSize"})
    public void changePageSize(@BindingParam("size") final int pageSize){
        this.pageSize = pageSize;
        research(0, pageSize);
    }
    //endregion

    //region > Programmatic
    private void research(final int offset, final int limit){
        employees = new ListModelList<Employee>(employeeService.findPaging(offset, limit));
        postNotifyChange("employees", this);
    }
    private void postNotifyChange(final String property, Object bean) {
        BindUtils.postNotifyChange(null, null, bean, property);
    }
    //endregion
}
