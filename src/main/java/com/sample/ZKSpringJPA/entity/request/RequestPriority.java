package com.sample.ZKSpringJPA.entity.request;

import lombok.Getter;
import lombok.Setter;

public enum RequestPriority {
    LOW(1, "Low", "default"),
    NORMAL(2, "Normal", "success"),
    URGENT(3, "Urgent", "warning"),
    IMPORTANT(4, "Important", "primary"),
    CRITICAL(5, "Critical", "danger");

    @Getter
    @Setter
    private int value;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String sclass;

    RequestPriority(final int value, final String name, final String sclass) {
        this.value = value;
        this.name = name;
        this.sclass = sclass;
    }
}
