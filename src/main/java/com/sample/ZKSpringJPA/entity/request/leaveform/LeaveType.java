package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.viewmodel.request.leaveform.LeaveFormVM;
import lombok.Getter;
import lombok.Setter;

public enum LeaveType {
    ANNUAL_LEAVE(1, "Annual Leave"),
    MATERNITY_LEAVE(2, "Maternity Leave"),
    Emergency_LEAVE(3, "Emergency Leave"),
    SICK_LEAVE(4, "Sick Leave"),
    COMPASSIONATE_LEAVE(5,"Compassionate Leave"),
    HOSPITALIZATION_LEAVE(6, "Hospitalization Leave"),
    MARRIAGE_LEAVE(7, "Marriage Leave"),
    PATERNITY_LEAVE(8, "Paternity Leave"),
    UNPAID_LEAVE(9, "Unpaid Leave"),
    OTHER(10, "Other Special Paid Leave");

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;


    LeaveType(final int value, final String name) {
        this.value = value;
        this.name = name;
    }

}
