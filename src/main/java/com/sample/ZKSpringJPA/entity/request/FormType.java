package com.sample.ZKSpringJPA.entity.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.viewmodel.request.leaveform.LeaveFormVM;
import lombok.Getter;
import lombok.Setter;

public enum FormType {
    LEAVE_REQUEST(1, "Leave Request", "HR Form", "plane", "leave-form", LeaveFormVM.class);

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String category;

    @Getter @Setter
    private String icon;

    @Getter @Setter
    private Class cls;

    @Getter @Setter
    private String menuUuid;

    <T> FormType(final int value, final String name, final String category, final String icon, final String menuUuid, final Class<T> cls) {
        this.value = value;
        this.category = category;
        this.name = name;
        this.icon = icon;
        this.cls = cls;
        this.menuUuid = menuUuid;
    }

    public Feature getFeature() throws ClassNotFoundException {
        Feature fs = (Feature) cls.getAnnotation(Feature.class);
        return fs;
    }

    public String getUuid() throws ClassNotFoundException {
        return getFeature().uuid();
    }
}
