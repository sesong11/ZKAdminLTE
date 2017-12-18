package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        uuid = "request",
        menuOrder = "3",
        displayName = "Request",
        menuIcon = "tag"
)
public class RequestMenu {
}
