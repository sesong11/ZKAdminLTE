package com.sample.ZKSpringJPA.viewmodel.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;

public abstract class ViewModel {
	
	public static final Logger logger = LogManager.getLogger(ViewModel.class);
	
    public void postNotifyChange(final Object bean, final String property) {
        BindUtils.postNotifyChange(null, null, bean, property);
    }
}
