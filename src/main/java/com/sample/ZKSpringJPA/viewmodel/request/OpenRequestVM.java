package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/dashboard.zul",
        uuid = "4open-request",
        menuOrder = "3.4",
        displayName = "Open Request",
        menuIcon = "folder-open"
)
public class OpenRequestVM {
}
