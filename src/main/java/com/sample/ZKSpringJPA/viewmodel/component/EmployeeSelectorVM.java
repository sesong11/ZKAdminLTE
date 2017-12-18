package com.sample.ZKSpringJPA.viewmodel.component;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeeSelectorVM {

    //region > Fields
    @Getter
    private final String standardDateFormat = StandardFormat.getStandardDateFormat();

    @Getter @Setter
    private List<Employee> employees;
    //endregion

    //region > Constructor
    @Init
    public void init(@ExecutionArgParam("employees") final List<Employee> employees){
        this.employees = employees;
    }
    //endregion

}
