package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.viewmodel.request.leaveform.LeaveFormVM;
import lombok.Getter;
import lombok.Setter;

public enum LeaveType {
    ANNUAL_LEAVE(1, "Annual Leave", new LeaveCalculatorExcludeDayOff()),
    MATERNITY_LEAVE(2, "Maternity Leave", new LeaveCalculatorOverall()),
    Emergency_LEAVE(3, "Emergency Leave", new LeaveCalculatorExcludeDayOff()),
    SICK_LEAVE(4, "Sick Leave", new LeaveCalculatorExcludeDayOff()),
    COMPASSIONATE_LEAVE(5,"Compassionate Leave", new LeaveCalculatorExcludeDayOff()),
    HOSPITALIZATION_LEAVE(6, "Hospitalization Leave", new LeaveCalculatorExcludeDayOff()),
    MARRIAGE_LEAVE(7, "Marriage Leave", new LeaveCalculatorExcludeDayOff()),
    PATERNITY_LEAVE(8, "Paternity Leave", new LeaveCalculatorExcludeDayOff()),
    UNPAID_LEAVE(9, "Unpaid Leave", new LeaveCalculatorExcludeDayOff()),
    OTHER(10, "Other Special Paid Leave", new LeaveCalculatorExcludeDayOff());

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private LeaveCalculator calculator;


    LeaveType(final int value, final String name, final LeaveCalculator calculator) {
        this.value = value;
        this.name = name;
        this.calculator = calculator;
    }
}
