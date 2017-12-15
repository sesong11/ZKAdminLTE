package com.sample.ZKSpringJPA.entity.employment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AllowanceType {
    LEAVE_ALLOWANCE(1, "Leave Allowance");

    @Getter
    private int value;
    @Getter
    private String name;
}
