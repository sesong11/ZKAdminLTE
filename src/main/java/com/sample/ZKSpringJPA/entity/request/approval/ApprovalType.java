package com.sample.ZKSpringJPA.entity.request.approval;

import lombok.Getter;
import lombok.Setter;

public enum ApprovalType {
    RELIEF(1, "Relief"),
    APPROVE(2, "Approve"),
    AUTHORIZE(3, "Authorize");

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;


    ApprovalType(final int value, final String name) {
        this.value = value;
        this.name = name;
    }
}
