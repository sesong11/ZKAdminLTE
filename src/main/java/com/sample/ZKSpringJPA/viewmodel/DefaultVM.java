package com.sample.ZKSpringJPA.viewmodel;

import com.sample.ZKSpringJPA.anotation.Feature;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/default.zul",
        uuid = "default",
        menuOrder = "0.0"
)
public class DefaultVM {
}
