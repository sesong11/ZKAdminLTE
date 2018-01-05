package com.sample.ZKSpringJPA.viewmodel.utils;

import org.zkoss.bind.BindUtils;

public abstract class ViewModel {
    public void postNotifyChange(final Object bean, final String property) {
        BindUtils.postNotifyChange(null, null, bean, property);
    }
}
