package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/dashboard.zul",
        uuid = "3pending-request",
        menuOrder = "3.3",
        displayName = "Pending Request",
        menuIcon = "tag"
)
public class PendingRequestVM {
}
