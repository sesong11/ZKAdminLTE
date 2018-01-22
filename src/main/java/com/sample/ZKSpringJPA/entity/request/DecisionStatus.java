package com.sample.ZKSpringJPA.entity.request;

import lombok.Getter;
import lombok.Setter;

public enum DecisionStatus {
    AWAITING(1, "Awaiting", "default"),
    APPROVED(2, "Approved", "success"),
    REJECTED(3, "Rejected", "danger");

    @Getter
    @Setter
    private int value;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String sclass;

    DecisionStatus(final int value, final String name, final String sclass) {
        this.value = value;
        this.name = name;
        this.sclass = sclass;
    }
}
