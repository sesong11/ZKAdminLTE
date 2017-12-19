package com.sample.ZKSpringJPA.entity.request;

import lombok.Getter;
import lombok.Setter;

public enum RequestPriority {
    LOW(1, "Low"),
    NORMAL(2, "Normal"),
    URGENT(3, "Urgent"),
    IMPORTANT(4, "Important"),
    CRITICAL(5, "Critical");

    @Getter
    @Setter
    private int value;

    @Getter @Setter
    private String name;

    RequestPriority(final int value, final String name) {
        this.value = value;
        this.name = name;
    }
}
