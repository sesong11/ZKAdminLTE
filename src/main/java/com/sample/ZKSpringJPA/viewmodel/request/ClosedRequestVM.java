package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/dashboard.zul",
        uuid = "5-closed-request",
        menuOrder = "3.5",
        displayName = "Closed Request",
        menuIcon = "folder"
)
public class ClosedRequestVM {
}
