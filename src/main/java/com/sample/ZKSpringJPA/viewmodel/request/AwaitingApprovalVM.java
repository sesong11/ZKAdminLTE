package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/dashboard.zul",
        uuid = "4-awaiting-approval",
        menuOrder = "3.2",
        displayName = "Awaiting Approve",
        menuIcon = "spinner"
)
public class AwaitingApprovalVM {
}
