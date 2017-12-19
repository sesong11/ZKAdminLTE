package com.sample.ZKSpringJPA.viewmodel.component;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeeSelectorVM {

    //region > Inject Service
    @Wire("#modalDialog")
    private Window win;
    //endregion

    //region > Fields
    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();

    @Getter @Setter
    private ListModel<Employee> employees;

    @Getter @Setter
    private String receiver;

    @Getter @Setter
    private Employee employee;
    //endregion

    //region > Constructor
    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view,
            @ExecutionArgParam("employees") final ListModelList<Employee> employees,
                     @ExecutionArgParam("receiver") final String receiver){
        Selectors.wireComponents(view, this, false);
        this.employees = employees;
        this.receiver = receiver;
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
    //endregion
}
