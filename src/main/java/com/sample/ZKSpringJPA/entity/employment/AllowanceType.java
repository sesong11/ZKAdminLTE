package com.sample.ZKSpringJPA.entity.employment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AllowanceType {
    LEAVE_ALLOWANCE(1, "Annual Leave"),
    MATERNITY_LEAVE(2, "Maternity Leave"),
    SICK_LEAVE(3, "Sick Leave"),
    HOSPITALIZATION_LEAVE(4, "Hospitalization Leave"),
    COMPASSIONATE_LEAVE(5, "Compassionate Leave"),
    MARRIAGE_LEAVE(6, "Marriage Leave"),
    PATERNITY_LEAVE(7, "Paternity Leave");

    @Getter
    private int value;
    @Getter
    private String name;
}
