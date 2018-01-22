package com.sample.ZKSpringJPA.entity.request;

import lombok.Getter;
import lombok.Setter;

public enum RequestStatus {
    DRAFT(1, "Draft"),
    PENDING(2, "Pending"),
    OPEN(3, "Open"),
    CLOSED(4, "Closed");

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;

    RequestStatus(final int value, final String name) {
        this.value = value;
        this.name = name;
    }
}
