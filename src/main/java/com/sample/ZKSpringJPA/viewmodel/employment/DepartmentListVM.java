package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;

import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/departmentlist.zul",
        uuid = "departmentlist",
        menuOrder = "2.4",
        displayName = "Department List",
        menuIcon = "building-o"
)
public class DepartmentListVM {
}
