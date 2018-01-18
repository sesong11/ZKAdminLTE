package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.entity.employment.AllowanceType;
import com.sample.ZKSpringJPA.viewmodel.request.leaveform.LeaveFormVM;
import lombok.Getter;
import lombok.Setter;

public enum LeaveType {
    ANNUAL_LEAVE(1, "Annual Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.LEAVE_ALLOWANCE),
    MATERNITY_LEAVE(2, "Maternity Leave", new LeaveCalculatorOverall(),
            AllowanceType.MATERNITY_LEAVE),
    Emergency_LEAVE(3, "Emergency Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.LEAVE_ALLOWANCE),
    SICK_LEAVE(4, "Sick Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.SICK_LEAVE),
    COMPASSIONATE_LEAVE(5,"Compassionate Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.COMPASSIONATE_LEAVE),
    HOSPITALIZATION_LEAVE(6, "Hospitalization Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.HOSPITALIZATION_LEAVE),
    MARRIAGE_LEAVE(7, "Marriage Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.MARRIAGE_LEAVE),
    PATERNITY_LEAVE(8, "Paternity Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.PATERNITY_LEAVE),
    UNPAID_LEAVE(9, "Unpaid Leave", new LeaveCalculatorExcludeDayOff(),
            null),
    OTHER(10, "Other Special Paid Leave", new LeaveCalculatorExcludeDayOff(),
            AllowanceType.LEAVE_ALLOWANCE);
    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private LeaveCalculator calculator;

    @Getter @Setter
    private AllowanceType allowanceType;

    LeaveType(final int value, final String name,
              final LeaveCalculator calculator,
              final AllowanceType allowanceType) {
        this.value = value;
        this.name = name;
        this.calculator = calculator;
        this.allowanceType = allowanceType;
    }
}
